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
import com.android.gandharvms.Inward_Tanker_Weighment.InTanWeighRequestModel;
import com.android.gandharvms.Inward_Tanker_Weighment.InTanWeighResponseModel;
import com.android.gandharvms.Inward_Tanker_Weighment.In_Tanker_Weighment_list;
import com.android.gandharvms.Inward_Tanker_Weighment.Inward_Tanker_Weighment;
import com.android.gandharvms.Inward_Tanker_Weighment.Inward_Tanker_Weighment_Viewdata;
import com.android.gandharvms.LoginWithAPI.LoginMethod;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.LoginWithAPI.Weighment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class InwardOut_Tanker_Weighment extends AppCompatActivity {

    EditText etintime,ettareweight,grswt,etvehicle,etnetwt;
    Button view;
    Button etsubmit;
    TimePickerDialog tpicker;
    FirebaseFirestore dbroot;

    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private final String EmployeId = Global_Var.getInstance().EmpId;
    private Weighment weighmentdetails;
    private int inwardid;
    private LoginMethod userDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inward_out_tanker_weighment);

        userDetails = RetroApiClient.getLoginApi();
        weighmentdetails = RetroApiClient.getWeighmentDetails();

        view = findViewById(R.id.btn_Viewweigmentslip);

        etintime = findViewById(R.id.etintime);
        ettareweight = findViewById(R.id.ettareweight);
        grswt = findViewById(R.id.etgrosswt);
        etnetwt = findViewById(R.id.etnetweight);
        etvehicle = findViewById(R.id.etvehicle);

        etvehicle.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    FetchVehicleDetails(etvehicle.getText().toString().trim(),vehicleType,nextProcess,inOut);
                }
            }

        });

        etsubmit = (Button) findViewById(R.id.prosubmit);
        dbroot = FirebaseFirestore.getInstance();

        /*etsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert();
            }
        });*/
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


    /*public void insert(){

        String vehiclenumber = etvehicle.getText().toString().trim();
        String intime = etintime.getText().toString().trim();
        String tare = ettareweight.getText().toString().trim();
        String grossweight = grswt.getText().toString().trim();
        String etnetweight = etnetwt.getText().toString().trim();

        if (intime.isEmpty() || tare.isEmpty()){
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
        }else {
//            Map<String,String> outitems = new HashMap<>();
//            outitems.put("In_Time",etintime.getText().toString().trim());
//            outitems.put("Tare_Weight",ettareweight.getText().toString().trim());
//
//
//            dbroot.collection("Inward Tanker Weighment(Out)").add(outitems)
//                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
//                        @Override
//                        public void onComplete(@NonNull Task<DocumentReference> task) {
//                            etintime.setText("");
//                            ettareweight.setText("");
//
//                            Toast.makeText(InwardOut_Tanker_Weighment.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
//
//                        }
//                    });

            InTanWeighRequestModel weighReqModel = new InTanWeighRequestModel(inwardid, intime, "", grossweight, tare, etnetweight,
                    "", "", "", "", Integer.parseInt(""), "", "", "",
                    vehiclenumber, "", "", "", "", Integer.parseInt(""), 'S', inOut, vehicleType, EmployeId, EmployeId,"","");
            Call<Boolean> call = weighmentdetails.inwardoutweighment(weighReqModel);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body() != null){
                        Log.d("Registration", "Response Body: " + response.body());
                        Toasty.success(InwardOut_Tanker_Weighment.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
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
                    Toasty.error(InwardOut_Tanker_Weighment.this, "failed..!", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }*/
    public void onBackPressed(){
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
        finish();
    }

    public void calculateNetWeight() {

        String grosswt = grswt.getText().toString().trim();
        String netweight = etnetwt.getText().toString().trim();

        double grossWeight = grosswt.isEmpty() ? 0.0 : Double.parseDouble(grosswt);
        double netwe = netweight.isEmpty() ? 0.0 : Double.parseDouble(netweight);

        double tareweight = grossWeight - netwe;

        ettareweight.setText(String.valueOf(tareweight));



    }
//    public void calculateNetWeight(){
//        String tareweight = ettareweight.getText().toString().trim();
//        String grossweight = grswt.getText().toString().trim();
//
//
//    }



    public void FetchVehicleDetails(@NonNull String vehicleNo, String vehicleType, char NextProcess, char inOut)
    {
//        CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Inward Tanker Weighment");
//        String searchText = VehicleNo.toString().trim();
//        CollectionReference collectionReferenceWe = FirebaseFirestore.getInstance().collection("Inward Tanker Weighment");
//        Query query = collectionReference.whereEqualTo("vehicle_number", searchText);
//        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    int totalCount = task.getResult().size();
//                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        In_Tanker_Weighment_list obj = document.toObject(In_Tanker_Weighment_list.class);
//                        // Check if the object already exists to avoid duplicates
//                        if (totalCount > 0) {
//                            String Grosswt = obj.getGross_Weight();
//
////                            etint.setText(obj.In_Time);
//
//
//                            etvehicle.setText(obj.getVehicle_number());
//                            grswt.setText(obj.getGross_Weight());
//
//
//                        }
//                    }
//                } else {
//                    Log.w("FirestoreData", "Error getting documents.", task.getException());
//                }
//            }
//        });
       Call<InTanWeighResponseModel> call = weighmentdetails.getWeighbyfetchVehData(vehicleNo,vehicleType,NextProcess,inOut);
       call.enqueue(new Callback<InTanWeighResponseModel>() {
           @Override
           public void onResponse(Call<InTanWeighResponseModel> call, Response<InTanWeighResponseModel> response) {
               if (response.isSuccessful()){
                   InTanWeighResponseModel data = response.body();
                   if (data.getVehicleNo() != ""){
                       grswt.setText(data.getGrossWeight());
                       etnetwt.callOnClick();
                   }
               }
           }

           @Override
           public void onFailure(Call<InTanWeighResponseModel> call, Throwable t) {

           }
       });
    }

}