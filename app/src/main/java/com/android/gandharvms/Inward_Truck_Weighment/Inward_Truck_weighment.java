package com.android.gandharvms.Inward_Truck_Weighment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.gandharvms.Global_Var;
import com.android.gandharvms.Inward_Tanker_Security.grid;
import com.android.gandharvms.Inward_Tanker_Weighment.InTanWeighRequestModel;
import com.android.gandharvms.Inward_Tanker_Weighment.InTanWeighResponseModel;
import com.android.gandharvms.Inward_Tanker_Weighment.Inward_Tanker_Weighment;
import com.android.gandharvms.Inward_Tanker_Weighment.ItUpdweighrequestmodel;
import com.android.gandharvms.Inward_Tanker_Weighment.it_in_weigh_Completedgrid;
import com.android.gandharvms.Inward_Truck_store.ExtraMaterial;
import com.android.gandharvms.LoginWithAPI.LoginMethod;
import com.android.gandharvms.LoginWithAPI.ResponseModel;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.LoginWithAPI.Weighment;
import com.android.gandharvms.NotificationAlerts.NotificationCommonfunctioncls;
import com.android.gandharvms.Util.ImageUtils;
import com.android.gandharvms.Util.MultipartTask;
import com.android.gandharvms.Util.dialogueprogreesbar;
import com.google.common.reflect.TypeToken;
import com.google.firebase.Timestamp;

