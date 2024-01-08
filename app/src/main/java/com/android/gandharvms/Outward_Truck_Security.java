package com.android.gandharvms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Outward_Truck_Security extends AppCompatActivity {

    EditText intime,serialnumber,vehiclenumber,lr,transporter,place,mobilenuumber;
    Button submit;
    FirebaseFirestore dbroot;
    TimePickerDialog tpicker;
    Calendar calendar = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outward_truck_security);

        intime = findViewById(R.id.etintime);
        serialnumber = findViewById(R.id.etserialnumber);
        vehiclenumber = findViewById(R.id.etvehicleno);
        lr = findViewById(R.id.etlr);
        transporter= findViewById(R.id.ettranseporter);
        place = findViewById(R.id.etplace);
        mobilenuumber = findViewById(R.id.etmobilenumber);

        submit = findViewById(R.id.etssubmit);
        dbroot= FirebaseFirestore.getInstance();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert();
            }
        });


        intime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hours = calendar.get(Calendar.HOUR_OF_DAY);
                int mins = calendar.get(Calendar.MINUTE);
                tpicker = new TimePickerDialog(Outward_Truck_Security.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        c.set(Calendar.MINUTE,minute);

                        // Set the formatted time to the EditText
                        intime.setText(hourOfDay +":"+ minute );
                    }
                },hours,mins,false);
                tpicker.show();
            }
        });
    }
    public void insert(){
//        intime,serialnumber,vehiclenumber,lr,transporter,place,mobilenuumber;

        String etintime = intime.getText().toString().trim();
        String etserialnumber = serialnumber.getText().toString().trim();
        String etvehiclnumber = vehiclenumber.getText().toString().trim();
        String etlr = lr.getText().toString().trim();
        String ettransporter = transporter.getText().toString().trim();
        String etplace = place.getText().toString().trim();
        String etmobilenumber = mobilenuumber.getText().toString().trim();

        if (etintime.isEmpty()||etserialnumber.isEmpty()||etvehiclnumber.isEmpty()||etlr.isEmpty()||
        ettransporter.isEmpty()||etplace.isEmpty()||etmobilenumber.isEmpty()){
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
        }else {

            Map<String,String>items = new HashMap<>();

            items.put("In_Time",intime.getText().toString().trim());
            items.put("Serial_Number",serialnumber.getText().toString().trim());
            items.put("Vehicle_Number",vehiclenumber.getText().toString().trim());
            items.put("LR",lr.getText().toString().trim());
            items.put("Transporter",transporter.getText().toString().trim());
            items.put("Place",place.getText().toString().trim());
            items.put("Mobile_Number",mobilenuumber.getText().toString().trim());

            dbroot.collection("Outward Truck Security").add(items)
                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            Toast.makeText(Outward_Truck_Security.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                        }
                    });
        }


    }
}