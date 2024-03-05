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

    // @Override
    /*protected String doInBackground1(Void... params) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL("https://gandhar.azurewebsites.net/api/Common/MediaUploadImage"); // Replace with your actual API endpoint URL
            connection = (HttpURLConnection) url.openConnection();

            // Set connection properties
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=---------------------------974767299852498929531610575");
            connection.setRequestProperty("UniqueId", fileName);

            // Enable input and output streams
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.connect();
            // Create output stream for sending data
            DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeBytes("Content-Disposition: form-data; name=\"file\"; filename=\"" + fileName + "\"\r\n");
            outputStream.writeBytes("Content-Type: application/octet-stream\r\n\r\n");
            outputStream.write(image);
            outputStream.writeBytes("\r\n");
            outputStream.writeBytes("-----------------------------974767299852498929531610575--\r\n");

            // Get the server response
            InputStream inputStream = connection.getInputStream();
            ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                resultStream.write(buffer, 0, length);
            }

            // Close streams
            outputStream.flush();
            outputStream.close();
            inputStream.close();

            // Convert the result to a string
            String result = resultStream.toString("UTF-8");

            // Disconnect the connection
            connection.disconnect();

            return result;
        } catch (IOException e) {
            assert connection != null;
            connection.disconnect();
            e.printStackTrace();
            return null;
        }
    }*/
    @Override
    protected String doInBackground(String... params) {
        String uploadUrl = "https://gandhar.azurewebsites.net/api/Common/Upload"; // Replace with your actual API endpoint
        String imagePath = path ; // The path to the image file on the device

        try {
            URL url = new URL(uploadUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
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
