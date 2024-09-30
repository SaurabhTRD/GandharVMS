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
import android.widget.TextView;
import android.widget.Toast;

import com.android.gandharvms.FcmNotificationsSender;
import com.android.gandharvms.Global_Var;
import com.android.gandharvms.LoginWithAPI.Login;
import com.android.gandharvms.LoginWithAPI.LoginMethod;
import com.android.gandharvms.LoginWithAPI.ResponseModel;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.Menu;
import com.android.gandharvms.OutwardOutTankerBilling.ot_outBilling;
import com.android.gandharvms.OutwardOut_Truck;
import com.android.gandharvms.Outward_Tanker_Billing.Outward_Tanker_Billinginterface;
import com.android.gandharvms.Outward_Tanker_Billing.Respons_Outward_Tanker_Billing;
import com.android.gandharvms.Outward_Tanker_Security.Grid_Outward;
import com.android.gandharvms.Outward_Tanker_Security.Outward_RetroApiclient;
import com.android.gandharvms.Outward_Truck_Billing.Model_OutwardOut_Truck_Billing;
import com.android.gandharvms.R;
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

public class OutwardOut_Truck_Billing extends AppCompatActivity {

    EditText intime,serialnumber,vehiclenumber,etoanumber,ettramsname,etdrivermob,etgrs,ettare,etnet,etseal,etbatch,etdensity,etremark,invoicenum,totalqty,industotalqty;
    Button submit,billcomp;
    FirebaseFirestore dbroot;
    TimePickerDialog tpicker;
    Calendar calendar = Calendar.getInstance();
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private final String EmployeId = Global_Var.getInstance().EmpId;
    private int OutwardId;
    private Outward_Tanker_Billinginterface outwardTankerBillinginterface;
    private LoginMethod userDetails;
    private String token;
    private String bvehicleno;
    String[] items1 = {"Ton", "Litre", "KL", "Kgs", "pcs"};
    AutoCompleteTextView totqtyautoCompleteTextView2;
    ArrayAdapter<String> nettotqtyuomdrop;
    Map<String, Integer> totqtyUomMapping= new HashMap<>();
    Integer nettotqtyuomvalue = 3;
    private int netweuom;
//    public String userialnumber,uvehiclenumer;

    ImageView btnlogout,btnhome;
    TextView username,empid;

    public static String Tanker;
    public static String Truck;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outward_out_truck_billing);

        FirebaseMessaging.getInstance().subscribeToTopic(token);

        outwardTankerBillinginterface = Outward_RetroApiclient.outwardTankerBillinginterface();
        userDetails = RetroApiClient.getLoginApi();

        intime=findViewById(R.id.etintime);
        serialnumber=findViewById(R.id.etserialnumber);
        vehiclenumber=findViewById(R.id.etvehical);
        etoanumber=findViewById(R.id.etoanumber);
        ettramsname=findViewById(R.id.ettarnspname);
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
        industotalqty = findViewById(R.id.industotalqty);



        submit = findViewById(R.id.submit);
        billcomp = findViewById(R.id.truckotoutbillingcompleted);
        dbroot= FirebaseFirestore.getInstance();

        btnhome = findViewById(R.id.btn_homeButton);
        btnlogout=findViewById(R.id.btn_logoutButton);
        username=findViewById(R.id.tv_username);
        empid=findViewById(R.id.tv_employeeId);

        String userName=Global_Var.getInstance().Name;
        String empId=Global_Var.getInstance().EmpId;

        username.setText(userName);
        empid.setText(empId);
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OutwardOut_Truck_Billing.this, Login.class));
            }
        });
        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OutwardOut_Truck_Billing.this, Menu.class));
            }
        });

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
        billcomp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(OutwardOut_Truck_Billing.this, Billing_Out_OR_Complete.class));
            }
        });

