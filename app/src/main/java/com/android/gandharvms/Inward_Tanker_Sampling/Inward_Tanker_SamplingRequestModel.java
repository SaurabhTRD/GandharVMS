package com.android.gandharvms.Inward_Tanker_Sampling;

public class Inward_Tanker_SamplingRequestModel {
    int Id ;
    int InwardId ;
    String SampleReceivingTime ;
    String SampleSubmittedTime ;
    String CreatedBy ;
    String UpdatedBy ;
    String VehicleNo ;
    String SerialNo ;
    char Nextprocess ;
    char I_O ;
    String VehicleType ;

    public Inward_Tanker_SamplingRequestModel(int inwardId, String sampleReceivingTime,
                                              String sampleSubmittedTime, String createdBy,
                                              String vehicleNo, String serialNo,String vehicleType,
                                              char nextprocess, char i_O,String updatedBy) {
        InwardId = inwardId;
        SampleReceivingTime = sampleReceivingTime;
        SampleSubmittedTime = sampleSubmittedTime;
        CreatedBy = createdBy;
        VehicleNo = vehicleNo;
        SerialNo = serialNo;
        VehicleType = vehicleType;
        Nextprocess = nextprocess;
        I_O = i_O;
        UpdatedBy = updatedBy;
    }
}
