package com.android.gandharvms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.gandharvms.Inward_Truck_Weighment.Inward_Truck_weighment;
import com.android.gandharvms.Outward_Tanker_Security.Isreportingupdate_Security_model;
import com.android.gandharvms.Outward_Tanker_Security.Outward_RetroApiclient;
import com.android.gandharvms.Outward_Tanker_Security.Outward_Tanker;
import com.android.gandharvms.Outward_Tanker_Security.Outward_Tanker_Security;
import com.android.gandharvms.Outward_Tanker_Security.Request_Model_Outward_Tanker_Security;
import com.android.gandharvms.Outward_Tanker_Security.Response_Outward_Security_Fetching;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.text.SimpleDateFormat;
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

public class Outward_Truck_Security extends AppCompatActivity {

    EditText date,intime,serialnumber,vehiclenumber,lr,transporter,place,mobilenumber,remark, reportingremark;
    RadioButton rbvehpermityes, rbLrCopyyes, rbpucyes, rbinsuranceyes, rbvehfitnessyes, rbdriverlicyes, rbrcbookyes,
                rbvehpermitno, rbLrCopyno, rbpucno, rbinsuranceno, rbvehfitnessno, rbdriverlicno, rbrcbookno;
    Button submit;
    FirebaseFirestore dbroot;
    TimePickerDialog tpicker;
    Calendar calendar = Calendar.getInstance();
    private CheckBox isReportingCheckBox;
    DatePickerDialog picker;
    SimpleDateFormat datef = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");

    private EditText reportingRemarkLayout;
    Button saveButton;

    private String vehicleType = Global_Var.getInstance().MenuType;
    private char nextProcess = Global_Var.getInstance().DeptType;
    private char inOut = Global_Var.getInstance().InOutType;
    private String EmployeId = Global_Var.getInstance().EmpId;
    private int OutwardId;
    private Outward_Tanker outwardTanker;
    Date currentDate = Calendar.getInstance().getTime();
    private int autoGeneratedNumber;
    private SharedPreferences sharedPreferences;
    CheckBox cbox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outward_truck_security);

        outwardTanker = Outward_RetroApiclient.insertoutwardtankersecurity();


        isReportingCheckBox = findViewById(R.id.trsisreporting);
        reportingRemarkLayout = findViewById(R.id.edtrsreportingremark);
        saveButton = findViewById(R.id.saveButton);

        reportingRemarkLayout.setVisibility(View.GONE);
        saveButton.setVisibility(View.GONE);

        sharedPreferences = getSharedPreferences("VehicleManagementPrefs", MODE_PRIVATE);

        isReportingCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Show the TextInputLayout and Button
                reportingRemarkLayout.setVisibility(View.VISIBLE);
                saveButton.setVisibility(View.VISIBLE);
            } else {
                // Hide the TextInputLayout and Button
                reportingRemarkLayout.setVisibility(View.GONE);
                saveButton.setVisibility(View.GONE);
            }
        });

        saveButton.setOnClickListener(v -> {
        });

        date=findViewById(R.id.ettrsdate);
        intime = findViewById(R.id.ettrsintime);
        serialnumber = findViewById(R.id.ettrsserialnumber);
        vehiclenumber = findViewById(R.id.ettrsvehicleno);
