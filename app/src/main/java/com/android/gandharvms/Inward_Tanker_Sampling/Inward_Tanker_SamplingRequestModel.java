package com.android.gandharvms.Inward_Tanker_Sampling;

public class Inward_Tanker_SamplingRequestModel {
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
    String VehicleType ;
    char FactoryOut ;
    String Date ;

    public Inward_Tanker_SamplingRequestModel(int inwardId, String sampleReceivingTime, String sampleSubmittedTime, boolean isActive, String createdBy, String vehicleNo, String serialNo, char nextprocess, char i_O, String vehicleType, char factoryOut, String date) {
        InwardId = inwardId;
        SampleReceivingTime = sampleReceivingTime;
        SampleSubmittedTime = sampleSubmittedTime;
        SerialNo = serialNo;
        IsActive = isActive;
        CreatedBy = createdBy;
        VehicleNo = vehicleNo;
        Nextprocess = nextprocess;
        I_O = i_O;
        VehicleType = vehicleType;
        FactoryOut = factoryOut;
        Date = date;
        UpdatedBy = createdBy;
    }

}
