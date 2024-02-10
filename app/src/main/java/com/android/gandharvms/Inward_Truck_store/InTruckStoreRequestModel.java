package com.android.gandharvms.Inward_Truck_store;

public class InTruckStoreRequestModel {
    int Id;
    int InwardId;
    String InTime;
    String OutTime;
    boolean IsActive;
    String CreatedBy;
    String UpdatedBy;
    String VehicleNo;
    String FactoryOut;
    String SerialNo;
    char Nextprocess;
    char I_O;
    String VehicleType;
    String InvoiceNo;
    String Date;
    String PartyName;
    String Material;
    String OA_PO_number;
    int Driver_MobileNo;
    int Qty;

    public InTruckStoreRequestModel(int inwardId, String inTime, String outTime, String createdBy, String updatedBy, String vehicleNo, String serialNo, char nextprocess, char i_O, String vehicleType, String invoiceNo, String date, String partyName, String material, String OA_PO_number, int driver_MobileNo, int qty) {
        InwardId = inwardId;
        InTime = inTime;
        OutTime = outTime;
        CreatedBy = createdBy;
        UpdatedBy = updatedBy;
        VehicleNo = vehicleNo;
        SerialNo = serialNo;
        Nextprocess = nextprocess;
        I_O = i_O;
        VehicleType = vehicleType;
        InvoiceNo = invoiceNo;
        Date = date;
        PartyName = partyName;
        Material = material;
        this.OA_PO_number = OA_PO_number;
        Driver_MobileNo = driver_MobileNo;
        Qty = qty;
    }
}
