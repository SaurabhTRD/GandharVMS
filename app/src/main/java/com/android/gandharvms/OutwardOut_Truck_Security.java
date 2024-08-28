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
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.gandharvms.Inward_Tanker_Security.Inward_Tanker_Security;
import com.android.gandharvms.LoginWithAPI.Login;
import com.android.gandharvms.LoginWithAPI.LoginMethod;
import com.android.gandharvms.LoginWithAPI.ResponseModel;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.Outward_Tanker_Security.Grid_Outward;
import com.android.gandharvms.Outward_Tanker_Security.Outward_RetroApiclient;
import com.android.gandharvms.Outward_Tanker_Security.Outward_Tanker;
import com.android.gandharvms.Outward_Tanker_Security.Response_Outward_Security_Fetching;
import com.android.gandharvms.Outward_Tanker_Weighment.Outward_Tanker_weighment;
import com.android.gandharvms.Outward_Truck_Laboratory.Outward_Truck_Laboratory;
import com.android.gandharvms.Outward_Truck_Security.Model_OutwardOut_Truck_Security;
import com.android.gandharvms.Outward_Truck_Security.Outward_Truck_Security;
import com.android.gandharvms.Outward_Truck_Security.SecOut_OR_Complete;
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

    EditText intime,serialnumber,vehiclenumber,invoice,party,gooddis,qty,uom1,netweight,qtyuom,outtime,sign,remark,etproduct,erqty,erinvoice;
    Button submit,complete;
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
    private LoginMethod userDetails;
    private String token;
    private String svehicleno;
    ImageView btnlogout,btnhome;
    TextView username,empid;

    public static String Tanker;
    public static String Truck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outward_out_truck_security);
        outwardTanker = Outward_RetroApiclient.insertoutwardtankersecurity();
        userDetails = RetroApiClient.getLoginApi();

        intime=findViewById(R.id.etintime);
        serialnumber=findViewById(R.id.etserialnumber);
        vehiclenumber=findViewById(R.id.etvehical);
//        invoice=findViewById(R.id.etinvoice);
        party=findViewById(R.id.etpartyname);
        gooddis=findViewById(R.id.etdisc);
//        qty=findViewById(R.id.etqty);
//        uom1=findViewById(R.id.qtyuom);
        netweight=findViewById(R.id.etnetweight);
        qtyuom=findViewById(R.id.netweuom);
        sign=findViewById(R.id.etsign);
        remark=findViewById(R.id.etremark);
        erqty = findViewById(R.id.etqty);
        erinvoice = findViewById(R.id.etinvoicenum);

        submit = findViewById(R.id.submit);
        complete = findViewById(R.id.truckotoutsecuritycompleted);
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
//        etproduct = findViewById(R.id.etproductnameoutsecurity);

        btnhome = findViewById(R.id.btn_homeButton);
        btnlogout=findViewById(R.id.btn_logoutButton);
        username=findViewById(R.id.tv_username);
        empid=findViewById(R.id.tv_employeeId);

        String userName=Global_Var.getInstance().Name;
        String empId=Global_Var.getInstance().EmpId;

        username.setText(userName);
        empid.setText(empId);
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OutwardOut_Truck_Security.this, Login.class));
            }
        });
        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OutwardOut_Truck_Security.this, Menu.class));
            }
        });





        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert();
            }
        });
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OutwardOut_Truck_Security.this, SecOut_OR_Complete.class));
            }
        });
        if (getIntent().hasExtra("vehiclenum")) {
            FetchVehicleDetails(getIntent().getStringExtra("vehiclenum"), Global_Var.getInstance().MenuType, nextProcess, inOut);
        }

        intime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                String time =  format.format(calendar.getTime());
                intime.setText(time);
            }
        });


        vehiclenumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    FetchVehicleDetails(vehiclenumber.getText().toString().trim(), vehicleType, nextProcess, inOut);
                }
            }
        });
//        autoCompleteTextView1outwardoutse = findViewById(R.id.qtyuom);
//        qtyUomMapping = new HashMap<>();
//        qtyUomMapping.put("NA", 1);
//        qtyUomMapping.put("Ton", 2);
//        qtyUomMapping.put("Litre", 3);
//        qtyUomMapping.put("KL", 4);
//        qtyUomMapping.put("Kgs", 5);
//        qtyUomMapping.put("pcs", 6);
//
//        qtyuomdrop = new ArrayAdapter<String>(this, R.layout.outwaout_securityqty, new ArrayList<>(qtyUomMapping.keySet()));
//        autoCompleteTextView1outwardoutse.setAdapter(qtyuomdrop);
//        autoCompleteTextView1outwardoutse.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String qtyUomDisplay = parent.getItemAtPosition(position).toString();
//                // Retrieve the corresponding numerical value from the mapping
//                qtyUomNumericValue = qtyUomMapping.get(qtyUomDisplay);
//                if (qtyUomNumericValue != null) {
//                    // Now, you can use qtyUomNumericValue when inserting into the database
//
//                    Toast.makeText(OutwardOut_Truck_Security.this, "qtyUomNumericValue : " + qtyUomNumericValue + " Selected", Toast.LENGTH_SHORT).show();
//                } else {
//                    // Handle the case where the mapping doesn't contain the display value
//                    Toast.makeText(OutwardOut_Truck_Security.this, "Unknown qtyUom : " + qtyUomDisplay, Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

