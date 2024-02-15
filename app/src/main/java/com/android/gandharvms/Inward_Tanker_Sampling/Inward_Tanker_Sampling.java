package com.android.gandharvms.Inward_Tanker_Sampling;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.gandharvms.FcmNotificationsSender;
import com.android.gandharvms.Global_Var;
import com.android.gandharvms.Inward_Tanker;
import com.android.gandharvms.Inward_Tanker_Security.grid;
import com.android.gandharvms.Inward_Tanker_Weighment.InTanWeighResponseModel;
import com.android.gandharvms.Inward_Tanker_Weighment.Inward_Tanker_Weighment;
import com.android.gandharvms.Inward_Truck_Weighment.Inward_Truck_weighment;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.LoginWithAPI.Weighment;
import com.android.gandharvms.Menu;
import com.android.gandharvms.R;
import com.android.gandharvms.RegisterwithAPI.RegRequestModel;
import com.android.gandharvms.RegisterwithAPI.Register;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class Inward_Tanker_Sampling extends AppCompatActivity {
    private Inward_Tanker_SamplingMethod inward_Tanker_SamplingMethod;
    private final int MAX_LENGTH = 10;
    EditText etssignofproduction, etinvoiceno, etsdate, etvehicleno;
    EditText etint,etvehicalnumber,etsupplier,etmaterial,etdriver,etoanumber,etdate;
    Button etssubmit;
    Button view;
    FirebaseFirestore sadbroot;
    Calendar currentTime = Calendar.getInstance();
    int currentHour = currentTime.get(Calendar.HOUR_OF_DAY);
    int currentMinute = currentTime.get(Calendar.MINUTE);

    Date currentDate = Calendar.getInstance().getTime();
    String dateFormatPattern = "dd-MM-yyyy";
    SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatPattern, Locale.getDefault());
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://gandharvms-default-rtdb.firebaseio.com/");
    private String token,etserialnumber;
    private String vehicltype= Global_Var.getInstance().MenuType;
    private char DeptType=Global_Var.getInstance().DeptType;
    private char InOutType=Global_Var.getInstance().InOutType;
    String createdby = Global_Var.getInstance().Name;
    private int inwardid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inward_tanker_sampling);

        inward_Tanker_SamplingMethod= RetroApiClient.getInward_Tanker_Sampling();
        //Send Notification to all
        FirebaseMessaging.getInstance().subscribeToTopic(token);

        etssignofproduction = findViewById(R.id.etreciving);
        etinvoiceno = findViewById(R.id.etsubmitted);
        etsdate = findViewById(R.id.etsdate);
        etvehicleno = findViewById(R.id.etvehicleno);

        view = findViewById(R.id.samplingview);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Inward_Tanker_Sampling.this, Inward_Tanker_saampling_View_data.class));
            }
        });

        // timepicker
        etssignofproduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hours = calendar.get(Calendar.HOUR_OF_DAY);
                int mins = calendar.get(Calendar.MINUTE);
                String formattedTime = String.format(Locale.getDefault(), "%02d:%02d", currentHour, currentMinute);
                etssignofproduction.setText(formattedTime);

                FetchVehicleDetails(etvehicleno.getText().toString().trim(),vehicltype,InOutType);
            }
        });

        etinvoiceno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hours = calendar.get(Calendar.HOUR_OF_DAY);
                int mins = calendar.get(Calendar.MINUTE);
                String formattedTime = String.format(Locale.getDefault(), "%02d:%02d", currentHour, currentMinute);
                etinvoiceno.setText(formattedTime);

            }
        });

        String formattedDate = dateFormat.format(currentDate);
        etsdate.setText(formattedDate);
        etssubmit = findViewById(R.id.etssubmit);
        sadbroot = FirebaseFirestore.getInstance();

        etssubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                sainsertdata();
                sainsertdataAdapter();
            }
        });

        etvehicleno.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    FetchVehicleDetails(etvehicleno.getText().toString().trim(),vehicltype,InOutType);
                }
            }
        });
    }

    public void makeNotification(String vehicleNumber, String outTime) {
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Assume you have a user role to identify the specific role
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                        String specificRole = "Laboratory";
                        // Get the value of the "role" node                    ;
                        if (issue.toString().contains(specificRole)) {
                            //getting the token
                            token = Objects.requireNonNull(issue.child("token").getValue()).toString();
                            FcmNotificationsSender notificationsSender = new FcmNotificationsSender(token,
                                    "Inward Tanker Sampling Process Done..!",
                                    "Vehicle Number:-" + vehicleNumber + " has completed Sampling process at " + outTime,
                                    getApplicationContext(), Inward_Tanker_Sampling.this);
                            notificationsSender.SendNotifications();
                        }
                    }
                } else {
                    // Handle the case when the "role" node doesn't exist
                    Log.d("Role Data", "Role node doesn't exist");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors here
                Log.e("Firebase", "Error fetching role data: " + databaseError.getMessage());
            }
        });
    }


    public void sainsertdataAdapter() {
        String etreciving = etssignofproduction.getText().toString().trim();
        String etsubmitted = etinvoiceno.getText().toString().trim();
        String date = etsdate.getText().toString().trim();
        String vehiclenumber = etvehicleno.getText().toString().trim();

        if (vehiclenumber.isEmpty() || etreciving.isEmpty() || date.isEmpty() || etsubmitted.isEmpty()) {
            Toasty.warning(this, "All fields must be filled", Toast.LENGTH_SHORT,true).show();
        } else {
            Inward_Tanker_SamplingRequestModel inward_Tanker_SamplingRequestModel= new Inward_Tanker_SamplingRequestModel(inwardid,etreciving,etsubmitted,true,createdby,vehiclenumber,etserialnumber,'L',InOutType,vehicltype,DeptType,date);
            inward_Tanker_SamplingMethod= RetroApiClient.getInward_Tanker_Sampling();

            Call<Boolean> call = inward_Tanker_SamplingMethod.postAdd(inward_Tanker_SamplingRequestModel);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if(response.isSuccessful() && response.body() != null)
                    {
                        if(response.body().booleanValue())
                        {
                            Log.d("Registration", "Response Body: " + response.body());
                            Toasty.success(Inward_Tanker_Sampling.this, "User Register succesfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Inward_Tanker_Sampling.this, Inward_Tanker.class);
                            startActivity(intent);
                        }
                        else {
                            // Registration failed
                            Log.e("Registration", "Registration failed. Response: " + response.body());
                            Toasty.error(Inward_Tanker_Sampling.this, "Registration failed..!", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        // Registration failed
                        Log.e("Registration", "Registration failed. Response: " + response.body());
                        Toasty.error(Inward_Tanker_Sampling.this, "Registration failed..!", Toast.LENGTH_SHORT).show();
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
                    Toasty.error(Inward_Tanker_Sampling.this,"Registration failed..!",Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    public void sainsertdata() {
        String etreciving = etssignofproduction.getText().toString().trim();
        String etsubmitted = etinvoiceno.getText().toString().trim();
        String date = etsdate.getText().toString().trim();
        String vehiclenumber = etvehicleno.getText().toString().trim();
        if (vehiclenumber.isEmpty() || etreciving.isEmpty() || date.isEmpty() || etsubmitted.isEmpty()) {
            Toasty.warning(this, "All fields must be filled", Toast.LENGTH_SHORT,true).show();
        } else {
            Map<String, String> saitems = new HashMap<>();
            saitems.put("Sample_Reciving_Time", etssignofproduction.getText().toString().trim());
            saitems.put("Sample_Submitted_Time", etinvoiceno.getText().toString().trim());
            saitems.put("Date", etsdate.getText().toString().trim());
            saitems.put("Vehicle_Number", etvehicleno.getText().toString().trim());
            makeNotification(etvehicleno.getText().toString(), etinvoiceno.getText().toString());
            sadbroot.collection("Inward Tanker Sampling").add(saitems)

                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {

                            etssignofproduction.setText("");
                            etinvoiceno.setText("");
                            etsdate.setText("");
                            etvehicleno.setText("");
                            Toasty.success(Inward_Tanker_Sampling.this, "Data Inserted Successfully", Toast.LENGTH_SHORT,true).show();
                        }
                    });
            Intent intent = new Intent(this, Inward_Tanker.class);
            startActivity(intent);
        }
    }

    public void FetchVehicleDetails(@NonNull String vehicleNo,String vehicleType,char inOut) {
        Call<InTanSamplingResponseModel> call=inward_Tanker_SamplingMethod.GetSamplingByFetchVehicleDetails(vehicleNo,vehicleType,'W',inOut);
        call.enqueue(new Callback<InTanSamplingResponseModel>() {
            @Override
            public void onResponse(Call<InTanSamplingResponseModel> call, Response<InTanSamplingResponseModel> response) {
                if(response.isSuccessful())
                {
                    InTanSamplingResponseModel data=response.body();
                    if(data.getVehicleNo()!="")
                    {
                        inwardid = data.getInwardId();
//                        etserialnumber = "GA25012025006";
                        etserialnumber = data.getSerialNo();
                    }
                }else
                {
                    Log.e("Retrofit", "Error Response Body: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<InTanSamplingResponseModel> call, Throwable t) {
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
                Toasty.error(Inward_Tanker_Sampling.this,"failed..!",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void onBackPressed() {
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
        finish();
    }

    public void samplegrid(View view){
        Intent intent = new Intent(this, grid.class);
        startActivity(intent);
        /*Intent intent = new Intent(this, in_Tanker_sampling_grid.class);
        startActivity(intent);*/
    }
    public void SamplingViewclick(View view){
        /*Intent intent = new Intent(this, grid.class);
        startActivity(intent);*/
        /*Intent intent = new Intent(this, in_Tanker_sampling_grid.class);
        startActivity(intent);*/
    }
}