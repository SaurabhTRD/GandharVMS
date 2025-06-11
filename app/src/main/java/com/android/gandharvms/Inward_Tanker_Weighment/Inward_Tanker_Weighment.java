package com.android.gandharvms.Inward_Tanker_Weighment;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.gandharvms.FcmNotificationsSender;
import com.android.gandharvms.Global_Var;
import com.android.gandharvms.InwardCompletedGrid.GridCompleted;
import com.android.gandharvms.Inward_Tanker;
import com.android.gandharvms.Inward_Tanker_Laboratory.Inward_Tanker_Laboratory;
import com.android.gandharvms.Inward_Tanker_Security.In_Tanker_Security_list;
import com.android.gandharvms.Inward_Tanker_Security.Inward_Tanker_Security;
import com.android.gandharvms.Inward_Tanker_Security.grid;
import com.android.gandharvms.Inward_Truck;
import com.android.gandharvms.Inward_Truck_Weighment.Inward_Truck_weighment;
import com.android.gandharvms.Inward_Truck_store.ExtraMaterial;
import com.android.gandharvms.LoginWithAPI.Login;
import com.android.gandharvms.LoginWithAPI.LoginMethod;
import com.android.gandharvms.LoginWithAPI.ResponseModel;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.LoginWithAPI.Weighment;
import com.android.gandharvms.Menu;
import com.android.gandharvms.NotificationAlerts.NotificationCommonfunctioncls;
import com.android.gandharvms.Outward_Tanker_Weighment.Outward_Tanker_weighment;
import com.android.gandharvms.R;
import com.android.gandharvms.RegisterwithAPI.Register;
import com.android.gandharvms.Util.ImageUtils;
import com.android.gandharvms.Util.MultipartTask;
import com.android.gandharvms.Util.dialogueprogreesbar;
import com.android.gandharvms.submenu.submenu_Inward_Tanker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.common.reflect.TypeToken;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;


