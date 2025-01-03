package com.android.gandharvms.submenu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.gandharvms.Global_Var;
import com.android.gandharvms.Inward_Tanker_Security.grid;
import com.android.gandharvms.LoginWithAPI.Login;
import com.android.gandharvms.Menu;
import com.android.gandharvms.NotificationAlerts.NotificationCommonfunctioncls;
import com.android.gandharvms.OR_VehicleStatus_Grid.or_statusgrid_livedata;
import com.android.gandharvms.OutwardOut_Truck;
import com.android.gandharvms.Outward_Tanker_Security.Grid_Outward;
import com.android.gandharvms.Outward_Truck;
import com.android.gandharvms.R;

public class Submenu_Outward_Truck extends NotificationCommonfunctioncls {
    ImageView btnlogout,btnhome;
    TextView username,empid;

    public static String Tanker;
    public static String Truck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submenu_outward_truck);
        /*btnhome = findViewById(R.id.btn_homeButton);
        btnlogout=findViewById(R.id.btn_logoutButton);
        username=findViewById(R.id.tv_username);
        empid=findViewById(R.id.tv_employeeId);

        String userName=Global_Var.getInstance().Name;
        String empId=Global_Var.getInstance().EmpId;

        username.setText(userName);
        empid.setText(empId);
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Submenu_Outward_Truck.this, Login.class));
            }
        });
        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Submenu_Outward_Truck.this, Menu.class));
            }
        });*/
        setupHeader();
    }
    public void intruckclick(View view){
        Global_Var.getInstance().InOutType='I';
        Intent intent = new Intent(this, Outward_Truck.class);
        startActivity(intent);
    }
    public void truckclick(View view){
        Global_Var.getInstance().InOutType='O';
        Intent intent = new Intent(this, OutwardOut_Truck.class);
        startActivity(intent);
    }

    public void outwardtankerstatusclick(View view){
        Global_Var.getInstance().InOutType='x';
        Global_Var.getInstance().DeptType='x';
        Intent intent = new Intent(this, or_statusgrid_livedata.class);
        startActivity(intent);
    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
        finish();
    }
}