//        totqtyautoCompleteTextView2 = findViewById(R.id.oroutbiltotalQuantityUOM);
//        totqtyUomMapping= new HashMap<>();
//        totqtyUomMapping.put("NA",1);
//        totqtyUomMapping.put("Ton", 2);
//        totqtyUomMapping.put("Litre", 3);
//        totqtyUomMapping.put("KL", 4);
//        totqtyUomMapping.put("Kgs", 5);
//        totqtyUomMapping.put("pcs", 6);
//
//        nettotqtyuomdrop = new ArrayAdapter<String>(this,R.layout.or_uom_outbill,new ArrayList<>(totqtyUomMapping.keySet()));
//        totqtyautoCompleteTextView2.setAdapter(nettotqtyuomdrop);
//        totqtyautoCompleteTextView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String item2 = parent.getItemAtPosition(position).toString();
//                nettotqtyuomvalue = totqtyUomMapping.get(item2);
//                if (nettotqtyuomvalue != null){
//                    Toasty.success(OutwardOut_Truck_Billing.this, "Total-Quantity:- " + item2 + " Selected", Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    Toasty.warning(OutwardOut_Truck_Billing.this, "Unknown Total-QuantityUom:- " + item2, Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

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
                if (response.isSuccessful()){
                    List<ResponseModel> userList = response.body();
                    if (userList != null){
                        for (ResponseModel resmodel : userList){
                            String specificRole = "Security";
                            if (specificRole.equals(resmodel.getDepartment())) {
                                token = resmodel.getToken();

                                FcmNotificationsSender notificationsSender = new FcmNotificationsSender(
                                        token,
                                        "OutwardOut Truck Billing Process Done..!",
                                        "Vehicle Number:-" + vehicleNumber + " has completed Weighment process at " + outTime,
                                        getApplicationContext(),
                                        OutwardOut_Truck_Billing.this
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
                Toasty.error(OutwardOut_Truck_Billing.this, "failed..!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void FetchVehicleDetails(@NonNull String vehicleNo, String vehicleType, char NextProcess, char inOut) {
        Call<Respons_Outward_Tanker_Billing> call = outwardTankerBillinginterface.outwardbillingfetching(vehicleNo,vehicleType,NextProcess,inOut);
        call.enqueue(new Callback<Respons_Outward_Tanker_Billing>() {
            @Override
            public void onResponse(Call<Respons_Outward_Tanker_Billing> call, Response<Respons_Outward_Tanker_Billing> response) {
                if (response.isSuccessful()){
                    Respons_Outward_Tanker_Billing data = response.body();
                    if (data.getVehicleNumber() != "" && data.getVehicleNumber()!= null){
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
                        industotalqty.setText(data.getIltotqty());
                        industotalqty.setEnabled(false);


//                        userialnumber = data.getSerialNumber();
//                        uvehiclenumer = data.getVehicleNumber();

//                        etbatch.setText(data.getBatchNo());
//                        etbatch.setEnabled(false);
//                        etdensity.setText(String.valueOf(data.getDensity_29_5C()));
//                        etdensity.setEnabled(false);

                        bvehicleno= data.getVehicleNumber();

                    }else {
                        Toasty.error(OutwardOut_Truck_Billing.this, "This Vehicle Number Is Not Available..!", Toast.LENGTH_SHORT).show();
                    }
                }else {
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

    public void insert(){
        String uvehiclenumer = vehiclenumber.getText().toString().trim();
        String userialnumber = serialnumber.getText().toString().trim();
        String uintime = intime.getText().toString().trim();
        String ubatch = etbatch.getText().toString().trim();
        String obOutTime=getCurrentTime();
        String uremark = etremark.getText().toString().trim();
        String uinvoicenum = invoicenum.getText().toString().trim();
        String uqty = totalqty.getText().toString().trim();

        if (uintime.isEmpty()|| ubatch.isEmpty()||obOutTime.isEmpty()){
            Toasty.warning(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
        }else {
            Model_OutwardOut_Truck_Billing requestoutBilmodel = new Model_OutwardOut_Truck_Billing(userialnumber,uvehiclenumer,OutwardId,uintime,obOutTime,uqty,netweuom,
                    uinvoicenum,uremark,ubatch,'S',inOut,vehicleType,EmployeId,EmployeId);
            Call<Boolean> call = outwardTankerBillinginterface.updateouttruckbilling(requestoutBilmodel);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body() != null && response.body()==true){
                        makeNotification(bvehicleno, obOutTime);
                        Toasty.success(OutwardOut_Truck_Billing.this, "Data Inserted Successfully", Toast.LENGTH_SHORT,true).show();
                        startActivity(new Intent(OutwardOut_Truck_Billing.this, OutwardOut_Truck.class));
                        finish();
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
                    Toasty.error(OutwardOut_Truck_Billing.this, "failed..!", Toast.LENGTH_SHORT).show();
                }
            });
        }


    }
    public void Outwardoutbillingpending(View view){
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