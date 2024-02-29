package com.android.gandharvms.Outward_Tanker_Production_forms;

public class Production_bulkloading_model {
    int OutwardId;
    String ProInTime;
    String ProOutTime;
    String ProRemark;
    String Psign;
    char ProductionProcess;
    String CreatedBy;
    String UpdatedBy;
    String SerialNumber;
    String VehicleNumber;
    char NextProcess;
    char I_O;
    String VehicleType;

    public Production_bulkloading_model(int outwardId, String proInTime, String proOutTime, String proRemark, String psign, char productionProcess, String createdBy, String updatedBy, String serialNumber, String vehicleNumber, char nextProcess, char i_O, String vehicleType) {
        OutwardId = outwardId;
        ProInTime = proInTime;
        ProOutTime = proOutTime;
        ProRemark = proRemark;
        Psign = psign;
        ProductionProcess = productionProcess;
        CreatedBy = createdBy;
        UpdatedBy = updatedBy;
        SerialNumber = serialNumber;
        VehicleNumber = vehicleNumber;
        NextProcess = nextProcess;
        I_O = i_O;
        VehicleType = vehicleType;
    }


}
