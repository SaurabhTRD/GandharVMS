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

import com.android.gandharvms.Inward_Tanker_Laboratory.Inward_Tanker_Laboratory;
import com.android.gandharvms.Inward_Tanker_Production.Inward_Tanker_Production;
import com.android.gandharvms.Inward_Tanker_Sampling.Inward_Tanker_Sampling;
import com.android.gandharvms.Inward_Tanker_Security.Inward_Tanker_Security;
import com.android.gandharvms.Inward_Tanker_Weighment.Inward_Tanker_Weighment;
import com.android.gandharvms.LoginWithAPI.Login;
import com.android.gandharvms.submenu.submenu_Inward_Tanker;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import es.dmoral.toasty.Toasty;


public class Inward_Tanker extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://gandharvms-default-rtdb.firebaseio.com/");
    private String userRole="default";
    ImageView btnlogout,btnhome;
    TextView username,empid;
    private String role=Global_Var.getInstance().Department;
    private String Empid= Global_Var.getInstance().EmpId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inward_tanker);
        btnlogout=findViewById(R.id.btn_logoutButton);
        btnhome = findViewById(R.id.btn_homeButton);
        username=findViewById(R.id.tv_username);
        empid=findViewById(R.id.tv_employeeId);

        String userName=Global_Var.getInstance().Name;
        String empId=Global_Var.getInstance().EmpId;

        username.setText(userName);
        empid.setText(empId);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String receivedEmplid = sharedPreferences.getString("EMPLID_KEY", "");
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Inward_Tanker.this, Login.class));
            }
        });
        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Inward_Tanker.this, Menu.class));
            }
        });
    }

    public void sequirityinwardT(View view) {
        if(Global_Var.getInstance().Department.contains("Security")){
            Global_Var.getInstance().DeptType='S';
            startActivity(new Intent(this, Inward_Tanker_Security.class));
        } else {
            Toasty.warning(Inward_Tanker.this, "You are not in Security Department", Toast.LENGTH_SHORT).show();
        }
    }

    public void Weighmentclick(View view) {
        if(Global_Var.getInstance().Department.contains("Weighment")){
            Global_Var.getInstance().DeptType='W';
            startActivity(new Intent(this, Inward_Tanker_Weighment.class));
        } else {
            Toasty.warning(Inward_Tanker.this, "You are not in Weighment Department", Toast.LENGTH_SHORT).show();
        }
    }

    public void samplicgclick(View view) {
        if(Global_Var.getInstance().Department.contains("Sampling")){
            Global_Var.getInstance().DeptType='M';
            startActivity(new Intent(this, Inward_Tanker_Sampling.class));
        } else {
            Toasty.warning(Inward_Tanker.this, "You are not in Sampling Department", Toast.LENGTH_SHORT).show();
        }
    }

    public void productionclick(View view) {
        if(Global_Var.getInstance().Department.contains("Production")){
            Global_Var.getInstance().DeptType='P';
            startActivity(new Intent(this, Inward_Tanker_Production.class));
        } else {
            Toasty.warning(Inward_Tanker.this, "You are not in Production Department", Toast.LENGTH_SHORT).show();
        }
    }

    public void Laboratoryclick(View view) {
        if(Global_Var.getInstance().Department.contains("Laboratory")){
            Global_Var.getInstance().DeptType='L';
            startActivity(new Intent(this, Inward_Tanker_Laboratory.class));
        } else {
            Toasty.warning(Inward_Tanker.this, "You are not in Laboratory Department", Toast.LENGTH_SHORT).show();
        }
    }
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
        finish();
    }
}