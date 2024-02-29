package com.android.gandharvms.Outward_Truck_Logistic;

public class InTrLogisticRequestModel {
    public int OutwardId;
    public String InTime;
    public String OutTime;
    public boolean IsActive;
    public String CreatedBy;
    public String UpdatedBy;
    public String Remark;
    public char CurrentProcess;
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

    public InTrLogisticRequestModel(int outwardId, String inTime, String outTime, String createdBy, String updatedBy, String remark, char currentProcess, String serialNumber, String vehicleNumber, String OAnumber, char nextProcess, char i_O, String vehicleType) {
        OutwardId = outwardId;
        InTime = inTime;
        OutTime = outTime;
        CreatedBy = createdBy;
        UpdatedBy = updatedBy;
        Remark = remark;
        CurrentProcess = currentProcess;
        SerialNumber = serialNumber;
        VehicleNumber = vehicleNumber;
        this.OAnumber = OAnumber;
        NextProcess = nextProcess;
        I_O = i_O;
        VehicleType = vehicleType;
    }
}
