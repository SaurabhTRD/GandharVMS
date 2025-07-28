package com.android.gandharvms.LoginWithAPI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.gandharvms.FirebaseMessagingService;
import com.android.gandharvms.Global_Var;
import com.android.gandharvms.Menu;
import com.android.gandharvms.R;
import com.android.gandharvms.RegisterwithAPI.RegResponseModel;
import com.android.gandharvms.RegisterwithAPI.Register;
import com.android.gandharvms.Util.AESUtils;
import com.google.firebase.BuildConfig;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;
import java.util.List;

import javax.crypto.SecretKey;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    private String emplidTxt;
    private String passwordTxt;
    private static final int NOTIFICATION_PERMISSION_CODE = 123;
    private LoginMethod loginMethod;

    Handler handler;
    private static final String PREFS_NAME = "MyPrefs";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_ENCRYPTED_PASSWORD = "password";

    public static final String pass = "password";
    private SecretKey aesKey;
    String username,encryptedPassword,password1;

    private static final String ALLOWED_VERSION = Global_Var.getInstance().APKversion;
    String versionName = Global_Var.getInstance().APKversion;
    String localVersionName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        final EditText Userid = findViewById(R.id.emplid);
        final EditText password = findViewById(R.id.etpassword);
        final Button login = findViewById(R.id.btnlogin);
        final TextView NotRegister = findViewById(R.id.registerlink);

        // Check and request notification permission for Android 13 (API level 33) and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                // Request the permission
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, NOTIFICATION_PERMISSION_CODE);
            } else {
                // Permission already granted
                showNotification();
            }
        } else {
            // If Android version is lower than 13, no need to ask for permission
            showNotification();
        }

        loginMethod = RetroApiClient.getLoginApi();

        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, this.MODE_PRIVATE);
        username = preferences.getString(KEY_USERNAME, "");
        encryptedPassword = preferences.getString(pass, "");
        AutoLogin(username, encryptedPassword);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emplidTxt = Userid.getText().toString();
                passwordTxt = password.getText().toString();

                if (emplidTxt.isEmpty() || passwordTxt.isEmpty()) {
                    Toasty.warning(Login.this, "Please Enter Your UserID or Password", Toast.LENGTH_SHORT).show();
                } else {
                    RequestModel restmodel = new RequestModel();
                    restmodel.setEmpId(emplidTxt);
                    restmodel.setPassword(passwordTxt);


                    Call<List<ResponseModel>> call = loginMethod.postData(restmodel);
                    call.enqueue(new Callback<List<ResponseModel>>() {
                        @Override
                        public void onResponse(Call<List<ResponseModel>> call, Response<List<ResponseModel>> response) {
                            if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                                ResponseModel resModel = response.body().get(0);
                                String empid = resModel.getEmpId();
                                String password = resModel.getPassword();
                                Global_Var.getInstance().EmpId=empid;
                                Global_Var.getInstance().Department=resModel.getDepartment();
                                Global_Var.getInstance().Name=resModel.getEmployeeName();
                                Global_Var.getInstance().Token=resModel.getToken();
                                Global_Var.getInstance().APKversion=resModel.getApp_Version();
                                try {
                                     localVersionName = getPackageManager()
                                            .getPackageInfo(getPackageName(), 0).versionName;
                                } catch (PackageManager.NameNotFoundException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    String serverVersion = resModel.getApp_Version();
                                    String localVersion = localVersionName;

                                    if (serverVersion != null && !serverVersion.isEmpty()) {
                                        if (compareVersions(localVersion, serverVersion) < 0) {
                                            showVersionBlockDialog(serverVersion);
                                            return;
                                        }
                                    } else {
                                        Log.w("VersionCheck", "Server version missing. Skipping check.");
                                    }
                                } catch (Exception e) {
                                    Log.e("VersionCheck", "Version comparison failed: " + e.getMessage());
                                }

                                SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                                SharedPreferences preferences1 = getSharedPreferences(pass, MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString(KEY_USERNAME, empid);
                                editor.putString(pass, password);
                                editor.apply();
                                if (resModel != null) {
                                    if (password != null && empid != null && password.equals(passwordTxt) && empid.equals(emplidTxt)) {
                                        if(preferences!=null)
                                        {
                                            FirebaseMessaging.getInstance().getToken()
                                                    .addOnCompleteListener(task -> {
                                                        if (task.isSuccessful() && task.getResult() != null) {
                                                            String newToken = task.getResult();  // Declare a new final variable
                                                            String userId = resModel.getEmpId();  // Get userId from shared preferences or login session
                                                            if (userId != null) {
                                                                // Call an API to update the token in your database
                                                                sendTokenToServer(newToken, userId);
                                                            }
                                                        } else {
                                                            Log.e("FCM", "Fetching FCM token failed", task.getException());
                                                        }
                                                    });
                                        }
                                        Toasty.success(getApplicationContext(), "Succesfully Logged In ..!", Toast.LENGTH_SHORT,true).show();
                                        startActivity(new Intent(Login.this, Menu.class));
                                        finish();
                                    } else {
                                        Toasty.error(getApplicationContext(), "Login Failed. Please try again.", Toast.LENGTH_SHORT,true).show();
                                    }
                                }
                            }
                            else{
                                Toasty.error(Login.this, "Incorrect UserName or Password.", Toast.LENGTH_SHORT,true).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<List<ResponseModel>> call, Throwable t) {
                            Log.e("Retrofit", "Failure: " + t.getMessage());
                            // Check if there's a response body in case of an HTTP error
                            if (call != null && call.isExecuted() && call.isCanceled() && t instanceof HttpException) {
                                Response<?> response = ((HttpException) t).response();
                                if (response != null) {
                                    Log.e("Retrofit", "Error Response Code: " + response.code());
                                    try {
                                        Log.e("Retrofit", "Error Response Body: " + response.errorBody().string());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            Toasty.warning(Login.this,"NetWork Issues..!",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        NotRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });
    }

    public void AutoLogin(String Username, String Password) {
        loginMethod = RetroApiClient.getLoginApi();
        RequestModel restmodel = new RequestModel();
        restmodel.setEmpId(Username);
        restmodel.setPassword(Password);

        Call<List<ResponseModel>> call = loginMethod.postData(restmodel);
        call.enqueue(new Callback<List<ResponseModel>>() {
            @Override
            public void onResponse(Call<List<ResponseModel>> call, Response<List<ResponseModel>> response) {

                if (response.isSuccessful() && response.body() != null) {
                    ResponseModel resModel = response.body().get(0);
                    String empid = resModel.getEmpId();
                    String password = resModel.getPassword();

                    Global_Var.getInstance().EmpId=empid;
                    Global_Var.getInstance().Department=resModel.getDepartment();
                    Global_Var.getInstance().Name=resModel.getEmployeeName();
                    Global_Var.getInstance().Token=resModel.getToken();
                    Global_Var.getInstance().APKversion=resModel.getApp_Version();
                    try {
                        localVersionName = getPackageManager()
                                .getPackageInfo(getPackageName(), 0).versionName;
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
                    try {
                        String serverVersion = resModel.getApp_Version();
                        String localVersion = localVersionName;

                        if (serverVersion != null && !serverVersion.isEmpty()) {
                            if (compareVersions(localVersion, serverVersion) < 0) {
                                showVersionBlockDialog(serverVersion);
                                return;
                            }
                        } else {
                            Log.w("VersionCheck", "Server version missing. Skipping check.");
                        }
                    } catch (Exception e) {
                        Log.e("VersionCheck", "Version comparison failed: " + e.getMessage());
                    }
                    if (resModel != null) {
                        if (empid != null || password != null) {
                            Toasty.success(getApplicationContext(), "Succesfully Logged In ..!", Toast.LENGTH_SHORT,true).show();
                            startActivity(new Intent(Login.this, Menu.class));
                            finish();
                        } else {
                            //Toasty.error(getApplicationContext(), "Login Failed. Please try again.", Toast.LENGTH_SHORT,true).show();
                        }
                    }
                }


            }

            @Override
            public void onFailure(Call<List<ResponseModel>> call, Throwable t) {
                Log.e("Retrofit", "Failure: " + t.getMessage());
                // Check if there's a response body in case of an HTTP error
                if (call != null && call.isExecuted() && call.isCanceled() && t instanceof HttpException) {
                    Response<?> response = ((HttpException) t).response();
                    if (response != null) {
                        Log.e("Retrofit", "Error Response Code: " + response.code());
                        try {
                            Log.e("Retrofit", "Error Response Body: " + response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                Toasty.warning(Login.this,"NetWork Issues..!",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendTokenToServer(String token, String empId) {
        loginMethod = RetroApiClient.getLoginApi();
        RegResponseModel regResponse = new RegResponseModel();
        regResponse.EmpId = empId;
        regResponse.Token = token;
        regResponse.UpdatedBy = Global_Var.getInstance().Name;

        Call<Boolean> call = loginMethod.updateusertokenbyempid(regResponse);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful() && response.body() != null && response.body()) {
                    Log.d("FCM", "Token successfully updated on server.");
                    Toasty.success(Login.this, "Token updated successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Login.this, Menu.class));
                } else {
                    Log.e("FCM", "Unexpected response code: " + response.code());
                    Toasty.error(Login.this, "Unexpected response code.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.e("FCM", "Token update failed: " + t.getMessage());

                if (t instanceof HttpException) {
                    Response<?> errorResponse = ((HttpException) t).response();
                    if (errorResponse != null) {
                        try {
                            Log.e("FCM", "Error response: " + errorResponse.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                Toasty.error(Login.this, "Token update failed.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == NOTIFICATION_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                Toasty.success(this, "Notification Permission Granted", Toast.LENGTH_SHORT).show();
                showNotification();
            } else {
                // Permission denied
                Toasty.warning(this, "Notification Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showVersionBlockDialog(String serverVersion) {
        new AlertDialog.Builder(Login.this)
                .setTitle("Update Required")
                .setMessage("You are using version " +localVersionName+
                        "\nRequired version: " + serverVersion +
                        "\nPlease Update the latest version to continue.")
                .setCancelable(false)
                .setPositiveButton("Ok", (dialog, which) -> {
                    dialog.dismiss();
                    finishAffinity();
                    System.exit(0);
                })
                .show();
    }

    public int compareVersions(String v1, String v2) {
        String[] arr1 = v1.split("\\.");
        String[] arr2 = v2.split("\\.");
        int len = Math.max(arr1.length, arr2.length);

        for (int i = 0; i < len; i++) {
            int val1 = i < arr1.length ? Integer.parseInt(arr1[i]) : 0;
            int val2 = i < arr2.length ? Integer.parseInt(arr2[i]) : 0;

            if (val1 < val2) return -1; // v1 < v2
            if (val1 > val2) return 1;  // v1 > v2
        }
        return 0; // equal
    }
    // Example function that triggers a notification (you can implement your notification here)
    private void showNotification() {
        // Your code to show notification (e.g., using NotificationManager or Firebase)
        Toasty.success(this, "Notifications are enabled", Toast.LENGTH_SHORT).show();
    }
}