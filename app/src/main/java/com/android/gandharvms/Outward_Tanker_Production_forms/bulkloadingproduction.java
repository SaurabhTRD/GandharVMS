package com.android.gandharvms.Outward_Tanker_Production_forms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.gandharvms.Global_Var;
import com.android.gandharvms.Outward_Tanker_Security.Outward_RetroApiclient;
import com.android.gandharvms.R;
import com.android.gandharvms.outward_Tanker_Lab_forms.Lab_Model__Outward_Tanker;
import com.android.gandharvms.outward_Tanker_Lab_forms.Outward_Tanker_Lab;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;


public class bulkloadingproduction extends AppCompatActivity {
    EditText etintime,etserialno,etvehicle,etoanumber,etdate,ettankno,etproduct,etcustomer,etdestination,etqtykl,
            ettransporter,etnameofficer,etremark,ettankblender,etbultqty,etbatchno;
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private final String EmployeId = Global_Var.getInstance().EmpId;
    private int OutwardId;
    private Outward_Tanker_Lab outwardTankerLab;

    Button grid,view,submit;
    TimePickerDialog tpicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulkloadingproduction);
        outwardTankerLab = Outward_RetroApiclient.outwardTankerLab();

        etintime = findViewById(R.id.etintime);
        etserialno = findViewById(R.id.etserialnumber);
        etvehicle = findViewById(R.id.etvehicleno);
        etoanumber = findViewById(R.id.etoanumber);
//        etdate = findViewById(R.id.etdate);
        ettankno = findViewById(R.id.ettankerno);
        etproduct = findViewById(R.id.etproduct);
        etcustomer = findViewById(R.id.etcustomer);
        etdestination = findViewById(R.id.etdestination);
        etqtykl = findViewById(R.id.etqty);
        ettransporter = findViewById(R.id.ettransporter);
        etnameofficer = findViewById(R.id.etsign);
        etremark = findViewById(R.id.etremark);

        ettankblender = findViewById(R.id.ettankorblender);
        etbultqty = findViewById(R.id.etbulkqty);
        etbatchno =findViewById(R.id.etbatchno);

        submit = findViewById(R.id.etssubmit);

        etvehicle.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    FetchVehicleDetails(etvehicle.getText().toString().trim(), vehicleType, nextProcess, inOut);
                }
            }
        });
        etintime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hours = calendar.get(Calendar.HOUR_OF_DAY);
                int mins = calendar.get(Calendar.MINUTE);
                tpicker = new TimePickerDialog(bulkloadingproduction.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        c.set(Calendar.MINUTE,minute);

                        // Set the formatted time to the EditText
                        etintime.setText(hourOfDay +":"+ minute );
                    }
                },hours,mins,false);
                tpicker.show();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });

    }
    private String getCurrentTime() {
        // Get the current time
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(new Date());
    }


    private void FetchVehicleDetails(@NonNull String vehicleNo, String vehicleType, char NextProcess, char inOut) {
        Call<Lab_Model__Outward_Tanker> call = outwardTankerLab.fetchlab(vehicleNo,vehicleType,nextProcess,inOut);
        call.enqueue(new Callback<Lab_Model__Outward_Tanker>() {
            @Override
            public void onResponse(Call<Lab_Model__Outward_Tanker> call, Response<Lab_Model__Outward_Tanker> response) {
                if (response.isSuccessful()){
                    Lab_Model__Outward_Tanker data = response.body();
                    if (data.getVehicleNumber()!= ""){
                        OutwardId = data.getOutwardId();
                        ettankno.setText (String.valueOf(data.getTankerNumber()));
                        etproduct.setText(data.getProductName());
                        etcustomer.setText(data.getCustomerName());
                        etdestination.setText(data.getLocation());
                        etqtykl.setText(String.valueOf(data.getHowMuchQuantityFilled()));
                        ettransporter.setText(data.getTransportName());
                        etserialno.setText(data.getSerialNumber());
                        etvehicle.setText(data.getVehicleNumber());
                        etoanumber.setText(data.getOAnumber());

                    }
                }else {
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
    private void insert() {
        String uintime = etintime.getText().toString().trim();
        String usign = etnameofficer.getText().toString().trim();
        String uremark = etremark.getText().toString().trim();
        String userialnumber = etserialno.getText().toString().trim();
        String uvehicleno = etvehicle.getText().toString().trim();
        String outTime = getCurrentTime();

        if (uintime.isEmpty()||usign.isEmpty()||userialnumber.isEmpty()||uvehicleno.isEmpty()){
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
        }else {
            Production_bulkloading_model productionBulkloadingModel = new Production_bulkloading_model(OutwardId,uintime,outTime,
                    uremark,usign,'P',EmployeId,EmployeId,userialnumber,uvehicleno,'L',inOut,
                    vehicleType);
            Call<Boolean> call = outwardTankerLab.insertbulkloadingproduction(productionBulkloadingModel);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body() && response.body() ==true){
                        Toast.makeText(bulkloadingproduction.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
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
                    Toasty.error(bulkloadingproduction.this, "failed..!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    private void update() {
        String utankboblend = ettankblender.getText().toString().trim();
        String ubulkqty = etbultqty.getText().toString().trim();
        String ubatchno = etbatchno.getText().toString().trim();

        if (utankboblend.isEmpty()||ubulkqty.isEmpty()||ubatchno.isEmpty()){
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
        }else {
            Production_Model_Update productionModelUpdate = new Production_Model_Update(OutwardId,utankboblend,ubulkqty,ubatchno,EmployeId,
                    'W','O',vehicleType);
            Call<Boolean> call = outwardTankerLab.updatebulkloadingproduction(productionModelUpdate);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body() && response.body()==true){
                        Toast.makeText(bulkloadingproduction.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
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
                    Toasty.error(bulkloadingproduction.this, "failed..!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


}