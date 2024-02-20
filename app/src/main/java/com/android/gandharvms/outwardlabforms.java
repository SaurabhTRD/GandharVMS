package com.android.gandharvms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class outwardlabforms extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outwardlabforms);

    }
    public void processformlaboratory(View view){
        Intent intent = new Intent(this, Outward_Tanker_Laboratory.class);
        startActivity(intent);
    }
    public void bulkloadinglaboratory(View view){
        Intent intent = new Intent(this, bulkloadinglaboratory.class);
        startActivity(intent);
    }
}