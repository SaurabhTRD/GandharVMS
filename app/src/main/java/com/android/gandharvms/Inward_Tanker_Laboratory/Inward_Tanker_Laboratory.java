package com.android.gandharvms.Inward_Tanker_Laboratory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.gandharvms.FcmNotificationsSender;
import com.android.gandharvms.Global_Var;
import com.android.gandharvms.InwardCompletedGrid.CommonResponseModelForAllDepartment;
import com.android.gandharvms.InwardCompletedGrid.GridCompleted;
import com.android.gandharvms.Inward_Tanker;
import com.android.gandharvms.Inward_Tanker_Sampling.Inward_Tanker_saampling_View_data;
import com.android.gandharvms.Inward_Tanker_Sampling.it_in_Samp_Completedgrid;
import com.android.gandharvms.Inward_Tanker_Security.In_Tanker_Security_list;
import com.android.gandharvms.Inward_Tanker_Security.Inward_Tanker_Security;
import com.android.gandharvms.Inward_Tanker_Security.grid;
import com.android.gandharvms.Inward_Tanker_Weighment.InTanWeighResponseModel;
import com.android.gandharvms.Inward_Tanker_Weighment.Inward_Tanker_Weighment;
import com.android.gandharvms.Inward_Tanker_Weighment.Inward_Tanker_Weighment_Viewdata;
import com.android.gandharvms.LoginWithAPI.Laboratory;
import com.android.gandharvms.LoginWithAPI.Login;
import com.android.gandharvms.LoginWithAPI.LoginMethod;
import com.android.gandharvms.LoginWithAPI.ResponseModel;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.Menu;
import com.android.gandharvms.R;
import com.android.gandharvms.submenu.submenu_Inward_Tanker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

import org.apache.poi.hpsf.Decimal;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class Inward_Tanker_Laboratory extends AppCompatActivity {

    final Calendar calendar = Calendar.getInstance();
    private final int MAX_LENGTH = 10;
    String[] remark = {"Accepted", "Rejected"};
    AutoCompleteTextView regAutoCompleteTextView;
    ArrayAdapter<String> remarkarray;
    String[] rcsTest = {"PASS","FAIL","NA"};
    ArrayAdapter<String> rcstest;
    String[] odor = {"ODORLESS","ODOR PASSOUT","NA"};
    ArrayAdapter<String> odorarray;
    EditText etintime, etserialnumber, etpsample, etvehiclenumber, etpapperance, etpodor, etpcolour, etpdensity,
            etqty, etPrcstest, etpkv, ethundred, etanline, etflash, etpaddtest, etpsamplere, etpremark, etpsignQc,
            etpdatesignofsign, etMaterial, etsupplier, remarkdisc, etviscosity,etfetchSecQty,etfetchlabqtyoum;
    Button etlabsub, updateclick;
    Button view, viewsamplereporting;
    TimePickerDialog tpicker;
    FirebaseFirestore dblabroot;
    DatePickerDialog picker, picker1, picker2;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
    Date currentDate = Calendar.getInstance().getTime();
    private final String dateTimeString = "";
    private String token;

    //Call Interface Method of Laboratory
    private Laboratory labdetails;
    private LoginMethod userDetails;
    private int inwardid;
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private final String EmployeId = Global_Var.getInstance().EmpId;
    String[] qtyuom = {"Ton", "Litre", "KL", "Kgs", "pcs", "NA"};
    Integer qtyUomNumericValue = 1;
    AutoCompleteTextView  autoCompleteTextView1,autoCompleteTextView2,autocompletTextView3;
    Map<String, Integer> qtyUomMapping = new HashMap<>();
    ArrayAdapter<String> qtyuomdrop;
    Map<String , Integer> RcsMapping = new HashMap<>(); // Create a mapping for RcsTest
    ArrayAdapter<String> RcsDrop;
    List<String> teamList = new ArrayList<>();
    private int qty;
    private int kv;
    private int hundred;
    private int anline;
    private int flash;
    ImageView btnlogout,btnhome;
    TextView username,empid;

    public static String Tanker;
    public static String Truck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inward_tanker_laboratory);

        //Send Notification to all
        FirebaseMessaging.getInstance().subscribeToTopic(token);

        labdetails = RetroApiClient.getLabDetails();//Call retrofit api
        userDetails = RetroApiClient.getLoginApi();

