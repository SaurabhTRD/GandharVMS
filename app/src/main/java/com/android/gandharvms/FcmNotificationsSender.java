package com.android.gandharvms;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;

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
    private static final String FCM_URL = "https://fcm.googleapis.com/v1/projects/notification-33a89/messages:send";
    private static final String[] SCOPES = {"https://www.googleapis.com/auth/firebase.messaging"};
    // static final String CREDENTIAL_PATH = "notification-33a89-firebase-adminsdk-9i10r-8c1d4001e6.json"; // Update the path
    String userFcmToken, title, body;
    Context mContext;
    Activity mActivity;

    public FcmNotificationsSender(String userFcmToken, String title, String body, Context mContext, Activity mActivity) {
        this.userFcmToken = "d44t-Wh4QGS5BXof7-t5UV:APA91bEgNuzgdT2gjim4h920cLhZLLUuf30S-ypiW8JGIhFAVE3S6m556YpPVU4NR4v68dw8mymQ_SoaBOnIhjKnpu__Q7RFlV-Z1aILAcjGKMJookDzi_Zwi8H4N2Jfs1aPwuByIkSz";
        this.title = title;
        this.body = body;
        this.mContext = mContext;
        this.mActivity = mActivity;
    }

    public void SendNotifications() {
        try {
            AssetManager assetManager = mContext.getAssets();
            InputStream serviceAccount = assetManager.open("notification-33a89-firebase-adminsdk-9i10r-8c1d4001e6.json");

            GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount).createScoped(Collections.singletonList(SCOPES[0]));
            credentials.refreshIfExpired();
            String accessToken = credentials.getAccessToken().getTokenValue();
            // Build the notification message JSON
            JSONObject message = new JSONObject();
            JSONObject notification = new JSONObject();
            JSONObject messageObject = new JSONObject();

            try {
                notification.put("title", title);
                notification.put("body", body);
                notification.put("icon", R.drawable.ic_notifications); // icon name as string
                messageObject.put("token", userFcmToken); // token for the recipient device
                messageObject.put("notification", notification);
                message.put("message", messageObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // Obtain the access token
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
    /*String userFcmToken,title,body;
    Context mContext;
    Activity mActivity;
    private RequestQueue requestQueue;
    private final String postUrl = "https://fcm.googleapis.com/fcm/send";
    private final String fcmServerKey ="AAAAs-mtD6k:APA91bGpQnKGHXYCNXbvzIIEYRHFzsZgvGNegY91-snrcmHPzNjgr0EtSOumq6G7sX4tGw6z6TvVBpVAzgZHLuFY2UOq-vwQpksf4rFq5ozpjM56ninXsky5oKiuhAzpmrWK7-jH_fwL";
    public FcmNotificationsSender(String userFcmToken, String title, String body, Context mContext,Activity mActivity) {
        this.userFcmToken = userFcmToken;
        this.title = title;
        this.body = body;
        this.mContext = mContext;
        this.mActivity = mActivity;
    }
    public void SendNotifications() {
        requestQueue = Volley.newRequestQueue(mActivity);
        JSONObject mainObj = new JSONObject();
        try {
            mainObj.put("to", userFcmToken);
            JSONObject notiObject = new JSONObject();
            notiObject.put("title", title);
            notiObject.put("body", body);
            notiObject.put("icon", R.drawable.ic_notifications); // enter icon that exists in drawable only
            mainObj.put("notification", notiObject);
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, postUrl, mainObj, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    // code run is got response
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // code run is got error
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> header = new HashMap<>();
                    header.put("content-type", "application/json");
                    header.put("authorization", "key=" + fcmServerKey);
                    return header;
                }
            };
            requestQueue.add(request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }*/

}








