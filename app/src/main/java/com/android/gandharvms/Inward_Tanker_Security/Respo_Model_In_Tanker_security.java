package com.android.gandharvms.Inward_Tanker_Security;

import com.google.type.DateTime;

public class Respo_Model_In_Tanker_security {

    private int Id;
    private int InwardId;
    private DateTime InTime;
    private DateTime OutTime;
    private int QtyUOM;
    private int NetWeightUOM;
    private  int NetWeight;
    private int Qty;
    private String  Extramaterials;
    private String Remark;
    private Byte IsReporting;
    private String ReportingRemark;
    private String Selectregister;
    private String IrCopy;
    private String DeliveryBill;
    private String TaxInvoice;
    private String EwayBill;

    private DateTime CreatedBy;
    private DateTime CreatedDate;

    private String SerialNo;
    private String VehicleNo;
    private int Driver_MobileNo;
    private String PartyName;

    private String Material;
    private String OA_PO_number;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getInwardId() {
        return InwardId;
    }

    public void setInwardId(int inwardId) {
        InwardId = inwardId;
    }

    public DateTime getInTime() {
        return InTime;
    }

    public void setInTime(DateTime inTime) {
        InTime = inTime;
    }

    public DateTime getOutTime() {
        return OutTime;
    }

    public void setOutTime(DateTime outTime) {
        OutTime = outTime;
    }

    public int getQtyUOM() {
        return QtyUOM;
    }

    public void setQtyUOM(int qtyUOM) {
        QtyUOM = qtyUOM;
    }

    public int getNetWeightUOM() {
        return NetWeightUOM;
    }

    public void setNetWeightUOM(int netWeightUOM) {
        NetWeightUOM = netWeightUOM;
    }

    public int getNetWeight() {
        return NetWeight;
    }

    public void setNetWeight(int netWeight) {
        NetWeight = netWeight;
    }

    public int getQty() {
        return Qty;
    }

    public void setQty(int qty) {
        Qty = qty;
    }

    public String getExtramaterials() {
        return Extramaterials;
    }

    public void setExtramaterials(String extramaterials) {
        Extramaterials = extramaterials;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public Byte getIsReporting() {
        return IsReporting;
    }

    public void setIsReporting(Byte isReporting) {
        IsReporting = isReporting;
    }

    public String getReportingRemark() {
        return ReportingRemark;
    }

    public void setReportingRemark(String reportingRemark) {
        ReportingRemark = reportingRemark;
    }

    public String getSelectregister() {
        return Selectregister;
    }

    public void setSelectregister(String selectregister) {
        Selectregister = selectregister;
    }

    public String getIrCopy() {
        return IrCopy;
    }

    public void setIrCopy(String irCopy) {
        IrCopy = irCopy;
    }

    public String getDeliveryBill() {
        return DeliveryBill;
    }

    public void setDeliveryBill(String deliveryBill) {
        DeliveryBill = deliveryBill;
    }

    public String getTaxInvoice() {
        return TaxInvoice;
    }

    public void setTaxInvoice(String taxInvoice) {
        TaxInvoice = taxInvoice;
    }

    public String getEwayBill() {
        return EwayBill;
    }

    public void setEwayBill(String ewayBill) {
        EwayBill = ewayBill;
    }

    public DateTime getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(DateTime createdBy) {
        CreatedBy = createdBy;
    }

    public DateTime getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(DateTime createdDate) {
        CreatedDate = createdDate;
    }

    public String getSerialNo() {
        return SerialNo;
    }

    public void setSerialNo(String serialNo) {
        SerialNo = serialNo;
    }

    public String getVehicleNo() {
        return VehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        VehicleNo = vehicleNo;
    }

    public int getDriver_MobileNo() {
        return Driver_MobileNo;
    }

    public void setDriver_MobileNo(int driver_MobileNo) {
        Driver_MobileNo = driver_MobileNo;
    }

    public String getPartyName() {
        return PartyName;
    }

    public void setPartyName(String partyName) {
        PartyName = partyName;
    }

    public String getMaterial() {
        return Material;
    }

    public void setMaterial(String material) {
        Material = material;
    }

    public String getOA_PO_number() {
        return OA_PO_number;
    }

    public void setOA_PO_number(String OA_PO_number) {
        this.OA_PO_number = OA_PO_number;
    }
}
