package com.android.gandharvms.Outward_Tanker_Production_forms;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.android.gandharvms.R;


public class bulkloadingproduction extends AppCompatActivity {
    EditText etintime,etserialno,etvehicle,etoanumber,etdate,ettankno,etproduct,etcustomer,etdestination,etqtykl,ettransporter,etnameofficer,etremark;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulkloadingproduction);
        etintime = findViewById(R.id.etintime);
        etserialno = findViewById(R.id.etserialnumber);
        etvehicle = findViewById(R.id.etvehicleno);
        etoanumber = findViewById(R.id.etoanumber);
        etdate = findViewById(R.id.etdate);
        ettankno = findViewById(R.id.ettankerno);
        etproduct = findViewById(R.id.etproduct);
        etcustomer = findViewById(R.id.etcustomer);
        etdestination = findViewById(R.id.etdestination);
        etqtykl = findViewById(R.id.etqty);
        ettransporter = findViewById(R.id.ettransporter);
        etnameofficer = findViewById(R.id.etsign);
        etremark = findViewById(R.id.etremark);
    }
}