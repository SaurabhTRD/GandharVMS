package com.android.gandharvms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class OutwardOut_Tanker extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outward_out_tanker);
    }

    public void outwardouttankersecurity(View view){
        Global_Var.getInstance().DeptType='S';
        Intent intent = new Intent(this, OutwardOut_Tanker_Security.class);
        startActivity(intent);
    }
    public void outwardouttankerwighment(View view){
        Global_Var.getInstance().DeptType='W';
        Intent intent = new Intent(this, OutwardOut_Tanker_Weighment.class);
        startActivity(intent);
    }




}