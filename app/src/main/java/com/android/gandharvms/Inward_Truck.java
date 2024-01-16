package com.android.gandharvms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.gandharvms.Inward_Tanker_Security.Inward_Tanker_Security;
import com.android.gandharvms.Inward_Truck_Security.Inward_Truck_Security;
import com.android.gandharvms.Inward_Truck_Weighment.Inward_Truck_weighment;
import com.android.gandharvms.Inward_Truck_store.Inward_Truck_Store;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Inward_Truck extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://gandharvms-default-rtdb.firebaseio.com/");
    private String userRole="default";
    Button btnlogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inward_truck);

        btnlogout=findViewById(R.id.btn_logoutButton);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String receivedEmplid = sharedPreferences.getString("EMPLID_KEY", "");


        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Inward_Truck.this, Login.class));
            }
        });
        databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(receivedEmplid).child("role").exists()) {
                    userRole = snapshot.child(receivedEmplid).child("role").getValue(String.class);
                } else {
                    Toast.makeText(Inward_Truck.this, "EmplId with this Role is Not Available", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void sequirtyinwardtruck(View view) {
        if(userRole.equals("Admin") || userRole.equals("Security")){
            startActivity(new Intent(this, Inward_Truck_Security.class));
        } else {
            Toast.makeText(Inward_Truck.this, "You are not in Security Department", Toast.LENGTH_SHORT).show();
        }
    }

    public void Weighmentinwardtruck(View view) {
        if(userRole.equals("Admin") || userRole.equals("Weighment")){
            startActivity(new Intent(this, Inward_Truck_weighment.class));
        } else {
            Toast.makeText(Inward_Truck.this, "You are not in Weighment Department", Toast.LENGTH_SHORT).show();
        }
    }

    public void storeinwardtruck(View view) {
        if(userRole.equals("Admin") || userRole.equals("Store")){
            startActivity(new Intent(this, Inward_Truck_Store.class));
        } else {
            Toast.makeText(Inward_Truck.this, "You are not in Store Department", Toast.LENGTH_SHORT).show();
        }
    }
}