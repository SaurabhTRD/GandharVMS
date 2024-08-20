package com.android.gandharvms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.gandharvms.LoginWithAPI.Login;
import com.android.gandharvms.OutwardOut_Truck_Billing.OutwardOut_Truck_Billing;
import com.android.gandharvms.Outward_Truck_Production.Outward_Truck_Production;
import com.android.gandharvms.submenu.Submenu_Outward_Truck;

import es.dmoral.toasty.Toasty;

public class OutwardOut_Truck extends AppCompatActivity {

    ImageView btnlogout,btnhome;
    TextView username,empid;

    public static String Tanker;
    public static String Truck;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outward_out_truck);
        btnhome = findViewById(R.id.btn_homeButton);
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
                startActivity(new Intent(OutwardOut_Truck.this, Login.class));
            }
        });
        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OutwardOut_Truck.this, Menu.class));
            }
        });
    }
    public void sequirtyoutwardouttruck(View view){
        if(Global_Var.getInstance().Department.contains("Security")){
            Global_Var.getInstance().DeptType='S';
            startActivity(new Intent(this, OutwardOut_Truck_Security.class));
        } else {
            Toasty.warning(OutwardOut_Truck.this, "You are not in Security Department", Toast.LENGTH_SHORT).show();
        }

    }
    public void Weighmentoutwardouttruck(View view){
        if(Global_Var.getInstance().Department.contains("Weighment")){
            Global_Var.getInstance().DeptType='W';
            startActivity(new Intent(this, OutwardOut_Truck_Weighment.class));
        } else {
            Toasty.warning(OutwardOut_Truck.this, "You are not in Weighment Department", Toast.LENGTH_SHORT).show();
        }
    }

    public void productionouttruck(View view){
        if(Global_Var.getInstance().Department.contains("DataEntry")){
            Global_Var.getInstance().DeptType='P';
            startActivity(new Intent(this, Outward_Truck_Production.class));
        } else {
            Toasty.warning(OutwardOut_Truck.this, "You are not in DataEntry Department", Toast.LENGTH_SHORT).show();
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