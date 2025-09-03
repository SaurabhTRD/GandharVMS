package com.android.gandharvms.OT_VehicleStatus_Grid;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gandharvms.Global_Var;
import com.android.gandharvms.Inward_Tanker_Security.Respo_Model_In_Tanker_security;
import com.android.gandharvms.Inward_Tanker_Security.grid;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.NotificationAlerts.NotificationCommonfunctioncls;
import com.android.gandharvms.Outward_Tanker_Security.Grid_Outward;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.Outward_Tanker_Security.Outward_Tanker;
import com.android.gandharvms.Outward_Tanker_Security.Response_Outward_Security_Fetching;
import com.android.gandharvms.Outward_Truck_Security.Common_Outward_model;
import com.android.gandharvms.Outward_Truck_Security.SecOut_OR_Complete;
import com.android.gandharvms.R;
import com.android.gandharvms.Util.FixedGridLayoutManager;
import com.android.gandharvms.outward_Tanker_Lab_forms.OT_Completd_bilkload_laboratory;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class ot_statusgrid_livedata extends NotificationCommonfunctioncls {

    RecyclerView rvClub;
    int scrollX = 0;
    HorizontalScrollView headerscroll;
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private final String EmployeId = Global_Var.getInstance().EmpId;
    List<Response_Outward_Security_Fetching> outwardclublist = new ArrayList<>();
    ot_statusgrid_adapter outwardGridAdapter;
    Button fromDate,toDate;
    String fromdate;
    String todate;

    TextView currdate;
    String strvehiclenumber = "x";
    //List<Respo_Model_In_Tanker_security> clubList = new ArrayList<>();
    List<Common_Outward_model> clubList = new ArrayList<>();
    private Outward_Tanker outwardTanker;
    EditText edtVehicleNumber, edtCustomerName;
    Button btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        outwardTanker = RetroApiClient.insertoutwardtankersecurity();
        setContentView(R.layout.activity_ot_statusgrid_livedata);
        currdate=findViewById(R.id.otgridcurrrentdate);
        edtVehicleNumber = findViewById(R.id.edtVehicleNumber);
        edtCustomerName = findViewById(R.id.edtCustomerName);
        btnSearch = findViewById(R.id.btnSearch);
        setupHeader();
        currdate.setText(getCurrentDateTime().toString().trim());
        fromDate = findViewById(R.id.gridbtnfromDateoutwardtanker);
        toDate = findViewById(R.id.gridbtntoDateoutwardtanker);
        initViews();
        fetchDataFromApiWithProgressDialog();

//        if(Global_Var.getInstance().DeptType!=0 && Integer.valueOf(Global_Var.getInstance().DeptType) !=120)
//        {
//            ProgressDialog loadingDialog = new ProgressDialog(ot_statusgrid_livedata.this);
//            loadingDialog.setMessage("Syncing data, please wait...");
//            loadingDialog.setCancelable(false); // Prevent user from dismissing the dialog
//            loadingDialog.show();
//            fromdate = getCurrentDateTime();
//            todate = getCurrentDateTime();
////            fetchDataFromApi(fromdate,todate,"x",vehicleType,Global_Var.getInstance().DeptType,inOut);
//            fetchDataFromApi(fromdate, todate, strvehiclenumber, vehicleType, Global_Var.getInstance().DeptType, inOut, loadingDialog);
//        }
//        else{
//            ProgressDialog loadingDialog = new ProgressDialog(ot_statusgrid_livedata.this);
//            loadingDialog.setMessage("Syncing data, please wait...");
//            loadingDialog.setCancelable(false); // Prevent user from dismissing the dialog
//            loadingDialog.show();
////            fetchDataFromApi("x",vehicleType,'x','x');
//            fromdate = getCurrentDateTime();
//            todate = getCurrentDateTime();
//            fetchDataFromApi(fromdate, todate, strvehiclenumber, vehicleType, 'x', 'x', loadingDialog);
//        }
        rvClub.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                super.onScrolled(recyclerView, dx, dy);
                scrollX += dx;
                headerscroll.scrollTo(scrollX, 0);
            }
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState)
            {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        fromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the onClick for fromDate button
                showDatePickerDialog(fromDate, true);
            }
        });

        toDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the onClick for fromDate button
                showDatePickerDialog(toDate, false);
            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vehicleNumber = edtVehicleNumber.getText().toString().trim();
                String customerName = edtCustomerName.getText().toString().trim();

                // Apply filter in adapter
                outwardGridAdapter.getFilter().filter(vehicleNumber + "|" + customerName);
            }
        });
    }

    private void initViews()
    {
        rvClub = findViewById(R.id.otstatusgridrecyclerview);
        headerscroll = findViewById(R.id.otstatusgridheaderscroll);
    }
    private void setUpRecyclerView()
    {
        outwardGridAdapter = new ot_statusgrid_adapter(outwardclublist);
        FixedGridLayoutManager manager = new FixedGridLayoutManager();
        manager.setTotalColumnCount(1);
        rvClub.setLayoutManager(manager);
        rvClub.setAdapter(outwardGridAdapter);
        rvClub.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }


