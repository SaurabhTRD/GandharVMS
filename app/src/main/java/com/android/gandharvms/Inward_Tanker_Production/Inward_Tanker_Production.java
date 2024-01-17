package com.android.gandharvms.Inward_Tanker_Production;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.android.gandharvms.Inward_Tanker_Security.In_Tanker_Security_list;
import com.android.gandharvms.Inward_Tanker_Security.Inward_Tanker_Security;
import com.android.gandharvms.Inward_Tanker_Weighment.Inward_Tanker_Weighment;
import com.android.gandharvms.Menu;
import com.android.gandharvms.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.Timestamp;
import com.google.firebase.messaging.FirebaseMessaging;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Inward_Tanker_Production extends AppCompatActivity {

    EditText etint, etserno,etreq, ettankno, etconbyop, tanknoun, etconunloadDateTime, etMaterial, etVehicleNumber;
    /*  Button viewdata;*/
    Button prosubmit;
    FirebaseFirestore prodbroot;
    DatePickerDialog picker;
    final Calendar calendar = Calendar.getInstance();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-YYYY, HH:mm:ss");
    Timestamp timestamp = new Timestamp(calendar.getTime());

    TimePickerDialog tpicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inward_tanker_production);
        //Send Notification to all
        //Send Notification to all
        FirebaseMessaging.getInstance().subscribeToTopic("cZO18soMQ7K8D3CkVdUA9i:APA91bFdQxHnryIGp1ZSaMMZggvyev0DbgwMqvuHOPzdi_CnUGJeGds6aIK9_2cZdb6e246Ju5a6erKkwkWHQmUKeYTYm_tO2NEsM-GiPsWlqFQK3E--ArUx99-UwGtsnZHgm7_jAFSs");

        etMaterial = findViewById(R.id.etMaterial);
        etVehicleNumber = findViewById(R.id.etvehicleNumber);
        etserno=findViewById(R.id.etpserialnumber);
        etint = findViewById(R.id.etintime);
        etreq = findViewById(R.id.etreq);
        ettankno = findViewById(R.id.ettankno);
        etconbyop = findViewById(R.id.etconbyop);
        tanknoun = findViewById(R.id.tanknoun);
        etconunloadDateTime = findViewById(R.id.etconunloadDateTime);

        prosubmit = findViewById(R.id.prosubmit);


        //datetimepickertesting
        etconunloadDateTime = findViewById(R.id.etconunloadDateTime);

        etconunloadDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                // Array of month abbreviations
                String[] monthAbbreviations = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
                picker = new DatePickerDialog(Inward_Tanker_Production.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Use the month abbreviation from the array
                        String monthAbbreviation = monthAbbreviations[month];
                        // etdate.setText(dayOfMonth + "/" + monthAbbreviation + "/" + year);
                        etconunloadDateTime.setText(dateFormat.format(calendar.getTime()));
                    }
                }, year, month, day);
                picker.show();
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

        etVehicleNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    FetchVehicleDetails(etVehicleNumber.getText().toString().trim());
                }
            }

        });

        prodbroot = FirebaseFirestore.getInstance();

        prosubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proinsertdata();
            }
        });

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
            Map<String, Object> proitems = new HashMap<>();
            proitems.put("In_Time", etint.getText().toString().trim());
            proitems.put("Req_to_unload", etreq.getText().toString().trim());
            proitems.put("Tank_Number_Request", ettankno.getText().toString().trim());
            proitems.put("confirm_unload", etconbyop.getText().toString().trim());
            proitems.put("Tank_Number", tanknoun.getText().toString().trim());
            proitems.put("con_unload_DT", timestamp);
            proitems.put("outTime", outTime);
            proitems.put("Material", etMaterial.getText().toString().trim());
            proitems.put("Vehicle_Number", etVehicleNumber.getText().toString().trim());
            proitems.put("Serial_Number",etserno.getText().toString().trim());

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
                            etserno.setText("");
                            etint.requestFocus();
                            etint.callOnClick();
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

    public void FetchVehicleDetails(@NonNull String VehicleNo) {
        CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Inward Tanker Security");
        String searchText = VehicleNo.trim();
        Query query = collectionReference.whereEqualTo("vehicalnumber", searchText)
                .whereNotEqualTo("intime", "");
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    int totalCount = task.getResult().size();
                    if (totalCount == 0) {
                        etMaterial.setText("");
                        etVehicleNumber.requestFocus();
                        etserno.setText("");
                        etconunloadDateTime.setText("");
                        Toast.makeText(Inward_Tanker_Production.this, "Vehicle Number not Available for Weighment", Toast.LENGTH_SHORT).show();
                    } else {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            In_Tanker_Security_list obj = document.toObject(In_Tanker_Security_list.class);
                            // Check if the object already exists to avoid duplicates
                            if (totalCount > 0) {
                                etserno.setText(obj.getSerialNumber());
                                etVehicleNumber.setText(obj.getVehicalnumber());
                                etMaterial.setText(obj.getMaterial());
                                etconunloadDateTime.setText(dateFormat.format(obj.getDate().toDate()));
                                etint.requestFocus();
                                etint.callOnClick();
                            }
                        }
                    }
                } else {
                    Log.w("FirestoreData", "Error getting documents.", task.getException());
                }
            }
        });
    }
}