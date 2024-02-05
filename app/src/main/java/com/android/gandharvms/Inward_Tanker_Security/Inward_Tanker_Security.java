package com.android.gandharvms.Inward_Tanker_Security;



import androidx.annotation.NonNull;
import androidx.annotation.ReturnThis;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.android.gandharvms.Inward_Tanker;
import com.android.gandharvms.MainActivity;
import com.android.gandharvms.Menu;
import com.android.gandharvms.R;
import com.google.android.gms.common.server.converter.StringToIntConverter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.Timestamp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import es.dmoral.toasty.Toasty;


public class Inward_Tanker_Security extends AppCompatActivity implements View.OnClickListener {

    String [] items = {"Capital Register", "General Register","Inward Register"};

    String DocId = "";

    final Calendar calendar = Calendar.getInstance();
    //uom
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");

    String[] qtyuom = {"Ton", "Litre", "KL", "Kgs", "pcs", "NA"};
    String[] netweuom = {"Ton", "Litre", "KL", "Kgs", "pcs", "NA"};
    AutoCompleteTextView autoCompleteTextView, autoCompleteTextView1, autoCompleteTextView2;
    ArrayAdapter<String> registeritem;
    ArrayAdapter<String> qtyuomdrop;
    ArrayAdapter<String> netweuomdrop;
    EditText etreg, etvehical, etinvoice, etdate, etsupplier, etmaterial, etintime, etnetweight, etqty, etoum, etregister, etqtyoum, etnetoum, etremark, edpooa, etmobilenum, repremark;
    Button btnadd, button1, dbbutton;
    EditText editmaterial, editqty, edituom;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://gandharvms-default-rtdb.firebaseio.com/");
    FirebaseFirestore dbroot;
    DatePickerDialog picker;
    List<String> teamList = new ArrayList<>();
    LinearLayout linearLayout;
    AppCompatSpinner spinner;
    TimePickerDialog tpicker;
    Button saveButton;
    CheckBox cbox;
    private SharedPreferences sharedPreferences;
    private int autoGeneratedNumber;
    private CheckBox isReportingCheckBox;
    private EditText reportingRemarkLayout;
    Date currentDate = Calendar.getInstance().getTime();
    private String token;
    public int MAX_LENGTH=10;

