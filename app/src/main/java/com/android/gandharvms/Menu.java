package com.android.gandharvms;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.gandharvms.LoginWithAPI.Login;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.NotificationAlerts.CalendarNotificationDatabaseHelper;
import com.android.gandharvms.NotificationAlerts.NotificationAccessHelper;
import com.android.gandharvms.NotificationAlerts.NotificationAlertsInterface;
import com.android.gandharvms.NotificationAlerts.NotificationCommonfunctioncls;
import com.android.gandharvms.NotificationAlerts.NotificationListActivity;
import com.android.gandharvms.NotificationAlerts.NotificationReceiverService;
import com.android.gandharvms.submenu.Submenu_Outward_Truck;
import com.android.gandharvms.submenu.Submenu_outward_tanker;
import com.android.gandharvms.submenu.submenu_Inward_Tanker;
import com.android.gandharvms.submenu.submenu_Inward_Truck;
import com.google.firebase.BuildConfig;

import java.io.IOException;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class Menu extends NotificationCommonfunctioncls {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setupHeader();
    }

    public void Inward_Tanker(View view) {
        Global_Var.getInstance().MenuType="IT";
        Intent intent = new Intent(this, submenu_Inward_Tanker.class);
        startActivity(intent);
    }

    public void Inward_process_Truckclick(View view) {
        Global_Var.getInstance().MenuType="IR";
        Intent intent = new Intent(this, submenu_Inward_Truck.class);
        startActivity(intent);
    }

    public void Outward_process_Tankerclick(View view) {
        Global_Var.getInstance().MenuType="OT";
        Intent intent = new Intent(this, Submenu_outward_tanker.class);
        startActivity(intent);
    }

    public void Outward_process_Truckclick(View view) {
        Global_Var.getInstance().MenuType="OR";
        Intent intent = new Intent(this, Submenu_Outward_Truck.class);
        startActivity(intent);
    }
}
