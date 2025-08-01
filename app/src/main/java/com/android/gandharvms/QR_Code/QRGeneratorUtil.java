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

    public static void callUpdateEmployeeExSOAP(Activity activity,
                                                String vehicleNumber,
                                                String serialNumber,
                                                String intime,
                                                String date) {
        new Thread(() -> {
            try {
                String soapRequest = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                        "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">\n" +
                        "  <soap12:Body>\n" +
                        "    <UpdateEmployeeEx xmlns=\"http://tempuri.org/\">\n" +
                        "      <UserName>essl</UserName>\n" +      //Client UserName
                        "      <Password>essl</Password>\n" +      ////Client Password
                        "      <EmployeeCode>" + vehicleNumber + "</EmployeeCode>\n" +   //vehicle No
                        "      <EmployeeName>" + serialNumber + "</EmployeeName>\n" +     // serial no
                        "      <EmployeeLocation>Mumbai</EmployeeLocation>\n" +       ////Client Location
                        "      <EmployeeRole>Normal</EmployeeRole>\n" +                //Normal
                        "      <EmployeeVerificationType>Finger or Face or Card or Password</EmployeeVerificationType>\n" +
                        "      <EmployeeExpiryFrom> " + date + " " + intime + "</EmployeeExpiryFrom>\n" +
                        "      <EmployeeExpiryTo>2050-07-31</EmployeeExpiryTo>\n" +
                        "      <EmployeeCardNumber>123456</EmployeeCardNumber>\n" +        // QRCode
                        "      <GroupId>1</GroupId>\n" +
                        "      <EmployeePhoto></EmployeePhoto>\n" +
                        "    </UpdateEmployeeEx>\n" +
                        "  </soap12:Body>\n" +
                        "</soap12:Envelope>";

                java.net.URL url = new java.net.URL("http://ebioservernew.esslsecurity.com:99/webservice.asmx?op=UpdateEmployeeEx");
                java.net.HttpURLConnection connection = (java.net.HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/soap+xml; charset=utf-8");
                connection.setDoOutput(true);

                connection.getOutputStream().write(soapRequest.getBytes());

                java.io.InputStream responseStream = connection.getInputStream();
                java.util.Scanner s = new java.util.Scanner(responseStream).useDelimiter("\\A");
                String response = s.hasNext() ? s.next() : "";

                // Log or show on UI thread
                activity.runOnUiThread(() -> {
                    android.util.Log.d("SOAP_RESPONSE", response);
                    android.widget.Toast.makeText(activity, "API called successfully!", android.widget.Toast.LENGTH_SHORT).show();
                });

            } catch (Exception e) {
                e.printStackTrace();
                activity.runOnUiThread(() -> {
                    android.widget.Toast.makeText(activity, "API call failed: " + e.getMessage(), android.widget.Toast.LENGTH_LONG).show();
                });
            }
        }).start();
    }

}
