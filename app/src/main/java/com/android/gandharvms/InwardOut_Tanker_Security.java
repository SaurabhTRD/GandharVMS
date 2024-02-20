package com.android.gandharvms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.android.gandharvms.Inward_Tanker_Security.grid;

public class InwardOut_Tanker_Security extends AppCompatActivity {

    EditText edintime ;
    RadioButton lrcopyYes, lrcopyNo, deliveryYes, deliveryNo, taxinvoiceYes, taxinvoiceNo, ewaybillYes, ewaybillNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inward_out_tanker_security);
        edintime = findViewById(R.id.etintime);


//        String lrCopySelection = lrcopyYes.isChecked() ? "Yes" : "No";
//        String deliverySelection = deliveryYes.isChecked() ? "Yes" : "No";
//        String taxInvoiceSelection = taxinvoiceYes.isChecked() ? "Yes" : "No";
//        String ewayBillSelection = ewaybillYes.isChecked() ? "Yes" : "No";
    }



    public void onBackPressed(){
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
        finish();
    }

    public void inwardoutsecurityclick(View view){
        Intent intent = new Intent(this, grid.class);
        startActivity(intent);
    }
}