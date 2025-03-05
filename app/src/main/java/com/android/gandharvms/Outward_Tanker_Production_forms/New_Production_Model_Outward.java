package com.android.gandharvms.Outward_Tanker_Production_forms;

import org.apache.commons.math3.geometry.partitioning.BSPTree;

import java.util.List;

public class New_Production_Model_Outward {
    public int OutwardId;
    public String ProInTime;
    public String ProOutTime;
    public String TankOrBlenderNo;
    public String Psign;
    public String OperatorSign;
    public String ProductionProcess;
    public String ProRemark;
    public String CreatedBy;
    public String VehicleType;
    public String SerialNumber;
    public String VehicleNumber;
    public char Nextprocess;
    public char I_O;
    public String UpdatedBy;
    public String PurposeProcess;

    // Separate JSON arrays for each compartment
    private String compartment1;
    private String compartment2;
    private String compartment3;
    private String compartment4;
    private String compartment5;
    private String compartment6;
    public int CompartmentCount;
    public boolean IsCheck;

    public New_Production_Model_Outward(int outwardId, String proInTime, String proOutTime, String tankOrBlenderNo, String psign, String operatorSign, String productionProcess, String proRemark, String createdBy, String vehicleType, String serialNumber, String vehicleNumber, char nextprocess, char i_O, String updatedBy, String purposeProcess,
                                        String compartment1,String compartment2,String compartment3,String compartment4,
                                        String compartment5,String compartment6,int compartmentCount,boolean isCheck) {
        OutwardId = outwardId;
        ProInTime = proInTime;
        ProOutTime = proOutTime;
        TankOrBlenderNo = tankOrBlenderNo;
        Psign = psign;
        OperatorSign = operatorSign;
        ProductionProcess = productionProcess;
        ProRemark = proRemark;
        CreatedBy = createdBy;
        VehicleType = vehicleType;
        SerialNumber = serialNumber;
        VehicleNumber = vehicleNumber;
        Nextprocess = nextprocess;
        I_O = i_O;
        UpdatedBy = updatedBy;
        this.PurposeProcess = purposeProcess;

        this.compartment1 = compartment1;
        this.compartment2 = compartment2;
        this.compartment3 = compartment3;
        this.compartment4 = compartment4;
        this.compartment5 = compartment5;
        this.compartment6 = compartment6;
        this.CompartmentCount = compartmentCount;
        this.IsCheck = isCheck;
    }
}
