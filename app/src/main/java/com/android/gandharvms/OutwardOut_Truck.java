package com.android.gandharvms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class OutwardOut_Truck extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outward_out_truck);
    }
    public void sequirtyinwardtruck(View view){
        Intent intent = new Intent(this, OutwardOut_Truck_Security.class);
        startActivity(intent);
    }
    public void Weighmentinwardtruck(View view){
        Intent intent = new Intent(this, OutwardOut_Truck_Weighment.class);
        startActivity(intent);
    }
    public void storeinwardtruck(View view){
        Intent intent = new Intent(this, OutwardOut_Truck_Billing.class);
        startActivity(intent);
    }
}