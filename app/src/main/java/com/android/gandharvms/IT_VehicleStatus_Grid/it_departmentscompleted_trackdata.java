package com.android.gandharvms.IT_VehicleStatus_Grid;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DownloadManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.icu.text.SimpleDateFormat;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
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
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gandharvms.Global_Var;
import com.android.gandharvms.InwardCompletedGrid.CommonResponseModelForAllDepartment;
import com.android.gandharvms.InwardCompletedGrid.GridCompleted;
import com.android.gandharvms.InwardCompletedGrid.gridadaptercompleted;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
import com.android.gandharvms.NotificationAlerts.NotificationCommonfunctioncls;
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

public class it_departmentscompleted_trackdata extends NotificationCommonfunctioncls {

    private final String vehicleType = Global_Var.getInstance().MenuType;
    int scrollX = 0;
    List<CommonResponseModelForAllDepartment> clubList = new ArrayList<>();
    RecyclerView rvClub;
    HorizontalScrollView headerscroll;
    it_deptcomp_trackdata_adapter gridadaptercomp;
    Button btnFromDate,btnToDate;
    TextView totrec;
    String fromdate;
    String todate;
    ImageButton imgBtnExportToExcel;
    private HSSFWorkbook hssfWorkBook;
    String formattedDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_it_departmentscompleted_trackdata);
        setupHeader();
        btnFromDate = findViewById(R.id.ItdepttrackbtnfromDate);
        btnToDate = findViewById(R.id.ItdepttrackcompbtntoDate);
        totrec = findViewById(R.id.Itdepttracktotrecord);
        fromdate = getCurrentDateTime();
        todate = getCurrentDateTime();
        imgBtnExportToExcel=findViewById(R.id.btnItdepttrackcompExportToExcel);
        hssfWorkBook = new HSSFWorkbook();
        initViews();
        getDatabydateselection();
        rvClub.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                scrollX += dx;
                headerscroll.scrollTo(scrollX, 0);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        btnFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the onClick for fromDate button
                showDatePickerDialog(btnFromDate, true);
            }
        });

        btnToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the onClick for fromDate button
                showDatePickerDialog(btnToDate, false);
            }
        });

        imgBtnExportToExcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clubList != null && !clubList.isEmpty()) {
                    new AlertDialog.Builder(it_departmentscompleted_trackdata.this)
                            .setTitle("Export Data")
                            .setMessage("Do you want to export the data to Excel?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // User confirmed, export to Excel
                                    exportToExcel(clubList);
                                }
                            })
                            .setNegativeButton("No", null) // Dismiss dialog on "No"
                            .show();
                } else {
                    Toasty.warning(getApplicationContext(), "No data to export", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getDatabydateselection() {
        ProgressDialog loadingDialog = new ProgressDialog(it_departmentscompleted_trackdata.this);
        loadingDialog.setMessage("Syncing data, please wait...");
        loadingDialog.setCancelable(false); // Prevent user from dismissing the dialog
        loadingDialog.show();
        fetchDataFromApiForCompReport(fromdate,todate,vehicleType,loadingDialog);
    }

    private void initViews() {
        rvClub = findViewById(R.id.recyclerviewitdepttrackgrid);
        headerscroll = findViewById(R.id.Itdepttrackheaderscroll);
    }

    private void setUpRecyclerView() {
        gridadaptercomp = new it_deptcomp_trackdata_adapter(clubList);
        FixedGridLayoutManager manager = new FixedGridLayoutManager();
        manager.setTotalColumnCount(1);
        rvClub.setLayoutManager(manager);
        rvClub.setAdapter(gridadaptercomp);
        rvClub.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    public void fetchDataFromApiForCompReport(String fromdate, String todate, String vehicleType, ProgressDialog loadingDialog) {
        Call<List<CommonResponseModelForAllDepartment>> call = RetroApiClient
                .getserccrityveh()
                .getitreportdata(fromdate, todate, vehicleType);

        call.enqueue(new Callback<List<CommonResponseModelForAllDepartment>>() {
            @Override
            public void onResponse(Call<List<CommonResponseModelForAllDepartment>> call, Response<List<CommonResponseModelForAllDepartment>> response) {
                loadingDialog.dismiss();

                try {
                    if (response.isSuccessful() && response.body() != null) {
                        List<CommonResponseModelForAllDepartment> data = response.body();

                        if (data != null && !data.isEmpty()) {
                            clubList = data;
                            totrec.setText("Tot-Rec: " + data.size());
                            setUpRecyclerView();
                        } else {
                            clubList = new ArrayList<>();
                            totrec.setText("Tot-Rec: 0");
                            Toasty.info(it_departmentscompleted_trackdata.this, "No records found", Toast.LENGTH_SHORT).show();
                            // You can also clear RecyclerView if needed
                        }
                    } else {
                        Toasty.error(it_departmentscompleted_trackdata.this, "Server error: " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toasty.error(it_departmentscompleted_trackdata.this, "Something went wrong while processing response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<CommonResponseModelForAllDepartment>> call, Throwable t) {
                loadingDialog.dismiss(); // Always dismiss the dialog
                Log.e("Retrofit", "Failure: " + t.getMessage());

                if (t instanceof HttpException) {
                    Response<?> errorResponse = ((HttpException) t).response();
                    if (errorResponse != null) {
                        Log.e("Retrofit", "HTTP error code: " + errorResponse.code());
                        try {
                            Log.e("Retrofit", "Error Body: " + errorResponse.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                Toasty.error(it_departmentscompleted_trackdata.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void exportToExcel(List<CommonResponseModelForAllDepartment> datalist) {
        try {
            Sheet sheet = hssfWorkBook.createSheet("InwardTankerDepartmentTrackStatus");
            // Create header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("VEHICLE_NO");
            headerRow.createCell(1).setCellValue("SERIALNUMBER");
            headerRow.createCell(2).setCellValue("DATE");
            headerRow.createCell(3).setCellValue("SEC_INTIME");
            headerRow.createCell(4).setCellValue("SEC_OUTTIME");
            headerRow.createCell(5).setCellValue("SEC_WAITINGTIME");
            headerRow.createCell(6).setCellValue("WEI_INTIME");
            headerRow.createCell(7).setCellValue("WEI_OUTTIME");
            headerRow.createCell(8).setCellValue("WEI_WAITINGTIME");
            headerRow.createCell(9).setCellValue("SAM_INTIME");
            headerRow.createCell(10).setCellValue("SAM_OUTTIME");
            headerRow.createCell(11).setCellValue("SAM_WAITINGTIME");
            headerRow.createCell(12).setCellValue("LAB_INTIME");
            headerRow.createCell(13).setCellValue("LAB_OUTTIME");
            headerRow.createCell(14).setCellValue("LAB_WAITINGTIME");
            headerRow.createCell(15).setCellValue("PRO_INTIME");
            headerRow.createCell(16).setCellValue("PRO_OUTTIME");
            headerRow.createCell(17).setCellValue("PRO_WAITINGTIME");
            headerRow.createCell(18).setCellValue("OUTWEI_INTIME");
            headerRow.createCell(19).setCellValue("OUTWEI_OUTTIME");
            headerRow.createCell(20).setCellValue("OUTWEI_WAITINGTIME");
            headerRow.createCell(21).setCellValue("OUTSEC_INTIME");
            headerRow.createCell(22).setCellValue("OUTSEC_OUTTIME");
            headerRow.createCell(23).setCellValue("OUTSEC_WAITINGTIME");

            // Populate data rows
            for (int i = 0; i < datalist.size(); i++) {
                Row dataRow = sheet.createRow(i + 1); // Start from the second row (index 1) for data
                CommonResponseModelForAllDepartment club = datalist.get(i);
                String vehicleno=club.getVehicleNo()!=null?club.getVehicleNo():"";
                String serialno=club.getSerialNo()!=null?club.getSerialNo():"";
                String date=club.getDate()!=null?club.getDate().substring(0,12):"";
                String secintimelength = club.getSecIntime()!=null ? club.getSecIntime() : "";
                String secouttimelength = club.getSecOuttime()!=null ? club.getSecOuttime(): "";
                String secwaittimelength = club.getSecWTTime()!=null ? club.getSecWTTime(): "";
                String weiintimelength = club.getWeiIntime()!=null ? club.getWeiIntime() : "";
                String weiouttimelength = club.getWeiOuttime()!=null ? club.getWeiOuttime(): "";
                String weiwaittimelength = club.getWeiWTTime()!=null ? club.getWeiWTTime(): "";
                String samintimelength = club.getSamIntime()!=null ? club.getSamIntime() : "";
                String samouttimelength = club.getSamOuttime()!=null ? club.getSamOuttime(): "";
                String samwaittimelength = club.getSamWTTime()!=null ? club.getSamWTTime(): "";
                String prointimelength = club.getProIntime()!=null ? club.getProIntime() : "";
                String proouttimelength = club.getProOuttime()!=null ? club.getProOuttime(): "";
                String prowaittimelength = club.getProWTTime()!=null ? club.getProWTTime(): "";
                String labintimelength = club.getLabIntime()!=null ? club.getLabIntime() : "";
                String labouttimelength = club.getLabOuttime()!=null ? club.getLabOuttime(): "";
                String labwaittimelength = club.getLabWTTime()!=null ? club.getLabWTTime(): "";
                String outweiintimelength = club.getOutWeiInTime()!=null ? club.getOutWeiInTime() : "";
                String outweiouttimelength = club.getOutWeiOutTime()!=null ? club.getOutWeiOutTime(): "";
                String outweiwaittimelength = club.getOutWeiWTTime()!=null ? club.getOutWeiWTTime(): "";
                String outsecintimelength = club.getOutSecInTime()!=null ? club.getOutSecInTime() : "";
                String outsecouttimelength = club.getOutSecOutTime()!=null ? club.getOutSecOutTime(): "";
                String outsecwaittimelength = club.getOutSecWTTime()!=null ? club.getOutSecWTTime(): "";

                dataRow.createCell(0).setCellValue(vehicleno);
                dataRow.createCell(1).setCellValue(serialno);
                dataRow.createCell(2).setCellValue(formattedDate = formatDate(date));
                dataRow.createCell(3).setCellValue(secintimelength);
                dataRow.createCell(4).setCellValue(secouttimelength);
                dataRow.createCell(5).setCellValue(secwaittimelength);
                dataRow.createCell(6).setCellValue(weiintimelength);
                dataRow.createCell(7).setCellValue(weiouttimelength);
                dataRow.createCell(8).setCellValue(weiwaittimelength);
                dataRow.createCell(9).setCellValue(samintimelength);
                dataRow.createCell(10).setCellValue(samouttimelength);
                dataRow.createCell(11).setCellValue(samwaittimelength);
                dataRow.createCell(12).setCellValue(prointimelength);
                dataRow.createCell(13).setCellValue(proouttimelength);
                dataRow.createCell(14).setCellValue(prowaittimelength);
                dataRow.createCell(15).setCellValue(labintimelength);
                dataRow.createCell(16).setCellValue(labouttimelength);
                dataRow.createCell(17).setCellValue(labwaittimelength);
                dataRow.createCell(18).setCellValue(outweiintimelength);
                dataRow.createCell(19).setCellValue(outweiouttimelength);
                dataRow.createCell(20).setCellValue(outweiwaittimelength);
                dataRow.createCell(21).setCellValue(outsecintimelength);
                dataRow.createCell(22).setCellValue(outsecouttimelength);
                dataRow.createCell(23).setCellValue(outsecwaittimelength);
            }

            saveWorkbookToDownloads(hssfWorkBook);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    private void saveWorkbookToDownloads(HSSFWorkbook hssfWorkBook) {
        try {
            String dateTimeSuffix = new SimpleDateFormat("ddMMMyyyy_HHmmss", Locale.getDefault()).format(new Date());
            String fileName = "InwardTanker_DepartmentTrackData_" + dateTimeSuffix + ".xls";

            File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File file = new File(downloadsDir, fileName);

            FileOutputStream out = new FileOutputStream(file);
            hssfWorkBook.write(out);
            out.flush();
            out.close();
            hssfWorkBook.close();

            // Trigger media scan so file appears in file manager
            MediaScannerConnection.scanFile(this,
                    new String[]{file.getAbsolutePath()},
                    new String[]{"application/vnd.ms-excel"},
                    null);

            Toasty.success(this, "Excel file saved to Downloads", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toasty.error(this, "Export failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void showDatePickerDialog(final TextView dateTextView, final boolean isFromDate) {
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
                            ProgressDialog loadingDialog = new ProgressDialog(it_departmentscompleted_trackdata.this);
                            loadingDialog.setMessage("Syncing data, please wait...");
                            loadingDialog.setCancelable(false); // Prevent user from dismissing the dialog
                            loadingDialog.show();
                            fetchDataFromApiForCompReport(fromdate, todate,vehicleType,loadingDialog);

                            rvClub.addOnScrollListener(new RecyclerView.OnScrollListener() {
                                @Override
                                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                                    super.onScrolled(recyclerView, dx, dy);
                                }

                                @Override
                                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                                    super.onScrollStateChanged(recyclerView, newState);
                                }
                            });
                        } else {
                            // Show an error message or take appropriate action
                            Toasty.warning(it_departmentscompleted_trackdata.this, "Invalid date selection", Toast.LENGTH_SHORT).show();
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
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(now);
    }
}