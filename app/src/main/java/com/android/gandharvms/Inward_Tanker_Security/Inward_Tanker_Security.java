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
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.gandharvms.FcmNotificationsSender;
import com.android.gandharvms.Global_Var;
import com.android.gandharvms.InwardCompletedGrid.CommonResponseModelForAllDepartment;
import com.android.gandharvms.InwardCompletedGrid.GridCompleted;
import com.android.gandharvms.Inward_Tanker;
import com.android.gandharvms.Inward_Tanker_Laboratory.Inward_Tanker_Laboratory;
import com.android.gandharvms.Inward_Tanker_Weighment.InTanWeighResponseModel;
import com.android.gandharvms.Inward_Tanker_Weighment.Inward_Tanker_Weighment;
import com.android.gandharvms.LoginWithAPI.Login;
import com.android.gandharvms.LoginWithAPI.LoginMethod;
import com.android.gandharvms.LoginWithAPI.ResponseModel;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.Menu;
import com.android.gandharvms.Outward_Tanker_Billing.Outward_Tanker_Billing;
import com.android.gandharvms.R;
import com.android.gandharvms.submenu.submenu_Inward_Tanker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    final int maxLength = 8;
    private final String dateTimeString = "";
    private final String EmployeId = Global_Var.getInstance().EmpId;
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
    AutoCompleteTextView autoCompleteTextView, autoCompleteTextView2;
    Map<String, Integer> qtyUomMapping = new HashMap<>();
    ArrayAdapter<String> registeritem;
    ArrayAdapter<String> qtyuomdrop;
    ArrayAdapter<String> netweuomdrop;
    EditText etreg, etvehical, etinvoice, etdate, etmaterial, etintime, etnetweight, etoum, etregister, etnetoum, etremark, repremark,etsupplier;
    Button btnadd, button1, dbbutton, updbtnclick;
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
    private API_In_Tanker_Security apiInTankerSecurity;
    private int InwardId;
    private LoginMethod userDetails;
    private int insertnetweight;
    private int insertnetweightUom;

    private String vehicletype=Global_Var.getInstance().MenuType;

    ImageView btnlogout,btnhome;
    TextView username,empid;

    public static String Tanker;
    public static String Truck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inward_tanker_security);

        getmaxserialno = RetroApiClient.getLoginApi();
        userDetails = RetroApiClient.getLoginApi();
        apiInTankerSecurity = RetroApiclient_In_Tanker_Security.getinsecurityApi();

//        isReportingCheckBox = findViewById(R.id.isreporting);
//        reportingRemarkLayout = findViewById(R.id.edtreportingremark);
//        saveButton = findViewById(R.id.saveButton);
//
//        reportingRemarkLayout.setVisibility(View.GONE);
//        saveButton.setVisibility(View.GONE);

        btnlogout=findViewById(R.id.btn_logoutButton);
        btnhome = findViewById(R.id.btn_homeButton);
        username=findViewById(R.id.tv_username);
        empid=findViewById(R.id.tv_employeeId);

        String userName=Global_Var.getInstance().Name;
        String empId=Global_Var.getInstance().EmpId;

        username.setText(userName);
        empid.setText(empId);
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Inward_Tanker_Security.this, Login.class));
            }
        });
        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Inward_Tanker_Security.this, Menu.class));
            }
        });

