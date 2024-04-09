package com.android.gandharvms.Inward_Tanker_Production;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.gandharvms.FcmNotificationsSender;
import com.android.gandharvms.Global_Var;
import com.android.gandharvms.InwardCompletedGrid.GridCompleted;
import com.android.gandharvms.Inward_Tanker;
import com.android.gandharvms.Inward_Tanker_Laboratory.Inward_Tanker_Laboratory;
import com.android.gandharvms.Inward_Tanker_Laboratory.it_Lab_Completedgrid;
import com.android.gandharvms.Inward_Tanker_Security.In_Tanker_Security_list;
import com.android.gandharvms.Inward_Tanker_Security.Inward_Tanker_Security;
import com.android.gandharvms.Inward_Tanker_Security.Respo_Model_In_Tanker_security;
import com.android.gandharvms.Inward_Tanker_Security.RetroApiclient_In_Tanker_Security;
import com.android.gandharvms.Inward_Tanker_Security.grid;
import com.android.gandharvms.Inward_Tanker_Weighment.Inward_Tanker_Weighment;
import com.android.gandharvms.LoginWithAPI.LoginMethod;
import com.android.gandharvms.LoginWithAPI.ResponseModel;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
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
import com.google.firebase.Timestamp;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;
import java.text.SimpleDateFormat;
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

public class Inward_Tanker_Production extends AppCompatActivity {

