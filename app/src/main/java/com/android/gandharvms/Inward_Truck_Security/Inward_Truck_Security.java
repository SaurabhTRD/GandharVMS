package com.android.gandharvms.Inward_Truck_Security;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.gandharvms.FcmNotificationsSender;
import com.android.gandharvms.Global_Var;
import com.android.gandharvms.InwardCompletedGrid.GridCompleted;
import com.android.gandharvms.Inward_Tanker_Laboratory.Inward_Tanker_Laboratory;
import com.android.gandharvms.Inward_Tanker_Security.API_In_Tanker_Security;
import com.android.gandharvms.Inward_Tanker_Security.In_Tanker_Security_list;
import com.android.gandharvms.Inward_Tanker_Security.Inward_Tanker_Security;
import com.android.gandharvms.Inward_Tanker_Security.Request_Model_In_Tanker_Security;
import com.android.gandharvms.Inward_Tanker_Security.Respo_Model_In_Tanker_security;
import com.android.gandharvms.Inward_Tanker_Security.RetroApiclient_In_Tanker_Security;
import com.android.gandharvms.Inward_Tanker_Security.Update_Request_Model_Insequrity;
import com.android.gandharvms.Inward_Tanker_Security.grid;
import com.android.gandharvms.Inward_Truck;
import com.android.gandharvms.Inward_Truck_store.Inward_Truck_Store;
import com.android.gandharvms.LoginWithAPI.Login;
import com.android.gandharvms.LoginWithAPI.LoginMethod;
import com.android.gandharvms.LoginWithAPI.ResponseModel;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.Menu;
import com.android.gandharvms.NotificationAlerts.NotificationCommonfunctioncls;
import com.android.gandharvms.R;
import com.android.gandharvms.submenu.submenu_Inward_Truck;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class Inward_Truck_Security extends NotificationCommonfunctioncls {

    final Calendar calendar = Calendar.getInstance();
    private final int MAX_LENGTH = 10;
    private final String dateTimeString = "";
    public LoginMethod getmaxserialno;
    String[] items = {"Ton", "Litre", "KL", "Kgs", "pcs"};
    String[] items1 = {"Ton", "Litre", "KL", "Kgs", "pcs"};
    String[] regitems = {"Capital Register", "General Register", "Inward Register"};
    List<String> teamList = new ArrayList<>();
    LinearLayout linearLayout;
    AutoCompleteTextView autoCompleteTextView, regAutoCompleteTextView;
    //    AutoCompleteTextView autoCompleteTextView1;
    ArrayAdapter<String> qtyuomdrop;
    ArrayAdapter<String> netweuomdrop;
    RadioButton lrcopyYes, lrcopyNo, deliveryYes, deliveryNo, taxinvoiceYes, taxinvoiceNo, ewaybillYes, ewaybillNo;
    ActivityResultLauncher<String> launcher;
    EditText etintime, etserialnumber, etvehicalnumber, etsinvocieno, etsdate, etssupplier,
             etsqty, etsuom, etsnetwt, etsuom2, etregister, repremark, etmobile, etoapo, etremark;
    Button wesubmit;
    Button view;
    FirebaseFirestore intrsdbroot;
    DatePickerDialog picker;
    TimePickerDialog tpicker;
    ArrayAdapter<String> registeritem;
    Button saveButton, button1, updbtnclick;
    CheckBox cbox;
    String DocId = "";
    Date currentDate = Calendar.getInstance().getTime();
    //uom
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
    Integer qtyUomNumericValue = 1;
    Integer netweuomvalue = 1;
    Map<String, Integer> qtyUomMappingintruck = new HashMap<>();
    AutoCompleteTextView autoCompleteTextView1, autoCompleteTextView2;
    Map<String, Integer> qtyUomMapping = new HashMap<>();
    private SharedPreferences sharedPreferences;
    private int autoGeneratedNumber;
    private CheckBox isReportingCheckBox;
    private EditText reportingRemarkLayout;
    private String token;
    private API_In_Tanker_Security apiInTankerSecurity;
    private int inwardid;
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private final String EmployeName = Global_Var.getInstance().Name;
    private final String EmployeId = Global_Var.getInstance().EmpId;
    private int InwardId;
    private LoginMethod userDetails;
    private int insertqty;
    private int insertnetweight;

    private int insertqtyUom;
    private int insertnetweightUom;
    ImageView btnlogout,btnhome;
    TextView username,empid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inward_truck_security);

        getmaxserialno = RetroApiClient.getLoginApi();
        userDetails = RetroApiClient.getLoginApi();

        setupHeader();
        //Send Notification to all
        FirebaseMessaging.getInstance().subscribeToTopic("all");

        regAutoCompleteTextView = findViewById(R.id.etregister);
        registeritem = new ArrayAdapter<String>(this, R.layout.security_registerlist, regitems);
        regAutoCompleteTextView.setAdapter(registeritem);
        regAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String items = parent.getItemAtPosition(position).toString();
                Toasty.success(getApplicationContext(), "Item:- " + items + " Selected", Toast.LENGTH_SHORT).show();
            }
        });

        // for Auto Genrated serial number
        sharedPreferences = getSharedPreferences("TruckSecurity", MODE_PRIVATE);

