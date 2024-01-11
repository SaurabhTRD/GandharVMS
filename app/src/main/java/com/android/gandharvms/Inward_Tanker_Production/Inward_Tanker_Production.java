package com.android.gandharvms.Inward_Tanker_Production;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.gandharvms.FcmNotificationsSender;
import com.android.gandharvms.Inward_Tanker;
import com.android.gandharvms.Menu;
import com.android.gandharvms.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Inward_Tanker_Production extends AppCompatActivity {

    EditText etint, etreq, ettankno, etconbyop, tanknoun, etconunloadDateTime, etMaterial, etVehicleNumber;
    /*  Button viewdata;*/
    Button prosubmit;
    FirebaseFirestore prodbroot;
    EditText datetimeTextview;
    Calendar calendar = Calendar.getInstance();
    TimePickerDialog tpicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inward_tanker_production);
        //Send Notification to all
        //Send Notification to all
        FirebaseMessaging.getInstance().subscribeToTopic("cZO18soMQ7K8D3CkVdUA9i:APA91bFdQxHnryIGp1ZSaMMZggvyev0DbgwMqvuHOPzdi_CnUGJeGds6aIK9_2cZdb6e246Ju5a6erKkwkWHQmUKeYTYm_tO2NEsM-GiPsWlqFQK3E--ArUx99-UwGtsnZHgm7_jAFSs");

        etMaterial = (EditText) findViewById(R.id.etMaterial);
        etVehicleNumber = (EditText) findViewById(R.id.etvehicleNumber);
        etint = (EditText) findViewById(R.id.etintime);
        etreq = (EditText) findViewById(R.id.etreq);
        ettankno = (EditText) findViewById(R.id.ettankno);
        etconbyop = (EditText) findViewById(R.id.etconbyop);
        tanknoun = (EditText) findViewById(R.id.tanknoun);
        etconunloadDateTime = (EditText) findViewById(R.id.etconunloadDateTime);

        prosubmit = (Button) findViewById(R.id.prosubmit);


        //datetimepickertesting
        datetimeTextview = findViewById(R.id.etconunloadDateTime);

        datetimeTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
        etint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hours = calendar.get(Calendar.HOUR_OF_DAY);
                int mins = calendar.get(Calendar.MINUTE);
                tpicker = new TimePickerDialog(Inward_Tanker_Production.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        c.set(Calendar.MINUTE, minute);

                        // Set the formatted time to the EditText
                        etint.setText(hourOfDay + ":" + minute);
                    }
                }, hours, mins, false);
                tpicker.show();
            }
        });

        /*etVehicleNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > MAX_LENGTH) {
                    etVehicleNumber.removeTextChangedListener(this);
                    String trimmedText = editable.toString().substring(0, MAX_LENGTH);
                    etVehicleNumber.setText(trimmedText);
                    etVehicleNumber.setSelection(MAX_LENGTH); // Move cursor to the end
                    etVehicleNumber.addTextChangedListener(this);
                } else if (editable.length() < MAX_LENGTH) {
                    // Show an error message for less than 10 digits
                    etVehicleNumber.setError("Invalid format. Enter 10 Character. \n Vehicle No Format - ST00AA9999 OR YYBR9999AA");
                } else {
                    // Clear any previous error message when valid
                    etVehicleNumber.setError(null);
                }
            }
        });*/

        prodbroot = FirebaseFirestore.getInstance();

        prosubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proinsertdata();
            }
        });

    }

    // Time and date for confirm unload
    public void showDatePicker() {

        DatePickerDialog datePickerDialog = new DatePickerDialog(Inward_Tanker_Production.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                showTimePicker();
            }
        },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();

    }

    public void showTimePicker() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(Inward_Tanker_Production.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);

                handleDateTimeSelection(calendar.getTime());

            }
        }, calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
        );
        timePickerDialog.show();
    }

    public void handleDateTimeSelection(java.util.Date dateTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault());
        etconunloadDateTime.setText(dateFormat.format(dateTime.getTime()));

    }

    public void makeNotification(String vehicleNo, String outTime) {
        FcmNotificationsSender notificationsSender = new FcmNotificationsSender("cZO18soMQ7K8D3CkVdUA9i:APA91bFdQxHnryIGp1ZSaMMZggvyev0DbgwMqvuHOPzdi_CnUGJeGds6aIK9_2cZdb6e246Ju5a6erKkwkWHQmUKeYTYm_tO2NEsM-GiPsWlqFQK3E--ArUx99-UwGtsnZHgm7_jAFSs",
                "Inward Tanker Production Process Done..!",
                "Vehicle Number:-" + vehicleNo + " has completed Laboratory process at " + outTime,
                getApplicationContext(), Inward_Tanker_Production.this);
        notificationsSender.SendNotifications();
    }

    public void btn_clicktoViewLabReport(View view) {
        Intent intent = new Intent(this, Tanker_Production_Lab_Report_Data.class);
        startActivity(intent);
    }

    public void weViewclick(View view) {
        Intent intent = new Intent(this, Inward_Tanker_Production_Viewdata.class);
        startActivity(intent);
    }

    private String getCurrentTime() {
        // Get the current time
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(new Date());
    }

    public void proinsertdata() {
        String intime = etint.getText().toString().trim();
        String reqtounload = etreq.getText().toString().trim();
        String tanknumber = ettankno.getText().toString().trim();
        String confirmunload = etconbyop.getText().toString().trim();
        String tanknumbers = tanknoun.getText().toString().trim();
        String conunload = etconunloadDateTime.getText().toString().trim();
        String outTime = getCurrentTime();//Insert out Time Directly to the Database
        String material = etMaterial.getText().toString().trim();
        String vehicleNumber = etVehicleNumber.getText().toString().trim();

        if (intime.isEmpty() || reqtounload.isEmpty() || tanknumber.isEmpty() || confirmunload.isEmpty() || tanknumbers.isEmpty() || conunload.isEmpty() || material.isEmpty() || vehicleNumber.isEmpty()) {
            Toast.makeText(this, "All Fields must be filled", Toast.LENGTH_SHORT).show();
        } else {
            Map<String, String> proitems = new HashMap<>();

            proitems.put("In_Time", etint.getText().toString().trim());
            proitems.put("Req_to_unload", etreq.getText().toString().trim());
            proitems.put("Tank_Number_Request", ettankno.getText().toString().trim());
            proitems.put("confirm_unload", etconbyop.getText().toString().trim());
            proitems.put("Tank_Number", tanknoun.getText().toString().trim());
            proitems.put("con_unload_DT", etconunloadDateTime.getText().toString().trim());
            proitems.put("outTime", outTime);
            proitems.put("Material", etMaterial.getText().toString().trim());
            proitems.put("Vehicle_Number", etVehicleNumber.getText().toString().trim());


            makeNotification(etVehicleNumber.getText().toString(), outTime);
            prodbroot.collection("Inward Tanker Production").add(proitems)
                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {

                            etint.setText("");
                            etreq.setText("");
                            ettankno.setText("");
                            etconbyop.setText("");
                            tanknoun.setText("");
                            etconunloadDateTime.setText("");
                            etMaterial.setText("");
                            etVehicleNumber.setText("");

                            Toast.makeText(Inward_Tanker_Production.this, "Data Added Successfully", Toast.LENGTH_SHORT).show();


                        }
                    });
            Intent intent = new Intent(this, Inward_Tanker.class);
            startActivity(intent);


        }
    }

    public void onBackPressed() {
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
        finish();
    }

}