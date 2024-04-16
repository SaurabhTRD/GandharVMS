package com.android.gandharvms.Outward_Truck_Dispatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.gandharvms.Outward_Truck_Logistic.Outward_Truck_Logistics;
import com.android.gandharvms.R;

public class Outward_Dispatch_IndusSmallPack extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outward_dispatch_indus_small_pack);
    }

    public void ordespIndusOnClick(View view){
        startActivity(new Intent(this, Outward_DesIndustriaLoading_Form.class));
        /*if(Global_Var.getInstance().Department.contains("Despatch")){
            Global_Var.getInstance().DeptType='D';
            startActivity(new Intent(this, Outward_Dispatch_IndusSmallPack.class));
        } else {
            Toasty.warning(Outward_Truck.this, "You are not in Despatch Department", Toast.LENGTH_SHORT).show();
        }*/
    }

    public void ordespsmallPackOnClick(View view){
        startActivity(new Intent(this, Outward_DesSmallPackLoading_Form.class));
        /*if(Global_Var.getInstance().Department.contains("Logistic")){
            Global_Var.getInstance().DeptType='G';
        } else {
            Toasty.warning(Outward_Truck.this, "You are not in Logistic Department", Toast.LENGTH_SHORT).show();
        }*/
    }
}