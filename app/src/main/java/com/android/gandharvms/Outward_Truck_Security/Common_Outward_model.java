package com.android.gandharvms.Outward_Truck_Security;

public class Common_Outward_model {
    public int Id ;
    public int OutwardId ;
    public String Intime ;
    public String OutTime ;
    public int Kl ;
    public String Place ;
    public String VehiclePermit ;
    public String Puc ;
    public String Insurance ;
    public String VehicleFitnessCertificate ;
    public String DriverLicenses ;
    public String RcBook ;
    public String InvoiceNumber ;
    public String NameofParty ;
    public String DescriptionofGoods ;
    public String Signature ;
    public String Remark ;
    public String TransportLRcopy ;
    public String Tremcard ;
    public String Ewaybill ;
    public String Test_Report ;
    public String Invoice ;
    public String OutInTime ;
    public boolean IsActive ;
    public String CreatedBy ;
    public String CreatedDate ;
    public String UpdatedBy ;
    public String UpdatedDate ;
    public boolean IsReporting ;
    public String ReportingRemark ;
    public char CurrentProcess ;
    public String Factory_In ;
    public String Factory_Out ;
    public String SerialNumber ;
    public String VehicleNumber ;
    public String TransportName ;
    public String MobileNumber ;
    public String CapacityVehicle ;
    public String ConditionOfVehicle ;
    public String Date ;
    public String MaterialName ;
    public String CustomerName ;
    public String OAnumber ;
    public int TankerNumber ;
    public String ProductName ;
    public int HowMuchQuantityFilled ;
    public String Location ;
    public char NextProcess ;
    public char I_O ;
    public String VehicleType ;
    public String OutSRemark ;
    public int OutQty ;
    public String OutNetWeight ;
    public int OutQtyUOMId ;
    public int OutNetWeightUOMId ;

    //2
    public String outTime;
    public String TankerPlanning;

    //3
    public String InDriverImage;
    public String InVehicleImage;
    public String OutDriverImage;
    public String OutVehicleImage;
    public String NetWeight;
    public String TareWeight;
    public String GrossWeight;
    public String NumberofPack;
    public String OutWRemark;
    public String SealNumber;

    public int ShortageDip;
    public int ShortageWeight;

    public String ProInTime;
    public String ProOutTime;
    public boolean IsBlendingReq;
    public boolean IsFlushingReq;
    public String Flushing_No;

    public String LabInTime ;
    public String LabOutTime ;

    public String LabRemark ;
    public String ProRemark ;
    public int FlushingNo ;
    public int QtyChginLtr1 ;
    public int QtyChginLtr2 ;
    public int QtyChginLtr3 ;
    public int QtyChginLtr4 ;
    public int QtyChginLtr5 ;
    public String Status ;
    public String QcSign ;
    public String TankOrBlenderNo ;
    public String BulkPqty ;
    public String BatchNo ;
    public String Psign ;
    public String OperatorSign ;
    public char ProductionProcess ;
    public char LaboratoryProcess ;
    public int Density_29_5C;
    public int obplOutwardId;


