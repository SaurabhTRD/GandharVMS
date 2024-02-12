package com.android.gandharvms.Inward_Tanker_Sampling;

public class Inward_Tanker_SamplingResponseModel {
    int Id ;
    int InwardId ;
    String SampleReceivingTime ;
    String SampleSubmittedTime ;
    boolean IsActive ;
    String CreatedBy ;
    String UpdatedBy ;
    String VehicleNo ;
    String SerialNo ;
    char Nextprocess ;
    char I_O ;
    char VehicleType ;
    String FactoryOut ;
    String Date ;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getInwardId() {
        return InwardId;
    }

    public void setInwardId(int inwardId) {
        InwardId = inwardId;
    }

    public String getSampleReceivingTime() {
        return SampleReceivingTime;
    }

    public void setSampleReceivingTime(String sampleReceivingTime) {
        SampleReceivingTime = sampleReceivingTime;
    }

    public String getSampleSubmittedTime() {
        return SampleSubmittedTime;
    }

    public void setSampleSubmittedTime(String sampleSubmittedTime) {
        SampleSubmittedTime = sampleSubmittedTime;
    }

    public boolean isActive() {
        return IsActive;
    }

    public void setActive(boolean active) {
        IsActive = active;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public String getUpdatedBy() {
        return UpdatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        UpdatedBy = updatedBy;
    }

    public String getVehicleNo() {
        return VehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        VehicleNo = vehicleNo;
    }

    public String getSerialNo() {
        return SerialNo;
    }

    public void setSerialNo(String serialNo) {
        SerialNo = serialNo;
    }

    public char getNextprocess() {
        return Nextprocess;
    }

    public void setNextprocess(char nextprocess) {
        Nextprocess = nextprocess;
    }

    public char getI_O() {
        return I_O;
    }

    public void setI_O(char i_O) {
        I_O = i_O;
    }

    public char getVehicleType() {
        return VehicleType;
    }

    public void setVehicleType(char vehicleType) {
        VehicleType = vehicleType;
    }

    public String getFactoryOut() {
        return FactoryOut;
    }

    public void setFactoryOut(String factoryOut) {
        FactoryOut = factoryOut;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

}
