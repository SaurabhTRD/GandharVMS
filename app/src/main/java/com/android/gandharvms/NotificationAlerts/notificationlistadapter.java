package com.android.gandharvms.NotificationAlerts;

import android.app.Notification;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.R;

import java.io.IOException;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class notificationlistadapter extends BaseAdapter {
    private List<NotificatioAlertsModel> notificationList;
    private NotificationAlertsInterface apinotificationalerts;
    private Context context;

    public notificationlistadapter(Context context, List<NotificatioAlertsModel> notifications,NotificationAlertsInterface apinotification) {
        this.context = context;
        this.notificationList = notifications;
        this.apinotificationalerts = apinotification;
    }

    @Override
    public int getCount() {
        return notificationList.size();
    }

    @Override
    public NotificatioAlertsModel getItem(int position) {
        return notificationList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.notification_item, parent, false);
        }

        NotificatioAlertsModel notification = getItem(position);

        TextView title = convertView.findViewById(R.id.tvNotificationTitle);
        TextView content = convertView.findViewById(R.id.tvNotificationContent);
        TextView timestamp = convertView.findViewById(R.id.tvNotificationTime);

        title.setText(notification.getTitle());
        content.setText(notification.getContent());
        timestamp.setText(notification.getTimestamp());
        if (notification.isRead()) {
            convertView.setBackgroundColor(ContextCompat.getColor(context, R.color.readBackground));
            title.setTypeface(null, Typeface.NORMAL);
        } else {
            convertView.setBackgroundColor(ContextCompat.getColor(context, R.color.unreadBackground));
            title.setTypeface(null, Typeface.BOLD);
        }
        convertView.setOnClickListener(v -> {
            if (!notification.IsRead) {
                markAsRead(notification.getId(), position);
            }
        });

        return convertView;
    }

    private void markAsRead(int notificationId, int position) {
        apinotificationalerts = RetroApiClient.NotificationInterface();
        Call<Integer> call=apinotificationalerts.marknotificationasreadbyid(notificationId);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful() && response.body()>0) {
                    notificationList.get(position).setRead(true);
                    notifyDataSetChanged();
                    Toasty.success(context, "Mark as Read", Toasty.LENGTH_SHORT).show();
                } else {
                    Toasty.error(context, "Failed to mark as read", Toasty.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
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
                Toasty.error(context, "Failed to Load API", Toasty.LENGTH_SHORT).show();
            }
        });
    }
}