//
//        isReportingCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            if (isChecked) {
//                // Show the TextInputLayout and Button
//                reportingRemarkLayout.setVisibility(View.VISIBLE);
//                saveButton.setVisibility(View.VISIBLE);
//            } else {
//                // Hide the TextInputLayout and Button
//                reportingRemarkLayout.setVisibility(View.GONE);
//                saveButton.setVisibility(View.GONE);
//            }
//        });
//
//        saveButton.setOnClickListener(v -> {
//        });

        //Send Notification to all
        FirebaseMessaging.getInstance().subscribeToTopic(token);

        //uom and netwe dropdown
        //autoCompleteTextView1 = findViewById(R.id.qtyuomtanker);
        qtyUomMapping = new HashMap<>();
        qtyUomMapping.put("NA", 1);
        qtyUomMapping.put("Ton", 2);
        qtyUomMapping.put("Litre", 3);
        qtyUomMapping.put("KL", 4);
        qtyUomMapping.put("Kgs", 5);
        qtyUomMapping.put("pcs", 6);
        qtyUomMapping.put("M3", 7);
        qtyUomMapping.put("Meter",9);
        qtyUomMapping.put("Feet",10);

        /*qtyuomdrop = new ArrayAdapter<String>(this, R.layout.in_ta_se_qty, new ArrayList<>(qtyUomMapping.keySet()));
        autoCompleteTextView1.setAdapter(qtyuomdrop);
        autoCompleteTextView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String qtyUomDisplay = parent.getItemAtPosition(position).toString();
                // Retrieve the corresponding numerical value from the mapping
                qtyUomNumericValue = qtyUomMapping.get(qtyUomDisplay);
                if (qtyUomNumericValue != null) {
                    // Now, you can use qtyUomNumericValue when inserting into the database
                    Toasty.success(Inward_Tanker_Security.this, "QTYUnitofMeasurement : " + qtyUomDisplay + " Selected", Toast.LENGTH_SHORT).show();
                } else {
                    // Handle the case where the mapping doesn't contain the display value
                    Toasty.error(Inward_Tanker_Security.this, "Default QTYUnitofMeasurement : " + "NA" + " Selected", Toast.LENGTH_SHORT).show();
                }
            }
        });*/

        autoCompleteTextView2 = findViewById(R.id.netweuom);
        netweuomdrop = new ArrayAdapter<String>(this, R.layout.in_ta_se_nw, new ArrayList<>(qtyUomMapping.keySet()));
        autoCompleteTextView2.setAdapter(netweuomdrop);
        autoCompleteTextView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String neweuom = parent.getItemAtPosition(position).toString();
                netweuomvalue = qtyUomMapping.get(neweuom);
                if (qtyUomNumericValue != null) {
                    Toasty.success(Inward_Tanker_Security.this, "NetWeighUnitofMeasurement : " + neweuom + " Selected", Toast.LENGTH_SHORT).show();
                } else {
                    Toasty.error(Inward_Tanker_Security.this, "Default NetWeighUnitofMeasurement : " + "NA", Toast.LENGTH_SHORT).show();
                }
            }
        });

        etreg = findViewById(R.id.etserialnumber);
        etvehical = findViewById(R.id.etvehical);
        etinvoice = findViewById(R.id.etinvoice);
        etdate = findViewById(R.id.etdate);
        etsupplier = findViewById(R.id.etsupplier);
        etmaterial = findViewById(R.id.etmaterial);
        etintime = findViewById(R.id.etintime);
        etnetweight = findViewById(R.id.etnetweight);
        //etqty = findViewById(R.id.etqty);
        //etqtyoum = findViewById(R.id.qtyuomtanker);
        etnetoum = findViewById(R.id.netweuom);

        updbtnclick = findViewById(R.id.itinsecupdateclick);
        etremark = findViewById(R.id.edtremark);
        //edpooa = findViewById(R.id.etpooa);
        //etmobilenum = findViewById(R.id.etcontactnumber);

//        repremark = findViewById(R.id.edtreportingremark);
//        cbox = findViewById(R.id.isreporting);


        // for Auto Genrated serial number
        sharedPreferences = getSharedPreferences("VehicleManagementPrefs", MODE_PRIVATE);
        //
        linearLayout = findViewById(R.id.layout_list);
        button1 = findViewById(R.id.button_add);
        button1.setOnClickListener(this);
        //dbbutton = findViewById(R.id.dbview);


        teamList.add(0, "Ton");
        teamList.add(1, "Litre");
        teamList.add(2, "KL");
        teamList.add(3, "Kgs");
        teamList.add(4, "Pcs");

