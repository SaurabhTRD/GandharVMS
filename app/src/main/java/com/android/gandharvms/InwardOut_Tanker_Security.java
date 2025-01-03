package com.android.gandharvms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.gandharvms.Inward_Tanker_Security.API_In_Tanker_Security;
import com.android.gandharvms.Inward_Tanker_Security.Respo_Model_In_Tanker_security;
import com.android.gandharvms.Inward_Tanker_Security.RetroApiclient_In_Tanker_Security;
import com.android.gandharvms.Inward_Tanker_Security.UpdateOutSecurityRequestModel;
import com.android.gandharvms.Inward_Tanker_Security.Update_Request_Model_Insequrity;
import com.android.gandharvms.Inward_Tanker_Security.grid;
import com.android.gandharvms.Inward_Truck_Security.Inward_Truck_Security;
import com.android.gandharvms.Inward_Truck_store.ExtraMaterial;
import com.android.gandharvms.LoginWithAPI.Login;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.NotificationAlerts.NotificationCommonfunctioncls;
import com.android.gandharvms.submenu.submenu_Inward_Tanker;
import com.google.common.reflect.TypeToken;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

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

public class InwardOut_Tanker_Security extends NotificationCommonfunctioncls {

    EditText edintime, etvehicle, etinvoice, etsupplier;
    Button submit;
    RadioButton Trasnportyes, transportno, deliveryes, deliveryno, taxyes, taxno, ewayyes, ewayno;
    String vehicltype = Global_Var.getInstance().MenuType;
    char InOutType = Global_Var.getInstance().InOutType;
    char DeptType = Global_Var.getInstance().DeptType;
    TimePickerDialog tpicker;
    private API_In_Tanker_Security apiInTankerSecurity;
    private int InwardId;
    private final String EmployeId = Global_Var.getInstance().EmpId;

    ImageView btnlogout,btnhome;
    TextView username,empid;

