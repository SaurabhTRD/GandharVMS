package com.android.gandharvms.OutwardOutDataEntryForm_Production;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.gandharvms.FcmNotificationsSender;
import com.android.gandharvms.Global_Var;
import com.android.gandharvms.LoginWithAPI.LoginMethod;
import com.android.gandharvms.LoginWithAPI.ResponseModel;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.OutwardOut_Tanker;
import com.android.gandharvms.Outward_Tanker_Security.Grid_Outward;
import com.android.gandharvms.Outward_Tanker_Security.Outward_RetroApiclient;
import com.android.gandharvms.R;
import com.android.gandharvms.outward_Tanker_Lab_forms.Lab_Model__Outward_Tanker;
import com.android.gandharvms.outward_Tanker_Lab_forms.Outward_Tanker_Lab;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class DataEntryForm_Production extends AppCompatActivity {
    EditText odeintime,odeserialnumber,odevehiclenumber,odedensity,odesealnumber,odeetremark,party,location,oanum,batch,product;
    Button odesubmit,completd;
    TimePickerDialog tpicker;

    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private final String EmployeId = Global_Var.getInstance().EmpId;
    private Outward_Tanker_Lab outwardTankerLab;
    private LoginMethod userDetails;
    private String token;
    private int OutwardId;
    private String odvehiclenum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_entry_form_production);

        outwardTankerLab = Outward_RetroApiclient.outwardTankerLab();

        odeintime=findViewById(R.id.etoutdataentryintime);
        odeserialnumber=findViewById(R.id.etoutdataentryserialnumber);
        odevehiclenumber=findViewById(R.id.etoutdataentryvehicleno);
        odedensity=findViewById(R.id.etoutdataentrydensity);
        odesealnumber=findViewById(R.id.etoutdataentrysealnumber);
        odeetremark=findViewById(R.id.etoutdataentryremakr);
        completd =findViewById(R.id.outdataentrycompletd);

        party = findViewById(R.id.etpartyname);
        location = findViewById(R.id.etlocation);
        oanum = findViewById(R.id.etoanum);
        batch = findViewById(R.id.etbatchno);
        product = findViewById(R.id.etproduct);

        odesubmit=findViewById(R.id.etoutdataentrysubmit);

        userDetails = RetroApiClient.getLoginApi();
        FirebaseMessaging.getInstance().subscribeToTopic(token);
        odesubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
            }
        });
        completd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DataEntryForm_Production.this, OT_Completed_outdataentry.class));
            }
        });

        odeintime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                String time =  format.format(calendar.getTime());
                odeintime.setText(time);
            }
        });

        odevehiclenumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    FetchVehicleDetails(odevehiclenumber.getText().toString().trim(), vehicleType, nextProcess, inOut);
                }
            }
        });

        if (getIntent().hasExtra("vehiclenum")) {
            FetchVehicleDetails(getIntent().getStringExtra("vehiclenum"), Global_Var.getInstance().MenuType, nextProcess, inOut);
        }
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
                        odeserialnumber.setText(data.getSerialNumber());
                        odeserialnumber.setEnabled(false);
                        odevehiclenumber.setText(data.getVehicleNumber());
                        odevehiclenumber.setEnabled(false);
                        odvehiclenum=data.getVehicleNumber();
                        odedensity.setText(String.valueOf(data.getDensity_29_5C()));
                        odedensity.setEnabled(false);
                        odesealnumber.setText(String.valueOf(data.getSealNumber()));
                        odesealnumber.setEnabled(false);

                        party.setText(data.getCustomerName());
                        party.setEnabled(false);
                        location.setText(data.getLocation());
                        location.setEnabled(false);
                        oanum.setText(data.getOAnumber());
                        oanum.setEnabled(false);
                        product.setText(data.getProductName());
                        product.setEnabled(false);
                        batch.setText(data.getBatchNo());
                        batch.setEnabled(false);


                        /*odeintime.requestFocus();
                        odeintime.callOnClick();*/
                    } else {
                        Toasty.error(DataEntryForm_Production.this, "This Vehicle Number Is Not Available..!", Toast.LENGTH_SHORT).show();
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

    public void makeNotification(String vehicleNumber) {
        Call<List<ResponseModel>> call = userDetails.getUsersListData();
        call.enqueue(new Callback<List<ResponseModel>>() {
            @Override
            public void onResponse(Call<List<ResponseModel>> call, Response<List<ResponseModel>> response) {
                if (response.isSuccessful()){
                    List<ResponseModel> userList = response.body();
                    if (userList != null){
                        for (ResponseModel resmodel : userList){
                            String specificRole = "Billing";
                            if (specificRole.equals(resmodel.getDepartment())) {
                                token = resmodel.getToken();

                                FcmNotificationsSender notificationsSender = new FcmNotificationsSender(
                                        token,
                                        "Outward Tanker OutDataEntryForm Process Done",
                                        "Vehicle Number:-" + vehicleNumber + " has Completed OutDataEntryForm Process",
                                        getApplicationContext(),
                                        DataEntryForm_Production.this
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
                Toasty.error(DataEntryForm_Production.this, "failed..!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void update() {
        String odfintime = odeintime.getText().toString().trim();
        String odfremark=odeetremark.getText().toString().trim();
        String odfouttime=getCurrentTime();
        if (odfintime.isEmpty()|| odfremark.isEmpty()){
            Toasty.warning(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
        }else {
            otoutDataEntryProduction_RequestModel requestmodeldata = new otoutDataEntryProduction_RequestModel(OutwardId, odfintime, odfouttime,
                    odfremark, 'B', inOut, vehicleType, EmployeId);
            Call<Boolean> call = outwardTankerLab.updateDataEntryFormProduction(requestmodeldata);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body() && response.body() == true) {
                        makeNotification(odvehiclenum);
                        Toasty.success(DataEntryForm_Production.this, "Data Inserted Successfully", Toast.LENGTH_SHORT, true).show();
                        startActivity(new Intent(DataEntryForm_Production.this, OutwardOut_Tanker.class));
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
                    Toasty.error(DataEntryForm_Production.this, "failed..!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void outwardoutdataentryclick(View view) {
        Intent intent = new Intent(this, Grid_Outward.class);
        startActivity(intent);
    }

    public void otoutdataentrycompletedclick(View view) {
        /*Intent intent = new Intent(this, it_in_weigh_Completedgrid.class);
        startActivity(intent);*/
    }
}