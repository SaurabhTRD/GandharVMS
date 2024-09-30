package com.android.gandharvms.Outward_Truck_Weighment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.gandharvms.FcmNotificationsSender;
import com.android.gandharvms.Global_Var;
import com.android.gandharvms.LoginWithAPI.Login;
import com.android.gandharvms.LoginWithAPI.LoginMethod;
import com.android.gandharvms.LoginWithAPI.ResponseModel;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.Menu;
import com.android.gandharvms.Outward_Truck_Dispatch.Update_SmallPack_Model;
import com.android.gandharvms.Outward_Truck_Dispatch.Varified_Model;
import com.android.gandharvms.Outward_Tanker_Security.Grid_Outward;
import com.android.gandharvms.Outward_Tanker_Security.Outward_RetroApiclient;
import com.android.gandharvms.Outward_Tanker_Weighment.Outward_weighment;
import com.android.gandharvms.Outward_Tanker_Weighment.Response_Outward_Tanker_Weighment;
import com.android.gandharvms.Outward_Truck;
import com.android.gandharvms.Outward_Truck_Dispatch.Outward_Truck_interface;
import com.android.gandharvms.Outward_Truck_Dispatch.Verified_Small_pack;
import com.android.gandharvms.R;
import com.android.gandharvms.Util.ImageUtils;
import com.android.gandharvms.Util.MultipartTask;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class Outward_Truck_weighment extends AppCompatActivity {

    EditText intime,serialnumber,vehiclenumber,material,customer,oanumber,tareweight,etremark,etloaded,etloadedtyuom,desweight,destotalqty,etvariremark,spweight,spqty;
    Button submit,btnweighmenttruck,varified;
    FirebaseFirestore dbroot;
    TimePickerDialog tpicker;
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private final String EmployeId = Global_Var.getInstance().EmpId;
    private int OutwardId;
    private Outward_weighment outwardWeighment;
    private Outward_Truck_interface outwardTruckInterface;
    SimpleDateFormat dtFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
    DatePickerDialog picker;
    private String token;
    private LoginMethod userDetails;
    private String wvehiclenumber;
    Uri image1, image2;
    byte[] ImgDriver, ImgVehicle;
    byte[][] arrayOfByteArrays = new byte[2][];
    Uri[] LocalImgPath = new Uri[2];
    private String imgPath1, imgPath2;
    private String serialNo;
    ImageView img1, img2;
    private static final int CAMERA_PERM_CODE1 = 100;
    private static final int CAMERA_PERM_CODE = 101;
    private static final int CAMERA_REQUEST_CODE = 102;
    private static final int CAMERA_REQUEST_CODE1 = 103;
    public LinearLayout layout,imglay,img;
    private String desaweight = "0";
    private String desatotalqty = "0";
    public String despatchnext = "";

    private String splweight = "0";
    private String spltotalqty = "0";
    public String smallnext = "";
    char despatchNextChar = ' ';
    char smallNextChar = ' ';

    ImageView btnlogout,btnhome;
    TextView username,empid;

    public static String Tanker;
    public static String Truck;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outward_truck_weighment);
        userDetails = RetroApiClient.getLoginApi();
        outwardTruckInterface = Outward_RetroApiclient.outwardtruckdispatch();


        outwardWeighment = Outward_RetroApiclient.outwardWeighment();

        FirebaseMessaging.getInstance().subscribeToTopic(token);

        intime = findViewById(R.id.etintime);
        serialnumber = findViewById(R.id.etserialnumber);
        vehiclenumber = findViewById(R.id.etvehicleno);
