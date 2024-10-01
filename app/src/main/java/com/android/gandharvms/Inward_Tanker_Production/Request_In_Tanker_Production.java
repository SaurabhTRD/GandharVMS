package com.android.gandharvms.Inward_Tanker_Production;

public class Request_In_Tanker_Production {

    private  int InwardId;
    private String InTime;
    private String OutTime;
    private String UnloadAboveMaterialInTK;
    private  String ProductName;
    private String AboveMaterialIsUnloadInTK;
    private String OperatorName;
    public String ProRemark;
    private String CreatedBy;
    private String VehicleNo;
    private String SerialNo;
    private char Nextprocess;
    private char I_O;
    private String VehicleType;
    private String Date;
    private String Material;

    public String UpdatedBy;

    public Request_In_Tanker_Production(int inwardId, String inTime, String outTime, String unloadAboveMaterialInTK, String productName, String aboveMaterialIsUnloadInTK, String operatorName,String remark,String createdBy, String vehicleNo, String serialNo, char nextprocess, char i_O, String vehicleType, String date, String material ,String updatedBy) {
        InwardId = inwardId;
        InTime = inTime;
        OutTime = outTime;
        UnloadAboveMaterialInTK = unloadAboveMaterialInTK;
        ProductName = productName;
        AboveMaterialIsUnloadInTK = aboveMaterialIsUnloadInTK;
        OperatorName = operatorName;
        ProRemark=remark;
        CreatedBy = createdBy;
        VehicleNo = vehicleNo;
        SerialNo = serialNo;
        Nextprocess = nextprocess;
        I_O = i_O;
        VehicleType = vehicleType;
        Date = date;
        Material = material;
        UpdatedBy = updatedBy;
    }
}
