package com.android.gandharvms.Inward_Tanker_Security;

public class gridmodel {

    String SerialNumber,material,vehicalnumber;

    public gridmodel() {
    }

    public gridmodel(String serialNumber, String material, String vehicalnumber) {
        SerialNumber = serialNumber;
        this.material = material;
        this.vehicalnumber = vehicalnumber;
    }

    public String getSerialNumber() {
        return SerialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        SerialNumber = serialNumber;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getVehicalnumber() {
        return vehicalnumber;
    }

    public void setVehicalnumber(String vehicalnumber) {
        this.vehicalnumber = vehicalnumber;
    }
}
