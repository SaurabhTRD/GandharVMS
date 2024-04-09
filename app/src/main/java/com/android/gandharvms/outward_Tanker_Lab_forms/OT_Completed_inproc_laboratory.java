package com.android.gandharvms.outward_Tanker_Lab_forms;

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
import com.android.gandharvms.Outward_Tanker_Production_forms.Adapter_OT_completed_inproc_prodcuction;
import com.android.gandharvms.Outward_Tanker_Production_forms.OT_Completed_inproc_production;
import com.android.gandharvms.Outward_Tanker_Security.Outward_RetroApiclient;
import com.android.gandharvms.Outward_Tanker_Security.Outward_Tanker;
import com.android.gandharvms.Outward_Truck_Security.Common_Outward_model;
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

public class OT_Completed_inproc_laboratory extends AppCompatActivity {

    int scrollX = 0;
    List<Common_Outward_model> clubList = new ArrayList<>();
    RecyclerView rvClub;
    HorizontalScrollView headerscroll;
    Adapter_OT_completed_inproc_Laboratory adapterOtCompletedInprocLaboratory;
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
        outwardTanker = Outward_RetroApiclient.insertoutwardtankersecurity();
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ot_completed_inproc_laboratory);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        totrec=findViewById(R.id.totrecdepartmentwise);
        fromDate=findViewById(R.id.orbtnfromDate);
        toDate=findViewById(R.id.orbtntoDate);
        fromdate="2024-01-01";
        todate = getCurrentDateTime();
        imgBtnExportToExcel = findViewById(R.id.btn_tankerlabinprocExportToExcel);
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
                            Toasty.warning(OT_Completed_inproc_laboratory.this, "Invalid date selection", Toast.LENGTH_SHORT).show();
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
            Sheet sheet = hssfWorkBook.createSheet("OutwardTankerLabInproc");
            // Create header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("DATE");
            headerRow.createCell(1).setCellValue("SERIALNUMBER");
            headerRow.createCell(2).setCellValue("VEHICLE_No");
            headerRow.createCell(3).setCellValue("FLUSHING PARA");
            headerRow.createCell(4).setCellValue("APPEARNCE");
            headerRow.createCell(5).setCellValue("CONDITON OF SAMPLE ");
            headerRow.createCell(6).setCellValue("SAMPLE DT");
            headerRow.createCell(7).setCellValue("RELEASE DATE");
            headerRow.createCell(8).setCellValue("COLOR");
            headerRow.createCell(9).setCellValue("ODOR");
            headerRow.createCell(10).setCellValue("DENSITY 29.5");
            headerRow.createCell(11).setCellValue("KV 40 C");
            headerRow.createCell(12).setCellValue("KV 100 C");
            headerRow.createCell(13).setCellValue("VISCOSITY INDEX");
            headerRow.createCell(14).setCellValue("TAB/TAN");
            headerRow.createCell(15).setCellValue("ANILINE POINT");
            headerRow.createCell(16).setCellValue("BREAKDOWN VOLTAGE");
            headerRow.createCell(17).setCellValue("DDF AT 90 C");
            headerRow.createCell(18).setCellValue("WATER CONTENT");
            headerRow.createCell(19).setCellValue("INTERFACIAL TENSION");
            headerRow.createCell(20).setCellValue("FLASH POINT");
            headerRow.createCell(21).setCellValue("POUR POINT");
            headerRow.createCell(22).setCellValue("RCS TEST");
            headerRow.createCell(23).setCellValue("REMARK");
            headerRow.createCell(24).setCellValue("CORRECTION REQUIRED");
            headerRow.createCell(25).setCellValue("RESISTIVITY");
            headerRow.createCell(26).setCellValue("INFRA - RED ");

            // Populate data rows
            for (int i = 0; i < datalist.size(); i++) {
                Row dataRow = sheet.createRow(i + 1); // Start from the second row (index 1) for data
                Common_Outward_model dataItem = datalist.get(i);
//                int intimelength = dataItem.getInTime()!=null ? dataItem.getInTime().length() : 0;
//                int outtimelength = dataItem.getOutTime()!=null ? dataItem.getOutTime().length() : 0;
                dataRow.createCell(0).setCellValue(formattedDate = formatDate(dataItem.getDate()));
                dataRow.createCell(1).setCellValue(dataItem.getSerialNumber());
                dataRow.createCell(2).setCellValue(dataItem.getVehicleNumber());
//                if(intimelength>0)
//                {
//                    dataRow.createCell(3).setCellValue(dataItem.getInTime().substring(12,intimelength));
//                }
//                if(intimelength>0)
//                {
//                    dataRow.createCell(4).setCellValue(dataItem.getOutTime().substring(12,intimelength));
//                }
                dataRow.createCell(3).setCellValue(dataItem.getFlushing_No());
                dataRow.createCell(4).setCellValue(dataItem.getApperance());
                dataRow.createCell(5).setCellValue(dataItem.getSampleCondition());
                dataRow.createCell(6).setCellValue(dataItem.getSample_receiving());
                dataRow.createCell(7).setCellValue(dataItem.getSample_Release_Date());
                dataRow.createCell(8).setCellValue(dataItem.getColor());
                dataRow.createCell(9).setCellValue(dataItem.getOdour());
                dataRow.createCell(10).setCellValue(dataItem.getDensity_29_5C());
                dataRow.createCell(11).setCellValue(dataItem.get_40KV());
                dataRow.createCell(12).setCellValue(dataItem.get_100KV());
                dataRow.createCell(13).setCellValue(dataItem.getViscosity_Index());
                dataRow.createCell(14).setCellValue(dataItem.getTBN_TAN());
                dataRow.createCell(15).setCellValue(dataItem.getAnline_Point());
                dataRow.createCell(16).setCellValue(dataItem.getBreakdownvoltage_BDV());
                dataRow.createCell(17).setCellValue(dataItem.getDDF_90C());
                dataRow.createCell(18).setCellValue(dataItem.getWaterContent());
                dataRow.createCell(19).setCellValue(dataItem.getInterfacialTension());
                dataRow.createCell(20).setCellValue(dataItem.getFlash_Point());
                dataRow.createCell(21).setCellValue(dataItem.getPourPoint());
                dataRow.createCell(22).setCellValue(dataItem.getRcs_Test());
                dataRow.createCell(23).setCellValue(dataItem.getLabRemark());
                dataRow.createCell(24).setCellValue(dataItem.getCorrectionRequired());
                dataRow.createCell(25).setCellValue(dataItem.getRestivity());
                dataRow.createCell(26).setCellValue(dataItem.getInfra_Red());
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
                String fileName = "Outward Tanker Lab (In Process) Data_" + dateTimeSuffix + ".xls";
                File outputfile = new File(storageVolume.getDirectory().getPath() + "/Download/" + fileName);
                while (outputfile.exists()) {
                    counter++;
                    fileName = "Outward Tanker Lab (In Process) Data_" + dateTimeSuffix + "_" + counter + ".xls";
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
        rvClub = findViewById(R.id.recyclerviewitin_inproclaboratory_cogrid_tankerkin);
        headerscroll = findViewById(R.id.itin_inproclaboratory_coheaderscroll_tankerin);
    }

    private void setUpRecyclerView()
    {
        adapterOtCompletedInprocLaboratory  = new Adapter_OT_completed_inproc_Laboratory(clubList);
        FixedGridLayoutManager manager = new FixedGridLayoutManager();
        manager.setTotalColumnCount(1);
        rvClub.setLayoutManager(manager);
        rvClub.setAdapter(adapterOtCompletedInprocLaboratory);
        rvClub.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }
    public void fetchDataFromApiforweigh(String FromDate,String Todate,String vehicleType,char nextprocess, char inOut) {
        Call<List<Common_Outward_model>> call = outwardTanker.get_tanker_production_inprocesscompleted(FromDate,Todate,vehicleType,nextprocess,inOut);
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
                Toasty.error(OT_Completed_inproc_laboratory.this,"failed..!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}