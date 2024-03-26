package com.android.gandharvms.Outward_Tanker_Security;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import com.android.gandharvms.Global_Var;
import com.android.gandharvms.Inward_Tanker_Security.gridAdapter;
import com.android.gandharvms.R;
import com.android.gandharvms.Util.FixedGridLayoutManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

    TextView currdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_outward);

        currdate=findViewById(R.id.otgridcurrrentdate);

        currdate.setText(getCurrentDateTime().toString().trim());

        initViews();
        if(Global_Var.getInstance().DeptType!=0 && Integer.valueOf(Global_Var.getInstance().DeptType) !=120)
        {
            fetchDataFromApi("x",vehicleType,Global_Var.getInstance().DeptType,inOut);
        }
        else{
            fetchDataFromApi("x",vehicleType,'x','x');
        }
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


    private void fetchDataFromApi(String vehicleno,String vehicleType,char nextProcess, char inOut) {
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
    }

    private String getCurrentDateTime() {
        // Get current date and time
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();

        // Format the date and time as a string
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM/dd/yyyy", Locale.getDefault());
        return dateFormat.format(now);
    }
}