package com.android.gandharvms;

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

import com.android.gandharvms.Outward_Tanker_Security.Outward_RetroApiclient;
import com.android.gandharvms.Outward_Tanker_Weighment.Model_OutwardOut_Weighment;
import com.android.gandharvms.Outward_Tanker_Weighment.Outward_Tanker_weighment;
import com.android.gandharvms.Outward_Tanker_Weighment.Outward_weighment;
import com.android.gandharvms.Outward_Tanker_Weighment.Response_Outward_Tanker_Weighment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class OutwardOut_Tanker_Weighment extends AppCompatActivity {

    EditText intime,serialnumber,vehiclenumber,sealnumber,batchnumber,netweight,grossw,etnumberpack,etremark;
    Button submit;
    FirebaseFirestore dbroot;
    TimePickerDialog tpicker;
    Calendar calendar = Calendar.getInstance();
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private final String EmployeId = Global_Var.getInstance().EmpId;
    private int OutwardId;
    private Outward_weighment outwardWeighment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outward_out_tanker_weighment);
        outwardWeighment = Outward_RetroApiclient.outwardWeighment();

        intime= findViewById(R.id.etintime);
        serialnumber = findViewById(R.id.etserialnumber);
        vehiclenumber = findViewById(R.id.etvehicleno);
        sealnumber = findViewById(R.id.etsealnumber);
        netweight = findViewById(R.id.etnetweight);
        grossw = findViewById(R.id.etgrosswt);
        etnumberpack = findViewById(R.id.etnumberpack);
        etremark = findViewById(R.id.etremakr);

        submit = findViewById(R.id.etssubmit);
        dbroot= FirebaseFirestore.getInstance();


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert();
            }
        });
        vehiclenumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    FetchVehicleDetails(vehiclenumber.getText().toString().trim(), vehicleType, nextProcess, inOut);
                }
            }
        });


        intime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hours = calendar.get(Calendar.HOUR_OF_DAY);
                int mins = calendar.get(Calendar.MINUTE);
                tpicker = new TimePickerDialog(OutwardOut_Tanker_Weighment.this, new TimePickerDialog.OnTimeSetListener() {
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
    }

    private void FetchVehicleDetails(@NonNull String vehicleNo, String vehicleType, char NextProcess, char inOut) {
        Call<Response_Outward_Tanker_Weighment> call = outwardWeighment.fetchweighment(vehicleNo,vehicleType,NextProcess,inOut);
        call.enqueue(new Callback<Response_Outward_Tanker_Weighment>() {
            @Override
            public void onResponse(Call<Response_Outward_Tanker_Weighment> call, Response<Response_Outward_Tanker_Weighment> response) {
                if (response.isSuccessful()){
                    Response_Outward_Tanker_Weighment data = response.body();
                    if (data.getVehicleNumber() != ""){
                        OutwardId = data.getOutwardId();
                        serialnumber.setText(data.getSerialNumber());
                        vehiclenumber.setText(data.getVehicleNumber());
                    }
                    else {
                        Log.e("Retrofit", "Error Response Body: " + response.code());
                    }
                }
            }
            @Override
            public void onFailure(Call<Response_Outward_Tanker_Weighment> call, Throwable t) {

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
//        intime,serialnumber,vehiclenumber,sealnumber,batchnumber,netweight,deinsity;

//        String etintime = intime.getText().toString().trim();
//        String etserialnumber = serialnumber.getText().toString().trim();
//        String etvehiclenumber = vehiclenumber.getText().toString().trim();
//        String etbacthnumber = batchnumber.getText().toString().trim();
//        String etdensity = grossw.getText().toString().trim();

        String etintime = intime.getText().toString().trim();
        String etsealnumber = sealnumber.getText().toString().trim();
        String etnetweight = netweight.getText().toString().trim();
        String outTime = getCurrentTime();
        String ugrosswt = grossw.getText().toString().trim();
        String unumberpack = etnumberpack.getText().toString().trim();
        String uremark = etremark.getText().toString().trim();

        if (etintime.isEmpty()||etsealnumber.isEmpty()||etnetweight.isEmpty()||uremark.isEmpty()){
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
        }else {
//            Map<String ,String>items =new HashMap<>();
//
//            items.put("In_Time",intime.getText().toString().trim());
//            items.put("Serial_Number",serialnumber.getText().toString().trim());
//            items.put("Vehicle_Number",vehiclenumber.getText().toString().trim());
//            items.put("Seal_Number",sealnumber.getText().toString().trim());
//            items.put("Batch_Number",batchnumber.getText().toString().trim());
//            items.put("Net_Weight",netweight.getText().toString().trim());
//            items.put("Density",deinsity.getText().toString().trim());
//
//            dbroot.collection("OutwardOut_Tanker_Weighment(Out)").add(items)
//                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
//                        @Override
//                        public void onComplete(@NonNull Task<DocumentReference> task) {
//                            Toast.makeText(OutwardOut_Tanker_Weighment.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
//                        }
//                    });
            Model_OutwardOut_Weighment modelOutwardOutWeighment = new Model_OutwardOut_Weighment(OutwardId,"","",
                    outTime,etnetweight,ugrosswt,unumberpack,uremark,etsealnumber,EmployeId,'S',inOut,vehicleType);
            Call<Boolean> call = outwardWeighment.updateoutwardoutweighment(modelOutwardOutWeighment);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body() && response.body() == true){
                        Toast.makeText(OutwardOut_Tanker_Weighment.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
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
                    Toasty.error(OutwardOut_Tanker_Weighment.this, "failed..!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}