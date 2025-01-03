package com.android.gandharvms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.TextView;

import com.android.gandharvms.Inward_Truck_Security.Inward_Truck_Security;
import com.android.gandharvms.Inward_Truck_Weighment.Inward_Truck_weighment;
import com.android.gandharvms.Inward_Truck_store.Inward_Truck_Store;
import com.android.gandharvms.LoginWithAPI.Login;
import com.android.gandharvms.NotificationAlerts.NotificationCommonfunctioncls;
import com.android.gandharvms.submenu.submenu_Inward_Truck;

import es.dmoral.toasty.Toasty;

public class Inward_Truck extends NotificationCommonfunctioncls {
    ImageView btnlogout,btnhome;
    TextView username,empid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inward_truck);

        btnlogout=findViewById(R.id.btn_logoutButton);
        btnhome = findViewById(R.id.btn_homeButton);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String receivedEmplid = sharedPreferences.getString("EMPLID_KEY", "");

        username=findViewById(R.id.tv_username);
        empid=findViewById(R.id.tv_employeeId);

        /*String userName=Global_Var.getInstance().Name;
        String empId=Global_Var.getInstance().EmpId;

        username.setText(userName);
        empid.setText(empId);

        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Inward_Truck.this, Login.class));
            }
        });
        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Inward_Truck.this, Menu.class));
            }
        });*/
        setupHeader();
    }

    public void sequirtyinwardtruck(View view) {
        if(Global_Var.getInstance().Department.contains("Security")){
            Global_Var.getInstance().DeptType='S';
            startActivity(new Intent(this, Inward_Truck_Security.class));
        } else {
            Toasty.warning(Inward_Truck.this, "You are not in Security Department", Toast.LENGTH_SHORT).show();
        }
    }

    public void Weighmentinwardtruck(View view) {
        if(Global_Var.getInstance().Department.contains("Weighment")){
            Global_Var.getInstance().DeptType='W';
            startActivity(new Intent(this, Inward_Truck_weighment.class));
        } else {
            Toasty.warning(Inward_Truck.this, "You are not in Weighment Department", Toast.LENGTH_SHORT).show();
        }

    }

    public void storeinwardtruck(View view) {
        if(Global_Var.getInstance().Department.contains("Store")){
            Global_Var.getInstance().DeptType='R';
            startActivity(new Intent(this, Inward_Truck_Store.class));
        } else {
            Toasty.warning(Inward_Truck.this, "You are not in Store Department", Toast.LENGTH_SHORT).show();
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
        finish();
    }
}