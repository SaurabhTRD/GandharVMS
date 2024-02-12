package com.android.gandharvms.Inward_Truck_Weighment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.gandharvms.Global_Var;
import com.android.gandharvms.Inward_Tanker_Weighment.InTanWeighResponseModel;
import com.android.gandharvms.Inward_Tanker_Weighment.Intankweighlistdata_adapter;
import com.android.gandharvms.Inward_Tanker_Weighment.Inward_Tanker_Weighment_Viewdata;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.LoginWithAPI.Weighment;
import com.android.gandharvms.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class Inward_Truck_Weighment_Viewdata extends AppCompatActivity {

    RecyclerView recview;

    List<InTanWeighResponseModel> weighdatalist;
    private Weighment weighmentdetails;

    FirebaseFirestore db;

    Intankweighlistdata_adapter intankweighlistdataAdapter;
    TextView txtTotalCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inward_truck_weighment_viewdata);

        txtTotalCount=findViewById(R.id.tv_TotalCount);

        weighmentdetails= RetroApiClient.getWeighmentDetails();

        recview = findViewById(R.id.recyclerview);
        recview.setLayoutManager(new LinearLayoutManager(this));

        weighdatalist = new ArrayList<>();
        char nextprocess= Global_Var.getInstance().DeptType;
        GetWeighmentListData(nextprocess);
        /*db=FirebaseFirestore.getInstance();
        db.collection("Inward Truck Weighment").orderBy("Date", Query.Direction.DESCENDING).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        int totalCount = list.size();
                        txtTotalCount.setText("Total count: " + totalCount);
                        datalist.clear();
                        for (DocumentSnapshot d:list)
                        {
                            In_Truck_weigment_list obj = d.toObject(In_Truck_weigment_list.class);
                            datalist.add(obj);
                        }
                        inTruckWeighmentAdapter.notifyDataSetChanged();
                    }
                });*/
    }

    private void GetWeighmentListData(char nextProcess) {
        Call<List<InTanWeighResponseModel>> call = weighmentdetails.getIntankWeighListData(nextProcess);
        call.enqueue(new Callback<List<InTanWeighResponseModel>>() {
            @Override
            public void onResponse(Call<List<InTanWeighResponseModel>> call, Response<List<InTanWeighResponseModel>> response) {
                if(response.isSuccessful()){
                    List<InTanWeighResponseModel> data=response.body();
                    int totalCount = data.size();
                    txtTotalCount.setText("Total count: " + totalCount);
                    weighdatalist.clear();
                    weighdatalist = data;
                    intankweighlistdataAdapter = new Intankweighlistdata_adapter(weighdatalist);
                    recview.setAdapter(intankweighlistdataAdapter);
                    intankweighlistdataAdapter.notifyDataSetChanged();
                }
                else
                {
                    Log.e("Retrofit", "Error Response Body: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<List<InTanWeighResponseModel>> call, Throwable t) {
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
                Toasty.error(Inward_Truck_Weighment_Viewdata.this,"failed..!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}