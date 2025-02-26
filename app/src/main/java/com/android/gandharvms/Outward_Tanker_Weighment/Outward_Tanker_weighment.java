package com.android.gandharvms.Outward_Tanker_Weighment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.gandharvms.FcmNotificationsSender;
import com.android.gandharvms.Global_Var;
import com.android.gandharvms.Inward_Tanker;
import com.android.gandharvms.Inward_Tanker_Weighment.Inward_Tanker_Weighment;
import com.android.gandharvms.Inward_Truck_store.ExtraMaterial;
import com.android.gandharvms.LoginWithAPI.Login;
import com.android.gandharvms.LoginWithAPI.LoginMethod;
import com.android.gandharvms.LoginWithAPI.ResponseModel;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.Menu;
import com.android.gandharvms.NotificationAlerts.NotificationCommonfunctioncls;
import com.android.gandharvms.OutwardOut_Truck_Weighment;
import com.android.gandharvms.Outward_Tanker;
import com.android.gandharvms.Outward_Tanker_Billing.Outward_Tanker_Billing;
import com.android.gandharvms.Outward_Tanker_Security.Grid_Outward;
import com.android.gandharvms.Outward_Tanker_Security.Outward_RetroApiclient;
import com.android.gandharvms.Outward_Tanker_Security.Outward_Tanker_Security;
import com.android.gandharvms.Outward_Truck;
import com.android.gandharvms.Outward_Truck_Dispatch.Outward_Truck_interface;
import com.android.gandharvms.Outward_Truck_Dispatch.Varified_Model;
import com.android.gandharvms.Outward_Truck_Weighment.Outward_Truck_weighment;
import com.android.gandharvms.ProductListData;
import com.android.gandharvms.R;
import com.android.gandharvms.Util.ImageUtils;
import com.android.gandharvms.Util.MultipartTask;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.common.reflect.TypeToken;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
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
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class Outward_Tanker_weighment extends NotificationCommonfunctioncls {

    private static final int CAMERA_PERM_CODE1 = 100;
    private static final int CAMERA_PERM_CODE = 101;
    private static final int CAMERA_REQUEST_CODE = 102;
    private static final int CAMERA_REQUEST_CODE1 = 103;
    public static String Tanker;
    public static String Truck;
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private final String EmployeId = Global_Var.getInstance().EmpId;
    EditText intime, serialnumber, vehiclenumber, materialname, custname, oanum, tareweight, tankernumber,
            etremark, transportername, howmuchqty, elocation,etbillremark,verifyremark,compartment1,compartment2,compartment3,compartment4,compartment5,compartment6,
             grossweight1,grossweight2,grossweight3,grossweight4,grossweight5,grossweight6;
    Button submit, complted;
    FirebaseFirestore dbroot;
    TimePickerDialog tpicker;
    Calendar calendar = Calendar.getInstance();
    ImageView img1, img2;
    Uri image1, image2;
    byte[] ImgDriver, ImgVehicle;
    byte[][] arrayOfByteArrays = new byte[2][];
    Uri[] LocalImgPath = new Uri[2];
    ImageView btnlogout, btnhome;
    TextView username, empid;
    private int OutwardId;
    private Outward_weighment outwardWeighment;
    private String token;
    private LoginMethod userDetails;
    private String serialNo;
    private String imgPath1, imgPath2;
    public LinearLayout layoutname,layoutimg;
    public String Netxtdept = "";
    char despatchNextChar = ' ';
    public String despatchnext = "";
    Button verifybtn;
    private Outward_Truck_interface outwardTruckInterface;
    CardView cardone,cardtwo,cardthree,cardfour,cardfive,cardsix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outward_tanker_weighment);
        setupHeader();
        outwardWeighment = Outward_RetroApiclient.outwardWeighment();
        userDetails = RetroApiClient.getLoginApi();
        FirebaseMessaging.getInstance().subscribeToTopic(token);
        outwardTruckInterface = Outward_RetroApiclient.outwardtruckdispatch();

        intime = findViewById(R.id.etintime);
        serialnumber = findViewById(R.id.etserialnumber);
        vehiclenumber = findViewById(R.id.etvehicleno);
        materialname = findViewById(R.id.etmaterialname);
        custname = findViewById(R.id.etcustomername);
        oanum = findViewById(R.id.etoanumberrecfrombilling);
        tareweight = findViewById(R.id.ettareweight);
        tankernumber = findViewById(R.id.ettankernumber);
        etremark = findViewById(R.id.etremark);
