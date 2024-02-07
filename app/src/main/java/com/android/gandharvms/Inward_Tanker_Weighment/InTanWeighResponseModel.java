package com.android.gandharvms.Inward_Tanker_Weighment;


import com.google.type.DateTime;

import org.apache.poi.hpsf.Decimal;




public class InTanWeighResponseModel {
    public String InTime;
    public String OutTime;
    public String Date;
    public String FactoryOut;
    public int Driver_MobileNO;
    public String GrossWeight;
    public String NetWeight;
    public String TareWeight;
    public String ShortageDip;
    public String ShortageWeight;
    public String Remark;
    public String SignBy;
    public String ContainerNo;
    public String InVehicleImage;
    public String InDriverImage;
    public String VehicleNo;
    public String SerialNo;
    public String PartyName;
    public String Material;
    public String OA_PO_number;

    Character Nextprocess,I_O,VehicleType;

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

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getFactoryOut() {
        return FactoryOut;
    }

    public void setFactoryOut(String factoryOut) {
        FactoryOut = factoryOut;
    }

    public int getDriver_MobileNO() {
        return Driver_MobileNO;
    }

    public void setDriver_MobileNO(int driver_MobileNO) {
        Driver_MobileNO = driver_MobileNO;
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

    public String getVehicleNo() {
        return VehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        VehicleNo = vehicleNo;
    }

    public String getSerialNo() {
        return SerialNo;
    }

    public void setSerialNo(String serialNo) {
        SerialNo = serialNo;
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

    public Character getNextprocess() {
        return Nextprocess;
    }

    public void setNextprocess(Character nextprocess) {
        Nextprocess = nextprocess;
    }

    public Character getI_O() {
        return I_O;
    }

    public void setI_O(Character i_O) {
        I_O = i_O;
    }

    public Character getVehicleType() {
        return VehicleType;
    }

    public void setVehicleType(Character vehicleType) {
        VehicleType = vehicleType;
    }
}
