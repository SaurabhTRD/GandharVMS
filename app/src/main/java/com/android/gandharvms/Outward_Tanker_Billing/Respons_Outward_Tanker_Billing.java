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
    public String ProductQTYUOMOA;

    String Density_29_5C;
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
    public int ilpackof10ltrqty;
    public int ilpackof18ltrqty;
    public int ilpackof20ltrqty;
    public int ilpackof26ltrqty;
    public int ilpackof50ltrqty;
    public int ilpackof210ltrqty;
    public int ilpackofboxbuxketltrqty;

    public int splpackof7ltrqty;
    public int splpackof7_5ltrqty;
    public int splpackof8_5ltrqty;
    public int splpackof10ltrqty;
    public int splpackof11ltrqty;
    public int splpackof12ltrqty;
    public int splpackof13ltrqty;
    public int splpackof15ltrqty;
    public int splpackof18ltrqty;
    public int splpackof20ltrqty;
    public int splpackof26ltrqty;
    public int splpackof50ltrqty;
    public int splpackof210ltrqty;
    public int splpackofboxbuxketltrqty;
    public String Batch_No;
    public String AllOTRemarks;
    public String AllORRemarks;
    public String HoldRemark;
    public String labcompartment1;
    public String labcompartment2;
    public String labcompartment3;
    public String labcompartment4;
    public String labcompartment5;
    public String labcompartment6;

    public String getHoldRemark() {
        return HoldRemark;
    }

    public void setHoldRemark(String holdRemark) {
        HoldRemark = holdRemark;
    }

    public String getProductQTYUOMOA() {
        return ProductQTYUOMOA;
    }

    public void setProductQTYUOMOA(String productQTYUOMOA) {
        ProductQTYUOMOA = productQTYUOMOA;
    }

    public String getBatch_No() {
        return Batch_No;
    }

    public void setBatch_No(String batch_No) {
        Batch_No = batch_No;
    }

    public String getHowMuchQTYUOM() {
        return HowMuchQTYUOM;
    }

    public void setHowMuchQTYUOM(String howMuchQTYUOM) {
        HowMuchQTYUOM = howMuchQTYUOM;
    }

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

    public String getDensity_29_5C() {
        return Density_29_5C;
    }

    public void setDensity_29_5C(String density_29_5C) {
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

    public Respons_Outward_Tanker_Billing(int outwardId, String intime, String outTime, String tankerPlanning, String createdBy, String updatedBy, char currentProcess, String remark, String serialNumber, String vehicleNumber, String OAnumber, String customerName, String productName, int howMuchQuantityFilled, String location, char nextProcess, char i_O, String vehicleType, String howMuchQTYUOM, String productqtyuomoa) {
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
        ProductQTYUOMOA=productqtyuomoa;
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

    public int getIlpackof10ltrqty() {
        return ilpackof10ltrqty;
    }

    public void setIlpackof10ltrqty(int ilpackof10ltrqty) {
        this.ilpackof10ltrqty = ilpackof10ltrqty;
    }

    public int getIlpackof18ltrqty() {
        return ilpackof18ltrqty;
    }

    public void setIlpackof18ltrqty(int ilpackof18ltrqty) {
        this.ilpackof18ltrqty = ilpackof18ltrqty;
    }

    public int getIlpackof20ltrqty() {
        return ilpackof20ltrqty;
    }

    public void setIlpackof20ltrqty(int ilpackof20ltrqty) {
        this.ilpackof20ltrqty = ilpackof20ltrqty;
    }

    public int getIlpackof26ltrqty() {
        return ilpackof26ltrqty;
    }

    public void setIlpackof26ltrqty(int ilpackof26ltrqty) {
        this.ilpackof26ltrqty = ilpackof26ltrqty;
    }

    public int getIlpackof50ltrqty() {
        return ilpackof50ltrqty;
    }

    public void setIlpackof50ltrqty(int ilpackof50ltrqty) {
        this.ilpackof50ltrqty = ilpackof50ltrqty;
    }

    public int getIlpackof210ltrqty() {
        return ilpackof210ltrqty;
    }

    public void setIlpackof210ltrqty(int ilpackof210ltrqty) {
        this.ilpackof210ltrqty = ilpackof210ltrqty;
    }

    public int getIlpackofboxbuxketltrqty() {
        return ilpackofboxbuxketltrqty;
    }

    public void setIlpackofboxbuxketltrqty(int ilpackofboxbuxketltrqty) {
        this.ilpackofboxbuxketltrqty = ilpackofboxbuxketltrqty;
    }

    public int getSplpackof7ltrqty() {
        return splpackof7ltrqty;
    }

    public void setSplpackof7ltrqty(int splpackof7ltrqty) {
        this.splpackof7ltrqty = splpackof7ltrqty;
    }

    public int getSplpackof7_5ltrqty() {
        return splpackof7_5ltrqty;
    }

    public void setSplpackof7_5ltrqty(int splpackof7_5ltrqty) {
        this.splpackof7_5ltrqty = splpackof7_5ltrqty;
    }

    public int getSplpackof8_5ltrqty() {
        return splpackof8_5ltrqty;
    }

    public void setSplpackof8_5ltrqty(int splpackof8_5ltrqty) {
        this.splpackof8_5ltrqty = splpackof8_5ltrqty;
    }

    public int getSplpackof10ltrqty() {
        return splpackof10ltrqty;
    }

    public void setSplpackof10ltrqty(int splpackof10ltrqty) {
        this.splpackof10ltrqty = splpackof10ltrqty;
    }

    public int getSplpackof11ltrqty() {
        return splpackof11ltrqty;
    }

    public void setSplpackof11ltrqty(int splpackof11ltrqty) {
        this.splpackof11ltrqty = splpackof11ltrqty;
    }

    public int getSplpackof12ltrqty() {
        return splpackof12ltrqty;
    }

    public void setSplpackof12ltrqty(int splpackof12ltrqty) {
        this.splpackof12ltrqty = splpackof12ltrqty;
    }

    public int getSplpackof13ltrqty() {
        return splpackof13ltrqty;
    }

    public void setSplpackof13ltrqty(int splpackof13ltrqty) {
        this.splpackof13ltrqty = splpackof13ltrqty;
    }

    public int getSplpackof15ltrqty() {
        return splpackof15ltrqty;
    }

    public void setSplpackof15ltrqty(int splpackof15ltrqty) {
        this.splpackof15ltrqty = splpackof15ltrqty;
    }

    public int getSplpackof18ltrqty() {
        return splpackof18ltrqty;
    }

    public void setSplpackof18ltrqty(int splpackof18ltrqty) {
        this.splpackof18ltrqty = splpackof18ltrqty;
    }

    public int getSplpackof20ltrqty() {
        return splpackof20ltrqty;
    }

    public void setSplpackof20ltrqty(int splpackof20ltrqty) {
        this.splpackof20ltrqty = splpackof20ltrqty;
    }

    public int getSplpackof26ltrqty() {
        return splpackof26ltrqty;
    }

    public void setSplpackof26ltrqty(int splpackof26ltrqty) {
        this.splpackof26ltrqty = splpackof26ltrqty;
    }

    public int getSplpackof50ltrqty() {
        return splpackof50ltrqty;
    }

    public void setSplpackof50ltrqty(int splpackof50ltrqty) {
        this.splpackof50ltrqty = splpackof50ltrqty;
    }

    public int getSplpackof210ltrqty() {
        return splpackof210ltrqty;
    }

    public void setSplpackof210ltrqty(int splpackof210ltrqty) {
        this.splpackof210ltrqty = splpackof210ltrqty;
    }

    public String getAllOTRemarks() {
        return AllOTRemarks;
    }

    public void setAllOTRemarks(String allOTRemarks) {
        AllOTRemarks = allOTRemarks;
    }

    public int getSplpackofboxbuxketltrqty() {
        return splpackofboxbuxketltrqty;
    }

    public void setSplpackofboxbuxketltrqty(int splpackofboxbuxketltrqty) {
        this.splpackofboxbuxketltrqty = splpackofboxbuxketltrqty;
    }

    public String getAllORRemarks() {
        return AllORRemarks;
    }

    public void setAllORRemarks(String allORRemarks) {
        AllORRemarks = allORRemarks;
    }

    public String getLabcompartment1() {
        return labcompartment1;
    }

    public void setLabcompartment1(String labcompartment1) {
        this.labcompartment1 = labcompartment1;
    }

    public String getLabcompartment2() {
        return labcompartment2;
    }

    public void setLabcompartment2(String labcompartment2) {
        this.labcompartment2 = labcompartment2;
    }

    public String getLabcompartment3() {
        return labcompartment3;
    }

    public void setLabcompartment3(String labcompartment3) {
        this.labcompartment3 = labcompartment3;
    }

    public String getLabcompartment4() {
        return labcompartment4;
    }

    public void setLabcompartment4(String labcompartment4) {
        this.labcompartment4 = labcompartment4;
    }

    public String getLabcompartment5() {
        return labcompartment5;
    }

    public void setLabcompartment5(String labcompartment5) {
        this.labcompartment5 = labcompartment5;
    }

    public String getLabcompartment6() {
        return labcompartment6;
    }

    public void setLabcompartment6(String labcompartment6) {
        this.labcompartment6 = labcompartment6;
    }
}
