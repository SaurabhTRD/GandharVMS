package com.android.gandharvms.outward_Tanker_Lab_forms;

import com.google.gson.annotations.SerializedName;

public class Lab_compartment_model {
    @SerializedName("Blender")
    private String blenderNumber;
    @SerializedName("ProductionSign")
    private String productionSign;
    @SerializedName("OperatorSign")
    private String operatorSign;

    @SerializedName("viscosity")
    private String viscosity;
    @SerializedName("density")
    private String density;
    @SerializedName("batchNumber")
    private String batchNumber;
    @SerializedName("qcOfficer")
    private String qcOfficer;
    @SerializedName("remark")
    private String remark;

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

    public String getBlenderNumber() { return blenderNumber; }
    public String getProductionSign() { return productionSign; }
    public String getOperatorSign() { return operatorSign; }

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

    public String getTareweight() {
        return tareweight;
    }

    public void setTareweight(String tareweight) {
        this.tareweight = tareweight;
    }

    public String getWeighremark() {
        return weighremark;
    }

    public void setWeighremark(String weighremark) {
        this.weighremark = weighremark;
    }
}
