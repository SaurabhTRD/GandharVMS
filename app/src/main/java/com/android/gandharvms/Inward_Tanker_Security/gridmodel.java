package com.android.gandharvms.Inward_Tanker_Security;

import javax.net.ssl.SSLEngineResult;

public class gridmodel {

    String SerialNumber,material,vehicalnumber,status,InTime,OutTime;

    public gridmodel() {
    }

    public gridmodel(String serialNumber, String material, String vehicalnumber, String Status,String inTime,String outTime) {
        SerialNumber = serialNumber;
        this.material = material;
        this.vehicalnumber = vehicalnumber;
        this.status = Status;
        InTime = inTime;
        OutTime = outTime;
    }

    public String getInTime() {
        return InTime;
    }

    public void setInTime(String inTime) {
        InTime = inTime;
    }

    public String getOutTime() {
        return OutTime;
    }

    public void setOutTime(String outTime) {
        OutTime = outTime;
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
