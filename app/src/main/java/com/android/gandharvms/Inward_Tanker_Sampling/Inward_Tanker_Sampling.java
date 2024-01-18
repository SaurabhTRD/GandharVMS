package com.android.gandharvms.Inward_Tanker_Sampling;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class Inward_Tanker_Sampling extends AppCompatActivity {

    private final int MAX_LENGTH = 10;
    EditText etssignofproduction, etinvoiceno, etinvoicedate, materialname, etsqty1, suomqty, snetweight, suomnetwt, svesselname, sstoragetn,
            ssuppliername, etscustname, etsdate, etvehicleno;
    Button etssubmit;
    Button view;
    FirebaseFirestore sadbroot;
    Calendar currentTime = Calendar.getInstance();
    int currentHour = currentTime.get(Calendar.HOUR_OF_DAY);
    int currentMinute = currentTime.get(Calendar.MINUTE);

    Date currentDate = Calendar.getInstance().getTime();
    String dateFormatPattern = "dd-MM-yyyy";
    SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatPattern, Locale.getDefault());
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://gandharvms-default-rtdb.firebaseio.com/");
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inward_tanker_sampling);

        //Send Notification to all
        FirebaseMessaging.getInstance().subscribeToTopic(token);

        etssignofproduction = findViewById(R.id.etreciving);
        etinvoiceno = findViewById(R.id.etsubmitted);
        etsdate = findViewById(R.id.etsdate);
        etvehicleno = findViewById(R.id.etvehicleno);

        view = findViewById(R.id.samplingview);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Inward_Tanker_Sampling.this, Inward_Tanker_saampling_View_data.class));
            }
        });

        // timepicker
        etssignofproduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hours = calendar.get(Calendar.HOUR_OF_DAY);
                int mins = calendar.get(Calendar.MINUTE);
                String formattedTime = String.format(Locale.getDefault(), "%02d:%02d", currentHour, currentMinute);
                etssignofproduction.setText(formattedTime);

            }
        });

        etinvoiceno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hours = calendar.get(Calendar.HOUR_OF_DAY);
                int mins = calendar.get(Calendar.MINUTE);
                String formattedTime = String.format(Locale.getDefault(), "%02d:%02d", currentHour, currentMinute);
                etinvoiceno.setText(formattedTime);

            }
        });

        String formattedDate = dateFormat.format(currentDate);
        etsdate.setText(formattedDate);
        etssubmit = findViewById(R.id.etssubmit);
        sadbroot = FirebaseFirestore.getInstance();

        etssubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sainsertdata();
            }
        });
    }

    public void makeNotification(String vehicleNumber, String outTime) {
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Assume you have a user role to identify the specific role
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                        String specificRole = "Laboratory";
                        // Get the value of the "role" node                    ;
                        if (issue.toString().contains(specificRole)) {
                            //getting the token
                            token = Objects.requireNonNull(issue.child("token").getValue()).toString();
                            FcmNotificationsSender notificationsSender = new FcmNotificationsSender(token,
                                    "Inward Tanker Sampling Process Done..!",
                                    "Vehicle Number:-" + vehicleNumber + " has completed Sampling process at " + outTime,
                                    getApplicationContext(), Inward_Tanker_Sampling.this);
                            notificationsSender.SendNotifications();
                        }
                    }
                } else {
                    // Handle the case when the "role" node doesn't exist
                    Log.d("Role Data", "Role node doesn't exist");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors here
                Log.e("Firebase", "Error fetching role data: " + databaseError.getMessage());
            }
        });
    }

    public void sainsertdata() {
        String etreciving = etssignofproduction.getText().toString().trim();
        String etsubmitted = etinvoiceno.getText().toString().trim();
        String date = etsdate.getText().toString().trim();
        String vehiclenumber = etvehicleno.getText().toString().trim();
        if (vehiclenumber.isEmpty() || etreciving.isEmpty() || date.isEmpty() || etsubmitted.isEmpty()) {
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
        } else {
            Map<String, String> saitems = new HashMap<>();
            saitems.put("Sample_Reciving_Time", etssignofproduction.getText().toString().trim());
            saitems.put("Sample_Submitted_Time", etinvoiceno.getText().toString().trim());
            saitems.put("Date", etsdate.getText().toString().trim());
            saitems.put("Vehicle_Number", etvehicleno.getText().toString().trim());
            makeNotification(etvehicleno.getText().toString(), etinvoiceno.getText().toString());
            sadbroot.collection("Inward Tanker Sampling").add(saitems)

                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {

                            etssignofproduction.setText("");
                            etinvoiceno.setText("");
                            etsdate.setText("");
                            etvehicleno.setText("");
                            Toast.makeText(Inward_Tanker_Sampling.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
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