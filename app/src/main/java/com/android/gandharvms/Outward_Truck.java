package com.android.gandharvms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;

public class Outward_Truck extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outward_truck);

    }
    public void sequirityoutwardTruck(View view){
        Intent intent = new Intent(this, Outward_Truck_Security.class);
        startActivity(intent);
    }

    public void Weighmentouttankerclick(View view){
        Intent intent = new Intent(this,Outward_Truck_weighment.class);
        startActivity(intent);
    }
    public void labouttanker(View view){
        Intent intent = new Intent(this, Outward_Truck_Laboratory.class);
        startActivity(intent);
    }
    public void productionouttanker(View view){
        Intent intent = new Intent(this, Outward_Truck_Production.class);
        startActivity(intent);
    }
    public void Billingtanker(View view){
        Intent intent = new Intent(this, Outward_Truck_Billing.class);
        startActivity(intent);
    }
    public void dispatchtanker(View view){
      Intent intent = new Intent(this, Outward_Truck_Dispatch.class);
      startActivity(intent);
    }
    public void logisticclick(View view){
        Intent intent = new Intent(this, Outward_Truck_Logistics.class);
        startActivity(intent);
    }


}