package com.android.gandharvms.Inward_Tanker_Production;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.pm.PackageManager;
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

import com.android.gandharvms.Global_Var;
import com.android.gandharvms.InwardCompletedGrid.CommonResponseModelForAllDepartment;
import com.android.gandharvms.InwardCompletedGrid.GridCompleted;
import com.android.gandharvms.Inward_Tanker_Laboratory.it_Lab_Completedgrid;
import com.android.gandharvms.Inward_Tanker_Laboratory.it_Lab_CompletedgridAdapter;
import com.android.gandharvms.Inward_Tanker_Security.RetroApiclient_In_Tanker_Security;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
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

public class it_pro_Completedgrid extends AppCompatActivity {
    int scrollX = 0;
    List<CommonResponseModelForAllDepartment> clubList = new ArrayList<>();
    RecyclerView rvClub;
    HorizontalScrollView headerscroll;
    it_pro_CompletedgridAdapter itprogridadaptercomp;
    private final String vehicleType = Global_Var.getInstance().MenuType;
    private final char nextProcess = Global_Var.getInstance().DeptType;
    private final char inOut = Global_Var.getInstance().InOutType;
    private final String EmployeId = Global_Var.getInstance().EmpId;
    Button fromDate,toDate;
    TextView totrec;
    String fromdate;
    String todate;
    String strvehiclenumber;
    String formattedDate;
    ImageButton imgBtnExportToExcel;
    private HSSFWorkbook hssfWorkBook;
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST = 123;

