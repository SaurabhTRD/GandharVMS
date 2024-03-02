package com.android.gandharvms.Inward_Truck_Security;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.gandharvms.Global_Var;
import com.android.gandharvms.Inward_Tanker_Security.API_In_Tanker_Security;
import com.android.gandharvms.InwardCompletedGrid.CommonResponseModelForAllDepartment;
import com.android.gandharvms.Inward_Tanker_Security.RetroApiclient_In_Tanker_Security;
import com.android.gandharvms.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.text.SimpleDateFormat;
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

public class Inward_Truck_Security_viewdata extends AppCompatActivity {

    RecyclerView recview;
    ArrayList<In_Truck_security_list> datalist;

    FirebaseFirestore db;

    In_Truck_Security_Adapter inTruckSecurityAdapter;
    String date_start,date_end;
    Button startDatePicker,endDatePicker,btnsrnumclear,btnptnamclear,btnclearSelectedDates;
    EditText etserialNumber,etSupplierName;
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    TextView txtTotalCount;
    private API_In_Tanker_Security securitydetails;
    List<CommonResponseModelForAllDepartment> trucksecuritylist;



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

//        btnclearSelectedDates.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                clearSelectedDates();
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
//        fetchDataFromFirestore(null, null);

//        btnsrnumclear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                etserialNumber.setText("");
//            }
//        });

//        btnptnamclear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                etSupplierName.setText("");
//            }
//        });
//        etserialNumber.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//            }
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Inward Truck Security");
//                String searchText = charSequence.toString().trim();
//                if (searchText.isEmpty()) {
//                    // If search text is empty, fetch all data without any filters
//                    collectionReference.orderBy("date", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                        @Override
//                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                            if (task.isSuccessful()) {
//                                int totalCount = task.getResult().size();
//                                txtTotalCount.setText("Total count: " + totalCount);
//                                datalist.clear(); // Clear the previous data
//                                for (QueryDocumentSnapshot document : task.getResult()) {
//                                    In_Truck_security_list obj = document.toObject(In_Truck_security_list.class);
//                                    // Check if the object already exists to avoid duplicates
//                                    if (!datalist.contains(obj)) {
//                                        datalist.add(obj);
//                                    }
//                                }
//                                inTruckSecurityAdapter.notifyDataSetChanged();
//                            } else {
//                                Log.w("FirestoreData", "Error getting documents.", task.getException());
//                            }
//                        }
//                    });
//                } else {
//                    // Create a query with filters for non-empty search text
//                    Query query = collectionReference.whereGreaterThanOrEqualTo("serialnumber", searchText)
//                            .whereLessThanOrEqualTo("serialnumber", searchText + "\uf8ff");
//
//                    query.orderBy("date", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                        @Override
//                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                            if (task.isSuccessful()) {
//                                int totalCount = task.getResult().size();
//                                txtTotalCount.setText("Total count: " + totalCount);
//                                datalist.clear(); // Clear the previous data
//                                for (QueryDocumentSnapshot document : task.getResult()) {
//                                    In_Truck_security_list obj = document.toObject(In_Truck_security_list.class);
//                                    // Check if the object already exists to avoid duplicates
//                                    if (!datalist.contains(obj)) {
//                                        datalist.add(obj);
//                                    }
//                                }
//                                inTruckSecurityAdapter.notifyDataSetChanged();
//                            } else {
//                                Log.w("FirestoreData", "Error getting documents.", task.getException());
//                            }
//                        }
//                    });
//                }
//            }
//            @Override
//            public void afterTextChanged(Editable editable) {
//            }
//        });

//        etSupplierName.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//            }
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Inward Truck Security");
//                String searchText = charSequence.toString().trim();
//                if (searchText.isEmpty()) {
//                    // If search text is empty, fetch all data without any filters
//                    collectionReference.orderBy("date", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                        @Override
//                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                            if (task.isSuccessful()) {
//                                int totalCount = task.getResult().size();
//                                txtTotalCount.setText("Total count: " + totalCount);
//                                datalist.clear(); // Clear the previous data
//                                for (QueryDocumentSnapshot document : task.getResult()) {
//                                    In_Truck_security_list obj = document.toObject(In_Truck_security_list.class);
//                                    // Check if the object already exists to avoid duplicates
//                                    if (!datalist.contains(obj)) {
//                                        datalist.add(obj);
//                                    }
//                                }
//                                inTruckSecurityAdapter.notifyDataSetChanged();
//                            } else {
//                                Log.w("FirestoreData", "Error getting documents.", task.getException());
//                            }
//                        }
//                    });
//                } else {
//                    // Create a query with filters for non-empty search text
//                    Query query = collectionReference.whereGreaterThanOrEqualTo("Supplier", searchText)
//                            .whereLessThanOrEqualTo("Supplier", searchText + "\uf8ff");
//
//                    query.orderBy("date", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                        @Override
//                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                            if (task.isSuccessful()) {
//                                int totalCount = task.getResult().size();
//                                txtTotalCount.setText("Total count: " + totalCount);
//                                datalist.clear(); // Clear the previous data
//                                for (QueryDocumentSnapshot document : task.getResult()) {
//                                    In_Truck_security_list obj = document.toObject(In_Truck_security_list.class);
//                                    // Check if the object already exists to avoid duplicates
//                                    if (!datalist.contains(obj)) {
//                                        datalist.add(obj);
//                                    }
//                                }
//                                inTruckSecurityAdapter.notifyDataSetChanged();
//                            } else {
//                                Log.w("FirestoreData", "Error getting documents.", task.getException());
//                            }
//                        }
//                    });
//                }
//            }
//            @Override
//            public void afterTextChanged(Editable editable) {
//            }
//        });