//    private void fetchDataFromApi(String vehicleno,String vehicleType,char nextProcess, char inOut) {
//        Call<List<Response_Outward_Security_Fetching>> call = Outward_RetroApiclient.insertoutwardtankersecurity().outwardsecurityfetching(vehicleno, vehicleType, nextProcess, inOut);
//        call.enqueue(new Callback<List<Response_Outward_Security_Fetching>>() {
//            @Override
//            public void onResponse(Call<List<Response_Outward_Security_Fetching>> call, Response<List<Response_Outward_Security_Fetching>> response) {
//                if (response.isSuccessful()){
//                    if (response.body().size() > 0){
//                        List<Response_Outward_Security_Fetching> data = response.body();
//                        outwardclublist= data;
//                        setUpRecyclerView();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Response_Outward_Security_Fetching>> call, Throwable t) {
//                Log.e("Retrofit", "Failure: " + t.getMessage());
//                // Check if there's a response body in case of an HTTP error
//                if (call != null && call.isExecuted() && call.isCanceled() && t instanceof HttpException) {
//                    Response<?> response = ((HttpException) t).response();
//                    if (response != null) {
//                        Log.e("Retrofit", "Error Response Code: " + response.code());
//                        try {
//                            Log.e("Retrofit", "Error Response Body: " + response.errorBody().string());
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }
//        });
//    }

    private void fetchDataFromApiWithProgressDialog() {
        ProgressDialog loadingDialog = new ProgressDialog(ot_statusgrid_livedata.this);
        loadingDialog.setMessage("Syncing data, please wait...");
        loadingDialog.setCancelable(false); // Prevent user from dismissing the dialog
        loadingDialog.show();

        if(Global_Var.getInstance().DeptType!=0 && Integer.valueOf(Global_Var.getInstance().DeptType) !=120)
        {
            fromdate = getCurrentDateTime();
            todate = getCurrentDateTime();
            fetchDataFromApi(fromdate,todate,strvehiclenumber,vehicleType,Global_Var.getInstance().DeptType,inOut,loadingDialog);
        }
        else{
            fromdate = getCurrentDateTime();
            todate = getCurrentDateTime();
            fetchDataFromApi(fromdate,todate,strvehiclenumber,vehicleType,'x','x',loadingDialog);
        }
    }
    private void fetchDataFromApi(String fromdate, String todate, String vehicleno, String vehicleType, char nextProcess, char inOut, ProgressDialog loadingDialog) {
        Call<List<Response_Outward_Security_Fetching>> call = RetroApiClient
                .insertoutwardtankersecurity()
                .getOutwardDatabyDateFilter(fromdate,todate,vehicleno, vehicleType, nextProcess, inOut);

        call.enqueue(new Callback<List<Response_Outward_Security_Fetching>>() {
            @Override
            public void onResponse(Call<List<Response_Outward_Security_Fetching>> call, Response<List<Response_Outward_Security_Fetching>> response) {
                // Dismiss ProgressDialog
                if (loadingDialog != null && loadingDialog.isShowing()) {
                    loadingDialog.dismiss();
                }

                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    outwardclublist = response.body();
                    setUpRecyclerView();
                } else {
                    Toasty.warning(getApplicationContext(), "No data available", Toast.LENGTH_SHORT).show();
                    // Clear the RecyclerView if no data
                    outwardclublist.clear();
                    setUpRecyclerView();
                }
            }

            @Override
            public void onFailure(Call<List<Response_Outward_Security_Fetching>> call, Throwable t) {
                // Dismiss ProgressDialog
                if (loadingDialog != null && loadingDialog.isShowing()) {
                    loadingDialog.dismiss();
                }

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
                Toasty.error(ot_statusgrid_livedata.this, "Failed to Fetch Data.Server Issues..!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getCurrentDateTime() {
        // Get current date and time
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();

        // Format the date and time as a string
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM/dd/yyyy", Locale.ENGLISH);
        return dateFormat.format(now);
    }
    private void showDatePickerDialog(final TextView dateTextView, final boolean isFromDate) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Update the TextView with the selected date
                        String selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                        if ((isFromDate || !isFromDate)) {
                            dateTextView.setText(selectedDate);
                            if (isFromDate) {
                                fromdate = selectedDate;
                            } else {
                                todate = selectedDate;
                            }
                                ProgressDialog loadingDialog = new ProgressDialog(ot_statusgrid_livedata.this);
                                loadingDialog.setMessage("Syncing data, please wait...");
                                loadingDialog.setCancelable(false); // Prevent user from dismissing the dialog
                                loadingDialog.show();
                                fetchDataFromApi(fromdate, todate, strvehiclenumber, vehicleType, Global_Var.getInstance().DeptType, inOut, loadingDialog);

                            rvClub.addOnScrollListener(new RecyclerView.OnScrollListener() {
                                @Override
                                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                                    super.onScrolled(recyclerView, dx, dy);
                                    //scrollX += dx;
                                    //headerscroll.scrollTo(scrollX, 0);
                                }

                                @Override
                                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                                    super.onScrollStateChanged(recyclerView, newState);
                                }
                            });
                        } else {
                            // Show an error message or take appropriate action
                            Toasty.warning(ot_statusgrid_livedata.this, "Invalid date selection", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                year, month, day);
        if (isFromDate && !todate.isEmpty()) {
            try {
                datePickerDialog.getDatePicker().setMaxDate(new SimpleDateFormat("MMM/dd/yyyy", Locale.getDefault()).parse(todate).getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else if (!isFromDate && !fromdate.isEmpty()) {
            try {
                datePickerDialog.getDatePicker().setMinDate(new SimpleDateFormat("MMM/dd/yyyy", Locale.getDefault()).parse(fromdate).getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        // Show the date picker dialog
        datePickerDialog.show();
    }
}