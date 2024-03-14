package com.android.gandharvms.outward_Tanker_Lab_forms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.gandharvms.Global_Var;
import com.android.gandharvms.R;

public class outwardlabforms extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outwardlabforms);

    }
    public void processformlaboratory(View view){
        Global_Var.getInstance().DeptType='Q';
        Intent intent = new Intent(this, Outward_Tanker_Laboratory.class);
        startActivity(intent);
    }
    public void bulkloadinglaboratory(View view){
        Global_Var.getInstance().DeptType='K';
        Intent intent = new Intent(this, bulkloadinglaboratory.class);
        startActivity(intent);
    }
}