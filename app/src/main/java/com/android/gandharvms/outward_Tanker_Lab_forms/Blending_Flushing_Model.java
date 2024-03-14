package com.android.gandharvms.outward_Tanker_Lab_forms;

public class Blending_Flushing_Model {
    public int OutwardId;
    public String LabInTime;
    public String LabOutTime;
    public char FlushingStatus;
    public char BlendingStatus;
    public int StatusCount;
    public String UpdatedBy;
    public char NextProcess;
    public char I_O;
    public String VehicleType;

    public Blending_Flushing_Model(int outwardId, String labInTime, String labOutTime, char flushingStatus, char blendingStatus, int statusCount, String updatedBy, char nextProcess, char i_O, String vehicleType) {
        OutwardId = outwardId;
        LabInTime = labInTime;
        LabOutTime = labOutTime;
        FlushingStatus = flushingStatus;
        BlendingStatus = blendingStatus;
        StatusCount = statusCount;
        UpdatedBy = updatedBy;
        NextProcess = nextProcess;
        I_O = i_O;
        VehicleType = vehicleType;
    }
}
