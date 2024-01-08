package com.android.gandharvms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.gandharvms.Inward_Tanker_Security.Inward_Tanker_Security_Viewdata;

public class InwardOut_Truck_Security extends AppCompatActivity {

    Button view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inward_out_truck_security);


        view = findViewById(R.id.btn_Viewweigmentslip);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(InwardOut_Truck_Security.this, Inward_Tanker_Security_Viewdata.class));
            }
        });
    }
    public void onBackPressed(){
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
        finish();
    }
}