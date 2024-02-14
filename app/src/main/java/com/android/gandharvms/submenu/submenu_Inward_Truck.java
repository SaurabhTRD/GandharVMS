package com.android.gandharvms.submenu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.gandharvms.Global_Var;
import com.android.gandharvms.Inward_Tanker_Out;
import com.android.gandharvms.Inward_Tanker_Security.grid;
import com.android.gandharvms.Inward_Truck;
import com.android.gandharvms.LoginWithAPI.Login;
import com.android.gandharvms.R;

public class submenu_Inward_Truck extends AppCompatActivity {
    Button btnlogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submenu_inward_truck);

        btnlogout=findViewById(R.id.btn_logoutButton);

        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(submenu_Inward_Truck.this, Login.class));
            }
        });
    }

    public void inwardtankerinclick(View view){
        Global_Var.getInstance().InOutType='I';
        Intent intent = new Intent(this, Inward_Truck.class);
        startActivity(intent);
    }
    public void inwardtankeroutclick(View view){
        Global_Var.getInstance().InOutType='O';
        Intent intent = new Intent(this, Inward_Tanker_Out.class);
        startActivity(intent);
    }
    public void intankerstatusclick(View view){
        Global_Var.getInstance().InOutType='x';
        Global_Var.getInstance().DeptType='x';
        Intent intent = new Intent(this, grid.class);
        startActivity(intent);
    }
}