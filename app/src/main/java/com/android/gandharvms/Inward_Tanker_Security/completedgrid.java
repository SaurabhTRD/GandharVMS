package com.android.gandharvms.Inward_Tanker_Security;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gandharvms.Global_Var;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.R;
import com.android.gandharvms.Util.FixedGridLayoutManager;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class completedgrid extends AppCompatActivity {
    private LinearLayout linearLayout;
    private FirebaseFirestore firestore;

    ArrayList<In_Tanker_Security_list> inTankerSecurityLists;
    In_Tanker_Se_Adapter in_tanker_se_adapter;
    int scrollX = 0;
    List<ListingResponse_InTankerSequrity> clubList = new ArrayList<>();
    RecyclerView rvClub;
    HorizontalScrollView headerscroll;
    SearchView searchView;

    /*gridAdapter GridAdapter;*/
    gridadaptercompleted gridadaptercomp;
    RecyclerView recyclerView;

    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private final String EmployeId = Global_Var.getInstance().EmpId;

    List<Respo_Model_In_Tanker_security> gridlistsecurity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);
        String fromdate = "2024-02-01";
        String todate = getCurrentDateTime();
        initViews();
        if(Global_Var.getInstance().DeptType!=0 && Integer.valueOf(Global_Var.getInstance().DeptType) !=120)
        {
            fetchDataFromApi(fromdate,todate,vehicleType,inOut);
        }
        else{
            fetchDataFromApi(fromdate,todate,vehicleType,'x');
        }

        //fetchDataFromApi("x",vehicleType,'x','x');
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

    private String getCurrentDateTime() {
        // Get current date and time
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();

        // Format the date and time as a string
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(now);
    }
    private void initViews()
    {
        rvClub = findViewById(R.id.recyclerviewgrid);
        headerscroll = findViewById(R.id.headerscroll);
    }

    private void setUpRecyclerView()
    {
        gridadaptercomp  = new gridadaptercompleted(clubList);
        FixedGridLayoutManager manager = new FixedGridLayoutManager();
        manager.setTotalColumnCount(1);
        rvClub.setLayoutManager(manager);
        rvClub.setAdapter(gridadaptercomp);
        rvClub.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }
    public void fetchDataFromApi(String FromDate,String Todate,String vehicleType, char inOut) {

        Call<List<ListingResponse_InTankerSequrity>> call = RetroApiClient.getserccrityveh().getintankersecurityListData(FromDate,Todate, vehicleType, inOut);
        call.enqueue(new Callback<List<ListingResponse_InTankerSequrity>>() {
            @Override
            public void onResponse(Call<List<ListingResponse_InTankerSequrity>> call, Response<List<ListingResponse_InTankerSequrity>> response) {
                if (response.isSuccessful()) {
                    if (response.body().size() > 0) {
                        List<ListingResponse_InTankerSequrity> data = response.body();
                        clubList = data;
                        setUpRecyclerView();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ListingResponse_InTankerSequrity>> call, Throwable t) {

            }
        });
    }
}
