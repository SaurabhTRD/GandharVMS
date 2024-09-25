package com.android.gandharvms.Outward_Truck_Dispatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

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
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.gandharvms.FcmNotificationsSender;
import com.android.gandharvms.Global_Var;
import com.android.gandharvms.InwardCompletedGrid.GridCompleted;
import com.android.gandharvms.Inward_Tanker_Security.Inward_Tanker_Security;
import com.android.gandharvms.LoginWithAPI.LoginMethod;
import com.android.gandharvms.LoginWithAPI.ResponseModel;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.Outward_Tanker_Security.Grid_Outward;
import com.android.gandharvms.Outward_Tanker_Security.Outward_RetroApiclient;
import com.android.gandharvms.Outward_Tanker_Weighment.Outward_Tanker_weighment;
import com.android.gandharvms.Outward_Tanker_Weighment.Outward_weighment;
import com.android.gandharvms.Outward_Truck;
import com.android.gandharvms.Outward_Truck_Billing.Outward_Truck_Billing;
import com.android.gandharvms.Outward_Truck_Logistic.Outward_Truck_Logistics;
import com.android.gandharvms.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

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

public class Outward_Truck_Dispatch extends AppCompatActivity {

//    String [] items = {"Packing of 210 Ltr","Packing of 50 Ltr","Packing of 26 Ltr","Packing of 20 Ltr","Packing of 10 Ltr",
//            "Packing of Box & Bucket"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItems;

    EditText intime,serialnumber,date,vehiclenumber,material,qty,partyname,oanumber,typepackeging,disfficer,datetime,
            secofficer,datetime2,signeweighment,datetime3,remark,etother,ettotal;
    Button submit,logisticbutton,btnlogicomplted;
    FirebaseFirestore dbroot;
    TimePickerDialog tpicker;
    Calendar calendar = Calendar.getInstance();
    DatePickerDialog picker;
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private final String EmployeId = Global_Var.getInstance().EmpId;
    private int OutwardId;
    private Outward_Truck_interface outwardTruckInterface;
    Map<String, Integer> typepcak = new HashMap<>();
    ArrayAdapter<String> packdrop;
    Integer dropUomNumericValue = 1;
    SimpleDateFormat dtFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
    private LoginMethod userDetails;
    private String token;
    LinearLayout linearLayout;
    List<String> packinglist = new ArrayList<>();
    private int uqty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outward_truck_dispatch);
        outwardTruckInterface = Outward_RetroApiclient.outwardtruckdispatch();
        userDetails = RetroApiClient.getLoginApi();

        FirebaseMessaging.getInstance().subscribeToTopic(token);
//        autoCompleteTextView = findViewById(R.id.typepacking);

//        adapterItems = new ArrayAdapter<String>(this,R.layout.dropdown_outward_truck_dispatch,items);
//        autoCompleteTextView.setAdapter(packdrop);

//        autoCompleteTextView = findViewById(R.id.typepacking);
//        typepcak = new HashMap<>();
//        typepcak.put("Packing of 210 Ltr", 1);
//        typepcak.put("Packing of 50 Ltr", 2);
//        typepcak.put("Packing of 26 Ltr", 3);
//        typepcak.put("Packing of 20 Ltr", 4);
//        typepcak.put("Packing of 10 Ltr", 5);
//        typepcak.put("Packing of Box & Bucket", 6);


//        packdrop = new ArrayAdapter<>(this,R.layout.dropdown_outward_truck_dispatch,new ArrayList<>(typepcak.keySet()));
//        autoCompleteTextView.setAdapter(packdrop);
//        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String typepcakdisplay = parent.getItemAtPosition(position).toString();
//                dropUomNumericValue = typepcak.get(typepcakdisplay);
//                if (dropUomNumericValue != null) {
//                    // Now, you can use qtyUomNumericValue when inserting into the database
//
//                    Toast.makeText(Outward_Truck_Dispatch.this, "qtyUomNumericValue : " + dropUomNumericValue + " Selected", Toast.LENGTH_SHORT).show();
//                } else {
//                    // Handle the case where the mapping doesn't contain the display value
//                    Toast.makeText(Outward_Truck_Dispatch.this, "Unknown qtyUom : " + typepcakdisplay, Toast.LENGTH_SHORT).show();
//                }
//                Toast.makeText(getApplicationContext(), "Item"+typepcakdisplay, Toast.LENGTH_SHORT).show();
//            }
//        });


        intime = findViewById(R.id.etintime);
        serialnumber = findViewById(R.id.etserialnumber);
        vehiclenumber = findViewById(R.id.etvehical);
        oanumber = findViewById(R.id.etoanumber);

        partyname= findViewById(R.id.ettransport);
        remark = findViewById(R.id.etremark);
//        ettotal = findViewById(R.id.ettotaalwt);

