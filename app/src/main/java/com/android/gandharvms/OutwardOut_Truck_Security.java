package com.android.gandharvms;

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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.gandharvms.Inward_Tanker_Security.Inward_Tanker_Security;
import com.android.gandharvms.LoginWithAPI.Login;
import com.android.gandharvms.LoginWithAPI.LoginMethod;
import com.android.gandharvms.LoginWithAPI.ResponseModel;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.NotificationAlerts.NotificationCommonfunctioncls;
import com.android.gandharvms.Outward_Tanker_Security.Grid_Outward;
import com.android.gandharvms.Outward_Tanker_Security.Outward_RetroApiclient;
import com.android.gandharvms.Outward_Tanker_Security.Outward_Tanker;
import com.android.gandharvms.Outward_Tanker_Security.Response_Outward_Security_Fetching;
import com.android.gandharvms.Outward_Tanker_Weighment.Outward_Tanker_weighment;
import com.android.gandharvms.Outward_Truck_Laboratory.Outward_Truck_Laboratory;
import com.android.gandharvms.Outward_Truck_Security.Model_OutwardOut_Truck_Security;
import com.android.gandharvms.Outward_Truck_Security.Outward_Truck_Security;
import com.android.gandharvms.Outward_Truck_Security.SecOut_OR_Complete;
import com.android.gandharvms.Util.dialogueprogreesbar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
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

public class OutwardOut_Truck_Security extends NotificationCommonfunctioncls {

