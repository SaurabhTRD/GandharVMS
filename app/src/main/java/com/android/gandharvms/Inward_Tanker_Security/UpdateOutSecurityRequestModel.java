package com.android.gandharvms.Inward_Tanker_Security;

public class UpdateOutSecurityRequestModel {
    public String OutInTime;
    public int InwardId;

    public String IrCopy;
    public String DeliveryBill;
    public String TaxInvoice;
    public String EwayBill;
    public char Nextprocess;
    public char I_O;
    public String VehicleType;
    public String UpdatedBy;

    public UpdateOutSecurityRequestModel(String outInTime, int inwardId, String irCopy, String deliveryBill, String taxInvoice, String ewayBill, char nextprocess, char i_O, String vehicleType, String updatedBy) {
        OutInTime = outInTime;
        InwardId = inwardId;
        IrCopy = irCopy;
        DeliveryBill = deliveryBill;
        TaxInvoice = taxInvoice;
        EwayBill = ewayBill;
        Nextprocess = nextprocess;
        I_O = i_O;
        VehicleType = vehicleType;
        UpdatedBy = updatedBy;
    }
}
