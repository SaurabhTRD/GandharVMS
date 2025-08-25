package com.android.gandharvms.Outward_Truck_Dispatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.android.gandharvms.Outward_Tanker_Security.Grid_Outward;
import com.android.gandharvms.Outward_Tanker_Security.Outward_RetroApiclient;
import com.android.gandharvms.Outward_Truck;
import com.android.gandharvms.Outward_Truck_Weighment.Outward_Truck_weighment;
import com.android.gandharvms.R;
import com.android.gandharvms.Util.NavigationUtil;
import com.android.gandharvms.Util.dialogueprogreesbar;
import com.google.firebase.messaging.FirebaseMessaging;

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

public class Outward_DesIndustriaLoading_Form extends NotificationCommonfunctioncls {

    public static String Tanker;
    public static String Truck;
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final String EmployeId = Global_Var.getInstance().EmpId;
    public char inOut = Global_Var.getInstance().InOutType;
    public String vehnumber = "";
    Button btndesilsubmit, pending, submit,updbtnclick;
    EditText etdesilserialnumber, etdesilvehiclenumber, etdesiltransport, etdesilintime, etdesiltotqty,
            etdesilremark, etdesiltypeofpackingid;
    EditText tenltr, eighteenltr, twentyltr, twentysixltr, fiftyltr, twotenltr, boxbucket, weight, indussign, indusremark, oa, party;
    String[] typeofpacking = {"Packing of 210 Ltr", "Packing of 50 Ltr", "Packing Of 26 Ltr", "Packing of 20 Ltr", "Packing of 10 Ltr", "Packing of Box & Bucket"};
    Integer typeofpackingNumericValue = 1;
    AutoCompleteTextView typeofpackingautoCompleteTextView1;
    Map<String, Integer> typeofpackingMapping = new HashMap<>();
    ArrayAdapter<String> typeofpackingdrop;
    AutoCompleteTextView dept;
    ArrayAdapter<String> nextdeptdrop;
    Map<String, String> nextdeptmapping = new HashMap<>();
    String nextdeptvalue = "W";
    String deptNumericValue = "W";
    LinearLayout lnlsmallpackqty;
    EditText splpack7literqty, splpack7_5literqty, splpack8_5literqty, splpack10literqty, splpack11literqty,
            splpack12literqty, splpack13literqty, splpack15literqty, splpack18literqty, splpack20literqty,
            splpack26literqty, splpack50literqty, splpack210literqty, splpackboxbucketqty,
            spltotqty, spltotweight;
    ImageView btnlogout, btnhome;
    TextView username, empid;
    dialogueprogreesbar dialogHelper = new dialogueprogreesbar();
    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            calculateTotal();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private int OutwardId;
    private Outward_Truck_interface outwardTruckInterface;
    private LoginMethod userDetails;
    private int Id;
    private String token;
    private SharedPreferences sharedPreferences;
    TextView tvAllRemarks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outward_des_industria_loading_form);
        outwardTruckInterface = Outward_RetroApiclient.outwardtruckdispatch();
        userDetails = RetroApiClient.getLoginApi();
        sharedPreferences = getSharedPreferences("VehicleManagementPrefs", MODE_PRIVATE);
//        typeofpackingautoCompleteTextView1 = findViewById(R.id.autodesindusloadTypeOfPacking);
//        typeofpackingMapping = new HashMap<>();
//        typeofpackingMapping.put("Packing of 210 Ltr", 1);
//        typeofpackingMapping.put("Packing of 50 Ltr", 2);
//        typeofpackingMapping.put("Packing Of 26 Ltr", 3);
//        typeofpackingMapping.put("Packing of 20 Ltr", 4);
//        typeofpackingMapping.put("Packing of 10 Ltr", 5);
//        typeofpackingMapping.put("Packing of Box & Bucket", 6);

        typeofpackingdrop = new ArrayAdapter<String>(this, R.layout.in_ta_se_qty, new ArrayList<>(typeofpackingMapping.keySet()));
