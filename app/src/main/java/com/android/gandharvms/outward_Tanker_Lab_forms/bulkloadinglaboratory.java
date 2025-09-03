package com.android.gandharvms.outward_Tanker_Lab_forms;

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
import com.android.gandharvms.Outward_Tanker;
import com.android.gandharvms.Outward_Tanker_Production_forms.Outward_Tanker_Production;
import com.android.gandharvms.Outward_Tanker_Production_forms.bulkloadingproduction;
import com.android.gandharvms.Outward_Tanker_Security.Grid_Outward;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.R;
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

public class bulkloadinglaboratory extends NotificationCommonfunctioncls {

    String[] remark = {"OK", "NOT OK"};
    AutoCompleteTextView OKNOTOK;
    ArrayAdapter<String> remarkarray;
    EditText intime,etserialnumber,etvehiclenumber,etremark,etbatchno,etbulklabdensity;
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private final String EmployeId = Global_Var.getInstance().EmpId;
    private int OutwardId;
    private Outward_Tanker_Lab outwardTankerLab;
    TimePickerDialog tpicker;

    Button grid,view,submit,competed;
    private String token;
    private LoginMethod userDetails;
    private String bulklabvehiclenumber;
    DatePickerDialog picker;
    SimpleDateFormat dtFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");

    ImageView btnlogout,btnhome;
    TextView username,empid;

    public static String Tanker;
    public static String Truck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulkloadinglaboratory);
        setupHeader();
        outwardTankerLab = RetroApiClient.outwardTankerLab();
        userDetails = RetroApiClient.getLoginApi();
        FirebaseMessaging.getInstance().subscribeToTopic(token);

        intime = findViewById(R.id.etintime);
        etserialnumber = findViewById(R.id.etserialno);
        etvehiclenumber = findViewById(R.id.etvehicle);
        etremark = findViewById(R.id.remark);
        etbatchno =findViewById(R.id.etbatchno);
        etbulklabdensity=findViewById(R.id.etbulklabdensity25);

        submit= findViewById(R.id.etlabsub);
        competed = findViewById(R.id.bulkloadlabcompleted);

        remarkarray = new ArrayAdapter<String>(this, R.layout.inout, remark);
        /*OKNOTOK.setAdapter(remarkarray);
        OKNOTOK.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String items = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Item: " + items + "Selected", Toast.LENGTH_SHORT).show();
            }
        });*/

        /*btnlogout=findViewById(R.id.btn_logoutButton);
        btnhome = findViewById(R.id.btn_homeButton);
        username=findViewById(R.id.tv_username);
        empid=findViewById(R.id.tv_employeeId);

        String userName=Global_Var.getInstance().Name;
        String empId=Global_Var.getInstance().EmpId;

        username.setText(userName);
        empid.setText(empId);
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(bulkloadinglaboratory.this, Login.class));
            }
        });
        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(bulkloadinglaboratory.this, Menu.class));
            }
        });*/
        etvehiclenumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    FetchVehicleDetails(etvehiclenumber.getText().toString().trim(), vehicleType, nextProcess, inOut);
                }
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert();
            }
        });
        competed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(bulkloadinglaboratory.this,OT_Completd_bilkload_laboratory.class));
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

    public void makeNotification(String vehicleNumber, String outTime) {
        Call<List<ResponseModel>> call = userDetails.getUsersListData();
        call.enqueue(new Callback<List<ResponseModel>>() {
            @Override
            public void onResponse(Call<List<ResponseModel>> call, Response<List<ResponseModel>> response) {
                if (response.isSuccessful()){
                    List<ResponseModel> userList = response.body();
                    if (userList != null){
                        for (ResponseModel resmodel : userList){
                            String specificRole = "Production";
                            if (specificRole.equals(resmodel.getDepartment())) {
                                token = resmodel.getToken();

                                FcmNotificationsSender notificationsSender = new FcmNotificationsSender(
                                        token,
                                        "Outward Tanker Laboratory BulkProcessform Completed",
                                        "This Vehicle Number:- " + vehicleNumber + " has Laboratory BulkProcessform Completed at"+outTime,
                                        getApplicationContext(),
                                        bulkloadinglaboratory.this
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
                Toasty.error(bulkloadinglaboratory.this, "failed..!", Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void FetchVehicleDetails(@NonNull String vehicleNo, String vehicleType, char nextProcess, char inOut) {
        Call<Lab_Model_Bulkloading> call = outwardTankerLab.fetchlabbulkloding(vehicleNo,vehicleType,nextProcess,inOut);
        call.enqueue(new Callback<Lab_Model_Bulkloading>() {
            @Override
            public void onResponse(Call<Lab_Model_Bulkloading> call, Response<Lab_Model_Bulkloading> response) {
                if (response.isSuccessful()){
                    Lab_Model_Bulkloading data = response.body();
                    if (data.getVehicleNumber()!= "" && data.getVehicleNumber()!=null){
                        OutwardId = data.getOutwardId();
                        etserialnumber.setText(data.getSerialNumber());
                        etserialnumber.setEnabled(false);
                        bulklabvehiclenumber = data.getVehicleNumber();
                        etbulklabdensity.setText(String.valueOf(data.getDensity_29_5C()));
                        etbulklabdensity.setEnabled(false);
                        etvehiclenumber.setText(data.getVehicleNumber());
                        etvehiclenumber.setEnabled(false);
                        /*intime.requestFocus();
                        intime.callOnClick();*/
                    }
                    else {
                        Toasty.error(bulkloadinglaboratory.this, "This Vehicle Number Is Not Available..!", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Log.e("Retrofit", "Error Response Body: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Lab_Model_Bulkloading> call, Throwable t) {

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
    private void insert() {
        String uintime = intime.getText().toString().trim();
        String outTime = getCurrentTime();
        String userialnumber = etserialnumber.getText().toString().trim();
        String uvehiclenum = etvehiclenumber.getText().toString().trim();
        String ubatchno = etbatchno.getText().toString().trim();
        String uremark = etremark.getText().toString().trim();

        if (uintime.isEmpty()||ubatchno.isEmpty()||uremark.isEmpty()){
            Toasty.warning(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
        }else {
            Lab_Model_Bulkloading labModelBulkloading = new Lab_Model_Bulkloading(OutwardId,uintime,outTime,ubatchno,uremark,
                    'L',EmployeId,userialnumber,uvehiclenum,'U',inOut,vehicleType);

            Call<Boolean> call = outwardTankerLab.updatebulkloadingform(labModelBulkloading);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body() && response.body() == true){
                        Toasty.success(bulkloadinglaboratory.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                        makeNotification(bulklabvehiclenumber,outTime);
                        startActivity(new Intent(bulkloadinglaboratory.this, Outward_Tanker.class));
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
                    Toasty.error(bulkloadinglaboratory.this, "failed..!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    public void outtankerLabbulktpending(View view){
        Intent intent = new Intent(this, Grid_Outward.class);
        startActivity(intent);
    }

    public void bulklabcompletedclick(View view){
        /*Intent intent = new Intent(this, Grid_Outward.class);
        startActivity(intent);*/
    }
}