import com.android.gandharvms.FcmNotificationsSender;
import com.android.gandharvms.Menu;
import com.android.gandharvms.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class Inward_Truck_weighment extends NotificationCommonfunctioncls {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int CAMERA_PERM_CODE1 = 100;
    private static final int CAMERA_PERM_CODE = 101;
    private static final int CAMERA_REQUEST_CODE = 102;
    private static final int CAMERA_REQUEST_CODE1 = 103;
    final Calendar calendar = Calendar.getInstance();
    private final int MAX_LENGTH = 10;
    EditText etint, etremark, etserialnumber, etcontainer, etvehicalnumber, etsupplier, etdriver, etoanumber, etdate, etgrossweight, etsignby;
    Button intsubmit;
    Button view;
    FirebaseFirestore trwdbroot;
    DatePickerDialog picker;
    EditText datetimeTextview;
    TimePickerDialog tpicker;
    ImageView img1, img2;
    Uri image1, image2;
    byte[] ImgDriver, ImgVehicle;
    byte[][] arrayOfByteArrays = new byte[2][];
    Uri[] LocalImgPath = new Uri[2];
    FirebaseStorage storage;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-YYYY, HH:mm:ss");
    Timestamp timestamp = new Timestamp(calendar.getTime());
    ImageView btnlogout, btnhome;
    TextView username, empid;
    dialogueprogreesbar dialogHelper = new dialogueprogreesbar();
    //prince
    private SharedPreferences sharedPreferences;
    private String imgPath1, imgPath2;
    private String token;
    private Weighment weighmentdetails;
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private final String EmployeId = Global_Var.getInstance().EmpId;
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
        weighmentdetails = RetroApiClient.getWeighmentDetails();

        sharedPreferences = getSharedPreferences("TruckWeighment", MODE_PRIVATE);

        etint = findViewById(R.id.etintime);
        etserialnumber = findViewById(R.id.ettrwserialnumber);
        etvehicalnumber = findViewById(R.id.ettrwvehicalno);
        etsupplier = findViewById(R.id.ettrwsupplier);
        //etmaterial=(EditText) findViewById(R.id.ettrwmaterial);
        etdriver = findViewById(R.id.ettrdriverno);
        etoanumber = findViewById(R.id.ettroa);
        etdate = findViewById(R.id.ettrdate);
        etgrossweight = findViewById(R.id.ettrgrossweight);
        etsignby = findViewById(R.id.ettrsignby);
        etcontainer = findViewById(R.id.ettrwcontainer);
        etremark = findViewById(R.id.ettwremark);
        setupHeader();

        // listing Data button
        img1 = findViewById(R.id.ettrimageView1);
        img2 = findViewById(R.id.ettrimageView2);
        storage = FirebaseStorage.getInstance();


        String vehicleNo = getIntent().getStringExtra("vehicle_number");
        String mode = getIntent().getStringExtra("mode"); // "update" or null

        if (vehicleNo != null && !vehicleNo.isEmpty()) {
            ReadWeighmentDataIn(vehicleNo,mode);
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

        /*etvehicalnumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    FetchVehicleDetails(etvehicalnumber.getText().toString().trim(), vehicleType, nextProcess, inOut);
                }
            }
        });*/

        if (getIntent().hasExtra("VehicleNumber")) {
            FetchVehicleDetails(getIntent().getStringExtra("VehicleNumber"), Global_Var.getInstance().MenuType, nextProcess, inOut);
        }

        intsubmit = findViewById(R.id.wesubmit);
        trwdbroot = FirebaseFirestore.getInstance();

        etint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                String time = format.format(calendar.getTime());
                etint.setText(time);
            }
        });


        intsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (image1 == null || image2 == null) {
                    Toasty.warning(Inward_Truck_weighment.this, "Please Upload Image", Toast.LENGTH_SHORT).show();
                } else {
                    UploadImagesAndInsert();
                }
            }
        });
    }

    public void ReadWeighmentDataIn(@NonNull String vehicleNo, @Nullable String mode) {
        // Use neutral/wildcard params so the record is found regardless of process filters
        Call<InTanWeighResponseModel> call = weighmentdetails.getWeighbyfetchVehData(vehicleNo, "x", 'x', 'x');
        call.enqueue(new Callback<InTanWeighResponseModel>() {
            @Override
            public void onResponse(Call<InTanWeighResponseModel> call, Response<InTanWeighResponseModel> response) {
                if (response.isSuccessful()) {
                    InTanWeighResponseModel data = response.body();
                    if (data.getVehicleNo() != null && !data.getVehicleNo().isEmpty()) {
                        inwardid = data.getInwardId();

                        // Prefill
                        etserialnumber.setText(data.getSerialNo());
                        etvehicalnumber.setText(data.getVehicleNo());
                        etsupplier.setText(data.getPartyName());
                        etoanumber.setText(data.getOA_PO_number());
                        etdriver.setText(String.valueOf(data.getDriver_MobileNo()));
                        etdate.setText(data.getDate());
                        etgrossweight.setText(data.getGrossWeight());
                        etcontainer.setText(data.getContainerNo());
                        etsignby.setText(data.getInWeiSignBy());
                        etremark.setText(data.getInWeiRemark());


                        // Materials list
                        String extraMaterialsJson = data.getExtramaterials();
                        List<ExtraMaterial> extraMaterials = parseExtraMaterials(extraMaterialsJson);
                        createExtraMaterialViews(extraMaterials);

                        boolean isUpdate = "update".equalsIgnoreCase(mode);

                        // Hide capture/time in update mode
                        if (isUpdate) {
                            img1.setVisibility(View.GONE);
                            img2.setVisibility(View.GONE);
                            etint.setVisibility(View.GONE);
                        }

                        // Always keep serial & date read-only
                        etserialnumber.setEnabled(false);
                        etdate.setEnabled(false);

                        // In UPDATE mode, allow editing these:
                        etvehicalnumber.setEnabled(isUpdate);
                        etsupplier.setEnabled(isUpdate);
                        etdriver.setEnabled(isUpdate);
                        etoanumber.setEnabled(isUpdate);


                        if ("update".equals(mode)) {
                            intsubmit.setVisibility(View.VISIBLE);
                            intsubmit.setText("Update");
                            intsubmit.setOnClickListener(v -> {
                                weupdatedataIn();
                            });
                        }
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
                Toasty.error(Inward_Truck_weighment.this, "failed..!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void weupdatedataIn() {
        String vehicelnumber = etvehicalnumber.getText().toString().trim();
        String suppliername = etsupplier.getText().toString().trim();
        String driverno = etdriver.getText().toString().trim();
        String oan = etoanumber.getText().toString().trim();
        String grossweight=etgrossweight.getText().toString().trim();
        String containerno=etcontainer.getText().toString().trim();
        String signby=etsignby.getText().toString().trim();
        String remark=etremark.getText().toString().trim();

        if (vehicelnumber.isEmpty() || suppliername.isEmpty() || driverno.isEmpty() || oan.isEmpty() || grossweight.isEmpty()
                || containerno.isEmpty()|| signby.isEmpty()|| remark.isEmpty()) {
            Toasty.warning(Inward_Truck_weighment.this, "All fields must be filled", Toast.LENGTH_SHORT, true).show();
            return;
        }

        ItUpdweighrequestmodel weighReqModel = new ItUpdweighrequestmodel();
        weighReqModel.setInwardId(inwardid);
        weighReqModel.setVehicleNo(vehicelnumber);
        weighReqModel.setPartyName(suppliername);
        weighReqModel.setOA_PO_number(oan);
        weighReqModel.setDriver_MobileNo(driverno);
        weighReqModel.setGrossWeight(grossweight);
        weighReqModel.setContainerNo(containerno);
        weighReqModel.setRemark(remark);
        weighReqModel.setSignBy(signby);
        weighReqModel.setUpdatedBy(Global_Var.getInstance().Name);


        Call<Boolean> call = weighmentdetails.irupdateweighdata(weighReqModel); // use truck-specific API if different
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful() && response.body() != null && response.body()) {
                    Toasty.success(Inward_Truck_weighment.this, "Data Updated successfully..!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Inward_Truck_weighment.this, it_in_weigh_Completedgrid.class));
                    finish();
                } else {
                    Toasty.error(Inward_Truck_weighment.this, "Data Update Failed..!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.e("Retrofit", "Failure: " + t.getMessage());
                Toasty.error(Inward_Truck_weighment.this, "Failed..!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getCurrentTime() {
        // Get the current time
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(new Date());
    }

    public void makeNotification(String vehicleNumber, String outTime) {
        Call<List<ResponseModel>> call = userDetails.getUsersListData();
        call.enqueue(new Callback<List<ResponseModel>>() {
            @Override
            public void onResponse(Call<List<ResponseModel>> call, Response<List<ResponseModel>> response) {
                if (response.isSuccessful()) {
                    List<ResponseModel> userList = response.body();
                    if (userList != null) {
                        for (ResponseModel responseModel : userList) {
                            String specificrole = "Store";
                            if (specificrole.equals(responseModel.getDepartment())) {
                                token = responseModel.getToken();
                                FcmNotificationsSender notificationsSender = new FcmNotificationsSender(
                                        token,
                                        "Inward Truck Weighment Process Done..!",
                                        "Vehicle Number:-" + vehicleNumber + " has completed Weighment process at " + outTime,
                                        getApplicationContext(),
                                        Inward_Truck_weighment.this
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
                Toasty.error(Inward_Truck_weighment.this, "failed..!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void intrinsert() {
        String intime = etint.getText().toString().trim();
        String serialnumber = etserialnumber.getText().toString().trim();
        String vehicalnumber = etvehicalnumber.getText().toString().trim();
        String supplier = etsupplier.getText().toString().trim();
        //String material=etmaterial.getText().toString().trim();
        String Driver = etdriver.getText().toString().trim();
        String oanumber = etoanumber.getText().toString().trim();
        String date = etdate.getText().toString().trim();
        String Grossweight = etgrossweight.getText().toString().trim();
        String container = etcontainer.getText().toString().trim();
        String tareweight = "0";
        String netweight = "0";
        String remark = etremark.getText().toString().trim();
        String signby = etsignby.getText().toString().trim();
        String outTime = getCurrentTime();


        if (intime.isEmpty() || serialnumber.isEmpty() || vehicalnumber.isEmpty() || supplier.isEmpty() ||
                Driver.isEmpty() || oanumber.isEmpty() || date.isEmpty() ||
                Grossweight.isEmpty() || container.isEmpty() || remark.isEmpty() || signby.isEmpty()) {
            Toasty.warning(this, "All fields must be filled", Toast.LENGTH_SHORT, true).show();
        } else {
            InTanWeighRequestModel weighReqModel = new InTanWeighRequestModel(inwardid, intime, outTime, Grossweight, tareweight,
                    netweight, remark, signby, container, imgPath1, imgPath2, serialnumber,
                    vehicalnumber, date, supplier, "material", oanumber, Driver, 'R', inOut, vehicleType,
                    EmployeId, EmployeId, "", "", "");
            dialogHelper.showConfirmationDialog(this, () -> {
                dialogHelper.showProgressDialog(this);
                Call<Boolean> call = weighmentdetails.insertWeighData(weighReqModel);
                call.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if (response.isSuccessful() && response.body() != null && response.body()) {
                            dialogHelper.hideProgressDialog();
                            Log.d("Registration", "Response Body: " + response.body());
                            deleteLocalImage(vehicalnumber, outTime);
                        } else {
                            Toasty.error(Inward_Truck_weighment.this, "Data Insertion Failed..!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        dialogHelper.hideProgressDialog();
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
                        Toasty.error(Inward_Truck_weighment.this, "failed..!", Toast.LENGTH_SHORT).show();
                    }
                });
            });
        }
    }

    public void UploadImagesAndInsert() {
        String FileInitial = "IRVeh_In_";
        arrayOfByteArrays[0] = ImgVehicle;
        arrayOfByteArrays[1] = ImgDriver;
        imgPath1 = "GAimages/" + FileInitial + etserialnumber.getText().toString() + ".jpeg";
        for (byte[] byteArray : arrayOfByteArrays) {

            MultipartTask multipartTask = new MultipartTask(byteArray, FileInitial + etserialnumber.getText().toString() + ".jpeg", "");
            multipartTask.execute();
            FileInitial = "IRDrv_In_";
        }
        imgPath2 = "GAimages/" + FileInitial + etserialnumber.getText().toString() + ".jpeg";
        FileInitial = "";
        intrinsert();
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

    // Function to upload image to server and delete local image after successful upload
    private void deleteLocalImage(String vehicalnumber, String outTime) {
        File imageFile;
        try {
            for (Uri imgpath : LocalImgPath) {
                ImageUtils.deleteImage(this, imgpath);
            }
            Toasty.success(Inward_Truck_weighment.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
            makeNotification(vehicalnumber, outTime);
            startActivity(new Intent(Inward_Truck_weighment.this, grid.class));
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
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
                    if (data.getVehicleNo() != "" && data.getVehicleNo() != null) {
                        inwardid = data.getInwardId();
                        etserialnumber.setText(data.getSerialNo());
                        etserialnumber.setEnabled(false);
                        etvehicalnumber.setText(data.getVehicleNo());
                        etvehicalnumber.setEnabled(false);
                        etsupplier.setText(data.getPartyName());
                        etsupplier.setEnabled(false);
                        //etmaterial.setText(data.getMaterial());
                        //etmaterial.setEnabled(false);
                        String extraMaterialsJson = data.getExtramaterials();
                        Log.d("JSON Debug", "Extra Materials JSON: " + extraMaterialsJson);
                        List<ExtraMaterial> extraMaterials = parseExtraMaterials(extraMaterialsJson);
                        Log.d("JSON Debug", "Parsed Extra Materials Size: " + extraMaterials.size());
                        createExtraMaterialViews(extraMaterials);
                        etoanumber.setText(data.getOA_PO_number());
                        etoanumber.setEnabled(false);
                        etdriver.setText(String.valueOf(data.getDriver_MobileNo()));
                        etdriver.setEnabled(false);
                        etdate.setText(data.getDate());
                        etdate.setEnabled(false);
                    } else {
                        Toasty.error(Inward_Truck_weighment.this, "This Vehicle Number Is Not Available..!", Toast.LENGTH_SHORT).show();
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
                Toasty.error(Inward_Truck_weighment.this, "failed..!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<ExtraMaterial> parseExtraMaterials(String jsonString) {
        if (jsonString == null || jsonString.trim().isEmpty()) {
            Log.e("JSON Parser", "JSON string is null or empty");
            return new ArrayList<>(); // Return an empty list if JSON is invalid
        }

        try {
            Log.d("JSON Parser", "JSON String: " + jsonString);
            Gson gson = new Gson();
            Type listType = new TypeToken<List<ExtraMaterial>>() {
            }.getType();
            return gson.fromJson(jsonString, listType);
        } catch (JsonSyntaxException e) {
            Log.e("JSON Parser", "Failed to parse JSON: " + jsonString, e);
            return new ArrayList<>(); // Return an empty list in case of parsing error
        }
    }

    private void validateJson(String jsonString) {
        try {
            new JsonParser().parse(jsonString);
            Log.d("JSON Validator", "Valid JSON: " + jsonString);
        } catch (JsonSyntaxException e) {
            Log.e("JSON Validator", "Invalid JSON: " + jsonString, e);
        }
    }

    public void createExtraMaterialViews(List<ExtraMaterial> extraMaterials) {
        LinearLayout linearLayout = findViewById(R.id.layout_trweighlistmaterial); // Ensure this is the correct ID

        // Clear previous views if any
        linearLayout.removeAllViews();

        for (ExtraMaterial extraMaterial : extraMaterials) {
            View materialView = getLayoutInflater().inflate(R.layout.allmaterial, null);

            EditText materialEditText = materialView.findViewById(R.id.etallmaterialmet);
            EditText qtyEditText = materialView.findViewById(R.id.etallmaterialqty);
            Spinner uomSpinner = materialView.findViewById(R.id.allmaterialspinner_team);

            materialEditText.setText(extraMaterial.getMaterial());
            materialEditText.setEnabled(false);
            qtyEditText.setText(extraMaterial.getQty());
            qtyEditText.setEnabled(false);

            List<String> teamList = Arrays.asList("NA", "Ton", "Litre", "KL", "Kgs", "Pcs", "M3", "Meter", "Feet"); // or fetch it dynamically
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, teamList);
            uomSpinner.setAdapter(arrayAdapter);
            uomSpinner.setEnabled(false);

            setSpinnerValue(uomSpinner, extraMaterial.getQtyuom());

            // Add the material view to the linear layout
            linearLayout.addView(materialView);
        }
        String extraMaterialsString = convertExtraMaterialsListToString(extraMaterials);
    }

    private String convertExtraMaterialsListToString(List<ExtraMaterial> extraMaterials) {
        StringBuilder result = new StringBuilder();

        for (ExtraMaterial extraMaterial : extraMaterials) {
            String materialString = convertExtraMaterialToString(extraMaterial);

            // Add this string to the result
            result.append(materialString).append("\n"); // Separate entries by a newline or any other delimiter
        }

        return result.toString();
    }

    private String convertExtraMaterialToString(ExtraMaterial extraMaterial) {
        String material = extraMaterial.getMaterial();
        String qty = extraMaterial.getQty();
        String qtyuom = extraMaterial.getQtyuom();

        // Concatenate fields into a single string
        return (material + "," + qty + "," + qtyuom);
    }

    private void setSpinnerValue(Spinner spinner, String value) {
        SpinnerAdapter adapter = spinner.getAdapter();
        if (adapter != null) {
            for (int i = 0; i < adapter.getCount(); i++) {
                if (adapter.getItem(i).toString().equals(value)) {
                    spinner.setSelection(i);
                    break;
                }
            }
        } else {
            Log.e("Spinner Error", "Spinner adapter is null");
        }
    }

    public void WeighViewclick(View view) {
        Intent intent = new Intent(this, grid.class);
        startActivity(intent);
    }

    public void truckweicompletedclick(View view) {
        Intent intent = new Intent(this, it_in_weigh_Completedgrid.class);
        startActivity(intent);
    }
}