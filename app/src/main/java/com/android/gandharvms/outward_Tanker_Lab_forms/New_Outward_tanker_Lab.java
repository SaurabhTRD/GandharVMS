package com.android.gandharvms.outward_Tanker_Lab_forms;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gandharvms.FcmNotificationsSender;
import com.android.gandharvms.Global_Var;
import com.android.gandharvms.Inward_Truck_store.Inward_Truck_Store;
import com.android.gandharvms.LoginWithAPI.LoginMethod;
import com.android.gandharvms.LoginWithAPI.ResponseModel;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.NotificationAlerts.NotificationCommonfunctioncls;
import com.android.gandharvms.Outward_Tanker;
import com.android.gandharvms.Outward_Tanker_Production_forms.Compartment;
import com.android.gandharvms.Outward_Tanker_Production_forms.CompartmentAdapter;
import com.android.gandharvms.Outward_Tanker_Production_forms.New_Outward_Tanker_Production;
import com.android.gandharvms.Outward_Tanker_Production_forms.Repet_update_Model;
import com.android.gandharvms.Outward_Tanker_Security.Grid_Outward;
import com.android.gandharvms.Outward_Tanker_Security.Outward_RetroApiclient;
import com.android.gandharvms.ProductListData;
import com.android.gandharvms.R;
import com.google.common.reflect.TypeToken;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class New_Outward_tanker_Lab extends NotificationCommonfunctioncls {

    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final String EmployeId = Global_Var.getInstance().EmpId;
    public char inOut = Global_Var.getInstance().InOutType;
    private Outward_Tanker_Lab outwardTankerLab;
    private String token;
    private LoginMethod userDetails;
    private int oploutwardid = 0;
    private int OutwardId;
    EditText newlseralnum,newlvehiclenum,newloanum,newlprodcut,newlcustomername,newldestination,newlquantity,newltransporter,
    newlintime,newlviscosity,newldentinity,newlbatchnum,newlqcofficer,newlremarks,billremark,proremark;
    Button btnsubmit,btnupdate;
    public boolean isCheck = false;
    public int compartmentArraycount,copartmentcount;
    public String procompartment1,procompartment2,procompartment3,procompartment4,procompartment5,procompartment6;
    public String compartment1,compartment2,compartment3,compartment4,compartment5,compartment6;
    private List<Lab_compartment_model> compartmentList;
    private RecyclerView recyclerView;
    private LabCompartmentAdapter adapter;
    public First_LabCompartmentAdapter firstLabCompartmentAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_outward_tanker_lab);
        setupHeader();
        outwardTankerLab = Outward_RetroApiclient.outwardTankerLab();
        userDetails = RetroApiClient.getLoginApi();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        FirebaseMessaging.getInstance().subscribeToTopic(token);

        newlseralnum = findViewById(R.id.etnewlserialnumber);
        newlvehiclenum = findViewById(R.id.etnewlvehicleno);
        newloanum = findViewById(R.id.etnewloanumer);
        newlprodcut = findViewById(R.id.etnewlproductname);
        newlcustomername = findViewById(R.id.etnewlcustname);
        newldestination = findViewById(R.id.etnewllocation);
        newlquantity = findViewById(R.id.etnewlhowmuch);
        newltransporter = findViewById(R.id.etnewltransportername);
        newlintime = findViewById(R.id.etinewlntime);
        newlviscosity = findViewById(R.id.elnewlviscosity);
        newldentinity = findViewById(R.id.elnewldensity);
        newlbatchnum = findViewById(R.id.elnewlbatchnumber);
        newlqcofficer = findViewById(R.id.elnewlqcofficer);
        newlremarks = findViewById(R.id.elnewblremark);
        billremark = findViewById(R.id.etbillingremark);
        proremark = findViewById(R.id.etproductionremark);


        btnsubmit = findViewById(R.id.etouttankerlabsubmit);
        btnupdate = findViewById(R.id.updatetankerlab);
        btnupdate.setVisibility(View.GONE);
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
        newlintime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                String time =  format.format(calendar.getTime());
                newlintime.setText(time);
            }
        });
        if (getIntent().hasExtra("vehiclenum")) {
            FetchVehicleDetails(getIntent().getStringExtra("vehiclenum"), Global_Var.getInstance().MenuType, nextProcess, inOut);
        }
        newlvehiclenum.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    FetchVehicleDetails(newlvehiclenum.getText().toString().trim(), vehicleType, nextProcess, inOut);
                }
            }
        });
        recyclerView = findViewById(R.id.recyclerView_labitem);
        compartmentList = new ArrayList<>();
        //List<Compartment> compartmentList = new ArrayList<>();
        // ✅ Pass `this` (Context) to Adapter
        adapter = new LabCompartmentAdapter(this, compartmentList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator()); // Smooth animations
