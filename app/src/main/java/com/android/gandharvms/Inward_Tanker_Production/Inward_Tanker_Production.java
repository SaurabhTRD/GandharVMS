package com.android.gandharvms.Inward_Tanker_Production;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.android.gandharvms.Inward_Tanker_Security.In_Tanker_Security_list;
import com.android.gandharvms.Inward_Tanker_Security.Inward_Tanker_Security;
import com.android.gandharvms.Inward_Tanker_Security.Respo_Model_In_Tanker_security;
import com.android.gandharvms.Inward_Tanker_Security.RetroApiclient_In_Tanker_Security;
import com.android.gandharvms.Inward_Tanker_Weighment.Inward_Tanker_Weighment;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.Menu;
import com.android.gandharvms.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.Timestamp;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class Inward_Tanker_Production extends AppCompatActivity {

    final Calendar calendar = Calendar.getInstance();
    EditText etint, etserno, etreq, ettankno, etconbyop, tanknoun, etconunloadDateTime, etMaterial, etVehicleNumber;
    /*  Button viewdata;*/
    Button prosubmit;
    FirebaseFirestore prodbroot;
    DatePickerDialog picker;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-YYYY, HH:mm:ss");
    Timestamp timestamp = new Timestamp(calendar.getTime());

    TimePickerDialog tpicker;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://gandharvms-default-rtdb.firebaseio.com/");
    private String token;
    private API_In_Tanker_production apiInTankerProduction;
    private int inwardid;
    private String vehicleType= Global_Var.getInstance().MenuType;
    private char nextProcess=Global_Var.getInstance().DeptType;
    private char inOut=Global_Var.getInstance().InOutType;
    private String EmployeName=Global_Var.getInstance().Name;
    private String EmployeId=Global_Var.getInstance().EmpId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inward_tanker_production);

        //Send Notification to all
        FirebaseMessaging.getInstance().subscribeToTopic(token);

        etMaterial = findViewById(R.id.etMaterial);
        etVehicleNumber = findViewById(R.id.etvehicleNumber);
        etserno = findViewById(R.id.etpserialnumber);
        etint = findViewById(R.id.etintime);
        etreq = findViewById(R.id.etreq);
        ettankno = findViewById(R.id.ettankno);
        etconbyop = findViewById(R.id.etconbyop);
        tanknoun = findViewById(R.id.tanknoun);
        etconunloadDateTime = findViewById(R.id.etconunloadDateTime);
        prosubmit = findViewById(R.id.prosubmit);
        apiInTankerProduction = RetroApiclient_In_Tanker_Security.getinproductionApi();
                //datetimepickertesting
        etconunloadDateTime = findViewById(R.id.etconunloadDateTime);

            if (getIntent().hasExtra("VehicleNumber")) {
                FetchVehicleDetails(getIntent().getStringExtra("VehicleNumber"), Global_Var.getInstance().MenuType, 'W', 'I');
            }
        etconunloadDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                // Array of month abbreviations
                String[] monthAbbreviations = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
                picker = new DatePickerDialog(Inward_Tanker_Production.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Use the month abbreviation from the array
                        String monthAbbreviation = monthAbbreviations[month];
                        // etdate.setText(dayOfMonth + "/" + monthAbbreviation + "/" + year);
                        etconunloadDateTime.setText(dateFormat.format(calendar.getTime()));
                    }
                }, year, month, day);
                picker.show();
            }
        });
        etint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hours = calendar.get(Calendar.HOUR_OF_DAY);
                int mins = calendar.get(Calendar.MINUTE);
                tpicker = new TimePickerDialog(Inward_Tanker_Production.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        c.set(Calendar.MINUTE, minute);

                        // Set the formatted time to the EditText
                        etint.setText(hourOfDay + ":" + minute);
                    }
                }, hours, mins, false);
                tpicker.show();
            }
        });

        etVehicleNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String vehicltype= Global_Var.getInstance().MenuType;
                    char DeptType= Global_Var.getInstance().DeptType;
                    char InOutType = Global_Var.getInstance().InOutType;
                    FetchVehicleDetails(etVehicleNumber.getText().toString().trim(),vehicltype,DeptType,InOutType);
                }
            }

        });

        prodbroot = FirebaseFirestore.getInstance();

        prosubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proinsertdata();
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
                        String specificRole = "Weighment";
                        // Get the value of the "role" node                    ;
                        if (issue.toString().contains(specificRole)) {
                            //getting the token
                            token = Objects.requireNonNull(issue.child("token").getValue()).toString();
                            FcmNotificationsSender notificationsSender = new FcmNotificationsSender(token,
                                    "Inward Tanker Production Process Done..!",
                                    "Vehicle Number:-" + vehicleNumber + " has completed Production process at " + outTime+" & Ready For Weighment",
                                    getApplicationContext(), Inward_Tanker_Production.this);
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

    public void btn_clicktoViewLabReport(View view) {
        Intent intent = new Intent(this, Tanker_Production_Lab_Report_Data.class);
        startActivity(intent);
    }

    public void weViewclick(View view) {
        Intent intent = new Intent(this, Inward_Tanker_Production_Viewdata.class);
        startActivity(intent);
    }

    private String getCurrentTime() {
        // Get the current time
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(new Date());
    }

    public void proinsertdata() {
        String intime = etint.getText().toString().trim();
        String etser = etserno.getText().toString().trim();
        String eddate = etconunloadDateTime.getText().toString().trim();
        int reqtounload = Integer.parseInt(etreq.getText().toString().trim());
        int tanknumber = Integer.parseInt(ettankno.getText().toString().trim());
        String confirmunload = etconbyop.getText().toString().trim();
        String tanknumbers = tanknoun.getText().toString().trim();
        String conunload = etconunloadDateTime.getText().toString().trim();
        String outTime = getCurrentTime();//Insert out Time Directly to the Database
        String material = etMaterial.getText().toString().trim();
        String vehicleNumber = etVehicleNumber.getText().toString().trim();

        if (intime.isEmpty() ||   confirmunload.isEmpty() || tanknumbers.isEmpty() || conunload.isEmpty() || material.isEmpty() || vehicleNumber.isEmpty()) {
            Toasty.warning(this, "All Fields must be filled", Toast.LENGTH_SHORT,true).show();
        } else {
//            Map<String, Object> proitems = new HashMap<>();
//            proitems.put("In_Time", etint.getText().toString().trim());
//            proitems.put("Req_to_unload", etreq.getText().toString().trim());
//            proitems.put("Tank_Number_Request", ettankno.getText().toString().trim());
//            proitems.put("confirm_unload", etconbyop.getText().toString().trim());
//            proitems.put("Tank_Number", tanknoun.getText().toString().trim());
//            proitems.put("con_unload_DT", timestamp);
//            proitems.put("outTime", outTime);
//            proitems.put("Material", etMaterial.getText().toString().trim());
//            proitems.put("Vehicle_Number", etVehicleNumber.getText().toString().trim());
//            proitems.put("Serial_Number", etserno.getText().toString().trim());
//
//            makeNotification(etVehicleNumber.getText().toString(), outTime);
//            prodbroot.collection("Inward Tanker Production").add(proitems)
//                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
//                        @Override
//                        public void onComplete(@NonNull Task<DocumentReference> task) {
//                            etint.setText("");
//                            etreq.setText("");
//                            ettankno.setText("");
//                            etconbyop.setText("");
//                            tanknoun.setText("");
//                            etconunloadDateTime.setText("");
//                            etMaterial.setText("");
//                            etVehicleNumber.setText("");
//                            etserno.setText("");
//                            etint.requestFocus();
//                            etint.callOnClick();
//                            Toasty.success(Inward_Tanker_Production.this, "Data Added Successfully", Toast.LENGTH_SHORT,true).show();
//                        }
//                    });
//            Intent intent = new Intent(this, Inward_Tanker.class);
//            startActivity(intent);

            Request_In_Tanker_Production requestInTankerProduction = new Request_In_Tanker_Production(inwardid,intime,outTime,reqtounload,confirmunload,tanknumber,tanknumbers,
                    EmployeId,vehicleNumber,etser,'W',inOut,vehicleType,eddate,material);

            Call<Boolean> call = apiInTankerProduction.insertproductionData(requestInTankerProduction);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body()!=null){
                        Log.d("Production", "Response Body: " + response.body());
                        Toasty.success(Inward_Tanker_Production.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Inward_Tanker_Production.this, Inward_Tanker.class));
                        finish();

                    }
                    else {
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
                    Toasty.error(Inward_Tanker_Production.this,"failed..!",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void onBackPressed() {
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
        finish();
    }

    public void FetchVehicleDetails(@NonNull String VehicleNo,String vehicltype,char DeptType, char InOutType) {
//        CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Inward Tanker Security");
//        String searchText = VehicleNo.trim();
//        Query query = collectionReference.whereEqualTo("vehicalnumber", searchText)
//                .whereNotEqualTo("intime", "");
//        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    int totalCount = task.getResult().size();
//                    if (totalCount == 0) {
//                        etMaterial.setText("");
//                        etVehicleNumber.requestFocus();
//                        etserno.setText("");
//                        etconunloadDateTime.setText("");
//                        Toasty.warning(Inward_Tanker_Production.this, "Vehicle Number not Available for Weighment", Toast.LENGTH_SHORT,true).show();
//                    } else {
//                        for (QueryDocumentSnapshot document : task.getResult()) {
//                            In_Tanker_Security_list obj = document.toObject(In_Tanker_Security_list.class);
//                            // Check if the object already exists to avoid duplicates
//                            if (totalCount > 0) {
//                                etserno.setText(obj.getSerialNumber());
//                                etVehicleNumber.setText(obj.getVehicalnumber());
//                                etMaterial.setText(obj.getMaterial());
//                                etconunloadDateTime.setText(dateFormat.format(obj.getDate().toDate()));
//                                etint.requestFocus();
//                                etint.callOnClick();
//                            }
//                        }
//                    }
//                } else {
//                    Log.w("FirestoreData", "Error getting documents.", task.getException());
//                }
//            }
//        });

        Call<Respo_Model_In_Tanker_Production>  call = apiInTankerProduction.GetinTankerprodcutionByvehicle(VehicleNo,vehicltype,DeptType,InOutType);
        call.enqueue(new Callback<Respo_Model_In_Tanker_Production>() {
            @Override
            public void onResponse(Call<Respo_Model_In_Tanker_Production> call, Response<Respo_Model_In_Tanker_Production> response) {
                if (response.isSuccessful()){
                    Respo_Model_In_Tanker_Production data = response.body();
                    if (data.getVehicleNo()!= ""){
                        inwardid = data.getInwardId();
                        etserno.setText(data.getSerialNo());
                        etVehicleNumber.setText(data.getVehicleNo());
                        etMaterial.setText(data.getMaterial());
//                        etconunloadDateTime.setText(dateFormat.format(data.getDate()));
                        etconunloadDateTime.setText(data.getDate());
                        etint.requestFocus();
                        etint.callOnClick();


                    }
                }
            }

            @Override
            public void onFailure(Call<Respo_Model_In_Tanker_Production> call, Throwable t) {

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
                Toasty.error(Inward_Tanker_Production.this,"failed..!",Toast.LENGTH_SHORT).show();
            }
        });

//        call.enqueue(new Callback<Respo_Model_In_Tanker_Production>() {
//            @Override
//            public void onResponse(Call<Respo_Model_In_Tanker_Production> call, Response<Respo_Model_In_Tanker_security> response) {
//                if (response.isSuccessful()){
//                    Respo_Model_In_Tanker_Production data = response.body();
//
//                    if (response.body().size() > 0){
////                        List<Object> Data = response.body();
////                        Respo_Model_In_Tanker_Production obj = (Respo_Model_In_Tanker_Production) Data.get(0);
////                        etMaterial.setText(obj.getMaterial());
////                        etserno.setText(obj.getSerialNo());
//
//
//                    }
//                }else {
//                    Log.e("Retrofit","Error"+ response.code());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Respo_Model_In_Tanker_Production> call, Throwable t) {
//
//                Log.e("Retrofit", "Failure: " + t.getMessage());
//// Check if there's a response body in case of an HTTP error
//                if (call != null && call.isExecuted() && call.isCanceled() && t instanceof HttpException) {
//                    Response<?> response = ((HttpException) t).response();
//                    if (response != null) {
//                        Log.e("Retrofit", "Error Response Code: " + response.code());
//                        try {
//                            Log.e("Retrofit", "Error Response Body: " + response.errorBody().string());
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }
//        });
    }
    public void statusgrid(View view){
        Intent intent = new Intent(this, in_tanker_produ_grid.class);
        startActivity(intent);
    }
}