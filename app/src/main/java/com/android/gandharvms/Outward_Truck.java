package com.android.gandharvms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.gandharvms.Outward_Truck_Billing.Outward_Truck_Billing;
import com.android.gandharvms.Outward_Truck_Dispatch.Outward_Dispatch_IndusSmallPack;
import com.android.gandharvms.Outward_Truck_Laboratory.Outward_Truck_Laboratory;
import com.android.gandharvms.Outward_Truck_Logistic.Outward_Truck_Logistics;
import com.android.gandharvms.Outward_Truck_Weighment.Outward_Truck_weighment;
import com.android.gandharvms.Outward_Truck_Production.Outward_Truck_Production;
import com.android.gandharvms.Outward_Truck_Security.Outward_Truck_Security;

import es.dmoral.toasty.Toasty;

public class Outward_Truck extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outward_truck);

    }
    public void sequirityoutwardTruck(View view){
        if(Global_Var.getInstance().Department.contains("Security")){
            Global_Var.getInstance().DeptType='S';
            startActivity(new Intent(this, Outward_Truck_Security.class));
        } else {
            Toasty.warning(Outward_Truck.this, "You are not in Security Department", Toast.LENGTH_SHORT).show();
        }

    }

    public void Weighmentouttankerclick(View view){
        if(Global_Var.getInstance().Department.contains("Weighment")){
            Global_Var.getInstance().DeptType='W';
            startActivity(new Intent(this, Outward_Truck_weighment.class));
        } else {
            Toasty.warning(Outward_Truck.this, "You are not in Weighment Department", Toast.LENGTH_SHORT).show();
        }

    }
    public void labouttanker(View view){
        if(Global_Var.getInstance().Department.contains("Laboratory")){
            Global_Var.getInstance().DeptType='L';
            startActivity(new Intent(this, Outward_Truck_Laboratory.class));
        } else {
            Toasty.warning(Outward_Truck.this, "You are not in Laboratory Department", Toast.LENGTH_SHORT).show();
        }
    }
    public void productionouttanker(View view){
        if(Global_Var.getInstance().Department.contains("DataEntry")){
            Global_Var.getInstance().DeptType='P';
            startActivity(new Intent(this, Outward_Truck_Production.class));
        } else {
            Toasty.warning(Outward_Truck.this, "You are not in DataEntry Department", Toast.LENGTH_SHORT).show();
        }

    }
    public void Billingtanker(View view){
        if(Global_Var.getInstance().Department.contains("Billing")){
            Global_Var.getInstance().DeptType='B';
            startActivity(new Intent(this, Outward_Truck_Billing.class));
        } else {
            Toasty.warning(Outward_Truck.this, "You are not in Billing Department", Toast.LENGTH_SHORT).show();
        }
    }
    public void dispatchtanker(View view){
        if(Global_Var.getInstance().Department.contains("Despatch")){
            Global_Var.getInstance().DeptType='D';
            startActivity(new Intent(this, Outward_Dispatch_IndusSmallPack.class));
        } else {
            Toasty.warning(Outward_Truck.this, "You are not in Despatch Department", Toast.LENGTH_SHORT).show();
        }
    }
    public void logisticclick(View view){
        if(Global_Var.getInstance().Department.contains("Logistic")){
            Global_Var.getInstance().DeptType='G';
            startActivity(new Intent(this, Outward_Truck_Logistics.class));
        } else {
            Toasty.warning(Outward_Truck.this, "You are not in Logistic Department", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onBackPressed() {
        // Call finish to close the activity
        finish();
    }

}