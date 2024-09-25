package com.android.gandharvms.outward_Tanker_Lab_forms;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.gandharvms.FcmNotificationsSender;
import com.android.gandharvms.Global_Var;
import com.android.gandharvms.Inward_Truck_store.Inward_Truck_Store;
import com.android.gandharvms.LoginWithAPI.LoginMethod;
import com.android.gandharvms.LoginWithAPI.ResponseModel;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.Outward_Tanker;
import com.android.gandharvms.Outward_Tanker_Production_forms.New_Outward_Tanker_Production;
import com.android.gandharvms.Outward_Tanker_Security.Grid_Outward;
import com.android.gandharvms.Outward_Tanker_Security.Outward_RetroApiclient;
import com.android.gandharvms.R;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;
import java.math.BigDecimal;
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

public class New_Outward_tanker_Lab extends AppCompatActivity {

    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final String EmployeId = Global_Var.getInstance().EmpId;
    public char inOut = Global_Var.getInstance().InOutType;
    private Outward_Tanker_Lab outwardTankerLab;
    private String token;
    private LoginMethod userDetails;
    private int oploutwardid = 0;
    private int OutwardId;
    EditText newlseralnum,newlvehiclenum,newloanum,newlprodcut,newlcustomername,newldestination,newlquantity,newltransporter,
    newlintime,newlviscosity,newldentinity,newlbatchnum,newlqcofficer,newlremarks,billremark,proremark;
    Button btnsubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_outward_tanker_lab);

        outwardTankerLab = Outward_RetroApiclient.outwardTankerLab();
        userDetails = RetroApiClient.getLoginApi();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        FirebaseMessaging.getInstance().subscribeToTopic(token);

        newlseralnum = findViewById(R.id.etnewlserialnumber);
        newlvehiclenum = findViewById(R.id.etnewlvehicleno);
        newloanum = findViewById(R.id.etnewloanumer);
        newlprodcut = findViewById(R.id.etnewlproductname);
        newlcustomername = findViewById(R.id.etnewlcustname);
        newldestination = findViewById(R.id.etnewllocation);
        newlquantity = findViewById(R.id.etnewlhowmuch);
        newltransporter = findViewById(R.id.etnewltransportername);
        newlintime = findViewById(R.id.etinewlntime);
        newlviscosity = findViewById(R.id.elnewlviscosity);
        newldentinity = findViewById(R.id.elnewldensity);
        newlbatchnum = findViewById(R.id.elnewlbatchnumber);
        newlqcofficer = findViewById(R.id.elnewlqcofficer);
        newlremarks = findViewById(R.id.elnewblremark);
        billremark = findViewById(R.id.etbillingremark);
        proremark = findViewById(R.id.etproductionremark);


        btnsubmit = findViewById(R.id.etouttankerlabsubmit);
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert();
            }
        });
        newlintime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                String time =  format.format(calendar.getTime());
                newlintime.setText(time);
            }
        });
        if (getIntent().hasExtra("vehiclenum")) {
            FetchVehicleDetails(getIntent().getStringExtra("vehiclenum"), Global_Var.getInstance().MenuType, nextProcess, inOut);
        }
        newlvehiclenum.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    FetchVehicleDetails(newlvehiclenum.getText().toString().trim(), vehicleType, nextProcess, inOut);
                }
            }
        });
    }

    private void FetchVehicleDetails(@NonNull String vehicleNo, String vehicleType, char nextProcess, char inOut) {
        Call<Lab_Model__Outward_Tanker> call = outwardTankerLab.fetchlab(vehicleNo, vehicleType, nextProcess, inOut);
        call.enqueue(new Callback<Lab_Model__Outward_Tanker>() {
            @Override
            public void onResponse(Call<Lab_Model__Outward_Tanker> call, Response<Lab_Model__Outward_Tanker> response) {
                if (response.isSuccessful()){
                    Lab_Model__Outward_Tanker data = response.body();
                    if (data.getVehicleNumber() != null && data.getVehicleNumber()!= ""){
                        oploutwardid = data.getOplOutwardId();
                        OutwardId = data.getOutwardId();
                        newlseralnum.setText(data.getSerialNumber());
                        newlseralnum.setEnabled(false);
                        newlvehiclenum.setText(data.getVehicleNumber());
                        newlvehiclenum.setEnabled(false);
                        newloanum.setText(data.getOAnumber());
                        newloanum.setEnabled(false);
                        newlprodcut.setText(data.getProductName());
                        newlprodcut.setEnabled(false);
                        newlcustomername.setText(data.getCustomerName());
                        newlcustomername.setEnabled(false);
                        newldestination.setText(data.getLocation());
                        newldestination.setEnabled(false);
                        newlquantity.setText(String.valueOf(data.getHowMuchQuantityFilled()));
                        newlquantity.setEnabled(false);
                        newltransporter.setText(data.getCustomerName());
                        newltransporter.setEnabled(false);
                        billremark.setText(data.getTankerBillingRemark());
                        billremark.setEnabled(false);
                        proremark.setText(data.getTankerProRemark());
                        proremark.setEnabled(false);

                    }else {
                        Toasty.error(New_Outward_tanker_Lab.this, "This Vehicle Number Is Not Available..!", Toast.LENGTH_SHORT).show();
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
    public void insert()
    {
        String outTime = getCurrentTime();
        String inTime = newlintime.getText().toString();
        String iserialnum = newlseralnum.getText().toString();
        String ivehicle = newlvehiclenum.getText().toString();
        String iviscosity = newlviscosity.getText().toString();
        String idensity = newldentinity.getText().toString();
        BigDecimal objdensity = new BigDecimal(idensity);
        String ibatchnum = newlbatchnum.getText().toString();
        String iqcofficer = newlqcofficer.getText().toString();
        String iremarks = newlremarks.getText().toString();

        if (iviscosity.isEmpty() || idensity.isEmpty()|| ibatchnum.isEmpty()){
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
        }else {
            New_Lab_Model_OutwardTanker newLabModelOutwardTanker = new New_Lab_Model_OutwardTanker(OutwardId,inTime,outTime,iviscosity,objdensity,
                    ibatchnum,iqcofficer,iremarks,EmployeId,"L",iserialnum,ivehicle,'W','O',vehicleType,EmployeId);
            Call<Boolean> call = outwardTankerLab.newOutwardTankerLaboratory(newLabModelOutwardTanker);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body() != null && response.body() == true){
                        Toasty.success(New_Outward_tanker_Lab.this, "Data Inserted Succesfully...!!", Toast.LENGTH_SHORT, true).show();
                        makeNotification(ivehicle, outTime);
                        startActivity(new Intent(New_Outward_tanker_Lab.this, Outward_Tanker.class));
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
                    Toasty.error(New_Outward_tanker_Lab.this, "failed..!", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    public void makeNotification(String vehicleNumber, String outTime) {
        Call<List<ResponseModel>> call = userDetails.getUsersListData();
        call.enqueue(new Callback<List<ResponseModel>>() {
            @Override
            public void onResponse(Call<List<ResponseModel>> call, Response<List<ResponseModel>> response) {
                if (response.isSuccessful()) {
                    List<ResponseModel> userList = response.body();
                    if (userList != null) {
                        for (ResponseModel responseModel : userList) {
                            String specificrole = "Laboratory";
                            if (specificrole.equals(responseModel.getDepartment())) {
                                token = responseModel.getToken();
                                FcmNotificationsSender notificationsSender = new FcmNotificationsSender(
                                        token,
                                        "Outward Tanker Laboratory Process Done..!",
                                        "Vehicle Number:-" + vehicleNumber + " has completed Store process at " + outTime,
                                        getApplicationContext(),
                                        New_Outward_tanker_Lab.this
                                );
                                notificationsSender.triggerSendNotification();
                            }
                        }
                    }
                } else {
                    // Handle unsuccessful API response
                    Log.d("API", "Unsuccessful API response");
                }
            }

            @Override
            public void onFailure(Call<List<ResponseModel>> call, Throwable t) {

            }
        });
    }
    public void inprocesprocompletedclick(View view) {
        Intent intent = new Intent(this, OT_Completd_bilkload_laboratory.class);
        startActivity(intent);
    }
    private String getCurrentTime() {
        // Get the current time
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(new Date());
    }
    public void new_uttankerlabinprocpending(View view) {
        Intent intent = new Intent(this, Grid_Outward.class);
        startActivity(intent);
    }
}