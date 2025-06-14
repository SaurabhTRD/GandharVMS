package com.android.gandharvms.Outward_Tanker_Production_forms;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gandharvms.FcmNotificationsSender;
import com.android.gandharvms.Global_Var;
import com.android.gandharvms.LoginWithAPI.LoginMethod;
import com.android.gandharvms.LoginWithAPI.ResponseModel;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.NotificationAlerts.NotificationCommonfunctioncls;
import com.android.gandharvms.Outward_Tanker;
import com.android.gandharvms.Outward_Tanker_Security.Grid_Outward;
import com.android.gandharvms.Outward_Tanker_Security.Outward_RetroApiclient;
import com.android.gandharvms.Outward_Truck_Dispatch.Outward_DesIndustriaLoading_Form;
import com.android.gandharvms.ProductListData;
import com.android.gandharvms.R;
import com.android.gandharvms.outward_Tanker_Lab_forms.Lab_Model__Outward_Tanker;
import com.android.gandharvms.outward_Tanker_Lab_forms.New_Outward_tanker_Lab;
import com.android.gandharvms.outward_Tanker_Lab_forms.Outward_Tanker_Lab;
import com.google.android.material.textfield.TextInputLayout;
import com.google.common.reflect.TypeToken;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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

public class New_Outward_Tanker_Production extends NotificationCommonfunctioncls {

    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final String EmployeId = Global_Var.getInstance().EmpId;
    public char inOut = Global_Var.getInstance().InOutType;
    private Outward_Tanker_Lab outwardTankerLab;
    private String token;
    private LoginMethod userDetails;
    private int oploutwardid = 0;
    private int OutwardId;
    EditText serialnumber,vehiclenumber,oanumber,product,customer,location,howqty,transporter,intime,blendernumber,signproduction,oprator,remark,etbillremark,etproduct;
    Button btnsubmit,btncompletd,btnupdate;
    ArrayAdapter<String> nextdeptdrop;
    Map<String, String> nextdeptmapping = new HashMap<>();
    String nextdeptvalue = "W";
    String movementvalue = "";
    AutoCompleteTextView dept,vehmovement;
    Map<String, Integer> Movement = new HashMap<>();
    ArrayAdapter<String> Movementdrop;
    String deptNumericValue = "W";
    private List<Compartment> compartmentList;
    private CompartmentAdapter adapter;
    private Button btnAddCompartment;
    private RecyclerView recyclerView;
    public String procompart;
    public String compartme,updateserialnumber,updatevehiclenumber;
    public CheckBox checkBoxMultipleVehicle;
    // ✅ Declare Global Variable
    private int movementValueInt = 0; // Default value
    // ✅ Declare Global Variable
    private boolean isMultipleVehicle = false; // Default is false
    public int compartmentArraycount;
    public int compartmentcount;
    public  int currentCompartment;

