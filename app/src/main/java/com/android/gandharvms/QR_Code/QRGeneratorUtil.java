package com.android.gandharvms.QR_Code;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import androidx.print.PrintHelper;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;

public class QRGeneratorUtil {

    // ✅ Method to generate QR bitmap only
    public static Bitmap generateQRCode(String text, int width, int height) throws WriterException {
        QRCodeWriter writer = new QRCodeWriter();
        com.google.zxing.common.BitMatrix bitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, width, height);

        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                bmp.setPixel(x, y, bitMatrix.get(x, y) ? android.graphics.Color.BLACK : android.graphics.Color.WHITE);
            }
        }
        return bmp;
    }

    // ✅ Main reusable method to handle QR checkbox and print logic
    public static void handleQRCheckbox(Activity activity,
                                        CheckBox cbGenerateQR,
                                        EditText etVehicle,
                                        EditText etSerial,
                                        EditText etDate,
                                        EditText etTime,
                                        ImageView ivQRCode,
                                        Button btnPrintQR) {

        cbGenerateQR.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                String vehicleNo = etVehicle.getText().toString().trim();
                String serialNumber = etSerial.getText().toString().trim();
                String date = etDate.getText().toString().trim();
                String time = etTime.getText().toString().trim();

                if (TextUtils.isEmpty(vehicleNo) || TextUtils.isEmpty(serialNumber) ||
                        TextUtils.isEmpty(date) || TextUtils.isEmpty(time)) {
                    Toast.makeText(activity, "Please enter all fields", Toast.LENGTH_SHORT).show();
                    cbGenerateQR.setChecked(false);
                    return;
                }

                String qrData = "VehicleNo: " + vehicleNo + "\nSerialNo: " + serialNumber
                        + "\nDate: " + date + "\nTime: " + time;

                try {
                    Bitmap qrBitmap = generateQRCode(qrData, 300, 300);
                    if (qrBitmap != null) {
                        ivQRCode.setImageBitmap(qrBitmap);
                        ivQRCode.setVisibility(ImageView.VISIBLE);
                    }
                } catch (WriterException e) {
                    Toast.makeText(activity, "QR generation failed", Toast.LENGTH_SHORT).show();
                    ivQRCode.setVisibility(ImageView.GONE);
                }

            } else {
                ivQRCode.setVisibility(ImageView.GONE);
            }
        });

        btnPrintQR.setOnClickListener(v -> {
            if (ivQRCode.getDrawable() != null) {
                Bitmap qrBitmap = ((BitmapDrawable) ivQRCode.getDrawable()).getBitmap();
                printQRCode(activity, qrBitmap);
            } else {
                Toast.makeText(activity, "Please generate QR first!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // ✅ Correct static printQRCode method
    private static void printQRCode(Activity activity, Bitmap qrBitmap) {
        PrintHelper printHelper = new PrintHelper(activity);
        printHelper.setScaleMode(PrintHelper.SCALE_MODE_FIT);
        printHelper.printBitmap("QR_Code_Print", qrBitmap);
    }
}
