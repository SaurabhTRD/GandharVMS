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
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.gandharvms.FcmNotificationsSender;
import com.android.gandharvms.Global_Var;
import com.android.gandharvms.InwardCompletedGrid.GridCompleted;
import com.android.gandharvms.Inward_Tanker;
import com.android.gandharvms.Inward_Tanker_Laboratory.Inward_Tanker_Laboratory;
import com.android.gandharvms.Inward_Tanker_Security.Inward_Tanker_Security;
import com.android.gandharvms.Inward_Tanker_Security.grid;
import com.android.gandharvms.Inward_Truck;
import com.android.gandharvms.Inward_Truck_Security.In_Truck_security_list;
import com.android.gandharvms.Inward_Truck_Security.Inward_Truck_Security;
import com.android.gandharvms.Inward_Truck_Weighment.In_Truck_weigment_list;
import com.android.gandharvms.Inward_Truck_Weighment.Inward_Truck_weighment;
import com.android.gandharvms.LoginWithAPI.Login;
import com.android.gandharvms.LoginWithAPI.LoginMethod;
import com.android.gandharvms.LoginWithAPI.ResponseModel;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.LoginWithAPI.Store;
import com.android.gandharvms.LoginWithAPI.Weighment;
import com.android.gandharvms.Menu;
import com.android.gandharvms.NotificationAlerts.NotificationCommonfunctioncls;
import com.android.gandharvms.R;
import com.android.gandharvms.Util.dialogueprogreesbar;
import com.android.gandharvms.submenu.submenu_Inward_Truck;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.common.reflect.TypeToken;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.Timestamp;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
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

public class Inward_Truck_Store extends NotificationCommonfunctioncls {

    final Calendar calendar = Calendar.getInstance();
    private final int MAX_LENGTH = 10;
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private final String EmployeId = Global_Var.getInstance().EmpId;
    ArrayAdapter<String> adapterItems;
    ArrayAdapter<String> adapterItems1;
    EditText etintime, etsuppliername, etserialnumber, etvehicalnum, etpo, etpodate, etmaterialrdate, etmaterial, etqty, etrecqtyoum, etremark, etinvqty, etinvdate, etinvnum, etinvqtyuom, etextra;
    Button trssubmit, buttonadd, button1, updbtnstoreclick;
    Button view;
    DatePickerDialog picker;
    FirebaseFirestore trsdbroot;
    TimePickerDialog intruckspicker;
    LinearLayout linearLayout;
    List<String> teamList = new ArrayList<>();
    List<String> teamList1 = new ArrayList<>();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-YYYY, HH:mm:ss");
    Timestamp timestamp = new Timestamp(calendar.getTime());
    Integer qtyUomNumericValue = 1;
    Integer netweuomvalue = 1;
    String[] netweuom = {"Ton", "Litre", "KL", "Kgs", "pcs", "NA"};
    AutoCompleteTextView autoCompleteTextViewINVUOM, autoCompleteTextViewRecQTYUOM;
    ArrayAdapter<String> qtyuomdrop;
    ArrayAdapter<String> netweuomdrop;
    Map<String, Integer> qtyUomMapping = new HashMap<>();
    ImageView btnlogout, btnhome;
    TextView username, empid;
    dialogueprogreesbar dialogHelper = new dialogueprogreesbar();
    //prince
    private SharedPreferences sharedPreferences;
    private int autoGeneratedNumber;
    private String token;
    private int inwardid;
    private Store storedetails;
    private LoginMethod userDetails;
    private int recqtyoum;
    private int recqty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inward_truck_store);

        //Send Notification to all
        FirebaseMessaging.getInstance().subscribeToTopic(token);

        storedetails = RetroApiClient.getStoreDetails();
        userDetails = RetroApiClient.getLoginApi();

        //prince
        //Send Notifications To All
        sharedPreferences = getSharedPreferences("TruckStore", MODE_PRIVATE);


        //for recqtyuom
