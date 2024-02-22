package com.android.gandharvms.Outward_Tanker_Security;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.gandharvms.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Outward_Tanker_Security extends AppCompatActivity {


    EditText intime,serialnumber,kl,vehiclenum,transname,place,mobilenum;
    Button submit;
    FirebaseFirestore dbroot;
    TimePickerDialog tpicker;
    Calendar calendar = Calendar.getInstance();
    private CheckBox isReportingCheckBox;
    private EditText reportingRemarkLayout;
    Button saveButton;
    RadioButton rbvehpermityes,rbvehpermitno,rbpucyes,rbpucno,rbinsuranceyes,rbinsuranceno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outward_tanker_security);

        isReportingCheckBox = findViewById(R.id.isreporting);
        reportingRemarkLayout = findViewById(R.id.edtreportingremark);
        saveButton = findViewById(R.id.saveButton);

        reportingRemarkLayout.setVisibility(View.GONE);
        saveButton.setVisibility(View.GONE);

        isReportingCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Show the TextInputLayout and Button
                reportingRemarkLayout.setVisibility(View.VISIBLE);
                saveButton.setVisibility(View.VISIBLE);
            } else {
                // Hide the TextInputLayout and Button
                reportingRemarkLayout.setVisibility(View.GONE);
                saveButton.setVisibility(View.GONE);
            }
        });

        saveButton.setOnClickListener(v -> {
        });

        intime = findViewById(R.id.etintime);
        serialnumber = findViewById(R.id.etserialnumber);
        kl = findViewById(R.id.etkl);
        vehiclenum = findViewById(R.id.etvehicleno);
        transname = findViewById(R.id.ettranseportname);
        place = findViewById(R.id.etplace);
        mobilenum= findViewById(R.id.etmobilenumber);

        submit = findViewById(R.id.etssubmit);
        dbroot= FirebaseFirestore.getInstance();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isReportingCheckBox = findViewById(R.id.isreporting);
                if (isReportingCheckBox.isChecked()){
                    updateData();
                }else {
                    insert();
                }

            }
        });


        intime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hours = calendar.get(Calendar.HOUR_OF_DAY);
                int mins = calendar.get(Calendar.MINUTE);
                tpicker = new TimePickerDialog(Outward_Tanker_Security.this, new TimePickerDialog.OnTimeSetListener() {
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

    private String getCurrentTime() {
        // Get the current time
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(new Date());
    }



    public void insert(){
        String etintime = intime.getText().toString().trim();
        String etserialnum = serialnumber.getText().toString().trim();
        String etkl = kl.getText().toString().trim();
        String etvehiclnum = vehiclenum.getText().toString().trim();
        String ettransname = transname.getText().toString().trim();
        String etplace = place.getText().toString().trim();
        String etmobilenum = mobilenum.getText().toString().trim();
        String outTime = getCurrentTime();

        if (etintime.isEmpty()|| etserialnum.isEmpty()|| etkl.isEmpty()|| etvehiclnum.isEmpty()|| ettransname.isEmpty()||
        etplace.isEmpty()|| etmobilenum.isEmpty()){
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
        }else {
//            Map<String,String> items = new HashMap<>();
//            items.put("In_Time",intime.getText().toString().trim());
//            items.put("Serial_Number",serialnumber.getText().toString().trim());
//            items.put("kl",kl.getText().toString().trim());
//            items.put("Vehicle_Number",vehiclenum.getText().toString().trim());
//            items.put("Transporter",transname.getText().toString().trim());
//            items.put("Place",place.getText().toString().trim());
//            items.put("Mobile_Number",mobilenum.getText().toString().trim());
//
//            dbroot.collection("Outward Tanker Security(In)").add(items)
//                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
//                        @Override
//                        public void onComplete(@NonNull Task<DocumentReference> task) {
//                            Toast.makeText(Outward_Tanker_Security.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
//                        }
//                    });

//            Request_Model_Outward_Tanker_Security requestModelOutwardTankerSecurity = new Request_Model_Outward_Tanker_Security('0','0',etintime,outTime,kl,etplace,
//                    )
        }

    }
    private void updateData() {
    }

}