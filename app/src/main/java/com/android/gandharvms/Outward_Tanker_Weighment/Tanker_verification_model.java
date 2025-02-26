package com.android.gandharvms.Outward_Tanker_Weighment;

public class Tanker_verification_model {
    public int OutwardId;
    public String DestilVerified;
    public char NextProcess;
    public char I_O;
    public String VehicleType;
    public String UpdatedBy;
    //public String DestILVerifiedRemark;
    public String DestSPLVerifiedRemark;
    public int compartment1;
    public int compartment2;
    public int compartment3;
    public int compartment4;
    public int compartment5;
    public int compartment6;

    public Tanker_verification_model(int outwardId, String destilVerified, char nextProcess, char i_O, String vehicleType, String updatedBy, String destILVerifiedRemark, int compartment1, int compartment2, int compartment3, int compartment4, int compartment5, int compartment6) {
        OutwardId = outwardId;
        DestilVerified = destilVerified;
        NextProcess = nextProcess;
        I_O = i_O;
        VehicleType = vehicleType;
        UpdatedBy = updatedBy;
        DestSPLVerifiedRemark = destILVerifiedRemark;
        this.compartment1 = compartment1;
        this.compartment2 = compartment2;
        this.compartment3 = compartment3;
        this.compartment4 = compartment4;
        this.compartment5 = compartment5;
        this.compartment6 = compartment6;
    }
}
