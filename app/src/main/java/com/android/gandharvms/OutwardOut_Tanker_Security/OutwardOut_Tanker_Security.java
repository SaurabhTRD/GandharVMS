package com.android.gandharvms.OutwardOut_Tanker_Security;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.gandharvms.FcmNotificationsSender;
import com.android.gandharvms.Global_Var;
import com.android.gandharvms.InwardOut_Tanker_Security;
import com.android.gandharvms.LoginWithAPI.Login;
import com.android.gandharvms.LoginWithAPI.LoginMethod;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.Menu;
import com.android.gandharvms.NotificationAlerts.NotificationCommonfunctioncls;
import com.android.gandharvms.OutwardOut_Tanker;
import com.android.gandharvms.Outward_Tanker_Security.Grid_Outward;
import com.android.gandharvms.Outward_Tanker_Security.Model_OutwardOut_Security;
import com.android.gandharvms.Outward_Tanker_Security.Outward_RetroApiclient;
import com.android.gandharvms.Outward_Tanker_Security.Outward_Tanker;
import com.android.gandharvms.Outward_Tanker_Security.Response_Outward_Security_Fetching;
import com.android.gandharvms.ProductListData;
import com.android.gandharvms.QR_Code.QRGeneratorUtil;
import com.android.gandharvms.R;
import com.android.gandharvms.Util.dialogueprogreesbar;
import com.google.common.reflect.TypeToken;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
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

public class OutwardOut_Tanker_Security extends NotificationCommonfunctioncls {

    public static String Tanker;
    public static String Truck;
    EditText intime, serialnumber, date, vehiclenumber, invoiceno, partyname, goodsdiscription, qty, otoutsecqtyuom, netweight, sign, remark, etsealn,etdate;
    Button submit, complted;
    FirebaseFirestore dbroot;
    TimePickerDialog tpicker;
    Calendar calendar = Calendar.getInstance();
    RadioButton Trasnportyes, transportno, tremyes, tremno, ewayyes, ewayno, testyes, testno, invoiceyes, invoicenono;
    ImageView btnlogout, btnhome;
    TextView username, empid;
    dialogueprogreesbar dialogHelper = new dialogueprogreesbar();
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private final String EmployeName = Global_Var.getInstance().Name;
    private final String EmployeId = Global_Var.getInstance().EmpId;
    private int OutwardId;
    private Outward_Tanker outwardTanker;
    private String outsecvehiclenum;
    private LoginMethod userDetails;
    private String token;
    CheckBox cbGenerateQR;
    ImageView ivQRCode;

    DatePickerDialog picker;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
    TextView tvAllRemarks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outward_out_tanker_security);
        outwardTanker = Outward_RetroApiclient.insertoutwardtankersecurity();

        userDetails = RetroApiClient.getLoginApi();
        FirebaseMessaging.getInstance().subscribeToTopic("all");

        intime = findViewById(R.id.etintime);
        serialnumber = findViewById(R.id.etserialnumber);
        etdate = findViewById(R.id.outwarddoutdate);
        vehiclenumber = findViewById(R.id.etvehicleno);
        invoiceno = findViewById(R.id.etinvoicenumber);
        partyname = findViewById(R.id.etpartyname);
        //newloanum = findViewById(R.id.etotoutsecnewloanumer);
//        goodsdiscription = findViewById(R.id.etgoodsdisc);
        qty = findViewById(R.id.etotoutsecuritytotalQuantity);
        otoutsecqtyuom = findViewById(R.id.etotoutsecuritytotalQuantityUOM);
        netweight = findViewById(R.id.etnetweight);
        sign = findViewById(R.id.etsign);
        remark = findViewById(R.id.etremark);
