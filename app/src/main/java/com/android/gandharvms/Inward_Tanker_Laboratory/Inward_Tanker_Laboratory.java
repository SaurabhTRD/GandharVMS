package com.android.gandharvms.Inward_Tanker_Laboratory;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.gandharvms.FcmNotificationsSender;
import com.android.gandharvms.Global_Var;
import com.android.gandharvms.InwardCompletedGrid.GridCompleted;
import com.android.gandharvms.Inward_Tanker;
import com.android.gandharvms.Inward_Tanker_Sampling.Inward_Tanker_saampling_View_data;
import com.android.gandharvms.Inward_Tanker_Security.In_Tanker_Security_list;
import com.android.gandharvms.Inward_Tanker_Security.grid;
import com.android.gandharvms.Inward_Tanker_Weighment.InTanWeighResponseModel;
import com.android.gandharvms.Inward_Tanker_Weighment.Inward_Tanker_Weighment;
import com.android.gandharvms.Inward_Tanker_Weighment.Inward_Tanker_Weighment_Viewdata;
import com.android.gandharvms.LoginWithAPI.Laboratory;
import com.android.gandharvms.LoginWithAPI.LoginMethod;
import com.android.gandharvms.LoginWithAPI.ResponseModel;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.Menu;
import com.android.gandharvms.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
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
import com.google.firebase.firestore.auth.User;
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

public class Inward_Tanker_Laboratory extends AppCompatActivity {

    String[] remark = {"Accepted", "Rejected"};
    AutoCompleteTextView regAutoCompleteTextView;
    ArrayAdapter<String> remarkarray;
    EditText etintime, etserialnumber, etpsample, etvehiclenumber, etpapperance, etpodor, etpcolour, etpdensity, etqty, etPrcstest, etpkv, ethundred, etanline, etflash, etpaddtest, etpsamplere, etpremark, etpsignQc, etpdatesignofsign, etMaterial, etsupplier, remarkdisc, etviscosity;
    Button etlabsub;
    Button view,viewsamplereporting;
    TimePickerDialog tpicker;

    FirebaseFirestore dblabroot;

    DatePickerDialog picker, picker1, picker2;

    final Calendar calendar = Calendar.getInstance();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
    Date currentDate = Calendar.getInstance().getTime();
    private String dateTimeString = "";

    private final int MAX_LENGTH = 10;
    private String token;

    //Call Interface Method of Laboratory
    private Laboratory labdetails;
    private LoginMethod userDetails;
    private int inwardid;
    private String vehicleType = Global_Var.getInstance().MenuType;
    private char nextProcess = Global_Var.getInstance().DeptType;
    private char inOut = Global_Var.getInstance().InOutType;
    private String EmployeId = Global_Var.getInstance().EmpId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inward_tanker_laboratory);

        //Send Notification to all
        FirebaseMessaging.getInstance().subscribeToTopic(token);

        labdetails = RetroApiClient.getLabDetails();//Call retrofit api
        userDetails = RetroApiClient.getLoginApi();

        regAutoCompleteTextView = findViewById(R.id.etpremark);
        remarkarray = new ArrayAdapter<String>(this, R.layout.in_tanker_labremarkitem, remark);
        regAutoCompleteTextView.setAdapter(remarkarray);
        regAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String items = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Item: " + items + "Selected", Toast.LENGTH_SHORT).show();
            }
        });

        etintime = (EditText) findViewById(R.id.etintime);
        etpsample = (EditText) findViewById(R.id.etpsample);
        etserialnumber = (EditText) findViewById(R.id.etlabserialnumber);
        etvehiclenumber = (EditText) findViewById(R.id.vehiclenumber);
        etpapperance = (EditText) findViewById(R.id.etpapperance);
        etpodor = (EditText) findViewById(R.id.etpodor);
        etpcolour = (EditText) findViewById(R.id.etpcolour);
        etqty = (EditText) findViewById(R.id.qtycolor);
        etpdensity = (EditText) findViewById(R.id.etpdensity);
        etPrcstest = (EditText) findViewById(R.id.etPrcstest);
        etpkv = (EditText) findViewById(R.id.etpkv);
        ethundred = (EditText) findViewById(R.id.hundered);
        etanline = (EditText) findViewById(R.id.anline);
        etflash = (EditText) findViewById(R.id.flash);
        etpaddtest = (EditText) findViewById(R.id.etpaddtest);
        etpsamplere = (EditText) findViewById(R.id.etpsamplere);
        etpremark = (EditText) findViewById(R.id.etpremark);
        etpsignQc = (EditText) findViewById(R.id.etpsignQc);
        etpdatesignofsign = (EditText) findViewById(R.id.etpdatesignofsign);
        etMaterial = (EditText) findViewById(R.id.et_materialname);

        etsupplier = (EditText) findViewById(R.id.supplier);
        remarkdisc = (EditText) findViewById(R.id.remarkdisc);
        etviscosity = (EditText) findViewById(R.id.etviscosityindex);
        etlabsub = (Button) findViewById(R.id.etlabsub);

        viewsamplereporting=(Button)findViewById(R.id.btn_viewsampleReport);
