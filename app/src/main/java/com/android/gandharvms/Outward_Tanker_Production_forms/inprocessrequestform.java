package com.android.gandharvms.Outward_Tanker_Production_forms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.gandharvms.Global_Var;
import com.android.gandharvms.R;

public class inprocessrequestform extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inprocessrequestform);
    }
//    public void processformproduction(View view){
//        Global_Var.getInstance().DeptType='I';
//        Intent intent = new Intent(this, Outward_Tanker_Production.class);
//        startActivity(intent);
//    }
//    public void bulkloadingproduction(View view){
//        Global_Var.getInstance().DeptType='U';
//        Intent intent = new Intent(this, bulkloadingproduction.class);
//        startActivity(intent);
//    }
}