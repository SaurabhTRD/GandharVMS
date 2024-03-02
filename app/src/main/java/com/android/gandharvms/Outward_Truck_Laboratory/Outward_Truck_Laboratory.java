package com.android.gandharvms.Outward_Truck_Laboratory;

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

import com.android.gandharvms.Global_Var;
import com.android.gandharvms.Outward_Tanker_Security.Outward_RetroApiclient;
import com.android.gandharvms.Outward_Truck;
import com.android.gandharvms.Outward_Truck_Billing.Outward_Truck_Billing;
import com.android.gandharvms.Outward_Truck_Dispatch.Model_Outward_Truck_Dispatch;
import com.android.gandharvms.Outward_Truck_Dispatch.Outward_Truck_Dispatch;
import com.android.gandharvms.Outward_Truck_Dispatch.Outward_Truck_interface;
import com.android.gandharvms.Outward_Truck_Production.Outward_Truck_Production_interface;
import com.android.gandharvms.R;
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

public class Outward_Truck_Laboratory extends AppCompatActivity {

    EditText intime,serialnumber,vehiclenumber,material,oanumber,remark;
    Button submit;
    FirebaseFirestore dbroot;
    TimePickerDialog tpicker;
    Calendar calendar = Calendar.getInstance();
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private final String EmployeId = Global_Var.getInstance().EmpId;
    private int OutwardId;
    private Outward_Truck_interface outwardTruckInterface;
    private Outward_Truck_Production_interface outwardTruckProductionInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outward_truck_laboratory);
        outwardTruckInterface = Outward_RetroApiclient.outwardtruckdispatch();
        outwardTruckProductionInterface = Outward_RetroApiclient.outwardTruckProductionInterface();

        intime = findViewById(R.id.etintime);
        serialnumber = findViewById(R.id.etserialnumber);
        vehiclenumber = findViewById(R.id.etvehicleno);
        material = findViewById(R.id.etmaterial);
        oanumber = findViewById(R.id.etoanumber);
        remark = findViewById(R.id.etremark);

        submit = findViewById(R.id.etssubmit);
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
                tpicker = new TimePickerDialog(Outward_Truck_Laboratory.this, new TimePickerDialog.OnTimeSetListener() {
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
        Call<Model_Outward_Truck_Dispatch> call = outwardTruckInterface.fetchdispatch(vehicleNo,vehicleType,NextProcess,inOut);
        call.enqueue(new Callback<Model_Outward_Truck_Dispatch>() {
            @Override
            public void onResponse(Call<Model_Outward_Truck_Dispatch> call, Response<Model_Outward_Truck_Dispatch> response) {
               if (response.isSuccessful()){
                   Model_Outward_Truck_Dispatch data = response.body();
                   if (data.getVehicleNumber()!= ""){
                       OutwardId = data.getOutwardId();
                       serialnumber.setText(data.getSerialNumber());
                       vehiclenumber.setText(data.getVehicleNumber());
                   }else {
                       Toasty.success(Outward_Truck_Laboratory.this, "Vehicle Is Not Available", Toast.LENGTH_SHORT).show();
                   }
               }else {
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
                Toasty.error(Outward_Truck_Laboratory.this, "failed..!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private String getCurrentTime() {
        // Get the current time
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(new Date());
    }

    public void insert(){
//        intime,serialnumber,vehiclenumber,material,oanumber;
        String etintime = intime.getText().toString().trim();
        String outTime = getCurrentTime();
        String uremark = remark.getText().toString().trim();


//        String etserialnumber =serialnumber.getText().toString().trim();
//        String etvehiclnumber = vehiclenumber.getText().toString().trim();
//        String etmaterial = material.getText().toString().trim();
//        String etoanumber = oanumber.getText().toString().trim();

        if (etintime.isEmpty()|| uremark.isEmpty()){
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
        }else {
//            Map<String,String> items = new HashMap<>();
//
//            items.put("In_Time",intime.getText().toString().trim());
//            items.put("Serial_Number",serialnumber.getText().toString().trim());
//            items.put("Vehicle_Number",vehiclenumber.getText().toString().trim());
//            items.put("Material",material.getText().toString().trim());
//            items.put("OA_Number",oanumber.getText().toString().trim());
//
//            dbroot.collection("Outward_Truck_Laboratory").add(items)
//                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
//                        @Override
//                        public void onComplete(@NonNull Task<DocumentReference> task) {
//                            Toast.makeText(Outward_Truck_Laboratory.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
//                        }
//                    });
            Model_Outward_Truck_Lab modelOutwardTruckLab = new Model_Outward_Truck_Lab(OutwardId,etintime,outTime,
                    'L',uremark,EmployeId,'W','O',vehicleType);
            Call<Boolean> call = outwardTruckProductionInterface.updateoutwardtrucklab(modelOutwardTruckLab);
             call.enqueue(new Callback<Boolean>() {
                 @Override
                 public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                     if (response.isSuccessful() && response.body() && response.body()==true){
                         Toasty.success(Outward_Truck_Laboratory.this, "Data Inserted Successfully", Toast.LENGTH_SHORT,true).show();
                         startActivity(new Intent(Outward_Truck_Laboratory.this, Outward_Truck.class));
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
                     Toasty.error(Outward_Truck_Laboratory.this, "failed..!", Toast.LENGTH_SHORT).show();
                 }
             });

        }
    }

}