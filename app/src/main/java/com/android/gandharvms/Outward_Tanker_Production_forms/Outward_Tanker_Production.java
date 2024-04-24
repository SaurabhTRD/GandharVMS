package com.android.gandharvms.Outward_Tanker_Production_forms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.gandharvms.FcmNotificationsSender;
import com.android.gandharvms.Global_Var;
import com.android.gandharvms.Inward_Tanker;
import com.android.gandharvms.Inward_Tanker_Laboratory.it_Lab_Completedgrid;
import com.android.gandharvms.Inward_Tanker_Security.Inward_Tanker_Security;
import com.android.gandharvms.Inward_Tanker_Weighment.Inward_Tanker_Weighment;
import com.android.gandharvms.LoginWithAPI.LoginMethod;
import com.android.gandharvms.LoginWithAPI.ResponseModel;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.Outward_Tanker;
import com.android.gandharvms.Outward_Tanker_Billing.Outward_Tanker_Billinginterface;
import com.android.gandharvms.Outward_Tanker_Billing.Respons_Outward_Tanker_Billing;
import com.android.gandharvms.Outward_Tanker_Security.Grid_Outward;
import com.android.gandharvms.Outward_Tanker_Security.Outward_RetroApiclient;
import com.android.gandharvms.Outward_Tanker_Security.Outward_Tanker_Security;
import com.android.gandharvms.Outward_Tanker_Weighment.Outward_Tanker_weighment;
import com.android.gandharvms.R;
import com.android.gandharvms.outward_Tanker_Lab_forms.Blending_Flushing_Model;
import com.android.gandharvms.outward_Tanker_Lab_forms.Lab_Model__Outward_Tanker;
import com.android.gandharvms.outward_Tanker_Lab_forms.OT_Completed_inproc_laboratory;
import com.android.gandharvms.outward_Tanker_Lab_forms.Outward_Tanker_Lab;
import com.android.gandharvms.outward_Tanker_Lab_forms.Outward_Tanker_Laboratory;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

public class Outward_Tanker_Production extends AppCompatActivity {

    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final String EmployeId = Global_Var.getInstance().EmpId;
    public char inOut = Global_Var.getInstance().InOutType;
    public LinearLayout clinerar;
    public char blendingstatus;
    public char flushingstatus;
    public String labintime;
    public int statuscount;
    EditText intime, serialnumber, vehiclenumber, blenderno, transporter, product, howmuch, customer, location, blendingratio, batchno,
            productspesification, custref, packingsatus, rinsingstatus, decisionrule, blendingmaterial, signof, dt, oanum, remark, etflush;
    Button submit, etsend,completed,viewlabreport;
    FirebaseFirestore dbroot;
    TimePickerDialog tpicker;
    Calendar calendar = Calendar.getInstance();
    DatePickerDialog picker;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItems;
    String[] items = {"For Small Pack", "Barrel", "Tanker Filling", "Other"};
    RadioButton rbrinsingyes, rbrinsingno, rbdecisionyes, rbdecisionno;
    private int oploutwardid = 0;
    private int OutwardId;
    private Outward_Tanker_Lab outwardTankerLab;
    private String token;
    private LoginMethod userDetails;
    private String nvehiclenumber;
    private CheckBox isblending, isflushing;

