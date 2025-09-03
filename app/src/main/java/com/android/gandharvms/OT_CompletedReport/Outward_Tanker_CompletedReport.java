package com.android.gandharvms.OT_CompletedReport;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.gandharvms.Global_Var;
import com.android.gandharvms.IT_VehicleStatus_Grid.it_departmentscompleted_trackdata;
import com.android.gandharvms.NotificationAlerts.NotificationCommonfunctioncls;
import com.android.gandharvms.OutwardOut_Tanker_Security.Adapter_OT_complete_OutSecurity;
import com.android.gandharvms.OutwardOut_Tanker_Security.OT_Complete_Out_security;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.Outward_Tanker_Security.Outward_Tanker;
import com.android.gandharvms.Outward_Truck_Security.Common_Outward_model;
import com.android.gandharvms.R;
import com.android.gandharvms.Util.FixedGridLayoutManager;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

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

public class Outward_Tanker_CompletedReport extends NotificationCommonfunctioncls {

    int scrollX = 0;
    List<Common_Outward_model> clubList = new ArrayList<>();
    RecyclerView rvClub;
    HorizontalScrollView headerscroll;
    Outward_Tanker_CompReportAdapter adapterOtCompleteReport;
    private Outward_Tanker outwardTanker;

    private final String vehicleType = Global_Var.getInstance().MenuType;
    Button btnFromDate,btnToDate;
    TextView totrec;
    String fromdate;
    String todate;
    ImageButton imgBtnExportToExcel;
    private HSSFWorkbook hssfWorkBook;
    String formattedDate;
    String strvehiclenumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outward_tanker_completed_report);
        btnFromDate = findViewById(R.id.OtdepttrackbtnfromDate);
        btnToDate = findViewById(R.id.OtdepttrackcompbtntoDate);
        totrec=findViewById(R.id.Otdepttracktotrecord);
        fromdate = getCurrentDateTime();
        todate = getCurrentDateTime();
        //imgBtnExportToExcel=findViewById(R.id.btnOtdepttrackcompExportToExcel);
        hssfWorkBook = new HSSFWorkbook();
        outwardTanker = RetroApiClient.insertoutwardtankersecurity();
        setupHeader();
        initViews();
        getDatabydateselection();
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

        btnFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the onClick for fromDate button
                showDatePickerDialog(btnFromDate, true);
            }
        });

        btnToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the onClick for fromDate button
                showDatePickerDialog(btnToDate, false);
            }
        });

        /*imgBtnExportToExcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clubList != null && !clubList.isEmpty()) {
                    new AlertDialog.Builder(Outward_Tanker_CompletedReport.this)
                            .setTitle("Export Data")
                            .setMessage("Do you want to export the data to Excel?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // User confirmed, export to Excel
                                    exportToExcel(clubList);
                                }
                            })
                            .setNegativeButton("No", null) // Dismiss dialog on "No"
                            .show();
                } else {
                    Toasty.warning(getApplicationContext(), "No data to export", Toast.LENGTH_SHORT).show();
                }
            }
        });*/
    }

    private void getDatabydateselection() {
        ProgressDialog loadingDialog = new ProgressDialog(Outward_Tanker_CompletedReport.this);
        loadingDialog.setMessage("Syncing data, please wait...");
        loadingDialog.setCancelable(false); // Prevent user from dismissing the dialog
        loadingDialog.show();
        //fetchDataFromApiForCompReport(fromdate,todate,vehicleType,loadingDialog);
        fetchDataFromApiforweigh(fromdate,todate,vehicleType,loadingDialog);
    }

    private void initViews()
    {
        rvClub = findViewById(R.id.recyclerotcompletereport);
        headerscroll = findViewById(R.id.hsotcompletereport);
    }

    private void setUpRecyclerView()
    {
        adapterOtCompleteReport  = new Outward_Tanker_CompReportAdapter(clubList);
        FixedGridLayoutManager manager = new FixedGridLayoutManager();
        manager.setTotalColumnCount(1);
        rvClub.setLayoutManager(manager);
        rvClub.setAdapter(adapterOtCompleteReport);
        rvClub.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    public void fetchDataFromApiforweigh(String fromdate, String todate,String vehicleType,ProgressDialog loadingDialog) {
        Call<List<Common_Outward_model>> call = outwardTanker.getVehicleCompReportData(fromdate,todate,vehicleType);
        call.enqueue(new Callback<List<Common_Outward_model>>() {
            @Override
            public void onResponse(Call<List<Common_Outward_model>> call, Response<List<Common_Outward_model>> response) {
                loadingDialog.dismiss();
                try {
                    if (response.isSuccessful() && response.body() != null) {
                        List<Common_Outward_model> data = response.body();
                        if (response.body().size() > 0 && data != null && !data.isEmpty()) {
                            int totalcount = data.size();
                            totrec.setText("Tot-Rec: " + totalcount);
                            clubList = data;
                            setUpRecyclerView();
                        } else {
                            clubList = new ArrayList<>();
                            totrec.setText("Tot-Rec: 0");
                            Toasty.info(Outward_Tanker_CompletedReport.this, "No records found", Toast.LENGTH_SHORT).show();
                            // You can also clear RecyclerView if needed
                        }
                    } else {
                        Toasty.error(Outward_Tanker_CompletedReport.this, "Server error: " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                    Toasty.error(Outward_Tanker_CompletedReport.this, "Something went wrong while processing response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Common_Outward_model>> call, Throwable t) {
                loadingDialog.dismiss();
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
                Toasty.error(Outward_Tanker_CompletedReport.this,"failed..!", Toast.LENGTH_SHORT).show();
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
                            ProgressDialog loadingDialog = new ProgressDialog(Outward_Tanker_CompletedReport.this);
                            loadingDialog.setMessage("Syncing data, please wait...");
                            loadingDialog.setCancelable(false); // Prevent user from dismissing the dialog
                            loadingDialog.show();
                            fetchDataFromApiforweigh(fromdate, todate,vehicleType,loadingDialog);

                            rvClub.addOnScrollListener(new RecyclerView.OnScrollListener() {
                                @Override
                                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                                    super.onScrolled(recyclerView, dx, dy);
                                }

                                @Override
                                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                                    super.onScrollStateChanged(recyclerView, newState);
                                }
                            });
                        } else {
                            // Show an error message or take appropriate action
                            Toasty.warning(Outward_Tanker_CompletedReport.this, "Invalid date selection", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                year, month, day);
        if (isFromDate && !todate.isEmpty()) {
            try {
                datePickerDialog.getDatePicker().setMaxDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(todate).getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else if (!isFromDate && !fromdate.isEmpty()) {
            try {
                datePickerDialog.getDatePicker().setMinDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(fromdate).getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        // Show the date picker dialog
        datePickerDialog.show();
    }

    private String formatDate(String inputDate) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("MMM dd yyyy hh:mma", Locale.getDefault());
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
            Date date = inputFormat.parse(inputDate);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return inputDate;
        }
    }

    private String getCurrentDateTime() {
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(now);
    }
}