//        autoCompleteTextView2outwardoutse = findViewById(R.id.netweuom);
//        netweuomdrop = new ArrayAdapter<String>(this, R.layout.outwaout_netwt, new ArrayList<>(qtyUomMapping.keySet()));
//        autoCompleteTextView2outwardoutse.setAdapter(netweuomdrop);
//        autoCompleteTextView2outwardoutse.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String netuomdisply = parent.getItemAtPosition(position).toString();
//                netweuomvalue = qtyUomMapping.get(netuomdisply);
//                if (qtyUomNumericValue != null) {
//                    Toast.makeText(OutwardOut_Truck_Security.this, "netwe: " + netuomdisply + " Selected", Toast.LENGTH_SHORT).show();
//
//                } else {
//                    Toast.makeText(OutwardOut_Truck_Security.this, "Unknown qtyUom : " + netweuom, Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }
    private String getCurrentTime() {
        // Get the current time
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(new Date());
    }
    private String getWeightUnit(int unitCode) {
        switch (unitCode) {
            case 1:
                return "kl";
            case 2:
                return "Ton";
            case 3:
                return "Litre";
            case 4:
                return "KL";
            case 5:
                return "Kgs";
            case 6:
                return "Pcs";
            case 7:
                return "M3";
            default:
                return "unknown";// or any default value you want to set
        }
    }

    public void makeNotification(String vehicleNumber, String outTime) {
        Call<List<ResponseModel>> call = userDetails.getUsersListData();
        call.enqueue(new Callback<List<ResponseModel>>() {
            @Override
            public void onResponse(Call<List<ResponseModel>> call, Response<List<ResponseModel>> response) {
                if (response.isSuccessful()){
                    List<ResponseModel> userList = response.body();
                    if (userList != null){
                        for (ResponseModel resmodel : userList){
                            String specificRole = "Security";
                            if (specificRole.equals(resmodel.getDepartment())) {
                                token = resmodel.getToken();

                                FcmNotificationsSender notificationsSender = new FcmNotificationsSender(
                                        token,
                                        "OutwardOut Truck Billing Process Done..!",
                                        "Vehicle Number:-" + vehicleNumber + " has completed Weighment process at " + outTime,
                                        getApplicationContext(),
                                        OutwardOut_Truck_Security.this
                                );
                                notificationsSender.SendNotifications();
                            }
                        }
                    }
                }
                else {
                    Log.d("API", "Unsuccessful API response");
                }
            }

            @Override
            public void onFailure(Call<List<ResponseModel>> call, Throwable t) {

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
                Toasty.error(OutwardOut_Truck_Security.this, "failed..!", Toast.LENGTH_SHORT).show();
            }
        });
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
//                        qty.setText(String.valueOf(obj.getOutTotalQty()));
//                        qty.setEnabled(false);
//                        uom1.setText(String.valueOf(obj.getOutTotalQtyUOM()));
                        netweight.setText(obj.getNetWeight());
                        netweight.setEnabled(false);
                        svehicleno = obj.getVehicleNumber();
//                        etproduct.setText(obj.getProductName());
//                        etproduct.setEnabled(false);
//                        invoice.setText(obj.getInvoiceNumber());
                        erinvoice.setText(obj.getOutInvoiceNumber());
                        erinvoice.setEnabled(false);
                        erqty.setText(obj.getOutTotalQty());
                        erqty.setEnabled(false);
                        qtyuom.setText(getWeightUnit(obj.getOutTotalQtyUOM()));
                        qtyuom.setEnabled(false);
                    }else {
                        Toasty.error(OutwardOut_Truck_Security.this, "This Vehicle Number Is Not Available..!", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Log.e("Retrofit", "Error" + response.code());
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
//        String etinvoice = invoice.getText().toString().trim();
        String etgooddis = gooddis.getText().toString().trim();


//        int qtyuom = Integer.parseInt(qtyUomNumericValue.toString().trim());
//        int netweuom = Integer.parseInt(netweuomvalue.toString().trim());
        if (etintime.isEmpty()||etsign.isEmpty()||etremark.isEmpty()){
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
        }else {
            Model_OutwardOut_Truck_Security modelOutwardOutTruckSecurity = new Model_OutwardOut_Truck_Security(OutwardId,"",
                    "",etgooddis,etsign,lrCopySelection,tremselection,ewayselection,testreselection,invoiceselection,
                    outTime,EmployeId,'S',inOut,vehicleType,etremark);
            Call<Boolean> call = outwardTanker.updateout_Truck_wardoutsecurity(modelOutwardOutTruckSecurity);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body() && response.body() == true){
                        makeNotification(svehicleno, outTime);
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