    public String DespatchInTime ;
    public String DespatchOutTime ;
    public char DespatchProcess ;
    public String DespatchRemark ;
    public int TotalCalCulatedWeight ;
    public String DespatchExtraMaterial ;
    public String DespatchOther ;
    public String Despatch_Sign ;
    public String LaboratoryInTime ;
    public String LaboratoryOutTime ;
    public String LaboratoryRemark ;
    public String ProductionInTime ;
    public String ProductionOutTime ;
    public String ProductionRemark ;
    public String BillingInTime ;
    public String BillingOutTime ;
    public String BillingProcess ;
    public String BillingRemark ;
    public String BillingOutBatchNo ;
    public int BarrelFormQty ;
    public int TypeOfPackagingId ;
    public String TypeOfPackaging ;
    public String SecurityCreatedBy ;
    public String SecurityCreatedDate ;
    public String WeighmentCreatedBy ;
    public String WeighmentCreatedDate ;



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
        return Intime;
    }

    public void setIntime(String intime) {
        Intime = intime;
    }

    public String getOutTime() {
        return OutTime;
    }

    public void setOutTime(String outTime) {
        OutTime = outTime;
    }

    public String getTankerPlanning() {
        return TankerPlanning;
    }

    public void setTankerPlanning(String tankerPlanning) {
        TankerPlanning = tankerPlanning;
    }

    public String getInDriverImage() {
        return InDriverImage;
    }

    public void setInDriverImage(String inDriverImage) {
        InDriverImage = inDriverImage;
    }

    public String getInVehicleImage() {
        return InVehicleImage;
    }

    public void setInVehicleImage(String inVehicleImage) {
        InVehicleImage = inVehicleImage;
    }

    public String getOutDriverImage() {
        return OutDriverImage;
    }

    public void setOutDriverImage(String outDriverImage) {
        OutDriverImage = outDriverImage;
    }

    public String getOutVehicleImage() {
        return OutVehicleImage;
    }

    public void setOutVehicleImage(String outVehicleImage) {
        OutVehicleImage = outVehicleImage;
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

    public String getNumberofPack() {
        return NumberofPack;
    }

    public void setNumberofPack(String numberofPack) {
        NumberofPack = numberofPack;
    }

    public String getOutWRemark() {
        return OutWRemark;
    }

    public void setOutWRemark(String outWRemark) {
        OutWRemark = outWRemark;
    }

    public String getSealNumber() {
        return SealNumber;
    }

    public void setSealNumber(String sealNumber) {
        SealNumber = sealNumber;
    }

    public int getShortageDip() {
        return ShortageDip;
    }

    public void setShortageDip(int shortageDip) {
        ShortageDip = shortageDip;
    }

    public int getShortageWeight() {
        return ShortageWeight;
    }

    public void setShortageWeight(int shortageWeight) {
        ShortageWeight = shortageWeight;
    }

    public String getProInTime() {
        return ProInTime;
    }

    public void setProInTime(String proInTime) {
        ProInTime = proInTime;
    }

    public String getProOutTime() {
        return ProOutTime;
    }

    public void setProOutTime(String proOutTime) {
        ProOutTime = proOutTime;
    }

    public boolean isBlendingReq() {
        return IsBlendingReq;
    }

    public void setBlendingReq(boolean blendingReq) {
        IsBlendingReq = blendingReq;
    }

    public boolean isFlushingReq() {
        return IsFlushingReq;
    }

    public void setFlushingReq(boolean flushingReq) {
        IsFlushingReq = flushingReq;
    }

    public String getFlushing_No() {
        return Flushing_No;
    }

    public void setFlushing_No(String flushing_No) {
        Flushing_No = flushing_No;
    }

    public String getLabInTime() {
        return LabInTime;
    }

    public void setLabInTime(String labInTime) {
        LabInTime = labInTime;
    }

    public String getLabOutTime() {
        return LabOutTime;
    }

    public void setLabOutTime(String labOutTime) {
        LabOutTime = labOutTime;
    }

    public String getLabRemark() {
        return LabRemark;
    }

    public void setLabRemark(String labRemark) {
        LabRemark = labRemark;
    }

    public String getProRemark() {
        return ProRemark;
    }

    public void setProRemark(String proRemark) {
        ProRemark = proRemark;
    }

    public int getFlushingNo() {
        return FlushingNo;
    }

    public void setFlushingNo(int flushingNo) {
        FlushingNo = flushingNo;
    }

    public int getQtyChginLtr1() {
        return QtyChginLtr1;
    }

    public void setQtyChginLtr1(int qtyChginLtr1) {
        QtyChginLtr1 = qtyChginLtr1;
    }

    public int getQtyChginLtr2() {
        return QtyChginLtr2;
    }

    public void setQtyChginLtr2(int qtyChginLtr2) {
        QtyChginLtr2 = qtyChginLtr2;
    }

    public int getQtyChginLtr3() {
        return QtyChginLtr3;
    }

    public void setQtyChginLtr3(int qtyChginLtr3) {
        QtyChginLtr3 = qtyChginLtr3;
    }

    public int getQtyChginLtr4() {
        return QtyChginLtr4;
    }

    public void setQtyChginLtr4(int qtyChginLtr4) {
        QtyChginLtr4 = qtyChginLtr4;
    }

    public int getQtyChginLtr5() {
        return QtyChginLtr5;
    }

    public void setQtyChginLtr5(int qtyChginLtr5) {
        QtyChginLtr5 = qtyChginLtr5;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getQcSign() {
        return QcSign;
    }

    public void setQcSign(String qcSign) {
        QcSign = qcSign;
    }

    public String getTankOrBlenderNo() {
        return TankOrBlenderNo;
    }

    public void setTankOrBlenderNo(String tankOrBlenderNo) {
        TankOrBlenderNo = tankOrBlenderNo;
    }

    public String getBulkPqty() {
        return BulkPqty;
    }

    public void setBulkPqty(String bulkPqty) {
        BulkPqty = bulkPqty;
    }

    public String getBatchNo() {
        return BatchNo;
    }

    public void setBatchNo(String batchNo) {
        BatchNo = batchNo;
    }

    public String getPsign() {
        return Psign;
    }

    public void setPsign(String psign) {
        Psign = psign;
    }

    public String getOperatorSign() {
        return OperatorSign;
    }

    public void setOperatorSign(String operatorSign) {
        OperatorSign = operatorSign;
    }

    public char getProductionProcess() {
        return ProductionProcess;
    }

    public void setProductionProcess(char productionProcess) {
        ProductionProcess = productionProcess;
    }

    public char getLaboratoryProcess() {
        return LaboratoryProcess;
    }

    public void setLaboratoryProcess(char laboratoryProcess) {
        LaboratoryProcess = laboratoryProcess;
    }

    public int getDensity_29_5C() {
        return Density_29_5C;
    }

    public void setDensity_29_5C(int density_29_5C) {
        Density_29_5C = density_29_5C;
    }

    public int getObplOutwardId() {
        return obplOutwardId;
    }

    public void setObplOutwardId(int obplOutwardId) {
        this.obplOutwardId = obplOutwardId;
    }

    public String getDespatchInTime() {
        return DespatchInTime;
    }

    public void setDespatchInTime(String despatchInTime) {
        DespatchInTime = despatchInTime;
    }

    public String getDespatchOutTime() {
        return DespatchOutTime;
    }

    public void setDespatchOutTime(String despatchOutTime) {
        DespatchOutTime = despatchOutTime;
    }

    public char getDespatchProcess() {
        return DespatchProcess;
    }

    public void setDespatchProcess(char despatchProcess) {
        DespatchProcess = despatchProcess;
    }

    public String getDespatchRemark() {
        return DespatchRemark;
    }

    public void setDespatchRemark(String despatchRemark) {
        DespatchRemark = despatchRemark;
    }

    public int getTotalCalCulatedWeight() {
        return TotalCalCulatedWeight;
    }

    public void setTotalCalCulatedWeight(int totalCalCulatedWeight) {
        TotalCalCulatedWeight = totalCalCulatedWeight;
    }

    public String getDespatchExtraMaterial() {
        return DespatchExtraMaterial;
    }

    public void setDespatchExtraMaterial(String despatchExtraMaterial) {
        DespatchExtraMaterial = despatchExtraMaterial;
    }

    public String getDespatchOther() {
        return DespatchOther;
    }

    public void setDespatchOther(String despatchOther) {
        DespatchOther = despatchOther;
    }

    public String getDespatch_Sign() {
        return Despatch_Sign;
    }

    public void setDespatch_Sign(String despatch_Sign) {
        Despatch_Sign = despatch_Sign;
    }

    public String getLaboratoryInTime() {
        return LaboratoryInTime;
    }

    public void setLaboratoryInTime(String laboratoryInTime) {
        LaboratoryInTime = laboratoryInTime;
    }

    public String getLaboratoryOutTime() {
        return LaboratoryOutTime;
    }

    public void setLaboratoryOutTime(String laboratoryOutTime) {
        LaboratoryOutTime = laboratoryOutTime;
    }

    public String getLaboratoryRemark() {
        return LaboratoryRemark;
    }

    public void setLaboratoryRemark(String laboratoryRemark) {
        LaboratoryRemark = laboratoryRemark;
    }

    public String getProductionInTime() {
        return ProductionInTime;
    }

    public void setProductionInTime(String productionInTime) {
        ProductionInTime = productionInTime;
    }

    public String getProductionOutTime() {
        return ProductionOutTime;
    }

    public void setProductionOutTime(String productionOutTime) {
        ProductionOutTime = productionOutTime;
    }

    public String getProductionRemark() {
        return ProductionRemark;
    }

    public void setProductionRemark(String productionRemark) {
        ProductionRemark = productionRemark;
    }

    public String getBillingInTime() {
        return BillingInTime;
    }

    public void setBillingInTime(String billingInTime) {
        BillingInTime = billingInTime;
    }

    public String getBillingOutTime() {
        return BillingOutTime;
    }

    public void setBillingOutTime(String billingOutTime) {
        BillingOutTime = billingOutTime;
    }

    public String getBillingProcess() {
        return BillingProcess;
    }

    public void setBillingProcess(String billingProcess) {
        BillingProcess = billingProcess;
    }

    public String getBillingRemark() {
        return BillingRemark;
    }

    public void setBillingRemark(String billingRemark) {
        BillingRemark = billingRemark;
    }

    public String getBillingOutBatchNo() {
        return BillingOutBatchNo;
    }

    public void setBillingOutBatchNo(String billingOutBatchNo) {
        BillingOutBatchNo = billingOutBatchNo;
    }

    public int getBarrelFormQty() {
        return BarrelFormQty;
    }

    public void setBarrelFormQty(int barrelFormQty) {
        BarrelFormQty = barrelFormQty;
    }

    public int getTypeOfPackagingId() {
        return TypeOfPackagingId;
    }

    public void setTypeOfPackagingId(int typeOfPackagingId) {
        TypeOfPackagingId = typeOfPackagingId;
    }

    public String getTypeOfPackaging() {
        return TypeOfPackaging;
    }

    public void setTypeOfPackaging(String typeOfPackaging) {
        TypeOfPackaging = typeOfPackaging;
    }

    public String getSecurityCreatedBy() {
        return SecurityCreatedBy;
    }

    public void setSecurityCreatedBy(String securityCreatedBy) {
        SecurityCreatedBy = securityCreatedBy;
    }

    public String getSecurityCreatedDate() {
        return SecurityCreatedDate;
    }

    public void setSecurityCreatedDate(String securityCreatedDate) {
        SecurityCreatedDate = securityCreatedDate;
    }

    public String getWeighmentCreatedBy() {
        return WeighmentCreatedBy;
    }

    public void setWeighmentCreatedBy(String weighmentCreatedBy) {
        WeighmentCreatedBy = weighmentCreatedBy;
    }

    public String getWeighmentCreatedDate() {
        return WeighmentCreatedDate;
    }

    public void setWeighmentCreatedDate(String weighmentCreatedDate) {
        WeighmentCreatedDate = weighmentCreatedDate;
    }

    public int getKl() {
        return Kl;
    }

    public void setKl(int kl) {
        Kl = kl;
    }

    public String getPlace() {
        return Place;
    }

    public void setPlace(String place) {
        Place = place;
    }

    public String getVehiclePermit() {
        return VehiclePermit;
    }

    public void setVehiclePermit(String vehiclePermit) {
        VehiclePermit = vehiclePermit;
    }

    public String getPuc() {
        return Puc;
    }

    public void setPuc(String puc) {
        Puc = puc;
    }

    public String getInsurance() {
        return Insurance;
    }

    public void setInsurance(String insurance) {
        Insurance = insurance;
    }

    public String getVehicleFitnessCertificate() {
        return VehicleFitnessCertificate;
    }

    public void setVehicleFitnessCertificate(String vehicleFitnessCertificate) {
        VehicleFitnessCertificate = vehicleFitnessCertificate;
    }

    public String getDriverLicenses() {
        return DriverLicenses;
    }

    public void setDriverLicenses(String driverLicenses) {
        DriverLicenses = driverLicenses;
    }

    public String getRcBook() {
        return RcBook;
    }

    public void setRcBook(String rcBook) {
        RcBook = rcBook;
    }

    public String getInvoiceNumber() {
        return InvoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        InvoiceNumber = invoiceNumber;
    }

    public String getNameofParty() {
        return NameofParty;
    }

    public void setNameofParty(String nameofParty) {
        NameofParty = nameofParty;
    }

    public String getDescriptionofGoods() {
        return DescriptionofGoods;
    }

    public void setDescriptionofGoods(String descriptionofGoods) {
        DescriptionofGoods = descriptionofGoods;
    }

    public String getSignature() {
        return Signature;
    }

    public void setSignature(String signature) {
        Signature = signature;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getTransportLRcopy() {
        return TransportLRcopy;
    }

    public void setTransportLRcopy(String transportLRcopy) {
        TransportLRcopy = transportLRcopy;
    }

    public String getTremcard() {
        return Tremcard;
    }

    public void setTremcard(String tremcard) {
        Tremcard = tremcard;
    }

    public String getEwaybill() {
        return Ewaybill;
    }

    public void setEwaybill(String ewaybill) {
        Ewaybill = ewaybill;
    }

    public String getTest_Report() {
        return Test_Report;
    }

    public void setTest_Report(String test_Report) {
        Test_Report = test_Report;
    }

    public String getInvoice() {
        return Invoice;
    }

    public void setInvoice(String invoice) {
        Invoice = invoice;
    }

    public String getOutInTime() {
        return OutInTime;
    }

    public void setOutInTime(String outInTime) {
        OutInTime = outInTime;
    }

    public boolean isActive() {
        return IsActive;
    }

    public void setActive(boolean active) {
        IsActive = active;
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

    public boolean isReporting() {
        return IsReporting;
    }

    public void setReporting(boolean reporting) {
        IsReporting = reporting;
    }

    public String getReportingRemark() {
        return ReportingRemark;
    }

    public void setReportingRemark(String reportingRemark) {
        ReportingRemark = reportingRemark;
    }

    public char getCurrentProcess() {
        return CurrentProcess;
    }

    public void setCurrentProcess(char currentProcess) {
        CurrentProcess = currentProcess;
    }

    public String getFactory_In() {
        return Factory_In;
    }

    public void setFactory_In(String factory_In) {
        Factory_In = factory_In;
    }

    public String getFactory_Out() {
        return Factory_Out;
    }

    public void setFactory_Out(String factory_Out) {
        Factory_Out = factory_Out;
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

    public String getOutSRemark() {
        return OutSRemark;
    }

    public void setOutSRemark(String outSRemark) {
        OutSRemark = outSRemark;
    }

    public int getOutQty() {
        return OutQty;
    }

    public void setOutQty(int outQty) {
        OutQty = outQty;
    }

    public String getOutNetWeight() {
        return OutNetWeight;
    }

    public void setOutNetWeight(String outNetWeight) {
        OutNetWeight = outNetWeight;
    }

    public int getOutQtyUOMId() {
        return OutQtyUOMId;
    }

    public void setOutQtyUOMId(int outQtyUOMId) {
        OutQtyUOMId = outQtyUOMId;
    }

    public int getOutNetWeightUOMId() {
        return OutNetWeightUOMId;
    }

    public void setOutNetWeightUOMId(int outNetWeightUOMId) {
        OutNetWeightUOMId = outNetWeightUOMId;
    }
}
