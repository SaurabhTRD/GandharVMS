package com.android.gandharvms.Inward_Tanker_Laboratory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.gandharvms.Global_Var;
import com.android.gandharvms.Inward_Tanker_Weighment.Intankweighlistdata_adapter;
import com.android.gandharvms.Inward_Tanker_Weighment.Inward_Tanker_Weighment_Viewdata;
import com.android.gandharvms.LoginWithAPI.Laboratory;
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

public class Inward_Tanker_Lab_Viewdata extends AppCompatActivity {

    RecyclerView recview;

    List<InTanLabResponseModel> labdatalist;

    FirebaseFirestore db;

    InTanLabListData_Adapter inTankLabAdapter;
    TextView txtTotalCount;

    //Call Interface Method of Laboratory
    private Laboratory labdetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inward_tanker_lab_viewdata);

        labdetails= RetroApiClient.getLabDetails();//Call retrofit api

        txtTotalCount=findViewById(R.id.tv_TotalCount);

        recview = (RecyclerView) findViewById(R.id.recyclerview);
        recview.setLayoutManager(new LinearLayoutManager(this));
        labdatalist = new ArrayList<>();

        /*recview.setAdapter(inTankLabAdapter);*/

        char nextprocess= Global_Var.getInstance().DeptType;
        GetLabListData(nextprocess);

        /*db= FirebaseFirestore.getInstance();
        db.collection("Inward Tanker Laboratory").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        int totalCount = list.size();
                        txtTotalCount.setText("Total count: " + totalCount);
                        datalist.clear(); // Clear the previous data
                        for (DocumentSnapshot d:list)
                        {
                            In_Tanker_lab_list obj = d.toObject(In_Tanker_lab_list.class);
                            String kv100Value = (String) d.get("100°KV");
                            String kv40Value = (String) d.get("40°KV");

                            obj.setKv100value(kv100Value);
                            obj.setKv40Value(kv40Value);
                            // Add the object to your data list
                            datalist.add(obj);
                        }
                        inTankerLabAdapter.notifyDataSetChanged();
                    }
                });*/

    }

    private void GetLabListData(char nextProcess) {
        Call<List<InTanLabResponseModel>> call = labdetails.getIntankLabListData(nextProcess);
        call.enqueue(new Callback<List<InTanLabResponseModel>>() {
            @Override
            public void onResponse(Call<List<InTanLabResponseModel>> call, Response<List<InTanLabResponseModel>> response) {
                if(response.isSuccessful()){
                    List<InTanLabResponseModel> data=response.body();
                    int totalCount = data.size();
                    txtTotalCount.setText("Total count: " + totalCount);
                    labdatalist.clear();
                    labdatalist = data;
                    inTankLabAdapter = new InTanLabListData_Adapter(labdatalist);
                    recview.setAdapter(inTankLabAdapter);
                    inTankLabAdapter.notifyDataSetChanged();
                }
                else
                {
                    Log.e("Retrofit", "Error Response Body: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<List<InTanLabResponseModel>> call, Throwable t) {
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
                Toasty.error(Inward_Tanker_Lab_Viewdata.this,"failed..!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}