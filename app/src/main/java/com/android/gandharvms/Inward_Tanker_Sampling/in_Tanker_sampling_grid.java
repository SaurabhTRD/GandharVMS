package com.android.gandharvms.Inward_Tanker_Sampling;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.gandharvms.Inward_Tanker_Security.gridAdapter;
import com.android.gandharvms.Inward_Tanker_Weighment.Intankweighlistdata_adapter;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class in_Tanker_sampling_grid extends AppCompatActivity {
    private Inward_Tanker_SamplingMethod inward_Tanker_SamplingMethod;
    RecyclerView recyclerView;
   gridAdapter_in_tanker_sampling gridAdapterInTankerSampling;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_tanker_sampling_grid);

        recyclerView =findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        fetchDataFromFirestore();
        fetchDataFromAPI();
    }

    public void fetchDataFromAPI(){
        inward_Tanker_SamplingMethod= RetroApiClient.getInward_Tanker_Sampling();
        Call<List<gridmodel_in_tanker_sampling>> call = inward_Tanker_SamplingMethod.GetInwardSamplinggrid();
        call.enqueue(new Callback<List<gridmodel_in_tanker_sampling>>() {
            @Override
            public void onResponse(Call<List<gridmodel_in_tanker_sampling>> call, Response<List<gridmodel_in_tanker_sampling>> response) {
                if(response.isSuccessful()){
                    List<gridmodel_in_tanker_sampling>samplegrid = new ArrayList<>();
////                    List<gridmodel_in_tanker_sampling> samplegrid=response.body();
//                    datalist.clear();
//                    datalist = samplegrid;
//                    inTankerSaAdapter = new Intankweighlistdata_adapter(datalist);
//                    recview.setAdapter(inTankerSaAdapter);
//                    inTankerSaAdapter.notifyDataSetChanged();
                }
                else
                {
                    Log.e("Retrofit", "Error Response Body: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<List<gridmodel_in_tanker_sampling>> call, Throwable t) {
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


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = db.collection("Inward Tanker Sampling");

        collectionReference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                List<gridmodel_in_tanker_sampling>samplegrid = new ArrayList<>();
                for (QueryDocumentSnapshot document :task.getResult()){
                    gridmodel_in_tanker_sampling obj = document.toObject(gridmodel_in_tanker_sampling.class);
                    samplegrid.add(obj);
                }
                gridAdapterInTankerSampling = new gridAdapter_in_tanker_sampling(samplegrid);
                recyclerView.setAdapter(gridAdapterInTankerSampling);
            }else {
                Log.e("Firestore","Error getting documnet: ",task.getException());
            }
        });


    }

    public void fetchDataFromFirestore(){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = db.collection("Inward Tanker Sampling");

        collectionReference.get().addOnCompleteListener(task -> {
           if (task.isSuccessful()){
               List<gridmodel_in_tanker_sampling>samplegrid = new ArrayList<>();
               for (QueryDocumentSnapshot document :task.getResult()){
                   gridmodel_in_tanker_sampling obj = document.toObject(gridmodel_in_tanker_sampling.class);
                   samplegrid.add(obj);
               }
               gridAdapterInTankerSampling = new gridAdapter_in_tanker_sampling(samplegrid);
               recyclerView.setAdapter(gridAdapterInTankerSampling);
           }else {
               Log.e("Firestore","Error getting documnet: ",task.getException());
           }
        });
    }
}