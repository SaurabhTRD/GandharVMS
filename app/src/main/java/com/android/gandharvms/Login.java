package com.android.gandharvms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.gandharvms.LoginWithAPI.LoginMethod;
import com.android.gandharvms.LoginWithAPI.RequestModel;
import com.android.gandharvms.LoginWithAPI.ResponseModel;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://gandharvms-default-rtdb.firebaseio.com/");

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
                                if (resModel != null) {
                                    if (password == null && empid == null) {
                                        Toast.makeText(Login.this, "Incorrect Password or EmpId", Toast.LENGTH_SHORT).show();
                                    } else if (password.equals(passwordTxt) && empid.equals(emplidTxt)) {
                                        Toast.makeText(Login.this, "Succesfully Logged In ..!", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(Login.this, Menu.class));
                                        finish();
                                    } else {
                                        Toast.makeText(Login.this, "Login Failed. Please try again.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<List<ResponseModel>> call, Throwable t) {
                            Toast.makeText(Login.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
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

    /*private void autoLoggingFunc() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String check = sharedPreferences.getString("EMPLID_KEY", "");
        if (!check.equals(emplidTxt))
        {
            Intent intent= new Intent(getApplicationContext(),Menu.class);
            startActivity(intent);2
            finish();
        }
    }*/
}