//        etsealn = findViewById(R.id.etsealnumbar);

        submit = findViewById(R.id.etssubmit);
        dbroot = FirebaseFirestore.getInstance();
        complted = findViewById(R.id.otoutseccompletd);
        tvAllRemarks = findViewById(R.id.otoutsectv_allremarks);
        Trasnportyes = findViewById(R.id.outwaoutrb_LRCopyYes);
        transportno = findViewById(R.id.outwaourb_LRCopyNo);
        tremyes = findViewById(R.id.tremcardyes);
        tremno = findViewById(R.id.tremcardno);
        ewayyes = findViewById(R.id.outwardoutrb_EwaybillYes);
        ewayno = findViewById(R.id.outwardoutrb_EwaybillNo);
        testyes = findViewById(R.id.testreportyes);
        testno = findViewById(R.id.testreportno);
        invoiceyes = findViewById(R.id.invoiceyes);
        invoicenono = findViewById(R.id.invoiceno);

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
                startActivity(new Intent(OutwardOut_Tanker_Security.this, Login.class));
            }
        });
        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OutwardOut_Tanker_Security.this, Menu.class));
            }
        });*/
        setupHeader();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert();
            }
        });
        complted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OutwardOut_Tanker_Security.this, OT_Complete_Out_security.class));
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
        etdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                // Array of month abbreviations
                String[] monthAbbreviations = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
                picker = new DatePickerDialog(OutwardOut_Tanker_Security.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Use the month abbreviation from the array
                        String monthAbbreviation = monthAbbreviations[month];
                        // etdate.setText(dayOfMonth + "/" + monthAbbreviation + "/" + year);
                        etdate.setText(dateFormat.format(calendar.getTime()).replace("Sept","Sep"));
                    }
                }, year, month, day);
                picker.show();
            }
        });
        vehiclenumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    FetchVehicleDetails(vehiclenumber.getText().toString().trim(), vehicleType, nextProcess, inOut);
                }
            }
        });

        if (getIntent().hasExtra("vehiclenum")) {
            FetchVehicleDetails(getIntent().getStringExtra("vehiclenum"), Global_Var.getInstance().MenuType, nextProcess, inOut);
        }
        cbGenerateQR = findViewById(R.id.cbGenerateQR);
        ivQRCode = findViewById(R.id.ivQRCode);
        Button btnPrint = findViewById(R.id.btnPrintQR);

        // call reusable QR function
        QRGeneratorUtil.handleQRCheckbox(this, cbGenerateQR,vehiclenumber, serialnumber, etdate,intime, ivQRCode, btnPrint);

    }

    private void FetchVehicleDetails(@NonNull String VehicleNo, String vehicltype, char DeptType, char InOutType) {
        Call<List<Response_Outward_Security_Fetching>> call = Outward_RetroApiclient.insertoutwardtankersecurity().outwardsecurityfetching(VehicleNo, vehicltype, DeptType, InOutType);
        call.enqueue(new Callback<List<Response_Outward_Security_Fetching>>() {
            @Override
            public void onResponse(Call<List<Response_Outward_Security_Fetching>> call, Response<List<Response_Outward_Security_Fetching>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().size() > 0) {
                        List<Response_Outward_Security_Fetching> data = response.body();
                        Response_Outward_Security_Fetching obj = data.get(0);
                        OutwardId = obj.getOutwardId();
                        serialnumber.setText(obj.getSerialNumber());
                        serialnumber.setEnabled(false);
                        vehiclenumber.setText(obj.getVehicleNumber());
                        vehiclenumber.setEnabled(false);
                        outsecvehiclenum = obj.getVehicleNumber();
                        partyname.setText(obj.getCustomerName());
                        partyname.setEnabled(false);
                        //newloanum.setText(obj.getOAnumber());
                        //newloanum.setEnabled(false);
                        invoiceno.setText(obj.getOutInvoiceNumber());
                        invoiceno.setEnabled(false);
                        qty.setText(obj.getOutTotalQty());
                        qty.setEnabled(false);
                        otoutsecqtyuom.setText(obj.getUnitofmeasurementname());
                        otoutsecqtyuom.setEnabled(false);
                        netweight.setText(obj.getNetWeight());
                        netweight.setEnabled(false);
                        String extraMaterialsJson = obj.getProductQTYUOMOA();
                        Log.d("JSON Debug", "Extra Materials JSON: " + extraMaterialsJson);
                        List<ProductListData> extraMaterials = parseExtraMaterials(extraMaterialsJson);
                        Log.d("JSON Debug", "Parsed Extra Materials Size: " + extraMaterials.size());
                        createExtraMaterialViews(extraMaterials);
//                        etsealn.setText(obj.getSealNumber());
//                        etsealn.setEnabled(false);
                        String allRemark = obj.getAllOTRemarks();
                        if (allRemark != null && !allRemark.trim().isEmpty()) {
                            tvAllRemarks.setText("   "+allRemark.replace(",", "\n")); // line-by-line
                        } else {
                            tvAllRemarks.setText("No system remarks.");
                        }
                    } else {
                        Toasty.error(OutwardOut_Tanker_Security.this, "This Vehicle Number Is Not Available..!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("Retrofit", "Error" + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Response_Outward_Security_Fetching>> call, Throwable t) {

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
                Toasty.error(OutwardOut_Tanker_Security.this, "failed..!", Toast.LENGTH_SHORT).show();
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
        LinearLayout linearLayout = findViewById(R.id.layout_productlistitoutsecurity); // Ensure this is the correct ID

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
        FcmNotificationsSender notificationsSender = new FcmNotificationsSender(
                "/topics/all",
                "Outward Tanker OutSecurity Process Completed",
                "This Vehicle:-" + vehicleNumber + " has Completed The Outward Tanker Completed",
                getApplicationContext(),
                OutwardOut_Tanker_Security.this
        );
        notificationsSender.triggerSendNotification();
    }

    public void insert() {
//        String etgooddiscription = goodsdiscription.getText().toString().trim();
        String etsign = sign.getText().toString().trim();
        String outTime = getCurrentTime();
        String etremark = remark.getText().toString().trim();
        String lrCopySelection = Trasnportyes.isChecked() ? "Yes" : "No";
        String tremselection = tremyes.isChecked() ? "Yes" : "No";
        String ewayselection = ewayyes.isChecked() ? "Yes" : "No";
        String testreselection = testyes.isChecked() ? "Yes" : "No";
        String invoiceselection = invoiceyes.isChecked() ? "Yes" : "No";
        if (etsign.isEmpty() || etremark.isEmpty()) {
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
        } else {
            Model_OutwardOut_Security modelOutwardOutSecurity = new Model_OutwardOut_Security(OutwardId, "", etsign,
                    lrCopySelection, tremselection, ewayselection, testreselection, invoiceselection, outTime, EmployeId,
                    'S', inOut, vehicleType, etremark);
            dialogHelper.showConfirmationDialog(this, () -> {
                dialogHelper.showProgressDialog(this); // Show progress when confirmed
                Call<Boolean> call = outwardTanker.updateOutwardoutsecurity(modelOutwardOutSecurity);
                call.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if (response.isSuccessful() && response.body() != null && response.body()) {
                            dialogHelper.hideProgressDialog(); // Hide after response
                            makeNotification(outsecvehiclenum);
                            Toasty.success(OutwardOut_Tanker_Security.this, "Data Inserted Successfully", Toast.LENGTH_SHORT, true).show();
                            startActivity(new Intent(OutwardOut_Tanker_Security.this, Grid_Outward.class));
                            finish();
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
                        Toasty.error(OutwardOut_Tanker_Security.this, "failed", Toast.LENGTH_SHORT).show();
                    }
                });
            });
        }
    }

    public void outwardoutpendingSecurityViewclick(View view) {
        Intent intent = new Intent(this, Grid_Outward.class);
        startActivity(intent);
    }

    public void otoutsecuritycompletedclick(View view) {
        /*Intent intent = new Intent(this, in_tanker_lab_grid.class);
        startActivity(intent);*/
    }
}