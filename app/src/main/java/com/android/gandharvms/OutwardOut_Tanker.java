package com.android.gandharvms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.gandharvms.Outward_Tanker_Security.Outward_Tanker_Security;
import com.android.gandharvms.Outward_Tanker_Weighment.Outward_Tanker_weighment;

import es.dmoral.toasty.Toasty;

public class OutwardOut_Tanker extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outward_out_tanker);
    }

    public void outwardouttankersecurity(View view){
        if(Global_Var.getInstance().Department.contains("Security")){
            Global_Var.getInstance().DeptType='S';
            startActivity(new Intent(this, OutwardOut_Tanker_Security.class));
        } else {
            Toasty.warning(OutwardOut_Tanker.this, "You are not in Security Department", Toast.LENGTH_SHORT).show();
        }

    }
    public void outwardouttankerwighment(View view){
        if(Global_Var.getInstance().Department.contains("Weighment")){
            Global_Var.getInstance().DeptType='W';
            startActivity(new Intent(this, Outward_Tanker_weighment.class));
        } else {
            Toasty.warning(OutwardOut_Tanker.this, "You are not in Weighment Department", Toast.LENGTH_SHORT).show();
        }
    }




}