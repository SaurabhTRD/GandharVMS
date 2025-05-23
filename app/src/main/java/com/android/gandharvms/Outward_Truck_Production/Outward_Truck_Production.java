package com.android.gandharvms.Outward_Truck_Production;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.gandharvms.FcmNotificationsSender;
import com.android.gandharvms.Global_Var;
import com.android.gandharvms.LoginWithAPI.Login;
import com.android.gandharvms.LoginWithAPI.LoginMethod;
import com.android.gandharvms.LoginWithAPI.ResponseModel;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.Menu;
import com.android.gandharvms.NotificationAlerts.NotificationCommonfunctioncls;
import com.android.gandharvms.OutwardOut_Truck;
import com.android.gandharvms.Outward_Tanker_Security.Grid_Outward;
import com.android.gandharvms.Outward_Tanker_Security.Outward_RetroApiclient;
import com.android.gandharvms.Outward_Truck;
import com.android.gandharvms.Outward_Truck_Billing.Outward_Truck_Billing;
import com.android.gandharvms.Outward_Truck_Dispatch.Model_Outward_Truck_Dispatch;
import com.android.gandharvms.Outward_Truck_Dispatch.Outward_Truck_Dispatch;
import com.android.gandharvms.Outward_Truck_Dispatch.Outward_Truck_interface;
import com.android.gandharvms.Outward_Truck_Logistic.Outward_Truck_Logistics;
import com.android.gandharvms.R;
import com.android.gandharvms.Util.dialogueprogreesbar;
import com.android.gandharvms.outward_Tanker_Lab_forms.Outward_Tanker_Lab;
import com.google.firebase.firestore.FirebaseFirestore;
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

public class Outward_Truck_Production extends NotificationCommonfunctioncls {

