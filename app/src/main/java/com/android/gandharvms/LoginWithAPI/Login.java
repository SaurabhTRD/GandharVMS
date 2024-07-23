package com.android.gandharvms.LoginWithAPI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.gandharvms.Global_Var;
import com.android.gandharvms.Menu;
import com.android.gandharvms.R;
import com.android.gandharvms.RegisterwithAPI.Register;
import com.android.gandharvms.Util.AESUtils;
import com.google.firebase.BuildConfig;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;

import javax.crypto.SecretKey;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

/*import es.dmoral.toasty.Toasty;
import io.github.muddz.styleabletoast.StyleableToast;
import www.sanju.motiontoast.MotionToast;*/

public class Login extends AppCompatActivity {

    private String emplidTxt;
    private String passwordTxt;

    private LoginMethod loginMethod;

    Handler handler;
    private static final String PREFS_NAME = "MyPrefs";
//    static final String PREF_NAME = "MyPrefs";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_ENCRYPTED_PASSWORD = "password";

    public static final String pass = "password";
    private SecretKey aesKey;
    String username,encryptedPassword,password1;

    private static final String ALLOWED_VERSION = "1.0.0";
    String versionName = "1.0.0";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText Userid = findViewById(R.id.emplid);
        final EditText password = findViewById(R.id.etpassword);
        final Button login = findViewById(R.id.btnlogin);
        final TextView NotRegister = findViewById(R.id.registerlink);

        // version vise login
        String versionName = "1.0.0";
        if (!ALLOWED_VERSION.equals(versionName)) {
            Toasty.error(this, "This version is not allowed for login.", Toast.LENGTH_LONG, true).show();
            login.setEnabled(false);
            return;
        }

        loginMethod = RetroApiClient.getLoginApi();

        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, this.MODE_PRIVATE);
        username = preferences.getString(KEY_USERNAME, "");
        encryptedPassword = preferences.getString(pass, "");
//        password1 = AESUtils.decryptPassword(encryptedPassword,aesKey);
        AutoLogin(username, encryptedPassword);

//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (!username.isEmpty() && (password != null)) {
//                    AutoLogin(username, String.valueOf(password));
//                }else {
//                    Intent intent = new Intent(Login.this, Login.class);
//                    startActivity(intent);
//                    finish();
//                }
//            }
//        }, 3000);
/*
        autoLoggingFunc();
*/
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emplidTxt = Userid.getText().toString();
                passwordTxt = password.getText().toString();

                if (emplidTxt.isEmpty() || passwordTxt.isEmpty()) {
                    Toast.makeText(Login.this, "Please Enter Your UserID or Password", Toast.LENGTH_SHORT).show();
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


//                                String encryptedPassword = AESUtils.encryptPassword(password,aesKey);
                                SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                                SharedPreferences preferences1 = getSharedPreferences(pass, MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString(KEY_USERNAME, empid);
                                editor.putString(pass, password);
                                editor.apply();
//
//

                                if (resModel != null) {
                                    if (password != null && empid != null && password.equals(passwordTxt) && empid.equals(emplidTxt)) {
                                        Toasty.success(getApplicationContext(), "Succesfully Logged In ..!", Toast.LENGTH_SHORT,true).show();
                                        startActivity(new Intent(Login.this, Menu.class));
                                        finish();
                                    } else {
                                        Toasty.error(getApplicationContext(), "Login Failed. Please try again.", Toast.LENGTH_SHORT,true).show();
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
                    if (resModel != null) {
                        if (empid != null || password != null) {
                            Toasty.success(getApplicationContext(), "Succesfully Logged In ..!", Toast.LENGTH_SHORT,true).show();
                            startActivity(new Intent(Login.this, Menu.class));
                            finish();
                        } else {
                            Toasty.error(getApplicationContext(), "Login Failed. Please try again.", Toast.LENGTH_SHORT,true).show();
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
}