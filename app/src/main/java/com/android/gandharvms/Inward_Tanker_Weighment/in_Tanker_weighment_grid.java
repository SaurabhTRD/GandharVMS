package com.android.gandharvms.Inward_Tanker_Weighment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.android.gandharvms.Inward_Tanker_Security.gridmodel;
import com.android.gandharvms.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class in_Tanker_weighment_grid extends AppCompatActivity {


    RecyclerView recyclerView;
    in_tanker_weigment_gridAdapter inTankerWeigmentGridAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_tanker_weighment_grid);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fetchDataFromFirestore();
    }
    public void fetchDataFromFirestore(){


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = db.collection("Inward Tanker Weighment");

        collectionReference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                List<gridmodel> Gridmodel = new ArrayList<>();
                for (QueryDocumentSnapshot documentSnapshot :task.getResult()){
                    gridmodel obj = documentSnapshot.toObject(gridmodel.class);
                    Gridmodel.add(obj);
                }
                inTankerWeigmentGridAdapter = new in_tanker_weigment_gridAdapter(Gridmodel);
                recyclerView.setAdapter(inTankerWeigmentGridAdapter);

            }else {
                Log.e("Firestore","Error getting documnet: ",task.getException());
            }
        });
    }
}