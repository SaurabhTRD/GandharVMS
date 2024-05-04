package com.android.gandharvms.Outward_Truck_Dispatch;

public class Verified_Small_pack {

    public int OutwardId;
    public String DestsplVerified;
    public String DestSPLVerifiedRemark;
    public char NextProcess;
    public char I_O;
    public String VehicleType;
    public String UpdatedBy;

    public Verified_Small_pack(int outwardId, String destsplVerified, String destSPLVerifiedRemark, char nextProcess, char i_O, String vehicleType, String updatedBy) {
        OutwardId = outwardId;
        DestsplVerified = destsplVerified;
        DestSPLVerifiedRemark = destSPLVerifiedRemark;
        NextProcess = nextProcess;
        I_O = i_O;
        VehicleType = vehicleType;
        UpdatedBy = updatedBy;
    }
}