//        typepackeging= findViewById(R.id.typepacking);
        qty=findViewById(R.id.etqty2);
        disfficer=findViewById(R.id.etdispatchofficer);


        submit = findViewById(R.id.etssubmit);
        btnlogicomplted = findViewById(R.id.outwardtruckdespatchcompleted);
        dbroot= FirebaseFirestore.getInstance();

        etother = findViewById(R.id.etotheroption);


        // type of packaging

//        linearLayout = findViewById(R.id.truckdispatch_list);
//        logisticbutton = findViewById(R.id.logistic_button_add);
//        logisticbutton.setOnClickListener(this::onClick);

//        packinglist.add(0, "Packing of 26 Ltr");
//        packinglist.add(1, "Packing of 10 Ltr");
//        packinglist.add(2, "Packing of 20 Ltr");
//        packinglist.add(3, "Packing of 210 Ltr");
//        packinglist.add(4, "Packing of Box & Bucket");
//        packinglist.add(5, "Packing of 50 Ltr");


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert();
            }
        });
        btnlogicomplted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Outward_Truck_Dispatch.this, Disp_OR_Complete.class));
            }
        });

        intime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                String time =  format.format(calendar.getTime());
                intime.setText(time);
            }
        });

//        date.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final Calendar calendar = Calendar.getInstance();
//                int day = calendar.get(Calendar.DAY_OF_MONTH);
//                int month = calendar.get(Calendar.MONTH);
//                int year = calendar.get(Calendar.YEAR);
//                picker = new DatePickerDialog(Outward_Truck_Dispatch.this, new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                        date.setText(dayOfMonth + "/" + (month +1 ) + "/" + year);
//                    }
//                } ,year,month,day);
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

        if (getIntent().hasExtra("vehiclenum")) {
            FetchVehicleDetails(getIntent().getStringExtra("vehiclenum"), Global_Var.getInstance().MenuType, nextProcess, inOut);
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
                            String specificRole = "Production";
                            if (specificRole.equals(resmodel.getDepartment())) {
                                token = resmodel.getToken();

                                FcmNotificationsSender notificationsSender = new FcmNotificationsSender(
                                        token,
                                        "Outward Tanker Despatch Process Done..!",
                                        "Vehicle Number:-" + vehicleNumber + " has completed Despatch process at " + outTime,
                                        getApplicationContext(),
                                        Outward_Truck_Dispatch.this
                                );
                                notificationsSender.triggerSendNotification();
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
                Toasty.error(Outward_Truck_Dispatch.this, "failed..!", Toast.LENGTH_SHORT).show();
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
                    if (data.getVehicleNumber() != "" && data.getVehicleNumber()!= null){
                        OutwardId = data.getOutwardId();
                        serialnumber.setText(data.getSerialNumber());
                        vehiclenumber.setText(data.getVehicleNumber());
                        oanumber.setText(data.getOAnumber());
                        partyname.setText(data.getTransportName());
                        partyname.setEnabled(false);
                        serialnumber.setEnabled(false);
                        vehiclenumber.setEnabled(false);

                    }else {
                        Toasty.error(Outward_Truck_Dispatch.this, "This Vehicle Number Is Not Available..!", Toast.LENGTH_SHORT).show();
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
            }
        });

    }
    private String getCurrentTime() {
        // Get the current time
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(new Date());
    }

    public void insert(){
//        intime,serialnumber,date,vehiclenumber,material,qty,partyname,oanumber,typepackeging,qty2,disfficer,datetime,
//                secofficer,datetime2,signeweighment,datetime3;
        String etintime = intime.getText().toString().trim();
        String outTime = getCurrentTime();
        String uremark = remark.getText().toString().trim();
        String etdisfficer = disfficer.getText().toString().trim();
//        int etqty2 = Integer.parseInt(qty2.getText().toString().trim());
        if (!qty.getText().toString().isEmpty()){
            try {
                uqty = Integer.parseInt(qty.getText().toString().trim());
            }catch (NumberFormatException e){
                e.printStackTrace();
            }
        }
//        int ettypepacking = Integer.parseInt(typepackeging.getText().toString().trim());
        String etseralnumber = serialnumber.getText().toString().trim();
        String etvehiclenumber = vehiclenumber.getText().toString().trim();
        String uother = etother.getText().toString().trim();
        String updateoa = oanumber.getText().toString().trim();
//        int utotalwt = Integer.parseInt(ettotal.getText().toString().trim());
//        String etdate = date.getText().toString().trim();
//        String etmaterial = material.getText().toString().trim();
//        String etqty=qty.getText().toString().trim();
//        String etpartyname = partyname.getText().toString().trim();
//        String etdatetime = datetime.getText().toString().trim();
//        String etsecofficer = secofficer.getText().toString().trim();
//        String etdatetime2 = datetime2.getText().toString().trim();
//        String etsignweigment = signeweighment.getText().toString().trim();
//        String etdatetime3 = datetime3.getText().toString().trim();

        if (etintime.isEmpty()||etseralnumber.isEmpty()|| etvehiclenumber.isEmpty()|| etdisfficer.isEmpty()||uremark.isEmpty()){
            Toasty.warning(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
        }else {
//            Map<String,String>items = new HashMap<>();
//
//            items.put("In_Time",intime.getText().toString().trim());
//            items.put("Serial_Number",serialnumber.getText().toString().trim());
//            items.put("Date",date.getText().toString().trim());
//            items.put("Vehicle_Number",vehiclenumber.getText().toString().trim());
//            items.put("Material",material.getText().toString().trim());
//            items.put("Qty",qty.getText().toString().trim());
//            items.put("Party_Name",partyname.getText().toString().trim());
//            items.put("OA_Number",oanumber.getText().toString().trim());
//            items.put("Type_Of_Packaging",typepackeging.getText().toString().trim());
//            items.put("Packaging_Qty",qty2.getText().toString().trim());
//            items.put("Signed_By_Dispatch_Officer",disfficer.getText().toString().trim());
//            items.put("Dispatch_Date_Time",datetime.getText().toString().trim());
//            items.put("Checked_By_Security_Officer",secofficer.getText().toString().trim());
//            items.put("Security_Date_Time",datetime2.getText().toString().trim());
//            items.put("Signed_By_Weighment",signeweighment.getText().toString().trim());
//            items.put("Weighment_Date_Time",datetime3.getText().toString().trim());
//
//            dbroot.collection("Outward_Truck_Dispatch(IN)").add(items)
//                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
//                        @Override
//                        public void onComplete(@NonNull Task<DocumentReference> task) {
//                            Toast.makeText(Outward_Truck_Dispatch.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
//                        }
//                    });

            //Extra material dynamic view
//            List<Map<String, String>> despatchmateriallist = new ArrayList<>();
//
//            for (int i = 0; i < linearLayout.getChildCount(); i++) {
//                View childView = linearLayout.getChildAt(i);
//                if (childView != null) {
//                    EditText materialEditText = childView.findViewById(R.id.editmaterial);
//                    EditText qtyEditText = childView.findViewById(R.id.editqty);
//                    AppCompatSpinner uomSpinner = childView.findViewById(R.id.spinner_team);
//
//                    String dynamaterial = materialEditText.getText().toString().trim();
//                    String dynaqty = qtyEditText.getText().toString().trim();
//                    String dynaqtyuom = uomSpinner.getSelectedItem().toString();
//
//                    // Check if both material and quantity fields are not empty
//                    if (!dynamaterial.isEmpty() && !dynaqty.isEmpty() && !dynaqtyuom.isEmpty()) {
//                        Map<String, String> materialMap = new HashMap<>();
//                        materialMap.put("material", dynamaterial);
//                        materialMap.put("qty", dynaqty);
//                        materialMap.put("qtyuom", dynaqtyuom);
//                        // Add material data to the list
//                        despatchmateriallist.add(materialMap);
//                    }
//                }
//            }
            Model_Outward_Truck_Dispatch modelOutwardTruckDispatch = new Model_Outward_Truck_Dispatch(OutwardId,etintime,outTime,
                    'D',uremark,etdisfficer,uqty,1,EmployeId,EmployeId,
                    etseralnumber,etvehiclenumber,'P',inOut,vehicleType,"",uother,updateoa);
            Call<Boolean> call = outwardTruckInterface.insertdispatch(modelOutwardTruckDispatch);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body() && response.body() == true){
                        makeNotification(etvehiclenumber, outTime);
                        Toasty.success(Outward_Truck_Dispatch.this, "Data Inserted Successfully", Toast.LENGTH_SHORT,true).show();
                        startActivity(new Intent(Outward_Truck_Dispatch.this, Outward_Truck.class));
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
                    Toasty.error(Outward_Truck_Dispatch.this, "failed..!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

//    public void onClick(View v) {
//        addlogisticmaterial();
//    }
//    public void addlogisticmaterial(){
//        View logisticmaterialview = getLayoutInflater().inflate(R.layout.row_logistic_add_material, null, false);
//
//        EditText editText = logisticmaterialview.findViewById(R.id.editmaterial);
//        EditText editqty = logisticmaterialview.findViewById(R.id.editqty);
//        AppCompatSpinner spinner = logisticmaterialview.findViewById(R.id.spinner_team);
//        ImageView img = logisticmaterialview.findViewById(R.id.editcancel);
//
//        linearLayout.addView(logisticmaterialview);
//
//        ArrayAdapter arrayAdapter = new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, packinglist);
//        spinner.setAdapter(arrayAdapter);
//        img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                removeView(logisticmaterialview);
//            }
//        });
//    }
//
//    private void removeView(View view) {
//
//        linearLayout.removeView(view);
//    }


    public void outwardtruckdespending(View view){
        Intent intent = new Intent(this, Grid_Outward.class);
        startActivity(intent);
    }
}