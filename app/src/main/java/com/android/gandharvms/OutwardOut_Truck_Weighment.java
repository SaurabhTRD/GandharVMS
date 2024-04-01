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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.gandharvms.LoginWithAPI.LoginMethod;
import com.android.gandharvms.LoginWithAPI.ResponseModel;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.Outward_Tanker_Security.Grid_Outward;
import com.android.gandharvms.Outward_Tanker_Security.Outward_RetroApiclient;
import com.android.gandharvms.Outward_Tanker_Weighment.Model_OutwardOut_Truck_Weighment;
import com.android.gandharvms.Outward_Tanker_Weighment.Outward_Tanker_weighment;
import com.android.gandharvms.Outward_Tanker_Weighment.Outward_weighment;
import com.android.gandharvms.Outward_Tanker_Weighment.Response_Outward_Tanker_Weighment;
import com.android.gandharvms.Outward_Truck_Billing.Outward_Truck_Billing;
import com.android.gandharvms.Outward_Truck_Laboratory.Outward_Truck_Laboratory;
import com.android.gandharvms.Outward_Truck_Weighment.Weigh_Out_OR_Complete;
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

public class OutwardOut_Truck_Weighment extends AppCompatActivity {

    EditText intime,serialnumber,vehiclenum,grosswright,noofpack,netwt,etremark,seal,ettare,etshdip,etshwe;

    Button submit,etcompleted;
    FirebaseFirestore dbroot;
    TimePickerDialog tpicker;
    Calendar calendar = Calendar.getInstance();

    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private final String EmployeId = Global_Var.getInstance().EmpId;
    private int OutwardId;
    private Outward_weighment outwardWeighment;
    SimpleDateFormat dtFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
    DatePickerDialog picker;
    private LoginMethod userDetails;
    private String token;
    private String woutTime;
    private String wvehiclenumber;
    Uri image1, image2;
    byte[] ImgDriver, ImgVehicle;
    byte[][] arrayOfByteArrays = new byte[2][];
    private String imgPath1, imgPath2;
    private String wserialNo;
    ImageView img1, img2;
    private static final int CAMERA_PERM_CODE1 = 100;
    private static final int CAMERA_PERM_CODE = 101;
    private static final int CAMERA_REQUEST_CODE = 102;
    private static final int CAMERA_REQUEST_CODE1 = 103;
    private int ushdip;
    private int ushwe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outward_out_truck_weighment);
        outwardWeighment = Outward_RetroApiclient.outwardWeighment();
        userDetails = RetroApiClient.getLoginApi();

        intime=findViewById(R.id.etintime);
        serialnumber=findViewById(R.id.etserialnumber);
        vehiclenum=findViewById(R.id.etvehical);
        grosswright=findViewById(R.id.etgrossweight);
        noofpack=findViewById(R.id.etpack);
        netwt = findViewById(R.id.etnetwwight);
        etremark = findViewById(R.id.etOTW_remark);
        seal = findViewById(R.id.etseal);
        ettare = findViewById(R.id.ettarewt);

        submit = findViewById(R.id.submit);
        etcompleted = findViewById(R.id.orweoutcomple);
        dbroot= FirebaseFirestore.getInstance();

        img1 = findViewById(R.id.outwardouttruckvehicle);
        img2 = findViewById(R.id.outwardouttruckdriver);
        etshdip = findViewById(R.id.etshortdip);
        etshwe = findViewById(R.id.etshortageweight);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (image1 == null || image2 == null) {
                    Toasty.warning(OutwardOut_Truck_Weighment.this, "Please Upload Image", Toast.LENGTH_SHORT).show();
                } else {
                    UploadImagesAndUpdate();
                }
