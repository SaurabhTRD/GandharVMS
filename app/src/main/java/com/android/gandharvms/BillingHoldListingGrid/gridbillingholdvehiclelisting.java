package com.android.gandharvms.BillingHoldListingGrid;

import android.os.Bundle;
import android.util.Log;
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

import com.android.gandharvms.Outward_Tanker_Security.Adapter_OT__Complete_sec;
import com.android.gandharvms.Outward_Tanker_Security.BillingHoldStatusModel;
import com.android.gandharvms.Outward_Tanker_Security.OT_Complete_sec;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.Outward_Tanker_Security.Outward_Tanker;
import com.android.gandharvms.Outward_Truck_Security.Common_Outward_model;
import com.android.gandharvms.R;
import com.android.gandharvms.Util.FixedGridLayoutManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class gridbillingholdvehiclelisting extends AppCompatActivity {

    int scrollX = 0;
    List<BillingHoldStatusModel> clubList = new ArrayList<>();
    RecyclerView rvClub;
    HorizontalScrollView headerscroll;
    adapterbillingholdveh adapterbillinghold;
    private Outward_Tanker outwardTanker;
    TextView totrec;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gridbillingholdvehiclelisting);
        outwardTanker = RetroApiClient.insertoutwardtankersecurity();
        totrec=findViewById(R.id.totrecordsbillholdveh);
        initViews();
        fetchlistofbillholdstatus();
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
        rvClub = findViewById(R.id.rcygridbilholdveh);
        headerscroll = findViewById(R.id.hsvgridbilholdveh);
    }

    public void fetchlistofbillholdstatus() {
        Call<List<BillingHoldStatusModel>> call = outwardTanker.getListofBillingHoldStatus();
        call.enqueue(new Callback<List<BillingHoldStatusModel>>() {
            @Override
            public void onResponse(Call<List<BillingHoldStatusModel>> call, Response<List<BillingHoldStatusModel>> response) {
                if (response.isSuccessful()){
                    if (response.body().size()>0){
                        List<BillingHoldStatusModel> data = response.body();
                        int totalcount = data.size();
                        totrec.setText("Tot-Rec: "+ totalcount);
                        clubList = data;
                        setUpRecyclerView();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<BillingHoldStatusModel>> call, Throwable t) {
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
                Toasty.error(gridbillingholdvehiclelisting.this,"failed..!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setUpRecyclerView()
    {
        adapterbillinghold  = new adapterbillingholdveh(clubList,gridbillingholdvehiclelisting.this);
        FixedGridLayoutManager manager = new FixedGridLayoutManager();
        manager.setTotalColumnCount(1);
        rvClub.setLayoutManager(manager);
        rvClub.setAdapter(adapterbillinghold);
        rvClub.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }
}