    public static String Tanker;
    public static String Truck;
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private final String EmployeId = Global_Var.getInstance().EmpId;
    EditText intime, serialnumber, vehiclenumber, invoice, party, gooddis, qty, uom1, netweight, qtyuom, outtime, sign, remark, etproduct, erinvoice;
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
    //EditText erqty,etqtyspl;
    Button submit, complete;
    FirebaseFirestore dbroot;
    TimePickerDialog tpicker;
    Calendar calendar = Calendar.getInstance();
    RadioButton Trasnportyes, transportno, tremyes, tremno, ewayyes, ewayno, testyes, testno, invoiceyes, invoicenono;
    AutoCompleteTextView autoCompleteTextView, autoCompleteTextView1outwardoutse, autoCompleteTextView2outwardoutse;
    Map<String, Integer> qtyUomMapping = new HashMap<>();
    ArrayAdapter<String> qtyuomdrop;
    Integer qtyUomNumericValue = 1;
    Integer netweuomvalue = 1;
    ArrayAdapter<String> netweuomdrop;
    String[] netweuom = {"Ton", "Litre", "KL", "Kgs", "pcs", "NA"};
    SimpleDateFormat dtFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
    DatePickerDialog picker;
    ImageView btnlogout, btnhome;
    TextView username, empid;
    dialogueprogreesbar dialogHelper = new dialogueprogreesbar();
    private int OutwardId;
    private Outward_Tanker outwardTanker;
    private LoginMethod userDetails;
    private String token;
    private String svehicleno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outward_out_truck_security);
        outwardTanker = Outward_RetroApiclient.insertoutwardtankersecurity();
        userDetails = RetroApiClient.getLoginApi();
        FirebaseMessaging.getInstance().subscribeToTopic("all");
        intime = findViewById(R.id.etintime);
        serialnumber = findViewById(R.id.etserialnumber);
        vehiclenumber = findViewById(R.id.etvehical);
        party = findViewById(R.id.etpartyname);
        gooddis = findViewById(R.id.etdisc);
        netweight = findViewById(R.id.etnetweight);
        sign = findViewById(R.id.etsign);
        remark = findViewById(R.id.etremark);
        //erqty = findViewById(R.id.etqty);
        //etqtyspl=findViewById(R.id.etqtyspl);
        erinvoice = findViewById(R.id.etinvoicenum);

        submit = findViewById(R.id.submit);
        complete = findViewById(R.id.truckotoutsecuritycompleted);
        dbroot = FirebaseFirestore.getInstance();

        Trasnportyes = findViewById(R.id.outwaoutrb_LRCopyYes);
        transportno = findViewById(R.id.outwaourb_LRCopyNo);
        tremyes = findViewById(R.id.tremcardyes);
        tremno = findViewById(R.id.tremcardno);
        ewayyes = findViewById(R.id.outwardoutrb_EwaybillYes);
        ewayno = findViewById(R.id.outwardoutrb_EwaybillNo);
        testyes = findViewById(R.id.testreportyes);
        testno = findViewById(R.id.testreportno);
        invoiceyes = findViewById(R.id.invoiceyes);
        invoicenono = findViewById(R.id.invoiceno);

        indutialpacktitle = findViewById(R.id.txtoroutseindustrailtitle);
        lnlindustrialbarrel = findViewById(R.id.lnloroutseindustrialbarrel);
        ilpack10literqty = findViewById(R.id.oroutseilpack10Liter);
        ilpack18literqty = findViewById(R.id.oroutseilpack18Liter);
        ilpack20literqty = findViewById(R.id.oroutseilpack20Liter);
        ilpack26literqty = findViewById(R.id.oroutseilpack26Liter);
        ilpack50literqty = findViewById(R.id.oroutseilpack50Liter);
        ilpack210literqty = findViewById(R.id.oroutseilpack210Liter);
        ilpackboxbucketqty = findViewById(R.id.oroutseilpackboxbucket);
        industotqty = findViewById(R.id.oroutseetinudustotqty);
        industotwight = findViewById(R.id.oroutseetinudustotweight);

        smallpacktitle = findViewById(R.id.oroutsesmalltitle);
        lnlsmallpackqty = findViewById(R.id.lnloroutsesmallpackbarrel);
        splpack7literqty = findViewById(R.id.oroutsesplpack7Liter);
        splpack7_5literqty = findViewById(R.id.oroutsesplpack7_5Liter);
        splpack8_5literqty = findViewById(R.id.oroutsesplpack8_5Liter);
        splpack10literqty = findViewById(R.id.oroutsesplpack10Liter);
        splpack11literqty = findViewById(R.id.oroutsesplpack11Liter);
        splpack12literqty = findViewById(R.id.oroutsesplpack12Liter);
        splpack13literqty = findViewById(R.id.oroutsesplpack13Liter);
        splpack15literqty = findViewById(R.id.oroutsesplpack15Liter);
        splpack18literqty = findViewById(R.id.oroutsesplpack18Liter);
        splpack20literqty = findViewById(R.id.oroutsesplpack20Liter);
        splpack26literqty = findViewById(R.id.oroutsesplpack26Liter);
        splpack50literqty = findViewById(R.id.oroutsesplpack50Liter);
        splpack210literqty = findViewById(R.id.oroutsesplpack210Liter);
        splpackboxbucketqty = findViewById(R.id.oroutsesplpackboxbucket);
        smalltotqty = findViewById(R.id.oroutseetsmalltotqty);
        smalltotweight = findViewById(R.id.oroutseetsmalltotweight);

        setupHeader();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert();
            }
        });
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OutwardOut_Truck_Security.this, SecOut_OR_Complete.class));
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
    }

    private String getCurrentTime() {
        // Get the current time
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(new Date());
    }

    private String getWeightUnit(int unitCode) {
        switch (unitCode) {
            case 1:
                return "kl";
            case 2:
                return "Ton";
            case 3:
                return "Litre";
            case 4:
                return "KL";
            case 5:
                return "Kgs";
            case 6:
                return "Pcs";
            case 7:
                return "M3";
            default:
                return "unknown";// or any default value you want to set
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
                            String specificRole = "Security";
                            if (specificRole.equals(resmodel.getDepartment())) {
                                token = resmodel.getToken();

                                FcmNotificationsSender notificationsSender = new FcmNotificationsSender(
                                        token,
                                        "OutwardOut Truck Security Process Done..!",
                                        "Vehicle Number:-" + vehicleNumber + " has completed Security Out process at " + outTime,
                                        getApplicationContext(),
                                        OutwardOut_Truck_Security.this
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
                Toasty.error(OutwardOut_Truck_Security.this, "failed..!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void FetchVehicleDetails(@NonNull String vehicleNo, String vehicleType, char NextProcess, char inOut) {
        Call<List<Response_Outward_Security_Fetching>> call = Outward_RetroApiclient.insertoutwardtankersecurity().outwardsecurityfetching(vehicleNo, vehicleType, NextProcess, inOut);
        call.enqueue(new Callback<List<Response_Outward_Security_Fetching>>() {
            @Override
            public void onResponse(Call<List<Response_Outward_Security_Fetching>> call, Response<List<Response_Outward_Security_Fetching>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().size() > 0) {
                        List<Response_Outward_Security_Fetching> data = response.body();
                        Response_Outward_Security_Fetching obj = data.get(0);
                        OutwardId = obj.getOutwardId();
                        serialnumber.setText(obj.getSerialNumber());
                        vehiclenumber.setText(obj.getVehicleNumber());
                        party.setText(obj.getCustomerName());
                        serialnumber.setEnabled(false);
                        vehiclenumber.setEnabled(false);
                        party.setEnabled(false);
                        netweight.setText(obj.getNetWeight());
                        netweight.setEnabled(false);
                        svehicleno = obj.getVehicleNumber();
                        erinvoice.setText(obj.getOutInvoiceNumber());
                        erinvoice.setEnabled(false);
                        gooddis.setText("Ok");
                        if (obj.iltotqty.isEmpty() || obj.ilweight == "0") {
                            smallpacktitle.setVisibility(View.VISIBLE);
                            lnlsmallpackqty.setVisibility(View.VISIBLE);
                            splpack7literqty.setText("SmallPack 7 Liter Qty :- " + obj.getSplpackof7ltrqty());
                            splpack7literqty.setEnabled(false);
                            splpack7_5literqty.setText("SmallPack 7.5 Liter Qty :- " + obj.getSplpackof7_5ltrqty());
                            splpack7_5literqty.setEnabled(false);
                            splpack8_5literqty.setText("SmallPack 8.5 Liter Qty :- " + obj.getSplpackof8_5ltrqty());
                            splpack8_5literqty.setEnabled(false);
                            splpack10literqty.setText("SmallPack 10 Liter Qty :- " + obj.getSplpackof10ltrqty());
                            splpack10literqty.setEnabled(false);
                            splpack11literqty.setText("SmallPack 11 Liter Qty :- " + obj.getSplpackof11ltrqty());
                            splpack11literqty.setEnabled(false);
                            splpack12literqty.setText("SmallPack 12 Liter Qty :- " + obj.getSplpackof12ltrqty());
                            splpack12literqty.setEnabled(false);
                            splpack13literqty.setText("SmallPack 13 Liter Qty :- " + obj.getSplpackof13ltrqty());
                            splpack13literqty.setEnabled(false);
                            splpack15literqty.setText("SmallPack 15 Liter Qty :- " + obj.getSplpackof15ltrqty());
                            splpack15literqty.setEnabled(false);
                            splpack18literqty.setText("SmallPack 18 Liter Qty :- " + obj.getSplpackof18ltrqty());
                            splpack18literqty.setEnabled(false);
                            splpack20literqty.setText("SmallPack 20 Liter Qty :- " + obj.getSplpackof20ltrqty());
                            splpack20literqty.setEnabled(false);
                            splpack26literqty.setText("SmallPack 26 Liter Qty :- " + obj.getSplpackof26ltrqty());
                            splpack26literqty.setEnabled(false);
                            splpack50literqty.setText("SmallPack 50 Liter Qty :- " + obj.getSplpackof50ltrqty());
                            splpack50literqty.setEnabled(false);
                            splpack210literqty.setText("SmallPack 210 Liter Qty :- " + obj.getSplpackof210ltrqty());
                            splpack210literqty.setEnabled(false);
                            splpackboxbucketqty.setText("SmallPack BoxBucket Qty :- " + obj.getSplpackofboxbuxketltrqty());
                            splpackboxbucketqty.setEnabled(false);
                            smalltotqty.setText("SmallPack Total Qty :- " + obj.getSpltotqty());
                            smalltotqty.setEnabled(false);
                            smalltotweight.setText("SmallPack Weight :- " + obj.getSplweight());
                            smalltotweight.setEnabled(false);
                        } else if (obj.spltotqty.isEmpty() || obj.splweight == "0") {
                            indutialpacktitle.setVisibility(View.VISIBLE);
                            lnlindustrialbarrel.setVisibility(View.VISIBLE);
                            ilpack10literqty.setText("IndusPack 10 Liter Qty :- " + obj.getIlpackof10ltrqty());
                            ilpack10literqty.setEnabled(false);
                            ilpack18literqty.setText("IndusPack 18 Liter Qty :- " + obj.getIlpackof18ltrqty());
                            ilpack18literqty.setEnabled(false);
                            ilpack20literqty.setText("IndusPack 20 Liter Qty :- " + obj.getIlpackof20ltrqty());
                            ilpack20literqty.setEnabled(false);
                            ilpack26literqty.setText("IndusPack 26 Liter Qty :- " + obj.getIlpackof26ltrqty());
                            ilpack26literqty.setEnabled(false);
                            ilpack50literqty.setText("IndusPack 50 Liter Qty :- " + obj.getIlpackof50ltrqty());
                            ilpack50literqty.setEnabled(false);
                            ilpack210literqty.setText("IndusPack 210 Liter Qty :- " + obj.getIlpackof210ltrqty());
                            ilpack210literqty.setEnabled(false);
                            ilpackboxbucketqty.setText("IndusPack BoxBucket Qty :- " + obj.getIlpackofboxbuxketltrqty());
                            ilpackboxbucketqty.setEnabled(false);
                            industotqty.setText("IndusPack Total Qty :- " + obj.getIltotqty());
                            industotqty.setEnabled(false);
                            industotwight.setText("IndusPack Total Weight :- " + obj.getIlweight());
                            industotwight.setEnabled(false);
                        } else {
                            indutialpacktitle.setVisibility(View.VISIBLE);
                            lnlindustrialbarrel.setVisibility(View.VISIBLE);
                            ilpack10literqty.setText("IndusPack 10 Liter Qty :- " + obj.getIlpackof10ltrqty());
                            ilpack10literqty.setEnabled(false);
                            ilpack18literqty.setText("IndusPack 18 Liter Qty :- " + obj.getIlpackof18ltrqty());
                            ilpack18literqty.setEnabled(false);
                            ilpack20literqty.setText("IndusPack 20 Liter Qty :- " + obj.getIlpackof20ltrqty());
                            ilpack20literqty.setEnabled(false);
                            ilpack26literqty.setText("IndusPack 26 Liter Qty :- " + obj.getIlpackof26ltrqty());
                            ilpack26literqty.setEnabled(false);
                            ilpack50literqty.setText("IndusPack 50 Liter Qty :- " + obj.getIlpackof50ltrqty());
                            ilpack50literqty.setEnabled(false);
                            ilpack210literqty.setText("IndusPack 210 Liter Qty :- " + obj.getIlpackof210ltrqty());
                            ilpack210literqty.setEnabled(false);
                            ilpackboxbucketqty.setText("IndusPack BoxBucket Qty :- " + obj.getIlpackofboxbuxketltrqty());
                            ilpackboxbucketqty.setEnabled(false);
                            industotqty.setText("IndusPack Total Qty :- " + obj.getIltotqty());
                            industotqty.setEnabled(false);
                            industotwight.setText("IndusPack Total Weight :- " + obj.getIlweight());
                            industotwight.setEnabled(false);

                            smallpacktitle.setVisibility(View.VISIBLE);
                            lnlsmallpackqty.setVisibility(View.VISIBLE);
                            splpack7literqty.setText("SmallPack 7 Liter Qty :- " + obj.getSplpackof7ltrqty());
                            splpack7literqty.setEnabled(false);
                            splpack7_5literqty.setText("SmallPack 7.5 Liter Qty :- " + obj.getSplpackof7_5ltrqty());
                            splpack7_5literqty.setEnabled(false);
                            splpack8_5literqty.setText("SmallPack 8.5 Liter Qty :- " + obj.getSplpackof8_5ltrqty());
                            splpack8_5literqty.setEnabled(false);
                            splpack10literqty.setText("SmallPack 10 Liter Qty :- " + obj.getSplpackof10ltrqty());
                            splpack10literqty.setEnabled(false);
                            splpack11literqty.setText("SmallPack 11 Liter Qty :- " + obj.getSplpackof11ltrqty());
                            splpack11literqty.setEnabled(false);
                            splpack12literqty.setText("SmallPack 12 Liter Qty :- " + obj.getSplpackof12ltrqty());
                            splpack12literqty.setEnabled(false);
                            splpack13literqty.setText("SmallPack 13 Liter Qty :- " + obj.getSplpackof13ltrqty());
                            splpack13literqty.setEnabled(false);
                            splpack15literqty.setText("SmallPack 15 Liter Qty :- " + obj.getSplpackof15ltrqty());
                            splpack15literqty.setEnabled(false);
                            splpack18literqty.setText("SmallPack 18 Liter Qty :- " + obj.getSplpackof18ltrqty());
                            splpack18literqty.setEnabled(false);
                            splpack20literqty.setText("SmallPack 20 Liter Qty :- " + obj.getSplpackof20ltrqty());
                            splpack20literqty.setEnabled(false);
                            splpack26literqty.setText("SmallPack 26 Liter Qty :- " + obj.getSplpackof26ltrqty());
                            splpack26literqty.setEnabled(false);
                            splpack50literqty.setText("SmallPack 50 Liter Qty :- " + obj.getSplpackof50ltrqty());
                            splpack50literqty.setEnabled(false);
                            splpack210literqty.setText("SmallPack 210 Liter Qty :- " + obj.getSplpackof210ltrqty());
                            splpack210literqty.setEnabled(false);
                            splpackboxbucketqty.setText("SmallPack BoxBucket Qty :- " + obj.getSplpackofboxbuxketltrqty());
                            splpackboxbucketqty.setEnabled(false);
                            smalltotqty.setText("SmallPack Total Qty :- " + obj.getSpltotqty());
                            smalltotqty.setEnabled(false);
                            smalltotweight.setText("SmallPack Weight :- " + obj.getSplweight());
                            smalltotweight.setEnabled(false);

                        }
                        /*erqty.setText(obj.getIltotqty());
                        erqty.setEnabled(false);
                        etqtyspl.setText(obj.getSpltotqty());
                        etqtyspl.setEnabled(false);*/
                        /*qtyuom.setText(getWeightUnit(obj.getOutTotalQtyUOM()));
                        qtyuom.setEnabled(false);*/
                    } else {
                        Toasty.error(OutwardOut_Truck_Security.this, "This Vehicle Number Is Not Available..!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("Retrofit", "Error" + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Response_Outward_Security_Fetching>> call, Throwable t) {

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
//        intime,serialnumber,vehiclenumber,invoice,party,gooddis,qty,uom1,netweight,uom2,outtime,sign,remark;

        String lrCopySelection = Trasnportyes.isChecked() ? "Yes" : "No";
        String tremselection = tremyes.isChecked() ? "Yes" : "No";
        String ewayselection = ewayyes.isChecked() ? "Yes" : "No";
        String testreselection = testyes.isChecked() ? "Yes" : "No";
        String invoiceselection = invoiceyes.isChecked() ? "Yes" : "No";
        String outTime = getCurrentTime();
        String etintime = intime.getText().toString().trim();
        String etsign = sign.getText().toString().trim();
        String etremark = remark.getText().toString().trim();
//        String etinvoice = invoice.getText().toString().trim();
        String etgooddis = gooddis.getText().toString().trim();


//        int qtyuom = Integer.parseInt(qtyUomNumericValue.toString().trim());
//        int netweuom = Integer.parseInt(netweuomvalue.toString().trim());
        if (etintime.isEmpty() || etsign.isEmpty() || etremark.isEmpty()) {
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
        } else {
            Model_OutwardOut_Truck_Security modelOutwardOutTruckSecurity = new Model_OutwardOut_Truck_Security(OutwardId, "",
                    "", etgooddis, etsign, lrCopySelection, tremselection, ewayselection, testreselection, invoiceselection,
                    outTime, EmployeId, 'S', inOut, vehicleType, etremark);
            dialogHelper.showConfirmationDialog(this, () -> {
                dialogHelper.showProgressDialog(this); // Show progress when confirmed
                Call<Boolean> call = outwardTanker.updateout_Truck_wardoutsecurity(modelOutwardOutTruckSecurity);
                call.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if (response.isSuccessful() && response.body() && response.body()) {
                            dialogHelper.hideProgressDialog(); // Hide after response
                            makeNotification(svehicleno);
                            makeNotification(svehicleno, outTime);
                            Toasty.success(OutwardOut_Truck_Security.this, "Data Inserted Succesfully !", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(OutwardOut_Truck_Security.this, Grid_Outward.class));
                            finish();
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
                        Toasty.error(OutwardOut_Truck_Security.this, "failed", Toast.LENGTH_SHORT).show();
                    }
                });
            });
        }
    }

    public void makeNotification(String vehicleNumber) {
        FcmNotificationsSender notificationsSender = new FcmNotificationsSender(
                "/topics/all",
                "Outward Truck Out Security Process Done..!",
                "This Vehicle:-" + vehicleNumber + " has Completed The Outward Truck Process",
                getApplicationContext(),
                OutwardOut_Truck_Security.this
        );
        notificationsSender.triggerSendNotification();
    }

    public void outwardoutsecpending(View view) {
        Intent intent = new Intent(this, Grid_Outward.class);
        startActivity(intent);
    }
}