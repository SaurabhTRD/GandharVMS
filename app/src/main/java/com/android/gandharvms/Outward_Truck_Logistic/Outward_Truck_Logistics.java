package com.android.gandharvms.Outward_Truck_Logistic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.gandharvms.FcmNotificationsSender;
import com.android.gandharvms.Global_Var;
import com.android.gandharvms.InwardCompletedGrid.GridCompleted;
import com.android.gandharvms.Inward_Tanker;
import com.android.gandharvms.Inward_Tanker_Laboratory.InTanLabResponseModel;
import com.android.gandharvms.Inward_Tanker_Laboratory.Inward_Tanker_Laboratory;
import com.android.gandharvms.LoginWithAPI.LoginMethod;
import com.android.gandharvms.LoginWithAPI.Logistic;
import com.android.gandharvms.LoginWithAPI.ResponseModel;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.Outward_Tanker_Security.Grid_Outward;
import com.android.gandharvms.Outward_Truck;
import com.android.gandharvms.Outward_Truck_Billing.Outward_Truck_Billing;
import com.android.gandharvms.R;
import com.android.gandharvms.outward_Tanker_Lab_forms.Outward_Tanker_Laboratory;
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

public class Outward_Truck_Logistics extends AppCompatActivity {

    EditText intime, serialnumber, vehiclenumber, transporter, place, oanumber, remark,customername,howqty;
    Button submit,btnlogisticcompletd;
    FirebaseFirestore dbroot;
    TimePickerDialog tpicker;
    Calendar calendar = Calendar.getInstance();
    private Logistic logisticdetails;
    private int inwardid;
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private final String EmployeId = Global_Var.getInstance().EmpId;
    SimpleDateFormat dtFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
    DatePickerDialog picker;
    private LoginMethod userDetails;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outward_truck_logistics);
        userDetails = RetroApiClient.getLoginApi();

        intime = findViewById(R.id.etintime);
        serialnumber = findViewById(R.id.etserialnumber);
        vehiclenumber = findViewById(R.id.etvehicleno);
        transporter = findViewById(R.id.ettransport);
        place = findViewById(R.id.etplace);
        oanumber = findViewById(R.id.etoanumber);
        remark = findViewById(R.id.etremark);
        howqty = findViewById(R.id.etloadedmaterqty);

        customername = findViewById(R.id.etcustomername);

        submit = findViewById(R.id.etssubmit);
        btnlogisticcompletd = findViewById(R.id.outwardtruckcompletedlogistic);
        dbroot = FirebaseFirestore.getInstance();

        logisticdetails = RetroApiClient.getLogisticDetails();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert();
            }
        });
        btnlogisticcompletd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Outward_Truck_Logistics.this, Logi_OR_Complete.class));
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
        intime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                String time =  format.format(calendar.getTime());
                intime.setText(time);
            }
        });

    }

    private String getCurrentTime() {
        // Get the current time
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(new Date());
    }

    public void FetchVehicleDetails(@NonNull String vehicleNo, String vehicleType, char NextProcess, char inOut) {
        Call<InTrLogisticResponseModel> call = logisticdetails.getLogisticbyfetchVehData(vehicleNo, vehicleType, NextProcess, inOut);
        call.enqueue(new Callback<InTrLogisticResponseModel>() {
            @Override
            public void onResponse(Call<InTrLogisticResponseModel> call, Response<InTrLogisticResponseModel> response) {
                if (response.isSuccessful()) {
                    InTrLogisticResponseModel data = response.body();
                    if (data.getVehicleNumber() != "" && data.getVehicleNumber() != null) {
                        inwardid = data.getOutwardId();
                        vehiclenumber.setText(data.getVehicleNumber());
                        vehiclenumber.setEnabled(false);
                        serialnumber.setText(data.getSerialNumber());
                        serialnumber.setEnabled(false);
                        transporter.setText(data.getTransportName());
                        transporter.setEnabled(false);
                        place.setText(data.getPlace());
                        place.setEnabled(false);
                        intime.requestFocus();
                        intime.callOnClick();
                    } else {
                        Toasty.success(Outward_Truck_Logistics.this, "Vehicle Is Not Available", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("Retrofit", "Error Response Body: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<InTrLogisticResponseModel> call, Throwable t) {
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
                Toasty.error(Outward_Truck_Logistics.this, "failed..!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void makeNotificationLogistic(String vehicleNumber, String outTime) {
        Call<List<ResponseModel>> call = userDetails.getUsersListData();
        call.enqueue(new Callback<List<ResponseModel>>() {
            @Override
            public void onResponse(Call<List<ResponseModel>> call, Response<List<ResponseModel>> response) {
                if (response.isSuccessful()){
                    List<ResponseModel> userList = response.body();
                    if (userList != null){
                        for (ResponseModel resmodel : userList){
                            String specificRole = "Weighment";
                            if (specificRole.equals(resmodel.getDepartment())) {
                                token = resmodel.getToken();

                                FcmNotificationsSender notificationsSender = new FcmNotificationsSender(
                                        token,
                                        "Outward Truck Logistic Process Done..!",
                                        "Vehicle Number:-" + vehicleNumber + " has completed Logistic process at " + outTime,
                                        getApplicationContext(),
                                        Outward_Truck_Logistics.this
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
                Toasty.error(Outward_Truck_Logistics.this, "failed..!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void insert() {
        String etintime = intime.getText().toString().trim();
        String etserialnumber = serialnumber.getText().toString().trim();
        String etvehiclenumber = vehiclenumber.getText().toString().trim();
        String ettransporter = transporter.getText().toString().trim();
        String etplace = place.getText().toString().trim();
        String etoanumber = oanumber.getText().toString().trim();
        String etremark = remark.getText().toString().trim();
        String outTime = getCurrentTime();
        String ucustoname = customername.getText().toString().trim();
        int uhowqty = Integer.parseInt(howqty.getText().toString().trim());

        if (etintime.isEmpty() || etserialnumber.isEmpty() || etvehiclenumber.isEmpty() || ettransporter.isEmpty() ||
                etoanumber.isEmpty() || etremark.isEmpty()) {
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
        } else {
            InTrLogisticRequestModel trucklogmodel = new InTrLogisticRequestModel(inwardid, etintime, outTime,
                    EmployeId, EmployeId, etremark, 'G', etserialnumber, etvehiclenumber,
                    etoanumber, 'W', inOut, vehicleType,ucustoname,uhowqty);
            Call<Boolean> call = logisticdetails.insertLogisticData(trucklogmodel);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body() != null && response.body()==true) {
                        makeNotificationLogistic(etvehiclenumber, outTime);
                        Log.d("Registration", "Response Body: " + response.body());
                        Toasty.success(Outward_Truck_Logistics.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Outward_Truck_Logistics.this, Outward_Truck.class));
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
                    Toasty.error(Outward_Truck_Logistics.this, "failed..!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    public void  outrucklogipending(View view){
        Intent intent = new Intent(this, Grid_Outward.class);
        startActivity(intent);
    }
}