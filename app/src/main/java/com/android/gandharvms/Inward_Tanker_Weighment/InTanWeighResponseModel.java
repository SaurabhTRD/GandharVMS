package com.android.gandharvms.Inward_Tanker_Weighment;


import com.google.type.DateTime;

import org.apache.poi.hpsf.Decimal;




public class InTanWeighResponseModel {
    int Id;
     int InwardId;
     String InTime ;
     String OutTime ;
     String GrossWeight;
     String NetWeight;
     String TareWeight ;
     String ShortageDip;
     String ShortageWeight ;
     String Remark ;
     String SignBy ;
    String ContainerNo ;
     String InVehicleImage ;
     String InDriverImage ;
     boolean IsActive ;
     String CreatedBy ;
     String SerialNo ;
     String InvoiceNo;
     String VehicleNo;
     String Date;
     String PartyName;
     String Material;
     String OA_PO_number;
     String Driver_MobileNo ;
     char Nextprocess;
     char I_O;
     String VehicleType;
     String UpdatedBy;

    String OutVehicleImage;
    String OutDriverImage;
    public int WeighQty ;
    public int WeightQtyUOM;
    public String WeiQTYUOM ;
    int Qty;
    public String OutInTime;
    public int SecNetWeightUOM;
    public String Extramaterials;
    public int SecNetWeight;
    public String UnitOfNetWeight;

    public int getSecNetWeight() {
        return SecNetWeight;
    }

    public void setSecNetWeight(int secNetWeight) {
        SecNetWeight = secNetWeight;
    }

    public String getUnitOfNetWeight() {
        return UnitOfNetWeight;
    }

    public void setUnitOfNetWeight(String unitOfNetWeight) {
        UnitOfNetWeight = unitOfNetWeight;
    }

    public int getSecNetWeightUOM() {
        return SecNetWeightUOM;
    }

    public void setSecNetWeightUOM(int secNetWeightUOM) {
        SecNetWeightUOM = secNetWeightUOM;
    }

    public int getWeighQty() {
        return WeighQty;
    }

    public void setWeighQty(int weighQty) {
        WeighQty = weighQty;
    }

    public int getWeightQtyUOM() {
        return WeightQtyUOM;
    }

    public void setWeightQtyUOM(int weightQtyUOM) {
        WeightQtyUOM = weightQtyUOM;
    }

    public String getWeiQTYUOM() {
        return WeiQTYUOM;
    }

    public void setWeiQTYUOM(String weiQTYUOM) {
        WeiQTYUOM = weiQTYUOM;
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

    public String getGrossWeight() {
        return GrossWeight;
    }

    public void setGrossWeight(String grossWeight) {
        GrossWeight = grossWeight;
    }

    public String getNetWeight() {
        return NetWeight;
    }

    public void setNetWeight(String netWeight) {
        NetWeight = netWeight;
    }

    public String getTareWeight() {
        return TareWeight;
    }

    public void setTareWeight(String tareWeight) {
        TareWeight = tareWeight;
    }

    public String getShortageDip() {
        return ShortageDip;
    }

    public void setShortageDip(String shortageDip) {
        ShortageDip = shortageDip;
    }

    public String getShortageWeight() {
        return ShortageWeight;
    }

    public void setShortageWeight(String shortageWeight) {
        ShortageWeight = shortageWeight;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getSignBy() {
        return SignBy;
    }

    public void setSignBy(String signBy) {
        SignBy = signBy;
    }

    public String getContainerNo() {
        return ContainerNo;
    }

    public void setContainerNo(String containerNo) {
        ContainerNo = containerNo;
    }

    public String getInVehicleImage() {
        return InVehicleImage;
    }

    public void setInVehicleImage(String inVehicleImage) {
        InVehicleImage = inVehicleImage;
    }

    public String getInDriverImage() {
        return InDriverImage;
    }

    public void setInDriverImage(String inDriverImage) {
        InDriverImage = inDriverImage;
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

    public String getSerialNo() {
        return SerialNo;
    }

    public void setSerialNo(String serialNo) {
        SerialNo = serialNo;
    }

    public String getInvoiceNo() {
        return InvoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        InvoiceNo = invoiceNo;
    }

    public String getVehicleNo() {
        return VehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        VehicleNo = vehicleNo;
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

    public String getUpdatedBy() {
        return UpdatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        UpdatedBy = updatedBy;
    }

    public String getOutVehicleImage() {
        return OutVehicleImage;
    }

    public void setOutVehicleImage(String outVehicleImage) {
        OutVehicleImage = outVehicleImage;
    }

    public String getOutDriverImage() {
        return OutDriverImage;
    }

    public void setOutDriverImage(String outDriverImage) {
        OutDriverImage = outDriverImage;
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
}
