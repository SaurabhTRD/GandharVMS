package com.android.gandharvms.Outward_Truck_Logistic;

import android.app.DatePickerDialog;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gandharvms.Global_Var;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.Outward_Tanker_Security.Outward_Tanker;
import com.android.gandharvms.Outward_Truck_Security.Adapter_OR_Completesec;
import com.android.gandharvms.Outward_Truck_Security.Common_Outward_model;
import com.android.gandharvms.Outward_Truck_Security.OR_Completesec;
import com.android.gandharvms.R;
import com.android.gandharvms.Util.FixedGridLayoutManager;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
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

public class Logi_OR_Complete extends AppCompatActivity {

    int scrollX = 0;
    List<Common_Outward_model> clubList = new ArrayList<>();
    RecyclerView rvClub;
    HorizontalScrollView headerscroll;
    Adapter_Logi_complete adapterLogiComplete;
    private Outward_Tanker outwardTanker;

    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private final String EmployeId = Global_Var.getInstance().EmpId;
    Button fromDate,toDate;
    TextView totrec;
    String fromdate;
    String todate;
    String strvehiclenumber;
    ImageButton imgBtnExportToExcel;
    private HSSFWorkbook hssfWorkBook;
    String formattedDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        outwardTanker = RetroApiClient.insertoutwardtankersecurity();
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_logi_or_complete);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        totrec=findViewById(R.id.totrecdepartmentwise);
        fromDate=findViewById(R.id.orbtnfromDate);
        toDate=findViewById(R.id.orbtntoDate);
        fromdate=getCurrentDateTime();
        todate = getCurrentDateTime();
        imgBtnExportToExcel = findViewById(R.id.btn_Logi_orExportToExcel);
        hssfWorkBook = new HSSFWorkbook();

        fromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the onClick for fromDate button
                showDatePickerDialog(fromDate,true);
            }
        });
        toDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the onClick for fromDate button
                showDatePickerDialog(toDate,false);
            }
        });
        imgBtnExportToExcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clubList != null && !clubList.isEmpty()) {
                    // Export data to Excel
                    exportToExcel(clubList);
                } else {
                    // Show a message indicating no data to export
                    Toasty.warning(getApplicationContext(), "No data to export", Toast.LENGTH_SHORT).show();
                }
            }
        });
        initViews();
        if(Global_Var.getInstance().DeptType!=0 && Integer.valueOf(Global_Var.getInstance().DeptType) !=120)
        {
            if(getIntent().hasExtra("vehiclenumber")==true)
            {
                strvehiclenumber= getIntent().getExtras().get("vehiclenumber").toString();
            }
            else{
                strvehiclenumber="x";
            }
            fetchDataFromApiforweigh(fromdate,todate,vehicleType,nextProcess,inOut);
        }
        else{
        }
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
    private void showDatePickerDialog(final TextView dateTextView,final boolean isFromDate) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Update the TextView with the selected date
                        String selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                        if ((isFromDate || !isFromDate)) {
                            dateTextView.setText(selectedDate);
                            if (isFromDate) {
                                fromdate = selectedDate;
                            } else {
                                todate = selectedDate;
                            }
                            if(Global_Var.getInstance().DeptType!=0 && Integer.valueOf(Global_Var.getInstance().DeptType) !=120)
                            {
                                if(getIntent().hasExtra("vehiclenumber")==true)
                                {
                                    strvehiclenumber= getIntent().getExtras().get("vehiclenumber").toString();
                                }
                                else{
                                    strvehiclenumber="x";
                                }
                                fetchDataFromApiforweigh(fromdate,todate,vehicleType,nextProcess,inOut);
                            }
                            else{
                            }
                        } else {
                            // Show an error message or take appropriate action
                            Toasty.warning(Logi_OR_Complete.this, "Invalid date selection", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                year, month, day);
        if (isFromDate && !todate.isEmpty()) {
            try {
                datePickerDialog.getDatePicker().setMaxDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(todate).getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else if (!isFromDate && !fromdate.isEmpty()) {
            try {
                datePickerDialog.getDatePicker().setMinDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(fromdate).getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        // Show the date picker dialog
        datePickerDialog.show();
    }
    private void exportToExcel(List<Common_Outward_model> datalist) {
        try {
            Sheet sheet = hssfWorkBook.createSheet("OutwardTruckLogistic");
            // Create header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("DATE");
            headerRow.createCell(0).setCellValue("SERIALNUMBER");
            headerRow.createCell(1).setCellValue("VEHICLE_No");
            headerRow.createCell(3).setCellValue("INTIME");
            headerRow.createCell(4).setCellValue("OUTTIME");
            headerRow.createCell(5).setCellValue("TRANSPORTER");
            headerRow.createCell(6).setCellValue("PLACE");
            headerRow.createCell(7).setCellValue("OA NUMBER");
            headerRow.createCell(8).setCellValue("CUSTOMER NAME");
            headerRow.createCell(9).setCellValue("LOADED QTY");
            headerRow.createCell(10).setCellValue("REMARK");

            // Populate data rows
            for (int i = 0; i < datalist.size(); i++) {
                Row dataRow = sheet.createRow(i + 1); // Start from the second row (index 1) for data
                Common_Outward_model dataItem = datalist.get(i);
                int intimelength = dataItem.getInTime()!=null ? dataItem.getInTime().length() : 0;
                int outtimelength = dataItem.getOutTime()!=null ? dataItem.getOutTime().length() : 0;
                dataRow.createCell(0).setCellValue(formattedDate = formatDate(dataItem.getDate()));
                dataRow.createCell(1).setCellValue(dataItem.getSerialNumber());
                dataRow.createCell(2).setCellValue(dataItem.getVehicleNumber());
                if(intimelength>0)
                {
                    dataRow.createCell(3).setCellValue(dataItem.getInTime().substring(12,intimelength));
                }
                if(intimelength>0)
                {
                    dataRow.createCell(4).setCellValue(dataItem.getOutTime().substring(12,intimelength));
                }
                dataRow.createCell(5).setCellValue(dataItem.getTransportName());
                dataRow.createCell(6).setCellValue(dataItem.getPlace());
                dataRow.createCell(7).setCellValue(dataItem.getOAnumber());
                dataRow.createCell(8).setCellValue(dataItem.getCustomerName());
                dataRow.createCell(9).setCellValue(dataItem.getHowMuchQuantityFilled());
                dataRow.createCell(10).setCellValue(dataItem.getRemark());
            }
            // Save the workbook
            //saveWorkBook(hssfWorkBook);
            saveWorkbookToFile(hssfWorkBook);
        }catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }
    private void saveWorkbookToFile(HSSFWorkbook hssfWorkBook) {
        try {
            StorageManager storageManager = (StorageManager) getSystemService(STORAGE_SERVICE);
            StorageVolume storageVolume = storageManager.getStorageVolumes().get(0);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                String dateTimeSuffix = new SimpleDateFormat("ddMMMyyyy_HHmmss", Locale.getDefault()).format(new Date());
                int counter = 1;
                String fileName = "Outward Truck Logistic Data_" + dateTimeSuffix + ".xls";
                File outputfile = new File(storageVolume.getDirectory().getPath() + "/Download/" + fileName);
                while (outputfile.exists()) {
                    counter++;
                    fileName = "Outward Truck Logistic Data_" + dateTimeSuffix + "_" + counter + ".xls";
                    outputfile = new File(storageVolume.getDirectory().getPath() + "/Download/" + fileName);
                }
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(outputfile);
                    hssfWorkBook.write(fileOutputStream);
                    fileOutputStream.close();
                    hssfWorkBook.close();
                    Toasty.success(this, "Excel File Created Successfully", Toast.LENGTH_SHORT).show();
                } catch (Exception ex) {
                    Toasty.error(this, "File Creation Failed", Toast.LENGTH_SHORT).show();
                    throw new RuntimeException(ex);
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    private String formatDate(String inputDate) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("MMM dd yyyy hh:mma", Locale.getDefault());
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
            Date date = inputFormat.parse(inputDate);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return inputDate;
        }
    }
    private String getCurrentDateTime()     {
        // Get current date and time
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();

        // Format the date and time as a string
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(now);
    }
    private void initViews()
    {
        rvClub = findViewById(R.id.recyclerviewitin_Logi_hcogrid_truckin);
        headerscroll = findViewById(R.id.itin_Logi_coheaderscroll_truckwe);
    }

    private void setUpRecyclerView()
    {
        adapterLogiComplete  = new Adapter_Logi_complete(clubList);
        FixedGridLayoutManager manager = new FixedGridLayoutManager();
        manager.setTotalColumnCount(1);
        rvClub.setLayoutManager(manager);
        rvClub.setAdapter(adapterLogiComplete);
        rvClub.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }
    public void fetchDataFromApiforweigh(String FromDate,String Todate,String vehicleType,char nextprocess, char inOut) {

        Call<List<Common_Outward_model>> call = outwardTanker.getintrucklogicomplete(FromDate,Todate,vehicleType,nextprocess,inOut);
        call.enqueue(new Callback<List<Common_Outward_model>>() {
            @Override
            public void onResponse(Call<List<Common_Outward_model>> call, Response<List<Common_Outward_model>> response) {
                if (response.isSuccessful()){
                    if (response.body().size()>0){
                        List<Common_Outward_model> data = response.body();
                        int totalcount = data.size();
                        totrec.setText("Tot-Rec: "+ totalcount);
                        clubList = data;
                        setUpRecyclerView();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Common_Outward_model>> call, Throwable t) {

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
                Toasty.error(Logi_OR_Complete.this,"failed..!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}