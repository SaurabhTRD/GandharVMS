package com.android.gandharvms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.gandharvms.submenu.Submenu_Outward_Truck;
import com.android.gandharvms.submenu.Submenu_outward_tanker;
import com.android.gandharvms.submenu.submenu_Inward_Tanker;
import com.android.gandharvms.submenu.submenu_Inward_Truck;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
//        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
//        String receivedEmplid = sharedPreferences.getString("EMPLID_KEY", "");

//        Intent intent = new Intent(this,Inward_Tanker.class);
//        startActivity(intent);
    }
    public void Inward_Tanker(View view){
       Intent intent = new Intent(this, submenu_Inward_Tanker.class);
        startActivity(intent);
   }
   public void Inward_process_Truckclick(View view){
        Intent intent = new Intent(this, submenu_Inward_Truck.class);
        startActivity(intent);
   }
   public void Outward_process_Tankerclick(View view){
        Intent intent = new Intent(this, Submenu_outward_tanker.class);
        startActivity(intent);
   }
   public void Outward_process_Truckclick(View view){
        Intent intent = new Intent(this, Submenu_Outward_Truck.class);
        startActivity(intent);
   }

}
