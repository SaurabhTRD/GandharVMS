package com.android.gandharvms.Outward_Tanker_Weighment;

public class Model_OutwardOut_Truck_Weighment {
    public int Id;
    public int OutwardId;
    public String Intime;
    public String OutTime;
    public String InDriverImage;
    public String InVehicleImage;
    public String OutDriverImage;
    public String OutVehicleImage;
    public String OutInTime;
    public String NetWeight;
    public String TareWeight;
    public String GrossWeight;
    public String NumberofPack;
    public String OutWRemark;
    public String SealNumber;
    public char CurrentProcess;
    public String Remark;
    public boolean IsActive;
    public String CreatedBy;
    public String CreatedDate;
    public String UpdatedBy;
    public String UpdatedDate;
    public String SerialNumber;
    public String VehicleNumber;
    public String TransportName;
    public String MobileNumber;
    public String CapacityVehicle;
    public String ConditionOfVehicle;
    public String Date;
    public String MaterialName;
    public String CustomerName;
    public String OAnumber;
    public int TankerNumber;
    public String ProductName;
    public int HowMuchQuantityFilled;
    public String Location;
    public char NextProcess;
    public char I_O;
    public String VehicleType;

    public Model_OutwardOut_Truck_Weighment(int outwardId, String outDriverImage, String outVehicleImage, String outInTime, String netWeight, String grossWeight, String numberofPack, String outWRemark, String sealNumber, String updatedBy, char nextProcess, char i_O, String vehicleType) {
        OutwardId = outwardId;
        OutDriverImage = outDriverImage;
        OutVehicleImage = outVehicleImage;
        OutInTime = outInTime;
        NetWeight = netWeight;
        GrossWeight = grossWeight;
        NumberofPack = numberofPack;
        OutWRemark = outWRemark;
        SealNumber = sealNumber;
        UpdatedBy = updatedBy;
        NextProcess = nextProcess;
        I_O = i_O;
        VehicleType = vehicleType;
    }
}
