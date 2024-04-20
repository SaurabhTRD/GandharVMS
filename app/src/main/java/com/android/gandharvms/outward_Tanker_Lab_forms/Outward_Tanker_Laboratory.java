package com.android.gandharvms.outward_Tanker_Lab_forms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.gandharvms.FcmNotificationsSender;
import com.android.gandharvms.Global_Var;
import com.android.gandharvms.Inward_Tanker_Security.Inward_Tanker_Security;
import com.android.gandharvms.LoginWithAPI.LoginMethod;
import com.android.gandharvms.LoginWithAPI.ResponseModel;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.Outward_Tanker;
import com.android.gandharvms.Outward_Tanker_Production_forms.Outward_Tanker_Production;
import com.android.gandharvms.Outward_Tanker_Security.Grid_Outward;
import com.android.gandharvms.Outward_Tanker_Security.Outward_RetroApiclient;
import com.android.gandharvms.Outward_Tanker_Weighment.Outward_Tanker_weighment;
import com.android.gandharvms.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;
import java.text.SimpleDateFormat;
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

public class Outward_Tanker_Laboratory extends AppCompatActivity {

    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private final String EmployeId = Global_Var.getInstance().EmpId;
    public RadioButton etisblendingyes, etisblendingno, etisflushingyes, etisflushingno;
    public CheckBox cisblending, cisflushing;
    public LinearLayout etbelow;
    public char blendingstatus;
    public char flushingstatus;
    public int statuscount;
    public String labintime;
    String[] items = {"OK", "Not OK", "Correction"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItems;
    EditText intime, serialnum, vehiclnum, blendingratio, appreance, color, odor, kv40, density25, kv100, viscosity, tbn, anlinepoint,
            breakdownvoltage, ddf, watercontent, interfacialtension, flashpoint, pourpoint, rcstest, remark,
            approveqc, dt, samplecondition, samplerecivdt, samplereleasedate, correctionrequird, etflushpara
            ,restivity,infrared,cust,qty;
    TextInputLayout retil;
    CardView btnc;
    Button submit, sendbtn,completd;
    FirebaseFirestore dbroot;
    TimePickerDialog tpicker;
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat dtFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
    DatePickerDialog picker;
    private int OutwardId;
    private Outward_Tanker_Lab outwardTankerLab;
    private String token;
    private LoginMethod userDetails;
    private String labvehicl;
    public RadioGroup blendingyesno,flushingyesno;
    private int etkv40, etkv100,etviscosity,etanlinepoint,etbreakdown,etdensity25;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outward_tanker_laboratory);
        outwardTankerLab = Outward_RetroApiclient.outwardTankerLab();

        userDetails = RetroApiClient.getLoginApi();
        FirebaseMessaging.getInstance().subscribeToTopic(token);

        autoCompleteTextView = findViewById(R.id.etremark);
        adapterItems = new ArrayAdapter<String>(this, R.layout.dropdown_outward_securitytanker, items);
        autoCompleteTextView.setAdapter(adapterItems);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String items = parent.getItemAtPosition(position).toString();
                Toasty.success(getApplicationContext(), "Remark:- " + items + " Selected", Toast.LENGTH_SHORT).show();
            }
        });

        intime = findViewById(R.id.etintime);
        serialnum = findViewById(R.id.etserialnumber);
        vehiclnum = findViewById(R.id.etvehicleno);
