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
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.gandharvms.FcmNotificationsSender;
import com.android.gandharvms.Global_Var;
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
import com.android.gandharvms.LoginWithAPI.LoginMethod;
import com.android.gandharvms.LoginWithAPI.ResponseModel;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.Menu;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

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

public class Inward_Truck_Security extends AppCompatActivity {

    final Calendar calendar = Calendar.getInstance();
    private final int MAX_LENGTH = 10;
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
            etsmaterial, etsqty, etsuom, etsnetwt, etsuom2, etregister, repremark, etmobile, etoapo,etremark;
    Button wesubmit;
    Button view;
    FirebaseFirestore intrsdbroot;
    public LoginMethod getmaxserialno;
    DatePickerDialog picker;
    TimePickerDialog tpicker;
    FirebaseStorage storage;
    ArrayAdapter<String> registeritem;
    Button saveButton, button1;
    CheckBox cbox;
    String DocId = "";
    Date currentDate = Calendar.getInstance().getTime();
    //uom
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://gandharvms-default-rtdb.firebaseio.com/");
    private SharedPreferences sharedPreferences;
    private int autoGeneratedNumber;
    private CheckBox isReportingCheckBox;
    private EditText reportingRemarkLayout;
    private final String dateTimeString = "";
    private String token;
    private API_In_Tanker_Security apiInTankerSecurity;
    private int inwardid;
    private String vehicleType= Global_Var.getInstance().MenuType;
    private char nextProcess=Global_Var.getInstance().DeptType;
    private char inOut=Global_Var.getInstance().InOutType;
    private String EmployeName=Global_Var.getInstance().Name;
    private String EmployeId=Global_Var.getInstance().EmpId;
    Integer qtyUomNumericValue = 1;
    Integer netweuomvalue = 1;
    Map<String, Integer> qtyUomMappingintruck= new HashMap<>();

    AutoCompleteTextView  autoCompleteTextView1, autoCompleteTextView2;
    Map<String, Integer> qtyUomMapping= new HashMap<>();
    private int InwardId;
    private LoginMethod userDetails;





//    String lrCopySelection = lrcopyYes.isChecked() ? "Yes" : "No";
//    String deliverySelection = deliveryYes.isChecked() ? "Yes" : "No";
//    String taxInvoiceSelection = taxinvoiceYes.isChecked() ? "Yes" : "No";
//    String ewayBillSelection = ewaybillYes.isChecked() ? "Yes" : "No";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inward_truck_security);

        getmaxserialno=RetroApiClient.getLoginApi();
        userDetails = RetroApiClient.getLoginApi();

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


        //Send Notification to all
        FirebaseMessaging.getInstance().subscribeToTopic("dQlMFtOqRnWEMM0zYYRJ7S:APA91bGy88WuHE94r7rbUGAiKRyRDKFbCHD1WAX0HICYSVUJEhb8_pLQPS0tDeThddTrnY_n5C3tqlobYFEpgOI0ZB5U-ob7YmfGWY40WEcOqpTCZskh-QSrzJ45DORulOLX5OT0cmE7");

        regAutoCompleteTextView = findViewById(R.id.etregister);
        registeritem = new ArrayAdapter<String>(this, R.layout.security_registerlist, regitems);
        regAutoCompleteTextView.setAdapter(registeritem);
        regAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String items = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Item:- " + items + " Selected", Toast.LENGTH_SHORT).show();
            }
        });
        // Uom work
//        autoCompleteTextView = findViewById(R.id.etsuom);
//        autoCompleteTextView1 = findViewById(R.id.etsuom2);
////
//        adapterItems1 = new ArrayAdapter<String>(this, R.layout.list_itemuom, items);
//        adapterItems2 = new ArrayAdapter<String>(this, R.layout.in_tr_se_nwe_list, items1);
////
//        autoCompleteTextView.setAdapter(adapterItems1);
//        autoCompleteTextView1.setAdapter(adapterItems2);

        // for Auto Genrated serial number
        sharedPreferences = getSharedPreferences("TruckSecurity", MODE_PRIVATE);
