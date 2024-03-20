package com.android.gandharvms.OutwardOutTankerBilling;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.gandharvms.Global_Var;
import com.android.gandharvms.Inward_Truck_Security.Inward_Truck_Security;
import com.android.gandharvms.OutwardOut_Tanker;
import com.android.gandharvms.OutwardOut_Tanker_Weighment;
import com.android.gandharvms.Outward_Tanker;
import com.android.gandharvms.Outward_Tanker_Billing.Outward_Tanker_Billing;
import com.android.gandharvms.Outward_Tanker_Billing.Outward_Tanker_Billinginterface;
import com.android.gandharvms.Outward_Tanker_Billing.Respons_Outward_Tanker_Billing;
import com.android.gandharvms.Outward_Tanker_Security.Grid_Outward;
import com.android.gandharvms.Outward_Tanker_Security.Outward_RetroApiclient;
import com.android.gandharvms.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class ot_outBilling extends AppCompatActivity {

    String[] items1 = {"Ton", "Litre", "KL", "Kgs", "pcs"};
    AutoCompleteTextView totqtyautoCompleteTextView2;
    ArrayAdapter<String> nettotqtyuomdrop;
    Map<String, Integer> totqtyUomMapping= new HashMap<>();
    Integer nettotqtyuomvalue = 3;

    private int netweuom;
    EditText oobintime,oobserialnumber,oobvehiclenumber,oobsealnumber,oobtareweight,
            oobnetweight,oobgrossw,oobetremark,oobfetchdensity,oobOANumber,
            oobTransporter,oobdriverno,oobbatchno,oobtotalQuantity,oobinvoicenumber;
    Button oobsubmit;
    TimePickerDialog tpicker;
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private final String EmployeId = Global_Var.getInstance().EmpId;

    private Outward_Tanker_Billinginterface outwardTankerBillinginterface;
    private int OutwardId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ot_out_billing);

        outwardTankerBillinginterface = Outward_RetroApiclient.outwardTankerBillinginterface();

        oobintime=findViewById(R.id.etotoutbilintime);
        oobserialnumber=findViewById(R.id.etotoutbilserialnumber);
        oobvehiclenumber=findViewById(R.id.etotoutbilvehicleno);
        oobsealnumber=findViewById(R.id.etotoutbilsealnumber);
        oobtareweight=findViewById(R.id.etotoutbiltareweight);
        oobnetweight=findViewById(R.id.etotoutbilnetweight);
        oobgrossw=findViewById(R.id.etotoutbilgrosswt);
        oobetremark=findViewById(R.id.etotoutbilremakr);
        oobfetchdensity=findViewById(R.id.etotoutbildensity);
        oobOANumber=findViewById(R.id.etotoutbilOANumber);
        oobTransporter=findViewById(R.id.etotoutbilTransporter);
        oobdriverno=findViewById(R.id.etotoutbildrivermonumber);
        oobbatchno=findViewById(R.id.etotoutbilBatchno);
        oobtotalQuantity=findViewById(R.id.etotoutbiltotalQuantity);
        oobinvoicenumber=findViewById(R.id.etotoutbilinvoicenumber);

        oobsubmit=findViewById(R.id.etotoutbilsubmit);

        oobsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insert();
            }
        });

        if (getIntent().hasExtra("vehiclenum")) {
            FetchVehicleDetails(getIntent().getStringExtra("vehiclenum"), Global_Var.getInstance().MenuType, nextProcess, inOut);
        }

        totqtyautoCompleteTextView2 = findViewById(R.id.etotoutbiltotalQuantityUOM);
        totqtyUomMapping= new HashMap<>();
        totqtyUomMapping.put("NA",1);
        totqtyUomMapping.put("Ton", 2);
        totqtyUomMapping.put("Litre", 3);
        totqtyUomMapping.put("KL", 4);
        totqtyUomMapping.put("Kgs", 5);
        totqtyUomMapping.put("pcs", 6);

        nettotqtyuomdrop = new ArrayAdapter<String>(this,R.layout.in_tr_se_nwe_list,new ArrayList<>(totqtyUomMapping.keySet()));
        totqtyautoCompleteTextView2.setAdapter(nettotqtyuomdrop);
        totqtyautoCompleteTextView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item2 = parent.getItemAtPosition(position).toString();
                nettotqtyuomvalue = totqtyUomMapping.get(item2);
                if (nettotqtyuomvalue != null){
                    Toasty.success(ot_outBilling.this, "Total-Quantity:- " + item2 + " Selected", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toasty.warning(ot_outBilling.this, "Unknown Total-QuantityUom:- " + item2, Toast.LENGTH_SHORT).show();
                }
            }
        });
        oobvehiclenumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    FetchVehicleDetails(oobvehiclenumber.getText().toString().trim(), vehicleType, nextProcess, inOut);
                }
            }
        });

        oobintime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hours = calendar.get(Calendar.HOUR_OF_DAY);
                int mins = calendar.get(Calendar.MINUTE);
                tpicker = new TimePickerDialog(ot_outBilling.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        c.set(Calendar.MINUTE,minute);

                        // Set the formatted time to the EditText
                        oobintime.setText(hourOfDay +":"+ minute );
                    }
                },hours,mins,false);
                tpicker.show();
            }
        });
    }


    private void FetchVehicleDetails(@NonNull String vehicleNo, String vehicleType, char NextProcess, char inOut) {
        Call<Respons_Outward_Tanker_Billing> call = outwardTankerBillinginterface.outwardbillingfetching(vehicleNo,vehicleType,NextProcess,inOut);
        call.enqueue(new Callback<Respons_Outward_Tanker_Billing>() {
            @Override
            public void onResponse(Call<Respons_Outward_Tanker_Billing> call, Response<Respons_Outward_Tanker_Billing> response) {
                if (response.isSuccessful()){
                    Respons_Outward_Tanker_Billing data = response.body();
                    if (data.getVehicleNumber() != "" && data.getVehicleNumber()!=null){
                        OutwardId = data.getOutwardId();
                        oobvehiclenumber.setText(data.getVehicleNumber());
                        oobvehiclenumber.setEnabled(false);
                        oobserialnumber.setText(data.getSerialNumber());
                        oobserialnumber.setEnabled(false);
                        oobTransporter.setText(data.getTransportName());
                        oobTransporter.setEnabled(false);
                        oobOANumber.setText(data.getOAnumber());
                        oobOANumber.setEnabled(false);
                        oobdriverno.setText(data.getMobileNumber());
                        oobdriverno.setEnabled(false);
                        oobbatchno.setText(data.getBatchNo());
                        oobbatchno.setEnabled(false);
                        oobfetchdensity.setText(String.valueOf(data.getDensity_29_5C()));
                        oobfetchdensity.setEnabled(false);
                        oobtareweight.setText(data.getTareWeight());
                        oobtareweight.setEnabled(false);
                        oobnetweight.setText(data.getNetWeight());
                        oobnetweight.setEnabled(false);
                        oobgrossw.setText(data.getGrossWeight());
                        oobgrossw.setEnabled(false);
                        oobsealnumber.setText(data.getSealNumber());
                        oobsealnumber.setEnabled(false);
                        calculateNetWeight();


                    }
                    else {
                        Toasty.error(ot_outBilling.this, "This Vehicle Number Is Not Available..!", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Log.e("Retrofit", "Error Response Body: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Respons_Outward_Tanker_Billing> call, Throwable t) {

                Log.e("Retrofit", "Failure: " + t.getMessage());
                // Check if there's a response body in case of an HTTP error
                if (call != null && call.isExecuted() && call.isCanceled() && t instanceof HttpException) {
                    Response<?> response = ((HttpException) t).response();
                    if (response != null) {
                        Log.e("Retrofit", "Error Response Code: " + response.code());
                        try {
                            Log.e("Retrofit", "Error Response Body: " + response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    public void calculateNetWeight() {

        String ntweight = oobnetweight.getText().toString().trim();
        String density = oobfetchdensity.getText().toString().trim();

        double netweigh = ntweight.isEmpty() ? 0.0 : Double.parseDouble(ntweight);
        double dens = density.isEmpty() ? 0.0 : Double.parseDouble(density);

        //double grossweig = netweigh/dens;
        double grossweig;
        if (dens != 0) {
            grossweig = netweigh / dens;
        } else {
            grossweig = 0.0;
        }
        oobtotalQuantity.setText(String.valueOf(grossweig));
    }

    private String getCurrentTime() {
        // Get the current time
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(new Date());
    }
    public void insert(){
        String obintime = oobintime.getText().toString().trim();
        String obremark = oobetremark.getText().toString().trim();
        String oobtotalqty=oobtotalQuantity.getText().toString().trim();
        String oobinvcnum=oobinvoicenumber.getText().toString().trim();
        if(!nettotqtyuomvalue.toString().trim().isEmpty())
        {
            try {
                netweuom=Integer.parseInt(nettotqtyuomvalue.toString().trim().trim());
            }catch (NumberFormatException e){
                e.printStackTrace();
            }
        }
        else{
            Toasty.warning(this, "TotaQty Uom Is Not Selected", Toast.LENGTH_SHORT).show();
        }
        String obOutTime=getCurrentTime();
        if (obintime.isEmpty()|| obremark.isEmpty()||oobtotalqty.isEmpty()||oobinvcnum.isEmpty()){
            Toasty.warning(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
        }else {
            ot_outBillingRequestModel requestoutBilmodel = new ot_outBillingRequestModel(OutwardId,obintime,obOutTime,
                    oobtotalqty,netweuom,oobinvcnum,obremark, 'S',inOut, vehicleType,EmployeId);
            Call<Boolean> call = outwardTankerBillinginterface.UpdateOutBillingDetails(requestoutBilmodel);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body() != null && response.body()==true){
                        Toasty.success(ot_outBilling.this, "Data Inserted Successfully", Toast.LENGTH_SHORT,true).show();
                        startActivity(new Intent(ot_outBilling.this, OutwardOut_Tanker.class));
                        finish();
                    }else {
                        Log.e("Retrofit", "Error Response Body: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {

                    Log.e("Retrofit", "Failure: " + t.getMessage());
                    // Check if there's a response body in case of an HTTP error
                    if (call != null && call.isExecuted() && call.isCanceled() && t instanceof HttpException) {
                        Response<?> response = ((HttpException) t).response();
                        if (response != null) {
                            Log.e("Retrofit", "Error Response Code: " + response.code());
                            try {
                                Log.e("Retrofit", "Error Response Body: " + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    Toasty.error(ot_outBilling.this, "failed..!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void outwardoutpendingbillingViewclick(View view) {
        Intent intent = new Intent(this, Grid_Outward.class);
        startActivity(intent);
    }
    public void otoutbilcompletedclick(View view) {
        /*Intent intent = new Intent(this, in_tanker_lab_grid.class);
        startActivity(intent);*/
    }
}