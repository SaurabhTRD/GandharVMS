package com.android.gandharvms.Inward_Tanker_Sampling;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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

public class Inward_Tanker_Sampling extends AppCompatActivity {

    EditText  etssignofproduction, etinvoiceno,etinvoicedate,materialname,etsqty1,suomqty,snetweight,suomnetwt,svesselname,sstoragetn,
            ssuppliername,etscustname,etsdate,etvehicleno;
    Button etssubmit;

    Button view;
    FirebaseFirestore sadbroot;
    DatePickerDialog picker;

    DatePickerDialog pickertr;
    TimePickerDialog tpicker;
    private final int MAX_LENGTH=10;

    Calendar currentTime = Calendar.getInstance();
    int currentHour = currentTime.get(Calendar.HOUR_OF_DAY);
    int currentMinute = currentTime.get(Calendar.MINUTE);

    Date currentDate = Calendar.getInstance().getTime();
    String dateFormatPattern = "dd-MM-yyyy";
    SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatPattern, Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inward_tanker_sampling);

        //Send Notification to all
        //Send Notification to all
        FirebaseMessaging.getInstance().subscribeToTopic("fpCeq7ExSG-mLN86F97WEv:APA91bFYFk9jntkrKOpzBBFVyUVoYwew8ixqRQ1r5eVE2RyFDcpDYoy7MuNuS8A-RYpl1TTDd4ZFubA0Mq3bbQNoETWUMZZbsFzGMwi9NUh-k4MCEkgIcI9IjZW5Nmpfe-ncTXsCWqfa");

        etssignofproduction=(EditText)findViewById(R.id.etreciving);
        etinvoiceno=(EditText) findViewById(R.id.etsubmitted);
//        etinvoicedate=(EditText) findViewById(R.id.invoicedate);
//        materialname=(EditText) findViewById(R.id.etmaterialname1);
//        etsqty1=(EditText) findViewById(R.id.etsqty);
//        suomqty=(EditText) findViewById(R.id.etsUOMqty);
//        snetweight=(EditText) findViewById(R.id.etsnetweight);
//        suomnetwt=(EditText) findViewById(R.id.etsuomnetwt);
//        svesselname=(EditText) findViewById(R.id.etsvesselname);
//        sstoragetn=(EditText) findViewById(R.id.etstoragetnkn);
//        ssuppliername=(EditText)findViewById(R.id.etssuppliername);
//        etscustname=(EditText)findViewById(R.id.etscustname);
        etsdate=(EditText)findViewById(R.id.etsdate);
        etvehicleno= (EditText)findViewById(R.id.etvehicleno);

        view = findViewById(R.id.samplingview);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Fragment fragment = new Inward_Tanker_Sampling_View_data();
//
//                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.frasample,fragment).commit();
//                startActivity(new Intent(Inward_Tanker_Sampling.this, Inward_Tanker_Sampling_View_data.class));
                startActivity(new Intent(Inward_Tanker_Sampling.this,Inward_Tanker_saampling_View_data.class) );
            }
        });

        // timepicker
        etssignofproduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hours = calendar.get(Calendar.HOUR_OF_DAY);
                int mins = calendar.get(Calendar.MINUTE);


//                tpicker = new TimePickerDialog(Inward_Tanker_Sampling.this, new TimePickerDialog.OnTimeSetListener() {
//                    @Override
//                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                        Calendar c = Calendar.getInstance();
//                        c.set(Calendar.HOUR_OF_DAY,hourOfDay);
//                        c.set(Calendar.MINUTE,minute);
//                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
//                        String formattedTime = sdf.format(calendar.getTime());
//
//                        etssignofproduction.setText(hourOfDay +":"+ minute );
//                    }
//                },hours,mins,false);
//                tpicker.show();

                String formattedTime = String.format(Locale.getDefault(), "%02d:%02d", currentHour, currentMinute);
                etssignofproduction.setText(formattedTime);

            }
        });


        etinvoiceno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hours = calendar.get(Calendar.HOUR_OF_DAY);
                int mins = calendar.get(Calendar.MINUTE);


//                tpicker = new TimePickerDialog(Inward_Tanker_Sampling.this, new TimePickerDialog.OnTimeSetListener() {
//                    @SuppressLint("SetTextI18n")
//                    @Override
//                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                        Calendar c = Calendar.getInstance();
//                        c.set(Calendar.HOUR_OF_DAY,hourOfDay);
//                        c.set(Calendar.MINUTE,minute);
//                        // SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
//                        // etinvoiceno.setText(sdf.format(hourOfDay +":"+ minute));
//                        etinvoiceno.setText(hourOfDay + ":" + minute );
//                    }
//                },hours,mins,false);
//                tpicker.show();
                String formattedTime = String.format(Locale.getDefault(), "%02d:%02d", currentHour, currentMinute);
                etinvoiceno.setText(formattedTime);

            }
        });


        etvehicleno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > MAX_LENGTH) {
                    etvehicleno.removeTextChangedListener(this);
                    String trimmedText = editable.toString().substring(0, MAX_LENGTH);
                    etvehicleno.setText(trimmedText);
                    etvehicleno.setSelection(MAX_LENGTH); // Move cursor to the end
                    etvehicleno.addTextChangedListener(this);
                }else if (editable.length() < MAX_LENGTH) {
                    // Show an error message for less than 10 digits
                    etvehicleno.setError("Invalid format. Enter 10 Character. \n Vehicle No Format - ST00AA9999 OR YYBR9999AA");
                } else {
                    // Clear any previous error message when valid
                    etvehicleno.setError(null);
                }
            }
        });

