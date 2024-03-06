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

import com.android.gandharvms.Inward_Tanker_Production.Inward_Tanker_Production;
import com.android.gandharvms.Inward_Tanker_Security.In_Tanker_Security_list;
import com.android.gandharvms.Inward_Tanker_Security.grid;
import com.android.gandharvms.Inward_Tanker_Weighment.InTanWeighRequestModel;
import com.android.gandharvms.Inward_Tanker_Weighment.InTanWeighResponseModel;
import com.android.gandharvms.Inward_Tanker_Weighment.In_Tanker_Weighment_list;
import com.android.gandharvms.Inward_Tanker_Weighment.Inward_Tanker_Weighment;
import com.android.gandharvms.Inward_Tanker_Weighment.Inward_Tanker_Weighment_Viewdata;
import com.android.gandharvms.Inward_Tanker_Weighment.Model_InwardOutweighment;
import com.android.gandharvms.Inward_Truck_Weighment.Inward_Truck_weighment;
import com.android.gandharvms.LoginWithAPI.LoginMethod;
import com.android.gandharvms.LoginWithAPI.ResponseModel;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.LoginWithAPI.Weighment;
import com.android.gandharvms.Util.MultipartTask;
import com.android.gandharvms.submenu.submenu_Inward_Tanker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class InwardOut_Tanker_Weighment extends AppCompatActivity {

    EditText etintime,ettareweight,grswt,etvehicle,etnetwt,shdip,shwe;
    Button view;
    Button etsubmit;
    TimePickerDialog tpicker;
    FirebaseFirestore dbroot;

    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private final String EmployeId = Global_Var.getInstance().EmpId;
    private Weighment weighmentdetails;
    private int inwardid;
    private String etSerialNumber;
    private LoginMethod userDetails;
    private static final int CAMERA_PERM_CODE1 = 100;
    private static final int CAMERA_PERM_CODE = 101;
    private static final int CAMERA_REQUEST_CODE = 102;
    private static final int CAMERA_REQUEST_CODE1 = 103;
    ImageView img1, img2;
    Uri image1, image2;
    byte[] ImgDriver, ImgVehicle;
    byte[][] arrayOfByteArrays = new byte[2][];
    private String imgPath1, imgPath2;

    private String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inward_out_tanker_weighment);

        userDetails = RetroApiClient.getLoginApi();
        weighmentdetails = RetroApiClient.getWeighmentDetails();

        /*view = findViewById(R.id.btn_Viewweigmentslip);*/

        etintime = findViewById(R.id.etintime);
        ettareweight = findViewById(R.id.ettareweight);
        grswt = findViewById(R.id.etgrosswt);
        etnetwt = findViewById(R.id.etnetweight);
        etvehicle = findViewById(R.id.etvehicle);
        shdip = findViewById(R.id.shortagedip);
        shwe = findViewById(R.id.shortageweight);

        img1=findViewById(R.id.intaweioutvehicleimage);
        img2=findViewById(R.id.intaweioutdriverimage);

        //Send Notification to all
        FirebaseMessaging.getInstance().subscribeToTopic(token);
        etvehicle.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    FetchVehicleDetails(etvehicle.getText().toString().trim(),vehicleType,nextProcess,inOut);
                }
            }
        });

        if (getIntent().hasExtra("VehicleNumber")) {
            FetchVehicleDetails(getIntent().getStringExtra("VehicleNumber"), Global_Var.getInstance().MenuType, nextProcess, inOut);
        }

        etsubmit = (Button) findViewById(R.id.prosubmit);
        dbroot = FirebaseFirestore.getInstance();

        etsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadImagesAndUpdate();
            }
        });
        /*view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InwardOut_Tanker_Weighment.this, Inward_Tanker_Weighment_Viewdata.class));
            }
        });*/

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
                int hours = calendar.get(Calendar.HOUR_OF_DAY);
                int mins = calendar.get(Calendar.MINUTE);
                tpicker = new TimePickerDialog(InwardOut_Tanker_Weighment.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        c.set(Calendar.MINUTE,minute);

                        // Set the formatted time to the EditText
                        etintime.setText(hourOfDay +":"+ minute );
                    }
                },hours,mins,false);
                tpicker.show();
            }
        });
    }
    public void onBackPressed(){
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
        finish();
    }

    public void calculateNetWeight() {

        String grosswt = grswt.getText().toString().trim();
        String netweight = etnetwt.getText().toString().trim();

        double grossWeight = grosswt.isEmpty() ? 0.0 : Double.parseDouble(grosswt);
        double netwe = netweight.isEmpty() ? 0.0 : Double.parseDouble(netweight);

        double tareweight = grossWeight - netwe;

        ettareweight.setText(String.valueOf(tareweight));
    }

    public void FetchVehicleDetails(@NonNull String vehicleNo, String vehicleType, char NextProcess, char inOut)
    {
       Call<InTanWeighResponseModel> call = weighmentdetails.getWeighbyfetchVehData(vehicleNo,vehicleType,NextProcess,inOut);
       call.enqueue(new Callback<InTanWeighResponseModel>() {
           @Override
           public void onResponse(Call<InTanWeighResponseModel> call, Response<InTanWeighResponseModel> response) {
               if (response.isSuccessful()){
                   InTanWeighResponseModel data = response.body();
                   if (data.getVehicleNo() != ""){
                       grswt.setText(data.getGrossWeight());
                       grswt.setEnabled(false);
                       etvehicle.setText(data.getVehicleNo());
                       etvehicle.setEnabled(false);
                       etnetwt.callOnClick();
                       inwardid = data.getInwardId();
                       etSerialNumber=data.getSerialNo();
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
               Toasty.error(InwardOut_Tanker_Weighment.this,"failed..!",Toast.LENGTH_SHORT).show();
           }
       });
    }

    public void makeNotification(String vehicleNumber) {
        Call<List<ResponseModel>> call = userDetails.getUsersListData();
        call.enqueue(new Callback<List<ResponseModel>>() {
            @Override
            public void onResponse(Call<List<ResponseModel>> call, Response<List<ResponseModel>> response) {
                if (response.isSuccessful()){
                    List<ResponseModel> userList = response.body();
                    if (userList != null){
                        for (ResponseModel responseModel :userList){
                            String specificrole = "Security";
                            if (specificrole.equals(responseModel.getDepartment())){
                                token = responseModel.getToken();
                                FcmNotificationsSender notificationsSender = new FcmNotificationsSender(
                                        token,
                                        "Inward Tanker Out Weighment Process Done..!",
                                        "This Vehicle:-" + vehicleNumber + "is Ready for Security",
                                        getApplicationContext(),
                                        InwardOut_Tanker_Weighment.this
                                );
                                notificationsSender.SendNotifications();
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
                Toasty.error(InwardOut_Tanker_Weighment.this,"failed..!",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void update() {
        String intime = etintime.getText().toString().trim();
        String vehiclnmo = etvehicle.getText().toString().trim();
        String grosswt = grswt.getText().toString().trim();
        String netwt = etnetwt.getText().toString().trim();
        String tare = ettareweight.getText().toString().trim();
        String udip = shdip.getText().toString().trim();
        String uwet = shwe.getText().toString().trim();

        if (intime.isEmpty()|| vehiclnmo.isEmpty()||grosswt.isEmpty()||netwt.isEmpty()||tare.isEmpty()){
            Toasty.warning(this, "All fields must be filled", Toast.LENGTH_SHORT, true).show();
        }else {
            Model_InwardOutweighment modelInwardOutweighment = new Model_InwardOutweighment(inwardid,grosswt,netwt,tare,imgPath1,imgPath2,
                    'S','O',vehicleType,intime,EmployeId,udip,uwet);
            Call<Boolean> call = weighmentdetails.inwardoutweighment(modelInwardOutweighment);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body() != null && response.body() == true){
                        makeNotification(vehiclnmo);
                        Log.d("Registration", "Response Body: " + response.body());
                        Toasty.success(InwardOut_Tanker_Weighment.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(InwardOut_Tanker_Weighment.this, submenu_Inward_Tanker.class));
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
                    Toasty.error(InwardOut_Tanker_Weighment.this, "failed..!", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    public void UploadImagesAndUpdate() {
        String FileInitial = "InVeh_Out_";
        arrayOfByteArrays[0] = ImgVehicle;
        arrayOfByteArrays[1] = ImgDriver;
        imgPath1 = "GAimages/"+ FileInitial + etSerialNumber.toString() + ".jpeg";
        for (byte[] byteArray : arrayOfByteArrays) {

            MultipartTask multipartTask = new MultipartTask(byteArray, FileInitial + etSerialNumber.toString() + ".jpeg", "");
            multipartTask.execute();
            FileInitial = "InDrv_Out_";
        }
        imgPath2 = "GAimages/"+ FileInitial + etSerialNumber.toString() + ".jpeg";
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
    public void gridinouttanker(View view){
        Intent intent = new Intent(this, grid.class);
        startActivity(intent);
    }

    public void itasecoutViewclick(View view){
        Intent intent = new Intent(this, grid.class);
        startActivity(intent);
    }
}