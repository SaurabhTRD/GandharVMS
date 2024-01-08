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

public class Outward_Tanker_Billing extends AppCompatActivity {

    EditText intime,serialnumber,vehiclenumber,tankernumber,qty,transporter,product,tankeno,oanumber,date,pqty,partyname,location,
    status,salesman,remark;

    FirebaseFirestore dbroot;
    Button submit;
    TimePickerDialog tpicker;
    Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outward_tanker_billing);
        intime = findViewById(R.id.etintime);
        serialnumber = findViewById(R.id.etserialnumber);
        vehiclenumber = findViewById(R.id.etvehicleno);
        tankernumber = findViewById(R.id.ettankerno);
        qty = findViewById(R.id.etqty);
        transporter = findViewById(R.id.ettransportname);
        product = findViewById(R.id.etproduct);
        tankeno = findViewById(R.id.ettankno);
        oanumber = findViewById(R.id.etoanumber);
        date= findViewById(R.id.etdate);
        pqty = findViewById(R.id.etpqty);
        partyname = findViewById(R.id.etpartyname);
        location = findViewById(R.id.etlocation);
        status = findViewById(R.id.etstatus);
        salesman = findViewById(R.id.etsalesmen);
        remark = findViewById(R.id.etremark);

        submit=findViewById(R.id.etssubmit);
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
                tpicker = new TimePickerDialog(Outward_Tanker_Billing.this, new TimePickerDialog.OnTimeSetListener() {
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
//        intime,serialnumber,vehiclenumber,tankernumber,qty,transporter,product,tankeno,oanumber,date,pqty,partyname,location,
//                status,salesman,remark;
        String etintime = intime.getText().toString().trim();
        String etserilnumber = serialnumber.getText().toString().trim();
        String etvehiclenumber = vehiclenumber.getText().toString().trim();
        String ettankernumber = tankernumber.getText().toString().trim();
        String etqty = qty.getText().toString().trim();
        String ettransporter = transporter.getText().toString().trim();
        String etproduct = product .getText().toString().trim();
        String ettankno = tankeno.getText().toString().trim();
        String etoanumber = oanumber.getText().toString().trim();
        String etdate = date.getText().toString().trim();
        String etpqty = qty.getText().toString().trim();
        String etpartyname = partyname.getText().toString().trim();
        String etlocation = location.getText().toString().trim();
        String etstatus = status.getText().toString().trim();
        String etsalesman = salesman.getText().toString().trim();
        String etremark = remark.getText().toString().trim();

        if (etintime.isEmpty()|| etserilnumber.isEmpty()||etvehiclenumber.isEmpty()|| ettankernumber.isEmpty()||etqty.isEmpty()||ettransporter.isEmpty()||
        etproduct.isEmpty()|| ettankno.isEmpty()||etoanumber.isEmpty()||etdate.isEmpty()||etpqty.isEmpty()||etpartyname.isEmpty()||etlocation.isEmpty()||
        etstatus.isEmpty()||etsalesman.isEmpty()||etremark.isEmpty()){
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
        }else {
            Map<String,String>items = new HashMap<>();
            items.put("In_Time",intime.getText().toString().trim());
            items.put("Serial_Number",serialnumber.getText().toString().trim());
            items.put("Vehicle_Number",vehiclenumber.getText().toString().trim());
            items.put("Tanker_Number",tankernumber.getText().toString().trim());
            items.put("QTY",qty.getText().toString().trim());
            items.put("Transporter",transporter.getText().toString().trim());
            items.put("Product",product.getText().toString().trim());
            items.put("Tanke_No",tankeno.getText().toString().trim());
            items.put("OA_Number",oanumber.getText().toString().trim());
            items.put("Date",date.getText().toString().trim());
            items.put("P/Qty",pqty.getText().toString().trim());
            items.put("Party_Name",partyname.getText().toString().trim());
            items.put("Location",location.getText().toString().trim());
            items.put("Status",status.getText().toString().trim());
            items.put("Salesman",salesman.getText().toString().trim());
            items.put("Remark",remark.getText().toString().trim());

            dbroot.collection("Outward Tanker Billing (IN) ").add(items)
                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            Toast.makeText(Outward_Tanker_Billing.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}