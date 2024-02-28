package com.android.gandharvms.Outward_Tanker_Production_forms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.gandharvms.Global_Var;
import com.android.gandharvms.Inward_Tanker_Security.Inward_Tanker_Security;
import com.android.gandharvms.Outward_Tanker_Billing.Outward_Tanker_Billinginterface;
import com.android.gandharvms.Outward_Tanker_Billing.Respons_Outward_Tanker_Billing;
import com.android.gandharvms.Outward_Tanker_Security.Outward_RetroApiclient;
import com.android.gandharvms.Outward_Tanker_Weighment.Outward_Tanker_weighment;
import com.android.gandharvms.R;
import com.android.gandharvms.outward_Tanker_Lab_forms.Lab_Model__Outward_Tanker;
import com.android.gandharvms.outward_Tanker_Lab_forms.Outward_Tanker_Lab;
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

public class Outward_Tanker_Production extends AppCompatActivity {

    EditText intime,serialnumber,vehiclenumber,blenderno,transporter,product,howmuch,customer,location,blendingratio,batchno,
            productspesification,custref,packingsatus,rinsingstatus,decisionrule,blendingmaterial,signof,dt,oanum,remark;
    Button submit;
    FirebaseFirestore dbroot;
    TimePickerDialog tpicker;
    Calendar calendar = Calendar.getInstance();
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private final String EmployeId = Global_Var.getInstance().EmpId;
    private int OutwardId;
    private Outward_Tanker_Lab outwardTankerLab;
    DatePickerDialog picker;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outward_tanker_production);

        outwardTankerLab = Outward_RetroApiclient.outwardTankerLab();


        intime= findViewById(R.id.etintime);
        serialnumber=findViewById(R.id.etserialnumber);
        vehiclenumber = findViewById(R.id.etvehicleno);
        blenderno = findViewById(R.id.elblendingno);
        transporter = findViewById(R.id.ettransportername);
        product =findViewById(R.id.etproductname);
        howmuch = findViewById(R.id.ethowmuch);
        customer = findViewById(R.id.etcustname);
        location = findViewById(R.id.etlocation);
        blendingratio = findViewById(R.id.etblendingrationrec);
        batchno = findViewById(R.id.etbatchno);
        productspesification = findViewById(R.id.etproductspesification);
        custref = findViewById(R.id.etcustrefno);
        packingsatus = findViewById(R.id.etpackingstatus);
        rinsingstatus = findViewById(R.id.etrinsingstatus);
        decisionrule = findViewById(R.id.etdecisionrule);
        blendingmaterial = findViewById(R.id.etblendingstatus);
        signof = findViewById(R.id.etsignofproduction);
        remark = findViewById(R.id.etremark);
