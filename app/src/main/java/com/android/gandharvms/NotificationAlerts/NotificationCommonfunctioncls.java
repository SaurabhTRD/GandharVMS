package com.android.gandharvms.NotificationAlerts;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.android.gandharvms.Global_Var;
import com.android.gandharvms.LoginWithAPI.Login;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.Menu;
import com.android.gandharvms.R;

import java.io.IOException;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class NotificationCommonfunctioncls extends AppCompatActivity {
    private String userRole = "default";
    TextView username,empid,notifybadge;
    ImageView btnlogout,btnnotifications,btnhome;
    private static final String PREFS_NAME = "MyPrefs";
    public static final String KEY_USERNAME = "username";
    public static final String pass = "password";

    private NotificationAccessHelper notificationAccessHelper;
    //private CalendarNotificationDatabaseHelper dbHelper;
    private NotificationAlertsInterface notificationalerts = RetroApiClient.NotificationInterface();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
    }

    protected void setupHeader() {
        btnlogout = findViewById(R.id.btn_logoutButton);
        btnnotifications = findViewById(R.id.btn_notificationButton);
        btnhome=findViewById(R.id.btn_homeButton);
        username = findViewById(R.id.tv_username);
        empid = findViewById(R.id.tv_employeeId);
        notifybadge = findViewById(R.id.notificationBadge);
        notificationAccessHelper = new NotificationAccessHelper();
        // Set up user info
        String userName = Global_Var.getInstance().Name;
        String empIdValue = Global_Var.getInstance().EmpId;
        username.setText(userName);
        empid.setText(empIdValue);

        TextView tvVersion = findViewById(R.id.tv_version);
//        String versionName = BuildConfig.VERSION_NAME;
        String versionName = Global_Var.getInstance().APKversion;
        tvVersion.setText("Version " + versionName);

        if (!notificationAccessHelper.isNotificationAccessEnabled(this)) {
            showNotificationAccessDialog();
        } else {
            //Toast.makeText(this, "Notification Access already enabled", Toast.LENGTH_SHORT).show();
        }

        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NotificationCommonfunctioncls.this, Menu.class));
            }
        });

        /*btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NotificationCommonfunctioncls.this, Login.class));
            }
        });*/
        // Set up notification button
        btnnotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NotificationCommonfunctioncls.this, NotificationListActivity.class));
            }
        });
        // Set up logout button
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
                Intent intent = new Intent(NotificationCommonfunctioncls.this, Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                Toasty.success(NotificationCommonfunctioncls.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        // Update notification badge
        updateNotificationBadge();
    }

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
        Call<Integer> call = notificationalerts.GetTotalNotification();
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()) {
                    int unreadCount = response.body();
                    if (unreadCount > 0) {
                        notifybadge.setText(String.valueOf(unreadCount));
                        notifybadge.setVisibility(View.VISIBLE);
                    } else {
                        notifybadge.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                notifybadge.setVisibility(View.GONE);
                Log.e("Retrofit", "Failure: " + t.getMessage());
                // Check if there's a response body in case of an HTTP error
                if (call != null && call.isExecuted() && call.isCanceled() && t instanceof HttpException) {
                    Response<?> response = ((HttpException) t).response();
                    if (response != null) {
                        Log.e("Retrofit", "Error Response Code: " + response.code());
                        try {
                            Log.e("Retrofit", "Error Response Body: " + response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                Toasty.error(NotificationCommonfunctioncls.this, "failed..!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