    final Calendar calendar = Calendar.getInstance();
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private final String EmployeName = Global_Var.getInstance().Name;
    private final String EmployeId = Global_Var.getInstance().EmpId;
    EditText etint, etserno, edunloadabovematerial, abovematerialunload, etconbyop, opratorname, etconunloadDateTime, etMaterial, etVehicleNumber;
    /*  Button viewdata;*/
    Button prosubmit, viewlabreport, updateproclick;
    FirebaseFirestore prodbroot;
    DatePickerDialog picker;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-YYYY, HH:mm:ss");
    Timestamp timestamp = new Timestamp(calendar.getTime());
    TimePickerDialog tpicker;
    private String token;
    private API_In_Tanker_production apiInTankerProduction;
    private int inwardid;
    private LoginMethod userDetails;
    private int reqtounload;
    private int abovematerialtank;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inward_tanker_production);

        //Send Notification to all
        FirebaseMessaging.getInstance().subscribeToTopic(token);

        userDetails = RetroApiClient.getLoginApi();

        etVehicleNumber = findViewById(R.id.etvehicleNumber);
        etMaterial = findViewById(R.id.etMaterial);
        etserno = findViewById(R.id.etpserialnumber);
        etconunloadDateTime = findViewById(R.id.etconunloadDateTime);
        etint = findViewById(R.id.etintime);
        edunloadabovematerial = findViewById(R.id.etreq);
        etconbyop = findViewById(R.id.etconbyop);
        abovematerialunload = findViewById(R.id.ettankno);
        opratorname = findViewById(R.id.tanknoun);
        viewlabreport = findViewById(R.id.btn_ViewlabReport);

        prosubmit = findViewById(R.id.prosubmit);
        updateproclick = findViewById(R.id.itproupdateclick);
        apiInTankerProduction = RetroApiclient_In_Tanker_Security.getinproductionApi();
        //datetimepickertesting
        etconunloadDateTime = findViewById(R.id.etconunloadDateTime);

        if (getIntent().hasExtra("VehicleNumber")) {
            String action = getIntent().getStringExtra("Action");
            if (action != null && action.equals("Up")) {
                FetchVehicleDetailsforUpdate(getIntent().getStringExtra("VehicleNumber"), Global_Var.getInstance().MenuType, 'x', 'O');
            } else {
                FetchVehicleDetails(getIntent().getStringExtra("VehicleNumber"), Global_Var.getInstance().MenuType, nextProcess, inOut);
            }
        }
        etconunloadDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                // Array of month abbreviations
                String[] monthAbbreviations = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
                picker = new DatePickerDialog(Inward_Tanker_Production.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Use the month abbreviation from the array
                        String monthAbbreviation = monthAbbreviations[month];
                        // etdate.setText(dayOfMonth + "/" + monthAbbreviation + "/" + year);
                        etconunloadDateTime.setText(dateFormat.format(calendar.getTime()));
                    }
                }, year, month, day);
                picker.show();
            }
        });

        edunloadabovematerial.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not needed for this implementation
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Not needed for this implementation
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String currentText = edunloadabovematerial.getText().toString();
                if (editable.length() > 0 && editable.length() <= 8) {
                    // Clear any previous error message when valid
                    edunloadabovematerial.setError(null);
                } else {
                    String trimmedText = editable.toString().substring(0, Math.min(editable.length(), 8));
                    if (!currentText.equals(trimmedText)) {
                        // Only set text and move cursor if the modification is not the desired text
                        edunloadabovematerial.setText(trimmedText);
                        edunloadabovematerial.setSelection(trimmedText.length()); // Move cursor to the end
                    }
                }
            }
        });

        abovematerialunload.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not needed for this implementation
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Not needed for this implementation
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String currentText = abovematerialunload.getText().toString();
                if (editable.length() > 0 && editable.length() <= 8) {
                    // Clear any previous error message when valid
                    abovematerialunload.setError(null);
                } else {
                    String trimmedText = editable.toString().substring(0, Math.min(editable.length(), 8));
                    if (!currentText.equals(trimmedText)) {
                        // Only set text and move cursor if the modification is not the desired text
                        abovematerialunload.setText(trimmedText);
                        abovematerialunload.setSelection(trimmedText.length()); // Move cursor to the end
                    }
                }
            }
        });

        etint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                String time = format.format(calendar.getTime());
                etint.setText(time);
            }
        });

        etVehicleNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String vehicltype = Global_Var.getInstance().MenuType;
                    char DeptType = Global_Var.getInstance().DeptType;
                    char InOutType = Global_Var.getInstance().InOutType;
                    FetchVehicleDetails(etVehicleNumber.getText().toString().trim(), vehicltype, DeptType, InOutType);

                }
            }
        });

        prodbroot = FirebaseFirestore.getInstance();

        prosubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proinsertdata();
            }
        });

        updateproclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proupdatebyinwardid();
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
                                        "Inward Tanker Production Process Done..!",
                                        "Vehicle Number:-" + vehicleNumber + " has completed Production process at " + outTime,
                                        getApplicationContext(),
                                        Inward_Tanker_Production.this
                                );
                                notificationsSender.SendNotifications();
                            }
                        }
                    }
                } else {
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

    public void proinsertdata() {
        String intime = etint.getText().toString().trim();
        String etser = etserno.getText().toString().trim();
        String eddate = etconunloadDateTime.getText().toString().trim();
        //int reqtounload = Integer.parseInt(edunloadabovematerial.getText().toString().trim());
        if (!edunloadabovematerial.getText().toString().isEmpty()) {
            try {
                reqtounload = Integer.parseInt(edunloadabovematerial.getText().toString().trim());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        //int abovematerialtank = Integer.parseInt(abovematerialunload.getText().toString().trim());
        if (!abovematerialunload.getText().toString().isEmpty()) {
            try {
                abovematerialtank = Integer.parseInt(abovematerialunload.getText().toString().trim());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        String confirmunload = etconbyop.getText().toString().trim();
        String oprator = opratorname.getText().toString().trim();
        String conunload = etconunloadDateTime.getText().toString().trim();
        String outTime = getCurrentTime();//Insert out Time Directly to the Database
        String material = etMaterial.getText().toString().trim();
        String vehicleNumber = etVehicleNumber.getText().toString().trim();

        if (intime.isEmpty() || reqtounload < 0 || abovematerialtank < 0 || confirmunload.isEmpty() || oprator.isEmpty() || conunload.isEmpty() || material.isEmpty() || vehicleNumber.isEmpty()) {
            Toasty.warning(this, "All Fields must be filled", Toast.LENGTH_SHORT, true).show();
        } else {
            Request_In_Tanker_Production requestInTankerProduction = new Request_In_Tanker_Production(inwardid, intime,
                    outTime, reqtounload, confirmunload, abovematerialtank, oprator,
                    EmployeId, vehicleNumber, etser, 'W', 'O', vehicleType, eddate, material, EmployeName);

            Call<Boolean> call = apiInTankerProduction.insertproductionData(requestInTankerProduction);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body() != null && response.body()==true) {
                        makeNotification(vehicleNumber, outTime);
                        Log.d("Production", "Response Body: " + response.body());
                        Toasty.success(Inward_Tanker_Production.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Inward_Tanker_Production.this, Inward_Tanker.class));
                        finish();
                    } else {
                        Toasty.error(Inward_Tanker_Production.this, "Data Insertion Failed..!", Toast.LENGTH_SHORT).show();
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
                    Toasty.error(Inward_Tanker_Production.this, "failed..!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void onBackPressed() {
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
        finish();
    }

    public void FetchVehicleDetails(@NonNull String VehicleNo, String vehicltype, char DeptType, char InOutType) {
        Call<Respo_Model_In_Tanker_Production> call = apiInTankerProduction.GetinTankerprodcutionByvehicle(VehicleNo, vehicltype, DeptType, InOutType);
        call.enqueue(new Callback<Respo_Model_In_Tanker_Production>() {
            @Override
            public void onResponse(Call<Respo_Model_In_Tanker_Production> call, Response<Respo_Model_In_Tanker_Production> response) {
                if (response.isSuccessful()) {
                    Respo_Model_In_Tanker_Production data = response.body();
                    if (data.getVehicleNo() != "" && data.getVehicleNo() != null) {
                        inwardid = data.getInwardId();
                        etserno.setText(data.getSerialNo());
                        etserno.setEnabled(false);
                        etVehicleNumber.setText(data.getVehicleNo());
                        etVehicleNumber.setEnabled(false);
                        etMaterial.setText(data.getMaterial());
                        etMaterial.setEnabled(false);
                        etconunloadDateTime.setText(data.getDate());
                        etconunloadDateTime.setEnabled(false);
                        viewlabreport.setVisibility(View.VISIBLE);
                    } else {
                        Toasty.error(Inward_Tanker_Production.this, "This Vehicle Is Not Available..!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Respo_Model_In_Tanker_Production> call, Throwable t) {

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
                Toasty.error(Inward_Tanker_Production.this, "failed..!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void FetchVehicleDetailsforUpdate(@NonNull String VehicleNo, String vehicltype, char DeptType, char InOutType) {
        Call<Respo_Model_In_Tanker_Production> call = apiInTankerProduction.GetinTankerprodcutionByvehicle(VehicleNo, vehicltype, DeptType, InOutType);
        call.enqueue(new Callback<Respo_Model_In_Tanker_Production>() {
            @Override
            public void onResponse(Call<Respo_Model_In_Tanker_Production> call, Response<Respo_Model_In_Tanker_Production> response) {
                if (response.isSuccessful()) {
                    Respo_Model_In_Tanker_Production data = response.body();
                    if (data.getVehicleNo() != "" && data.getVehicleNo() != null) {
                        inwardid = data.getInwardId();
                        etserno.setText(data.getSerialNo());
                        etserno.setEnabled(false);
                        etVehicleNumber.setText(data.getVehicleNo());
                        etVehicleNumber.setEnabled(false);
                        etMaterial.setText(data.getMaterial());
                        etMaterial.setEnabled(false);
                        etconunloadDateTime.setText(data.getDate());
                        etconunloadDateTime.setEnabled(false);
                        edunloadabovematerial.setText(String.valueOf(data.getUnloadAboveMaterialInTK()));
                        edunloadabovematerial.setEnabled(true);
                        etconbyop.setText(data.getProductName());
                        etconbyop.setEnabled(true);
                        abovematerialunload.setText(String.valueOf(data.getAboveMaterialIsUnloadInTK()));
                        abovematerialunload.setEnabled(true);
                        opratorname.setText(data.getOperatorName());
                        opratorname.setEnabled(true);
                        viewlabreport.setVisibility(View.VISIBLE);
                        updateproclick.setVisibility(View.VISIBLE);
                        prosubmit.setVisibility(View.GONE);
                        etint.setVisibility(View.GONE);
                    } else {
                        Toasty.error(Inward_Tanker_Production.this, "This Vehicle Number Is Out From Factory.\n You Can Not Update", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(Inward_Tanker_Production.this, it_pro_Completedgrid.class));
                    }
                }
            }

            @Override
            public void onFailure(Call<Respo_Model_In_Tanker_Production> call, Throwable t) {

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
                Toasty.error(Inward_Tanker_Production.this, "failed..!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void proupdatebyinwardid() {
        if (!edunloadabovematerial.getText().toString().isEmpty()) {
            try {
                reqtounload = Integer.parseInt(edunloadabovematerial.getText().toString().trim());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        if (!abovematerialunload.getText().toString().isEmpty()) {
            try {
                abovematerialtank = Integer.parseInt(abovematerialunload.getText().toString().trim());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        String confirmunload = etconbyop.getText().toString().trim();
        String oprator = opratorname.getText().toString().trim();

        It_Pro_UpdateByInwardid_req_model updproreqmodel = new It_Pro_UpdateByInwardid_req_model(inwardid,
                reqtounload, confirmunload, abovematerialtank, oprator,
                EmployeId);

        Call<Boolean> call = apiInTankerProduction.updprobyinwardid(updproreqmodel);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful() && response.body() != null && response.body()==true) {
                    Log.d("Production", "Response Body: " + response.body());
                    Toasty.success(Inward_Tanker_Production.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Inward_Tanker_Production.this, Inward_Tanker.class));
                    finish();
                } else {
                    Toasty.error(Inward_Tanker_Production.this, "Data Insertion Failed..!", Toast.LENGTH_SHORT).show();
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
                Toasty.error(Inward_Tanker_Production.this, "failed..!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void statusgrid(View view) {
        Intent intent = new Intent(this, it_pro_Completedgrid.class);
        startActivity(intent);
    }

    public void btn_clicktoViewLabReport(View view) {
        Global_Var.getInstance().DeptType = 'L';
        Intent intent = new Intent(this, it_Lab_Completedgrid.class);
        intent.putExtra("vehiclenumber", etVehicleNumber.getText());
        view.getContext().startActivity(intent);
    }

    public void ProductionViewclick(View view) {
        Intent intent = new Intent(this, grid.class);
        startActivity(intent);
    }
}