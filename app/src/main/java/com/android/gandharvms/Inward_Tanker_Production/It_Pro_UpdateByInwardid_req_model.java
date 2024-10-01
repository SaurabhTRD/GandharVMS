package com.android.gandharvms.Inward_Tanker_Production;

public class It_Pro_UpdateByInwardid_req_model {

    public String UpdatedBy;
    private final int InwardId;
    private String InTime;
    private String OutTime;
    private final String UnloadAboveMaterialInTK;
    private final String ProductName;
    private final String AboveMaterialIsUnloadInTK;
    private final String OperatorName;
    private String CreatedBy;
    private String VehicleNo;
    private String SerialNo;
    private char Nextprocess;
    private char I_O;
    private String VehicleType;
    private String Date;
    private String Material;

    public It_Pro_UpdateByInwardid_req_model(int inwardId, String unloadAboveMaterialInTK, String productName, String aboveMaterialIsUnloadInTK, String operatorName, String updatedBy) {
        InwardId = inwardId;
        UnloadAboveMaterialInTK = unloadAboveMaterialInTK;
        ProductName = productName;
        AboveMaterialIsUnloadInTK = aboveMaterialIsUnloadInTK;
        OperatorName = operatorName;
        UpdatedBy = updatedBy;
    }
}
