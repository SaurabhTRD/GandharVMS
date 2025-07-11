package com.android.gandharvms.QR_Code;

public class VehicleManager {
    private String vehicleNumber;
    private String serialNumber;


    public VehicleManager(String vehicleNumber, String serialNumber) {
        this.vehicleNumber = vehicleNumber;
        this.serialNumber = serialNumber;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
}


