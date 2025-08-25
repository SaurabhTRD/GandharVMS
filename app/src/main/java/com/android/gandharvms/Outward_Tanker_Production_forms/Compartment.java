package com.android.gandharvms.Outward_Tanker_Production_forms;

import com.google.gson.annotations.SerializedName;

public class Compartment {
    @SerializedName("Blender")
    private String blenderNumber;
    @SerializedName("ProductionSign")
    private String productionSign;
    @SerializedName("OperatorSign")
    private String operatorSign;

    private String viscosity;
    private String density;
    private String batchNumber;
    private String qcOfficer;
    private String Remark;

    public String ProductName;
    private boolean isSelected; // ✅ New field to track selection
    public String InTime;

    public Compartment(String intime, String blenderNumber, String productionSign, String operatorSign, String productname) {
        this.InTime=intime;
        this.blenderNumber = blenderNumber;
        this.productionSign = productionSign;
        this.operatorSign = operatorSign;
        this.ProductName = productname;
        this.isSelected = false; // Default: Not selected
    }
    public String getBlenderNumber() { return blenderNumber; }
    public String getProductionSign() { return productionSign; }
    public String getOperatorSign() { return operatorSign; }
    public String getProductname() { return ProductName; }
    public String setProductname() { return operatorSign; }


    // ✅ Add Getter and Setter for `isSelected`
    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        this.isSelected = selected;
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
        return Remark;
    }

    public void setRemark(String remark) {
        this.Remark = remark;
    }

    public String getIntime() {
        return InTime;
    }

    public void setIntime(String intime) {
        this.InTime = intime;
    }
}
