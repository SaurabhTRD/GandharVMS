package com.android.gandharvms;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.gandharvms.LoginWithAPI.Login;
import com.android.gandharvms.NotificationAlerts.CalendarNotificationDatabaseHelper;
import com.android.gandharvms.NotificationAlerts.NotificationAccessHelper;
import com.android.gandharvms.NotificationAlerts.NotificationListActivity;
import com.android.gandharvms.submenu.Submenu_Outward_Truck;
import com.android.gandharvms.submenu.Submenu_outward_tanker;
import com.android.gandharvms.submenu.submenu_Inward_Tanker;
import com.android.gandharvms.submenu.submenu_Inward_Truck;
import com.google.firebase.BuildConfig;

import es.dmoral.toasty.Toasty;

public class Menu extends AppCompatActivity {

    //DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://gandharvms-default-rtdb.firebaseio.com/");
    private String userRole = "default";
    TextView username,empid,notifybadge;
    ImageView btnlogout,btnnotifications;
    private static final String PREFS_NAME = "MyPrefs";
    public static final String KEY_USERNAME = "username";
    public static final String pass = "password";

    private NotificationAccessHelper notificationAccessHelper;
    private CalendarNotificationDatabaseHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnlogout = findViewById(R.id.btn_logoutButton);
        btnnotifications=findViewById(R.id.btn_notificationButton);
        username=findViewById(R.id.tv_username);
        empid=findViewById(R.id.tv_employeeId);
        notifybadge=findViewById(R.id.notificationBadge);

        notificationAccessHelper = new NotificationAccessHelper();
        dbHelper = new CalendarNotificationDatabaseHelper(this);

        String userName=Global_Var.getInstance().Name;
        String empId=Global_Var.getInstance().EmpId;

        username.setText(userName);
        empid.setText(empId);
        // Set the version number
        TextView tvVersion = findViewById(R.id.tv_version);
//        String versionName = BuildConfig.VERSION_NAME;
        String versionName = "1.0.5";
        tvVersion.setText("Version " + versionName);

        if (!notificationAccessHelper.isNotificationAccessEnabled(this)) {
            showNotificationAccessDialog();
        } else {
            //Toast.makeText(this, "Notification Access already enabled", Toast.LENGTH_SHORT).show();
        }

        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Menu.this, Login.class));
            }
        });

        btnnotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this, NotificationListActivity.class));
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
                Toasty.success(Menu.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        updateNotificationBadge();
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

    // Show a dialog to request notification access
    private void showNotificationAccessDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Notification Access Required")
                .setMessage("This app requires notification access to process notification Alerts.")
                .setPositiveButton("Enable", (dialog, which) -> notificationAccessHelper.requestNotificationAccess(this))
                .setNegativeButton("Cancel", (dialog, which) -> {
                    Toasty.info(this, "Feature will not work without Notification Access", Toast.LENGTH_LONG).show();
                })
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateNotificationBadge();
    }

    private void updateNotificationBadge() {
        int unreadCount = dbHelper.getUnreadNotificationCount();
        if (unreadCount > 0) {
            notifybadge.setVisibility(View.VISIBLE);
            notifybadge.setText(String.valueOf(unreadCount));
        } else {
            notifybadge.setVisibility(View.GONE); // Hide badge if no unread notifications
        }
    }
}
