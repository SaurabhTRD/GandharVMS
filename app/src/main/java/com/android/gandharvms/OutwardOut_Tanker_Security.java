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

public class OutwardOut_Tanker_Security extends AppCompatActivity {

    EditText intime,serialnumber,date,vehiclenumber,invoiceno,partyname,goodsdiscription,qty,netweight,sign,remark;
    Button submit;
    FirebaseFirestore dbroot;
    TimePickerDialog tpicker;
    Calendar calendar = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outward_out_tanker_security);

        intime = findViewById(R.id.etintime);
        serialnumber = findViewById(R.id.etserialnumber);
        date = findViewById(R.id.etdate);
        vehiclenumber = findViewById(R.id.etvehicleno);
        invoiceno =findViewById(R.id.etinvoicenumber);
        partyname = findViewById(R.id.etpartyname);
        goodsdiscription = findViewById(R.id.etgoodsdisc);
        qty = findViewById(R.id.etqty);
        netweight = findViewById(R.id.etnetweight);
        sign = findViewById(R.id.etsign);
        remark = findViewById(R.id.etremark);

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
                tpicker = new TimePickerDialog(OutwardOut_Tanker_Security.this, new TimePickerDialog.OnTimeSetListener() {
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
//        intime,serialnumber,date,vehiclenumber,invoiceno,partyname,goodsdiscription,qty,netweight,sign,remark;

        String etintime = intime.getText().toString().trim();
        String etserialnumber = serialnumber.getText().toString().trim();
        String etdate = date.getText().toString().trim();
        String etvehiclenumber = vehiclenumber.getText().toString().trim();
        String etinvoiceno = invoiceno.getText().toString().trim();
        String etpartyname = partyname.getText().toString().trim();
        String etgooddiscription = goodsdiscription.getText().toString().trim();
        String etqty = qty.getText().toString().trim();
        String etnetweight = netweight.getText().toString().trim();
        String etsign = sign.getText().toString().trim();
        String etremark = remark.getText().toString().trim();

        if (etintime.isEmpty()||etserialnumber.isEmpty()||etdate.isEmpty()||etvehiclenumber.isEmpty()||etinvoiceno.isEmpty()||etpartyname.isEmpty()
        ||etgooddiscription.isEmpty()||etqty.isEmpty()||etnetweight.isEmpty()||etsign.isEmpty()||etremark.isEmpty()){
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
        }else {
            Map<String,String> items = new HashMap<>();

            items.put("In_Time",intime.getText().toString().trim());
            items.put("Serial_Number",serialnumber.getText().toString().trim());
            items.put("Date",date.getText().toString().trim());
            items.put("Vehicle_Number",vehiclenumber.getText().toString().trim());
            items.put("Invoice_No",invoiceno.getText().toString().trim());
            items.put("Party_Name",partyname.getText().toString().trim());
            items.put("Goods_Discription",goodsdiscription.getText().toString().trim());
            items.put("Qty",qty.getText().toString().trim());
            items.put("Net_Weight",netweight.getText().toString().trim());
            items.put("Sign",sign.getText().toString().trim());
            items.put("Remark",remark.getText().toString().trim());


            dbroot.collection("OutwardOut Tanker Security(Out)").add(items)
                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            Toast.makeText(OutwardOut_Tanker_Security.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}