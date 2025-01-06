package com.android.gandharvms.Outward_Tanker_Security;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.gandharvms.Global_Var;
import com.android.gandharvms.Inward_Tanker_Security.grid;
import com.android.gandharvms.Inward_Tanker_Security.gridAdapter;
import com.android.gandharvms.Outward_Truck_Dispatch.Outward_DesIndustriaLoading_Form;
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

public class Grid_Outward extends AppCompatActivity {

    RecyclerView rvClub;
    int scrollX = 0;
    HorizontalScrollView headerscroll;
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private final String EmployeId = Global_Var.getInstance().EmpId;
    List<Response_Outward_Security_Fetching> outwardclublist = new ArrayList<>();
    Outward_GridAdapter outwardGridAdapter;

    Button fromDate, toDate;
    String fromdate;
    String todate;
    String strvehiclenumber = "x";

    public ImageView imgsync;
    TextView currdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_outward);

        fromDate = findViewById(R.id.gridoutwrdbtnfromDate);
        toDate = findViewById(R.id.gridoutwrdbtntoDate);
        imgsync = findViewById(R.id.outwardsyncbt);
        currdate=findViewById(R.id.outwardgridcurrrentdate);

        currdate.setText(getCurrentDateTime().toString().trim());

        initViews();
        /*if(Global_Var.getInstance().DeptType!=0 && Integer.valueOf(Global_Var.getInstance().DeptType) !=120)
        {
            fetchDataFromApi("x",vehicleType,Global_Var.getInstance().DeptType,inOut);
        }
        else{
            fetchDataFromApi("x",vehicleType,'x','x');
        }*/
        fetchDataFromApiWithProgressDialog();
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

        imgsync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Show confirmation dialog before syncing
                new AlertDialog.Builder(Grid_Outward.this)
                        .setTitle("Sync Data")
                        .setMessage("Are you sure you want to Refresh Data?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                fromDate.setText("From-Date");
                                toDate.setText("To-Date");
                                fetchDataFromApiWithProgressDialog();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
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
        rvClub = findViewById(R.id.outwardrecyclerviewgrid);
        headerscroll = findViewById(R.id.outwardgridheaderscroll);
    }
    private void setUpRecyclerView()
    {
        outwardGridAdapter = new Outward_GridAdapter(outwardclublist);
        FixedGridLayoutManager manager = new FixedGridLayoutManager();
        manager.setTotalColumnCount(1);
        rvClub.setLayoutManager(manager);
        rvClub.setAdapter(outwardGridAdapter);
        rvClub.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    private void fetchDataFromApiWithProgressDialog() {
        ProgressDialog loadingDialog = new ProgressDialog(Grid_Outward.this);
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
        Call<List<Response_Outward_Security_Fetching>> call = Outward_RetroApiclient
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
                Toasty.error(Grid_Outward.this, "Failed to Fetch Data.Server Issues..!", Toast.LENGTH_SHORT).show();
            }
        });
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
                            if (Global_Var.getInstance().DeptType != 0 && Integer.valueOf(Global_Var.getInstance().DeptType) != 120) {
                                if (getIntent().hasExtra("vehiclenumber")) {
                                    strvehiclenumber = getIntent().getExtras().get("vehiclenumber").toString();
                                } else {
                                    strvehiclenumber = "x";
                                }
                                ProgressDialog loadingDialog = new ProgressDialog(Grid_Outward.this);
                                loadingDialog.setMessage("Syncing data, please wait...");
                                loadingDialog.setCancelable(false); // Prevent user from dismissing the dialog
                                loadingDialog.show();
                                fetchDataFromApi(fromdate, todate, strvehiclenumber, vehicleType, Global_Var.getInstance().DeptType, inOut, loadingDialog);
                            } else {
                            }
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
                            Toasty.warning(Grid_Outward.this, "Invalid date selection", Toast.LENGTH_SHORT).show();
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

    private String getCurrentDateTime() {
        // Get current date and time
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();

        // Format the date and time as a string
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM/dd/yyyy", Locale.getDefault());
        return dateFormat.format(now);
    }
       /* private void fetchDataFromApi(String vehicleno,String vehicleType,char nextProcess, char inOut) {
        Call<List<Response_Outward_Security_Fetching>> call = Outward_RetroApiclient.insertoutwardtankersecurity().outwardsecurityfetching(vehicleno, vehicleType, nextProcess, inOut);
        call.enqueue(new Callback<List<Response_Outward_Security_Fetching>>() {
            @Override
            public void onResponse(Call<List<Response_Outward_Security_Fetching>> call, Response<List<Response_Outward_Security_Fetching>> response) {
                if (response.isSuccessful()){
                    if (response.body().size() > 0){
                        List<Response_Outward_Security_Fetching> data = response.body();
                        outwardclublist= data;
                        setUpRecyclerView();
                    }
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
            }
        });
    }*/
        /*private void fetchDataFromApi(String vehicleno, String vehicleType, char nextProcess, char inOut, ProgressDialog loadingDialog) {
        Call<List<Response_Outward_Security_Fetching>> call = Outward_RetroApiclient
                .insertoutwardtankersecurity()
                .outwardsecurityfetching(vehicleno, vehicleType, nextProcess, inOut);

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
                    Toast.makeText(getApplicationContext(), "No data available", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getApplicationContext(), "Failed to fetch data. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }*/
}