package com.android.gandharvms.Inward_Tanker_Security;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Inward_Tanker_Security_Viewdata extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<In_Tanker_Security_list>inTankerSecurityLists;
    In_Tanker_Se_Adapter in_tanker_se_adapter;

    FirebaseFirestore db;
    ProgressDialog progressDialog;
//    AlertDialog alertDialog;

    SearchView searchdata;
    Spinner spinner;
//    Context context;

                //date filter

    private Button fetchDataButton;
    private Button dateButton;

    private FirebaseFirestore dbs;

    EditText input_start,input_end;
    Button btn_start,btn_end,btncleardateselection;
    TextView txtTotalCount;
    Calendar calendar = Calendar.getInstance();
    Context context;
    LayoutInflater layoutInflater;
    View showInput;
    String date_start;
    String date_end;
    Locale id = new Locale("in","ID");
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMMM-YYYY",id);

    Button subm;
    FirebaseFirestore rootdb;

     Button startDatePicker;
     Button endDatePicker;
    EditText etserialNumber,etpartyName;
    Button btnsrnumclear,btnptnamclear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inward_tanker_security_viewdata);

        startDatePicker = findViewById(R.id.startdate);
        endDatePicker = findViewById(R.id.enddate);
        btnsrnumclear=findViewById(R.id.btn_srnumberbutton_clear);
        btnptnamclear=findViewById(R.id.btn_partytnamebutton_clear);
        etserialNumber=findViewById(R.id.et_SerialNumber);
        etpartyName=findViewById(R.id.et_PartyName);
        btncleardateselection=findViewById(R.id.btn_clearDateSelectionfields);
        txtTotalCount=findViewById(R.id.tv_TotalCount);

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

        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
//        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        inTankerSecurityLists = new ArrayList<In_Tanker_Security_list>();
        in_tanker_se_adapter = new In_Tanker_Se_Adapter(Inward_Tanker_Security_Viewdata.this,inTankerSecurityLists);

        recyclerView.setAdapter(in_tanker_se_adapter);

        db = FirebaseFirestore.getInstance();
        db.collection("Inward Tanker Security").orderBy("date", Query.Direction.DESCENDING).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        int totalCount = list.size();
                        txtTotalCount.setText("Total count: " + totalCount);
                        inTankerSecurityLists.clear();
                        for (DocumentSnapshot d:list)
                        {
                            In_Tanker_Security_list obj = d.toObject(In_Tanker_Security_list.class);
                            inTankerSecurityLists.add(obj);
                        }
                        //update Adapter
                        in_tanker_se_adapter.notifyDataSetChanged();
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
                CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Inward Tanker Security");
                String searchText = charSequence.toString().trim();
                if (searchText.isEmpty()) {
                    // If search text is empty, fetch all data without any filters
                    collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                int totalCount = task.getResult().size();
                                txtTotalCount.setText("Total count: " + totalCount);
                                inTankerSecurityLists.clear(); // Clear the previous data
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    In_Tanker_Security_list obj = document.toObject(In_Tanker_Security_list.class);
                                    // Check if the object already exists to avoid duplicates
                                    if (!inTankerSecurityLists.contains(obj)) {
                                        inTankerSecurityLists.add(obj);
                                    }
                                }
                                in_tanker_se_adapter.notifyDataSetChanged();
                            } else {
                                Log.w("FirestoreData", "Error getting documents.", task.getException());
                            }
                        }
                    });
                } else {
                    // Create a query with filters for non-empty search text
                    Query query = collectionReference.whereGreaterThanOrEqualTo("SerialNumber", searchText)
                            .whereLessThanOrEqualTo("SerialNumber", searchText + "\uf8ff");

                    query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                int totalCount = task.getResult().size();
                                txtTotalCount.setText("Total count: " + totalCount);
                                inTankerSecurityLists.clear(); // Clear the previous data
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    In_Tanker_Security_list obj = document.toObject(In_Tanker_Security_list.class);
                                    // Check if the object already exists to avoid duplicates
                                    if (!inTankerSecurityLists.contains(obj)) {
                                        inTankerSecurityLists.add(obj);
                                    }
                                }
                                in_tanker_se_adapter.notifyDataSetChanged();
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
                CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Inward Tanker Security");
                String searchText = charSequence.toString().trim();
                if (searchText.isEmpty()) {
                    // If search text is empty, fetch all data without any filters
                    collectionReference
                            .orderBy("date", Query.Direction.DESCENDING)
                            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                int totalCount = task.getResult().size();
                                txtTotalCount.setText("Total count: " + totalCount);
                                inTankerSecurityLists.clear(); // Clear the previous data
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    In_Tanker_Security_list obj = document.toObject(In_Tanker_Security_list.class);
                                    // Check if the object already exists to avoid duplicates
                                    if (!inTankerSecurityLists.contains(obj)) {
                                        inTankerSecurityLists.add(obj);
                                    }
                                }
                                in_tanker_se_adapter.notifyDataSetChanged();
                            } else {
                                Log.w("FirestoreData", "Error getting documents.", task.getException());
                            }
                        }
                    });
                } else {
                    // Create a query with filters for non-empty search text
                    Query query = collectionReference.whereGreaterThanOrEqualTo("partyname", searchText)
                            .whereLessThanOrEqualTo("partyname", searchText + "\uf8ff")
                            .orderBy("date", Query.Direction.DESCENDING);

                    query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                int totalCount = task.getResult().size();
                                txtTotalCount.setText("Total count: " + totalCount);
                                inTankerSecurityLists.clear(); // Clear the previous data
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    In_Tanker_Security_list obj = document.toObject(In_Tanker_Security_list.class);
                                    // Check if the object already exists to avoid duplicates
                                    if (!inTankerSecurityLists.contains(obj)) {
                                        inTankerSecurityLists.add(obj);
                                    }
                                }
                                in_tanker_se_adapter.notifyDataSetChanged();
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


