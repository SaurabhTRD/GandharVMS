package com.android.gandharvms.submenu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.gandharvms.Inward_Tanker;
import com.android.gandharvms.Inward_Tanker_Out;
import com.android.gandharvms.login.Login;
import com.android.gandharvms.R;

public class submenu_Inward_Tanker extends AppCompatActivity {
    Button btnlogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submenu_inward_tanker);
        btnlogout=findViewById(R.id.btn_logoutButton);
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(submenu_Inward_Tanker.this, Login.class));
            }
        });
    }


    public void inwardtankerinclick(View view){
        Intent intent = new Intent(this, Inward_Tanker.class);
        startActivity(intent);
    }
    public void inwardtankeroutclick(View view){
        Intent intent = new Intent(this, Inward_Tanker_Out.class);
        startActivity(intent);
    }
}