//        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String items = parent.getItemAtPosition(position).toString();
//                Toast.makeText(getApplicationContext(), "Item :- " + items + " Selected", Toast.LENGTH_SHORT).show();
//            }
//        });

        autoCompleteTextView1 = findViewById(R.id.etsuom);
        qtyUomMapping= new HashMap<>();
        qtyUomMapping.put("NA",1);
        qtyUomMapping.put("Ton", 2);
        qtyUomMapping.put("Litre", 3);
        qtyUomMapping.put("KL", 4);
        qtyUomMapping.put("Kgs", 5);
        qtyUomMapping.put("pcs", 6);

        qtyuomdrop= new ArrayAdapter<String>(this, R.layout.in_ta_se_qty,new ArrayList<>(qtyUomMapping.keySet()));
        autoCompleteTextView1.setAdapter(qtyuomdrop);
        autoCompleteTextView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String qtyUomDisplay = parent.getItemAtPosition(position).toString();
                // Retrieve the corresponding numerical value from the mapping
                qtyUomNumericValue = qtyUomMapping.get(qtyUomDisplay);
                if (qtyUomNumericValue != null) {
                    // Now, you can use qtyUomNumericValue when inserting into the database

                    Toast.makeText(Inward_Truck_Security.this, "qtyUomNumericValue : " + qtyUomNumericValue + " Selected", Toast.LENGTH_SHORT).show();
                } else {
                    // Handle the case where the mapping doesn't contain the display value
                    Toast.makeText(Inward_Truck_Security.this, "Unknown qtyUom : " + qtyUomDisplay, Toast.LENGTH_SHORT).show();
                }
            }
        });
        autoCompleteTextView2 = findViewById(R.id.etsuom2);
        netweuomdrop = new ArrayAdapter<String>(this,R.layout.in_tr_se_nwe_list,new ArrayList<>(qtyUomMapping.keySet()));
        autoCompleteTextView2.setAdapter(netweuomdrop);
        autoCompleteTextView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item2 = parent.getItemAtPosition(position).toString();
                netweuomvalue = qtyUomMapping.get(item2);
                if (netweuomvalue != null){
                    Toast.makeText(Inward_Truck_Security.this, "netwe: " + item2 + " Selected", Toast.LENGTH_SHORT).show();

                }
                else {
                    Toast.makeText(Inward_Truck_Security.this, "Unknown qtyUom : " + item2, Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(getApplicationContext(), "Item :- " + items1 + " Selected", Toast.LENGTH_SHORT).show();
            }
        });


        etintime = findViewById(R.id.etintime);
        etserialnumber = findViewById(R.id.etserialnumber);
        etvehicalnumber = findViewById(R.id.etvehicalnumber);
        etsinvocieno = findViewById(R.id.etsinvocieno);
        etsdate = findViewById(R.id.etsdate);
        etssupplier = findViewById(R.id.etssupplier);
        etsmaterial = findViewById(R.id.etsmaterial);
        etsqty = findViewById(R.id.etsqty);
        etsuom = findViewById(R.id.etsuom);
        etsnetwt = findViewById(R.id.etsnetwt);
        etsuom2 = findViewById(R.id.etsuom2);
        etregister = findViewById(R.id.etregister);
        etmobile = findViewById(R.id.etmob);
        etoapo = findViewById(R.id.etoapo);
        etremark = findViewById(R.id.edtremark);


        //for add material

        linearLayout = findViewById(R.id.layout_list);
        button1 = findViewById(R.id.button_add);
        button1.setOnClickListener(this::onClick);

        teamList.add("Ton");
        teamList.add("Litre");
        teamList.add("KL");
        teamList.add("Kgs");
        teamList.add("Pcs");

        repremark = findViewById(R.id.edtreportingremark);
        cbox = findViewById(R.id.isreporting);

        lrcopyYes = findViewById(R.id.rb_LRCopyYes);
        lrcopyNo = findViewById(R.id.rb_LRCopyNo);
        deliveryYes = findViewById(R.id.rb_DeliveryYes);
        deliveryNo = findViewById(R.id.rb_DeliveryNo);
        taxinvoiceYes = findViewById(R.id.rb_TaxInvoiceYes);
        taxinvoiceNo = findViewById(R.id.rb_TaxInvoiceNo);
        ewaybillYes = findViewById(R.id.rb_EwaybillYes);
        ewaybillNo = findViewById(R.id.rb_EwaybillNo);

        storage = FirebaseStorage.getInstance();
