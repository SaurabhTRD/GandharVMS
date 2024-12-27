package com.android.gandharvms.NotificationAlerts;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;

import androidx.core.app.NotificationManagerCompat;

public class NotificationAccessHelper {
    // Check if notification access is enabled
    public boolean isNotificationAccessEnabled(Context context) {
        return NotificationManagerCompat.getEnabledListenerPackages(context)
                .contains(context.getPackageName());
    }

    // Open the notification access settings screen
    public void requestNotificationAccess(Context context) {
        Intent intent = new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS);
        context.startActivity(intent);
    }

    // Ensure notification access is enabled
    public void ensureNotificationAccess(Context context) {
        if (!isNotificationAccessEnabled(context)) {
            Log.d("NotificationAccess", "Notification access not enabled. Prompting user.");
            requestNotificationAccess(context);
        } else {
            Log.d("NotificationAccess", "Notification access already enabled.");
        }
    }
}