//        product = findViewById(R.id.etproduct);
        transportername = findViewById(R.id.ettransportname);
        howmuchqty = findViewById(R.id.ethowmuchqtyfill);
        elocation = findViewById(R.id.etlocation);
        etbillremark=findViewById(R.id.etBillingRemark);
        layoutname = findViewById(R.id.imglayoutouttanername);
        layoutimg = findViewById(R.id.imglayoutouttanerimg);
        verifybtn = findViewById(R.id.outwardtankerbtnvarified);
        verifybtn.setVisibility(View.GONE);
        verifyremark = findViewById(R.id.etoutwardtankerweightvarifyremark);
        verifyremark.setVisibility(View.GONE);
        cardone  = findViewById(R.id.compaone);
        cardone.setVisibility(View.GONE);
        cardtwo = findViewById(R.id.compatwo);
        cardtwo.setVisibility(View.GONE);
        cardthree = findViewById(R.id.compathree);
        cardthree.setVisibility(View.GONE);
        cardfour = findViewById(R.id.compafour);
        cardfour.setVisibility(View.GONE);
        cardfive = findViewById(R.id.compafive);
        cardfive.setVisibility(View.GONE);
        cardsix = findViewById(R.id.compasix);
        cardsix.setVisibility(View.GONE);

        compartment1 = findViewById(R.id.netweight1);
        compartment2 = findViewById(R.id.netweight2);
        compartment3 = findViewById(R.id.netweight3);
        compartment4 = findViewById(R.id.netweight4);
        compartment5 = findViewById(R.id.netweight5);
        compartment6 = findViewById(R.id.netweight6);

        grossweight1 = findViewById(R.id.gross1);
        grossweight2 = findViewById(R.id.gross2);
        grossweight3 = findViewById(R.id.gross3);
        grossweight4 = findViewById(R.id.gross4);
        grossweight5 = findViewById(R.id.gross5);
        grossweight6 = findViewById(R.id.gross6);


        img1 = findViewById(R.id.etoutwardotinweighvehicleimg);
        img2 = findViewById(R.id.etoutwardotinweighDriverimg);

        submit = findViewById(R.id.etssubmit);
        dbroot = FirebaseFirestore.getInstance();
        complted = findViewById(R.id.otinweighcompleted);

        tankernumber.setVisibility(View.GONE);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (image1 == null || image2 == null) {
                    Toasty.warning(Outward_Tanker_weighment.this, "Please Upload Image", Toast.LENGTH_SHORT).show();
                } else {
                    UploadImagesAndUpdate();
                }
            }
        });
        complted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Outward_Tanker_weighment.this, OT_Completed_Weighment.class));
            }
        });
        intime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                String time = format.format(calendar.getTime());
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

        if (getIntent().hasExtra("vehiclenum")) {
            FetchVehicleDetails(getIntent().getStringExtra("vehiclenum"), Global_Var.getInstance().MenuType, nextProcess, inOut);
        }
        verifybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verification();
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
                            String specificRole = "Production";
                            if (specificRole.equals(resmodel.getDepartment())) {
                                token = resmodel.getToken();

                                FcmNotificationsSender notificationsSender = new FcmNotificationsSender(
                                        token,
                                        "Outward Tanker Weighment Process Done..!",
                                        "Vehicle Number:-" + vehicleNumber + " has completed Weighment process at " + outTime,
                                        getApplicationContext(),
                                        Outward_Tanker_weighment.this
                                );
                                notificationsSender.triggerSendNotification();
                            }
                        }
                    }
                } else {
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
                Toasty.error(Outward_Tanker_weighment.this, "failed..!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void FetchVehicleDetails(@NonNull String vehicleNo, String vehicleType, char NextProcess, char inOut) {
        Call<Response_Outward_Tanker_Weighment> call = outwardWeighment.fetchweighment(vehicleNo, vehicleType, NextProcess, inOut);
        call.enqueue(new Callback<Response_Outward_Tanker_Weighment>() {
            @Override
            public void onResponse(Call<Response_Outward_Tanker_Weighment> call, Response<Response_Outward_Tanker_Weighment> response) {
                if (response.isSuccessful()) {
                    Response_Outward_Tanker_Weighment data = response.body();
                    if (data.getVehicleNumber() != "" && data.getVehicleNumber() != null) {
                        OutwardId = data.getOutwardId();
                        serialNo = data.getSerialNumber();
                        serialnumber.setText(data.getSerialNumber());
                        serialnumber.setEnabled(false);
                        oanum.setText(data.getOAnumber());
                        oanum.setEnabled(false);
                        vehiclenumber.setText(data.getVehicleNumber());
                        vehiclenumber.setEnabled(false);
                        materialname.setText(data.getProductName());
                        materialname.setEnabled(false);
                        custname.setText(data.getCustomerName());
                        custname.setEnabled(false);
                        howmuchqty.setText(String.valueOf(data.getHowMuchQuantityFilled()));
                        howmuchqty.setEnabled(false);
                        transportername.setText(data.getTransportName());
                        transportername.setEnabled(false);
                        elocation.setText(data.getLocation());
                        elocation.setEnabled(false);
                        etbillremark.setText(data.getTankerBillingRemark());
                        etbillremark.setEnabled(false);
//                        tareweight.setEnabled(false);
                        String extraMaterialsJson = data.getProductQTYUOMOA();
                        Log.d("JSON Debug", "Extra Materials JSON: " + extraMaterialsJson);
                        List<ProductListData> extraMaterials = parseExtraMaterials(extraMaterialsJson);
                        Log.d("JSON Debug", "Parsed Extra Materials Size: " + extraMaterials.size());
                        createExtraMaterialViews(extraMaterials);
                        /*intime.requestFocus();
                        intime.callOnClick();*/
                        Netxtdept = data.getPurposeProcess();
                        if(data.getPurposeProcess() == null || data.getPurposeProcess().trim().isEmpty()){
                            intime.setVisibility(View.VISIBLE);
                            tareweight.setVisibility(View.VISIBLE);
                            etremark.setVisibility(View.VISIBLE);
                        }else {
                            // Hide fields when purposeProcess contains any value
                            intime.setVisibility(View.GONE);
//                            tareweight.setVisibility(View.GONE);
                            etremark.setVisibility(View.GONE);
                            layoutname.setVisibility(View.GONE);
                            layoutimg.setVisibility(View.GONE);
                            submit.setVisibility(View.GONE);
                            verifybtn.setVisibility(View.VISIBLE);
                            verifyremark.setVisibility(View.VISIBLE);
                            cardone.setVisibility(View.VISIBLE);
                            cardtwo.setVisibility(View.VISIBLE);
                            cardthree.setVisibility(View.VISIBLE);
                            cardfour.setVisibility(View.VISIBLE);
                            cardfive.setVisibility(View.VISIBLE);
                            cardsix.setVisibility(View.VISIBLE);
//                            compartment1.setText(data.get);
                            tareweight.setText(data.getTareWeight());
                            tareweight.setEnabled(false);
                            handleCompartmentFields(data);
                            // Handle gross weight and net weight logic
                            setupGrossWeightLogic();
                            compartment1.setText(String.valueOf(data.getCompartment1()));
                            compartment2.setText(String.valueOf(data.getCompartment2()));
                            compartment3.setText(String.valueOf(data.getCompartment3()));
                            compartment4.setText(String.valueOf(data.getCompartment4()));
                            compartment5.setText(String.valueOf(data.getCompartment5()));
                            compartment6.setText(String.valueOf(data.getCompartment6()));

                        }
                    } else {
                        Toasty.error(Outward_Tanker_weighment.this, "This Vehicle Number Is Not Available..!", Toast.LENGTH_SHORT).show();
                    }
                } else {
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

    private List<ProductListData> parseExtraMaterials(String jsonString) {
        if (jsonString == null || jsonString.trim().isEmpty()) {
            Log.e("JSON Parser", "JSON string is null or empty");
            return new ArrayList<>(); // Return an empty list if JSON is invalid
        }
        try {
            Log.d("JSON Parser", "JSON String: " + jsonString);
            Gson gson = new Gson();
            Type listType = new TypeToken<List<ProductListData>>() {
            }.getType();
            return gson.fromJson(jsonString, listType);
        } catch (JsonSyntaxException e) {
            Log.e("JSON Parser", "Failed to parse JSON: " + jsonString, e);
            return new ArrayList<>(); // Return an empty list in case of parsing error
        }
    }

    public void createExtraMaterialViews(List<ProductListData> extraMaterials) {
        LinearLayout linearLayout = findViewById(R.id.layout_productlistitinweighment); // Ensure this is the correct ID

        // Clear previous views if any
        linearLayout.removeAllViews();

        for (ProductListData extraMaterial : extraMaterials) {
            View materialView = getLayoutInflater().inflate(R.layout.allproductdetaisllist, null);

            EditText etoanumber = materialView.findViewById(R.id.etitinweioano);
            EditText etproductname = materialView.findViewById(R.id.etitinweiproductname);
            EditText etproductqty = materialView.findViewById(R.id.etitinweiproductqty);
            Spinner productuom = materialView.findViewById(R.id.etitinweiprospinner_team);

            etoanumber.setText(extraMaterial.getOANumber());
            etoanumber.setEnabled(false);
            etproductname.setText(extraMaterial.getProductName());
            etproductname.setEnabled(false);
            etproductqty.setText(extraMaterial.getProductQty());
            etproductqty.setEnabled(false);

            List<String> teamList = Arrays.asList("Ton", "KL"); // or fetch it dynamically
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, teamList);
            productuom.setAdapter(arrayAdapter);
            productuom.setEnabled(false);
            setSpinnerValue(productuom, extraMaterial.getProductQtyuom());
            // Add the material view to the linear layout
            linearLayout.addView(materialView);
        }
        String extraMaterialsString = convertExtraMaterialsListToString(extraMaterials);
    }

    private String convertExtraMaterialsListToString(List<ProductListData> extraMaterials) {
        StringBuilder result = new StringBuilder();
        for (ProductListData extraMaterial : extraMaterials) {
            String materialString = convertExtraMaterialToString(extraMaterial);
            // Add this string to the result
            result.append(materialString).append("\n"); // Separate entries by a newline or any other delimiter
        }
        return result.toString();
    }

    private String convertExtraMaterialToString(ProductListData extraMaterial) {
        String OANumber = extraMaterial.getOANumber();
        String productname = extraMaterial.getProductName();
        String productqty = extraMaterial.getProductQty();
        String productqtyuom = extraMaterial.getProductQtyuom();
        // Concatenate fields into a single string
        return (OANumber + "," + productname + "," + productqty + "," + productqtyuom);
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

    private String getCurrentTime() {
        // Get the current time
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(new Date());
    }


    public void insert() {
        String etintime = intime.getText().toString().trim();
        String etserialnumber = serialnumber.getText().toString().trim();
        String etvehiclenumber = vehiclenumber.getText().toString().trim();
        String ettareweight = tareweight.getText().toString().trim();
        String outTime = getCurrentTime();
        String uremark = etremark.getText().toString().trim();
        if (etintime.isEmpty() || etserialnumber.isEmpty() || etvehiclenumber.isEmpty() || ettareweight.isEmpty() || uremark.isEmpty()) {
            Toasty.warning(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
        } else {
            Response_Outward_Tanker_Weighment responseOutwardTankerWeighment = new Response_Outward_Tanker_Weighment(
                    OutwardId, etintime, outTime, imgPath2, imgPath1, "", "", "",
                    "", ettareweight, "", "", "", "", 'W',
                    uremark, EmployeId, EmployeId, 'P', 'I', vehicleType, etserialnumber, etvehiclenumber);
            Call<Boolean> call = outwardWeighment.updateweighmentoutwardtanker(responseOutwardTankerWeighment);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body() && response.body()) {
                        Log.d("Registration", "Response Body: " + response.body());
                        deleteLocalImage(etvehiclenumber, outTime);
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
                    Toasty.error(Outward_Tanker_weighment.this, "failed..!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void UploadImagesAndUpdate() {

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

    private void deleteLocalImage(String vehicalnumber, String outTime) {
        File imageFile;
        try {
            for (Uri imgpath : LocalImgPath) {
                ImageUtils.deleteImage(this, imgpath);
            }
            makeNotification(vehicalnumber, outTime);
            Toasty.success(Outward_Tanker_weighment.this, "Data Inserted Successfully", Toast.LENGTH_SHORT, true).show();
            startActivity(new Intent(Outward_Tanker_weighment.this, Grid_Outward.class));
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void outtankerweighmentpending(View view) {
        Intent intent = new Intent(this, Grid_Outward.class);
        startActivity(intent);
    }

    public void otinweighcompletedclick(View view) {
        /*Intent intent = new Intent(this, Grid_Outward.class);
        startActivity(intent);*/
    }
    private void verification() {
        String verification_remark = verifyremark.getText().toString().trim();
        // Handle empty fields safely
//        int netw1 = getSafeInt(compartment1);
//        int netw2 = getSafeInt(compartment2);
//        int netw3 = getSafeInt(compartment3);
//        int netw4 = getSafeInt(compartment4);
//        int netw5 = getSafeInt(compartment5);
//        int netw6 = getSafeInt(compartment6);

        // Fetch existing net weight values (if available)
        double existingNet1 = getSafeDouble(compartment1);
        double existingNet2 = getSafeDouble(compartment2);
        double existingNet3 = getSafeDouble(compartment3);
        double existingNet4 = getSafeDouble(compartment4);
        double existingNet5 = getSafeDouble(compartment5);
        double existingNet6 = getSafeDouble(compartment6);
        // Handle empty fields safely
        double tareWeight = getSafeDouble(tareweight);
        double gross1 = getSafeDouble(grossweight1);
        Log.d("Debug", "Gross1 value: " + gross1);
        double gross2 = getSafeDouble(grossweight2);
        double gross3 = getSafeDouble(grossweight3);
        double gross4 = getSafeDouble(grossweight3);
        double gross5 = getSafeDouble(grossweight3);
        double gross6 = getSafeDouble(grossweight6);

//        // Calculate net weight for each compartment (ensuring no negative values)
//        int netw1 = (int) Math.max(gross1 - tareWeight, 0);
//        int netw2 = (int) Math.max(gross2 - tareWeight, 0);
//        int netw3 = (int) Math.max(gross3 - tareWeight, 0);
//        int netw4 = (int) Math.max(gross4 - tareWeight, 0);
//        int netw5 = (int) Math.max(gross5 - tareWeight, 0);
//        int netw6 = (int) Math.max(gross6 - tareWeight, 0);

        // Calculate net weight for each compartment (use existing value if available)
        int netw1 = (int) ((existingNet1 > 0) ? existingNet1 : Math.max(gross1 - tareWeight, 0));
        int netw2 = (int) ((existingNet2 > 0) ? existingNet2 : Math.max(gross2 - tareWeight, 0));
        int netw3 = (int) ((existingNet3 > 0) ? existingNet3 : Math.max(gross3 - tareWeight, 0));
        int netw4 = (int) ((existingNet4 > 0) ? existingNet4 : Math.max(gross4 - tareWeight, 0));
        int netw5 = (int) ((existingNet5 > 0) ? existingNet5 : Math.max(gross5 - tareWeight, 0));
        int netw6 = (int) ((existingNet6 > 0) ? existingNet6 : Math.max(gross6 - tareWeight, 0));

//        // Set calculated net weight values in respective fields
//        compartment1.setText(String.valueOf(netw1));
//        compartment2.setText(String.valueOf(netw2));
//        compartment3.setText(String.valueOf(netw3));
//        compartment4.setText(String.valueOf(netw4));
//        compartment5.setText(String.valueOf(netw5));
//        compartment6.setText(String.valueOf(netw6));

        // Set net weight values in respective fields
        setCompartmentValue(compartment1, netw1);
        setCompartmentValue(compartment2, netw2);
        setCompartmentValue(compartment3, netw3);
        setCompartmentValue(compartment4, netw4);
        setCompartmentValue(compartment5, netw5);
        setCompartmentValue(compartment6, netw6);

        if (Netxtdept.length()>0){
            despatchNextChar = Netxtdept.charAt(0);
        }
        if (verification_remark.isEmpty()){
            Toasty.warning(this, "Please Enter Remark", Toast.LENGTH_SHORT).show();
        }else {
            Tanker_verification_model tankerVerificationModel = new Tanker_verification_model(OutwardId,"Yes",despatchNextChar,inOut,vehicleType,EmployeId,
                    verification_remark,netw1,netw2,netw3,netw4,netw5,netw6);
            Log.d("API_REQUEST", "Sending: " + new Gson().toJson(tankerVerificationModel));
            Call<Boolean> call = outwardTruckInterface.Tanker_weighmentvarified(tankerVerificationModel);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body() && response.body() == true){
                        Log.d("API_RESPONSE", "Response: " + response.body());
                        Toasty.success(Outward_Tanker_weighment.this, "Data Inserted Succesfully !", Toast.LENGTH_SHORT).show();
                        //makeNotificationforsmallverified(wvehiclenumber);
                        startActivity(new Intent(Outward_Tanker_weighment.this, Outward_Tanker.class));
                        finish();
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
                    Toasty.error(Outward_Tanker_weighment.this, "failed..!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    // Helper method to safely parse integer values
    private int getSafeInt(EditText editText) {
        String text = editText.getText().toString().trim();
        if (text.isEmpty()) {
            return 0; // Return default value (e.g., 0) if the field is empty
        }
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return 0; // Handle parsing error by returning a default value
        }
    }
    private void handleCompartmentFields(Response_Outward_Tanker_Weighment data) {
        setCompartmentField(compartment1, data.getCompartment1());
        setCompartmentField(compartment2, data.getCompartment2());
        setCompartmentField(compartment3, data.getCompartment3());
        setCompartmentField(compartment4, data.getCompartment4());
        setCompartmentField(compartment5, data.getCompartment5());
        setCompartmentField(compartment6, data.getCompartment6());
    }

    private void setCompartmentField(EditText compartmentField, Integer value) {
        if (value != null && value > 0) {
            compartmentField.setText(String.valueOf(value));
            compartmentField.setEnabled(false);
        } else {
            compartmentField.setText("");
            compartmentField.setEnabled(true);
        }
    }
    private void setupGrossWeightLogic() {
        setupNetWeightCalculation(grossweight1, compartment1);
        setupNetWeightCalculation(grossweight2, compartment2);
        setupNetWeightCalculation(grossweight3, compartment3);
        setupNetWeightCalculation(grossweight4, compartment4);
        setupNetWeightCalculation(grossweight5, compartment5);
        setupNetWeightCalculation(grossweight6, compartment6);
    }

    private void setupNetWeightCalculation(EditText grossWeightField, EditText netWeightField) {
        grossWeightField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    double tare = Double.parseDouble(tareweight.getText().toString().trim());
                    double gross = Double.parseDouble(s.toString().trim());
//                    double net = gross - tare;
                    double net = Math.max(gross - tare, 0);  // Ensure no negative values
                    netWeightField.setText(String.valueOf(net));
                } catch (NumberFormatException e) {
                    netWeightField.setText(""); // Reset if input is invalid
                }
            }
        });
    }

    // Utility function to safely parse double values
    private double getSafeDouble(EditText editText) {
        try {
            return Double.parseDouble(editText.getText().toString().trim());
        } catch (NumberFormatException e) {
            return 0; // Return 0 if the value is empty or invalid
        }
    }
    private void setCompartmentValue(EditText editText, int value) {
        editText.setText(String.valueOf(value));
        editText.setEnabled(value == 0);  // Disable field if value exists, enable if empty
    }

}