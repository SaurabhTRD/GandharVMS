package com.android.gandharvms.NotificationAlerts;

public class NotificationModel {
    private String title;
    private String content;
    private String timeAgo;

    public NotificationModel(String title, String content, String timeAgo) {
        this.title = title;
        this.content = content;
        this.timeAgo = timeAgo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTimeAgo() {
        return timeAgo;
    }

    public void setTimeAgo(String timeAgo) {
        this.timeAgo = timeAgo;
    }
}