//        adapter = new LabCompartmentAdapter(compartmentList);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(adapter);
//        recyclerView.setItemAnimator(new DefaultItemAnimator()); // Smooth animations
    }

    private void FetchVehicleDetails(@NonNull String vehicleNo, String vehicleType, char nextProcess, char inOut) {
        Call<Lab_Model__Outward_Tanker> call = outwardTankerLab.fetchlab(vehicleNo, vehicleType, nextProcess, inOut);
        call.enqueue(new Callback<Lab_Model__Outward_Tanker>() {
            @Override
            public void onResponse(Call<Lab_Model__Outward_Tanker> call, Response<Lab_Model__Outward_Tanker> response) {
                if (response.isSuccessful()){
                    Lab_Model__Outward_Tanker data = response.body();
                    if (data.getVehicleNumber() != null && data.getVehicleNumber()!= ""){
                        oploutwardid = data.getOplOutwardId();
                        OutwardId = data.getOutwardId();
                        newlseralnum.setText(data.getSerialNumber());
                        newlseralnum.setEnabled(false);
                        newlvehiclenum.setText(data.getVehicleNumber());
                        newlvehiclenum.setEnabled(false);
                        newloanum.setText(data.getOAnumber());
                        newloanum.setEnabled(false);
                        newlprodcut.setText(data.getProductName());
                        newlprodcut.setEnabled(false);
                        newlcustomername.setText(data.getCustomerName());
                        newlcustomername.setEnabled(false);
                        newldestination.setText(data.getLocation());
                        newldestination.setEnabled(false);
                        newlquantity.setText(String.valueOf(data.getHowMuchQuantityFilled()));
                        newlquantity.setEnabled(false);
                        newltransporter.setText(data.getTransportName());
                        newltransporter.setEnabled(false);
                        billremark.setText(data.getTankerBillingRemark());
                        billremark.setEnabled(false);
                        proremark.setText(data.getTankerProRemark());
                        proremark.setEnabled(false);
//                        procompartment1 = data.getProcompartment1();
//                        procompartment2 = data.getProcompartment2();
//                        procompartment3 = data.getProcompartment3();
//                        procompartment4 = data.getProcompartment4();
//                        procompartment5 = data.getProcompartment5();
//                        procompartment6 = data.getProcompartment6();
                        String extraMaterialsJson = data.getProductQTYUOMOA();
                        Log.d("JSON Debug", "Extra Materials JSON: " + extraMaterialsJson);
                        List<ProductListData> extraMaterials = parseExtraMaterials(extraMaterialsJson);
                        Log.d("JSON Debug", "Parsed Extra Materials Size: " + extraMaterials.size());
                        createExtraMaterialViews(extraMaterials);
                        isCheck = data.getisCheck();
                        copartmentcount = data.getCompartmentCount();
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

                        // ✅ Show First Compartment (procompartment1)
                        if (data.getProcompartment1() != null && !data.getProcompartment1().isEmpty()) {
                            Lab_compartment_model labfirstCompartment = parseCompartment(data.getProcompartment1());
                            if (labfirstCompartment != null) {
                                showSingleCompartmentCard(labfirstCompartment);
                            }
                        } else {
                            Log.e("COMPARTMENT", "No Compartment 1 Data Available");
                        }
                        List<String> compartmentsJson = Arrays.asList(
//                                data.getProcompartment1(),
                                data.getProcompartment2(),
                                data.getProcompartment3(),
                                data.getProcompartment4(),
                                data.getProcompartment5(),
                                data.getProcompartment6()
                        );
                        for (String json : compartmentsJson) {
                            Lab_compartment_model labCompartmentModel = parseCompartment(json);
                            if (labCompartmentModel != null) {
                                compartmentList.add(labCompartmentModel);
                                adapter.notifyDataSetChanged();
                                // 🔹 Show Update or Submit button based on compartment data
                            }
                        }

                        // ✅ Show "Update" Button If More Than One Compartment Exists
                        if (compartmentArraycount > 1) {
                            btnupdate.setVisibility(View.VISIBLE);
                            btnsubmit.setVisibility(View.GONE);
                            newlintime.setVisibility(View.GONE);
                            newlviscosity.setVisibility(View.GONE);
                            newldentinity.setVisibility(View.GONE);
                            newlbatchnum.setVisibility(View.GONE);
                            newlqcofficer.setVisibility(View.GONE);
                            newlremarks.setVisibility(View.GONE);
                            //newlintime,newlviscosity,newldentinity,newlbatchnum,newlqcofficer,newlremarks
                            Log.d("BUTTON_DEBUG", "Showing UPDATE button");
                        } else {
                            btnupdate.setVisibility(View.GONE);
                            btnsubmit.setVisibility(View.VISIBLE);
                            newlintime.setVisibility(View.VISIBLE);
                            newlviscosity.setVisibility(View.VISIBLE);
                            newldentinity.setVisibility(View.VISIBLE);
                            newlbatchnum.setVisibility(View.VISIBLE);
                            newlqcofficer.setVisibility(View.VISIBLE);
                            newlremarks.setVisibility(View.VISIBLE);
                            Log.d("BUTTON_DEBUG", "Showing SUBMIT button");
                        }

                    }else {
                        Toasty.error(New_Outward_tanker_Lab.this, "This Vehicle Number Is Not Available..!", Toast.LENGTH_SHORT).show();
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
    // ✅ Helper Function to Add Non-Empty Compartments
    private void addCompartmentIfNotEmpty(String compartmentJson) {
        if (compartmentJson != null && !compartmentJson.isEmpty()) {
            compartmentList.add(parseCompartment(compartmentJson));
        }
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
        LinearLayout linearLayout = findViewById(R.id.layout_productlistitinlaboratory); // Ensure this is the correct ID

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



    public void insert()
    {
        String outTime = getCurrentTime();
        String inTime = newlintime.getText().toString();
        String iserialnum = newlseralnum.getText().toString();
        String ivehicle = newlvehiclenum.getText().toString();
        String iviscosity = newlviscosity.getText().toString();
        String idensity = newldentinity.getText().toString();
        BigDecimal objdensity = new BigDecimal(idensity);
        String ibatchnum = newlbatchnum.getText().toString();
        String iqcofficer = newlqcofficer.getText().toString();
        String iremarks = newlremarks.getText().toString();

        // ✅ Determine I_O based on compartmentcount
        char I_O_Value;
        if (isCheck == true && compartmentArraycount < copartmentcount) {
            I_O_Value = 'I';  // If exactly 1 compartment and copartmentcount is 0, pass 'O'
        } else {
            I_O_Value = 'O';  // Default case (fallback)
        }

        if (iviscosity.isEmpty() || idensity.isEmpty()|| ibatchnum.isEmpty()){
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
        }else {
            New_Lab_Model_OutwardTanker newLabModelOutwardTanker = new New_Lab_Model_OutwardTanker(OutwardId,inTime,outTime,iviscosity,objdensity,
                    ibatchnum,iqcofficer,iremarks,EmployeId,"P",iserialnum,ivehicle,'W',I_O_Value,vehicleType,EmployeId);
            Call<Boolean> call = outwardTankerLab.newOutwardTankerLaboratory(newLabModelOutwardTanker);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body() != null && response.body() == true){
                        Toasty.success(New_Outward_tanker_Lab.this, "Data Inserted Succesfully...!!", Toast.LENGTH_SHORT, true).show();
                        makeNotification(ivehicle, outTime);
                        startActivity(new Intent(New_Outward_tanker_Lab.this, Grid_Outward.class));
                        finish();
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
                    Toasty.error(New_Outward_tanker_Lab.this, "failed..!", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    public void update1(){
        String iserialnum = newlseralnum.getText().toString();
        String ivehicle = newlvehiclenum.getText().toString();

        // ✅ Ensure data in `compartmentList` is up-to-date before sending to API
        for (int i = 0; i < compartmentList.size(); i++) {
            View view = recyclerView.getLayoutManager().findViewByPosition(i);
            if (view != null) {
                EditText edtViscosity = view.findViewById(R.id.edtViscosity);
                EditText edtDensity = view.findViewById(R.id.edtDensity);
                EditText edtBatchNumber = view.findViewById(R.id.edtBatchNumber);
                EditText edtQCOfficer = view.findViewById(R.id.edtQCOfficer);
                EditText edtRemark = view.findViewById(R.id.edtRemark);

                Lab_compartment_model compartment = compartmentList.get(i);
                compartment.setViscosity(edtViscosity.getText().toString());
                compartment.setDensity(edtDensity.getText().toString());
                compartment.setBatchNumber(edtBatchNumber.getText().toString());
                compartment.setQcOfficer(edtQCOfficer.getText().toString());
                compartment.setRemark(edtRemark.getText().toString());
            }
        }

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

        Repet_update_Model repetUpdateModel = new Repet_update_Model(oploutwardid,"",
                compartment1String,compartment2String,compartment3String,compartment4String,compartment5String,compartment6String,
                iserialnum,ivehicle,'W',inOut,vehicleType,EmployeId);
        //Convert the object to JSON for logging
        Gson gson = new Gson();
        String jsonRequest = gson.toJson(repetUpdateModel);
        Log.d("API_REQUEST", jsonRequest);


        Call<Boolean> call = outwardTankerLab.UpdateOutwardLabTestProduction(repetUpdateModel);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful() && response.body() != null && response.body() == true){
                    Toasty.success(New_Outward_tanker_Lab.this, "Data Updated Successfully!", Toast.LENGTH_SHORT, true).show();
                    startActivity(new Intent(New_Outward_tanker_Lab.this, Grid_Outward.class));
                    finish();
                }else {
                    Log.e("Retrofit", "Error Response Code: " + response.code());
                    try {
                        Log.e("API_ERROR", "Error Body: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toasty.error(New_Outward_tanker_Lab.this, "Update Failed!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.e("Retrofit", "Failure: " + t.getMessage());
                Toasty.error(New_Outward_tanker_Lab.this, "API Call Failed!", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void update(){
        String iserialnum = newlseralnum.getText().toString();
        String ivehicle = newlvehiclenum.getText().toString();
        char I_O_Value;
        if (isCheck == true && compartmentArraycount < copartmentcount) {
            I_O_Value = 'I';  // If exactly 1 compartment and copartmentcount is 0, pass 'O'
        } else {
            I_O_Value = 'O';  // Default case (fallback)
        }

        // ✅ Now compartmentList already has the latest values, so just convert to JSON
//        String compartment1String = (compartmentList.size() > 0) ? convertCompartmentToJson(compartmentList.get(0)) : "";
        String compartment2String = (compartmentList.size() > 0) ? convertCompartmentToJson(compartmentList.get(0)) : "";
        String compartment3String = (compartmentList.size() > 1) ? convertCompartmentToJson(compartmentList.get(1)) : "";
        String compartment4String = (compartmentList.size() > 2) ? convertCompartmentToJson(compartmentList.get(2)) : "";
        String compartment5String = (compartmentList.size() > 3) ? convertCompartmentToJson(compartmentList.get(3)) : "";
        String compartment6String = (compartmentList.size() > 4) ? convertCompartmentToJson(compartmentList.get(4)) : "";

        // ✅ Log the updated compartment values
//        Log.d("Compartment JSON", compartment1String);
        Log.d("Compartment JSON", compartment2String);
        Log.d("Compartment JSON", compartment3String);
        Log.d("Compartment JSON", compartment4String);
        Log.d("Compartment JSON", compartment5String);
        Log.d("Compartment JSON", compartment6String);

        // ✅ Create API Model
        Repet_update_Model repetUpdateModel = new Repet_update_Model(oploutwardid, "P",
                "", compartment2String, compartment3String, compartment4String, compartment5String, compartment6String,
                iserialnum, ivehicle, 'W', I_O_Value, vehicleType, EmployeId);

        // ✅ Convert to JSON and log for debugging
        Gson gson = new Gson();
        String jsonRequest = gson.toJson(repetUpdateModel);
        Log.d("API_REQUEST", jsonRequest);

        // ✅ Make API Call
        Call<Boolean> call = outwardTankerLab.UpdateOutwardLabTestProduction(repetUpdateModel);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful() && response.body() != null && response.body()) {
                    Toasty.success(New_Outward_tanker_Lab.this, "Data Updated Successfully!", Toast.LENGTH_SHORT, true).show();
                    startActivity(new Intent(New_Outward_tanker_Lab.this, Grid_Outward.class));
                    finish();
                } else {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("API_ERROR", "Error Body: " + errorResponse);
                        } catch (IOException e) {
                            Log.e("API_ERROR", "Error reading errorBody()", e);
                        }
                    } else {
                        Log.e("API_ERROR", "Error Body is NULL");
                    }

                    Toasty.error(New_Outward_tanker_Lab.this, "Update Failed!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.e("Retrofit", "Failure: " + t.getMessage());
                Toasty.error(New_Outward_tanker_Lab.this, "API Call Failed!", Toast.LENGTH_SHORT).show();
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
                                        "Outward Tanker Laboratory Process Done..!",
                                        "Vehicle Number:-" + vehicleNumber + " has completed Laboratory process at " + outTime,
                                        getApplicationContext(),
                                        New_Outward_tanker_Lab.this
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
        Intent intent = new Intent(this, OT_Completd_bilkload_laboratory.class);
        startActivity(intent);
    }
    private String getCurrentTime() {
        // Get the current time
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(new Date());
    }
    public void new_uttankerlabinprocpending(View view) {
        Intent intent = new Intent(this, Grid_Outward.class);
        startActivity(intent);
    }
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

    private Lab_compartment_model parseCompartment(String jsonString) {
        if (jsonString == null || jsonString.isEmpty()) {
            Log.e("JSON_ERROR", "Empty JSON string");
            return null; // Handle empty data safely
        }
        try {
            Gson gson = new Gson();
            return gson.fromJson(jsonString.replace("/",""), Lab_compartment_model.class);
        } catch (Exception e) {
            Log.e("JSON_ERROR", "Error parsing JSON: " + e.getMessage());
            return null;
        }
    }
    private void showSingleCompartmentCard(Lab_compartment_model compartment) {
        CardView compartmentCard = findViewById(R.id.compartmentCard);
        TextView txtBlender = findViewById(R.id.txtBlender);
        TextView txtProductionSign = findViewById(R.id.txtProductionSign);
        TextView txtOperatorSign = findViewById(R.id.txtOperatorSign);

        // ✅ Set Text Values
        txtBlender.setText("Blender: " + compartment.getBlenderNumber());
        txtProductionSign.setText("Production Sign: " + compartment.getProductionSign());
        txtOperatorSign.setText("Operator Sign: " + compartment.getOperatorSign());

        // ✅ Make Card Visible
        compartmentCard.setVisibility(View.VISIBLE);
    }

    private void showCompartmentDialog(List<Lab_compartment_model> compartmentList) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.activity_new_outward_tanker_lab, null);
        builder.setView(dialogView);

        RecyclerView recyclerView = dialogView.findViewById(R.id.recyclerView_labitem);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // ✅ Set up Adapter with Compartment Data
        LabCompartmentAdapter adapter = new LabCompartmentAdapter(this, compartmentList);
        recyclerView.setAdapter(adapter);

        // ✅ Save Button to Capture Inputs
//        Button btnSave = dialogView.findViewById(R.id.btnSaveCompartment);
//        btnSave.setOnClickListener(v -> {
//            // Logic to handle save button (e.g., collect entered data)
//            Toast.makeText(this, "Compartment Data Saved!", Toast.LENGTH_SHORT).show();
//        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private String convertCompartmentToJson(Lab_compartment_model compartment) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Blender", compartment.getBlenderNumber()); // Using only Blender
            jsonObject.put("ProductionSign", compartment.getProductionSign()); // Production Sign
            jsonObject.put("OperatorSign", compartment.getOperatorSign()); // Operator Sign
            jsonObject.put("Viscosity", compartment.getViscosity());
            jsonObject.put("Density", compartment.getDensity());
            jsonObject.put("BatchNumber", compartment.getBatchNumber());
            jsonObject.put("QcOfficer", compartment.getQcOfficer());
            jsonObject.put("Remark", compartment.getRemark());
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "{}"; // Return empty JSON if an error occurs
        }
    }

}