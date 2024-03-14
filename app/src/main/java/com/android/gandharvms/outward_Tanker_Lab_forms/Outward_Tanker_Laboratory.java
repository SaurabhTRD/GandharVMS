package com.android.gandharvms.outward_Tanker_Lab_forms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

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
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.gandharvms.FcmNotificationsSender;
import com.android.gandharvms.Global_Var;
import com.android.gandharvms.Inward_Tanker_Security.Inward_Tanker_Security;
import com.android.gandharvms.LoginWithAPI.LoginMethod;
import com.android.gandharvms.LoginWithAPI.ResponseModel;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.Outward_Tanker_Production_forms.Outward_Tanker_Production;
import com.android.gandharvms.Outward_Tanker_Security.Grid_Outward;
import com.android.gandharvms.Outward_Tanker_Security.Outward_RetroApiclient;
import com.android.gandharvms.Outward_Tanker_Weighment.Outward_Tanker_weighment;
import com.android.gandharvms.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
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

public class Outward_Tanker_Laboratory extends AppCompatActivity {

    String [] items = {"OK","Not OK","Correction"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItems;
    EditText intime,serialnum,vehiclnum,blendingratio,appreance,color,odor,kv40,density25,kv100,viscosity,tbn,anlinepoint,
            breakdownvoltage,ddf,watercontent,interfacialtension,flashpoint,pourpoint,rcstest,remark,approveqc,dt,samplecondition,samplerecivdt,samplereleasedate
            ,correctionrequird,etflushpara;
    TextInputLayout retil;
    CardView btnc;

    Button submit,sendbtn;
    FirebaseFirestore dbroot;
    TimePickerDialog tpicker;
    Calendar calendar = Calendar.getInstance();
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private final String EmployeId = Global_Var.getInstance().EmpId;
    private int OutwardId;
    private Outward_Tanker_Lab outwardTankerLab;
    SimpleDateFormat dtFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
    DatePickerDialog picker;
    private String token;
    private LoginMethod userDetails;
    private String labvehicl;
    public RadioButton etisblendingyes,etisblendingno,etisflushingyes,etisflushingno;
    public CheckBox cisblending,cisflushing;
    public LinearLayout etbelow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outward_tanker_laboratory);
        outwardTankerLab = Outward_RetroApiclient.outwardTankerLab();

        userDetails = RetroApiClient.getLoginApi();

        autoCompleteTextView = findViewById(R.id.etremark);
        adapterItems = new ArrayAdapter<String>(this,R.layout.dropdown_outward_securitytanker,items);
        autoCompleteTextView.setAdapter(adapterItems);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String items = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Item"+items, Toast.LENGTH_SHORT).show();
            }
        });

        intime = findViewById(R.id.etintime);
        serialnum = findViewById(R.id.etserialnumber);
        vehiclnum = findViewById(R.id.etvehicleno);
//        blendingratio = findViewById(R.id.elblendingratio);
        appreance = findViewById(R.id.etapperance);
        color = findViewById(R.id.etcolor);
        odor = findViewById(R.id.etodor);
        density25 = findViewById(R.id.etdensity25);
        kv40 = findViewById(R.id.etviscosity40);
        kv100 = findViewById(R.id.etvisocity100);
        viscosity = findViewById(R.id.etviscosityindex);
        tbn = findViewById(R.id.ettabtan);
        anlinepoint = findViewById(R.id.etanlinepoint);
        breakdownvoltage = findViewById(R.id.etbreakvol);
        ddf = findViewById(R.id.etddf);
        watercontent = findViewById(R.id.etwatercontent);
        interfacialtension = findViewById(R.id.etinterfacial);
        flashpoint = findViewById(R.id.etflashpoint);
        pourpoint = findViewById(R.id.etpourpoint);
        rcstest = findViewById(R.id.etrcstest);
        remark = findViewById(R.id.etremark);
