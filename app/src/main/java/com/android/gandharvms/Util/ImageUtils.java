package com.android.gandharvms.Util;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.io.IOException;

public class ImageUtils {

    // The insertImage() method from the previous code snippet

    public static String getImagePath(ContentResolver contentResolver, Uri imageUri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = contentResolver.query(imageUri, projection, null, null, null);
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    return cursor.getString(columnIndex);
                }
            } finally {
                cursor.close();
            }
        }
        return null;
    }

    public static Uri insertImage(ContentResolver contentResolver, Bitmap bitmap, String title, String description) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, title);
        values.put(MediaStore.Images.Media.DESCRIPTION, description);

        Uri imageUri = null;
        try {
            imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            if (imageUri != null) {
                contentResolver.openOutputStream(imageUri).close(); // Create an empty image file
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, contentResolver.openOutputStream(imageUri));
                return imageUri;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static boolean deleteImage(Context context, Uri imageUri) {
        ContentResolver contentResolver = context.getContentResolver();
        // First, delete the image from the MediaStore
        int deletedRows = contentResolver.delete(imageUri, null, null);

        // Second, delete the actual file associated with the image
        String imagePath = getImagePathByContext(context, imageUri);
        if (imagePath != null) {
            File imageFile = new File(imagePath);
            if (imageFile.exists()) {
                boolean deleted = imageFile.delete();
                return deleted && (deletedRows > 0);
            }
        }
        return false;
    }
    private static String getImagePathByContext(Context context, Uri imageUri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(imageUri, projection, null, null, null);
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    return cursor.getString(columnIndex);
                }
            } finally {
                cursor.close();
            }
        }
        return null;
    }
}