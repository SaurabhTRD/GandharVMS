package com.android.gandharvms.InwardCompletedGrid.TruckCompReport;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.gandharvms.Global_Var;
import com.android.gandharvms.InwardCompletedGrid.CommonResponseModelForAllDepartment;
import com.android.gandharvms.InwardCompletedGrid.GridCompleted;
import com.android.gandharvms.Inward_Tanker_Production.API_In_Tanker_production;
import com.android.gandharvms.Inward_Tanker_Sampling.Inward_Tanker_SamplingMethod;
import com.android.gandharvms.Inward_Tanker_Security.In_Tanker_Se_Adapter;
import com.android.gandharvms.Inward_Tanker_Security.In_Tanker_Security_list;
import com.android.gandharvms.Inward_Tanker_Security.RetroApiclient_In_Tanker_Security;
import com.android.gandharvms.LoginWithAPI.Laboratory;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.LoginWithAPI.Store;
import com.android.gandharvms.LoginWithAPI.Weighment;
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

public class ir_Comp_Data_Report extends AppCompatActivity {
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private final String EmployeId = Global_Var.getInstance().EmpId;
    ArrayList<In_Tanker_Security_list> inTankerSecurityLists;
    In_Tanker_Se_Adapter in_tanker_se_adapter;
    int scrollX = 0;
    List<CommonResponseModelForAllDepartment> clubList = new ArrayList<>();
    RecyclerView rvClub;
    HorizontalScrollView headerscroll;
    SearchView searchView;
    /*gridAdapter GridAdapter;*/
    it_comp_data_report_adapter truckgridadapter;
    RecyclerView recyclerView;
    Button btnFromDate,btnToDate;
    TextView totrec;
    String fromdate;
    String todate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ir_comp_data_report);

        btnFromDate = findViewById(R.id.IrtotgridcompletedbtnfromDate);
        btnToDate = findViewById(R.id.IrtotgridcompletedbtntoDate);
        totrec = findViewById(R.id.Irtotgridcompletedrecdepartmentwise);
        fromdate = getCurrentDateTime();
        todate = getCurrentDateTime();

        initViews();
        getDatabydateselection();
        rvClub.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                scrollX += dx;
                headerscroll.scrollTo(scrollX, 0);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
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
    }

    private void getDatabydateselection() {
        ProgressDialog loadingDialog = new ProgressDialog(ir_Comp_Data_Report.this);
        loadingDialog.setMessage("Syncing data, please wait...");
        loadingDialog.setCancelable(false); // Prevent user from dismissing the dialog
        loadingDialog.show();
        fromdate = getCurrentDateTime();
        todate = getCurrentDateTime();
        fetchDataFromApiForCompReport(fromdate,todate,vehicleType,loadingDialog);
    }

    private String getCurrentDateTime() {
        // Get current date and time
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();

        // Format the date and time as a string
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(now);
    }

    private void initViews() {
        rvClub = findViewById(R.id.recyclerviewirreportcogrid);
        headerscroll = findViewById(R.id.coirreportheaderscroll);
    }

    private void setUpRecyclerView() {
        truckgridadapter = new it_comp_data_report_adapter(clubList);
        FixedGridLayoutManager manager = new FixedGridLayoutManager();
        manager.setTotalColumnCount(1);
        rvClub.setLayoutManager(manager);
        rvClub.setAdapter(truckgridadapter);
        rvClub.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    public void fetchDataFromApiForCompReport(String fromdate, String todate, String vehicleType, ProgressDialog loadingDialog) {
        Call<List<CommonResponseModelForAllDepartment>> call = RetroApiClient
                .getserccrityveh()
                .getitreportdata(fromdate, todate, vehicleType);

        call.enqueue(new Callback<List<CommonResponseModelForAllDepartment>>() {
            @Override
            public void onResponse(Call<List<CommonResponseModelForAllDepartment>> call, Response<List<CommonResponseModelForAllDepartment>> response) {
                loadingDialog.dismiss(); // Always dismiss the dialog

                if (response.isSuccessful() && response.body() != null) {
                    List<CommonResponseModelForAllDepartment> data = response.body();

                    if (!data.isEmpty()) {
                        int totalcount = data.size();
                        totrec.setText("Tot-Rec: " + totalcount);
                        clubList = data;
                        setUpRecyclerView();
                    } else {
                        totrec.setText("Tot-Rec: 0");
                        Toasty.info(ir_Comp_Data_Report.this, "No records found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toasty.error(ir_Comp_Data_Report.this, "Server error: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<CommonResponseModelForAllDepartment>> call, Throwable t) {
                loadingDialog.dismiss(); // Always dismiss the dialog
                Log.e("Retrofit", "Failure: " + t.getMessage());

                if (t instanceof HttpException) {
                    Response<?> errorResponse = ((HttpException) t).response();
                    if (errorResponse != null) {
                        Log.e("Retrofit", "HTTP error code: " + errorResponse.code());
                        try {
                            Log.e("Retrofit", "Error Body: " + errorResponse.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                Toasty.error(ir_Comp_Data_Report .this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
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
                            ProgressDialog loadingDialog = new ProgressDialog(ir_Comp_Data_Report.this);
                            loadingDialog.setMessage("Syncing data, please wait...");
                            loadingDialog.setCancelable(false); // Prevent user from dismissing the dialog
                            loadingDialog.show();
                            fetchDataFromApiForCompReport(fromdate, todate,vehicleType,loadingDialog);

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
                            Toasty.warning(ir_Comp_Data_Report.this, "Invalid date selection", Toast.LENGTH_SHORT).show();
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
}