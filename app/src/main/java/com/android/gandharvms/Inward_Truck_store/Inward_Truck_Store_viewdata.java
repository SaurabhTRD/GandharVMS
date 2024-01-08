package com.android.gandharvms.Inward_Truck_store;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.android.gandharvms.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Inward_Truck_Store_viewdata extends AppCompatActivity {

    RecyclerView recview;
    ArrayList<In_Truck_Store_list> datalist;

    FirebaseFirestore db;

    Im_Truck_Store_Adapter imTruckStoreAdapter;
    TextView txtTotalCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inward_truck_store_viewdata);

        txtTotalCount=findViewById(R.id.tv_TotalCount);

        recview = (RecyclerView) findViewById(R.id.recyclerview);
        recview.setLayoutManager(new LinearLayoutManager(this));
        datalist = new ArrayList<>();
        imTruckStoreAdapter = new Im_Truck_Store_Adapter(datalist);
        recview.setAdapter(imTruckStoreAdapter);

        db= FirebaseFirestore.getInstance();
        db.collection("Inward Truck Store").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        int totalCount = list.size();
                        txtTotalCount.setText("Total count: " + totalCount);
                        datalist.clear();
                        for (DocumentSnapshot d:list)
                        {
                            In_Truck_Store_list  obj = d.toObject(In_Truck_Store_list.class);
                            datalist.add(obj);

                        }
                        imTruckStoreAdapter.notifyDataSetChanged();

                    }
                });
    }
}