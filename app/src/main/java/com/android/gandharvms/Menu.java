package com.android.gandharvms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.gandharvms.submenu.Submenu_Outward_Truck;
import com.android.gandharvms.submenu.Submenu_outward_tanker;
import com.android.gandharvms.submenu.submenu_Inward_Tanker;
import com.android.gandharvms.submenu.submenu_Inward_Truck;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Menu extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://gandharvms-default-rtdb.firebaseio.com/");
    private String userRole = "default";
    Button btnlogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnlogout = findViewById(R.id.btn_logoutButton);
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Menu.this, Login.class));
            }
        });
    }

    public void Inward_Tanker(View view) {
        Intent intent = new Intent(this, submenu_Inward_Tanker.class);
        startActivity(intent);
    }

    public void Inward_process_Truckclick(View view) {
        Intent intent = new Intent(this, submenu_Inward_Truck.class);
        startActivity(intent);
    }

    public void Outward_process_Tankerclick(View view) {
        Intent intent = new Intent(this, Submenu_outward_tanker.class);
        startActivity(intent);
    }

    public void Outward_process_Truckclick(View view) {
        Intent intent = new Intent(this, Submenu_Outward_Truck.class);
        startActivity(intent);
    }

}