//        dt = findViewById(R.id.etdatetime);
        oanum = findViewById(R.id.etoanumber);

        submit=findViewById(R.id.etssubmit);
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
                tpicker = new TimePickerDialog(Outward_Tanker_Production.this, new TimePickerDialog.OnTimeSetListener() {
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
//        dt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final Calendar calendar = Calendar.getInstance();
//                int day = calendar.get(Calendar.DAY_OF_MONTH);
//                int month = calendar.get(Calendar.MONTH);
//                int year = calendar.get(Calendar.YEAR);
//                // Array of month abbreviations
//                String[] monthAbbreviations = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
//                picker = new DatePickerDialog(Outward_Tanker_Production.this, new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                        // Use the month abbreviation from the array
//                        String monthAbbreviation = monthAbbreviations[month];
//                        // etdate.setText(dayOfMonth + "/" + monthAbbreviation + "/" + year);
//                        dt.setText(dateFormat.format(calendar.getTime()));
//                    }
//                }, year, month, day);
//                picker.show();
//            }
//        });

        vehiclenumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    FetchVehicleDetails(vehiclenumber.getText().toString().trim(), vehicleType, nextProcess, inOut);
                }
            }
        });

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
                        serialnumber.setText(data.getSerialNumber());
                        vehiclenumber.setText(data.getVehicleNumber());
                        oanum.setText(data.getOAnumber());
                        blenderno.setText(String.valueOf(data.getTankerNumber()));
                        transporter.setText(data.getTransportName());
                        product.setText(data.getProductName());
                        howmuch.setText(String.valueOf(data.getHowMuchQuantityFilled()));
                        customer.setText(data.getCustomerName());
                        location.setText(data.getLocation());
                        blendingratio.setText(data.getBlending_Ratio());
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
    private String getCurrentTime() {
        // Get the current time
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(new Date());
    }

    public void insert(){
//        intime,serialnumber,vehiclenumber,blenderno,transporter,product,howmuch,customer,location,blendingratio,batchno,
//                productspesification,custref,packingsatus,rinsingstatus,decisionrule,blendingmaterial,signof,dt;
        String etintime = intime.getText().toString().trim();
        String outTime = getCurrentTime();
        String uremark = remark.getText().toString().trim();
        int etcustref = Integer.parseInt(custref.getText().toString().trim());
        String packstatus = packingsatus.getText().toString().trim();
        String etrinisingstatus = rinsingstatus.getText().toString().trim();
        String etdecisionrule = decisionrule.getText().toString().trim();
        String etblendingmaterial = blendingmaterial.getText().toString().trim();
        String  etsignof = signof.getText().toString().trim();
        String etbatchno = batchno.getText().toString().trim();


//        String etserialnumber = serialnumber.getText().toString().trim();
//        String etvehiclnumber = vehiclenumber.getText().toString().trim();
//        String etblenderno = blenderno.getText().toString().trim();
//        String ettrasnporter = transporter.getText().toString().trim();
//        String etproduct = product.getText().toString().trim();
//        String ethowmuch = howmuch.getText().toString().trim();
//        String etcustomer = customer.getText().toString().trim();
//        String etlocation = location.getText().toString().trim();
//        String etblendingratio = blendingratio.getText().toString().trim();
//        String etproductspesification = productspesification.getText().toString().trim();
//        String etdt = dt.getText().toString().trim();




        if (etintime.isEmpty()|| etbatchno.isEmpty()||packstatus.isEmpty()||etrinisingstatus.isEmpty()||etdecisionrule.isEmpty()||etblendingmaterial.isEmpty()||etsignof.isEmpty()){
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
        }else {
//            Map<String,String> items = new HashMap<>();
//            items.put("In_Time",intime.getText().toString().trim());
//            items.put("Serial_Number",serialnumber.getText().toString().trim());
//            items.put("Vehicle_Number",vehiclenumber.getText().toString().trim());
//            items.put("Blender_No",blenderno.getText().toString().trim());
//            items.put("Transporter",transporter.getText().toString().trim());
//            items.put("Product",product.getText().toString().trim());
//            items.put("How_Much_Qty_Of_Oil_To_Be_Filled",howmuch.getText().toString().trim());
//            items.put("Customer",customer.getText().toString().trim());
//            items.put("Location",location.getText().toString().trim());
//            items.put("Blending_Ratio",blendingratio.getText().toString().trim());
//            items.put("Batch_No",batchno.getText().toString().trim());
//            items.put("Product_Spesification",productspesification.getText().toString().trim());
//            items.put("Customer_Refrence_Number",custref.getText().toString().trim());
//            items.put("Packing_Status",packingsatus.getText().toString().trim());
//            items.put("Rinsing_Status",rinsingstatus.getText().toString().trim());
//            items.put("Decision_Rule_Applicable_to_Customer",decisionrule.getText().toString().trim());
//            items.put("Blending_Material_Status",blendingmaterial.getText().toString().trim());
//            items.put("Date_Time",dt.getText().toString().trim());
//
//            dbroot.collection("Outward Tanker Production(IN)").add(items)
//                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
//                        @Override
//                        public void onComplete(@NonNull Task<DocumentReference> task) {
//                            Toast.makeText(Outward_Tanker_Production.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
//                        }
//                    });
           Production_Model_Outward productionModelOutward = new Production_Model_Outward(OutwardId,etintime,outTime,uremark,
                   etcustref,packstatus,etrinisingstatus,etdecisionrule,etblendingmaterial,etsignof,
                   EmployeId,'L',inOut,'P',etbatchno,vehicleType);

           Call<Boolean> call = outwardTankerLab.insertinprocessproduction(productionModelOutward);
           call.enqueue(new Callback<Boolean>() {
               @Override
               public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                   if (response.isSuccessful() && response.body() && response.body() == true){
                       Toast.makeText(Outward_Tanker_Production.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
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
                   Toasty.error(Outward_Tanker_Production.this, "failed..!", Toast.LENGTH_SHORT).show();
               }
           });
        }

    }
}