    private String dateTimeString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inward_tanker_security);

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
        autoCompleteTextView1 = findViewById(R.id.qtyuom);
        qtyuomdrop = new ArrayAdapter<String>(this, R.layout.in_ta_se_qty, qtyuom);
        autoCompleteTextView1.setAdapter(qtyuomdrop);
        autoCompleteTextView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String qtyuom = parent.getItemAtPosition(position).toString();
                Toast.makeText(Inward_Tanker_Security.this, "qtyuom : " + qtyuom+" Selected", Toast.LENGTH_SHORT).show();
            }
        });

        autoCompleteTextView2 = findViewById(R.id.netweuom);
        netweuomdrop = new ArrayAdapter<String>(this, R.layout.in_ta_se_nw, netweuom);
        autoCompleteTextView2.setAdapter(netweuomdrop);

        autoCompleteTextView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String neweuom = parent.getItemAtPosition(position).toString();
                Toast.makeText(Inward_Tanker_Security.this, "netwe: " + neweuom + " Selected", Toast.LENGTH_SHORT).show();
            }
        });
        autoCompleteTextView1 = findViewById(R.id.qtyuom);
        qtyuomdrop = new ArrayAdapter<String>(this, R.layout.in_ta_se_qty, qtyuom);
        autoCompleteTextView1.setAdapter(qtyuomdrop);
        autoCompleteTextView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String qtyuom = parent.getItemAtPosition(position).toString();
                Toast.makeText(Inward_Tanker_Security.this, "qtyuom: " + qtyuom + " Selected", Toast.LENGTH_SHORT).show();
            }
        });

        etreg = findViewById(R.id.etserialnumber);
        etvehical = findViewById(R.id.etvehical);
        etinvoice = findViewById(R.id.etinvoice);
        etdate = findViewById(R.id.etdate);
        etsupplier = findViewById(R.id.etsupplier);
        etmaterial = findViewById(R.id.etmaterial);
        etintime = findViewById(R.id.etqty);
        etnetweight = findViewById(R.id.etnetweight);
        etqty = findViewById(R.id.etintime);
        etqtyoum = findViewById(R.id.qtyuom);
        etnetoum = findViewById(R.id.netweuom);

        etremark = findViewById(R.id.edtremark);
        edpooa = findViewById(R.id.etpooa);
        etmobilenum = findViewById(R.id.etcontactnumber);

        repremark = findViewById(R.id.edtreportingremark);
       
        cbox = (CheckBox) findViewById(R.id.isreporting);


        // for Auto Genrated serial number
        sharedPreferences = getSharedPreferences("VehicleManagementPrefs", MODE_PRIVATE);
        //
        linearLayout = findViewById(R.id.layout_list);
        button1 = findViewById(R.id.button_add);
        button1.setOnClickListener(this);
        dbbutton = findViewById(R.id.dbview);


        teamList.add("Ton");
        teamList.add("Litre");
        teamList.add("KL");
        teamList.add("Kgs");
        teamList.add("Pcs");

        //listdata button
        dbbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Inward_Tanker_Security.this, Inward_Tanker_Security_Viewdata.class));
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

        etqty.setOnClickListener(new View.OnClickListener() {
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
                        etqty.setText(hourOfDay + ":" + minute);
                    }
                }, hours, mins, false);
                tpicker.show();
            }
        });


        btnadd = (Button) findViewById(R.id.submit);
        dbroot = FirebaseFirestore.getInstance();
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

        saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertreporting();
            }
        });

        etvehical.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    FetchVehicleDetails(etvehical.getText().toString().trim());
                }
            }
        });

        String dateFormatPattern = "ddMMyy";
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

        EditText editText = (EditText) materialview.findViewById(R.id.editmaterial);
        EditText editqty = (EditText) materialview.findViewById(R.id.editqty);
        AppCompatSpinner spinner = (AppCompatSpinner) materialview.findViewById(R.id.spinner_team);
        ImageView img = (ImageView) materialview.findViewById(R.id.editcancel);

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

    public void makeNotification(String vehicleNumber,String outTime) {
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
                                    "Inward Tanker Security Process Done..!",
                                    "Vehicle Number:-" + vehicleNumber + " has completed security process at " + outTime,
                                    getApplicationContext(), Inward_Tanker_Security.this);
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
        String qty = etqty.getText().toString().trim();
        String netweight = etnetweight.getText().toString().trim();
        String intime = etintime.getText().toString().trim();
        String outTime = getCurrentTime();//Insert out Time Directly to the Database
        String qtyuom = etqtyoum.getText().toString().trim();
        String netweuom = etnetoum.getText().toString().trim();
        String remark = etremark.getText().toString().trim();
        String pooa = edpooa.getText().toString().trim();
        String mobnumber = etmobilenum.getText().toString().trim();
        if (vehicalnumber.isEmpty() || invoicenumber.isEmpty() || Date.isEmpty() || partyname.isEmpty() ||
                netweight.isEmpty() || intime.isEmpty() || material.isEmpty()) {
            Toasty.warning(this, "All fields must be filled", Toast.LENGTH_SHORT,true).show();
        } else {
            // Material data handling for dynamically added fields
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
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            Timestamp timestamp = new Timestamp(calendar.getTime());
            Map<String, Object> items = new HashMap<>();
            items.put("SerialNumber", etreg.getText().toString().trim());
            items.put("vehicalnumber", etvehical.getText().toString().trim());
            items.put("invoiceno", etinvoice.getText().toString().trim());
          //  items.put("date", etdate.getText().toString().trim());
            items.put("date",  timestamp);
            items.put("partyname", etsupplier.getText().toString());
            items.put("extramaterials", materialList.toString().replace("[]", ""));
            items.put("material", etmaterial.getText().toString().trim());
            items.put("qty", etintime.getText().toString().trim());
            items.put("netweight", etnetweight.getText().toString().trim());
            items.put("intime", etqty.getText().toString().trim());
            if (cbox.isChecked()) {
                items.put("outTime", "");
            } else {
                items.put("outTime", outTime);
            }
            items.put("qtyuom", etqtyoum.getText().toString().trim());
            items.put("netweightuom", etnetoum.getText().toString().trim());
            items.put("Remark", etremark.getText().toString().trim());
            items.put("OA_PO_Number", edpooa.getText().toString().trim());
            items.put("Driver_Mobile_No", etmobilenum.getText().toString().trim());

            String rpremark = "";
            int isreporting = 0;

            if (cbox.isChecked()) {
                rpremark = repremark.getText().toString().trim();
                isreporting = 1;
            }
            items.put("Is_Reporting", String.valueOf(isreporting));
            items.put("Reporting_Remark", rpremark);
            makeNotification(etvehical.getText().toString(), outTime);
            dbroot.collection("Inward Tanker Security").add(items)
                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            etvehical.setText("");
                            etinvoice.setText("");
                            etdate.setText("");
                            etsupplier.setText("");
                            etmaterial.setText("");
                            etintime.setText("");
                            etnetweight.setText("");
                            etqty.setText("");
                            etnetoum.setText("");
                            etqtyoum.setText("");
                            Toasty.success(getApplicationContext(), "Data Inserted Successfully", Toast.LENGTH_SHORT,true).show();
                        }
                    });
            Intent intent = new Intent(this, Inward_Tanker.class);
            startActivity(intent);
        }
    }

    public void insertreporting() {
        String serialnumber = etreg.getText().toString().trim();
        String vehicalnumber = etvehical.getText().toString().trim();
        String invoicenumber = "";
        String Date = etdate.getText().toString().trim();
        String partyname = "";
        String material = "";
        String qty = "";
        String netweight = "";
        String intime = "";
        String outTime = "";//Insert out Time Directly to the Database
        String qtyuom = "";
        String netweuom = "";
        String remark = "";
        String pooa = edpooa.getText().toString().trim();
        String mobnumber = etmobilenum.getText().toString().trim();
        if (vehicalnumber.isEmpty() || Date.isEmpty()) {
            Toasty.success(this, "All fields must be filled", Toast.LENGTH_SHORT,true).show();
        } else {
            // Material data handling for dynamically added fields
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            Timestamp timestamp = new Timestamp(calendar.getTime());
            Map<String, Object> items = new HashMap<>();
//            List<Map<String, String>> materialList = new ArrayList<>();

            items.put("SerialNumber", serialnumber);
            items.put("vehicalnumber", vehicalnumber);
            items.put("invoiceno", invoicenumber);
            items.put("date", timestamp);
            items.put("partyname", partyname);
            items.put("extramaterials", material);
            items.put("material", material);
            items.put("qty", qty);
            items.put("netweight", netweight);
            items.put("intime", intime);
            items.put("outTime", "");
            items.put("qtyuom", qtyuom);
            items.put("netweightuom", netweuom);
            items.put("Remark", remark);
            items.put("OA_PO_Number", pooa);
            items.put("Driver_Mobile_No", mobnumber);
            String rpremark = "";
            int isreporting = 0;
            if (cbox.isChecked()) {
                rpremark = repremark.getText().toString().trim();
                isreporting = 1;
            }
            items.put("Is_Reporting", String.valueOf(isreporting));
            items.put("Reporting_Remark", rpremark);
            dbroot.collection("Inward Tanker Security").add(items)
                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            etvehical.setText("");
                            etinvoice.setText("");
                            etdate.setText("");
                            etsupplier.setText("");
                            etmaterial.setText("");
                            etintime.setText("");
                            etnetweight.setText("");
                            etqty.setText("");
                            etnetoum.setText("");
                            etqtyoum.setText("");
                            Toasty.success(getApplicationContext(), "Data Inserted Successfully", Toast.LENGTH_SHORT,true).show();
                        }
                    });
            Intent intent = new Intent(this, Inward_Tanker.class);
            startActivity(intent);
        }
    }


    public void FetchVehicleDetails(@NonNull String VehicleNo) {
        CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Inward Tanker Security");
        String searchText = VehicleNo.trim();
        CollectionReference collectionReferenceWe = FirebaseFirestore.getInstance().collection("Inward Tanker Security");
        Query query = collectionReference.whereEqualTo("vehicalnumber", searchText);
        Timestamp timestamp = new Timestamp(calendar.getTime());
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    int totalCount = task.getResult().size();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        In_Tanker_Security_list obj = document.toObject(In_Tanker_Security_list.class);
                        // Check if the object already exists to avoid duplicates
                        if (totalCount > 0) {
//                            etint.setText(obj.In_Time);
                            etreg.setText(obj.getSerialNumber());
                            etvehical.setText(obj.getVehicalnumber());
                            repremark.setText(obj.getReporting_Remark());
                            etdate.setText(dateFormat.format(obj.getDate().toDate()));
                            etnetweight.setText(obj.getNetweight());
                            cbox.setChecked(true);
                            cbox.setEnabled(false);
                            saveButton.setVisibility(View.GONE);
                            repremark.setEnabled(false);
                            etreg.setEnabled(false);
                            etdate.setEnabled(false);
                            DocId = document.getId();
                            etqty.requestFocus();
                            etqty.callOnClick();

                        }
                    }
                } else {
                    Log.w("FirestoreData", "Error getting documents.", task.getException());
                }
            }
        });
    }

    public void GetMaxSerialNo(String formattedDate) {
        String serialNoPreFix = "GA" + formattedDate;
        CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Inward Tanker Security");
        String searchText = serialNoPreFix.trim();
        Query query = collectionReference
                .whereGreaterThanOrEqualTo("SerialNumber", searchText)
                .whereLessThanOrEqualTo("SerialNumber", searchText + "\uf8ff")
                .orderBy("SerialNumber", Query.Direction.DESCENDING)
                .limit(1);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    int totalCount = task.getResult().size();
                    if (totalCount > 0) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            In_Tanker_Security_list obj = document.toObject(In_Tanker_Security_list.class);
                            // Check if the object already exists to avoid duplicates
                            autoGeneratedNumber = Integer.parseInt(obj.getSerialNumber().substring(8, 11)) + 1;
                            //  sharedPreferences.edit().putInt("autoGeneratedNumber", autoGeneratedNumber).apply();
                            @SuppressLint("DefaultLocale") String autoGeneratedNumberString = String.format("%03d", autoGeneratedNumber);
                            // Create the serial number
                            String serialNumber = "GA" + formattedDate + autoGeneratedNumberString;
                            // Set the serial number in the EditText
                            etreg.setText(serialNumber);
                            etreg.setEnabled(true);
                        }
                    } else {
                        String serialNumber = "GA" + formattedDate + "001";
                        etreg.setText(serialNumber);
                    }
                }
            }
        });
        //    return DocId.toString();
    }    public void updateData() {
        //  String vehiclnumber = "0JTDOizXVgFrAuOeosCy";
        //etvehical.getText().toString().trim();
        String outTime=getCurrentTime();
        if (DocId != "") {
            Map<String, Object> updates = new HashMap<>();
            updates.put("intime", etqty.getText().toString().trim());
            updates.put("invoiceno", etinvoice.getText().toString().trim());
            updates.put("Driver_Mobile_No", etmobilenum.getText().toString().trim());
            updates.put("partyname", etsupplier.getText().toString().trim());
            updates.put("material", etmaterial.getText().toString().trim());
            updates.put("OA_PO_Number", edpooa.getText().toString().trim());
            updates.put("qty", etintime.getText().toString().trim());
            updates.put("qtyuom", etqtyoum.getText().toString().trim());
            updates.put("netweight", etnetweight.getText().toString().trim());
            updates.put("netweightuom", etnetoum.getText().toString().trim());
            updates.put("outTime",outTime);

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
            updates.put("extramaterials", materialList.toString().replace("[]", ""));
            makeNotification(etvehical.getText().toString(), outTime);
            DocumentReference documentReference = dbroot.collection("Inward Tanker Security").document(DocId);
            updates.put("Remark", etremark.getText().toString().trim());
            documentReference.update(updates)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            etvehical.setText("");
                            etinvoice.setText("");
                            etdate.setText("");
                            etsupplier.setText("");
                            etmaterial.setText("");
                            etintime.setText("");
                            etnetweight.setText("");
                            etqty.setText("");
                            etnetoum.setText("");
                            etqtyoum.setText("");
                            edpooa.setText("");
                            etmobilenum.setText("");
                            etremark.setText("");
                            Toasty.success(Inward_Tanker_Security.this, "Data Updated Successfully", Toast.LENGTH_SHORT,true).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toasty.error(Inward_Tanker_Security.this, "Failed to update data", Toast.LENGTH_SHORT,true).show();
                        }
                    });
        } else {
            Toasty.warning(Inward_Tanker_Security.this, "Please Provide Vehicle no", Toast.LENGTH_SHORT,true).show();
        }

}
public void gridclick(View view){
        Intent intent = new Intent(this,grid.class);
        startActivity(intent);
}

}

