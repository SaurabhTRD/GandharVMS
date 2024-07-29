package com.android.gandharvms.Inward_Tanker_Laboratory;

import org.apache.poi.hpsf.Decimal;

import java.math.BigDecimal;

public class InTanLabRequestModel {
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
    String CreatedBy ;
    
    String UpdatedBy ;


    String VehicleNo ;
    String Material ;
    String FactoryOut ;
    String SerialNo ;
    char Nextprocess ;
    char I_O ;
    String VehicleType ;
    String PartyName ;

    public InTanLabRequestModel(int inwardId, String inTime, String outTime, String date, String sampleReceiving,
                                String apperance, String odor, String color, int qty, BigDecimal density, String rcsTest,
                                int _40KV, int _100KV, int anlinePoint, int flashPoint, String additionalTest,
                                String sampleTest, String remark, String signOf, String dateAndTime, String remarkDescription,
                                int viscosityIndex, String createdBy, String updatedBy, String vehicleNo,
                                String material, String serialNo, char nextprocess, char i_O, String vehicleType,
                                String partyName) {

        PartyName = partyName;
        Date = date;
        VehicleNo = vehicleNo;
        Material = material;
        SerialNo = serialNo;
        InwardId = inwardId;
        InTime = inTime;
        OutTime = outTime;
        SampleReceiving = sampleReceiving;
        Apperance = apperance;
        Odor = odor;
        Color = color;
        LQty = qty;
        Density = density;
        RcsTest = rcsTest;
        this._40KV = _40KV;
        this._100KV = _100KV;
        AnLinePoint = anlinePoint;
        FlashPoint = flashPoint;
        AdditionalTest = additionalTest;
        SampleTest = sampleTest;
        Remark = remark;
        SignOf = signOf;
        DateAndTime = dateAndTime;
        RemarkDescription = remarkDescription;
        ViscosityIndex = viscosityIndex;
        CreatedBy = createdBy;
        UpdatedBy = updatedBy;
        Nextprocess = nextprocess;
        I_O = i_O;
        VehicleType = vehicleType;
    }
}
