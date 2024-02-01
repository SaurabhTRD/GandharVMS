package com.android.gandharvms.Inward_Tanker_Production;

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

public class in_tanker_produ_grid extends AppCompatActivity {

    RecyclerView recyclerView;

    in_tanker_prod_Adapter inTankerProdAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_tanker_produ_grid);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fetchDataFromFirestore();

    }
    public void fetchDataFromFirestore(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = db.collection("Inward Tanker Production");

        collectionReference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                List<in_tanker_production_model> obj = new ArrayList<>();
                for (QueryDocumentSnapshot documentSnapshot:task.getResult()){
                    in_tanker_production_model obj1 = documentSnapshot.toObject(in_tanker_production_model.class);
                    obj.add(obj1);
                }
                inTankerProdAdapter = new in_tanker_prod_Adapter(obj);
                recyclerView.setAdapter(inTankerProdAdapter);
            }else {
                Log.e("Firestore","Error getting documnet: ",task.getException());
            }
        });
    }
}