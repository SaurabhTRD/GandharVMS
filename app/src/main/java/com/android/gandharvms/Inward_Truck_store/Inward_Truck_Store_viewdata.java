package com.android.gandharvms.Inward_Truck_store;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.gandharvms.Global_Var;
import com.android.gandharvms.InwardCompletedGrid.CommonResponseModelForAllDepartment;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.LoginWithAPI.Store;
import com.android.gandharvms.R;
import com.google.firebase.firestore.FirebaseFirestore;

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

public class Inward_Truck_Store_viewdata extends AppCompatActivity {

    RecyclerView recview;
    /*ArrayList<In_Truck_Store_list> datalist;*/

    List<CommonResponseModelForAllDepartment> storeListDetails;

    FirebaseFirestore db;

    /*Im_Truck_Store_Adapter imTruckStoreAdapter;*/

    InTruckStoreList_DataAdapter intruckStoreAdapter;
    String date_start,date_end;
    EditText etserialNumber,etpartyName;
    Button btnsrnumclear,btnptnamclear,startDatePicker,endDatePicker,btncleardateselection,btnlogout;
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    TextView txtTotalCount;

    private Store storedetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inward_truck_store_viewdata);

        startDatePicker = findViewById(R.id.startdate);
        endDatePicker = findViewById(R.id.enddate);
        btnsrnumclear=findViewById(R.id.btn_srnumberbutton_clear);
        btnptnamclear=findViewById(R.id.btn_partytnamebutton_clear);
        etserialNumber=findViewById(R.id.et_SerialNumber);
        etpartyName=findViewById(R.id.et_PartyName);
        btncleardateselection=findViewById(R.id.btn_clearDateSelectionfields);
        txtTotalCount=findViewById(R.id.tv_TotalCount);
        btnlogout=findViewById(R.id.btn_logoutButton);
        storedetails= RetroApiClient.getStoreDetails();

        recview = (RecyclerView) findViewById(R.id.recyclerview);
        recview.setLayoutManager(new LinearLayoutManager(this));
        storeListDetails = new ArrayList<>();

        String FromDate = getCurrentDateTime();
        String Todate = getCurrentDateTime();
        GetStoreListData(FromDate,Todate,vehicleType,inOut);
        /*startDatePicker.setOnClickListener(new View.OnClickListener() {
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

        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Inward_Truck_Store_viewdata.this, Login.class));
            }
        });

        etserialNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Inward Truck Store");
                String searchText = charSequence.toString().trim();
                if (searchText.isEmpty()) {
                    // If search text is empty, fetch all data without any filters
                    collectionReference.orderBy("Po_Date", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                int totalCount = task.getResult().size();
                                txtTotalCount.setText("Total count: " + totalCount);
                                datalist.clear(); // Clear the previous data
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    In_Truck_Store_list obj = document.toObject(In_Truck_Store_list.class);
                                    // Check if the object already exists to avoid duplicates
                                    if (!datalist.contains(obj)) {
                                        datalist.add(obj);
                                    }
                                }
                                imTruckStoreAdapter.notifyDataSetChanged();
                            } else {
                                Log.w("FirestoreData", "Error getting documents.", task.getException());
                            }
                        }
                    });
                } else {
                    // Create a query with filters for non-empty search text
                    Query query = collectionReference.whereGreaterThanOrEqualTo("Serial_Number", searchText)
                            .whereLessThanOrEqualTo("Serial_Number", searchText + "\uf8ff");

                    query.orderBy("Po_Date", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                int totalCount = task.getResult().size();
                                txtTotalCount.setText("Total count: " + totalCount);
                                datalist.clear(); // Clear the previous data
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    In_Truck_Store_list obj = document.toObject(In_Truck_Store_list.class);
                                    // Check if the object already exists to avoid duplicates
                                    if (!datalist.contains(obj)) {
                                        datalist.add(obj);
                                    }
                                }
                                imTruckStoreAdapter.notifyDataSetChanged();
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
                CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Inward Truck Store");
                String searchText = charSequence.toString().trim();
                if (searchText.isEmpty()) {
                    // If search text is empty, fetch all data without any filters
                    collectionReference.orderBy("Po_Date", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                int totalCount = task.getResult().size();
                                txtTotalCount.setText("Total count: " + totalCount);
                                datalist.clear(); // Clear the previous data
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    In_Truck_Store_list obj = document.toObject(In_Truck_Store_list.class);
                                    // Check if the object already exists to avoid duplicates
                                    if (!datalist.contains(obj)) {
                                        datalist.add(obj);
                                    }
                                }
                                imTruckStoreAdapter.notifyDataSetChanged();
                            } else {
                                Log.w("FirestoreData", "Error getting documents.", task.getException());
                            }
                        }
                    });
                } else {
                    // Create a query with filters for non-empty search text
                    Query query = collectionReference.whereGreaterThanOrEqualTo("Material", searchText)
                            .whereLessThanOrEqualTo("Material", searchText + "\uf8ff");

                    query.orderBy("Po_Date", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                int totalCount = task.getResult().size();
                                txtTotalCount.setText("Total count: " + totalCount);
                                datalist.clear(); // Clear the previous data
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    In_Truck_Store_list obj = document.toObject(In_Truck_Store_list.class);
                                    // Check if the object already exists to avoid duplicates
                                    if (!datalist.contains(obj)) {
                                        datalist.add(obj);
                                    }
                                }
                                imTruckStoreAdapter.notifyDataSetChanged();
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
        imTruckStoreAdapter = new Im_Truck_Store_Adapter(datalist);
        recview.setAdapter(imTruckStoreAdapter);

        db= FirebaseFirestore.getInstance();
        db.collection("Inward Truck Store").orderBy("Po_Date", Query.Direction.DESCENDING).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        int totalCount = list.size();
                        txtTotalCount.setText("Total count: " + totalCount);
                        datalist.clear();
                        for (DocumentSnapshot d:list)
                        {
                            In_Truck_Store_list  obj = d.toObject(In_Truck_Store_list.class);
                            datalist.add(obj);

                        }
                        imTruckStoreAdapter.notifyDataSetChanged();

                    }
                });*/
    }
    private String getCurrentDateTime() {
        // Get current date and time
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();

        // Format the date and time as a string
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return dateFormat.format(now);
    }
    private void GetStoreListData(String FromDate,String Todate,String vehicletype,char inout) {
        Call<List<CommonResponseModelForAllDepartment>> call = storedetails.getInTruckStoreListData(FromDate,Todate,vehicletype,inout);
        call.enqueue(new Callback<List<CommonResponseModelForAllDepartment>>() {
            @Override
            public void onResponse(Call<List<CommonResponseModelForAllDepartment>> call, Response<List<CommonResponseModelForAllDepartment>> response) {
                if(response.isSuccessful())
                {
                    List<CommonResponseModelForAllDepartment> data=response.body();
                    int totalCount = data.size();
                    txtTotalCount.setText("Total count: " + totalCount);
                    storeListDetails.clear();
                    storeListDetails = data;
                    intruckStoreAdapter = new InTruckStoreList_DataAdapter(storeListDetails);
                    recview.setAdapter(intruckStoreAdapter);
                    intruckStoreAdapter.notifyDataSetChanged();
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
                Toasty.error(Inward_Truck_Store_viewdata.this,"failed..!", Toast.LENGTH_SHORT).show();
            }
        });
        /*call.enqueue(new Callback<List<InTanLabResponseModel>>() {
            @Override
            public void onResponse(Call<List<InTanLabResponseModel>> call, Response<List<InTanLabResponseModel>> response) {
                if(response.isSuccessful()){
                    List<InTanLabResponseModel> data=response.body();
                    int totalCount = data.size();
                    txtTotalCount.setText("Total count: " + totalCount);
                    labdatalist.clear();
                    labdatalist = data;
                    inTankLabAdapter = new InTanLabListData_Adapter(labdatalist);
                    recview.setAdapter(inTankLabAdapter);
                    inTankLabAdapter.notifyDataSetChanged();
                }
                else
                {
                    Log.e("Retrofit", "Error Response Body: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<List<InTanLabResponseModel>> call, Throwable t) {
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
                Toasty.error(Inward_Tanker_Lab_Viewdata.this,"failed..!", Toast.LENGTH_SHORT).show();
            }
        });*/
    }
    /*public void showDatePickerDialog(final boolean isStartDate){
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
        recview = (RecyclerView) findViewById(R.id.recyclerview);
        recview.setLayoutManager(new LinearLayoutManager(this));
        datalist = new ArrayList<>();
        imTruckStoreAdapter = new Im_Truck_Store_Adapter(datalist);
        recview.setAdapter(imTruckStoreAdapter);

        db= FirebaseFirestore.getInstance();
        db.collection("Inward Truck Store").orderBy("Po_Date", Query.Direction.DESCENDING).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        int totalCount = list.size();
                        txtTotalCount.setText("Total count: " + totalCount);
                        datalist.clear();
                        for (DocumentSnapshot d:list)
                        {
                            In_Truck_Store_list  obj = d.toObject(In_Truck_Store_list.class);
                            datalist.add(obj);

                        }
                        imTruckStoreAdapter.notifyDataSetChanged();

                    }
                });
    }

    public void fetchDataFromFirestore(String startDate, String endDate){

        CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Inward Truck Store");
        Query baseQuery = collectionReference.orderBy("Po_Date");

        if (startDate != null && endDate != null){
            baseQuery = baseQuery.whereGreaterThanOrEqualTo("Po_Date", startDate)
                    .whereLessThanOrEqualTo("Po_Date", endDate);
        } else if (startDate != null){
            baseQuery = baseQuery.whereGreaterThanOrEqualTo("Po_Date", startDate);
        } else if (endDate != null) {
            baseQuery = baseQuery.whereLessThanOrEqualTo("Po_Date", endDate);
        }

        baseQuery.orderBy("Po_Date", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    int totalCount = task.getResult().size();
                    txtTotalCount.setText("Total count: " + totalCount);
                    datalist.clear(); // Clear the previous data
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        In_Truck_Store_list obj = document.toObject(In_Truck_Store_list.class);
                        datalist.add(obj);
                    }
                    imTruckStoreAdapter.notifyDataSetChanged();
                } else {
                    Log.w("FirestoreData", "Error getting documents.", task.getException());
                }
            }
        });
    }*/
}