//        autoCompleteTextViewRecQTYUOM = findViewById(R.id.recqtysuom);
//        qtyUomMapping = new HashMap<>();
//        qtyUomMapping.put("NA", 1);
//        qtyUomMapping.put("Ton", 2);
//        qtyUomMapping.put("Litre", 3);
//        qtyUomMapping.put("KL", 4);
//        qtyUomMapping.put("Kgs", 5);
//        qtyUomMapping.put("pcs", 6);
//
//        qtyuomdrop = new ArrayAdapter<String>(this, R.layout.in_ta_se_qty, new ArrayList<>(qtyUomMapping.keySet()));
//        autoCompleteTextViewRecQTYUOM.setAdapter(qtyuomdrop);
//        autoCompleteTextViewRecQTYUOM.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String qtyUomDisplay = parent.getItemAtPosition(position).toString();
//                // Retrieve the corresponding numerical value from the mapping
//                qtyUomNumericValue = qtyUomMapping.get(qtyUomDisplay);
//                if (qtyUomNumericValue != null) {
//                    Toasty.success(Inward_Truck_Store.this, "RecQty : " + qtyUomDisplay + " Selected", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toasty.warning(Inward_Truck_Store.this, "Default ReceiveQTYUnitofMeasurement : " + "NA" + " Selected", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        if (getIntent().hasExtra("VehicleNumber")) {
            String action = getIntent().getStringExtra("Action");
            if (action != null && action.equals("Up")) {
                FetchVehicleDetailsforUpdate(getIntent().getStringExtra("VehicleNumber"), Global_Var.getInstance().MenuType, 'x', 'O');
            } else {
                FetchVehicleDetails(getIntent().getStringExtra("VehicleNumber"), Global_Var.getInstance().MenuType, nextProcess, inOut);
            }
        }

        etintime = findViewById(R.id.etintime);
        etserialnumber = findViewById(R.id.ettrsserialnumber);
        etvehicalnum = findViewById(R.id.ettrsvehical);
        etpo = findViewById(R.id.ettrsponumber);
        etpodate = findViewById(R.id.ettrpodate);
        etmaterialrdate = findViewById(R.id.materialrecievingdate);
//        etmaterial = findViewById(R.id.ettsmaterial);
//        etqty = findViewById(R.id.etsqty);
//        etrecqtyoum = findViewById(R.id.recqtysuom);
//        etinvqtyuom = findViewById(R.id.etinvqtysuom);
        etremark = findViewById(R.id.etremark);
//        etinvqty = findViewById(R.id.ettrinvqty);
        etinvdate = findViewById(R.id.ettrinvDate);
        etinvnum = findViewById(R.id.ettinvnumber);
        etsuppliername = findViewById(R.id.ettrssupplier);

//        etextra = findViewById(R.id.ettsmaterialextra);

        updbtnstoreclick = findViewById(R.id.irstoreupdateclick);

