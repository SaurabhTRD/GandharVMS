package com.android.gandharvms.Outward_Tanker_Weighment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
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
import com.android.gandharvms.Outward_Tanker_Production_forms.Compartment;
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
import com.android.gandharvms.Util.dialogueprogreesbar;
import com.android.gandharvms.outward_Tanker_Lab_forms.LabCompartmentAdapter;
import com.android.gandharvms.outward_Tanker_Lab_forms.Lab_compartment_model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.common.reflect.TypeToken;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.squareup.picasso.Picasso;

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
    public LinearLayout layoutname, layoutimg;
    public String Netxtdept = "";
    public String despatchnext = "";
    public int compartmentArraycount, copartmentcount;
    private boolean isUpdateMode = false;
    private String outwardIdToUpdate;    // will store the id to send back

    EditText intime, serialnumber, vehiclenumber, materialname, custname, oanum, tareweight, tankernumber,
            etremark, transportername, howmuchqty, elocation, etbillremark, verifyremark, compartment1, compartment2, compartment3, compartment4, compartment5, compartment6,
            grossweight1, grossweight2, grossweight3, grossweight4, grossweight5, grossweight6, edgrosswt, edoneremark;
    Button submit, complted, verifybtn;
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
    char despatchNextChar = ' ';
    CardView cardone, cardtwo, cardthree, cardfour, cardfive, cardsix;
    LinearLayout mainContainer;
    dialogueprogreesbar dialogHelper = new dialogueprogreesbar();
    private int OutwardId;
    private Outward_weighment outwardWeighment;
    private String token;
    private LoginMethod userDetails;
    private String serialNo;
    private String imgPath1, imgPath2;
    private Outward_Truck_interface outwardTruckInterface;
    private List<Lab_compartment_model> compartmentList;
    private Weighment_compartment_Adapter adapter;
    private RecyclerView recyclerView;
    boolean isEditMode = false;

    int firstProCompartmentIndex;
    public List<String> compartmentsJson ;

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
        etbillremark = findViewById(R.id.etBillingRemark);
        layoutname = findViewById(R.id.imglayoutouttanername);
        layoutimg = findViewById(R.id.imglayoutouttanerimg);
        verifybtn = findViewById(R.id.outwardtankerbtnvarified);
        verifybtn.setVisibility(View.GONE);
        verifyremark = findViewById(R.id.etoutwardtankerweightvarifyremark);
        verifyremark.setVisibility(View.GONE);
        cardone = findViewById(R.id.compaone);
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

        submit = findViewById(R.id.etssubmit_outwardtanker);
        dbroot = FirebaseFirestore.getInstance();
        complted = findViewById(R.id.otinweighcompleted);
        mainContainer = findViewById(R.id.mainContainer);

        tankernumber.setVisibility(View.GONE);
        submit = findViewById(R.id.etssubmit_outwardtanker); // ensure this line exists

        String vehicleNo = getIntent().getStringExtra("vehiclenumber");
        String mode = getIntent().getStringExtra("mode"); // "update" or null

        if (vehicleNo != null && !vehicleNo.isEmpty()) {
            readWeighmentDataOutward(vehicleNo,vehicleType);
        }

