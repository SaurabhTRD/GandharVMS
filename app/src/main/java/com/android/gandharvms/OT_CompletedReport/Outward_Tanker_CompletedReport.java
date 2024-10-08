package com.android.gandharvms.OT_CompletedReport;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.HorizontalScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.gandharvms.Global_Var;
import com.android.gandharvms.OutwardOut_Tanker_Security.Adapter_OT_complete_OutSecurity;
import com.android.gandharvms.OutwardOut_Tanker_Security.OT_Complete_Out_security;
import com.android.gandharvms.Outward_Tanker_Security.Outward_RetroApiclient;
import com.android.gandharvms.Outward_Tanker_Security.Outward_Tanker;
import com.android.gandharvms.Outward_Truck_Security.Common_Outward_model;
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

public class Outward_Tanker_CompletedReport extends AppCompatActivity {

    int scrollX = 0;
    List<Common_Outward_model> clubList = new ArrayList<>();
    RecyclerView rvClub;
    HorizontalScrollView headerscroll;
    Outward_Tanker_CompReportAdapter adapterOtCompleteReport;
    private Outward_Tanker outwardTanker;

    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private final String EmployeId = Global_Var.getInstance().EmpId;
    Button fromDate,toDate;
    TextView totrec;
    String fromdate;
    String todate;
    String strvehiclenumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outward_tanker_completed_report);
        totrec=findViewById(R.id.totrecdepartmentwise);
        outwardTanker = Outward_RetroApiclient.insertoutwardtankersecurity();

        initViews();
        fetchDataFromApiforweigh(vehicleType);

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
    public void fetchDataFromApiforweigh(String vehicleType) {

        Call<List<Common_Outward_model>> call = outwardTanker.getVehicleCompReportData(vehicleType);
        call.enqueue(new Callback<List<Common_Outward_model>>() {
            @Override
            public void onResponse(Call<List<Common_Outward_model>> call, Response<List<Common_Outward_model>> response) {
                if (response.isSuccessful()){
                    if (response.body().size()>0){
                        List<Common_Outward_model> data = response.body();
                        int totalcount = data.size();
                        totrec.setText("Tot-Rec: "+ totalcount);
                        clubList = data;
                        setUpRecyclerView();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Common_Outward_model>> call, Throwable t) {
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
}