package com.android.gandharvms;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.gandharvms.InwardCompletedGrid.GridCompleted;
import com.android.gandharvms.LoginWithAPI.LoginMethod;
import com.android.gandharvms.LoginWithAPI.ResponseModel;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.Outward_Tanker_Security.Grid_Outward;
import com.android.gandharvms.Outward_Tanker_Security.Outward_RetroApiclient;
import com.android.gandharvms.Outward_Tanker_Weighment.Outward_Tanker_weighment;
import com.android.gandharvms.Outward_Tanker_Weighment.Outward_weighment;
import com.android.gandharvms.Outward_Tanker_Weighment.Response_Outward_Tanker_Weighment;
import com.android.gandharvms.Outward_Truck_Billing.Outward_Truck_Billing;
import com.android.gandharvms.Outward_Truck_Logistic.Outward_Truck_Logistics;
import com.android.gandharvms.Util.MultipartTask;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class Outward_Truck_weighment extends AppCompatActivity {

    EditText intime,serialnumber,vehiclenumber,material,customer,oanumber,tareweight,etremark,etloaded;
    Button submit,btnweighmenttruck;
    FirebaseFirestore dbroot;
    TimePickerDialog tpicker;
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private final String EmployeId = Global_Var.getInstance().EmpId;
    private int OutwardId;
    private Outward_weighment outwardWeighment;
    SimpleDateFormat dtFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
    DatePickerDialog picker;
    private String token;
    private LoginMethod userDetails;
    private String wvehiclenumber;
    Uri image1, image2;
    byte[] ImgDriver, ImgVehicle;
    byte[][] arrayOfByteArrays = new byte[2][];
    private String imgPath1, imgPath2;
    private String serialNo;
    ImageView img1, img2;
    private static final int CAMERA_PERM_CODE1 = 100;
    private static final int CAMERA_PERM_CODE = 101;
    private static final int CAMERA_REQUEST_CODE = 102;
    private static final int CAMERA_REQUEST_CODE1 = 103;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outward_truck_weighment);
        userDetails = RetroApiClient.getLoginApi();

        outwardWeighment = Outward_RetroApiclient.outwardWeighment();

        intime = findViewById(R.id.etintime);
        serialnumber = findViewById(R.id.etserialnumber);
        vehiclenumber = findViewById(R.id.etvehicleno);
