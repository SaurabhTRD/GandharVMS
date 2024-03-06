package com.android.gandharvms.Inward_Tanker_Weighment;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
import android.graphics.Camera;
import android.graphics.drawable.BitmapDrawable;
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

import com.android.gandharvms.FcmNotificationsSender;
import com.android.gandharvms.Global_Var;
import com.android.gandharvms.InwardCompletedGrid.GridCompleted;
import com.android.gandharvms.Inward_Tanker;
import com.android.gandharvms.Inward_Tanker_Laboratory.Inward_Tanker_Laboratory;
import com.android.gandharvms.Inward_Tanker_Security.In_Tanker_Security_list;
import com.android.gandharvms.Inward_Tanker_Security.Inward_Tanker_Security;
import com.android.gandharvms.Inward_Tanker_Security.grid;
import com.android.gandharvms.LoginWithAPI.LoginMethod;
import com.android.gandharvms.LoginWithAPI.ResponseModel;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.LoginWithAPI.Weighment;
import com.android.gandharvms.Menu;
import com.android.gandharvms.R;
import com.android.gandharvms.RegisterwithAPI.Register;
import com.android.gandharvms.Util.MultipartTask;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
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
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
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


public class Inward_Tanker_Weighment extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int CAMERA_PERM_CODE1 = 100;
    private static final int CAMERA_PERM_CODE = 101;
    private static final int CAMERA_REQUEST_CODE = 102;
    private static final int CAMERA_REQUEST_CODE1 = 103;
    final Calendar calendar = Calendar.getInstance();
    private final int MAX_LENGTH = 10;
    private final String dateTimeString = "";
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private final String EmployeId = Global_Var.getInstance().EmpId;
    EditText etint, etserialnumber, etvehicalno, etsuppliername, etmaterialname, etdriverno, etoano, etdate,
            etgrossweight, etremark, etsignby, etcontainer, etshortagedip, etshortageweight;
    Button wesubmit;
    FirebaseFirestore wedbroot;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-YYYY, HH:mm:ss");
    Timestamp timestamp = new Timestamp(calendar.getTime());
    DatePickerDialog picker;
    Button view;
    FirebaseStorage storage;
    ImageView img1, img2;
    Uri image1, image2;
    byte[] ImgDriver, ImgVehicle;
    byte[][] arrayOfByteArrays = new byte[2][];
    TimePickerDialog tpicker;
    private Weighment weighmentdetails;
    private String imgPath1, imgPath2;
    private SharedPreferences sharedPreferences;
    private int autoGeneratedNumber;
    private String token;
    private int inwardid;
    private LoginMethod userDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inward_tanker_weighment);

        //Send Notification to all
        FirebaseMessaging.getInstance().subscribeToTopic(token);
        userDetails = RetroApiClient.getLoginApi();

        img1 = findViewById(R.id.intanvehimageView1);
        img2 = findViewById(R.id.intandriverimageView2);

        etint = findViewById(R.id.etintime);
        etserialnumber = findViewById(R.id.etserialnumber);
        etvehicalno = findViewById(R.id.etvehicalno);
        etsuppliername = findViewById(R.id.etsuppliername);
        etmaterialname = findViewById(R.id.etmaterialname);
        etdriverno = findViewById(R.id.etdriverno);
        etoano = findViewById(R.id.etoano);
        etdate = findViewById(R.id.etdate);
        etgrossweight = findViewById(R.id.etgrossweight);
        etremark = findViewById(R.id.etremark);
        etsignby = findViewById(R.id.etsignby);
        etcontainer = findViewById(R.id.container);
