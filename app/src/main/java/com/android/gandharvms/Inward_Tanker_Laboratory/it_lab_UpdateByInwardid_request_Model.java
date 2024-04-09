package com.android.gandharvms.Inward_Tanker_Laboratory;

public class it_lab_UpdateByInwardid_request_Model {
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
    int Density ;
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

    public it_lab_UpdateByInwardid_request_Model(int inwardId, String apperance, String odor, String color, int LQty, int density, String rcsTest, int _40KV, int _100KV, int anLinePoint, int flashPoint, String additionalTest, String sampleTest, String remark, String signOf, String dateAndTime, String remarkDescription, int viscosityIndex, String updatedBy) {
        InwardId = inwardId;
        Apperance = apperance;
        Odor = odor;
        Color = color;
        this.LQty = LQty;
        Density = density;
        RcsTest = rcsTest;
        this._40KV = _40KV;
        this._100KV = _100KV;
        AnLinePoint = anLinePoint;
        FlashPoint = flashPoint;
        AdditionalTest = additionalTest;
        SampleTest = sampleTest;
        Remark = remark;
        SignOf = signOf;
        DateAndTime = dateAndTime;
        RemarkDescription = remarkDescription;
        ViscosityIndex = viscosityIndex;
        UpdatedBy = updatedBy;
    }
}
