package com.android.gandharvms.Inward_Tanker_Security;


import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.ColorSpace;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.gandharvms.FcmNotificationsSender;
import com.android.gandharvms.Global_Var;
import com.android.gandharvms.Inward_Tanker;
import com.android.gandharvms.Inward_Tanker_Laboratory.Inward_Tanker_Laboratory;
import com.android.gandharvms.Inward_Tanker_Weighment.InTanWeighResponseModel;
import com.android.gandharvms.Inward_Tanker_Weighment.Inward_Tanker_Weighment;
import com.android.gandharvms.LoginWithAPI.LoginMethod;
import com.android.gandharvms.LoginWithAPI.ResponseModel;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.google.firebase.messaging.FirebaseMessaging;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
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
import retrofit2.Retrofit;

public class Inward_Tanker_Security extends AppCompatActivity implements View.OnClickListener {

    final Calendar calendar = Calendar.getInstance();
    public int MAX_LENGTH = 10;
    public LoginMethod getmaxserialno;
    String[] items = {"Capital Register", "General Register", "Inward Register"};
    String DocId = "";
    //uom
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
    String[] qtyuom = {"Ton", "Litre", "KL", "Kgs", "pcs", "NA"};
    Integer qtyUomNumericValue = 1;
    Integer netweuomvalue = 1;
    char input = 'i';
    int isReportingInt = 0;


//     String vehicleType = String.valueOf(T);
    String isReportingString = "0";  // or "1"
    boolean isReporting = isReportingString.equals("1");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate currentDatee = LocalDate.now();
    // Assign the current date to a variable of type java.sql.Date
    String datetimeString = "2022-01-31 12:34:56";
    String[] netweuom = {"Ton", "Litre", "KL", "Kgs", "pcs", "NA"};
    AutoCompleteTextView autoCompleteTextView, autoCompleteTextView1, autoCompleteTextView2;
    Map<String, Integer> qtyUomMapping = new HashMap<>();
    ArrayAdapter<String> registeritem;
    ArrayAdapter<String> qtyuomdrop;
    ArrayAdapter<String> netweuomdrop;
    EditText etreg, etvehical, etinvoice, etdate, etsupplier, etmaterial, etintime, etnetweight, etqty, etoum, etregister, etqtyoum, etnetoum, etremark, edpooa, etmobilenum, repremark;
    Button btnadd, button1, dbbutton;
    EditText editmaterial, editqty, edituom;
    DatePickerDialog picker;
    List<String> teamList = new ArrayList<>();
    LinearLayout linearLayout;
    AppCompatSpinner spinner;
    TimePickerDialog tpicker;
    Button saveButton;
    CheckBox cbox;
    Date currentDate = Calendar.getInstance().getTime();
    private SharedPreferences sharedPreferences;
    private int autoGeneratedNumber;
    private CheckBox isReportingCheckBox;
    private EditText reportingRemarkLayout;
    private String token;
    private final String dateTimeString = "";
    private API_In_Tanker_Security apiInTankerSecurity;
    private final String EmployeId = Global_Var.getInstance().EmpId;
    private int InwardId;
    private LoginMethod userDetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inward_tanker_security);

        getmaxserialno = RetroApiClient.getLoginApi();
        userDetails = RetroApiClient.getLoginApi();
        apiInTankerSecurity = RetroApiclient_In_Tanker_Security.getinsecurityApi();

        isReportingCheckBox = findViewById(R.id.isreporting);
        reportingRemarkLayout = findViewById(R.id.edtreportingremark);
        saveButton = findViewById(R.id.saveButton);

        reportingRemarkLayout.setVisibility(View.GONE);
        saveButton.setVisibility(View.GONE);


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

        //Send Notification to all
        FirebaseMessaging.getInstance().subscribeToTopic(token);

        //uom and netwe dropdown
        autoCompleteTextView1 = findViewById(R.id.qtyuomtanker);
        qtyUomMapping = new HashMap<>();
        qtyUomMapping.put("NA", 1);
        qtyUomMapping.put("Ton", 2);
        qtyUomMapping.put("Litre", 3);
        qtyUomMapping.put("KL", 4);
        qtyUomMapping.put("Kgs", 5);
        qtyUomMapping.put("pcs", 6);

        qtyuomdrop = new ArrayAdapter<String>(this, R.layout.in_ta_se_qty, new ArrayList<>(qtyUomMapping.keySet()));
        autoCompleteTextView1.setAdapter(qtyuomdrop);
        autoCompleteTextView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String qtyUomDisplay = parent.getItemAtPosition(position).toString();
                // Retrieve the corresponding numerical value from the mapping
                qtyUomNumericValue = qtyUomMapping.get(qtyUomDisplay);
                if (qtyUomNumericValue != null) {
                    // Now, you can use qtyUomNumericValue when inserting into the database

                    Toast.makeText(Inward_Tanker_Security.this, "qtyUomNumericValue : " + qtyUomNumericValue + " Selected", Toast.LENGTH_SHORT).show();
                } else {
                    // Handle the case where the mapping doesn't contain the display value
                    Toast.makeText(Inward_Tanker_Security.this, "Unknown qtyUom : " + qtyUomDisplay, Toast.LENGTH_SHORT).show();
                }
            }
        });