//        listdata button
        /*dbbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Inward_Tanker_Security.this, GridCompleted.class));
            }
        });*/

        /*etmobilenum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not needed for this implementation
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Not needed for this implementation
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String currentText = etmobilenum.getText().toString();
                if (editable.length() > 0 && editable.length() <= 16) {
                    // Clear any previous error message when valid
                    etmobilenum.setError(null);
                } else {
                    String trimmedText = editable.toString().substring(0, Math.min(editable.length(), 8));
                    if (!currentText.equals(trimmedText)) {
                        // Only set text and move cursor if the modification is not the desired text
                        etmobilenum.setText(trimmedText);
                        etmobilenum.setSelection(trimmedText.length()); // Move cursor to the end
                    }
                }
            }
        });*/

        etnetweight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not needed for this implementation
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Not needed for this implementation
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String currentText = etnetweight.getText().toString();
                if (editable.length() > 0 && editable.length() <= 8) {
                    // Clear any previous error message when valid
                    etnetweight.setError(null);
                } else {
                    String trimmedText = editable.toString().substring(0, Math.min(editable.length(), 8));
                    if (!currentText.equals(trimmedText)) {
                        // Only set text and move cursor if the modification is not the desired text
                        etnetweight.setText(trimmedText);
                        etnetweight.setSelection(trimmedText.length()); // Move cursor to the end
                    }
                }
            }
        });

        /*etqty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not needed for this implementation
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Not needed for this implementation
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String currentText = etqty.getText().toString();
                if (editable.length() > 0 && editable.length() <= 8) {
                    // Clear any previous error message when valid
                    etqty.setError(null);
                } else {
                    String trimmedText = editable.toString().substring(0, Math.min(editable.length(), 8));
                    if (!currentText.equals(trimmedText)) {
                        // Only set text and move cursor if the modification is not the desired text
                        etqty.setText(trimmedText);
                        etqty.setSelection(trimmedText.length()); // Move cursor to the end
                    }
                }
            }
        });*/

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
                        etdate.setText(dateFormat.format(calendar.getTime()).replace("Sept","Sep"));
                    }
                }, year, month, day);
                picker.show();
            }
        });

        etintime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                String time = format.format(calendar.getTime());
                etintime.setText(time);
            }
        });


        btnadd = findViewById(R.id.submit);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    insertdata();
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        updbtnclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upditinsecbyinwardid();
            }
        });
//        saveButton = findViewById(R.id.saveButton);
//        saveButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                insertreporting();
//            }
//        });

        etvehical.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
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
                String action = getIntent().getStringExtra("Action");
                if (action != null && action.equals("Up")) {
                    FetchVehicleDetailsforUpdate(getIntent().getStringExtra("VehicleNumber"), Global_Var.getInstance().MenuType, 'x', 'I');
                } else {
                    FetchVehicleDetails(getIntent().getStringExtra("VehicleNumber"), Global_Var.getInstance().MenuType, 'S', 'I');
                    saveButton.setVisibility(View.GONE);
                    button1.setVisibility(View.GONE);
                }
