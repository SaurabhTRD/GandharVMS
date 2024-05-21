package com.android.gandharvms.Outward_Tanker_Billing;

public class Respons_Outward_Tanker_Billing {
    public int Id;
    public int OutwardId;
    public String intime;
    public String outTime;
    public String TankerPlanning;
    public String CreatedBy;
    public String CreatedDate;
    public String UpdatedBy;
    public String UpdatedDate;
    public char CurrentProcess;
    public String Remark;
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
    public String HowMuchQTYUOM;

    int Density_29_5C;
    String SealNumber;
    String NetWeight;
    String TareWeight;
    String GrossWeight;
    String BatchNo;
    private int Kl;

    public String spltotqty;
    public String splweight;
    public String iltotqty;
    public  String ilweight;

    public String getSpltotqty() {
        return spltotqty;
    }

    public void setSpltotqty(String spltotqty) {
        this.spltotqty = spltotqty;
    }

    public String getSplweight() {
        return splweight;
    }

    public void setSplweight(String splweight) {
        this.splweight = splweight;
    }

    public String getIltotqty() {
        return iltotqty;
    }

    public void setIltotqty(String iltotqty) {
        this.iltotqty = iltotqty;
    }

    public String getIlweight() {
        return ilweight;
    }

    public void setIlweight(String ilweight) {
        this.ilweight = ilweight;
    }

    public int getDensity_29_5C() {
        return Density_29_5C;
    }

    public void setDensity_29_5C(int density_29_5C) {
        Density_29_5C = density_29_5C;
    }

    public String getSealNumber() {
        return SealNumber;
    }

    public void setSealNumber(String sealNumber) {
        SealNumber = sealNumber;
    }

    public String getNetWeight() {
        return NetWeight;
    }

    public void setNetWeight(String netWeight) {
        NetWeight = netWeight;
    }

    public String getTareWeight() {
        return TareWeight;
    }

    public void setTareWeight(String tareWeight) {
        TareWeight = tareWeight;
    }

    public String getGrossWeight() {
        return GrossWeight;
    }

    public void setGrossWeight(String grossWeight) {
        GrossWeight = grossWeight;
    }

    public String getBatchNo() {
        return BatchNo;
    }

    public void setBatchNo(String batchNo) {
        BatchNo = batchNo;
    }

    public int getKl() {
        return Kl;
    }

    public void setKl(int kl) {
        Kl = kl;
    }

    public Respons_Outward_Tanker_Billing(int outwardId, String intime, String outTime, String tankerPlanning, String createdBy, String updatedBy, char currentProcess, String remark, String serialNumber, String vehicleNumber, String OAnumber, String customerName, String productName, int howMuchQuantityFilled, String location, char nextProcess, char i_O, String vehicleType, String howMuchQTYUOM) {
        OutwardId = outwardId;
        this.intime = intime;
        this.outTime = outTime;
        TankerPlanning = tankerPlanning;
        CreatedBy = createdBy;
        UpdatedBy = updatedBy;
        CurrentProcess = currentProcess;
        Remark = remark;
        SerialNumber = serialNumber;
        VehicleNumber = vehicleNumber;
        this.OAnumber = OAnumber;
        CustomerName = customerName;
        ProductName = productName;
        Location = location;
        HowMuchQuantityFilled = howMuchQuantityFilled;
        NextProcess = nextProcess;
        I_O = i_O;
        VehicleType = vehicleType;
        HowMuchQTYUOM = howMuchQTYUOM;

    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getOutwardId() {
        return OutwardId;
    }

    public void setOutwardId(int outwardId) {
        OutwardId = outwardId;
    }

    public String getIntime() {
        return intime;
    }

    public void setIntime(String intime) {
        this.intime = intime;
    }

    public String getOutTime() {
        return outTime;
    }

    public void setOutTime(String outTime) {
        this.outTime = outTime;
    }

    public String getTankerPlanning() {
        return TankerPlanning;
    }

    public void setTankerPlanning(String tankerPlanning) {
        TankerPlanning = tankerPlanning;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getUpdatedBy() {
        return UpdatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        UpdatedBy = updatedBy;
    }

    public String getUpdatedDate() {
        return UpdatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        UpdatedDate = updatedDate;
    }

    public char getCurrentProcess() {
        return CurrentProcess;
    }

    public void setCurrentProcess(char currentProcess) {
        CurrentProcess = currentProcess;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getSerialNumber() {
        return SerialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        SerialNumber = serialNumber;
    }

    public String getVehicleNumber() {
        return VehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        VehicleNumber = vehicleNumber;
    }

    public String getTransportName() {
        return TransportName;
    }

    public void setTransportName(String transportName) {
        TransportName = transportName;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public String getCapacityVehicle() {
        return CapacityVehicle;
    }

    public void setCapacityVehicle(String capacityVehicle) {
        CapacityVehicle = capacityVehicle;
    }

    public String getConditionOfVehicle() {
        return ConditionOfVehicle;
    }

    public void setConditionOfVehicle(String conditionOfVehicle) {
        ConditionOfVehicle = conditionOfVehicle;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getMaterialName() {
        return MaterialName;
    }

    public void setMaterialName(String materialName) {
        MaterialName = materialName;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getOAnumber() {
        return OAnumber;
    }

    public void setOAnumber(String OAnumber) {
        this.OAnumber = OAnumber;
    }

    public int getTankerNumber() {
        return TankerNumber;
    }

    public void setTankerNumber(int tankerNumber) {
        TankerNumber = tankerNumber;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public int getHowMuchQuantityFilled() {
        return HowMuchQuantityFilled;
    }

    public void setHowMuchQuantityFilled(int howMuchQuantityFilled) {
        HowMuchQuantityFilled = howMuchQuantityFilled;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public char getNextProcess() {
        return NextProcess;
    }

    public void setNextProcess(char nextProcess) {
        NextProcess = nextProcess;
    }

    public char getI_O() {
        return I_O;
    }

    public void setI_O(char i_O) {
        I_O = i_O;
    }

    public String getVehicleType() {
        return VehicleType;
    }

    public void setVehicleType(String vehicleType) {
        VehicleType = vehicleType;
    }
}