//        autoCompleteTextView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String qtyuom = parent.getItemAtPosition(position).toString();
//                Toast.makeText(Inward_Tanker_Security.this, "qtyuom : " + qtyuom + " Selected", Toast.LENGTH_SHORT).show();
//            }
//
        autoCompleteTextView2 = findViewById(R.id.netweuom);
        netweuomdrop = new ArrayAdapter<String>(this, R.layout.in_ta_se_nw, new ArrayList<>(qtyUomMapping.keySet()));
        autoCompleteTextView2.setAdapter(netweuomdrop);
        autoCompleteTextView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String neweuom = parent.getItemAtPosition(position).toString();
                netweuomvalue = qtyUomMapping.get(neweuom);
                if (qtyUomNumericValue != null) {
                    Toast.makeText(Inward_Tanker_Security.this, "netwe: " + neweuom + " Selected", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(Inward_Tanker_Security.this, "Unknown qtyUom : " + netweuom, Toast.LENGTH_SHORT).show();
                }
            }
        });
//        autoCompleteTextView1 = findViewById(R.id.qtyuomtanker);
//        qtyuomdrop = new ArrayAdapter<String>(this, R.layout.in_ta_se_qty, qtyuom);
//        autoCompleteTextView1.setAdapter(qtyuomdrop);
//        autoCompleteTextView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String qtyuom = parent.getItemAtPosition(position).toString();
//                Toast.makeText(Inward_Tanker_Security.this, "qtyuom: " + qtyuom + " Selected", Toast.LENGTH_SHORT).show();
//            }
//        });

        etreg = findViewById(R.id.etserialnumber);
        etvehical = findViewById(R.id.etvehical);
        etinvoice = findViewById(R.id.etinvoice);
        etdate = findViewById(R.id.etdate);
        etsupplier = findViewById(R.id.etsupplier);
        etmaterial = findViewById(R.id.etmaterial);
        etintime = findViewById(R.id.etintime);
        etnetweight = findViewById(R.id.etnetweight);
        etqty = findViewById(R.id.etqty);
        etqtyoum = findViewById(R.id.qtyuomtanker);
        etnetoum = findViewById(R.id.netweuom);

        etremark = findViewById(R.id.edtremark);
        edpooa = findViewById(R.id.etpooa);
        etmobilenum = findViewById(R.id.etcontactnumber);

        repremark = findViewById(R.id.edtreportingremark);

        cbox = findViewById(R.id.isreporting);


        // for Auto Genrated serial number
        sharedPreferences = getSharedPreferences("VehicleManagementPrefs", MODE_PRIVATE);
        //
        linearLayout = findViewById(R.id.layout_list);
        button1 = findViewById(R.id.button_add);
        button1.setOnClickListener(this);
        dbbutton = findViewById(R.id.dbview);


        teamList.add(0, "Ton");
        teamList.add(1, "Litre");
        teamList.add(2, "KL");
        teamList.add(3, "Kgs");
        teamList.add(4, "Pcs");

