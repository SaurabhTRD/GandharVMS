package com.android.gandharvms.Inward_Tanker_Laboratory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.android.gandharvms.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class in_tanker_lab_grid extends AppCompatActivity {

    RecyclerView recyclerView;
    in_tanker_lab_gridAdapter inTankerLabGridAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_tanker_lab_grid);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fetchDataFromFirestore();

    }
    public void fetchDataFromFirestore(){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = db.collection("Inward Tanker Laboratory");

        collectionReference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                List<in_Tanker_lab_model> obj = new ArrayList<>();
                for (QueryDocumentSnapshot documentSnapshot:task.getResult()){
                    in_Tanker_lab_model obj1 = documentSnapshot.toObject(in_Tanker_lab_model.class);
                    obj.add(obj1);
                }
                inTankerLabGridAdapter = new in_tanker_lab_gridAdapter(obj);
                recyclerView.setAdapter(inTankerLabGridAdapter);
            }else {
                Log.e("Firestore","Error getting documnet: ",task.getException());
            }
        });
    }
}