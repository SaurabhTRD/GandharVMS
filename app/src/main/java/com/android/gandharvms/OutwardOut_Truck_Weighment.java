package com.android.gandharvms;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.gandharvms.LoginWithAPI.Login;
import com.android.gandharvms.LoginWithAPI.LoginMethod;
import com.android.gandharvms.LoginWithAPI.ResponseModel;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.NotificationAlerts.NotificationCommonfunctioncls;
import com.android.gandharvms.Outward_Tanker_Security.Grid_Outward;
import com.android.gandharvms.Outward_Tanker_Security.Outward_RetroApiclient;
import com.android.gandharvms.Outward_Tanker_Weighment.Model_OutwardOut_Truck_Weighment;
import com.android.gandharvms.Outward_Tanker_Weighment.Outward_Tanker_weighment;
import com.android.gandharvms.Outward_Tanker_Weighment.Outward_weighment;
import com.android.gandharvms.Outward_Tanker_Weighment.Response_Outward_Tanker_Weighment;
import com.android.gandharvms.Outward_Truck_Billing.Outward_Truck_Billing;
import com.android.gandharvms.Outward_Truck_Laboratory.Outward_Truck_Laboratory;
import com.android.gandharvms.Outward_Truck_Weighment.Weigh_Out_OR_Complete;
import com.android.gandharvms.Outwardout_Tanker_Weighment.OutwardOut_Tanker_Weighment;
import com.android.gandharvms.Util.ImageUtils;
import com.android.gandharvms.Util.MultipartTask;
import com.android.gandharvms.Util.dialogueprogreesbar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class OutwardOut_Truck_Weighment extends NotificationCommonfunctioncls {

    TextView smallpacktitle,indutialpacktitle;
    EditText intime,serialnumber,vehiclenum,grosswright,noofpack,netwt,etremark,seal,ettare,etshdip,etshwe;
    LinearLayout lnlsmallpackqty;
    EditText splpack7literqty,splpack7_5literqty,splpack8_5literqty,splpack10literqty,splpack11literqty,
            splpack12literqty,splpack13literqty,splpack15literqty,splpack18literqty,splpack20literqty,
            splpack26literqty, splpack50literqty,splpack210literqty,splpackboxbucketqty,
            smalltotqty,smalltotweight;
    LinearLayout lnlindustrialbarrel;
    EditText ilpack10literqty,ilpack18literqty,ilpack20literqty,
            ilpack26literqty, ilpack50literqty,ilpack210literqty,ilpackboxbucketqty,
            iltotqty,iltotweight,industotqty,industotwight;
    //EditText industotqty,industotwight,smalltotqty,smalltotweight;

    Button submit,etcompleted;
    FirebaseFirestore dbroot;
    TimePickerDialog tpicker;
    Calendar calendar = Calendar.getInstance();

    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private final String EmployeId = Global_Var.getInstance().EmpId;
    private int OutwardId;
    private Outward_weighment outwardWeighment;
    SimpleDateFormat dtFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
    DatePickerDialog picker;
    private LoginMethod userDetails;
    private String token;
    private String woutTime;
    private String wvehiclenumber;
    Uri image1, image2;
    byte[] ImgDriver, ImgVehicle;
    byte[][] arrayOfByteArrays = new byte[2][];
    Uri[] LocalImgPath = new Uri[2];
    private String imgPath1, imgPath2;
    private String wserialNo;
    ImageView img1, img2;
    private static final int CAMERA_PERM_CODE1 = 100;
    private static final int CAMERA_PERM_CODE = 101;
    private static final int CAMERA_REQUEST_CODE = 102;
    private static final int CAMERA_REQUEST_CODE1 = 103;
    private int ushdip;
    private int ushwe;
    ImageView btnlogout,btnhome;
    TextView username,empid;
    dialogueprogreesbar dialogHelper = new dialogueprogreesbar();
    public static String Tanker;
    public static String Truck;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outward_out_truck_weighment);
        outwardWeighment = Outward_RetroApiclient.outwardWeighment();
        userDetails = RetroApiClient.getLoginApi();

        intime=findViewById(R.id.etintime);
        serialnumber=findViewById(R.id.etserialnumber);
        vehiclenum=findViewById(R.id.etvehical);
        grosswright=findViewById(R.id.etgrossweight);
        noofpack=findViewById(R.id.etpack);
        netwt = findViewById(R.id.etnetwwight);
        etremark = findViewById(R.id.etOTW_remark);
        seal = findViewById(R.id.etseal);
        ettare = findViewById(R.id.ettarewt);
        /*industotqty=findViewById(R.id.etinudustotqty);
        industotwight=findViewById(R.id.etinudustotweight);
        smalltotqty=findViewById(R.id.etsmalltotqty);
        smalltotweight=findViewById(R.id.etsmalltotweight);*/

        indutialpacktitle=findViewById(R.id.txtotowindustrailtitle);
        lnlindustrialbarrel=findViewById(R.id.lnlotoutwindustrialbarrel);
        ilpack10literqty=findViewById(R.id.otoutwilpack10Liter);
        ilpack18literqty=findViewById(R.id.otoutwilpack18Liter);
        ilpack20literqty=findViewById(R.id.otoutwilpack20Liter);
        ilpack26literqty=findViewById(R.id.otoutwilpack26Liter);
        ilpack50literqty=findViewById(R.id.otoutwilpack50Liter);
        ilpack210literqty=findViewById(R.id.otoutwilpack210Liter);
        ilpackboxbucketqty=findViewById(R.id.otoutwilpackboxbucket);
        industotqty=findViewById(R.id.etinudustotqty);
        industotwight=findViewById(R.id.etinudustotweight);

        smallpacktitle=findViewById(R.id.txtotowsmalltitle);
        lnlsmallpackqty=findViewById(R.id.lnlotoutwsmallpackbarrel);
        splpack7literqty=findViewById(R.id.otoutwsplpack7Liter);
        splpack7_5literqty=findViewById(R.id.otoutwsplpack7_5Liter);
        splpack8_5literqty=findViewById(R.id.otoutwsplpack8_5Liter);
        splpack10literqty=findViewById(R.id.otoutwsplpack10Liter);
        splpack11literqty=findViewById(R.id.otoutwsplpack11Liter);
        splpack12literqty=findViewById(R.id.otoutwsplpack12Liter);
        splpack13literqty=findViewById(R.id.otoutwsplpack13Liter);
        splpack15literqty=findViewById(R.id.otoutwsplpack15Liter);
        splpack18literqty=findViewById(R.id.otoutwsplpack18Liter);
        splpack20literqty=findViewById(R.id.otoutwsplpack20Liter);
        splpack26literqty=findViewById(R.id.otoutwsplpack26Liter);
        splpack50literqty=findViewById(R.id.otoutwsplpack50Liter);
        splpack210literqty=findViewById(R.id.otoutwsplpack210Liter);
        splpackboxbucketqty=findViewById(R.id.otoutwsplpackboxbucket);
        smalltotqty=findViewById(R.id.etsmalltotqty);
        smalltotweight=findViewById(R.id.etsmalltotweight);


        submit = findViewById(R.id.submit);
        etcompleted = findViewById(R.id.orweoutcomple);
        dbroot= FirebaseFirestore.getInstance();

        img1 = findViewById(R.id.outwardouttruckvehicle);
        img2 = findViewById(R.id.outwardouttruckdriver);
        etshdip = findViewById(R.id.etshortdip);
        etshwe = findViewById(R.id.etshortageweight);
        setupHeader();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (image1 == null || image2 == null) {
                    Toasty.warning(OutwardOut_Truck_Weighment.this, "Please Upload Image", Toast.LENGTH_SHORT).show();
                } else {
                    UploadImagesAndUpdate();
                }
