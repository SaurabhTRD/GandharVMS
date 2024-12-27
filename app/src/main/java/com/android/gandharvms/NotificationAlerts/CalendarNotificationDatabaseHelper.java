package com.android.gandharvms.NotificationAlerts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CalendarNotificationDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "vehicles_notifications.db";
    private static final int DATABASE_VERSION = 2;

    public static final String TABLE_NOTIFICATIONS = "notifications";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_CONTENT = "content";
    public static final String COLUMN_TIMESTAMP = "timestamp";  // When the notification was triggered
    public static final String COLUMN_IS_READ = "is_read";

    public CalendarNotificationDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NOTIFICATIONS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_CONTENT + " TEXT, " +
                COLUMN_TIMESTAMP + " TEXT, " +
                COLUMN_IS_READ + " INTEGER DEFAULT 0)"; // 0 for unread, 1 for read
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) { // Assuming DATABASE_VERSION is now updated to 2
            String addColumnQuery = "ALTER TABLE " + TABLE_NOTIFICATIONS + " ADD COLUMN " + COLUMN_IS_READ + " INTEGER DEFAULT 0";
            db.execSQL(addColumnQuery);
        }
    }

    public void insertNotification(String title, String text, String timestamp) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_CONTENT, text);
        values.put(COLUMN_TIMESTAMP, timestamp);
        db.insert(TABLE_NOTIFICATIONS, null, values);
        db.close();
    }

    public Cursor getAllNotifications() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_NOTIFICATIONS, null, null, null, null, null, COLUMN_TIMESTAMP + " DESC");
    }

    public int getUnreadNotificationCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM " + TABLE_NOTIFICATIONS + " WHERE " + COLUMN_IS_READ + " = 0"; // Assume `COLUMN_IS_READ` tracks read/unread
        Cursor cursor = db.rawQuery(query, null);
        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        return count;
    }

    public void markAllNotificationsAsRead() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_IS_READ, 1); // Mark as read
        db.update(TABLE_NOTIFICATIONS, values, null, null); // Update all rows
    }
}
