package com.android.gandharvms.Outward_Truck_Dispatch;

public class Varified_Model {
    public int OutwardId;
    public String DestilVerified;
    public char NextProcess;
    public char I_O;
    public String VehicleType;
    public String UpdatedBy;
    public String DestILVerifiedRemark;

    public Varified_Model(int outwardId, String destilVerified, char nextProcess, char i_O, String vehicleType, String updatedBy, String destILVerifiedRemark) {
        OutwardId = outwardId;
        DestilVerified = destilVerified;
        NextProcess = nextProcess;
        I_O = i_O;
        VehicleType = vehicleType;
        UpdatedBy = updatedBy;
        DestILVerifiedRemark = destILVerifiedRemark;

    }
}