//        autoCompleteTextView1 = findViewById(R.id.fetchlabqtyuomtanker);
//        qtyUomMapping = new HashMap<>();
//        qtyUomMapping.put("NA", 1);
//        qtyUomMapping.put("Ton", 2);
//        qtyUomMapping.put("Litre", 3);
//        qtyUomMapping.put("KL", 4);
//        qtyUomMapping.put("Kgs", 5);
//        qtyUomMapping.put("pcs", 6);
//        qtyUomMapping.put("M3", 7);
//
//        qtyuomdrop = new ArrayAdapter<String>(this, R.layout.in_ta_se_qty, new ArrayList<>(qtyUomMapping.keySet()));
//        autoCompleteTextView1.setAdapter(qtyuomdrop);
//        autoCompleteTextView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String qtyUomDisplay = parent.getItemAtPosition(position).toString();
//                // Retrieve the corresponding numerical value from the mapping
//                qtyUomNumericValue = qtyUomMapping.get(qtyUomDisplay);
//                if (qtyUomNumericValue != null) {
//                    // Now, you can use qtyUomNumericValue when inserting into the database
//                    Toasty.success(Inward_Tanker_Laboratory.this, "QTYUnitofMeasurement : " + qtyUomDisplay + " Selected", Toast.LENGTH_SHORT).show();
//                } else {
//                    // Handle the case where the mapping doesn't contain the display value
//                    Toasty.error(Inward_Tanker_Laboratory.this, "Default QTYUnitofMeasurement : " + "NA" + " Selected", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        autoCompleteTextView2 = findViewById(R.id.etPrcstest);
        rcstest = new ArrayAdapter<String>(this,R.layout.in_rcs_test,rcsTest);
        autoCompleteTextView2.setAdapter(rcstest);
        autoCompleteTextView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String tests = parent.getItemAtPosition(position).toString();
                Toasty.success(getApplicationContext(), "RcsTest : " + tests + " Selected", Toast.LENGTH_SHORT).show();
            }
        });

        autocompletTextView3 = findViewById(R.id.etpodor);
        odorarray = new ArrayAdapter<String>(this,R.layout.odor_drop,odor);
        autocompletTextView3.setAdapter(odorarray);
        autocompletTextView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String items = parent.getItemAtPosition(position).toString();
                Toasty.success(getApplicationContext(), "Odor : " + items + " Selected", Toast.LENGTH_SHORT).show();
            }
        });



        regAutoCompleteTextView = findViewById(R.id.etpremark);
        remarkarray = new ArrayAdapter<String>(this, R.layout.in_tanker_labremarkitem, remark);
        regAutoCompleteTextView.setAdapter(remarkarray);
        regAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String items = parent.getItemAtPosition(position).toString();
                Toasty.success(getApplicationContext(), "Remark : " + items + " Selected", Toast.LENGTH_SHORT).show();
            }
        });

        etintime = (EditText) findViewById(R.id.etintime);
        etpsample = (EditText) findViewById(R.id.etpsample);
        etserialnumber = (EditText) findViewById(R.id.etlabserialnumber);
        etvehiclenumber = (EditText) findViewById(R.id.vehiclenumber);
        etpapperance = (EditText) findViewById(R.id.etpapperance);
        etpodor = (EditText) findViewById(R.id.etpodor);
        etpcolour = (EditText) findViewById(R.id.etpcolour);
//        etqty = (EditText) findViewById(R.id.qtycolor);
        etpdensity = (EditText) findViewById(R.id.etpdensity);
        etPrcstest = (EditText) findViewById(R.id.etPrcstest);
        etpkv = (EditText) findViewById(R.id.etpkv);
        ethundred = (EditText) findViewById(R.id.hundered);
        etanline = (EditText) findViewById(R.id.anline);
        etflash = (EditText) findViewById(R.id.flash);
        etpaddtest = (EditText) findViewById(R.id.etpaddtest);
        etpsamplere = (EditText) findViewById(R.id.etpsamplere);
        etpremark = (EditText) findViewById(R.id.etpremark);
        etpsignQc = (EditText) findViewById(R.id.etpsignQc);
        etpdatesignofsign = (EditText) findViewById(R.id.etpdatesignofsign);
        etMaterial = (EditText) findViewById(R.id.et_materialname);
        etfetchSecQty=(EditText)findViewById(R.id.etfetchlabqty);
        etfetchlabqtyoum = findViewById(R.id.fetchlabqtyuomtanker);

        etsupplier = (EditText) findViewById(R.id.supplier);
        remarkdisc = (EditText) findViewById(R.id.remarkdisc);
        etviscosity = (EditText) findViewById(R.id.etviscosityindex);
        etlabsub = (Button) findViewById(R.id.etlabsub);
        updateclick = (Button) findViewById(R.id.itlabupdateclick);

        viewsamplereporting = (Button) findViewById(R.id.btn_viewsampleReport);
