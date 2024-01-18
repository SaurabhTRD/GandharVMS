package com.android.gandharvms.Inward_Truck_Weighment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.android.gandharvms.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Inward_Truck_Weighment_Viewdata extends AppCompatActivity {

    RecyclerView recview;

    ArrayList<In_Truck_weigment_list> datalist;

    FirebaseFirestore db;

  In_Truck_weighment_Adapter inTruckWeighmentAdapter;
    TextView txtTotalCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inward_truck_weighment_viewdata);

        txtTotalCount=findViewById(R.id.tv_TotalCount);

        recview = (RecyclerView) findViewById(R.id.recyclerview);
        recview.setLayoutManager(new LinearLayoutManager(this));
        datalist = new ArrayList<>();
        inTruckWeighmentAdapter = new In_Truck_weighment_Adapter(datalist);
        recview.setAdapter(inTruckWeighmentAdapter);


        db=FirebaseFirestore.getInstance();
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
                });
    }
}