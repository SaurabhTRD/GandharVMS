package com.android.gandharvms.outward_Tanker_Lab_forms;

import com.google.gson.annotations.SerializedName;

public class Lab_compartment_model {
    @SerializedName("ProductName")
    public String ProductName;
    @SerializedName("Blender")
    private String blenderNumber;
    @SerializedName("ProductionSign")
    private String productionSign;
    @SerializedName("OperatorSign")
    private String operatorSign;
    private String TareWeight;

    @SerializedName("VerificationRemark")
    private String VerificationRemark;
//    public String VerificationRemark() {
//        return VerificationRemark;
//    }
//
//    public String getVerificationRemark() {
//        return VerificationRemark;
//    }

//    public void setVerificationRemark(String verificationRemark) {
//        VerificationRemark = verificationRemark;
//    }

    @SerializedName("Viscosity")  // ❌ Fix (capital "V")
    private String viscosity;

    @SerializedName("Density")  // ❌ Fix (capital "D")
    private String density;

    @SerializedName("BatchNumber")  // ❌ Fix (capital "B")
    private String batchNumber;

    @SerializedName("QcOfficer")  // ❌ Fix (capital "Q")
    private String qcOfficer;

    @SerializedName("Remark")  // ❌ Fix (capital "R")
    private String remark;

    @SerializedName("LabRemark")  // ❌ Fix (capital "R")
    private String Labremark;





    public String inTime;
    public String iviscosity;
    public String identinity;
    public String ibatchnum;
    public String iqcofficer;
    public String iremarks;

    public String getInTime() {
        return inTime;
    }

    public void setInTime(String inTime) {
        this.inTime = inTime;
    }

    public String getIviscosity() {
        return iviscosity;
    }

    public void setIviscosity(String iviscosity) {
        this.iviscosity = iviscosity;
    }

    public String getIdentinity() {
        return identinity;
    }

    public void setIdentinity(String identinity) {
        this.identinity = identinity;
    }

    public String getIbatchnum() {
        return ibatchnum;
    }

    public void setIbatchnum(String ibatchnum) {
        this.ibatchnum = ibatchnum;
    }

    public String getIqcofficer() {
        return iqcofficer;
    }

    public void setIqcofficer(String iqcofficer) {
        this.iqcofficer = iqcofficer;
    }

    public String getIremarks() {
        return iremarks;
    }

    public void setIremarks(String iremarks) {
        this.iremarks = iremarks;
    }

    @SerializedName("wehintime")
    private String wehintime;
    @SerializedName("tareweight")
    private String tareweight;
    @SerializedName("weighremark")
    private String weighremark;


//    public Lab_compartment_model(String blenderNumber, String productionSign, String operatorSign) {
//        this.blenderNumber = blenderNumber;
//        this.productionSign = productionSign;
//        this.operatorSign = operatorSign;
//    }

    public Lab_compartment_model(String viscosity, String density, String batchNumber, String qcOfficer, String remark) {
        this.viscosity = viscosity;
        this.density = density;
        this.batchNumber = batchNumber;
        this.qcOfficer = qcOfficer;
        this.remark = remark;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        this.ProductName = productName;
    }

    public String getBlenderNumber() { return blenderNumber; }
    public String getProductionSign() { return productionSign; }
    public String getOperatorSign() { return operatorSign; }

    public String getTareweight() { return TareWeight; } // ✅ Correct getter method
    public void setTareweight(String tareWeight) { this.TareWeight = tareWeight; } // ✅ Correct setter method
//    public String getVerificationRemark() { return remark; }
//    public void setVerificationRemark(String remark) { this.remark = remark; }
    public String getVerificationRemark() {
        return VerificationRemark;
    }

    public void setVerificationRemark(String verificationRemark) {
        VerificationRemark = verificationRemark;
    }

    public void setBlenderNumber(String blenderNumber) {
        this.blenderNumber = blenderNumber;
    }

    public void setProductionSign(String productionSign) {
        this.productionSign = productionSign;
    }

    public void setOperatorSign(String operatorSign) {
        this.operatorSign = operatorSign;
    }

    public String getViscosity() {
        return viscosity;
    }

    public void setViscosity(String viscosity) {
        this.viscosity = viscosity;
    }

    public String getDensity() {
        return density;
    }

    public void setDensity(String density) {
        this.density = density;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getQcOfficer() {
        return qcOfficer;
    }

    public void setQcOfficer(String qcOfficer) {
        this.qcOfficer = qcOfficer;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getWehintime() {
        return wehintime;
    }

    public void setWehintime(String wehintime) {
        this.wehintime = wehintime;
    }

//    public String getTareweight() {
//        return tareweight;
//    }
//
//    public void setTareweight(String tareweight) {
//        this.tareweight = tareweight;
//    }

    public String getWeighremark() {
        return weighremark;
    }

    public void setWeighremark(String weighremark) {
        this.weighremark = weighremark;
    }

    public String getLabremark() {
        return Labremark;
    }

    public void setLabremark(String labremark) {
        Labremark = labremark;
    }
}
