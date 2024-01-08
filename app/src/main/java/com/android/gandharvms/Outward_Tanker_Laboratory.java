package com.android.gandharvms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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

public class Outward_Tanker_Laboratory extends AppCompatActivity {

    String [] items = {"OK","Not OK","Correction"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItems;
    EditText intime,serialnum,vehiclnum,blendingratio,appreance,color,odor,kv40,density25,kv100,viscosity,tbn,anlinepoint,
            breakdownvoltage,ddf,watercontent,interfacialtension,flashpoint,pourpoint,rcstest,remark,approveqc,dt;

    Button submit;
    FirebaseFirestore dbroot;
    TimePickerDialog tpicker;
    Calendar calendar = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outward_tanker_laboratory);

        autoCompleteTextView = findViewById(R.id.etremark);
        adapterItems = new ArrayAdapter<String>(this,R.layout.dropdown_outward_securitytanker,items);
        autoCompleteTextView.setAdapter(adapterItems);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String items = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Item"+items, Toast.LENGTH_SHORT).show();
            }
        });

        intime = findViewById(R.id.etintime);
        serialnum = findViewById(R.id.etserialnumber);
        vehiclnum = findViewById(R.id.etvehicleno);
        blendingratio = findViewById(R.id.elblendingratio);
        appreance = findViewById(R.id.etapperance);
        color = findViewById(R.id.etcolor);
        odor = findViewById(R.id.etodor);
        density25 = findViewById(R.id.etdensity25);
        kv40 = findViewById(R.id.etviscosity40);
        kv100 = findViewById(R.id.etvisocity100);
        viscosity = findViewById(R.id.etviscosityindex);
        tbn = findViewById(R.id.ettabtan);
        anlinepoint = findViewById(R.id.etanlinepoint);
        breakdownvoltage = findViewById(R.id.etbreakvol);
        ddf = findViewById(R.id.etddf);
        watercontent = findViewById(R.id.etwatercontent);
        interfacialtension = findViewById(R.id.etinterfacial);
        flashpoint = findViewById(R.id.etflashpoint);
        pourpoint = findViewById(R.id.etpourpoint);
        rcstest = findViewById(R.id.etrcstest);
        remark = findViewById(R.id.etremark);
        approveqc= findViewById(R.id.etapprove);
        dt = findViewById(R.id.etdatetime);



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
                tpicker = new TimePickerDialog(Outward_Tanker_Laboratory.this, new TimePickerDialog.OnTimeSetListener() {
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
//        intime,serialnum,vehiclnum,blendingratio,appreance,color,odor,kv40,density25,kv100,viscosity,tbn,anlinepoint,
//                breakdownvoltage,ddf,watercontent,interfacialtension,flashpoint,pourpoint,rcstest,remark,approveqc,dt;
        String etintime = intime.getText().toString().trim();
        String etserialnum = serialnum.getText().toString().trim();
        String etvehicl = vehiclnum.getText().toString().trim();
        String etblending = blendingratio.getText().toString().trim();
        String etapperance = appreance.getText().toString().trim();
        String etcolor = color.getText().toString().trim();
        String etodor = odor.getText().toString().trim();
        String etdensity25 = density25.getText().toString().trim();
        String etkv40 = kv40.getText().toString().trim();
        String etkv100 = kv100.getText().toString().trim();
        String etviscosity = viscosity.getText().toString().trim();
        String ettbn = tbn.getText().toString().trim();
        String etanlinepoint = anlinepoint.getText().toString().trim();
        String etbreakdown = breakdownvoltage.getText().toString().trim();
        String etddf = ddf.getText().toString().trim();
        String etwatercontent = watercontent.getText().toString().trim();
        String etinetrfacial = interfacialtension.getText().toString().trim();
        String etflashpoint = flashpoint.getText().toString().trim();
        String etpourpoint =  pourpoint.getText().toString().trim();
        String etrcstest = rcstest.getText().toString().trim();
        String etremark = remark.getText().toString().trim();
        String etapprove = approveqc.getText().toString().trim();
        String etdt = dt.getText().toString().trim();

        if (etintime.isEmpty() || etserialnum.isEmpty() ||etvehicl.isEmpty()||etblending.isEmpty()||etapperance.isEmpty()||etcolor.isEmpty()||
        etodor.isEmpty()|| etdensity25.isEmpty()||etkv40.isEmpty()||etkv100.isEmpty()||etviscosity.isEmpty()||ettbn.isEmpty()||etanlinepoint.isEmpty()||
        etbreakdown.isEmpty()||etddf.isEmpty()||etwatercontent.isEmpty()||etinetrfacial.isEmpty()||etflashpoint.isEmpty()||etpourpoint.isEmpty()||
                etrcstest.isEmpty()||etremark.isEmpty()||etapprove.isEmpty()||etdt.isEmpty()){
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
        }else {
            Map<String,String> items = new HashMap<>();
            items.put("In_Time",intime.getText().toString().trim());
            items.put("Serial_Number",serialnum.getText().toString().trim());
            items.put("Vehicle_Number",vehiclnum.getText().toString().trim());
            items.put("Blending_Ratio",blendingratio.getText().toString().trim());
            items.put("Appearance",approveqc.getText().toString().trim());
            items.put("Color",color.getText().toString().trim());
            items.put("Odor",odor.getText().toString().trim());
            items.put("Density_at_29.5°C",density25.getText().toString().trim());
            items.put("K.Viscosity_at_40°_c",kv40.getText().toString().trim());
            items.put("K.Viscosity_at_100°_c",kv100.getText().toString().trim());
            items.put("Viscosity_Index",viscosity.getText().toString().trim());
            items.put("TBN_TAN",tbn.getText().toString().trim());
            items.put("Anilin_Point",anlinepoint.getText().toString().trim());
            items.put("BreakDown_Voltage",breakdownvoltage.getText().toString().trim());
            items.put("DDF_at_90°C",ddf.getText().toString().trim());
            items.put("Water_Content",watercontent.getText().toString().trim());
            items.put("Interfacial_Tension",interfacialtension.getText().toString().trim());
            items.put("Flash_Point",flashpoint.getText().toString().trim());
            items.put("Pour_Point",pourpoint.getText().toString().trim());
            items.put("RCS_Test",rcstest.getText().toString().trim());
            items.put("Remark",remark.getText().toString().trim());
            items.put("Approved_By_Qc_Manager",approveqc.getText().toString().trim());
            items.put("Date_Time",dt.getText().toString().trim());

            dbroot.collection("Outward_Tanker_Laboratory(In)").add(items)
                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            Toast.makeText(Outward_Tanker_Laboratory.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                        }
                    });
        }


    }
}