package com.android.gandharvms.Inward_Truck_store;

public class InTruckStoreResponseModel {
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
    int ReceiveQTY ;
    int ReceiveQTYUOM ;
    String Remark ;

    String StoreExtramaterials;
    char Nextprocess;
    char I_O;
    String VehicleType;
    String InvoiceNo;
    String Date;
    String PartyName;
    String Material;
    String OA_PO_number;
    String Driver_MobileNo;
    int Qty;
    int QtyUOM;
    String UnitOfMeasurement;
    String ReQTYUom;
    String storeRemark;
    String Extramaterials;
    public String AllIRRemarks;

    public String getExtramaterials() {
        return Extramaterials;
    }

    public void setExtramaterials(String extramaterials) {
        Extramaterials = extramaterials;
    }

    public String getReQTYUom() {
        return ReQTYUom;
    }

    public void setReQTYUom(String reQTYUom) {
        ReQTYUom = reQTYUom;
    }

    public String getStoreRemark() {
        return storeRemark;
    }

    public void setStoreRemark(String storeRemark) {
        this.storeRemark = storeRemark;
    }

    public String getUnitOfMeasurement() {
        return UnitOfMeasurement;
    }

    public void setUnitOfMeasurement(String unitOfMeasurement) {
        UnitOfMeasurement = unitOfMeasurement;
    }

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

    public String getInTime() {
        return InTime;
    }

    public void setInTime(String inTime) {
        InTime = inTime;
    }

    public String getOutTime() {
        return OutTime;
    }

    public void setOutTime(String outTime) {
        OutTime = outTime;
    }

    public boolean isActive() {
        return IsActive;
    }

    public void setActive(boolean active) {
        IsActive = active;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public String getUpdatedBy() {
        return UpdatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        UpdatedBy = updatedBy;
    }

    public String getVehicleNo() {
        return VehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        VehicleNo = vehicleNo;
    }

    public String getFactoryOut() {
        return FactoryOut;
    }

    public void setFactoryOut(String factoryOut) {
        FactoryOut = factoryOut;
    }

    public String getSerialNo() {
        return SerialNo;
    }

    public void setSerialNo(String serialNo) {
        SerialNo = serialNo;
    }

    public int getReceiveQTY() {
        return ReceiveQTY;
    }

    public void setReceiveQTY(int receiveQTY) {
        ReceiveQTY = receiveQTY;
    }

    public int getReceiveQTYUOM() {
        return ReceiveQTYUOM;
    }

    public void setReceiveQTYUOM(int receiveQTYUOM) {
        ReceiveQTYUOM = receiveQTYUOM;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getStoreExtramaterials() {
        return StoreExtramaterials;
    }

    public void setStoreExtramaterials(String storeExtramaterials) {
        StoreExtramaterials = storeExtramaterials;
    }

    public int getQtyUOM() {
        return QtyUOM;
    }

    public void setQtyUOM(int qtyUOM) {
        QtyUOM = qtyUOM;
    }

    public char getNextprocess() {
        return Nextprocess;
    }

    public void setNextprocess(char nextprocess) {
        Nextprocess = nextprocess;
    }

    public char getI_O() {
        return I_O;
    }

    public void setI_O(char i_O) {
        I_O = i_O;
    }

    public String getVehicleType() {
        return VehicleType;
    }

    public void setVehicleType(String vehicleType) {
        VehicleType = vehicleType;
    }

    public String getInvoiceNo() {
        return InvoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        InvoiceNo = invoiceNo;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
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

    public String getDriver_MobileNo() {
        return Driver_MobileNo;
    }

    public void setDriver_MobileNo(String driver_MobileNo) {
        Driver_MobileNo = driver_MobileNo;
    }

    public int getQty() {
        return Qty;
    }

    public void setQty(int qty) {
        Qty = qty;
    }

    public String getAllIRRemarks() {
        return AllIRRemarks;
    }

    public void setAllIRRemarks(String allIRRemarks) {
        AllIRRemarks = allIRRemarks;
    }
}
