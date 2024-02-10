package com.android.gandharvms.Inward_Tanker_Weighment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.android.gandharvms.Global_Var;
import com.android.gandharvms.LoginWithAPI.Login;
import com.android.gandharvms.LoginWithAPI.LoginMethod;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.LoginWithAPI.Weighment;
import com.android.gandharvms.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class Inward_Tanker_Weighment_Viewdata extends AppCompatActivity {

    RecyclerView recview;
    List<InTanWeighResponseModel> weighdatalist;
    FirebaseFirestore db;
    Intankweighlistdata_adapter intankweighlistdataAdapter;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-YYYY, HH:mm:ss");

    TextView txtTotalCount;
    Button startDatePicker, endDatePicker, btncleardateselection, btnlogout;
    EditText etserialNumber, etpartyName;
    Button btnsrnumclear, btnptnamclear, btnExportExcel;
    String date_start, date_end;
/*
    private ListView listView;
*/

    private Weighment weighmentdetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inward_tanker_weighment_viewdata);

        btnExportExcel = findViewById(R.id.btn_ExportToExcel);

        btnlogout = findViewById(R.id.btn_logoutButton);
        startDatePicker = findViewById(R.id.startdate);
        endDatePicker = findViewById(R.id.enddate);
        btnsrnumclear = findViewById(R.id.btn_srnumberbutton_clear);
        btnptnamclear = findViewById(R.id.btn_partytnamebutton_clear);
        etserialNumber = findViewById(R.id.et_SerialNumber);
        etpartyName = findViewById(R.id.et_PartyName);
        btncleardateselection = findViewById(R.id.btn_clearDateSelectionfields);
        txtTotalCount = findViewById(R.id.tv_TotalCount);

        weighmentdetails= RetroApiClient.getWeighmentDetails();

        recview = findViewById(R.id.recyclerview);
        recview.setLayoutManager(new LinearLayoutManager(this));

        weighdatalist = new ArrayList<>();

        char nextprocess= Global_Var.getInstance().DeptType;
        GetWeighmentListData(nextprocess);
    }
    private void GetWeighmentListData(char nextProcess) {
        Call<List<InTanWeighResponseModel>> call = weighmentdetails.getIntankWeighListData(nextProcess);
        call.enqueue(new Callback<List<InTanWeighResponseModel>>() {
            @Override
            public void onResponse(Call<List<InTanWeighResponseModel>> call, Response<List<InTanWeighResponseModel>> response) {
                if(response.isSuccessful()){
                    List<InTanWeighResponseModel> data=response.body();
                    int totalCount = data.size();
                    txtTotalCount.setText("Total count: " + totalCount);
                    weighdatalist.clear();
                    weighdatalist = data;
                    intankweighlistdataAdapter = new Intankweighlistdata_adapter(weighdatalist);
                    recview.setAdapter(intankweighlistdataAdapter);
                    intankweighlistdataAdapter.notifyDataSetChanged();
                }
                else
                {
                    Log.e("Retrofit", "Error Response Body: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<List<InTanWeighResponseModel>> call, Throwable t) {
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
                Toasty.error(Inward_Tanker_Weighment_Viewdata.this,"failed..!",Toast.LENGTH_SHORT).show();
            }
        });
    }
        /*btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Inward_Tanker_Weighment_Viewdata.this, Login.class));
            }
        });
        startDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(true);
            }
        });
        endDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(false);
            }
        });

        btnsrnumclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etserialNumber.setText("");
            }
        });

        btnptnamclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etpartyName.setText("");
            }
        });


        etserialNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Inward Tanker Weighment");
                String searchText = charSequence.toString().trim();
                if (searchText.isEmpty()) {
                    // If search text is empty, fetch all data without any filters
                    collectionReference.orderBy("Date", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                int totalCount = task.getResult().size();
                                txtTotalCount.setText("Total count: " + totalCount);
                                datalist.clear(); // Clear the previous data
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    In_Tanker_Weighment_list obj = document.toObject(In_Tanker_Weighment_list.class);
                                    // Check if the object already exists to avoid duplicates
                                    if (!datalist.contains(obj)) {
                                        datalist.add(obj);
                                    }
                                }
                                in_tanker_we_adapter.notifyDataSetChanged();
                            } else {
                                Log.w("FirestoreData", "Error getting documents.", task.getException());
                            }
                        }
                    });
                } else {
                    // Create a query with filters for non-empty search text
                    Query query = collectionReference.whereGreaterThanOrEqualTo("serial_number", searchText)
                            .whereLessThanOrEqualTo("serial_number", searchText + "\uf8ff");

                    query.orderBy("Date", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                int totalCount = task.getResult().size();
                                txtTotalCount.setText("Total count: " + totalCount);
                                datalist.clear(); // Clear the previous data
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    In_Tanker_Weighment_list obj = document.toObject(In_Tanker_Weighment_list.class);
                                    // Check if the object already exists to avoid duplicates
                                    if (!datalist.contains(obj)) {
                                        datalist.add(obj);
                                    }
                                }
                                in_tanker_we_adapter.notifyDataSetChanged();
                            } else {
                                Log.w("FirestoreData", "Error getting documents.", task.getException());
                            }
                        }
                    });
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        btncleardateselection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearSelectedDates();
            }
        });
        etpartyName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Inward Tanker Weighment");
                String searchText = charSequence.toString().trim();
                if (searchText.isEmpty()) {
                    // If search text is empty, fetch all data without any filters
                    collectionReference.orderBy("Date", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                int totalCount = task.getResult().size();
                                txtTotalCount.setText("Total count: " + totalCount);
                                datalist.clear(); // Clear the previous data
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    In_Tanker_Weighment_list obj = document.toObject(In_Tanker_Weighment_list.class);
                                    // Check if the object already exists to avoid duplicates
                                    if (!datalist.contains(obj)) {
                                        datalist.add(obj);
                                    }
                                }
                                in_tanker_we_adapter.notifyDataSetChanged();
                            } else {
                                Log.w("FirestoreData", "Error getting documents.", task.getException());
                            }
                        }
                    });
                } else {
                    // Create a query with filters for non-empty search text
                    Query query = collectionReference.whereGreaterThanOrEqualTo("supplier_name", searchText)
                            .whereLessThanOrEqualTo("supplier_name", searchText + "\uf8ff");

                    query.orderBy("Date", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                int totalCount = task.getResult().size();
                                txtTotalCount.setText("Total count: " + totalCount);
                                datalist.clear(); // Clear the previous data
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    In_Tanker_Weighment_list obj = document.toObject(In_Tanker_Weighment_list.class);
                                    // Check if the object already exists to avoid duplicates
                                    if (!datalist.contains(obj)) {
                                        datalist.add(obj);
                                    }
                                }
                                in_tanker_we_adapter.notifyDataSetChanged();
                            } else {
                                Log.w("FirestoreData", "Error getting documents.", task.getException());
                            }
                        }
                    });
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        btnExportExcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the data from the adapter
                List<In_Tanker_Weighment_list> dataList = in_tanker_we_adapter.datalist;
                // Export data to Excel
                exportToExcel(dataList);
            }
        });
    }

    public void showDatePickerDialog(final boolean isStartDate) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        // Array of month abbreviations
        String[] monthAbbreviations = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String monthAbbreviation = monthAbbreviations[monthOfYear];
                        // Month is 0 based, so adding 1 to monthOfYear
                        String selectedDate = dayOfMonth + "/" + monthAbbreviation + "/" + year;

                        if (isStartDate) {
                            date_start = selectedDate;
                            startDatePicker.setText(selectedDate);
                        } else {
                            date_end = selectedDate;
                            endDatePicker.setText(selectedDate);
                        }

                        // Call method to fetch data after selecting the date
                        fetchDataFromFirestore(date_start, date_end);
                    }
                }, year, month, day);

        datePickerDialog.show();
    }

    private void clearSelectedDates() {
        startDatePicker.setText("start of Date");
        endDatePicker.setText("End of Data");
        etserialNumber.setText("");
        etpartyName.setText("");
        recview = findViewById(R.id.recyclerview);
        recview.setLayoutManager(new LinearLayoutManager(this));
        datalist = new ArrayList<>();
        in_tanker_we_adapter = new In_Tanker_we_Adapter(datalist);
        recview.setAdapter(in_tanker_we_adapter);

        db = FirebaseFirestore.getInstance();
        db.collection("Inward Tanker Weighment").orderBy("Date", Query.Direction.DESCENDING).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        int totalCount = list.size();
                        txtTotalCount.setText("Total count: " + totalCount);
                        datalist.clear();
                        for (DocumentSnapshot d : list) {
                            In_Tanker_Weighment_list obj = d.toObject(In_Tanker_Weighment_list.class);
                            datalist.add(obj);
                        }
                        in_tanker_we_adapter.notifyDataSetChanged();
                    }
                });
    }

    public void fetchDataFromFirestore(String startDate, String endDate) {

        CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Inward Tanker Weighment");
        Query baseQuery = collectionReference.orderBy("Date");

        if (startDate != null && endDate != null) {
            baseQuery = baseQuery.whereGreaterThanOrEqualTo("Date", startDate)
                    .whereLessThanOrEqualTo("Date", endDate);
        } else if (startDate != null) {
            baseQuery = baseQuery.whereGreaterThanOrEqualTo("Date", startDate);
        } else if (endDate != null) {
            baseQuery = baseQuery.whereLessThanOrEqualTo("Date", endDate);
        }

        baseQuery.orderBy("Date", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    int totalCount = task.getResult().size();
                    txtTotalCount.setText("Total count: " + totalCount);
                    datalist.clear(); // Clear the previous data
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        In_Tanker_Weighment_list obj = document.toObject(In_Tanker_Weighment_list.class);
                        datalist.add(obj);
                    }
                    in_tanker_we_adapter.notifyDataSetChanged();
                } else {
                    Log.w("FirestoreData", "Error getting documents.", task.getException());
                }
            }
        });
    }

    private void exportToExcel(List<In_Tanker_Weighment_list> datalist) {
        try {
            HSSFWorkbook workbook = new HSSFWorkbook();
            Sheet sheet = workbook.createSheet("InwardTankerWeighmentData");
            // Create header row
            Row headerRow = sheet.createRow(0);

            headerRow.createCell(0).setCellValue("IN TIME");
            headerRow.createCell(1).setCellValue("OUT TIME");
            headerRow.createCell(2).setCellValue("SERIAL NUMBER");
            headerRow.createCell(3).setCellValue("VEHICLE NUMBER");
            headerRow.createCell(4).setCellValue("SUPPLIER NAME");
            headerRow.createCell(5).setCellValue("MATERIAL NAME");
            headerRow.createCell(6).setCellValue("DRIVER MOB NUMBER");
            headerRow.createCell(7).setCellValue("OA/PO NUMBER");
            headerRow.createCell(8).setCellValue("DATE");
            headerRow.createCell(9).setCellValue("GROSS WEIGHT");
            headerRow.createCell(10).setCellValue("REMARK");
            headerRow.createCell(11).setCellValue("CONTAINER NO");
            headerRow.createCell(12).setCellValue("SIGN BY");
            headerRow.createCell(13).setCellValue("SHORTAGE DIP");
            headerRow.createCell(14).setCellValue("SHORTAGE WEIGHT");
            headerRow.createCell(15).setCellValue("INVEHICLEIMAGE");
            headerRow.createCell(16).setCellValue("INDRIVERIMAGE");

            // Populate data rows
            for (int i = 0; i < datalist.size(); i++) {
                Row dataRow = sheet.createRow(i + 1); // Start from the second row (index 1) for data

                In_Tanker_Weighment_list dataItem = datalist.get(i);
                dataRow.createCell(0).setCellValue(dataItem.getIn_Time());
                dataRow.createCell(1).setCellValue(dataItem.getOuttime());
                dataRow.createCell(2).setCellValue(dataItem.getSerial_number());
                dataRow.createCell(3).setCellValue(dataItem.getVehicle_number());
                dataRow.createCell(4).setCellValue(dataItem.getSupplier_name());
                dataRow.createCell(5).setCellValue(dataItem.getMaterial_name());
                dataRow.createCell(6).setCellValue(dataItem.getDriver_Number());
                dataRow.createCell(7).setCellValue(dataItem.getOA_number());
                dataRow.createCell(8).setCellValue(dateFormat.format(dataItem.getDate().toDate()));
                dataRow.createCell(9).setCellValue(dataItem.getGross_Weight());
                dataRow.createCell(10).setCellValue(dataItem.getBatch_Number());
                dataRow.createCell(11).setCellValue(dataItem.getContainer_No());
                dataRow.createCell(12).setCellValue(dataItem.getSign_By());
                dataRow.createCell(13).setCellValue(dataItem.getShortage_Dip());
                dataRow.createCell(14).setCellValue(dataItem.getShortage_weight());
                dataRow.createCell(15).setCellValue(dataItem.getInVehicleImage());
                dataRow.createCell(16).setCellValue(dataItem.getInDriverImage());
            }
            // Save the workbook
            saveWorkBook(workbook);
        }catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

    private void saveWorkBook(HSSFWorkbook hssfWorkBook) {
        try {
            StorageManager storageManager = (StorageManager) getSystemService(STORAGE_SERVICE);
            StorageVolume storageVolume = storageManager.getStorageVolumes().get(0);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                String dateTimeSuffix = new SimpleDateFormat("ddMMMyyyy_HHmmss", Locale.getDefault()).format(new Date());
                int counter = 1;
                String fileName = "Inward Tanker Weighment Data_" + dateTimeSuffix + ".xls";
                File outputfile = new File(storageVolume.getDirectory().getPath() + "/Download/" + fileName);
                while (outputfile.exists()) {
                    counter++;
                    fileName = "Inward Tanker Weighment Data_" + dateTimeSuffix + "_" + counter + ".xls";
                    outputfile = new File(storageVolume.getDirectory().getPath() + "/Download/" + fileName);
                }
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(outputfile);
                    hssfWorkBook.write(fileOutputStream);
                    fileOutputStream.close();
                    hssfWorkBook.close();
                    Toast.makeText(this, "Excel File Created Successfully", Toast.LENGTH_SHORT).show();
                } catch (Exception ex) {
                    Toast.makeText(this, "File Creation Failed", Toast.LENGTH_SHORT).show();
                    throw new RuntimeException(ex);
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }*/
}