//        linearLayout = findViewById(R.id.layout_list);
//        button1 = findViewById(R.id.button_add);
//        button1.setOnClickListener(this::onClick);
//        teamList.add("Ton");
//        teamList.add("Litre");
//        teamList.add("KL");
//        teamList.add("Kgs");
//        teamList.add("Pcs");
//        teamList.add("NA");

        setupHeader();
        /*btnlogout=findViewById(R.id.btn_logoutButton);
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
                startActivity(new Intent(Inward_Truck_Store.this, Login.class));
            }
        });
        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Inward_Truck_Store.this, Menu.class));
            }
        });*/
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
                SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                String time = format.format(calendar.getTime());
                etintime.setText(time);
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
                if (!hasFocus) {
                    FetchVehicleDetails(etvehicalnum.getText().toString().trim(), vehicleType, nextProcess, inOut);
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

        trssubmit = findViewById(R.id.submit);
        trsdbroot = FirebaseFirestore.getInstance();
        trssubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trsinsert();
            }
        });

        updbtnstoreclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updstrreqmodel();
            }
        });
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
                                        "Inward Truck Store Process Done..!",
                                        "Vehicle Number:-" + vehicleNumber + " has completed Store process at " + outTime,
                                        getApplicationContext(),
                                        Inward_Truck_Store.this
                                );
                                notificationsSender.triggerSendNotification();
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

            }
        });
    }

    private String getCurrentTime() {
        // Get the current time
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(new Date());
    }

    private String convertExtraMaterialsListToString1(List<ExtraMaterial> extraMaterials) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<ExtraMaterial>>() {
        }.getType();
        return gson.toJson(extraMaterials, type);
    }

    public void trsinsert() {
        String intime = etintime.getText().toString().trim();
        String serialnumber = etserialnumber.getText().toString().trim();
        String vehicalnumber = etvehicalnum.getText().toString().trim();
        String invoicenum = etpo.getText().toString().trim();
        String date = etpodate.getText().toString().trim();
        String supplier = etmaterialrdate.getText().toString().trim();
        String remark = etremark.getText().toString().trim();
        String outTime = getCurrentTime();

        if (intime.isEmpty() || serialnumber.isEmpty() || vehicalnumber.isEmpty() || invoicenum.isEmpty()
                || date.isEmpty() || supplier.isEmpty() || remark.isEmpty()) {
            Toasty.warning(this, "All fields must be filled", Toast.LENGTH_SHORT, true).show();
        } else {
            // Get the material list
            List<ExtraMaterial> extraMaterials = new ArrayList<>();
            LinearLayout linearLayout = findViewById(R.id.layout_liststore);
            for (int i = 0; i < linearLayout.getChildCount(); i++) {
                View childView = linearLayout.getChildAt(i);
                if (childView != null) {
                    EditText materialEditText = childView.findViewById(R.id.editmaterial);
                    EditText qtyEditText = childView.findViewById(R.id.editqty);
                    Spinner uomSpinner = childView.findViewById(R.id.spinner_team);
                    EditText recivingqty = childView.findViewById(R.id.recivingqty);
                    Spinner recqtyuomSpinner = childView.findViewById(R.id.recuomspinner_team);

                    ExtraMaterial extraMaterial = new ExtraMaterial(
                            materialEditText.getText().toString().trim(),
                            qtyEditText.getText().toString().trim(),
                            uomSpinner.getSelectedItem().toString(),
                            recivingqty.getText().toString().trim(),
                            recqtyuomSpinner.getSelectedItem().toString()
                    );
                    extraMaterials.add(extraMaterial); // Add the extraMaterial to the list
                }
            }
            String extraMaterialsString = convertExtraMaterialsListToString1(extraMaterials);
            InTruckStoreRequestModel storeRequestModel = new InTruckStoreRequestModel(inwardid, intime, outTime,
                    EmployeId, EmployeId, vehicalnumber, serialnumber
                    , 'W', 'O', vehicleType, 1, 1
                    , remark, extraMaterialsString.replace("=", ":"));
            dialogHelper.showConfirmationDialog(this, () -> {
                dialogHelper.showProgressDialog(this); // Show progress when confirmed
                Call<Boolean> call = storedetails.insertStoreData(storeRequestModel);
                call.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if (response.isSuccessful() && response.body() != null && response.body()) {
                            dialogHelper.hideProgressDialog(); // Hide after response
                            Log.d("Registration", "Response Body: " + response.body());
                            Toasty.success(Inward_Truck_Store.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                            makeNotification(vehicalnumber, outTime);
                            startActivity(new Intent(Inward_Truck_Store.this, grid.class));
                            finish();
                        } else {
                            Toasty.error(Inward_Truck_Store.this, "Data Insertion Failed..!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        dialogHelper.hideProgressDialog(); // Hide after response
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
                        Toasty.error(Inward_Truck_Store.this, "failed..!", Toast.LENGTH_SHORT).show();
                    }
                });
            });
        }
    }

    public void updstrreqmodel() {
        if (!etqty.getText().toString().isEmpty()) {
            try {
                String input = etqty.getText().toString().trim();
                int integerValue;

                if (input.contains(".")) {
                    // Input contains a decimal point
                    String[] parts = input.split("\\.");
                    int wholeNumberPart = Integer.parseInt(parts[0]);
                    int decimalPart = Integer.parseInt(parts[1]);
                    // Adjust decimal part to two digits
                    if (parts[1].length() > 2) {
                        // Take only first two digits after decimal point
                        decimalPart = Integer.parseInt(parts[1].substring(0, 2));
                    }
                    // Combine integer and decimal parts
                    integerValue = wholeNumberPart * 100 + decimalPart;
                } else {
                    // Input is a whole number
                    integerValue = Integer.parseInt(input) * 100;
                }
                recqty = integerValue;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        //recqtyoum = Integer.parseInt( qtyUomNumericValue.toString().trim());
        if (!qtyUomNumericValue.toString().isEmpty()) {
            try {
                recqtyoum = Integer.parseInt(qtyUomNumericValue.toString().trim());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        String remark = etremark.getText().toString().trim();

        List<ExtraMaterial> extraMaterials = new ArrayList<>();
        LinearLayout linearLayout = findViewById(R.id.layout_liststore);
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            View childView = linearLayout.getChildAt(i);
            if (childView != null) {
                EditText materialEditText = childView.findViewById(R.id.editmaterial);
                EditText qtyEditText = childView.findViewById(R.id.editqty);
                Spinner uomSpinner = childView.findViewById(R.id.spinner_team);
                EditText recivingqty = childView.findViewById(R.id.recivingqty);
                Spinner recqtyuomSpinner = childView.findViewById(R.id.recuomspinner_team);

                ExtraMaterial extraMaterial = new ExtraMaterial(
                        materialEditText.getText().toString().trim(),
                        qtyEditText.getText().toString().trim(),
                        uomSpinner.getSelectedItem().toString(),
                        recivingqty.getText().toString().trim(),
                        recqtyuomSpinner.getSelectedItem().toString()
                );
                extraMaterials.add(extraMaterial); // Add the extraMaterial to the list
            }
        }
        String extraMaterialsString = convertExtraMaterialsListToString1(extraMaterials);
        ir_updstorebyinwardid_req_model updstrreqmodel = new ir_updstorebyinwardid_req_model(inwardid,
                EmployeId, recqty, recqtyoum, remark, extraMaterialsString.replace("=", ":"));
        dialogHelper.showConfirmationDialog(this, () -> {
            dialogHelper.showProgressDialog(this); // Show progress when confirmed
            Call<Boolean> call = storedetails.updstoredatabyinwardid(updstrreqmodel);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body() != null && response.body()) {
                        dialogHelper.hideProgressDialog(); // Hide after response
                        Log.d("Registration", "Response Body: " + response.body());
                        Toasty.success(Inward_Truck_Store.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                        //makeNotification(vehicalnumber, outTime);
                        startActivity(new Intent(Inward_Truck_Store.this, Inward_Truck.class));
                        finish();
                    } else {
                        Toasty.error(Inward_Truck_Store.this, "Data Insertion Failed..!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    dialogHelper.hideProgressDialog(); // Hide after response
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
                    Toasty.error(Inward_Truck_Store.this, "failed..!", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    public void onClick(View view) {
        addview();
    }

    private void addview() {
        View materialview = getLayoutInflater().inflate(R.layout.row_add_store, null, false);
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

    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
        finish();
    }

    private List<ExtraMaterial> parseExtraMaterials(String jsonString) {
        if (jsonString == null || jsonString.trim().isEmpty()) {
            Log.e("JSON Parser", "JSON string is null or empty");
            return new ArrayList<>(); // Return an empty list if JSON is invalid
        }

        try {
            Log.d("JSON Parser", "JSON String: " + jsonString);
            Gson gson = new Gson();
            Type listType = new TypeToken<List<ExtraMaterial>>() {
            }.getType();
            return gson.fromJson(jsonString, listType);
        } catch (JsonSyntaxException e) {
            Log.e("JSON Parser", "Failed to parse JSON: " + jsonString, e);
            return new ArrayList<>(); // Return an empty list in case of parsing error
        }
    }

    private void validateJson(String jsonString) {
        try {
            new JsonParser().parse(jsonString);
            Log.d("JSON Validator", "Valid JSON: " + jsonString);
        } catch (JsonSyntaxException e) {
            Log.e("JSON Validator", "Invalid JSON: " + jsonString, e);
        }
    }

    public void createExtraMaterialViews(List<ExtraMaterial> extraMaterials) {
        LinearLayout linearLayout = findViewById(R.id.layout_liststore); // Ensure this is the correct ID

        // Clear previous views if any
        linearLayout.removeAllViews();

        for (ExtraMaterial extraMaterial : extraMaterials) {
            View materialView = getLayoutInflater().inflate(R.layout.row_logistic_add_material, null);

            EditText materialEditText = materialView.findViewById(R.id.editmaterial);
            EditText qtyEditText = materialView.findViewById(R.id.editqty);
            Spinner uomSpinner = materialView.findViewById(R.id.spinner_team);
            EditText recivingqty = materialView.findViewById(R.id.recivingqty);
            Spinner recuomSpinner = materialView.findViewById(R.id.recuomspinner_team);


            materialEditText.setText(extraMaterial.getMaterial());
            materialEditText.setEnabled(false);
            qtyEditText.setText(extraMaterial.getQty());
            qtyEditText.setEnabled(false);

            List<String> teamList = Arrays.asList("NA", "Ton", "Litre", "KL", "Kgs", "Pcs", "M3", "Meter", "Feet"); // or fetch it dynamically
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, teamList);
            uomSpinner.setAdapter(arrayAdapter);
            uomSpinner.setEnabled(false);

            setSpinnerValue(uomSpinner, extraMaterial.getQtyuom());

            List<String> teamListrec = Arrays.asList("NA", "Ton", "Litre", "KL", "Kgs", "Pcs", "M3", "Meter", "Feet"); // or fetch it dynamically
            ArrayAdapter<String> arrayAdapterrec = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, teamListrec);
            recuomSpinner.setAdapter(arrayAdapterrec);

            setSpinnerValue(recuomSpinner, extraMaterial.getRecQtyUOM());

            // Add the material view to the linear layout
            linearLayout.addView(materialView);
        }
        String extraMaterialsString = convertExtraMaterialsListToString(extraMaterials);
    }

    private String convertExtraMaterialsListToString(List<ExtraMaterial> extraMaterials) {
        StringBuilder result = new StringBuilder();

        for (ExtraMaterial extraMaterial : extraMaterials) {
            String materialString = convertExtraMaterialToString(extraMaterial);

            // Add this string to the result
            result.append(materialString).append("\n"); // Separate entries by a newline or any other delimiter
        }

        return result.toString();
    }

    private String convertExtraMaterialToString(ExtraMaterial extraMaterial) {
        String material = extraMaterial.getMaterial();
        String qty = extraMaterial.getQty();
        String qtyuom = extraMaterial.getQtyuom();
        String reciving_qty = extraMaterial.getRecivingqty();
        String receiqty_UOM = extraMaterial.getRecQtyUOM();
        // Concatenate fields into a single string
        return (material + "," + qty + "," + qtyuom + "," + reciving_qty + "," + receiqty_UOM);
    }

    private void setSpinnerValue(Spinner spinner, String value) {
        SpinnerAdapter adapter = spinner.getAdapter();
        if (adapter != null) {
            for (int i = 0; i < adapter.getCount(); i++) {
                if (adapter.getItem(i).toString().equals(value)) {
                    spinner.setSelection(i);
                    break;
                }
            }
        } else {
            Log.e("Spinner Error", "Spinner adapter is null");
        }
    }

    public void FetchVehicleDetails(@NonNull String vehicleNo, String vehicleType, char NextProcess, char inOut) {
        Call<InTruckStoreResponseModel> call = storedetails.getstorebyfetchVehData(vehicleNo, vehicleType, NextProcess, inOut);
        call.enqueue(new Callback<InTruckStoreResponseModel>() {
            @Override
            public void onResponse(Call<InTruckStoreResponseModel> call, Response<InTruckStoreResponseModel> response) {
                if (response.isSuccessful()) {
                    InTruckStoreResponseModel data = response.body();
                    if (data.getVehicleNo() != "" && data.getVehicleNo() != null) {
                        inwardid = data.getInwardId();
                        etvehicalnum.setText(data.getVehicleNo());
                        etvehicalnum.setEnabled(false);
                        etsuppliername.setText(data.getPartyName());
                        etsuppliername.setEnabled(false);
                        etserialnumber.setText(data.getSerialNo());
                        etserialnumber.setEnabled(false);
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

                        String extraMaterialsJson = data.getExtramaterials();
                        Log.d("JSON Debug", "Extra Materials JSON: " + extraMaterialsJson);
                        List<ExtraMaterial> extraMaterials = parseExtraMaterials(extraMaterialsJson);
                        Log.d("JSON Debug", "Parsed Extra Materials Size: " + extraMaterials.size());
                        createExtraMaterialViews(extraMaterials);

                    } else {
                        Toasty.error(Inward_Truck_Store.this, "This Vehicle Number Is Not Available..!", Toast.LENGTH_SHORT).show();
                    }
                } else {
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
                Toasty.error(Inward_Truck_Store.this, "failed..!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void FetchVehicleDetailsforUpdate(@NonNull String vehicleNo, String vehicleType, char NextProcess, char inOut) {
        Call<InTruckStoreResponseModel> call = storedetails.getstorebyfetchVehData(vehicleNo, vehicleType, NextProcess, inOut);
        call.enqueue(new Callback<InTruckStoreResponseModel>() {
            @Override
            public void onResponse(Call<InTruckStoreResponseModel> call, Response<InTruckStoreResponseModel> response) {
                if (response.isSuccessful()) {
                    InTruckStoreResponseModel data = response.body();
                    if (data.getVehicleNo() != "" && data.getVehicleNo() != null) {
                        inwardid = data.getInwardId();
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
                        etinvqtyuom.setText(data.UnitOfMeasurement);
                        String recqty = String.format("%.2f", data.getReceiveQTY() / 100.0);
                        etinvqtyuom.setEnabled(false);
                        etqty.setText(recqty);
                        etqty.setEnabled(true);
                        etrecqtyoum.setText(data.getReQTYUom());
                        etrecqtyoum.setEnabled(false);
                        etremark.setText(data.getStoreRemark());
                        etremark.setEnabled(true);
                        etintime.setVisibility(View.GONE);
                        trssubmit.setVisibility(View.GONE);
                        updbtnstoreclick.setVisibility(View.VISIBLE);
                    } else {
                        Toasty.error(Inward_Truck_Store.this, "This Vehicle Number Is Out From Factory.\n You Can Not Update", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(Inward_Truck_Store.this, ir_stor_Completedgrid.class));
                    }
                } else {
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
                Toasty.error(Inward_Truck_Store.this, "failed..!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void StoreViewclick(View view) {
        Intent intent = new Intent(this, grid.class);
        startActivity(intent);
    }

    public void truckstorecompletedclick(View view) {
        Intent intent = new Intent(this, ir_stor_Completedgrid.class);
        startActivity(intent);
    }
}