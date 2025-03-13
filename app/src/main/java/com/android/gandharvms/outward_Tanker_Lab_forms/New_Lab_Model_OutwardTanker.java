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

    public New_Lab_Model_OutwardTanker(int outwardId, String labInTime, String labOutTime, String viscosity_Index, BigDecimal density_29_5C, String batch_No, String signatureofOfficer, String labRemark, String createdBy, String barrelLaboratoryProcess, String serialNumber, String vehicleNumber, char nextProcess, char i_O, String vehicleType, String updatedBy, String compartment1) {
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
    }
}
