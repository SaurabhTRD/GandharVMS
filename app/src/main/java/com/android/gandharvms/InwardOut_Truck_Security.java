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
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.gandharvms.Inward_Tanker_Security.API_In_Tanker_Security;
import com.android.gandharvms.Inward_Tanker_Security.Inward_Tanker_Security_Viewdata;
import com.android.gandharvms.Inward_Tanker_Security.Respo_Model_In_Tanker_security;
import com.android.gandharvms.Inward_Tanker_Security.RetroApiclient_In_Tanker_Security;
import com.android.gandharvms.Inward_Tanker_Security.Update_Request_Model_Insequrity;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;

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

public class InwardOut_Truck_Security extends AppCompatActivity {

    EditText edintime ,etvehicle,etinvoice,etmaterial,etsupplier ;
    Button submit;
    RadioButton Trasnportyes,transportno,deliveryes,deliveryno,taxyes,taxno,ewayyes,ewayno;
    private API_In_Tanker_Security apiInTankerSecurity;
    private int InwardId;
    String vehicltype= Global_Var.getInstance().MenuType;
    char InOutType = Global_Var.getInstance().InOutType;
    char DeptType= Global_Var.getInstance().DeptType;
    private String EmployeId=Global_Var.getInstance().EmpId;
    TimePickerDialog tpicker;
    Button view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inward_out_truck_security);

        apiInTankerSecurity = RetroApiClient.getserccrityveh();

        edintime = findViewById(R.id.etintime);
        etvehicle = findViewById(R.id.etvehicalnumber);
        etinvoice = findViewById(R.id.etsinvocieno);
        etmaterial = findViewById(R.id.etsmaterial);
        etsupplier = findViewById(R.id.etssupplier);
        submit = findViewById(R.id.prosubmit);

        Trasnportyes = findViewById(R.id.rb_LRCopyYes);
        transportno = findViewById(R.id.rb_LRCopyNo);
        deliveryes = findViewById(R.id.rb_DeliveryYes);
        deliveryno = findViewById(R.id.rb_DeliveryNo);
        taxyes = findViewById(R.id.rb_TaxInvoiceYes);
        taxno = findViewById(R.id.rb_TaxInvoiceNo);
        ewayyes = findViewById(R.id.rb_EwaybillYes);
        ewayno = findViewById(R.id.rb_EwaybillNo);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });


        view = findViewById(R.id.btn_Viewweigmentslip);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(InwardOut_Truck_Security.this, Inward_Tanker_Security_Viewdata.class));
            }
        });
        edintime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hours = calendar.get(Calendar.HOUR_OF_DAY);
                int mins = calendar.get(Calendar.MINUTE);
                tpicker = new TimePickerDialog(InwardOut_Truck_Security.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        c.set(Calendar.MINUTE, minute);
                        edintime.setText(hourOfDay + ":" + minute);
                    }
                }, hours, mins, false);
                tpicker.show();
            }
        });

        etvehicle.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
//                    String VehicleNo = etvehical.getText().toString();
                    String vehicltype = Global_Var.getInstance().MenuType;
                    char DeptType = Global_Var.getInstance().DeptType;
                    char InOutType = Global_Var.getInstance().InOutType;

                    FetchVehicleDetails(etvehicle.getText().toString().trim(), vehicltype, DeptType, InOutType);
                }
            }
        });
    }

    private void FetchVehicleDetails(@NonNull String VehicleNo, String vehicltype, char DeptType, char InOutType) {
        Call<List<Respo_Model_In_Tanker_security>> call = RetroApiClient.getserccrityveh().GetIntankerSecurityByVehicle(VehicleNo, vehicltype, DeptType, InOutType);
        call.enqueue(new Callback<List<Respo_Model_In_Tanker_security>>() {
            @Override
            public void onResponse(Call<List<Respo_Model_In_Tanker_security>> call, Response<List<Respo_Model_In_Tanker_security>> response) {
                if (response.isSuccessful()){
                    if (response.body().size()>0){
                        List<Respo_Model_In_Tanker_security> Data = response.body();
                        Respo_Model_In_Tanker_security obj = Data.get(0);

                        InwardId = obj.getInwardId();
                        etinvoice.setText(obj.getInvoiceNo());
                        etmaterial.setText(obj.getMaterial());
                        etsupplier.setText(obj.getPartyName());
                    }
                }else {
                    Log.e("Retrofit", "Error" + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Respo_Model_In_Tanker_security>> call, Throwable t) {

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

    private void update() {
        String lrCopySelection = Trasnportyes.isChecked() ? "Yes" : "No";
        String deliverySelection = deliveryes.isChecked() ? "Yes" : "No";
        String taxInvoiceSelection = taxyes.isChecked() ? "Yes" : "No";
        String ewayBillSelection = ewayyes.isChecked() ? "Yes" : "No";
        String outTime = getCurrentTime();
        String intime = edintime.getText().toString().trim();
        String vehiclenumber = etvehicle.getText().toString().trim();
        String material = etmaterial.getText().toString().trim();
        String supplier = etsupplier.getText().toString().trim();
        String invoice = etinvoice.getText().toString().trim();

        if (lrCopySelection.isEmpty()|| deliverySelection.isEmpty()||taxInvoiceSelection.isEmpty()||ewayBillSelection.isEmpty()||
                outTime.isEmpty()||intime.isEmpty()||vehiclenumber.isEmpty()||material.isEmpty()||supplier.isEmpty()||invoice.isEmpty()){
            Toasty.warning(this, "All fields must be filled", Toast.LENGTH_SHORT, true).show();
        }else {
            Update_Request_Model_Insequrity updateRequestModelInsequrity = new Update_Request_Model_Insequrity(InwardId,"",invoice,vehiclenumber,"",supplier,material,
                    "","",'S','O',intime,"",vehicltype,intime,outTime,'0','0',
                    '0','0',"","","",lrCopySelection,deliverySelection,taxInvoiceSelection,
                    ewayBillSelection,EmployeId,intime);
            apiInTankerSecurity = RetroApiclient_In_Tanker_Security.getinsecurityApi();
            Call<Boolean> call = apiInTankerSecurity.intankersecurityoutupdate(updateRequestModelInsequrity);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body() != null && response.body() == true){
                        Toast.makeText(InwardOut_Truck_Security.this, "Inserted Succesfully", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(InwardOut_Truck_Security.this, "failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void onBackPressed(){
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
        finish();
    }
}