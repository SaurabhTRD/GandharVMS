package com.android.gandharvms.submenu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.gandharvms.Inward_Tanker_Out;
import com.android.gandharvms.Inward_Truck;
import com.android.gandharvms.R;

public class submenu_Inward_Truck extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submenu_inward_truck);
    }

    public void inwardtankerinclick(View view){
        Intent intent = new Intent(this, Inward_Truck.class);
        startActivity(intent);
    }
    public void inwardtankeroutclick(View view){
        Intent intent = new Intent(this, Inward_Tanker_Out.class);
        startActivity(intent);
    }
}