//        Button selectDateButton = findViewById(R.id.selectDateButton);
//
//        selectDateButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showDatePickerDialog();
//            }
//        });

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
    private void clearSelectedDates(){
        startDatePicker.setText("start of Date");
        endDatePicker.setText("End of Data");
        etserialNumber.setText("");
        etpartyName.setText("");
        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
//        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        inTankerSecurityLists = new ArrayList<In_Tanker_Security_list>();
        in_tanker_se_adapter = new In_Tanker_Se_Adapter(Inward_Tanker_Security_Viewdata.this,inTankerSecurityLists);

        recyclerView.setAdapter(in_tanker_se_adapter);

        db = FirebaseFirestore.getInstance();
        db.collection("Inward Tanker Security")
                .orderBy("date", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        int totalCount = list.size();
                        txtTotalCount.setText("Total count: " + totalCount);
                        inTankerSecurityLists.clear(); // Clear the previous data
                        for (DocumentSnapshot d:list)
                        {
                            In_Tanker_Security_list obj = d.toObject(In_Tanker_Security_list.class);
                            inTankerSecurityLists.add(obj);
                        }
                        //update Adapter
                        in_tanker_se_adapter.notifyDataSetChanged();
                    }
                });
    }

    public void fetchDataFromFirestore(String startDate, String endDate){

        CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Inward Tanker Security");
        Query baseQuery = collectionReference.orderBy("date", Query.Direction.DESCENDING);

        if (startDate != null && endDate != null){
            baseQuery = baseQuery.whereGreaterThanOrEqualTo("date", startDate)
                    .whereLessThanOrEqualTo("date", endDate);
        } else if (startDate != null){
            baseQuery = baseQuery.whereGreaterThanOrEqualTo("date", startDate);
        } else if (endDate != null) {
            baseQuery = baseQuery.whereLessThanOrEqualTo("date", endDate);
        }

        baseQuery.orderBy("date", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    int totalCount = task.getResult().size();
                    txtTotalCount.setText("Total count: " + totalCount);
                    inTankerSecurityLists.clear(); // Clear the previous data
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        In_Tanker_Security_list obj = document.toObject(In_Tanker_Security_list.class);
                        inTankerSecurityLists.add(obj);
                    }
                    in_tanker_se_adapter.notifyDataSetChanged();
                } else {
                    Log.w("FirestoreData", "Error getting documents.", task.getException());
                }
            }
        });
    }
//    private void showDatePickerDialog() {
//        Calendar calendar = Calendar.getInstance();
//        int year = calendar.get(Calendar.YEAR);
//        int month = calendar.get(Calendar.MONTH);
//        int day = calendar.get(Calendar.DAY_OF_MONTH);
//
//        DatePickerDialog datePickerDialog = new DatePickerDialog(
//                this,
//                new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
//                        // Handle the selected date
//                        String selectedDate = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay;
//                        fetchDataFromFirestore(selectedDate);
//                    }
//                },
//                year, month, day
//        );
//
//        datePickerDialog.show();
//    }

//    private void fetchDataFromFirestore(String selectedDate) {
//        // Convert the selected date string to a Date object if needed
//        // Perform a Firestore query to get data for the selected date
//
//        CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Inward Tanker Security");
//
//        // Example query: Get documents where "dateField" is equal to the selected date
//        Query query = collectionReference.whereEqualTo("etdate", selectedDate);
//
//        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        // Handle document data
//                        String data = document.getData().toString();
//                        Log.d("FirestoreData", data);
//                    }
//                } else {
//                    // Handle errors
//                    Log.w("FirestoreData", "Error getting documents.", task.getException());
//                }
//            }
//        });
//    }
//



}
