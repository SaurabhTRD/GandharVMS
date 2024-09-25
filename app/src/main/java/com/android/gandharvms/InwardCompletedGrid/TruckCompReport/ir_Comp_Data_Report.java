package com.android.gandharvms.InwardCompletedGrid.TruckCompReport;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
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
    Button fromDate, toDate;
    TextView totrec;
    String fromdate;
    String todate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ir_comp_data_report);

        totrec = findViewById(R.id.totrecdepartmentwise);
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
    }

    private void getDatabydateselection() {
        fetchDataFromApiForCompReport(vehicleType);
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

    public void fetchDataFromApiForCompReport(String vehicleType) {

        Call<List<CommonResponseModelForAllDepartment>> call = RetroApiClient.getserccrityveh().getitreportdata(vehicleType);

        call.enqueue(new Callback<List<CommonResponseModelForAllDepartment>>() {
            @Override
            public void onResponse(Call<List<CommonResponseModelForAllDepartment>> call, Response<List<CommonResponseModelForAllDepartment>> response) {
                if (response.isSuccessful()) {
                    if (response.body().size() > 0) {
                        List<CommonResponseModelForAllDepartment> data = response.body();
                        int totalcount = data.size();
                        totrec.setText("Tot-Rec: " + totalcount);
                        clubList = data;
                        setUpRecyclerView();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<CommonResponseModelForAllDepartment>> call, Throwable t) {
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
                Toasty.error(ir_Comp_Data_Report.this, "failed..!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}