package com.android.gandharvms.Inward_Tanker_Security;

public class Update_Request_Model_Insequrity {

    public int InwardId;
    public String SerialNo;
    public String InvoiceNo;
    public String VehicleNo;
    public String Date;
    public String PartyName;
    public  String Material;
    public String OA_PO_number;
    public  String Driver_MobileNo;
    public char Nextprocess;
    public char I_O;
    public String FactoryIn;
    public String FactoryOut;
    public String VehicleType;
    public String InTime;
    public String OutTime;
    public int QtyUOM;
    public int NetWeightUOM;
    public int NetWeight;
    public int  Qty;
    public String Extramaterials;
    public String Remark;
    public String Selectregister;
    public String IrCopy;
    public String DeliveryBill;
    public String TaxInvoice;
    public String EwayBill;
    public String UpdatedBy;
    public String OutInTime;
    /*public Update_Request_Model_Insequrity(int inwardId,String serialNo, String invoiceNo, String vehicleNo, String date, String partyName, String material, String OA_PO_number, String driver_MobileNo, char nextprocess, char i_O, String factoryIn, String factoryOut, String vehicleType, String inTime, String outTime, int qtyUOM, int netWeightUOM, int netWeight, int qty, String extramaterials, String remark, String selectregister, String irCopy, String deliveryBill, String taxInvoice, String ewayBill, String updatedBy) {
        InwardId = inwardId;
        SerialNo = serialNo;
        InvoiceNo = invoiceNo;
        VehicleNo = vehicleNo;
        Date = date;
        PartyName = partyName;
        Material = material;
        this.OA_PO_number = OA_PO_number;
        Driver_MobileNo = driver_MobileNo;
        Nextprocess = nextprocess;
        I_O = i_O;
        FactoryIn = factoryIn;
        FactoryOut = factoryOut;
        VehicleType = vehicleType;
        InTime = inTime;
        OutTime = outTime;
        QtyUOM = qtyUOM;
        NetWeightUOM = netWeightUOM;
        NetWeight = netWeight;
        Qty = qty;
        Extramaterials = extramaterials;
        Remark = remark;
        Selectregister = selectregister;
        IrCopy = irCopy;
        DeliveryBill = deliveryBill;
        TaxInvoice = taxInvoice;
        EwayBill = ewayBill;
        UpdatedBy = updatedBy;
    }*/

    public Update_Request_Model_Insequrity(int inwardId, String serialNo, String invoiceNo, String vehicleNo, String date, String partyName, String material, String OA_PO_number, String driver_MobileNo, char nextprocess, char i_O, String factoryIn, String factoryOut, String vehicleType, String inTime, String outTime, int qtyUOM, int netWeightUOM, int netWeight, int qty, String extramaterials, String remark, String selectregister, String irCopy, String deliveryBill, String taxInvoice, String ewayBill, String updatedBy, String outInTime) {
        InwardId = inwardId;
        SerialNo = serialNo;
        InvoiceNo = invoiceNo;
        VehicleNo = vehicleNo;
        Date = date;
        PartyName = partyName;
        Material = material;
        this.OA_PO_number = OA_PO_number;
        Driver_MobileNo = driver_MobileNo;
        Nextprocess = nextprocess;
        I_O = i_O;
        FactoryIn = factoryIn;
        FactoryOut = factoryOut;
        VehicleType = vehicleType;
        InTime = inTime;
        OutTime = outTime;
        QtyUOM = qtyUOM;
        NetWeightUOM = netWeightUOM;
        NetWeight = netWeight;
        Qty = qty;
        Extramaterials = extramaterials;
        Remark = remark;
        Selectregister = selectregister;
        IrCopy = irCopy;
        DeliveryBill = deliveryBill;
        TaxInvoice = taxInvoice;
        EwayBill = ewayBill;
        UpdatedBy = updatedBy;
        OutInTime = outInTime;
    }
}