//        blendingratio = findViewById(R.id.elblendingratio);
        appreance = findViewById(R.id.etapperance);
        color = findViewById(R.id.etcolor);
        odor = findViewById(R.id.etodor);
        density25 = findViewById(R.id.etdensity25);
        kv40 = findViewById(R.id.etviscosity40);
        kv100 = findViewById(R.id.etvisocity100);
        viscosity = findViewById(R.id.etviscosityindex);
        tbn = findViewById(R.id.ettabtan);
        anlinepoint = findViewById(R.id.etanlinepoint);
        breakdownvoltage = findViewById(R.id.etbreakvol);
        ddf = findViewById(R.id.etddf);
        watercontent = findViewById(R.id.etwatercontent);
        interfacialtension = findViewById(R.id.etinterfacial);
        flashpoint = findViewById(R.id.etflashpoint);
        pourpoint = findViewById(R.id.etpourpoint);
        rcstest = findViewById(R.id.etrcstest);
        remark = findViewById(R.id.etremark);
        sendbtn = findViewById(R.id.saveButton);
        samplecondition = findViewById(R.id.etsamplecondition);
        samplerecivdt = findViewById(R.id.etsampledt);
        samplereleasedate = findViewById(R.id.etsamplereleasedate);
        correctionrequird = findViewById(R.id.etcorrection);
        restivity=findViewById(R.id.etotinprocesslabrestivity);
        infrared=findViewById(R.id.etotinprocesslabInfrared);
        cust = findViewById(R.id.etotinprocesscustomer);
        qty = findViewById(R.id.etotinprocessqty);

        blendingyesno=findViewById(R.id.isblendingyesno);
        flushingyesno=findViewById(R.id.isflushingyesno);

        retil = findViewById(R.id.remarktil);
        btnc = findViewById(R.id.btncdview);
        etbelow = findViewById(R.id.belowflush);

        submit = findViewById(R.id.etssubmit);
        dbroot = FirebaseFirestore.getInstance();
        completd = findViewById(R.id.proclabcompletd);

        etisblendingyes = findViewById(R.id.outwaoutrb_blendingYes);
        etisblendingno = findViewById(R.id.outwaourb_blendingNo);
        etisflushingyes = findViewById(R.id.outwaoutrb_flushingYes);
        etisflushingno = findViewById(R.id.outwaourb_flushingNo);

        cisblending = findViewById(R.id.isblendinglab);
        cisflushing = findViewById(R.id.isflushinglab);
        etflushpara = findViewById(R.id.etflushingpara);

        cisflushing.setOnCheckedChangeListener((buttonView, isChecked) -> {
            checkbelowparam();
        });
        cisblending.setOnCheckedChangeListener((buttonView, isChecked) -> {
            checkbelowparam();
        });

        etbelow.setVisibility(View.GONE);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert();
            }
        });
        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (blendingstatus == 'x' || flushingstatus == 'x') {
                    updatefirst();
                } else {
                    updatesecond();
                }
            }
        });
        completd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Outward_Tanker_Laboratory.this, OT_Completed_inproc_laboratory.class));
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
        samplerecivdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                // Array of month abbreviations
                String[] monthAbbreviations = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
                picker = new DatePickerDialog(Outward_Tanker_Laboratory.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Use the month abbreviation from the array
                        String monthAbbreviation = monthAbbreviations[month];
                        // etdate.setText(dayOfMonth + "/" + monthAbbreviation + "/" + year);
                        samplerecivdt.setText(dtFormat.format(calendar.getTime()));
                    }
                }, year, month, day);
                picker.show();
            }
        });

        samplereleasedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                // Array of month abbreviations
                String[] monthAbbreviations = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
                picker = new DatePickerDialog(Outward_Tanker_Laboratory.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Use the month abbreviation from the array
                        String monthAbbreviation = monthAbbreviations[month];
                        // etdate.setText(dayOfMonth + "/" + monthAbbreviation + "/" + year);
                        samplereleasedate.setText(dtFormat.format(calendar.getTime()));
                    }
                }, year, month, day);
                picker.show();
            }
        });

        vehiclnum.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    FetchVehicleDetails(vehiclnum.getText().toString().trim(), vehicleType, nextProcess, inOut);
                }
            }
        });
    }

    private String getCurrentTime() {
        // Get the current time
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(new Date());
    }

    public void makeNotificationiblabcomp(String vehicleNumber) {
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
                                        "Outward Tanker Laboratory InProcessform Completed",
                                        "Vehicle Number:-" + vehicleNumber + " has Completed Laboratory InProcessForm",
                                        getApplicationContext(),
                                        Outward_Tanker_Laboratory.this
                                );
                                notificationsSender.SendNotifications();
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
                Toasty.error(Outward_Tanker_Laboratory.this, "failed..!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void makeNotificationforresend(String vehicleNumber) {
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
                                        "Outward Tanker Laboratory Required Blending and Flushing",
                                        "Vehicle Number:-" + vehicleNumber + " has Required Bleding and Flushing ",
                                        getApplicationContext(),
                                        Outward_Tanker_Laboratory.this
                                );
                                notificationsSender.SendNotifications();
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
                Toasty.error(Outward_Tanker_Laboratory.this, "failed..!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updatefirst() {
        String fintime = intime.getText().toString().trim();
        char blendingstatus = etisblendingyes.isChecked() ? 'Y' : 'N';
        char flushingstatus = etisflushingyes.isChecked() ? 'Y' : 'N';
        //String outTime = getCurrentTime();
        String labouttime="";
        if (fintime.isEmpty()) {
            Toasty.warning(this, "All fields must be filled", Toast.LENGTH_SHORT, true).show();
        } else {
            if(blendingstatus == 'Y' && flushingstatus == 'Y')
            {
                labouttime = getCurrentTime();
            }
            Blending_Flushing_Model blendingFlushingModel = new Blending_Flushing_Model(OutwardId, fintime, labouttime, flushingstatus,
                    blendingstatus, statuscount, 'I', inOut, vehicleType, EmployeId);
            Call<Boolean> call = outwardTankerLab.updateflushblend(blendingFlushingModel);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body() && response.body()==true) {
                        makeNotificationforresend(labvehicl);
                        Toasty.success(Outward_Tanker_Laboratory.this, "Blending And Flushing Send To Production for Verify", Toasty.LENGTH_LONG, true).show();
                        startActivity(new Intent(Outward_Tanker_Laboratory.this, Outward_Tanker.class));
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
                    Toasty.error(Outward_Tanker_Laboratory.this, "failed..!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void updatesecond() {
        char blendingstatus = etisblendingyes.isChecked() ? 'Y' : 'N';
        char flushingstatus = etisflushingyes.isChecked() ? 'Y' : 'N';
        String labouttime="";
        if(blendingstatus == 'Y' && flushingstatus == 'Y')
        {
            labouttime = getCurrentTime();
        }
        Blending_Flushing_Model blendingFlushingModel = new Blending_Flushing_Model(OutwardId, labintime, labouttime, flushingstatus,
                blendingstatus, statuscount, 'I', inOut, vehicleType, EmployeId);
        Call<Boolean> call = outwardTankerLab.updateflushblend(blendingFlushingModel);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful() && response.body() && response.body()==true) {
                    makeNotificationforresend(labvehicl);
                    Toasty.success(Outward_Tanker_Laboratory.this, "Data Inserted Successfully", Toasty.LENGTH_SHORT, true).show();
                    startActivity(new Intent(Outward_Tanker_Laboratory.this, Outward_Tanker.class));
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
                Toasty.error(Outward_Tanker_Laboratory.this, "failed..!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void FetchVehicleDetails(@NonNull String vehicleNo, String vehicleType, char nextProcess, char inOut) {
        Call<Lab_Model__Outward_Tanker> call = outwardTankerLab.fetchlab(vehicleNo, vehicleType, nextProcess, inOut);
        call.enqueue(new Callback<Lab_Model__Outward_Tanker>() {
            @Override
            public void onResponse(Call<Lab_Model__Outward_Tanker> call, Response<Lab_Model__Outward_Tanker> response) {
                if (response.isSuccessful()) {
                    Lab_Model__Outward_Tanker data = response.body();
                    if (data.getVehicleNumber() != null && data.getVehicleNumber() != "") {
                        OutwardId = data.getOutwardId();
                        statuscount = data.getStatusCount();
                        blendingstatus = data.getBlendingStatus();
                        flushingstatus = data.getFlushingStatus();
                        serialnum.setText(data.getSerialNumber());
                        labvehicl = data.getVehicleNumber();
                        labintime = data.getIPFLabInTime();
                        cust.setText(data.getCustomerName());
                        qty.setText(String.valueOf(data.getHowMuchQuantityFilled()));
                        vehiclnum.setText(data.getVehicleNumber());
                        serialnum.setEnabled(false);
                        vehiclnum.setEnabled(false);
                        cisblending.setChecked(data.IsBlendingReq);
                        cisflushing.setChecked(data.IsFlushingReq);
                        cisblending.setEnabled(false);
                        cisflushing.setEnabled(false);
                        etflushpara.setText(data.getFlushingNo());
                        etflushpara.setEnabled(false);
                        if(cisblending.isChecked()==true && blendingstatus=='Y')
                        {
                            etisblendingyes.setChecked(true);
                            etisblendingyes.setEnabled(false);
                            etisblendingno.setEnabled(false);
                        }else{
                            etisblendingyes.setChecked(false);
                        }
                        if(cisflushing.isChecked()==true && flushingstatus=='Y')
                        {
                            etisflushingyes.setChecked(true);
                            etisflushingyes.setEnabled(false);
                            etisflushingno.setEnabled(false);
                        }
                        else {
                            etisflushingyes.setChecked(false);
                        }
                        if (blendingstatus == 'Y' && flushingstatus == 'Y' && statuscount >= 0) {
                            intime.setVisibility(View.GONE);
                            etbelow.setVisibility(View.VISIBLE);
                            sendbtn.setVisibility(View.GONE);
                        } else {
                            if (statuscount > 0) {
                                intime.setVisibility(View.GONE);
                                sendbtn.setText("Resend");
                                Toasty.success(Outward_Tanker_Laboratory.this, "IsBlending or IsFlushing Should Be Required.Repeat The Task", Toasty.LENGTH_SHORT, true).show();
                            }
                            else{
                                /*intime.requestFocus();
                                intime.callOnClick();*/
                                sendbtn.setText("send");
                            }
                        }
                    } else {
                        Toasty.error(Outward_Tanker_Laboratory.this, "This Vehicle Number Is Not Available..!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("Retrofit", "Error Response Body: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Lab_Model__Outward_Tanker> call, Throwable t) {

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

    public void checkbelowparam() {
        if (cisflushing.isChecked() && cisblending.isChecked()) {
            if (cisflushing.isChecked()) {
                etflushpara.setVisibility(View.VISIBLE);
            } else {
                etflushpara.setVisibility(View.GONE);
            }

            sendbtn.setVisibility(View.VISIBLE);
            etbelow.setVisibility(View.GONE);
        } else if (cisflushing.isChecked() || cisblending.isChecked()) {

            if (cisflushing.isChecked()) {
                etflushpara.setVisibility(View.VISIBLE);
            } else {
                etflushpara.setVisibility(View.GONE);
            }
            sendbtn.setVisibility(View.VISIBLE);
            etbelow.setVisibility(View.GONE);
        } else if (!cisflushing.isChecked() && !cisblending.isChecked()) {
            // Hide the TextInputLayout and Button
            etflushpara.setVisibility(View.GONE);
            sendbtn.setVisibility(View.GONE);
//                clinerar.setVisibility(View.GONE);
            etbelow.setVisibility(View.VISIBLE);
        }
    }

    public void insert() {
        //String etintime = intime.getText().toString().trim();
        String outTime = getCurrentTime();
        String etremark = remark.getText().toString().trim();
        String etsamplecondition = samplecondition.getText().toString().trim();
        String etsamplereceving = samplerecivdt.getText().toString().trim();
        String etsamplerelease = samplereleasedate.getText().toString().trim();
        String etapperance = appreance.getText().toString().trim();
        String etodor = odor.getText().toString().trim();
        String etcolor = color.getText().toString().trim();
        if (!kv40.getText().toString().isEmpty()) {
            try {
                String input = kv40.getText().toString().trim();
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
                etkv40 = integerValue;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        } else {
            Toasty.warning(this, "KV40°C Cannot be Empty", Toast.LENGTH_SHORT).show();
        }
        if (!kv100.getText().toString().isEmpty()) {
            try {
                String input = kv100.getText().toString().trim();
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
                etkv100 = integerValue;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        } else {
            Toasty.warning(this, "KV100°C Cannot be Empty", Toast.LENGTH_SHORT).show();
        }
        if(!viscosity.getText().toString().isEmpty())
        {
            try {
                etviscosity=Integer.parseInt(viscosity.getText().toString().trim());
            }catch (NumberFormatException e){
                e.printStackTrace();
            }
        }
        else{
            Toasty.warning(this, "ViscosityIndex Cannot be Empty", Toast.LENGTH_SHORT).show();
        }
        String ettbn = tbn.getText().toString().trim();
        if(!anlinepoint.getText().toString().isEmpty())
        {
            try {
                etanlinepoint=Integer.parseInt(anlinepoint.getText().toString().trim());
            }catch (NumberFormatException e){
                e.printStackTrace();
            }
        }
        else{
            Toasty.warning(this, "AnlinePoint Cannot be Empty", Toast.LENGTH_SHORT).show();
        }
        if(!breakdownvoltage.getText().toString().isEmpty())
        {
            try {
                etbreakdown=Integer.parseInt(breakdownvoltage.getText().toString().trim());
            }catch (NumberFormatException e){
                e.printStackTrace();
            }
        }
        else{
            Toasty.warning(this, "BreakDownVoltage Cannot be Empty", Toast.LENGTH_SHORT).show();
        }
        String etddf = ddf.getText().toString().trim();
        String etwatercontent = watercontent.getText().toString().trim();
        String etinetrfacial = interfacialtension.getText().toString().trim();
        String etflashpoint = flashpoint.getText().toString().trim();
        String etpourpoint = pourpoint.getText().toString().trim();
        String etrcstest = rcstest.getText().toString().trim();
        String etcorrectionrequird = correctionrequird.getText().toString().trim();
        if (!density25.getText().toString().isEmpty()) {
            try {
                String input = density25.getText().toString().trim();
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

                etdensity25 = integerValue;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        } else {
            Toasty.warning(this, "Density Cannot be Empty", Toast.LENGTH_SHORT).show();
        }
        String etrestivity=restivity.getText().toString().trim();
        String etinfrared=infrared.getText().toString().trim();

        if (etapperance.isEmpty() || etcolor.isEmpty() ||
                etodor.isEmpty() || ettbn.isEmpty() || etddf.isEmpty() || etwatercontent.isEmpty() ||
                etinetrfacial.isEmpty() || etflashpoint.isEmpty() || etpourpoint.isEmpty() || etrcstest.isEmpty() || etremark.isEmpty()) {
            Toasty.warning(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
        } else {
            Lab_Model_insert_Outward_Tanker labModelInsertOutwardTanker = new Lab_Model_insert_Outward_Tanker(OutwardId, outTime,
                    etremark, etsamplecondition, etsamplereceving, etsamplerelease, etapperance, etodor, etcolor, etdensity25,
                    etkv40, etkv100, etviscosity, ettbn, etanlinepoint, etbreakdown,
                    etddf, etwatercontent, etinetrfacial, etflashpoint, etpourpoint, etrcstest, etcorrectionrequird,etrestivity,etinfrared,
                    EmployeId, 'U', inOut, 'L', vehicleType);
            Call<Boolean> call = outwardTankerLab.insertinprocessLaboratory(labModelInsertOutwardTanker);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body() && response.body()==true) {
                        makeNotificationiblabcomp(labvehicl);
                        Toasty.success(Outward_Tanker_Laboratory.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Outward_Tanker_Laboratory.this, Outward_Tanker.class));
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
                    Toasty.error(Outward_Tanker_Laboratory.this, "failed..!", Toast.LENGTH_SHORT).show();
                }
            });
        }


    }

    public void outtankerlabinprocpending(View view) {
        Intent intent = new Intent(this, Grid_Outward.class);
        startActivity(intent);
    }
    public void otinprocesslabcompleted(View view) {
        /*Intent intent = new Intent(this, Grid_Outward.class);
        startActivity(intent);*/
    }
}