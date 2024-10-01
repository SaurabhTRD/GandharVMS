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
import com.android.gandharvms.Outward_Tanker_Security.Grid_Outward;
import com.android.gandharvms.Outward_Tanker_Security.Outward_RetroApiclient;
import com.android.gandharvms.Outward_Truck;
import com.android.gandharvms.Outward_Truck_Billing.Outward_Truck_Billing;
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

public class Outward_DesSmallPackLoading_Form extends AppCompatActivity {

    EditText serial,vehicle,transporter,intime,sevenltr,sevenandhalfltr,eighthalfltr,elevenltr,twelltr,threteenltr,fifteenltr,tenltr,eighteenltr,twentyltr,twentysixltr,fiftyltr,twotenltr,boxbucket,totalqty
            ,etweight,smallsign,remark,party,oa;
    Button submit;
    AutoCompleteTextView dept;
    ArrayAdapter<String> nextdeptdrop;
    Map<String, String> nextdeptmapping = new HashMap<>();
    String nextdeptvalue = "W";
    String deptNumericValue = "W";
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    public char inOut = Global_Var.getInstance().InOutType;
    private final String EmployeId = Global_Var.getInstance().EmpId;
    private int OutwardId;
    private Outward_Truck_interface outwardTruckInterface;
    private LoginMethod userDetails;
    private int Id;

    ImageView btnlogout,btnhome;
    TextView username,empid;