//        etsdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final Calendar calendar = Calendar.getInstance();
//                int day = calendar.get(Calendar.DAY_OF_MONTH);
//                int month = calendar.get(Calendar.MONTH);
//                int year = calendar.get(Calendar.YEAR);
//                // Array of month abbreviations
//                String[] monthAbbreviations = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
//                picker = new DatePickerDialog(Inward_Tanker_Sampling.this, new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                        // Use the month abbreviation from the array
//                        String monthAbbreviation = monthAbbreviations[month];
//                        etsdate.setText(dayOfMonth + "/" + monthAbbreviation + "/" + year);
//                    }
//                }, year, month, day);
//                picker.show();
//            }
//        });
        String formattedDate = dateFormat.format(currentDate);
        etsdate.setText(formattedDate);


        etssubmit=(Button) findViewById(R.id.etssubmit);
        sadbroot=FirebaseFirestore.getInstance();

        etssubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sainsertdata();
            }
        });


    }

    public void makeNotification(String vehicleNo,String outTime){
        FcmNotificationsSender notificationsSender = new FcmNotificationsSender("fpCeq7ExSG-mLN86F97WEv:APA91bFYFk9jntkrKOpzBBFVyUVoYwew8ixqRQ1r5eVE2RyFDcpDYoy7MuNuS8A-RYpl1TTDd4ZFubA0Mq3bbQNoETWUMZZbsFzGMwi9NUh-k4MCEkgIcI9IjZW5Nmpfe-ncTXsCWqfa",
                "Inward Tanker Sampling Process Done..!",
                "Vehicle Number:-" + vehicleNo + " has completed Sampling process at " + outTime,
                getApplicationContext(), Inward_Tanker_Sampling.this);
        notificationsSender.SendNotifications();
    }

    public void sainsertdata()
    {
        String etreciving = etssignofproduction.getText().toString().trim();
        String etsubmitted = etinvoiceno.getText().toString().trim();
//        String invoicedate = etinvoicedate.getText().toString().trim();
//        String materialnames = materialname.getText().toString().trim();
//        String Qty = etsqty1.getText().toString().trim();
//        String uomqty = suomqty.getText().toString().trim();
//        String netweight = snetweight.getText().toString().trim();
//        String uomnetwe= suomnetwt.getText().toString().trim();
//        String vesselname = svesselname.getText().toString().trim();
//        String storagetanl = sstoragetn.getText().toString().trim();
//        String suppliername = ssuppliername.getText().toString().trim();
//        String customername = etscustname.getText().toString().trim();
        String  date = etsdate.getText().toString().trim();
        String vehiclenumber =etvehicleno.getText().toString().trim();


        if (vehiclenumber.isEmpty() || etreciving.isEmpty() || date.isEmpty()|| etsubmitted.isEmpty()){
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
        }
        else {


            Map<String,String> saitems = new HashMap<>();

            saitems.put("Sample_Reciving_Time",etssignofproduction.getText().toString().trim());
            saitems.put("Sample_Submitted_Time",etinvoiceno.getText().toString().trim());
//            saitems.put("Invoice Date",etinvoicedate.getText().toString().trim());
//            saitems.put("Material Name",materialname.getText().toString().trim());
//            saitems.put("Qty",etsqty1.getText().toString().trim());
//            saitems.put("UOMqty",suomqty.getText().toString().trim());
//            saitems.put("Net Weight",snetweight.getText().toString().trim());
//            saitems.put("UOM Net Weight",suomnetwt.getText().toString().trim());
//            saitems.put("Vessel Name",svesselname.getText().toString().trim());
//            saitems.put("Storage Tank",sstoragetn.getText().toString().trim());
//            saitems.put("Supplier Name",ssuppliername.getText().toString().trim());
//            saitems.put("Customer Name",etscustname.getText().toString().trim());
            saitems.put("Date",etsdate.getText().toString().trim());
            saitems.put("Vehicle_Number",etvehicleno.getText().toString().trim());



            makeNotification(etvehicleno.getText().toString(),etinvoiceno.getText().toString());







            sadbroot.collection("Inward Tanker Sampling").add(saitems)

                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {

                            etssignofproduction.setText("");
                            etinvoiceno.setText("");
//                            etinvoicedate.setText("");
//                            materialname.setText("");
//                            etsqty1.setText("");
//                            suomqty.setText("");
//                            snetweight.setText("");
//                            suomnetwt.setText("");
//                            svesselname.setText("");
//                            sstoragetn.setText("");
//                            ssuppliername.setText("");
//                            etscustname.setText("");
                            etsdate.setText("");
                            etvehicleno.setText("");


                            Toast.makeText(Inward_Tanker_Sampling.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();




                        }
                    });

            Intent intent= new Intent(this, Inward_Tanker.class);
            startActivity(intent);

        }



    }

    public void onBackPressed(){
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
        finish();
    }

}