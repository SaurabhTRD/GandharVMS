package com.android.gandharvms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Inward_Tanker_Out extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inward_tanker_out);
    }

    public void inwardtankerinweighmentclick(View view){
        Global_Var.getInstance().DeptType='W';
        Intent intent = new Intent(this,InwardOut_Tanker_Weighment.class);
        startActivity(intent);
    }
    public void inwadtankeroutsecurity(View view){
        Global_Var.getInstance().DeptType='S';
        Intent intent = new Intent(this, InwardOut_Tanker_Security.class);
        startActivity(intent);
    }


}