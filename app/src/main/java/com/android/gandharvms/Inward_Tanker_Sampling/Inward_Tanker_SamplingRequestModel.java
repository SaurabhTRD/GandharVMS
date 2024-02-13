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
    char VehicleType ;
    char FactoryOut ;
    String Date ;

    public Inward_Tanker_SamplingRequestModel(int id, int inwardId, String sampleReceivingTime, String sampleSubmittedTime, boolean isActive, String createdBy, String vehicleNo, String serialNo, char nextprocess, char i_O, char vehicleType, char factoryOut, String date) {
        Id = id;
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
    }

}
