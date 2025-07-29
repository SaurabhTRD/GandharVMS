package com.android.gandharvms.Outward_Tanker_Production_forms;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridLayout;
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
import com.android.gandharvms.Outward_Tanker_Security.Grid_Outward;
import com.android.gandharvms.Outward_Tanker_Security.Outward_RetroApiclient;
import com.android.gandharvms.ProductListData;
import com.android.gandharvms.R;
import com.android.gandharvms.Util.dialogueprogreesbar;
import com.android.gandharvms.outward_Tanker_Lab_forms.Lab_Model__Outward_Tanker;
import com.android.gandharvms.outward_Tanker_Lab_forms.Outward_Tanker_Lab;
import com.google.android.material.textfield.TextInputLayout;
import com.google.common.reflect.TypeToken;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class Outward_Tanker_Production_Multiple extends NotificationCommonfunctioncls {
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final String EmployeId = Global_Var.getInstance().EmpId;
    public char inOut = Global_Var.getInstance().InOutType;
    public String procompart;
    public String compartme, updateserialnumber, updatevehiclenumber;
    public CheckBox checkBoxMultipleVehicle;
    public int compartmentArraycount;
    public int compartmentcount;
    public int currentCompartment;
    EditText serialnumber, vehiclenumber, oanumber, product, customer, location, howqty, transporter, intime, blendernumber, signproduction, oprator, remark, etbillremark, etproduct;
    Button btnsubmit, btncompletd, btnupdate;
    ArrayAdapter<String> nextdeptdrop;
    Map<String, String> nextdeptmapping = new HashMap<>();
    String nextdeptvalue = "W";
    String movementvalue = "";
    AutoCompleteTextView dept, vehmovement;
    Map<String, Integer> Movement = new HashMap<>();
    ArrayAdapter<String> Movementdrop;
    String deptNumericValue = "W";
    LinearLayout productDetailsLayout;
    EditText etProductName;
    dialogueprogreesbar dialogHelper = new dialogueprogreesbar();
    private Outward_Tanker_Lab outwardTankerLab;
    private String token;
    private LoginMethod userDetails;
    private int oploutwardid = 0;
    private int OutwardId;
    private List<Compartment> compartmentList;
    private CompartmentAdapter adapter;
    private Button btnAddCompartment;
    private RecyclerView recyclerView;
    // ✅ Declare Global Variable
    private int movementValueInt = 0; // Default value
    // ✅ Declare Global Variable
    private boolean isMultipleVehicle = false; // Default is false
    private ProductAdapter productAdapter; // ✅ Declare globally
    public String productdtls;
    public  List<String> compartmentsJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_outward_tanker_production_multiple);
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
        etbillremark = findViewById(R.id.etprducBillingRemark);
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
                String time = format.format(calendar.getTime());
                intime.setText(time);
            }
        });


        checkBoxMultipleVehicle = findViewById(R.id.checkBoxMultipleVehicle);
        TextInputLayout nextDeptLayout = findViewById(R.id.layout_next_dept); // TextInputLayout of AutoCompleteTextView
        TextInputLayout vehicleMovementLayout = findViewById(R.id.layout_vehicle_movement); // TextInputLayout of AutoCompleteTextView

        // ✅ Hide both dropdown fields initially
        nextDeptLayout.setVisibility(View.GONE);
        vehicleMovementLayout.setVisibility(View.GONE);

        // ✅ Listen for CheckBox changes
