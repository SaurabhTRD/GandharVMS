package com.android.gandharvms.Inward_Tanker_Laboratory;

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

public class Inward_Tanker_Lab_Viewdata extends AppCompatActivity {

    RecyclerView recview;

    ArrayList<In_Tanker_lab_list> datalist;

    FirebaseFirestore db;

    In_Tanker_lab_Adapter inTankerLabAdapter;
    TextView txtTotalCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inward_tanker_lab_viewdata);

        txtTotalCount=findViewById(R.id.tv_TotalCount);

        recview = (RecyclerView) findViewById(R.id.recyclerview);
        recview.setLayoutManager(new LinearLayoutManager(this));
        datalist = new ArrayList<>();
        inTankerLabAdapter = new In_Tanker_lab_Adapter(datalist);
        recview.setAdapter(inTankerLabAdapter);

        db= FirebaseFirestore.getInstance();
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
                });
    }
}