    public static String Tanker;
    public static String Truck;
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private final String EmployeId = Global_Var.getInstance().EmpId;
    EditText intime, serialnumber, vehiclenumber, typepack, signdis, dtdis, signsec, dtsec, signweigh, dtweigh, tare, etremark;
    Button submit;
    FirebaseFirestore dbroot;
    TimePickerDialog tpicker;
    Calendar calendar = Calendar.getInstance();
    ImageView btnlogout, btnhome;
    TextView username, empid;
    dialogueprogreesbar dialogHelper = new dialogueprogreesbar();
    private int OutwardId;
    private Outward_Tanker_Lab outwardTankerLab;
    private Outward_Truck_interface outwardTruckInterface;
    private Outward_Truck_Production_interface outwardTruckProductionInterface;
    private LoginMethod userDetails;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outward_truck_production);
        outwardTankerLab = Outward_RetroApiclient.outwardTankerLab();
        outwardTruckInterface = Outward_RetroApiclient.outwardtruckdispatch();
        userDetails = RetroApiClient.getLoginApi();

        outwardTruckProductionInterface = Outward_RetroApiclient.outwardTruckProductionInterface();

        FirebaseMessaging.getInstance().subscribeToTopic(token);

        setupHeader();
        intime = findViewById(R.id.etintime);
        serialnumber = findViewById(R.id.etserialnumber);
        vehiclenumber = findViewById(R.id.etvehicleno);
        typepack = findViewById(R.id.typeofpackproduction);
        signdis = findViewById(R.id.etdispatchofficer);
        signsec = findViewById(R.id.etsecurityofficer);
        dtsec = findViewById(R.id.etdtsecurity);
        signweigh = findViewById(R.id.etweighmentofficer);
        dtweigh = findViewById(R.id.etdtweighment);
        etremark = findViewById(R.id.etremark);

        submit = findViewById(R.id.etssubmit);
        dbroot = FirebaseFirestore.getInstance();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert();
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
                            String specificRole = "Billing";
                            if (specificRole.equals(resmodel.getDepartment())) {
                                token = resmodel.getToken();

                                FcmNotificationsSender notificationsSender = new FcmNotificationsSender(
                                        token,
                                        "Outward Truck Production(Data Entry) Process Done..!",
                                        "Vehicle Number:-" + vehicleNumber + " has completed Production(Data Entry) process at " + outTime,
                                        getApplicationContext(),
                                        Outward_Truck_Production.this
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
                Toasty.error(Outward_Truck_Production.this, "failed..!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void FetchVehicleDetails(@NonNull String vehicleNo, String vehicleType, char nextProcess, char inOut) {
        Call<Model_Outward_Truck_Dispatch> call = outwardTruckInterface.fetchdispatch(vehicleNo, vehicleType, nextProcess, inOut);
        call.enqueue(new Callback<Model_Outward_Truck_Dispatch>() {
            @Override
            public void onResponse(Call<Model_Outward_Truck_Dispatch> call, Response<Model_Outward_Truck_Dispatch> response) {
                if (response.isSuccessful()) {
                    Model_Outward_Truck_Dispatch data = response.body();
                    if (data.getVehicleNumber() != "" && data.getVehicleNumber() != null) {
                        OutwardId = data.getOutwardId();
                        serialnumber.setText(data.getSerialNumber());
                        vehiclenumber.setText(data.getVehicleNumber());
                        signdis.setText(data.getSplsign());
                        typepack.setText(data.getIlsign());
                        /*if(data.ilsign=="" && data.getIlsign()== null)
                        {
                            typepack.setVisibility(View.GONE);
                            signdis.setVisibility(View.VISIBLE);
                            signdis.setText(data.getSplsign());
                        } else if (data.splsign=="" && data.getSplsign()== null) {
                            signdis.setVisibility(View.GONE);
                            typepack.setVisibility(View.VISIBLE);
                            typepack.setText(data.getIlsign());
                        }else {
                            signdis.setVisibility(View.VISIBLE);
                            signdis.setText(data.getSplsign());
                            typepack.setVisibility(View.VISIBLE);
                            typepack.setText(data.getIlsign());
                        }*/
                        signsec.setText(data.getSecurityCreatedBy());
                        dtsec.setText(data.getSecurityCreatedDate());
                        signweigh.setText(data.getWeighmentCreatedBy());
                        dtweigh.setText(data.getWeighmentCreatedDate());
                        serialnumber.setEnabled(false);
                        vehiclenumber.setEnabled(false);
                        typepack.setEnabled(false);
                        signdis.setEnabled(false);
                        signsec.setEnabled(false);
                        dtsec.setEnabled(false);
                        signsec.setEnabled(false);
                        dtweigh.setEnabled(false);
                        signweigh.setEnabled(false);
                    } else {
                        Toasty.success(Outward_Truck_Production.this, "Vehicle Is Not Available", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("Retrofit", "Error Response Body: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Model_Outward_Truck_Dispatch> call, Throwable t) {

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
                Toasty.error(Outward_Truck_Production.this, "failed..!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getCurrentTime() {
        // Get the current time
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(new Date());
    }

    public void insert() {
//        intime,serialnumber,vehiclenumber,material,oanumber;
        String etintime = intime.getText().toString().trim();
        String etserialnumber = serialnumber.getText().toString().trim();
        String etvehiclnumber = vehiclenumber.getText().toString().trim();
        String outTime = getCurrentTime();
        String uremark = etremark.getText().toString().trim();
        if (etintime.isEmpty() || etserialnumber.isEmpty() || etvehiclnumber.isEmpty()) {
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
        } else {
            Outward_Truck_Production_Model outwardTruckProductionModel = new Outward_Truck_Production_Model(OutwardId, etintime, outTime,
                    uremark, 'P', EmployeId, 'B', 'O', vehicleType);
            dialogHelper.showConfirmationDialog(this, () -> {
                dialogHelper.showProgressDialog(this); // Show progress when confirmed
                Call<Boolean> call = outwardTruckProductionInterface.updateouttruckproduction(outwardTruckProductionModel);
                call.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if (response.isSuccessful() && response.body() && response.body()) {
                            dialogHelper.hideProgressDialog(); // Hide after response
                            makeNotification(etvehiclnumber, outTime);
                            Toasty.success(Outward_Truck_Production.this, "Data Inserted Successfully", Toast.LENGTH_SHORT, true).show();
                            startActivity(new Intent(Outward_Truck_Production.this, Grid_Outward.class));
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
                        Toasty.error(Outward_Truck_Production.this, "failed..!", Toast.LENGTH_SHORT).show();
                    }
                });

            });
        }
    }

    public void outwardtruckpropending(View view) {
        Intent intent = new Intent(this, Grid_Outward.class);
        startActivity(intent);
    }

    public void Viewclick(View view) {
        Intent intent = new Intent(this, OR_DataEntry_Completed_Listing.class);
        startActivity(intent);
    }
}