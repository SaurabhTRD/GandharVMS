package com.android.gandharvms.Inward_Tanker_Sampling;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.gandharvms.Global_Var;
import com.android.gandharvms.InwardCompletedGrid.CommonResponseModelForAllDepartment;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.R;
import com.google.firebase.firestore.FirebaseFirestore;

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

public class Inward_Tanker_saampling_View_data extends AppCompatActivity {

    private Inward_Tanker_SamplingMethod inward_Tanker_SamplingMethod;
    RecyclerView recview;
    List<CommonResponseModelForAllDepartment> samdatalist;
    FirebaseFirestore db;
    TextView txtTotalCount;

    In_Tanker_sa_Adapter inTankerSaAdapter;
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inward_tanker_saampling_view_data);

        txtTotalCount=findViewById(R.id.tv_TotalCount);

        recview= (RecyclerView) findViewById(R.id.recyclerview);
        recview.setLayoutManager(new LinearLayoutManager(this));
        samdatalist = new ArrayList<>();
        /*inTankerSaAdapter= new In_Tanker_sa_Adapter(datalist);
        recview.setAdapter(inTankerSaAdapter);*/

        String FromDate = getCurrentDateTime();
        String Todate = getCurrentDateTime();
        GetInward_Tanker_saamplingListData(FromDate,Todate,vehicleType,null,inOut);

        /*db = FirebaseFirestore.getInstance();
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
                });*/
    }

    private String getCurrentDateTime() {
        // Get current date and time
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();

        // Format the date and time as a string
        java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return dateFormat.format(now);
    }
    private void GetInward_Tanker_saamplingListData(String FromDate,String Todate,String vehicletype,String vehicleno,char inout) {
        inward_Tanker_SamplingMethod= RetroApiClient.getInward_Tanker_Sampling();
        Call<List<CommonResponseModelForAllDepartment>> call = inward_Tanker_SamplingMethod.getIntankSamplingListingData(FromDate,Todate,vehicletype,vehicleno,inout);
        call.enqueue(new Callback<List<CommonResponseModelForAllDepartment>>() {
            @Override
            public void onResponse(Call<List<CommonResponseModelForAllDepartment>> call, Response<List<CommonResponseModelForAllDepartment>> response) {
                if(response.isSuccessful()){
                    List<CommonResponseModelForAllDepartment> data=response.body();
                    int totalCount = data.size();
                    txtTotalCount.setText("Total count: " + totalCount);
                    samdatalist.clear();
                    samdatalist=data;
                    inTankerSaAdapter= new In_Tanker_sa_Adapter(samdatalist);
                    recview.setAdapter(inTankerSaAdapter);
                    inTankerSaAdapter.notifyDataSetChanged();
                }
                else
                {
                    Log.e("Retrofit", "Error Response Body: " + response.code());
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
                Toasty.error(Inward_Tanker_saampling_View_data.this,"failed..!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}