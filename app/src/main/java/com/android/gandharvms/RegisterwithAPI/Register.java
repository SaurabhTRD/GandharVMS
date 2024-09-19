package com.android.gandharvms.RegisterwithAPI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.gandharvms.LoginWithAPI.Login;
import com.android.gandharvms.LoginWithAPI.LoginMethod;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.Menu;
import com.android.gandharvms.R;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class Register extends AppCompatActivity {

    private final int MAX_LENGTH = 10;
    EditText name, emplid, emailid, phoneno, password;
    Button register;
    TextView loginnowbtn;
    CheckBox security, weighment, sampling, production, laboratary, stores ,logistic,billing,despatch,dataentry,smallpack,industrial;
    //DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://gandharvms-default-rtdb.firebaseio.com/");
    private int setRole;
    private String token;
    private LoginMethod loginMethod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name = findViewById(R.id.name);
        emplid = findViewById(R.id.emplid);
        emailid = findViewById(R.id.emailid);
        phoneno = findViewById(R.id.phoneno);
        password = findViewById(R.id.password);

        register = findViewById(R.id.btnregister);
        loginnowbtn = findViewById(R.id.loginlink);

        security = findViewById(R.id.isSecurity);
        weighment = findViewById(R.id.isWeighment);
        sampling = findViewById(R.id.isSampling);
        production = findViewById(R.id.isProduction);
        laboratary = findViewById(R.id.isLaboratary);
        stores = findViewById(R.id.isStores);
        logistic = findViewById(R.id.isLogistic);
        billing = findViewById(R.id.isbilling);
        despatch = findViewById(R.id.isdespatch);
        dataentry = findViewById(R.id.isdataentry);
        smallpack = findViewById(R.id.isdespatchsmall);
        industrial = findViewById(R.id.isdespatchindustrial);

        loginMethod = RetroApiClient.getLoginApi();
        emailid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String email = editable.toString().trim();
                if (!isValidEmail(email)) {
                    emailid.setError("Invalid Email Format");
                } else {
                    emailid.setError(null);//clear the error
                }
            }
        });
        /*phoneno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > MAX_LENGTH) {
                    phoneno.removeTextChangedListener(this);
                    String trimmedText = editable.toString().substring(0, MAX_LENGTH);
                    phoneno.setText(trimmedText);
                    phoneno.setSelection(MAX_LENGTH); // Move cursor to the end
                    phoneno.addTextChangedListener(this);
                }else if (editable.length() < MAX_LENGTH) {
                    // Show an error message for less than 10 digits
                    phoneno.setError("Invalid format. Enter 10 digits");
                } else {
                    // Clear any previous error message when valid
                    phoneno.setError(null);
                }
            }
        });*/

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        token = task.getResult();
                    } else {
                    }
                });
        security.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    weighment.setChecked(false);
                    sampling.setChecked(false);
                    production.setChecked(false);
                    laboratary.setChecked(false);
                    stores.setChecked(false);
                    logistic.setChecked(false);
                    billing.setChecked(false);
                    despatch.setChecked(false);
                    dataentry.setChecked(false);
                    smallpack.setChecked(false);
                    industrial.setChecked(false);
                }
            }
        });

        weighment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    security.setChecked(false);
                    sampling.setChecked(false);
                    production.setChecked(false);
                    laboratary.setChecked(false);
                    stores.setChecked(false);
                    logistic.setChecked(false);
                    billing.setChecked(false);
                    despatch.setChecked(false);
                    dataentry.setChecked(false);
                    smallpack.setChecked(false);
                    industrial.setChecked(false);
                }
            }
        });

        sampling.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    weighment.setChecked(false);
                    security.setChecked(false);
                    production.setChecked(false);
                    laboratary.setChecked(false);
                    stores.setChecked(false);
                    logistic.setChecked(false);
                    billing.setChecked(false);
                    despatch.setChecked(false);
                    dataentry.setChecked(false);
                    smallpack.setChecked(false);
                    industrial.setChecked(false);
                }
            }
        });
        production.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    weighment.setChecked(false);
                    sampling.setChecked(false);
                    security.setChecked(false);
                    laboratary.setChecked(false);
                    stores.setChecked(false);
                    logistic.setChecked(false);
                    billing.setChecked(false);
                    despatch.setChecked(false);
                    dataentry.setChecked(false);
                    smallpack.setChecked(false);
                    industrial.setChecked(false);
                }
            }
        });
        laboratary.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    weighment.setChecked(false);
                    sampling.setChecked(false);
                    production.setChecked(false);
                    security.setChecked(false);
                    stores.setChecked(false);
                    logistic.setChecked(false);
                    billing.setChecked(false);
                    despatch.setChecked(false);
                    dataentry.setChecked(false);
                    smallpack.setChecked(false);
                    industrial.setChecked(false);
                }
            }
        });

        stores.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {
                if (buttonView.isChecked()) {
                    weighment.setChecked(false);
                    sampling.setChecked(false);
                    production.setChecked(false);
                    security.setChecked(false);
                    laboratary.setChecked(false);
                    logistic.setChecked(false);
                    billing.setChecked(false);
                    despatch.setChecked(false);
                    dataentry.setChecked(false);
                    smallpack.setChecked(false);
                    industrial.setChecked(false);
                }
            }
        });

        logistic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {
                if (buttonView.isChecked()) {
                    weighment.setChecked(false);
                    sampling.setChecked(false);
                    production.setChecked(false);
                    security.setChecked(false);
                    laboratary.setChecked(false);
                    stores.setChecked(false);
                    billing.setChecked(false);
                    despatch.setChecked(false);
                    dataentry.setChecked(false);
                    smallpack.setChecked(false);
                    industrial.setChecked(false);
                }
            }
        });
        billing.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {
                if (buttonView.isChecked()) {
                    weighment.setChecked(false);
                    sampling.setChecked(false);
                    production.setChecked(false);
                    security.setChecked(false);
                    laboratary.setChecked(false);
                    stores.setChecked(false);
                    logistic.setChecked(false);
                    despatch.setChecked(false);
                    dataentry.setChecked(false);
                    smallpack.setChecked(false);
                    industrial.setChecked(false);
                }
            }
        });
        dataentry.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {
                if (buttonView.isChecked()) {
                    weighment.setChecked(false);
                    sampling.setChecked(false);
                    production.setChecked(false);
                    security.setChecked(false);
                    laboratary.setChecked(false);
                    stores.setChecked(false);
                    logistic.setChecked(false);
                    despatch.setChecked(false);
                    billing.setChecked(false);
                    smallpack.setChecked(false);
                    industrial.setChecked(false);
                }
            }
        });
        despatch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {
                if (buttonView.isChecked()) {
                    weighment.setChecked(false);
                    sampling.setChecked(false);
                    production.setChecked(false);
                    security.setChecked(false);
                    laboratary.setChecked(false);
                    stores.setChecked(false);
                    logistic.setChecked(false);
                    billing.setChecked(false);
                    dataentry.setChecked(false);
                    smallpack.setChecked(false);
                    industrial.setChecked(false);
                }
            }
        });
        smallpack.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {
                if (buttonView.isChecked()) {
                    weighment.setChecked(false);
                    sampling.setChecked(false);
                    production.setChecked(false);
                    security.setChecked(false);
                    laboratary.setChecked(false);
                    stores.setChecked(false);
                    logistic.setChecked(false);
                    billing.setChecked(false);
                    dataentry.setChecked(false);
                    despatch.setChecked(false);
                    industrial.setChecked(false);
                }
            }
        });
        industrial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {
                if (buttonView.isChecked()) {
                    weighment.setChecked(false);
                    sampling.setChecked(false);
                    production.setChecked(false);
                    security.setChecked(false);
                    laboratary.setChecked(false);
                    stores.setChecked(false);
                    logistic.setChecked(false);
                    billing.setChecked(false);
                    dataentry.setChecked(false);
                    despatch.setChecked(false);
                    smallpack.setChecked(false);
                }
            }
        });




        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertRegistrationDetails();
            }
        });
        loginnowbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void InsertRegistrationDetails() {
        final String nameTxt = name.getText().toString();
        final String emplidTxt = emplid.getText().toString();
        final String emailidTxt = emailid.getText().toString();
        final String phoneTxt = phoneno.getText().toString();
        final String passwordTxt = password.getText().toString();

        if (security.isChecked()) {
            setRole = 1;
        } else if (weighment.isChecked()) {
            setRole = 2;
        } else if (sampling.isChecked()) {
            setRole = 3;
        } else if (production.isChecked()) {
            setRole = 5;
        } else if (laboratary.isChecked()) {
            setRole = 4;
        } else if (stores.isChecked()) {
            setRole = 6;
        } else if (logistic.isChecked()) {
            setRole = 10;
        } else if (billing.isChecked()) {
            setRole = 12;
        } else if (despatch.isChecked()) {
            setRole = 13;
        } else if (dataentry.isChecked()) {
            setRole = 14;
        } else if (smallpack.isChecked()) {
            setRole = 15;
        } else if (industrial.isChecked()) {
            setRole = 17;
        }
        if (TextUtils.isEmpty(nameTxt)) {
            name.setError("Name is Required");
            return;
        }
        if (TextUtils.isEmpty(emplidTxt)) {
            emplid.setError("Employee Id is Required");
            return;
        }
        if (TextUtils.isEmpty(emailidTxt)) {
            emailid.setError("Email Id is Required");
            return;
        }
        if (TextUtils.isEmpty(phoneTxt)) {
            phoneno.setError("Phone No is Required");
            return;
        }
        if (TextUtils.isEmpty(passwordTxt)) {
            password.setError("Passsword is Required");
            return;
        }
        if (passwordTxt.length() < 6) {
            password.setError("Password Must be >= 6 Characters");
            return;
        }

        //Checkbox Validation
        if (!(security.isChecked() || weighment.isChecked() || sampling.isChecked() || production.isChecked() || laboratary.isChecked() || stores.isChecked()||
                logistic.isChecked() || billing.isChecked() ||despatch.isChecked() || dataentry.isChecked() || smallpack.isChecked() ||industrial.isChecked())) {
            Toasty.warning(Register.this, "Please Select the Department", Toast.LENGTH_SHORT).show();
        } else {
            RegRequestModel regmodel = new RegRequestModel(nameTxt, emplidTxt, emailidTxt, phoneTxt, passwordTxt, token, setRole, nameTxt);
            Call<Boolean> call = loginMethod.postregData(regmodel);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body() != null && response.body()==true) {
                        Log.d("Registration", "Response Body: " + response.body());
                        Toasty.success(Register.this, "User Register succesfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Register.this, Login.class));
                        finish();
                    }else {
                        // Registration failed
                        Log.e("Registration", "Unexpected response code: " + response.code());
                        Toasty.error(Register.this, "Unexpected response code..!", Toast.LENGTH_SHORT).show();
                        Log.e("Registration", "Registration failed. Response: " + response.body());
                        Toasty.error(Register.this, "Registration failed..!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
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
                    Toasty.error(Register.this, "Registration failed..!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private boolean isValidEmail(CharSequence target) {
        return (Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}