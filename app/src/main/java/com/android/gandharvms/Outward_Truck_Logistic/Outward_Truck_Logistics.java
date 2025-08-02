package com.android.gandharvms.Outward_Truck_Logistic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.gandharvms.FcmNotificationsSender;
import com.android.gandharvms.Global_Var;
import com.android.gandharvms.InwardCompletedGrid.GridCompleted;
import com.android.gandharvms.Inward_Tanker;
import com.android.gandharvms.Inward_Tanker_Laboratory.InTanLabResponseModel;
import com.android.gandharvms.Inward_Tanker_Laboratory.Inward_Tanker_Laboratory;
import com.android.gandharvms.Inward_Tanker_Security.Inward_Tanker_Security;
import com.android.gandharvms.LoginWithAPI.Login;
import com.android.gandharvms.LoginWithAPI.LoginMethod;
import com.android.gandharvms.LoginWithAPI.Logistic;
import com.android.gandharvms.LoginWithAPI.ResponseModel;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.Menu;
import com.android.gandharvms.NotificationAlerts.NotificationCommonfunctioncls;
import com.android.gandharvms.Outward_Tanker_Security.Grid_Outward;
import com.android.gandharvms.Outward_Truck;
import com.android.gandharvms.Outward_Truck_Billing.Outward_Truck_Billing;
import com.android.gandharvms.R;
import com.android.gandharvms.Util.dialogueprogreesbar;
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

public class Outward_Truck_Logistics extends NotificationCommonfunctioncls {

    public static String Tanker;
    public static String Truck;
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private final String EmployeId = Global_Var.getInstance().EmpId;
    public int uhowqty;
    EditText intime, serialnumber, vehiclenumber, transporter, place, oanumber, remark, customername, howqty, uomet;
    Button submit, btnlogisticcompletd, updatebtn;
    FirebaseFirestore dbroot;
    TimePickerDialog tpicker;
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat dtFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
    DatePickerDialog picker;
    ImageView btnlogout, btnhome;
    TextView username, empid;
    String[] uom = {"Ton", "Litre", "KL", "Kgs", "pcs", "NA"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> uomdrop;
    dialogueprogreesbar dialogHelper = new dialogueprogreesbar();
    private Logistic logisticdetails;
    private int inwardid;
    private LoginMethod userDetails;
    private String token;
    private SharedPreferences sharedPreferences;
    TextView tvAllRemarks;
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
        uomet = findViewById(R.id.etuom);
        tvAllRemarks = findViewById(R.id.irinsectv_allremarks);
        customername = findViewById(R.id.etcustomername);
        sharedPreferences = getSharedPreferences("VehicleManagementPrefs", MODE_PRIVATE);

        FirebaseMessaging.getInstance().subscribeToTopic(token);

        submit = findViewById(R.id.etssubmit);
        btnlogisticcompletd = findViewById(R.id.outwardtruckcompletedlogistic);
        updatebtn = findViewById(R.id.outwardtruckupatebtn);
        dbroot = FirebaseFirestore.getInstance();

        logisticdetails = RetroApiClient.getLogisticDetails();

        autoCompleteTextView = findViewById(R.id.etuom);
        uomdrop = new ArrayAdapter<String>(this, R.layout.in_rcs_test, uom);
        autoCompleteTextView.setAdapter(uomdrop);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedUom = parent.getItemAtPosition(position).toString();
                Toasty.success(getApplicationContext(), "UOM : " + selectedUom + " Selected", Toast.LENGTH_SHORT).show();
            }
        });
        setupHeader();
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
        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upditinsecbyinwardid();
            }
        });

        vehiclenumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String vehicltype = Global_Var.getInstance().MenuType;
                    char DeptType = Global_Var.getInstance().DeptType;
                    char InOutType = Global_Var.getInstance().InOutType;
                    FetchVehicleDetails(vehiclenumber.getText().toString().trim(), vehicltype, DeptType, InOutType);
                }
            }
        });

