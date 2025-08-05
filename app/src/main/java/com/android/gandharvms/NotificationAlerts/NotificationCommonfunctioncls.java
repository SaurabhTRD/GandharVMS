package com.android.gandharvms.NotificationAlerts;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.DisplayMetrics;
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
import com.android.gandharvms.Util.ConnectivityReceiverNew;
import com.android.gandharvms.Util.NoInternetDialogUtil;

import java.io.IOException;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class NotificationCommonfunctioncls extends AppCompatActivity implements ConnectivityReceiverNew.ConnectivityReceiverListener {
    private String userRole = "default";
    TextView username,empid,notifybadge;
    ImageView btnlogout,btnnotifications,btnhome;
    private static final String PREFS_NAME = "MyPrefs";
    public static final String KEY_USERNAME = "username";
    public static final String pass = "password";
    private static final long MAX_IDLE_TIME = 10 * 60 * 1000; // 10 minutes
    private long lastActiveTime;
    private boolean isFirstLaunch = true;
    //private View customActionBar;
    private NoInternetDialogUtil noInternetUtil = new NoInternetDialogUtil();
    private ConnectivityReceiverNew connectivityReceiver;

    private NotificationAccessHelper notificationAccessHelper;
    //private CalendarNotificationDatabaseHelper dbHelper;
    private NotificationAlertsInterface notificationalerts = RetroApiClient.NotificationInterface();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        // Only show warning if user has increased the display size (densityDpi > stable)
        int currentDpi = metrics.densityDpi;
        int defaultDpi = DisplayMetrics.DENSITY_DEVICE_STABLE;

        if (currentDpi > defaultDpi) {
            showUnsupportedDisplaySizeDialog();
        }

        long currentTime = System.currentTimeMillis();
        if (!isFirstLaunch && (currentTime - lastActiveTime > MAX_IDLE_TIME)) {
            onSessionTimeout();
        }
        isFirstLaunch = false;  // After first launch, session checks apply
        lastActiveTime = currentTime;
        connectivityReceiver = new ConnectivityReceiverNew(this);
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(connectivityReceiver, filter);
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

    private void showUnsupportedDisplaySizeDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Display Size Not Supported")
                .setMessage("Please set your display size to 'Small' or 'Default' or 'Standard' for the best experience.")
                .setCancelable(false)
                .setPositiveButton("Open Settings", (dialog, which) -> {
                    startActivity(new Intent(Settings.ACTION_DISPLAY_SETTINGS));
                })
                .setNegativeButton("Exit App", (dialog, which) -> {
                    finishAffinity(); // Close the app
                })
                .show();
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration(res.getConfiguration());
        float currentScale = config.fontScale;

        DisplayMetrics metrics = res.getDisplayMetrics();
        float maxScale = 1.0f; // Force max scale to 1.0f on all devices

        if (currentScale > maxScale) {
            config.fontScale = maxScale;
            res.updateConfiguration(config, metrics);
        }

        return res;
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if (isConnected) {
            noInternetUtil.dismissDialog(this);
            onNetworkAvailable();
        } else {
            noInternetUtil.showDialog(this);
            onNetworkLost();
        }
    }

    protected void onNetworkAvailable() { }
    protected void onNetworkLost() { }

    @Override
    protected void onResume() {
        super.onResume();
        updateNotificationBadge();
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastActiveTime > MAX_IDLE_TIME) {
            onSessionTimeout();
        }

        lastActiveTime = currentTime;
        boolean isConnected = ConnectivityReceiverNew.isConnected(this);
        onNetworkConnectionChanged(isConnected);
    }

    @Override
    protected void onPause() {
        super.onPause();
        lastActiveTime = System.currentTimeMillis();
    }

    protected void onSessionTimeout() {
        new AlertDialog.Builder(this)
                .setTitle("Session Expired")
                .setMessage("You were inactive for a while. Please log in again or reload the page.")
                .setCancelable(false)
                .setPositiveButton("Reload", (dialog, which) -> {
                    recreate();  // Reload the current screen safely
                })
                .setNegativeButton("Exit", (dialog, which) -> {
                    finishAffinity();
                })
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (connectivityReceiver != null) {
            unregisterReceiver(connectivityReceiver);
        }
    }
}
