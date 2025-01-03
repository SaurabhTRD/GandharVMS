package com.android.gandharvms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.gandharvms.LoginWithAPI.Login;
import com.android.gandharvms.NotificationAlerts.NotificationCommonfunctioncls;
import com.android.gandharvms.OutwardOutDataEntryForm_Production.DataEntryForm_Production;
import com.android.gandharvms.OutwardOutTankerBilling.ot_outBilling;
import com.android.gandharvms.OutwardOut_Tanker_Security.OutwardOut_Tanker_Security;
import com.android.gandharvms.Outwardout_Tanker_Weighment.OutwardOut_Tanker_Weighment;
import com.android.gandharvms.submenu.Submenu_outward_tanker;

import es.dmoral.toasty.Toasty;

public class OutwardOut_Tanker extends NotificationCommonfunctioncls {
    ImageView btnlogout,btnhome;
    TextView username,empid;

    public static String Tanker;
    public static String Truck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outward_out_tanker);
        /*btnlogout=findViewById(R.id.btn_logoutButton);
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
                startActivity(new Intent(OutwardOut_Tanker.this, Login.class));
            }
        });
        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OutwardOut_Tanker.this, Menu.class));
            }
        });*/
        setupHeader();
    }
    public void outwardouttankerwighment(View view){
        if(Global_Var.getInstance().Department.contains("Weighment")){
            Global_Var.getInstance().DeptType='W';
            startActivity(new Intent(this, OutwardOut_Tanker_Weighment.class));
        } else {
            Toasty.warning(OutwardOut_Tanker.this, "You are not in Weighment Department", Toast.LENGTH_SHORT).show();
        }
    }

    public void dataentryoutwardouttankerclick(View view){
        if(Global_Var.getInstance().Department.contains("DataEntry")){
            Global_Var.getInstance().DeptType='P';
            startActivity(new Intent(this, DataEntryForm_Production.class));
        } else {
            Toasty.warning(OutwardOut_Tanker.this, "You are not in DataEntry Department", Toast.LENGTH_SHORT).show();
        }
    }

    public void outwardouttankerbillingclick(View view){
        if(Global_Var.getInstance().Department.contains("Billing")){
            Global_Var.getInstance().DeptType='B';
            startActivity(new Intent(this, ot_outBilling.class));
        } else {
            Toasty.warning(OutwardOut_Tanker.this, "You are not in Billing Department", Toast.LENGTH_SHORT).show();
        }
    }
    public void outwardouttankersecurity(View view){
        if(Global_Var.getInstance().Department.contains("Security")){
            Global_Var.getInstance().DeptType='S';
            startActivity(new Intent(this, OutwardOut_Tanker_Security.class));
        } else {
            Toasty.warning(OutwardOut_Tanker.this, "You are not in Security Department", Toast.LENGTH_SHORT).show();
        }
    }
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
        finish();
    }
}