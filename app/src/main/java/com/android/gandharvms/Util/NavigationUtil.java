package com.android.gandharvms.Util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class NavigationUtil {
    public static void navigateAndClear(Context context, Class<?> destination) {
        Intent intent = new Intent(context, destination);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

        if (context instanceof Activity) {
            ((Activity) context).finish();
        }
    }
}
