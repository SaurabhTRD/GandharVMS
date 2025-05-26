package com.android.gandharvms.Outward_Tanker_Billing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.gandharvms.FcmNotificationsSender;
import com.android.gandharvms.Global_Var;
import com.android.gandharvms.Inward_Tanker_Laboratory.in_tanker_lab_grid;
import com.android.gandharvms.Inward_Tanker_Weighment.Inward_Tanker_Weighment;
import com.android.gandharvms.LoginWithAPI.Login;
import com.android.gandharvms.LoginWithAPI.LoginMethod;
import com.android.gandharvms.LoginWithAPI.ResponseModel;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.Menu;
import com.android.gandharvms.NotificationAlerts.NotificationCommonfunctioncls;
import com.android.gandharvms.Outward_Tanker;
import com.android.gandharvms.Outward_Tanker_Security.Grid_Outward;
import com.android.gandharvms.Outward_Tanker_Security.Outward_RetroApiclient;
import com.android.gandharvms.Outward_Tanker_Security.Outward_Tanker_Security;
import com.android.gandharvms.Outward_Truck_Logistic.Outward_Truck_Logistics;
import com.android.gandharvms.R;
import com.android.gandharvms.Util.dialogueprogreesbar;
import com.android.gandharvms.outward_Tanker_Lab_forms.Outward_Tanker_Laboratory;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

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

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class Outward_Tanker_Billing extends NotificationCommonfunctioncls {

    public static String Tanker;
    public static String Truck;
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private final String EmployeId = Global_Var.getInstance().EmpId;
    public String dyprooano = "";
    EditText intime, serialnumber, vehiclenumber, transporter, oanumber, date, location,
            remark, etcust, etprod, ethowmuch, euom, kl;
    FirebaseFirestore dbroot;
    Button submit, completed;
    TimePickerDialog tpicker;
    Calendar calendar = Calendar.getInstance();
    AutoCompleteTextView uom;
    ArrayAdapter<String> uomitem;
    String[] uomi = {"TON", "KL"};
    ImageView btnlogout, btnhome;
    TextView username, empid;
    LinearLayout productlinearlayout;
    Button productaddbtn;
    List<String> uomList = new ArrayList<>();
    String holdremark = "";
    dialogueprogreesbar dialogHelper = new dialogueprogreesbar();
    private Outward_Tanker_Billinginterface outwardTankerBillinginterface;
    private com.android.gandharvms.Outward_Tanker_Security.Outward_Tanker outwardTanker;
    private int OutwardId;
    private String token;
    private LoginMethod userDetails;
    private int uhowmuch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        outwardTanker = Outward_RetroApiclient.insertoutwardtankersecurity();
        outwardTankerBillinginterface = Outward_RetroApiclient.outwardTankerBillinginterface();
        userDetails = RetroApiClient.getLoginApi();
        FirebaseMessaging.getInstance().subscribeToTopic(token);

        setContentView(R.layout.activity_outward_tanker_billing);
        intime = findViewById(R.id.etintime);
        serialnumber = findViewById(R.id.etserialnumber);
        vehiclenumber = findViewById(R.id.etvehicleno);
        transporter = findViewById(R.id.ettransportname);
        oanumber = findViewById(R.id.etoanumber);
        date = findViewById(R.id.etdate);
        remark = findViewById(R.id.etremark);
        submit = findViewById(R.id.etssubmit);
        dbroot = FirebaseFirestore.getInstance();
        etcust = findViewById(R.id.etcustomer);
        etprod = findViewById(R.id.etproduct);
        ethowmuch = findViewById(R.id.ethowmuch);
        location = findViewById(R.id.etloca);
        euom = findViewById(R.id.etuombilling);
        kl = findViewById(R.id.etkl);

        productlinearlayout = findViewById(R.id.productlayout_list);
        productaddbtn = findViewById(R.id.button_addproduct);
        completed = findViewById(R.id.otbillincompleted);

        /*btnlogout = findViewById(R.id.btn_logoutButton);
        btnhome = findViewById(R.id.btn_homeButton);
        username = findViewById(R.id.tv_username);
        empid = findViewById(R.id.tv_employeeId);

        String userName = Global_Var.getInstance().Name;
        String empId = Global_Var.getInstance().EmpId;*/

        uomList.add("Ton");
        uomList.add("KL");
        setupHeader();
        /*username.setText(userName);
        empid.setText(empId);
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Outward_Tanker_Billing.this, Login.class));
            }
        });
        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Outward_Tanker_Billing.this, Menu.class));
            }
        });*/

        uom = findViewById(R.id.etuombilling);
        uomitem = new ArrayAdapter<String>(this, R.layout.outward_billing_uom, uomi);
        uom.setAdapter(uomitem);
        uom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String items = parent.getItemAtPosition(position).toString();
                Toasty.success(getApplicationContext(), "Item:- " + items + " Selected", Toast.LENGTH_SHORT).show();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert();
            }
        });

        completed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Outward_Tanker_Billing.this, OT_Completed_Billing.class));
            }
        });

        productaddbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addview();
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

        vehiclenumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    FetchVehicleDetails(vehiclenumber.getText().toString().trim(), vehicleType, nextProcess, inOut);
                }
            }
        });

        if (getIntent().hasExtra("vehiclenum")) {
            FetchVehicleDetails(getIntent().getStringExtra("vehiclenum"), Global_Var.getInstance().MenuType, nextProcess, inOut);
        }
    }

    public void makeNotificationforweighment(String vehicleNumber, String outTime) {
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
                                        "Outward Tanker Billing Process Done..!",
                                        "Vehicle Number:-" + vehicleNumber + " has completed Billing process at " + outTime,
                                        getApplicationContext(),
                                        Outward_Tanker_Billing.this
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
                Toasty.error(Outward_Tanker_Billing.this, "failed..!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void makeNotificationSecurity(String vehicleNumber, String OANumber) {
        Call<List<ResponseModel>> call = userDetails.getUsersListData();
        call.enqueue(new Callback<List<ResponseModel>>() {
            @Override
            public void onResponse(Call<List<ResponseModel>> call, Response<List<ResponseModel>> response) {
                if (response.isSuccessful()) {
                    List<ResponseModel> userList = response.body();
                    if (userList != null) {
                        for (ResponseModel resmodel : userList) {
                            String specificRole = "Security";
                            if (specificRole.equals(resmodel.getDepartment())) {
                                token = resmodel.getToken();

                                FcmNotificationsSender notificationsSender = new FcmNotificationsSender(
                                        token,
                                        "Outward Tanker Billing Process Done And Has Send OA Number",
                                        "This OA Number:-" + OANumber + " is For This Vehicle Number:- " + vehicleNumber,
                                        getApplicationContext(),
                                        Outward_Tanker_Billing.this
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
                Toasty.error(Outward_Tanker_Billing.this, "failed..!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void makeNotificationforproduction(String vehicleNumber, String outTime) {
        Call<List<ResponseModel>> call = userDetails.getUsersListData();
        call.enqueue(new Callback<List<ResponseModel>>() {
            @Override
            public void onResponse(Call<List<ResponseModel>> call, Response<List<ResponseModel>> response) {
                if (response.isSuccessful()) {
                    List<ResponseModel> userList = response.body();
                    if (userList != null) {
                        for (ResponseModel resmodel : userList) {
                            String specificRole = "Production";
                            if (specificRole.equals(resmodel.getDepartment())) {
                                token = resmodel.getToken();

                                FcmNotificationsSender notificationsSender = new FcmNotificationsSender(
                                        token,
                                        "Outward Tanker Billing Process Done..!",
                                        "Vehicle Number:-" + vehicleNumber + " has completed Billing process at " + outTime,
                                        getApplicationContext(),
                                        Outward_Tanker_Billing.this
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
                Toasty.error(Outward_Tanker_Billing.this, "failed..!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void FetchVehicleDetails(@NonNull String vehicleNo, String vehicleType, char NextProcess, char inOut) {
        Call<Respons_Outward_Tanker_Billing> call = outwardTankerBillinginterface.outwardbillingfetching(vehicleNo, vehicleType, NextProcess, inOut);
        call.enqueue(new Callback<Respons_Outward_Tanker_Billing>() {
            @Override
            public void onResponse(Call<Respons_Outward_Tanker_Billing> call, Response<Respons_Outward_Tanker_Billing> response) {
                if (response.isSuccessful()) {
                    Respons_Outward_Tanker_Billing data = response.body();
                    if (data.getVehicleNumber() != "" && data.getVehicleNumber() != null) {
                        OutwardId = data.getOutwardId();
                        vehiclenumber.setText(data.getVehicleNumber());
                        serialnumber.setText(data.getSerialNumber());
                        date.setText(data.getDate());
                        transporter.setText(data.getTransportName());
                        vehiclenumber.setEnabled(false);
                        serialnumber.setEnabled(false);
                        date.setEnabled(false);
                        transporter.setEnabled(false);
                        kl.setText(String.valueOf(data.getKl()));
                        kl.setEnabled(false);
                        holdremark = data.getHoldRemark();
                        /*intime.callOnClick();
                        intime.requestFocus();*/
                    } else {
                        Toasty.error(Outward_Tanker_Billing.this, "This Vehicle Number Is Not Available..!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("Retrofit", "Error Response Body: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Respons_Outward_Tanker_Billing> call, Throwable t) {

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

    private String getCurrentTime() {
        // Get the current time
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(new Date());
    }

    private void addview() {
        View materialview = getLayoutInflater().inflate(R.layout.add_listof_product, null, false);
        EditText editTextoano = materialview.findViewById(R.id.etotbilOANo);
        EditText editTextproduct = materialview.findViewById(R.id.etotbilProduct);
        EditText editqty = materialview.findViewById(R.id.etotbilqty);
        AppCompatSpinner spinner = materialview.findViewById(R.id.etotbilspinner_team);
        ImageView img = materialview.findViewById(R.id.etotbileditcancel);

        productlinearlayout.addView(materialview);

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, uomList);
        spinner.setAdapter(arrayAdapter);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeView(materialview);
            }
        });
    }

    private void removeView(View view) {

        productlinearlayout.removeView(view);
    }

    public void insert() {
        String etintime = intime.getText().toString().trim();
        String etserilnumber = serialnumber.getText().toString().trim();
        String etvehiclenumber = vehiclenumber.getText().toString().trim();
        //String etoanumber = oanumber.getText().toString().trim();
        String etdate = date.getText().toString().trim();
        String etremark = remark.getText().toString().trim();
        String outTime = getCurrentTime();
        String ucustname = etcust.getText().toString().trim();
        JSONArray ProductArray = new JSONArray();
        for (int i = 0; i < productlinearlayout.getChildCount(); i++) {
            View childView = productlinearlayout.getChildAt(i);
            if (childView != null) {
                EditText etoano = childView.findViewById(R.id.etotbilOANo);
                EditText etproduct = childView.findViewById(R.id.etotbilProduct);
                EditText etproductqty = childView.findViewById(R.id.etotbilqty);
                AppCompatSpinner spnproductqtyuom = childView.findViewById(R.id.etotbilspinner_team);

                dyprooano = etoano.getText().toString().trim();
                String dyproductname = etproduct.getText().toString().trim();
                String dyproductqty = etproductqty.getText().toString().trim();
                String dyproductqtyuom = spnproductqtyuom.getSelectedItem().toString();

                // Check if both material and quantity fields are not empty
                if (!dyprooano.isEmpty() && !dyproductname.isEmpty() && !dyproductqty.isEmpty() && !dyproductqtyuom.isEmpty()) {
                    try {
                        // Create a new JSONObject for each material
                        JSONObject materialObject = new JSONObject();
                        materialObject.put("OANumber", dyprooano);
                        materialObject.put("ProductName", dyproductname);
                        materialObject.put("ProductQty", Double.parseDouble(dyproductqty));
                        materialObject.put("ProductQtyuom", dyproductqtyuom);

                        // Add the material JSON object to the array
                        ProductArray.put(materialObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        String ulocation = location.getText().toString().trim();
        if (etintime.isEmpty() || etserilnumber.isEmpty() || etvehiclenumber.isEmpty() ||
                etdate.isEmpty() || etremark.isEmpty() || ucustname.isEmpty() ||
                ulocation.isEmpty()) {
            Toasty.warning(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
        }/*else if (ProductArray.length() == 0) {
            // Check if no materials are added
            Toasty.warning(this, "Please add at least one product with OANumber before submitting.", Toast.LENGTH_SHORT, true).show();
        }*/ else {
            String allproductString = ProductArray.toString();
            Respons_Outward_Tanker_Billing responsOutwardTankerBilling = new Respons_Outward_Tanker_Billing(OutwardId, etintime, outTime,
                    "", EmployeId, EmployeId, 'B', etremark, etserilnumber, etvehiclenumber, "", ucustname,
                    "", 0, ulocation, 'W', inOut,
                    vehicleType, "", allproductString);
            dialogHelper.showConfirmationDialog(this, () -> {
                dialogHelper.showProgressDialog(this); // Show progress when confirmed
                Call<Boolean> call = outwardTankerBillinginterface.updatebillingoanumber(responsOutwardTankerBilling);
                call.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if (response.isSuccessful() && response.body() != null && response.body()) {
                            dialogHelper.hideProgressDialog(); // Hide after response
                            makeNotificationSecurity(etvehiclenumber, dyprooano);
                            makeNotificationforweighment(etvehiclenumber, outTime);
                            makeNotificationforproduction(etvehiclenumber, outTime);
                            if (!holdremark.isEmpty()) {
                                Call<Boolean> call1 = outwardTanker.updateBillingActivestatus(etvehiclenumber, Global_Var.getInstance().Name);
                                call1.enqueue(new Callback<Boolean>() {
                                    @Override
                                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                        if (response.isSuccessful() && response.body() != null && response.body()) {
                                            Toasty.success(Outward_Tanker_Billing.this, "Vehicle Active", Toast.LENGTH_SHORT, true).show();
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
                                        Toasty.error(Outward_Tanker_Billing.this, "failed..!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            Toasty.success(Outward_Tanker_Billing.this, "Data Inserted Successfully", Toast.LENGTH_SHORT, true).show();
                            startActivity(new Intent(Outward_Tanker_Billing.this, Grid_Outward.class));
                            finish();
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
                        Toasty.error(Outward_Tanker_Billing.this, "failed..!", Toast.LENGTH_SHORT).show();
                    }
                });
            });
        }
    }

    public void outtankerbillpending(View view) {
        Intent intent = new Intent(this, Grid_Outward.class);
        startActivity(intent);
    }

    public void otinbilonclickevent(View view) {
        /*Intent intent = new Intent(this, in_tanker_lab_grid.class);
        startActivity(intent);*/
    }
}