//        approveqc= findViewById(R.id.etapprove);
//        dt = findViewById(R.id.etdatetime);
        sendbtn = findViewById(R.id.saveButton);
        samplecondition = findViewById(R.id.etsamplecondition);
        samplerecivdt = findViewById(R.id.etsampledt);
        samplereleasedate = findViewById(R.id.etsamplereleasedate);
        correctionrequird = findViewById(R.id.etcorrection);

        retil = findViewById(R.id.remarktil);
        btnc = findViewById(R.id.btncdview);
        etbelow = findViewById(R.id.belowflush);




        submit = findViewById(R.id.etssubmit);
        dbroot= FirebaseFirestore.getInstance();

        etisblendingyes = findViewById(R.id.outwaoutrb_blendingYes);
        etisblendingno = findViewById(R.id.outwaourb_blendingNo);
        etisflushingyes = findViewById(R.id.outwaoutrb_flushingYes);
        etisflushingno = findViewById(R.id.outwaourb_flushingNo);

        cisblending = findViewById(R.id.isblendinglab);
        cisflushing = findViewById(R.id.isflushinglab);
        etflushpara = findViewById(R.id.etflushingpara);

        cisflushing.setOnCheckedChangeListener((buttonView, isChecked) -> {
            checkbelowparam();
        });
        cisblending.setOnCheckedChangeListener((buttonView, isChecked) -> {
            checkbelowparam();
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    insert();
            }
        });
        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                sendblendingratio();
                sendflusblend();
            }
        });

        intime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                // Array of month abbreviations
                String[] monthAbbreviations = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
                picker = new DatePickerDialog(Outward_Tanker_Laboratory.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Use the month abbreviation from the array
                        String monthAbbreviation = monthAbbreviations[month];
                        // etdate.setText(dayOfMonth + "/" + monthAbbreviation + "/" + year);
                        intime.setText(dtFormat.format(calendar.getTime()));
                    }
                }, year, month, day);
                picker.show();
            }
        });
        samplerecivdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                // Array of month abbreviations
                String[] monthAbbreviations = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
                picker = new DatePickerDialog(Outward_Tanker_Laboratory.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Use the month abbreviation from the array
                        String monthAbbreviation = monthAbbreviations[month];
                        // etdate.setText(dayOfMonth + "/" + monthAbbreviation + "/" + year);
                        samplerecivdt.setText(dtFormat.format(calendar.getTime()));
                    }
                }, year, month, day);
                picker.show();
            }
        });

        samplereleasedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                // Array of month abbreviations
                String[] monthAbbreviations = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
                picker = new DatePickerDialog(Outward_Tanker_Laboratory.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Use the month abbreviation from the array
                        String monthAbbreviation = monthAbbreviations[month];
                        // etdate.setText(dayOfMonth + "/" + monthAbbreviation + "/" + year);
                        samplereleasedate.setText(dtFormat.format(calendar.getTime()));
                    }
                }, year, month, day);
                picker.show();
            }
        });

        vehiclnum.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    FetchVehicleDetails(vehiclnum.getText().toString().trim(), vehicleType, nextProcess, inOut);
                }
            }
        });



    }
    private String getCurrentTime() {
        // Get the current time
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(new Date());
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
                            String specificRole = "Laboratory";
                            if (specificRole.equals(resmodel.getDepartment())) {
                                token = resmodel.getToken();

                                FcmNotificationsSender notificationsSender = new FcmNotificationsSender(
                                        token,
                                        "Outward Tanker Laboratory  Process form  Done..!",
                                        "Vehicle Number:-" + vehicleNumber + " has completed Security process at " + outTime,
                                        getApplicationContext(),
                                        Outward_Tanker_Laboratory.this
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
                Toasty.error(Outward_Tanker_Laboratory.this, "failed..!", Toast.LENGTH_SHORT).show();
            }
        });
    }