//        checkBoxMultipleVehicle.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            // ✅ Ensure `isMultipleVehicle` is true if movementValueInt > 1
//            isMultipleVehicle = (movementValueInt > 1) || isChecked;
//
//            vehicleMovementLayout.setVisibility(isMultipleVehicle ? View.VISIBLE : View.GONE); // ✅ Update visibility
//            Log.d("MULTIPLE_VEHICLE", "isMultipleVehicle: " + isMultipleVehicle);
//        });


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
                    Toasty.success(Outward_Tanker_Production_Multiple.this, "NetWeighUnitofMeasurement : " + indusdept + " Selected", Toast.LENGTH_SHORT).show();
                } else {
                    Toasty.error(Outward_Tanker_Production_Multiple.this, "Default NetWeighUnitofMeasurement : " + "NA", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //Movement.put("Compartment1", 1);
//        Movement.put("Compartment2", 2);
//        Movement.put("Compartment3", 3);
//        Movement.put("Compartment4", 4);
//        Movement.put("Compartment5", 5);
//        Movement.put("Compartment6", 6);

//        vehmovement = findViewById(R.id.vehicelmovement);
//        ArrayAdapter<String> Movementdrop = new ArrayAdapter<>(this, R.layout.indus_nextdept, new ArrayList<>(Movement.keySet()));
//        vehmovement.setAdapter(Movementdrop);
//
//// ✅ Handle Selection Correctly
//        vehmovement.setOnItemClickListener((parent, view, position, id) -> {
//            String movement = parent.getItemAtPosition(position).toString();
//
//            // ✅ Get Integer Value Directly Without Conversion
//            if (Movement.containsKey(movement)) {
//                movementValueInt = Movement.get(movement);  // ✅ Now it's already an integer
//                Log.d("MOVEMENT_SELECTED", "Selected Value: " + movementValueInt);
//            } else {
//                Log.e("MOVEMENT_ERROR", "Invalid selection: " + movement);
//            }
//        });


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
        productDetailsLayout = findViewById(R.id.productDetailsLayout);
        etProductName = findViewById(R.id.etproductottankerprodcut);
        EditText etBlenderNumber = findViewById(R.id.elnewblendingno);
        EditText etRemark = findViewById(R.id.etnewremark);
    }

    public void onProductClick(String productName) {
        // Show the hidden layout

        productDetailsLayout.setVisibility(View.VISIBLE);
        productDetailsLayout.setEnabled(true);

        // Set product name in EditText
        etProductName.setText(productName);

        // Clear previous values (if needed)
//        etBlenderNumber.setText("");
//        etRemark.setText("");
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
                        productdtls = data.getProductQTYUOMOA();

                        Set<String> insertedProducts = new HashSet<>();
                        for (Compartment compartment : compartmentList) {
                            insertedProducts.add(compartment.getProductname().trim().toLowerCase());
                        }


                        List<Product> productList = new ArrayList<>(); // ✅ Always initialize the list

                             // 🔹 Log the JSON before parsing
                        Log.d("JSON_DEBUG", "Raw JSON Data: " + data.getProductQTYUOMOA());

                        try {
                            String jsonString = data.getProductQTYUOMOA();

                            if (jsonString == null || jsonString.isEmpty()) {
                                Log.e("JSON_DEBUG", "JSON data is null or empty");
                            } else {
                                // ✅ Fix: Handle cases where JSON is inside a string
                                if (!jsonString.startsWith("[")) {
                                    jsonString = new JSONTokener(jsonString).nextValue().toString();
                                }

                                JSONArray jsonArray = new JSONArray(jsonString);
                                int productCount = jsonArray.length(); // ✅ Get product count
                                movementValueInt = productCount; // ✅ Set movementValueInt dynamically
                                isMultipleVehicle = productCount > 1; // ✅ Set isMultipleVehicle dynamically
                                Log.d("JSON_DEBUG", "Total Products Found: " + jsonArray.length()); // ✅ Log the number of items

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject obj = jsonArray.getJSONObject(i);
                                    if (obj.has("ProductName")) {
                                        String productName = obj.getString("ProductName");
                                        String oaNumber = obj.optString("OANumber", ""); // ✅ Create the variable here
                                        //productList.add(new Product(productName));
                                        // ✅ Step 2: Only add products that are NOT in `compartmentList`
                                        if (!insertedProducts.contains(productName)) {
                                            productList.add(new Product(obj.getString("ProductName"), oaNumber)); // Keep original case
                                            Log.d("JSON_DEBUG", "Added Product: " + obj.getString("ProductName"));
                                        } else {
                                            Log.d("JSON_DEBUG", "Skipping Inserted Product: " + obj.getString("ProductName"));
                                        }

                                        // 🔹 Log each product
                                        Log.d("JSON_DEBUG", "Added Product: " + productName);
                                    } else {
                                        Log.e("JSON_DEBUG", "ProductName key not found in object at index " + i);
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            Log.e("JSON_ERROR", "JSON Parsing Error: " + e.getMessage());
                            e.printStackTrace();
                        }

                        // ✅ 2. Fetch and parse Procompartment Data
//                        List<Compartment> compartmentDataList = new ArrayList<>();
                        Map<Integer, Compartment> compartmentDataMap = new HashMap<>();
                         compartmentsJson = Arrays.asList(
                                data.getProcompartment1(), data.getProcompartment2(), data.getProcompartment3(),
                                data.getProcompartment4(), data.getProcompartment5(), data.getProcompartment6()
                        );

                        for (int i = 0; i < compartmentsJson.size(); i++) {
                            String json = compartmentsJson.get(i);
                            if (json != null && !json.trim().isEmpty()) {
                                Compartment compartment = parseCompartment(json);
                                if (compartment != null) {
                                    compartmentDataMap.put(i, compartment); // 🔥 Index-based mapping
                                    Log.d("DEBUG", "Added Compartment at index " + i + ": " + compartment.getProductname());
                                }
                            }
                        }

//                        for (String json : compartmentsJson) {
//                            if (json != null && !json.trim().isEmpty()) {
//                                Compartment compartment = parseCompartment(json);
//                                if (compartment != null) {
//                                    compartmentDataList.add(compartment);
//                                    Log.d("DEBUG", "Added Procompartment: " + compartment.getProductname());
//                                }
//                            }
//                        }

//                          ✅ Check if productList is empty before setting the adapter
                        if (productList.isEmpty()) {
                            Log.e("RecyclerView", "No products found after parsing.");
                        } else {
                            RecyclerView productRecyclerView = findViewById(R.id.productRecyclerView);
                            productRecyclerView.setLayoutManager(new LinearLayoutManager(Outward_Tanker_Production_Multiple.this));

//                            ProductAdapter adapter = new ProductAdapter(productList, compartmentDataList,OutwardId,productdtls);
                            ProductAdapter adapter = new ProductAdapter(productList, compartmentDataMap, OutwardId, productdtls);

                            productRecyclerView.setAdapter(adapter);
                            Log.d("RecyclerView", "Adapter set with " + productList.size() + " products.");
                        }


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
                        Toasty.error(Outward_Tanker_Production_Multiple.this, "This Vehicle Number Is Not Available..!", Toast.LENGTH_SHORT).show();
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


            if (blender.isEmpty() || productionSign.isEmpty() || operatorSign.isEmpty() || productname.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            } else {
                // ✅ Add new compartment and update adapter
                compartmentList.add(new Compartment(blender, productionSign, operatorSign, productname));
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


        int selectedCompartmentIndex = getSelectedCompartmentIndex(); // ✅ Get selected compartment index

        if (selectedCompartmentIndex == -1) {
            Toast.makeText(this, "Please select a compartment before submitting!", Toast.LENGTH_SHORT).show();
            return; // ❌ Stop execution if no compartment is selected
        }

        List<Product> productList = ProductAdapter.getProductList(); // ✅ Get product list

        if (selectedCompartmentIndex >= productList.size()) {
            Toast.makeText(this, "Invalid selection!", Toast.LENGTH_SHORT).show();
            return;
        }

        Product selectedProduct = productList.get(selectedCompartmentIndex); // ✅ Now safe to access

        if (selectedProduct.getBlenderNumber().isEmpty() || selectedProduct.getOperatorSign().isEmpty() || selectedProduct.getInTime().isEmpty()) {
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
            return;
        }

        // ✅ Convert to JSON
//        Compartment selectedCompartment = new Compartment(
//                selectedProduct.getBlenderNumber(),
//                selectedProduct.getOperatorSign(),
//                selectedProduct.getOperatorSign(),
//                selectedProduct.getProductName()
//        );
        // ✅ Check if all fields are filled
        if (selectedProduct.getProductName().isEmpty() ||
                selectedProduct.getInTime().isEmpty() ||
                selectedProduct.getBlenderNumber().isEmpty() ||
                selectedProduct.getOperatorSign().isEmpty() ||
                selectedProduct.getSignOfProduction().isEmpty() ||
                selectedProduct.getRemark().isEmpty()
        ) {
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
            return;
        }

        String selectedCompartmentJson = convertCompartmentToJson(selectedProduct);
        Log.d("JSON_DEBUG", "Generated JSON: " + selectedCompartmentJson);

        // ✅ Initialize all compartments as empty
        String compartment1String = "", compartment2String = "", compartment3String = "";
        String compartment4String = "", compartment5String = "", compartment6String = "";

        // ✅ Assign data to the selected compartment
        switch (selectedCompartmentIndex) {
            case 0:
                compartment1String = selectedCompartmentJson;
                break;
            case 1:
                compartment2String = selectedCompartmentJson;
                break;
            case 2:
                compartment3String = selectedCompartmentJson;
                break;
            case 3:
                compartment4String = selectedCompartmentJson;
                break;
            case 4:
                compartment5String = selectedCompartmentJson;
                break;
            case 5:
                compartment6String = selectedCompartmentJson;
                break;
        }


        if (selectedCompartmentJson.isEmpty()) {
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
        } else {
            New_Production_Model_Outward newproductionoutwardmodel = new New_Production_Model_Outward(OutwardId, inTime,
                    outTime, "", "", "", "P", iremark, EmployeId, vehicleType,
                    iserialnum, ivehicle, 'L', inOut, EmployeId, "P",
                    compartment1String, compartment2String, compartment3String,
                    compartment4String, compartment5String, compartment6String, movementValueInt, isMultipleVehicle);
            Gson gson = new Gson();
            String jsonRequest = gson.toJson(newproductionoutwardmodel);
            Log.d("API_REQUEST", jsonRequest);
            dialogHelper.showConfirmationDialog(this, () -> {
                dialogHelper.showProgressDialog(this); // Show progress when confirmed
                Call<Boolean> call = outwardTankerLab.newOutwardTankerProduction(newproductionoutwardmodel);
                call.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if (response.isSuccessful() && response.body() != null && response.body()) {
                            //Log.e("API_ERROR", "Error Body: " + response.errorBody().toString());
                            dialogHelper.hideProgressDialog(); // Hide after response
                            Toasty.success(Outward_Tanker_Production_Multiple.this, "Data Inserted Succesfully...!!", Toast.LENGTH_SHORT, true).show();
                            makeNotification(ivehicle, outTime);
                            startActivity(new Intent(Outward_Tanker_Production_Multiple.this, Grid_Outward.class));
                            finish();
                        } else {
                            Log.e("Retrofit", "Error Response Body: " + response.code());
                            Log.e("API_ERROR", "Error Body: " + response.errorBody().toString());
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
                        Toasty.error(Outward_Tanker_Production_Multiple.this, "failed..!", Toast.LENGTH_SHORT).show();
                    }
                });
            });
        }

    }



    public void update() {
        String outTime = getCurrentTime();
        String ivehicle = vehiclenumber.getText().toString();
        String iserialnumber = serialnumber.getText().toString();

//        List<String> updatecompartmentsJson = compartmentsJson;
//
//        List<Product> productList = ProductAdapter.getProductList();
//        if (productList == null || productList.isEmpty()) {
//            Toast.makeText(this, "Product list is empty!", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        List<String> compartmentDataList = new ArrayList<>(Arrays.asList("", "", "", "", "", "")); // ✅ Proper mutable list
//
//        for (int i = 0; i < productList.size(); i++) {
//            Product p = productList.get(i);
//            boolean isFilled = p != null &&
//                    !TextUtils.isEmpty(p.getBlenderNumber()) &&
//                    !TextUtils.isEmpty(p.getSignOfProduction()) &&
//                    !TextUtils.isEmpty(p.getOperatorSign());
//
//            if (isFilled) {
//                String json = convertCompartmentToJson(p);
//                compartmentDataList.set(i, json); // ✅ No casting needed
//                Log.d("UPDATE_COMPARTMENT", "Compartment " + (i + 1) + " added: " + json);
//            } else {
//                Log.d("SKIPPED_COMPARTMENT", "Compartment " + (i + 1) + " is empty or incomplete, skipping.");
//            }
//        }
//
//
//        Log.d("FINAL_JSON", "Prepared Compartment List: " + new Gson().toJson(compartmentDataList));

        // Start with previous data
        List<String> updatecompartmentsJson = new ArrayList<>(Arrays.asList("", "", "", "", "", ""));

        if (compartmentsJson != null) {
            for (int i = 0; i < compartmentsJson.size() && i < 6; i++) {
                String existing = compartmentsJson.get(i);
                if (existing != null && !existing.trim().isEmpty()) {
                    updatecompartmentsJson.set(i, existing); // Preserve old value
                }
            }
        }

// Now update with current product input
        List<Product> productList = ProductAdapter.getProductList();
        if (productList == null || productList.isEmpty()) {
            Toast.makeText(this, "Product list is empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        for (int i = 0; i < productList.size() && i < 6; i++) {
            Product p = productList.get(i);
            boolean isFilled = p != null &&
                    !TextUtils.isEmpty(p.getBlenderNumber()) &&
                    !TextUtils.isEmpty(p.getSignOfProduction()) &&
                    !TextUtils.isEmpty(p.getOperatorSign());

            if (isFilled) {
                String json = convertCompartmentToJson(p);
                updatecompartmentsJson.set(i, json); // ✅ Overwrite with new if available
                Log.d("UPDATE_COMPARTMENT", "Compartment " + (i + 1) + " added: " + json);
            } else {
                Log.d("SKIPPED_COMPARTMENT", "Compartment " + (i + 1) + " is empty or incomplete, using existing.");
            }
        }

        Log.d("FINAL_JSON", "Prepared Compartment List: " + new Gson().toJson(updatecompartmentsJson));


        // Build update model
        Repet_update_Model updateModel = new Repet_update_Model(
                OutwardId,
                "",
//                compartmentDataList.get(0),
//                compartmentDataList.get(1),
//                compartmentDataList.get(2),
//                compartmentDataList.get(3),
//                compartmentDataList.get(4),
//                compartmentDataList.get(5),
                updatecompartmentsJson.get(0),
                updatecompartmentsJson.get(1),
                updatecompartmentsJson.get(2),
                updatecompartmentsJson.get(3),
                updatecompartmentsJson.get(4),
                updatecompartmentsJson.get(5),
                iserialnumber,
                ivehicle,
                'L',
                inOut,
                vehicleType,
                EmployeId
        );

        // Make API call
        Gson gson = new Gson();
        String jsonRequest = gson.toJson(updateModel);
        Log.d("API_REQUEST", jsonRequest);

        dialogHelper.showConfirmationDialog(this, () -> {
            dialogHelper.showProgressDialog(this);
            Call<Boolean> call = outwardTankerLab.UpdateOutwardTankerProduction(updateModel);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    dialogHelper.hideProgressDialog();
                    if (response.isSuccessful() && Boolean.TRUE.equals(response.body())) {
                        Toasty.success(Outward_Tanker_Production_Multiple.this, "Data Updated Successfully!", Toast.LENGTH_SHORT, true).show();
                        makeNotification(ivehicle, outTime);
                        startActivity(new Intent(Outward_Tanker_Production_Multiple.this, Grid_Outward.class));
                        finish();
                    } else {
                        Log.e("Retrofit", "Error Response Code: " + response.code());
                        try {
                            Log.e("API_ERROR", "Error Body: " + response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Toasty.error(Outward_Tanker_Production_Multiple.this, "Update Failed!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    dialogHelper.hideProgressDialog();
                    Log.e("Retrofit", "Failure: " + t.getMessage());
                    Toasty.error(Outward_Tanker_Production_Multiple.this, "API Call Failed!", Toast.LENGTH_SHORT).show();
                }
            });
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
                                        Outward_Tanker_Production_Multiple.this
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
    private String convertCompartmentToJson1(Compartment compartment) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Productname", compartment.getProductname()); // Productname")
            jsonObject.put("intime", compartment.getIntime()); // Productname")
            jsonObject.put("Blender", compartment.getBlenderNumber()); // Using only Blender
            jsonObject.put("ProductionSign", compartment.getProductionSign()); // Production Sign
            jsonObject.put("OperatorSign", compartment.getOperatorSign()); // Operator Sign
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "{}"; // Return empty JSON if an error occurs
        }
    }

    private String convertCompartmentToJson(Product product) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ProductName", product.getProductName()); // ✅ Product Name
            jsonObject.put("InTime", product.getInTime()); // ✅ In-Time
            jsonObject.put("Blender", product.getBlenderNumber()); // ✅ Blender Number
            jsonObject.put("ProductionSign", product.getSignOfProduction()); // ✅ Production Sign
            jsonObject.put("OperatorSign", product.getOperatorSign()); // ✅ Operator Sign
            jsonObject.put("Remark", product.getRemark()); // ✅ Remark

            return jsonObject.toString(); // ✅ Return JSON string
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
            return gson.fromJson(jsonString.replace("/", ""), Compartment.class);
        } catch (Exception e) {
            Log.e("JSON_ERROR", "Error parsing JSON: " + e.getMessage());
            return null;
        }
    }

    private int getSelectedCompartmentIndex() {
        List<Product> productList = ProductAdapter.getProductList(); // ✅ Get updated product list

        for (int i = 0; i < productList.size(); i++) {
            Log.d("SELECTION_DEBUG", "Product: " + productList.get(i).getProductName() +
                    " | isSelected: " + productList.get(i).isSelected());

            if (productList.get(i).isSelected()) { // ✅ Check if a product is selected
                return i; // ✅ Return selected compartment index (0-5)
            }
        }

        Log.e("SELECTION_DEBUG", "No product selected!");
        return -1; // ❌ No compartment selected
    }

    private List<String> getExistingCompartmentData(Repet_update_Model existingModel) {
        List<String> existingData = new ArrayList<>();

        // ✅ Use the existing model data instead of calling the API again
        existingData.add(existingModel.getCompartment1() != null ? existingModel.getCompartment1() : "");
        existingData.add(existingModel.getCompartment2() != null ? existingModel.getCompartment2() : "");
        existingData.add(existingModel.getCompartment3() != null ? existingModel.getCompartment3() : "");
        existingData.add(existingModel.getCompartment4() != null ? existingModel.getCompartment4() : "");
        existingData.add(existingModel.getCompartment5() != null ? existingModel.getCompartment5() : "");
        existingData.add(existingModel.getCompartment6() != null ? existingModel.getCompartment6() : "");

        Log.d("EXISTING_DATA", "Fetched compartments: " + existingData);
        return existingData;
    }


}