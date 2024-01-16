package com.android.gandharvms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.gandharvms.Inward_Tanker_Production.Inward_Tanker_Production;
import com.android.gandharvms.Inward_Tanker_Security.In_Tanker_Security_list;
import com.android.gandharvms.Inward_Tanker_Weighment.In_Tanker_Weighment_list;
import com.android.gandharvms.Inward_Tanker_Weighment.Inward_Tanker_Weighment_Viewdata;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class InwardOut_Tanker_Weighment extends AppCompatActivity {

    EditText etintime,ettareweight,grswt,etvehicle;
    Button view;
    Button etsubmit;
    TimePickerDialog tpicker;
    FirebaseFirestore dbroot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inward_out_tanker_weighment);

        view = findViewById(R.id.btn_Viewweigmentslip);

        etintime = findViewById(R.id.etintime);
        ettareweight = findViewById(R.id.ettareweight);
        grswt = findViewById(R.id.etgrosswt);
        etvehicle = findViewById(R.id.etvehicle);

        etvehicle.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    FetchVehicleDetails(etvehicle.getText().toString().trim());
                }
            }

        });

        etsubmit = (Button) findViewById(R.id.prosubmit);
        dbroot = FirebaseFirestore.getInstance();

        etsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert();
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InwardOut_Tanker_Weighment.this, Inward_Tanker_Weighment_Viewdata.class));
            }
        });


        etintime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hours = calendar.get(Calendar.HOUR_OF_DAY);
                int mins = calendar.get(Calendar.MINUTE);
                tpicker = new TimePickerDialog(InwardOut_Tanker_Weighment.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        c.set(Calendar.MINUTE,minute);

                        // Set the formatted time to the EditText
                        etintime.setText(hourOfDay +":"+ minute );
                    }
                },hours,mins,false);
                tpicker.show();
            }
        });
    }


    public void insert(){
        String intime = etintime.getText().toString().trim();
        String tare = ettareweight.getText().toString().trim();

        if (intime.isEmpty() || tare.isEmpty()){
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
        }else {
            Map<String,String> outitems = new HashMap<>();
            outitems.put("In_Time",etintime.getText().toString().trim());
            outitems.put("Tare_Weight",ettareweight.getText().toString().trim());


            dbroot.collection("Inward Tanker Weighment(Out)").add(outitems)
                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            etintime.setText("");
                            ettareweight.setText("");

                            Toast.makeText(InwardOut_Tanker_Weighment.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();

                        }
                    });
        }
    }
    public void onBackPressed(){
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
        finish();
    }



    public void FetchVehicleDetails(@NonNull String VehicleNo)
    {
        CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Inward Tanker Weighment");
        String searchText = VehicleNo.toString().trim();
        CollectionReference collectionReferenceWe = FirebaseFirestore.getInstance().collection("Inward Tanker Weighment");
        Query query = collectionReference.whereEqualTo("Gross_Weight", searchText);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    int totalCount = task.getResult().size();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        In_Tanker_Weighment_list obj = document.toObject(In_Tanker_Weighment_list.class);
                        // Check if the object already exists to avoid duplicates
                        if (totalCount > 0) {
//                            etint.setText(obj.In_Time);


                            etvehicle.setText(obj.getVehicle_number());
                            grswt.setText(obj.getGross_Weight());

                        }
                    }
                } else {
                    Log.w("FirestoreData", "Error getting documents.", task.getException());
                }
            }
        });
    }

}