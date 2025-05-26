package com.android.gandharvms.OutwardOut_Truck_Billing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.android.gandharvms.OutwardOutTankerBilling.ot_outBilling;
import com.android.gandharvms.OutwardOut_Truck;
import com.android.gandharvms.Outward_Tanker_Billing.Outward_Tanker_Billinginterface;
import com.android.gandharvms.Outward_Tanker_Billing.Respons_Outward_Tanker_Billing;
import com.android.gandharvms.Outward_Tanker_Security.Grid_Outward;
import com.android.gandharvms.Outward_Tanker_Security.Outward_RetroApiclient;
import com.android.gandharvms.Outward_Truck_Billing.Model_OutwardOut_Truck_Billing;
import com.android.gandharvms.R;
import com.android.gandharvms.Util.dialogueprogreesbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

public class OutwardOut_Truck_Billing extends NotificationCommonfunctioncls {

    public static String Tanker;
    public static String Truck;
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private final String EmployeId = Global_Var.getInstance().EmpId;
    EditText intime, serialnumber, vehiclenumber, etoanumber, ettramsname, etdrivermob, etgrs, ettare, etnet, etseal, etbatch, etdensity, etremark, invoicenum, totalqty;
    TextView smallpacktitle, indutialpacktitle;
    LinearLayout lnlindustrialbarrel;
    EditText ilpack10literqty, ilpack18literqty, ilpack20literqty,
            ilpack26literqty, ilpack50literqty, ilpack210literqty, ilpackboxbucketqty,
            iltotqty, iltotweight, industotqty, industotwight;
    LinearLayout lnlsmallpackqty;
    EditText splpack7literqty, splpack7_5literqty, splpack8_5literqty, splpack10literqty, splpack11literqty,
            splpack12literqty, splpack13literqty, splpack15literqty, splpack18literqty, splpack20literqty,
            splpack26literqty, splpack50literqty, splpack210literqty, splpackboxbucketqty,
            smalltotqty, smalltotweight;
    //EditText industotalqty;
    Button submit, billcomp;
    FirebaseFirestore dbroot;
    TimePickerDialog tpicker;
    Calendar calendar = Calendar.getInstance();
    String[] items1 = {"Ton", "Litre", "KL", "Kgs", "pcs"};
    AutoCompleteTextView totqtyautoCompleteTextView2;
    ArrayAdapter<String> nettotqtyuomdrop;
    Map<String, Integer> totqtyUomMapping = new HashMap<>();
    Integer nettotqtyuomvalue = 3;
    ImageView btnlogout, btnhome;
    TextView username, empid;
    dialogueprogreesbar dialogHelper = new dialogueprogreesbar();
    private int OutwardId;
//    public String userialnumber,uvehiclenumer;
    private Outward_Tanker_Billinginterface outwardTankerBillinginterface;
    private LoginMethod userDetails;
    private String token;
    private String bvehicleno;
    private int netweuom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outward_out_truck_billing);

        FirebaseMessaging.getInstance().subscribeToTopic(token);

        outwardTankerBillinginterface = Outward_RetroApiclient.outwardTankerBillinginterface();
        userDetails = RetroApiClient.getLoginApi();

        intime = findViewById(R.id.etintime);
        serialnumber = findViewById(R.id.etserialnumber);
        vehiclenumber = findViewById(R.id.etvehical);
        etoanumber = findViewById(R.id.etoanumber);
        ettramsname = findViewById(R.id.ettarnspname);
        etdrivermob = findViewById(R.id.etdriverno);
        etgrs = findViewById(R.id.etgrswt);
        ettare = findViewById(R.id.ettarewt);
        etnet = findViewById(R.id.etnetwt);
        etseal = findViewById(R.id.etsealnum);
        etbatch = findViewById(R.id.etbacthno);
