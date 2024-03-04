package com.android.gandharvms.LoginWithAPI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText Userid = findViewById(R.id.emplid);
        final EditText password = findViewById(R.id.etpassword);
        final Button login = findViewById(R.id.btnlogin);
        final TextView NotRegister = findViewById(R.id.registerlink);

        loginMethod = RetroApiClient.getLoginApi();
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
}