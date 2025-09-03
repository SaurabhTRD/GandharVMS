package com.android.gandharvms.OT_VehicleStatus_Grid;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.icu.text.SimpleDateFormat;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
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
import com.android.gandharvms.IR_VehicleStatus_Grid.ir_deptcomp_trackdata_adapter;
import com.android.gandharvms.InwardCompletedGrid.CommonResponseModelForAllDepartment;
import com.android.gandharvms.NotificationAlerts.NotificationCommonfunctioncls;
import com.android.gandharvms.OT_CompletedReport.Outward_Tanker_CompReportAdapter;
import com.android.gandharvms.OT_CompletedReport.Outward_Tanker_CompletedReport;
import com.android.gandharvms.LoginWithAPI.RetroApiClient;
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

public class ot_departmentscompletedexport_trackdata extends NotificationCommonfunctioncls {

    private final String vehicleType = Global_Var.getInstance().MenuType;
    int scrollX = 0;
    List<Common_Outward_model> clubList = new ArrayList<>();
    RecyclerView rvClub;
    HorizontalScrollView headerscroll;
    ot_departmentscompletedexport_trackdataadapter gridadaptercomp;
    Button btnFromDate,btnToDate;
    TextView totrec;
    String fromdate;
    String todate;
    ImageButton imgBtnExportToExcel;
    private HSSFWorkbook hssfWorkBook;
    String formattedDate;
    private Outward_Tanker outwardTanker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ot_departmentscompletedexport_trackdata);
        btnFromDate = findViewById(R.id.OtdeptcomptrackexportbtnfromDate);
        btnToDate = findViewById(R.id.OtdeptcomptrackexportbtntoDate);
        totrec=findViewById(R.id.Otdeptcomptrackexporttotrecord);
        fromdate = getCurrentDateTime();
        todate = getCurrentDateTime();
        imgBtnExportToExcel=findViewById(R.id.btnOtdeptdeptcomptrackexportToExcel);
        hssfWorkBook = new HSSFWorkbook();
        outwardTanker = RetroApiClient.insertoutwardtankersecurity();
        setupHeader();
        initViews();
        getDatabydateselection();

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
                    new AlertDialog.Builder(ot_departmentscompletedexport_trackdata.this)
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

    private void initViews()
    {
        rvClub = findViewById(R.id.Otdeptcomptrackexportrecy);
        headerscroll = findViewById(R.id.Otdeptcomptrackexporths);
    }

    private void getDatabydateselection() {
        ProgressDialog loadingDialog = new ProgressDialog(ot_departmentscompletedexport_trackdata.this);
        loadingDialog.setMessage("Syncing data, please wait...");
        loadingDialog.setCancelable(false); // Prevent user from dismissing the dialog
        loadingDialog.show();
        //fetchDataFromApiForCompReport(fromdate,todate,vehicleType,loadingDialog);
        fetchDataFromApi(fromdate,todate,vehicleType,loadingDialog);
    }

    private void setUpRecyclerView()
    {
        gridadaptercomp  = new ot_departmentscompletedexport_trackdataadapter(clubList);
        FixedGridLayoutManager manager = new FixedGridLayoutManager();
        manager.setTotalColumnCount(1);
        rvClub.setLayoutManager(manager);
        rvClub.setAdapter(gridadaptercomp);
        rvClub.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    public void fetchDataFromApi(String fromdate, String todate,String vehicleType,ProgressDialog loadingDialog) {
        Call<List<Common_Outward_model>> call = outwardTanker.getVehicleCompReportData(fromdate,todate,vehicleType);
        call.enqueue(new Callback<List<Common_Outward_model>>() {
            @Override
            public void onResponse(Call<List<Common_Outward_model>> call, Response<List<Common_Outward_model>> response) {
                loadingDialog.dismiss();
                try {
                    if (response.isSuccessful() && response.body() != null) {
                        List<Common_Outward_model> data = response.body();
                        if (response.body().size() > 0 && data != null && !data.isEmpty()) {
                            int totalcount = data.size();
                            totrec.setText("Tot-Rec: " + totalcount);
                            clubList = data;
                            setUpRecyclerView();
                        } else {
                            clubList = new ArrayList<>();
                            totrec.setText("Tot-Rec: 0");
                            Toasty.info(ot_departmentscompletedexport_trackdata.this, "No records found", Toast.LENGTH_SHORT).show();
                            // You can also clear RecyclerView if needed
                        }
                    } else {
                        Toasty.error(ot_departmentscompletedexport_trackdata.this, "Server error: " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                    Toasty.error(ot_departmentscompletedexport_trackdata.this, "Something went wrong while processing response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Common_Outward_model>> call, Throwable t) {
                loadingDialog.dismiss();
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
                Toasty.error(ot_departmentscompletedexport_trackdata.this,"failed..!", Toast.LENGTH_SHORT).show();
            }
        });
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
                            ProgressDialog loadingDialog = new ProgressDialog(ot_departmentscompletedexport_trackdata.this);
                            loadingDialog.setMessage("Syncing data, please wait...");
                            loadingDialog.setCancelable(false); // Prevent user from dismissing the dialog
                            loadingDialog.show();
                            fetchDataFromApi(fromdate, todate,vehicleType,loadingDialog);

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
                            Toasty.warning(ot_departmentscompletedexport_trackdata.this, "Invalid date selection", Toast.LENGTH_SHORT).show();
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
            Sheet sheet = hssfWorkBook.createSheet("OutwardTankerDepartmentTrackStatus");
            // Create header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("DATE");
            headerRow.createCell(1).setCellValue("SERIALNUMBER");
            headerRow.createCell(2).setCellValue("VEHICLE_NO");
            headerRow.createCell(3).setCellValue("SEC_INTIME");
            headerRow.createCell(4).setCellValue("SEC_OUTTIME");
            headerRow.createCell(5).setCellValue("SEC_WTTIME");
            headerRow.createCell(6).setCellValue("Bill_INTIME");
            headerRow.createCell(7).setCellValue("Bill_OUTTIME");
            headerRow.createCell(8).setCellValue("Bill_WTTIME");
            headerRow.createCell(9).setCellValue("WEI_INTIME");
            headerRow.createCell(10).setCellValue("WEI_OUTTIME");
            headerRow.createCell(11).setCellValue("WEI_WTTIME");
            headerRow.createCell(12).setCellValue("PRO_INTIME");
            headerRow.createCell(13).setCellValue("PRO_OUTTIME");
            headerRow.createCell(14).setCellValue("PRO_WTIME");
            headerRow.createCell(15).setCellValue("LAB_INTIME");
            headerRow.createCell(16).setCellValue("LAB_OUTTIME");
            headerRow.createCell(17).setCellValue("LAB_WTTIME");
            headerRow.createCell(18).setCellValue("OUTWEI_INTIME");
            headerRow.createCell(19).setCellValue("OUTWEI_OUTTIME");
            headerRow.createCell(20).setCellValue("OUTWEI_WTTIME");
            headerRow.createCell(21).setCellValue("OUTDEINTIME");
            headerRow.createCell(22).setCellValue("OUTDEOUTTIME");
            headerRow.createCell(23).setCellValue("OUTDEWTTIME");
            headerRow.createCell(24).setCellValue("OUTBILL_INTIME");
            headerRow.createCell(25).setCellValue("OUTBILL_OUTTIME");
            headerRow.createCell(26).setCellValue("OUTBILL_WTTIME");
            headerRow.createCell(27).setCellValue("OUTSEC_INTIME");
            headerRow.createCell(28).setCellValue("OUTSEC_OUTTIME");
            headerRow.createCell(29).setCellValue("OUTSEC_WTTIME");

            // Populate data rows
            for (int i = 0; i < datalist.size(); i++) {
                Row dataRow = sheet.createRow(i + 1); // Start from the second row (index 1) for data
                Common_Outward_model club = datalist.get(i);
                String date=club.getDate()!=null?club.getDate().substring(0,12):"";
                String vehicleno=club.getVehicleNumber()!=null?club.getVehicleNumber():"";
                String serialno=club.getSerialNumber()!=null?club.getSerialNumber():"";
                String secintimelength = club.getSecInTime()!=null ? club.getSecInTime() : "";
                String secouttimelength = club.getSecOutTime()!=null ? club.getSecOutTime(): "";
                String secwaittimelength = club.getSecWTTime()!=null ? club.getSecWTTime(): "";

                String billintimelength = club.getBilInTime()!=null ? club.getBilInTime() : "";
                String billouttimelength = club.getBilOutTime()!=null ? club.getBilOutTime(): "";
                String billwaittimelength = club.getBillWTTime()!=null ? club.getBillWTTime(): "";

                String weiintimelength = club.getWeiInTime()!=null ? club.getWeiInTime() : "";
                String weiouttimelength = club.getWeiOutTime()!=null ? club.getWeiOutTime(): "";
                String weiwaittimelength = club.getWeiWTTime()!=null ? club.getWeiWTTime(): "";

                String prointimelength = club.getBLFProInTime()!=null ? club.getBLFProInTime() : "";
                String proouttimelength = club.getBLFProOutTime()!=null ? club.getBLFProOutTime(): "";
                String prowaittimelength = club.getBLFProWTTime()!=null ? club.getBLFProWTTime(): "";

                String labintimelength = club.getIPFLabInTime()!=null ? club.getIPFLabInTime() : "";
                String labouttimelength = club.getIPFLabOutTime()!=null ? club.getIPFLabOutTime(): "";
                String labwaittimelength = club.getIPFLabWTTime()!=null ? club.getIPFLabWTTime(): "";

                String outweiintimelength = club.getOutWeiInTime()!=null ? club.getOutWeiInTime() : "";
                String outweiouttimelength = club.getOutWeiOutTime()!=null ? club.getOutWeiOutTime(): "";
                String outweiwaittimelength = club.getOutWeiWTTime()!=null ? club.getOutWeiWTTime(): "";

                String outdataentryintimelength = club.getOutDataEntryInTime()!=null ? club.getOutDataEntryInTime() : "";
                String outdataentryouttimelength = club.getOutDataEntryOutTime()!=null ? club.getOutDataEntryOutTime(): "";
                String outdataentrywaittimelength = club.getOutDataEntryWTTime()!=null ? club.getOutDataEntryWTTime(): "";

                String outbillintimelength = club.getOutBilInTime()!=null ? club.getOutBilInTime() : "";
                String outbillouttimelength = club.getOutBilOutTime()!=null ? club.getOutBilOutTime(): "";
                String outbillwaittimelength = club.getOutBilWTTime()!=null ? club.getOutBilWTTime(): "";

                String outsecintimelength = club.getOutSecInTime()!=null ? club.getOutSecInTime() : "";
                String outsecouttimelength = club.getOutSecOutTime()!=null ? club.getOutSecOutTime(): "";
                String outsecwaittimelength = club.getOutSecWTTime()!=null ? club.getOutSecWTTime(): "";

                dataRow.createCell(0).setCellValue(formattedDate = formatDate(date));
                dataRow.createCell(1).setCellValue(serialno);
                dataRow.createCell(2).setCellValue(vehicleno);
                dataRow.createCell(3).setCellValue(secintimelength);
                dataRow.createCell(4).setCellValue(secouttimelength);
                dataRow.createCell(5).setCellValue(secwaittimelength);
                dataRow.createCell(6).setCellValue(billintimelength);
                dataRow.createCell(7).setCellValue(billouttimelength);
                dataRow.createCell(8).setCellValue(billwaittimelength);
                dataRow.createCell(9).setCellValue(weiintimelength);
                dataRow.createCell(10).setCellValue(weiouttimelength);
                dataRow.createCell(11).setCellValue(weiwaittimelength);
                dataRow.createCell(12).setCellValue(prointimelength);
                dataRow.createCell(13).setCellValue(proouttimelength);
                dataRow.createCell(14).setCellValue(prowaittimelength);
                dataRow.createCell(15).setCellValue(labintimelength);
                dataRow.createCell(16).setCellValue(labouttimelength);
                dataRow.createCell(17).setCellValue(labwaittimelength);
                dataRow.createCell(18).setCellValue(outweiintimelength);
                dataRow.createCell(19).setCellValue(outweiouttimelength);
                dataRow.createCell(20).setCellValue(outweiwaittimelength);
                dataRow.createCell(21).setCellValue(outdataentryintimelength);
                dataRow.createCell(22).setCellValue(outdataentryouttimelength);
                dataRow.createCell(23).setCellValue(outdataentrywaittimelength);
                dataRow.createCell(24).setCellValue(outbillintimelength);
                dataRow.createCell(25).setCellValue(outbillouttimelength);
                dataRow.createCell(26).setCellValue(outbillwaittimelength);
                dataRow.createCell(27).setCellValue(outsecintimelength);
                dataRow.createCell(28).setCellValue(outsecouttimelength);
                dataRow.createCell(29).setCellValue(outsecwaittimelength);
            }

            saveWorkbookToDownloads(hssfWorkBook);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    private void saveWorkbookToDownloads(HSSFWorkbook hssfWorkBook) {
        try {
            String dateTimeSuffix = new SimpleDateFormat("ddMMMyyyy_HHmmss", Locale.getDefault()).format(new Date());
            String fileName = "OutwardTanker_DepartmentTrackData_" + dateTimeSuffix + ".xls";

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