//        material = findViewById(R.id.etmaterial);
        customer = findViewById(R.id.etcust);
        oanumber = findViewById(R.id.etoanumber);
        tareweight = findViewById(R.id.ettareweight);
        etremark = findViewById(R.id.etremark);
        etloaded = findViewById(R.id.etloadmaterialqty);
        etloadedtyuom=findViewById(R.id.etloadmaterialqtyuom);

        submit = findViewById(R.id.etssubmit);
        btnweighmenttruck = findViewById(R.id.outwardtruckweighmentcompleted);
        dbroot= FirebaseFirestore.getInstance();

        img1 = findViewById(R.id.outwardtriuckinvehicle);
        img2 = findViewById(R.id.outwardtruckindriver);

        layout = findViewById(R.id.despatchchecklinear);
        imglay = findViewById(R.id.imglayout);
        img = findViewById(R.id.imglayou);

        desweight = findViewById(R.id.etindussmallweight);
        destotalqty = findViewById(R.id.ettotalqty);

        layout.setVisibility(View.GONE);
        varified = findViewById(R.id.btnvarified);
        varified.setVisibility(View.GONE);
        etvariremark = findViewById(R.id.etindusremark);
        etvariremark.setVisibility(View.GONE);

        desweight.setVisibility(View.GONE);
        destotalqty.setVisibility(View.GONE);

        spweight= findViewById(R.id.etsmallpackweight);
        spweight.setVisibility(View.GONE);
        spqty= findViewById(R.id.etsmallpackqty);
        spqty.setVisibility(View.GONE);

        btnhome = findViewById(R.id.btn_homeButton);
        btnlogout=findViewById(R.id.btn_logoutButton);
        username=findViewById(R.id.tv_username);
        empid=findViewById(R.id.tv_employeeId);

        String userName=Global_Var.getInstance().Name;
        String empId=Global_Var.getInstance().EmpId;

        username.setText(userName);
        empid.setText(empId);
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Outward_Truck_weighment.this, Login.class));
            }
        });
        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Outward_Truck_weighment.this, Menu.class));
            }
        });

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
        varified.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    // Check if desaweight is a decimal or an integer
                    float desaweightFloat = desaweight.contains(".") ? Float.parseFloat(desaweight) : Integer.parseInt(desaweight);
                    float splweightFloat = splweight.contains(".") ? Float.parseFloat(splweight) : Integer.parseInt(splweight);

                    // Compare the parsed values
                    if (desaweightFloat > 0) {
                        indusverify();
                    } else if (splweightFloat > 0) {
                        smallverify();
                    }
                } catch (NumberFormatException e) {
                    // Handle invalid number format if parsing fails
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Invalid weight format", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnweighmenttruck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Outward_Truck_weighment.this, Weigh_OR_Complete.class));
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
                                notificationsSender.triggerSendNotification();
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
                        etloadedtyuom.setText(data.getHowMuchQTYUOM());
                        etloadedtyuom.setEnabled(false);
                        customer.setText(data.getCustomerName());
                        customer.setEnabled(false);
                        despatchnext = data.getPurposeProcess();


                        desaweight = data.getIlweight();
                        desweight.setText(data.getIlweight());
                        desatotalqty = data.getIltotqty();
                        destotalqty.setText(data.getIltotqty());
                        destotalqty.setEnabled(false);

                        //snallpack
                        splweight = data.getSplweight();
                        spltotalqty = data.getSpltotqty();
                        spweight.setText(data.getSplweight());
                        spweight.setEnabled(false);
                        spqty.setText(data.getSpltotqty());
                        spqty.setEnabled(false);

                        desweight.setVisibility(View.GONE);
                        destotalqty.setVisibility(View.GONE);
                        if (!desaweight.equals("0") || desatotalqty.equals("0") ){
                            layout.setVisibility(View.VISIBLE);
                            varified.setVisibility(View.VISIBLE);
                            etvariremark.setVisibility(View.VISIBLE);
                            desweight.setVisibility(View.VISIBLE);
                            destotalqty.setVisibility(View.VISIBLE);
                            img.setVisibility(View.GONE);
                            tareweight.setVisibility(View.GONE);
                            etremark.setVisibility(View.GONE);
                            imglay.setVisibility(View.GONE);
                            intime.setVisibility(View.GONE);

                        }else if (!splweight.equals("0") ||spltotalqty.equals("0")){
                            layout.setVisibility(View.VISIBLE);
                            varified.setVisibility(View.VISIBLE);
                            spweight.setVisibility(View.VISIBLE);
                            spqty.setVisibility(View.VISIBLE);
                            etvariremark.setVisibility(View.VISIBLE);
                            tareweight.setVisibility(View.GONE);
                            etremark.setVisibility(View.GONE);
                            imglay.setVisibility(View.GONE);
                            intime.setVisibility(View.GONE);
                            img.setVisibility(View.GONE);




//                            layout.setVisibility(View.GONE);
//                            varified.setVisibility(View.GONE);
//                            etvariremark.setVisibility(View.GONE);
//                            desweight.setVisibility(View.GONE);
//                            destotalqty.setVisibility(View.GONE);
                        }
                        desweight.setText(data.getIlweight());
                        desweight.setEnabled(false);
//                        destotalqty.setText(data.getIltotqty());
//                        destotalqty.setEnabled(false);

                    }else {
                        //Toasty.error(Outward_Truck_weighment.this, "This Vehicle Number Is Not Available..!", Toast.LENGTH_SHORT).show();
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
                    "", 'A','I', vehicleType,etserialnumber,etvehiclenumber);
            Call<Boolean> call = outwardWeighment.updateweighmentoutwardtanker(responseOutwardTankerWeighment);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body() && response.body() == true){
                        Log.d("Registration", "Response Body: " + response.body());
                        deleteLocalImage(etvehiclenumber, outTime);
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

    public void indusverify(){

        String uvarifuremark = etvariremark.getText().toString().trim();
        if (despatchnext.length()>0){
            despatchNextChar = despatchnext.charAt(0);
        }
        if (uvarifuremark.isEmpty()){
            Toasty.warning(this, "Please Enter Remark", Toast.LENGTH_SHORT).show();
        }else {
//            Varified_Model varifiedModel = new Varified_Model(OutwardId,"Yes",'J',inOut,vehicleType,EmployeId,uvarifuremark);

            Varified_Model varifiedModel = new Varified_Model(OutwardId,"Yes",despatchNextChar,inOut,vehicleType,EmployeId,
                    uvarifuremark);
            Call<Boolean> call = outwardTruckInterface.weighmentvarified(varifiedModel);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body() && response.body() == true){
                        Toasty.success(Outward_Truck_weighment.this, "Data Inserted Succesfully !", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Outward_Truck_weighment.this, Outward_Truck.class));
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {

                }
            });
        }
    }

    public void smallverify(){
        String uvarifuremark = etvariremark.getText().toString().trim();
        if (despatchnext.length()>0){
            despatchNextChar = despatchnext.charAt(0);
        }
        if (uvarifuremark.isEmpty()){
            Toasty.warning(this, "Please Enter Remark", Toast.LENGTH_SHORT).show();
        }else {
            Verified_Small_pack verifiedSmallPack = new Verified_Small_pack(OutwardId,"Yes",uvarifuremark,despatchNextChar,inOut,
                    vehicleType,EmployeId);
            Call<Boolean> call = outwardTruckInterface.smallpackvarified(verifiedSmallPack);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body() && response.body() == true){
                        Toasty.success(Outward_Truck_weighment.this, "Data Inserted Succesfully !", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Outward_Truck_weighment.this, Outward_Truck.class));
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {

                }
            });
        }
    }

    public void UploadImagesAndUpdate_outwardtruck() {

        String FileInitial = "OutwardVeh_In_";
        arrayOfByteArrays[0] = ImgVehicle;
        arrayOfByteArrays[1] = ImgDriver;
        imgPath1 = "GAimages/" + FileInitial + serialnumber.getText().toString() + ".jpeg";
        for (byte[] byteArray : arrayOfByteArrays) {

            MultipartTask multipartTask = new MultipartTask(byteArray, FileInitial + serialnumber.getText().toString() + ".jpeg", "");
            multipartTask.execute();
            FileInitial = "OutwardDrv_In_";
        }
        imgPath2 = "GAimages/" + FileInitial + serialnumber.getText().toString() + ".jpeg";
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
        ContentResolver contentResolver = getContentResolver();
        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_REQUEST_CODE && data != null) {
                Bitmap bimage1 = (Bitmap) data.getExtras().get("data");
                if (bimage1 != null) {
                    img1.setImageBitmap(bimage1);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bimage1.compress(Bitmap.CompressFormat.JPEG, 90, baos);
                    image1 = ImageUtils.insertImage(contentResolver, bimage1, "", null);
                    if (image1 != null) {
                        String imagePath = ImageUtils.getImagePath(contentResolver, image1);
                        LocalImgPath[0] = image1;
                    }

                    ImgVehicle = baos.toByteArray();
                } else {
                    // Handle case when no image is captured
                    Toasty.error(this, "No image captured", Toast.LENGTH_SHORT).show();
                }
            } else if (requestCode == CAMERA_REQUEST_CODE1 && data != null) {
                Bitmap bimage2 = (Bitmap) data.getExtras().get("data");
                if (bimage2 != null) {
                    img2.setImageBitmap(bimage2);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bimage2.compress(Bitmap.CompressFormat.JPEG, 90, baos);
                    image2 = ImageUtils.insertImage(contentResolver, bimage2, "", null);
                    if (image2 != null) {
                        String imagePath = ImageUtils.getImagePath(contentResolver, image2);
                        LocalImgPath[1] = image2;
                    }
                    ImgDriver = baos.toByteArray();
                } else {
                    // Handle case when no image is captured
                    Toasty.error(this, "No image captured", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            // Handle case when camera activity is canceled
            Toasty.warning(this, "Camera operation canceled", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteLocalImage(String vehicalnumber,String outTime) {
        File imageFile;
        try {
            for (Uri imgpath : LocalImgPath) {
                ImageUtils.deleteImage(this,imgpath);
            }
            makeNotification(wvehiclenumber, outTime);
            Toasty.success(Outward_Truck_weighment.this, "Data Inserted Successfully", Toast.LENGTH_SHORT,true).show();
            startActivity(new Intent(Outward_Truck_weighment.this, Outward_Truck.class));
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void outtruckweighmentpending(View view){
        Intent intent = new Intent(this, Grid_Outward.class);
        startActivity(intent);
    }
}