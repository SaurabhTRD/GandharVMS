package com.android.gandharvms.NotificationAlerts;

import android.app.Notification;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.gandharvms.R;

import java.util.List;

public class notificationlistadapter extends BaseAdapter {
    private Context context;
    private List<NotificationModel> notifications;

    public notificationlistadapter(Context context, List<NotificationModel> notifications) {
        this.context = context;
        this.notifications = notifications;
    }

    @Override
    public int getCount() {
        return notifications.size();
    }

    @Override
    public Object getItem(int position) {
        return notifications.get(position);
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

        NotificationModel notification = notifications.get(position);

        TextView tvTitle = convertView.findViewById(R.id.tvNotificationTitle);
        TextView tvContent = convertView.findViewById(R.id.tvNotificationContent);
        TextView tvTimestamp = convertView.findViewById(R.id.tvNotificationTime);

        tvTitle.setText(notification.getTitle());
        tvContent.setText(notification.getContent());
        tvTimestamp.setText(notification.getTimeAgo());

        return convertView;
    }
}
