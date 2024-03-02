package com.android.gandharvms.Outward_Truck_Laboratory;

public class Model_Outward_Truck_Lab {
    public int Id ;
    public int OutwardId ;
    public String DespatchInTime ;
    public String DespatchOutTime ;
    public char DespatchProcess ;
    public String DespatchRemark ;
    public String Despatch_Sign ;
    public String LaboratoryInTime ;
    public String LaboratoryOutTime ;
    public char LaboratoryProcess ;
    public String LaboratoryRemark ;
    public String ProductionInTime ;
    public String ProductionOutTime ;
    public String ProductionRemark ;
    public char ProductionProcess ;
    public String BillingInTime ;
    public String BillingOutTime ;
    public String BillingProcess ;
    public char BillingRemark ;
    public int BarrelFormQty ;
    public int TypeOfPackagingId ;
    public boolean IsActive;
    public String CreatedBy;
    public String CreatedDate;
    public String UpdatedBy;
    public String UpdatedDate;
    public String SerialNumber ;
    public String VehicleNumber ;
    public String TransportName ;
    public String MobileNumber ;
    public String CapacityVehicle ;
    public String GrossWeight ;
    public String ConditionOfVehicle ;
    public String Date ;
    public String MaterialName ;
    public String CustomerName ;
    public String OAnumber ;
    public int TankerNumber ;
    public String ProductName ;
    public int HowMuchQuantityFilled ;
    public String Location ;
    public char NextProcess ;
    public char I_O ;
    public String VehicleType ;

    public Model_Outward_Truck_Lab(int outwardId, String laboratoryInTime, String laboratoryOutTime, char laboratoryProcess, String laboratoryRemark, String updatedBy, char nextProcess, char i_O, String vehicleType) {
        OutwardId = outwardId;
        LaboratoryInTime = laboratoryInTime;
        LaboratoryOutTime = laboratoryOutTime;
        LaboratoryProcess = laboratoryProcess;
        LaboratoryRemark = laboratoryRemark;
        UpdatedBy = updatedBy;
        NextProcess = nextProcess;
        I_O = i_O;
        VehicleType = vehicleType;
    }
}
