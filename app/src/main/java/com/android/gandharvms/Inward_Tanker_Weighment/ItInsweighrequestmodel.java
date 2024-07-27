package com.android.gandharvms.Inward_Tanker_Weighment;

public class ItInsweighrequestmodel {
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
    public int WeighQty ;
    public int WeightQtyUOM;

    public ItInsweighrequestmodel(int inwardId, String inTime, String outTime, String grossWeight, String netWeight,
                                  String tareWeight, String remark, String signBy, String containerNo, String inVehicleImage,
                                  String inDriverImage,String serialnumber,String vehiclenumber, String createdBy,
                                  String partyName, String OA_PO_number,
                                  String driver_MobileNo, char nextprocess, char i_O, String vehicleType,
                                  String updatedBy, String outVehicleImage, String outDriverImage, String outInTime,
                                  int weighQty, int weightQtyUOM) {
        InwardId = inwardId;
        InTime = inTime;
        OutTime = outTime;
        GrossWeight = grossWeight;
        NetWeight = netWeight;
        TareWeight = tareWeight;
        Remark = remark;
        SignBy = signBy;
        ContainerNo = containerNo;
        InVehicleImage = inVehicleImage;
        InDriverImage = inDriverImage;
        SerialNo=serialnumber;
        VehicleNo=vehiclenumber;
        CreatedBy = createdBy;
        PartyName = partyName;
        this.OA_PO_number = OA_PO_number;
        Driver_MobileNo = driver_MobileNo;
        Nextprocess = nextprocess;
        I_O = i_O;
        VehicleType = vehicleType;
        UpdatedBy = updatedBy;
        OutVehicleImage = outVehicleImage;
        OutDriverImage = outDriverImage;
        OutInTime = outInTime;
        WeighQty = weighQty;
        WeightQtyUOM = weightQtyUOM;
    }
}
