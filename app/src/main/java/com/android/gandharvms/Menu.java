package com.android.gandharvms;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.gandharvms.LoginWithAPI.Login;
import com.android.gandharvms.submenu.Submenu_Outward_Truck;
import com.android.gandharvms.submenu.Submenu_outward_tanker;
import com.android.gandharvms.submenu.submenu_Inward_Tanker;
import com.android.gandharvms.submenu.submenu_Inward_Truck;
import com.google.firebase.BuildConfig;

public class Menu extends AppCompatActivity {

    //DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://gandharvms-default-rtdb.firebaseio.com/");
    private String userRole = "default";
    TextView username,empid;
    ImageView btnlogout;
    private static final String PREFS_NAME = "MyPrefs";
    public static final String KEY_USERNAME = "username";
    public static final String pass = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnlogout = findViewById(R.id.btn_logoutButton);
        username=findViewById(R.id.tv_username);
        empid=findViewById(R.id.tv_employeeId);

        String userName=Global_Var.getInstance().Name;
        String empId=Global_Var.getInstance().EmpId;

        username.setText(userName);
        empid.setText(empId);
        // Set the version number
        TextView tvVersion = findViewById(R.id.tv_version);
//        String versionName = BuildConfig.VERSION_NAME;
        String versionName = "1.0.4";
        tvVersion.setText("Version " + versionName);

        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Menu.this, Login.class));
            }
        });

        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Clear stored username and encrypted password
                SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(KEY_USERNAME);
                editor.remove(pass);
                editor.apply();

                // Clear Global_Var instance data if necessary
                Global_Var.getInstance().clear();

                // Redirect to login activity
                Intent intent = new Intent(Menu.this, Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                Toast.makeText(Menu.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    public void Inward_Tanker(View view) {
        Global_Var.getInstance().MenuType="IT";
        Intent intent = new Intent(this, submenu_Inward_Tanker.class);
        startActivity(intent);
    }

    public void Inward_process_Truckclick(View view) {
        Global_Var.getInstance().MenuType="IR";
        Intent intent = new Intent(this, submenu_Inward_Truck.class);
        startActivity(intent);
    }

    public void Outward_process_Tankerclick(View view) {
        Global_Var.getInstance().MenuType="OT";
        Intent intent = new Intent(this, Submenu_outward_tanker.class);
        startActivity(intent);
    }

    public void Outward_process_Truckclick(View view) {
        Global_Var.getInstance().MenuType="OR";
        Intent intent = new Intent(this, Submenu_Outward_Truck.class);
        startActivity(intent);
    }

}
