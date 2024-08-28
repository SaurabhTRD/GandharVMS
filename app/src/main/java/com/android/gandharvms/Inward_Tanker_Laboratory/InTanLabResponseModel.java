package com.android.gandharvms.Inward_Tanker_Laboratory;

import org.apache.poi.hpsf.Decimal;

import java.math.BigDecimal;

public class InTanLabResponseModel {
    int Id ;
    int InwardId ;
    String InTime ;
    String OutTime ;
    String Date ;
    String SampleReceiving ;
    String Apperance ;
    String Odor ;
    String Color ;
    int LQty ;
    BigDecimal Density ;
    String RcsTest ;
    int _40KV ;
    int _100KV ;
    int AnLinePoint ;
    int FlashPoint ;
    String AdditionalTest ;
    String SampleTest ;
    String Remark ;
    String SignOf ;
    String DateAndTime ;
    String RemarkDescription ;
    int ViscosityIndex ;
    /*String CreatedBy ;
    String UpdatedBy ;*/
    String VehicleNo ;
    String Material ;
    String FactoryOut ;
    String SerialNo ;
    char Nextprocess ;
    char I_O ;
    String VehicleType ;
    String PartyName ;
    public String UnitOfMeasurement;
    private int Qty ;

    String LabRemark;
    public  int SecNetWeightUOM;
    public int SecNetWeight;
    public String UnitOfNetWeight;

    public String getUnitOfMeasurement() {
        return UnitOfMeasurement;
    }

    public void setUnitOfMeasurement(String unitOfMeasurement) {
        UnitOfMeasurement = unitOfMeasurement;
    }

    public int getQty() {
        return Qty;
    }

    public void setQty(int qty) {
        Qty = qty;
    }

    public void setAnLinePoint(int anLinePoint) {
        AnLinePoint = anLinePoint;
    }

    public String getLabRemark() {
        return LabRemark;
    }

    public void setLabRemark(String labRemark) {
        LabRemark = labRemark;
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

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getSampleReceiving() {
        return SampleReceiving;
    }

    public void setSampleReceiving(String sampleReceiving) {
        SampleReceiving = sampleReceiving;
    }

    public String getApperance() {
        return Apperance;
    }

    public void setApperance(String apperance) {
        Apperance = apperance;
    }

    public String getOdor() {
        return Odor;
    }

    public void setOdor(String odor) {
        Odor = odor;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public int getLQty() {
        return LQty;
    }

    public void setLQty(int qty) {
        LQty = qty;
    }

    public BigDecimal getDensity() {
        return Density;
    }

    public void setDensity(BigDecimal density) {
        Density = density;
    }

    public String getRcsTest() {
        return RcsTest;
    }

    public void setRcsTest(String rcsTest) {
        RcsTest = rcsTest;
    }

    public int get_40KV() {
        return _40KV;
    }

    public void set_40KV(int _40KV) {
        this._40KV = _40KV;
    }

    public int get_100KV() {
        return _100KV;
    }

    public void set_100KV(int _100KV) {
        this._100KV = _100KV;
    }

    public int getAnLinePoint() {
        return AnLinePoint;
    }

    public void setAnlinePoint(int anlinePoint) {
        AnLinePoint = anlinePoint;
    }

    public int getFlashPoint() {
        return FlashPoint;
    }

    public void setFlashPoint(int flashPoint) {
        FlashPoint = flashPoint;
    }

    public String getAdditionalTest() {
        return AdditionalTest;
    }

    public void setAdditionalTest(String additionalTest) {
        AdditionalTest = additionalTest;
    }

    public String getSampleTest() {
        return SampleTest;
    }

    public void setSampleTest(String sampleTest) {
        SampleTest = sampleTest;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getSignOf() {
        return SignOf;
    }

    public void setSignOf(String signOf) {
        SignOf = signOf;
    }

    public String getDateAndTime() {
        return DateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        DateAndTime = dateAndTime;
    }

    public String getRemarkDescription() {
        return RemarkDescription;
    }

    public void setRemarkDescription(String remarkDescription) {
        RemarkDescription = remarkDescription;
    }

    public int getViscosityIndex() {
        return ViscosityIndex;
    }

    public void setViscosityIndex(int viscosityIndex) {
        ViscosityIndex = viscosityIndex;
    }

    /*public String getCreatedBy() {
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
    }*/

    public String getVehicleNo() {
        return VehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        VehicleNo = vehicleNo;
    }

    public String getMaterial() {
        return Material;
    }

    public void setMaterial(String material) {
        Material = material;
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

    public String getPartyName() {
        return PartyName;
    }

    public void setPartyName(String partyName) {
        PartyName = partyName;
    }

    public int getSecNetWeightUOM() {
        return SecNetWeightUOM;
    }

    public void setSecNetWeightUOM(int secNetWeightUOM) {
        SecNetWeightUOM = secNetWeightUOM;
    }

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
}
