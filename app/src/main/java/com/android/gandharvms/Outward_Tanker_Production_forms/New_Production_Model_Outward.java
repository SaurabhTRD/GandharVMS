package com.android.gandharvms.Outward_Tanker_Production_forms;

public class New_Production_Model_Outward {
    public int OutwardId;
    public String ProInTime;
    public String ProOutTime;
    public String TankOrBlenderNo;
    public String Psign;
    public String OperatorSign;
    public String ProductionProcess;
    public String ProRemark;
    public String CreatedBy;
    public String VehicleType;
    public String SerialNumber;
    public String VehicleNumber;
    public char Nextprocess;
    public char I_O;
    public String UpdatedBy;

    public New_Production_Model_Outward(int outwardId, String proInTime, String proOutTime, String tankOrBlenderNo, String psign, String operatorSign, String productionProcess, String proRemark, String createdBy, String vehicleType, String serialNumber, String vehicleNumber, char nextprocess, char i_O, String updatedBy) {
        OutwardId = outwardId;
        ProInTime = proInTime;
        ProOutTime = proOutTime;
        TankOrBlenderNo = tankOrBlenderNo;
        Psign = psign;
        OperatorSign = operatorSign;
        ProductionProcess = productionProcess;
        ProRemark = proRemark;
        CreatedBy = createdBy;
        VehicleType = vehicleType;
        SerialNumber = serialNumber;
        VehicleNumber = vehicleNumber;
        Nextprocess = nextprocess;
        I_O = i_O;
        UpdatedBy = updatedBy;
    }
}