/*
        viewweighmentreporting=(Button)findViewById(R.id.btn_clicktoViewWEIGHMENTREPORT);
*/
        /*view = findViewById(R.id.viewclick);*/
        /*view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Inward_Tanker_Laboratory.this, Inward_Tanker_Lab_Viewdata.class));
            }
        });*/

        btnlogout=findViewById(R.id.btn_logoutButton);
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
                startActivity(new Intent(Inward_Tanker_Laboratory.this, Login.class));
            }
        });
        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Inward_Tanker_Laboratory.this, Menu.class));
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


        etpsample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                // Array of month abbreviations
                String[] monthAbbreviations = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

                picker = new DatePickerDialog(Inward_Tanker_Laboratory.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Use the month abbreviation from the array
                        String monthAbbreviation = monthAbbreviations[month];
                        etpsample.setText(dayOfMonth + "/" + monthAbbreviation + "/" + year);
                    }
                }, year, month, day);
                picker.show();
            }
        });

        etpsamplere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                // Array of month abbreviations
                String[] monthAbbreviations = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

                picker = new DatePickerDialog(Inward_Tanker_Laboratory.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Use the month abbreviation from the array
                        String monthAbbreviation = monthAbbreviations[month];
                        etpsamplere.setText(dayOfMonth + "/" + monthAbbreviation + "/" + year);
                    }
                }, year, month, day);
                picker.show();
            }
        });
        etpdatesignofsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                // Array of month abbreviations
                String[] monthAbbreviations = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

                picker = new DatePickerDialog(Inward_Tanker_Laboratory.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Use the month abbreviation from the array
                        String monthAbbreviation = monthAbbreviations[month];
                        etpdatesignofsign.setText(dayOfMonth + "/" + monthAbbreviation + "/" + year);
                    }
                }, year, month, day);
                picker.show();
            }
        });

        dblabroot = FirebaseFirestore.getInstance();

        etvehiclenumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    FetchVehicleDetails(etvehiclenumber.getText().toString().trim(), vehicleType, nextProcess, inOut);
                }
            }
        });

        etlabsub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                labinsertdata();
            }
        });

        updateclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updlabbyinwardid();
            }
        });

        if (getIntent().hasExtra("VehicleNumber")) {
            String action = getIntent().getStringExtra("Action");
            if (action != null && action.equals("Up")) {
                FetchVehicleDetailsforUpdate(getIntent().getStringExtra("VehicleNumber"), Global_Var.getInstance().MenuType, 'x', 'I');
            } else {
                FetchVehicleDetails(getIntent().getStringExtra("VehicleNumber"), Global_Var.getInstance().MenuType, nextProcess, inOut);
            }
        }
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
                                // Adjust the notification sender based on your new token and data structure
                                FcmNotificationsSender notificationsSender = new FcmNotificationsSender(
                                        token,
                                        "Inward Tanker Laboratory Process Done..!",
                                        "Vehicle Number:-" + vehicleNumber + " has completed Laboratory process at " + outTime,
                                        getApplicationContext(),
                                        Inward_Tanker_Laboratory.this
                                );
                                notificationsSender.triggerSendNotification();
                            }
                        }
                    }
                } else {
                    // Handle unsuccessful API response
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
                Toasty.error(Inward_Tanker_Laboratory.this, "failed..!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void btn_clicktoViewSAMPLEREPORT(View view) {
        Global_Var.getInstance().DeptType = 'M';
        Intent intent = new Intent(this, it_in_Samp_Completedgrid.class);
        intent.putExtra("vehiclenumber", etvehiclenumber.getText());
        view.getContext().startActivity(intent);
    }

    public void Labviewclick(View view) {
        Intent intent = new Intent(this, grid.class);
        startActivity(intent);
    }

    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(new Date());
    }

    public void labinsertdata() {
        String intime = etintime.getText().toString().trim();
        String serialNumber = etserialnumber.getText().toString().trim();
        String date = etpsample.getText().toString().trim();
        String vehicle = etvehiclenumber.getText().toString().trim();
        String apperance = etpapperance.getText().toString().trim();
        String odor = etpodor.getText().toString().trim();
        String color = etpcolour.getText().toString().trim();
        //int qty = Integer.parseInt(etqty.getText().toString().trim());
//        if (!etqty.getText().toString().isEmpty()) {
//            try {
//                String input = etqty.getText().toString().trim();
//                int integerValue;
//
//                if (input.contains(".")) {
//                    // Input contains a decimal point
//                    String[] parts = input.split("\\.");
//                    int wholeNumberPart = Integer.parseInt(parts[0]);
//                    int decimalPart = Integer.parseInt(parts[1]);
//                    // Adjust decimal part to two digits
//                    if (parts[1].length() > 2) {
//                        // Take only first two digits after decimal point
//                        decimalPart = Integer.parseInt(parts[1].substring(0, 2));
//                    }
//                    // Combine integer and decimal parts
//                    integerValue = wholeNumberPart * 100 + decimalPart;
//                } else {
//                    // Input is a whole number
//                    integerValue = Integer.parseInt(input) * 100;
//                }
//                qty = integerValue;
//            } catch (NumberFormatException e) {
//                e.printStackTrace();
//            }
//        }

        String rcsTest = etPrcstest.getText().toString().trim();
        //int kv = Integer.parseInt(etpkv.getText().toString().trim());
        if (!etpkv.getText().toString().isEmpty()) {
            try {
                String input = etpkv.getText().toString().trim();
                int integerValue;

                if (input.contains(".")) {
                    // Input contains a decimal point
                    String[] parts = input.split("\\.");
                    int wholeNumberPart = Integer.parseInt(parts[0]);
                    int decimalPart = Integer.parseInt(parts[1]);
                    // Adjust decimal part to two digits
                    if (parts[1].length() > 2) {
                        // Take only first two digits after decimal point
                        decimalPart = Integer.parseInt(parts[1].substring(0, 2));
                    }
                    // Combine integer and decimal parts
                    integerValue = wholeNumberPart * 100 + decimalPart;
                } else {
                    // Input is a whole number
                    integerValue = Integer.parseInt(input) * 100;
                }
                kv = integerValue;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        //int hundred = Integer.parseInt(ethundred.getText().toString().trim());
        if (!ethundred.getText().toString().isEmpty()) {
            try {
                String input = ethundred.getText().toString().trim();
                int integerValue;

                if (input.contains(".")) {
                    // Input contains a decimal point
                    String[] parts = input.split("\\.");
                    int wholeNumberPart = Integer.parseInt(parts[0]);
                    int decimalPart = Integer.parseInt(parts[1]);
                    // Adjust decimal part to two digits
                    if (parts[1].length() > 2) {
                        // Take only first two digits after decimal point
                        decimalPart = Integer.parseInt(parts[1].substring(0, 2));
                    }
                    // Combine integer and decimal parts
                    integerValue = wholeNumberPart * 100 + decimalPart;
                } else {
                    // Input is a whole number
                    integerValue = Integer.parseInt(input) * 100;
                }
                hundred = integerValue;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        //int anline = Integer.parseInt(etanline.getText().toString().trim());
        if (!etanline.getText().toString().isEmpty()) {
            try {
                String input = etanline.getText().toString().trim();
                int integerValue;

                if (input.contains(".")) {
                    // Input contains a decimal point
                    String[] parts = input.split("\\.");
                    int wholeNumberPart = Integer.parseInt(parts[0]);
                    int decimalPart = Integer.parseInt(parts[1]);
                    // Adjust decimal part to two digits
                    if (parts[1].length() > 2) {
                        // Take only first two digits after decimal point
                        decimalPart = Integer.parseInt(parts[1].substring(0, 2));
                    }
                    // Combine integer and decimal parts
                    integerValue = wholeNumberPart * 100 + decimalPart;
                } else {
                    // Input is a whole number
                    integerValue = Integer.parseInt(input) * 100;
                }
                anline = integerValue;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        //int flash = Integer.parseInt(etflash.getText().toString().trim());
        if (!etflash.getText().toString().isEmpty()) {
            try {
                String input = etflash.getText().toString().trim();
                int integerValue;

                if (input.contains(".")) {
                    // Input contains a decimal point
                    String[] parts = input.split("\\.");
                    int wholeNumberPart = Integer.parseInt(parts[0]);
                    int decimalPart = Integer.parseInt(parts[1]);
                    // Adjust decimal part to two digits
                    if (parts[1].length() > 2) {
                        // Take only first two digits after decimal point
                        decimalPart = Integer.parseInt(parts[1].substring(0, 2));
                    }
                    // Combine integer and decimal parts
                    integerValue = wholeNumberPart * 100 + decimalPart;
                } else {
                    // Input is a whole number
                    integerValue = Integer.parseInt(input) * 100;
                }
                flash = integerValue;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        String densityinput = etpdensity.getText().toString().trim();
        String addTest = etpaddtest.getText().toString().trim();
        String samplereceivingdate = etpsamplere.getText().toString().trim();
        String remark = etpremark.getText().toString().trim();
        String signQc = etpsignQc.getText().toString().trim();
        String dateSignOfSign = etpdatesignofsign.getText().toString().trim();
        String outTime = getCurrentTime();//Insert out Time Directly to the Database
        String material = etMaterial.getText().toString().trim();
        String edsupplier = etsupplier.getText().toString().trim();
        String viscosity = etviscosity.getText().toString().trim();
        String disc = remarkdisc.getText().toString().trim();
        if (intime.isEmpty() || serialNumber.isEmpty() || remark.isEmpty() || date.isEmpty() ||
                hundred < 0 || vehicle.isEmpty() || apperance.isEmpty() || odor.isEmpty() ||
                color.isEmpty() || qty < 0 || anline < 0 || flash < 0 || densityinput.isEmpty() ||
                rcsTest.isEmpty() || kv < 0 || disc.isEmpty() || addTest.isEmpty() ||
                samplereceivingdate.isEmpty() || viscosity.isEmpty() ||
                signQc.isEmpty() || dateSignOfSign.isEmpty() || material.isEmpty() || edsupplier.isEmpty()) {
            Toasty.warning(this, "All fields must be filled", Toast.LENGTH_SHORT, true).show();
        } else {
            BigDecimal density = new BigDecimal(densityinput);
            InTanLabRequestModel labRequestModel = new InTanLabRequestModel(inwardid, intime, outTime, date,
                    samplereceivingdate, apperance, odor, color, 0,  density,
                    rcsTest, kv, hundred, anline,
                    flash, addTest, samplereceivingdate, remark, signQc, dateSignOfSign,
                    disc, Integer.parseInt(viscosity), EmployeId, EmployeId, vehicle, material,
                    serialNumber, 'P', inOut, vehicleType, edsupplier);
            Call<Boolean> call = labdetails.insertLabData(labRequestModel);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body() != null && response.body()==true) {
                        makeNotification(vehicle, outTime);
                        Log.d("Registration", "Response Body: " + response.body());
                        Toasty.success(Inward_Tanker_Laboratory.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Inward_Tanker_Laboratory.this, Inward_Tanker.class));
                        finish();
                    } else {
                        Toasty.error(Inward_Tanker_Laboratory.this, "Data Insertion Failed..!", Toast.LENGTH_SHORT).show();
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
                    Toasty.error(Inward_Tanker_Laboratory.this, "failed..!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void FetchVehicleDetails(@NonNull String vehicleNo, String vehicleType, char NextProcess, char inOut) {
        Call<InTanLabResponseModel> call = labdetails.getLabbyfetchVehData(vehicleNo, vehicleType, NextProcess, inOut);
        call.enqueue(new Callback<InTanLabResponseModel>() {
            @Override
            public void onResponse(Call<InTanLabResponseModel> call, Response<InTanLabResponseModel> response) {
                if (response.isSuccessful()) {
                    InTanLabResponseModel data = response.body();
                    if (data.getVehicleNo() != "" && data.getVehicleNo() != null) {
                        inwardid = data.getInwardId();
                        etvehiclenumber.setText(data.getVehicleNo());
                        etvehiclenumber.setEnabled(false);
                        etserialnumber.setText(data.getSerialNo());
                        etserialnumber.setEnabled(false);
                        etsupplier.setText(data.getPartyName());
                        etsupplier.setEnabled(false);
                        etMaterial.setText(data.getMaterial());
                        etMaterial.setEnabled(false);
                        etpsample.setText(data.getDate());
                        etpsample.setEnabled(false);
                        etfetchSecQty.setText(String.valueOf(data.getSecNetWeight()));
                        etfetchSecQty.setEnabled(false);
                        etfetchlabqtyoum.setText(data.getUnitOfNetWeight());
                        etfetchlabqtyoum.setEnabled(false);
                        viewsamplereporting.setVisibility(View.VISIBLE);
                    } else {
                        Toasty.error(Inward_Tanker_Laboratory.this, "This Vehicle Is Not Available..!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("Retrofit", "Error Response Body: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<InTanLabResponseModel> call, Throwable t) {
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
                Toasty.error(Inward_Tanker_Laboratory.this, "failed..!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void FetchVehicleDetailsforUpdate(@NonNull String vehicleNo, String vehicleType, char NextProcess, char inOut) {
        Call<InTanLabResponseModel> call = labdetails.getLabbyfetchVehData(vehicleNo, vehicleType, NextProcess, inOut);
        call.enqueue(new Callback<InTanLabResponseModel>() {
            @Override
            public void onResponse(Call<InTanLabResponseModel> call, Response<InTanLabResponseModel> response) {
                if (response.isSuccessful()) {
                    InTanLabResponseModel data = response.body();
                    if (data.getVehicleNo() != "" && data.getVehicleNo() != null) {
                        inwardid = data.getInwardId();
                        etvehiclenumber.setText(data.getVehicleNo());
                        etvehiclenumber.setEnabled(false);
                        etserialnumber.setText(data.getSerialNo());
                        etserialnumber.setEnabled(false);
                        etsupplier.setText(data.getPartyName());
                        etsupplier.setEnabled(false);
                        etMaterial.setText(data.getMaterial());
                        etMaterial.setEnabled(false);
                        etpsample.setText(data.getDate());
                        etpsample.setEnabled(false);
                        etintime.setEnabled(false);
                        etpapperance.setText(data.getApperance());
                        etpapperance.setEnabled(true);
                        etpodor.setText(data.getOdor());
                        etpodor.setEnabled(true);
                        etpcolour.setText(data.getColor());
                        etpcolour.setEnabled(true);
                        String lqty = String.format("%.2f", data.getLQty() / 100.0);
                        String anlinepoint = String.format("%.2f", data.getAnLinePoint() / 100.0);
                        String flashpoint = String.format("%.2f", data.getFlashPoint() / 100.0);
                        String kv40 = String.format("%.2f", data.get_40KV() / 100.0);
                        String kv100 = String.format("%.2f", data.get_100KV() / 100.0);
                        etqty.setText(lqty);
                        etqty.setEnabled(true);
                        etpdensity.setText((CharSequence) data.getDensity());
                        etpdensity.setEnabled(true);
                        etPrcstest.setText(data.getRcsTest());
                        etPrcstest.setEnabled(true);
                        etpkv.setText(kv40);
                        etpkv.setEnabled(true);
                        ethundred.setText(kv100);
                        ethundred.setEnabled(true);
                        etanline.setText(anlinepoint);
                        etanline.setEnabled(true);
                        etflash.setText(flashpoint);
                        etflash.setEnabled(true);
                        etpaddtest.setText(data.getAdditionalTest());
                        etpaddtest.setEnabled(true);
                        etpdatesignofsign.setText(data.getDateAndTime());
                        etpdatesignofsign.setEnabled(true);
                        remarkdisc.setText(data.getRemarkDescription());
                        remarkdisc.setEnabled(true);
                        etviscosity.setText(String.valueOf(data.getViscosityIndex()));
                        etviscosity.setEnabled(true);
                        etpsignQc.setText(data.getSignOf());
                        etpsignQc.setEnabled(true);
                        etpsamplere.setText(data.getSampleTest());
                        etpsamplere.setEnabled(true);
                        etpremark.setText(data.getLabRemark());
                        etpremark.setEnabled(true);
                        viewsamplereporting.setVisibility(View.VISIBLE);
                        etlabsub.setVisibility(View.GONE);
                        updateclick.setVisibility(View.VISIBLE);
                    } else {
                        Toasty.error(Inward_Tanker_Laboratory.this, "This Vehicle Number Is Out From Factory.\n You Can Not Update", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(Inward_Tanker_Laboratory.this, it_Lab_Completedgrid.class));
                    }
                } else {
                    Log.e("Retrofit", "Error Response Body: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<InTanLabResponseModel> call, Throwable t) {
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
                Toasty.error(Inward_Tanker_Laboratory.this, "failed..!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updlabbyinwardid() {
        String apperance = etpapperance.getText().toString().trim();
        String odor = etpodor.getText().toString().trim();
        String color = etpcolour.getText().toString().trim();
//        if (!etqty.getText().toString().isEmpty()) {
//            try {
//                String input = etqty.getText().toString().trim();
//                int integerValue;
//
//                if (input.contains(".")) {
//                    // Input contains a decimal point
//                    String[] parts = input.split("\\.");
//                    int wholeNumberPart = Integer.parseInt(parts[0]);
//                    int decimalPart = Integer.parseInt(parts[1]);
//                    // Adjust decimal part to two digits
//                    if (parts[1].length() > 2) {
//                        // Take only first two digits after decimal point
//                        decimalPart = Integer.parseInt(parts[1].substring(0, 2));
//                    }
//                    // Combine integer and decimal parts
//                    integerValue = wholeNumberPart * 100 + decimalPart;
//                } else {
//                    // Input is a whole number
//                    integerValue = Integer.parseInt(input) * 100;
//                }
//                qty = integerValue;
//            } catch (NumberFormatException e) {
//                e.printStackTrace();
//            }
//        }
        /*if (!etpdensity.getText().toString().isEmpty()) {
            try {
                String input = etpdensity.getText().toString().trim();
                int integerValue;

                if (input.contains(".")) {
                    // Input contains a decimal point
                    String[] parts = input.split("\\.");
                    int wholeNumberPart = Integer.parseInt(parts[0]);
                    int decimalPart = Integer.parseInt(parts[1]);
                    // Adjust decimal part to two digits
                    if (parts[1].length() > 2) {
                        // Take only first two digits after decimal point
                        decimalPart = Integer.parseInt(parts[1].substring(0, 2));
                    }
                    // Combine integer and decimal parts
                    integerValue = wholeNumberPart * 100 + decimalPart;
                } else {
                    // Input is a whole number
                    integerValue = Integer.parseInt(input) * 100;
                }
                density = integerValue;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }*/
        String rcsTest = etPrcstest.getText().toString().trim();
        if (!etpkv.getText().toString().isEmpty()) {
            try {
                String input = etpkv.getText().toString().trim();
                int integerValue;

                if (input.contains(".")) {
                    // Input contains a decimal point
                    String[] parts = input.split("\\.");
                    int wholeNumberPart = Integer.parseInt(parts[0]);
                    int decimalPart = Integer.parseInt(parts[1]);
                    // Adjust decimal part to two digits
                    if (parts[1].length() > 2) {
                        // Take only first two digits after decimal point
                        decimalPart = Integer.parseInt(parts[1].substring(0, 2));
                    }
                    // Combine integer and decimal parts
                    integerValue = wholeNumberPart * 100 + decimalPart;
                } else {
                    // Input is a whole number
                    integerValue = Integer.parseInt(input) * 100;
                }
                kv = integerValue;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        if (!ethundred.getText().toString().isEmpty()) {
            try {
                String input = ethundred.getText().toString().trim();
                int integerValue;

                if (input.contains(".")) {
                    // Input contains a decimal point
                    String[] parts = input.split("\\.");
                    int wholeNumberPart = Integer.parseInt(parts[0]);
                    int decimalPart = Integer.parseInt(parts[1]);
                    // Adjust decimal part to two digits
                    if (parts[1].length() > 2) {
                        // Take only first two digits after decimal point
                        decimalPart = Integer.parseInt(parts[1].substring(0, 2));
                    }
                    // Combine integer and decimal parts
                    integerValue = wholeNumberPart * 100 + decimalPart;
                } else {
                    // Input is a whole number
                    integerValue = Integer.parseInt(input) * 100;
                }
                hundred = integerValue;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        if (!etanline.getText().toString().isEmpty()) {
            try {
                String input = etanline.getText().toString().trim();
                int integerValue;

                if (input.contains(".")) {
                    // Input contains a decimal point
                    String[] parts = input.split("\\.");
                    int wholeNumberPart = Integer.parseInt(parts[0]);
                    int decimalPart = Integer.parseInt(parts[1]);
                    // Adjust decimal part to two digits
                    if (parts[1].length() > 2) {
                        // Take only first two digits after decimal point
                        decimalPart = Integer.parseInt(parts[1].substring(0, 2));
                    }
                    // Combine integer and decimal parts
                    integerValue = wholeNumberPart * 100 + decimalPart;
                } else {
                    // Input is a whole number
                    integerValue = Integer.parseInt(input) * 100;
                }
                anline = integerValue;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        if (!etflash.getText().toString().isEmpty()) {
            try {
                String input = etflash.getText().toString().trim();
                int integerValue;

                if (input.contains(".")) {
                    // Input contains a decimal point
                    String[] parts = input.split("\\.");
                    int wholeNumberPart = Integer.parseInt(parts[0]);
                    int decimalPart = Integer.parseInt(parts[1]);
                    // Adjust decimal part to two digits
                    if (parts[1].length() > 2) {
                        // Take only first two digits after decimal point
                        decimalPart = Integer.parseInt(parts[1].substring(0, 2));
                    }
                    // Combine integer and decimal parts
                    integerValue = wholeNumberPart * 100 + decimalPart;
                } else {
                    // Input is a whole number
                    integerValue = Integer.parseInt(input) * 100;
                }
                flash = integerValue;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        String densityinput = etpdensity.getText().toString().trim();
        String addTest = etpaddtest.getText().toString().trim();
        String samplereceivingdate = etpsamplere.getText().toString().trim();
        String remark = etpremark.getText().toString().trim();
        String signQc = etpsignQc.getText().toString().trim();
        String dateSignOfSign = etpdatesignofsign.getText().toString().trim();
        String viscosity = etviscosity.getText().toString().trim();
        String disc = remarkdisc.getText().toString().trim();
        BigDecimal density = new BigDecimal(densityinput);

        it_lab_UpdateByInwardid_request_Model updlabbyinwardid = new it_lab_UpdateByInwardid_request_Model(inwardid,
                apperance, odor, color, qty, density, rcsTest, kv, hundred, anline,
                flash, addTest, samplereceivingdate, remark, signQc, dateSignOfSign,
                disc, Integer.parseInt(viscosity), EmployeId);
        Call<Boolean> call = labdetails.updateLabByInwardId(updlabbyinwardid);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful() && response.body() != null && response.body()==true) {
                    //makeNotification(vehicle, outTime);
                    Log.d("Registration", "Response Body: " + response.body());
                    Toasty.success(Inward_Tanker_Laboratory.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Inward_Tanker_Laboratory.this, Inward_Tanker.class));
                    finish();
                } else {
                    Toasty.error(Inward_Tanker_Laboratory.this, "Data Insertion Failed..!", Toast.LENGTH_SHORT).show();
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
                Toasty.error(Inward_Tanker_Laboratory.this, "failed..!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @SuppressLint("MissingSuperCall")
    public void onBackPressed() {
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
        finish();
    }

    public void vgrid(View view) {
        Intent intent = new Intent(this, in_tanker_lab_grid.class);
        startActivity(intent);
    }

    public void labviewclick(View view) {
        Intent intent = new Intent(this, it_Lab_Completedgrid.class);
        startActivity(intent);
    }

    /*private boolean isValidDensity(String input) {
        try {
            BigDecimal density = new BigDecimal(input);
            // Add any specific validation logic here (e.g., range check)
            if (density.compareTo(BigDecimal.ZERO) >= 0) { // Example range check
                return true;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return false;
    }*/
}