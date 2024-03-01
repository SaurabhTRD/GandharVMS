package com.android.gandharvms.Outward_Tanker_Production_forms;

public class Production_Model_Outward {
    int OutwardId;
    String ProInTime;
    String ProOutTime;
    String ProRemark;
    int CustomerReferenceNumber;
    String PackingStatus;
    String RinsingStatus;
    String DecisionRuleforCustomer;
    String BlendingMaterialStatus;
    String SignatureofOfficer;
    String UpdatedBy;
    char NextProcess;
    char I_O;
    char BarrelProductionProcess;
    String Batch_No;
    String VehicleType;

    public Production_Model_Outward(int outwardId, String proInTime, String proOutTime, String proRemark, int customerReferenceNumber, String packingStatus, String rinsingStatus, String decisionRuleforCustomer, String blendingMaterialStatus, String signatureofOfficer, String updatedBy, char nextProcess, char i_O, char barrelProductionProcess, String batch_No, String vehicleType) {
        OutwardId = outwardId;
        ProInTime = proInTime;
        ProOutTime = proOutTime;
        ProRemark = proRemark;
        CustomerReferenceNumber = customerReferenceNumber;
        PackingStatus = packingStatus;
        RinsingStatus = rinsingStatus;
        DecisionRuleforCustomer = decisionRuleforCustomer;
        BlendingMaterialStatus = blendingMaterialStatus;
        SignatureofOfficer = signatureofOfficer;
        UpdatedBy = updatedBy;
        NextProcess = nextProcess;
        I_O = i_O;
        BarrelProductionProcess = barrelProductionProcess;
        Batch_No = batch_No;
        VehicleType = vehicleType;
    }

}
