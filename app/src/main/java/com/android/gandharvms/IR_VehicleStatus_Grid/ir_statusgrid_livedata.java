package com.android.gandharvms.IR_VehicleStatus_Grid;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.HorizontalScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gandharvms.Global_Var;
import com.android.gandharvms.IT_VehicleStatus_Grid.it_statusgrid_adapter;
import com.android.gandharvms.IT_VehicleStatus_Grid.it_statusgrid_livedata;
import com.android.gandharvms.Inward_Tanker_Security.Respo_Model_In_Tanker_security;
import com.android.gandharvms.Inward_Tanker_Security.grid;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.NotificationAlerts.NotificationCommonfunctioncls;
import com.android.gandharvms.OT_VehicleStatus_Grid.ot_statusgrid_livedata;
import com.android.gandharvms.Outward_Tanker_Security.Outward_RetroApiclient;
import com.android.gandharvms.Outward_Tanker_Security.Response_Outward_Security_Fetching;
import com.android.gandharvms.R;
import com.android.gandharvms.Util.FixedGridLayoutManager;

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

public class ir_statusgrid_livedata extends NotificationCommonfunctioncls {

    int scrollX = 0;
    List<Respo_Model_In_Tanker_security> clubList = new ArrayList<>();
    RecyclerView rvClub;
    HorizontalScrollView headerscroll;
    ir_statusgrid_adapter GridAdapter;

    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private final String EmployeId = Global_Var.getInstance().EmpId;
    Button fromDate,toDate;
    String fromdate;
    String todate;
    String strvehiclenumber = "x";
    List<Response_Outward_Security_Fetching> outwardclublist = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ir_statusgrid_livedata);
        fromDate = findViewById(R.id.gridbtnfromDateInwardtanker);
        toDate = findViewById(R.id.gridbtntoDateInwardtanker);
        initViews();
        setupHeader();
        fetchDataFromApiWithProgressDialog();
//        if(Global_Var.getInstance().DeptType!=0 && Integer.valueOf(Global_Var.getInstance().DeptType) !=120)
//        {
//            fetchDataFromApi("x",vehicleType,Global_Var.getInstance().DeptType,inOut);
//        }
//        else{
//            fetchDataFromApi("x",vehicleType,'x','x');
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
    }
    private void initViews()
    {
        rvClub = findViewById(R.id.irstatusgridrecyclerview);
        headerscroll = findViewById(R.id.irstatusgridheaderscroll);
    }

    private void setUpRecyclerView()
    {
        GridAdapter  = new ir_statusgrid_adapter(clubList);
        FixedGridLayoutManager manager = new FixedGridLayoutManager();
        manager.setTotalColumnCount(1);
        rvClub.setLayoutManager(manager);
        rvClub.setAdapter(GridAdapter);
        rvClub.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

//    public void fetchDataFromApi(String vehicleno,String vehicleType,char nextProcess, char inOut) {
//        Call<List<Respo_Model_In_Tanker_security>> call = RetroApiClient.getserccrityveh().GetIntankerSecurityByVehicle(vehicleno, vehicleType, nextProcess, inOut);
//        call.enqueue(new Callback<List<Respo_Model_In_Tanker_security>>() {
//            @Override
//            public void onResponse(Call<List<Respo_Model_In_Tanker_security>> call, Response<List<Respo_Model_In_Tanker_security>> response) {
//                if (response.isSuccessful()) {
//                    if (response.body().size() > 0) {
//                        List<Respo_Model_In_Tanker_security> data = response.body();
//                        clubList = data;
//                        setUpRecyclerView();
//                    }else{
//                        Toasty.info(ir_statusgrid_livedata.this,"Data Is Not Available..!", Toast.LENGTH_LONG).show();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Respo_Model_In_Tanker_security>> call, Throwable t) {
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
//                Toasty.error(ir_statusgrid_livedata.this,"failed..!", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    private void fetchDataFromApiWithProgressDialog() {
        ProgressDialog loadingDialog = new ProgressDialog(ir_statusgrid_livedata.this);
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
    public void fetchDataFromApi(String fromdate, String todate, String vehicleno, String vehicleType, char nextProcess, char inOut, ProgressDialog loadingDialog) {
        Call<List<Respo_Model_In_Tanker_security>> call = RetroApiClient.getserccrityveh().GetInwarddatebyfilterdatewise(fromdate, todate, vehicleno, vehicleType, nextProcess, inOut);
        call.enqueue(new Callback<List<Respo_Model_In_Tanker_security>>() {
            @Override
            public void onResponse(Call<List<Respo_Model_In_Tanker_security>> call, Response<List<Respo_Model_In_Tanker_security>> response) {
                // Dismiss ProgressDialog
                if (loadingDialog != null && loadingDialog.isShowing()) {
                    loadingDialog.dismiss();
                }

                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    List<Respo_Model_In_Tanker_security> data = response.body();
                    clubList = data;
                    setUpRecyclerView();
                } else {
                    Toasty.warning(getApplicationContext(), "No data available", Toast.LENGTH_SHORT).show();
                    // Clear the RecyclerView if no data
                    clubList.clear();
                    setUpRecyclerView();
                }
            }

            @Override
            public void onFailure(Call<List<Respo_Model_In_Tanker_security>> call, Throwable t) {
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
                Toasty.error(ir_statusgrid_livedata.this, "failed..!", Toast.LENGTH_SHORT).show();
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
                            ProgressDialog loadingDialog = new ProgressDialog(ir_statusgrid_livedata.this);
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
                            Toasty.warning(ir_statusgrid_livedata.this, "Invalid date selection", Toast.LENGTH_SHORT).show();
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