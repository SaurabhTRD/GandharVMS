package com.android.gandharvms.Inward_Tanker_Weighment;

import com.google.type.DateTime;

import org.apache.poi.hpsf.Decimal;

public class InTanWeighResponseModel {
    DateTime InTime,OutTime,Date,FactoryOut;
    int Driver_MobileNO;
    Decimal GrossWeight,NetWeight,TareWeight;
    String ShortageDip,ShortageWeight,Remark,SignBy,ContainerNo,InVehicleImage,InDriverImage,VehicleNo,SerialNo,PartyName,Material,OA_PO_number;

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

    public DateTime getDate() {
        return Date;
    }

    public void setDate(DateTime date) {
        Date = date;
    }

    public DateTime getFactoryOut() {
        return FactoryOut;
    }

    public void setFactoryOut(DateTime factoryOut) {
        FactoryOut = factoryOut;
    }

    public int getDriver_MobileNO() {
        return Driver_MobileNO;
    }

    public void setDriver_MobileNO(int driver_MobileNO) {
        Driver_MobileNO = driver_MobileNO;
    }

    public Decimal getGrossWeight() {
        return GrossWeight;
    }

    public void setGrossWeight(Decimal grossWeight) {
        GrossWeight = grossWeight;
    }

    public Decimal getNetWeight() {
        return NetWeight;
    }

    public void setNetWeight(Decimal netWeight) {
        NetWeight = netWeight;
    }

    public Decimal getTareWeight() {
        return TareWeight;
    }

    public void setTareWeight(Decimal tareWeight) {
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
}
