package com.android.gandharvms.NotificationAlerts;

import android.content.Intent;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.widget.Toast;

import com.android.gandharvms.Inward_Tanker;
import com.android.gandharvms.Inward_Tanker_Production.Inward_Tanker_Production;
import com.android.gandharvms.LoginWithAPI.LoginMethod;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;


public class NotificationReceiverService extends NotificationListenerService {

    private NotificationAlertsInterface notificationalerts = RetroApiClient.NotificationInterface();
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
        NotificatioAlertsModel alertnotification = new NotificatioAlertsModel();
        alertnotification.Title=title;
        alertnotification.Content=text;
        Date currentDate = Calendar.getInstance().getTime();
        String dateFormatPattern = "dd-MM-yyyy HH:mm:ss";
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatPattern, Locale.getDefault());
        String formattedDate = dateFormat.format(currentDate);
        alertnotification.Timestamp=formattedDate;
        alertnotification.IsRead=false;
        Call<Boolean> call=notificationalerts.addnotificationmodel(alertnotification);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful() && response.body() != null && response.body()==true) {
                    Log.d("Production", "Response Body: " + response.body());
                } else {
                    Toasty.error(NotificationReceiverService.this, "Data Insertion Failed..!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
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
                Toasty.error(NotificationReceiverService.this, "failed..!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
