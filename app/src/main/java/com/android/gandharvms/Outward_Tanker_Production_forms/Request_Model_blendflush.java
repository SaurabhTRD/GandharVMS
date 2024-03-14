package com.android.gandharvms.Outward_Tanker_Production_forms;

public class Request_Model_blendflush {
    public String ProInTime;
    public String ProOutTime;
    public boolean IsBlendingReq;
    public boolean IsFlushingReq;
    public String Flushing_No;
    public String CreatedBy;
    public String UpdatedBy;
    public int OutwardId;
    public char NextProcess;
    public String VehicleNumber;
    public String VehicleType;
    public String SerialNumber;
    public char I_O;

    public Request_Model_blendflush(String proInTime, String proOutTime, boolean isBlendingReq, boolean isFlushingReq, String flushing_No, String createdBy, String updatedBy, int outwardId, char nextProcess, String vehicleNumber, String vehicleType, String serialNumber, char i_O) {
        ProInTime = proInTime;
        ProOutTime = proOutTime;
        IsBlendingReq = isBlendingReq;
        IsFlushingReq = isFlushingReq;
        Flushing_No = flushing_No;
        CreatedBy = createdBy;
        UpdatedBy = updatedBy;
        OutwardId = outwardId;
        NextProcess = nextProcess;
        VehicleNumber = vehicleNumber;
        VehicleType = vehicleType;
        SerialNumber = serialNumber;
        I_O = i_O;

    }
}
