package com.android.gandharvms.Outward_Truck_Dispatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.gandharvms.FcmNotificationsSender;
import com.android.gandharvms.Global_Var;
import com.android.gandharvms.LoginWithAPI.Login;
import com.android.gandharvms.LoginWithAPI.LoginMethod;
import com.android.gandharvms.LoginWithAPI.ResponseModel;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.Menu;
import com.android.gandharvms.NotificationAlerts.NotificationCommonfunctioncls;
import com.android.gandharvms.Outward_Tanker_Security.Grid_Outward;
import com.android.gandharvms.Outward_Tanker_Security.Outward_RetroApiclient;
import com.android.gandharvms.Outward_Truck;
import com.android.gandharvms.Outward_Truck_Weighment.Outward_Truck_weighment;
import com.android.gandharvms.R;
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

public class Outward_DesIndustriaLoading_Form extends NotificationCommonfunctioncls {

    Button btndesilsubmit,pending,submit;
    EditText etdesilserialnumber,etdesilvehiclenumber,etdesiltransport,etdesilintime,etdesiltotqty,
            etdesilremark,etdesiltypeofpackingid;
    EditText tenltr,eighteenltr,twentyltr,twentysixltr,fiftyltr,twotenltr,boxbucket,weight,indussign,indusremark,oa,party;
    String[] typeofpacking= {"Packing of 210 Ltr", "Packing of 50 Ltr", "Packing Of 26 Ltr", "Packing of 20 Ltr", "Packing of 10 Ltr", "Packing of Box & Bucket"};
    Integer typeofpackingNumericValue = 1;
    AutoCompleteTextView  typeofpackingautoCompleteTextView1;
    Map<String, Integer> typeofpackingMapping = new HashMap<>();
    ArrayAdapter<String> typeofpackingdrop;
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    public char inOut = Global_Var.getInstance().InOutType;
    private final String EmployeId = Global_Var.getInstance().EmpId;
    private int OutwardId;
    private Outward_Truck_interface outwardTruckInterface;
    private LoginMethod userDetails;
    AutoCompleteTextView dept;
    ArrayAdapter<String> nextdeptdrop;
    Map<String, String> nextdeptmapping = new HashMap<>();
    String nextdeptvalue = "W";
    String deptNumericValue = "W";
    private int Id;
    public String vehnumber="";

    ImageView btnlogout,btnhome;
    TextView username,empid;

    public static String Tanker;
    public static String Truck;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outward_des_industria_loading_form);
        outwardTruckInterface = Outward_RetroApiclient.outwardtruckdispatch();
        userDetails = RetroApiClient.getLoginApi();
//        typeofpackingautoCompleteTextView1 = findViewById(R.id.autodesindusloadTypeOfPacking);
//        typeofpackingMapping = new HashMap<>();
//        typeofpackingMapping.put("Packing of 210 Ltr", 1);
//        typeofpackingMapping.put("Packing of 50 Ltr", 2);
//        typeofpackingMapping.put("Packing Of 26 Ltr", 3);
//        typeofpackingMapping.put("Packing of 20 Ltr", 4);
//        typeofpackingMapping.put("Packing of 10 Ltr", 5);
//        typeofpackingMapping.put("Packing of Box & Bucket", 6);

        typeofpackingdrop = new ArrayAdapter<String>(this, R.layout.in_ta_se_qty, new ArrayList<>(typeofpackingMapping.keySet()));
//        typeofpackingautoCompleteTextView1.setAdapter(typeofpackingdrop);
//        typeofpackingautoCompleteTextView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String qtyUomDisplay = parent.getItemAtPosition(position).toString();
//                // Retrieve the corresponding numerical value from the mapping
//                typeofpackingNumericValue = typeofpackingMapping.get(qtyUomDisplay);
//                if (typeofpackingNumericValue != null) {
//                    // Now, you can use qtyUomNumericValue when inserting into the database
//                    Toasty.success(Outward_DesIndustriaLoading_Form.this, "Type Of Packing : " + qtyUomDisplay + " Selected", Toast.LENGTH_SHORT).show();
//                } else {
//                    // Handle the case where the mapping doesn't contain the display value
//                    Toasty.error(Outward_DesIndustriaLoading_Form.this, "Default Type Of Packing  : " + "NA" + " Selected", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        etdesilserialnumber=findViewById(R.id.etdesindusloadserialnumber);
        etdesilvehiclenumber=findViewById(R.id.etdesindusloadvehical);
        etdesiltransport=findViewById(R.id.etdesindusloadtranseportname);
        etdesilintime=findViewById(R.id.etdesindusloadintime);
        etdesilremark=findViewById(R.id.eddesindusloadremark);
        etdesiltypeofpackingid=findViewById(R.id.etdesindusloadserialnumber);
        oa= findViewById(R.id.etoanumberindus);
        party = findViewById(R.id.etpartyname);
