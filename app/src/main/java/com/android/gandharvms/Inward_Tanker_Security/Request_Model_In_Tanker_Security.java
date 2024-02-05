package com.android.gandharvms.Inward_Tanker_Security;


import java.sql.Date;

public class Request_Model_In_Tanker_Security {
    public String SerialNo;
    public String InvoiceNo;
    public String VehicleNo;
    public String Date;
    public String PartyName;
    public  String Material;
    public String OA_PO_number;
    public  int Driver_MobileNo;
    public char Nextprocess;
    public char I_O;
    public String FactoryIn;
    public String FactoryOut;
    public char VehicleType;
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

    public Request_Model_In_Tanker_Security(String serialNo, String invoiceNo, String vehicleNo, String date, String partyName, String material, String OA_PO_number, int driver_MobileNo, char nextprocess, char i_O, String factoryIn, String factoryOut, char vehicleType, String inTime, String outTime, int qtyUOM, int netWeightUOM, int netWeight, int qty, String extramaterials, String remark, boolean isReporting, String reportingRemark, String selectregister, String irCopy, String deliveryBill, String taxInvoice, String ewayBill, String createdBy) {
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
        IsReporting = isReporting;
        ReportingRemark = reportingRemark;
        Selectregister = selectregister;
        IrCopy = irCopy;
        DeliveryBill = deliveryBill;
        TaxInvoice = taxInvoice;
        EwayBill = ewayBill;
        CreatedBy = createdBy;
    }
//    public String getSerialNo() {
//        return SerialNo;
//    }

//    public void setSerialNo(String serialNo) {
//        SerialNo = serialNo;
//    }
//
//    public String getInvoiceNo() {
//        return InvoiceNo;
//    }
//
//    public void setInvoiceNo(String invoiceNo) {
//        InvoiceNo = invoiceNo;
//    }
//
//    public String getVehicleNo() {
//        return VehicleNo;
//    }
//
//    public void setVehicleNo(String vehicleNo) {
//        VehicleNo = vehicleNo;
//    }
//
//    public String getDate() {
//        return Date;
//    }
//
//    public void setDate(String date) {
//        Date = date;
//    }
//
//    public String getPartyName() {
//        return PartyName;
//    }
//
//    public void setPartyName(String partyName) {
//        PartyName = partyName;
//    }
//
//    public String getMaterial() {
//        return Material;
//    }
//
//    public void setMaterial(String material) {
//        Material = material;
//    }
//
//    public String getOA_PO_number() {
//        return OA_PO_number;
//    }
//
//    public void setOA_PO_number(String OA_PO_number) {
//        this.OA_PO_number = OA_PO_number;
//    }
//
//    public int getDriver_MobileNo() {
//        return Driver_MobileNo;
//    }
//
//    public void setDriver_MobileNo(String driver_MobileNo) {
//        Driver_MobileNo = convertToInt(driver_MobileNo);
//    }
//
//    private int convertToInt(String value) {
//        try {
//            return Integer.parseInt(value);
//        } catch (NumberFormatException e) {
//            e.printStackTrace();
//            return 0; // or handle the error in an appropriate way
//        }
//    }
//
//    public String getNextprocess() {
//        return Nextprocess;
//    }
//
//    public void setNextprocess(String nextprocess) {
//        Nextprocess = nextprocess;
//    }
//
//    public char getI_O() {
//        return I_O;
//    }
//
//    public void setI_O(char i_O) {
//        I_O = i_O;
//    }
//
//    public DateTime getFactoryIn() {
//        return FactoryIn;
//    }
//
//    public void setFactoryIn(DateTime factoryIn) {
//        FactoryIn = factoryIn;
//    }
//
//    public DateTime getFactoryOut() {
//        return FactoryOut;
//    }
//
//    public void setFactoryOut(DateTime factoryOut) {
//        FactoryOut = factoryOut;
//    }
//
//    public char getVehicleType() {
//        return VehicleType;
//    }
//
//    public void setVehicleType(char vehicleType) {
//        VehicleType = vehicleType;
//    }
//
//    public String getInTime() {
//        return InTime;
//    }
//
//    public void setInTime(String inTime) {
//        InTime = inTime;
//    }
//
//    public DateTime getOutTime() {
//        return OutTime;
//    }
//
//    public void setOutTime(DateTime outTime) {
//        OutTime = outTime;
//    }
//
//    public int getQtyUOM() {
//        return QtyUOM;
//    }
//
//    public void setQtyUOM(String qtyUOM) {
//        QtyUOM = convertToIntqtyuom(qtyUOM);
//    }
//    private int convertToIntqtyuom(String value) {
//        try {
//            return Integer.parseInt(value);
//        } catch (NumberFormatException e) {
//            e.printStackTrace();
//            return 0; // or handle the error in an appropriate way
//        }
//    }
//
//    public int getNetWeightUOM() {
//        return NetWeightUOM;
//    }
//
//    public void setNetWeightUOM(String netWeightUOM) {
//        NetWeightUOM = convertToIntnetweightuom(netWeightUOM);
//    }
//    private int convertToIntnetweightuom(String value) {
//        try {
//            return Integer.parseInt(value);
//        } catch (NumberFormatException e) {
//            e.printStackTrace();
//            return 0; // or handle the error in an appropriate way
//        }
//    }
//
//    public int getNetWeight() {
//        return NetWeight;
//    }
//
//    public void setNetWeight(String netWeight) {
//        NetWeight = convertToIntnetweight(netWeight);
//    }
//
//    private int convertToIntnetweight(String value) {
//        try {
//            return Integer.parseInt(value);
//        } catch (NumberFormatException e) {
//            e.printStackTrace();
//            return 0; // or handle the error in an appropriate way
//        }
//    }
//
//    public int getQty() {
//        return Qty;
//    }
//
//    public void setQty(String qty) {
//        Qty = convertsetqty(qty);
//    }
//    private int convertsetqty(String value){
//        try {
//            return  Integer.parseInt(value);
//        }catch (NumberFormatException e){
//            e.printStackTrace();
//            return 0;
//        }
//    }
//    public String getExtramaterials() {
//        return Extramaterials;
//    }
//
//    public void setExtramaterials(String extramaterials) {
//        Extramaterials = extramaterials;
//    }
//
//    public String getRemark() {
//        return Remark;
//    }
//
//    public void setRemark(String remark) {
//        Remark = remark;
//    }
//
//    public boolean isReporting() {
//        return IsReporting;
//    }
//
//    public void setReporting(boolean reporting) {
//        IsReporting = reporting;
//    }
//
//    public String getReportingRemark() {
//        return ReportingRemark;
//    }
//
//    public void setReportingRemark(String reportingRemark) {
//        ReportingRemark = reportingRemark;
//    }
//
//    public String getSelectregister() {
//        return Selectregister;
//    }
//
//    public void setSelectregister(String selectregister) {
//        Selectregister = selectregister;
//    }
//
//    public String getIrCopy() {
//        return IrCopy;
//    }
//
//    public void setIrCopy(String irCopy) {
//        IrCopy = irCopy;
//    }
//
//    public String getDeliveryBill() {
//        return DeliveryBill;
//    }
//
//    public void setDeliveryBill(String deliveryBill) {
//        DeliveryBill = deliveryBill;
//    }
//
//    public String getTaxInvoice() {
//        return TaxInvoice;
//    }
//
//    public void setTaxInvoice(String taxInvoice) {
//        TaxInvoice = taxInvoice;
//    }
//
//    public String getEwayBill() {
//        return EwayBill;
//    }
//
//    public void setEwayBill(String ewayBill) {
//        EwayBill = ewayBill;
//    }
//
//    public boolean isActive() {
//        return IsActive;
//    }
//
//    public void setActive(boolean active) {
//        IsActive = active;
//    }
//
//    public String getCreatedBy() {
//        return CreatedBy;
//    }
//
//    public void setCreatedBy(String createdBy) {
//        CreatedBy = createdBy;
//    }
//
//    public DateTime getCreatedDate() {
//        return CreatedDate;
//    }
//
//    public void setCreatedDate(DateTime createdDate) {
//        CreatedDate = createdDate;
//    }
}
