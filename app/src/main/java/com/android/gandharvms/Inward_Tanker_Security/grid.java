package com.android.gandharvms.Inward_Tanker_Security;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.gandharvms.Global_Var;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.R;
import com.android.gandharvms.Util.FixedGridLayoutManager;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class grid extends AppCompatActivity {
    private LinearLayout linearLayout;
    private FirebaseFirestore firestore;

    ArrayList<In_Tanker_Security_list> inTankerSecurityLists;
    In_Tanker_Se_Adapter in_tanker_se_adapter;
    int scrollX = 0;
    List<Respo_Model_In_Tanker_security> clubList = new ArrayList<>();
    RecyclerView rvClub;
    HorizontalScrollView headerscroll;
    SearchView searchView;

    gridAdapter GridAdapter;
    RecyclerView recyclerView;

    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private final String EmployeId = Global_Var.getInstance().EmpId;

    List<Respo_Model_In_Tanker_security> gridlistsecurity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);
//        LinearLayoutManager layoutManager= new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
//        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView  = findViewById(R.id.recyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        fetchDataFromFirestore();
        initViews();
        if(Global_Var.getInstance().DeptType!=0 && Integer.valueOf(Global_Var.getInstance().DeptType) !=120)
        {
            fetchDataFromApi("x",vehicleType,Global_Var.getInstance().DeptType,'x');
        }
        else{
            fetchDataFromApi("x",vehicleType,'x','x');
        }

        //fetchDataFromApi("x",vehicleType,'x','x');
        rvClub.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                super.onScrolled(recyclerView, dx, dy);
                scrollX += dx;
                headerscroll.scrollTo(scrollX, 0);
            }
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState)
            {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    private void initViews()
    {
        rvClub = findViewById(R.id.recyclerviewgrid);
        headerscroll = findViewById(R.id.headerscroll);
    }

    private void setUpRecyclerView()
    {
        GridAdapter  = new gridAdapter(clubList);
        FixedGridLayoutManager manager = new FixedGridLayoutManager();
        manager.setTotalColumnCount(1);
        rvClub.setLayoutManager(manager);
        rvClub.setAdapter(GridAdapter);
        rvClub.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }
    public void fetchDataFromApi(String vehicleno,String vehicleType,char nextProcess, char inOut){
        Call<List<Respo_Model_In_Tanker_security>> call = RetroApiClient.getserccrityveh().GetIntankerSecurityByVehicle(vehicleno,vehicleType,nextProcess,inOut);
        call.enqueue(new Callback<List<Respo_Model_In_Tanker_security>>() {
            @Override
            public void onResponse(Call<List<Respo_Model_In_Tanker_security>> call, Response<List<Respo_Model_In_Tanker_security>> response) {
                if (response.isSuccessful()){
                    if (response.body().size()>0){
                        List<Respo_Model_In_Tanker_security> data = response.body();
                        clubList= data;
                        setUpRecyclerView();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Respo_Model_In_Tanker_security>> call, Throwable t) {

            }
        });




//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        CollectionReference collectionReference = db.collection("Inward Tanker Security");
//        collectionReference.get().addOnCompleteListener(task -> {
//            if (task.isSuccessful()){
//                List<gridmodel> Gridmodel = new ArrayList<>();
//                for (QueryDocumentSnapshot document :task.getResult()){
//                    gridmodel obj = document.toObject(gridmodel.class);
//                    obj.status ="";
//                    Gridmodel.add(obj);
//                }
//                clubList = Gridmodel;
//                setUpRecyclerView();
////                GridAdapter = new gridAdapter(Gridmodel);
////                recyclerView.setAdapter(GridAdapter);
//            }else {
//                Log.e("Firestore","Error getting documnet: ",task.getException());
//            }
//        });
    }

//        recyclerView = (RecyclerView) findViewById(R.id.recycler);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        inTankerSecurityLists = new ArrayList<In_Tanker_Security_list>();
//        GridAdapter = new gridAdapter(grid.this,inTankerSecurityLists);
//        recyclerView.setAdapter(GridAdapter);
//
//        firestore.collection("Inward Tanker Security")
//                .get()
//                .addOnCompleteListener(task -> {
//
//                    if (task.isSuccessful()){
//
//                    for (QueryDocumentSnapshot documentSnapshot :task.getResult()){
//                        String serialnumber = documentSnapshot.getString("SerialNumber");
//                        String vehiclnumber = documentSnapshot.getString("vehicalnumber");
//                        String material = documentSnapshot.getString("material");
//
////                        populateTableRow(serialnumber, vehiclnumber, material);
//                    }
//
//                    }else {
//
//                    }
//                });
//    }


//    private void populateTableRow(String... data) {
//        TableRow row = new TableRow(this);
//        row.setLayoutParams(new TableLayout.LayoutParams(
//                TableLayout.LayoutParams.MATCH_PARENT,
//                TableLayout.LayoutParams.WRAP_CONTENT));
//
//        for (int i = 0; i < data.length; i++) {
//            // Add data
//            TextView dataTextView = createTextView(data[i]);
//            row.addView(dataTextView);
//
////            if (i < data.length - 1) {
////                // Add vertical line between columns
////                View verticalLine = createVerticalLine();
////                row.addView(verticalLine);
////            }
//        }
//
//        linearLayout.addView(row);
//    }

//    private TextView createVerticalLine() {
//        TextView textView = new TextView(this);
//        textView.setText((CharSequence) textView);
//        textView.setLayoutParams(new TableRow.LayoutParams(
//                TableRow.LayoutParams.WRAP_CONTENT,
//                TableRow.LayoutParams.WRAP_CONTENT));
//        textView.setGravity(Gravity.END);
//        textView.setPadding(10, 10, 10, 10);
//        return textView;
//    }

    /*private TextView createTextView(String datum) {
        View verticalLine = new View(this);
        verticalLine.setLayoutParams(new TableRow.LayoutParams(1, TableRow.LayoutParams.MATCH_PARENT));
        verticalLine.setBackgroundColor(getResources().getColor(android.R.color.black));
        return (verticalLine) verticalLine;
    }*/
//    private TextView createTextView(String text) {
//        TextView textView = new TextView(this);
//        textView.setText(text);
//        // You can set other properties for the TextView here if needed
//        return textView;
//    }
}