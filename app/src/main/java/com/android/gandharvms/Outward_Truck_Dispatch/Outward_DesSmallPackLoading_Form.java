package com.android.gandharvms.Outward_Truck_Dispatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.android.gandharvms.R;

public class Outward_DesSmallPackLoading_Form extends AppCompatActivity {

    EditText sevenltr,sevenandhalfltr,eighthalfltr,elevenltr,twelltr,threteenltr,fifteenltr,tenltr,eighteenltr,twentyltr,twentysixltr,fiftyltr,twotenltr,boxbucket;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outward_des_small_pack_loading_form);

        sevenltr = findViewById(R.id.etdesindusloadpacking7ltrqty);
        sevenandhalfltr = findViewById(R.id.etdesindusloadpacking7halfltrqty);
        eighthalfltr = findViewById(R.id.etdesindusloadpacking8halfltrqty);
        elevenltr = findViewById(R.id.etdesindusloadpacking11ltrqty);
    }
}