    public static String Tanker;
    public static String Truck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inward_out_tanker_security);
        setupHeader();
        apiInTankerSecurity = RetroApiClient.getserccrityveh();

        //Send Notification to all
        FirebaseMessaging.getInstance().subscribeToTopic("all");

        edintime = findViewById(R.id.etintime);
        etvehicle = findViewById(R.id.etvehicalnumber);
        etinvoice = findViewById(R.id.etsinvocieno);
        //etmaterial = findViewById(R.id.etsmaterial);
        etsupplier = findViewById(R.id.etssupplier);
        submit = findViewById(R.id.etssubmit);

        Trasnportyes = findViewById(R.id.rb_LRCopyYes);
        transportno = findViewById(R.id.rb_LRCopyNo);
        deliveryes = findViewById(R.id.rb_DeliveryYes);
        deliveryno = findViewById(R.id.rb_DeliveryNo);
        taxyes = findViewById(R.id.rb_TaxInvoiceYes);
        taxno = findViewById(R.id.rb_TaxInvoiceNo);
        ewayyes = findViewById(R.id.rb_EwaybillYes);
        ewayno = findViewById(R.id.rb_EwaybillNo);

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
                startActivity(new Intent(InwardOut_Tanker_Security.this, Login.class));
            }
        });
        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InwardOut_Tanker_Security.this, Menu.class));
            }
        });*/
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });

        if (getIntent().hasExtra("VehicleNumber")) {
            FetchVehicleDetails(getIntent().getStringExtra("VehicleNumber"), Global_Var.getInstance().MenuType, DeptType, InOutType);
        }

        edintime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                String time = format.format(calendar.getTime());
                edintime.setText(time);
            }
        });

        etvehicle.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String vehicltype = Global_Var.getInstance().MenuType;
                    char DeptType = Global_Var.getInstance().DeptType;
                    char InOutType = Global_Var.getInstance().InOutType;
                    FetchVehicleDetails(etvehicle.getText().toString().trim(), vehicltype, DeptType, InOutType);
                }
            }

        });
    }

    private String getCurrentTime() {
        // Get the current time
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(new Date());
    }

    private void FetchVehicleDetails(@NonNull String VehicleNo, String vehicltype, char DeptType, char InOutType) {
        Call<List<Respo_Model_In_Tanker_security>> call = RetroApiClient.getserccrityveh().GetIntankerSecurityByVehicle(VehicleNo, vehicltype, DeptType, InOutType);
        call.enqueue(new Callback<List<Respo_Model_In_Tanker_security>>() {
            @Override
            public void onResponse(Call<List<Respo_Model_In_Tanker_security>> call, Response<List<Respo_Model_In_Tanker_security>> response) {
                if (response.isSuccessful()) {
                    if (response.body().size() > 0) {
                        List<Respo_Model_In_Tanker_security> Data = response.body();
                        Respo_Model_In_Tanker_security obj = Data.get(0);
                        InwardId = obj.getInwardId();
                        etvehicle.setText(obj.getVehicleNo());
                        etvehicle.setEnabled(false);
                        etinvoice.setText(obj.getInvoiceNo());
                        etinvoice.setEnabled(false);
                        String extraMaterialsJson = obj.getExtramaterials();
                        Log.d("JSON Debug", "Extra Materials JSON: " + extraMaterialsJson);
                        List<ExtraMaterial> extraMaterials = parseExtraMaterials(extraMaterialsJson);
                        Log.d("JSON Debug", "Parsed Extra Materials Size: " + extraMaterials.size());
                        createExtraMaterialViews(extraMaterials);
                        //etmaterial.setText(obj.getMaterial());
                        //etmaterial.setEnabled(false);
                        etsupplier.setText(obj.getPartyName());
                        etsupplier.setEnabled(false);
                    }else {
                        Toasty.error(InwardOut_Tanker_Security.this, "This Vehicle Is Not Available..!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("Retrofit", "Error" + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Respo_Model_In_Tanker_security>> call, Throwable t) {
                Log.e("Retrofit", "Failure: " + t.getMessage());
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

    private List<ExtraMaterial> parseExtraMaterials(String jsonString) {
        if (jsonString == null || jsonString.trim().isEmpty()) {
            Log.e("JSON Parser", "JSON string is null or empty");
            return new ArrayList<>(); // Return an empty list if JSON is invalid
        }

        try {
            Log.d("JSON Parser", "JSON String: " + jsonString);
            Gson gson = new Gson();
            Type listType = new TypeToken<List<ExtraMaterial>>() {}.getType();
            return gson.fromJson(jsonString, listType);
        } catch (JsonSyntaxException e) {
            Log.e("JSON Parser", "Failed to parse JSON: " + jsonString, e);
            return new ArrayList<>(); // Return an empty list in case of parsing error
        }
    }

    public void createExtraMaterialViews(List<ExtraMaterial> extraMaterials) {
        LinearLayout linearLayout = findViewById(R.id.layout_outsecuritylistmaterial); // Ensure this is the correct ID

        // Clear previous views if any
        linearLayout.removeAllViews();

        for (ExtraMaterial extraMaterial : extraMaterials) {
            View materialView = getLayoutInflater().inflate(R.layout.allmaterial, null);

            EditText materialEditText = materialView.findViewById(R.id.etallmaterialmet);
            EditText qtyEditText = materialView.findViewById(R.id.etallmaterialqty);
            Spinner uomSpinner = materialView.findViewById(R.id.allmaterialspinner_team);

            materialEditText.setText(extraMaterial.getMaterial());
            materialEditText.setEnabled(false);
            qtyEditText.setText(extraMaterial.getQty());
            qtyEditText.setEnabled(false);

            List<String> teamList = Arrays.asList("NA","Ton", "Litre", "KL","Kgs","Pcs","M3","Meter","Feet"); // or fetch it dynamically
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, teamList);
            uomSpinner.setAdapter(arrayAdapter);
            uomSpinner.setEnabled(false);

            setSpinnerValue(uomSpinner, extraMaterial.getQtyuom());

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

        // Concatenate fields into a single string
        return (material + "," + qty + "," + qtyuom);
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

    private void update() {
        String lrCopySelection = Trasnportyes.isChecked() ? "Yes" : "No";
        String deliverySelection = deliveryes.isChecked() ? "Yes" : "No";
        String taxInvoiceSelection = taxyes.isChecked() ? "Yes" : "No";
        String ewayBillSelection = ewayyes.isChecked() ? "Yes" : "No";
        String outinintime = edintime.getText().toString().trim();
        String vehiclenumber = etvehicle.getText().toString().trim();
        //String material = etmaterial.getText().toString().trim();
        String supplier = etsupplier.getText().toString().trim();
        String invoice = etinvoice.getText().toString().trim();

        if (lrCopySelection.isEmpty() || deliverySelection.isEmpty() || taxInvoiceSelection.isEmpty() ||
                ewayBillSelection.isEmpty() ||outinintime.isEmpty()||
                vehiclenumber.isEmpty() || supplier.isEmpty() || invoice.isEmpty()) {
            Toasty.warning(this, "All fields must be filled", Toast.LENGTH_SHORT, true).show();
        } else {
            UpdateOutSecurityRequestModel updateOutSecRequestModel = new UpdateOutSecurityRequestModel(outinintime,
                    InwardId, lrCopySelection, deliverySelection, taxInvoiceSelection, ewayBillSelection
                    , 'F', 'O', vehicltype, EmployeId);
            apiInTankerSecurity = RetroApiclient_In_Tanker_Security.getinsecurityApi();
            Call<Boolean> call = apiInTankerSecurity.intankersecurityoutupdate(updateOutSecRequestModel);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body() != null && response.body()==true) {
                        Toasty.success(InwardOut_Tanker_Security.this, "Data Inserted Succesfully !", Toast.LENGTH_SHORT).show();
                        makeNotification(vehiclenumber);
                        startActivity(new Intent(InwardOut_Tanker_Security.this, submenu_Inward_Tanker.class));
                        finish();
                    }else{
                        Toasty.error(InwardOut_Tanker_Security.this, "Data Insertion Failed..!", Toast.LENGTH_SHORT).show();
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
                    Toasty.error(InwardOut_Tanker_Security.this, "failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    public void makeNotification(String vehicleNumber) {
        FcmNotificationsSender notificationsSender = new FcmNotificationsSender(
                "/topics/all",
                "Inward Tanker Out Security Process Done..!",
                "This Vehicle:-" + vehicleNumber + " has Completed The Inward Tanker Process",
                getApplicationContext(),
                InwardOut_Tanker_Security.this
        );
        notificationsSender.triggerSendNotification();
    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
        finish();
    }

    public void inwardoutsecurityclick(View view) {
        Intent intent = new Intent(this, grid.class);
        startActivity(intent);
    }

    public void intrsecoutgridclick(View view) {
        Intent intent = new Intent(this, it_out_sec_Completedgrid.class);
        startActivity(intent);
    }
}