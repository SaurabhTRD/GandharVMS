package com.android.gandharvms.Inward_Tanker_Laboratory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.gandharvms.FcmNotificationsSender;
import com.android.gandharvms.Inward_Tanker;
import com.android.gandharvms.Menu;
import com.android.gandharvms.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Inward_Tanker_Laboratory extends AppCompatActivity {

    String [] remark = {"Accepted","Rejected"};
    AutoCompleteTextView regAutoCompleteTextView;
    ArrayAdapter<String> remarkarray;
    EditText etintime, etpsample,etvehiclenumber,etpapperance,etpodor,etpcolour,etpdensity,etqty,etPrcstest,etpkv,ethundred,etanline,etflash,etpaddtest,etpsamplere,etpremark,etpsignQc,etpdatesignofsign,etMaterial,etsupplier,disc,etviscosity;
    Button etlabsub;
    Button view;
    TimePickerDialog tpicker;

    FirebaseFirestore dblabroot;

    DatePickerDialog picker,picker1,picker2;

    private final int MAX_LENGTH=10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inward_tanker_laboratory);
        //Send Notification to all
        //Send Notification to all
        FirebaseMessaging.getInstance().subscribeToTopic("eeEJ-ixdncup2-FtyzLVSu:APA91bGSjIm_CSC_VJCo56OFoc4IfAjKxvtSpg_Afmfha2OGxloggj3hmuMCjWtVmFF7cmGnKER_1fbHgkDpCjt8U9FCUtnLgF8lQonhS0WS-Aicu-W6j2bKAUDItioRUVHqCdrOGzEy");


        regAutoCompleteTextView = findViewById(R.id.etpremark);
        remarkarray = new ArrayAdapter<String>(this,R.layout.in_tanker_labremarkitem,remark);
        regAutoCompleteTextView.setAdapter(remarkarray);
        regAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String items = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(),"Item: "+items+"Selected",Toast.LENGTH_SHORT).show();
            }
        });


        etintime = (EditText) findViewById(R.id.etintime);
        etpsample = (EditText) findViewById(R.id.etpsample);
        etvehiclenumber = (EditText) findViewById(R.id.vehiclenumber);
        etpapperance = (EditText) findViewById(R.id.etpapperance);
        etpodor = (EditText) findViewById(R.id.etpodor);
        etpcolour = (EditText) findViewById(R.id.etpcolour);
        etqty = (EditText) findViewById(R.id.qtycolor);
        etpdensity = (EditText) findViewById(R.id.etpdensity);
        etPrcstest = (EditText) findViewById(R.id.etPrcstest);
        etpkv=(EditText) findViewById(R.id.etpkv);
        ethundred = (EditText)findViewById(R.id.hundered);
        etanline = (EditText) findViewById(R.id.anline);
        etflash = (EditText) findViewById(R.id.flash);
        etpaddtest=(EditText) findViewById(R.id.etpaddtest);
        etpsamplere=(EditText) findViewById(R.id.etpsamplere);
        etpremark =(EditText) findViewById(R.id.etpremark);
        etpsignQc=(EditText) findViewById(R.id.etpsignQc);
        etpdatesignofsign=(EditText) findViewById(R.id.etpdatesignofsign);
        etMaterial=(EditText) findViewById(R.id.et_materialname);

        etsupplier = (EditText) findViewById(R.id.supplier);
        disc = (EditText) findViewById(R.id.remarkdisc);
        etviscosity = (EditText) findViewById(R.id.etviscosityindex);






        etlabsub=(Button) findViewById(R.id.etlabsub);

        view = findViewById(R.id.viewclick);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Inward_Tanker_Laboratory.this,Inward_Tanker_Lab_Viewdata.class));
            }
        });

        etintime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hours = calendar.get(Calendar.HOUR_OF_DAY);
                int mins = calendar.get(Calendar.MINUTE);
                tpicker = new TimePickerDialog(Inward_Tanker_Laboratory.this, new TimePickerDialog.OnTimeSetListener() {
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


        etpsample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                // Array of month abbreviations
                String[] monthAbbreviations = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

                picker = new DatePickerDialog(Inward_Tanker_Laboratory.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Use the month abbreviation from the array
                        String monthAbbreviation = monthAbbreviations[month];
                        etpsample.setText(dayOfMonth + "/" + monthAbbreviation + "/" + year);
                    }
                }, year, month, day);
                picker.show();
            }
        });

        etpsamplere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                // Array of month abbreviations
                String[] monthAbbreviations = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

                picker = new DatePickerDialog(Inward_Tanker_Laboratory.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Use the month abbreviation from the array
                        String monthAbbreviation = monthAbbreviations[month];
                        etpsamplere.setText(dayOfMonth + "/" + monthAbbreviation + "/" + year);
                    }
                }, year, month, day);
                picker.show();
            }
        });
        etpdatesignofsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                // Array of month abbreviations
                String[] monthAbbreviations = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

                picker = new DatePickerDialog(Inward_Tanker_Laboratory.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Use the month abbreviation from the array
                        String monthAbbreviation = monthAbbreviations[month];
                        etpdatesignofsign.setText(dayOfMonth + "/" + monthAbbreviation + "/" + year);
                    }
                }, year, month, day);
                picker.show();
            }
        });
        etvehiclenumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > MAX_LENGTH) {
                    etvehiclenumber.removeTextChangedListener(this);
                    String trimmedText = editable.toString().substring(0, MAX_LENGTH);
                    etvehiclenumber.setText(trimmedText);
                    etvehiclenumber.setSelection(MAX_LENGTH); // Move cursor to the end
                    etvehiclenumber.addTextChangedListener(this);
                }else if (editable.length() < MAX_LENGTH) {
                    // Show an error message for less than 10 digits
                    etvehiclenumber.setError("Invalid format. Enter 10 Character. \n Vehicle No Format - ST00AA9999 OR YYBR9999AA");
                } else {
                    // Clear any previous error message when valid
                    etvehiclenumber.setError(null);
                }
            }
        });

        dblabroot=FirebaseFirestore.getInstance();

        etlabsub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                labinsertdata();
            }
        });
    }
    public void makeNotification(String vehicleNo,String outTime){
        FcmNotificationsSender notificationsSender = new FcmNotificationsSender("eeEJ-ixdncup2-FtyzLVSu:APA91bGSjIm_CSC_VJCo56OFoc4IfAjKxvtSpg_Afmfha2OGxloggj3hmuMCjWtVmFF7cmGnKER_1fbHgkDpCjt8U9FCUtnLgF8lQonhS0WS-Aicu-W6j2bKAUDItioRUVHqCdrOGzEy",
                "Inward Tanker Laboratory Process Done..!",
                "Vehicle Number:-" + vehicleNo + " has completed Laboratory process at " + outTime,
                getApplicationContext(), Inward_Tanker_Laboratory.this);
        notificationsSender.SendNotifications();
    }

    private String getCurrentTime() {
        // Get the current time
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(new Date());
    }

    public void labinsertdata()
    {
        String intime = etintime.getText().toString().trim();
        String sample = etpsample.getText().toString().trim();
        String vehicle = etvehiclenumber.getText().toString().trim();
        String apperance = etpapperance.getText().toString().trim();
        String odor = etpodor.getText().toString().trim();
        String color = etpcolour.getText().toString().trim();
        String qty = etqty.getText().toString().trim();
        String density = etpdensity.getText().toString().trim();
        String rcsTest = etPrcstest.getText().toString().trim();
        String kv = etpkv.getText().toString().trim();
        String hundred = ethundred.getText().toString().trim();
        String anline = etanline.getText().toString().trim();
        String flash = etflash.getText().toString().trim();
        String addTest = etpaddtest.getText().toString().trim();
        String sampleTest = etpsamplere.getText().toString().trim();
        String remark = etpremark.getText().toString().trim();
        String signQc = etpsignQc.getText().toString().trim();
        String dateSignOfSign = etpdatesignofsign.getText().toString().trim();
        String outTime = getCurrentTime();//Insert out Time Directly to the Database
        String material=etMaterial.getText().toString().trim();

        String edsupplier= etsupplier.getText().toString().trim();



        if ( intime.isEmpty() || sample.isEmpty() || vehicle.isEmpty() ||  apperance.isEmpty() || odor.isEmpty() || color.isEmpty() || qty.isEmpty()||  anline.isEmpty()|| flash.isEmpty()|| density.isEmpty() || rcsTest.isEmpty() ||
                kv.isEmpty() || addTest.isEmpty() || sampleTest.isEmpty() || remark.isEmpty() || signQc.isEmpty() || dateSignOfSign.isEmpty() || material.isEmpty()|| edsupplier.isEmpty()){

            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
        }else {



            Map<String,String> labitems = new HashMap<>();

            labitems.put("In_Time",etintime.getText().toString().trim());
            labitems.put("sample_reciving",etpsample.getText().toString().trim());
            labitems.put("Vehicle_Number",etvehiclenumber.getText().toString().trim());
            labitems.put("apperance",etpapperance.getText().toString().trim());
            labitems.put("odor",etpodor.getText().toString().trim());
            labitems.put("color",etpcolour.getText().toString().trim());
            labitems.put("Qty",etqty.getText().toString().trim());
            labitems.put("density",etpdensity.getText().toString().trim());
            labitems.put("Rcs_Test",etPrcstest.getText().toString().trim());
            labitems.put("40°KV",etpkv.getText().toString().trim());
            labitems.put("100°KV",ethundred.getText().toString().trim());
            labitems.put("Anline_Point",etanline.getText().toString().trim());
            labitems.put("Flash_Point",etflash.getText().toString().trim());
            labitems.put("Additional_test",etpaddtest.getText().toString().trim());
            labitems.put("sample_test",etpsamplere.getText().toString().trim());
            labitems.put("Remark",etpremark.getText().toString().trim());
            labitems.put("sign_of",etpsignQc.getText().toString().trim());
            labitems.put("Date_and_Time",etpdatesignofsign.getText().toString().trim());
            labitems.put("outTime",outTime.toString());
            labitems.put("Material",etMaterial.getText().toString().trim());

            labitems.put("Supplier",etsupplier.getText().toString().trim());
            labitems.put("Remark_Discription",disc.getText().toString().trim());
            labitems.put("Viscosity_Index",etviscosity.getText().toString().trim());



            makeNotification(etvehiclenumber.getText().toString(),outTime.toString());
            dblabroot.collection("Inward Tanker Laboratory").add(labitems)
                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {

                            etintime.setText("");
                            etpsample.setText("");
                            etvehiclenumber.setText("");
                            etpapperance.setText("");
                            etpodor.setText("");
                            etpcolour.setText("");
                            etqty.setText("");
                            etpdensity.setText("");
                            etPrcstest.setText("");
                            etpkv.setText("");
                            ethundred.setText("");
                            etanline.setText("");
                            etflash.setText("");
                            etpaddtest.setText("");
                            etpsamplere.setText("");
                            etpremark.setText("");
                            etpsignQc.setText("");
                            etpdatesignofsign.setText("");
                            etMaterial.setText("");
                            etsupplier.setText("");
                            disc.setText("");
                            etviscosity.setText("");

                            Toast.makeText(Inward_Tanker_Laboratory.this, "Data Added Successfully", Toast.LENGTH_SHORT).show();



                        }
                    });
            Intent intent= new Intent(this, Inward_Tanker.class);
            startActivity(intent);



        }