//        autoCompleteTextView1 = findViewById(R.id.etsuom);
        qtyUomMapping = new HashMap<>();
        qtyUomMapping.put("NA", 1);
        qtyUomMapping.put("Ton", 2);
        qtyUomMapping.put("Litre", 3);
        qtyUomMapping.put("KL", 4);
        qtyUomMapping.put("Kgs", 5);
        qtyUomMapping.put("pcs", 6);
        qtyUomMapping.put("M3",7);
        qtyUomMapping.put("Meter",9);
        qtyUomMapping.put("Feet",10);

        qtyuomdrop = new ArrayAdapter<String>(this, R.layout.in_ta_se_qty, new ArrayList<>(qtyUomMapping.keySet()));

        autoCompleteTextView2 = findViewById(R.id.etsuom2);
        netweuomdrop = new ArrayAdapter<String>(this, R.layout.in_tr_se_nwe_list, new ArrayList<>(qtyUomMapping.keySet()));
        autoCompleteTextView2.setAdapter(netweuomdrop);
        autoCompleteTextView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item2 = parent.getItemAtPosition(position).toString();
                netweuomvalue = qtyUomMapping.get(item2);
                if (netweuomvalue != null) {
                    Toasty.success(Inward_Truck_Security.this, "NetWeighUnitofMeasurement : " + item2 + " Selected", Toast.LENGTH_SHORT).show();
                } else {
                    Toasty.warning(Inward_Truck_Security.this, "Default NetWeighUnitofMeasurement : " + "NA", Toast.LENGTH_SHORT).show();
                }
            }
        });


        etintime = findViewById(R.id.etintime);
        etserialnumber = findViewById(R.id.etserialnumber);
        etvehicalnumber = findViewById(R.id.etvehicalnumber);
        etsinvocieno = findViewById(R.id.etsinvocieno);
        etsdate = findViewById(R.id.etsdate);
        etssupplier = findViewById(R.id.etssupplier);
        etsnetwt = findViewById(R.id.etsnetwt);
        etsuom2 = findViewById(R.id.etsuom2);
        etregister = findViewById(R.id.etregister);
        etmobile = findViewById(R.id.etmob);
        etoapo = findViewById(R.id.etoapo);
        etremark = findViewById(R.id.edtremark);


        //for add material

        linearLayout = findViewById(R.id.layout_list);
        button1 = findViewById(R.id.button_add);
        updbtnclick = findViewById(R.id.irinsecupdateclick);
        button1.setOnClickListener(this::onClick);

        teamList.add("NA");
        teamList.add("Ton");
        teamList.add("Litre");
        teamList.add("KL");
        teamList.add("Kgs");
        teamList.add("Pcs");
        teamList.add("M3");
        teamList.add("Meter");
        teamList.add("Feet");

        lrcopyYes = findViewById(R.id.rb_LRCopyYes);
        lrcopyNo = findViewById(R.id.rb_LRCopyNo);
        deliveryYes = findViewById(R.id.rb_DeliveryYes);
        deliveryNo = findViewById(R.id.rb_DeliveryNo);
        taxinvoiceYes = findViewById(R.id.rb_TaxInvoiceYes);
        taxinvoiceNo = findViewById(R.id.rb_TaxInvoiceNo);
        ewaybillYes = findViewById(R.id.rb_EwaybillYes);
        ewaybillNo = findViewById(R.id.rb_EwaybillNo);
        etsdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                // Array of month abbreviations
                String[] monthAbbreviations = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
                picker = new DatePickerDialog(Inward_Truck_Security.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Use the month abbreviation from the array
                        String monthAbbreviation = monthAbbreviations[month];
                        // etdate.setText(dayOfMonth + "/" + monthAbbreviation + "/" + year);
                        etsdate.setText(dateFormat.format(calendar.getTime()).replace("Sept","Sep"));
                    }
                }, year, month, day);
                picker.show();
            }
        });

        etsnetwt.addTextChangedListener(new TextWatcher() {
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
                String currentText = etsnetwt.getText().toString();
                if (editable.length() > 0 && editable.length() <= 8) {
                    // Clear any previous error message when valid
                    etsnetwt.setError(null);
                } else {
                    String trimmedText = editable.toString().substring(0, Math.min(editable.length(), 8));
                    if (!currentText.equals(trimmedText)) {
                        // Only set text and move cursor if the modification is not the desired text
                        etsnetwt.setText(trimmedText);
                        etsnetwt.setSelection(trimmedText.length()); // Move cursor to the end
                    }
                }
            }
        });

        etmobile.addTextChangedListener(new TextWatcher() {
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
                String currentText = etmobile.getText().toString();
                if (editable.length() > 0 && editable.length() <= 16) {
                    // Clear any previous error message when valid
                    etmobile.setError(null);
                } else {
                    String trimmedText = editable.toString().substring(0, Math.min(editable.length(), 8));
                    if (!currentText.equals(trimmedText)) {
                        // Only set text and move cursor if the modification is not the desired text
                        etmobile.setText(trimmedText);
                        etmobile.setSelection(trimmedText.length()); // Move cursor to the end
                    }
                }
            }
        });

        String dateFormatPattern = "ddMMyyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatPattern, Locale.getDefault());
        String formattedDate = dateFormat.format(currentDate);

        if (getIntent().hasExtra("VehicleNumber")) {
            String action = getIntent().getStringExtra("Action");
            if (action != null && action.equals("Up")) {
                FetchVehicleDetailsforUpdate(getIntent().getStringExtra("VehicleNumber"), Global_Var.getInstance().MenuType, 'x', 'I');
            } else {
                FetchVehicleDetails(getIntent().getStringExtra("VehicleNumber"), Global_Var.getInstance().MenuType, nextProcess, inOut);
            }
        } else {
            GetMaxSerialNo(vehicleType+formattedDate);
        }
        etintime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                String time = format.format(calendar.getTime());
                etintime.setText(time);
            }
        });

        wesubmit = findViewById(R.id.wesubmit);
        intrsdbroot = FirebaseFirestore.getInstance();

        wesubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                trsedata();
            }
        });

        updbtnclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irinsecupdbyinwardid();
            }
        });

        etvehicalnumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    FetchVehicleDetails(etvehicalnumber.getText().toString().trim(), vehicleType, nextProcess, inOut);
                }
            }
        });

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
                        for (ResponseModel responseModel : userList) {
                            String specificrole = "Weighment";
                            if (specificrole.equals(responseModel.getDepartment())) {
                                token = responseModel.getToken();
                                FcmNotificationsSender notificationsSender = new FcmNotificationsSender(
                                        token,
                                        "Inward Truck Security Process Done..!",
                                        "Vehicle Number:-" + vehicleNumber + " has completed Security process at " + outTime,
                                        getApplicationContext(),
                                        Inward_Truck_Security.this
                                );
                                notificationsSender.triggerSendNotification();
                            }
                        }
                    }
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
                Toasty.error(Inward_Truck_Security.this, "failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void Notificationforall(String vehicleNumber) {
        FcmNotificationsSender notificationsSender = new FcmNotificationsSender(
                "/topics/all",
                "Vehicle Entry in Factory for Inward Truck Process",
                "This Vehicle:-" + vehicleNumber + " Entry in Factory for Inward Truck Process",
                getApplicationContext(),
                Inward_Truck_Security.this
        );
        notificationsSender.triggerSendNotification();
    }

    public void trsedata() {
        String serialnumber = etserialnumber.getText().toString().trim();
        String vehicalnumber = etvehicalnumber.getText().toString().trim();
        String invoicenumber = etsinvocieno.getText().toString().trim();
        String Date = etsdate.getText().toString().trim();
        String partyname = etssupplier.getText().toString().trim();
        //int netweight = Integer.parseInt(etsnetwt.getText().toString().trim());
        if (!etsnetwt.getText().toString().isEmpty()) {
            try {
                insertnetweight = Integer.parseInt(etsnetwt.getText().toString().trim());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        //int netweuom = Integer.parseInt(netweuomvalue.toString().trim());
        if (!netweuomvalue.toString().isEmpty()) {
            try {
                insertnetweightUom = Integer.parseInt(netweuomvalue.toString().trim());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        String intime = etintime.getText().toString().trim();
        String outTime = getCurrentTime();
        String vehicltype = Global_Var.getInstance().MenuType;
        char InOutType = Global_Var.getInstance().InOutType;
        char DeptType = Global_Var.getInstance().DeptType;

        String lrCopySelection = lrcopyYes.isChecked() ? "Yes" : "No";
        String deliverySelection = deliveryYes.isChecked() ? "Yes" : "No";
        String taxInvoiceSelection = taxinvoiceYes.isChecked() ? "Yes" : "No";
        String ewayBillSelection = ewaybillYes.isChecked() ? "Yes" : "No";

        String remark = etremark.getText().toString().trim();
        String pooa = etoapo.getText().toString().trim();
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
        String mobnumber = etmobile.getText().toString().trim();
//        String edremark = repremark.getText().toString().trim();
        String selectregister = etregister.getText().toString().trim();
        if (serialnumber.isEmpty() || vehicalnumber.isEmpty() || invoicenumber.isEmpty() || Date.isEmpty() || partyname.isEmpty() ||
                intime.isEmpty() || remark.isEmpty() || pooa.isEmpty() || mobnumber.isEmpty() || selectregister.isEmpty()
                 || insertnetweight < 0 ||  insertnetweightUom < 0) {
            Toasty.warning(this, "All fields must be filled", Toast.LENGTH_SHORT, true).show();
        } else if (extraMaterialsArray.length() == 0) {
            // Check if no materials are added
            Toasty.warning(this, "Please add at least one material before submitting.", Toast.LENGTH_SHORT, true).show();
        }
        else {
// Convert JSONArray to string
            String extraMaterialsString = extraMaterialsArray.toString();
            Request_Model_In_Tanker_Security requestModelInTankerSecurity = new Request_Model_In_Tanker_Security(serialnumber, invoicenumber, vehicalnumber, Date, partyname, "material", pooa, mobnumber, 'W', 'I', Date,
                    "", vehicltype, intime, outTime, 1, insertnetweightUom, insertnetweight, 1, extraMaterialsString.toString(), remark, false, "No", selectregister, lrCopySelection, deliverySelection, taxInvoiceSelection, ewayBillSelection, EmployeId, "", InwardId);

            apiInTankerSecurity = RetroApiclient_In_Tanker_Security.getinsecurityApi();
            Call<Boolean> call = apiInTankerSecurity.postData(requestModelInTankerSecurity);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body() != null && response.body()) {
                        Notificationforall(vehicalnumber);
                        makeNotification(vehicalnumber, outTime);
                        Toasty.success(Inward_Truck_Security.this, "Data Inserted Succesfully !", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Inward_Truck_Security.this, grid.class));
                        finish();
                    } else {
                        Toasty.error(Inward_Truck_Security.this, "Data Insertion Failed..!", Toast.LENGTH_SHORT).show();
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
                    Toasty.error(Inward_Truck_Security.this, "failed", Toast.LENGTH_SHORT).show();
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
                        Respo_Model_In_Tanker_security obj = (Respo_Model_In_Tanker_security) Data.get(0);
                        int intimelength = obj.getInTime().length();
                        InwardId = obj.getInwardId();
                        /*etintime.setText(obj.getInTime().substring(12,intimelength));*/
                        etserialnumber.setText("");
                        etserialnumber.setText(obj.getSerialNo());
                        etserialnumber.setEnabled(false);
                        etvehicalnumber.setText(obj.getVehicleNo());
                        etvehicalnumber.setEnabled(false);
                        etsdate.setText(obj.getDate());
                        etsdate.setEnabled(false);
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
                        etserialnumber.setText(obj.getSerialNo());
                        etserialnumber.setEnabled(false);
                        etvehicalnumber.setText(obj.getVehicleNo());
                        etvehicalnumber.setEnabled(false);
                        etsdate.setText(obj.getDate());
                        etsdate.setEnabled(false);
                        etsinvocieno.setText(obj.getInvoiceNo());
                        etsinvocieno.setEnabled(true);
                        etmobile.setText(obj.getDriver_MobileNo());
                        etmobile.setEnabled(true);
                        etssupplier.setText(obj.getPartyName());
                        etssupplier.setEnabled(true);
                        //etsmaterial.setText(obj.getMaterial());
                        //etsmaterial.setEnabled(true);
                        etoapo.setText(obj.getOA_PO_number());
                        etoapo.setEnabled(true);
                        etsqty.setText(String.valueOf(obj.getQty()));
                        etsqty.setEnabled(true);
                        etsnetwt.setText(String.valueOf(obj.getNetWeight()));
                        etsnetwt.setEnabled(true);
                        etsuom.setText(obj.getUnitOfMeasurement());
                        etsuom.setEnabled(true);
                        etsuom2.setText(obj.getUnitOfNetWeight());
                        etsuom2.setEnabled(true);
                        etremark.setText(obj.getSecRemark());
                        etremark.setEnabled(true);
                        lrcopyYes.setChecked(obj.getIrCopy().equalsIgnoreCase("Yes")); // Assuming the value is "Yes" or "No"
                        lrcopyNo.setChecked(obj.getIrCopy().equalsIgnoreCase("No"));
                        deliveryYes.setChecked(obj.getDeliveryBill().equalsIgnoreCase("Yes"));
                        deliveryNo.setChecked(obj.getDeliveryBill().equalsIgnoreCase("No"));
                        taxinvoiceYes.setChecked(obj.getTaxInvoice().equalsIgnoreCase("Yes"));
                        taxinvoiceNo.setChecked(obj.getTaxInvoice().equalsIgnoreCase("No"));
                        ewaybillYes.setChecked(obj.getEwayBill().equalsIgnoreCase("Yes"));
                        ewaybillNo.setChecked(obj.getEwayBill().equalsIgnoreCase("No"));
                        etregister.setText(obj.getSelectregister());
                        etregister.setEnabled(true);
                        isReportingCheckBox.setEnabled(false);
                        //isReportingCheckBox.setVisibility(View.GONE);
                        saveButton.setVisibility(View.GONE);
                        wesubmit.setVisibility(View.GONE);
                        updbtnclick.setVisibility(View.VISIBLE);
                        etintime.setVisibility(View.GONE);
                    }
                    else {
                        Toasty.error(Inward_Truck_Security.this, "This Vehicle Number Is Out From Factory.\n You Can Not Update", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(Inward_Truck_Security.this, ir_in_sec_Completedgrid.class));
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
                Toasty.error(Inward_Truck_Security.this, "failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateData() {
        String lrCopySelection = lrcopyYes.isChecked() ? "Yes" : "No";
        String deliverySelection = deliveryYes.isChecked() ? "Yes" : "No";
        String taxInvoiceSelection = taxinvoiceYes.isChecked() ? "Yes" : "No";
        String ewayBillSelection = ewaybillYes.isChecked() ? "Yes" : "No";
        String outTime = getCurrentTime();
        String serialnumber = etserialnumber.getText().toString().trim();
        String vehiclenumber = etvehicalnumber.getText().toString().trim();
        String Date = etsdate.getText().toString().trim();
        String intime = etintime.getText().toString().trim();
        String invoice = etsinvocieno.getText().toString().trim();
        String drivermobile = etmobile.getText().toString().trim();
        String party = etssupplier.getText().toString().trim();
        //String material = etsmaterial.getText().toString().trim();
        String oapo = etoapo.getText().toString().trim();
        String remark = etremark.getText().toString().trim();
        if (!etsnetwt.getText().toString().isEmpty()) {
            try {
                insertnetweight = Integer.parseInt(etsnetwt.getText().toString().trim());
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
        if (!etsqty.getText().toString().isEmpty()) {
            try {
                insertqty = Integer.parseInt(etsqty.getText().toString().trim());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
//        int qtyuom = Integer.parseInt( qtyUomNumericValue.toString().trim());
        if (!qtyUomNumericValue.toString().isEmpty()) {
            try {
                insertqtyUom = Integer.parseInt(qtyUomNumericValue.toString().trim());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        String vehicltype = Global_Var.getInstance().MenuType;
        char InOutType = Global_Var.getInstance().InOutType;
        char DeptType = Global_Var.getInstance().DeptType;

        String selectregister = etregister.getText().toString().trim();

        if (intime.isEmpty() || drivermobile.isEmpty() || invoice.isEmpty() || party.isEmpty() ||
                oapo.isEmpty() || remark.isEmpty() ||
                insertqty < 0 || insertnetweight < 0 || insertqtyUom < 0 || insertnetweightUom < 0) {
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
            Update_Request_Model_Insequrity requestModelInTankerSecurityupdate = new Update_Request_Model_Insequrity(InwardId, serialnumber, invoice, vehiclenumber, Date, party, "material", oapo, drivermobile, 'W', 'I', Date,
                    "", vehicltype, intime, outTime, insertqtyUom, insertnetweightUom, insertnetweight, insertqty, materialList.toString().replace("[]", ""), remark, selectregister, lrCopySelection, deliverySelection, taxInvoiceSelection, ewayBillSelection, EmployeId, "");

            apiInTankerSecurity = RetroApiclient_In_Tanker_Security.getinsecurityApi();
            Call<Boolean> call = apiInTankerSecurity.updatesecuritydata(requestModelInTankerSecurityupdate);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body() != null && response.body()) {
                        Toasty.success(Inward_Truck_Security.this, "Data Inserted Succesfully !", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Inward_Truck_Security.this, Inward_Truck.class));
                        finish();
                    } else {
                        Toasty.error(Inward_Truck_Security.this, "Data Insertion Failed..!", Toast.LENGTH_SHORT).show();
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
                    Toasty.error(Inward_Truck_Security.this, "failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void irinsecupdbyinwardid() {
        String lrCopySelection = lrcopyYes.isChecked() ? "Yes" : "No";
        String deliverySelection = deliveryYes.isChecked() ? "Yes" : "No";
        String taxInvoiceSelection = taxinvoiceYes.isChecked() ? "Yes" : "No";
        String ewayBillSelection = ewaybillYes.isChecked() ? "Yes" : "No";
        String serialnumber = etserialnumber.getText().toString().trim();
        String vehiclenumber = etvehicalnumber.getText().toString().trim();
        String Date = etsdate.getText().toString().trim();
        String intime = etintime.getText().toString().trim();
        String invoice = etsinvocieno.getText().toString().trim();
        String drivermobile = etmobile.getText().toString().trim();
        String party = etssupplier.getText().toString().trim();
        //String material = etsmaterial.getText().toString().trim();
        String oapo = etoapo.getText().toString().trim();
        String remark = etremark.getText().toString().trim();
        if (!etsnetwt.getText().toString().isEmpty()) {
            try {
                insertnetweight = Integer.parseInt(etsnetwt.getText().toString().trim());
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
        if (!etsqty.getText().toString().isEmpty()) {
            try {
                insertqty = Integer.parseInt(etsqty.getText().toString().trim());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        if (!qtyUomNumericValue.toString().isEmpty()) {
            try {
                insertqtyUom = Integer.parseInt(qtyUomNumericValue.toString().trim());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        String selectregister = etregister.getText().toString().trim();

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
        ir_in_updsecbyinwardid_re_model irinupdsecbyinwardid = new ir_in_updsecbyinwardid_re_model(InwardId, serialnumber,
                invoice, vehiclenumber, Date, party, "", oapo, drivermobile,0, insertnetweightUom, insertnetweight, 0,
                materialList.toString().replace("[]", ""), remark, selectregister, lrCopySelection,
                deliverySelection, taxInvoiceSelection, ewayBillSelection, EmployeId);

        apiInTankerSecurity = RetroApiclient_In_Tanker_Security.getinsecurityApi();
        Call<Boolean> call = apiInTankerSecurity.irinupdsecbyinwardid(irinupdsecbyinwardid);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful() && response.body() != null && response.body()) {
                    Toasty.success(Inward_Truck_Security.this, "Data Inserted Succesfully !", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Inward_Truck_Security.this, Inward_Truck.class));
                    finish();
                } else {
                    Toasty.error(Inward_Truck_Security.this, "Data Insertion Failed..!", Toast.LENGTH_SHORT).show();
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
                Toasty.error(Inward_Truck_Security.this, "failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getCurrentTime() {
        // Get the current time
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(new Date());
    }

    @SuppressLint("MissingSuperCall")
    public void onBackPressed() {
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
        finish();
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
                    String serialNumber = formattedDate + autoGeneratedNumberString;
                    etserialnumber.setText(serialNumber);
                    etserialnumber.setEnabled(true);
                } else {
                    // Handle the error
                    String serialNumber =  formattedDate + "001";
                    etserialnumber.setText(serialNumber);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                String serialNumber =  formattedDate + "001";
                etserialnumber.setText(serialNumber);
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
                Toasty.error(Inward_Truck_Security.this, "failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void Truckgridclick(View view) {
        Intent intent = new Intent(this, grid.class);
        startActivity(intent);
    }

    public void truckseccompletedclick(View view) {
        Intent intent = new Intent(this, ir_in_sec_Completedgrid.class);
        startActivity(intent);
    }
}