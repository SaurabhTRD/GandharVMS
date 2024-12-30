package com.android.gandharvms.NotificationAlerts;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class NotificationListActivity extends AppCompatActivity {

    private ListView lvNotifications;
    //private CalendarNotificationDatabaseHelper dbHelper;
    private notificationlistadapter notificationadapter;
    private NotificationAlertsInterface notificationalerts = RetroApiClient.NotificationInterface();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_list);

        lvNotifications = findViewById(R.id.lvNotifications);
        fetchNotificationListing();
    }

    public void fetchNotificationListing(){
        Call<List<NotificatioAlertsModel>> call = notificationalerts.getnotificationlist();
        call.enqueue(new Callback<List<NotificatioAlertsModel>>() {
            @Override
            public void onResponse(Call<List<NotificatioAlertsModel>> call, Response<List<NotificatioAlertsModel>> response) {
                if (response.isSuccessful()) {
                    List<NotificatioAlertsModel> notifications = response.body();
                    notificationadapter = new notificationlistadapter(NotificationListActivity.this, notifications, notificationalerts);
                    lvNotifications.setAdapter(notificationadapter);
                } else {
                    Toasty.error(NotificationListActivity.this, "Failed to load notifications", Toasty.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<NotificatioAlertsModel>> call, Throwable t) {
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
                Toasty.error(NotificationListActivity.this, "failed..!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}