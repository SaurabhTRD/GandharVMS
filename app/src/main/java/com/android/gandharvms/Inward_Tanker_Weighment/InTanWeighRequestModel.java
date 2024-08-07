package com.android.gandharvms.Inward_Tanker_Weighment;

import com.google.type.DateTime;

import org.apache.poi.hpsf.Decimal;

import java.util.ArrayList;

public class InTanWeighRequestModel {

    int Id;
    int InwardId;
    String InTime ;
    String OutTime ;
    String GrossWeight;
    String NetWeight;
    String TareWeight ;
//    String ShortageDip;
//    String ShortageWeight ;
    String Remark ;
    String SignBy ;
    String ContainerNo ;
    String InVehicleImage ;
    String InDriverImage ;
    boolean IsActive ;
    String CreatedBy ;
    String SerialNo ;
    String VehicleNo;
    String Date;
    String PartyName;
    String Material;
    String OA_PO_number;
    String Driver_MobileNo ;
    char Nextprocess;
    char I_O;
    String VehicleType;
    String UpdatedBy;
    String OutVehicleImage;
    String OutDriverImage;
    public String OutInTime;


    public InTanWeighRequestModel(int inwardId, String inTime, String outTime, String grossWeight, String netWeight,
                                  String tareWeight, String remark,
                                  String signBy, String containerNo, String inVehicleImage, String inDriverImage,
                                  String serialNo, String vehicleNo, String date, String partyName,
                                  String material, String OA_PO_number, String driver_MobileNo, char nextprocess,
                                  char i_O, String vehicleType, String createdBy,
                                  String updatedBy,String outVehicleImage, String outDriverImage,String outInTime) {
        InwardId = inwardId;
        InTime = inTime;
        OutTime = outTime;
        GrossWeight = grossWeight;
        Remark = remark;
        SignBy = signBy;
        ContainerNo = containerNo;
        InVehicleImage = inVehicleImage;
        InDriverImage = inDriverImage;
        SerialNo = serialNo;
        VehicleNo = vehicleNo;
        Date = date;
        PartyName = partyName;
        Material = material;
        this.OA_PO_number = OA_PO_number;
        Driver_MobileNo = driver_MobileNo;
        Nextprocess = nextprocess;
        I_O = i_O;
        VehicleType = vehicleType;
        CreatedBy=createdBy;
        NetWeight=netWeight;
        TareWeight=tareWeight;
        UpdatedBy=updatedBy;
        OutVehicleImage = outVehicleImage;
        OutDriverImage = outDriverImage;
        OutInTime=outInTime;
    }
}
