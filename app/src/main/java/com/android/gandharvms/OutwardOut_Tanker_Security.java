package com.android.gandharvms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.gandharvms.Outward_Tanker_Security.Model_OutwardOut_Security;
import com.android.gandharvms.Outward_Tanker_Security.Outward_RetroApiclient;
import com.android.gandharvms.Outward_Tanker_Security.Outward_Tanker;
import com.android.gandharvms.Outward_Tanker_Security.Outward_Tanker_Security;
import com.android.gandharvms.Outward_Tanker_Security.Response_Outward_Security_Fetching;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class OutwardOut_Tanker_Security extends AppCompatActivity {

    EditText intime,serialnumber,date,vehiclenumber,invoiceno,partyname,goodsdiscription,qty,netweight,sign,remark;
    Button submit;
    FirebaseFirestore dbroot;
    TimePickerDialog tpicker;
    Calendar calendar = Calendar.getInstance();
    private String vehicleType = Global_Var.getInstance().MenuType;
    private char nextProcess = Global_Var.getInstance().DeptType;
    private char inOut = Global_Var.getInstance().InOutType;
    private String EmployeName = Global_Var.getInstance().Name;
    private String EmployeId = Global_Var.getInstance().EmpId;
    private int OutwardId;
    private Outward_Tanker outwardTanker;
    RadioButton Trasnportyes,transportno,tremyes,tremno,ewayyes,ewayno,testyes,testno,invoiceyes,invoicenono;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outward_out_tanker_security);
        outwardTanker = Outward_RetroApiclient.insertoutwardtankersecurity();

        intime = findViewById(R.id.etintime);
        serialnumber = findViewById(R.id.etserialnumber);
        vehiclenumber = findViewById(R.id.etvehicleno);
        invoiceno =findViewById(R.id.etinvoicenumber);
        partyname = findViewById(R.id.etpartyname);
        goodsdiscription = findViewById(R.id.etgoodsdisc);
        qty = findViewById(R.id.etqty);
        netweight = findViewById(R.id.etnetweight);
        sign = findViewById(R.id.etsign);
        remark = findViewById(R.id.etremark);

        submit = findViewById(R.id.etssubmit);
        dbroot= FirebaseFirestore.getInstance();

        Trasnportyes = findViewById(R.id.outwaoutrb_LRCopyYes);
        transportno = findViewById(R.id.outwaourb_LRCopyNo);
        tremyes = findViewById(R.id.tremcardyes);
        tremno = findViewById(R.id.tremcardno);
        ewayyes = findViewById(R.id.outwardoutrb_EwaybillYes);
        ewayno = findViewById(R.id.outwardoutrb_EwaybillNo);
        testyes = findViewById(R.id.testreportyes);
        testno = findViewById(R.id.testreportno);
        invoiceyes = findViewById(R.id.invoiceyes);
        invoicenono = findViewById(R.id.invoiceno);

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
                tpicker = new TimePickerDialog(OutwardOut_Tanker_Security.this, new TimePickerDialog.OnTimeSetListener() {
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
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    FetchVehicleDetail(vehiclenumber.getText().toString().trim(), vehicleType, nextProcess, inOut);
                }
            }
        });

    }

    private void FetchVehicleDetail(@NonNull String VehicleNo, String vehicltype, char DeptType, char InOutType) {
        Call<List<Response_Outward_Security_Fetching>> call = Outward_RetroApiclient.insertoutwardtankersecurity().outwardsecurityfetching(VehicleNo, vehicltype, DeptType, InOutType);
        call.enqueue(new Callback<List<Response_Outward_Security_Fetching>>() {
            @Override
            public void onResponse(Call<List<Response_Outward_Security_Fetching>> call, Response<List<Response_Outward_Security_Fetching>> response) {
                if (response.isSuccessful() && response.body()!= null){
                    if (response.body().size() > 0){
                        List<Response_Outward_Security_Fetching> data = response.body();
                        Response_Outward_Security_Fetching obj = data.get(0);
                        OutwardId= obj.getOutwardId();
                        serialnumber.setText(obj.getSerialNumber());
                        vehiclenumber.setText(obj.getVehicleNumber());
                    }
                }else {
                    Log.e("Retrofit", "Error" + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Response_Outward_Security_Fetching>> call, Throwable t) {


            }
        });
    }

    private String getCurrentTime() {
        // Get the current time
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(new Date());
    }
    public void insert(){
//        intime,serialnumber,date,vehiclenumber,invoiceno,partyname,goodsdiscription,qty,netweight,sign,remark;

        String etinvoiceno = invoiceno.getText().toString().trim();
        String etpartyname = partyname.getText().toString().trim();
        String etgooddiscription = goodsdiscription.getText().toString().trim();
        String etsign = sign.getText().toString().trim();
        String outTime = getCurrentTime();
        String etremark = remark.getText().toString().trim();
        int etqty = Integer.parseInt(qty.getText().toString().trim());
        String etnetweight = netweight.getText().toString().trim();

        String lrCopySelection = Trasnportyes.isChecked() ? "Yes" : "No";
        String tremselection = tremyes.isChecked() ? "Yes" : "No";
        String ewayselection = ewayyes.isChecked() ? "Yes" : "No";
        String testreselection = testyes.isChecked() ? "Yes" : "No";
        String invoiceselection = invoiceyes.isChecked() ? "Yes" : "No";

//        String etintime = intime.getText().toString().trim();
//        String etserialnumber = serialnumber.getText().toString().trim();
//        String etdate = date.getText().toString().trim();
//        String etvehiclenumber = vehiclenumber.getText().toString().trim();

        if (etinvoiceno.isEmpty()||etpartyname.isEmpty() ||etgooddiscription.isEmpty()||etnetweight.isEmpty()||etsign.isEmpty()||etremark.isEmpty()){
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
        }else {
//            Map<String,String> items = new HashMap<>();
//
//            items.put("In_Time",intime.getText().toString().trim());
//            items.put("Serial_Number",serialnumber.getText().toString().trim());
//            items.put("Date",date.getText().toString().trim());
//            items.put("Vehicle_Number",vehiclenumber.getText().toString().trim());
//            items.put("Invoice_No",invoiceno.getText().toString().trim());
//            items.put("Party_Name",partyname.getText().toString().trim());
//            items.put("Goods_Discription",goodsdiscription.getText().toString().trim());
//            items.put("Qty",qty.getText().toString().trim());
//            items.put("Net_Weight",netweight.getText().toString().trim());
//            items.put("Sign",sign.getText().toString().trim());
//            items.put("Remark",remark.getText().toString().trim());
//
//
//            dbroot.collection("OutwardOut Tanker Security(Out)").add(items)
//                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
//                        @Override
//                        public void onComplete(@NonNull Task<DocumentReference> task) {
//                            Toast.makeText(OutwardOut_Tanker_Security.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
//                        }
//                    });
            Model_OutwardOut_Security modelOutwardOutSecurity = new Model_OutwardOut_Security(OutwardId,etinvoiceno,etpartyname,
                    etgooddiscription,etsign,lrCopySelection,tremselection,ewayselection,testreselection,invoiceselection,outTime,EmployeId,
                    'S',inOut,vehicleType,etremark,etqty,etnetweight,1,1);

            Call<Boolean> call = outwardTanker.updateOutwardoutsecurity(modelOutwardOutSecurity);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body() != null && response.body() == true){
                        Toast.makeText(OutwardOut_Tanker_Security.this, "Inserted Succesfully !", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(OutwardOut_Tanker_Security.this, "failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}