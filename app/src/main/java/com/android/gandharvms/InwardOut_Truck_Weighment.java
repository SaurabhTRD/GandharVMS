package com.android.gandharvms;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
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
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.gandharvms.Inward_Tanker_Security.grid;
import com.android.gandharvms.Inward_Tanker_Weighment.InTanWeighResponseModel;
import com.android.gandharvms.Inward_Tanker_Weighment.Model_InwardOutweighment;
import com.android.gandharvms.Inward_Truck_Weighment.Inward_Truck_Weighment_Viewdata;
import com.android.gandharvms.Inward_Truck_Weighment.Inward_Truck_weighment;
import com.android.gandharvms.LoginWithAPI.Login;
import com.android.gandharvms.LoginWithAPI.LoginMethod;
import com.android.gandharvms.LoginWithAPI.ResponseModel;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.LoginWithAPI.Weighment;
import com.android.gandharvms.NotificationAlerts.NotificationCommonfunctioncls;
import com.android.gandharvms.Util.ImageUtils;
import com.android.gandharvms.Util.MultipartTask;
import com.android.gandharvms.Util.dialogueprogreesbar;
import com.android.gandharvms.submenu.submenu_Inward_Tanker;
import com.android.gandharvms.submenu.submenu_Inward_Truck;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class InwardOut_Truck_Weighment extends NotificationCommonfunctioncls {

    private static final int CAMERA_PERM_CODE1 = 100;
    private static final int CAMERA_PERM_CODE = 101;
    private static final int CAMERA_REQUEST_CODE = 102;
    private static final int CAMERA_REQUEST_CODE1 = 103;
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private final String EmployeId = Global_Var.getInstance().EmpId;
    EditText etintime, etvehicel, etgrosswt, etnetwt, ettarewt, shdip, shwe;
    Button view, submit;
    TimePickerDialog tpicker;
    ImageView img1, img2;
    Uri image1, image2;
    byte[] ImgDriver, ImgVehicle;
    byte[][] arrayOfByteArrays = new byte[2][];
    Uri[] LocalImgPath = new Uri[2];
    dialogueprogreesbar dialogHelper = new dialogueprogreesbar();
    ImageView btnlogout, btnhome;
    TextView username, empid;
    private Weighment weighmentdetails;
    private LoginMethod userDetails;
    private int inwardid;
    private String token;
    private String etSerialNumber;
    private String imgPath1, imgPath2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inward_out_truck_weighment);
        setupHeader();
        weighmentdetails = RetroApiClient.getWeighmentDetails();
        userDetails = RetroApiClient.getLoginApi();

        etintime = findViewById(R.id.intime);
        etvehicel = findViewById(R.id.vehicle);
        etgrosswt = findViewById(R.id.etgrosswt);
        etnetwt = findViewById(R.id.etnetweight);
        ettarewt = findViewById(R.id.ettareweight);
        shdip = findViewById(R.id.irinweishortagedip);
        shwe = findViewById(R.id.irinweishortageweight);

        submit = findViewById(R.id.prosubmit);

        img1 = findViewById(R.id.inwardtruckoutvehicleimg);
        img2 = findViewById(R.id.inwardtruckoutdriverimg);

        //Send Notification to all
        FirebaseMessaging.getInstance().subscribeToTopic(token);

        /*btnlogout=findViewById(R.id.btn_logoutButton);
        btnhome = findViewById(R.id.btn_homeButton);
        username=findViewById(R.id.tv_username);
        empid=findViewById(R.id.tv_employeeId);

        String userName=Global_Var.getInstance().Name;
        String empId=Global_Var.getInstance().EmpId;

        username.setText(userName);
        empid.setText(empId);

        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InwardOut_Truck_Weighment.this, Login.class));
            }
        });
        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InwardOut_Truck_Weighment.this, Menu.class));
            }
        });*/

        //view = findViewById(R.id.btn_Viewweigmentslip);
        /*view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InwardOut_Truck_Weighment.this, Inward_Truck_Weighment_Viewdata.class));
            }
        });*/

        if (getIntent().hasExtra("VehicleNumber")) {
            FetchVehicleDetails(getIntent().getStringExtra("VehicleNumber"), Global_Var.getInstance().MenuType, nextProcess, inOut);
        }
        etnetwt.addTextChangedListener(new TextWatcher() {
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

        etintime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                String time = format.format(calendar.getTime());
                etintime.setText(time);
            }
        });
        etvehicel.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    FetchVehicleDetails(etvehicel.getText().toString().trim(), vehicleType, nextProcess, inOut);
                }
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                update();
                if (image1 == null || image2 == null) {
                    Toasty.warning(InwardOut_Truck_Weighment.this, "Please Upload Image", Toast.LENGTH_SHORT).show();
                } else {
                    inwardouttruckweigmentuploadandupdate();
                }
            }
        });
    }

    public void calculateNetWeight() {

        String grosswt = etgrosswt.getText().toString().trim();
        String netweight = etnetwt.getText().toString().trim();

        double grossWeight = grosswt.isEmpty() ? 0.0 : Double.parseDouble(grosswt);
        double netwe = netweight.isEmpty() ? 0.0 : Double.parseDouble(netweight);

        double tareweight = grossWeight - netwe;

        ettarewt.setText(String.valueOf(tareweight));
    }

    private void FetchVehicleDetails(@NonNull String vehicleNo, String vehicleType, char NextProcess, char inOut) {

        Call<InTanWeighResponseModel> call = weighmentdetails.getWeighbyfetchVehData(vehicleNo, vehicleType, NextProcess, inOut);
        call.enqueue(new Callback<InTanWeighResponseModel>() {
            @Override
            public void onResponse(Call<InTanWeighResponseModel> call, Response<InTanWeighResponseModel> response) {
                if (response.isSuccessful()) {
                    InTanWeighResponseModel data = response.body();
                    if (data.getVehicleNo() != "" && data.getVehicleNo() != null) {
                        etgrosswt.setText(data.getGrossWeight());
                        etgrosswt.setEnabled(false);
                        etvehicel.setText(data.getVehicleNo());
                        etvehicel.setEnabled(false);
                        inwardid = data.getInwardId();
                        etSerialNumber = data.getSerialNo();
                    } else {
                        Toasty.error(InwardOut_Truck_Weighment.this, "This Vehicle Number Is Not Available..!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<InTanWeighResponseModel> call, Throwable t) {
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
                Toasty.error(InwardOut_Truck_Weighment.this, "failed..!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void makeNotification(String vehicleNumber) {
        Call<List<ResponseModel>> call = userDetails.getUsersListData();
        call.enqueue(new Callback<List<ResponseModel>>() {
            @Override
            public void onResponse(Call<List<ResponseModel>> call, Response<List<ResponseModel>> response) {
                if (response.isSuccessful()) {
                    List<ResponseModel> userList = response.body();
                    if (userList != null) {
                        for (ResponseModel responseModel : userList) {
                            String specificrole = "Security";
                            if (specificrole.equals(responseModel.getDepartment())) {
                                token = responseModel.getToken();
                                FcmNotificationsSender notificationsSender = new FcmNotificationsSender(
                                        token,
                                        "Inward Truck Out Weighment Process Done..!",
                                        "This Vehicle:-" + vehicleNumber + " is Ready for Security",
                                        getApplicationContext(),
                                        InwardOut_Truck_Weighment.this
                                );
                                notificationsSender.triggerSendNotification();
                            }
                        }
                    }
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
                Toasty.error(InwardOut_Truck_Weighment.this, "failed..!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void update() {
        String intime = etintime.getText().toString().trim();
        String vehicleno = etvehicel.getText().toString().trim();
        String gross = etgrosswt.getText().toString().trim();
        String tare = ettarewt.getText().toString().trim();
        String net = etnetwt.getText().toString().trim();
        String udip = shdip.getText().toString() != null ? shdip.getText().toString().trim() : "";
        String uwet = shwe.getText().toString() != null ? shwe.getText().toString().trim() : "";

        if (intime.isEmpty() || vehicleno.isEmpty() || gross.isEmpty() || tare.isEmpty() || net.isEmpty()) {
            Toasty.warning(this, "All fields must be filled", Toast.LENGTH_SHORT, true).show();
        } else {
            Model_InwardOutweighment modelInwardOutweighment = new Model_InwardOutweighment(inwardid, gross, net, tare, imgPath1, imgPath2,
                    'S', 'O', vehicleType, intime, EmployeId, udip, uwet, "", "");
            dialogHelper.showConfirmationDialog(this, () -> {
                dialogHelper.showProgressDialog(this); // Show progress when confirmed
                Call<Boolean> call = weighmentdetails.inwardoutweighment(modelInwardOutweighment);
                call.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if (response.isSuccessful() && response.body() != null && response.body()) {
                            dialogHelper.hideProgressDialog(); // Hide after response
                            Log.d("Registration", "Response Body: " + response.body());
                            deleteLocalImage(vehicleno);
                        } else {
                            Toasty.error(InwardOut_Truck_Weighment.this, "Data Insertion Failed..!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        dialogHelper.hideProgressDialog(); // Hide after response
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
                        Toasty.error(InwardOut_Truck_Weighment.this, "failed..!", Toast.LENGTH_SHORT).show();
                    }
                });
            });
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
        finish();
    }

    public void inouttruckgrid(View view) {
        Intent intent = new Intent(this, grid.class);
        startActivity(intent);
    }

    public void intrwegridclick(View view) {
        Intent intent = new Intent(this, it_out_weigh_completedgrid.class);
        startActivity(intent);
    }

    public void inwardouttruckweigmentuploadandupdate() {
        String FileInitial = "IRVeh_Out_";
        arrayOfByteArrays[0] = ImgVehicle;
        arrayOfByteArrays[1] = ImgDriver;
        imgPath1 = "GAimages/" + FileInitial + etSerialNumber + ".jpeg";
        for (byte[] byteArray : arrayOfByteArrays) {

            MultipartTask multipartTask = new MultipartTask(byteArray, FileInitial + etSerialNumber + ".jpeg", "");
            multipartTask.execute();
            FileInitial = "IRDrv_Out_";
        }
        imgPath2 = "GAimages/" + FileInitial + etSerialNumber + ".jpeg";
        FileInitial = "";
        update();
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

    private void deleteLocalImage(String vehicalnumber) {
        File imageFile;
        try {
            for (Uri imgpath : LocalImgPath) {
                ImageUtils.deleteImage(this, imgpath);
            }
            Toasty.success(InwardOut_Truck_Weighment.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
            makeNotification(vehicalnumber);
            startActivity(new Intent(InwardOut_Truck_Weighment.this, grid.class));
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}