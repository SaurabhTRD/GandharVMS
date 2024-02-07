package com.android.gandharvms.Inward_Tanker_Weighment;

import com.google.type.DateTime;

import org.apache.poi.hpsf.Decimal;

import java.util.ArrayList;

public class InTanWeighRequestModel {

    String InTime,OutTime,Date,FactoryOut;
    int Driver_MobileNO,ContainerNo;
    String GrossWeight,NetWeight,TareWeight;
    String ShortageDip,ShortageWeight,Remark,SignBy,InVehicleImage,InDriverImage,VehicleNo,SerialNo,PartyName,Material,OA_PO_number;

    Character Nextprocess,I_O,VehicleType;

    public InTanWeighRequestModel(String inTime, String outTime, String date, String factoryOut, int driver_MobileNO, String grossWeight, String netWeight, String tareWeight, String shortageDip, String shortageWeight, String remark, String signBy, int containerNo, String inVehicleImage, String inDriverImage, String vehicleNo, String serialNo, String partyName, String material, String OA_PO_number, Character nextprocess, Character i_O, Character vehicleType) {
        InTime = inTime;
        OutTime = outTime;
        Date = date;
        FactoryOut = factoryOut;
        Driver_MobileNO = driver_MobileNO;
        GrossWeight = grossWeight;
        NetWeight = netWeight;
        TareWeight = tareWeight;
        ShortageDip = shortageDip;
        ShortageWeight = shortageWeight;
        Remark = remark;
        SignBy = signBy;
        ContainerNo = containerNo;
        InVehicleImage = inVehicleImage;
        InDriverImage = inDriverImage;
        VehicleNo = vehicleNo;
        SerialNo = serialNo;
        PartyName = partyName;
        Material = material;
        this.OA_PO_number = OA_PO_number;
        Nextprocess = nextprocess;
        I_O = i_O;
        VehicleType = vehicleType;
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

    public int getContainerNo() {
        return ContainerNo;
    }

    public void setContainerNo(int containerNo) {
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

    public  InTanWeighRequestModel(){

    }

}
