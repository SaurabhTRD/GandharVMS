package com.android.gandharvms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.gandharvms.LoginWithAPI.LoginMethod;
import com.android.gandharvms.LoginWithAPI.ResponseModel;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.OutwardOutTankerBilling.ot_outBilling;
import com.android.gandharvms.OutwardOutTankerBilling.ot_outBillingRequestModel;
import com.android.gandharvms.Outward_Tanker_Billing.Outward_Tanker_Billinginterface;
import com.android.gandharvms.Outward_Tanker_Billing.Respons_Outward_Tanker_Billing;
import com.android.gandharvms.Outward_Tanker_Security.Grid_Outward;
import com.android.gandharvms.Outward_Tanker_Security.Outward_RetroApiclient;
import com.android.gandharvms.Outward_Tanker_Weighment.Outward_Tanker_weighment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

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

public class OutwardOut_Truck_Billing extends AppCompatActivity {

    EditText intime,serialnumber,vehiclenumber,etoanumber,ettramsname,etdrivermob,etgrs,ettare,etnet,etseal,etbatch,etdensity,etremark;
    Button submit;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outward_out_truck_billing);

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
        etdensity = findViewById(R.id.etdensity);
        etremark = findViewById(R.id.etremark);



        submit = findViewById(R.id.submit);
        dbroot= FirebaseFirestore.getInstance();

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
                                notificationsSender.SendNotifications();
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
//                        etbatch.setText(data.getBatchNo());
//                        etbatch.setEnabled(false);
                        etdensity.setText(String.valueOf(data.getDensity_29_5C()));
                        etdensity.setEnabled(false);

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
        String uintime = intime.getText().toString().trim();
        String ubatch = etbatch.getText().toString().trim();
        String obOutTime=getCurrentTime();
        String uremark = etremark.getText().toString().trim();

        if (uintime.isEmpty()|| ubatch.isEmpty()||obOutTime.isEmpty()){
            Toasty.warning(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
        }else {
            ot_outBillingRequestModel requestoutBilmodel = new ot_outBillingRequestModel(OutwardId,uintime,obOutTime,"",
                    0,"",uremark,'S',inOut,vehicleType,EmployeId);
            Call<Boolean> call = outwardTankerBillinginterface.UpdateOutBillingDetails(requestoutBilmodel);
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
}