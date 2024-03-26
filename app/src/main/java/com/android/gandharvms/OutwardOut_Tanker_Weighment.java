package com.android.gandharvms;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.gandharvms.Inward_Tanker_Weighment.it_in_weigh_Completedgrid;
import com.android.gandharvms.LoginWithAPI.LoginMethod;
import com.android.gandharvms.LoginWithAPI.ResponseModel;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.Outward_Tanker_Security.Grid_Outward;
import com.android.gandharvms.Outward_Tanker_Security.Outward_RetroApiclient;
import com.android.gandharvms.Outward_Tanker_Weighment.Model_OutwardOut_Weighment;
import com.android.gandharvms.Outward_Tanker_Weighment.Outward_Tanker_weighment;
import com.android.gandharvms.Outward_Tanker_Weighment.Outward_weighment;
import com.android.gandharvms.Outward_Tanker_Weighment.Response_Outward_Tanker_Weighment;
import com.android.gandharvms.Util.MultipartTask;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

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

public class OutwardOut_Tanker_Weighment extends AppCompatActivity {

    EditText intime,serialnumber,vehiclenumber,sealnumber,tareweight,netweight,grossw,etnumberpack,etremark,fetchdensity,etotdip,etotwt;
    Button submit;
    FirebaseFirestore dbroot;
    TimePickerDialog tpicker;
    Calendar calendar = Calendar.getInstance();
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private final String EmployeId = Global_Var.getInstance().EmpId;
    private int OutwardId;
    private Outward_weighment outwardWeighment;
    private LoginMethod userDetails;
    private String token;

