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
    public String SerialNumber;
    public String VehicleNumber;
    char NextProcess;
    char I_O;
    String VehicleType;

    public Production_bulkloading_model(int outwardId, String proInTime, String proOutTime, String proRemark,
           String psign, char productionProcess, String createdBy, String updatedBy, char nextProcess,String serialNumber
           ,String vehicleNumber, char i_O, String vehicleType) {
        OutwardId = outwardId;
        ProInTime = proInTime;
        ProOutTime = proOutTime;
        ProRemark = proRemark;
        Psign = psign;
        ProductionProcess = productionProcess;
        CreatedBy = createdBy;
        UpdatedBy = updatedBy;
        NextProcess = nextProcess;
        SerialNumber=serialNumber;
        VehicleNumber=vehicleNumber;
        I_O = i_O;
        VehicleType = vehicleType;
    }


}
