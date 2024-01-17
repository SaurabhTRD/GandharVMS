package com.android.gandharvms.Inward_Truck_Weighment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.gandharvms.Inward_Tanker_Security.Inward_Tanker_Security;
import com.google.firebase.Timestamp;

import com.android.gandharvms.FcmNotificationsSender;
import com.android.gandharvms.Inward_Tanker_Security.In_Tanker_Security_list;
import com.android.gandharvms.Inward_Tanker_Weighment.Inward_Tanker_Weighment;
import com.android.gandharvms.Inward_Truck;
import com.android.gandharvms.Inward_Truck_Security.In_Truck_security_list;
import com.android.gandharvms.Menu;
import com.android.gandharvms.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class Inward_Truck_weighment extends AppCompatActivity {

    //prince
    private SharedPreferences sharedPreferences;
    private final int MAX_LENGTH=10;

    EditText etint,etserialnumber,etvehicalnumber,etsupplier,etmaterial,etdriver,etoanumber,etdate,etgrossweight,etsignby;

    Button intsubmit;
    Button view;
    FirebaseFirestore trwdbroot;
    DatePickerDialog picker;
    EditText datetimeTextview;
    TimePickerDialog tpicker;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int CAMERA_PERM_CODE1 = 100;
    private static final int CAMERA_PERM_CODE = 101;
    private static final int CAMERA_REQUEST_CODE = 102;
    private static final int CAMERA_REQUEST_CODE1 = 103;
    ImageView img1, img2;
    Uri image1, image2;
    private String imgPath1, imgPath2;

    FirebaseStorage storage;

    final Calendar calendar = Calendar.getInstance();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-YYYY, HH:mm:ss");
    Timestamp timestamp = new Timestamp(calendar.getTime());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inward_truck_weighment);
        //Send Notification to all
        FirebaseMessaging.getInstance().subscribeToTopic("cCeBwmIxRz2HRH6A6blM94:APA91bHCinIPsP8HvzUL3qLI9EXBo0l8wAS5pJmz2UdxahlFpe_FMoRC0SyH9DgcbBoCXrwZy01YMr_QZcpSFnXOhoKGE2S17Bn39xW7MTLDyh0UnvwqLvdcotNoqDl6UyJ5oCBC990z");

        //Prince
         //Send Notification to All
        sharedPreferences = getSharedPreferences("TruckWeighment", MODE_PRIVATE);

        etint = (EditText) findViewById(R.id.etintime);
        etserialnumber=(EditText) findViewById(R.id.ettrwserialnumber);
        etvehicalnumber=(EditText) findViewById(R.id.ettrwvehicalno);
        etsupplier=(EditText) findViewById(R.id.ettrwsupplier);
        etmaterial=(EditText) findViewById(R.id.ettrwmaterial);
        etdriver=(EditText) findViewById(R.id.ettrdriverno);
        etoanumber=(EditText) findViewById(R.id.ettroa);
        etdate= (EditText) findViewById(R.id.ettrdate);
        etgrossweight=(EditText) findViewById(R.id.ettrgrossweight);
        /*ettareweight=(EditText) findViewById(R.id.ettrtareweight);
        etnetweight=(EditText) findViewById(R.id.ettrnetweight);*/
        etsignby =(EditText) findViewById(R.id.ettrsignby);

        // listing Data button
        img1 = findViewById(R.id.ettrimageView1);
        img2 = findViewById(R.id.ettrimageView2);
        storage = FirebaseStorage.getInstance();

        view = findViewById(R.id.viewclick);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Inward_Truck_weighment.this, Inward_Truck_Weighment_Viewdata.class));
            }
        });

        etdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                // Array of month abbreviations
                String[] monthAbbreviations = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
                picker = new DatePickerDialog(Inward_Truck_weighment.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Use the month abbreviation from the array
                        String monthAbbreviation = monthAbbreviations[month];
                        // etdate.setText(dayOfMonth + "/" + monthAbbreviation + "/" + year);
                        etdate.setText(dateFormat.format(calendar.getTime()));
                    }
                }, year, month, day);
                picker.show();
            }
        });

        /*etvehicalnumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > MAX_LENGTH) {
                    etvehicalnumber.removeTextChangedListener(this);
                    String trimmedText = editable.toString().substring(0, MAX_LENGTH);
                    etvehicalnumber.setText(trimmedText);
                    etvehicalnumber.setSelection(MAX_LENGTH); // Move cursor to the end
                    etvehicalnumber.addTextChangedListener(this);
                }else if (editable.length() < MAX_LENGTH) {
                    // Show an error message for less than 10 digits
                    etvehicalnumber.setError("Invalid format. Enter 10 Character. \n Vehicle No Format - ST00AA9999 OR YYBR9999AA");
                } else {
                    // Clear any previous error message when valid
                    etvehicalnumber.setError(null);
                }
            }
        });*/

        etvehicalnumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    FetchVehicleDetails(etvehicalnumber.getText().toString().trim());
                }
            }

        });
        /*etdriver.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > MAX_LENGTH) {
                    etdriver.removeTextChangedListener(this);
                    String trimmedText = editable.toString().substring(0, MAX_LENGTH);
                    etdriver.setText(trimmedText);
                    etdriver.setSelection(MAX_LENGTH); // Move cursor to the end
                    etdriver.addTextChangedListener(this);
                }else if (editable.length() < MAX_LENGTH) {
                    // Show an error message for less than 10 digits
                    etdriver.setError("Invalid format. Enter 10 digits");
                } else {
                    // Clear any previous error message when valid
                    etdriver.setError(null);
                }
            }
        });
*/
        intsubmit=(Button) findViewById(R.id.wesubmit);
        trwdbroot=FirebaseFirestore.getInstance();

        etint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hours = calendar.get(Calendar.HOUR_OF_DAY);
                int mins = calendar.get(Calendar.MINUTE);
                tpicker = new TimePickerDialog(Inward_Truck_weighment.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        c.set(Calendar.MINUTE,minute);

                        // Set the formatted time to the EditText
                        etint.setText(hourOfDay +":"+ minute );
                    }
                },hours,mins,false);
                tpicker.show();
            }
        });


        intsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadimg(image1, image2);
                intrinsert();
            }
        });
    }
    private String getCurrentTime() {
        // Get the current time
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(new Date());
    }

    public void makeNotification(String vehicleNo,String outTime){
        FcmNotificationsSender notificationsSender = new FcmNotificationsSender("cCeBwmIxRz2HRH6A6blM94:APA91bHCinIPsP8HvzUL3qLI9EXBo0l8wAS5pJmz2UdxahlFpe_FMoRC0SyH9DgcbBoCXrwZy01YMr_QZcpSFnXOhoKGE2S17Bn39xW7MTLDyh0UnvwqLvdcotNoqDl6UyJ5oCBC990z",
                "Inward Truck Weighment Process Done..!",
                "Vehicle Number:-" + vehicleNo + " has Completed Weighment Process at " + outTime,
                getApplicationContext(), Inward_Truck_weighment.this);
        notificationsSender.SendNotifications();
    }

    public void intrinsert()
    {
        String intime = etint.getText().toString().trim();
        String serial_Number=etserialnumber.getText().toString().trim();
        String vehicalnumber=etvehicalnumber.getText().toString().trim();
        String supplier=etsupplier.getText().toString().trim();
        String material=etmaterial.getText().toString().trim();
        String Driver = etdriver.getText().toString().trim();
        /*String customer = etcustomer.getText().toString().trim();*/
        String oanumber = etoanumber.getText().toString().trim();
        String date = etdate.getText().toString().trim();
        String Grossweight = etgrossweight.getText().toString().trim();
        /*String Tareweight = ettareweight.getText().toString().trim();
        String netweight = etnetweight.getText().toString().trim();*/
        /*String Density = etdensity.getText().toString().trim();
        String Batchno = etbatchno.getText().toString().trim();*/
        String signby = etsignby.getText().toString().trim();
       /* String DateTime = etdatetime.getText().toString().trim();*/
        String outTime = getCurrentTime();


        if ( intime.isEmpty()|| serial_Number.isEmpty()|| vehicalnumber.isEmpty()|| supplier.isEmpty()|| material.isEmpty()|| Driver.isEmpty() || oanumber.isEmpty()|| date.isEmpty()||Grossweight.isEmpty()
        || signby.isEmpty()){
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
        }
        else {
            Map<String,String>trweitems= new HashMap<>();
            trweitems.put("In_Time",etint.getText().toString().trim());
            trweitems.put("Serial_Number",etserialnumber.getText().toString().trim());
            trweitems.put("Vehicle_Number",etvehicalnumber.getText().toString().trim());
            trweitems.put("Supplier",etsupplier.getText().toString().trim());
            trweitems.put("Material",etmaterial.getText().toString().trim());
            trweitems.put("Driver_No",etdriver.getText().toString().trim());
            /*trweitems.put("Customer",etcustomer.getText().toString().trim());*/
            trweitems.put("Oa_Number",etoanumber.getText().toString().trim());
            trweitems.put("Date",etdate.getText().toString().trim());
            trweitems.put("Gross_Weight",etgrossweight.getText().toString().trim());
            /*trweitems.put("Tare_Weight",ettareweight.getText().toString().trim());
            trweitems.put("Net_Weight",etnetweight.getText().toString().trim());*/
            /*trweitems.put("Density",etdensity.getText().toString().trim());
            trweitems.put("Batch_No",etbatchno.getText().toString().trim());*/
            trweitems.put("Sign_By",etsignby.getText().toString().trim());
            /*trweitems.put("Date_Time",etdatetime.getText().toString().trim());*/
            trweitems.put("outTime",outTime.toString());
            trweitems.put("InVehicleImage", imgPath1);
            trweitems.put("InDriverImage", imgPath2);


            makeNotification(etvehicalnumber.getText().toString(),outTime.toString());
            trwdbroot.collection("Inward Truck Weighment").add(trweitems)
                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {

                            etint.setText("");
                            etserialnumber.setText("");
                            etvehicalnumber.setText("");
                            etsupplier.setText("");
                            etmaterial.setText("");
                            etdriver.setText("");
                            /*etcustomer.setText("");*/
                            etoanumber.setText("");
                            etdate.setText("");
                            etgrossweight.setText("");
                            /*ettareweight.setText("");
                            etnetweight.setText("");*/
                            /*etdensity.setText("");
                            etbatchno.setText("");*/
                            etsignby.setText("");
                            /*etdatetime.setText("");*/

                            Toast.makeText(Inward_Truck_weighment.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                        }
                    });
            Intent intent= new Intent(this, Inward_Truck.class);
            startActivity(intent);
        }
    }
    public void uploadimg(Uri Image1, Uri Image2) {
        StorageReference storageReference = storage.getReference();
        if (Image1 != null) {
            String InVehicleImage = "image1_" + UUID.randomUUID().toString() + ".jpeg";
            StorageReference imgref1 = storageReference.child("/WeighmentTruckImage1" + "/" + InVehicleImage);
            imgPath1 = "/WeighmentTruckImage1%2F" + InVehicleImage;
            imgref1.putFile(Image1)
                    .addOnSuccessListener(taskSnapshot -> {
                        imgref1.getDownloadUrl().addOnSuccessListener(uri -> {
                            final String imageUrl = uri.toString();
                            img1.setImageURI(Uri.parse(imageUrl));
                        });
                    }).addOnFailureListener(e -> {
                        Toast.makeText(Inward_Truck_weighment.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    });

        }

        if (Image2 != null) {
            String InDriverImage = "image2_" + UUID.randomUUID().toString() + ".jpeg";
            StorageReference imgref2 = storageReference.child("/WeighmentTruckImage2" + "/" + InDriverImage);
            imgPath2 = "/WeighmentTruckImage2%2F" + InDriverImage;
            imgref2.putFile(Image2)
                    .addOnSuccessListener(taskSnapshot -> {
                        imgref2.getDownloadUrl().addOnSuccessListener(uri -> {
                            final String imageUrl = uri.toString();
                            img2.setImageURI(Uri.parse(imageUrl));
                        });
                    }).addOnFailureListener(e -> {
                        Toast.makeText(Inward_Truck_weighment.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }
    }
    //  image upload firebase
    public void captureImageFromCamera1(android.view.View view) {
        askCameraPermission(CAMERA_REQUEST_CODE);
    }

    public void captureImageFromCamera2(android.view.View view) {
        askCameraPermission1(CAMERA_REQUEST_CODE1);
    }

    private void askCameraPermission(int requestcode) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
        } else {
            openCamera(requestcode);
        }
    }

    private void askCameraPermission1(int requestcode1) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
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
        if (requestCode == CAMERA_REQUEST_CODE) {
            Bitmap bimage1 = (Bitmap) data.getExtras().get("data");
            img1.setImageBitmap(bimage1);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bimage1.compress(Bitmap.CompressFormat.JPEG, 90, baos);
            String path = MediaStore.Images.Media.insertImage(getContentResolver(), bimage1, "title1", null);
            image1 = Uri.parse(path);


        } else if (requestCode == CAMERA_REQUEST_CODE1) {
            Bitmap bimage2 = (Bitmap) data.getExtras().get("data");
            img2.setImageBitmap(bimage2);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bimage2.compress(Bitmap.CompressFormat.JPEG, 90, baos);
            String path = MediaStore.Images.Media.insertImage(getContentResolver(), bimage2, "title2", null);
            image2 = Uri.parse(path);
        }
    }

    public void onBackPressed(){
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
        finish();
    }
    public void FetchVehicleDetails(@NonNull String VehicleNo) {
        CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Inward Truck Security");
        String searchText = VehicleNo.trim();
        Query query = collectionReference.whereEqualTo("VehicalNumber", searchText)
                .whereNotEqualTo("Intime","" );
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    int totalCount = task.getResult().size();
                    if(totalCount == 0) {
                        etserialnumber.setText("");
                        etvehicalnumber.setText("");
                        etsupplier.setText("");
                        etmaterial.setText("");
                        etoanumber.setText("");
                        etdriver.setText("");
                        etdate.setText("");
                        /*etnetweight.setText("");*/
                        etvehicalnumber.requestFocus();
                        Toast.makeText(Inward_Truck_weighment.this, "Vehicle Number not Available for Weighment", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            In_Truck_security_list obj = document.toObject(In_Truck_security_list.class);
                            // Check if the object already exists to avoid duplicates
                            if (totalCount > 0) {
                                etserialnumber.setText(obj.getSerialnumber());
                                etvehicalnumber.setText(obj.getVehicalNumber());
                                etsupplier.setText(obj.getSupplier());
                                etmaterial.setText(obj.getMaterial());
                                etdate.setText(dateFormat.format(obj.getDate().toDate()));
                                /*etnetweight.setText(obj.getNetweight());
                                etnetweight.setEnabled(false);*/
                                etint.requestFocus();
                                etint.callOnClick();
                            }
                        }
                    }
                } else {
                    Log.w("FirestoreData", "Error getting documents.", task.getException());
                }
            }
        });
    }
}