    private SharedPreferences sharedPreferences;
    public ImageView imgupdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_outward_tanker_production);
        setupHeader();
        outwardTankerLab = Outward_RetroApiclient.outwardTankerLab();
        userDetails = RetroApiClient.getLoginApi();
        FirebaseMessaging.getInstance().subscribeToTopic(token);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        serialnumber = findViewById(R.id.etnewserialnumber);
        vehiclenumber = findViewById(R.id.etnewvehicleno);
        oanumber = findViewById(R.id.etnewoanumer);
        product = findViewById(R.id.etnewproductname);
        customer = findViewById(R.id.etnewcustname);
        location = findViewById(R.id.etnewlocation);
        howqty = findViewById(R.id.etnewhowmuch);
        transporter = findViewById(R.id.etnewtransportername);
        intime = findViewById(R.id.etinewntime);
        blendernumber = findViewById(R.id.elnewblendingno);
        signproduction = findViewById(R.id.etnewsignofproduction);
        oprator = findViewById(R.id.etnewsignofoprator);
        remark = findViewById(R.id.etnewremark);
        etproduct = findViewById(R.id.etproductottankerprodcut);
        etbillremark=findViewById(R.id.etprducBillingRemark);
        btnupdate = findViewById(R.id.etnewsupdate);
        btnupdate.setVisibility(View.GONE);
        btnsubmit = findViewById(R.id.etnewssubmit);
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert();
            }
        });
        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });

        intime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                String time =  format.format(calendar.getTime());
                intime.setText(time);
            }
        });
        imgupdate = findViewById(R.id.updateIcon);
        imgupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatecustomername();
            }
        });


        checkBoxMultipleVehicle = findViewById(R.id.checkBoxMultipleVehicle);
        TextInputLayout nextDeptLayout = findViewById(R.id.layout_next_dept); // TextInputLayout of AutoCompleteTextView
        TextInputLayout vehicleMovementLayout = findViewById(R.id.layout_vehicle_movement); // TextInputLayout of AutoCompleteTextView

        // ✅ Hide both dropdown fields initially
        nextDeptLayout.setVisibility(View.GONE);
        vehicleMovementLayout.setVisibility(View.GONE);

        // ✅ Listen for CheckBox changes
        checkBoxMultipleVehicle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            isMultipleVehicle = isChecked;  // ✅ Update Boolean Value
            if (isChecked) {
//                nextDeptLayout.setVisibility(View.VISIBLE);  // ✅ Show if checked
                vehicleMovementLayout.setVisibility(View.VISIBLE);

            } else {
//                nextDeptLayout.setVisibility(View.GONE);  // ✅ Hide if unchecked
                vehicleMovementLayout.setVisibility(View.GONE);
            }
        });


        if (getIntent().hasExtra("vehiclenum")) {
            FetchVehicleDetails(getIntent().getStringExtra("vehiclenum"), Global_Var.getInstance().MenuType, nextProcess, inOut);
        }
        vehiclenumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    FetchVehicleDetails(vehiclenumber.getText().toString().trim(), vehicleType, nextProcess, inOut);
                }
            }
        });

        nextdeptmapping = new HashMap<>();
        nextdeptmapping.put("Production", "P");
        nextdeptmapping.put("Laboratory", "L");

        dept = findViewById(R.id.nextdept_outproduction);
        nextdeptdrop = new ArrayAdapter<String>(this, R.layout.indus_nextdept, new ArrayList<>(nextdeptmapping.keySet()));
        dept.setAdapter(nextdeptdrop);
        dept.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String indusdept = parent.getItemAtPosition(position).toString();
                nextdeptvalue = nextdeptmapping.get(indusdept);
                if (deptNumericValue != null) {
                    Toasty.success(New_Outward_Tanker_Production.this, "NetWeighUnitofMeasurement : " + indusdept + " Selected", Toast.LENGTH_SHORT).show();
                } else {
                    Toasty.error(New_Outward_Tanker_Production.this, "Default NetWeighUnitofMeasurement : " + "NA", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //Movement.put("Compartment1", 1);
        Movement.put("Compartment2", 2);
        Movement.put("Compartment3", 3);
        Movement.put("Compartment4", 4);
        Movement.put("Compartment5", 5);
        Movement.put("Compartment6", 6);

        vehmovement = findViewById(R.id.vehicelmovement);
        ArrayAdapter<String> Movementdrop = new ArrayAdapter<>(this, R.layout.indus_nextdept, new ArrayList<>(Movement.keySet()));
        vehmovement.setAdapter(Movementdrop);

// ✅ Handle Selection Correctly
        vehmovement.setOnItemClickListener((parent, view, position, id) -> {
            String movement = parent.getItemAtPosition(position).toString();

            // ✅ Get Integer Value Directly Without Conversion
            if (Movement.containsKey(movement)) {
                movementValueInt = Movement.get(movement);  // ✅ Now it's already an integer
                Log.d("MOVEMENT_SELECTED", "Selected Value: " + movementValueInt);
            } else {
                Log.e("MOVEMENT_ERROR", "Invalid selection: " + movement);
            }
        });

        btnAddCompartment = findViewById(R.id.btnAddCompartment);
        recyclerView = findViewById(R.id.recyclerView);

        compartmentList = new ArrayList<>();
        adapter = new CompartmentAdapter(this, compartmentList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator()); // Smooth animations

        btnAddCompartment.setOnClickListener(v -> {
            if (compartmentList.size() < 6) {
                showAddCompartmentDialogs(compartmentcount);
            } else {
                Toast.makeText(this, "Maximum 6 compartments allowed", Toast.LENGTH_SHORT).show();
            }
        });

        sharedPreferences = getSharedPreferences("VehicleManagementPrefs", MODE_PRIVATE);

        if (sharedPreferences != null) {
            if (getIntent().hasExtra("VehicleNumber")) {
                String action = getIntent().getStringExtra("Action");
                if (action != null && action.equals("Up")) {
                    FetchVehicleDetailsupdate(getIntent().getStringExtra("VehicleNumber"), Global_Var.getInstance().MenuType, 'x', 'I');
                } else {
                    FetchVehicleDetails(getIntent().getStringExtra("VehicleNumber"), Global_Var.getInstance().MenuType, 'S', 'I');
                    btnsubmit.setVisibility(View.GONE);
                    //button1.setVisibility(View.GONE);
                }
//                btnadd.setVisibility(View.GONE);
            } else {
                //GetMaxSerialNo(vehicletype+ formattedDate);
            }

        } else {
            Log.e("MainActivity", "SharedPreferences is null");
        }




    }

    private void FetchVehicleDetails(@NonNull String vehicleNo, String vehicleType, char nextProcess, char inOut) {
        Call<Lab_Model__Outward_Tanker> call = outwardTankerLab.fetchlab(vehicleNo, vehicleType, nextProcess, inOut);
        call.enqueue(new Callback<Lab_Model__Outward_Tanker>() {
            @Override
            public void onResponse(Call<Lab_Model__Outward_Tanker> call, Response<Lab_Model__Outward_Tanker> response) {
                if (response.isSuccessful()) {
                    Lab_Model__Outward_Tanker data = response.body();
                    if (data.getVehicleNumber() != null && data.getVehicleNumber() != "") {
                        oploutwardid = data.getOplOutwardId();
                        OutwardId = data.getOutwardId();
                        serialnumber.setText(data.getSerialNumber());
                        serialnumber.setEnabled(false);
                        updateserialnumber = data.getSerialNumber();
                        vehiclenumber.setText(data.getVehicleNumber());
                        vehiclenumber.setEnabled(false);
                        updatevehiclenumber = data.getVehicleNumber();
                        oanumber.setText(data.getOAnumber());
                        oanumber.setEnabled(false);
                        product.setText(data.getProductQTYUOMOA());
                        product.setEnabled(false);
                        customer.setText(data.getCustomerName());
                        customer.setEnabled(false);
                        location.setText(data.getLocation());
                        location.setEnabled(false);
                        howqty.setText(String.valueOf(data.getHowMuchQuantityFilled()));
                        howqty.setEnabled(false);
                        transporter.setText(data.getTransportName());
                        transporter.setEnabled(false);
                        etbillremark.setText(data.getTankerBillingRemark());
                        etbillremark.setEnabled(false);
                        compartmentcount = data.getCompartmentCount();

                        // ✅ Compartment Count Logic
                        List<String> compartments = new ArrayList<>();

                        if (data.getProcompartment1() != null && !data.getProcompartment1().isEmpty())
                            compartments.add(data.getProcompartment1());
                        if (data.getProcompartment2() != null && !data.getProcompartment2().isEmpty())
                            compartments.add(data.getProcompartment2());
                        if (data.getProcompartment3() != null && !data.getProcompartment3().isEmpty())
                            compartments.add(data.getProcompartment3());
                        if (data.getProcompartment4() != null && !data.getProcompartment4().isEmpty())
                            compartments.add(data.getProcompartment4());
                        if (data.getProcompartment5() != null && !data.getProcompartment5().isEmpty())
                            compartments.add(data.getProcompartment5());
                        if (data.getProcompartment6() != null && !data.getProcompartment6().isEmpty())
                            compartments.add(data.getProcompartment6());

                        compartmentArraycount = compartments.size(); // ✅ Store count dynamically
                        Log.d("COMPARTMENT_COUNT", "Total Compartments: " + compartmentArraycount);

                        // 🔹 Fetch and parse compartments, then show dialog
                        List<String> compartmentsJson = Arrays.asList(
                                data.getProcompartment1(),
                                data.getProcompartment2(),
                                data.getProcompartment3(),
                                data.getProcompartment4(),
                                data.getProcompartment5(),
                                data.getProcompartment6()
                        );
                        boolean hasCompartmentData = false; // Flag to check if at least one compartment has data

                        for (String json : compartmentsJson) {
                            Compartment compartment = parseCompartment(json);
                            if (compartment != null) {
                                compartmentList.add(compartment);
                                hasCompartmentData = true; // Set flag to true when a compartment is found
                                adapter.notifyDataSetChanged();
                                // 🔹 Show Update or Submit button based on compartment data
                                if (compartmentcount >= compartmentArraycount) {
                                    btnsubmit.setVisibility(View.GONE);
                                    btnupdate.setVisibility(View.VISIBLE);
//                                    intime.setVisibility(View.GONE);
//                                    blendernumber.setVisibility(View.GONE);
//                                    signproduction.setVisibility(View.GONE);
//                                    oprator.setVisibility(View.GONE);
//                                    remark.setVisibility(View.GONE);
//                                    etbillremark.setVisibility(View.GONE);

                                } else {
                                    btnsubmit.setVisibility(View.VISIBLE);
                                    btnupdate.setVisibility(View.GONE);
                                }
                            }
                        }
                        // ✅ Hide fields if more than 1 compartment
                        if (compartmentArraycount >= 1) {
                            intime.setVisibility(View.GONE);
                            blendernumber.setVisibility(View.GONE);
                            signproduction.setVisibility(View.GONE);
                            oprator.setVisibility(View.GONE);
                            remark.setVisibility(View.GONE);
                            checkBoxMultipleVehicle.setVisibility(View.GONE);
                            Log.d("VISIBILITY_UPDATE", "Hiding fields because compartment count is: " + compartmentArraycount);
                        } else {
                            // ✅ Show fields if only one compartment
                            intime.setVisibility(View.VISIBLE);
                            blendernumber.setVisibility(View.VISIBLE);
                            signproduction.setVisibility(View.VISIBLE);
                            oprator.setVisibility(View.VISIBLE);
                            remark.setVisibility(View.VISIBLE);
                            checkBoxMultipleVehicle.setVisibility(View.VISIBLE);
                            Log.d("VISIBILITY_UPDATE", "Showing fields because compartment count is: " + compartmentArraycount);
                        }

                    } else {
                        Toasty.error(New_Outward_Tanker_Production.this, "This Vehicle Number Is Not Available..!", Toast.LENGTH_SHORT).show();
                    }
                } else {
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

    private void FetchVehicleDetailsupdate(@NonNull String vehicleNo, String vehicleType, char nextProcess, char inOut) {
        Call<Lab_Model__Outward_Tanker> call = outwardTankerLab.fetchlab(vehicleNo, vehicleType, nextProcess, inOut);
        call.enqueue(new Callback<Lab_Model__Outward_Tanker>() {
            @Override
            public void onResponse(Call<Lab_Model__Outward_Tanker> call, Response<Lab_Model__Outward_Tanker> response) {
                if (response.isSuccessful()) {
                    Lab_Model__Outward_Tanker data = response.body();
                    if (data.getVehicleNumber() != null && data.getVehicleNumber() != "") {
                        oploutwardid = data.getOplOutwardId();
                        OutwardId = data.getOutwardId();
                        serialnumber.setText(data.getSerialNumber());
                        serialnumber.setEnabled(false);
                        updateserialnumber = data.getSerialNumber();
                        vehiclenumber.setText(data.getVehicleNumber());
                        vehiclenumber.setEnabled(false);
                        updatevehiclenumber = data.getVehicleNumber();
                        oanumber.setText(data.getOAnumber());
                        oanumber.setEnabled(false);
                        product.setText(data.getProductQTYUOMOA());
                        product.setEnabled(false);
                        customer.setText(data.getCustomerName());
                        location.setText(data.getLocation());
                        location.setEnabled(false);
                        howqty.setText(String.valueOf(data.getHowMuchQuantityFilled()));
                        howqty.setEnabled(false);
                        transporter.setText(data.getTransportName());
                        transporter.setEnabled(false);
                        etbillremark.setText(data.getTankerBillingRemark());
                        etbillremark.setEnabled(false);
                        compartmentcount = data.getCompartmentCount();

                        // ✅ Compartment Count Logic
                        List<String> compartments = new ArrayList<>();

                        if (data.getProcompartment1() != null && !data.getProcompartment1().isEmpty())
                            compartments.add(data.getProcompartment1());
                        if (data.getProcompartment2() != null && !data.getProcompartment2().isEmpty())
                            compartments.add(data.getProcompartment2());
                        if (data.getProcompartment3() != null && !data.getProcompartment3().isEmpty())
                            compartments.add(data.getProcompartment3());
                        if (data.getProcompartment4() != null && !data.getProcompartment4().isEmpty())
                            compartments.add(data.getProcompartment4());
                        if (data.getProcompartment5() != null && !data.getProcompartment5().isEmpty())
                            compartments.add(data.getProcompartment5());
                        if (data.getProcompartment6() != null && !data.getProcompartment6().isEmpty())
                            compartments.add(data.getProcompartment6());

                        compartmentArraycount = compartments.size(); // ✅ Store count dynamically
                        Log.d("COMPARTMENT_COUNT", "Total Compartments: " + compartmentArraycount);

                        // 🔹 Fetch and parse compartments, then show dialog
                        List<String> compartmentsJson = Arrays.asList(
                                data.getProcompartment1(),
                                data.getProcompartment2(),
                                data.getProcompartment3(),
                                data.getProcompartment4(),
                                data.getProcompartment5(),
                                data.getProcompartment6()
                        );
                        boolean hasCompartmentData = false; // Flag to check if at least one compartment has data

                        for (String json : compartmentsJson) {
                            Compartment compartment = parseCompartment(json);
                            if (compartment != null) {
                                compartmentList.add(compartment);
                                hasCompartmentData = true; // Set flag to true when a compartment is found
                                adapter.notifyDataSetChanged();
                                // 🔹 Show Update or Submit button based on compartment data
                                if (compartmentcount >= compartmentArraycount) {
                                    btnsubmit.setVisibility(View.GONE);
                                    btnupdate.setVisibility(View.VISIBLE);
//                                    intime.setVisibility(View.GONE);
//                                    blendernumber.setVisibility(View.GONE);
//                                    signproduction.setVisibility(View.GONE);
//                                    oprator.setVisibility(View.GONE);
//                                    remark.setVisibility(View.GONE);
//                                    etbillremark.setVisibility(View.GONE);

                                } else {
                                    btnsubmit.setVisibility(View.VISIBLE);
                                    btnupdate.setVisibility(View.GONE);
                                }
                            }
                        }
                        // ✅ Hide fields if more than 1 compartment
                        if (compartmentArraycount >= 1) {
                            intime.setVisibility(View.GONE);
                            blendernumber.setVisibility(View.GONE);
                            signproduction.setVisibility(View.GONE);
                            oprator.setVisibility(View.GONE);
                            remark.setVisibility(View.GONE);
                            checkBoxMultipleVehicle.setVisibility(View.GONE);
                            Log.d("VISIBILITY_UPDATE", "Hiding fields because compartment count is: " + compartmentArraycount);
                        } else {
                            // ✅ Show fields if only one compartment
                            intime.setVisibility(View.VISIBLE);
                            blendernumber.setVisibility(View.VISIBLE);
                            signproduction.setVisibility(View.VISIBLE);
                            oprator.setVisibility(View.VISIBLE);
                            remark.setVisibility(View.VISIBLE);
                            checkBoxMultipleVehicle.setVisibility(View.VISIBLE);
                            Log.d("VISIBILITY_UPDATE", "Showing fields because compartment count is: " + compartmentArraycount);
                        }

                    } else {
                        Toasty.error(New_Outward_Tanker_Production.this, "This Vehicle Number Is Not Available..!", Toast.LENGTH_SHORT).show();
                    }
                } else {
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

    private List<ProductListData> parseExtraMaterials(String jsonString) {
        if (jsonString == null || jsonString.trim().isEmpty()) {
            Log.e("JSON Parser", "JSON string is null or empty");
            return new ArrayList<>(); // Return an empty list if JSON is invalid
        }
        try {
            Log.d("JSON Parser", "JSON String: " + jsonString);
            Gson gson = new Gson();
            Type listType = new TypeToken<List<ProductListData>>() {
            }.getType();
            return gson.fromJson(jsonString, listType);
        } catch (JsonSyntaxException e) {
            Log.e("JSON Parser", "Failed to parse JSON: " + jsonString, e);
            return new ArrayList<>(); // Return an empty list in case of parsing error
        }
    }

    public void createExtraMaterialViews(List<ProductListData> extraMaterials) {
        LinearLayout linearLayout = findViewById(R.id.layout_productlistitinproduction); // Ensure this is the correct ID

        // Clear previous views if any
        linearLayout.removeAllViews();

        for (ProductListData extraMaterial : extraMaterials) {
            View materialView = getLayoutInflater().inflate(R.layout.allproductdetaisllist, null);

            EditText etoanumber = materialView.findViewById(R.id.etitinweioano);
            EditText etproductname = materialView.findViewById(R.id.etitinweiproductname);
            EditText etproductqty = materialView.findViewById(R.id.etitinweiproductqty);
            Spinner productuom = materialView.findViewById(R.id.etitinweiprospinner_team);

            etoanumber.setText(extraMaterial.getOANumber());
            etoanumber.setEnabled(false);
            etproductname.setText(extraMaterial.getProductName());
            etproductname.setEnabled(false);
            etproductqty.setText(extraMaterial.getProductQty());
            etproductqty.setEnabled(false);

            List<String> teamList = Arrays.asList("Ton", "KL"); // or fetch it dynamically
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, teamList);
            productuom.setAdapter(arrayAdapter);
            productuom.setEnabled(false);
            setSpinnerValue(productuom, extraMaterial.getProductQtyuom());
            // Add the material view to the linear layout
            linearLayout.addView(materialView);
        }
        String extraMaterialsString = convertExtraMaterialsListToString(extraMaterials);
    }

    private String convertExtraMaterialsListToString(List<ProductListData> extraMaterials) {
        StringBuilder result = new StringBuilder();
        for (ProductListData extraMaterial : extraMaterials) {
            String materialString = convertExtraMaterialToString(extraMaterial);
            // Add this string to the result
            result.append(materialString).append("\n"); // Separate entries by a newline or any other delimiter
        }
        return result.toString();
    }

    private String convertExtraMaterialToString(ProductListData extraMaterial) {
        String OANumber = extraMaterial.getOANumber();
        String productname = extraMaterial.getProductName();
        String productqty = extraMaterial.getProductQty();
        String productqtyuom = extraMaterial.getProductQtyuom();
        // Concatenate fields into a single string
        return (OANumber + "," + productname + "," + productqty + "," + productqtyuom);
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

//    private void showAddCompartmentDialog1() {
//        if (compartmentList.size() >= 6) {
//            Toast.makeText(this, "Maximum 6 compartments allowed!", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        Dialog dialog = new Dialog(this);
//        dialog.setContentView(R.layout.dialog_add_compartment);
//        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//
//        // Set width and height for a medium-size dialog
//        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
//        layoutParams.copyFrom(dialog.getWindow().getAttributes());
//        layoutParams.width = (int) (getResources().getDisplayMetrics().widthPixels * 0.85);
//        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        dialog.getWindow().setAttributes(layoutParams);
//
//        EditText edtBlender = dialog.findViewById(R.id.edtBlender);
//        EditText edtProductionSign = dialog.findViewById(R.id.edtProductionSign);
//        EditText edtOperatorSign = dialog.findViewById(R.id.edtOperatorSign);
//        Button btnSave = dialog.findViewById(R.id.btnSave);
//        TextView txtCompartmentNumber = dialog.findViewById(R.id.txtCompartmentNumber);
//
//        // ✅ Check if TextView exists before using it
//        int currentCompartment = compartmentList.size() + 2; // Start from 1
//        if (txtCompartmentNumber != null) {
//            txtCompartmentNumber.setText("Adding Compartment " + currentCompartment);
//        } else {
//            Log.e("DIALOG_ERROR", "txtCompartmentNumber is NULL. Check dialog_add_compartment.xml!");
//        }
//
//
//        btnSave.setOnClickListener(v -> {
//            String blender = edtBlender.getText().toString().trim();
//            String productionSign = edtProductionSign.getText().toString().trim();
//            String operatorSign = edtOperatorSign.getText().toString().trim();
//
//            if (blender.isEmpty() || productionSign.isEmpty() || operatorSign.isEmpty()) {
//                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
//            } else {
//                compartmentList.add(new Compartment(blender, productionSign, operatorSign,));
//                adapter.notifyDataSetChanged();
//                dialog.dismiss();
//
////                // Automatically insert when 6 compartments are added
////                if (compartmentList.size() == 6) {
////                    insert();
////                }
//            }
//        });
//
//        dialog.show();
//    }
//    private void showAddCompartmentDialog() {
//        if (compartmentList.size() >= 6) {
//            Toast.makeText(this, "Maximum 6 compartments allowed!", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        Dialog dialog = new Dialog(this);
//        dialog.setContentView(R.layout.dialog_add_compartment);
//        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//
//        // Set width and height for a medium-size dialog
//        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
//        layoutParams.copyFrom(dialog.getWindow().getAttributes());
//        layoutParams.width = (int) (getResources().getDisplayMetrics().widthPixels * 0.85);
//        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        dialog.getWindow().setAttributes(layoutParams);
//
//        EditText edtBlender = dialog.findViewById(R.id.edtBlender);
//        EditText edtProductionSign = dialog.findViewById(R.id.edtProductionSign);
//        EditText edtOperatorSign = dialog.findViewById(R.id.edtOperatorSign);
//        Button btnSave = dialog.findViewById(R.id.btnSave);
//
//
//        btnSave.setOnClickListener(v -> {
//            String blender = edtBlender.getText().toString().trim();
//            String productionSign = edtProductionSign.getText().toString().trim();
//            String operatorSign = edtOperatorSign.getText().toString().trim();
//
//
//            if (blender.isEmpty() || productionSign.isEmpty() || operatorSign.isEmpty()) {
//                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
//            } else {
//                // ✅ Add new compartment and update the adapter
//                compartmentList.add(new Compartment(blender, productionSign, operatorSign));
//                adapter.notifyDataSetChanged();
//                dialog.dismiss();
//
//                // ✅ Automatically open next compartment if less than 6
////                if (compartmentList.size() < 6) {
////                    showAddCompartmentDialog(); // Open next compartment
////                }
//            }
//        });
//
//        dialog.show();
//    }
private void showAddCompartmentDialogs(int count) {
    if (compartmentList.size() >= count) {
        Toast.makeText(this, "Maximum " + count + " compartments allowed!", Toast.LENGTH_SHORT).show();
        return;
    }

    showCompartmentDialog(0, count); // Start showing dialogs from index 0
}

    private void showCompartmentDialog(int index, int maxCount) {
        if (index >= maxCount) return; // Stop when required number of dialogs are opened

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_add_compartment);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        // Set width and height for a medium-size dialog
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = (int) (getResources().getDisplayMetrics().widthPixels * 0.85);
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(layoutParams);

        EditText edtBlender = dialog.findViewById(R.id.edtBlender);
        EditText edtProductionSign = dialog.findViewById(R.id.edtProductionSign);
        EditText edtOperatorSign = dialog.findViewById(R.id.edtOperatorSign);
        EditText edproductname = dialog.findViewById(R.id.edtProductname);
        Button btnSave = dialog.findViewById(R.id.btnSave);

        btnSave.setOnClickListener(v -> {
            String blender = edtBlender.getText().toString().trim();
            String productionSign = edtProductionSign.getText().toString().trim();
            String operatorSign = edtOperatorSign.getText().toString().trim();
            String productname = edproductname.getText().toString().trim();


            if (blender.isEmpty() || productionSign.isEmpty() || operatorSign.isEmpty()||productname.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            } else {
                // ✅ Add new compartment and update adapter
                compartmentList.add(new Compartment(blender, productionSign, operatorSign,productname));
                adapter.notifyDataSetChanged();
                dialog.dismiss();

                // ✅ Open the next dialog until reaching the required count
                //showCompartmentDialog(index + 1, maxCount);
            }
        });

        dialog.show();
    }



    public void insert() {
        String outTime = getCurrentTime();
        String inTime = intime.getText().toString();
        String iserialnum = serialnumber.getText().toString();
        String ivehicle = vehiclenumber.getText().toString();
        String iblender = blendernumber.getText().toString();
        String isignofproduction = signproduction.getText().toString();
        String isignofoprator = oprator.getText().toString();
        String iremark = this.remark.getText().toString();
        //String nextu = nextdeptvalue.toString().trim();
        String product = this.remark.getText().toString();

        // Assign each compartment to a separate JSON string
        // ✅ Create Compartment 1 Object
        Compartment compartment1 = new Compartment(iblender, isignofproduction, isignofoprator,product);
        String compartment1String = convertCompartmentToJson(compartment1);

        // Convert each compartment to a JSON string (up to 6 compartments)
        //String compartment1String = (compartmentList.size() > 0) ? convertCompartmentToJson(compartmentList.get(0)) : "";
        String compartment2String = (compartmentList.size() > 1) ? convertCompartmentToJson(compartmentList.get(1)) : "";
        String compartment3String = (compartmentList.size() > 2) ? convertCompartmentToJson(compartmentList.get(2)) : "";
        String compartment4String = (compartmentList.size() > 3) ? convertCompartmentToJson(compartmentList.get(3)) : "";
        String compartment5String = (compartmentList.size() > 4) ? convertCompartmentToJson(compartmentList.get(4)) : "";
        String compartment6String = (compartmentList.size() > 5) ? convertCompartmentToJson(compartmentList.get(5)) : "";

        // Only proceed if at least 1 compartment is filled
//        if (compartmentList.isEmpty()) {
//            Toast.makeText(this, "Please add at least one compartment!", Toast.LENGTH_SHORT).show();
//            return;
//        }

        if (iblender.isEmpty()||isignofproduction.isEmpty()||isignofoprator.isEmpty()) {
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
        }else {
            New_Production_Model_Outward newproductionoutwardmodel = new New_Production_Model_Outward(OutwardId,inTime,
                    outTime,"","","","P",iremark,EmployeId,vehicleType,
                    iserialnum,ivehicle,'L',inOut,EmployeId,"P",
                    compartment1String, compartment2String, compartment3String,
                    compartment4String, compartment5String, compartment6String,movementValueInt,isMultipleVehicle);
            Gson gson = new Gson();
            String jsonRequest = gson.toJson(newproductionoutwardmodel);
            Log.d("API_REQUEST", jsonRequest);
            Call<Boolean> call = outwardTankerLab.newOutwardTankerProduction(newproductionoutwardmodel);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body() != null && response.body() == true) {
                        //Log.e("API_ERROR", "Error Body: " + response.errorBody().toString());
                        Toasty.success(New_Outward_Tanker_Production.this, "Data Inserted Succesfully...!!", Toast.LENGTH_SHORT, true).show();
                        makeNotification(ivehicle, outTime);
                        startActivity(new Intent(New_Outward_Tanker_Production.this, Grid_Outward.class));
                        finish();
                    }else {
                        Log.e("Retrofit", "Error Response Body: " + response.code());
                        Log.e("API_ERROR", "Error Body: " + response.errorBody().toString());
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
                    Toasty.error(New_Outward_Tanker_Production.this, "failed..!", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    public void update() {
        String outTime = getCurrentTime();
        //String nextu = nextdeptvalue.toString().trim();
        String ivehicle = vehiclenumber.getText().toString();
        String iserialnumber = serialnumber.getText().toString();

        // Convert Compartment objects to JSON strings
        String compartment1String = (compartmentList.size() > 0) ? convertCompartmentToJson(compartmentList.get(0)) : "";
        String compartment2String = (compartmentList.size() > 1) ? convertCompartmentToJson(compartmentList.get(1)) : "";
        String compartment3String = (compartmentList.size() > 2) ? convertCompartmentToJson(compartmentList.get(2)) : "";
        String compartment4String = (compartmentList.size() > 3) ? convertCompartmentToJson(compartmentList.get(3)) : "";
        String compartment5String = (compartmentList.size() > 4) ? convertCompartmentToJson(compartmentList.get(4)) : "";
        String compartment6String = (compartmentList.size() > 5) ? convertCompartmentToJson(compartmentList.get(5)) : "";

        // Log the compartment strings to check their format
        Log.d("Compartment JSON", compartment1String);
        Log.d("Compartment JSON", compartment2String);
        Log.d("Compartment JSON", compartment3String);
        Log.d("Compartment JSON", compartment4String);
        Log.d("Compartment JSON", compartment5String);
        Log.d("Compartment JSON", compartment6String);

        // Creating the update model object
        Repet_update_Model updateModel = new Repet_update_Model(
                OutwardId,
                "",
                compartment1String,
                compartment2String,
                compartment3String,
                compartment4String,
                compartment5String,
                compartment6String,
                iserialnumber,
                ivehicle,
                'L',
                inOut,
                vehicleType,
                EmployeId

        );

         //Convert the object to JSON for logging
        Gson gson = new Gson();
        String jsonRequest = gson.toJson(updateModel);
        Log.d("API_REQUEST", jsonRequest);

        // API call for update
        Call<Boolean> call = outwardTankerLab.UpdateOutwardTankerProduction(updateModel);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful() && response.body() != null && response.body()) {
                    Toasty.success(New_Outward_Tanker_Production.this, "Data Updated Successfully!", Toast.LENGTH_SHORT, true).show();
                    makeNotification(ivehicle, outTime);
                    startActivity(new Intent(New_Outward_Tanker_Production.this, Grid_Outward.class));
                    finish();
                } else {
                    Log.e("Retrofit", "Error Response Code: " + response.code());
                    try {
                        Log.e("API_ERROR", "Error Body: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toasty.error(New_Outward_Tanker_Production.this, "Update Failed!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.e("Retrofit", "Failure: " + t.getMessage());
                Toasty.error(New_Outward_Tanker_Production.this, "API Call Failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updatecustomername(){
        String custname = customer.getText().toString();

        updatecustomername updatecustomername = new updatecustomername(OutwardId,custname,EmployeId);

        Call<Boolean> call = outwardTankerLab.updateCustomerName(updatecustomername);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful() && response.body() != null){
                    Toasty.success(New_Outward_Tanker_Production.this, "Data Updated Successfully!", Toast.LENGTH_SHORT, true).show();
                }else {
                    Toasty.error(New_Outward_Tanker_Production.this, "Update Failed!", Toast.LENGTH_SHORT).show();
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
                            String specificrole = "Laboratory";
                            if (specificrole.equals(responseModel.getDepartment())) {
                                token = responseModel.getToken();
                                FcmNotificationsSender notificationsSender = new FcmNotificationsSender(
                                        token,
                                        "Outward Tanker Production Process Done..!",
                                        "Vehicle Number:-" + vehicleNumber + " has completed Production process at " + outTime,
                                        getApplicationContext(),
                                        New_Outward_Tanker_Production.this
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

    public void inprocesprocompletedclick(View view) {
        Intent intent = new Intent(this, OT_Completed_inproc_production.class);
        startActivity(intent);
    }
    private String getCurrentTime() {
        // Get the current time
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(new Date());
    }
    public void new_uttankerproinprocpending(View view) {
        Intent intent = new Intent(this, Grid_Outward.class);
        startActivity(intent);
    }

    /**
     * Converts a single compartment to a JSON string with only required fields.
     */
    private String convertCompartmentToJson(Compartment compartment) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Productname", compartment.getProductname()); // Productname")
            jsonObject.put("Blender", compartment.getBlenderNumber()); // Using only Blender
            jsonObject.put("ProductionSign", compartment.getProductionSign()); // Production Sign
            jsonObject.put("OperatorSign", compartment.getOperatorSign()); // Operator Sign
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "{}"; // Return empty JSON if an error occurs
        }
    }
    private String convertCompartmentToJson2(Compartment compartment) {
        if (compartment == null) return "";

        try {
            return new Gson().toJson(compartment); // ✅ Ensures properly formatted JSON string
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }





    private Compartment parseCompartment(String jsonString) {
        if (jsonString == null || jsonString.isEmpty()) {
            Log.e("JSON_ERROR", "Empty JSON string");
            return null; // Handle empty data safely
        }
        try {
            Gson gson = new Gson();
            return gson.fromJson(jsonString.replace("/",""), Compartment.class);
        } catch (Exception e) {
            Log.e("JSON_ERROR", "Error parsing JSON: " + e.getMessage());
            return null;
        }
    }

//    private void showAddCompartmentDialog(Compartment compartment) {
//        Dialog dialog = new Dialog(this);
//        dialog.setContentView(R.layout.dialog_add_compartment);
//        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//
//        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
//        layoutParams.copyFrom(dialog.getWindow().getAttributes());
//        layoutParams.width = (int) (getResources().getDisplayMetrics().widthPixels * 0.85);
//        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        dialog.getWindow().setAttributes(layoutParams);
//
//        EditText edtBlender = dialog.findViewById(R.id.edtBlender);
//        EditText edtProductionSign = dialog.findViewById(R.id.edtProductionSign);
//        EditText edtOperatorSign = dialog.findViewById(R.id.edtOperatorSign);
//        Button btnSave = dialog.findViewById(R.id.btnSave);
//
//        // Pre-fill the data if available
//        if (compartment != null) {
//            Log.d("DEBUG", "Setting Blender: " + compartment.getBlenderNumber());
//            Log.d("DEBUG", "Setting ProductionSign: " + compartment.getProductionSign());
//            Log.d("DEBUG", "Setting OperatorSign: " + compartment.getOperatorSign());
//
//
//            edtBlender.setText(compartment.getBlenderNumber());
//            edtProductionSign.setText(compartment.getProductionSign());
//            edtOperatorSign.setText(compartment.getOperatorSign());
//
//            // Disable editing if data exists
//            edtBlender.setEnabled(compartment.getBlenderNumber().isEmpty());
//            edtProductionSign.setEnabled(compartment.getProductionSign().isEmpty());
//            edtOperatorSign.setEnabled(compartment.getOperatorSign().isEmpty());
//        } else {
//            Log.e("DEBUG", "Compartment object is NULL in dialog!");
//        }
//
//        btnSave.setOnClickListener(v -> {
//            String blender = edtBlender.getText().toString().trim();
//            String productionSign = edtProductionSign.getText().toString().trim();
//            String operatorSign = edtOperatorSign.getText().toString().trim();
//
//            if (blender.isEmpty() || productionSign.isEmpty() || operatorSign.isEmpty()) {
//                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
//            } else {
//                compartmentList.add(new Compartment(blender, productionSign, operatorSign));
//                adapter.notifyDataSetChanged();
//                dialog.dismiss();
//            }
//        });
//
//        dialog.show();
//    }


}