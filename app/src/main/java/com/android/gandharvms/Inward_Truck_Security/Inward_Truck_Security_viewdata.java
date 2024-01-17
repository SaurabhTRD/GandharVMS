package com.android.gandharvms.Inward_Truck_Security;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

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

public class Inward_Truck_Security_viewdata extends AppCompatActivity {

    RecyclerView recview;
    ArrayList<In_Truck_security_list> datalist;

    FirebaseFirestore db;

    In_Truck_Security_Adapter inTruckSecurityAdapter;
    String date_start,date_end;
    Button startDatePicker,endDatePicker,btnsrnumclear,btnptnamclear,btnclearSelectedDates;
    EditText etserialNumber,etSupplierName;
    TextView txtTotalCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inward_truck_security_viewdata);


        startDatePicker = findViewById(R.id.startdate);
        endDatePicker = findViewById(R.id.enddate);
        etserialNumber=findViewById(R.id.et_SerialNumber);
        etSupplierName=findViewById(R.id.et_SupplierName);
        btnsrnumclear=findViewById(R.id.btn_srnumberbutton_clear);
        btnptnamclear=findViewById(R.id.btn_partytnamebutton_clear);

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

        btnsrnumclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etserialNumber.setText("");
            }
        });

        btnptnamclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etSupplierName.setText("");
            }
        });
        etserialNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Inward Truck Security");
                String searchText = charSequence.toString().trim();
                if (searchText.isEmpty()) {
                    // If search text is empty, fetch all data without any filters
                    collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                int totalCount = task.getResult().size();
                                txtTotalCount.setText("Total count: " + totalCount);
                                datalist.clear(); // Clear the previous data
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    In_Truck_security_list obj = document.toObject(In_Truck_security_list.class);
                                    // Check if the object already exists to avoid duplicates
                                    if (!datalist.contains(obj)) {
                                        datalist.add(obj);
                                    }
                                }
                                inTruckSecurityAdapter.notifyDataSetChanged();
                            } else {
                                Log.w("FirestoreData", "Error getting documents.", task.getException());
                            }
                        }
                    });
                } else {
                    // Create a query with filters for non-empty search text
                    Query query = collectionReference.whereGreaterThanOrEqualTo("serialnumber", searchText)
                            .whereLessThanOrEqualTo("serialnumber", searchText + "\uf8ff");

                    query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                int totalCount = task.getResult().size();
                                txtTotalCount.setText("Total count: " + totalCount);
                                datalist.clear(); // Clear the previous data
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    In_Truck_security_list obj = document.toObject(In_Truck_security_list.class);
                                    // Check if the object already exists to avoid duplicates
                                    if (!datalist.contains(obj)) {
                                        datalist.add(obj);
                                    }
                                }
                                inTruckSecurityAdapter.notifyDataSetChanged();
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

        etSupplierName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Inward Truck Security");
                String searchText = charSequence.toString().trim();
                if (searchText.isEmpty()) {
                    // If search text is empty, fetch all data without any filters
                    collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                int totalCount = task.getResult().size();
                                txtTotalCount.setText("Total count: " + totalCount);
                                datalist.clear(); // Clear the previous data
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    In_Truck_security_list obj = document.toObject(In_Truck_security_list.class);
                                    // Check if the object already exists to avoid duplicates
                                    if (!datalist.contains(obj)) {
                                        datalist.add(obj);
                                    }
                                }
                                inTruckSecurityAdapter.notifyDataSetChanged();
                            } else {
                                Log.w("FirestoreData", "Error getting documents.", task.getException());
                            }
                        }
                    });
                } else {
                    // Create a query with filters for non-empty search text
                    Query query = collectionReference.whereGreaterThanOrEqualTo("Supplier", searchText)
                            .whereLessThanOrEqualTo("Supplier", searchText + "\uf8ff");

                    query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                int totalCount = task.getResult().size();
                                txtTotalCount.setText("Total count: " + totalCount);
                                datalist.clear(); // Clear the previous data
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    In_Truck_security_list obj = document.toObject(In_Truck_security_list.class);
                                    // Check if the object already exists to avoid duplicates
                                    if (!datalist.contains(obj)) {
                                        datalist.add(obj);
                                    }
                                }
                                inTruckSecurityAdapter.notifyDataSetChanged();
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

        recview = (RecyclerView) findViewById(R.id.recyclerview);

        recview.setLayoutManager(new LinearLayoutManager(this));
        datalist = new ArrayList<>();
        inTruckSecurityAdapter = new In_Truck_Security_Adapter(datalist);
        recview.setAdapter(inTruckSecurityAdapter);

        db= FirebaseFirestore.getInstance();
        db.collection("Inward Truck Security").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        int totalCount = list.size();
                        txtTotalCount.setText("Total count: " + totalCount);
                        datalist.clear(); // Clear the previous data
                        for (DocumentSnapshot d:list)
                        {
                            In_Truck_security_list obj = d.toObject(In_Truck_security_list.class);
                            datalist.add(obj);
                        }
                        inTruckSecurityAdapter.notifyDataSetChanged();
                    }
                });

    }
    private void clearSelectedDates(){
        startDatePicker.setText("start of Date");
        endDatePicker.setText("End of Data");
        etserialNumber.setText("");
        etSupplierName.setText("");
        recview = (RecyclerView) findViewById(R.id.recyclerview);

        recview.setLayoutManager(new LinearLayoutManager(this));
        datalist = new ArrayList<>();
        inTruckSecurityAdapter = new In_Truck_Security_Adapter(datalist);
        recview.setAdapter(inTruckSecurityAdapter);

        db= FirebaseFirestore.getInstance();
        db.collection("Inward Truck Security").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        int totalCount = list.size();
                        txtTotalCount.setText("Total count: " + totalCount);
                        datalist.clear(); // Clear the previous data
                        for (DocumentSnapshot d:list)
                        {
                            In_Truck_security_list obj = d.toObject(In_Truck_security_list.class);
                            datalist.add(obj);
                        }
                        inTruckSecurityAdapter.notifyDataSetChanged();
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

        CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Inward Truck Security");
        Query baseQuery = collectionReference.orderBy("date");

        if (startDate != null && endDate != null){
            baseQuery = baseQuery.whereGreaterThanOrEqualTo("date", startDate)
                    .whereLessThanOrEqualTo("date", endDate);
        } else if (startDate != null){
            baseQuery = baseQuery.whereGreaterThanOrEqualTo("date", startDate);
        } else if (endDate != null) {
            baseQuery = baseQuery.whereLessThanOrEqualTo("date", endDate);
        }

        baseQuery.orderBy("date", Query.Direction.ASCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    int totalCount = task.getResult().size();
                    txtTotalCount.setText("Total count: " + totalCount);
                    datalist.clear(); // Clear the previous data
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        In_Truck_security_list obj = document.toObject(In_Truck_security_list.class);
                        datalist.add(obj);
                    }
                    inTruckSecurityAdapter.notifyDataSetChanged();
                } else {
                    Log.w("FirestoreData", "Error getting documents.", task.getException());
                }
            }
        });
    }

}