    private static final int CAMERA_PERM_CODE1 = 100;
    private static final int CAMERA_PERM_CODE = 101;
    private static final int CAMERA_REQUEST_CODE = 102;
    private static final int CAMERA_REQUEST_CODE1 = 103;
    ImageView img1, img2;
    Uri image1, image2;
    byte[] ImgDriver, ImgVehicle;
    byte[][] arrayOfByteArrays = new byte[2][];
    private String imgPath1, imgPath2;
    private String etSerialNumber;
    private String vehicleNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outward_out_tanker_weighment);
        outwardWeighment = Outward_RetroApiclient.outwardWeighment();
        userDetails = RetroApiClient.getLoginApi();

        intime= findViewById(R.id.etintime);
        serialnumber = findViewById(R.id.etserialnumber);
        vehiclenumber = findViewById(R.id.etvehicleno);
        fetchdensity=findViewById(R.id.etdensitybyfetching);
        tareweight=findViewById(R.id.ettareweight);
        netweight = findViewById(R.id.etnetweight);
        grossw = findViewById(R.id.etgrosswt);
        sealnumber = findViewById(R.id.etsealnumber);
        etnumberpack = findViewById(R.id.etnumberpack);
        etremark = findViewById(R.id.etremakr);

        img1=findViewById(R.id.otoutweighvehicleimage);
        img2=findViewById(R.id.otoutweighdriverimage);

        submit = findViewById(R.id.etssubmit);
        dbroot= FirebaseFirestore.getInstance();
        etotdip = findViewById(R.id.otouttankeretshortdip);
        etotwt = findViewById(R.id.otouttankeretshortwt);

        FirebaseMessaging.getInstance().subscribeToTopic(token);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (image1 == null || image2 == null) {
                    Toasty.warning(OutwardOut_Tanker_Weighment.this, "Please Upload Image", Toast.LENGTH_SHORT).show();
                } else {
                    UploadImagesAndUpdate();
                }
            }
        });
        vehiclenumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    FetchVehicleDetails(vehiclenumber.getText().toString().trim(), vehicleType, nextProcess, inOut);
                }
            }
        });

        if (getIntent().hasExtra("vehiclenum")) {
            FetchVehicleDetails(getIntent().getStringExtra("vehiclenum"), Global_Var.getInstance().MenuType, nextProcess, inOut);
        }
        netweight.addTextChangedListener(new TextWatcher() {
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

        intime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                String time =  format.format(calendar.getTime());
                intime.setText(time);
            }
        });
    }

    public void calculateNetWeight() {

        String trweight = tareweight.getText().toString().trim();
        String ntweight = netweight.getText().toString().trim();

        double tweig = trweight.isEmpty() ? 0.0 : Double.parseDouble(trweight);
        double netweigh = ntweight.isEmpty() ? 0.0 : Double.parseDouble(ntweight);

        double grossweig = tweig + netweigh;

        grossw.setText(String.valueOf(grossweig));
    }
    private void FetchVehicleDetails(@NonNull String vehicleNo, String vehicleType, char NextProcess, char inOut) {
        Call<Response_Outward_Tanker_Weighment> call = outwardWeighment.fetchweighment(vehicleNo,vehicleType,NextProcess,inOut);
        call.enqueue(new Callback<Response_Outward_Tanker_Weighment>() {
            @Override
            public void onResponse(Call<Response_Outward_Tanker_Weighment> call, Response<Response_Outward_Tanker_Weighment> response) {
                if (response.isSuccessful()){
                    Response_Outward_Tanker_Weighment data = response.body();
                    if (data.getVehicleNumber() != "" && data.getVehicleNumber()!=null){
                        OutwardId = data.getOutwardId();
                        etSerialNumber=data.getSerialNumber();
                        serialnumber.setText(data.getSerialNumber());
                        serialnumber.setEnabled(false);
                        vehiclenumber.setText(data.getVehicleNumber());
                        vehiclenumber.setEnabled(false);
                        vehicleNum=data.getVehicleNumber();
                        fetchdensity.setText(String.valueOf(data.getDensity_29_5C()));
                        fetchdensity.setEnabled(false);
                        tareweight.setText(String.valueOf(data.getTareWeight()));
                        tareweight.setEnabled(false);
                       /* intime.requestFocus();
                        intime.callOnClick();*/
                    }
                    else{
                        Toasty.error(OutwardOut_Tanker_Weighment.this, "This Vehicle Number is Not Availabe", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
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

    public void makeNotification(String vehicleNumber) {
        Call<List<ResponseModel>> call = userDetails.getUsersListData();
        call.enqueue(new Callback<List<ResponseModel>>() {
            @Override
            public void onResponse(Call<List<ResponseModel>> call, Response<List<ResponseModel>> response) {
                if (response.isSuccessful()){
                    List<ResponseModel> userList = response.body();
                    if (userList != null){
                        for (ResponseModel resmodel : userList){
                            String specificRole = "Production";
                            if (specificRole.equals(resmodel.getDepartment())) {
                                token = resmodel.getToken();

                                FcmNotificationsSender notificationsSender = new FcmNotificationsSender(
                                        token,
                                        "Outward Tanker OutWeighment Process Done..!",
                                        "Vehicle Number:-" + vehicleNumber + " has completed OutWeighment Process",
                                        getApplicationContext(),
                                        OutwardOut_Tanker_Weighment.this
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
                Toasty.error(OutwardOut_Tanker_Weighment.this, "failed..!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void insert(){
        String etintime = intime.getText().toString().trim();
        String etsealnumber = sealnumber.getText().toString().trim();
        String etnetweight = netweight.getText().toString().trim();
        String outTime = getCurrentTime();
        String ugrosswt = grossw.getText().toString().trim();
        String unumberpack = etnumberpack.getText().toString().trim()!=null?etnumberpack.getText().toString():"";
        String uremark = etremark.getText().toString().trim()!=null?etremark.getText().toString():"";
        int ushdip = Integer.parseInt(etotdip.getText().toString().trim());
        int ushwt = Integer.parseInt(etotwt.getText().toString().trim());
        if (etintime.isEmpty()||etsealnumber.isEmpty()||etnetweight.isEmpty()||ugrosswt.isEmpty()
                ||uremark.isEmpty()||unumberpack.isEmpty()) {
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
        }else {
            Model_OutwardOut_Weighment modelOutwardOutWeighment = new Model_OutwardOut_Weighment(OutwardId,imgPath2,imgPath1,
                    etintime,etnetweight,ugrosswt,unumberpack,uremark,etsealnumber,EmployeId,'P',inOut,vehicleType,ushdip,ushwt);
            Call<Boolean> call = outwardWeighment.updateoutwardoutweighment(modelOutwardOutWeighment);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body() && response.body() == true){
                        makeNotification(vehicleNum);
                        Toasty.success(OutwardOut_Tanker_Weighment.this, "Data Inserted Successfully", Toast.LENGTH_SHORT, true).show();
                        startActivity(new Intent(OutwardOut_Tanker_Weighment.this, OutwardOut_Tanker.class));
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
                    Toasty.error(OutwardOut_Tanker_Weighment.this, "failed..!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void UploadImagesAndUpdate() {
        String FileInitial = "OutwardVeh_Out_";
        arrayOfByteArrays[0] = ImgVehicle;
        arrayOfByteArrays[1] = ImgDriver;
        imgPath1 = "GAimages/"+ FileInitial + etSerialNumber.toString() + ".jpeg";
        for (byte[] byteArray : arrayOfByteArrays) {

            MultipartTask multipartTask = new MultipartTask(byteArray, FileInitial + etSerialNumber.toString() + ".jpeg", "");
            multipartTask.execute();
            FileInitial = "OutwardDrv_Out_";
        }
        imgPath2 = "GAimages/"+ FileInitial + etSerialNumber.toString() + ".jpeg";
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

    public void outwardoutpendingweighViewclick(View view) {
        Intent intent = new Intent(this, Grid_Outward.class);
        startActivity(intent);
    }

    public void otoutweighcompletedclick(View view) {
        /*Intent intent = new Intent(this, it_in_weigh_Completedgrid.class);
        startActivity(intent);*/
    }
}