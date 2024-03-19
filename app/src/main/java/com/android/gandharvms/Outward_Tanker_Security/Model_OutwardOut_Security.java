package com.android.gandharvms.Outward_Tanker_Security;

public class Model_OutwardOut_Security {

    public int OutwardId;
    public String Intime;
    public String OutTime;
    public int Kl;
    public String Place;
    public String VehiclePermit;
    public String Puc;
    public String Insurance;
    public String VehicleFitnessCertificate;
    public String DriverLicenses;
    public String RcBook;
    public String InvoiceNumber;
    public String NameofParty;
    public String DescriptionofGoods;
    public String Signature;
    public String Remark;
    public String TransportLRcopy;
    public String Tremcard;
    public String Ewaybill;
    public String Test_Report;
    public String Invoice;
    public String OutInTime;
    //    public boolean IsActive;
    public String CreatedBy;
    public String CreatedDate;
    //    public String UpdatedBy;
//    public String UpdatedDate;
    public boolean IsReporting;
    public String ReportingRemark;
    public char CurrentProcess;
    public String SerialNumber;
    public String VehicleNumber;
    public String TransportName;
    public String MobileNumber;
    public String CapacityVehicle;
    public String ConditionOfVehicle;
    public String Date;
    public String MaterialName;
    public String CustomerName;
    public String OAnumber;
    public int TankerNumber;
    public String ProductName;
    public int HowMuchQuantityFilled;
    public String Location;
    public char NextProcess;
    public char I_O;
    public String VehicleType;
    public String Factory_In;
    public String Factory_Out;

    public String UpdatedBy;
    public String OutSRemark;
    public int OutQty;
    public String OutNetWeight;
    public int OutQtyUOMId;
    public int OutNetWeightUOMId;


    public Model_OutwardOut_Security(int outwardId, String descriptionofGoods, String signature, String transportLRcopy,
           String tremcard, String ewaybill, String test_Report, String invoice, String outInTime,String updatedBy ,
           char nextProcess, char i_O, String vehicleType,String outSRemark) {
        OutwardId = outwardId;
        DescriptionofGoods = descriptionofGoods;
        Signature = signature;
        TransportLRcopy = transportLRcopy;
        Tremcard = tremcard;
        Ewaybill = ewaybill;
        Test_Report = test_Report;
        Invoice = invoice;
        OutInTime = outInTime;
        UpdatedBy = updatedBy;
        NextProcess = nextProcess;
        I_O = i_O;
        VehicleType = vehicleType;
        OutSRemark = outSRemark;
    }
}
