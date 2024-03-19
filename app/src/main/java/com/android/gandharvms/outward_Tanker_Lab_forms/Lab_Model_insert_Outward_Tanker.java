package com.android.gandharvms.outward_Tanker_Lab_forms;

public class Lab_Model_insert_Outward_Tanker {

    int OutwardId;
    String LabInTime;
    String LabOutTime;
    String LabRemark;
    String SampleCondition;
    String sample_receiving;
    String Sample_Release_Date;
    String Apperance;
    String Odour;
    String Color;
    int Density_29_5C;
    int _40KV;
    int _100KV;
    int Viscosity_Index;
    String TBN_TAN;
    int Anline_Point;
    int Breakdownvoltage_BDV;
    String DDF_90C;
    String WaterContent;
    String InterfacialTension;
    String Flash_Point;
    String PourPoint;
    String Rcs_Test;
    String CorrectionRequired;
    String updatedBy;
    char NextProcess;
    char I_O;
    char BarrelLaboratoryProcess;
    String VehicleType;
    String Restivity;
    String Infra_Red;


    public Lab_Model_insert_Outward_Tanker(int outwardId,String labOutTime, String labRemark, String sampleCondition,
                    String sample_receiving, String sample_Release_Date, String apperance, String odour, String color,
                    int density_29_5C, int _40KV, int _100KV, int viscosity_Index, String TBN_TAN, int anline_Point,
                    int breakdownvoltage_BDV, String DDF_90C, String waterContent, String interfacialTension,
                    String flash_Point, String pourPoint, String rcs_Test, String correctionRequired,String restivity,String infrared, String updatedBy,
                    char nextProcess, char i_O, char barrelLaboratoryProcess, String vehicleType) {
        OutwardId = outwardId;
        LabOutTime = labOutTime;
        LabRemark = labRemark;
        SampleCondition = sampleCondition;
        this.sample_receiving = sample_receiving;
        Sample_Release_Date = sample_Release_Date;
        Apperance = apperance;
        Odour = odour;
        Color = color;
        Density_29_5C = density_29_5C;
        this._40KV = _40KV;
        this._100KV = _100KV;
        Viscosity_Index = viscosity_Index;
        this.TBN_TAN = TBN_TAN;
        Anline_Point = anline_Point;
        Breakdownvoltage_BDV = breakdownvoltage_BDV;
        this.DDF_90C = DDF_90C;
        WaterContent = waterContent;
        InterfacialTension = interfacialTension;
        Flash_Point = flash_Point;
        PourPoint = pourPoint;
        Rcs_Test = rcs_Test;
        CorrectionRequired = correctionRequired;
        Restivity=restivity;
        Infra_Red=infrared;
        this.updatedBy = updatedBy;
        NextProcess = nextProcess;
        I_O = i_O;
        BarrelLaboratoryProcess = barrelLaboratoryProcess;
        VehicleType = vehicleType;
    }
}
