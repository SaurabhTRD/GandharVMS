package com.android.gandharvms.Inward_Tanker_Sampling;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.gandharvms.Inward_Tanker_Weighment.InTanWeighResponseModel;
import com.android.gandharvms.Inward_Tanker_Weighment.Intankweighlistdata_adapter;
import com.android.gandharvms.Inward_Tanker_Weighment.Inward_Tanker_Weighment_Viewdata;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class Inward_Tanker_saampling_View_data extends AppCompatActivity {

    private Inward_Tanker_SamplingMethod inward_Tanker_SamplingMethod;
    RecyclerView recview;
    ArrayList<In_Tanker_Sampling_list> datalist;
    FirebaseFirestore db;
    TextView txtTotalCount;

    In_Tanker_sa_Adapter inTankerSaAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inward_tanker_saampling_view_data);

        txtTotalCount=findViewById(R.id.tv_TotalCount);

        recview= (RecyclerView) findViewById(R.id.recyclerview);
        recview.setLayoutManager(new LinearLayoutManager(this));
        datalist = new ArrayList<>();
        inTankerSaAdapter= new In_Tanker_sa_Adapter(datalist);
        recview.setAdapter(inTankerSaAdapter);

        db = FirebaseFirestore.getInstance();
        db.collection("Inward Tanker Sampling").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        datalist.clear();
                        int totalCount = list.size();
                        txtTotalCount.setText("Total count: " + totalCount);
                        for (DocumentSnapshot d:list)
                        {
                            In_Tanker_Sampling_list  obj = d.toObject(In_Tanker_Sampling_list.class);
                            datalist.add(obj);
                        }
                        inTankerSaAdapter.notifyDataSetChanged();

                    }
                });
    }

    private void GetInward_Tanker_saamplingListData(char nextProcess) {
        inward_Tanker_SamplingMethod= RetroApiClient.getInward_Tanker_Sampling();
        Call<List<Inward_Tanker_SamplingResponseModel>> call = inward_Tanker_SamplingMethod.GetInwardSampling();
        call.enqueue(new Callback<List<Inward_Tanker_SamplingResponseModel>>() {
            @Override
            public void onResponse(Call<List<Inward_Tanker_SamplingResponseModel>> call, Response<List<Inward_Tanker_SamplingResponseModel>> response) {
                if(response.isSuccessful()){
                    List<Inward_Tanker_SamplingResponseModel> data=response.body();
                    int totalCount = data.size();
                    txtTotalCount.setText("Total count: " + totalCount);
                    datalist.clear();
//                    datalist = data;
//                    inTankerSaAdapter = new gridAdapter_in_tanker_sampling(datalist);
                    recview.setAdapter(inTankerSaAdapter);
                    inTankerSaAdapter.notifyDataSetChanged();
                }
                else
                {
                    Log.e("Retrofit", "Error Response Body: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<List<Inward_Tanker_SamplingResponseModel>> call, Throwable t) {
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
                Toasty.error(Inward_Tanker_saampling_View_data.this,"failed..!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}