//    private void sendblendingratio() {
//        String userialnumber = serialnum.getText().toString().trim();
//        String uvehiclnum = vehiclnum.getText().toString().trim();
//        String blending = blendingratio.getText().toString().trim();
//
//        if (userialnumber.isEmpty()||uvehiclnum.isEmpty()||blending.isEmpty()){
//            Toast.makeText(this, "all field must be filled", Toast.LENGTH_SHORT).show();
//        }else {
//            Lab_Model__Outward_Tanker labModel__outwardTanker = new Lab_Model__Outward_Tanker(OutwardId,EmployeId,EmployeId,userialnumber,
//                    uvehiclnum,'P',inOut,blending,vehicleType);
//            Call<Boolean> call =outwardTankerLab.addblendingration(labModel__outwardTanker);
//            call.enqueue(new Callback<Boolean>() {
//                @Override
//                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
//                    if (response.isSuccessful() && response.body() && response.body() == true){
//
//                        Toast.makeText(Outward_Tanker_Laboratory.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
//                    }else {
//                        Log.e("Retrofit", "Error Response Body: " + response.code());
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<Boolean> call, Throwable t) {
//
//                    Log.e("Retrofit", "Failure: " + t.getMessage());
//                    // Check if there's a response body in case of an HTTP error
//                    if (call != null && call.isExecuted() && call.isCanceled() && t instanceof HttpException) {
//                        Response<?> response = ((HttpException) t).response();
//                        if (response != null) {
//                            Log.e("Retrofit", "Error Response Code: " + response.code());
//                            try {
//                                Log.e("Retrofit", "Error Response Body: " + response.errorBody().string());
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                    Toasty.error(Outward_Tanker_Laboratory.this, "failed..!", Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
//    }

    public void sendflusblend(){
        String fintime = intime.getText().toString().trim();
        char blendingstatus= etisblendingyes.isChecked() ? 'Y' : 'N';
        char flushingstatus = etisflushingyes.isChecked() ? 'Y' : 'N';
        String outTime = getCurrentTime();

        if (fintime.isEmpty()){
            Toasty.warning(this, "All fields must be filled", Toast.LENGTH_SHORT, true).show();
        }else {

            Blending_Flushing_Model blendingFlushingModel = new Blending_Flushing_Model(OutwardId,fintime,outTime,flushingstatus,
                    blendingstatus,0,EmployeId,'I',inOut,vehicleType);
            Call<Boolean> call = outwardTankerLab.updateflushblend(blendingFlushingModel);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body() && response.body() == true){
                        Toasty.success(Outward_Tanker_Laboratory.this,"Data Inserted Successfully",Toasty.LENGTH_SHORT,true).show();
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
                    Toasty.error(Outward_Tanker_Laboratory.this, "failed..!", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }


    private void FetchVehicleDetails(@NonNull String vehicleNo, String vehicleType, char nextProcess, char inOut) {
        Call<Lab_Model__Outward_Tanker> call = outwardTankerLab.fetchlab(vehicleNo,vehicleType,nextProcess,inOut);
        call.enqueue(new Callback<Lab_Model__Outward_Tanker>() {
            @Override
            public void onResponse(Call<Lab_Model__Outward_Tanker> call, Response<Lab_Model__Outward_Tanker> response) {
                if (response.isSuccessful()){
                    Lab_Model__Outward_Tanker data = response.body();
                    if (data.getVehicleNumber()!= ""){
                        OutwardId = data.getOutwardId();
                        serialnum.setText(data.getSerialNumber());
                        labvehicl= data.getVehicleNumber();
                        vehiclnum.setText(data.getVehicleNumber());

                        serialnum.setEnabled(false);
                        vehiclnum.setEnabled(false);


                       cisblending.setChecked(true);
                       cisflushing.setChecked(true);
                       cisblending.setEnabled(false);
                       cisflushing.setEnabled(false);
                       etflushpara.setText(data.getFlushingNo());
                       etflushpara.setEnabled(false);



//                        if (data.getBlending_Ratio() != null ){
////                        if (data.getBlending_Ratio().isEmpty() ){
////                            intime.setVisibility(View.GONE);
////                            appreance.setVisibility(View.GONE);
////                            color.setVisibility(View.GONE);
////                            odor.setVisibility(View.GONE);
////                            density25.setVisibility(View.GONE);
////                            kv40.setVisibility(View.GONE);
////                            kv100.setVisibility(View.GONE);
////                            viscosity.setVisibility(View.GONE);
//////                            tbn.setVisibility(View.GONE);
////                            anlinepoint.setVisibility(View.GONE);
////                            breakdownvoltage.setVisibility(View.GONE);
////                            ddf.setVisibility(View.GONE);
////                            watercontent.setVisibility(View.GONE);
////                            interfacialtension.setVisibility(View.GONE);
////                            flashpoint.setVisibility(View.GONE);
////                            pourpoint.setVisibility(View.GONE);
////                            rcstest.setVisibility(View.GONE);
////
////                            samplecondition.setVisibility(View.GONE);
////                            samplerecivdt.setVisibility(View.GONE);
////                            samplereleasedate.setVisibility(View.GONE);
////                            correctionrequird.setVisibility(View.GONE);
////                            tbn.setVisibility(View.GONE);
////                          //  remark.setVisibility(View.GONE);
//////                            autoCompleteTextView.setVisibility(View.GONE);
////                            retil.setVisibility(View.GONE);
////                            btnc.setVisibility(View.GONE);
////
//////                            autoCompleteTextView.setVisibility(View.GONE);
//////                            approveqc.setVisibility(View.GONE);
//////                            dt.setVisibility(View.GONE);
//////                            remark.setVisibility(View.GONE);
//////                            sendbtn.setEnabled(true);
////
////                        }else {
////                            blendingratio.setText(data.getBlending_Ratio());
////                            intime.setVisibility(View.VISIBLE);
////                            appreance.setVisibility(View.VISIBLE);
////                            color.setVisibility(View.VISIBLE);
////                            odor.setVisibility(View.VISIBLE);
////                            density25.setVisibility(View.VISIBLE);
////                            kv40.setVisibility(View.VISIBLE);
////                            kv100.setVisibility(View.VISIBLE);
////                            viscosity.setVisibility(View.VISIBLE);
//////                            tbn.setVisibility(View.GONE);
////                            anlinepoint.setVisibility(View.VISIBLE);
////                            breakdownvoltage.setVisibility(View.VISIBLE);
////                            ddf.setVisibility(View.VISIBLE);
////                            watercontent.setVisibility(View.VISIBLE);
////                            interfacialtension.setVisibility(View.VISIBLE);
////                            flashpoint.setVisibility(View.VISIBLE);
////                            pourpoint.setVisibility(View.VISIBLE);
////                            rcstest.setVisibility(View.VISIBLE);
////
////                            samplecondition.setVisibility(View.VISIBLE);
////                            samplerecivdt.setVisibility(View.VISIBLE);
////                            samplereleasedate.setVisibility(View.VISIBLE);
////                            correctionrequird.setVisibility(View.VISIBLE);
////                            tbn.setVisibility(View.VISIBLE);
////                            //  remark.setVisibility(View.GONE);
//////                            autoCompleteTextView.setVisibility(View.GONE);
////                            retil.setVisibility(View.VISIBLE);
////                            btnc.setVisibility(View.VISIBLE);
////                            sendbtn.setVisibility(View.GONE);
////
////
//////                            autoCompleteTextView.setEnabled(true);
//////                            remark.setEnabled(true);
////
////                        }
//
//                        }else {
//                            Toasty.warning(Outward_Tanker_Laboratory.this, "Not Exist Blending Ratio..!", Toast.LENGTH_SHORT).show();
//                        }
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
    public void checkbelowparam(){
        if (cisflushing.isChecked() && cisblending.isChecked())
        {
            if (cisflushing.isChecked()){
                etflushpara.setVisibility(View.VISIBLE);
            }else {
                etflushpara.setVisibility(View.GONE);
            }

            sendbtn.setVisibility(View.VISIBLE);
            etbelow.setVisibility(View.GONE);
        }
        else if(cisflushing.isChecked() || cisblending.isChecked())
        {

            if (cisflushing.isChecked()){
                etflushpara.setVisibility(View.VISIBLE);
            }else {
                etflushpara.setVisibility(View.GONE);
            }
            sendbtn.setVisibility(View.VISIBLE);
            etbelow.setVisibility(View.GONE);
        }
        else if(!cisflushing.isChecked() && !cisblending.isChecked()){
            // Hide the TextInputLayout and Button
            etflushpara.setVisibility(View.GONE);
            sendbtn.setVisibility(View.GONE);
//                clinerar.setVisibility(View.GONE);
            etbelow.setVisibility(View.VISIBLE);
        }
    }

    public void insert(){
//        intime,serialnum,vehiclnum,blendingratio,appreance,color,odor,kv40,density25,kv100,viscosity,tbn,anlinepoint,
//                breakdownvoltage,ddf,watercontent,interfacialtension,flashpoint,pourpoint,rcstest,remark,approveqc,dt;
        String etintime = intime.getText().toString().trim();
        String outTime = getCurrentTime();
        String etremark = remark.getText().toString().trim();
        String etsamplecondition = samplecondition.getText().toString().trim();
        String etsamplereceving = samplerecivdt.getText().toString().trim();
        String etsamplerelease = samplereleasedate.getText().toString().trim();
        String etapperance = appreance.getText().toString().trim();
        String etodor = odor.getText().toString().trim();
        String etcolor = color.getText().toString().trim();
        int etkv40 = Integer.parseInt(kv40.getText().toString().trim());
        int etkv100 = Integer.parseInt(kv100.getText().toString().trim());
        int etviscosity = Integer.parseInt(viscosity.getText().toString().trim());
        String ettbn = tbn.getText().toString().trim();
        int etanlinepoint = Integer.parseInt(anlinepoint.getText().toString().trim());
        int etbreakdown = Integer.parseInt(breakdownvoltage.getText().toString().trim());
        String etddf = ddf.getText().toString().trim();
        String etwatercontent = watercontent.getText().toString().trim();
        String etinetrfacial = interfacialtension.getText().toString().trim();
        String etflashpoint = flashpoint.getText().toString().trim();
        String etpourpoint =  pourpoint.getText().toString().trim();
        String etrcstest = rcstest.getText().toString().trim();
        String etcorrectionrequird = correctionrequird.getText().toString().trim();
        int etdensity25 = Integer.parseInt(density25.getText().toString().trim());

//        String etserialnum = serialnum.getText().toString().trim();
//        String etvehicl = vehiclnum.getText().toString().trim();
//        String etblending = blendingratio.getText().toString().trim();
//        String etapprove = approveqc.getText().toString().trim();
//        String etdt = dt.getText().toString().trim();
        if (etintime.isEmpty() || etapperance.isEmpty()||etcolor.isEmpty()||
                etodor.isEmpty()||ettbn.isEmpty()||etddf.isEmpty()||etwatercontent.isEmpty()||
                etinetrfacial.isEmpty()||etflashpoint.isEmpty()||etpourpoint.isEmpty()|| etrcstest.isEmpty()||etremark.isEmpty()){
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
        }else {
//            Map<String,String> items = new HashMap<>();
//            items.put("In_Time",intime.getText().toString().trim());
//            items.put("Serial_Number",serialnum.getText().toString().trim());
//            items.put("Vehicle_Number",vehiclnum.getText().toString().trim());
//            items.put("Blending_Ratio",blendingratio.getText().toString().trim());
//            items.put("Appearance",approveqc.getText().toString().trim());
//            items.put("Color",color.getText().toString().trim());
//            items.put("Odor",odor.getText().toString().trim());
//            items.put("Density_at_29.5°C",density25.getText().toString().trim());
//            items.put("K.Viscosity_at_40°_c",kv40.getText().toString().trim());
//            items.put("K.Viscosity_at_100°_c",kv100.getText().toString().trim());
//            items.put("Viscosity_Index",viscosity.getText().toString().trim());
//            items.put("TBN_TAN",tbn.getText().toString().trim());
//            items.put("Anilin_Point",anlinepoint.getText().toString().trim());
//            items.put("BreakDown_Voltage",breakdownvoltage.getText().toString().trim());
//            items.put("DDF_at_90°C",ddf.getText().toString().trim());
//            items.put("Water_Content",watercontent.getText().toString().trim());
//            items.put("Interfacial_Tension",interfacialtension.getText().toString().trim());
//            items.put("Flash_Point",flashpoint.getText().toString().trim());
//            items.put("Pour_Point",pourpoint.getText().toString().trim());
//            items.put("RCS_Test",rcstest.getText().toString().trim());
//            items.put("Remark",remark.getText().toString().trim());
//            items.put("Approved_By_Qc_Manager",approveqc.getText().toString().trim());
//            items.put("Date_Time",dt.getText().toString().trim());
//
//            dbroot.collection("Outward_Tanker_Laboratory(In)").add(items)
//                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
//                        @Override
//                        public void onComplete(@NonNull Task<DocumentReference> task) {
//                            Toast.makeText(Outward_Tanker_Laboratory.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
//                        }
//                    });

            Lab_Model_insert_Outward_Tanker labModelInsertOutwardTanker = new Lab_Model_insert_Outward_Tanker(OutwardId,etintime,outTime,
                    etremark,etsamplecondition,etsamplereceving,etsamplerelease,etapperance,etodor,etcolor,etdensity25,
                    etkv40,etkv100,etviscosity,ettbn,etanlinepoint,etbreakdown,
                    etddf,etwatercontent,etinetrfacial,etflashpoint,etpourpoint,etrcstest,etcorrectionrequird,
                    EmployeId,'P',inOut,'L',vehicleType);
            Call<Boolean> call = outwardTankerLab.insertinprocessLaboratory(labModelInsertOutwardTanker);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body() && response.body() == true){
                        makeNotification(labvehicl, outTime);
                        Toast.makeText(Outward_Tanker_Laboratory.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
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
                    Toasty.error(Outward_Tanker_Laboratory.this, "failed..!", Toast.LENGTH_SHORT).show();
                }
            });
        }


    }
    public void outtankerlabinprocpending(View view){
        Intent intent = new Intent(this, Grid_Outward.class);
        startActivity(intent);
    }
}