//        etdesiltypeofpackingid=findViewById(R.id.autodesindusloadTypeOfPacking);

        btndesilsubmit=findViewById(R.id.etdesindusloadsubmit);
        pending = findViewById(R.id.pendingindustrial);

        tenltr = findViewById(R.id.etdesindusloadpacking10ltrqty);
        eighteenltr = findViewById(R.id.etdesindusloadpacking18ltrqty);
        twentyltr = findViewById(R.id.etdesindusloadpacking20ltrqty);
        twentysixltr = findViewById(R.id.etdesindusloadpacking26ltrqty);
        fiftyltr = findViewById(R.id.etdesindusloadpacking50ltrqty);
        twotenltr = findViewById(R.id.etdesindusloadpacking210ltrqty);
        boxbucket = findViewById(R.id.etdesindusloadpackingboxbucket);
        etdesiltotqty=findViewById(R.id.etdesindusloadTotalQuantity);
        weight = findViewById(R.id.industrialweight);
        indussign = findViewById(R.id.industrialsign);
        indusremark = findViewById(R.id.eddesindusloadremark);

        tenltr.addTextChangedListener(textWatcher);
        eighteenltr.addTextChangedListener(textWatcher);
        twentyltr.addTextChangedListener(textWatcher);
        twentysixltr.addTextChangedListener(textWatcher);
        fiftyltr.addTextChangedListener(textWatcher);
        twotenltr.addTextChangedListener(textWatcher);
        boxbucket.addTextChangedListener(textWatcher);


        FirebaseMessaging.getInstance().subscribeToTopic(token);
        submit = findViewById(R.id.etdesindusloadsubmit);
        setupHeader();
        /*btnhome = findViewById(R.id.btn_homeButton);
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
                startActivity(new Intent(Outward_DesIndustriaLoading_Form.this, Login.class));
            }
        });
        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Outward_DesIndustriaLoading_Form.this, Menu.class));
            }
        });*/
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Id == 0){
                    indusinsert();
                }else {
                    indusupdate();
                }

            }
        });
        etdesilintime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                String time =  format.format(calendar.getTime());
                etdesilintime.setText(time);
            }
        });

        if (getIntent().hasExtra("vehiclenum")) {
            FetchVehicleDetails(getIntent().getStringExtra("vehiclenum"), Global_Var.getInstance().MenuType, nextProcess, inOut);
        }
        etdesilvehiclenumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasfocus) {
                if (!hasfocus){
                    FetchVehicleDetails(etdesilvehiclenumber.getText().toString().trim(), vehicleType, nextProcess, inOut);
                }
            }
        });

        nextdeptmapping = new HashMap<>();
        nextdeptmapping.put("NA", "W");
        nextdeptmapping.put("SmallPack", "J");

        dept = findViewById(R.id.nextdept);
        nextdeptdrop = new ArrayAdapter<String>(this, R.layout.indus_nextdept, new ArrayList<>(nextdeptmapping.keySet()));
        dept.setAdapter(nextdeptdrop);
        dept.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String indusdept = parent.getItemAtPosition(position).toString();
                nextdeptvalue = nextdeptmapping.get(indusdept);
                if (deptNumericValue != null) {
                    Toasty.success(Outward_DesIndustriaLoading_Form.this, "NetWeighUnitofMeasurement : " + indusdept + " Selected", Toast.LENGTH_SHORT).show();
                } else {
                    Toasty.error(Outward_DesIndustriaLoading_Form.this, "Default NetWeighUnitofMeasurement : " + "NA", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            calculateTotal();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    public void calculateTotal(){

        int total = 0;
        total += getEditTextValue(tenltr);
        total += getEditTextValue(eighteenltr);
        total += getEditTextValue(twentyltr);
        total += getEditTextValue(twentysixltr);
        total += getEditTextValue(fiftyltr);
        total += getEditTextValue(twotenltr);
        total += getEditTextValue(boxbucket);

        etdesiltotqty.setText(String.valueOf(total));
    }

    private int getEditTextValue(EditText editText) {

        try {

            return Integer.parseInt(editText.getText().toString());
        }    catch (NumberFormatException e) {
            return 0; // Return 0 if EditText is empty or non-numeric

        }
    }

    public void btndesindusloadViewclick(View view) {
        Intent intent = new Intent(this, OR_DespatchIndustrial_Completed_Listing.class);
        startActivity(intent);
    }


    private void FetchVehicleDetails(@NonNull String vehicleNo, String vehicleType, char NextProcess, char inOut){
        Call<Model_industrial> call = outwardTruckInterface.fetchindusrtial(vehicleNo,vehicleType,NextProcess,inOut);
        call.enqueue(new Callback<Model_industrial>() {
            @Override
            public void onResponse(Call<Model_industrial> call, Response<Model_industrial> response) {
                if (response.isSuccessful()){
                    Model_industrial data = response.body();
                    if (data.getVehicleNumber() != "" && data.getVehicleNumber() != null){
                        OutwardId = data.getOutwardId();
                        Id = data.getId();
                        etdesilserialnumber.setText(data.getSerialNumber());
                        etdesilserialnumber.setEnabled(false);
                        etdesilvehiclenumber.setText(data.getVehicleNumber());
                        etdesilvehiclenumber.setEnabled(false);
                        vehnumber=data.getVehicleNumber();
                        etdesiltransport.setText(data.getTransportName());
                        etdesiltransport.setEnabled(false);
                        oa.setText(data.getOAnumber());
                        oa.setEnabled(false);
                        party.setText(data.getCustomerName());
                        party.setEnabled(false);


                    }
                }
            }

            @Override
            public void onFailure(Call<Model_industrial> call, Throwable t) {

            }
        });

    }
    private String getCurrentTime() {
        // Get the current time
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(new Date());
    }
    public void indusinsert(){
        String uintime = etdesilintime.getText().toString().trim();
        String uouttime = getCurrentTime();
        int u10 = !tenltr.getText().toString().trim().isEmpty() ? Integer.parseInt(tenltr.getText().toString().trim()) : 0;
        int u18 = !eighteenltr.getText().toString().trim().isEmpty() ? Integer.parseInt(eighteenltr.getText().toString().trim()) : 0;
        int u20 = !twentyltr.getText().toString().trim().isEmpty() ? Integer.parseInt(twentyltr.getText().toString().trim()) : 0;
        int u26 = !twentysixltr.getText().toString().trim().isEmpty() ? Integer.parseInt(twentysixltr.getText().toString().trim()) : 0;
        int u50 = !fiftyltr.getText().toString().trim().isEmpty() ? Integer.parseInt(fiftyltr.getText().toString().trim()) : 0;
        int u210 = !twotenltr.getText().toString().trim().isEmpty() ? Integer.parseInt(twotenltr.getText().toString().trim()) : 0;
        int uboxbucet = !boxbucket.getText().toString().trim().isEmpty() ? Integer.parseInt(boxbucket.getText().toString().trim()) : 0;
        String totalqty = etdesiltotqty.getText().toString().trim();
        String uweight = weight.getText().toString().trim();
        String uindussign = indussign.getText().toString().trim();
        String uindusremark = indusremark.getText().toString().trim();

        String uvehiclenum = etdesilvehiclenumber.getText().toString().trim();
        String userial = etdesilserialnumber.getText().toString().trim();
        String nextu = nextdeptvalue.toString().trim();

        char Inoutins;
        if (nextu.contains("W"))
        {
            Inoutins = 'O';
        }else {
            Inoutins = 'I';
        }

        if (uindussign.isEmpty()||uweight.isEmpty()|| nextu.isEmpty()){
            Toasty.warning(this, "All fields must be filled", Toast.LENGTH_SHORT, true).show();
        }else {
            Model_industrial modelIndustrial = new Model_industrial(OutwardId,uintime,uouttime,u10,u18,u20,
                    u26,u50,u210,uboxbucet,totalqty,uweight,uindussign,uindusremark,EmployeId,uvehiclenum,vehicleType,userial,
                    EmployeId,'W',Inoutins,nextu);
            Call<Boolean> call = outwardTruckInterface.insertindustrial(modelIndustrial);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body() && response.body() == true){
                        Toasty.success(Outward_DesIndustriaLoading_Form.this, "Data Inserted Succesfully !", Toast.LENGTH_SHORT).show();
                        makeNotification(vehnumber,uintime);
                        makeNotificationforsmallpack(vehnumber,uintime);
                        startActivity(new Intent(Outward_DesIndustriaLoading_Form.this, Outward_Truck.class));
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
                    Toasty.error(Outward_DesIndustriaLoading_Form.this, "failed", Toast.LENGTH_SHORT).show();
                }
            });

        }

    }
    public void  indusupdate(){

        String uintime = etdesilintime.getText().toString().trim();
        String uouttime = getCurrentTime();
        int u10 = !tenltr.getText().toString().trim().isEmpty() ? Integer.parseInt(tenltr.getText().toString().trim()) : 0;
        int u18 = !eighteenltr.getText().toString().trim().isEmpty() ? Integer.parseInt(eighteenltr.getText().toString().trim()) : 0;
        int u20 = !twentyltr.getText().toString().trim().isEmpty() ? Integer.parseInt(twentyltr.getText().toString().trim()) : 0;
        int u26 = !twentysixltr.getText().toString().trim().isEmpty() ? Integer.parseInt(twentysixltr.getText().toString().trim()) : 0;
        int u50 = !fiftyltr.getText().toString().trim().isEmpty() ? Integer.parseInt(fiftyltr.getText().toString().trim()) : 0;
        int u210 = !twotenltr.getText().toString().trim().isEmpty() ? Integer.parseInt(twotenltr.getText().toString().trim()) : 0;
        int uboxbucet = !boxbucket.getText().toString().trim().isEmpty() ? Integer.parseInt(boxbucket.getText().toString().trim()) : 0;
        String totalqty = etdesiltotqty.getText().toString().trim();
        String uweight = weight.getText().toString().trim();
        String uindussign = indussign.getText().toString().trim();
        String uindusremark = indusremark.getText().toString().trim();
        String nextu = nextdeptvalue.toString().trim();

        char Inoutins;
        if (nextu.contains("W"))
        {
            Inoutins = 'O';
        }else {
            Inoutins = 'I';
        }


        //if selcet Na to go weighment and if sp select then go smallpack handle the code in this method
        // we mila tho w pe and sp mila tho j milna chaiye


        if (uintime.isEmpty()||uouttime.isEmpty()|| uweight.isEmpty()||uindusremark.isEmpty()||uindussign.isEmpty()||nextu.isEmpty()){
            Toasty.warning(this, "All fields must be filled", Toast.LENGTH_SHORT, true).show();
        }else {

            Indus_update_Model indusUpdateModel = new Indus_update_Model(OutwardId,uintime,uouttime,u10,u18,u20,u26,u50,u210,uboxbucet,totalqty,
                    uweight,uindussign,uindusremark,EmployeId,vehicleType,EmployeId,'W',Inoutins,nextu);
            Call<Boolean> call = outwardTruckInterface.updateindustrial(indusUpdateModel);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body() && response.body() == true){
                        Toasty.success(Outward_DesIndustriaLoading_Form.this, "Data Inserted Succesfully !", Toast.LENGTH_SHORT).show();
                        makeNotification(vehnumber,uintime);
                        startActivity(new Intent(Outward_DesIndustriaLoading_Form.this, Outward_Truck.class));
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
                    Toasty.error(Outward_DesIndustriaLoading_Form.this, "failed", Toast.LENGTH_SHORT).show();
                }
            });

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
                            String specificRole = "Weighment";
                            if (specificRole.equals(resmodel.getDepartment())) {
                                token = resmodel.getToken();

                                FcmNotificationsSender notificationsSender = new FcmNotificationsSender(
                                        token,
                                        "Outward Truck Industrail Pack Process Done..!",
                                        "Vehicle Number:-" + vehicleNumber + " has completed Industrail Pack process at " + outTime,
                                        getApplicationContext(),
                                        Outward_DesIndustriaLoading_Form.this
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
                Toasty.error(Outward_DesIndustriaLoading_Form.this, "failed..!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void makeNotificationforsmallpack(String vehicleNumber, String outTime) {
        Call<List<ResponseModel>> call = userDetails.getUsersListData();
        call.enqueue(new Callback<List<ResponseModel>>() {
            @Override
            public void onResponse(Call<List<ResponseModel>> call, Response<List<ResponseModel>> response) {
                if (response.isSuccessful()){
                    List<ResponseModel> userList = response.body();
                    if (userList != null){
                        for (ResponseModel resmodel : userList){
                            String specificRole = "SmallPack";
                            if (specificRole.equals(resmodel.getDepartment())) {
                                token = resmodel.getToken();

                                FcmNotificationsSender notificationsSender = new FcmNotificationsSender(
                                        token,
                                        "Outward Truck Industrail Pack Process Done..!",
                                        "Vehicle Number:-" + vehicleNumber + " has completed Industrail Pack process at " + outTime,
                                        getApplicationContext(),
                                        Outward_DesIndustriaLoading_Form.this
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
                Toasty.error(Outward_DesIndustriaLoading_Form.this, "failed..!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void pendingindustrial(View view){
        Intent intent = new Intent(this, Grid_Outward.class);
        startActivity(intent);
    }

}