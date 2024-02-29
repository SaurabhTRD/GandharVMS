package com.android.gandharvms.outward_Tanker_Lab_forms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.gandharvms.Global_Var;
import com.android.gandharvms.Outward_Tanker_Security.Outward_RetroApiclient;
import com.android.gandharvms.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class bulkloadinglaboratory extends AppCompatActivity {

    String[] remark = {"OK", "NOT OK"};
    AutoCompleteTextView OKNOTOK;
    ArrayAdapter<String> remarkarray;
    EditText intime,etserialnumber,etvehiclenumber,etflushing,etflushing1,etflushing2,etflushing3,etflushing4,etflushing5,etstatus,etremark,qcofficer;
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private final String EmployeId = Global_Var.getInstance().EmpId;
    private int OutwardId;
    private Outward_Tanker_Lab outwardTankerLab;

    Button grid,view,submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulkloadinglaboratory);
        outwardTankerLab = Outward_RetroApiclient.outwardTankerLab();

        OKNOTOK = findViewById(R.id.etoknotok);

        intime = findViewById(R.id.etintime);
        etserialnumber = findViewById(R.id.etserialno);
        etvehiclenumber = findViewById(R.id.etvehicle);
        etflushing = findViewById(R.id.flushingno);
        etflushing1 = findViewById(R.id.flushing1);
        etflushing2 = findViewById(R.id.flushing2);
        etflushing3 = findViewById(R.id.flushing3);
        etflushing4 = findViewById(R.id.flushing4);
        etflushing5 = findViewById(R.id.flushing5);
        etstatus = findViewById(R.id.etoknotok);
        etremark = findViewById(R.id.remark);
        qcofficer = findViewById(R.id.etqcsign);

        submit= findViewById(R.id.etlabsub);

        remarkarray = new ArrayAdapter<String>(this, R.layout.inout, remark);
        OKNOTOK.setAdapter(remarkarray);
        OKNOTOK.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String items = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Item: " + items + "Selected", Toast.LENGTH_SHORT).show();
            }
        });
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
    }
    private String getCurrentTime() {
        // Get the current time
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(new Date());
    }


    private void FetchVehicleDetails(@NonNull String vehicleNo, String vehicleType, char nextProcess, char inOut) {
        Call<Lab_Model_Bulkloading> call = outwardTankerLab.fetchlabbulkloding(vehicleNo,vehicleType,nextProcess,inOut);
        call.enqueue(new Callback<Lab_Model_Bulkloading>() {
            @Override
            public void onResponse(Call<Lab_Model_Bulkloading> call, Response<Lab_Model_Bulkloading> response) {
                if (response.isSuccessful()){
                    Lab_Model_Bulkloading data = response.body();
                    if (data.getVehicleNumber()!= ""){
                        OutwardId = data.getOutwardId();
                        etserialnumber.setText(data.getSerialNumber());
                        etvehiclenumber.setText(data.getVehicleNumber());
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
        int uflushing = Integer.parseInt(etflushing.getText().toString().trim());
        int flush1 = Integer.parseInt(etflushing1.getText().toString().trim());
        int flush2 = Integer.parseInt(etflushing2.getText().toString().trim());
        int flush3 = Integer.parseInt(etflushing3.getText().toString().trim());
        int flush4 = Integer.parseInt(etflushing4.getText().toString().trim());
        int flush5 = Integer.parseInt(etflushing5.getText().toString().trim());
        String ustatus = etstatus.getText().toString().trim();
        String uqcoprator = qcofficer.getText().toString().trim();
        String uremark = etremark.getText().toString().trim();

        if (uintime.isEmpty()||userialnumber.isEmpty()||uvehiclenum.isEmpty()){
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
        }else {
            Lab_Model_Bulkloading labModelBulkloading = new Lab_Model_Bulkloading(OutwardId,uintime,outTime,uremark,uflushing,
                    flush1,flush2,flush3,flush4,flush5,ustatus,uqcoprator,
                    uqcoprator,'L',EmployeId,userialnumber,uvehiclenum,'P',inOut,vehicleType);

            Call<Boolean> call = outwardTankerLab.updatebulkloadingform(labModelBulkloading);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body() && response.body() == true){
                        Toast.makeText(bulkloadinglaboratory.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
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

}