public class Inward_Tanker_Weighment extends NotificationCommonfunctioncls {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int CAMERA_PERM_CODE1 = 100;
    private static final int CAMERA_PERM_CODE = 101;
    private static final int CAMERA_REQUEST_CODE = 102;
    private static final int CAMERA_REQUEST_CODE1 = 103;
    public static String Tanker;
    public static String Truck;
    final Calendar calendar = Calendar.getInstance();
    private final int MAX_LENGTH = 10;
    private final String dateTimeString = "";
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private final String EmployeId = Global_Var.getInstance().EmpId;
    EditText etint, etserialnumber, etvehicalno, etsuppliername, etdriverno, etoano, etdate,
            etgrossweight, etremark, etsignby, etcontainer, etshortagedip, etshortageweight, etweighqty, qtyuom;
    Button wesubmit;
    FirebaseFirestore wedbroot;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-YYYY, HH:mm:ss");
    Timestamp timestamp = new Timestamp(calendar.getTime());
    DatePickerDialog picker;
    Button view;
    FirebaseStorage storage;
    ImageView img1, img2;
    Uri image1, image2;
    byte[] ImgDriver, ImgVehicle;
    byte[][] arrayOfByteArrays = new byte[2][];
    Uri[] LocalImgPath = new Uri[2];
    TimePickerDialog tpicker;
    String[] weighqtyuom = {"Ton", "Litre", "KL", "Kgs", "pcs", "NA"};
    Integer weighqtyUomNumericValue = 1;
    AutoCompleteTextView weighautoCompleteTextView1;
    Map<String, Integer> weighqtyUomMapping = new HashMap<>();
    ArrayAdapter<String> weighqtyuomdrop;
    List<String> teamList = new ArrayList<>();
    ImageView btnlogout, btnhome;
    TextView username, empid;
    dialogueprogreesbar dialogHelper = new dialogueprogreesbar();
    private Weighment weighmentdetails;
    private String imgPath1, imgPath2;
    private SharedPreferences sharedPreferences;
    private int autoGeneratedNumber;
    private String token, vehicle_No;
    private int id, inwardid;
    private LoginMethod userDetails;
    private int insertweighqty;
    private int insertnetweighqtyUom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inward_tanker_weighment);

        vehicle_No = getIntent().getStringExtra("vehicleNo");

        //Send Notification to all
        FirebaseMessaging.getInstance().subscribeToTopic(token);
        userDetails = RetroApiClient.getLoginApi();

        img1 = findViewById(R.id.intanvehimageView1);
        img2 = findViewById(R.id.intandriverimageView2);

        etint = findViewById(R.id.etintime);
        etserialnumber = findViewById(R.id.etserialnumber);
        etvehicalno = findViewById(R.id.etvehicalno);
        etsuppliername = findViewById(R.id.etsuppliername);
        //etmaterialname = findViewById(R.id.etmaterialname);
        etdriverno = findViewById(R.id.etdriverno);
        etoano = findViewById(R.id.etoano);
        etdate = findViewById(R.id.etdate);
        etgrossweight = findViewById(R.id.etgrossweight);
        etremark = findViewById(R.id.etremark);
        etsignby = findViewById(R.id.etsignby);
        etcontainer = findViewById(R.id.container);
        etweighqty = findViewById(R.id.etitweighqty);
        qtyuom = findViewById(R.id.itweighqtyuomtanker);
        //Call Api method
        weighmentdetails = RetroApiClient.getWeighmentDetails();
        setupHeader();

        sharedPreferences = getSharedPreferences("TankerWeighment", MODE_PRIVATE);
        if (sharedPreferences != null) {
            if (getIntent().hasExtra("VehicleNumber")) {
                FetchVehicleDetails(getIntent().getStringExtra("VehicleNumber"), Global_Var.getInstance().MenuType, nextProcess, inOut);
            }
        }

        String vehicleNo = getIntent().getStringExtra("vehicle_number");
        String mode = getIntent().getStringExtra("mode"); // "update" or null
        if (vehicleNo != null && !vehicleNo.isEmpty()) {
            ReadWeighmentData(vehicleNo, mode);
        }

        etweighqty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not needed for this implementation
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Not needed for this implementation
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String currentText = etweighqty.getText().toString();
                if (editable.length() > 0 && editable.length() <= 8) {
                    // Clear any previous error message when valid
                    etweighqty.setError(null);
                } else {
                    String trimmedText = editable.toString().substring(0, Math.min(editable.length(), 8));
                    if (!currentText.equals(trimmedText)) {
                        // Only set text and move cursor if the modification is not the desired text
                        etweighqty.setText(trimmedText);
                        etweighqty.setSelection(trimmedText.length()); // Move cursor to the end
                    }
                }
            }
        });

        etdriverno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not needed for this implementation
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Not needed for this implementation
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String currentText = etdriverno.getText().toString();
                if (editable.length() > 0 && editable.length() <= 16) {
                    // Clear any previous error message when valid
                    etdriverno.setError(null);
                } else {
                    String trimmedText = editable.toString().substring(0, Math.min(editable.length(), 8));
                    if (!currentText.equals(trimmedText)) {
                        // Only set text and move cursor if the modification is not the desired text
                        etdriverno.setText(trimmedText);
                        etdriverno.setSelection(trimmedText.length()); // Move cursor to the end
                    }
                }
            }
        });

        storage = FirebaseStorage.getInstance();
        //view button
        view = findViewById(R.id.dbview);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inward_Tanker_Weighment.this, grid.class);
                startActivity(intent);
            }
        });

        etint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                String time = format.format(calendar.getTime());
                etint.setText(time);
            }
        });

        etvehicalno.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    FetchVehicleDetails(etvehicalno.getText().toString().trim(), vehicleType, nextProcess, inOut);
                }
            }
        });

        wesubmit = findViewById(R.id.wesubmit);
        wedbroot = FirebaseFirestore.getInstance();

        wesubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (image1 == null || image2 == null) {
                    Toasty.warning(Inward_Tanker_Weighment.this, "Please Upload Image", Toast.LENGTH_SHORT).show();
                } else {
                    UploadImagesAndInsert();
                }
                //  uploadimg(image1, image2);
            }
        });
        //ReadWeighmentData("MH04KU3499");

