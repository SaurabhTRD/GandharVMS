package com.android.gandharvms.Outward_Tanker_Billing;

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

import com.android.gandharvms.FcmNotificationsSender;
import com.android.gandharvms.Global_Var;
import com.android.gandharvms.Inward_Tanker_Laboratory.in_tanker_lab_grid;
import com.android.gandharvms.Inward_Tanker_Weighment.Inward_Tanker_Weighment;
import com.android.gandharvms.LoginWithAPI.LoginMethod;
import com.android.gandharvms.LoginWithAPI.ResponseModel;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.Outward_Tanker;
import com.android.gandharvms.Outward_Tanker_Security.Grid_Outward;
import com.android.gandharvms.Outward_Tanker_Security.Outward_RetroApiclient;
import com.android.gandharvms.Outward_Tanker_Security.Outward_Tanker_Security;
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

public class Outward_Tanker_Billing extends AppCompatActivity {

    EditText intime,serialnumber,vehiclenumber,transporter,oanumber,date,location,
    remark,etcust,etprod,ethowmuch;

    FirebaseFirestore dbroot;
    Button submit;
    TimePickerDialog tpicker;
    Calendar calendar = Calendar.getInstance();

    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private final String EmployeId = Global_Var.getInstance().EmpId;
    private Outward_Tanker_Billinginterface outwardTankerBillinginterface;
    private int OutwardId;
    private String token;
    private LoginMethod userDetails;
    private int uhowmuch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        outwardTankerBillinginterface = Outward_RetroApiclient.outwardTankerBillinginterface();
        userDetails = RetroApiClient.getLoginApi();
        setContentView(R.layout.activity_outward_tanker_billing);
        intime = findViewById(R.id.etintime);
        serialnumber = findViewById(R.id.etserialnumber);
        vehiclenumber = findViewById(R.id.etvehicleno);
        transporter = findViewById(R.id.ettransportname);
        oanumber = findViewById(R.id.etoanumber);
        date= findViewById(R.id.etdate);
        remark = findViewById(R.id.etremark);
        submit=findViewById(R.id.etssubmit);
        dbroot= FirebaseFirestore.getInstance();
        etcust = findViewById(R.id.etcustomer);
        etprod = findViewById(R.id.etproduct);
        ethowmuch = findViewById(R.id.ethowmuch);
        location = findViewById(R.id.etloca);

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
                int hours = calendar.get(Calendar.HOUR_OF_DAY);
                int mins = calendar.get(Calendar.MINUTE);
                tpicker = new TimePickerDialog(Outward_Tanker_Billing.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        c.set(Calendar.MINUTE,minute);

                        // Set the formatted time to the EditText
                        intime.setText(hourOfDay +":"+ minute );
                    }
                },hours,mins,false);
                tpicker.show();
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
                if (response.isSuccessful()){
                    List<ResponseModel> userList = response.body();
                    if (userList != null){
                        for (ResponseModel resmodel : userList){
                            String specificRole = "Billing";
                            if (specificRole.equals(resmodel.getDepartment())) {
                                token = resmodel.getToken();

                                FcmNotificationsSender notificationsSender = new FcmNotificationsSender(
                                        token,
                                        "Outward Tanker Security Process Done..!",
                                        "Vehicle Number:-" + vehicleNumber + " has completed Security process at " + outTime,
                                        getApplicationContext(),
                                        Outward_Tanker_Billing.this
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
                Toasty.error(Outward_Tanker_Billing.this, "failed..!", Toast.LENGTH_SHORT).show();
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
                    if (data.getVehicleNumber() != "" && data.getVehicleNumber()!=null){
                        OutwardId = data.getOutwardId();
                        vehiclenumber.setText(data.getVehicleNumber());
                        serialnumber.setText(data.getSerialNumber());
                        date.setText(data.getDate());
                        transporter.setText(data.getTransportName());
                        vehiclenumber.setEnabled(false);
                        serialnumber.setEnabled(false);
                        date.setEnabled(false);
                        transporter.setEnabled(false);
                        /*intime.callOnClick();
                        intime.requestFocus();*/
                    }
                    else {
                        Toasty.error(Outward_Tanker_Billing.this, "This Vehicle Number Is Not Available..!", Toast.LENGTH_SHORT).show();
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

    private String getCurrentTime() {
        // Get the current time
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(new Date());
    }
    public void insert(){
        String etintime = intime.getText().toString().trim();
        String etserilnumber = serialnumber.getText().toString().trim();
        String etvehiclenumber = vehiclenumber.getText().toString().trim();
        String etoanumber = oanumber.getText().toString().trim();
        String etdate = date.getText().toString().trim();
        String etremark = remark.getText().toString().trim();
        String outTime = getCurrentTime();
        String ucustname = etcust.getText().toString().trim();
        String uproduct = etprod.getText().toString().trim();
        if(!ethowmuch.getText().toString().isEmpty())
        {
            try {
                uhowmuch=Integer.parseInt(ethowmuch.getText().toString().trim());
            }catch (NumberFormatException e){
                e.printStackTrace();
            }
        }
        else{
            Toasty.warning(this, "How Much Quantity Is Empty", Toast.LENGTH_SHORT).show();
        }
        String ulocation = location.getText().toString().trim();
        if (etintime.isEmpty()|| etserilnumber.isEmpty()||etvehiclenumber.isEmpty()||
       etoanumber.isEmpty()||etdate.isEmpty()|| etremark.isEmpty()|| ucustname.isEmpty()||
                uproduct.isEmpty()||ulocation.isEmpty()){
            Toasty.warning(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
        }else {
            Respons_Outward_Tanker_Billing responsOutwardTankerBilling = new Respons_Outward_Tanker_Billing(OutwardId,etintime,outTime,
                    "",EmployeId,EmployeId,'B',etremark,etserilnumber,etvehiclenumber,etoanumber,ucustname,uproduct,uhowmuch,ulocation,'W',inOut,
                    vehicleType);
            Call<Boolean> call = outwardTankerBillinginterface.updatebillingoanumber(responsOutwardTankerBilling);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                    if (response.isSuccessful() && response.body() != null && response.body()==true){
                        makeNotification(etvehiclenumber, outTime);
                        Toasty.success(Outward_Tanker_Billing.this, "Data Inserted Successfully", Toast.LENGTH_SHORT,true).show();
                        startActivity(new Intent(Outward_Tanker_Billing.this, Outward_Tanker.class));
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
                    Toasty.error(Outward_Tanker_Billing.this, "failed..!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    public void outtankerbillpending(View view) {
        Intent intent = new Intent(this, Grid_Outward.class);
        startActivity(intent);
    }
    public void otinbilonclickevent(View view) {
        /*Intent intent = new Intent(this, in_tanker_lab_grid.class);
        startActivity(intent);*/
    }
}