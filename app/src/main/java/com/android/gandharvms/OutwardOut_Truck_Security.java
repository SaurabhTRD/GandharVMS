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

public class OutwardOut_Truck_Security extends AppCompatActivity {

    EditText intime,serialnumber,vehiclenumber,invoice,party,gooddis,qty,uom1,netweight,uom2,outtime,sign,remark;
    Button submit;
    FirebaseFirestore dbroot;
    TimePickerDialog tpicker;
    Calendar calendar = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outward_out_truck_security);

        intime=findViewById(R.id.etintime);
        serialnumber=findViewById(R.id.etserialnumber);
        vehiclenumber=findViewById(R.id.etvehical);
        invoice=findViewById(R.id.etinvoice);
        party=findViewById(R.id.etpartyname);
        gooddis=findViewById(R.id.etdisc);
        qty=findViewById(R.id.etqty);
        uom1=findViewById(R.id.qtyuom);
        netweight=findViewById(R.id.etnetweight);
        uom2=findViewById(R.id.netweuom);
        outtime=findViewById(R.id.etouttime);
        sign=findViewById(R.id.etsign);
        remark=findViewById(R.id.etremark);

        submit = findViewById(R.id.submit);
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
                tpicker = new TimePickerDialog(OutwardOut_Truck_Security.this, new TimePickerDialog.OnTimeSetListener() {
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
//        intime,serialnumber,vehiclenumber,invoice,party,gooddis,qty,uom1,netweight,uom2,outtime,sign,remark;

        String etintime = intime.getText().toString().trim();
        String etserialnumber = serialnumber.getText().toString().trim();
        String etvehiclenumber = vehiclenumber.getText().toString().trim();
        String etinvoice = invoice.getText().toString().trim();
        String etparty= party.getText().toString().trim();
        String etgooddis = gooddis.getText().toString().trim();
        String etqty = qty.getText().toString().trim();
        String etuom1 = uom1.getText().toString().trim();
        String etnetweight = netweight.getText().toString().trim();
        String etuom2 = uom2.getText().toString().trim();
        String etouttime = outtime.getText().toString().trim();
        String etsign = sign.getText().toString().trim();
        String etremark = remark.getText().toString().trim();

        if (etintime.isEmpty()||etserialnumber.isEmpty()||etvehiclenumber.isEmpty()||etinvoice.isEmpty()||etparty.isEmpty()||
        etgooddis.isEmpty()|| etqty.isEmpty()||etuom1.isEmpty()||etnetweight.isEmpty()||etuom2.isEmpty()||etouttime.isEmpty()
        ||etsign.isEmpty()||etremark.isEmpty()){
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
        }else {
            Map<String,String>items = new HashMap<>();

            items.put("In_Time",intime.getText().toString().trim());
            items.put("Serial_Number",serialnumber.getText().toString().trim());
            items.put("Vehicle_Number",vehiclenumber.getText().toString().trim());
            items.put("Invoice_No",invoice.getText().toString().trim());
            items.put("Party_Name",party.getText().toString().trim());
            items.put("Goods_Discription",gooddis.getText().toString().trim());
            items.put("Qty",qty.getText().toString().trim());
            items.put("Uom_qty",uom1.getText().toString().trim());
            items.put("Net_Weight",netweight.getText().toString().trim());
            items.put("Uom_Netweight",uom2.getText().toString().trim());
            items.put("Out_Time",outtime.getText().toString().trim());
            items.put("Sign",sign.getText().toString().trim());
            items.put("Remark",remark.getText().toString().trim());

            dbroot.collection("OutwardOut Truck Security(OUT)").add(items)
                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            Toast.makeText(OutwardOut_Truck_Security.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}