//        if (getIntent().hasExtra("vehiclenum")) {
//            FetchVehicleDetails(getIntent().getStringExtra("vehiclenum"), Global_Var.getInstance().MenuType, nextProcess, inOut);
//        }
        intime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                String time = format.format(calendar.getTime());
                intime.setText(time);
            }
        });

        if (sharedPreferences != null) {
            if (getIntent().hasExtra("vehiclenum")) {
                String action = getIntent().getStringExtra("Action");
                if (action != null && action.equals("Up")) {
                    FetchVehicleDetailsforUpdate(getIntent().getStringExtra("vehiclenum"), Global_Var.getInstance().MenuType, 'x', 'I');
                } else {
                    FetchVehicleDetails(getIntent().getStringExtra("vehiclenum"), Global_Var.getInstance().MenuType, nextProcess, inOut);
                }
            }
        } else {
            Log.e("MainActivity", "SharedPreferences is null");
        }

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
                        String allRemark = data.getAllORRemarks();
                        if (allRemark != null && !allRemark.trim().isEmpty()) {
                            tvAllRemarks.setText("   "+allRemark.replace(",", "\n")); // line-by-line
                        } else {
                            tvAllRemarks.setText("No system remarks.");
                        }
                    } else {
                        Toasty.success(Outward_Truck_Logistics.this, "This Vehicle Number Is Not Available", Toast.LENGTH_SHORT).show();
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

    public void FetchVehicleDetailsforUpdate(@NonNull String vehicleNo, String vehicleType, char NextProcess, char inOut) {
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
                        transporter.setEnabled(true);
                        place.setText(data.getPlace());
                        place.setEnabled(false);
                        intime.requestFocus();
                        intime.callOnClick();
                        oanumber.setText(data.getOAnumber());
                        oanumber.setEnabled(true);
                        customername.setText(data.getCustomerName());
                        customername.setEnabled(true);
                        updatebtn.setVisibility(View.VISIBLE);
                        howqty.setText(String.valueOf(data.getHowMuchQuantityFilled()));
                        howqty.setEnabled(false);
                        remark.setText(data.getRemark());
                        remark.setEnabled(false);


                    } else {
                        Toasty.error(Outward_Truck_Logistics.this, "This Vehicle Number Is Out From Factory.\n You Can Not Update", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(Outward_Truck_Logistics.this, Logi_OR_Complete.class));
                    }
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
                Toasty.error(Outward_Truck_Logistics.this, "failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void makeNotificationLogistic(String vehicleNumber, String outTime) {
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
                                        "Outward Truck Logistic Process Done..!",
                                        "Vehicle Number:-" + vehicleNumber + " has completed Logistic process at " + outTime,
                                        getApplicationContext(),
                                        Outward_Truck_Logistics.this
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
                Toasty.error(Outward_Truck_Logistics.this, "failed..!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void makeNotificationSecurity(String vehicleNumber, String OANumber) {
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
                                        "Outward Truck Logistic Process Done And Has Send OA Number",
                                        "This OA Number:-" + OANumber + " is For This Vehicle Number:- " + vehicleNumber,
                                        getApplicationContext(),
                                        Outward_Truck_Logistics.this
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
                Toasty.error(Outward_Truck_Logistics.this, "failed..!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void upditinsecbyinwardid() {
        String uptransporter = transporter.getText().toString().trim();
        String upoanumber = oanumber.getText().toString().trim();
        String upcustname = customername.getText().toString().trim();
        Update_Request_Model_Outward_Logistic updateRequestModelOutwardLogistic = new Update_Request_Model_Outward_Logistic(
                inwardid, uptransporter, upoanumber, upcustname, EmployeId
        );
        dialogHelper.showConfirmationDialog(this, () -> {
            dialogHelper.showProgressDialog(this); // Show progress when confirmed
            Call<Boolean> call = logisticdetails.updateoutwardlogistic(updateRequestModelOutwardLogistic);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body() && response.body()) {
                        dialogHelper.hideProgressDialog(); // Hide after response
                        Toasty.success(Outward_Truck_Logistics.this, "Data Inserted Succesfully !", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Outward_Truck_Logistics.this, Outward_Truck.class));
                        finish();
                    } else {
                        Toasty.error(Outward_Truck_Logistics.this, "Data Insertion Failed..!", Toasty.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    dialogHelper.hideProgressDialog(); // Hide after response
                }
            });
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
        String unitofm = uomet.getText().toString().trim();
//        int uhowqty = Integer.parseInt(howqty.getText().toString().trim());

        if (!howqty.getText().toString().isEmpty()) {
            try {
                uhowqty = Integer.parseInt(howqty.getText().toString().trim());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        if (etintime.isEmpty() || etserialnumber.isEmpty() || etvehiclenumber.isEmpty() || ettransporter.isEmpty() ||
                etoanumber.isEmpty() || etremark.isEmpty()) {
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
        } else {
            InTrLogisticRequestModel trucklogmodel = new InTrLogisticRequestModel(inwardid, etintime, outTime,
                    EmployeId, EmployeId, etremark, 'G', etserialnumber, etvehiclenumber,
                    etoanumber, 'W', inOut, vehicleType, ucustoname, uhowqty, unitofm);
            dialogHelper.showConfirmationDialog(this, () -> {
                dialogHelper.showProgressDialog(this); // Show progress when confirmed
                Call<Boolean> call = logisticdetails.insertLogisticData(trucklogmodel);
                call.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if (response.isSuccessful() && response.body() != null && response.body()) {
                            dialogHelper.hideProgressDialog(); // Hide after response
                            makeNotificationSecurity(etvehiclenumber, etoanumber);
                            makeNotificationLogistic(etvehiclenumber, outTime);
                            Log.d("Registration", "Response Body: " + response.body());
                            Toasty.success(Outward_Truck_Logistics.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Outward_Truck_Logistics.this, Grid_Outward.class));
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
                        Toasty.error(Outward_Truck_Logistics.this, "failed..!", Toast.LENGTH_SHORT).show();
                    }
                });
            });
        }
    }

    public void outrucklogipending(View view) {
        Intent intent = new Intent(this, Grid_Outward.class);
        startActivity(intent);
    }
}