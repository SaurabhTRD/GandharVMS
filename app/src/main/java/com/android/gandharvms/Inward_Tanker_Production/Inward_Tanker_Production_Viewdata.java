package com.android.gandharvms.Inward_Tanker_Production;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
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

import com.android.gandharvms.Global_Var;
import com.android.gandharvms.Inward_Tanker_Security.Inward_Tanker_Security_Viewdata;
import com.android.gandharvms.Inward_Tanker_Security.RetroApiclient_In_Tanker_Security;
import com.android.gandharvms.LoginWithAPI.Login;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class Inward_Tanker_Production_Viewdata extends AppCompatActivity {

    RecyclerView recview;
    ArrayList<In_Tanker_Production_list> datalist;
    FirebaseFirestore db;
    In_Tanker_Pro_Adapter inTankerProAdapter;
    Button btntanknumclear, btnmaterialnumclear, startDatePicker, endDatePicker, btnclearSelectedDates, btnlogout;
    EditText etTankNumber, etMaterialName;
    String date_start, date_end;
    TextView txtTotalCount;

    private API_In_Tanker_production apiInTankerProduction;
    List<ListingResponse_InTankerproduction> productionlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inward_tanker_production_viewdata);

        startDatePicker = findViewById(R.id.startdate);
        endDatePicker = findViewById(R.id.enddate);

        btnlogout = findViewById(R.id.btn_logoutButton);
        txtTotalCount = findViewById(R.id.tv_TotalCount);
        btnclearSelectedDates = findViewById(R.id.btn_clearDateSelectionfields);
        btntanknumclear = findViewById(R.id.btn_sTnnumberbutton_clear);
        btnmaterialnumclear = findViewById(R.id.btn_MaterialNamebutton_clear);
        etTankNumber = findViewById(R.id.et_TankNumber);
        etMaterialName = findViewById(R.id.et_ProMaterialName);

//        datalist = new ArrayList<>();
//        inTankerProAdapter = new In_Tanker_Pro_Adapter(datalist);
//        recview.setAdapter(inTankerProAdapter);

        apiInTankerProduction = RetroApiclient_In_Tanker_Security.getinproductionApi();
        recview = findViewById(R.id.recyclerview);
        recview.setLayoutManager(new LinearLayoutManager(this));
        productionlist = new ArrayList<>();
        char nextprocess= Global_Var.getInstance().DeptType;
        GetproductionlistData(nextprocess);



//        db = FirebaseFirestore.getInstance();
//        db.collection("Inward Tanker Production").orderBy("con_unload_DT", Query.Direction.DESCENDING).get()
//                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
//                        int totalCount = list.size();
//                        txtTotalCount.setText("Total count: " + totalCount);
//                        datalist.clear();
//                        for (DocumentSnapshot d : list) {
//                            In_Tanker_Production_list obj = d.toObject(In_Tanker_Production_list.class);
//                            datalist.add(obj);
//                        }
//                        inTankerProAdapter.notifyDataSetChanged();
//                    }
//                });

//        btnlogout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(Inward_Tanker_Production_Viewdata.this, Login.class));
//            }
//        });
//        startDatePicker.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showDatePickerDialog(true);
//            }
//        });
//        endDatePicker.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showDatePickerDialog(false);
//            }
//        });

