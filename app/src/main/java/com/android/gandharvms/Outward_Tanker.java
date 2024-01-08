package com.android.gandharvms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Outward_Tanker extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outward_tanker);

    }
    public void sequirityoutwardTanker(View view){
        Intent intent = new Intent(this,Outward_Tanker_Security.class);
        startActivity(intent);
    }
    public void Weighmentouttankerclick(View view){
        Intent intent = new Intent(this, Outward_Tanker_weighment.class);
        startActivity(intent);
    }

    public void labouttanker(View view){
        Intent intent = new Intent(this, Outward_Tanker_Laboratory.class);
        startActivity(intent);
    }
    public void productionouttanker(View view){
        Intent intent = new Intent(this, Outward_Tanker_Production.class);
        startActivity(intent);
    }
    public void samplingouttanker(View view){
        Intent intent = new Intent(this, Outward_Tanker_Billing.class);
        startActivity(intent);
    }
}