package com.android.gandharvms.submenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.gandharvms.Global_Var;
import com.android.gandharvms.IR_VehicleStatus_Grid.ir_departmentscompleted_trackdata;
import com.android.gandharvms.IR_VehicleStatus_Grid.ir_statusgrid_livedata;
import com.android.gandharvms.IT_VehicleStatus_Grid.it_departmentscompleted_trackdata;
import com.android.gandharvms.InwardCompletedGrid.GridCompleted;
import com.android.gandharvms.InwardCompletedGrid.TruckCompReport.ir_Comp_Data_Report;
import com.android.gandharvms.Inward_Tanker_Out;
import com.android.gandharvms.Inward_Tanker_Security.grid;
import com.android.gandharvms.Inward_Truck;
import com.android.gandharvms.Inward_Truck_Out;
import com.android.gandharvms.LoginWithAPI.Login;
import com.android.gandharvms.Menu;
import com.android.gandharvms.NotificationAlerts.NotificationCommonfunctioncls;
import com.android.gandharvms.R;

import es.dmoral.toasty.Toasty;

public class submenu_Inward_Truck extends NotificationCommonfunctioncls {
    ImageView btnlogout,btnhome;
    TextView username,empid;
    public CardView deptrackdata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submenu_inward_truck);
        deptrackdata = findViewById(R.id.cv_InwardirdepartmentTrackdata);
        setupHeader();
        if (Global_Var.getInstance().Department.contains("Production") || Global_Var.getInstance().Name.contains("Admin")) {
            deptrackdata.setVisibility(View.VISIBLE);
        } else {
            deptrackdata.setVisibility(View.GONE);
        }
    }

    public void inward_truck_in(View view){
        Global_Var.getInstance().InOutType='I';
        Intent intent = new Intent(this, Inward_Truck.class);
        startActivity(intent);
    }
    public void inward_out_truck(View view){
        Global_Var.getInstance().InOutType='O';
        Intent intent = new Intent(this, Inward_Truck_Out.class);
        startActivity(intent);
    }
    public void intankerstatusclick(View view){
        Global_Var.getInstance().InOutType='x';
        Global_Var.getInstance().DeptType='x';
        Intent intent = new Intent(this, ir_statusgrid_livedata.class);
        startActivity(intent);
    }

    public void irVehicleReportCompletedclick(View view){
        if(Global_Var.getInstance().Department.contains("Production")){
            Intent intent = new Intent(this, ir_Comp_Data_Report.class);
            startActivity(intent);
        } else {
            Toasty.info(submenu_Inward_Truck.this, "You Don't Have Access to View Vehicle Reports Data", Toast.LENGTH_LONG).show();
        }
    }

    public void irdepartmenttrackdata(View view) {
        Intent intent = new Intent(this, ir_departmentscompleted_trackdata.class);
        startActivity(intent);
    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
        finish();
    }
}