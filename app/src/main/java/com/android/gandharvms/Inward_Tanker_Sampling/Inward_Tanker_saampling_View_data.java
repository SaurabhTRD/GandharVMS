package com.android.gandharvms.Inward_Tanker_Sampling;

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

public class Inward_Tanker_saampling_View_data extends AppCompatActivity {


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
}