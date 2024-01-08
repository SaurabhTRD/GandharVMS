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

public class Outward_Tanker_Production extends AppCompatActivity {

    EditText intime,serialnumber,vehiclenumber,blenderno,transporter,product,howmuch,customer,location,blendingratio,batchno,
            productspesification,custref,packingsatus,rinsingstatus,decisionrule,blendingmaterial,signof,dt;
    Button submit;
    FirebaseFirestore dbroot;
    TimePickerDialog tpicker;
    Calendar calendar = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outward_tanker_production);

        intime= findViewById(R.id.etintime);
        serialnumber=findViewById(R.id.etserialnumber);
        vehiclenumber = findViewById(R.id.etvehicleno);
        blenderno = findViewById(R.id.elblendingno);
        transporter = findViewById(R.id.ettransportername);
        product =findViewById(R.id.etproductname);
        howmuch = findViewById(R.id.ethowmuch);
        customer = findViewById(R.id.etcustname);
        location = findViewById(R.id.etlocation);
        blendingratio = findViewById(R.id.etblendingrationrec);
        batchno = findViewById(R.id.etbatchno);
        productspesification = findViewById(R.id.etproductspesification);
        custref = findViewById(R.id.etcustrefno);
        packingsatus = findViewById(R.id.etpackingstatus);
        rinsingstatus = findViewById(R.id.etrinsingstatus);
        decisionrule = findViewById(R.id.etdecisionrule);
        blendingmaterial = findViewById(R.id.etblendingstatus);
        signof = findViewById(R.id.etsignofproduction);
        dt = findViewById(R.id.etdatetime);

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
                tpicker = new TimePickerDialog(Outward_Tanker_Production.this, new TimePickerDialog.OnTimeSetListener() {
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
//        intime,serialnumber,vehiclenumber,blenderno,transporter,product,howmuch,customer,location,blendingratio,batchno,
//                productspesification,custref,packingsatus,rinsingstatus,decisionrule,blendingmaterial,signof,dt;
        String etintime = intime.getText().toString().trim();
        String etserialnumber = serialnumber.getText().toString().trim();
        String etvehiclnumber = vehiclenumber.getText().toString().trim();
        String etblenderno = blenderno.getText().toString().trim();
        String ettrasnporter = transporter.getText().toString().trim();
        String etproduct = product.getText().toString().trim();
        String ethowmuch = howmuch.getText().toString().trim();
        String etcustomer = customer.getText().toString().trim();
        String etlocation = location.getText().toString().trim();
        String etblendingratio = blendingratio.getText().toString().trim();
        String etbatchno = batchno.getText().toString().trim();
        String etproductspesification = productspesification.getText().toString().trim();
        String etcustref = custref.getText().toString().trim();
        String etpackingref = packingsatus.getText().toString().trim();
        String etrinisingstatus = rinsingstatus.getText().toString().trim();
        String etdecisionrule = decisionrule.getText().toString().trim();
        String etblendingmaterial = blendingmaterial.getText().toString().trim();
        String  etsignof = signof.getText().toString().trim();
        String etdt = dt.getText().toString().trim();

        if (etintime.isEmpty()|| etserialnumber.isEmpty()||etvehiclnumber.isEmpty()||etblenderno.isEmpty()|| ettrasnporter.isEmpty()||etproduct.isEmpty()||
        ethowmuch.isEmpty()||etcustomer.isEmpty()||etlocation.isEmpty()||etblendingratio.isEmpty()||etbatchno.isEmpty()||etproductspesification.isEmpty()||
        etcustref.isEmpty()||etpackingref.isEmpty()||etrinisingstatus.isEmpty()||etdecisionrule.isEmpty()||etblendingmaterial.isEmpty()||etsignof.isEmpty()||etdt.isEmpty()){
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
        }else {
            Map<String,String> items = new HashMap<>();
            items.put("In_Time",intime.getText().toString().trim());
            items.put("Serial_Number",serialnumber.getText().toString().trim());
            items.put("Vehicle_Number",vehiclenumber.getText().toString().trim());
            items.put("Blender_No",blenderno.getText().toString().trim());
            items.put("Transporter",transporter.getText().toString().trim());
            items.put("Product",product.getText().toString().trim());
            items.put("How_Much_Qty_Of_Oil_To_Be_Filled",howmuch.getText().toString().trim());
            items.put("Customer",customer.getText().toString().trim());
            items.put("Location",location.getText().toString().trim());
            items.put("Blending_Ratio",blendingratio.getText().toString().trim());
            items.put("Batch_No",batchno.getText().toString().trim());
            items.put("Product_Spesification",productspesification.getText().toString().trim());
            items.put("Customer_Refrence_Number",custref.getText().toString().trim());
            items.put("Packing_Status",packingsatus.getText().toString().trim());
            items.put("Rinsing_Status",rinsingstatus.getText().toString().trim());
            items.put("Decision_Rule_Applicable_to_Customer",decisionrule.getText().toString().trim());
            items.put("Blending_Material_Status",blendingmaterial.getText().toString().trim());
            items.put("Date_Time",dt.getText().toString().trim());

            dbroot.collection("Outward Tanker Production(IN)").add(items)
                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            Toast.makeText(Outward_Tanker_Production.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                        }
                    });
        }

    }
}