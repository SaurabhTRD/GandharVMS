package com.android.gandharvms;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.android.gandharvms.LoginWithAPI.Login;
import com.android.gandharvms.LoginWithAPI.LoginMethod;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.RegisterwithAPI.RegRequestModel;
import com.android.gandharvms.RegisterwithAPI.RegResponseModel;
import com.android.gandharvms.RegisterwithAPI.Register;
import com.google.firebase.messaging.RemoteMessage;

import java.io.IOException;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    NotificationManager mNotificationManager;
    private LoginMethod loginMethod;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        // playing audio and vibration when user se reques
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
        r.play();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            r.setLooping(false);
        }

        // vibration
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {100, 300, 300, 300};
        v.vibrate(pattern, -1);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "CHANNEL_ID");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setSmallIcon(R.drawable.ic_notifications);
        } else {
            builder.setSmallIcon(R.drawable.ic_notifications);
        }
        Intent resultIntent = new Intent(this, Login.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, resultIntent, PendingIntent.FLAG_IMMUTABLE);
        builder.setContentTitle(remoteMessage.getNotification().getTitle());
        builder.setContentText(remoteMessage.getNotification().getBody());
        builder.setContentIntent(pendingIntent);
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(remoteMessage.getNotification().getBody()));
        builder.setAutoCancel(true);
        builder.setPriority(Notification.PRIORITY_MAX);
        mNotificationManager =
                (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
        {
            String channelId = "my_channel_id";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(channel);
            builder.setChannelId(channelId);
        }
        // notificationId is a unique int for each notification that you must define
        mNotificationManager.notify(100, builder.build());
    }

    public void onNewToken(String token) {
        super.onNewToken(token);
        Log.d("FCM", "New token: " + token);
        sendTokenToServer(token,Global_Var.getInstance().EmpId);  // Call your method to send the token to the server
    }

    private void sendTokenToServer(String token,String empid) {
        loginMethod = RetroApiClient.getLoginApi();
        RegResponseModel regrespo= new RegResponseModel();
        regrespo.EmpId = empid;
        regrespo.Token=token;
        regrespo.UpdatedBy=Global_Var.getInstance().Name;
        Call<Boolean> call = loginMethod.updateusertokenbyempid(regrespo);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful() && response.body() != null && response.body()==true) {
                    Log.d("Registration", "Response Body: " + response.body());
                    Toasty.success(FirebaseMessagingService.this, "Token UpdatedSuccessFully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(FirebaseMessagingService.this, Menu.class));
                }else {
                    // Registration failed
                    Log.e("Registration", "Unexpected response code: " + response.code());
                    Toasty.error(FirebaseMessagingService.this, "Unexpected response code..!", Toast.LENGTH_SHORT).show();
                    Log.e("Registration", "Registration failed. Response: " + response.body());
                    Toasty.error(FirebaseMessagingService.this, "Registration failed..!", Toast.LENGTH_SHORT).show();
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
                Toasty.error(FirebaseMessagingService.this, "Registration failed..!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}