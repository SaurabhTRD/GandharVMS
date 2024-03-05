package com.android.gandharvms.Util;

import android.os.AsyncTask;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MultipartTask extends AsyncTask<String, Void, String> {

    private byte[] image;
    private String fileName;
    private String path;

    public MultipartTask(byte[] image, String fileName, String path) {
        this.image = image;
        this.fileName = fileName;
        this.path = path;
    }
    @Override
    protected String doInBackground(String... params) {
        String uploadUrl = "https://gandhar.azurewebsites.net/api/Common/Upload"; // Replace with your actual API endpoint
        String imagePath = path ; // The path to the image file on the device

        try {
            URL url = new URL(uploadUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("UniqueId", fileName);
            connection.setDoOutput(true);
            try (OutputStream outputStream = connection.getOutputStream()) {
                outputStream.write(image);
                outputStream.flush();
            }
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Image uploaded successfully
                // Handle the response if needed
            } else {
                // Image upload failed, handle the error
            }

            connection.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
    @Override
    protected void onPostExecute(String result) {
        // Handle the result (response) from the server
        if (result != null){
            //display Status
        }
        else {
            // Handle the error
        }
    }
}
