package com.android.gandharvms.OutwardOutDataEntryForm_Production;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.gandharvms.FcmNotificationsSender;
import com.android.gandharvms.Global_Var;
import com.android.gandharvms.LoginWithAPI.Login;
import com.android.gandharvms.LoginWithAPI.LoginMethod;
import com.android.gandharvms.LoginWithAPI.ResponseModel;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.Menu;
import com.android.gandharvms.NotificationAlerts.NotificationCommonfunctioncls;
import com.android.gandharvms.OutwardOut_Tanker;
import com.android.gandharvms.Outward_Tanker_Security.Grid_Outward;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.ProductListData;
import com.android.gandharvms.R;
import com.android.gandharvms.Util.NavigationUtil;
import com.android.gandharvms.Util.dialogueprogreesbar;
import com.android.gandharvms.outward_Tanker_Lab_forms.Lab_Model__Outward_Tanker;
import com.android.gandharvms.outward_Tanker_Lab_forms.Outward_Tanker_Lab;
import com.google.common.reflect.TypeToken;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
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

public class DataEntryForm_Production extends NotificationCommonfunctioncls {
    public static String Tanker;
    public static String Truck;
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private final String EmployeId = Global_Var.getInstance().EmpId;
    EditText odeintime, odeserialnumber, odevehiclenumber, odedensity, odesealnumber, odeetremark, party, location, oanum, batch, product;
    Button odesubmit, completd;
    TimePickerDialog tpicker;
    ImageView btnlogout, btnhome;
    TextView username, empid;
    dialogueprogreesbar dialogHelper = new dialogueprogreesbar();
    private Outward_Tanker_Lab outwardTankerLab;
    private LoginMethod userDetails;
    private String token;
    private int OutwardId;
    private String odvehiclenum;
    TextView tvAllRemarks;
    LinearLayout layoutLabCompartments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_entry_form_production);
        setupHeader();
        outwardTankerLab = RetroApiClient.outwardTankerLab();

        odeintime = findViewById(R.id.etoutdataentryintime);
        odeserialnumber = findViewById(R.id.etoutdataentryserialnumber);
        odevehiclenumber = findViewById(R.id.etoutdataentryvehicleno);
        odedensity = findViewById(R.id.etoutdataentrydensity);
        odesealnumber = findViewById(R.id.etoutdataentrysealnumber);
        odeetremark = findViewById(R.id.etoutdataentryremakr);
        completd = findViewById(R.id.outdataentrycompletd);
        tvAllRemarks = findViewById(R.id.outotdeprotv_allremarks);
        party = findViewById(R.id.etpartyname);
        location = findViewById(R.id.etlocation);
        oanum = findViewById(R.id.etoanum);
        batch = findViewById(R.id.etbatchno);
        product = findViewById(R.id.etproduct);
        layoutLabCompartments = findViewById(R.id.layout_lab_compartments);
        odesubmit = findViewById(R.id.etoutdataentrysubmit);

        userDetails = RetroApiClient.getLoginApi();
        FirebaseMessaging.getInstance().subscribeToTopic(token);

        odesubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
            }
        });
        completd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DataEntryForm_Production.this, OT_Completed_outdataentry.class));
            }
        });

        odeintime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                String time = format.format(calendar.getTime());
                odeintime.setText(time);
            }
        });

        odevehiclenumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    FetchVehicleDetails(odevehiclenumber.getText().toString().trim(), vehicleType, nextProcess, inOut);
                }
            }
        });

        if (getIntent().hasExtra("vehiclenum")) {
            FetchVehicleDetails(getIntent().getStringExtra("vehiclenum"), Global_Var.getInstance().MenuType, nextProcess, inOut);
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
                        OutwardId = data.getOutwardId();
                        odeserialnumber.setText(data.getSerialNumber());
                        odeserialnumber.setEnabled(false);
                        odevehiclenumber.setText(data.getVehicleNumber());
                        odevehiclenumber.setEnabled(false);
                        odvehiclenum = data.getVehicleNumber();
                        /*odedensity.setText(data.getDensity_29_5C());
                        odedensity.setEnabled(false);*/
                        odesealnumber.setText(String.valueOf(data.getSealNumber()));
                        odesealnumber.setEnabled(false);

                        party.setText(data.getCustomerName());
                        party.setEnabled(false);
                        location.setText(data.getLocation());
                        location.setEnabled(false);
                        oanum.setText(data.getOAnumber());
                        oanum.setEnabled(false);
                        product.setText(data.getProductName());
                        product.setEnabled(false);
                        /*batch.setText(data.getBatch_No());
                        batch.setEnabled(false);*/
                        String allRemark = data.getAllOTRemarks();
                        if (allRemark != null && !allRemark.trim().isEmpty()) {
                            tvAllRemarks.setText("   "+allRemark.replace(",", "\n")); // line-by-line
                        } else {
                            tvAllRemarks.setText("No system remarks.");
                        }
                        String extraMaterialsJson = data.getProductQTYUOMOA();
                        Log.d("JSON Debug", "Extra Materials JSON: " + extraMaterialsJson);
                        List<ProductListData> extraMaterials = parseExtraMaterials(extraMaterialsJson);
                        Log.d("JSON Debug", "Parsed Extra Materials Size: " + extraMaterials.size());
                        createExtraMaterialViews(extraMaterials);
                        List<String> labcompartmentsJson = Arrays.asList(
                                data.getLabcompartment1(),
                                data.getLabcompartment2(),
                                data.getLabcompartment3(),
                                data.getLabcompartment4(),
                                data.getLabcompartment5(),
                                data.getLabcompartment6()
                        );
                        for (int i = 0; i < labcompartmentsJson.size(); i++) {
                            String labJson = labcompartmentsJson.get(i);
                            if (labJson != null && !labJson.trim().isEmpty()) {
                                try {
                                    JSONObject jsonObject = new JSONObject(labJson);

                                    String density = jsonObject.optString("Density", "");
                                    String batchNo = jsonObject.optString("BatchNumber", "");

                                    if (!density.isEmpty() || !batchNo.isEmpty()) {
                                        // Inflate card
                                        View cardView = getLayoutInflater().inflate(R.layout.item_labcompartment_card, layoutLabCompartments, false);

                                        TextView tvTitle = cardView.findViewById(R.id.tvCompartmentTitle);
                                        EditText tvDensity = cardView.findViewById(R.id.tvDensity);
                                        EditText tvBatchNo = cardView.findViewById(R.id.tvBatchNo);

                                        tvTitle.setText("Compartment " + (i + 1));
                                        tvDensity.setText((density.isEmpty() ? "-" : density));
                                        tvBatchNo.setText((batchNo.isEmpty() ? "-" : batchNo));
                                        layoutLabCompartments.addView(cardView);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Log.e("LabCompartmentParse", "Invalid JSON in compartment " + (i + 1) + ": " + labJson);
                                }
                            }
                        }
                        /*odeintime.requestFocus();
                        odeintime.callOnClick();*/
                    } else {
                        Toasty.error(DataEntryForm_Production.this, "This Vehicle Number Is Not Available..!", Toast.LENGTH_SHORT).show();
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
        LinearLayout linearLayout = findViewById(R.id.layout_productlistitoutdataentry); // Ensure this is the correct ID

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

    private String getCurrentTime() {
        // Get the current time
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(new Date());
    }

    public void makeNotification(String vehicleNumber) {
        Call<List<ResponseModel>> call = userDetails.getUsersListData();
        call.enqueue(new Callback<List<ResponseModel>>() {
            @Override
            public void onResponse(Call<List<ResponseModel>> call, Response<List<ResponseModel>> response) {
                if (response.isSuccessful()) {
                    List<ResponseModel> userList = response.body();
                    if (userList != null) {
                        for (ResponseModel resmodel : userList) {
                            String specificRole = "Billing";
                            if (specificRole.equals(resmodel.getDepartment())) {
                                token = resmodel.getToken();

                                FcmNotificationsSender notificationsSender = new FcmNotificationsSender(
                                        token,
                                        "Outward Tanker OutDataEntryForm Process Done",
                                        "Vehicle Number:-" + vehicleNumber + " has Completed OutDataEntryForm Process",
                                        getApplicationContext(),
                                        DataEntryForm_Production.this
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
                Toasty.error(DataEntryForm_Production.this, "failed..!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void update() {
        String odfintime = odeintime.getText().toString().trim();
        String odfremark = odeetremark.getText().toString().trim();
        String odfouttime = getCurrentTime();
        if (odfintime.isEmpty() || odfremark.isEmpty()) {
            Toasty.warning(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
        } else {
            otoutDataEntryProduction_RequestModel requestmodeldata = new otoutDataEntryProduction_RequestModel(OutwardId, odfintime, odfouttime,
                    odfremark, 'B', inOut, vehicleType, EmployeId);
            dialogHelper.showConfirmationDialog(this, () -> {
                dialogHelper.showProgressDialog(this); // Show progress when confirmed
                Call<Boolean> call = outwardTankerLab.updateDataEntryFormProduction(requestmodeldata);
                call.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if (response.isSuccessful() && response.body() && response.body()) {
                            dialogHelper.hideProgressDialog(); // Hide after response
                            makeNotification(odvehiclenum);
                            Toasty.success(DataEntryForm_Production.this, "Data Inserted Successfully", Toast.LENGTH_SHORT, true).show();
                            NavigationUtil.navigateAndClear(DataEntryForm_Production.this, Grid_Outward.class);
                        } else {
                            Log.e("Retrofit", "Error Response Body: " + response.code());
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
                        Toasty.error(DataEntryForm_Production.this, "failed..!", Toast.LENGTH_SHORT).show();
                    }
                });
            });
        }
    }

    public void outwardoutdataentryclick(View view) {
        Intent intent = new Intent(this, Grid_Outward.class);
        startActivity(intent);
    }

    public void otoutdataentrycompletedclick(View view) {
        /*Intent intent = new Intent(this, it_in_weigh_Completedgrid.class);
        startActivity(intent);*/
    }
}