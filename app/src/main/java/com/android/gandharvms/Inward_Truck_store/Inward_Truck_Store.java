package com.android.gandharvms.Inward_Truck_store;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

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
import com.android.gandharvms.Inward_Tanker_Security.Inward_Tanker_Security;
import com.android.gandharvms.Inward_Truck;
import com.android.gandharvms.Inward_Truck_Security.In_Truck_security_list;
import com.android.gandharvms.Inward_Truck_Weighment.In_Truck_weigment_list;
import com.android.gandharvms.Inward_Truck_Weighment.Inward_Truck_weighment;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.LoginWithAPI.Store;
import com.android.gandharvms.LoginWithAPI.Weighment;
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
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.Timestamp;

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

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class Inward_Truck_Store extends AppCompatActivity {

    private final int MAX_LENGTH = 10;
    /*String[] items = {"Ton", "Litre", "KL", "Kgs", "pcs", "NA"};
    String[] items1 = {"Ton", "Litre", "KL", "Kgs", "pcs", "NA"};*/
    /*AutoCompleteTextView autoCompleteTextView, autoCompleteTextView1;*/
    ArrayAdapter<String> adapterItems;
    ArrayAdapter<String> adapterItems1;
    EditText etintime, etserialnumber, etvehicalnum, etpo, etpodate, etmaterialrdate, etmaterial, etqty, etrecqtyoum, etremark, etinvqty, etinvdate, etinvnum, etinvqtyuom;
    Button trssubmit, buttonadd, button1;
    Button view;
    DatePickerDialog picker;
    FirebaseFirestore trsdbroot;
    TimePickerDialog intruckspicker;
    LinearLayout linearLayout;
    List<String> teamList = new ArrayList<>();

    List<String> teamList1 = new ArrayList<>();
    //prince
    private SharedPreferences sharedPreferences;
    private int autoGeneratedNumber;
    final Calendar calendar = Calendar.getInstance();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-YYYY, HH:mm:ss");
    Timestamp timestamp = new Timestamp(calendar.getTime());
    private String token;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://gandharvms-default-rtdb.firebaseio.com/");

    Integer qtyUomNumericValue = 1;
    Integer netweuomvalue = 1;
    String[] netweuom = {"Ton", "Litre", "KL", "Kgs", "pcs", "NA"};
    AutoCompleteTextView autoCompleteTextViewINVUOM, autoCompleteTextViewRecQTYUOM;
    ArrayAdapter<String> qtyuomdrop;
    ArrayAdapter<String> netweuomdrop;
    Map<String, Integer> qtyUomMapping= new HashMap<>();
    private String vehicleType= Global_Var.getInstance().MenuType;
    private char nextProcess=Global_Var.getInstance().DeptType;
    private char inOut=Global_Var.getInstance().InOutType;
    private String EmployeId=Global_Var.getInstance().EmpId;
    private int inwardid;
    private Store storedetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inward_truck_store);

        //Send Notification to all
        //Send Notification to all
        FirebaseMessaging.getInstance().subscribeToTopic(token);

        storedetails= RetroApiClient.getStoreDetails();

        //prince
        //Send Notifications To All
        sharedPreferences = getSharedPreferences("TruckStore", MODE_PRIVATE);


        //for recqtyuom
        autoCompleteTextViewRecQTYUOM = findViewById(R.id.recqtysuom);
        qtyUomMapping= new HashMap<>();
        qtyUomMapping.put("NA",1);
        qtyUomMapping.put("Ton", 2);
        qtyUomMapping.put("Litre", 3);
        qtyUomMapping.put("KL", 4);
        qtyUomMapping.put("Kgs", 5);
        qtyUomMapping.put("pcs", 6);

        qtyuomdrop = new ArrayAdapter<String>(this, R.layout.in_ta_se_qty,new ArrayList<>(qtyUomMapping.keySet()));
        autoCompleteTextViewRecQTYUOM.setAdapter(qtyuomdrop);
        autoCompleteTextViewRecQTYUOM.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String qtyUomDisplay = parent.getItemAtPosition(position).toString();
                // Retrieve the corresponding numerical value from the mapping
                qtyUomNumericValue = qtyUomMapping.get(qtyUomDisplay);
                if (qtyUomNumericValue != null) {
                    // Now, you can use qtyUomNumericValue when inserting into the database
                    Toast.makeText(Inward_Truck_Store.this, "RecQty : " + qtyUomNumericValue + " Selected", Toast.LENGTH_SHORT).show();
                } else {
                    // Handle the case where the mapping doesn't contain the display value
                    Toast.makeText(Inward_Truck_Store.this, "Unknown qtyUom : " + qtyUomDisplay, Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (getIntent().hasExtra("VehicleNumber")) {
            FetchVehicleDetails(getIntent().getStringExtra("VehicleNumber"), Global_Var.getInstance().MenuType, 'R', 'I');
        }
        autoCompleteTextViewINVUOM = findViewById(R.id.etinvqtysuom);
        netweuomdrop = new ArrayAdapter<String>(this, R.layout.in_ta_se_nw,new ArrayList<>(qtyUomMapping.keySet()));
        autoCompleteTextViewINVUOM.setAdapter(netweuomdrop);
        autoCompleteTextViewINVUOM.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String neweuom = parent.getItemAtPosition(position).toString();
                netweuomvalue = qtyUomMapping.get(neweuom);
                if (qtyUomNumericValue != null){
                    Toast.makeText(Inward_Truck_Store.this, "Invoice QTY: " + neweuom + " Selected", Toast.LENGTH_SHORT).show();

                }
                else {
                    Toast.makeText(Inward_Truck_Store.this, "Unknown qtyUom : " + netweuom, Toast.LENGTH_SHORT).show();
                }
            }
        });
        /*autoCompleteTextView = findViewById(R.id.etsuom);
        adapterItems = new ArrayAdapter<String>(this, R.layout.list_itemuom, items);
        autoCompleteTextView.setAdapter(adapterItems);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String items = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Item: " + items + " Selected", Toast.LENGTH_SHORT).show();
            }
        });

        //for invqtyuom
        autoCompleteTextView1 = findViewById(R.id.etinvqtysuom);
        adapterItems1 = new ArrayAdapter<String>(this, R.layout.list_itemuom, items1);
        autoCompleteTextView1.setAdapter(adapterItems1);
        autoCompleteTextView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String items = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Item: " + items + " Selected", Toast.LENGTH_SHORT).show();
            }
        });*/


        etintime = (EditText) findViewById(R.id.etintime);
        etserialnumber = (EditText) findViewById(R.id.ettrsserialnumber);
        etvehicalnum = (EditText) findViewById(R.id.ettrsvehical);
        etpo = (EditText) findViewById(R.id.ettrsponumber);
        etpodate = (EditText) findViewById(R.id.ettrpodate);
        etmaterialrdate = (EditText) findViewById(R.id.materialrecievingdate);
        etmaterial = (EditText) findViewById(R.id.ettsmaterial);
        etqty = (EditText) findViewById(R.id.etsqty);
        etrecqtyoum = (EditText) findViewById(R.id.recqtysuom);
        etinvqtyuom = (EditText) findViewById(R.id.etinvqtysuom);
        etremark = (EditText) findViewById(R.id.etremark);
        etinvqty = (EditText) findViewById(R.id.ettrinvqty);
        etinvdate = (EditText) findViewById(R.id.ettrinvDate);
        etinvnum = (EditText) findViewById(R.id.ettinvnumber);


        linearLayout = findViewById(R.id.layout_list);
        button1 = findViewById(R.id.button_add);
        button1.setOnClickListener(this::onClick);

        teamList.add("Ton");
        teamList.add("Litre");
        teamList.add("KL");
        teamList.add("Kgs");
        teamList.add("Pcs");
        teamList.add("NA");


        //listdata
        view = findViewById(R.id.viewclick);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Inward_Truck_Store.this, Inward_Truck_Store_viewdata.class));
            }
        });

        etintime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hours = calendar.get(Calendar.HOUR_OF_DAY);
                int mins = calendar.get(Calendar.MINUTE);


                intruckspicker = new TimePickerDialog(Inward_Truck_Store.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        c.set(Calendar.MINUTE, minute);
                        etintime.setText(hourOfDay + ":" + minute);
                    }
                }, hours, mins, false);
                intruckspicker.show();
            }
        });

        etintime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hours = calendar.get(Calendar.HOUR_OF_DAY);
                int mins = calendar.get(Calendar.MINUTE);

                intruckspicker = new TimePickerDialog(Inward_Truck_Store.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        c.set(Calendar.MINUTE, minute);
                        etintime.setText(hourOfDay + ":" + minute);
                    }
                }, hours, mins, false);
                intruckspicker.show();
            }
        });


        //date
        etpodate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                // Array of month abbreviations
                String[] monthAbbreviations = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
                picker = new DatePickerDialog(Inward_Truck_Store.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Use the month abbreviation from the array
                        String monthAbbreviation = monthAbbreviations[month];
                        // etdate.setText(dayOfMonth + "/" + monthAbbreviation + "/" + year);
                        etpodate.setText(dateFormat.format(calendar.getTime()));
                    }
                }, year, month, day);
                picker.show();
            }
        });
        etvehicalnum.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    FetchVehicleDetails(etvehicalnum.getText().toString().trim(),vehicleType,nextProcess,inOut);
                }
            }

        });

        etinvdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                // Array of month abbreviations
                String[] monthAbbreviations = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
                picker = new DatePickerDialog(Inward_Truck_Store.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Use the month abbreviation from the array
                        String monthAbbreviation = monthAbbreviations[month];
                        etinvdate.setText(dayOfMonth + "/" + monthAbbreviation + "/" + year);
                    }
                }, year, month, day);
                picker.show();
            }
        });
        etmaterialrdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                // Array of month abbreviations
                String[] monthAbbreviations = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
                picker = new DatePickerDialog(Inward_Truck_Store.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Use the month abbreviation from the array
                        String monthAbbreviation = monthAbbreviations[month];
                        etmaterialrdate.setText(dayOfMonth + "/" + monthAbbreviation + "/" + year);
                    }
                }, year, month, day);
                picker.show();
            }
        });

        trssubmit = (Button) findViewById(R.id.submit);
        trsdbroot = FirebaseFirestore.getInstance();

        //prince

        trssubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trsinsert();
            }
        });
    }




    public void makeNotification(String vehicleNumber,String outTime) {
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Assume you have a user role to identify the specific role
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                        String specificRole = "Sampling";
                        // Get the value of the "role" node                    ;
                        if (issue.toString().contains(specificRole)) {
                            //getting the token
                            token = Objects.requireNonNull(issue.child("token").getValue()).toString();
                            FcmNotificationsSender notificationsSender = new FcmNotificationsSender(token,
                                    "Inward Tanker Weighment Process Done..!",
                                    "Vehicle Number:-" + vehicleNumber + " has completed Weighment process at " + outTime,
                                    getApplicationContext(), Inward_Truck_Store.this);
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

    public void trsinsert() {
        String intime = etintime.getText().toString().trim();
        String serialnumber = etserialnumber.getText().toString().trim();
        String vehicalnumber = etvehicalnum.getText().toString().trim();
        String invoicenum = etpo.getText().toString().trim();
        String date = etpodate.getText().toString().trim();
        String supplier = etmaterialrdate.getText().toString().trim();
        String material = etmaterial.getText().toString().trim();
        String qty = etqty.getText().toString().trim();
        String invqtyuom = etinvqtyuom.getText().toString().trim();
        int recqtyoum = Integer.parseInt( qtyUomNumericValue.toString().trim());
        String remark = etremark.getText().toString().trim();
        String invqty = etinvqty.getText().toString().trim();
        String invdate = etinvdate.getText().toString().trim();
        String invNum = etinvnum.getText().toString().trim();
        String outTime = getCurrentTime();

        if (intime.isEmpty() || serialnumber.isEmpty() || vehicalnumber.isEmpty() || invoicenum.isEmpty()
                || date.isEmpty() || supplier.isEmpty() || material.isEmpty() || qty.isEmpty()
                || remark.isEmpty() || invqty.isEmpty() || invdate.isEmpty() || invNum.isEmpty()) {
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
                        materialMap.put("Extramaterial", dynamaterial);
                        materialMap.put("Extraqty", dynaqty);
                        materialMap.put("Extraqtyuom", dynaqtyuom);
                        // Add material data to the list
                        materialList.add(materialMap);
                    }
                }
            }
            InTruckStoreRequestModel storeRequestModel= new InTruckStoreRequestModel(inwardid,intime,outTime,
                    EmployeId,EmployeId,vehicalnumber,serialnumber
                    ,'W','O',vehicleType,Integer.parseInt(qty),recqtyoum
                    ,remark,materialList.toString());
            Call<Boolean> call=storedetails.insertStoreData(storeRequestModel);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body()!=null)
                    {
                        Log.d("Registration", "Response Body: " + response.body());
                        Toasty.success(Inward_Truck_Store.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Inward_Truck_Store.this, Inward_Tanker.class));
                        finish();
                    }
                    else
                    {
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
                    Toasty.error(Inward_Truck_Store.this,"failed..!",Toast.LENGTH_SHORT).show();
                }
            });
            /*Map<String, Object> trsitens = new HashMap<>();
            trsitens.put("In_Time", etintime.getText().toString().trim());
            trsitens.put("Serial_Number", etserialnumber.getText().toString().trim());
            trsitens.put("Vehicle_Number", etvehicalnum.getText().toString().trim());
            trsitens.put("PO_No", etpo.getText().toString().trim());
            trsitens.put("Po_Date", timestamp);
            trsitens.put("Material_Rec_Date", etmaterialrdate.getText().toString().trim());
            trsitens.put("extramaterials", materialList.toString().replace("[]",""));
            trsitens.put("Material", etmaterial.getText().toString().trim());
            trsitens.put("Qty", etqty.getText().toString().trim());
            trsitens.put("ReceiveQTY_Uom", etoum.getText().toString().trim());
            trsitens.put("Remarks", etremark.getText().toString().trim());
            trsitens.put("Invoice_Quantity", etinvqty.getText().toString().trim());
            trsitens.put("Inv_QuantityUom", etinqtyuom.getText().toString().trim());
            trsitens.put("Invoice_Date", etinvdate.getText().toString().trim());
            trsitens.put("Invoice_Number", etinvnum.getText().toString().trim());
            trsitens.put("outTime", outTime);


            makeNotification(etvehicalnum.getText().toString(), outTime);
            trsdbroot.collection("Inward Truck Store").add(trsitens)
                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            etintime.setText("");
                            etserialnumber.setText("");
                            etvehicalnum.setText("");
                            etpo.setText("");
                            etpodate.setText("");
                            etmaterialrdate.setText("");
                            etmaterial.setText("");
                            etqty.setText("");
                            etoum.setText("");
                            etinvqty.setText("");
                            etinqtyuom.setText("");
                            etinvdate.setText("");
                            etinvnum.setText("");
                            Toasty.success(Inward_Truck_Store.this, "Data Inserted Successfully", Toast.LENGTH_SHORT,true).show();
                        }
                    });
            Intent intent = new Intent(this, Inward_Truck.class);
            startActivity(intent);*/
        }
    }


    public void onClick(View view) {

        addview();
    }

    private void addview() {
        View materialview = getLayoutInflater().inflate(R.layout.row_add_store, null, false);
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

    public void onBackPressed() {
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
        finish();
    }
    public void FetchVehicleDetails(@NonNull String vehicleNo,String vehicleType,char NextProcess,char inOut) {
        Call<InTruckStoreResponseModel> call=storedetails.getstorebyfetchVehData(vehicleNo,vehicleType,NextProcess,inOut);
        call.enqueue(new Callback<InTruckStoreResponseModel>() {
            @Override
            public void onResponse(Call<InTruckStoreResponseModel> call, Response<InTruckStoreResponseModel> response) {
                if(response.isSuccessful())
                {
                    InTruckStoreResponseModel data=response.body();
                    if(data.getVehicleNo()!="")
                    {
                        inwardid=data.getInwardId();
                        etvehicalnum.setText(data.getVehicleNo());
                        etvehicalnum.setEnabled(false);
                        etserialnumber.setText(data.getSerialNo());
                        etserialnumber.setEnabled(false);
                        etmaterial.setText(data.getMaterial());
                        etmaterial.setEnabled(false);
                        etmaterialrdate.setText(data.getDate());
                        etmaterialrdate.setEnabled(false);
                        etpo.setText(data.getOA_PO_number());
                        etpo.setEnabled(false);
                        etpodate.setText(data.getDate());
                        etpodate.setEnabled(false);
                        etinvnum.setText(data.getInvoiceNo());
                        etinvnum.setEnabled(false);
                        etinvdate.setText(data.getDate());
                        etinvdate.setEnabled(false);
                        etinvqty.setText(String.valueOf(data.getQty()));
                        etinvqty.setEnabled(false);
                        /*etinqtyuom.setSelection(Integer.parseInt(String.valueOf(data.QtyUOM)));*/
                        /*etqty.setText(String.valueOf(data.getQty()));
                        etqty.setEnabled(false);*/
                        etintime.requestFocus();
                        etintime.callOnClick();
                    }
                }else
                {
                    Log.e("Retrofit", "Error Response Body: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<InTruckStoreResponseModel> call, Throwable t) {
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
                Toasty.error(Inward_Truck_Store.this,"failed..!",Toast.LENGTH_SHORT).show();
            }
        });
    }
    /*public void FetchVehicleDetails(@NonNull String VehicleNo) {
        CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Inward Truck Weighment");
        String searchText = VehicleNo.trim();
        Query query = collectionReference.whereEqualTo("Vehicle_Number", searchText)
                .whereNotEqualTo("In_Time","" );
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    int totalCount = task.getResult().size();
                    if(totalCount == 0) {
                        etvehicalnum.setText("");
                        etserialnumber.setText("");
                        etmaterial.setText("");
                        etpo.setText("");
                        etpodate.setText("");
                        etvehicalnum.requestFocus();
                        Toasty.warning(Inward_Truck_Store.this, "Vehicle Number not Available for Weighment", Toast.LENGTH_SHORT,true).show();
                    }
                    else {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            In_Truck_weigment_list obj = document.toObject(In_Truck_weigment_list.class);
                            // Check if the object already exists to avoid duplicates
                            if (totalCount > 0) {
                                etserialnumber.setText(obj.getSerial_Number());
                                etserialnumber.setEnabled(true);
                                etvehicalnum.setText(obj.getVehicle_Number());
                                etvehicalnum.setEnabled(true);
                                etpo.setText(obj.getOA_Number());
                                etpo.setEnabled(true);
                                etmaterial.setText(obj.getMaterial());
                                etmaterial.setEnabled(true);
                                etpodate.setText(dateFormat.format(obj.getDate().toDate()));
                                etpodate.setEnabled(true);
                                etintime.requestFocus();
                                etintime.callOnClick();
                            }
                        }
                    }
                } else {
                    Log.w("FirestoreData", "Error getting documents.", task.getException());
                }
            }
        });
    }*/
}