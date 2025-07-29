package com.android.gandharvms.submenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.gandharvms.Global_Var;
import com.android.gandharvms.IT_VehicleStatus_Grid.it_departmentscompleted_trackdata;
import com.android.gandharvms.LoginWithAPI.Login;
import com.android.gandharvms.Menu;
import com.android.gandharvms.NotificationAlerts.NotificationCommonfunctioncls;
import com.android.gandharvms.OT_VehicleStatus_Grid.ot_departmentscompletedexport_trackdata;
import com.android.gandharvms.OT_VehicleStatus_Grid.ot_statusgrid_livedata;
import com.android.gandharvms.OT_CompletedReport.Outward_Tanker_CompletedReport;
import com.android.gandharvms.OutwardOut_Tanker;
import com.android.gandharvms.Outward_Tanker;
import com.android.gandharvms.R;

import es.dmoral.toasty.Toasty;

public class Submenu_outward_tanker extends NotificationCommonfunctioncls {

    ImageView btnlogout,btnhome;
    TextView username,empid;
    public CardView deptrackdata;
    public static String Tanker;
    public static String Truck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submenu_outward_tanker);
        deptrackdata = findViewById(R.id.cv_InwardotdepartmentTrackdata);
        setupHeader();
        if (Global_Var.getInstance().Department.contains("Production") || Global_Var.getInstance().Name.contains("Admin")) {
            deptrackdata.setVisibility(View.VISIBLE);
        } else {
            deptrackdata.setVisibility(View.GONE);
        }
    }
    public void tankerinclick(View view){
        Global_Var.getInstance().InOutType='I';
        Intent intent = new Intent(this, Outward_Tanker.class);
        startActivity(intent);
    }

    public void tankeroutclick(View view){
        Global_Var.getInstance().InOutType='O';
        Intent intent = new Intent(this, OutwardOut_Tanker.class);
        startActivity(intent);
    }
    public void outwardtankerstatusclick(View view){
        Global_Var.getInstance().InOutType='x';
        Global_Var.getInstance().DeptType='x';
        Intent intent = new Intent(this, ot_statusgrid_livedata.class);
        startActivity(intent);

    }

    public void otVehicleReportCompletedclick(View view){
        if(Global_Var.getInstance().Department.contains("Production")){
            Intent intent = new Intent(this, Outward_Tanker_CompletedReport.class);
            startActivity(intent);
        } else {
            Toasty.info(Submenu_outward_tanker.this, "You Don't Have Access to View Vehicle Reports Data", Toast.LENGTH_LONG).show();
        }
    }

    public void otdepartmenttrackdata(View view) {
        Intent intent = new Intent(this, ot_departmentscompletedexport_trackdata.class);
        startActivity(intent);
    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
        finish();
    }
}