//        typeofpackingautoCompleteTextView1.setAdapter(typeofpackingdrop);
//        typeofpackingautoCompleteTextView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String qtyUomDisplay = parent.getItemAtPosition(position).toString();
//                // Retrieve the corresponding numerical value from the mapping
//                typeofpackingNumericValue = typeofpackingMapping.get(qtyUomDisplay);
//                if (typeofpackingNumericValue != null) {
//                    // Now, you can use qtyUomNumericValue when inserting into the database
//                    Toasty.success(Outward_DesIndustriaLoading_Form.this, "Type Of Packing : " + qtyUomDisplay + " Selected", Toast.LENGTH_SHORT).show();
//                } else {
//                    // Handle the case where the mapping doesn't contain the display value
//                    Toasty.error(Outward_DesIndustriaLoading_Form.this, "Default Type Of Packing  : " + "NA" + " Selected", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        etdesilserialnumber = findViewById(R.id.etdesindusloadserialnumber);
        etdesilvehiclenumber = findViewById(R.id.etdesindusloadvehical);
        etdesiltransport = findViewById(R.id.etdesindusloadtranseportname);
        etdesilintime = findViewById(R.id.etdesindusloadintime);
        etdesilremark = findViewById(R.id.eddesindusloadremark);
        etdesiltypeofpackingid = findViewById(R.id.etdesindusloadserialnumber);
        oa = findViewById(R.id.etoanumberindus);
        party = findViewById(R.id.etpartyname);
        tvAllRemarks = findViewById(R.id.iriniltv_allremarks);
