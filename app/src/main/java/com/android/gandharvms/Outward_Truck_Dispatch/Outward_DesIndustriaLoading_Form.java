package com.android.gandharvms.Outward_Truck_Dispatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.gandharvms.Inward_Tanker_Laboratory.Inward_Tanker_Laboratory;
import com.android.gandharvms.Inward_Tanker_Laboratory.it_Lab_Completedgrid;
import com.android.gandharvms.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class Outward_DesIndustriaLoading_Form extends AppCompatActivity {

    Button btndesilsubmit;
    EditText etdesilserialnumber,etdesilvehiclenumber,etdesiltransport,etdesilintime,etdesiltotqty,
            etdesilremark,etdesiltypeofpackingid;
    String[] typeofpacking= {"Packing of 210 Ltr", "Packing of 50 Ltr", "Packing Of 26 Ltr", "Packing of 20 Ltr", "Packing of 10 Ltr", "Packing of Box & Bucket"};
    Integer typeofpackingNumericValue = 1;
    AutoCompleteTextView  typeofpackingautoCompleteTextView1;
    Map<String, Integer> typeofpackingMapping = new HashMap<>();
    ArrayAdapter<String> typeofpackingdrop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outward_des_industria_loading_form);

        typeofpackingautoCompleteTextView1 = findViewById(R.id.autodesindusloadTypeOfPacking);
        typeofpackingMapping = new HashMap<>();
        typeofpackingMapping.put("Packing of 210 Ltr", 1);
        typeofpackingMapping.put("Packing of 50 Ltr", 2);
        typeofpackingMapping.put("Packing Of 26 Ltr", 3);
        typeofpackingMapping.put("Packing of 20 Ltr", 4);
        typeofpackingMapping.put("Packing of 10 Ltr", 5);
        typeofpackingMapping.put("Packing of Box & Bucket", 6);

        typeofpackingdrop = new ArrayAdapter<String>(this, R.layout.in_ta_se_qty, new ArrayList<>(typeofpackingMapping.keySet()));
        typeofpackingautoCompleteTextView1.setAdapter(typeofpackingdrop);
        typeofpackingautoCompleteTextView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String qtyUomDisplay = parent.getItemAtPosition(position).toString();
                // Retrieve the corresponding numerical value from the mapping
                typeofpackingNumericValue = typeofpackingMapping.get(qtyUomDisplay);
                if (typeofpackingNumericValue != null) {
                    // Now, you can use qtyUomNumericValue when inserting into the database
                    Toasty.success(Outward_DesIndustriaLoading_Form.this, "Type Of Packing : " + qtyUomDisplay + " Selected", Toast.LENGTH_SHORT).show();
                } else {
                    // Handle the case where the mapping doesn't contain the display value
                    Toasty.error(Outward_DesIndustriaLoading_Form.this, "Default Type Of Packing  : " + "NA" + " Selected", Toast.LENGTH_SHORT).show();
                }
            }
        });

        etdesilserialnumber=findViewById(R.id.etdesindusloadserialnumber);
        etdesilvehiclenumber=findViewById(R.id.etdesindusloadserialnumber);
        etdesiltransport=findViewById(R.id.etdesindusloadserialnumber);
        etdesilintime=findViewById(R.id.etdesindusloadserialnumber);
        etdesiltotqty=findViewById(R.id.etdesindusloadserialnumber);
        etdesilremark=findViewById(R.id.etdesindusloadserialnumber);
        etdesiltypeofpackingid=findViewById(R.id.etdesindusloadserialnumber);
        etdesiltypeofpackingid=findViewById(R.id.autodesindusloadTypeOfPacking);

        btndesilsubmit=findViewById(R.id.etdesindusloadsubmit);

    }

    public void btndesindusloadViewclick(View view) {
        //Intent intent = new Intent(this, it_Lab_Completedgrid.class);
        //startActivity(intent);
    }
}