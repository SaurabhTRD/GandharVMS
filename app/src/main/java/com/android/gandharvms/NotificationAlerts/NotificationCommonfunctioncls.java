package com.android.gandharvms.NotificationAlerts;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import es.dmoral.toasty.Toasty;

public class NotificationCommonfunctioncls extends AppCompatActivity {
     Context context;
    private NotificationAccessHelper notificationAccessHelper;
    public NotificationCommonfunctioncls(Context context) {
        this.context = context;
    }

    private void showNotificationAccessDialog() {
        notificationAccessHelper = new NotificationAccessHelper();
        new AlertDialog.Builder(context)
                .setTitle("Notification Access Required")
                .setMessage("This app requires notification access to process notification Alerts.")
                .setPositiveButton("Enable", (dialog, which) -> notificationAccessHelper.requestNotificationAccess(this))
                .setNegativeButton("Cancel", (dialog, which) -> {
                    Toasty.info(context, "Feature will not work without Notification Access", Toast.LENGTH_LONG).show();
                })
                .show();
    }
}
