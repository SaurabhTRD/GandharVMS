package com.android.gandharvms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.gandharvms.LoginWithAPI.Login;
import com.android.gandharvms.submenu.submenu_Inward_Tanker;

public class Inward_Tanker_Out extends AppCompatActivity {

    ImageView btnlogout,btnhome;
    TextView username,empid;

    public static String Tanker;
    public static String Truck;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inward_tanker_out);
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
                startActivity(new Intent(Inward_Tanker_Out.this, Login.class));
            }
        });
        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Inward_Tanker_Out.this, Menu.class));
            }
        });
    }

    public void inwardtankerinweighmentclick(View view){
        Global_Var.getInstance().DeptType='W';
        Intent intent = new Intent(this,InwardOut_Tanker_Weighment.class);
        startActivity(intent);
    }
    public void inwadtankeroutsecurity(View view){
        Global_Var.getInstance().DeptType='S';
        Intent intent = new Intent(this, InwardOut_Tanker_Security.class);
        startActivity(intent);
    }
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
        finish();
    }
}