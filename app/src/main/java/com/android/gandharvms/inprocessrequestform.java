package com.android.gandharvms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class inprocessrequestform extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inprocessrequestform);


    }
    public void processformproduction(View view){
        Intent intent = new Intent(this,Outward_Tanker_Production.class);
        startActivity(intent);
    }
    public void bulkloadingproduction(View view){
        Intent intent = new Intent(this, bulkloadingproduction.class);
        startActivity(intent);
    }
}