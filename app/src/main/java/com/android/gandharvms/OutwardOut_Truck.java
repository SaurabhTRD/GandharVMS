package com.android.gandharvms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.gandharvms.OutwardOut_Truck_Billing.OutwardOut_Truck_Billing;

import es.dmoral.toasty.Toasty;

public class OutwardOut_Truck extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outward_out_truck);
    }
    public void sequirtyinwardtruck(View view){
        if(Global_Var.getInstance().Department.contains("Security")){
            Global_Var.getInstance().DeptType='S';
            startActivity(new Intent(this, OutwardOut_Truck_Security.class));
        } else {
            Toasty.warning(OutwardOut_Truck.this, "You are not in Security Department", Toast.LENGTH_SHORT).show();
        }

    }
    public void Weighmentinwardtruck(View view){
        if(Global_Var.getInstance().Department.contains("Weighment")){
            Global_Var.getInstance().DeptType='W';
            startActivity(new Intent(this, OutwardOut_Truck_Weighment.class));
        } else {
            Toasty.warning(OutwardOut_Truck.this, "You are not in Weighment Department", Toast.LENGTH_SHORT).show();
        }
    }
    public void Outwardouttruckbilling(View view){
        Global_Var.getInstance().DeptType='B';
        Intent intent = new Intent(this, OutwardOut_Truck_Billing.class);
        startActivity(intent);
    }
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
        finish();
    }
}