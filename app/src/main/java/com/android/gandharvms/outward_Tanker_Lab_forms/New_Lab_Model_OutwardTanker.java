package com.android.gandharvms.outward_Tanker_Lab_forms;

import org.apache.poi.hpsf.Decimal;

import java.math.BigDecimal;

public class New_Lab_Model_OutwardTanker {
    public int OutwardId;
    public String LabInTime;
    public String LabOutTime;
    public String Viscosity_Index;
    public BigDecimal Density_29_5C;
    public String Batch_No;
    public String SignatureofOfficer;
    public String LabRemark;
    public String CreatedBy;
    public String BarrelLaboratoryProcess;
    public String SerialNumber;
    public String  VehicleNumber;
    public char NextProcess;
    public char I_O;
    public String VehicleType;
    public String UpdatedBy;
    public String compartment1;
    public String compartment2;
    public String compartment3;
    public String compartment4;
    public String compartment5;
    public String compartment6;

    private int compartmentNumber;

    public New_Lab_Model_OutwardTanker(int outwardId, String labInTime, String labOutTime, String viscosity_Index, BigDecimal density_29_5C, String batch_No, String signatureofOfficer, String labRemark, String createdBy, String barrelLaboratoryProcess, String serialNumber, String vehicleNumber, char nextProcess, char i_O, String vehicleType,
                                       String updatedBy, String compartment1,String compartment2,String compartment3,String compartment4,String compartment5,String compartment6) {
        OutwardId = outwardId;
        LabInTime = labInTime;
        LabOutTime = labOutTime;
        Viscosity_Index = viscosity_Index;
        Density_29_5C = density_29_5C;
        Batch_No = batch_No;
        SignatureofOfficer = signatureofOfficer;
        LabRemark = labRemark;
        CreatedBy = createdBy;
        BarrelLaboratoryProcess = barrelLaboratoryProcess;
        SerialNumber = serialNumber;
        VehicleNumber = vehicleNumber;
        NextProcess = nextProcess;
        I_O = i_O;
        VehicleType = vehicleType;
        UpdatedBy = updatedBy;
        this.compartment1 = compartment1;
        this.compartment2 = compartment2;
        this.compartment3 = compartment3;
        this.compartment4 = compartment4;
        this.compartment5 = compartment5;
        this.compartment6 = compartment6;
    }
}