    public static String Tanker;
    public static String Truck;
    public String vehnumber="";
    private String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outward_des_small_pack_loading_form);
        outwardTruckInterface = Outward_RetroApiclient.outwardtruckdispatch();
        userDetails = RetroApiClient.getLoginApi();
        serial = findViewById(R.id.etsmallserialnumber);
        vehicle = findViewById(R.id.etdessmallloadvehical);
        transporter = findViewById(R.id.etdessmallloadtranseportname);
        intime = findViewById(R.id.etdessmallloadintime);
        etweight = findViewById(R.id.smallpackweight);
        smallsign = findViewById(R.id.smallpacksign);
        remark = findViewById(R.id.eddesindusloadremark);
        party = findViewById(R.id.etsmallparty);
        oa = findViewById(R.id.etsmalloa);


        sevenltr = findViewById(R.id.etdesindusloadpacking7ltrqty);
        sevenandhalfltr = findViewById(R.id.etdesindusloadpacking7halfltrqty);
        eighthalfltr = findViewById(R.id.etdesindusloadpacking8halfltrqty);
        tenltr = findViewById(R.id.etdesindusloadpacking10ltrqty);
        elevenltr = findViewById(R.id.etdesindusloadpacking11ltrqty);
        twelltr = findViewById(R.id.etdesindusloadpacking12ltrqty);
        threteenltr = findViewById(R.id.etdesindusloadpacking13ltrqty);
        fifteenltr = findViewById(R.id.etdesindusloadpacking15ltrqty);
        eighteenltr = findViewById(R.id.etdesindusloadpacking18ltrqty);
        twentyltr = findViewById(R.id.etdesindusloadpacking20ltrqty);
        twentysixltr = findViewById(R.id.etdesindusloadpacking26ltrqty);
        fiftyltr = findViewById(R.id.etdesindusloadpacking50ltrqty);
        twotenltr = findViewById(R.id.etdesindusloadpacking210ltrqty);
        boxbucket = findViewById(R.id.etdesindusloadpackingboxbucket);

        totalqty = findViewById(R.id.etdesindusloadTotalQuantitysmallpack);
        FirebaseMessaging.getInstance().subscribeToTopic(token);
        sevenltr.addTextChangedListener(textWatcher);
        sevenandhalfltr.addTextChangedListener(textWatcher);
        eighthalfltr.addTextChangedListener(textWatcher);
        elevenltr.addTextChangedListener(textWatcher);
        twelltr.addTextChangedListener(textWatcher);
        threteenltr.addTextChangedListener(textWatcher);
        fifteenltr.addTextChangedListener(textWatcher);
        tenltr.addTextChangedListener(textWatcher);
        eighteenltr.addTextChangedListener(textWatcher);
        twentyltr.addTextChangedListener(textWatcher);
        twentysixltr.addTextChangedListener(textWatcher);
        fiftyltr.addTextChangedListener(textWatcher);
        twotenltr.addTextChangedListener(textWatcher);
        boxbucket.addTextChangedListener(textWatcher);

        submit = findViewById(R.id.etdesindusloadsubmit);

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
                startActivity(new Intent(Outward_DesSmallPackLoading_Form.this, Login.class));
            }
        });
        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Outward_DesSmallPackLoading_Form.this, Menu.class));
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Id == 0){
                    smallinsert();
                }else {
                    smallupdate();
                }


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

        if (getIntent().hasExtra("vehiclenum")) {
            FetchVehicleDetails(getIntent().getStringExtra("vehiclenum"), Global_Var.getInstance().MenuType, nextProcess, inOut);
        }
        vehicle.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasfocus) {
                if (!hasfocus){
                    FetchVehicleDetails(vehicle.getText().toString().trim(), vehicleType, nextProcess, inOut);
                }
            }
        });


        nextdeptmapping = new HashMap<>();
        nextdeptmapping.put("NA", "W");
        nextdeptmapping.put("IndustrialPack", "A");

        dept = findViewById(R.id.nextdeptsmall);
        nextdeptdrop = new ArrayAdapter<String>(this, R.layout.indus_nextdept, new ArrayList<>(nextdeptmapping.keySet()));
        dept.setAdapter(nextdeptdrop);
        dept.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String smalldept = parent.getItemAtPosition(position).toString();
                nextdeptvalue = nextdeptmapping.get(smalldept);
                if (deptNumericValue != null) {
                    Toasty.success(Outward_DesSmallPackLoading_Form.this, "NetWeighUnitofMeasurement : " + smalldept + " Selected", Toast.LENGTH_SHORT).show();
                } else {
                    Toasty.error(Outward_DesSmallPackLoading_Form.this, "Default NetWeighUnitofMeasurement : " + "NA", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            calculateTotal();
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    private void calculateTotal() {
        int total = 0;
        total += getEditTextValue(sevenltr);
        total += getEditTextValue(sevenandhalfltr);
        total += getEditTextValue(eighthalfltr);
        total += getEditTextValue(elevenltr);
        total += getEditTextValue(twelltr);
        total += getEditTextValue(threteenltr);
        total += getEditTextValue(fifteenltr);
        total += getEditTextValue(tenltr);
        total += getEditTextValue(eighteenltr);
        total += getEditTextValue(twentyltr);
        total += getEditTextValue(twentysixltr);
        total += getEditTextValue(fiftyltr);
        total += getEditTextValue(twotenltr);
        total += getEditTextValue(boxbucket);

        totalqty.setText(String.valueOf(total));
    }

    private int getEditTextValue(EditText editText) {
        try {

            return Integer.parseInt(editText.getText().toString());
        }    catch (NumberFormatException e) {
            return 0; // Return 0 if EditText is empty or non-numeric

        }
    }

    public void  FetchVehicleDetails(@NonNull String vehicleNo, String vehicleType, char NextProcess, char inOut){

        Call<Model_industrial> call = outwardTruckInterface.fetchindusrtial(vehicleNo,vehicleType,NextProcess,inOut);
        call.enqueue(new Callback<Model_industrial>() {
            @Override
            public void onResponse(Call<Model_industrial> call, Response<Model_industrial> response) {
                if (response.isSuccessful()){
                    Model_industrial data = response.body();
                    if (data.getVehicleNumber() != "" && data.getVehicleNumber() != null){
                        OutwardId = data.getOutwardId();
                        serial.setText(data.getSerialNumber());
                        serial.setEnabled(false);
                        vehicle.setText(data.getVehicleNumber());
                        vehicle.setEnabled(false);
                        transporter.setText(data.getTransportName());
                        transporter.setEnabled(false);
                        vehnumber=data.getVehicleNumber();
                        Id = data.getId();
                        party.setText(data.getCustomerName());
                        party.setEnabled(false);
                        oa.setText(data.getOAnumber());
                        oa.setEnabled(false);
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

    private void smallinsert() {
        String ivehiclenu = vehicle.getText().toString().trim();
        String iserial = serial.getText().toString().trim();
        String uintime = intime.getText().toString().trim();
        String uouttime = getCurrentTime();
        int u7 = !sevenltr.getText().toString().trim().isEmpty() ? Integer.parseInt(sevenltr.getText().toString().trim()) : 0;
        int u7_5 = !sevenandhalfltr.getText().toString().trim().isEmpty() ? Integer.parseInt(sevenandhalfltr.getText().toString().trim()) : 0;
        int u8_5 = !eighthalfltr.getText().toString().trim().isEmpty() ? Integer.parseInt(eighthalfltr.getText().toString().trim()) : 0;
        int u10 = !tenltr.getText().toString().trim().isEmpty() ? Integer.parseInt(tenltr.getText().toString().trim()) : 0;
        int u11 = !elevenltr.getText().toString().trim().isEmpty() ? Integer.parseInt(elevenltr.getText().toString().trim()) : 0;
        int u12 = !twelltr.getText().toString().trim().isEmpty() ? Integer.parseInt(twelltr.getText().toString().trim()) : 0;
        int u13 = !threteenltr.getText().toString().trim().isEmpty() ? Integer.parseInt(threteenltr.getText().toString().trim()) : 0;
        int u15 = !fifteenltr.getText().toString().trim().isEmpty() ? Integer.parseInt(fifteenltr.getText().toString().trim()) : 0;
        int u18 = !eighteenltr.getText().toString().trim().isEmpty() ? Integer.parseInt(eighteenltr.getText().toString().trim()) : 0;
        int u20 = !twentyltr.getText().toString().trim().isEmpty() ? Integer.parseInt(twentyltr.getText().toString().trim()) : 0;
        int u26 = !twentysixltr.getText().toString().trim().isEmpty() ? Integer.parseInt(twentysixltr.getText().toString().trim()) : 0;
        int u50 = !fiftyltr.getText().toString().trim().isEmpty() ? Integer.parseInt(fiftyltr.getText().toString().trim()) : 0;
        int u210 = !twotenltr.getText().toString().trim().isEmpty() ? Integer.parseInt(twotenltr.getText().toString().trim()) : 0;
        int boxbucet = !boxbucket.getText().toString().trim().isEmpty() ? Integer.parseInt(boxbucket.getText().toString().trim()) : 0;
        String utotalqty = totalqty.getText().toString().trim();
        String uweight = etweight.getText().toString().trim();
        String usign = smallsign.getText().toString().trim();
        String uremark  = remark.getText().toString().trim();
        String nextu = nextdeptvalue.toString().trim();

        char Inoutins;
        if (nextu.contains("W"))
        {
            Inoutins = 'O';
        }else {
            Inoutins = 'I';
        }

        if (uintime.isEmpty()|| utotalqty.isEmpty()||uweight.isEmpty()||usign.isEmpty()||uremark.isEmpty()){
            Toasty.warning(this, "All fields must be filled", Toast.LENGTH_SHORT, true).show();
        }else {
            SmallPcak_Model smallPcakModel = new SmallPcak_Model(OutwardId,uintime,uouttime,u7,u7_5,u8_5,u10,u11,u12,u13,u15,u18,u20,u26,
                    u50,u210,boxbucet,utotalqty,uweight,usign,uremark,EmployeId,ivehiclenu,iserial,EmployeId,'W',Inoutins,vehicleType,nextu);
            Call<Boolean> call = outwardTruckInterface.insertsmallpack(smallPcakModel);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body() && response.body() == true){
                        Toasty.success(Outward_DesSmallPackLoading_Form.this, "Data Inserted Succesfully !", Toast.LENGTH_SHORT).show();
                        makeNotification(vehnumber,uintime);
                        startActivity(new Intent(Outward_DesSmallPackLoading_Form.this, Outward_Truck.class));
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
                    Toasty.error(Outward_DesSmallPackLoading_Form.this, "failed..!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void  smallupdate(){

        String uintime = intime.getText().toString().trim();
        String uouttime = getCurrentTime();
        int u7 = !sevenltr.getText().toString().trim().isEmpty() ? Integer.parseInt(sevenltr.getText().toString().trim()) : 0;
        int u7_5 = !sevenandhalfltr.getText().toString().trim().isEmpty() ? Integer.parseInt(sevenandhalfltr.getText().toString().trim()) : 0;
        int u8_5 = !eighthalfltr.getText().toString().trim().isEmpty() ? Integer.parseInt(eighthalfltr.getText().toString().trim()) : 0;
        int u10 = !tenltr.getText().toString().trim().isEmpty() ? Integer.parseInt(tenltr.getText().toString().trim()) : 0;
        int u11 = !elevenltr.getText().toString().trim().isEmpty() ? Integer.parseInt(elevenltr.getText().toString().trim()) : 0;
        int u12 = !twelltr.getText().toString().trim().isEmpty() ? Integer.parseInt(twelltr.getText().toString().trim()) : 0;
        int u13 = !threteenltr.getText().toString().trim().isEmpty() ? Integer.parseInt(threteenltr.getText().toString().trim()) : 0;
        int u15 = !fifteenltr.getText().toString().trim().isEmpty() ? Integer.parseInt(fifteenltr.getText().toString().trim()) : 0;
        int u18 = !eighteenltr.getText().toString().trim().isEmpty() ? Integer.parseInt(eighteenltr.getText().toString().trim()) : 0;
        int u20 = !twentyltr.getText().toString().trim().isEmpty() ? Integer.parseInt(twentyltr.getText().toString().trim()) : 0;
        int u26 = !twentysixltr.getText().toString().trim().isEmpty() ? Integer.parseInt(twentysixltr.getText().toString().trim()) : 0;
        int u50 = !fiftyltr.getText().toString().trim().isEmpty() ? Integer.parseInt(fiftyltr.getText().toString().trim()) : 0;
        int u210 = !twotenltr.getText().toString().trim().isEmpty() ? Integer.parseInt(twotenltr.getText().toString().trim()) : 0;
        int boxbucet = !boxbucket.getText().toString().trim().isEmpty() ? Integer.parseInt(boxbucket.getText().toString().trim()) : 0;
        String utotalqty = totalqty.getText().toString().trim();
        String uweight = etweight.getText().toString().trim();
        String usign = smallsign.getText().toString().trim();
        String uremark  = remark.getText().toString().trim();
        String nextu = nextdeptvalue.toString().trim();

        char Inoutins;
        if (nextu.contains("W"))
        {
            Inoutins = 'O';
        }else {
            Inoutins = 'I';
        }

        if (uweight.isEmpty()||nextu.isEmpty()){
            Toasty.warning(this, "All fields must be filled", Toast.LENGTH_SHORT, true).show();
        }else {
            Update_SmallPack_Model updateSmallPackModel = new Update_SmallPack_Model(OutwardId,uintime,uouttime,u7,
                    u7_5,u8_5,u10,u11,u12,u13,u15,u18,u20,u26,u50,u210,boxbucet,utotalqty,uweight,usign,uremark,EmployeId,'W',
                    Inoutins,vehicleType,nextu);
            Call<Boolean> call = outwardTruckInterface.updatesmallpack(updateSmallPackModel);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body() && response.body() == true){
                        Toasty.success(Outward_DesSmallPackLoading_Form.this, "Data Inserted Succesfully !", Toast.LENGTH_SHORT).show();
                        makeNotification(vehnumber,uintime);
                        startActivity(new Intent(Outward_DesSmallPackLoading_Form.this, Outward_Truck.class));
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
                    Toasty.error(Outward_DesSmallPackLoading_Form.this, "failed..!", Toast.LENGTH_SHORT).show();
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
                                        "Outward Truck Small Pack Process Done..!",
                                        "Vehicle Number:-" + vehicleNumber + " has completed Small Pack process at " + outTime,
                                        getApplicationContext(),
                                        Outward_DesSmallPackLoading_Form.this
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
                Toasty.error(Outward_DesSmallPackLoading_Form.this, "failed..!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void pendsmallpack(View view){

        Intent intent = new Intent(this, Grid_Outward.class);
        intent.putExtra("Activity","SmallPack");
        startActivity(intent);
    }

    public void btndessmallpackloadViewclick(View view)
    {
        Intent intent = new Intent(this, OR_DespatchSmallPack_Completed_Listing.class);
        startActivity(intent);
    }
}