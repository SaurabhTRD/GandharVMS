package com.android.gandharvms.OutwardOutDataEntryForm_Production;

public class otoutDataEntryProduction_RequestModel {
    int OutwardId;
    String DataEntryInTime;
    String DataEntryOutTime;
    String DataEntryRemark;
    char NextProcess;
    char I_O;
    String VehicleType;
    String UpdatedBy;

    public otoutDataEntryProduction_RequestModel(int outwardId, String dataEntryInTime, String dataEntryOutTime,
                                                 String dataEntryRemark, char nextProcess, char i_O, String vehicleType,
                                                 String updatedBy) {
        OutwardId = outwardId;
        DataEntryInTime = dataEntryInTime;
        DataEntryOutTime = dataEntryOutTime;
        DataEntryRemark = dataEntryRemark;
        NextProcess = nextProcess;
        I_O = i_O;
        VehicleType = vehicleType;
        UpdatedBy = updatedBy;
    }
}