//        etdensity = findViewById(R.id.etdensity);
        etremark = findViewById(R.id.etremark);
        invoicenum = findViewById(R.id.etinvoicenum);
        totalqty = findViewById(R.id.oroutbiltotalQuantity);
        //industotalqty = findViewById(R.id.industotalqty);
        submit = findViewById(R.id.submit);
        billcomp = findViewById(R.id.truckotoutbillingcompleted);
        dbroot = FirebaseFirestore.getInstance();

        indutialpacktitle = findViewById(R.id.txtoroutbindustrailtitle);
        lnlindustrialbarrel = findViewById(R.id.lnloroutbindustrialbarrel);
        ilpack10literqty = findViewById(R.id.oroutbilpack10Liter);
        ilpack18literqty = findViewById(R.id.oroutbilpack18Liter);
        ilpack20literqty = findViewById(R.id.oroutbilpack20Liter);
        ilpack26literqty = findViewById(R.id.oroutbilpack26Liter);
        ilpack50literqty = findViewById(R.id.oroutbilpack50Liter);
        ilpack210literqty = findViewById(R.id.oroutbilpack210Liter);
        ilpackboxbucketqty = findViewById(R.id.oroutbilpackboxbucket);
        industotqty = findViewById(R.id.oroutbetinudustotqty);
        industotwight = findViewById(R.id.oroutbetinudustotweight);

        smallpacktitle = findViewById(R.id.oroutbsmalltitle);
        lnlsmallpackqty = findViewById(R.id.lnloroutbsmallpackbarrel);
        splpack7literqty = findViewById(R.id.oroutbsplpack7Liter);
        splpack7_5literqty = findViewById(R.id.oroutbsplpack7_5Liter);
        splpack8_5literqty = findViewById(R.id.oroutbsplpack8_5Liter);
        splpack10literqty = findViewById(R.id.oroutbsplpack10Liter);
        splpack11literqty = findViewById(R.id.oroutbsplpack11Liter);
        splpack12literqty = findViewById(R.id.oroutbsplpack12Liter);
        splpack13literqty = findViewById(R.id.oroutbsplpack13Liter);
        splpack15literqty = findViewById(R.id.oroutbsplpack15Liter);
        splpack18literqty = findViewById(R.id.oroutbsplpack18Liter);
        splpack20literqty = findViewById(R.id.oroutbsplpack20Liter);
        splpack26literqty = findViewById(R.id.oroutbsplpack26Liter);
        splpack50literqty = findViewById(R.id.oroutbsplpack50Liter);
        splpack210literqty = findViewById(R.id.oroutbsplpack210Liter);
        splpackboxbucketqty = findViewById(R.id.oroutbsplpackboxbucket);
        smalltotqty = findViewById(R.id.oroutbetsmalltotqty);
        smalltotweight = findViewById(R.id.oroutbetsmalltotweight);

        setupHeader();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert();
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
        billcomp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(OutwardOut_Truck_Billing.this, Billing_Out_OR_Complete.class));
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
                        for (ResponseModel resmodel : userList) {
                            String specificRole = "Security";
                            if (specificRole.equals(resmodel.getDepartment())) {
                                token = resmodel.getToken();

                                FcmNotificationsSender notificationsSender = new FcmNotificationsSender(
                                        token,
                                        "OutwardOut Truck Billing Process Done..!",
                                        "Vehicle Number:-" + vehicleNumber + " has completed Billing process at " + outTime,
                                        getApplicationContext(),
                                        OutwardOut_Truck_Billing.this
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
                Toasty.error(OutwardOut_Truck_Billing.this, "failed..!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void FetchVehicleDetails(@NonNull String vehicleNo, String vehicleType, char NextProcess, char inOut) {
        Call<Respons_Outward_Tanker_Billing> call = outwardTankerBillinginterface.outwardbillingfetching(vehicleNo, vehicleType, NextProcess, inOut);
        call.enqueue(new Callback<Respons_Outward_Tanker_Billing>() {
            @Override
            public void onResponse(Call<Respons_Outward_Tanker_Billing> call, Response<Respons_Outward_Tanker_Billing> response) {
                if (response.isSuccessful()) {
                    Respons_Outward_Tanker_Billing data = response.body();
                    if (data.getVehicleNumber() != "" && data.getVehicleNumber() != null) {
                        OutwardId = data.getOutwardId();
                        serialnumber.setText(data.getSerialNumber());
                        serialnumber.setEnabled(false);
                        vehiclenumber.setText(data.getVehicleNumber());
                        vehiclenumber.setEnabled(false);
                        ettramsname.setText(data.getTransportName());
                        ettramsname.setEnabled(false);
                        etoanumber.setText(data.getOAnumber());
                        etoanumber.setEnabled(false);
                        ettare.setText(data.getTareWeight());
                        ettare.setEnabled(false);
                        etdrivermob.setText(data.getMobileNumber());
                        etdrivermob.setEnabled(false);
                        etgrs.setText(data.getGrossWeight());
                        etgrs.setEnabled(false);
                        etnet.setText(data.getNetWeight());
                        etnet.setEnabled(false);
                        etseal.setText(data.getSealNumber());
                        etseal.setEnabled(false);
                        totalqty.setText(data.getSpltotqty());
                        totalqty.setEnabled(false);
                        etbatch.setText("No BatchNumber");
                        if (data.iltotqty.isEmpty() || data.ilweight == "0") {
                            smallpacktitle.setVisibility(View.VISIBLE);
                            lnlsmallpackqty.setVisibility(View.VISIBLE);
                            splpack7literqty.setText("SmallPack 7 Liter Qty :- " + data.getSplpackof7ltrqty());
                            splpack7literqty.setEnabled(false);
                            splpack7_5literqty.setText("SmallPack 7.5 Liter Qty :- " + data.getSplpackof7_5ltrqty());
                            splpack7_5literqty.setEnabled(false);
                            splpack8_5literqty.setText("SmallPack 8.5 Liter Qty :- " + data.getSplpackof8_5ltrqty());
                            splpack8_5literqty.setEnabled(false);
                            splpack10literqty.setText("SmallPack 10 Liter Qty :- " + data.getSplpackof10ltrqty());
                            splpack10literqty.setEnabled(false);
                            splpack11literqty.setText("SmallPack 11 Liter Qty :- " + data.getSplpackof11ltrqty());
                            splpack11literqty.setEnabled(false);
                            splpack12literqty.setText("SmallPack 12 Liter Qty :- " + data.getSplpackof12ltrqty());
                            splpack12literqty.setEnabled(false);
                            splpack13literqty.setText("SmallPack 13 Liter Qty :- " + data.getSplpackof13ltrqty());
                            splpack13literqty.setEnabled(false);
                            splpack15literqty.setText("SmallPack 15 Liter Qty :- " + data.getSplpackof15ltrqty());
                            splpack15literqty.setEnabled(false);
                            splpack18literqty.setText("SmallPack 18 Liter Qty :- " + data.getSplpackof18ltrqty());
                            splpack18literqty.setEnabled(false);
                            splpack20literqty.setText("SmallPack 20 Liter Qty :- " + data.getSplpackof20ltrqty());
                            splpack20literqty.setEnabled(false);
                            splpack26literqty.setText("SmallPack 26 Liter Qty :- " + data.getSplpackof26ltrqty());
                            splpack26literqty.setEnabled(false);
                            splpack50literqty.setText("SmallPack 50 Liter Qty :- " + data.getSplpackof50ltrqty());
                            splpack50literqty.setEnabled(false);
                            splpack210literqty.setText("SmallPack 210 Liter Qty :- " + data.getSplpackof210ltrqty());
                            splpack210literqty.setEnabled(false);
                            splpackboxbucketqty.setText("SmallPack BoxBucket Qty :- " + data.getSplpackofboxbuxketltrqty());
                            splpackboxbucketqty.setEnabled(false);
                            smalltotqty.setText("SmallPack Total Qty :- " + data.getSpltotqty());
                            smalltotqty.setEnabled(false);
                            smalltotweight.setText("SmallPack Weight :- " + data.getSplweight());
                            smalltotweight.setEnabled(false);
                        } else if (data.spltotqty.isEmpty() || data.splweight == "0") {
                            indutialpacktitle.setVisibility(View.VISIBLE);
                            lnlindustrialbarrel.setVisibility(View.VISIBLE);
                            ilpack10literqty.setText("IndusPack 10 Liter Qty :- " + data.getIlpackof10ltrqty());
                            ilpack10literqty.setEnabled(false);
                            ilpack18literqty.setText("IndusPack 18 Liter Qty :- " + data.getIlpackof18ltrqty());
                            ilpack18literqty.setEnabled(false);
                            ilpack20literqty.setText("IndusPack 20 Liter Qty :- " + data.getIlpackof20ltrqty());
                            ilpack20literqty.setEnabled(false);
                            ilpack26literqty.setText("IndusPack 26 Liter Qty :- " + data.getIlpackof26ltrqty());
                            ilpack26literqty.setEnabled(false);
                            ilpack50literqty.setText("IndusPack 50 Liter Qty :- " + data.getIlpackof50ltrqty());
                            ilpack50literqty.setEnabled(false);
                            ilpack210literqty.setText("IndusPack 210 Liter Qty :- " + data.getIlpackof50ltrqty());
                            ilpack210literqty.setEnabled(false);
                            ilpackboxbucketqty.setText("IndusPack BoxBucket Qty :- " + data.getIlpackofboxbuxketltrqty());
                            ilpackboxbucketqty.setEnabled(false);
                            industotqty.setText("IndusPack Total Qty :- " + data.getIltotqty());
                            industotqty.setEnabled(false);
                            industotwight.setText("IndusPack Total Weight :- " + data.getIlweight());
                            industotwight.setEnabled(false);
                        } else {
                            indutialpacktitle.setVisibility(View.VISIBLE);
                            lnlindustrialbarrel.setVisibility(View.VISIBLE);
                            ilpack10literqty.setText("IndusPack 10 Liter Qty :- " + data.getIlpackof10ltrqty());
                            ilpack10literqty.setEnabled(false);
                            ilpack18literqty.setText("IndusPack 18 Liter Qty :- " + data.getIlpackof18ltrqty());
                            ilpack18literqty.setEnabled(false);
                            ilpack20literqty.setText("IndusPack 20 Liter Qty :- " + data.getIlpackof20ltrqty());
                            ilpack20literqty.setEnabled(false);
                            ilpack26literqty.setText("IndusPack 26 Liter Qty :- " + data.getIlpackof26ltrqty());
                            ilpack26literqty.setEnabled(false);
                            ilpack50literqty.setText("IndusPack 50 Liter Qty :- " + data.getIlpackof50ltrqty());
                            ilpack50literqty.setEnabled(false);
                            ilpack210literqty.setText("IndusPack 210 Liter Qty :- " + data.getIlpackof50ltrqty());
                            ilpack210literqty.setEnabled(false);
                            ilpackboxbucketqty.setText("IndusPack BoxBucket Qty :- " + data.getIlpackofboxbuxketltrqty());
                            ilpackboxbucketqty.setEnabled(false);
                            industotqty.setText("IndusPack Total Qty :- " + data.getIltotqty());
                            industotqty.setEnabled(false);
                            industotwight.setText("IndusPack Total Weight :- " + data.getIlweight());
                            industotwight.setEnabled(false);

                            smallpacktitle.setVisibility(View.VISIBLE);
                            lnlsmallpackqty.setVisibility(View.VISIBLE);
                            splpack7literqty.setText("SmallPack 7 Liter Qty :- " + data.getSplpackof7ltrqty());
                            splpack7literqty.setEnabled(false);
                            splpack7_5literqty.setText("SmallPack 7.5 Liter Qty :- " + data.getSplpackof7_5ltrqty());
                            splpack7_5literqty.setEnabled(false);
                            splpack8_5literqty.setText("SmallPack 8.5 Liter Qty :- " + data.getSplpackof8_5ltrqty());
                            splpack8_5literqty.setEnabled(false);
                            splpack10literqty.setText("SmallPack 10 Liter Qty :- " + data.getSplpackof10ltrqty());
                            splpack10literqty.setEnabled(false);
                            splpack11literqty.setText("SmallPack 11 Liter Qty :- " + data.getSplpackof11ltrqty());
                            splpack11literqty.setEnabled(false);
                            splpack12literqty.setText("SmallPack 12 Liter Qty :- " + data.getSplpackof12ltrqty());
                            splpack12literqty.setEnabled(false);
                            splpack13literqty.setText("SmallPack 13 Liter Qty :- " + data.getSplpackof13ltrqty());
                            splpack13literqty.setEnabled(false);
                            splpack15literqty.setText("SmallPack 15 Liter Qty :- " + data.getSplpackof15ltrqty());
                            splpack15literqty.setEnabled(false);
                            splpack18literqty.setText("SmallPack 18 Liter Qty :- " + data.getSplpackof18ltrqty());
                            splpack18literqty.setEnabled(false);
                            splpack20literqty.setText("SmallPack 20 Liter Qty :- " + data.getSplpackof20ltrqty());
                            splpack20literqty.setEnabled(false);
                            splpack26literqty.setText("SmallPack 26 Liter Qty :- " + data.getSplpackof26ltrqty());
                            splpack26literqty.setEnabled(false);
                            splpack50literqty.setText("SmallPack 50 Liter Qty :- " + data.getSplpackof50ltrqty());
                            splpack50literqty.setEnabled(false);
                            splpack210literqty.setText("SmallPack 210 Liter Qty :- " + data.getSplpackof210ltrqty());
                            splpack210literqty.setEnabled(false);
                            splpackboxbucketqty.setText("SmallPack BoxBucket Qty :- " + data.getSplpackofboxbuxketltrqty());
                            splpackboxbucketqty.setEnabled(false);
                            smalltotqty.setText("SmallPack Total Qty :- " + data.getSpltotqty());
                            smalltotqty.setEnabled(false);
                            smalltotweight.setText("SmallPack Weight :- " + data.getSplweight());
                            smalltotweight.setEnabled(false);

                        }
                        //industotalqty.setText(data.getIltotqty());
                        //industotalqty.setEnabled(false);
                        bvehicleno = data.getVehicleNumber();

                    } else {
                        Toasty.error(OutwardOut_Truck_Billing.this, "This Vehicle Number Is Not Available..!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("Retrofit", "Error Response Body: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Respons_Outward_Tanker_Billing> call, Throwable t) {

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

    public void insert() {
        String uvehiclenumer = vehiclenumber.getText().toString().trim();
        String userialnumber = serialnumber.getText().toString().trim();
        String uintime = intime.getText().toString().trim();
        String ubatch = etbatch.getText().toString().trim();
        String obOutTime = getCurrentTime();
        String uremark = etremark.getText().toString().trim();
        String uinvoicenum = invoicenum.getText().toString().trim();
        String uqty = totalqty.getText().toString().trim();

        if (uintime.isEmpty() || ubatch.isEmpty() || obOutTime.isEmpty()) {
            Toasty.warning(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
        } else {
            Model_OutwardOut_Truck_Billing requestoutBilmodel = new Model_OutwardOut_Truck_Billing(userialnumber, uvehiclenumer, OutwardId, uintime, obOutTime, uqty, netweuom,
                    uinvoicenum, uremark, ubatch, 'S', inOut, vehicleType, EmployeId, EmployeId);
            dialogHelper.showConfirmationDialog(this, () -> {
                dialogHelper.showProgressDialog(this); // Show progress when confirmed
                Call<Boolean> call = outwardTankerBillinginterface.updateouttruckbilling(requestoutBilmodel);
                call.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if (response.isSuccessful() && response.body() != null && response.body()) {
                            dialogHelper.hideProgressDialog(); // Hide after response
                            makeNotification(bvehicleno, obOutTime);
                            Toasty.success(OutwardOut_Truck_Billing.this, "Data Inserted Successfully", Toast.LENGTH_SHORT, true).show();
                            startActivity(new Intent(OutwardOut_Truck_Billing.this, Grid_Outward.class));
                            finish();
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
                        Toasty.error(OutwardOut_Truck_Billing.this, "failed..!", Toast.LENGTH_SHORT).show();
                    }
                });
            });
        }


    }

    public void Outwardoutbillingpending(View view) {
        Intent intent = new Intent(this, Grid_Outward.class);
        startActivity(intent);
    }

    /*public void btn_clicktoViewWeighmentSlip(View view){
        //Intent intent = new Intent(this, Grid_Outward.class);
        //startActivity(intent);
    }
    public void btn_clicktoViewBarrelForm(View view){
        //Intent intent = new Intent(this, Grid_Outward.class);
        //startActivity(intent);
    }
    public void btn_clicktoViewTestReport(View view){
        //Intent intent = new Intent(this, Grid_Outward.class);
        //startActivity(intent);
    }*/
}