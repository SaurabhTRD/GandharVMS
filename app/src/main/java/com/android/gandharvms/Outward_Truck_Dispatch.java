package com.android.gandharvms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.gandharvms.Inward_Tanker_Security.Inward_Tanker_Security;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Outward_Truck_Dispatch extends AppCompatActivity {

    String [] items = {"Packing of 210 Ltr","Packing of 50 Ltr","Packing of 26 Ltr","Packing of 20 Ltr","Packing of 10 Ltr",
            "Packing of Box & Bucket"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItems;

    EditText intime,serialnumber,date,vehiclenumber,material,qty,partyname,oanumber,typepackeging,qty2,disfficer,datetime,
            secofficer,datetime2,signeweighment,datetime3;
    Button submit;
    FirebaseFirestore dbroot;
    TimePickerDialog tpicker;
    Calendar calendar = Calendar.getInstance();
    DatePickerDialog picker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outward_truck_dispatch);

        autoCompleteTextView = findViewById(R.id.typepacking);
        adapterItems = new ArrayAdapter<String>(this,R.layout.dropdown_outward_truck_dispatch,items);
        autoCompleteTextView.setAdapter(adapterItems);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String items = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Item"+items, Toast.LENGTH_SHORT).show();
            }
        });


        intime = findViewById(R.id.etintime);
        serialnumber = findViewById(R.id.etserialnumber);
        date = findViewById(R.id.etdate);
        vehiclenumber = findViewById(R.id.etvehical);
        material = findViewById(R.id.etmaterial);
        qty = findViewById(R.id.etqty);
        partyname= findViewById(R.id.etpartyname);
        oanumber = findViewById(R.id.etoanumber);
        typepackeging= findViewById(R.id.typepacking);
        qty2=findViewById(R.id.etqty2);
        disfficer=findViewById(R.id.etdispatchofficer);
        datetime=findViewById(R.id.etdispatchdt);
        secofficer=findViewById(R.id.etcheckofficer);
        datetime2=findViewById(R.id.etsecurityofficerdt);
        signeweighment=findViewById(R.id.etsignedbyweigment);
        datetime3=findViewById(R.id.etweighmentdt);

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
                tpicker = new TimePickerDialog(Outward_Truck_Dispatch.this, new TimePickerDialog.OnTimeSetListener() {
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

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                picker = new DatePickerDialog(Outward_Truck_Dispatch.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date.setText(dayOfMonth + "/" + (month +1 ) + "/" + year);
                    }
                } ,year,month,day);
                picker.show();
            }
        });


    }
    public void insert(){
//        intime,serialnumber,date,vehiclenumber,material,qty,partyname,oanumber,typepackeging,qty2,disfficer,datetime,
//                secofficer,datetime2,signeweighment,datetime3;

        String etintime = intime.getText().toString().trim();
        String etseralnumber = serialnumber.getText().toString().trim();
        String etdate = date.getText().toString().trim();
        String etvehiclenumber = vehiclenumber.getText().toString().trim();
        String etmaterial = material.getText().toString().trim();
        String etqty=qty.getText().toString().trim();
        String etpartyname = partyname.getText().toString().trim();
        String etoanumber = oanumber.getText().toString().trim();
        String ettypepacking = typepackeging.getText().toString().trim();
        String etqty2 = qty2.getText().toString().trim();
        String etdisfficer = disfficer.getText().toString().trim();
        String etdatetime = datetime.getText().toString().trim();
        String etsecofficer = secofficer.getText().toString().trim();
        String etdatetime2 = datetime2.getText().toString().trim();
        String etsignweigment = signeweighment.getText().toString().trim();
        String etdatetime3 = datetime3.getText().toString().trim();

        if (etintime.isEmpty()||etseralnumber.isEmpty()||etdate.isEmpty()|| etvehiclenumber.isEmpty()||etmaterial.isEmpty()||
        etqty.isEmpty()||etpartyname.isEmpty()||etoanumber.isEmpty()||ettypepacking.isEmpty()||etqty2.isEmpty()||etdisfficer.isEmpty()||
        etdatetime.isEmpty()||etsecofficer.isEmpty()||etdatetime2.isEmpty()||etsignweigment.isEmpty()||etdatetime3.isEmpty()){
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
        }else {
            Map<String,String>items = new HashMap<>();

            items.put("In_Time",intime.getText().toString().trim());
            items.put("Serial_Number",serialnumber.getText().toString().trim());
            items.put("Date",date.getText().toString().trim());
            items.put("Vehicle_Number",vehiclenumber.getText().toString().trim());
            items.put("Material",material.getText().toString().trim());
            items.put("Qty",qty.getText().toString().trim());
            items.put("Party_Name",partyname.getText().toString().trim());
            items.put("OA_Number",oanumber.getText().toString().trim());
            items.put("Type_Of_Packaging",typepackeging.getText().toString().trim());
            items.put("Packaging_Qty",qty2.getText().toString().trim());
            items.put("Signed_By_Dispatch_Officer",disfficer.getText().toString().trim());
            items.put("Dispatch_Date_Time",datetime.getText().toString().trim());
            items.put("Checked_By_Security_Officer",secofficer.getText().toString().trim());
            items.put("Security_Date_Time",datetime2.getText().toString().trim());
            items.put("Signed_By_Weighment",signeweighment.getText().toString().trim());
            items.put("Weighment_Date_Time",datetime3.getText().toString().trim());

            dbroot.collection("Outward_Truck_Dispatch(IN)").add(items)
                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            Toast.makeText(Outward_Truck_Dispatch.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}