//        etdesiltypeofpackingid=findViewById(R.id.autodesindusloadTypeOfPacking);

        btndesilsubmit = findViewById(R.id.etdesindusloadsubmit);
        pending = findViewById(R.id.pendingindustrial);

        tenltr = findViewById(R.id.etdesindusloadpacking10ltrqty);
        eighteenltr = findViewById(R.id.etdesindusloadpacking18ltrqty);
        twentyltr = findViewById(R.id.etdesindusloadpacking20ltrqty);
        twentysixltr = findViewById(R.id.etdesindusloadpacking26ltrqty);
        fiftyltr = findViewById(R.id.etdesindusloadpacking50ltrqty);
        twotenltr = findViewById(R.id.etdesindusloadpacking210ltrqty);
        boxbucket = findViewById(R.id.etdesindusloadpackingboxbucket);
        etdesiltotqty = findViewById(R.id.etdesindusloadTotalQuantity);
        weight = findViewById(R.id.industrialweight);
        indussign = findViewById(R.id.industrialsign);
        indusremark = findViewById(R.id.eddesindusloadremark);

        lnlsmallpackqty = findViewById(R.id.lnlsmallpackbarrel);
        splpack7literqty = findViewById(R.id.splpack7Liter);
        splpack7_5literqty = findViewById(R.id.splpack7_5Liter);
        splpack8_5literqty = findViewById(R.id.splpack8_5Liter);
        splpack10literqty = findViewById(R.id.splpack10Liter);
        splpack11literqty = findViewById(R.id.splpack11Liter);
        splpack12literqty = findViewById(R.id.splpack12Liter);
        splpack13literqty = findViewById(R.id.splpack13Liter);
        splpack15literqty = findViewById(R.id.splpack15Liter);
        splpack18literqty = findViewById(R.id.splpack18Liter);
        splpack20literqty = findViewById(R.id.splpack20Liter);
        splpack26literqty = findViewById(R.id.splpack26Liter);
        splpack50literqty = findViewById(R.id.splpack50Liter);
        splpack210literqty = findViewById(R.id.splpack210Liter);
        splpackboxbucketqty = findViewById(R.id.splpackboxbucket);
        spltotqty = findViewById(R.id.spltotqtyLiter);
        spltotweight = findViewById(R.id.spltotalweight);

        tenltr.addTextChangedListener(textWatcher);
        eighteenltr.addTextChangedListener(textWatcher);
        twentyltr.addTextChangedListener(textWatcher);
        twentysixltr.addTextChangedListener(textWatcher);
        fiftyltr.addTextChangedListener(textWatcher);
        twotenltr.addTextChangedListener(textWatcher);
        boxbucket.addTextChangedListener(textWatcher);


        updbtnclick =findViewById(R.id.indusupdateclick);

        FirebaseMessaging.getInstance().subscribeToTopic(token);
        submit = findViewById(R.id.etdesindusloadsubmit);
        setupHeader();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Id == 0) {
                    indusinsert();
                } else {
                    indusupdate();
                }

            }
        });
        etdesilintime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                String time = format.format(calendar.getTime());
                etdesilintime.setText(time);
            }
        });

        if (getIntent().hasExtra("vehiclenum")) {
            FetchVehicleDetails(getIntent().getStringExtra("vehiclenum"), Global_Var.getInstance().MenuType, nextProcess, inOut);
        }
        etdesilvehiclenumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasfocus) {
                if (!hasfocus) {
                    FetchVehicleDetails(etdesilvehiclenumber.getText().toString().trim(), vehicleType, nextProcess, inOut);
                }
            }
        });

        nextdeptmapping = new HashMap<>();
        nextdeptmapping.put("NA", "W");
        nextdeptmapping.put("SmallPack", "J");

        dept = findViewById(R.id.nextdept);
        nextdeptdrop = new ArrayAdapter<String>(this, R.layout.indus_nextdept, new ArrayList<>(nextdeptmapping.keySet()));
        dept.setAdapter(nextdeptdrop);
        dept.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String indusdept = parent.getItemAtPosition(position).toString();
                nextdeptvalue = nextdeptmapping.get(indusdept);
                if (deptNumericValue != null) {
                    Toasty.success(Outward_DesIndustriaLoading_Form.this, "NetWeighUnitofMeasurement : " + indusdept + " Selected", Toast.LENGTH_SHORT).show();
                } else {
                    Toasty.error(Outward_DesIndustriaLoading_Form.this, "Default NetWeighUnitofMeasurement : " + "NA", Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (sharedPreferences != null) {
            if (getIntent().hasExtra("VehicleNumber")) {
                String action = getIntent().getStringExtra("Action");
                if (action != null && action.equals("Up")) {
                    FetchVehicleDetailsforUpdate(getIntent().getStringExtra("VehicleNumber"), Global_Var.getInstance().MenuType, 'W', 'O');
                } else {
                    FetchVehicleDetails(getIntent().getStringExtra("VehicleNumber"), Global_Var.getInstance().MenuType, 'S', 'I');
                    submit.setVisibility(View.GONE);
//                    button1.setVisibility(View.GONE);
                }
//                btnadd.setVisibility(View.GONE);
            } else {
                //GetMaxSerialNo(vehicletype+ formattedDate);
            }

        } else {
            Log.e("MainActivity", "SharedPreferences is null");
        }


        updbtnclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upditinsecbyinwardid();
            }
        });


    }

    public void calculateTotal() {

        int total = 0;
        total += getEditTextValue(tenltr);
        total += getEditTextValue(eighteenltr);
        total += getEditTextValue(twentyltr);
        total += getEditTextValue(twentysixltr);
        total += getEditTextValue(fiftyltr);
        total += getEditTextValue(twotenltr);
        total += getEditTextValue(boxbucket);

        etdesiltotqty.setText(String.valueOf(total));
    }

    private int getEditTextValue(EditText editText) {

        try {

            return Integer.parseInt(editText.getText().toString());
        } catch (NumberFormatException e) {
            return 0; // Return 0 if EditText is empty or non-numeric

        }
    }

    public void btndesindusloadViewclick(View view) {
        Intent intent = new Intent(this, OR_DespatchIndustrial_Completed_Listing.class);
        startActivity(intent);
    }



    public void FetchVehicleDetailsforUpdate(@NonNull String VehicleNo, String vehicltype, char DeptType, char InOutType){
        Call<Model_industrial> call = outwardTruckInterface.fetchindusrtial(VehicleNo, vehicltype, DeptType, InOutType);
        call.enqueue(new Callback<Model_industrial>() {
            @Override
            public void onResponse(Call<Model_industrial> call, Response<Model_industrial> response) {
                if (response.isSuccessful()) {
                    Model_industrial data = response.body();
                    if (data.getVehicleNumber() != "" && data.getVehicleNumber() != null) {
                        OutwardId = data.getOutwardId();
                        Id = data.getId();
                        etdesilserialnumber.setText(data.getSerialNumber());
                        etdesilserialnumber.setEnabled(false);
                        etdesilvehiclenumber.setText(data.getVehicleNumber());
                        etdesilvehiclenumber.setEnabled(false);
                        vehnumber = data.getVehicleNumber();
                        etdesiltransport.setText(data.getTransportName());
                        etdesiltransport.setEnabled(false);
                        oa.setText(data.getOAnumber());
                        oa.setEnabled(false);
                        party.setText(data.getCustomerName());
                        party.setEnabled(false);

                        tenltr.setText(String.valueOf(data.getIlpackof10ltrqty()));
                        eighteenltr.setText(String.valueOf(data.getIlpackof18ltrqty()));
                        twentyltr.setText(String.valueOf(data.getIlpackof20ltrqty()));
                        twentysixltr.setText(String.valueOf(data.getIlpackof26ltrqty()));
                        fiftyltr.setText(String.valueOf(data.getIlpackof50ltrqty()));
                        twotenltr.setText(String.valueOf(data.getIlpackof210ltrqty()));
                        boxbucket.setText(String.valueOf(data.getIlpackofboxbuxketltrqty()));
                        etdesiltotqty.setText(String.valueOf(data.getIltotqty()));
                        weight.setText(String.valueOf(data.getIlweight()));
                        indussign.setText(String.valueOf(data.getIlsign()));

                        updbtnclick.setVisibility(View.VISIBLE);
                        submit.setVisibility(View.GONE);

                        if (!data.splweight.contains("0")) {
                            lnlsmallpackqty.setVisibility(View.VISIBLE);
                            splpack7literqty.setText("SmallPack 7 Liter Qty :- " + data.getSplpackof7ltrqty());
                            splpack7literqty.setEnabled(false);
                            splpack7_5literqty.setText("SmallPack 7.5 Liter Qty :- " + data.getSplpackof7_5ltrqty());
                            splpack7_5literqty.setEnabled(false);
                            splpack8_5literqty.setText("SmallPack 8.5 Liter Qty :- " + data.getSplpackof8_5ltrqty());
                            splpack8_5literqty.setEnabled(false);
                            splpack10literqty.setText("SmallPack 10 Liter Qty :- " + data.getSplpackof10ltrqty());
                            splpack10literqty.setEnabled(false);
                            splpack11literqty.setText("SmallPack 11 Liter Qty :- " + data.getSplpackof11ltrqty());
                            splpack11literqty.setEnabled(false);
                            splpack12literqty.setText("SmallPack 12 Liter Qty :- " + data.getSplpackof12ltrqty());
                            splpack12literqty.setEnabled(false);
                            splpack13literqty.setText("SmallPack 13 Liter Qty :- " + data.getSplpackof13ltrqty());
                            splpack13literqty.setEnabled(false);
                            splpack15literqty.setText("SmallPack 15 Liter Qty :- " + data.getSplpackof15ltrqty());
                            splpack15literqty.setEnabled(false);
                            splpack18literqty.setText("SmallPack 18 Liter Qty :- " + data.getSplpackof18ltrqty());
                            splpack18literqty.setEnabled(false);
                            splpack20literqty.setText("SmallPack 20 Liter Qty :- " + data.getSplpackof20ltrqty());
                            splpack20literqty.setEnabled(false);
                            splpack26literqty.setText("SmallPack 26 Liter Qty :- " + data.getSplpackof26ltrqty());
                            splpack26literqty.setEnabled(false);
                            splpack50literqty.setText("SmallPack 50 Liter Qty :- " + data.getSplpackof50ltrqty());
                            splpack50literqty.setEnabled(false);
                            splpack210literqty.setText("SmallPack 210 Liter Qty :- " + data.getSplpackof210ltrqty());
                            splpack210literqty.setEnabled(false);
                            splpackboxbucketqty.setText("SmallPack BoxBucket Qty :- " + data.getSplpackofboxbuxketltrqty());
                            splpackboxbucketqty.setEnabled(false);
                            spltotqty.setText("SmallPack Total Qty :- " + data.getSpltotqty());
                            spltotqty.setEnabled(false);
                            spltotweight.setText("SmallPack Total Weight :- " + data.getSplweight());
                            spltotweight.setEnabled(false);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Model_industrial> call, Throwable t) {
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
                Toasty.error(Outward_DesIndustriaLoading_Form.this, "failed..!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void FetchVehicleDetails(@NonNull String vehicleNo, String vehicleType, char NextProcess, char inOut) {
        Call<Model_industrial> call = outwardTruckInterface.fetchindusrtial(vehicleNo, vehicleType, NextProcess, inOut);
        call.enqueue(new Callback<Model_industrial>() {
            @Override
            public void onResponse(Call<Model_industrial> call, Response<Model_industrial> response) {
                if (response.isSuccessful()) {
                    Model_industrial data = response.body();
                    if (data.getVehicleNumber() != "" && data.getVehicleNumber() != null) {
                        OutwardId = data.getOutwardId();
                        Id = data.getId();
                        etdesilserialnumber.setText(data.getSerialNumber());
                        etdesilserialnumber.setEnabled(false);
                        etdesilvehiclenumber.setText(data.getVehicleNumber());
                        etdesilvehiclenumber.setEnabled(false);
                        vehnumber = data.getVehicleNumber();
                        etdesiltransport.setText(data.getTransportName());
                        etdesiltransport.setEnabled(false);
                        String allRemark = data.getAllORRemarks();
                        if (allRemark != null && !allRemark.trim().isEmpty()) {
                            tvAllRemarks.setText("   "+allRemark.replace(",", "\n")); // line-by-line
                        } else {
                            tvAllRemarks.setText("No system remarks.");
                        }
                        oa.setText(data.getOAnumber());
                        oa.setEnabled(false);
                        party.setText(data.getCustomerName());
                        party.setEnabled(false);
                        if (!data.splweight.contains("0")) {
                            lnlsmallpackqty.setVisibility(View.VISIBLE);
                            splpack7literqty.setText("SmallPack 7 Liter Qty :- " + data.getSplpackof7ltrqty());
                            splpack7literqty.setEnabled(false);
                            splpack7_5literqty.setText("SmallPack 7.5 Liter Qty :- " + data.getSplpackof7_5ltrqty());
                            splpack7_5literqty.setEnabled(false);
                            splpack8_5literqty.setText("SmallPack 8.5 Liter Qty :- " + data.getSplpackof8_5ltrqty());
                            splpack8_5literqty.setEnabled(false);
                            splpack10literqty.setText("SmallPack 10 Liter Qty :- " + data.getSplpackof10ltrqty());
                            splpack10literqty.setEnabled(false);
                            splpack11literqty.setText("SmallPack 11 Liter Qty :- " + data.getSplpackof11ltrqty());
                            splpack11literqty.setEnabled(false);
                            splpack12literqty.setText("SmallPack 12 Liter Qty :- " + data.getSplpackof12ltrqty());
                            splpack12literqty.setEnabled(false);
                            splpack13literqty.setText("SmallPack 13 Liter Qty :- " + data.getSplpackof13ltrqty());
                            splpack13literqty.setEnabled(false);
                            splpack15literqty.setText("SmallPack 15 Liter Qty :- " + data.getSplpackof15ltrqty());
                            splpack15literqty.setEnabled(false);
                            splpack18literqty.setText("SmallPack 18 Liter Qty :- " + data.getSplpackof18ltrqty());
                            splpack18literqty.setEnabled(false);
                            splpack20literqty.setText("SmallPack 20 Liter Qty :- " + data.getSplpackof20ltrqty());
                            splpack20literqty.setEnabled(false);
                            splpack26literqty.setText("SmallPack 26 Liter Qty :- " + data.getSplpackof26ltrqty());
                            splpack26literqty.setEnabled(false);
                            splpack50literqty.setText("SmallPack 50 Liter Qty :- " + data.getSplpackof50ltrqty());
                            splpack50literqty.setEnabled(false);
                            splpack210literqty.setText("SmallPack 210 Liter Qty :- " + data.getSplpackof210ltrqty());
                            splpack210literqty.setEnabled(false);
                            splpackboxbucketqty.setText("SmallPack BoxBucket Qty :- " + data.getSplpackofboxbuxketltrqty());
                            splpackboxbucketqty.setEnabled(false);
                            spltotqty.setText("SmallPack Total Qty :- " + data.getSpltotqty());
                            spltotqty.setEnabled(false);
                            spltotweight.setText("SmallPack Total Weight :- " + data.getSplweight());
                            spltotweight.setEnabled(false);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Model_industrial> call, Throwable t) {
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
                Toasty.error(Outward_DesIndustriaLoading_Form.this, "failed..!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private String getCurrentTime() {
        // Get the current time
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(new Date());
    }

    public void indusinsert() {
        String uintime = etdesilintime.getText().toString().trim();
        String uouttime = getCurrentTime();
        int u10 = !tenltr.getText().toString().trim().isEmpty() ? Integer.parseInt(tenltr.getText().toString().trim()) : 0;
        int u18 = !eighteenltr.getText().toString().trim().isEmpty() ? Integer.parseInt(eighteenltr.getText().toString().trim()) : 0;
        int u20 = !twentyltr.getText().toString().trim().isEmpty() ? Integer.parseInt(twentyltr.getText().toString().trim()) : 0;
        int u26 = !twentysixltr.getText().toString().trim().isEmpty() ? Integer.parseInt(twentysixltr.getText().toString().trim()) : 0;
        int u50 = !fiftyltr.getText().toString().trim().isEmpty() ? Integer.parseInt(fiftyltr.getText().toString().trim()) : 0;
        int u210 = !twotenltr.getText().toString().trim().isEmpty() ? Integer.parseInt(twotenltr.getText().toString().trim()) : 0;
        int uboxbucet = !boxbucket.getText().toString().trim().isEmpty() ? Integer.parseInt(boxbucket.getText().toString().trim()) : 0;
        String totalqty = etdesiltotqty.getText().toString().trim();
        String uweight = weight.getText().toString().trim();
        String uindussign = indussign.getText().toString().trim();
        String uindusremark = indusremark.getText().toString().trim();

        String uvehiclenum = etdesilvehiclenumber.getText().toString().trim();
        String userial = etdesilserialnumber.getText().toString().trim();
        String nextu = nextdeptvalue.trim();

        char Inoutins;
        if (nextu.contains("W")) {
            Inoutins = 'O';
        } else {
            Inoutins = 'I';
        }

        if (uindussign.isEmpty() || uweight.isEmpty() || nextu.isEmpty()) {
            Toasty.warning(this, "All fields must be filled", Toast.LENGTH_SHORT, true).show();
        } else {
            Model_industrial modelIndustrial = new Model_industrial(OutwardId, uintime, uouttime, u10, u18, u20,
                    u26, u50, u210, uboxbucet, totalqty, uweight, uindussign, uindusremark, EmployeId, uvehiclenum, vehicleType, userial,
                    EmployeId, 'W', Inoutins, nextu);
            dialogHelper.showConfirmationDialog(this, () -> {
                dialogHelper.showProgressDialog(this); // Show progress when confirmed
                Call<Boolean> call = outwardTruckInterface.insertindustrial(modelIndustrial);
                call.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if (response.isSuccessful() && response.body() && response.body()) {
                            dialogHelper.hideProgressDialog();
                            Toasty.success(Outward_DesIndustriaLoading_Form.this, "Data Inserted Succesfully !", Toast.LENGTH_SHORT).show();
                            makeNotification(vehnumber, uintime);
                            makeNotificationforsmallpack(vehnumber, uintime);
                            NavigationUtil.navigateAndClear(Outward_DesIndustriaLoading_Form.this, Grid_Outward.class);
                        }
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        dialogHelper.hideProgressDialog();
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
                        Toasty.error(Outward_DesIndustriaLoading_Form.this, "failed", Toast.LENGTH_SHORT).show();
                    }
                });
            });
        }
    }

    public void indusupdate() {
        String uintime = etdesilintime.getText().toString().trim();
        String uouttime = getCurrentTime();
        int u10 = !tenltr.getText().toString().trim().isEmpty() ? Integer.parseInt(tenltr.getText().toString().trim()) : 0;
        int u18 = !eighteenltr.getText().toString().trim().isEmpty() ? Integer.parseInt(eighteenltr.getText().toString().trim()) : 0;
        int u20 = !twentyltr.getText().toString().trim().isEmpty() ? Integer.parseInt(twentyltr.getText().toString().trim()) : 0;
        int u26 = !twentysixltr.getText().toString().trim().isEmpty() ? Integer.parseInt(twentysixltr.getText().toString().trim()) : 0;
        int u50 = !fiftyltr.getText().toString().trim().isEmpty() ? Integer.parseInt(fiftyltr.getText().toString().trim()) : 0;
        int u210 = !twotenltr.getText().toString().trim().isEmpty() ? Integer.parseInt(twotenltr.getText().toString().trim()) : 0;
        int uboxbucet = !boxbucket.getText().toString().trim().isEmpty() ? Integer.parseInt(boxbucket.getText().toString().trim()) : 0;
        String totalqty = etdesiltotqty.getText().toString().trim();
        String uweight = weight.getText().toString().trim();
        String uindussign = indussign.getText().toString().trim();
        String uindusremark = indusremark.getText().toString().trim();
        String nextu = nextdeptvalue.trim();

        char Inoutins;
        if (nextu.contains("W")) {
            Inoutins = 'O';
        } else {
            Inoutins = 'I';
        }

        if (uintime.isEmpty() || uouttime.isEmpty() || uweight.isEmpty() || uindusremark.isEmpty() || uindussign.isEmpty() || nextu.isEmpty()) {
            Toasty.warning(this, "All fields must be filled", Toast.LENGTH_SHORT, true).show();
        } else {

            Indus_update_Model indusUpdateModel = new Indus_update_Model(OutwardId, uintime, uouttime, u10, u18, u20, u26, u50, u210, uboxbucet, totalqty,
                    uweight, uindussign, uindusremark, EmployeId, vehicleType, EmployeId, 'W', Inoutins, nextu);
            dialogHelper.showConfirmationDialog(this, () -> {
                dialogHelper.showProgressDialog(this); // Show progress when confirmed
                Call<Boolean> call = outwardTruckInterface.updateindustrial(indusUpdateModel);
                call.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if (response.isSuccessful() && response.body() && response.body()) {
                            dialogHelper.hideProgressDialog(); // Hide after response
                            Toasty.success(Outward_DesIndustriaLoading_Form.this, "Data Inserted Succesfully !", Toast.LENGTH_SHORT).show();
                            makeNotification(vehnumber, uintime);
                            startActivity(new Intent(Outward_DesIndustriaLoading_Form.this, Grid_Outward.class));
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
                        Toasty.error(Outward_DesIndustriaLoading_Form.this, "failed", Toast.LENGTH_SHORT).show();
                    }
                });
            });
        }
    }

    public void upditinsecbyinwardid(){
        String uintime = etdesilintime.getText().toString().trim();
        String uouttime = getCurrentTime();
        int u10 = !tenltr.getText().toString().trim().isEmpty() ? Integer.parseInt(tenltr.getText().toString().trim()) : 0;
        int u18 = !eighteenltr.getText().toString().trim().isEmpty() ? Integer.parseInt(eighteenltr.getText().toString().trim()) : 0;
        int u20 = !twentyltr.getText().toString().trim().isEmpty() ? Integer.parseInt(twentyltr.getText().toString().trim()) : 0;
        int u26 = !twentysixltr.getText().toString().trim().isEmpty() ? Integer.parseInt(twentysixltr.getText().toString().trim()) : 0;
        int u50 = !fiftyltr.getText().toString().trim().isEmpty() ? Integer.parseInt(fiftyltr.getText().toString().trim()) : 0;
        int u210 = !twotenltr.getText().toString().trim().isEmpty() ? Integer.parseInt(twotenltr.getText().toString().trim()) : 0;
        int uboxbucet = !boxbucket.getText().toString().trim().isEmpty() ? Integer.parseInt(boxbucket.getText().toString().trim()) : 0;
        String totalqty = etdesiltotqty.getText().toString().trim();
        String uweight = weight.getText().toString().trim();
        String uindussign = indussign.getText().toString().trim();
        String uindusremark = indusremark.getText().toString().trim();
        String nextu = nextdeptvalue.trim();

        char Inoutins;
        if (nextu.contains("W")) {
            Inoutins = 'O';
        } else {
            Inoutins = 'I';
        }

        if (uintime.isEmpty() || uouttime.isEmpty() || uweight.isEmpty() || uindusremark.isEmpty() || uindussign.isEmpty() || nextu.isEmpty()) {
            Toasty.warning(this, "All fields must be filled", Toast.LENGTH_SHORT, true).show();
        } else {

            Indus_update_Model indusUpdateModel = new Indus_update_Model(OutwardId, uintime, uouttime, u10, u18, u20, u26, u50, u210, uboxbucet, totalqty,
                    uweight, uindussign, uindusremark, EmployeId, vehicleType, EmployeId, 'W', Inoutins, nextu);
            dialogHelper.showConfirmationDialog(this, () -> {
                dialogHelper.showProgressDialog(this); // Show progress when confirmed
                Call<Boolean> call = outwardTruckInterface.updateindustrial(indusUpdateModel);
                call.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if (response.isSuccessful() && response.body() && response.body()) {
                            dialogHelper.hideProgressDialog(); // Hide after response
                            Toasty.success(Outward_DesIndustriaLoading_Form.this, "Data Inserted Succesfully !", Toast.LENGTH_SHORT).show();
                            makeNotification(vehnumber, uintime);
                            startActivity(new Intent(Outward_DesIndustriaLoading_Form.this, Grid_Outward.class));
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
                        Toasty.error(Outward_DesIndustriaLoading_Form.this, "failed", Toast.LENGTH_SHORT).show();
                    }
                });
            });
        }
    }

    public void makeNotification(String vehicleNumber, String outTime) {
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
                                        "Outward Truck Industrail Pack Process Done..!",
                                        "Vehicle Number:-" + vehicleNumber + " has completed Industrail Pack process at " + outTime,
                                        getApplicationContext(),
                                        Outward_DesIndustriaLoading_Form.this
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
                Toasty.error(Outward_DesIndustriaLoading_Form.this, "failed..!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void makeNotificationforsmallpack(String vehicleNumber, String outTime) {
        Call<List<ResponseModel>> call = userDetails.getUsersListData();
        call.enqueue(new Callback<List<ResponseModel>>() {
            @Override
            public void onResponse(Call<List<ResponseModel>> call, Response<List<ResponseModel>> response) {
                if (response.isSuccessful()) {
                    List<ResponseModel> userList = response.body();
                    if (userList != null) {
                        for (ResponseModel resmodel : userList) {
                            String specificRole = "SmallPack";
                            if (specificRole.equals(resmodel.getDepartment())) {
                                token = resmodel.getToken();

                                FcmNotificationsSender notificationsSender = new FcmNotificationsSender(
                                        token,
                                        "Outward Truck Industrail Pack Process Done..!",
                                        "Vehicle Number:-" + vehicleNumber + " has completed Industrail Pack process at " + outTime,
                                        getApplicationContext(),
                                        Outward_DesIndustriaLoading_Form.this
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
                Toasty.error(Outward_DesIndustriaLoading_Form.this, "failed..!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void pendingindustrial(View view) {
        Intent intent = new Intent(this, Grid_Outward.class);
        startActivity(intent);
    }

}