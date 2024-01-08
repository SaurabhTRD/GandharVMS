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

public class OutwardOut_Tanker_Weighment extends AppCompatActivity {

    EditText intime,serialnumber,vehiclenumber,sealnumber,batchnumber,netweight,deinsity;
    Button submit;
    FirebaseFirestore dbroot;
    TimePickerDialog tpicker;
    Calendar calendar = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outward_out_tanker_weighment);

        intime= findViewById(R.id.etintime);
        serialnumber = findViewById(R.id.etserialnumber);
        vehiclenumber = findViewById(R.id.etvehicleno);
        sealnumber = findViewById(R.id.etsealnumber);
        batchnumber = findViewById(R.id.etbatchnumber);
        netweight = findViewById(R.id.etnetweight);
        deinsity = findViewById(R.id.etdensity);

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
                tpicker = new TimePickerDialog(OutwardOut_Tanker_Weighment.this, new TimePickerDialog.OnTimeSetListener() {
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
//        intime,serialnumber,vehiclenumber,sealnumber,batchnumber,netweight,deinsity;

        String etintime = intime.getText().toString().trim();
        String etserialnumber = serialnumber.getText().toString().trim();
        String etvehiclenumber = vehiclenumber.getText().toString().trim();
        String etsealnumber = sealnumber.getText().toString().trim();
        String etbacthnumber = batchnumber.getText().toString().trim();
        String etnetweight = netweight.getText().toString().trim();
        String etdensity = deinsity.getText().toString().trim();

        if (etintime.isEmpty()||etserialnumber.isEmpty()||etvehiclenumber.isEmpty()||etsealnumber.isEmpty()||etbacthnumber.isEmpty()||
        etnetweight.isEmpty()||etdensity.isEmpty()){
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
        }else {
            Map<String ,String>items =new HashMap<>();

            items.put("In_Time",intime.getText().toString().trim());
            items.put("Serial_Number",serialnumber.getText().toString().trim());
            items.put("Vehicle_Number",vehiclenumber.getText().toString().trim());
            items.put("Seal_Number",sealnumber.getText().toString().trim());
            items.put("Batch_Number",batchnumber.getText().toString().trim());
            items.put("Net_Weight",netweight.getText().toString().trim());
            items.put("Density",deinsity.getText().toString().trim());

            dbroot.collection("OutwardOut_Tanker_Weighment(Out)").add(items)
                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            Toast.makeText(OutwardOut_Tanker_Weighment.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}