//                btnadd.setVisibility(View.GONE);
            } else {
                GetMaxSerialNo(vehicletype+ formattedDate);
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
                if (response.isSuccessful()) {
                    List<ResponseModel> userList = response.body();
                    if (userList != null) {
                        for (ResponseModel resmodel : userList) {
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
                                notificationsSender.triggerSendNotification();
                            }
                        }
                    }
                } else {
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

    public void Notificationforall(String vehicleNumber) {
        FcmNotificationsSender notificationsSender = new FcmNotificationsSender(
                "/topics/all",
                "Vehicle Entry in Factory for Inward Tanker Process",
                "This Vehicle:-" + vehicleNumber + " Entry in Factory for Inward Tanker Process",
                getApplicationContext(),
                Inward_Tanker_Security.this
        );
        notificationsSender.triggerSendNotification();
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
        /*if (!etqty.getText().toString().isEmpty()) {
            try {
                insertqty = Integer.parseInt(etqty.getText().toString().trim());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }*/
        //int netweight = Integer.parseInt(etnetweight.getText().toString().trim());
        if (!etnetweight.getText().toString().isEmpty()) {
            try {
                insertnetweight = Integer.parseInt(etnetweight.getText().toString().trim());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        String intime = etintime.getText().toString().trim();
        String outTime = getCurrentTime();//Insert out Time Directly to the Database
        //int qtyuom = Integer.parseInt(qtyUomNumericValue.toString().trim());
        /*if (!qtyUomNumericValue.toString().isEmpty()) {
            try {
                insertqtyUom = Integer.parseInt(qtyUomNumericValue.toString().trim());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }*/
        String vehicltype = Global_Var.getInstance().MenuType;
        char InOutType = Global_Var.getInstance().InOutType;
        char DeptType = Global_Var.getInstance().DeptType;
        //int netweuom = Integer.parseInt(netweuomvalue.toString().trim());
        if (!netweuomvalue.toString().isEmpty()) {
            try {
                insertnetweightUom = Integer.parseInt(netweuomvalue.toString().trim());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        String remark = etremark.getText().toString().trim();
        //String pooa = edpooa.getText().toString().trim();
        //String mobnumber = etmobilenum.getText().toString().trim();
//        String edremark = repremark.getText().toString().trim();
        if (vehicalnumber.isEmpty() || invoicenumber.isEmpty() || Date.isEmpty() ||
                intime.isEmpty() || remark.isEmpty()
                || insertnetweight < 0 || insertnetweightUom < 0) {
            Toasty.warning(this, "All fields must be filled", Toast.LENGTH_SHORT, true).show();
        } else {

            //Extra material dynamic view
            //List<Map<String, String>> materialList = new ArrayList<>();

// Create a JSON array to hold the extra materials
            JSONArray extraMaterialsArray = new JSONArray();
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
                        try {
                            // Create a new JSONObject for each material
                            JSONObject materialObject = new JSONObject();
                            materialObject.put("Material", dynamaterial);
                            materialObject.put("Qty", Double.parseDouble(dynaqty));
                            materialObject.put("Qtyuom", dynaqtyuom);

                            // Add the material JSON object to the array
                            extraMaterialsArray.put(materialObject);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

// Convert JSONArray to string
            String extraMaterialsString = extraMaterialsArray.toString();
            Request_Model_In_Tanker_Security requestModelInTankerSecurity = new Request_Model_In_Tanker_Security(serialnumber, invoicenumber, vehicalnumber, Date, partyname, "material", "", "", 'W', 'I', Date,
                    "", vehicltype, intime, outTime, 1, insertnetweightUom, insertnetweight, 1, extraMaterialsString.toString(), remark, false, "No", "", "", "", "", "", EmployeId, "", InwardId);

            Call<Boolean> call = apiInTankerSecurity.postData(requestModelInTankerSecurity);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body() != null && response.body()==true) {
                        makeNotification(vehicalnumber, outTime);
                        Toasty.success(Inward_Tanker_Security.this, "Data Inserted Succesfully !", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Inward_Tanker_Security.this, Inward_Tanker.class));
                        finish();
                    } else {
                        Toasty.error(Inward_Tanker_Security.this, "Data Insertion Failed..!", Toast.LENGTH_SHORT).show();
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
                    "", vehicltype, intime, outTime, qtyuom, netweuom, netweight, qty, "", remark, isreporting, edremark, "", "", "", "", "", EmployeId, "", InwardId);

            apiInTankerSecurity = RetroApiclient_In_Tanker_Security.getinsecurityApi();
            Call<Boolean> call = apiInTankerSecurity.postData(requestModelInTankerSecurity);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body() != null && response.body()==true) {
                        Toasty.success(Inward_Tanker_Security.this, "Data Inserted Succesfully !", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Inward_Tanker_Security.this, Inward_Tanker.class));
                        finish();
                    } else {
                        Toasty.error(Inward_Tanker_Security.this, "Data Insertion Failed..!", Toast.LENGTH_SHORT).show();
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
                        etreg.setText(obj.getSerialNo());
                        etreg.setEnabled(false);
                        etvehical.setText(obj.getVehicleNo());
                        etvehical.setEnabled(false);
                        repremark.setText(obj.getReportingRemark());
                        repremark.setEnabled(false);
                        etdate.setText(obj.getDate());
                        etdate.setEnabled(false);
//                        cbox.setChecked(true);
//                        cbox.setEnabled(false);
//                        saveButton.setVisibility(View.GONE);
//                        repremark.setEnabled(false);
                        etreg.setEnabled(false);
                        etdate.setEnabled(false);
//                            DocId = document.getId();
                    }
                } else {
                    Toasty.error(Inward_Tanker_Security.this, "This Vehicle Number Is Not Available..!", Toast.LENGTH_SHORT).show();
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

    public void FetchVehicleDetailsforUpdate(@NonNull String VehicleNo, String vehicltype, char DeptType, char InOutType) {
        Call<List<Respo_Model_In_Tanker_security>> call = RetroApiClient.getserccrityveh().GetIntankerSecurityByVehicle(VehicleNo, vehicltype, DeptType, InOutType);
        call.enqueue(new Callback<List<Respo_Model_In_Tanker_security>>() {
            @Override
            public void onResponse(Call<List<Respo_Model_In_Tanker_security>> call, Response<List<Respo_Model_In_Tanker_security>> response) {
                if (response.isSuccessful()) {
                    if (response.body().size() > 0) {
                        List<Respo_Model_In_Tanker_security> Data = response.body();
                        Respo_Model_In_Tanker_security obj = Data.get(0);
                        InwardId = obj.getInwardId();
                        etreg.setText(obj.getSerialNo());
                        etreg.setEnabled(false);
                        etvehical.setText(obj.getVehicleNo());
                        etvehical.setEnabled(false);
                        etdate.setText(obj.getDate());
                        etdate.setEnabled(false);
                        etinvoice.setText(obj.getInvoiceNo());
                        etinvoice.setEnabled(true);
                        //etmobilenum.setText(obj.getDriver_MobileNo());
                        //etmobilenum.setEnabled(true);
                        //etsupplier.setText(obj.getPartyName());
                        //etsupplier.setEnabled(true);
                        etmaterial.setText(obj.getMaterial());
                        etmaterial.setEnabled(true);
                        //edpooa.setText(obj.getOA_PO_number());
                        //edpooa.setEnabled(true);
                        //etqty.setText(String.valueOf(obj.getQty()));
                        //etqty.setEnabled(true);
                        etnetweight.setText(String.valueOf(obj.getNetWeight()));
                        etnetweight.setEnabled(true);
                        //etqtyoum.setText(obj.getUnitOfMeasurement());
                        //etqtyoum.setEnabled(true);
                        etnetoum.setText(obj.getUnitOfNetWeight());
                        etnetoum.setEnabled(true);
                        etremark.setText(obj.getSecRemark());
                        etremark.setEnabled(true);
                        updbtnclick.setVisibility(View.VISIBLE);
                        etintime.setVisibility(View.GONE);
                        isReportingCheckBox.setEnabled(false);
                        btnadd.setVisibility(View.GONE);
                        //isReportingCheckBox.setVisibility(View.GONE);
                        saveButton.setVisibility(View.GONE);
                    }else {
                        Toasty.error(Inward_Tanker_Security.this, "This Vehicle Number Is Out From Factory.\n You Can Not Update", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(Inward_Tanker_Security.this, it_in_sec_Completedgrid.class));
                    }
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
                Toasty.error(Inward_Tanker_Security.this, "failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void upditinsecbyinwardid() {
        String serialnumber = etreg.getText().toString().trim();
        String vehicalnumber = etvehical.getText().toString().trim();
        String invoicenumber = etinvoice.getText().toString().trim();
        String Date = etdate.getText().toString().trim();
        //String partyname = etsupplier.getText().toString().trim();
        String material = etmaterial.getText().toString().trim();
        /*if (!etqty.getText().toString().isEmpty()) {
            try {
                insertqty = Integer.parseInt(etqty.getText().toString().trim());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }*/
        if (!etnetweight.getText().toString().isEmpty()) {
            try {
                insertnetweight = Integer.parseInt(etnetweight.getText().toString().trim());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        /*if (!qtyUomNumericValue.toString().isEmpty()) {
            try {
                insertqtyUom = Integer.parseInt(qtyUomNumericValue.toString().trim());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }*/
        if (!netweuomvalue.toString().isEmpty()) {
            try {
                insertnetweightUom = Integer.parseInt(netweuomvalue.toString().trim());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        String remark = etremark.getText().toString().trim();
        //String pooa = edpooa.getText().toString().trim();
        //String mobnumber = etmobilenum.getText().toString().trim();

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
                    materialMap.put("Material", dynamaterial);
                    materialMap.put("Qty", dynaqty);
                    materialMap.put("Qtyuom", dynaqtyuom);
                    // Add material data to the list
                    materialList.add(materialMap);
                }
            }
        }

        It_in_updsecbyinwardid_req_model requestModelITSecurity = new It_in_updsecbyinwardid_req_model(InwardId, serialnumber,
                invoicenumber, vehicalnumber, Date, "", material, "", "", 1, insertnetweightUom,
                insertnetweight, 1, materialList.toString(), remark, EmployeId);

        Call<Boolean> call = apiInTankerSecurity.itinsecupd(requestModelITSecurity);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful() && response.body() != null && response.body()==true) {
                    //makeNotification(vehicalnumber, outTime);
                    Toasty.success(Inward_Tanker_Security.this, "Data Inserted Succesfully !", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Inward_Tanker_Security.this, Inward_Tanker.class));
                    finish();
                } else {
                    Toasty.error(Inward_Tanker_Security.this, "Data Insertion Failed..!", Toast.LENGTH_SHORT).show();
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

    /*public void GetMaxSerialNo(String formattedDate) {
        Call<String> call = getmaxserialno.getMaxSerialNumber(formattedDate);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String maxSerialNumber = response.body();
                    String newSerialNumber;

                    // Check if the fetched serial number belongs to the specified vehicle type
                    if (maxSerialNumber.startsWith(vehicletype)) {
                        int autoGeneratedNumber = Integer.parseInt(maxSerialNumber.substring(10, 13)) + 1;
                        @SuppressLint("DefaultLocale") String autoGeneratedNumberString = String.format("%03d", autoGeneratedNumber);
                        newSerialNumber = vehicletype + formattedDate + autoGeneratedNumberString;
                    } else {
                        // Start with a new serial number for that vehicle type
                        newSerialNumber = vehicletype + formattedDate + "001";
                    }

                    etreg.setText(newSerialNumber);
                    etreg.setEnabled(true);
                } else {
                    // Handle the error
                    String serialNumber = vehicletype + formattedDate + "001";
                    etreg.setText(serialNumber);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                String serialNumber = vehicletype + formattedDate + "001";
                etreg.setText(serialNumber);
                Log.e("Retrofit", "Failure: " + t.getMessage());
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
    }*/

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
                    String serialNumber = formattedDate + autoGeneratedNumberString;
                    etreg.setText(serialNumber);
                    etreg.setEnabled(true);
                } else {
                    // Handle the error
                    String serialNumber = formattedDate + "001";
                    etreg.setText(serialNumber);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                String serialNumber = formattedDate + "001";
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
        //String drivermobile = etmobilenum.getText().toString().trim();
        //String party = etsupplier.getText().toString().trim();
        String material = etmaterial.getText().toString().trim();
        //String oapo = edpooa.getText().toString().trim();
        if (!etnetweight.getText().toString().isEmpty()) {
            try {
                insertnetweight = Integer.parseInt(etnetweight.getText().toString().trim());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        if (!netweuomvalue.toString().isEmpty()) {
            try {
                insertnetweightUom = Integer.parseInt(netweuomvalue.toString().trim());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        /*if (!etqty.getText().toString().isEmpty()) {
            try {
                insertqty = Integer.parseInt(etqty.getText().toString().trim());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }*/
//        int qtyuom = Integer.parseInt( qtyUomNumericValue.toString().trim());
        /*if (!qtyUomNumericValue.toString().isEmpty()) {
            try {
                insertqtyUom = Integer.parseInt(qtyUomNumericValue.toString().trim());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }*/
        String remark = etremark.getText().toString().trim();
        String outTime = getCurrentTime();
        String vehicltype = Global_Var.getInstance().MenuType;
        char InOutType = Global_Var.getInstance().InOutType;
        char DeptType = Global_Var.getInstance().DeptType;

        if (invoice.isEmpty() || material.isEmpty()||
                 insertnetweight < 0  || insertnetweightUom < 0
        ) {
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

            Update_Request_Model_Insequrity requestModelInTankerSecurityupdate = new Update_Request_Model_Insequrity(
                    InwardId, serialnumber, invoice, vehiclenumber, Date, "", material, "", "",
                    'W', 'I', Date, "", vehicltype, intime, outTime, 1,
                    insertnetweightUom, insertnetweight, 1,
                    materialList.toString(), remark, "", "",
                    "", "", "", EmployeId, "");

            apiInTankerSecurity = RetroApiclient_In_Tanker_Security.getinsecurityApi();
            Call<Boolean> call = apiInTankerSecurity.updatesecuritydata(requestModelInTankerSecurityupdate);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body() != null && response.body()==true) {
                        Notificationforall(vehiclenumber);
                        makeNotification(vehiclenumber, outTime);
                        Toasty.success(Inward_Tanker_Security.this, "Data Inserted Succesfully..!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Inward_Tanker_Security.this, Inward_Tanker.class));
                        finish();
                    } else {
                        Toasty.error(Inward_Tanker_Security.this, "Data Insertion Failed..!", Toast.LENGTH_SHORT).show();
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

    public void olcViewclick(View view) {
        Intent intent = new Intent(this, it_in_sec_Completedgrid.class);
        startActivity(intent);
    }
}

