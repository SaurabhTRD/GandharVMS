package com.android.gandharvms.Util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class ConnectivityReceiverNew extends BroadcastReceiver {
    private ConnectivityReceiverListener listener;
    //private String username=Global_Var.getInstance().UserId;
    //private String password=Global_Var.getInstance().Password;
    //private int species,roleid;
    //LogInActivity logInActivity= new LogInActivity();
    //=Global_Var.getInstance().SpeciesId;
    //private int =Global_Var.getInstance().RoleId;
    //private String username,password;


    public ConnectivityReceiverNew(ConnectivityReceiverListener listener) {
        this.listener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isConnected = isConnected(context);

        if (listener != null) {
            listener.onNetworkConnectionChanged(isConnected);
        }
        /*species=Global_Var.getInstance().SpeciesId;
        roleid=Global_Var.getInstance().RoleId;
        if (isConnected) {
            Log.d("ConnectivityReceiver", "No Internet Connection - Fetching Local Data");
            //DatabaseHelper databaseHelper = new DatabaseHelper(context);
            // Check if username and password are available (Modify according to your logic)
            if (username != null && password != null) {
                Global_Var.getInstance().SpeciesId=species;
                Global_Var.getInstance().RoleId=roleid;
                Global_Var.getInstance().speciesdropdown=true;
                if (username != null && password != null) {
                    OfflineLoginUtils.loginUserViaAPI(context, username, password, species, roleid);
                }
                //logInActivity.loginUserViaAPI(username, password);
                *//*boolean isUserValid = databaseHelper.checkUsernamePassword(username, password);
                if (!isUserValid) {
                    Log.e("ConnectivityReceiver", "No valid offline user found!");
                } else {
                    Log.d("ConnectivityReceiver", "Offline user data loaded successfully.");
                }*//*
            }
        }*/
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public interface ConnectivityReceiverListener {
        void onNetworkConnectionChanged(boolean isConnected);
    }
}
