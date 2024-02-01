package com.android.gandharvms.Inward_Tanker_Sampling;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.android.gandharvms.Inward_Tanker_Security.gridAdapter;
import com.android.gandharvms.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class in_Tanker_sampling_grid extends AppCompatActivity {

    RecyclerView recyclerView;
   gridAdapter_in_tanker_sampling gridAdapterInTankerSampling;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_tanker_sampling_grid);

        recyclerView =findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fetchDataFromFirestore();
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