//        material = findViewById(R.id.etmaterial);
        customer = findViewById(R.id.etcust);
        oanumber = findViewById(R.id.etoanumber);
        tareweight = findViewById(R.id.ettareweight);
        etremark = findViewById(R.id.etremark);
        etloaded = findViewById(R.id.etloadmaterialqty);

        submit = findViewById(R.id.etssubmit);
        btnweighmenttruck = findViewById(R.id.outwardtruckweighmentcompleted);
        dbroot= FirebaseFirestore.getInstance();

        img1 = findViewById(R.id.outwardtriuckinvehicle);
        img2 = findViewById(R.id.outwardtruckindriver);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (image1 == null || image2 == null) {
                    Toasty.warning(Outward_Truck_weighment.this, "Please Upload Image", Toast.LENGTH_SHORT).show();
                } else {
                    UploadImagesAndUpdate_outwardtruck();
                }
                //insert();
            }
        });
        btnweighmenttruck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Outward_Truck_weighment.this, GridCompleted.class));
            }
        });

        if (getIntent().hasExtra("vehiclenum")) {
            FetchVehicleDetails(getIntent().getStringExtra("vehiclenum"), Global_Var.getInstance().MenuType, nextProcess, inOut);
        }

        intime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                String time =  format.format(calendar.getTime());
                intime.setText(time);
            }
        });
        vehiclenumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    FetchVehicleDetails(vehiclenumber.getText().toString().trim(), vehicleType, nextProcess, inOut);
                }
            }
        });

    }

    public void makeNotification(String vehicleNumber, String outTime) {
        Call<List<ResponseModel>> call = userDetails.getUsersListData();
        call.enqueue(new Callback<List<ResponseModel>>() {
            @Override
            public void onResponse(Call<List<ResponseModel>> call, Response<List<ResponseModel>> response) {
                if (response.isSuccessful()){
                    List<ResponseModel> userList = response.body();
                    if (userList != null){
                        for (ResponseModel resmodel : userList){
                            String specificRole = "Despatch";
                            if (specificRole.equals(resmodel.getDepartment())) {
                                token = resmodel.getToken();

                                FcmNotificationsSender notificationsSender = new FcmNotificationsSender(
                                        token,
                                        "Outward Truck Weighment Process Done..!",
                                        "Vehicle Number:-" + vehicleNumber + " has completed Weighment process at " + outTime,
                                        getApplicationContext(),
                                        Outward_Truck_weighment.this
                                );
                                notificationsSender.SendNotifications();
                            }
                        }
                    }
                }
                else {
                    Log.d("API", "Unsuccessful API response");
                }
            }

            @Override
            public void onFailure(Call<List<ResponseModel>> call, Throwable t) {

                Log.e("Retrofit", "Failure: " + t.getMessage());
                // Check if there's a response body in case of an HTTP error
                if (call != null && call.isExecuted() && call.isCanceled() && t instanceof HttpException) {
                    Response<?> response = ((HttpException) t).response();
                    if (response != null) {
                        Log.e("Retrofit", "Error Response Code: " + response.code());
                        try {
                            Log.e("Retrofit", "Error Response Body: " + response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                Toasty.error(Outward_Truck_weighment.this, "failed..!", Toast.LENGTH_SHORT).show();
            }
        });

        if (getIntent().hasExtra("vehiclenum")) {
            FetchVehicleDetails(getIntent().getStringExtra("vehiclenum"), Global_Var.getInstance().MenuType, nextProcess, inOut);
        }
    }

    private void FetchVehicleDetails(@NonNull String vehicleNo, String vehicleType, char NextProcess, char inOut) {
        Call<Response_Outward_Tanker_Weighment> call = outwardWeighment.fetchweighment(vehicleNo,vehicleType,NextProcess,inOut);
        call.enqueue(new Callback<Response_Outward_Tanker_Weighment>() {
            @Override
            public void onResponse(Call<Response_Outward_Tanker_Weighment> call, Response<Response_Outward_Tanker_Weighment> response) {
                if (response.isSuccessful()){
                    Response_Outward_Tanker_Weighment data = response.body();
                    if (data.getVehicleNumber() != "" && data.getVehicleNumber() != null){
                        OutwardId = data.getOutwardId();
                        serialnumber.setText(data.getSerialNumber());
                        oanumber.setText(data.getOAnumber());
                        vehiclenumber.setText(data.getVehicleNumber());
                        serialnumber.setEnabled(false);
                        oanumber.setEnabled(false);
                        vehiclenumber.setEnabled(false);
                        wvehiclenumber = data.getVehicleNumber();
                        etloaded.setText(String.valueOf(data.getHowMuchQuantityFilled()));
                        etloaded.setEnabled(false);
                        customer.setText(data.getCustomerName());
                        customer.setEnabled(false);
                    }else {
                        Toasty.error(Outward_Truck_weighment.this, "This Vehicle Number Is Not Available..!", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Log.e("Retrofit", "Error Response Body: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Response_Outward_Tanker_Weighment> call, Throwable t) {

                Log.e("Retrofit", "Failure: " + t.getMessage());
                // Check if there's a response body in case of an HTTP error
                if (call != null && call.isExecuted() && call.isCanceled() && t instanceof HttpException) {
                    Response<?> response = ((HttpException) t).response();
                    if (response != null) {
                        Log.e("Retrofit", "Error Response Code: " + response.code());
                        try {
                            Log.e("Retrofit", "Error Response Body: " + response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    private String getCurrentTime() {
        // Get the current time
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(new Date());
    }

    public void insert(){
//        intime,serialnumber,vehiclenumber,material,customer,oanumber,tareweight;
        String  etintime = intime.getText().toString().trim();
        String ettareweight = tareweight.getText().toString().trim();
        String outTime = getCurrentTime();
//        String etmaterial = material.getText().toString().trim();
        String etcustomer = customer.getText().toString().trim();
        String  uremark = etremark.getText().toString().trim();
       String etserialnumber = serialnumber.getText().toString().trim();
       String etvehiclenumber = vehiclenumber.getText().toString().trim();
//        String etoanumber = oanumber.getText().toString().trim();
        if (etintime.isEmpty()|| etcustomer.isEmpty()||ettareweight.isEmpty()||uremark.isEmpty() ){
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
        }else {
            Response_Outward_Tanker_Weighment responseOutwardTankerWeighment = new Response_Outward_Tanker_Weighment(OutwardId,
                    etintime, outTime,imgPath1,imgPath2,"","","","",
                    ettareweight, "","","","",'W',uremark,EmployeId,
                    "", 'D','I', vehicleType,etserialnumber,etvehiclenumber);
            Call<Boolean> call = outwardWeighment.updateweighmentoutwardtanker(responseOutwardTankerWeighment);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body() && response.body() == true){
                        makeNotification(wvehiclenumber, outTime);
                        Toasty.success(Outward_Truck_weighment.this, "Data Inserted Successfully", Toast.LENGTH_SHORT,true).show();
                        startActivity(new Intent(Outward_Truck_weighment.this, Outward_Truck.class));
                        finish();
                    }else {
                        Log.e("Retrofit", "Error Response Body: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {

                    Log.e("Retrofit", "Failure: " + t.getMessage());
                    // Check if there's a response body in case of an HTTP error
                    if (call != null && call.isExecuted() && call.isCanceled() && t instanceof HttpException) {
                        Response<?> response = ((HttpException) t).response();
                        if (response != null) {
                            Log.e("Retrofit", "Error Response Code: " + response.code());
                            try {
                                Log.e("Retrofit", "Error Response Body: " + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    Toasty.error(Outward_Truck_weighment.this, "failed..!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void UploadImagesAndUpdate_outwardtruck() {

        String FileInitial = "OutwardVeh_In_";
        arrayOfByteArrays[0] = ImgVehicle;
        arrayOfByteArrays[1] = ImgDriver;
        imgPath1 = "GAimages/" + FileInitial + serialNo + ".jpeg";
        for (byte[] byteArray : arrayOfByteArrays) {

            MultipartTask multipartTask = new MultipartTask(byteArray, FileInitial + serialNo + ".jpeg", "");
            multipartTask.execute();
            FileInitial = "OutwardDrv_In_";
        }
        imgPath2 = "GAimages/" + FileInitial + serialNo + ".jpeg";
        FileInitial = "";
        insert();
    }

    public void captureImageFromCamera1(android.view.View view) {
        askCameraPermission(CAMERA_REQUEST_CODE);
    }

    public void captureImageFromCamera2(android.view.View view) {
        askCameraPermission1(CAMERA_REQUEST_CODE1);
    }

    private void askCameraPermission(int requestcode) {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
        } else {
            openCamera(requestcode);
        }
    }

    private void askCameraPermission1(int requestcode1) {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERM_CODE1);
        } else {
            openCamera(requestcode1);
        }
    }

    private void openCamera(int requestCode) {
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (requestCode == CAMERA_REQUEST_CODE) {
            startActivityForResult(camera, CAMERA_REQUEST_CODE);
        } else if (requestCode == CAMERA_REQUEST_CODE1) {
            startActivityForResult(camera, CAMERA_REQUEST_CODE1);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE) {
            Bitmap bimage1 = (Bitmap) data.getExtras().get("data");
            img1.setImageBitmap(bimage1);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bimage1.compress(Bitmap.CompressFormat.JPEG, 90, baos);
            String path = MediaStore.Images.Media.insertImage(getContentResolver(), bimage1, "title1", null);
            image1 = Uri.parse(path);
            ImgVehicle = baos.toByteArray();
        } else if (requestCode == CAMERA_REQUEST_CODE1) {
            Bitmap bimage2 = (Bitmap) data.getExtras().get("data");
            img2.setImageBitmap(bimage2);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bimage2.compress(Bitmap.CompressFormat.JPEG, 90, baos);
            String path = MediaStore.Images.Media.insertImage(getContentResolver(), bimage2, "title2", null);
            image2 = Uri.parse(path);
            ImgDriver = baos.toByteArray();
        }
    }
    public void outtruckweighmentpending(View view){
        Intent intent = new Intent(this, Grid_Outward.class);
        startActivity(intent);
    }
}