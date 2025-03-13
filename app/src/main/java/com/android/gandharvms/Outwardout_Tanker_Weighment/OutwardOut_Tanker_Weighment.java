package com.android.gandharvms.Outwardout_Tanker_Weighment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.Toast;

import com.android.gandharvms.FcmNotificationsSender;
import com.android.gandharvms.Global_Var;
import com.android.gandharvms.LoginWithAPI.Login;
import com.android.gandharvms.LoginWithAPI.LoginMethod;
import com.android.gandharvms.LoginWithAPI.ResponseModel;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.Menu;
import com.android.gandharvms.NotificationAlerts.NotificationCommonfunctioncls;
import com.android.gandharvms.OutwardOut_Tanker;
import com.android.gandharvms.Outward_Tanker;
import com.android.gandharvms.Outward_Tanker_Production_forms.Compartment;
import com.android.gandharvms.Outward_Tanker_Security.Grid_Outward;
import com.android.gandharvms.Outward_Tanker_Security.Outward_RetroApiclient;
import com.android.gandharvms.Outward_Tanker_Weighment.Model_OutwardOut_Weighment;
import com.android.gandharvms.Outward_Tanker_Weighment.Outward_Tanker_weighment;
import com.android.gandharvms.Outward_Tanker_Weighment.Outward_weighment;
import com.android.gandharvms.Outward_Tanker_Weighment.Response_Outward_Tanker_Weighment;
import com.android.gandharvms.Outward_Tanker_Weighment.Weighment_compartment_Adapter;
import com.android.gandharvms.ProductListData;
import com.android.gandharvms.R;
import com.android.gandharvms.Util.ImageUtils;
import com.android.gandharvms.Util.MultipartTask;
import com.android.gandharvms.outward_Tanker_Lab_forms.Lab_compartment_model;
import com.google.common.reflect.TypeToken;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

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

public class OutwardOut_Tanker_Weighment extends NotificationCommonfunctioncls {

    EditText intime,serialnumber,vehiclenumber,sealnumber,tareweight,netweight,grossw,etnumberpack,etremark,fetchdensity,etotdip,etotwt,batch,product;
    Button submit,completed;
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
    Uri[] LocalImgPath = new Uri[2];
    private String imgPath1, imgPath2;
    private String etSerialNumber;
    private String vehicleNum;
    private int isemushdip;
    private int iseushwt;
    ImageView btnlogout,btnhome;
    TextView username,empid;
    private List<Lab_compartment_model> compartmentList;
    private Weighment_compartment_Adapter adapter;

    public static String Tanker;
    public static String Truck;
    private RecyclerView recyclerView;
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
        batch =findViewById(R.id.etbatchn);
        product = findViewById(R.id.etproductnam);

        img1=findViewById(R.id.otoutweighvehicleimage);
        img2=findViewById(R.id.otoutweighdriverimage);

        submit = findViewById(R.id.etssubmit);
        dbroot= FirebaseFirestore.getInstance();
//        etotdip = findViewById(R.id.otouttankeretshortdip);
//        etotwt = findViewById(R.id.otouttankeretshortwt);
        completed = findViewById(R.id.otoutweighcompleted);

        FirebaseMessaging.getInstance().subscribeToTopic(token);
        setupHeader();
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
                startActivity(new Intent(OutwardOut_Tanker_Weighment.this, Login.class));
            }
        });
        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OutwardOut_Tanker_Weighment.this, Menu.class));
            }
        });*/

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
        completed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OutwardOut_Tanker_Weighment.this,OT_Completed_Outweighment.class));
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
        recyclerView = findViewById(R.id.recyclerView_labitem_weighment);
        compartmentList = new ArrayList<>();
        //List<Compartment> compartmentList = new ArrayList<>();
        // ✅ Pass `this` (Context) to Adapter
        adapter = new Weighment_compartment_Adapter(this, compartmentList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator()); // Smooth animations
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
                        fetchdensity.setText(data.getDensity_29_5C());
                        fetchdensity.setEnabled(false);
                        tareweight.setText(String.valueOf(data.getTareWeight()));
                        tareweight.setEnabled(false);
                        batch.setText(data.getBatchNo());
                        batch.setEnabled(false);
                        product.setText(data.getProductName());
                        product.setEnabled(false);
                        String extraMaterialsJson = data.getProductQTYUOMOA();
                        Log.d("JSON Debug", "Extra Materials JSON: " + extraMaterialsJson);
                        List<ProductListData> extraMaterials = parseExtraMaterials(extraMaterialsJson);
                        Log.d("JSON Debug", "Parsed Extra Materials Size: " + extraMaterials.size());
                        createExtraMaterialViews(extraMaterials);
                       /* intime.requestFocus();
                        intime.callOnClick();*/
                    //  List<Lab_compartment_model> lstlabcompmodel = new ArrayList<>() ;
