package com.android.gandharvms.Inward_Tanker_Production;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.gandharvms.Inward_Tanker_Laboratory.In_Tanker_lab_Adapter;
import com.android.gandharvms.Inward_Tanker_Laboratory.In_Tanker_lab_list;
import com.android.gandharvms.Inward_Tanker_Production.In_Tanker_View_Lab_Report_Adapter;
import com.android.gandharvms.Inward_Tanker_Production.In_Tanker_View_Lab_Report_List;
import com.android.gandharvms.Inward_Tanker_Security.In_Tanker_Security_list;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Tanker_Production_Lab_Report_Data extends AppCompatActivity {
    RecyclerView recyclerview;

    ArrayList<In_Tanker_View_Lab_Report_List>  labDataViewList;

    FirebaseFirestore db;
    In_Tanker_View_Lab_Report_Adapter InTankerLabDataAdapter;
    Button startDatePicker,endDatePicker,btnvehnumclear,btnmtnamclear,btnclearSelectedDates;
    TextView txtTotalCount;
    EditText etVehicleNumber,etMaterialName;
    String date_start,date_end;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tanker_production_lab_report_data);

        startDatePicker = findViewById(R.id.startdate);
        endDatePicker = findViewById(R.id.enddate);
        etVehicleNumber=findViewById(R.id.et_VehicleNumber);
        etMaterialName=findViewById(R.id.et_MaterialName);
        btnvehnumclear=findViewById(R.id.btn_vehnumberbutton_clear);
        btnmtnamclear=findViewById(R.id.btn_materialnamebutton_clear);

        txtTotalCount=findViewById(R.id.tv_TotalCount);
        btnclearSelectedDates=findViewById(R.id.btn_clearDateSelectionfields);

        btnclearSelectedDates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearSelectedDates();
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
        fetchDataFromFirestore(null, null);

        btnvehnumclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etVehicleNumber.setText("");
            }
        });

        btnmtnamclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etMaterialName.setText("");
            }
        });
        etVehicleNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Inward Tanker Laboratory");
                String searchText = charSequence.toString().trim();
                if (searchText.isEmpty()) {
                    // If search text is empty, fetch all data without any filters
                    collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                int totalCount = task.getResult().size();
                                txtTotalCount.setText("Total count: " + totalCount);
                                labDataViewList.clear(); // Clear the previous data
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    In_Tanker_View_Lab_Report_List obj = document.toObject(In_Tanker_View_Lab_Report_List.class);
                                    // Check if the object already exists to avoid duplicates
                                    if (!labDataViewList.contains(obj)) {
                                        labDataViewList.add(obj);
                                    }
                                }
                                InTankerLabDataAdapter.notifyDataSetChanged();
                            } else {
                                Log.w("FirestoreData", "Error getting documents.", task.getException());
                            }
                        }
                    });
                } else {
                    // Create a query with filters for non-empty search text
                    Query query = collectionReference.whereGreaterThanOrEqualTo("Vehicle_Number", searchText)
                            .whereLessThanOrEqualTo("Vehicle_Number", searchText + "\uf8ff");

                    query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                int totalCount = task.getResult().size();
                                txtTotalCount.setText("Total count: " + totalCount);
                                labDataViewList.clear(); // Clear the previous data
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    In_Tanker_View_Lab_Report_List obj = document.toObject(In_Tanker_View_Lab_Report_List.class);
                                    // Check if the object already exists to avoid duplicates
                                    if (!labDataViewList.contains(obj)) {
                                        labDataViewList.add(obj);
                                    }
                                }
                                InTankerLabDataAdapter.notifyDataSetChanged();
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

        etMaterialName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Inward Tanker Laboratory");
                String searchText = charSequence.toString().trim();

                if (searchText.isEmpty()) {
                    // If search text is empty, fetch all data without any filters
                    collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                int totalCount = task.getResult().size();
                                txtTotalCount.setText("Total count: " + totalCount);
                                labDataViewList.clear(); // Clear the previous data
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    In_Tanker_View_Lab_Report_List obj = document.toObject(In_Tanker_View_Lab_Report_List.class);
                                    // Check if the object already exists to avoid duplicates
                                    if (!labDataViewList.contains(obj)) {
                                        labDataViewList.add(obj);
                                    }
                                }
                                InTankerLabDataAdapter.notifyDataSetChanged();
                            } else {
                                Log.w("FirestoreData", "Error getting documents.", task.getException());
                            }
                        }
                    });
                } else {
                    // Create a query with filters for non-empty search text
                    Query query = collectionReference.whereGreaterThanOrEqualTo("Material", searchText)
                            .whereLessThanOrEqualTo("Material", searchText + "\uf8ff");

                    query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                int totalCount = task.getResult().size();
                                txtTotalCount.setText("Total count: " + totalCount);
                                labDataViewList.clear(); // Clear the previous data
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    In_Tanker_View_Lab_Report_List obj = document.toObject(In_Tanker_View_Lab_Report_List.class);
                                    // Check if the object already exists to avoid duplicates
                                    if (!labDataViewList.contains(obj)) {
                                        labDataViewList.add(obj);
                                    }
                                }
                                InTankerLabDataAdapter.notifyDataSetChanged();
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

        recyclerview=(RecyclerView) findViewById(R.id.labrecyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        labDataViewList= new ArrayList<>();
        InTankerLabDataAdapter= new In_Tanker_View_Lab_Report_Adapter(labDataViewList);
        recyclerview.setAdapter(InTankerLabDataAdapter);
        db= FirebaseFirestore.getInstance();
        db.collection("Inward Tanker Laboratory").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    int totalCount = task.getResult().size();
                    txtTotalCount.setText("Total count: " + totalCount);
                    labDataViewList.clear(); // Clear the previous data
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        In_Tanker_View_Lab_Report_List obj = document.toObject(In_Tanker_View_Lab_Report_List.class);
                        // Check if the object already exists to avoid duplicates
                        if (!labDataViewList.contains(obj)) {
                            labDataViewList.add(obj);
                        }
                    }
                    InTankerLabDataAdapter.notifyDataSetChanged();
                } else {
                    Log.w("FirestoreData", "Error getting documents.", task.getException());
                }
            }
        });
    }
    public void showDatePickerDialog(final boolean isStartDate){
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
                        String selectedDate = dayOfMonth + "/" + monthAbbreviation  + "/" + year;

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
    public void fetchDataFromFirestore(String startDate, String endDate){

        CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Inward Tanker Laboratory");
        Query baseQuery = collectionReference.orderBy("Date_and_Time");

        if (startDate != null && endDate != null){
            baseQuery = baseQuery.whereGreaterThanOrEqualTo("Date_and_Time", startDate)
                    .whereLessThanOrEqualTo("Date_and_Time", endDate);
        } else if (startDate != null){
            baseQuery = baseQuery.whereGreaterThanOrEqualTo("Date_and_Time", startDate);
        } else if (endDate != null) {
            baseQuery = baseQuery.whereLessThanOrEqualTo("Date_and_Time", endDate);
        }

        baseQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    int totalCount = task.getResult().size();
                    txtTotalCount.setText("Total count: " + totalCount);
                    labDataViewList.clear(); // Clear the previous data
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        In_Tanker_View_Lab_Report_List obj = document.toObject(In_Tanker_View_Lab_Report_List.class);
                        labDataViewList.add(obj);
                    }
                    InTankerLabDataAdapter.notifyDataSetChanged();
                } else {
                    Log.w("FirestoreData", "Error getting documents.", task.getException());
                }
            }
        });
    }
    private void clearSelectedDates(){
        startDatePicker.setText("start of Date");
        endDatePicker.setText("End of Data");
        etVehicleNumber.setText("");
        etMaterialName.setText("");
        recyclerview=(RecyclerView) findViewById(R.id.labrecyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        labDataViewList= new ArrayList<>();
        InTankerLabDataAdapter= new In_Tanker_View_Lab_Report_Adapter(labDataViewList);
        recyclerview.setAdapter(InTankerLabDataAdapter);
        db= FirebaseFirestore.getInstance();
        db.collection("Inward Tanker Laboratory").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    int totalCount = task.getResult().size();
                    txtTotalCount.setText("Total count: " + totalCount);
                    labDataViewList.clear(); // Clear the previous data
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        In_Tanker_View_Lab_Report_List obj = document.toObject(In_Tanker_View_Lab_Report_List.class);
                        // Check if the object already exists to avoid duplicates
                        if (!labDataViewList.contains(obj)) {
                            labDataViewList.add(obj);
                        }
                    }
                    InTankerLabDataAdapter.notifyDataSetChanged();
                } else {
                    Log.w("FirestoreData", "Error getting documents.", task.getException());
                }
            }
        });
    }
}