    private API_In_Tanker_production apiInTankerProduction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_it_pro_completedgrid);

        apiInTankerProduction = RetroApiclient_In_Tanker_Security.getinproductionApi();
        fromDate=findViewById(R.id.btnfromDate);
        toDate=findViewById(R.id.btntoDate);
        totrec=findViewById(R.id.totrecdepartmentwise);
        fromdate=getCurrentDateTime();
        todate = getCurrentDateTime();
        imgBtnExportToExcel=findViewById(R.id.btn_itproExportToExcel);
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
            fetchDataFromApiforPro(fromdate,todate,vehicleType,inOut);
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
                                fetchDataFromApiforPro(fromdate,todate,vehicleType,inOut);
                            }
                            else{
                            }
                            rvClub.addOnScrollListener(new RecyclerView.OnScrollListener()
                            {
                                @Override
                                public void onScrolled(RecyclerView recyclerView, int dx, int dy)
                                {
                                    //super.onScrolled(recyclerView, dx, dy);
                                    //scrollX += dx;
                                    //headerscroll.scrollTo(scrollX, 0);
                                }
                                @Override
                                public void onScrollStateChanged(RecyclerView recyclerView, int newState)
                                {
                                    super.onScrollStateChanged(recyclerView, newState);
                                }
                            });
                        } else {
                            // Show an error message or take appropriate action
                            Toasty.warning(it_pro_Completedgrid.this, "Invalid date selection", Toast.LENGTH_SHORT).show();
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

    private void exportToExcel(List<CommonResponseModelForAllDepartment> datalist) {
        try {
            Sheet sheet = hssfWorkBook.createSheet("InwardTankerProductionData");
            // Create header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("DATE");
            headerRow.createCell(1).setCellValue("VEHICLE_No");
            headerRow.createCell(2).setCellValue("SERIAL_No");
            headerRow.createCell(3).setCellValue("MATERIAL_NAME");
            headerRow.createCell(4).setCellValue("IN_TIME");
            headerRow.createCell(5).setCellValue("OUT_TIME");
            headerRow.createCell(6).setCellValue("UNLOADABOVEMATERIALINTK");
            headerRow.createCell(7).setCellValue("PRODUCTNAME");
            headerRow.createCell(8).setCellValue("ABOVEMATERIALISUNLOADINTK");
            headerRow.createCell(9).setCellValue("OPERATORNAME");


            // Populate data rows
            for (int i = 0; i < datalist.size(); i++) {
                Row dataRow = sheet.createRow(i + 1); // Start from the second row (index 1) for data
                CommonResponseModelForAllDepartment dataItem = datalist.get(i);
                int intimelength = dataItem.getInTime()!=null ? dataItem.getInTime().length() : 0;
                int outtimelength = dataItem.getOutTime()!=null ? dataItem.getOutTime().length() : 0;
                dataRow.createCell(0).setCellValue(formattedDate = formatDate(dataItem.getDate()));
                dataRow.createCell(1).setCellValue(dataItem.getVehicleNo());
                dataRow.createCell(2).setCellValue(dataItem.getSerialNo());
                dataRow.createCell(3).setCellValue(dataItem.getMaterial());
                if(intimelength>0)
                {
                    dataRow.createCell(4).setCellValue(dataItem.getInTime().substring(12,intimelength));
                }
                if(outtimelength>0)
                {
                    dataRow.createCell(5).setCellValue(dataItem.getOutTime().substring(12,outtimelength));
                }
                dataRow.createCell(6).setCellValue(dataItem.getUnloadAboveMaterialInTK());
                dataRow.createCell(7).setCellValue(dataItem.getProductName());
                dataRow.createCell(8).setCellValue(dataItem.getAboveMaterialIsUnloadInTK());
                dataRow.createCell(9).setCellValue(dataItem.getOperatorName());
            }
            // Save the workbook
            //saveWorkBook(hssfWorkBook);
            saveWorkbookToFile(hssfWorkBook);
        }catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

    /*private void saveWorkBook(HSSFWorkbook hssfWorkBook) {
        try {
            // Check if permission is granted
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                // Permission is already granted, proceed with saving the workbook
                saveWorkbookToFile(hssfWorkBook);
            } else {
                // Permission is not granted, request permission from the user
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE_REQUEST);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    // Override onRequestPermissionsResult method to handle the result of the permission request
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == WRITE_EXTERNAL_STORAGE_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted, proceed with saving the workbook
                saveWorkbookToFile(hssfWorkBook);
            } else {
                // Permission is denied, show a message to the user
                Toasty.warning(this, "Permission denied. Cannot save file.", Toast.LENGTH_SHORT).show();
            }
        }
    }*/

    private void saveWorkbookToFile(HSSFWorkbook hssfWorkBook) {
        try {
            StorageManager storageManager = (StorageManager) getSystemService(STORAGE_SERVICE);
            StorageVolume storageVolume = storageManager.getStorageVolumes().get(0);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                String dateTimeSuffix = new SimpleDateFormat("ddMMMyyyy_HHmmss", Locale.getDefault()).format(new Date());
                int counter = 1;
                String fileName = "Inward Tanker Production Data_" + dateTimeSuffix + ".xls";
                File outputfile = new File(storageVolume.getDirectory().getPath() + "/Download/" + fileName);
                while (outputfile.exists()) {
                    counter++;
                    fileName = "Inward Tanker Production Data_" + dateTimeSuffix + "_" + counter + ".xls";
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
    private String getCurrentDateTime() {
        // Get current date and time
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();

        // Format the date and time as a string
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(now);
    }
    private void initViews()
    {
        rvClub = findViewById(R.id.recyclerviewitprocogrid);
        headerscroll = findViewById(R.id.itprocoheaderscroll);
    }

    private void setUpRecyclerView()
    {
        itprogridadaptercomp  = new it_pro_CompletedgridAdapter(clubList);
        FixedGridLayoutManager manager = new FixedGridLayoutManager();
        manager.setTotalColumnCount(1);
        rvClub.setLayoutManager(manager);
        rvClub.setAdapter(itprogridadaptercomp);
        rvClub.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    public void fetchDataFromApiforPro(String FromDate,String Todate,String vehicleType, char inOut) {

        Call<List<CommonResponseModelForAllDepartment>> call =apiInTankerProduction.getintankerproductionListdata(FromDate,Todate, vehicleType, inOut);
        call.enqueue(new Callback<List<CommonResponseModelForAllDepartment>>() {
            @Override
            public void onResponse(Call<List<CommonResponseModelForAllDepartment>> call, Response<List<CommonResponseModelForAllDepartment>> response) {
                if (response.isSuccessful()) {
                    if (response.body().size() > 0) {
                        List<CommonResponseModelForAllDepartment> data = response.body();
                        int totalcount = data.size();
                        totrec.setText("Tot-Rec: "+ totalcount);
                        clubList = data;
                        setUpRecyclerView();
                    }
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
                Toasty.error(it_pro_Completedgrid.this,"failed..!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}