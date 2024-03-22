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

import com.android.gandharvms.Outward_Tanker_Billing.Outward_Tanker_Billinginterface;
import com.android.gandharvms.Outward_Tanker_Billing.Respons_Outward_Tanker_Billing;
import com.android.gandharvms.Outward_Tanker_Security.Outward_RetroApiclient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class OutwardOut_Truck_Billing extends AppCompatActivity {

    EditText intime,serialnumber,vehiclenumber,etoanumber,ettramsname,etdrivermob,etgrs,ettare,etnet,etseal,etbatch,etdensity;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outward_out_truck_billing);

        outwardTankerBillinginterface = Outward_RetroApiclient.outwardTankerBillinginterface();

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



        submit = findViewById(R.id.submit);
        dbroot= FirebaseFirestore.getInstance();

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
                tpicker = new TimePickerDialog(OutwardOut_Truck_Billing.this, new TimePickerDialog.OnTimeSetListener() {
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
//        intime,serialnumber,vehiclenumber,invoiceno,ewbnumber;

//        String etintime = intime.getText().toString().trim();
//        String etserialnumber = serialnumber.getText().toString().trim();
//        String etvehiclenumber = vehiclenumber.getText().toString().trim();
//        String etinvoiceno = invoiceno.getText().toString().trim();
//        String etewbnumber = ewbnumber.getText().toString().trim();
//
//        if (etintime.isEmpty()||etserialnumber.isEmpty()||etvehiclenumber.isEmpty()||etinvoiceno.isEmpty()||etewbnumber.isEmpty()){
//            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
//        }else {
//            Map<String,String>items = new HashMap<>();
//
//            items.put("In_Time",intime.getText().toString().trim());
//            items.put("Serial_Number",serialnumber.getText().toString().trim());
//            items.put("Vehicle_Number",vehiclenumber.getText().toString().trim());
//            items.put("Invoice_No",invoiceno.getText().toString().trim());
//            items.put("EWB_Number",ewbnumber.getText().toString().trim());
//
//            dbroot.collection("OutwardOut_Truck_Billing(OUT)").add(items)
//                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
//                        @Override
//                        public void onComplete(@NonNull Task<DocumentReference> task) {
//                            Toast.makeText(OutwardOut_Truck_Billing.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//        }
        String uintime = intime.getText().toString().trim();
        String ubatch = etbatch.getText().toString().trim();


    }
}