//      for imgpicker

        // listing button
        view = findViewById(R.id.viewdb);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Inward_Truck_Security.this, Inward_Truck_Security_viewdata.class));
            }
        });
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
                        etsdate.setText(dateFormat.format(calendar.getTime()));
                    }
                }, year, month, day);
                picker.show();
            }
        });

        if (getIntent().hasExtra("VehicleNumber")) {
            FetchVehicleDetails(getIntent().getStringExtra("VehicleNumber"), Global_Var.getInstance().MenuType, 'S', 'I');
        }
        etintime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hours = calendar.get(Calendar.HOUR_OF_DAY);
                int mins = calendar.get(Calendar.MINUTE);
                tpicker = new TimePickerDialog(Inward_Truck_Security.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        c.set(Calendar.MINUTE, minute);
                        etintime.setText(hourOfDay + ":" + minute);
                    }
                }, hours, mins, false);
                tpicker.show();
            }
        });

        wesubmit = findViewById(R.id.wesubmit);
        intrsdbroot = FirebaseFirestore.getInstance();

        wesubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isReportingCheckBox = findViewById(R.id.isreporting);
                if (isReportingCheckBox.isChecked()) {
                    updateData();
                } else {
                    trsedata();
                }
            }
        });

        saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DocId == "") {
                    insertreporting();
                } else {
                    Toast.makeText(Inward_Truck_Security.this, "Record Already exist", Toast.LENGTH_SHORT).show();
                }
            }
        });

        etvehicalnumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    FetchVehicleDetails(etvehicalnumber.getText().toString().trim(),vehicleType,nextProcess,inOut);
                }
            }
        });

        // AUTO GENRATED SERIAL NUMBER
        String dateFormatPattern = "ddMMyyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatPattern, Locale.getDefault());
        String formattedDate = dateFormat.format(currentDate);

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
            GetMaxSerialNo(formattedDate);
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
//        databaseReference = FirebaseDatabase.getInstance().getReference("users");
//        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    // Assume you have a user role to identify the specific role
//                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
//                        String specificRole = "Weighment";
//                        // Get the value of the "role" node                    ;
//                        if (issue.toString().contains(specificRole)) {
//                            //getting the token
//                            token = Objects.requireNonNull(issue.child("token").getValue()).toString();
//                            FcmNotificationsSender notificationsSender = new FcmNotificationsSender(token,
//                                    "Inward Truck Security Process Done..!",
//                                    "Vehicle Number:-" + vehicleNumber + " has completed Security process at " + outTime,
//                                    getApplicationContext(), Inward_Truck_Security.this);
//                            notificationsSender.SendNotifications();
//                        }
//                    }
//                } else {
//                    // Handle the case when the "role" node doesn't exist
//                    Log.d("Role Data", "Role node doesn't exist");
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                // Handle errors here
//                Log.e("Firebase", "Error fetching role data: " + databaseError.getMessage());
//            }
//        });
        Call<List<ResponseModel>> call = userDetails.getUsersListData();
        call.enqueue(new Callback<List<ResponseModel>>() {
            @Override
            public void onResponse(Call<List<ResponseModel>> call, Response<List<ResponseModel>> response) {
                if (response.isSuccessful()){
                    List<ResponseModel> userList = response.body();
                    if (userList != null){
                        for (ResponseModel responseModel : userList) {
                            String specificrole = "Weighment";
                            if (specificrole.equals(responseModel.getDepartment())){
                                token = responseModel.getToken();
                                FcmNotificationsSender notificationsSender = new FcmNotificationsSender(
                                        token,
                                        "Inward Tanker Weightment Process Done..!",
                                        "Vehicle Number:-" + vehicleNumber + " has completed Production process at " + outTime,
                                        getApplicationContext(),
                                        Inward_Truck_Security.this
                                );
                                notificationsSender.SendNotifications();
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ResponseModel>> call, Throwable t) {

            }
        });
    }

    public void trsedata() {

//        String register = etregister.getText().toString().trim();
//        String intime = etintime.getText().toString().trim();
//        String serialnumber = etserialnumber.getText().toString().trim();
//        String vehicalnumber = etvehicalnumber.getText().toString().trim();
//        String invoiceno = etsinvocieno.getText().toString().trim();
//        String date = etsdate.getText().toString().trim();
//        String supplier = etssupplier.getText().toString().trim();
//        String material = etsmaterial.getText().toString().trim();
//        String qty = etsqty.getText().toString().trim();
//        String uom = etsuom.getText().toString().trim();
//        String netwt = etsnetwt.getText().toString().trim();
//        String uom2 = etsuom2.getText().toString().trim();
//        String outTime = getCurrentTime();
//        String lrCopySelection = lrcopyYes.isChecked() ? "Yes" : "No";
//        String deliverySelection = deliveryYes.isChecked() ? "Yes" : "No";
//        String taxInvoiceSelection = taxinvoiceYes.isChecked() ? "Yes" : "No";
//        String ewayBillSelection = ewaybillYes.isChecked() ? "Yes" : "No";
//        String mobile = etmobile.getText().toString().trim();
//        String oapo = etoapo.getText().toString().trim();
//
//
//        if (intime.isEmpty() || serialnumber.isEmpty() || vehicalnumber.isEmpty() || invoiceno.isEmpty() || date.isEmpty() || supplier.isEmpty() || material.isEmpty()
//                || qty.isEmpty() || uom.isEmpty() || netwt.isEmpty() || uom2.isEmpty() || register.isEmpty() || mobile.isEmpty() || oapo.isEmpty()) {
//            Toasty.warning(this, "All fields must be filled", Toast.LENGTH_SHORT,true).show();
//        } else {
//
//
//            List<Map<String, String>> materialList = new ArrayList<>();
//
//            for (int i = 0; i < linearLayout.getChildCount(); i++) {
//                View childView = linearLayout.getChildAt(i);
//                if (childView != null) {
//                    EditText materialEditText = childView.findViewById(R.id.editmaterial);
//                    EditText qtyEditText = childView.findViewById(R.id.editqty);
//                    AppCompatSpinner uomSpinner = childView.findViewById(R.id.spinner_team);
//
//                    String dynamaterial = materialEditText.getText().toString().trim();
//                    String dynaqty = qtyEditText.getText().toString().trim();
//                    String dynaqtyuom = uomSpinner.getSelectedItem().toString();
//
//                    // Check if both material and quantity fields are not empty
//                    if (!dynamaterial.isEmpty() && !dynaqty.isEmpty() && !dynaqtyuom.isEmpty()) {
//                        Map<String, String> materialMap = new HashMap<>();
//                        materialMap.put("material", dynamaterial);
//                        materialMap.put("qty", dynaqty);
//                        materialMap.put("qtyuom", dynaqtyuom);
//                        // Add material data to the list
//                        materialList.add(materialMap);
//                    }
//                }
//            }
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
//
//            Timestamp timestamp = new Timestamp(calendar.getTime());
//            Map<String, Object> trseitems = new HashMap<>();
//            trseitems.put("Intime", etintime.getText().toString().trim());
//            trseitems.put("serialnumber", etserialnumber.getText().toString().trim());
//            trseitems.put("VehicalNumber", etvehicalnumber.getText().toString().trim());
//            trseitems.put("invoicenumber", etsinvocieno.getText().toString().trim());
//            trseitems.put("date", timestamp);
//            trseitems.put("Supplier", etssupplier.getText().toString().trim());
//            trseitems.put("Material", etsmaterial.getText().toString().trim());
//            trseitems.put("Qty", etsqty.getText().toString().trim());
//            trseitems.put("UOM", etsuom.getText().toString().trim());
//            trseitems.put("etsnetweight", etsnetwt.getText().toString().trim());
//            trseitems.put("UOM2", etsuom2.getText().toString().trim());
//            trseitems.put("extramaterials", materialList.toString().replace("[]", ""));
//            trseitems.put("Driver_Mobile_Number", etmobile.getText().toString().trim());
//            trseitems.put("OA_PO_Number", etoapo.getText().toString().trim());
//
//            if (cbox.isChecked()) {
//                trseitems.put("outTime", "");
//            } else {
//                trseitems.put("outTime", outTime);
//            }
//            trseitems.put("SelectRegister", etregister.getText().toString().trim());
//            trseitems.put("lrcopy", lrCopySelection);
//            trseitems.put("deliverybill", deliverySelection);
//            trseitems.put("taxinvoice", taxInvoiceSelection);
//            trseitems.put("ewaybill", ewayBillSelection);
//
//            String rpremark = "";
//            int isreporting = 0;
//
//            if (cbox.isChecked()) {
//                rpremark = repremark.getText().toString().trim();
//                isreporting = 1;
//            }
//            trseitems.put("Is_Reporting", String.valueOf(isreporting));
//            trseitems.put("Reporting_Remark", rpremark);
//
//            makeNotification(etvehicalnumber.getText().toString(), outTime);
//            intrsdbroot.collection("Inward Truck Security").add(trseitems)
//                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
//                        @Override
//                        public void onComplete(@NonNull Task<DocumentReference> task) {
//                            etintime.setText("");
//                            etserialnumber.setText("");
//                            etvehicalnumber.setText("");
//                            etsinvocieno.setText("");
//                            etsdate.setText("");
//                            etssupplier.setText("");
//                            etsmaterial.setText("");
//                            etsqty.setText("");
//                            etsuom.setText("");
//                            etsnetwt.setText("");
//                            etsuom2.setText("");
//                            etregister.setText("");
//
//                            Toasty.success(Inward_Truck_Security.this, "Data Inserted Successfully", Toast.LENGTH_SHORT,true).show();
//                        }
//                    });
//
//            Intent intent = new Intent(this, Inward_Truck.class);
//            startActivity(intent);
//        }
        String serialnumber = etserialnumber.getText().toString().trim();
        String vehicalnumber = etvehicalnumber.getText().toString().trim();
        String invoicenumber = etsinvocieno.getText().toString().trim();
        String Date = etsdate.getText().toString().trim();
        String partyname = etssupplier.getText().toString().trim();
        String material = etsmaterial.getText().toString().trim();
        int qty = Integer.parseInt(etsqty.getText().toString().trim());
        int netweight = Integer.parseInt(etsnetwt.getText().toString().trim());
        int netweuom = Integer.parseInt(netweuomvalue.toString().trim());
        String intime = etintime.getText().toString().trim();
        String outTime = getCurrentTime();//Insert out Time Directly to the Database
        int qtyuom = Integer.parseInt( qtyUomNumericValue.toString().trim());
        String vehicltype= Global_Var.getInstance().MenuType;
        char InOutType = Global_Var.getInstance().InOutType;
        char DeptType= Global_Var.getInstance().DeptType;

        String lrCopySelection = lrcopyYes.isChecked() ? "Yes" : "No";
        String deliverySelection = deliveryYes.isChecked() ? "Yes" : "No";
        String taxInvoiceSelection = taxinvoiceYes.isChecked() ? "Yes" : "No";
        String ewayBillSelection = ewaybillYes.isChecked() ? "Yes" : "No";

        String remark = etremark.getText().toString().trim();
        String pooa = etoapo.getText().toString().trim();
        String mobnumber = etmobile.getText().toString().trim();
        String edremark = repremark.getText().toString().trim();
        String selectregister = etregister.getText().toString().trim();
        if (vehicalnumber.isEmpty() || invoicenumber.isEmpty() || Date.isEmpty() || partyname.isEmpty() ||
                intime.isEmpty() || material.isEmpty()) {
            Toasty.warning(this, "All fields must be filled", Toast.LENGTH_SHORT,true).show();
        } else {

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
            Request_Model_In_Tanker_Security requestModelInTankerSecurity = new Request_Model_In_Tanker_Security(serialnumber,invoicenumber,vehicalnumber,Date,partyname,material,pooa,mobnumber,'W','I',Date,
                    "",vehicltype,intime,outTime,qtyuom,netweuom,netweight,qty,materialList.toString().replace("[]", ""),remark,false,"No",selectregister,lrCopySelection,deliverySelection,taxInvoiceSelection,ewayBillSelection,EmployeId,"",InwardId);

            apiInTankerSecurity = RetroApiclient_In_Tanker_Security.getinsecurityApi();
            Call<Boolean> call =  apiInTankerSecurity.postData(requestModelInTankerSecurity);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body() != null && response.body()==true){
                        makeNotification(vehicalnumber, outTime);
                        Toast.makeText(Inward_Truck_Security.this, "Inserted Succesfully !", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Inward_Truck_Security.this, Inward_Truck.class));
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
                    Toast.makeText(Inward_Truck_Security.this, "failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void insertreporting() {
//        String register = "";
//        String intime = "";
//        String serialnumber = etserialnumber.getText().toString().trim();
//        String vehicalnumber = etvehicalnumber.getText().toString().trim();
//        String date = etsdate.getText().toString().trim();
//        String invoiceno = "";
//        String supplier = "";
//        String material = "";
//        String qty = "";
//        String uom = "";
//        String netwt = "";
//        String uom2 = "";
//        String outTime = "";
//
//
//        if (vehicalnumber.isEmpty() || date.isEmpty()) {
//            Toasty.warning(this, "All fields must be filled", Toast.LENGTH_SHORT,true).show();
//        } else {
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
//            Timestamp timestamp = new Timestamp(calendar.getTime());
//            Map<String, Object> trseitems = new HashMap<>();
//
//            trseitems.put("date", timestamp);
//            trseitems.put("serialnumber", etserialnumber.getText().toString().trim());
//            trseitems.put("VehicalNumber", etvehicalnumber.getText().toString().trim());
//            String rpremark = "";
//            int isreporting = 0;
//            if (cbox.isChecked()) {
//                rpremark = repremark.getText().toString().trim();
//                isreporting = 1;
//            }
//            trseitems.put("IS_Reporting", String.valueOf(isreporting));
//            trseitems.put("Reporting_Remark", rpremark);
//            trseitems.put("Intime", etintime.getText().toString().trim());
//            trseitems.put("invoicenumber", etsinvocieno.getText().toString().trim());
//            trseitems.put("Supplier", etssupplier.getText().toString().trim());
//            trseitems.put("Material", etsmaterial.getText().toString().trim());
//            trseitems.put("Qty", etsqty.getText().toString().trim());
//            trseitems.put("UOM", etsuom.getText().toString().trim());
//            trseitems.put("etsnetweight", etsnetwt.getText().toString().trim());
//            trseitems.put("UOM2", etsuom2.getText().toString().trim());
//            trseitems.put("lrcopy", "");
//            trseitems.put("delivery", "");
//            trseitems.put("taxinvoice", "");
//            trseitems.put("ewaybill", "");
//            trseitems.put("Driver_Mobile_Number", "");
//            trseitems.put("OA_PO_Number", "");
//
//
//            intrsdbroot.collection("Inward Truck Security").add(trseitems)
//                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
//                        @Override
//                        public void onComplete(@NonNull Task<DocumentReference> task) {
//                            etintime.setText("");
//                            etserialnumber.setText("");
//                            etvehicalnumber.setText("");
//                            etsinvocieno.setText("");
//                            etsdate.setText("");
//                            etssupplier.setText("");
//                            etsmaterial.setText("");
//                            etsqty.setText("");
//                            etsuom.setText("");
//                            etsnetwt.setText("");
//                            etsuom2.setText("");
//                            etmobile.setText("");
//                            etoapo.setText("");
//                            Toasty.success(getApplicationContext(), "Reporting Remark Inserted Successfully", Toast.LENGTH_SHORT,true).show();
//                        }
//                    });
//            Intent intent = new Intent(this, Inward_Truck.class);
//            startActivity(intent);
//        }
        String serialnumber = etserialnumber.getText().toString().trim();
        String vehicalnumber = etvehicalnumber.getText().toString().trim();
        String invoicenumber ="";
        String Date = etsdate.getText().toString().trim();
        String partyname = "";
        String material = "";
        int qty = 0;
        int netweight =0;
        String intime = "";
        String outTime = "";//Insert out Time Directly to the Database
        int qtyuom = 2;

        String vehicltype= Global_Var.getInstance().MenuType;
        char InOutType = Global_Var.getInstance().InOutType;
        char DeptType= Global_Var.getInstance().DeptType;
        int netweuom = 1;
        String remark = "";
        String pooa = "";
        String mobnumber = "";
        String edremark = "";
        Boolean isreporting = false ;
        if (cbox.isChecked()) {
            edremark = repremark.getText().toString().trim();
            isreporting = true;
        }
        if (vehicalnumber.isEmpty() ||  Date.isEmpty()) {
            Toasty.warning(this, "All fields must be filled", Toast.LENGTH_SHORT,true).show();
        } else {
            Request_Model_In_Tanker_Security requestModelInTankerSecurity = new Request_Model_In_Tanker_Security(serialnumber,invoicenumber,vehicalnumber,Date,partyname,material,pooa,mobnumber,'S',InOutType,"",
                    "",vehicltype,intime,outTime,qtyuom,netweuom,netweight,qty,"",remark,isreporting,edremark,"","","","","",EmployeId,"",InwardId);

            apiInTankerSecurity = RetroApiclient_In_Tanker_Security.getinsecurityApi();
            Call<Boolean> call =  apiInTankerSecurity.postData(requestModelInTankerSecurity);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body() != null ){
                        Toast.makeText(Inward_Truck_Security.this, "Inserted Succesfully !", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(Inward_Truck_Security.this, "failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void FetchVehicleDetails(@NonNull String VehicleNo,String vehicltype,char DeptType,char InOutType) {
//        CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Inward Truck Security");
//        String searchText = VehicleNo.trim();
//        CollectionReference collectionReference1 = FirebaseFirestore.getInstance().collection("Inward Truck Security");
//        Query query = collectionReference.whereEqualTo("VehicalNumber", searchText);
//        Timestamp timestamp = new Timestamp(calendar.getTime());
//        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    int totalcount = task.getResult().size();
//                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
//                        In_Truck_security_list obj = documentSnapshot.toObject(In_Truck_security_list.class);
//                        if (totalcount > 0) {
//                            etserialnumber.setText(obj.getSerialnumber());
//                            etvehicalnumber.setText(obj.getVehicalNumber());
//                            repremark.setText(obj.getReporting_Remark());
//                            etsdate.setText(dateFormat.format(obj.getDate().toDate()));
//                            etsnetwt.setText(obj.getEtsnetweight());
//                            cbox.setChecked(true);
//                            cbox.setEnabled(false);
//                            saveButton.setVisibility(View.GONE);
//                            repremark.setEnabled(false);
//                            etregister.setEnabled(false);
//                            DocId = documentSnapshot.getId();
//                            etintime.requestFocus();
//                            etintime.callOnClick();
//                        }
//
//                    }
//                } else {
//                    Log.w("FirestoreData", "Error getting documents.", task.getException());
//                }
//            }
//        });
        Call<List<Respo_Model_In_Tanker_security>> call = RetroApiClient.getserccrityveh().GetIntankerSecurityByVehicle(VehicleNo,vehicltype,DeptType,InOutType);
        call.enqueue(new Callback<List<Respo_Model_In_Tanker_security>>() {
            @Override
            public void onResponse(Call<List<Respo_Model_In_Tanker_security>> call, Response<List<Respo_Model_In_Tanker_security>> response) {
                if (response.isSuccessful()){

                    if (response.body().size() > 0) {
                        List<Respo_Model_In_Tanker_security> Data = response.body();
                        Respo_Model_In_Tanker_security obj = (Respo_Model_In_Tanker_security) Data.get(0);
                        int intimelength = obj.getInTime().length();
                        InwardId=obj.getInwardId();
                        etintime.setText(obj.getInTime().substring(12,intimelength));
                        etserialnumber.setText(obj.getSerialNo());
                        etvehicalnumber.setText(obj.getVehicleNo());
                        repremark.setText(obj.getReportingRemark());
                        etsdate.setText(obj.getDate());
//                            etdate.setText(dateFormat.format(obj.getDate()));
                        etsnetwt.setText(String.valueOf(obj.getNetWeight()));
                        cbox.setChecked(true);
                        cbox.setEnabled(false);
                        saveButton.setVisibility(View.GONE);
                        repremark.setEnabled(false);
//                        etreg.setEnabled(false);
                        etsdate.setEnabled(false);
//                            DocId = document.getId();
//                        etqty.requestFocus();
//                        etqty.callOnClick();

                    }
                }else {
                    Log.e("Retrofit","Error"+ response.code());
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


    public void updateData() {
        String lrCopySelection = lrcopyYes.isChecked() ? "Yes" : "No";
        String deliverySelection = deliveryYes.isChecked() ? "Yes" : "No";
        String taxInvoiceSelection = taxinvoiceYes.isChecked() ? "Yes" : "No";
        String ewayBillSelection = ewaybillYes.isChecked() ? "Yes" : "No";
        String outTime = getCurrentTime();

//        if (DocId != "") {
//            Map<String, Object> updates = new HashMap<>();
//            updates.put("Intime", etintime.getText().toString().trim());
//            updates.put("invoicenumber", etsinvocieno.getText().toString().trim());
//            updates.put("lrcopy", lrCopySelection);
//            updates.put("deliverybill", deliverySelection);
//            updates.put("taxinvoice", taxInvoiceSelection);
//            updates.put("ewaybill", ewayBillSelection);
//            updates.put("SelectRegister", etregister.getText().toString().trim());
//            updates.put("Supplier", etssupplier.getText().toString().trim());
//            updates.put("Material", etsmaterial.getText().toString().trim());
//            updates.put("Qty", etsqty.getText().toString().trim());
//            updates.put("UOM", etsuom.getText().toString().trim());
//            updates.put("etsnetweight", etsnetwt.getText().toString().trim());
//            updates.put("UOM2", etsuom2.getText().toString().trim());
//            updates.put("Driver_Mobile_Number", etmobile.getText().toString().trim());
//            updates.put("OA_PO_Number", etoapo.getText().toString().trim());
//            makeNotification(etvehicalnumber.getText().toString(), outTime);
//            List<Map<String, String>> materialList = new ArrayList<>();
//            for (int i = 0; i < linearLayout.getChildCount(); i++) {
//                View childView = linearLayout.getChildAt(i);
//                if (childView != null) {
//                    EditText materialEditText = childView.findViewById(R.id.editmaterial);
//                    EditText qtyEditText = childView.findViewById(R.id.editqty);
//                    AppCompatSpinner uomSpinner = childView.findViewById(R.id.spinner_team);
//
//                    String dynamaterial = materialEditText.getText().toString().trim();
//                    String dynaqty = qtyEditText.getText().toString().trim();
//                    String dynaqtyuom = uomSpinner.getSelectedItem().toString();
//
//                    // Check if both material and quantity fields are not empty
//                    if (!dynamaterial.isEmpty() && !dynaqty.isEmpty() && !dynaqtyuom.isEmpty()) {
//                        Map<String, String> materialMap = new HashMap<>();
//                        materialMap.put("material", dynamaterial);
//                        materialMap.put("qty", dynaqty);
//                        materialMap.put("qtyuom", dynaqtyuom);
//                        // Add material data to the list
//                        materialList.add(materialMap);
//                    }
//                }
//            }
//
//            updates.put("extramaterials", materialList.toString().replace("[]", ""));
//            DocumentReference documentReference = intrsdbroot.collection("Inward Truck Security").document(DocId);
//            documentReference.update(updates)
//                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void unused) {
//                            etsdate.setText("");
//                            etserialnumber.setText("");
//                            etvehicalnumber.setText("");
//                            etintime.setText("");
//                            etsinvocieno.setText("");
//                            etregister.setText("");
//                            etssupplier.setText("");
//                            etsmaterial.setText("");
//                            etsqty.setText("");
//                            etsuom.setText("");
//                            etsnetwt.setText("");
//                            etsuom2.setText("");
//                            etmobile.setText("");
//                            etoapo.setText("");
//
//                            Toasty.success(Inward_Truck_Security.this, "Data Updated Successfully", Toast.LENGTH_SHORT,true).show();
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(Inward_Truck_Security.this, "Failed to update data", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//            Intent intent = new Intent(this, Inward_Truck.class);
//            startActivity(intent);
//
//        } else {
//            Toasty.warning(this, "Please Provide Vehicle no", Toast.LENGTH_SHORT,true).show();
//        }
        String serialnumber = etserialnumber.getText().toString().trim();
        String vehiclenumber = etvehicalnumber.getText().toString().trim();
        String Date = etsdate.getText().toString().trim();
        String intime = etintime.getText().toString().trim();
        String invoice = etsinvocieno.getText().toString().trim();
        String drivermobile = etmobile.getText().toString().trim();
        String party = etssupplier.getText().toString().trim();
        String material = etsmaterial.getText().toString().trim();
        String oapo = etoapo.getText().toString().trim();
        int netweight = Integer.parseInt(etsnetwt.getText().toString().trim());
        int netwtuom = Integer.parseInt(netweuomvalue.toString());
        int qty = Integer.parseInt(etsqty.getText().toString().trim());
        int qtyuom = Integer.parseInt(qtyUomNumericValue.toString().trim());
        String remark = etremark.getText().toString().trim();
        String etouttime = getCurrentTime();
        String vehicltype= Global_Var.getInstance().MenuType;
        char InOutType = Global_Var.getInstance().InOutType;
        char DeptType= Global_Var.getInstance().DeptType;

        String selectregister = etregister.getText().toString().trim();

        if ( invoice.isEmpty()||party.isEmpty()||material.isEmpty()||oapo.isEmpty()){
            Toasty.warning(this, "All fields must be filled", Toast.LENGTH_SHORT,true).show();
        }else {


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

           //  Update_Request_Model_Insequrity requestModelInTankerSecurityupdate = new Update_Request_Model_Insequrity(InwardId,serialnumber,invoice,vehiclenumber,Date,party,material,oapo,drivermobile,'W','I',intime,
            //                    "",vehicltype,intime,outTime,qty,qtyuom,netwtuom,netweight,materialList.toString().replace("[]", ""),remark,selectregister,lrCopySelection,deliverySelection,taxInvoiceSelection,ewayBillSelection,EmployeId);

            Update_Request_Model_Insequrity requestModelInTankerSecurityupdate = new Update_Request_Model_Insequrity(InwardId,serialnumber,invoice,vehiclenumber,Date,party,material,oapo,drivermobile,'W','I',intime,
                    "",vehicltype,intime,outTime,qtyuom,netwtuom,netweight,qty,materialList.toString().replace("[]", ""),remark,selectregister,lrCopySelection,deliverySelection,taxInvoiceSelection,ewayBillSelection,EmployeId);

            apiInTankerSecurity = RetroApiclient_In_Tanker_Security.getinsecurityApi();
            Call<Boolean> call = apiInTankerSecurity.updatesecuritydata(requestModelInTankerSecurityupdate);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body() != null){
                        Toast.makeText(Inward_Truck_Security.this, "Inserted Succesfully !", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(Inward_Truck_Security.this, "failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private String getCurrentTime() {
        // Get the current time
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(new Date());
    }

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
                if(response.isSuccessful())
                {
                    String maxSerialNumber = response.body();
                    autoGeneratedNumber = Integer.parseInt(maxSerialNumber.substring(10,13))+1;
                    @SuppressLint("DefaultLocale") String autoGeneratedNumberString = String.format("%03d", autoGeneratedNumber);
                    String serialNumber = "GA" + formattedDate + autoGeneratedNumberString;
                    etserialnumber.setText(serialNumber);
                    etserialnumber.setEnabled(true);
                }
                else {
                    // Handle the error
                    String serialNumber = "GA" + formattedDate + "001";
                    etserialnumber.setText(serialNumber);
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                String serialNumber = "GA" + formattedDate + "001";
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
                Toast.makeText(Inward_Truck_Security.this, "failed", Toast.LENGTH_SHORT).show();
                // Handle the failure

            }
        });
    }

    public void Truckgridclick(View view) {
        Intent intent = new Intent(this, grid.class);
        startActivity(intent);
    }
}