//                insert();
            }
        });
        etcompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(OutwardOut_Truck_Weighment.this,Weigh_Out_OR_Complete.class));

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

        vehiclenum.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    FetchVehicleDetails(vehiclenum.getText().toString().trim(), vehicleType, nextProcess, inOut);
                }
            }
        });

        netwt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                calculateNetWeight();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void calculateNetWeight() {

        String trweight = ettare.getText().toString().trim();
        String ntweight = netwt.getText().toString().trim();

        double tweig = trweight.isEmpty() ? 0.0 : Double.parseDouble(trweight);
        double netweigh = ntweight.isEmpty() ? 0.0 : Double.parseDouble(ntweight);

        double grossweig = tweig + netweigh;

        grosswright.setText(String.valueOf(grossweig));
    }

    public void makeNotification(String vehicleNumber) {
        Call<List<ResponseModel>> call = userDetails.getUsersListData();
        call.enqueue(new Callback<List<ResponseModel>>() {
            @Override
            public void onResponse(Call<List<ResponseModel>> call, Response<List<ResponseModel>> response) {
                if (response.isSuccessful()){
                    List<ResponseModel> userList = response.body();
                    if (userList != null){
                        for (ResponseModel resmodel : userList){
                            String specificRole = "Billing";
                            if (specificRole.equals(resmodel.getDepartment())) {
                                token = resmodel.getToken();

                                FcmNotificationsSender notificationsSender = new FcmNotificationsSender(
                                        token,
                                        "OutwardOut Truck Weighment Process Done..!",
                                        "Vehicle Number:-" + vehicleNumber + " has completed Weighment process at ",
                                        getApplicationContext(),
                                        OutwardOut_Truck_Weighment.this
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
                Toasty.error(OutwardOut_Truck_Weighment.this, "failed..!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void FetchVehicleDetails(@NonNull String vehicleNo, String vehicleType, char NextProcess, char inOut) {
        Call<Response_Outward_Tanker_Weighment> call = outwardWeighment.fetchweighment(vehicleNo,vehicleType,NextProcess,inOut);
        call.enqueue(new Callback<Response_Outward_Tanker_Weighment>() {
            @Override
            public void onResponse(Call<Response_Outward_Tanker_Weighment> call, Response<Response_Outward_Tanker_Weighment> response) {
                if (response.isSuccessful()){
                    Response_Outward_Tanker_Weighment data = response.body();
                    if (data.getVehicleNumber()!= "" && data.getVehicleNumber()!= null){
                        OutwardId =data.getOutwardId();
                        serialnumber.setText(data.getSerialNumber());
                        vehiclenum.setText(data.getVehicleNumber());
                        serialnumber.setEnabled(false);
                        vehiclenum.setEnabled(false);
                        wvehiclenumber = data.getVehicleNumber();
                        woutTime = data.getOutTime();
                        ettare.setText(data.getTareWeight());
                        ettare.setEnabled(false);
                        wserialNo= data.getSerialNumber();

                    }else {
                        Toasty.error(OutwardOut_Truck_Weighment.this, "This Vehicle Number Is Not Available..!", Toast.LENGTH_SHORT).show();
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

//        intime,serialnumber,vehiclenum,grosswright,noofpack;
        String etintime = intime.getText().toString().trim();
        String unetwt = netwt.getText().toString().trim();
        String etgrossweight = grosswright.getText().toString().trim();
        String etnoofpack = noofpack.getText().toString().trim();
        String uremark = etremark.getText().toString().trim();
        String useal = seal.getText().toString().trim();



        if (!etshdip.getText().toString().isEmpty()){
            try {
                 ushdip = Integer.parseInt(etshdip.getText().toString().trim());
            }catch (NumberFormatException e){
                e.printStackTrace();
            }
        }else {
            Toasty.warning(this,"Shortage Dip Is Empty",Toast.LENGTH_SHORT).show();
//            ushdip=0;
        }

        if (!etshwe.getText().toString().isEmpty()){
            try {
                 ushwe = Integer.parseInt(etshwe.getText().toString().trim());
            }catch (NumberFormatException e){
                e.printStackTrace();
            }
        }else {
            Toasty.warning(this,"Shortage Weight Is Empty",Toast.LENGTH_SHORT).show();
        }


//        String etserialnumber = serialnumber.getText().toString().trim();
//        String etvehiclenum = vehiclenum.getText().toString().trim();



        if (etintime.isEmpty()||etgrossweight.isEmpty()||etnoofpack.isEmpty() ){
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
        }else {
            Model_OutwardOut_Truck_Weighment modelOutwardOutTruckWeighment = new Model_OutwardOut_Truck_Weighment(OutwardId,
                    imgPath1,imgPath2,etintime,unetwt,etgrossweight,etnoofpack,uremark,useal,EmployeId,'B',inOut,
                    vehicleType,ushdip,ushwe);
            Call<Boolean> call = outwardWeighment.updateoutwardouttruckweighment(modelOutwardOutTruckWeighment);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful()&& response.body() && response.body() == true){
                       // not available outtime and vehicle no for notification
                        makeNotification(wvehiclenumber);
                        Toasty.success(OutwardOut_Truck_Weighment.this, "Data Inserted Successfully", Toast.LENGTH_SHORT,true).show();
                        startActivity(new Intent(OutwardOut_Truck_Weighment.this, OutwardOut_Truck.class));
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
                    Toasty.error(OutwardOut_Truck_Weighment.this, "failed..!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void UploadImagesAndUpdate() {
        String FileInitial = "OutwardVeh_Out_";
        arrayOfByteArrays[0] = ImgVehicle;
        arrayOfByteArrays[1] = ImgDriver;
        imgPath1 = "GAimages/"+ FileInitial + wserialNo.toString() + ".jpeg";
        for (byte[] byteArray : arrayOfByteArrays) {

            MultipartTask multipartTask = new MultipartTask(byteArray, FileInitial + wserialNo.toString() + ".jpeg", "");
            multipartTask.execute();
            FileInitial = "OutwardDrv_Out_";
        }
        imgPath2 = "GAimages/"+ FileInitial + wserialNo.toString() + ".jpeg";
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
    public void outwardouttruckweighmentpending(View view){
        Intent intent = new Intent(this, Grid_Outward.class);
        startActivity(intent);
    }
}