//        btnclearSelectedDates.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                clearSelectedDates();
//            }
//        });
//        etTankNumber.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Inward Tanker Production");
//                String searchtext = charSequence.toString().trim();
//                if (searchtext.isEmpty()) {
//                    // If search text is empty, fetch all data without any filters
//                    collectionReference.orderBy("con_unload_DT", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                        @Override
//                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                            if (task.isSuccessful()) {
//                                int totalCount = task.getResult().size();
//                                txtTotalCount.setText("Total count: " + totalCount);
//                                datalist.clear(); // Clear the previous data
//                                for (QueryDocumentSnapshot document : task.getResult()) {
//                                    In_Tanker_Production_list obj = document.toObject(In_Tanker_Production_list.class);
//                                    // Check if the object already exists to avoid duplicates
//                                    if (!datalist.contains(obj)) {
//                                        datalist.add(obj);
//                                    }
//                                }
//                                inTankerProAdapter.notifyDataSetChanged();
//                            } else {
//                                Log.w("FirestoreData", "Error getting documents.", task.getException());
//                            }
//                        }
//                    });
//                } else {
//                    // Create a query with filters for non-empty search text
//                    Query query = collectionReference.whereGreaterThanOrEqualTo("Tank_Number", searchtext)
//                            .whereLessThanOrEqualTo("Tank_Number", searchtext + "\uf8ff");
//
//                    query.orderBy("con_unload_DT", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                        @Override
//                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                            if (task.isSuccessful()) {
//                                int totalCount = task.getResult().size();
//                                txtTotalCount.setText("Total count: " + totalCount);
//                                datalist.clear(); // Clear the previous data
//                                for (QueryDocumentSnapshot document : task.getResult()) {
//                                    In_Tanker_Production_list obj = document.toObject(In_Tanker_Production_list.class);
//                                    // Check if the object already exists to avoid duplicates
//                                    if (!datalist.contains(obj)) {
//                                        datalist.add(obj);
//                                    }
//                                }
//                                inTankerProAdapter.notifyDataSetChanged();
//                            } else {
//                                Log.w("FirestoreData", "Error getting documents.", task.getException());
//                            }
//                        }
//                    });
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });

//        etMaterialName.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Inward Tanker Production");
//                String searchtext = charSequence.toString().trim();
//                if (searchtext.isEmpty()) {
//                    // If search text is empty, fetch all data without any filters
//                    collectionReference.orderBy("con_unload_DT", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                        @Override
//                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                            if (task.isSuccessful()) {
//                                int totalCount = task.getResult().size();
//                                txtTotalCount.setText("Total count: " + totalCount);
//                                datalist.clear(); // Clear the previous data
//                                for (QueryDocumentSnapshot document : task.getResult()) {
//                                    In_Tanker_Production_list obj = document.toObject(In_Tanker_Production_list.class);
//                                    // Check if the object already exists to avoid duplicates
//                                    if (!datalist.contains(obj)) {
//                                        datalist.add(obj);
//                                    }
//                                }
//                                inTankerProAdapter.notifyDataSetChanged();
//                            } else {
//                                Log.w("FirestoreData", "Error getting documents.", task.getException());
//                            }
//                        }
//                    });
//                } else {
//                    // Create a query with filters for non-empty search text
//                    Query query = collectionReference.whereGreaterThanOrEqualTo("Material", searchtext)
//                            .whereLessThanOrEqualTo("Material", searchtext + "\uf8ff");
//                    query.orderBy("con_unload_DT", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                        @Override
//                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                            if (task.isSuccessful()) {
//                                int totalCount = task.getResult().size();
//                                txtTotalCount.setText("Total count: " + totalCount);
//                                datalist.clear(); // Clear the previous data
//                                for (QueryDocumentSnapshot document : task.getResult()) {
//                                    In_Tanker_Production_list obj = document.toObject(In_Tanker_Production_list.class);
//                                    // Check if the object already exists to avoid duplicates
//                                    if (!datalist.contains(obj)) {
//                                        datalist.add(obj);
//                                    }
//                                }
//                                inTankerProAdapter.notifyDataSetChanged();
//                            } else {
//                                Log.w("FirestoreData", "Error getting documents.", task.getException());
//                            }
//                        }
//                    });
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });

//        btnmaterialnumclear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                etMaterialName.setText("");
//            }
//        });
//        btntanknumclear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                etTankNumber.setText("");
//            }
//        });
    }

    private void GetproductionlistData(char nextprocess) {
        Call<List<ListingResponse_InTankerproduction>> call = apiInTankerProduction.getintankerproductionListdata(nextprocess);
        call.enqueue(new Callback<List<ListingResponse_InTankerproduction>>() {
            @Override
            public void onResponse(Call<List<ListingResponse_InTankerproduction>> call, Response<List<ListingResponse_InTankerproduction>> response) {
                if (response.isSuccessful()){
                    List<ListingResponse_InTankerproduction> data = response.body();
                    int totalcount = data.size();
                    txtTotalCount.setText("Total count :"+ totalcount);
                    productionlist.clear();
                    productionlist= data;
                    inTankerProAdapter = new In_Tanker_Pro_Adapter(productionlist);
                    recview.setAdapter(inTankerProAdapter);
                    inTankerProAdapter.notifyDataSetChanged();

                }else  {
                    Log.e("Retrofit", "Error Response Body: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<ListingResponse_InTankerproduction>> call, Throwable t) {

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
                Toasty.error(Inward_Tanker_Production_Viewdata.this,"failed..!", Toast.LENGTH_SHORT).show();
            }
        });
    }

//    public void showDatePickerDialog(final boolean isStartDate) {
//        Calendar calendar = Calendar.getInstance();
//        int year = calendar.get(Calendar.YEAR);
//        int month = calendar.get(Calendar.MONTH);
//        int day = calendar.get(Calendar.DAY_OF_MONTH);
//        // Array of month abbreviations
//        String[] monthAbbreviations = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
//        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
//                new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                        String monthAbbreviation = monthAbbreviations[monthOfYear];
//                        // Month is 0 based, so adding 1 to monthOfYear
//                        String selectedDate = dayOfMonth + " " + monthAbbreviation + " " + year;
//
//                        if (isStartDate) {
//                            date_start = selectedDate;
//                            startDatePicker.setText(selectedDate);
//                        } else {
//                            date_end = selectedDate;
//                            endDatePicker.setText(selectedDate);
//                        }
//
//                        // Call method to fetch data after selecting the date
//                        fetchDataFromFirestore(date_start, date_end);
//                    }
//                }, year, month, day);
//
//        datePickerDialog.show();
//    }

//    public void fetchDataFromFirestore(String startDate, String endDate) {
//
//        CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Inward Tanker Production");
//        Query baseQuery = collectionReference.orderBy("con_unload_DT");
//
//        if (startDate != null && endDate != null) {
//            baseQuery = baseQuery.whereGreaterThanOrEqualTo("con_unload_DT", startDate)
//                    .whereLessThanOrEqualTo("con_unload_DT", endDate);
//        } else if (startDate != null) {
//            baseQuery = baseQuery.whereGreaterThanOrEqualTo("con_unload_DT", startDate);
//        } else if (endDate != null) {
//            baseQuery = baseQuery.whereLessThanOrEqualTo("con_unload_DT", endDate);
//        }
//
//        baseQuery.orderBy("con_unload_DT", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    int totalCount = task.getResult().size();
//                    txtTotalCount.setText("Total count: " + totalCount);
//                    datalist.clear(); // Clear the previous data
//                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        In_Tanker_Production_list obj = document.toObject(In_Tanker_Production_list.class);
//                        datalist.add(obj);
//                    }
//                    inTankerProAdapter.notifyDataSetChanged();
//                } else {
//                    Log.w("FirestoreData", "Error getting documents.", task.getException());
//                }
//            }
//        });
//    }

//    private void clearSelectedDates() {
//        startDatePicker.setText("start of Date");
//        endDatePicker.setText("End of Data");
//        etTankNumber.setText("");
//        etMaterialName.setText("");
//        recview = findViewById(R.id.recyclerview);
//        recview.setLayoutManager(new LinearLayoutManager(this));
//        datalist = new ArrayList<>();
//        inTankerProAdapter = new In_Tanker_Pro_Adapter(datalist);
//        recview.setAdapter(inTankerProAdapter);
//
//        db = FirebaseFirestore.getInstance();
//        db.collection("Inward Tanker Production").orderBy("con_unload_DT", Query.Direction.DESCENDING).get()
//                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
//                        int totalCount = list.size();
//                        txtTotalCount.setText("Total count: " + totalCount);
//                        datalist.clear();
//                        for (DocumentSnapshot d : list) {
//                            In_Tanker_Production_list obj = d.toObject(In_Tanker_Production_list.class);
//                            datalist.add(obj);
//                        }
//                        inTankerProAdapter.notifyDataSetChanged();
//                    }
//                });
//
//    }
}