        recview = (RecyclerView) findViewById(R.id.recyclerview);

//        recview.setLayoutManager(new LinearLayoutManager(this));
//        datalist = new ArrayList<>();
//        inTruckSecurityAdapter = new In_Truck_Security_Adapter(datalist);
//        recview.setAdapter(inTruckSecurityAdapter);


        securitydetails = RetroApiclient_In_Tanker_Security.getinsecurityApi();
        recview = (RecyclerView)findViewById(R.id.recyclerview);
        recview.setLayoutManager(new LinearLayoutManager(this));
        trucksecuritylist = new ArrayList<>();
        String FromDate = getCurrentDateTime();
        String Todate = getCurrentDateTime();
        GetsecuritylistData(FromDate,Todate,vehicleType,inOut);



//        db= FirebaseFirestore.getInstance();
//        db.collection("Inward Truck Security").orderBy("date", Query.Direction.DESCENDING).get()
//                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
//                        int totalCount = list.size();
//                        txtTotalCount.setText("Total count: " + totalCount);
//                        datalist.clear(); // Clear the previous data
//                        for (DocumentSnapshot d:list)
//                        {
//                            In_Truck_security_list obj = d.toObject(In_Truck_security_list.class);
//                            datalist.add(obj);
//                        }
//                        inTruckSecurityAdapter.notifyDataSetChanged();
//                    }
//                });

    }

    private String getCurrentDateTime() {
        // Get current date and time
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();

        // Format the date and time as a string
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return dateFormat.format(now);
    }

    private void GetsecuritylistData(String FromDate,String Todate,String vehicletype,char inout) {
        Call<List<CommonResponseModelForAllDepartment>> call = securitydetails.getintankersecurityListData(FromDate,Todate,vehicletype,inout);
        call.enqueue(new Callback<List<CommonResponseModelForAllDepartment>>() {
            @Override
            public void onResponse(Call<List<CommonResponseModelForAllDepartment>> call, Response<List<CommonResponseModelForAllDepartment>> response) {
                if (response.isSuccessful()){
                    List<CommonResponseModelForAllDepartment> data = response.body();
                    int totalcount = data.size();
                    txtTotalCount.setText("Total count: "+ totalcount);
                    trucksecuritylist.clear();
                    trucksecuritylist = data;
                    inTruckSecurityAdapter = new In_Truck_Security_Adapter(trucksecuritylist);
                    recview.setAdapter(inTruckSecurityAdapter);
                    inTruckSecurityAdapter.notifyDataSetChanged();

                }else  {
                    Log.e("Retrofit", "Error Response Body: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<CommonResponseModelForAllDepartment>> call, Throwable t) {

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
                Toasty.error(Inward_Truck_Security_viewdata.this,"failed..!", Toast.LENGTH_SHORT).show();
            }
        });
    }
//    private void clearSelectedDates(){
//        startDatePicker.setText("start of Date");
//        endDatePicker.setText("End of Data");
//        etserialNumber.setText("");
//        etSupplierName.setText("");
//        recview = (RecyclerView) findViewById(R.id.recyclerview);
//
//        recview.setLayoutManager(new LinearLayoutManager(this));
//        datalist = new ArrayList<>();
//        inTruckSecurityAdapter = new In_Truck_Security_Adapter(datalist);
//        recview.setAdapter(inTruckSecurityAdapter);
//
//        db= FirebaseFirestore.getInstance();
//        db.collection("Inward Truck Security").orderBy("date", Query.Direction.DESCENDING).get()
//                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
//                        int totalCount = list.size();
//                        txtTotalCount.setText("Total count: " + totalCount);
//                        datalist.clear(); // Clear the previous data
//                        for (DocumentSnapshot d:list)
//                        {
//                            In_Truck_security_list obj = d.toObject(In_Truck_security_list.class);
//                            datalist.add(obj);
//                        }
//                        inTruckSecurityAdapter.notifyDataSetChanged();
//                    }
//                });
//    }
//    public void showDatePickerDialog(final boolean isStartDate){
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
//                        String selectedDate = dayOfMonth + "/" + monthAbbreviation  + "/" + year;
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
//    public void fetchDataFromFirestore(String startDate, String endDate){
//
//        CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Inward Truck Security");
//        Query baseQuery = collectionReference.orderBy("date",Query.Direction.DESCENDING);
//
//        if (startDate != null && endDate != null){
//            baseQuery = baseQuery.whereGreaterThanOrEqualTo("date", startDate)
//                    .whereLessThanOrEqualTo("date", endDate);
//        } else if (startDate != null){
//            baseQuery = baseQuery.whereGreaterThanOrEqualTo("date", startDate);
//        } else if (endDate != null) {
//            baseQuery = baseQuery.whereLessThanOrEqualTo("date", endDate);
//        }
//
//        baseQuery.orderBy("date", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    int totalCount = task.getResult().size();
//                    txtTotalCount.setText("Total count: " + totalCount);
//                    datalist.clear(); // Clear the previous data
//                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        In_Truck_security_list obj = document.toObject(In_Truck_security_list.class);
//                        datalist.add(obj);
//                    }
//                    inTruckSecurityAdapter.notifyDataSetChanged();
//                } else {
//                    Log.w("FirestoreData", "Error getting documents.", task.getException());
//                }
//            }
//        });
//    }

}