//
//        Map<String,String> labitems = new HashMap<>();
//
//        labitems.put("sample reciving",etpsample.getText().toString().trim());
//        labitems.put("apperance",etpapperance.getText().toString().trim());
//        labitems.put("odor",etpodor.getText().toString().trim());
//        labitems.put("color",etpcolour.getText().toString().trim());
//        labitems.put("density",etpdensity.getText().toString().trim());
//        labitems.put("Rcs Test",etPrcstest.getText().toString().trim());
//        labitems.put("KV",etpkv.getText().toString().trim());
//        labitems.put("Additional test",etpaddtest.getText().toString().trim());
//        labitems.put("sample test",etpsamplere.getText().toString().trim());
//        labitems.put("Remark",etpremark.getText().toString().trim());
//        labitems.put("sign of",etpsignQc.getText().toString().trim());
//        labitems.put("Date and Time",etpdatesignofsign.getText().toString().trim());
//
//
//        dblabroot.collection("Inward Tanker Laboratory").add(labitems)
//                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentReference> task) {
//
//                        etpsample.setText("");
//                        etpapperance.setText("");
//                        etpodor.setText("");
//                        etpcolour.setText("");
//                        etpdensity.setText("");
//                        etPrcstest.setText("");
//                        etpkv.setText("");
//                        etpaddtest.setText("");
//                        etpsamplere.setText("");
//                        etpremark.setText("");
//                        etpsignQc.setText("");
//                        etpdatesignofsign.setText("");
//
//                        Toast.makeText(Inward_Tanker_Laboratory.this, "Data Added Successfully", Toast.LENGTH_SHORT).show();
//
//
//
//
//
//
//
//
//
//
//
//
//                    }
//                });


    }
    public void onBackPressed(){
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
        finish();
    }

}