//                        Lab_compartment_model labCompartmentModel1 =  parseCompartment(data.getProcompartment1());
//                        if (labCompartmentModel1 != null) {
//                            labCompartmentModel1.setTareweight(String.valueOf(data.getCompartment1()));
//                            compartmentList.add(labCompartmentModel1);
//                        }
//
//                        Lab_compartment_model labCompartmentModel2 =  parseCompartment(data.getProcompartment2());
//                        if (labCompartmentModel2 != null) {
//                            labCompartmentModel2.setTareweight(String.valueOf(data.getCompartment2()));
//                            compartmentList.add(labCompartmentModel2);
//                        }
//
//                        Lab_compartment_model labCompartmentModel3 =  parseCompartment(data.getProcompartment3());
//                        if (labCompartmentModel3 != null) {
//                            labCompartmentModel3.setTareweight(String.valueOf(data.getCompartment3()));
//                            compartmentList.add(labCompartmentModel3);
//                        }
//
//                        Lab_compartment_model labCompartmentModel4 =  parseCompartment(data.getProcompartment4());
//                        if (labCompartmentModel4 != null) {
//                            labCompartmentModel4.setTareweight(String.valueOf(data.getCompartment4()));
//                            compartmentList.add(labCompartmentModel4);
//                        }
//
//                        Lab_compartment_model labCompartmentModel5 =  parseCompartment(data.getProcompartment5());
//                        if (labCompartmentModel5 != null) {
//                            labCompartmentModel5.setTareweight(String.valueOf(data.getCompartment5()));
//                            compartmentList.add(labCompartmentModel5);
//                        }
//
//                        Lab_compartment_model labCompartmentModel6 =  parseCompartment(data.getProcompartment6());
//                        if (labCompartmentModel6 != null) {
//                            labCompartmentModel6.setTareweight(String.valueOf(data.getCompartment6()));
//                            compartmentList.add(labCompartmentModel6);
//                        }
//                        //Weighment_compartment_Adapter adapter = new Weighment_compartment_Adapter(this, lstlabcompmodel);
//                        //recyclerView.setAdapter(adapter);
//                        adapter = new Weighment_compartment_Adapter(OutwardOut_Tanker_Weighment.this, compartmentList);
//                        recyclerView.setAdapter(adapter);
//                        recyclerView.setLayoutManager(new LinearLayoutManager(OutwardOut_Tanker_Weighment.this));
//                        adapter.notifyDataSetChanged();

                        List<String> compartmentsJson = Arrays.asList(
                                data.getCompartment1(),
                                data.getCompartment2(),
                                data.getCompartment3(),
                                data.getCompartment4(),
                                data.getCompartment5(),
                                data.getCompartment6()
                        );
                        List<String> procompartmentsJson = Arrays.asList(
                                data.getProcompartment1(),
                                data.getProcompartment2(),
                                data.getProcompartment3(),
                                data.getProcompartment4(),
                                data.getProcompartment5(),
                                data.getProcompartment6()
                        );
                        for (String json : compartmentsJson) {
                            Lab_compartment_model labCompartmentModel = parseCompartment(json);
                            if (labCompartmentModel != null) {
                                compartmentList.add(labCompartmentModel);
                                adapter.notifyDataSetChanged();
                                // 🔹 Show Update or Submit button based on compartment data
                            }
                        }
                        // If Cluase to get Latest Compartment Data for Weight Verification
                        if(compartmentList.size() < procompartmentsJson.size()) {
                            Lab_compartment_model labCompartmentModel1 = parseCompartment(procompartmentsJson.get(compartmentList.size()));
                            compartmentList.add(labCompartmentModel1);
                            adapter.notifyDataSetChanged();
                        }
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
        LinearLayout linearLayout = findViewById(R.id.layout_productlistitoutweighment); // Ensure this is the correct ID

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

    public void makeNotification(String vehicleNumber) {
        Call<List<ResponseModel>> call = userDetails.getUsersListData();
        call.enqueue(new Callback<List<ResponseModel>>() {
            @Override
            public void onResponse(Call<List<ResponseModel>> call, Response<List<ResponseModel>> response) {
                if (response.isSuccessful()){
                    List<ResponseModel> userList = response.body();
                    if (userList != null){
                        for (ResponseModel resmodel : userList){
                            String specificRole = "DataEntry";
                            if (specificRole.equals(resmodel.getDepartment())) {
                                token = resmodel.getToken();

                                FcmNotificationsSender notificationsSender = new FcmNotificationsSender(
                                        token,
                                        "Outward Tanker OutWeighment Process Done..!",
                                        "Vehicle Number:-" + vehicleNumber + " has completed OutWeighment Process",
                                        getApplicationContext(),
                                        OutwardOut_Tanker_Weighment.this
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

        // Convert Compartment objects to JSON strings
        String compartment1String = (compartmentList.size() > 0) ? convertCompartmentToJson(compartmentList.get(0)) : "";
        String compartment2String = (compartmentList.size() > 1) ? convertCompartmentToJson(compartmentList.get(1)) : "";
        String compartment3String = (compartmentList.size() > 2) ? convertCompartmentToJson(compartmentList.get(2)) : "";
        String compartment4String = (compartmentList.size() > 3) ? convertCompartmentToJson(compartmentList.get(3)) : "";
        String compartment5String = (compartmentList.size() > 4) ? convertCompartmentToJson(compartmentList.get(4)) : "";
        String compartment6String = (compartmentList.size() > 5) ? convertCompartmentToJson(compartmentList.get(5)) : "";

        // Log the compartment strings to check their format
        Log.d("Compartment JSON", compartment1String);
        Log.d("Compartment JSON", compartment2String);
        Log.d("Compartment JSON", compartment3String);
        Log.d("Compartment JSON", compartment4String);
        Log.d("Compartment JSON", compartment5String);
        Log.d("Compartment JSON", compartment6String);


        if (etintime.isEmpty()||etsealnumber.isEmpty()||etnetweight.isEmpty()||ugrosswt.isEmpty()
                ||uremark.isEmpty()||unumberpack.isEmpty()) {
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
        }else {
            out_weighment_model modelOutwardOutWeighment = new out_weighment_model(OutwardId,imgPath2,imgPath1,
                    etintime,etnetweight,ugrosswt,unumberpack,uremark,etsealnumber,EmployeId,'P',inOut,vehicleType,0,0,
                    compartment1String,compartment2String,compartment3String,compartment4String,compartment5String,compartment6String);
            Call<Boolean> call = outwardWeighment.updateoutwardoutweighment_outTanker(modelOutwardOutWeighment);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body() && response.body() == true){
                        deleteLocalImage(vehicleNum);
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
                ImageUtils.deleteImage(this,imgpath);
            }
            makeNotification(vehicleNum);
            Toasty.success(OutwardOut_Tanker_Weighment.this, "Data Inserted Successfully", Toast.LENGTH_SHORT, true).show();
            startActivity(new Intent(OutwardOut_Tanker_Weighment.this, Grid_Outward.class));
            finish();
        } catch (Exception e) {
            e.printStackTrace();
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
    private Lab_compartment_model parseCompartment(String jsonString) {
        if (jsonString == null || jsonString.isEmpty()) {
            Log.e("JSON_ERROR", "Empty JSON string");
            return null; // Handle empty data safely
        }
        try {
            Gson gson = new Gson();
            return gson.fromJson(jsonString.replace("/",""), Lab_compartment_model.class);
        } catch (Exception e) {
            Log.e("JSON_ERROR", "Error parsing JSON: " + e.getMessage());
            return null;
        }
    }
    private String convertCompartmentToJson(Lab_compartment_model compartment) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Blender", compartment.getBlenderNumber()); // Using only Blender
            jsonObject.put("ProductionSign", compartment.getProductionSign()); // Production Sign
            jsonObject.put("OperatorSign", compartment.getOperatorSign()); // Operator Sign
            jsonObject.put("TareWeight", compartment.getTareweight()); // Tare Weight")
            jsonObject.put("",compartment.getVerificationRemark());
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "{}"; // Return empty JSON if an error occurs
        }
    }
}