//        // Get the vehicle number from intent extras
//        String vehicleNo = getIntent().getStringExtra("vehicleNo");
//
//        if (vehicleNo != null && !vehicleNo.isEmpty()) {
//            ReadWeighmentData(vehicleNo);
//        } else {
//            Toasty.error(this, "Vehicle number is missing", Toast.LENGTH_SHORT).show();
//        }
    }

    public void showUpdateDialog(View view, int position, String currentVehicleNo, String serialNo) {
        //Send Notification to all
        FirebaseMessaging.getInstance().subscribeToTopic(token);
        userDetails = RetroApiClient.getLoginApi();

        img1 = findViewById(R.id.intanvehimageView1);
        img2 = findViewById(R.id.intandriverimageView2);

        etint = findViewById(R.id.etintime);
        etserialnumber = findViewById(R.id.etserialnumber);
        etvehicalno = findViewById(R.id.etvehicalno);
        etsuppliername = findViewById(R.id.etsuppliername);
        //etmaterialname = findViewById(R.id.etmaterialname);
        etdriverno = findViewById(R.id.etdriverno);
        etoano = findViewById(R.id.etoano);
        etdate = findViewById(R.id.etdate);
        etgrossweight = findViewById(R.id.etgrossweight);
        etremark = findViewById(R.id.etremark);
        etsignby = findViewById(R.id.etsignby);
        etcontainer = findViewById(R.id.container);
        etweighqty = findViewById(R.id.etitweighqty);
        qtyuom = findViewById(R.id.itweighqtyuomtanker);
        //Call Api method
        weighmentdetails = RetroApiClient.getWeighmentDetails();
        setupHeader();

        sharedPreferences = getSharedPreferences("TankerWeighment", MODE_PRIVATE);
        if (sharedPreferences != null) {
            if (getIntent().hasExtra("VehicleNumber")) {
                FetchVehicleDetails(getIntent().getStringExtra("VehicleNumber"), Global_Var.getInstance().MenuType, nextProcess, inOut);
            }
        }

        etweighqty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not needed for this implementation
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Not needed for this implementation
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String currentText = etweighqty.getText().toString();
                if (editable.length() > 0 && editable.length() <= 8) {
                    // Clear any previous error message when valid
                    etweighqty.setError(null);
                } else {
                    String trimmedText = editable.toString().substring(0, Math.min(editable.length(), 8));
                    if (!currentText.equals(trimmedText)) {
                        // Only set text and move cursor if the modification is not the desired text
                        etweighqty.setText(trimmedText);
                        etweighqty.setSelection(trimmedText.length()); // Move cursor to the end
                    }
                }
            }
        });

        etdriverno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not needed for this implementation
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Not needed for this implementation
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String currentText = etdriverno.getText().toString();
                if (editable.length() > 0 && editable.length() <= 16) {
                    // Clear any previous error message when valid
                    etdriverno.setError(null);
                } else {
                    String trimmedText = editable.toString().substring(0, Math.min(editable.length(), 8));
                    if (!currentText.equals(trimmedText)) {
                        // Only set text and move cursor if the modification is not the desired text
                        etdriverno.setText(trimmedText);
                        etdriverno.setSelection(trimmedText.length()); // Move cursor to the end
                    }
                }
            }
        });

        storage = FirebaseStorage.getInstance();
        //view button
        view = findViewById(R.id.dbview);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inward_Tanker_Weighment.this, grid.class);
                startActivity(intent);
            }
        });

        etint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                String time = format.format(calendar.getTime());
                etint.setText(time);
            }
        });

        etvehicalno.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    FetchVehicleDetails(etvehicalno.getText().toString().trim(), vehicleType, nextProcess, inOut);
                }
            }
        });

        wesubmit = findViewById(R.id.wesubmit);
        wedbroot = FirebaseFirestore.getInstance();

        wesubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (image1 == null || image2 == null) {
                    Toasty.warning(Inward_Tanker_Weighment.this, "Please Upload Image", Toast.LENGTH_SHORT).show();
                } else {
                    UploadImagesAndInsert();
                }
                //  uploadimg(image1, image2);
            }
        });
    }

    public void makeNotification(String vehicleNumber, String outTime) {
        Call<List<ResponseModel>> call = userDetails.getUsersListData();
        call.enqueue(new Callback<List<ResponseModel>>() {
            @Override
            public void onResponse(Call<List<ResponseModel>> call, Response<List<ResponseModel>> response) {
                if (response.isSuccessful()) {
                    List<ResponseModel> userList = response.body();
                    if (userList != null) {
                        for (ResponseModel resmodel : userList) {
                            String specificrole = "Sampling";
                            if (specificrole.equals(resmodel.getDepartment())) {
                                token = resmodel.getToken();

                                FcmNotificationsSender notificationsSender = new FcmNotificationsSender(
                                        token,
                                        "Inward Tanker Weighment Process Done..!",
                                        "Vehicle Number:-" + vehicleNumber + " has completed Weighment process at " + outTime,
                                        getApplicationContext(),
                                        Inward_Tanker_Weighment.this
                                );
                                notificationsSender.triggerSendNotification();
                            }
                        }
                    }
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
                Toasty.error(Inward_Tanker_Weighment.this, "failed..!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(new Date());
    }

    public void weinsertdata() {

        String intime = etint.getText().toString().trim();
        String serialnumber = etserialnumber.getText().toString().trim();
        String vehicelnumber = etvehicalno.getText().toString().trim();
        String suppliername = etsuppliername.getText().toString().trim();
        //String materialname = etmaterialname.getText().toString().trim();
        String driverno = etdriverno.getText().toString().trim();
        String oan = etoano.getText().toString().trim();
        String date = etdate.getText().toString().trim();
        String grossweight = etgrossweight.getText().toString().trim();
        String tareweight = "0";
        String netweight = "0";
        if (!etweighqty.getText().toString().isEmpty()) {
            try {
                insertweighqty = Integer.parseInt(etweighqty.getText().toString().trim());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        if (!weighqtyUomNumericValue.toString().isEmpty()) {
            try {
                insertnetweighqtyUom = Integer.parseInt(weighqtyUomNumericValue.toString().trim());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        String remark = etremark.getText().toString().trim();
        String signby = etsignby.getText().toString().trim();
        String container = etcontainer.getText().toString().trim();
        String outTime = getCurrentTime();//Insert out Time Directly to the Database


        if (intime.isEmpty() || serialnumber.isEmpty() || vehicelnumber.isEmpty() || suppliername.isEmpty() ||
                driverno.isEmpty() || oan.isEmpty() || grossweight.isEmpty() ||
                signby.isEmpty() || container.isEmpty()) {
            Toasty.warning(this, "All fields must be filled", Toast.LENGTH_SHORT, true).show();
        } else {
            ItInsweighrequestmodel weighReqModel = new ItInsweighrequestmodel(inwardid, intime, outTime, grossweight,
                    netweight, tareweight, remark, signby, container, imgPath1, imgPath2, serialnumber,
                    vehicelnumber, EmployeId, suppliername, oan, driverno, 'M', inOut, vehicleType,
                    EmployeId, "", "", "", insertweighqty, insertnetweighqtyUom);
            dialogHelper.showConfirmationDialog(this, () -> {
                dialogHelper.showProgressDialog(this); // Show progress when confirmed
                Call<Boolean> call = weighmentdetails.itinsertweighdata(weighReqModel);
                call.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if (response.isSuccessful() && response.body() != null && response.body()) {
                            dialogHelper.hideProgressDialog(); // Hide after response
                            Log.d("Registration", "Response Body: " + response.body());
                            deleteLocalImage(vehicelnumber, outTime);
                        } else {
                            dialogHelper.hideProgressDialog();
                            Toasty.error(Inward_Tanker_Weighment.this, "Data Insertion Failed..!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        dialogHelper.hideProgressDialog(); // Hide after failure
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
                        Toasty.error(Inward_Tanker_Weighment.this, "failed..!", Toast.LENGTH_SHORT).show();
                    }
                });
            });
        }
    }

    public void UploadImagesAndInsert() {
        String FileInitial = "InVeh_In_";
        arrayOfByteArrays[0] = ImgVehicle;
        arrayOfByteArrays[1] = ImgDriver;
        imgPath1 = "GAimages/" + FileInitial + etserialnumber.getText().toString() + ".jpeg";
        for (byte[] byteArray : arrayOfByteArrays) {

            MultipartTask multipartTask = new MultipartTask(byteArray, FileInitial + etserialnumber.getText().toString() + ".jpeg", "");
            multipartTask.execute();
            FileInitial = "InDrv_In_";
        }
        imgPath2 = "GAimages/" + FileInitial + etserialnumber.getText().toString() + ".jpeg";
        FileInitial = "";
        weinsertdata();
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

    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
        finish();
    }


    public void FetchVehicleDetails(@NonNull String vehicleNo, String vehicleType, char NextProcess, char inOut) {
        Call<InTanWeighResponseModel> call = weighmentdetails.getWeighbyfetchVehData(vehicleNo, vehicleType, NextProcess, inOut);
        call.enqueue(new Callback<InTanWeighResponseModel>() {
            @Override
            public void onResponse(Call<InTanWeighResponseModel> call, Response<InTanWeighResponseModel> response) {
                if (response.isSuccessful()) {
                    InTanWeighResponseModel data = response.body();
                    if (data.getVehicleNo() != "" && data.getVehicleNo() != null) {
                        id = data.getId();
                        inwardid = data.getInwardId();
                        etserialnumber.setText(data.getSerialNo());
                        etserialnumber.setEnabled(false);
                        etvehicalno.setText(data.getVehicleNo());
                        etvehicalno.setEnabled(false);
                        //etmaterialname.setText(data.getMaterial());
                        //etmaterialname.setEnabled(false);
                        etdate.setText(data.getDate());
                        etdate.setEnabled(false);
                        etsuppliername.setText(data.getPartyName());
                        etsuppliername.setEnabled(false);
                        etweighqty.setText(String.valueOf(data.getSecNetWeight()));
                        etweighqty.setEnabled(false);
                        qtyuom.setText(data.getUnitOfNetWeight());
                        qtyuom.setEnabled(false);
                        String extraMaterialsJson = data.getExtramaterials();
                        Log.d("JSON Debug", "Extra Materials JSON: " + extraMaterialsJson);
                        List<ExtraMaterial> extraMaterials = parseExtraMaterials(extraMaterialsJson);
                        Log.d("JSON Debug", "Parsed Extra Materials Size: " + extraMaterials.size());
                        createExtraMaterialViews(extraMaterials);
                    } else {
                        Toasty.error(Inward_Tanker_Weighment.this, "This Vehicle Number Is Not Available..!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("Retrofit", "Error Response Body: " + response.code());
                }
            }


            @Override
            public void onFailure(Call<InTanWeighResponseModel> call, Throwable t) {
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
                Toasty.error(Inward_Tanker_Weighment.this, "failed..!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<ExtraMaterial> parseExtraMaterials(String jsonString) {
        if (jsonString == null || jsonString.trim().isEmpty()) {
            Log.e("JSON Parser", "JSON string is null or empty");
            return new ArrayList<>(); // Return an empty list if JSON is invalid
        }

        try {
            Log.d("JSON Parser", "JSON String: " + jsonString);
            Gson gson = new Gson();
            Type listType = new TypeToken<List<ExtraMaterial>>() {
            }.getType();
            return gson.fromJson(jsonString, listType);
        } catch (JsonSyntaxException e) {
            Log.e("JSON Parser", "Failed to parse JSON: " + jsonString, e);
            return new ArrayList<>(); // Return an empty list in case of parsing error
        }
    }

    private void validateJson(String jsonString) {
        try {
            new JsonParser().parse(jsonString);
            Log.d("JSON Validator", "Valid JSON: " + jsonString);
        } catch (JsonSyntaxException e) {
            Log.e("JSON Validator", "Invalid JSON: " + jsonString, e);
        }
    }

    public void createExtraMaterialViews(List<ExtraMaterial> extraMaterials) {
        LinearLayout linearLayout = findViewById(R.id.layout_materiallistweighment); // Ensure this is the correct ID

        // Clear previous views if any
        linearLayout.removeAllViews();

        for (ExtraMaterial extraMaterial : extraMaterials) {
            View materialView = getLayoutInflater().inflate(R.layout.allmaterial, null);

            EditText materialEditText = materialView.findViewById(R.id.etallmaterialmet);
            EditText qtyEditText = materialView.findViewById(R.id.etallmaterialqty);
            Spinner uomSpinner = materialView.findViewById(R.id.allmaterialspinner_team);

            materialEditText.setText(extraMaterial.getMaterial());
            materialEditText.setEnabled(false);
            qtyEditText.setText(extraMaterial.getQty());
            qtyEditText.setEnabled(false);

            List<String> teamList = Arrays.asList("NA", "Ton", "Litre", "KL", "Kgs", "Pcs", "M3", "Meter", "Feet"); // or fetch it dynamically
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, teamList);
            uomSpinner.setAdapter(arrayAdapter);
            uomSpinner.setEnabled(false);

            setSpinnerValue(uomSpinner, extraMaterial.getQtyuom());

            // Add the material view to the linear layout
            linearLayout.addView(materialView);
        }
        String extraMaterialsString = convertExtraMaterialsListToString(extraMaterials);
    }

    private String convertExtraMaterialsListToString(List<ExtraMaterial> extraMaterials) {
        StringBuilder result = new StringBuilder();

        for (ExtraMaterial extraMaterial : extraMaterials) {
            String materialString = convertExtraMaterialToString(extraMaterial);

            // Add this string to the result
            result.append(materialString).append("\n"); // Separate entries by a newline or any other delimiter
        }

        return result.toString();
    }

    private String convertExtraMaterialToString(ExtraMaterial extraMaterial) {
        String material = extraMaterial.getMaterial();
        String qty = extraMaterial.getQty();
        String qtyuom = extraMaterial.getQtyuom();

        // Concatenate fields into a single string
        return (material + "," + qty + "," + qtyuom);
    }

    private void setSpinnerValue(Spinner spinner, String value) {
        SpinnerAdapter adapter = spinner.getAdapter();
        if (adapter != null) {
            for (int i = 0; i < adapter.getCount(); i++) {
                if (adapter.getItem(i).toString().equals(value)) {
                    spinner.setSelection(i);
                    break;
                }
            }
        } else {
            Log.e("Spinner Error", "Spinner adapter is null");
        }
    }

    private String getWeightUnit(int unitCode) {
        switch (unitCode) {
            case 1:
                return "kl";
            case 2:
                return "Ton";
            case 3:
                return "Litre";
            case 4:
                return "KL";
            case 5:
                return "Kgs";
            case 6:
                return "Pcs";
            case 7:
                return "M3";
            default:
                return "unknown";// or any default value you want to set
        }
    }

    // Function to upload image to server and delete local image after successful upload
    private void deleteLocalImage(String vehicalnumber, String outTime) {
        File imageFile;
        try {
            for (Uri imgpath : LocalImgPath) {
                ImageUtils.deleteImage(this, imgpath);
            }
            Toasty.success(Inward_Tanker_Weighment.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
            makeNotification(vehicalnumber, outTime);
            Intent intent = new Intent(Inward_Tanker_Weighment.this, grid.class);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void inweighmenttankergridclick(View view) {
        Intent intent = new Intent(this, it_in_weigh_Completedgrid.class);
        startActivity(intent);
    }

    public void ReadWeighmentData(@NonNull String vehicleNo, @Nullable String mode) {
        Call<InTanWeighResponseModel> call = weighmentdetails.getWeighbyfetchVehData(vehicleNo, "x", 'x', 'x');
        call.enqueue(new Callback<InTanWeighResponseModel>() {
            @Override
            public void onResponse(Call<InTanWeighResponseModel> call, Response<InTanWeighResponseModel> response) {
                if (response.isSuccessful()) {
                    InTanWeighResponseModel data = response.body();
                    if (data.getVehicleNo() != null && !data.getVehicleNo().isEmpty()) {
                        id = data.getId();
                        inwardid = data.getInwardId();
                        etint.setVisibility(View.GONE);
                        etserialnumber.setText(data.getSerialNo());
                        etserialnumber.setEnabled(false);
                        etvehicalno.setText(data.getVehicleNo());
                        etvehicalno.setEnabled(true); // editable only if not update mode
                        etdate.setText(data.getDate());
                        etdate.setEnabled(false);
                        etsuppliername.setText(data.getPartyName());
                        etsuppliername.setEnabled(true);
                        etweighqty.setText(String.valueOf(data.getSecNetWeight()));
                        etweighqty.setEnabled(true);
                        qtyuom.setText(data.getUnitOfNetWeight());
                        qtyuom.setEnabled(true);
                        etdriverno.setText(data.getDriver_MobileNo());
                        etdriverno.setEnabled(true);
                        etoano.setText(data.getOA_PO_number());
                        etoano.setEnabled(true);
                        etint.setText(data.getInTime());
                        //etint.setEnabled(false);
                        etgrossweight.setText(data.getGrossWeight());
                        etgrossweight.setEnabled(false);
                        etcontainer.setText(data.getContainerNo());
                        etcontainer.setEnabled(false);
                        etremark.setVisibility(View.GONE);
                        etsignby.setText(data.getSignBy());
                        etsignby.setEnabled(false);
                        img1.setVisibility(View.GONE);
                        img2.setVisibility(View.GONE);
                        String extraMaterialsJson = data.getExtramaterials();
                        Log.d("JSON Debug", "Extra Materials JSON: " + extraMaterialsJson);
                        List<ExtraMaterial> extraMaterials = parseExtraMaterials(extraMaterialsJson);
                        Log.d("JSON Debug", "Parsed Extra Materials Size: " + extraMaterials.size());
                        createExtraMaterialViews(extraMaterials);
                        // Show and configure submit button only in update mode
                        if ("update".equals(mode)) {
                            wesubmit.setVisibility(View.VISIBLE);
                            wesubmit.setText("Update");
                            wesubmit.setOnClickListener(v -> {
                                weupdatedata();
                            });
                        }

                    } else {
                        Toasty.warning(Inward_Tanker_Weighment.this, "This Vehicle is Completed Inward Tanker Process", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<InTanWeighResponseModel> call, Throwable t) {
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
                Toasty.error(Inward_Tanker_Weighment.this, "failed..!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void weupdatedata() {
        String vehicelnumber = etvehicalno.getText().toString().trim();
        String suppliername = etsuppliername.getText().toString().trim();
        String driverno = etdriverno.getText().toString().trim();
        String oan = etoano.getText().toString().trim();

        if (vehicelnumber.isEmpty() || suppliername.isEmpty() ||
                driverno.isEmpty() || oan.isEmpty()) {
            Toasty.warning(Inward_Tanker_Weighment.this, "All fields must be filled", Toast.LENGTH_SHORT, true).show();
        } else {
            ItUpdweighrequestmodel weighReqModel = new ItUpdweighrequestmodel();
            if (!etweighqty.getText().toString().isEmpty()) {
                try {
                    insertweighqty = Integer.parseInt(etweighqty.getText().toString().trim());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            if (!weighqtyUomNumericValue.toString().isEmpty()) {
                try {
                    insertnetweighqtyUom = Integer.parseInt(weighqtyUomNumericValue.toString().trim());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            weighReqModel.InwardId = inwardid;
            weighReqModel.WeighQty = insertweighqty;
            weighReqModel.WeightQtyUOM = insertnetweighqtyUom;
            weighReqModel.UpdatedBy = EmployeId;
            weighReqModel.VehicleNo = etvehicalno.getText().toString().trim();
            weighReqModel.PartyName = etsuppliername.getText().toString().trim();
            weighReqModel.OA_PO_number = etoano.getText().toString().trim();
            weighReqModel.Driver_MobileNo = etdriverno.getText().toString().trim();
            Call<Boolean> call = weighmentdetails.itupdateweighdata(weighReqModel);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body() != null && response.body()) {
                        Log.d("Registration", "Response Body: " + response.body());
                        Toasty.success(Inward_Tanker_Weighment.this, "Data Updated successfully..!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Inward_Tanker_Weighment.this, grid.class);
                        startActivity(intent);
                    } else {
                        Toasty.error(Inward_Tanker_Weighment.this, "Data Insertion Failed..!", Toast.LENGTH_SHORT).show();
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
                    Toasty.error(Inward_Tanker_Weighment.this, "failed..!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}