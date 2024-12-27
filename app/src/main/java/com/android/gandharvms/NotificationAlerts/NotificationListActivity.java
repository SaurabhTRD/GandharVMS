package com.android.gandharvms.NotificationAlerts;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.gandharvms.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NotificationListActivity extends AppCompatActivity {

    private ListView lvNotifications;
    private CalendarNotificationDatabaseHelper dbHelper;
    private notificationlistadapter notificationadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_list);

        lvNotifications = findViewById(R.id.lvNotifications);
        dbHelper = new CalendarNotificationDatabaseHelper(this);

        // Fetch notifications from the database
        Cursor cursor = dbHelper.getAllNotifications();
        List<NotificationModel> notificationList = new ArrayList<>();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(CalendarNotificationDatabaseHelper.COLUMN_TITLE));
                @SuppressLint("Range") String content = cursor.getString(cursor.getColumnIndex(CalendarNotificationDatabaseHelper.COLUMN_CONTENT));
                @SuppressLint("Range") String timestamp = cursor.getString(cursor.getColumnIndex(CalendarNotificationDatabaseHelper.COLUMN_TIMESTAMP));

                // Skip notifications with empty title or content
                if (title == null || title.trim().isEmpty() || content == null || content.trim().isEmpty()) {
                    continue;
                }

                String readableTimestamp = convertTimestampToDateTime(Long.parseLong(timestamp));
                // Create a Notification object and add it to the list
                notificationList.add(new NotificationModel(title, content, readableTimestamp));
            }
            cursor.close();
        }

        // Display the notifications in the ListView
        notificationadapter = new notificationlistadapter(this, notificationList);
        lvNotifications.setAdapter(notificationadapter);

        // Mark all notifications as read
        dbHelper.markAllNotificationsAsRead();
    }

    public static String convertTimestampToDateTime(long timestampInMillis) {
        // Define the desired format
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        // Create a Date object from the timestamp
        Date date = new Date(timestampInMillis);
        // Format the Date object into a human-readable string
        return sdf.format(date);
    }
}