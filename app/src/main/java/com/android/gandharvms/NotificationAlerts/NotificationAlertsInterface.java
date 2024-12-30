package com.android.gandharvms.NotificationAlerts;

import com.android.gandharvms.InwardCompletedGrid.CommonResponseModelForAllDepartment;
import com.android.gandharvms.Inward_Tanker_Laboratory.InTanLabRequestModel;
import com.android.gandharvms.Inward_Tanker_Laboratory.InTanLabResponseModel;
import com.android.gandharvms.Inward_Tanker_Laboratory.it_lab_UpdateByInwardid_request_Model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface NotificationAlertsInterface {

    @GET("api/NotificationAlerts/GetNotificationList")
    Call<List<NotificatioAlertsModel>> getnotificationlist();

    @POST("api/NotificationAlerts/AddNotifications")
    Call<Boolean> addnotificationmodel(@Body NotificatioAlertsModel alertsnotification);

    @POST("api/NotificationAlerts/MarkNotificationAsReadbyId")
    Call<Integer> marknotificationasreadbyid(@Query("notificationid") int notificationid);

    @GET("api/NotificationAlerts/GetUnreadNotificationCount")
    Call<Integer> GetTotalNotification();

    @POST("api/NotificationAlerts/MarkAllNotificationAsRead")
    Call<Integer> markallnotificationasread();
}
