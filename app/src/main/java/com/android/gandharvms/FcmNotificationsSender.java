package com.android.gandharvms;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Environment;

import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.auth.oauth2.GoogleCredentials;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class FcmNotificationsSender {
    private static final String FCM_URL = "https://fcm.googleapis.com/v1/projects/gandharvms-5a192/messages:send";
    private static final String[] SCOPES = {"https://www.googleapis.com/auth/firebase.messaging"};
    public static final String CREDENTIAL_PATH = "gandharvms-5a192-firebase-adminsdk-3j88x-850cbcb6aa.json"; // Update the path
    String userFcmToken, title, body;
    Context mContext;
    Activity mActivity;

    public FcmNotificationsSender(String userFcmToken, String title, String body, Context mContext, Activity mActivity) {
        this.userFcmToken = userFcmToken;
        this.title = title;
        this.body = body;
        this.mContext = mContext;
        this.mActivity = mActivity;
    }

    public void SendNotifications() {
        try {
            // Example of loading the JSON file from external storage
            AssetManager assetManager = mContext.getAssets();
            InputStream serviceAccount = assetManager.open(CREDENTIAL_PATH);

            GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount).createScoped(Collections.singletonList(SCOPES[0]));
            credentials.refreshIfExpired();
            String accessToken = credentials.getAccessToken().getTokenValue();
            // Build the notification message JSON
            JSONObject message = new JSONObject();
            JSONObject notification = new JSONObject();
            JSONObject messageObject = new JSONObject();
            JSONObject notificationObject = new JSONObject();

            try {
                notificationObject.put("title", title);
                notificationObject.put("body", body);
                messageObject.put("token", userFcmToken);
                messageObject.put("notification", notificationObject);
                message.put("message", messageObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // Send the notification
            URL url = new URL(FCM_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Authorization", "Bearer " + accessToken);
            connection.setRequestProperty("Content-Type", "application/json; UTF-8");
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");

            // Send the JSON payload
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(message.toString().getBytes());
            outputStream.flush();
            outputStream.close();
            // Get the response code
            int responseCode = connection.getResponseCode();
            System.out.println("FCM Response Code: " + responseCode);

            // Handle response for better error handling
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("Notification sent successfully!");
            } else {
                System.out.println("Error sending notification. Response code: " + responseCode);
                InputStream errorStream = connection.getErrorStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(errorStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println("Error Response: " + line);
                }
                reader.close();
            }
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error while sending notification: " + e.getMessage());
        }
    }

    private class SendNotificationTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            SendNotifications();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            // Handle any UI updates here if needed
        }
    }

    // Call this method to send notifications
    public void triggerSendNotification() {
        new SendNotificationTask().execute();
    }
}








