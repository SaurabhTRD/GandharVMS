package com.android.gandharvms.submenu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.gandharvms.Global_Var;
import com.android.gandharvms.IT_VehicleStatus_Grid.it_statusgrid_livedata;
import com.android.gandharvms.InwardCompletedGrid.GridCompleted;
import com.android.gandharvms.Inward_Tanker;
import com.android.gandharvms.Inward_Tanker_Out;
import com.android.gandharvms.Inward_Tanker_Production.Inward_Tanker_Production;
import com.android.gandharvms.Inward_Tanker_Security.grid;
import com.android.gandharvms.LoginWithAPI.Login;
import com.android.gandharvms.Menu;
import com.android.gandharvms.R;

import es.dmoral.toasty.Toasty;

public class submenu_Inward_Tanker extends AppCompatActivity {
    ImageView btnlogout,btnhome;
    TextView username,empid;

    public static String Tanker;
    public static String Truck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submenu_inward_tanker);
        btnlogout=findViewById(R.id.btn_logoutButton);
        btnhome = findViewById(R.id.btn_homeButton);
        username=findViewById(R.id.tv_username);
        empid=findViewById(R.id.tv_employeeId);

        String userName=Global_Var.getInstance().Name;
        String empId=Global_Var.getInstance().EmpId;

        username.setText(userName);
        empid.setText(empId);
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(submenu_Inward_Tanker.this, Login.class));
            }
        });
        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(submenu_Inward_Tanker.this, Menu.class));
            }
        });
    }


    public void inwardtankerinclick(View view){
        Global_Var.getInstance().InOutType='I';
        Intent intent = new Intent(this, Inward_Tanker.class);
        Tanker = "I";
        startActivity(intent);
    }
    public void inwardtankeroutclick(View view){
        Global_Var.getInstance().InOutType='O';
        Intent intent = new Intent(this, Inward_Tanker_Out.class);
        Truck = "O";
        startActivity(intent);
    }
    public void intankerstatusclick(View view){
        Global_Var.getInstance().InOutType='x';
        Global_Var.getInstance().DeptType='x';
            Intent intent = new Intent(this, it_statusgrid_livedata.class);
            startActivity(intent);

    }

    public void itVehicleReportCompletedclick(View view){
        if(Global_Var.getInstance().Department.contains("Production")){
            Intent intent = new Intent(this, GridCompleted.class);
            startActivity(intent);
        } else {
            Toasty.info(submenu_Inward_Tanker.this, "You Don't Have Access to View Vehicle Reports Data", Toast.LENGTH_LONG).show();
        }
    }
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
        finish();
    }
}