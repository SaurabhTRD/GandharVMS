package com.android.gandharvms.submenu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.gandharvms.OutwardOut_Tanker;
import com.android.gandharvms.Outward_Tanker;
import com.android.gandharvms.R;

public class Submenu_outward_tanker extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submenu_outward_tanker);


//        public void Outward_process_Tankerclick(View view){
//            Intent intent = new Intent(this, Submenu_outward_tanker.class);
//        }


    }
    public void tankerinclick(View view){
        Intent intent = new Intent(this, Outward_Tanker.class);
        startActivity(intent);
    }

    public void tankeroutclick(View view){
        Intent intent = new Intent(this, OutwardOut_Tanker.class);
        startActivity(intent);
    }
}