/*
        viewweighmentreporting=(Button)findViewById(R.id.btn_clicktoViewWEIGHMENTREPORT);
*/
        /*view = findViewById(R.id.viewclick);*/
        /*view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Inward_Tanker_Laboratory.this, Inward_Tanker_Lab_Viewdata.class));
            }
        });*/

        etintime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hours = calendar.get(Calendar.HOUR_OF_DAY);
                int mins = calendar.get(Calendar.MINUTE);
                tpicker = new TimePickerDialog(Inward_Tanker_Laboratory.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        c.set(Calendar.MINUTE, minute);

                        // Set the formatted time to the EditText
                        etintime.setText(hourOfDay + ":" + minute);
                    }
                }, hours, mins, false);
                tpicker.show();
            }
        });


        etpsample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                // Array of month abbreviations
                String[] monthAbbreviations = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

                picker = new DatePickerDialog(Inward_Tanker_Laboratory.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Use the month abbreviation from the array
                        String monthAbbreviation = monthAbbreviations[month];
                        etpsample.setText(dayOfMonth + "/" + monthAbbreviation + "/" + year);
                    }
                }, year, month, day);
                picker.show();
            }
        });

        etpsamplere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                // Array of month abbreviations
                String[] monthAbbreviations = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

                picker = new DatePickerDialog(Inward_Tanker_Laboratory.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Use the month abbreviation from the array
                        String monthAbbreviation = monthAbbreviations[month];
                        etpsamplere.setText(dayOfMonth + "/" + monthAbbreviation + "/" + year);
                    }
                }, year, month, day);
                picker.show();
            }
        });
        etpdatesignofsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                // Array of month abbreviations
                String[] monthAbbreviations = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

                picker = new DatePickerDialog(Inward_Tanker_Laboratory.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Use the month abbreviation from the array
                        String monthAbbreviation = monthAbbreviations[month];
                        etpdatesignofsign.setText(dayOfMonth + "/" + monthAbbreviation + "/" + year);
                    }
                }, year, month, day);
                picker.show();
            }
        });

        dblabroot = FirebaseFirestore.getInstance();

        etvehiclenumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    FetchVehicleDetails(etvehiclenumber.getText().toString().trim(), vehicleType, nextProcess, inOut);
                }
            }
        });

        etlabsub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                labinsertdata();
            }
        });

        if (getIntent().hasExtra("VehicleNumber")) {
            FetchVehicleDetails(getIntent().getStringExtra("VehicleNumber"), Global_Var.getInstance().MenuType, nextProcess, inOut);
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
                        for (ResponseModel resmodel : userList) {
                            String specificRole = "Production";
                            if (specificRole.equals(resmodel.getDepartment())) {
                                token = resmodel.getToken();
                                // Adjust the notification sender based on your new token and data structure
                                FcmNotificationsSender notificationsSender = new FcmNotificationsSender(
                                        token,
                                        "Inward Tanker Laboratory Process Done..!",
                                        "Vehicle Number:-" + vehicleNumber + " has completed Laboratory process at " + outTime,
                                        getApplicationContext(),
                                        Inward_Tanker_Laboratory.this
                                );
                                notificationsSender.SendNotifications();
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
                Toasty.error(Inward_Tanker_Laboratory.this, "failed..!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void btn_clicktoViewSAMPLEREPORT(View view) {
        Global_Var.getInstance().DeptType='M';
        Intent intent = new Intent(this, GridCompleted.class);
        intent.putExtra("vehiclenumber",etvehiclenumber.getText());
        view.getContext().startActivity(intent);
    }

    /*public void weViewclick(View view) {
        Global_Var.getInstance().DeptType='W';
        Intent intent = new Intent(this, GridCompleted.class);
        intent.putExtra("vehiclenumber",etvehiclenumber.getText());
        view.getContext().startActivity(intent);
    }*/

    public void Labviewclick(View view) {
        Intent intent = new Intent(this, grid.class);
        startActivity(intent);
    }

    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(new Date());
    }

    public void labinsertdata() {
        String intime = etintime.getText().toString().trim();
        String serialNumber = etserialnumber.getText().toString().trim();
        String date = etpsample.getText().toString().trim();
        String vehicle = etvehiclenumber.getText().toString().trim();
        String apperance = etpapperance.getText().toString().trim();
        String odor = etpodor.getText().toString().trim();
        String color = etpcolour.getText().toString().trim();
        String qty = etqty.getText().toString().trim();
        String density = etpdensity.getText().toString().trim();
        String rcsTest = etPrcstest.getText().toString().trim();
        String kv = etpkv.getText().toString().trim();
        String hundred = ethundred.getText().toString().trim();
        String anline = etanline.getText().toString().trim();
        String flash = etflash.getText().toString().trim();
        String addTest = etpaddtest.getText().toString().trim();
        String samplereceivingdate = etpsamplere.getText().toString().trim();
        String remark = etpremark.getText().toString().trim();
        String signQc = etpsignQc.getText().toString().trim();
        String dateSignOfSign = etpdatesignofsign.getText().toString().trim();
        String outTime = getCurrentTime();//Insert out Time Directly to the Database
        String material = etMaterial.getText().toString().trim();
        String edsupplier = etsupplier.getText().toString().trim();
        String viscosity = etviscosity.getText().toString().trim();
        String disc = remarkdisc.getText().toString().trim();
        if (intime.isEmpty() || serialNumber.isEmpty() ||remark.isEmpty()|| date.isEmpty() ||
                hundred.isEmpty() || vehicle.isEmpty() || apperance.isEmpty() || odor.isEmpty() ||
                color.isEmpty() || qty.isEmpty() || anline.isEmpty() || flash.isEmpty() || density.isEmpty() ||
                rcsTest.isEmpty() || kv.isEmpty() || disc.isEmpty() || addTest.isEmpty() ||
                samplereceivingdate.isEmpty() || viscosity.isEmpty() || remark.isEmpty() ||
                signQc.isEmpty() || dateSignOfSign.isEmpty() || material.isEmpty() || edsupplier.isEmpty()) {
            Toasty.warning(this, "All fields must be filled", Toast.LENGTH_SHORT, true).show();
        } else {
            InTanLabRequestModel labRequestModel = new InTanLabRequestModel(inwardid, intime, outTime, date,
                    samplereceivingdate, apperance, odor, color, Integer.parseInt(qty), Integer.parseInt(density),
                    rcsTest, Integer.parseInt(kv), Integer.parseInt(hundred), Integer.parseInt(anline),
                    Integer.parseInt(flash), addTest, samplereceivingdate, remark, signQc, dateSignOfSign,
                    disc, Integer.parseInt(viscosity), EmployeId, EmployeId, vehicle, material,
                    serialNumber, 'P', inOut, vehicleType, edsupplier);
            Call<Boolean> call = labdetails.insertLabData(labRequestModel);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body() != null && response.body()==true) {
                        makeNotification(vehicle, outTime);
                        Log.d("Registration", "Response Body: " + response.body());
                        Toasty.success(Inward_Tanker_Laboratory.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Inward_Tanker_Laboratory.this, Inward_Tanker.class));
                        finish();
                    } else {
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
                    Toasty.error(Inward_Tanker_Laboratory.this, "failed..!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void FetchVehicleDetails(@NonNull String vehicleNo, String vehicleType, char NextProcess, char inOut) {
        Call<InTanLabResponseModel> call = labdetails.getLabbyfetchVehData(vehicleNo, vehicleType, NextProcess, inOut);
        call.enqueue(new Callback<InTanLabResponseModel>() {
            @Override
            public void onResponse(Call<InTanLabResponseModel> call, Response<InTanLabResponseModel> response) {
                if (response.isSuccessful()) {
                    InTanLabResponseModel data = response.body();
                    if (data.getVehicleNo() != "") {
                        inwardid = data.getInwardId();
                        etvehiclenumber.setText(data.getVehicleNo());
                        etvehiclenumber.setEnabled(false);
                        etserialnumber.setText(data.getSerialNo());
                        etserialnumber.setEnabled(false);
                        etsupplier.setText(data.getPartyName());
                        etsupplier.setEnabled(false);
                        etMaterial.setText(data.getMaterial());
                        etMaterial.setEnabled(false);
                        etpsample.setText(data.getDate());
                        etpsample.setEnabled(false);
                        etintime.requestFocus();
                        etintime.callOnClick();
                        viewsamplereporting.setVisibility(view.VISIBLE);
                    } else {
                        Toasty.success(Inward_Tanker_Laboratory.this, "Vehicle Is Not Available", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("Retrofit", "Error Response Body: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<InTanLabResponseModel> call, Throwable t) {
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
                Toasty.error(Inward_Tanker_Laboratory.this, "failed..!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onBackPressed() {
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
        finish();
    }

    public void vgrid(View view) {
        Intent intent = new Intent(this, in_tanker_lab_grid.class);
        startActivity(intent);
    }
    public void labviewclick(View view) {
        Intent intent = new Intent(this, GridCompleted.class);
        startActivity(intent);
    }
}