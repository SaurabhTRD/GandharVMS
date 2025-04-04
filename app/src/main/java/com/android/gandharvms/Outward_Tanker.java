package com.android.gandharvms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.gandharvms.Inward_Tanker_Sampling.Inward_Tanker_Sampling;
import com.android.gandharvms.Inward_Tanker_Security.Inward_Tanker_Security;
import com.android.gandharvms.Inward_Tanker_Weighment.Inward_Tanker_Weighment;
import com.android.gandharvms.LoginWithAPI.Login;
import com.android.gandharvms.NotificationAlerts.NotificationCommonfunctioncls;
import com.android.gandharvms.Outward_Tanker_Billing.Outward_Tanker_Billing;
import com.android.gandharvms.Outward_Tanker_Production_forms.New_Outward_Tanker_Production;
import com.android.gandharvms.Outward_Tanker_Production_forms.Outward_Tanker_Production_Multiple;
import com.android.gandharvms.Outward_Tanker_Production_forms.inprocessrequestform;
import com.android.gandharvms.Outward_Tanker_Security.Outward_Tanker_Security;
import com.android.gandharvms.Outward_Tanker_Weighment.Outward_Tanker_weighment;
import com.android.gandharvms.outward_Tanker_Lab_forms.New_Outward_tanker_Lab;
import com.android.gandharvms.outward_Tanker_Lab_forms.outwardlabforms;
import com.android.gandharvms.submenu.Submenu_outward_tanker;

import es.dmoral.toasty.Toasty;

public class Outward_Tanker extends NotificationCommonfunctioncls {
    ImageView btnlogout,btnhome;
    TextView username,empid;

    public static String Tanker;
    public static String Truck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outward_tanker);
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
                startActivity(new Intent(Outward_Tanker.this, Login.class));
            }
        });
        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Outward_Tanker.this, Menu.class));
            }
        });*/
        setupHeader();
    }
    public void sequirityoutwardTanker(View view){

        if(Global_Var.getInstance().Department.contains("Security")){
            Global_Var.getInstance().DeptType='S';
            startActivity(new Intent(this, Outward_Tanker_Security.class));
        } else {
            Toasty.warning(Outward_Tanker.this, "You are not in Security Department", Toast.LENGTH_SHORT).show();
        }
    }
    public void Weighmentouttankerclick(View view){
        if(Global_Var.getInstance().Department.contains("Weighment")){
            Global_Var.getInstance().DeptType='W';
            startActivity(new Intent(this, Outward_Tanker_weighment.class));
        } else {
            Toasty.warning(Outward_Tanker.this, "You are not in Weighment Department", Toast.LENGTH_SHORT).show();
        }
    }

//    public void inprocessrequestform(View view){
//        Intent intent = new Intent(this, inprocessrequestform.class);
//        startActivity(intent);
//    }

    public void labouttanker(View view){
        if(Global_Var.getInstance().Department.contains("Laboratory")){
            Global_Var.getInstance().DeptType='L';
            startActivity(new Intent(this, New_Outward_tanker_Lab.class));
        } else {
            Toasty.warning(Outward_Tanker.this, "You are not in Laboratory Department", Toast.LENGTH_SHORT).show();
        }
    }
    public void productionouttanker(View view){
        if(Global_Var.getInstance().Department.contains("Production")){
            Global_Var.getInstance().DeptType='P';
            startActivity(new Intent(this, Outward_Tanker_Production_Multiple.class));
        } else {
            Toasty.warning(Outward_Tanker.this, "You are not in Production Department", Toast.LENGTH_SHORT).show();
        }
    }
    public void samplingouttanker(View view){
        if(Global_Var.getInstance().Department.contains("Billing")){
            Global_Var.getInstance().DeptType='B';
            startActivity(new Intent(this, Outward_Tanker_Billing.class));
        } else {
            Toasty.warning(Outward_Tanker.this, "You are not in Sampling Department", Toast.LENGTH_SHORT).show();
        }
    }
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
        finish();
    }
}