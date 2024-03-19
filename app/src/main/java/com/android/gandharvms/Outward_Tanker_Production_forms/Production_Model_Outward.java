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

    public Production_Model_Outward(int outwardId, int customerReferenceNumber, String packingStatus,
                                    String rinsingStatus, String decisionRuleforCustomer, String blendingMaterialStatus,
                                    String signatureofOfficer, String updatedBy, char nextProcess, char i_O,
                                    char barrelProductionProcess,String proRemark,String proouttime, String vehicleType) {
        OutwardId = outwardId;
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
        ProOutTime=proouttime;
        VehicleType = vehicleType;
    }

}