    private int etcustref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outward_tanker_production);

        outwardTankerLab = Outward_RetroApiclient.outwardTankerLab();
        userDetails = RetroApiClient.getLoginApi();
        FirebaseMessaging.getInstance().subscribeToTopic(token);

        isblending = findViewById(R.id.isblending);
        isflushing = findViewById(R.id.isflushing);
        etflush = findViewById(R.id.etflushingno);
        etsend = findViewById(R.id.sendbtn);
        clinerar = findViewById(R.id.checklinear);
        viewlabreport=findViewById(R.id.btn_otViewlabReport);
        //
        // etflush.setVisibility(View.GONE);
        etsend.setVisibility(View.GONE);

        isflushing.setOnCheckedChangeListener((buttonView, isChecked) -> {
            ischecktoDisplay();
        });
        isblending.setOnCheckedChangeListener((buttonView, isChecked) -> {
            ischecktoDisplay();
        });

        clinerar.setVisibility(View.GONE);
        etflush.setVisibility(View.VISIBLE);
        etsend.setVisibility(View.VISIBLE);

        autoCompleteTextView = findViewById(R.id.etpackingstatus);
        adapterItems = new ArrayAdapter<String>(this, R.layout.packing_status_dropdown, items);
        autoCompleteTextView.setAdapter(adapterItems);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String items = parent.getItemAtPosition(position).toString();
                Toasty.success(getApplicationContext(), "Packing Status:- " + items +" Selected", Toast.LENGTH_SHORT).show();
            }
        });


        intime = findViewById(R.id.etintime);
        serialnumber = findViewById(R.id.etserialnumber);
        vehiclenumber = findViewById(R.id.etvehicleno);
       //blenderno = findViewById(R.id.elblendingno);
        transporter = findViewById(R.id.ettransportername);
        product = findViewById(R.id.etproductname);
        howmuch = findViewById(R.id.ethowmuch);
        customer = findViewById(R.id.etcustname);
        location = findViewById(R.id.etlocation);
        //blendingratio = findViewById(R.id.etblendingrationrec);
        /*batchno = findViewById(R.id.etbatchno);*/
        //productspesification = findViewById(R.id.etproductspesification);
       // custref = findViewById(R.id.etcustrefno);
        packingsatus = findViewById(R.id.etpackingstatus);
        //rinsingstatus = findViewById(R.id.etrinsingstatus);
        //decisionrule = findViewById(R.id.etdecisionrule);
        blendingmaterial = findViewById(R.id.etblendingstatus);
        signof = findViewById(R.id.etsignofproduction);
        remark = findViewById(R.id.etremark);