//        lr = findViewById(R.id.etlr);
        transporter= findViewById(R.id.ettrstranseporter);
        place = findViewById(R.id.ettrsplace);
        mobilenumber = findViewById(R.id.ettrsmobilenumber);
        remark=findViewById(R.id.ettrsremark);
        cbox = findViewById(R.id.trsisreporting);
        reportingremark = findViewById(R.id.edtrsreportingremark);

        rbvehpermityes=findViewById(R.id.rbtrsvehpermityes);
        rbLrCopyyes=findViewById(R.id.rb_trsLRCopyYes);
        rbpucyes=findViewById(R.id.rb_trsPUCYes);
        rbinsuranceyes=findViewById(R.id.rb_trsInsuranceYes);
        rbvehfitnessyes=findViewById(R.id.rb_trsvehfitcertYes);
        rbdriverlicyes=findViewById(R.id.rb_trsdriverlicYes);
        rbrcbookyes=findViewById(R.id.rb_trsrcbookYes);

        rbvehpermitno=findViewById(R.id.rbtrsvehpermitno);
        rbLrCopyno=findViewById(R.id.rb_trsLRCopyNo);
        rbpucno=findViewById(R.id.rb_trsPUCNo);
        rbinsuranceno=findViewById(R.id.rb_trsInsuranceNo);
        rbvehfitnessno=findViewById(R.id.rb_trsvehfitcertNo);
        rbdriverlicno=findViewById(R.id.rb_trsdriverlicNo);
        rbrcbookno=findViewById(R.id.rb_trsrcbookNo);

        submit = findViewById(R.id.etssubmit);
        dbroot= FirebaseFirestore.getInstance();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isReportingCheckBox = findViewById(R.id.trsisreporting);
                if (isReportingCheckBox.isChecked()) {
                    updateData();
                } else {
                    insert();
                }

            }
        });

        saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertreporting();
            }
        });

        String dateFormatPattern = "ddMMyyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatPattern, Locale.getDefault());
        String formattedDate = dateFormat.format(currentDate);
        // AUTO GENRATED SERIAL NUMBER
        int lastDay = sharedPreferences.getInt("lastDay", -1);
        int currentDay = Integer.parseInt(formattedDate);
        if (currentDay != lastDay) {
            // Day has changed, reset auto-generated number to 1
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("autoGeneratedNumber", 1);
            editor.putInt("lastDay", currentDay);
            editor.apply();
        }

        if (sharedPreferences != null) {
            if (getIntent().hasExtra("VehicleNumber")) {
                FetchVehicleDetail(getIntent().getStringExtra("VehicleNumber"), Global_Var.getInstance().MenuType, 'S', 'I');
                saveButton.setVisibility(View.GONE);
            } else {
                GetMaxSerialNo(formattedDate);
            }

        } else {
            Log.e("MainActivity", "SharedPreferences is null");
        }

        intime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hours = calendar.get(Calendar.HOUR_OF_DAY);
                int mins = calendar.get(Calendar.MINUTE);
                tpicker = new TimePickerDialog(Outward_Truck_Security.this, new TimePickerDialog.OnTimeSetListener() {
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
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                // Array of month abbreviations
                String[] monthAbbreviations = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
                picker = new DatePickerDialog(Outward_Truck_Security.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Use the month abbreviation from the array
                        String monthAbbreviation = monthAbbreviations[month];
                        // etdate.setText(dayOfMonth + "/" + monthAbbreviation + "/" + year);
                        date.setText(datef.format(calendar.getTime()));
                    }
                }, year, month, day);
                picker.show();
            }
        });
        vehiclenumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    FetchVehicleDetail(vehiclenumber.getText().toString().trim(), vehicleType, nextProcess, inOut);
                }
            }
        });
    }

    private void insertreporting() {
        String serial = serialnumber.getText().toString().trim();
        String vehicle = vehiclenumber.getText().toString().trim();
        String cudate = date.getText().toString().trim();
        String edremark = "";
        Boolean isreporting = false;
        if (cbox.isChecked()) {
            edremark = reportingremark.getText().toString().trim();
            isreporting = true;
        }
        if (vehicle.isEmpty() || cudate.isEmpty()) {
            Toasty.warning(this, "All fields must be filled", Toast.LENGTH_SHORT, true).show();
        } else {
            Request_Model_Outward_Tanker_Security requestModelOutwardTankerSecurity = new Request_Model_Outward_Tanker_Security(OutwardId, "", "",
                    '0', "", "", "", "", "", "", "", "", "",
                    "", "", "", "", "", "", "", "", "", EmployeId,
                    isreporting, edremark, 'S', serial, vehicle, "", "", "","",
                    cudate, "", "", "", '0', "", '0', "", 'S', inOut,
                    vehicleType, "", "");
            Call<Boolean> call = outwardTanker.outwardtankerinsert(requestModelOutwardTankerSecurity);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body() != null && response.body() == true) {
                        Toasty.success(Outward_Truck_Security.this, "Data Inserted Succesfully !", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Outward_Truck_Security.this, Outward_Truck.class));
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
                    Toast.makeText(Outward_Truck_Security.this, "failed", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
    private void FetchVehicleDetail(@NonNull String VehicleNo, String vehicltype, char DeptType, char InOutType) {

        Call<List<Response_Outward_Security_Fetching>> call = Outward_RetroApiclient.insertoutwardtankersecurity().outwardsecurityfetching(VehicleNo, vehicltype, DeptType, InOutType);
        call.enqueue(new Callback<List<Response_Outward_Security_Fetching>>() {
            @Override
            public void onResponse(Call<List<Response_Outward_Security_Fetching>> call, Response<List<Response_Outward_Security_Fetching>> response) {
                if (response.isSuccessful()&& response.body()!= null ) {
                    if (response.body().size() > 0){
                        List<Response_Outward_Security_Fetching> data = response.body();
                        Response_Outward_Security_Fetching obj = data.get(0);
                        OutwardId = obj.getOutwardId();
                        serialnumber.setText(obj.getSerialNumber());
                        serialnumber.setEnabled(false);
                        vehiclenumber.setText(obj.getVehicleNumber());
                        vehiclenumber.setEnabled(false);
                        date.setText(obj.getDate());
                        date.setEnabled(false);
                        cbox.setChecked(true);
                        cbox.setEnabled(false);
                        saveButton.setVisibility(View.GONE);
                        reportingremark.setEnabled(false);
                        reportingremark.setVisibility(View.GONE);
                        intime.callOnClick();
                    }
                } else {
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

    public void insert() {
        String etintime = intime.getText().toString().trim();
        String trdate = date.getText().toString().trim();
        String etserialnum = serialnumber.getText().toString().trim();
        String etvehiclnum = vehiclenumber.getText().toString().trim();
        String ettransname = transporter.getText().toString().trim();
        String etplace = place.getText().toString().trim();
        String etmobilenum = mobilenumber.getText().toString().trim();
        String trremark = remark.getText().toString().trim();
        String outTime = getCurrentTime();

        String vehpermitselection = rbvehpermityes.isChecked() ? "Yes" : "No";
        String vehlrcopy = rbLrCopyyes.isChecked() ? "Yes" : "No";
        String pucselection = rbpucyes.isChecked() ? "Yes" : "No";
        String insuranceselection = rbinsuranceyes.isChecked() ? "Yes" : "No";
        String vehfitnesselection = rbvehfitnessyes.isChecked() ? "Yes" : "No";
        String drlicselection = rbdriverlicyes.isChecked() ? "Yes" : "No";
        String rcselection = rbrcbookyes.isChecked() ? "Yes" : "No";

        if (etintime.isEmpty() ||trdate.isEmpty()|| etserialnum.isEmpty() || etvehiclnum.isEmpty() || ettransname.isEmpty() ||
                etplace.isEmpty() || etmobilenum.isEmpty()||trremark.isEmpty()) {
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
        } else {
            Request_Model_Outward_Tanker_Security requestModelOutwardTankerSecurity = new Request_Model_Outward_Tanker_Security(OutwardId, etintime,
                    outTime, 0, etplace, vehpermitselection, pucselection, insuranceselection, vehfitnesselection, drlicselection, rcselection, "", "",
                    "", "", trremark, vehlrcopy, "", "", "", "", outTime, EmployeId, false,
                    "", 'S', etserialnum, etvehiclnum, ettransname, etmobilenum, "", "",trdate
                    , "", "", "", 0, "", 0, "",
                    'L', inOut, vehicleType, etintime, "");


            Call<Boolean> call = outwardTanker.outwardtankerinsert(requestModelOutwardTankerSecurity);

            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body() != null && response.body() == true) {
                        Toasty.success(Outward_Truck_Security.this, "Data Inserted Succesfully !", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Outward_Truck_Security.this, Outward_Truck.class));
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
                    Toasty.error(Outward_Truck_Security.this, "failed", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }
    public void GetMaxSerialNo(String formattedDate) {
        /*String serialNoPreFix = "GA" + formattedDate;*/
        Call<String> call = outwardTanker.getmaxserialnumberoutward(formattedDate);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String maxSerialNumber = response.body();
                    autoGeneratedNumber = Integer.parseInt(maxSerialNumber.substring(10, 13)) + 1;
                    @SuppressLint("DefaultLocale") String autoGeneratedNumberString = String.format("%03d", autoGeneratedNumber);
                    String serialNumber = "GA" + formattedDate + autoGeneratedNumberString;
                    serialnumber.setText(serialNumber);
                    serialnumber.setEnabled(true);
                } else {
                    // Handle the error
                    String serialNumber = "GA" + formattedDate + "001";
                    serialnumber.setText(serialNumber);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                String serialNumber = "GA" + formattedDate + "001";
                serialnumber.setText(serialNumber);
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
                Toasty.error(Outward_Truck_Security.this, "failed", Toast.LENGTH_SHORT).show();
                // Handle the failure

            }
        });
    }
    private String getCurrentTime() {
        // Get the current time
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(new Date());
    }

    private void updateData() {
        String serial = serialnumber.getText().toString().trim();
        String uvehicle = vehiclenumber.getText().toString().trim();
        String udate = date.getText().toString().trim();
        String uintime = intime.getText().toString().trim();
        String utransporter = transporter.getText().toString().trim();
        String uplace = place.getText().toString().trim();
        String umobile = mobilenumber.getText().toString().trim();
        String uremark = remark.getText().toString().trim();
        String outTime = getCurrentTime();

        String vehpermitselection = rbvehpermityes.isChecked() ? "Yes" : "No";
        String vehlrcopy = rbLrCopyyes.isChecked() ? "Yes" : "No";
        String pucselection = rbpucyes.isChecked() ? "Yes" : "No";
        String insuranceselection = rbinsuranceyes.isChecked() ? "Yes" : "No";
        String vehfitnesselection = rbvehfitnessyes.isChecked() ? "Yes" : "No";
        String drlicselection = rbdriverlicyes.isChecked() ? "Yes" : "No";
        String rcselection = rbrcbookyes.isChecked() ? "Yes" : "No";

        if (serial.isEmpty()||uvehicle.isEmpty()||udate.isEmpty()||uintime.isEmpty()||utransporter.isEmpty()||uplace.isEmpty()||
                umobile.isEmpty()||uremark.isEmpty()){
            Toasty.warning(this,"All fields must be filled",Toast.LENGTH_SHORT,true).show();
        }else {
            Isreportingupdate_Security_model isreportingupdateSecurityModel = new Isreportingupdate_Security_model(OutwardId,uintime,outTime,
                    0,uplace,vehpermitselection,pucselection,insuranceselection,vehfitnesselection,drlicselection,rcselection,"",
                    "","","",uremark,vehlrcopy,EmployeId,"","","",
                    "","",'S',serial,uvehicle,utransporter,umobile,"","","","","",0,
                    "",0,"",'L',inOut,vehicleType,uintime,"");

            Call<Boolean> call = outwardTanker.updateoutwardsecurity(isreportingupdateSecurityModel);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body() && response.body() == true){
                        Toasty.success(Outward_Truck_Security.this, "Data Inserted Succesfully !", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Outward_Truck_Security.this, Outward_Truck.class));
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
                    Toasty.error(Outward_Truck_Security.this, "failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}