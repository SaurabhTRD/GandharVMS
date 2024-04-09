package com.android.gandharvms.Inward_Tanker_Production;

public class It_Pro_UpdateByInwardid_req_model {

    private  int InwardId;
    private String InTime;
    private String OutTime;
    private int UnloadAboveMaterialInTK;
    private  String ProductName;
    private int AboveMaterialIsUnloadInTK;
    private String OperatorName;
    private String CreatedBy;
    private String VehicleNo;
    private String SerialNo;
    private char Nextprocess;
    private char I_O;
    private String VehicleType;
    private String Date;
    private String Material;

    public String UpdatedBy;

    public It_Pro_UpdateByInwardid_req_model(int inwardId, int unloadAboveMaterialInTK, String productName, int aboveMaterialIsUnloadInTK, String operatorName, String updatedBy) {
        InwardId = inwardId;
        UnloadAboveMaterialInTK = unloadAboveMaterialInTK;
        ProductName = productName;
        AboveMaterialIsUnloadInTK = aboveMaterialIsUnloadInTK;
        OperatorName = operatorName;
        UpdatedBy = updatedBy;
    }
}
