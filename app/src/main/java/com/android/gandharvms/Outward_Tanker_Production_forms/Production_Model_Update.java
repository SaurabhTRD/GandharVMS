package com.android.gandharvms.Outward_Tanker_Production_forms;

public class Production_Model_Update {

    public int Id ;
    public int OutwardId ;
    public String LabInTime ;
    public String LabOutTime ;
    public String ProInTime ;
    public String ProOutTime ;
    public String LabRemark ;
    public String ProRemark ;
    public int FlushingNo ;
    public int QtyChginLtr1 ;
    public int QtyChginLtr2 ;
    public int QtyChginLtr3 ;
    public int QtyChginLtr4 ;
    public int QtyChginLtr5 ;
    public String Status ;
    public String QcSign ;
    public String TankOrBlenderNo ;
    public String BulkPqty ;
    public String BatchNo ;
    public String Psign ;
    public String OperatorSign ;
    public char ProductionProcess ;
    public char LaboratoryProcess ;
    public boolean IsActive ;
    public String CreatedBy ;
    public String CreatedDate ;
    public String UpdatedBy ;
    public String UpdatedDate ;
    public String SerialNumber ;
    public String VehicleNumber ;
    public String TransportName ;
    public String MobileNumber ;
    public String CapacityVehicle ;
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

    public Production_Model_Update(int outwardId, String tankOrBlenderNo, String bulkPqty, String batchNo, String updatedBy, char nextProcess, char i_O, String vehicleType) {
        OutwardId = outwardId;
        TankOrBlenderNo = tankOrBlenderNo;
        BulkPqty = bulkPqty;
        BatchNo = batchNo;
        UpdatedBy = updatedBy;
        NextProcess = nextProcess;
        I_O = i_O;
        VehicleType = vehicleType;
    }
}