//        listdata button
        dbbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Inward_Tanker_Security.this, completedgrid.class));
            }
        });


        etdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                // Array of month abbreviations
                String[] monthAbbreviations = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
                picker = new DatePickerDialog(Inward_Tanker_Security.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Use the month abbreviation from the array
                        String monthAbbreviation = monthAbbreviations[month];
                        // etdate.setText(dayOfMonth + "/" + monthAbbreviation + "/" + year);
                        etdate.setText(dateFormat.format(calendar.getTime()));
                    }
                }, year, month, day);
                picker.show();
            }
        });

        etintime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hours = calendar.get(Calendar.HOUR_OF_DAY);
                int mins = calendar.get(Calendar.MINUTE);
                tpicker = new TimePickerDialog(Inward_Tanker_Security.this, new TimePickerDialog.OnTimeSetListener() {
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


        btnadd = findViewById(R.id.submit);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isReportingCheckBox = findViewById(R.id.isreporting);
                if (isReportingCheckBox.isChecked()) {
                    updateData();
                } else {
                    try {
                        insertdata();
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
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

        etvehical.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {

//                    String VehicleNo = etvehical.getText().toString();
                    String vehicltype = Global_Var.getInstance().MenuType;
                    char DeptType = Global_Var.getInstance().DeptType;
                    char InOutType = Global_Var.getInstance().InOutType;

                    FetchVehicleDetails(etvehical.getText().toString().trim(), vehicltype, DeptType, InOutType);
                }
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
                FetchVehicleDetails(getIntent().getStringExtra("VehicleNumber"), Global_Var.getInstance().MenuType, 'S', 'I');
                saveButton.setVisibility(View.GONE);
                button1.setVisibility(View.GONE);
//                btnadd.setVisibility(View.GONE);
            } else {
                GetMaxSerialNo(formattedDate);
            }

        } else {
            Log.e("MainActivity", "SharedPreferences is null");
        }
    }


    public void onClick(View v) {
        addview();
    }

    private void addview() {
        View materialview = getLayoutInflater().inflate(R.layout.row_add_material, null, false);

        EditText editText = materialview.findViewById(R.id.editmaterial);
        EditText editqty = materialview.findViewById(R.id.editqty);
        AppCompatSpinner spinner = materialview.findViewById(R.id.spinner_team);
        ImageView img = materialview.findViewById(R.id.editcancel);

        linearLayout.addView(materialview);

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, teamList);
        spinner.setAdapter(arrayAdapter);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeView(materialview);
            }
        });
    }

    private void removeView(View view) {

        linearLayout.removeView(view);
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
                                        "Inward Tanker Security Process Done..!",
                                        "Vehicle Number:-" + vehicleNumber + " has completed Security process at " + outTime,
                                        getApplicationContext(),
                                        Inward_Tanker_Security.this
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
                Toasty.error(Inward_Tanker_Security.this, "failed..!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getCurrentTime() {
        // Get the current time
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(new Date());
    }

    public void insertdata() throws ParseException {
        String serialnumber = etreg.getText().toString().trim();
        String vehicalnumber = etvehical.getText().toString().trim();
        String invoicenumber = etinvoice.getText().toString().trim();
        String Date = etdate.getText().toString().trim();
        String partyname = etsupplier.getText().toString().trim();
        String material = etmaterial.getText().toString().trim();
        int qty = Integer.parseInt(etqty.getText().toString().trim());
        int netweight = Integer.parseInt(etnetweight.getText().toString().trim());
        String intime = etintime.getText().toString().trim();
        String outTime = getCurrentTime();//Insert out Time Directly to the Database
        int qtyuom = Integer.parseInt(qtyUomNumericValue.toString().trim());
        String vehicltype = Global_Var.getInstance().MenuType;
        char InOutType = Global_Var.getInstance().InOutType;
        char DeptType = Global_Var.getInstance().DeptType;
        int netweuom = Integer.parseInt(netweuomvalue.toString().trim());
        String remark = etremark.getText().toString().trim();
        String pooa = edpooa.getText().toString().trim();
        String mobnumber = etmobilenum.getText().toString().trim();
        String edremark = repremark.getText().toString().trim();
        if (vehicalnumber.isEmpty() || invoicenumber.isEmpty() || Date.isEmpty() || partyname.isEmpty() ||
                intime.isEmpty() || material.isEmpty() ||edremark.isEmpty()) {
            Toasty.warning(this, "All fields must be filled", Toast.LENGTH_SHORT, true).show();
        } else {

            //Extra material dynamic view
            List<Map<String, String>> materialList = new ArrayList<>();

            for (int i = 0; i < linearLayout.getChildCount(); i++) {
                View childView = linearLayout.getChildAt(i);
                if (childView != null) {
                    EditText materialEditText = childView.findViewById(R.id.editmaterial);
                    EditText qtyEditText = childView.findViewById(R.id.editqty);
                    AppCompatSpinner uomSpinner = childView.findViewById(R.id.spinner_team);

                    String dynamaterial = materialEditText.getText().toString().trim();
                    String dynaqty = qtyEditText.getText().toString().trim();
                    String dynaqtyuom = uomSpinner.getSelectedItem().toString();

                    // Check if both material and quantity fields are not empty
                    if (!dynamaterial.isEmpty() && !dynaqty.isEmpty() && !dynaqtyuom.isEmpty()) {
                        Map<String, String> materialMap = new HashMap<>();
                        materialMap.put("material", dynamaterial);
                        materialMap.put("qty", dynaqty);
                        materialMap.put("qtyuom", dynaqtyuom);
                        // Add material data to the list
                        materialList.add(materialMap);
                    }
                }
            }

            Request_Model_In_Tanker_Security requestModelInTankerSecurity = new Request_Model_In_Tanker_Security(serialnumber, invoicenumber, vehicalnumber, Date, partyname, material, pooa, mobnumber, 'W', 'I', Date,
                    "", vehicltype, intime, outTime, qtyuom, netweuom, netweight, qty, materialList.toString().replace("[]", ""), remark, false, "No", "", "", "", "", "", EmployeId,"",InwardId);

            Call<Boolean> call = apiInTankerSecurity.postData(requestModelInTankerSecurity);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body() != null && response.body()==true) {
                        makeNotification(vehicalnumber, outTime);
                        Toasty.success(Inward_Tanker_Security.this, "Data Inserted Succesfully !", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Inward_Tanker_Security.this, Inward_Tanker.class));
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
                    Toast.makeText(Inward_Tanker_Security.this, "failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void insertreporting() {
        String serialnumber = etreg.getText().toString().trim();
        String vehicalnumber = etvehical.getText().toString().trim();
        String invoicenumber = "";
        String Date = etdate.getText().toString().trim();
        String partyname = "";
        String material = "";
        int qty = 0;
        int netweight = 0;
        String intime = "";
        String outTime = "";//Insert out Time Directly to the Database
        int qtyuom = 1;

        String vehicltype = Global_Var.getInstance().MenuType;
        char InOutType = Global_Var.getInstance().InOutType;
        char DeptType = Global_Var.getInstance().DeptType;
        int netweuom = 1;
        String remark = "";
        String pooa = "";
        String mobnumber = "";
        String edremark = "";
        Boolean isreporting = false;
        if (cbox.isChecked()) {
            edremark = repremark.getText().toString().trim();
            isreporting = true;
        }
        if (vehicalnumber.isEmpty() || Date.isEmpty()) {
            Toasty.warning(this, "All fields must be filled", Toast.LENGTH_SHORT, true).show();
        } else {
            Request_Model_In_Tanker_Security requestModelInTankerSecurity = new Request_Model_In_Tanker_Security(serialnumber, invoicenumber, vehicalnumber, Date, partyname, material, pooa, mobnumber, 'S', InOutType, "",
                    "", vehicltype, intime, outTime, qtyuom, netweuom, netweight, qty, "", remark, isreporting, edremark, "", "", "", "", "", EmployeId,"",InwardId);

            apiInTankerSecurity = RetroApiclient_In_Tanker_Security.getinsecurityApi();
            Call<Boolean> call = apiInTankerSecurity.postData(requestModelInTankerSecurity);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body() != null && response.body()==true) {
                        Toasty.success(Inward_Tanker_Security.this, "Data Inserted Succesfully !", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Inward_Tanker_Security.this, Inward_Tanker.class));
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
                    Toasty.error(Inward_Tanker_Security.this, "failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void FetchVehicleDetails(@NonNull String VehicleNo, String vehicltype, char DeptType, char InOutType) {
        Call<List<Respo_Model_In_Tanker_security>> call = RetroApiClient.getserccrityveh().GetIntankerSecurityByVehicle(VehicleNo, vehicltype, DeptType, InOutType);
        call.enqueue(new Callback<List<Respo_Model_In_Tanker_security>>() {
            @Override
            public void onResponse(Call<List<Respo_Model_In_Tanker_security>> call, Response<List<Respo_Model_In_Tanker_security>> response) {
                if (response.isSuccessful()) {

                    if (response.body().size() > 0) {
                        List<Respo_Model_In_Tanker_security> Data = response.body();
                        Respo_Model_In_Tanker_security obj = Data.get(0);
                        InwardId = obj.getInwardId();
                        /*int intimelength = obj.getInTime().length();
                        etintime.setText(obj.getInTime().substring(12, intimelength));*/
                        etreg.setText(obj.getSerialNo());
                        etreg.setEnabled(false);
                        etvehical.setText(obj.getVehicleNo());
                        etvehical.setEnabled(false);
                        repremark.setText(obj.getReportingRemark());
                        repremark.setEnabled(false);
                        etdate.setText(obj.getDate());
                        etdate.setEnabled(false);
                        etnetweight.setText(String.valueOf(obj.getNetWeight()));
                        etnetweight.setEnabled(false);
                        cbox.setChecked(true);
                        cbox.setEnabled(false);
                        saveButton.setVisibility(View.GONE);
                        repremark.setEnabled(false);
                        etreg.setEnabled(false);
                        etdate.setEnabled(false);
//                            DocId = document.getId();
                        etqty.requestFocus();
                        etqty.callOnClick();
                    }
                } else {
                    Log.e("Retrofit", "Error" + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Respo_Model_In_Tanker_security>> call, Throwable t) {
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

    public void GetMaxSerialNo(String formattedDate) {
        /*String serialNoPreFix = "GA" + formattedDate;*/
        Call<String> call = getmaxserialno.getMaxSerialNumber(formattedDate);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String maxSerialNumber = response.body();
                    autoGeneratedNumber = Integer.parseInt(maxSerialNumber.substring(10, 13)) + 1;
                    @SuppressLint("DefaultLocale") String autoGeneratedNumberString = String.format("%03d", autoGeneratedNumber);
                    String serialNumber = "GA" + formattedDate + autoGeneratedNumberString;
                    etreg.setText(serialNumber);
                    etreg.setEnabled(true);
                } else {
                    // Handle the error
                    String serialNumber = "GA" + formattedDate + "001";
                    etreg.setText(serialNumber);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                String serialNumber = "GA" + formattedDate + "001";
                etreg.setText(serialNumber);
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
                Toasty.error(Inward_Tanker_Security.this, "failed", Toast.LENGTH_SHORT).show();
                // Handle the failure

            }
        });
    }

    public void updateData() {
        String serialnumber = etreg.getText().toString().trim();
        String vehiclenumber = etvehical.getText().toString().trim();
        String Date = etdate.getText().toString().trim();
        String intime = etintime.getText().toString().trim();
        String invoice = etinvoice.getText().toString().trim();
        String drivermobile = etmobilenum.getText().toString().trim();
        String party = etsupplier.getText().toString().trim();
        String material = etmaterial.getText().toString().trim();
        String oapo = edpooa.getText().toString().trim();
        int netweight = Integer.parseInt(etnetweight.getText().toString().trim());
        int netwtuom = Integer.parseInt(netweuomvalue.toString());
        int qty = Integer.parseInt(etqty.getText().toString().trim());
//        int qtyuom = Integer.parseInt( qtyUomNumericValue.toString().trim());
        int qtyuom = Integer.parseInt(qtyUomNumericValue.toString().trim());
        String remark = etremark.getText().toString().trim();
        String outTime = getCurrentTime();
        String vehicltype = Global_Var.getInstance().MenuType;
        char InOutType = Global_Var.getInstance().InOutType;
        char DeptType = Global_Var.getInstance().DeptType;

        if (invoice.isEmpty() || party.isEmpty() || material.isEmpty() || oapo.isEmpty() || outTime.isEmpty()) {
            Toasty.warning(this, "All fields must be filled", Toast.LENGTH_SHORT, true).show();
        } else {


            //Extra material dynamic view
            List<Map<String, String>> materialList = new ArrayList<>();

            for (int i = 0; i < linearLayout.getChildCount(); i++) {
                View childView = linearLayout.getChildAt(i);
                if (childView != null) {
                    EditText materialEditText = childView.findViewById(R.id.editmaterial);
                    EditText qtyEditText = childView.findViewById(R.id.editqty);
                    AppCompatSpinner uomSpinner = childView.findViewById(R.id.spinner_team);

                    String dynamaterial = materialEditText.getText().toString().trim();
                    String dynaqty = qtyEditText.getText().toString().trim();
                    String dynaqtyuom = uomSpinner.getSelectedItem().toString();

                    // Check if both material and quantity fields are not empty
                    if (!dynamaterial.isEmpty() && !dynaqty.isEmpty() && !dynaqtyuom.isEmpty()) {
                        Map<String, String> materialMap = new HashMap<>();
                        materialMap.put("material", dynamaterial);
                        materialMap.put("qty", dynaqty);
                        materialMap.put("qtyuom", dynaqtyuom);
                        // Add material data to the list
                        materialList.add(materialMap);
                    }
                }
            }

            Update_Request_Model_Insequrity requestModelInTankerSecurityupdate = new Update_Request_Model_Insequrity(InwardId, serialnumber, invoice, vehiclenumber, Date, party, material, oapo, drivermobile, 'W', 'I', Date,
                    "", vehicltype, intime, outTime, qtyuom, netwtuom, netweight, qty, materialList.toString().replace("[]", ""), remark, "", "", "", "", "", EmployeId);

            apiInTankerSecurity = RetroApiclient_In_Tanker_Security.getinsecurityApi();
            Call<Boolean> call = apiInTankerSecurity.updatesecuritydata(requestModelInTankerSecurityupdate);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body() != null && response.body()==true) {
                        makeNotification(vehiclenumber, outTime);
                        Toasty.success(Inward_Tanker_Security.this, "Data Inserted Succesfully !", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Inward_Tanker_Security.this, Inward_Tanker.class));
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
                    Toasty.error(Inward_Tanker_Security.this, "failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void olcgridclick(View view) {
        Intent intent = new Intent(this, grid.class);
        startActivity(intent);
    }

    /*public void olcViewclick(View view) {
        Intent intent = new Intent(this, gridadaptercompleted.class);
        startActivity(intent);
    }*/
}