//        etshortagedip = findViewById(R.id.shortagedip);
//        etshortageweight = findViewById(R.id.shortageweight);

        //Call Api method
        weighmentdetails = RetroApiClient.getWeighmentDetails();

        sharedPreferences = getSharedPreferences("TankerWeighment", MODE_PRIVATE);
        if (sharedPreferences != null) {
            if (getIntent().hasExtra("VehicleNumber")) {
                FetchVehicleDetails(getIntent().getStringExtra("VehicleNumber"), Global_Var.getInstance().MenuType, nextProcess, inOut);
            }
        }

        storage = FirebaseStorage.getInstance();
        //view button
        view = findViewById(R.id.dbview);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inward_Tanker_Weighment.this, grid.class);
                startActivity(intent);
            }
        });

        etint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hours = calendar.get(Calendar.HOUR_OF_DAY);
                int mins = calendar.get(Calendar.MINUTE);
                tpicker = new TimePickerDialog(Inward_Tanker_Weighment.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        c.set(Calendar.MINUTE, minute);

                        // Set the formatted time to the EditText
                        etint.setText(hourOfDay + ":" + minute);
                    }
                }, hours, mins, false);
                tpicker.show();
            }
        });

        etvehicalno.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    FetchVehicleDetails(etvehicalno.getText().toString().trim(), vehicleType, nextProcess, inOut);
                }
            }
        });

        wesubmit = findViewById(R.id.wesubmit);
        wedbroot = FirebaseFirestore.getInstance();

        wesubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  uploadimg(image1, image2);
                UploadImagesAndInsert();
            }


        });
    }

    public void makeNotification(String vehicleNumber, String outTime) {
        Call<List<ResponseModel>> call = userDetails.getUsersListData();
        call.enqueue(new Callback<List<ResponseModel>>() {
            @Override
            public void onResponse(Call<List<ResponseModel>> call, Response<List<ResponseModel>> response) {
                if (response.isSuccessful()) {
                    List<ResponseModel> userList = response.body();
                    if (userList != null) {
                        for (ResponseModel resmodel : userList) {
                            String specificrole = "Sampling";
                            if (specificrole.equals(resmodel.getDepartment())) {
                                token = resmodel.getToken();

                                FcmNotificationsSender fcmNotificationsSender = new FcmNotificationsSender(
                                        token,
                                        "Inward Tanker Weighment Process Done..!",
                                        "Vehicle Number:-" + vehicleNumber + " has completed Weighment process at " + outTime,
                                        getApplicationContext(),
                                        Inward_Tanker_Weighment.this
                                );
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
                Toasty.error(Inward_Tanker_Weighment.this, "failed..!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(new Date());
    }

    public void weinsertdata() {
        String intime = etint.getText().toString().trim();
        String serialnumber = etserialnumber.getText().toString().trim();
        String vehicelnumber = etvehicalno.getText().toString().trim();
        String suppliername = etsuppliername.getText().toString().trim();
        String materialname = etmaterialname.getText().toString().trim();
        String driverno = etdriverno.getText().toString().trim();
        String oan = etoano.getText().toString().trim();
        String date = etdate.getText().toString().trim();
        String grossweight = etgrossweight.getText().toString().trim();
        String tareweight = "0";
        String netweight = "0";
        String remark = etremark.getText().toString().trim();
        String signby = etsignby.getText().toString().trim();
        String container = etcontainer.getText().toString().trim();
//        String shortagedip = etshortagedip.getText().toString().trim();
//        String shortageweight = etshortageweight.getText().toString().trim();
        String outTime = getCurrentTime();//Insert out Time Directly to the Database


        if (intime.isEmpty() || serialnumber.isEmpty() || vehicelnumber.isEmpty() || suppliername.isEmpty() || materialname.isEmpty() ||
                driverno.isEmpty() || oan.isEmpty() || grossweight.isEmpty() ||
                signby.isEmpty() || container.isEmpty()) {
            Toasty.warning(this, "All fields must be filled", Toast.LENGTH_SHORT, true).show();
        } else {
            InTanWeighRequestModel weighReqModel = new InTanWeighRequestModel(inwardid, intime, outTime, grossweight, tareweight, netweight,
                     remark, signby, Integer.parseInt(container), imgPath1, imgPath2, serialnumber,
                    vehicelnumber, date, suppliername, materialname, oan, driverno, 'M', inOut, vehicleType, EmployeId, EmployeId, "", "", "");

            Call<Boolean> call = weighmentdetails.insertWeighData(weighReqModel);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body() != null && response.body()) {
                        makeNotification(vehicelnumber, outTime);
                        Log.d("Registration", "Response Body: " + response.body());
                        Toasty.success(Inward_Tanker_Weighment.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Inward_Tanker_Weighment.this, Inward_Tanker.class));
                        finish();
                    } else {
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
                    Toasty.error(Inward_Tanker_Weighment.this, "failed..!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    /*public void uploadimg(Uri Image1, Uri Image2) {
        StorageReference storageReference = storage.getReference();
        if (Image1 != null) {
            String InVehicleImage = "image1_1" + UUID.randomUUID().toString() + ".jpeg";
            StorageReference imgref1 = storageReference.child("/WeighmentImage1" + "/" + InVehicleImage);
            imgPath1 = "/WeighmentImage1%2F" + InVehicleImage;
            imgref1.putFile(Image1)
                    .addOnSuccessListener(taskSnapshot -> {
                        imgref1.getDownloadUrl().addOnSuccessListener(uri -> {
                            final String imageUrl = uri.toString();
                            img1.setImageURI(Uri.parse(imageUrl));
                        });
                    }).addOnFailureListener(e -> {
                        Toast.makeText(Inward_Tanker_Weighment.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    });

        }

        if (Image2 != null) {
            String InDriverImage = "image2_1" + UUID.randomUUID().toString() + ".jpeg";
            StorageReference imgref2 = storageReference.child("/WeighmentImage2" + "/" + InDriverImage);
            imgPath2 = "/WeighmentImage2%2F" + InDriverImage;
            imgref2.putFile(Image2)
                    .addOnSuccessListener(taskSnapshot -> {
                        imgref2.getDownloadUrl().addOnSuccessListener(uri -> {
                            final String imageUrl = uri.toString();
                            img2.setImageURI(Uri.parse(imageUrl));
                        });
                    }).addOnFailureListener(e -> {
                        Toast.makeText(Inward_Tanker_Weighment.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }
    }*/

    public void UploadImagesAndInsert() {
        String FileInitial = "InVeh_In_";
        arrayOfByteArrays[0] = ImgVehicle;
        arrayOfByteArrays[1] = ImgDriver;
        imgPath1 = "GAimages/"+ FileInitial + etserialnumber.getText().toString() + ".jpeg";
        for (byte[] byteArray : arrayOfByteArrays) {

            MultipartTask multipartTask = new MultipartTask(byteArray, FileInitial + etserialnumber.getText().toString() + ".jpeg", "");
            multipartTask.execute();
            FileInitial = "InDrv_In_";
        }
        imgPath2 = "GAimages/"+ FileInitial + etserialnumber.getText().toString() + ".jpeg";
        FileInitial = "";
        weinsertdata();
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

    public void onBackPressed() {
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
        finish();
    }

    public void FetchVehicleDetails(@NonNull String vehicleNo, String vehicleType, char NextProcess, char inOut) {
        Call<InTanWeighResponseModel> call = weighmentdetails.getWeighbyfetchVehData(vehicleNo, vehicleType, NextProcess, inOut);
        call.enqueue(new Callback<InTanWeighResponseModel>() {
            @Override
            public void onResponse(Call<InTanWeighResponseModel> call, Response<InTanWeighResponseModel> response) {
                if (response.isSuccessful()) {
                    InTanWeighResponseModel data = response.body();
                    if (data.getVehicleNo() != "") {
                        inwardid = data.getInwardId();
                        etserialnumber.setText(data.getSerialNo());
                        etserialnumber.setEnabled(false);
                        etvehicalno.setText(data.getVehicleNo());
                        etvehicalno.setEnabled(false);
                        etsuppliername.setText(data.getPartyName());
                        etsuppliername.setEnabled(false);
                        etmaterialname.setText(data.getMaterial());
                        etmaterialname.setEnabled(false);
                        etoano.setText(data.getOA_PO_number());
                        etoano.setEnabled(false);
                        etdriverno.setText(data.getDriver_MobileNo());
                        etdriverno.setEnabled(false);
                        etdate.setText(data.getDate());
                        etdate.setEnabled(false);
                        etint.requestFocus();
                        etint.callOnClick();
                    }
                } else {
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
                Toasty.error(Inward_Tanker_Weighment.this, "failed..!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void inweighmenttankergridclick(View view) {
        Intent intent = new Intent(this, GridCompleted.class);
        startActivity(intent);
    }
}