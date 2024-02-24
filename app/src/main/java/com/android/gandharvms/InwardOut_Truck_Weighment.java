package com.android.gandharvms;

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

import com.android.gandharvms.Inward_Tanker_Security.grid;
import com.android.gandharvms.Inward_Tanker_Weighment.InTanWeighResponseModel;
import com.android.gandharvms.Inward_Tanker_Weighment.Model_InwardOutweighment;
import com.android.gandharvms.Inward_Truck_Weighment.Inward_Truck_Weighment_Viewdata;
import com.android.gandharvms.Inward_Truck_Weighment.Inward_Truck_weighment;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.LoginWithAPI.Weighment;

import java.io.IOException;
import java.util.Calendar;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class InwardOut_Truck_Weighment extends AppCompatActivity {

    EditText etintime,etvehicel,etgrosswt,etnetwt,ettarewt;
    Button view,submit;
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private final String EmployeId = Global_Var.getInstance().EmpId;
    private Weighment weighmentdetails;
    private int inwardid;
    TimePickerDialog tpicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inward_out_truck_weighment);

        weighmentdetails = RetroApiClient.getWeighmentDetails();

        etintime = findViewById(R.id.intime);
        etvehicel = findViewById(R.id.vehicle);
        etgrosswt = findViewById(R.id.etgrosswt);
        etnetwt = findViewById(R.id.etnetweight);
        ettarewt = findViewById(R.id.ettareweight);
        
        submit = findViewById(R.id.prosubmit);

        view = findViewById(R.id.btn_Viewweigmentslip);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InwardOut_Truck_Weighment.this, Inward_Truck_Weighment_Viewdata.class));
            }
        });
//        etvehicel.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onClick(View v, boolean hasFocus) {
//                if (!hasFocus){
//                    FetchVehicleDetails(etvehicel.getText().toString().trim(),vehicleType,nextProcess,inOut);
//                }
//            }
//        });

        etintime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hours = calendar.get(Calendar.HOUR_OF_DAY);
                int mins = calendar.get(Calendar.MINUTE);
                tpicker = new TimePickerDialog(InwardOut_Truck_Weighment.this, new TimePickerDialog.OnTimeSetListener() {
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
        etvehicel.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    FetchVehicleDetails(etvehicel.getText().toString().trim(),vehicleType,nextProcess,inOut);
                }
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });
    }
    private void FetchVehicleDetails(@NonNull String vehicleNo, String vehicleType, char NextProcess, char inOut) {

        Call<InTanWeighResponseModel> call = weighmentdetails.getWeighbyfetchVehData(vehicleNo,vehicleType,NextProcess,inOut);
        call.enqueue(new Callback<InTanWeighResponseModel>() {
            @Override
            public void onResponse(Call<InTanWeighResponseModel> call, Response<InTanWeighResponseModel> response) {
                if (response.isSuccessful()){
                    InTanWeighResponseModel data = response.body();
                    if (data.getVehicleNo() != ""){
                        etgrosswt.setText(data.getGrossWeight());
                        etnetwt.setText(data.getNetWeight());
                        inwardid = data.getInwardId();
                        etvehicel.setText(data.getVehicleNo());
                    }
                }
            }

            @Override
            public void onFailure(Call<InTanWeighResponseModel> call, Throwable t) {
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
                Toasty.error(InwardOut_Truck_Weighment.this,"failed..!", Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void update() {

        String intime = etintime.getText().toString().trim();
        String vehicleno = etvehicel.getText().toString().trim();
        String gross = etgrosswt.getText().toString().trim();
        String tare = ettarewt.getText().toString().trim();
        String net = etnetwt.getText().toString().trim();

        if (intime.isEmpty()||vehicleno.isEmpty()||gross.isEmpty()||tare.isEmpty()||net.isEmpty()){
            Toasty.warning(this, "All fields must be filled", Toast.LENGTH_SHORT, true).show();
        }else {
            Model_InwardOutweighment modelInwardOutweighment = new Model_InwardOutweighment(inwardid,gross,net,tare,"","",
                    'S','O',vehicleType,intime);

            Call<Boolean> call = weighmentdetails.inwardoutweighment(modelInwardOutweighment);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body() != null && response.body() == true){
                        Log.d("Registration", "Response Body: " + response.body());
                        Toasty.success(InwardOut_Truck_Weighment.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(InwardOut_Truck_Weighment.this, Inward_Tanker.class));
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
                    Toasty.error(InwardOut_Truck_Weighment.this, "failed..!", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }


    public void onBackPressed(){
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
        finish();
    }
    public void inouttruckgrid(View view){
        Intent intent = new Intent(this, grid.class);
        startActivity(intent);
    }
}