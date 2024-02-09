package com.android.gandharvms.Inward_Tanker_Security;

import javax.net.ssl.SSLEngineResult;

public class gridmodel {

    String SerialNumber,material,vehicalnumber,status;

    public gridmodel() {
    }

    public gridmodel(String serialNumber, String material, String vehicalnumber, String Status) {
        SerialNumber = serialNumber;
        this.material = material;
        this.vehicalnumber = vehicalnumber;
        this.status = Status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        SerialNumber = status;
    }
}
