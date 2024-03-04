package com.android.gandharvms;

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
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.gandharvms.Inward_Tanker_Security.Inward_Tanker_Security;
import com.android.gandharvms.Outward_Tanker_Security.Grid_Outward;
import com.android.gandharvms.Outward_Tanker_Security.Outward_RetroApiclient;
import com.android.gandharvms.Outward_Tanker_Security.Outward_Tanker;
import com.android.gandharvms.Outward_Tanker_Security.Response_Outward_Security_Fetching;
import com.android.gandharvms.Outward_Truck_Laboratory.Outward_Truck_Laboratory;
import com.android.gandharvms.Outward_Truck_Security.Model_OutwardOut_Truck_Security;
import com.android.gandharvms.Outward_Truck_Security.Outward_Truck_Security;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

public class OutwardOut_Truck_Security extends AppCompatActivity {

    EditText intime,serialnumber,vehiclenumber,invoice,party,gooddis,qty,uom1,netweight,uom2,outtime,sign,remark;
    Button submit;
    FirebaseFirestore dbroot;
    TimePickerDialog tpicker;
    Calendar calendar = Calendar.getInstance();
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private final String EmployeId = Global_Var.getInstance().EmpId;
    private int OutwardId;
    RadioButton Trasnportyes,transportno,tremyes,tremno,ewayyes,ewayno,testyes,testno,invoiceyes,invoicenono;
    private Outward_Tanker outwardTanker;
    AutoCompleteTextView autoCompleteTextView, autoCompleteTextView1outwardoutse, autoCompleteTextView2outwardoutse;
    Map<String, Integer> qtyUomMapping = new HashMap<>();
    ArrayAdapter<String> qtyuomdrop;
    Integer qtyUomNumericValue = 1;
    Integer netweuomvalue = 1;
    ArrayAdapter<String> netweuomdrop;
    String[] netweuom = {"Ton", "Litre", "KL", "Kgs", "pcs", "NA"};
    SimpleDateFormat dtFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
    DatePickerDialog picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outward_out_truck_security);
        outwardTanker = Outward_RetroApiclient.insertoutwardtankersecurity();

        intime=findViewById(R.id.etintime);
        serialnumber=findViewById(R.id.etserialnumber);
        vehiclenumber=findViewById(R.id.etvehical);
        invoice=findViewById(R.id.etinvoice);
        party=findViewById(R.id.etpartyname);
        gooddis=findViewById(R.id.etdisc);
        qty=findViewById(R.id.etqty);
        uom1=findViewById(R.id.qtyuom);
        netweight=findViewById(R.id.etnetweight);
        uom2=findViewById(R.id.netweuom);
        sign=findViewById(R.id.etsign);
        remark=findViewById(R.id.etremark);

        submit = findViewById(R.id.submit);
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
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                // Array of month abbreviations
                String[] monthAbbreviations = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
                picker = new DatePickerDialog(OutwardOut_Truck_Security.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Use the month abbreviation from the array
                        String monthAbbreviation = monthAbbreviations[month];
                        // etdate.setText(dayOfMonth + "/" + monthAbbreviation + "/" + year);
                        intime.setText(dtFormat.format(calendar.getTime()));
                    }
                }, year, month, day);
                picker.show();
            }
        });


        vehiclenumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    FetchVehicleDetails(vehiclenumber.getText().toString().trim(), vehicleType, nextProcess, inOut);
                }
            }
        });
        autoCompleteTextView1outwardoutse = findViewById(R.id.qtyuom);
        qtyUomMapping = new HashMap<>();
        qtyUomMapping.put("NA", 1);
        qtyUomMapping.put("Ton", 2);
        qtyUomMapping.put("Litre", 3);
        qtyUomMapping.put("KL", 4);
        qtyUomMapping.put("Kgs", 5);
        qtyUomMapping.put("pcs", 6);

        qtyuomdrop = new ArrayAdapter<String>(this, R.layout.outwaout_securityqty, new ArrayList<>(qtyUomMapping.keySet()));
        autoCompleteTextView1outwardoutse.setAdapter(qtyuomdrop);
        autoCompleteTextView1outwardoutse.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String qtyUomDisplay = parent.getItemAtPosition(position).toString();
                // Retrieve the corresponding numerical value from the mapping
                qtyUomNumericValue = qtyUomMapping.get(qtyUomDisplay);
                if (qtyUomNumericValue != null) {
                    // Now, you can use qtyUomNumericValue when inserting into the database

                    Toast.makeText(OutwardOut_Truck_Security.this, "qtyUomNumericValue : " + qtyUomNumericValue + " Selected", Toast.LENGTH_SHORT).show();
                } else {
                    // Handle the case where the mapping doesn't contain the display value
                    Toast.makeText(OutwardOut_Truck_Security.this, "Unknown qtyUom : " + qtyUomDisplay, Toast.LENGTH_SHORT).show();
                }
            }
        });

        autoCompleteTextView2outwardoutse = findViewById(R.id.netweuom);
        netweuomdrop = new ArrayAdapter<String>(this, R.layout.outwaout_netwt, new ArrayList<>(qtyUomMapping.keySet()));
        autoCompleteTextView2outwardoutse.setAdapter(netweuomdrop);
        autoCompleteTextView2outwardoutse.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String netuomdisply = parent.getItemAtPosition(position).toString();
                netweuomvalue = qtyUomMapping.get(netuomdisply);
                if (qtyUomNumericValue != null) {
                    Toast.makeText(OutwardOut_Truck_Security.this, "netwe: " + netuomdisply + " Selected", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(OutwardOut_Truck_Security.this, "Unknown qtyUom : " + netweuom, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private String getCurrentTime() {
        // Get the current time
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(new Date());
    }

    private void FetchVehicleDetails(@NonNull String vehicleNo, String vehicleType, char NextProcess, char inOut) {
        Call<List<Response_Outward_Security_Fetching>> call = Outward_RetroApiclient.insertoutwardtankersecurity().outwardsecurityfetching(vehicleNo, vehicleType, NextProcess, inOut);
        call.enqueue(new Callback<List<Response_Outward_Security_Fetching>>() {
            @Override
            public void onResponse(Call<List<Response_Outward_Security_Fetching>> call, Response<List<Response_Outward_Security_Fetching>> response) {
                if (response.isSuccessful() && response.body()!= null){
                    if (response.body().size() >0){
                        List<Response_Outward_Security_Fetching> data = response.body();
                        Response_Outward_Security_Fetching obj = data.get(0);
                        OutwardId = obj.getOutwardId();
                        serialnumber.setText(obj.getSerialNumber());
                        vehiclenumber.setText(obj.getVehicleNumber());
                        party.setText(obj.getCustomerName());
                        serialnumber.setEnabled(false);
                        vehiclenumber.setEnabled(false);
                        party.setEnabled(false);



                    }else {
                        Log.e("Retrofit", "Error" + response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Response_Outward_Security_Fetching>> call, Throwable t) {

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
//        intime,serialnumber,vehiclenumber,invoice,party,gooddis,qty,uom1,netweight,uom2,outtime,sign,remark;

        String lrCopySelection = Trasnportyes.isChecked() ? "Yes" : "No";
        String tremselection = tremyes.isChecked() ? "Yes" : "No";
        String ewayselection = ewayyes.isChecked() ? "Yes" : "No";
        String testreselection = testyes.isChecked() ? "Yes" : "No";
        String invoiceselection = invoiceyes.isChecked() ? "Yes" : "No";
        String outTime = getCurrentTime();
        String etintime = intime.getText().toString().trim();
        String etsign = sign.getText().toString().trim();
        String etremark = remark.getText().toString().trim();

        String etinvoice = invoice.getText().toString().trim();
        String etgooddis = gooddis.getText().toString().trim();
        int etqty = Integer.parseInt(qty.getText().toString().trim());
//        int etuom1 = Integer.parseInt(uom1.getText().toString().trim());
        String etnetweight = netweight.getText().toString().trim();
//        int etuom2 = Integer.parseInt(uom2.getText().toString().trim());

        int qtyuom = Integer.parseInt(qtyUomNumericValue.toString().trim());
        int netweuom = Integer.parseInt(netweuomvalue.toString().trim());



//        String etserialnumber = serialnumber.getText().toString().trim();
//        String etvehiclenumber = vehiclenumber.getText().toString().trim();
//
//        String etparty= party.getText().toString().trim();
//
//


        if (etintime.isEmpty()||etsign.isEmpty()||etremark.isEmpty()){
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
        }else {
//            Map<String,String>items = new HashMap<>();
//
//            items.put("In_Time",intime.getText().toString().trim());
//            items.put("Serial_Number",serialnumber.getText().toString().trim());
//            items.put("Vehicle_Number",vehiclenumber.getText().toString().trim());
//            items.put("Invoice_No",invoice.getText().toString().trim());
//            items.put("Party_Name",party.getText().toString().trim());
//            items.put("Goods_Discription",gooddis.getText().toString().trim());
//            items.put("Qty",qty.getText().toString().trim());
//            items.put("Uom_qty",uom1.getText().toString().trim());
//            items.put("Net_Weight",netweight.getText().toString().trim());
//            items.put("Uom_Netweight",uom2.getText().toString().trim());
//            items.put("Out_Time",outtime.getText().toString().trim());
//            items.put("Sign",sign.getText().toString().trim());
//            items.put("Remark",remark.getText().toString().trim());
//
//            dbroot.collection("OutwardOut Truck Security(OUT)").add(items)
//                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
//                        @Override
//                        public void onComplete(@NonNull Task<DocumentReference> task) {
//                            Toast.makeText(OutwardOut_Truck_Security.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
//                        }
//                    });
            Model_OutwardOut_Truck_Security modelOutwardOutTruckSecurity = new Model_OutwardOut_Truck_Security(OutwardId,etinvoice,
                    "",etgooddis,etsign,lrCopySelection,tremselection,ewayselection,testreselection,invoiceselection,
                    outTime,EmployeId,'S',inOut,vehicleType,etremark,etqty,etnetweight,qtyuom,netweuom);
            Call<Boolean> call = outwardTanker.updateout_Truck_wardoutsecurity(modelOutwardOutTruckSecurity);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body() && response.body() == true){
                        Toasty.success(OutwardOut_Truck_Security.this, "Data Inserted Succesfully !", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(OutwardOut_Truck_Security.this, Menu.class));
                        finish();
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
                    Toasty.error(OutwardOut_Truck_Security.this, "failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    public void outwardoutsecpending(View view){
        Intent intent = new Intent(this, Grid_Outward.class);
        startActivity(intent);
    }
}