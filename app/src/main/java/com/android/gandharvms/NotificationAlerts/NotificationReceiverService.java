package com.android.gandharvms.NotificationAlerts;

import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

public class NotificationReceiverService extends NotificationListenerService {

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        // Check if the notification is from the Google Calendar App
        String packageName = sbn.getPackageName();

        if (packageName.equals("com.android.gandharvms")) {
            String title = sbn.getNotification().extras.getString("android.title");
            String text = sbn.getNotification().extras.getString("android.text");

            // Log the received notification details (or process it as needed)
            Log.d("CalendarNotification", "Received notification: " + title + " - " + text);

            // Store the notification in SQLite
            storeNotificationToDB(title, text);
        }
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        // Handle notification removal if needed
    }

    // Method to store the notification in SQLite database
    private void storeNotificationToDB(String title, String text) {
        CalendarNotificationDatabaseHelper dbHelper = new CalendarNotificationDatabaseHelper(getApplicationContext());
        String timestamp = String.valueOf(System.currentTimeMillis());
        dbHelper.insertNotification(title, text,  timestamp); // You can also store start/end time here if needed
    }
}
