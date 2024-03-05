package com.android.gandharvms.Inward_Truck_Weighment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.android.gandharvms.Global_Var;
import com.android.gandharvms.InwardCompletedGrid.GridCompleted;
import com.android.gandharvms.Inward_Tanker;
import com.android.gandharvms.Inward_Tanker_Laboratory.Inward_Tanker_Laboratory;
import com.android.gandharvms.Inward_Tanker_Security.Inward_Tanker_Security;
import com.android.gandharvms.Inward_Tanker_Security.grid;
import com.android.gandharvms.Inward_Tanker_Weighment.InTanWeighRequestModel;
import com.android.gandharvms.Inward_Tanker_Weighment.InTanWeighResponseModel;
import com.android.gandharvms.LoginWithAPI.LoginMethod;
import com.android.gandharvms.LoginWithAPI.ResponseModel;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.LoginWithAPI.Weighment;
import com.google.firebase.Timestamp;

import com.android.gandharvms.FcmNotificationsSender;
import com.android.gandharvms.Inward_Tanker_Security.In_Tanker_Security_list;
import com.android.gandharvms.Inward_Tanker_Weighment.Inward_Tanker_Weighment;
import com.android.gandharvms.Inward_Truck;
import com.android.gandharvms.Inward_Truck_Security.In_Truck_security_list;
import com.android.gandharvms.Menu;
import com.android.gandharvms.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class Inward_Truck_weighment extends AppCompatActivity {

    //prince
    private SharedPreferences sharedPreferences;
    private final int MAX_LENGTH=10;

    EditText etint,etremark,etserialnumber,etcontainer,etvehicalnumber,etsupplier,etmaterial,etdriver,etoanumber,etdate,etgrossweight,etsignby;

    Button intsubmit;
    Button view;
    FirebaseFirestore trwdbroot;
    DatePickerDialog picker;
    EditText datetimeTextview;
    TimePickerDialog tpicker;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int CAMERA_PERM_CODE1 = 100;
    private static final int CAMERA_PERM_CODE = 101;
    private static final int CAMERA_REQUEST_CODE = 102;
    private static final int CAMERA_REQUEST_CODE1 = 103;
    ImageView img1, img2;
    Uri image1, image2;
    private String imgPath1, imgPath2;

    FirebaseStorage storage;

    final Calendar calendar = Calendar.getInstance();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-YYYY, HH:mm:ss");
    Timestamp timestamp = new Timestamp(calendar.getTime());
    private String token;

    private Weighment weighmentdetails;
    private String vehicleType= Global_Var.getInstance().MenuType;
    private char nextProcess=Global_Var.getInstance().DeptType;
    private char inOut=Global_Var.getInstance().InOutType;
    private String EmployeId=Global_Var.getInstance().EmpId;
    private int inwardid;
    private LoginMethod userDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inward_truck_weighment);
        //Send Notification to all
        FirebaseMessaging.getInstance().subscribeToTopic(token);
        userDetails = RetroApiClient.getLoginApi();

        //Call Api method
        weighmentdetails= RetroApiClient.getWeighmentDetails();

        sharedPreferences = getSharedPreferences("TruckWeighment", MODE_PRIVATE);

        etint = (EditText) findViewById(R.id.etintime);
        etserialnumber=(EditText) findViewById(R.id.ettrwserialnumber);
        etvehicalnumber=(EditText) findViewById(R.id.ettrwvehicalno);
        etsupplier=(EditText) findViewById(R.id.ettrwsupplier);
        etmaterial=(EditText) findViewById(R.id.ettrwmaterial);
        etdriver=(EditText) findViewById(R.id.ettrdriverno);
        etoanumber=(EditText) findViewById(R.id.ettroa);
        etdate= (EditText) findViewById(R.id.ettrdate);
        etgrossweight=(EditText) findViewById(R.id.ettrgrossweight);
        etsignby =(EditText) findViewById(R.id.ettrsignby);
        etcontainer=(EditText)findViewById(R.id.ettrwcontainer);
        etremark=(EditText)findViewById(R.id.ettwremark);

        // listing Data button
        img1 = findViewById(R.id.ettrimageView1);
        img2 = findViewById(R.id.ettrimageView2);
        storage = FirebaseStorage.getInstance();

        if (getIntent().hasExtra("VehicleNumber")) {
            FetchVehicleDetails(getIntent().getStringExtra("VehicleNumber"), Global_Var.getInstance().MenuType, nextProcess, inOut);
        }

        etdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                // Array of month abbreviations
                String[] monthAbbreviations = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
                picker = new DatePickerDialog(Inward_Truck_weighment.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Use the month abbreviation from the array
                        String monthAbbreviation = monthAbbreviations[month];
                        // etdate.setText(dayOfMonth + "/" + monthAbbreviation + "/" + year);
                        etdate.setText(dateFormat.format(calendar.getTime()));
                    }
                }, year, month, day);
                picker.show();
            }
        });

        etvehicalnumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    FetchVehicleDetails(etvehicalnumber.getText().toString().trim(),vehicleType,nextProcess,inOut);
                }
            }
        });

        intsubmit=(Button) findViewById(R.id.wesubmit);
        trwdbroot=FirebaseFirestore.getInstance();

        etint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hours = calendar.get(Calendar.HOUR_OF_DAY);
                int mins = calendar.get(Calendar.MINUTE);
                tpicker = new TimePickerDialog(Inward_Truck_weighment.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        c.set(Calendar.MINUTE,minute);

                        // Set the formatted time to the EditText
                        etint.setText(hourOfDay +":"+ minute );
                    }
                },hours,mins,false);
                tpicker.show();
            }
        });


        intsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadimg(image1, image2);
                intrinsert();
            }
        });
    }
    private String getCurrentTime() {
        // Get the current time
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(new Date());
    }

    public void makeNotification(String vehicleNumber,String outTime) {
        Call<List<ResponseModel>> call = userDetails.getUsersListData();
        call.enqueue(new Callback<List<ResponseModel>>() {
            @Override
            public void onResponse(Call<List<ResponseModel>> call, Response<List<ResponseModel>> response) {
                if (response.isSuccessful()){
                    List<ResponseModel> userList = response.body();
                    if (userList != null){
                        for (ResponseModel responseModel :userList){
                            String specificrole = "Store";
                            if (specificrole.equals(responseModel.getDepartment())){
                                token = responseModel.getToken();
                                FcmNotificationsSender notificationsSender = new FcmNotificationsSender(
                                        token,
                                        "Inward Truck Weighment Process Done..!",
                                        "Vehicle Number:-" + vehicleNumber + " has completed Weighment process at " + outTime,
                                        getApplicationContext(),
                                        Inward_Truck_weighment.this
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
                Toasty.error(Inward_Truck_weighment.this,"failed..!",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void intrinsert()
    {
        String intime = etint.getText().toString().trim();
        String serialnumber=etserialnumber.getText().toString().trim();
        String vehicalnumber=etvehicalnumber.getText().toString().trim();
        String supplier=etsupplier.getText().toString().trim();
        String material=etmaterial.getText().toString().trim();
        String Driver = etdriver.getText().toString().trim();
        String oanumber = etoanumber.getText().toString().trim();
        String date = etdate.getText().toString().trim();
        String Grossweight = etgrossweight.getText().toString().trim();
        String container=etcontainer.getText().toString().trim();
        String tareweight = "0";
        String netweight = "0";
        String remark=etremark.getText().toString().trim();
        String signby = etsignby.getText().toString().trim();
        String outTime = getCurrentTime();


        if ( intime.isEmpty()|| serialnumber.isEmpty()|| vehicalnumber.isEmpty()|| supplier.isEmpty()||
                material.isEmpty()|| Driver.isEmpty() || oanumber.isEmpty()|| date.isEmpty()||
                Grossweight.isEmpty()||container.isEmpty()|| remark.isEmpty() || signby.isEmpty()){
            Toasty.warning(this, "All fields must be filled", Toast.LENGTH_SHORT,true).show();
        }
        else {
            InTanWeighRequestModel weighReqModel=new InTanWeighRequestModel(inwardid,intime,outTime,Grossweight,tareweight,netweight,
                    "","",remark,signby,Integer.parseInt(container),"","",serialnumber,
                    vehicalnumber,date,supplier,material,oanumber,Driver,'R',inOut,vehicleType,EmployeId,EmployeId,"","","");

            Call<Boolean> call=weighmentdetails.insertWeighData(weighReqModel);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body()!=null && response.body()==true)
                    {
                        makeNotification(vehicalnumber, outTime);
                        Log.d("Registration", "Response Body: " + response.body());
                        Toasty.success(Inward_Truck_weighment.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Inward_Truck_weighment.this, Inward_Truck.class));
                        finish();
                    }
                    else
                    {
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
                    Toasty.error(Inward_Truck_weighment.this,"failed..!",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    public void uploadimg(Uri Image1, Uri Image2) {
        StorageReference storageReference = storage.getReference();
        if (Image1 != null) {
            String InVehicleImage = "image1_" + UUID.randomUUID().toString() + ".jpeg";
            StorageReference imgref1 = storageReference.child("/WeighmentTruckImage1" + "/" + InVehicleImage);
            imgPath1 = "/WeighmentTruckImage1%2F" + InVehicleImage;
            imgref1.putFile(Image1)
                    .addOnSuccessListener(taskSnapshot -> {
                        imgref1.getDownloadUrl().addOnSuccessListener(uri -> {
                            final String imageUrl = uri.toString();
                            img1.setImageURI(Uri.parse(imageUrl));
                        });
                    }).addOnFailureListener(e -> {
                        Toast.makeText(Inward_Truck_weighment.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    });

        }

        if (Image2 != null) {
            String InDriverImage = "image2_" + UUID.randomUUID().toString() + ".jpeg";
            StorageReference imgref2 = storageReference.child("/WeighmentTruckImage2" + "/" + InDriverImage);
            imgPath2 = "/WeighmentTruckImage2%2F" + InDriverImage;
            imgref2.putFile(Image2)
                    .addOnSuccessListener(taskSnapshot -> {
                        imgref2.getDownloadUrl().addOnSuccessListener(uri -> {
                            final String imageUrl = uri.toString();
                            img2.setImageURI(Uri.parse(imageUrl));
                        });
                    }).addOnFailureListener(e -> {
                        Toast.makeText(Inward_Truck_weighment.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }
    }
    //  image upload firebase
    public void captureImageFromCamera1(android.view.View view) {
        askCameraPermission(CAMERA_REQUEST_CODE);
    }

    public void captureImageFromCamera2(android.view.View view) {
        askCameraPermission1(CAMERA_REQUEST_CODE1);
    }

    private void askCameraPermission(int requestcode) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
        } else {
            openCamera(requestcode);
        }
    }

    private void askCameraPermission1(int requestcode1) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
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


        } else if (requestCode == CAMERA_REQUEST_CODE1) {
            Bitmap bimage2 = (Bitmap) data.getExtras().get("data");
            img2.setImageBitmap(bimage2);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bimage2.compress(Bitmap.CompressFormat.JPEG, 90, baos);
            String path = MediaStore.Images.Media.insertImage(getContentResolver(), bimage2, "title2", null);
            image2 = Uri.parse(path);
        }
    }

    public void onBackPressed(){
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
        finish();
    }

    public void FetchVehicleDetails(@NonNull String vehicleNo,String vehicleType,char NextProcess,char inOut) {
        Call<InTanWeighResponseModel> call=weighmentdetails.getWeighbyfetchVehData(vehicleNo,vehicleType,NextProcess,inOut);
        call.enqueue(new Callback<InTanWeighResponseModel>() {
            @Override
            public void onResponse(Call<InTanWeighResponseModel> call, Response<InTanWeighResponseModel> response) {
                if(response.isSuccessful())
                {
                    InTanWeighResponseModel data=response.body();
                    if(data.getVehicleNo()!="")
                    {
                        inwardid=data.getInwardId();
                        etserialnumber.setText(data.getSerialNo());
                        etserialnumber.setEnabled(false);
                        etvehicalnumber.setText(data.getVehicleNo());
                        etvehicalnumber.setEnabled(false);
                        etsupplier.setText(data.getPartyName());
                        etsupplier.setEnabled(false);
                        etmaterial.setText(data.getMaterial());
                        etmaterial.setEnabled(false);
                        etoanumber.setText(data.getOA_PO_number());
                        etoanumber.setEnabled(false);
                        etdriver.setText(String.valueOf(data.getDriver_MobileNo()));
                        etdriver.setEnabled(false);
                        etdate.setText(data.getDate());
                        etdate.setEnabled(false);
                        etint.requestFocus();
                        etint.callOnClick();
                    }
                }else
                {
                    Log.e("Retrofit", "Error Response Body: " + response.code());
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
                Toasty.error(Inward_Truck_weighment.this,"failed..!",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void WeighViewclick(View view){
        Intent intent = new Intent(this, grid.class);
        startActivity(intent);
    }
    public void truckweicompletedclick(View view){
        Intent intent = new Intent(this, GridCompleted.class);
        startActivity(intent);
    }
}