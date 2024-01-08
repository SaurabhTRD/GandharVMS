package com.android.gandharvms.submenu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.gandharvms.OutwardOut_Truck;
import com.android.gandharvms.Outward_Truck;
import com.android.gandharvms.R;

public class Submenu_Outward_Truck extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submenu_outward_truck);



    }
    public void intruckclick(View view){
        Intent intent = new Intent(this, Outward_Truck.class);
        startActivity(intent);
    }
    public void truckclick(View view){
        Intent intent = new Intent(this, OutwardOut_Truck.class);
        startActivity(intent);
    }
}