//        dt = findViewById(R.id.etdatetime);
        oanum = findViewById(R.id.etoanumber);

        rbrinsingyes = findViewById(R.id.rinsingyes);
        rbrinsingno = findViewById(R.id.rinsingno);
        rbdecisionyes = findViewById(R.id.decisionyes);
        rbdecisionno = findViewById(R.id.decisionno);

        submit = findViewById(R.id.etssubmit);
        dbroot = FirebaseFirestore.getInstance();
        completed = findViewById(R.id.inproceproduction);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert();
            }
        });
        completed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Outward_Tanker_Production.this,OT_Completed_inproc_production.class));
            }
        });
        etsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (oploutwardid == 0) {
                    request();
                } else {
                    recheckandUpdate();
                }
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

        vehiclenumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    FetchVehicleDetails(vehiclenumber.getText().toString().trim(), vehicleType, nextProcess, inOut);
                }
            }
        });
    }

    public void ischecktoDisplay() {
        if (isflushing.isChecked() && isblending.isChecked()) {
            if (isflushing.isChecked()) {
                etflush.setVisibility(View.VISIBLE);
            } else {
                etflush.setVisibility(View.GONE);
            }
            etsend.setVisibility(View.VISIBLE);
            clinerar.setVisibility(View.GONE);
        } else if (isflushing.isChecked() || isblending.isChecked()) {
            if (isflushing.isChecked()) {
                etflush.setVisibility(View.VISIBLE);
            } else {
                etflush.setVisibility(View.GONE);
            }
            etsend.setVisibility(View.VISIBLE);
            clinerar.setVisibility(View.GONE);
        } else if (!isflushing.isChecked() && !isblending.isChecked()) {
            // Hide the TextInputLayout and Button
            etflush.setVisibility(View.GONE);
            etsend.setVisibility(View.VISIBLE);
//                clinerar.setVisibility(View.GONE);
            //clinerar.setVisibility(View.VISIBLE);
        }
    }

    public void makeNotificationforiplabifblNflUntick(String vehicleNumber) {
        Call<List<ResponseModel>> call = userDetails.getUsersListData();
        call.enqueue(new Callback<List<ResponseModel>>() {
            @Override
            public void onResponse(Call<List<ResponseModel>> call, Response<List<ResponseModel>> response) {
                if (response.isSuccessful()) {
                    List<ResponseModel> userList = response.body();
                    if (userList != null) {
                        for (ResponseModel resmodel : userList) {
                            String specificRole = "Laboratory";
                            if (specificRole.equals(resmodel.getDepartment())) {
                                token = resmodel.getToken();

                                FcmNotificationsSender notificationsSender = new FcmNotificationsSender(
                                        token,
                                        "Outward Tanker Production No Blending and Flushing",
                                        "Vehicle Number:-" + vehicleNumber + " has Not Require Blending and Flushing",
                                        getApplicationContext(),
                                        Outward_Tanker_Production.this
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
                Toasty.error(Outward_Tanker_Production.this, "failed..!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void makeNotificationforiplab(String vehicleNumber) {
        Call<List<ResponseModel>> call = userDetails.getUsersListData();
        call.enqueue(new Callback<List<ResponseModel>>() {
            @Override
            public void onResponse(Call<List<ResponseModel>> call, Response<List<ResponseModel>> response) {
                if (response.isSuccessful()) {
                    List<ResponseModel> userList = response.body();
                    if (userList != null) {
                        for (ResponseModel resmodel : userList) {
                            String specificRole = "Laboratory";
                            if (specificRole.equals(resmodel.getDepartment())) {
                                token = resmodel.getToken();

                                FcmNotificationsSender notificationsSender = new FcmNotificationsSender(
                                        token,
                                        "Outward Tanker Production Send Blending and Flushing",
                                        "Vehicle Number:-" + vehicleNumber + " has send To Laboratory for Check Blending and Flushing",
                                        getApplicationContext(),
                                        Outward_Tanker_Production.this
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
                Toasty.error(Outward_Tanker_Production.this, "failed..!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void makeNotificationforipprocom(String vehicleNumber) {
        Call<List<ResponseModel>> call = userDetails.getUsersListData();
        call.enqueue(new Callback<List<ResponseModel>>() {
            @Override
            public void onResponse(Call<List<ResponseModel>> call, Response<List<ResponseModel>> response) {
                if (response.isSuccessful()) {
                    List<ResponseModel> userList = response.body();
                    if (userList != null) {
                        for (ResponseModel resmodel : userList) {
                            String specificRole = "Laboratory";
                            if (specificRole.equals(resmodel.getDepartment())) {
                                token = resmodel.getToken();

                                FcmNotificationsSender notificationsSender = new FcmNotificationsSender(
                                        token,
                                        "Outward Tanker Production InProcessForm Completed",
                                        "Vehicle Number:-" + vehicleNumber + " has Completed Production InProcessForm",
                                        getApplicationContext(),
                                        Outward_Tanker_Production.this
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
                Toasty.error(Outward_Tanker_Production.this, "failed..!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void makeNotificationoutweigh(String vehicleNumber) {
        Call<List<ResponseModel>> call = userDetails.getUsersListData();
        call.enqueue(new Callback<List<ResponseModel>>() {
            @Override
            public void onResponse(Call<List<ResponseModel>> call, Response<List<ResponseModel>> response) {
                if (response.isSuccessful()) {
                    List<ResponseModel> userList = response.body();
                    if (userList != null) {
                        for (ResponseModel resmodel : userList) {
                            String specificRole = "Weighment";
                            if (specificRole.equals(resmodel.getDepartment())) {
                                token = resmodel.getToken();

                                FcmNotificationsSender notificationsSender = new FcmNotificationsSender(
                                        token,
                                        "Outward Tanker Reject Due To Blending and Flushing Not Received..!",
                                        "This Vehicle Number:-" + vehicleNumber + " has been Rejected",
                                        getApplicationContext(),
                                        Outward_Tanker_Production.this
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
                Toasty.error(Outward_Tanker_Production.this, "failed..!", Toast.LENGTH_SHORT).show();
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
                        oploutwardid = data.getOplOutwardId();
                        OutwardId = data.getOutwardId();
                        statuscount = data.getStatusCount();
                        blendingstatus = data.getBlendingStatus();
                        flushingstatus = data.getFlushingStatus();
                        labintime = data.getIPFLabInTime();
                        serialnumber.setText(data.getSerialNumber());
                        serialnumber.setEnabled(false);
                        nvehiclenumber = data.getVehicleNumber();
                        vehiclenumber.setText(data.getVehicleNumber());
                        vehiclenumber.setEnabled(false);
                        etflush.setText(data.getFlushing_No());
                        serialnumber.setEnabled(false);
                        product.setText(data.getProductName());
                        product.setEnabled(false);
                        howmuch.setText(String.valueOf(data.getHowMuchQuantityFilled()));
                        howmuch.setEnabled(false);
                        customer.setText(data.getCustomerName());
                        customer.setEnabled(false);
                        location.setText(data.getLocation());
                        location.setEnabled(false);
                        transporter.setText(data.getTransportName());
                        transporter.setEnabled(false);
                        isblending.setChecked(data.IsBlendingReq);
                        isflushing.setChecked(data.IsFlushingReq);
                        viewlabreport.setVisibility(View.VISIBLE);
                        //isblending.setEnabled(false);
                        //isflushing.setEnabled(false);
                        etflush.setVisibility(View.GONE);
                        etsend.setVisibility(View.VISIBLE);
                        if (isflushing.isChecked() && isblending.isChecked()) {
                            if (isflushing.isChecked()) {
                                etflush.setVisibility(View.VISIBLE);
                                etflush.setEnabled(false);
                                isflushing.setEnabled(false);
                                isblending.setEnabled(false);
                            } else {
                                etflush.setVisibility(View.GONE);
                            }
                        }
                        if (oploutwardid == 0) {
                            intime.setVisibility(View.VISIBLE);
                            /*intime.requestFocus();
                            intime.callOnClick();*/
                        } else {
                            intime.setVisibility(View.GONE);
                        }
                        if (oploutwardid > 0 && blendingstatus == 'Y' && flushingstatus == 'Y') {
                            etflush.setText(data.getFlushing_No());
                            etsend.setVisibility(View.GONE);
                            intime.setVisibility(View.GONE);
                            isblending.setEnabled(false);
                            isflushing.setEnabled(false);
                            intime.setEnabled(false);
                            clinerar.setVisibility(View.VISIBLE);
                            oanum.setText(data.getOAnumber());
                            oanum.setEnabled(false);
                            /*blenderno.setText(String.valueOf(data.getTankerNumber()));
                            blenderno.setEnabled(false);*/




                        } else {
                            if (statuscount == 2) {
                                etsend.setText("Reject");
                            } else if (oploutwardid > 0) {
                                etsend.setText("Recheck");
                                Toasty.success(Outward_Tanker_Production.this, "Blending or Flushing Ratio Would Be Okay.Repeat The Task", Toasty.LENGTH_LONG, true).show();
                            } else {
                                etsend.setText("check");
                            }
                        }
                    } else {
                        Toasty.error(Outward_Tanker_Production.this, "This Vehicle Number Is Not Available..!", Toast.LENGTH_SHORT).show();
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

    private String getCurrentTime() {
        // Get the current time
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(new Date());
    }

    public void request() {
        String rintime = intime.getText().toString().trim();
        String rserialno = serialnumber.getText().toString().trim();
        String rvehicle = vehiclenumber.getText().toString().trim();
        String rflushing = etflush.getText().toString().trim();
        String proouttime="";
        boolean rblending = isblending.isChecked();
        boolean reflushing = isflushing.isChecked();
        char nextprocess = 'Q';
        if (rintime.isEmpty() || rserialno.isEmpty() || rvehicle.isEmpty()) {
            Toasty.warning(this, "All fields must be filled", Toast.LENGTH_SHORT, true).show();
        } else {
            if (!isblending.isChecked() && !isflushing.isChecked()) {
                nextprocess = 'U';
                proouttime = getCurrentTime();
            }
            Request_Model_blendflush requestModelBlendflush = new Request_Model_blendflush(rintime, proouttime, rblending,
                    reflushing, rflushing, EmployeId, EmployeId, OutwardId, nextprocess,
                    rvehicle, vehicleType, rserialno, inOut);
            Call<Boolean> call = outwardTankerLab.requestflushblend(requestModelBlendflush);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body() && response.body()==true) {
                        if (!isblending.isChecked() && !isflushing.isChecked()) {
                            makeNotificationforiplabifblNflUntick(rvehicle);
                            Toasty.success(Outward_Tanker_Production.this, "No Flushing or Blending Required", Toast.LENGTH_SHORT, true).show();
                        } else {
                            makeNotificationforiplab(rvehicle);
                            Toasty.success(Outward_Tanker_Production.this, "Flushing Or Blending is Send For Checking to Lab", Toast.LENGTH_LONG, true).show();
                        }
                        startActivity(new Intent(Outward_Tanker_Production.this, Outward_Tanker.class));
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
                    Toasty.error(Outward_Tanker_Production.this, "failed..!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void recheckandUpdate() {
        char nextprocess = 'Q';
        String labouttime="";
        if (statuscount == 2) {
            labouttime = getCurrentTime();
            nextprocess = 'W';
            inOut = 'O';
        }
        Blending_Flushing_Model blendingFlushingModel = new Blending_Flushing_Model(OutwardId, labintime, labouttime, flushingstatus,
                blendingstatus, statuscount + 1, nextprocess, inOut, vehicleType, EmployeId);
        Call<Boolean> call = outwardTankerLab.updateflushblend(blendingFlushingModel);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful() && response.body() && response.body()==true) {
                    if (statuscount == 2) {
                        makeNotificationoutweigh(nvehiclenumber);
                        Toasty.success(Outward_Tanker_Production.this, "Vehicle Send To Reject", Toasty.LENGTH_SHORT, true).show();
                    } else {
                        makeNotificationforiplab(nvehiclenumber);
                        Toasty.success(Outward_Tanker_Production.this, "Blending and Flushing Send For Recheck", Toasty.LENGTH_SHORT, true).show();
                    }
                    startActivity(new Intent(Outward_Tanker_Production.this, Outward_Tanker.class));
                    finish();
                } else {
                    Toasty.error(Outward_Tanker_Production.this, "Recheck Failed", Toasty.LENGTH_SHORT, true).show();
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
                Toasty.error(Outward_Tanker_Production.this, "failed..!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void insert() {
        String outTime = getCurrentTime();
        String uremark = remark.getText().toString().trim();
        /*if(!custref.getText().toString().isEmpty())
        {
            try {
                etcustref=Integer.parseInt(custref.getText().toString().trim());
            }catch (NumberFormatException e){
                e.printStackTrace();
            }
        }
        else{
            Toasty.warning(this, "CustomerReferenceNumber Cannot be Empty", Toast.LENGTH_SHORT).show();
        }*/
        String packstatus = packingsatus.getText().toString().trim();
        String etrinisingstatus = rbrinsingyes.isChecked() ? "Yes" : "No";
        String etdecisionrule = rbdecisionyes.isChecked() ? "Yes" : "No";
        String etblendingmaterial = blendingmaterial.getText().toString().trim();
        String etsignof = signof.getText().toString().trim();
/*
        String etbatchno = batchno.getText().toString().trim();
*/

        if (packstatus.isEmpty() || etrinisingstatus.isEmpty() || etdecisionrule.isEmpty() || etblendingmaterial.isEmpty() || etsignof.isEmpty()) {
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
        } else {
            Production_Model_Outward productionModelOutward = new Production_Model_Outward(OutwardId,
                    0, packstatus, etrinisingstatus, etdecisionrule, etblendingmaterial, etsignof,
                    EmployeId, 'Q', inOut, 'P', uremark,outTime, vehicleType);

            Call<Boolean> call = outwardTankerLab.insertinprocessproduction(productionModelOutward);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body() && response.body()==true) {
                        makeNotificationforipprocom(nvehiclenumber);
                        Toasty.success(Outward_Tanker_Production.this, "Data Inserted Successfully", Toast.LENGTH_SHORT, true).show();
                        startActivity(new Intent(Outward_Tanker_Production.this, Outward_Tanker.class));
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
                    Toasty.error(Outward_Tanker_Production.this, "failed..!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    public void outtankerproinprocpending(View view) {
        Intent intent = new Intent(this, Grid_Outward.class);
        startActivity(intent);
    }
    public void btn_inporcctankerclicktoViewLabReport(View view){
        Global_Var.getInstance().DeptType = 'Q';
        Intent intent = new Intent(this, OT_Completed_inproc_laboratory.class);
        intent.putExtra("vehiclenumber", vehiclenumber.getText());
        view.getContext().startActivity(intent);
    }
}