//                insert();
            }
        });
        etcompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(OutwardOut_Truck_Weighment.this,Weigh_Out_OR_Complete.class));

            }
        });

        if (getIntent().hasExtra("vehiclenum")) {
            FetchVehicleDetails(getIntent().getStringExtra("vehiclenum"), Global_Var.getInstance().MenuType, nextProcess, inOut);
        }
        intime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                String time =  format.format(calendar.getTime());
                intime.setText(time);
            }
        });

        vehiclenum.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    FetchVehicleDetails(vehiclenum.getText().toString().trim(), vehicleType, nextProcess, inOut);
                }
            }
        });

        netwt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                calculateNetWeight();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void calculateNetWeight() {

        String trweight = ettare.getText().toString().trim();
        String ntweight = netwt.getText().toString().trim();

        double tweig = trweight.isEmpty() ? 0.0 : Double.parseDouble(trweight);
        double netweigh = ntweight.isEmpty() ? 0.0 : Double.parseDouble(ntweight);

        double grossweig = tweig + netweigh;

        grosswright.setText(String.valueOf(grossweig));
    }

    public void makeNotification(String vehicleNumber) {
        Call<List<ResponseModel>> call = userDetails.getUsersListData();
        call.enqueue(new Callback<List<ResponseModel>>() {
            @Override
            public void onResponse(Call<List<ResponseModel>> call, Response<List<ResponseModel>> response) {
                if (response.isSuccessful()){
                    List<ResponseModel> userList = response.body();
                    if (userList != null){
                        for (ResponseModel resmodel : userList){
                            String specificRole = "DataEntry";
                            if (specificRole.equals(resmodel.getDepartment())) {
                                token = resmodel.getToken();

                                FcmNotificationsSender notificationsSender = new FcmNotificationsSender(
                                        token,
                                        "OutwardOut Truck Weighment Process Done..!",
                                        "Vehicle Number:-" + vehicleNumber + " has completed Weighment process at ",
                                        getApplicationContext(),
                                        OutwardOut_Truck_Weighment.this
                                );
                                notificationsSender.triggerSendNotification();
                            }
                        }
                    }
                }
                else {
                    Log.d("API", "Unsuccessful API response");
                }
            }

            @Override
            public void onFailure(Call<List<ResponseModel>> call, Throwable t) {

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
                Toasty.error(OutwardOut_Truck_Weighment.this, "failed..!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void FetchVehicleDetails(@NonNull String vehicleNo, String vehicleType, char NextProcess, char inOut) {
        Call<Response_Outward_Tanker_Weighment> call = outwardWeighment.fetchweighment(vehicleNo,vehicleType,NextProcess,inOut);
        call.enqueue(new Callback<Response_Outward_Tanker_Weighment>() {
            @Override
            public void onResponse(Call<Response_Outward_Tanker_Weighment> call, Response<Response_Outward_Tanker_Weighment> response) {
                if (response.isSuccessful()){
                    Response_Outward_Tanker_Weighment data = response.body();
                    if (data.getVehicleNumber()!= "" && data.getVehicleNumber()!= null){
                        OutwardId =data.getOutwardId();
                        serialnumber.setText(data.getSerialNumber());
                        vehiclenum.setText(data.getVehicleNumber());
                        serialnumber.setEnabled(false);
                        vehiclenum.setEnabled(false);
                        wvehiclenumber = data.getVehicleNumber();
                        woutTime = data.getOutTime();
                        ettare.setText(data.getTareWeight());
                        ettare.setEnabled(false);
                        wserialNo= data.getSerialNumber();
                        seal.setText("0");
                        noofpack.setText("0");
                        etshdip.setText("0");
                        etshwe.setText("0");
                        etremark.setText("OK");
                        if(data.iltotqty.isEmpty() || data.ilweight=="0")
                        {
                            smallpacktitle.setVisibility(View.VISIBLE);
                            lnlsmallpackqty.setVisibility(View.VISIBLE);
                            splpack7literqty.setText(String.valueOf("SmallPack 7 Liter Qty :- "+ data.getSplpackof7ltrqty()));
                            splpack7literqty.setEnabled(false);
                            splpack7_5literqty.setText(String.valueOf("SmallPack 7.5 Liter Qty :- "+ data.getSplpackof7_5ltrqty()));
                            splpack7_5literqty.setEnabled(false);
                            splpack8_5literqty.setText(String.valueOf("SmallPack 8.5 Liter Qty :- "+ data.getSplpackof8_5ltrqty()));
                            splpack8_5literqty.setEnabled(false);
                            splpack10literqty.setText(String.valueOf("SmallPack 10 Liter Qty :- "+ data.getSplpackof10ltrqty()));
                            splpack10literqty.setEnabled(false);
                            splpack11literqty.setText(String.valueOf("SmallPack 11 Liter Qty :- "+ data.getSplpackof11ltrqty()));
                            splpack11literqty.setEnabled(false);
                            splpack12literqty.setText(String.valueOf("SmallPack 12 Liter Qty :- "+ data.getSplpackof12ltrqty()));
                            splpack12literqty.setEnabled(false);
                            splpack13literqty.setText(String.valueOf("SmallPack 13 Liter Qty :- "+ data.getSplpackof13ltrqty()));
                            splpack13literqty.setEnabled(false);
                            splpack15literqty.setText(String.valueOf("SmallPack 15 Liter Qty :- "+ data.getSplpackof15ltrqty()));
                            splpack15literqty.setEnabled(false);
                            splpack18literqty.setText(String.valueOf("SmallPack 18 Liter Qty :- "+ data.getSplpackof18ltrqty()));
                            splpack18literqty.setEnabled(false);
                            splpack20literqty.setText(String.valueOf("SmallPack 20 Liter Qty :- "+ data.getSplpackof20ltrqty()));
                            splpack20literqty.setEnabled(false);
                            splpack26literqty.setText(String.valueOf("SmallPack 26 Liter Qty :- "+ data.getSplpackof26ltrqty()));
                            splpack26literqty.setEnabled(false);
                            splpack50literqty.setText(String.valueOf("SmallPack 50 Liter Qty :- "+ data.getSplpackof50ltrqty()));
                            splpack50literqty.setEnabled(false);
                            splpack210literqty.setText(String.valueOf("SmallPack 210 Liter Qty :- "+ data.getSplpackof210ltrqty()));
                            splpack210literqty.setEnabled(false);
                            splpackboxbucketqty.setText(String.valueOf("SmallPack BoxBucket Qty :- "+ data.getSplpackofboxbuxketltrqty()));
                            splpackboxbucketqty.setEnabled(false);
                            smalltotqty.setText("SmallPack Total Qty :- " + data.getSpltotqty());
                            smalltotqty.setEnabled(false);
                            smalltotweight.setText("SmallPack Weight :- " + data.getSplweight());
                            smalltotweight.setEnabled(false);
                            /*smalltotqty.setVisibility(View.VISIBLE);
                            smalltotweight.setVisibility(View.VISIBLE);
                            smalltotqty.setText(data.getSpltotqty());
                            smalltotqty.setEnabled(false);
                            smalltotweight.setText(data.getSplweight());
                            smalltotweight.setEnabled(false);
                            industotqty.setVisibility(View.GONE);
                            industotwight.setVisibility(View.GONE);*/
                        }
                        else if(data.spltotqty.isEmpty() || data.splweight=="0") {
                            indutialpacktitle.setVisibility(View.VISIBLE);
                            lnlindustrialbarrel.setVisibility(View.VISIBLE);
                            ilpack10literqty.setText(String.valueOf("IndusPack 10 Liter Qty :- " + data.getIlpackof10ltrqty()));
                            ilpack10literqty.setEnabled(false);
                            ilpack18literqty.setText(String.valueOf("IndusPack 18 Liter Qty :- " + data.getIlpackof18ltrqty()));
                            ilpack18literqty.setEnabled(false);
                            ilpack20literqty.setText(String.valueOf("IndusPack 20 Liter Qty :- " + data.getIlpackof20ltrqty()));
                            ilpack20literqty.setEnabled(false);
                            ilpack26literqty.setText(String.valueOf("IndusPack 26 Liter Qty :- " + data.getIlpackof26ltrqty()));
                            ilpack26literqty.setEnabled(false);
                            ilpack50literqty.setText(String.valueOf("IndusPack 50 Liter Qty :- " + data.getIlpackof50ltrqty()));
                            ilpack50literqty.setEnabled(false);
                            ilpack210literqty.setText(String.valueOf("IndusPack 210 Liter Qty :- " + data.getIlpackof50ltrqty()));
                            ilpack210literqty.setEnabled(false);
                            ilpackboxbucketqty.setText(String.valueOf("IndusPack BoxBucket Qty :- " + data.getIlpackofboxbuxketltrqty()));
                            ilpackboxbucketqty.setEnabled(false);
                            industotqty.setText("IndusPack Total Qty :- " + data.getIltotqty());
                            industotqty.setEnabled(false);
                            industotwight.setText("IndusPack Total Weight :- " + data.getIlweight());
                            industotwight.setEnabled(false);
                            /*industotqty.setVisibility(View.VISIBLE);
                            industotwight.setVisibility(View.VISIBLE);
                            industotqty.setText(data.getIltotqty());
                            industotqty.setEnabled(false);
                            industotwight.setText(data.getIlweight());
                            industotwight.setEnabled(false);
                            smalltotqty.setVisibility(View.GONE);
                            smalltotweight.setVisibility(View.GONE);*/
                        }
                        else{
                            indutialpacktitle.setVisibility(View.VISIBLE);
                            lnlindustrialbarrel.setVisibility(View.VISIBLE);
                            ilpack10literqty.setText(String.valueOf("IndusPack 10 Liter Qty :- " + data.getIlpackof10ltrqty()));
                            ilpack10literqty.setEnabled(false);
                            ilpack18literqty.setText(String.valueOf("IndusPack 18 Liter Qty :- " + data.getIlpackof18ltrqty()));
                            ilpack18literqty.setEnabled(false);
                            ilpack20literqty.setText(String.valueOf("IndusPack 20 Liter Qty :- " + data.getIlpackof20ltrqty()));
                            ilpack20literqty.setEnabled(false);
                            ilpack26literqty.setText(String.valueOf("IndusPack 26 Liter Qty :- " + data.getIlpackof26ltrqty()));
                            ilpack26literqty.setEnabled(false);
                            ilpack50literqty.setText(String.valueOf("IndusPack 50 Liter Qty :- " + data.getIlpackof50ltrqty()));
                            ilpack50literqty.setEnabled(false);
                            ilpack210literqty.setText(String.valueOf("IndusPack 210 Liter Qty :- " + data.getIlpackof50ltrqty()));
                            ilpack210literqty.setEnabled(false);
                            ilpackboxbucketqty.setText(String.valueOf("IndusPack BoxBucket Qty :- " + data.getIlpackofboxbuxketltrqty()));
                            ilpackboxbucketqty.setEnabled(false);
                            industotqty.setText("IndusPack Total Qty :- " + data.getIltotqty());
                            industotqty.setEnabled(false);
                            industotwight.setText("IndusPack Total Weight :- " + data.getIlweight());
                            industotwight.setEnabled(false);

                            smallpacktitle.setVisibility(View.VISIBLE);
                            lnlsmallpackqty.setVisibility(View.VISIBLE);
                            splpack7literqty.setText(String.valueOf("SmallPack 7 Liter Qty :- "+ data.getSplpackof7ltrqty()));
                            splpack7literqty.setEnabled(false);
                            splpack7_5literqty.setText(String.valueOf("SmallPack 7.5 Liter Qty :- "+ data.getSplpackof7_5ltrqty()));
                            splpack7_5literqty.setEnabled(false);
                            splpack8_5literqty.setText(String.valueOf("SmallPack 8.5 Liter Qty :- "+ data.getSplpackof8_5ltrqty()));
                            splpack8_5literqty.setEnabled(false);
                            splpack10literqty.setText(String.valueOf("SmallPack 10 Liter Qty :- "+ data.getSplpackof10ltrqty()));
                            splpack10literqty.setEnabled(false);
                            splpack11literqty.setText(String.valueOf("SmallPack 11 Liter Qty :- "+ data.getSplpackof11ltrqty()));
                            splpack11literqty.setEnabled(false);
                            splpack12literqty.setText(String.valueOf("SmallPack 12 Liter Qty :- "+ data.getSplpackof12ltrqty()));
                            splpack12literqty.setEnabled(false);
                            splpack13literqty.setText(String.valueOf("SmallPack 13 Liter Qty :- "+ data.getSplpackof13ltrqty()));
                            splpack13literqty.setEnabled(false);
                            splpack15literqty.setText(String.valueOf("SmallPack 15 Liter Qty :- "+ data.getSplpackof15ltrqty()));
                            splpack15literqty.setEnabled(false);
                            splpack18literqty.setText(String.valueOf("SmallPack 18 Liter Qty :- "+ data.getSplpackof18ltrqty()));
                            splpack18literqty.setEnabled(false);
                            splpack20literqty.setText(String.valueOf("SmallPack 20 Liter Qty :- "+ data.getSplpackof20ltrqty()));
                            splpack20literqty.setEnabled(false);
                            splpack26literqty.setText(String.valueOf("SmallPack 26 Liter Qty :- "+ data.getSplpackof26ltrqty()));
                            splpack26literqty.setEnabled(false);
                            splpack50literqty.setText(String.valueOf("SmallPack 50 Liter Qty :- "+ data.getSplpackof50ltrqty()));
                            splpack50literqty.setEnabled(false);
                            splpack210literqty.setText(String.valueOf("SmallPack 210 Liter Qty :- "+ data.getSplpackof210ltrqty()));
                            splpack210literqty.setEnabled(false);
                            splpackboxbucketqty.setText(String.valueOf("SmallPack BoxBucket Qty :- "+ data.getSplpackofboxbuxketltrqty()));
                            splpackboxbucketqty.setEnabled(false);
                            smalltotqty.setText("SmallPack Total Qty :- " + data.getSpltotqty());
                            smalltotqty.setEnabled(false);
                            smalltotweight.setText("SmallPack Weight :- " + data.getSplweight());
                            smalltotweight.setEnabled(false);
                            /*industotqty.setText(data.getIltotqty());
                            industotqty.setEnabled(false);
                            industotwight.setText(data.getIlweight());
                            industotwight.setEnabled(false);
                            smalltotqty.setText(data.getSpltotqty());
                            smalltotqty.setEnabled(false);
                            smalltotweight.setText(data.getSplweight());
                            smalltotweight.setEnabled(false);*/

                        }
                    }else {
                        Toasty.error(OutwardOut_Truck_Weighment.this, "This Vehicle Number Is Not Available..!", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Log.e("Retrofit", "Error Response Body: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Response_Outward_Tanker_Weighment> call, Throwable t) {

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
    private String getCurrentTime() {
        // Get the current time
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(new Date());
    }

    public void insert(){

        String etintime = intime.getText().toString().trim();
        String unetwt = netwt.getText().toString().trim();
        String etgrossweight = grosswright.getText().toString().trim();
        String etnoofpack = noofpack.getText().toString().trim();
        String uremark = etremark.getText().toString().trim();
        String useal = seal.getText().toString().trim();



        if (!etshdip.getText().toString().isEmpty()){
            try {
                 ushdip = Integer.parseInt(etshdip.getText().toString().trim());
            }catch (NumberFormatException e){
                e.printStackTrace();
            }
        }else {
            Toasty.warning(this,"Shortage Dip Is Empty",Toast.LENGTH_SHORT).show();
//            ushdip=0;
        }

        if (!etshwe.getText().toString().isEmpty()){
            try {
                 ushwe = Integer.parseInt(etshwe.getText().toString().trim());
            }catch (NumberFormatException e){
                e.printStackTrace();
            }
        }else {
            Toasty.warning(this,"Shortage Weight Is Empty",Toast.LENGTH_SHORT).show();
        }

        if (etintime.isEmpty()||etgrossweight.isEmpty()||etnoofpack.isEmpty() ){
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
        }else {
            Model_OutwardOut_Truck_Weighment modelOutwardOutTruckWeighment = new Model_OutwardOut_Truck_Weighment(OutwardId,
                    imgPath1,imgPath2,etintime,unetwt,etgrossweight,etnoofpack,uremark,useal,EmployeId,'P',inOut,
                    vehicleType,ushdip,ushwe);
            dialogHelper.showConfirmationDialog(this, () -> {
                dialogHelper.showProgressDialog(this); // Show progress when confirmed
            Call<Boolean> call = outwardWeighment.updateoutwardouttruckweighment(modelOutwardOutTruckWeighment);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful()&& response.body() && response.body() == true){
                        dialogHelper.hideProgressDialog(); // Hide after response
                       // not available outtime and vehicle no for notification
                        deleteLocalImage(wvehiclenumber);
                    }else {
                        Log.e("Retrofit", "Error Response Body: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    dialogHelper.hideProgressDialog(); // Hide after response
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
                    Toasty.error(OutwardOut_Truck_Weighment.this, "failed..!", Toast.LENGTH_SHORT).show();
                }
            });
            });
        }
    }

    public void UploadImagesAndUpdate() {
        String FileInitial = "OutwardVeh_Out_";
        arrayOfByteArrays[0] = ImgVehicle;
        arrayOfByteArrays[1] = ImgDriver;
        imgPath1 = "GAimages/"+ FileInitial + serialnumber.getText().toString() + ".jpeg";
        for (byte[] byteArray : arrayOfByteArrays) {

            MultipartTask multipartTask = new MultipartTask(byteArray, FileInitial + serialnumber.getText().toString() + ".jpeg", "");
            multipartTask.execute();
            FileInitial = "OutwardDrv_Out_";
        }
        imgPath2 = "GAimages/"+ FileInitial + serialnumber.getText().toString() + ".jpeg";
        FileInitial = "";
        insert();
    }

    public void captureImageFromCamera1(android.view.View view) {
        askCameraPermission(CAMERA_REQUEST_CODE);
    }

    public void captureImageFromCamera2(android.view.View view) {
        askCameraPermission1(CAMERA_REQUEST_CODE1);
    }

    private void askCameraPermission(int requestcode) {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
        } else {
            openCamera(requestcode);
        }
    }

    private void askCameraPermission1(int requestcode1) {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERM_CODE1);
        } else {
            openCamera(requestcode1);
        }
    }

    private void openCamera(int requestCode) {
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (requestCode == CAMERA_REQUEST_CODE) {
            startActivityForResult(camera, CAMERA_REQUEST_CODE);
        } else if (requestCode == CAMERA_REQUEST_CODE1) {
            startActivityForResult(camera, CAMERA_REQUEST_CODE1);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ContentResolver contentResolver = getContentResolver();
        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_REQUEST_CODE && data != null) {
                Bitmap bimage1 = (Bitmap) data.getExtras().get("data");
                if (bimage1 != null) {
                    img1.setImageBitmap(bimage1);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bimage1.compress(Bitmap.CompressFormat.JPEG, 90, baos);
                    image1 = ImageUtils.insertImage(contentResolver, bimage1, "", null);
                    if (image1 != null) {
                        String imagePath = ImageUtils.getImagePath(contentResolver, image1);
                        LocalImgPath[0] = image1;
                    }

                    ImgVehicle = baos.toByteArray();
                } else {
                    // Handle case when no image is captured
                    Toasty.error(this, "No image captured", Toast.LENGTH_SHORT).show();
                }
            } else if (requestCode == CAMERA_REQUEST_CODE1 && data != null) {
                Bitmap bimage2 = (Bitmap) data.getExtras().get("data");
                if (bimage2 != null) {
                    img2.setImageBitmap(bimage2);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bimage2.compress(Bitmap.CompressFormat.JPEG, 90, baos);
                    image2 = ImageUtils.insertImage(contentResolver, bimage2, "", null);
                    if (image2 != null) {
                        String imagePath = ImageUtils.getImagePath(contentResolver, image2);
                        LocalImgPath[1] = image2;
                    }
                    ImgDriver = baos.toByteArray();
                } else {
                    // Handle case when no image is captured
                    Toasty.error(this, "No image captured", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            // Handle case when camera activity is canceled
            Toasty.warning(this, "Camera operation canceled", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteLocalImage(String vehicalnumber) {
        File imageFile;
        try {
            for (Uri imgpath : LocalImgPath) {
                ImageUtils.deleteImage(this,imgpath);
            }
            makeNotification(wvehiclenumber);
            Toasty.success(OutwardOut_Truck_Weighment.this, "Data Inserted Successfully", Toast.LENGTH_SHORT,true).show();
            startActivity(new Intent(OutwardOut_Truck_Weighment.this, Grid_Outward.class));
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void outwardouttruckweighmentpending(View view){
        Intent intent = new Intent(this, Grid_Outward.class);
        startActivity(intent);
    }
}