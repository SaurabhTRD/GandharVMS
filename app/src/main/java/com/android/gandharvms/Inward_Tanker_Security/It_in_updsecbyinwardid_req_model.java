package com.android.gandharvms.Inward_Tanker_Security;

public class It_in_updsecbyinwardid_req_model {
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
    public boolean IsReporting;
    public String ReportingRemark;
    public String Selectregister;
    public String IrCopy;
    public String DeliveryBill;
    public String TaxInvoice;
    public String EwayBill;
    public String CreatedBy;
    public String OutInTime;
    public String UpdatedBy;

    public It_in_updsecbyinwardid_req_model(int inwardId, String serialNo, String invoiceNo, String vehicleNo,
                                            String date, String partyName, String material, String OA_PO_number,
                                            String driver_MobileNo, int qtyUOM, int netWeightUOM, int netWeight,
                                            int qty,String extramaterials, String remark,String updatedby) {
        InwardId = inwardId;
        SerialNo = serialNo;
        InvoiceNo = invoiceNo;
        VehicleNo = vehicleNo;
        Date = date;
        PartyName = partyName;
        Material = material;
        this.OA_PO_number = OA_PO_number;
        Driver_MobileNo = driver_MobileNo;
        QtyUOM = qtyUOM;
        NetWeightUOM = netWeightUOM;
        NetWeight = netWeight;
        Qty = qty;
        Extramaterials=extramaterials;
        Remark = remark;
        UpdatedBy=updatedby;
    }
}