//        String mode = getIntent().getStringExtra("mode"); // Get the "mode" value
//        if ("update".equals(mode)) {
//            submit.setText("Update");  // ✅ change text
//        }
//// Fetch record and pre-fill form
//        if (vehiclenumber.getText() != null) {
//            readWeighmentDataOutward(String.valueOf(vehiclenumber), mode);
//        }
        Intent in = getIntent();                       // comes from adapter
        if (in != null && "update".equals(in.getStringExtra("mode"))) {
            // We’re editing an existing record – flip the button text
            submit.setText("Update");               // or use R.string.update
            isUpdateMode = true;

        }
        // 2️⃣  Your UNCHANGED listener, with ONE tiny branch inside
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* --- NEW: decide which method to call --- */
                if (isUpdateMode) {          // ← true when you loaded a record to edit
                    weupdatedata();          //    call your update API
                }
                else
                {
                    if (image1 == null || image2 == null) {
                        Toasty.warning(Outward_Tanker_weighment.this,
                                "Please Upload Image", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    UploadImagesAndUpdate(); //    keep your original “submit” flow
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

        recyclerView = findViewById(R.id.recyclerView_labitem_weighment);
        compartmentList = new ArrayList<>();
        //List<Compartment> compartmentList = new ArrayList<>();
        // ✅ Pass `this` (Context) to Adapter
        adapter = new Weighment_compartment_Adapter(this, compartmentList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator()); // Smooth animations


    }

    private void readWeighmentDataOutward(String vehicleNo,String vehicleType) {
        Call<Response_Outward_Tanker_Weighment> call = outwardWeighment.fetchweighmentbyvehicleno(vehicleNo, vehicleType);

        call.enqueue(new Callback<Response_Outward_Tanker_Weighment>() {
            @Override
            public void onResponse(Call<Response_Outward_Tanker_Weighment> call, Response<Response_Outward_Tanker_Weighment> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Response_Outward_Tanker_Weighment data = response.body();
                    serialnumber.setFocusable(false);
                    serialnumber.setEnabled(false);
                    serialnumber.setClickable(false);
                    serialnumber.setCursorVisible(false);
                    serialnumber.setLongClickable(false);
                    serialnumber.setKeyListener(null);

//                    vehiclenumber.setFocusable(false);
//                    vehiclenumber.setEnabled(false);
//                    vehiclenumber.setClickable(false);
//                    vehiclenumber.setCursorVisible(false);
//                    vehiclenumber.setLongClickable(false);
//                    vehiclenumber.setKeyListener(null);

                    elocation.setFocusable(false);
                    elocation.setEnabled(false);
                    elocation.setClickable(false);
                    elocation.setCursorVisible(false);
                    elocation.setLongClickable(false);
                    elocation.setKeyListener(null);

                    intime.setFocusable(false);
                    intime.setEnabled(false);
                    intime.setClickable(false);
                    intime.setCursorVisible(false);
                    intime.setLongClickable(false);
                    intime.setKeyListener(null);


                    // Populate UI fields
                    OutwardId = data.getOutwardId();
                    serialnumber.setText(nonNull(data.SerialNumber));
                    vehiclenumber.setText(nonNull(data.VehicleNumber));
                    custname.setText(nonNull(data.CustomerName));
                    transportername.setText(nonNull(data.TransportName));
                    elocation.setText(nonNull(data.Location));
                    etremark.setText(nonNull(data.IRInWeiRemark));
//                    grossweight1.setText(nonNull(data.GrossWeight));
                    tareweight.setText(nonNull(data.TareWeight));
//                    oanum.setText(nonNull(data.OAnumber));
                    etbillremark.setText(nonNull(data.TankerBillingRemark));
                    if (data.OTWeiInTime != null && !data.OTWeiInTime.isEmpty())
                    {
                        intime.setText(nonNull(data.OTWeiInTime.substring(12,17)));
                    }

                    if (data.InVehicleImage != null && !data.InVehicleImage.isEmpty()) {
                        Picasso.get()
                                .load(RetroApiClient.BASE_URL + data.InVehicleImage)
                                .placeholder(R.drawable.gandhar)
                                .error(R.drawable.gandhar2)
                                .noFade().resize(120, 120)
                                .centerCrop()
                                .into(img1);

                    }

                    if (data.InDriverImage != null && !data.InDriverImage.isEmpty()) {
                        Picasso.get()
                                .load(RetroApiClient.BASE_URL + data.InDriverImage)
                                .placeholder(R.drawable.gandhar)
                                .noFade().resize(120, 120)
                                .into(img2);
                        }

                    if (data.InVehicleImage != null && !data.InVehicleImage.isEmpty()) {
                        imgPath1 = data.InVehicleImage;
                    }

                    if (data.InDriverImage != null && !data.InDriverImage.isEmpty()) {
                        imgPath2 = data.InDriverImage;
                    }

                }
                else {
                     Toast.makeText(Outward_Tanker_weighment.this, "No record found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Response_Outward_Tanker_Weighment> call, Throwable t) {
                Toast.makeText(Outward_Tanker_weighment.this, "Failed to fetch: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void weupdatedata() {
//        String FileInitial = "OutwardVeh_In_";
//        arrayOfByteArrays[0] = ImgVehicle;
//        arrayOfByteArrays[1] = ImgDriver;
//        imgPath1 = "GAimages/" + FileInitial + serialNo + ".jpeg";
//        for (byte[] byteArray : arrayOfByteArrays) {
//
//            MultipartTask multipartTask = new MultipartTask(byteArray, FileInitial + serialNo + ".jpeg", "");
//            multipartTask.execute();
//            FileInitial = "OutwardDrv_In_";
//        }
//        imgPath2 = "GAimages/" + FileInitial + serialNo + ".jpeg";
//        FileInitial = "";
        update();
    }

    // Utility to handle null strings safely
    private String nonNull(String value) {
        return value != null ? value : "";
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
                        boolean ismultiple = data.isCheck();
//                        tareweight.setEnabled(false);
                        String extraMaterialsJson = data.getProductQTYUOMOA();
                        Log.d("JSON Debug", "Extra Materials JSON: " + extraMaterialsJson);
                        List<ProductListData> extraMaterials = parseExtraMaterials(extraMaterialsJson);
                        Log.d("JSON Debug", "Parsed Extra Materials Size: " + extraMaterials.size());
                        createExtraMaterialViews(extraMaterials);
                        /*intime.requestFocus();
                        intime.callOnClick();*/
                        Netxtdept = data.getPurposeProcess();
                        String procompartment1 = data.getProcompartment1();

                        List<String> compartments = new ArrayList<>();

                        if (data.getProcompartment1() != null && !data.getProcompartment1().isEmpty())
                            compartments.add(data.getProcompartment1());
                        if (data.getProcompartment2() != null && !data.getProcompartment2().isEmpty())
                            compartments.add(data.getProcompartment2());
                        if (data.getProcompartment3() != null && !data.getProcompartment3().isEmpty())
                            compartments.add(data.getProcompartment3());
                        if (data.getProcompartment4() != null && !data.getProcompartment4().isEmpty())
                            compartments.add(data.getProcompartment4());
                        if (data.getProcompartment5() != null && !data.getProcompartment5().isEmpty())
                            compartments.add(data.getProcompartment5());
                        if (data.getProcompartment6() != null && !data.getProcompartment6().isEmpty())
                            compartments.add(data.getProcompartment6());

                        compartmentArraycount = compartments.size(); // ✅ Store count dynamically
                        Log.d("COMPARTMENT_COUNT", "Total Compartments: " + compartmentArraycount);

                         compartmentsJson = Arrays.asList(
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

                        compartmentList.clear(); // 🔄 Clear old data

                        firstProCompartmentIndex = -1;
                        for (int i = 0; i < procompartmentsJson.size(); i++) {
                            String proJson = procompartmentsJson.get(i);
                            if (proJson != null && !proJson.trim().isEmpty()) {
                                firstProCompartmentIndex = i;
                                break;
                            }
                        }

                        for (int i = 0; i < procompartmentsJson.size(); i++) {
                            String proJson = procompartmentsJson.get(i);
                            String labJson = (i < compartmentsJson.size()) ? compartmentsJson.get(i) : null;

                            if (proJson != null && !proJson.trim().isEmpty()) {
                                boolean shouldBind = false;

                                if (labJson == null || labJson.trim().isEmpty()) {
                                    shouldBind = true;  // ✅ Compartment not filled yet, bind for verification
                                }

                                if (shouldBind) {
                                    Lab_compartment_model model = parseCompartment(proJson);
                                    model.setTargetIndex(firstProCompartmentIndex);
                                    if (model != null) {
                                        model.setTargetIndex(i);
                                        model.setOriginalJson(proJson);
                                        compartmentList.add(model);
                                        Log.d("VERIFY_BIND", "✔ Added proCompartment at index " + i);
                                    } else {
                                        Log.w("VERIFY_BIND", "⚠ Failed to parse proCompartment at index " + i);
                                    }
                                }
                            }
                        }

                        adapter.notifyDataSetChanged();

                        if (compartmentArraycount > 0) {
                            verifybtn.setVisibility(View.VISIBLE);
                            tareweight.setVisibility(View.GONE);
                            etremark.setVisibility(View.GONE);
                            submit.setVisibility(View.GONE);
                            intime.setVisibility(View.GONE);
                            mainContainer.setVisibility(View.GONE);
                            Log.d("BUTTON_DEBUG", "Showing UPDATE button");
                        } else {
                            intime.setVisibility(View.VISIBLE);
                            tareweight.setVisibility(View.VISIBLE);
                            etremark.setVisibility(View.VISIBLE);
                            verifybtn.setVisibility(View.GONE);
                            submit.setVisibility(View.VISIBLE);
                            // mainContainer.setVisibility(View.GONE);
                            Log.d("BUTTON_DEBUG", "Showing SUBMIT button");
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

    private void FetchVehicleDetailsByVehicleNo(@NonNull String vehicleNo, String vehicleType) {
        Call<Response_Outward_Tanker_Weighment> call = outwardWeighment.fetchweighmentbyvehicleno(vehicleNo, vehicleType);
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
                        boolean ismultiple = data.isCheck();
//                        tareweight.setEnabled(false);
                        String extraMaterialsJson = data.getProductQTYUOMOA();
                        Log.d("JSON Debug", "Extra Materials JSON: " + extraMaterialsJson);
                        List<ProductListData> extraMaterials = parseExtraMaterials(extraMaterialsJson);
                        Log.d("JSON Debug", "Parsed Extra Materials Size: " + extraMaterials.size());
                        createExtraMaterialViews(extraMaterials);
                        /*intime.requestFocus();
                        intime.callOnClick();*/
                        Netxtdept = data.getPurposeProcess();
                        String procompartment1 = data.getProcompartment1();

                        List<String> compartments = new ArrayList<>();

                        if (data.getProcompartment1() != null && !data.getProcompartment1().isEmpty())
                            compartments.add(data.getProcompartment1());
                        if (data.getProcompartment2() != null && !data.getProcompartment2().isEmpty())
                            compartments.add(data.getProcompartment2());
                        if (data.getProcompartment3() != null && !data.getProcompartment3().isEmpty())
                            compartments.add(data.getProcompartment3());
                        if (data.getProcompartment4() != null && !data.getProcompartment4().isEmpty())
                            compartments.add(data.getProcompartment4());
                        if (data.getProcompartment5() != null && !data.getProcompartment5().isEmpty())
                            compartments.add(data.getProcompartment5());
                        if (data.getProcompartment6() != null && !data.getProcompartment6().isEmpty())
                            compartments.add(data.getProcompartment6());

                        compartmentArraycount = compartments.size(); // ✅ Store count dynamically
                        Log.d("COMPARTMENT_COUNT", "Total Compartments: " + compartmentArraycount);


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
                        if (ismultiple && compartmentList.size() < procompartmentsJson.size()) {
                            Lab_compartment_model labCompartmentModel1 = parseCompartment(procompartmentsJson.get(compartmentList.size()));
                            compartmentList.add(labCompartmentModel1);
                            adapter.notifyDataSetChanged();
                        }
                        if (compartmentArraycount > 0) {
                            verifybtn.setVisibility(View.VISIBLE);
                            tareweight.setVisibility(View.GONE);
                            etremark.setVisibility(View.GONE);
                            submit.setVisibility(View.GONE);
                            intime.setVisibility(View.GONE);
                            mainContainer.setVisibility(View.GONE);
                            Log.d("BUTTON_DEBUG", "Showing UPDATE button");
                        } else {
                            intime.setVisibility(View.VISIBLE);
                            tareweight.setVisibility(View.VISIBLE);
                            etremark.setVisibility(View.VISIBLE);
                            verifybtn.setVisibility(View.GONE);
                            submit.setVisibility(View.VISIBLE);
                            // mainContainer.setVisibility(View.GONE);
                            Log.d("BUTTON_DEBUG", "Showing SUBMIT button");
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

    private void FetchVehicleDetails1(@NonNull String vehicleNo, String vehicleType, char NextProcess, char inOut) {
        Call<Response_Outward_Tanker_Weighment> call = outwardWeighment.fetchweighment(vehicleNo, vehicleType, NextProcess, inOut);
        call.enqueue(new Callback<Response_Outward_Tanker_Weighment>() {
            @Override
            public void onResponse(Call<Response_Outward_Tanker_Weighment> call, Response<Response_Outward_Tanker_Weighment> response) {
                if (response.isSuccessful()) {
                    Response_Outward_Tanker_Weighment data = response.body();
                    if (data != null && data.getVehicleNumber() != null && !data.getVehicleNumber().isEmpty()) {

                        // ✅ Set Vehicle Details (Always)
                        OutwardId = data.getOutwardId();
                        serialNo = data.getSerialNumber();
                        serialnumber.setText(data.getSerialNumber());
                        serialnumber.setEnabled(false);
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

                        // ✅ Check if JSON Data is Available for Extra Materials
                        if (data.getProductQTYUOMOA() != null) {
                            String extraMaterialsJson = data.getProductQTYUOMOA();
                            List<ProductListData> extraMaterials = parseExtraMaterials(extraMaterialsJson);
                            createExtraMaterialViews(extraMaterials);
                        }

                        // ✅ Initialize Compartment Lists (with NULL CHECKS)
                        List<String> weighmentCompartments = Arrays.asList(
                                data.getCompartment1(), data.getCompartment2(), data.getCompartment3(),
                                data.getCompartment4(), data.getCompartment5(), data.getCompartment6()
                        );

                        List<String> productionCompartments = Arrays.asList(
                                data.getProcompartment1(), data.getProcompartment2(), data.getProcompartment3(),
                                data.getProcompartment4(), data.getProcompartment5(), data.getProcompartment6()
                        );

                        // ✅ Check if at least one valid compartment exists
                        boolean hasCompartmentData = false;
                        for (String comp : weighmentCompartments) {
                            if (comp != null && !comp.isEmpty()) {
                                hasCompartmentData = true;
                                break;
                            }
                        }

                        for (String proComp : productionCompartments) {
                            if (proComp != null && !proComp.isEmpty()) {
                                hasCompartmentData = true;
                                break;
                            }
                        }

                        // ✅ If there is valid compartment data, update the RecyclerView
                        if (hasCompartmentData) {
                            Log.d("DEBUG", "Compartment data found. Updating adapter...");
                            compartmentList.clear();

                            for (int i = 0; i < weighmentCompartments.size(); i++) {
                                String weighmentJson = weighmentCompartments.get(i);
                                String productionJson = productionCompartments.get(i);

                                if (weighmentJson != null && !weighmentJson.isEmpty()) {
                                    Lab_compartment_model labCompartmentModel = parseCompartment(weighmentJson);
                                    if (labCompartmentModel != null) {
                                        labCompartmentModel.setTareweight(weighmentJson);
                                        compartmentList.add(labCompartmentModel);
                                    }
                                }

                                if (productionJson != null && !productionJson.isEmpty() && compartmentList.size() < productionCompartments.size()) {
                                    Lab_compartment_model labCompartmentModel = parseCompartment(productionJson);
                                    if (labCompartmentModel != null) {
                                        compartmentList.add(labCompartmentModel);
                                    }
                                }
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            Log.d("DEBUG", "No compartment data found. Skipping adapter update.");
                        }
                        if (compartmentArraycount > 0) {
                            verifybtn.setVisibility(View.VISIBLE);
                            tareweight.setVisibility(View.GONE);
                            etremark.setVisibility(View.GONE);
                            submit.setVisibility(View.GONE);
                            intime.setVisibility(View.GONE);
                            mainContainer.setVisibility(View.GONE);
                            Log.d("BUTTON_DEBUG", "Showing UPDATE button");
                        } else {
                            intime.setVisibility(View.VISIBLE);
                            tareweight.setVisibility(View.VISIBLE);
                            etremark.setVisibility(View.VISIBLE);
                            verifybtn.setVisibility(View.GONE);
                            submit.setVisibility(View.VISIBLE);
                            mainContainer.setVisibility(View.GONE);
                            Log.d("BUTTON_DEBUG", "Showing SUBMIT button");
                        }

//                        if (productionCompartments != null && !productionCompartments.isEmpty()){
//                            edgrosswt.setVisibility(View.VISIBLE);
//                            tareweight.setVisibility(View.GONE);
//                            verifybtn.setVisibility(View.VISIBLE);
//                            submit.setVisibility(View.GONE);
//                        }else {
//                            edgrosswt.setVisibility(View.GONE);
//                            tareweight.setVisibility(View.VISIBLE);
//                            verifybtn.setVisibility(View.GONE);
//                            submit.setVisibility(View.VISIBLE);
//                            Log.d("DEBUG", "procompartment1 missing. Showing submit, hiding verifybtn.");
//                        }

                    } else {
                        Toasty.error(Outward_Tanker_weighment.this, "This Vehicle Number Is Not Available!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("Retrofit", "Error Response Body: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Response_Outward_Tanker_Weighment> call, Throwable t) {
                Log.e("Retrofit", "Failure: " + t.getMessage());
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
            dialogHelper.showConfirmationDialog(this, () -> {
                dialogHelper.showProgressDialog(this); // Show progress when confirmed
                Call<Boolean> call = outwardWeighment.updateweighmentoutwardtanker(responseOutwardTankerWeighment);
                call.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if (response.isSuccessful() && response.body() && response.body()) {
                            dialogHelper.hideProgressDialog(); // Hide after response
                            Log.d("Registration", "Response Body: " + response.body());
                            deleteLocalImage(etvehiclenumber, outTime);
                        } else {
                            Log.e("Retrofit", "Error Response Body: " + response.code());
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
                        Toasty.error(Outward_Tanker_weighment.this, "failed..!", Toast.LENGTH_SHORT).show();
                    }
                });
            });
        }
    }

    public void update() {
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
            dialogHelper.showConfirmationDialog(this, () -> {
                dialogHelper.showProgressDialog(this); // Show progress when confirmed
                Call<Boolean> call = outwardWeighment.updateweighmentoutwardtankerDetails(responseOutwardTankerWeighment);
                call.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if (response.isSuccessful() && response.body() && response.body()) {
                            dialogHelper.hideProgressDialog(); // Hide after response
                            Log.d("Registration", "Response Body: " + response.body());
                            deleteLocalImageAfterUpdate(etvehiclenumber, outTime);
                        } else {
                            Log.e("Retrofit", "Error Response Body: " + response.code());
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
                        Toasty.error(Outward_Tanker_weighment.this, "failed..!", Toast.LENGTH_SHORT).show();
                    }
                });
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

    private void deleteLocalImageAfterUpdate(String vehicalnumber, String outTime) {
        File imageFile;
        try {
            for (Uri imgpath : LocalImgPath) {
                if (imgpath != null) {
                    ImageUtils.deleteImage(this, imgpath);
                }
            }
            makeNotification(vehicalnumber, outTime);
            Toasty.success(Outward_Tanker_weighment.this, "Data Inserted Successfully", Toast.LENGTH_SHORT, true).show();
            startActivity(new Intent(Outward_Tanker_weighment.this, OT_Completed_Weighment.class));
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
        String outTime = getCurrentTime();
        String etvehiclenumber = vehiclenumber.getText().toString().trim();
        String verification_remark = "ok";

        // Use array for cleaner index-based assignment
        String[] compartmentJsonStrings = new String[6]; // default all null

        // Initialize all compartments as empty
        for (int i = 0; i < 6; i++) {
            compartmentJsonStrings[i] = "";
        }

        // Step 2: Fill from existing saved compartmentsJson if available
        for (int i = 0; i < 6; i++) {
            if (compartmentsJson != null && compartmentsJson.size() > i && compartmentsJson.get(i) != null && !compartmentsJson.get(i).isEmpty()) {
                compartmentJsonStrings[i] = compartmentsJson.get(i);
            }
        }

        // Step 3: Override specific indexes with newly inserted values from adapter
        for (Lab_compartment_model comp : compartmentList) {
            int index = comp.getTargetIndex(); // expected 0–5
            if (index >= 0 && index < 6) {
                compartmentJsonStrings[index] = convertCompartmentToJson(comp);
            }
        }



        // ✅ Log the updated compartment values
//        Log.d("Compartment JSON", compartment1String);
//        Log.d("Compartment JSON", compartment2String);
//        Log.d("Compartment JSON", compartment3String);
//        Log.d("Compartment JSON", compartment4String);
//        Log.d("Compartment JSON", compartment5String);
//        Log.d("Compartment JSON", compartment6String);

        if (verification_remark.isEmpty()) {
            Toasty.warning(this, "Please Enter Remark", Toast.LENGTH_SHORT).show();
        } else {
            Tanker_verification_model tankerVerificationModel = new Tanker_verification_model(
                    OutwardId, "Yes", 'P', inOut, vehicleType, EmployeId,
                    verification_remark,
                    compartmentJsonStrings[0],
                    compartmentJsonStrings[1],
                    compartmentJsonStrings[2],
                    compartmentJsonStrings[3],
                    compartmentJsonStrings[4],
                    compartmentJsonStrings[5]

//                    compartmentJsonStrings[0], compartmentJsonStrings[1], compartmentJsonStrings[2],
//                    compartmentJsonStrings[3], compartmentJsonStrings[4], compartmentJsonStrings[5]
            );

            Gson gson = new Gson();
            String jsonRequest = gson.toJson(tankerVerificationModel);
            Log.d("API_REQUEST", jsonRequest);

            Call<Boolean> call = outwardTruckInterface.Tanker_weighmentvarified(tankerVerificationModel);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body()) {
                        Log.d("API_RESPONSE", "Response: " + response.body());
                        Toasty.success(Outward_Tanker_weighment.this, "Data Inserted Successfully!", Toast.LENGTH_SHORT).show();
                        makeNotification(etvehiclenumber, outTime);
                        startActivity(new Intent(Outward_Tanker_weighment.this, Outward_Tanker.class));
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Log.e("Retrofit", "Failure: " + t.getMessage());
                    Toasty.error(Outward_Tanker_weighment.this, "Failed..!", Toast.LENGTH_SHORT).show();
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
//    private void handleCompartmentFields(Response_Outward_Tanker_Weighment data) {
//        setCompartmentField(compartment1, data.getCompartment1());
//        setCompartmentField(compartment2, data.getCompartment2());
//        setCompartmentField(compartment3, data.getCompartment3());
//        setCompartmentField(compartment4, data.getCompartment4());
//        setCompartmentField(compartment5, data.getCompartment5());
//        setCompartmentField(compartment6, data.getCompartment6());
//    }

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
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

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

    private Lab_compartment_model parseCompartment(String jsonString) {
        if (jsonString == null || jsonString.isEmpty()) {
            Log.e("JSON_ERROR", "Empty JSON string");
            return null; // Handle empty data safely
        }
        try {
            Gson gson = new Gson();
            return gson.fromJson(jsonString.replace("/", ""), Lab_compartment_model.class);
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
            jsonObject.put("VerificationRemark", compartment.getVerificationRemark());
            jsonObject.put("VerificationRemark", compartment.getVerificationRemark()); // Weight Remark")
//            jsonObject.put("ProductName", compartment.getProductName()); // ✅ Product Name
//            jsonObject.put("InTime", compartment.getInTime()); // ✅ In-Time
//            jsonObject.put("Blender", compartment.getBlenderNumber()); // ✅ Blender Number
//            jsonObject.put("ProductionSign", compartment.getProductionSign()); // ✅ Production Sign
//            jsonObject.put("OperatorSign", compartment.getOperatorSign()); // ✅ Operator Sign
//            jsonObject.put("Remark", compartment.getRemark()); // ✅ Remark
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "{}"; // Return empty JSON if an error occurs
        }
    }

    private String convertCompartmentToJsonfirst(First_compartment_model firstCompartmentModel) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("TareWeight", firstCompartmentModel.getTareweight()); // Tare Weight")
            jsonObject.put("WeightRemark", firstCompartmentModel.getWeighremark()); // Weight Remark")
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "{}"; // Return empty JSON if an error occurs
        }
    }

}