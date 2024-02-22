package com.android.gandharvms.Outward_Tanker_Security;

public class Request_Model_Outward_Tanker_Security {

    public int Id;
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
    public boolean IsActive;
    public String CreatedBy;
    public String CreatedDate;
    public String UpdatedBy;
    public String UpdatedDate;
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


    public Request_Model_Outward_Tanker_Security(int id, int outwardId, String intime, String outTime, int kl, String place, String vehiclePermit, String puc, String insurance, String vehicleFitnessCertificate, String driverLicenses, String rcBook, String invoiceNumber, String nameofParty, String descriptionofGoods, String signature, String remark, String transportLRcopy, String tremcard, String ewaybill, String test_Report, String invoice, String outInTime, boolean isActive, String createdBy, String createdDate, String updatedBy, String updatedDate, boolean isReporting, String reportingRemark, char currentProcess, String serialNumber, String vehicleNumber, String transportName, String mobileNumber, String capacityVehicle, String conditionOfVehicle, String date, String materialName, String customerName, String OAnumber, int tankerNumber, String productName, int howMuchQuantityFilled, String location, char nextProcess, char i_O, String vehicleType) {
        Id = id;
        OutwardId = outwardId;
        Intime = intime;
        OutTime = outTime;
        Kl = kl;
        Place = place;
        VehiclePermit = vehiclePermit;
        Puc = puc;
        Insurance = insurance;
        VehicleFitnessCertificate = vehicleFitnessCertificate;
        DriverLicenses = driverLicenses;
        RcBook = rcBook;
        InvoiceNumber = invoiceNumber;
        NameofParty = nameofParty;
        DescriptionofGoods = descriptionofGoods;
        Signature = signature;
        Remark = remark;
        TransportLRcopy = transportLRcopy;
        Tremcard = tremcard;
        Ewaybill = ewaybill;
        Test_Report = test_Report;
        Invoice = invoice;
        OutInTime = outInTime;
        IsActive = isActive;
        CreatedBy = createdBy;
        CreatedDate = createdDate;
        UpdatedBy = updatedBy;
        UpdatedDate = updatedDate;
        IsReporting = isReporting;
        ReportingRemark = reportingRemark;
        CurrentProcess = currentProcess;
        SerialNumber = serialNumber;
        VehicleNumber = vehicleNumber;
        TransportName = transportName;
        MobileNumber = mobileNumber;
        CapacityVehicle = capacityVehicle;
        ConditionOfVehicle = conditionOfVehicle;
        Date = date;
        MaterialName = materialName;
        CustomerName = customerName;
        this.OAnumber = OAnumber;
        TankerNumber = tankerNumber;
        ProductName = productName;
        HowMuchQuantityFilled = howMuchQuantityFilled;
        Location = location;
        NextProcess = nextProcess;
        I_O = i_O;
        VehicleType = vehicleType;
    }
}