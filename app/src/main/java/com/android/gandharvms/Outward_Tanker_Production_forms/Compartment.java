package com.android.gandharvms.Outward_Tanker_Production_forms;

import com.google.gson.annotations.SerializedName;

public class Compartment {
    @SerializedName("Blender")
    private String blenderNumber;
    @SerializedName("ProductionSign")
    private String productionSign;
    @SerializedName("OperatorSign")
    private String operatorSign;

    public Compartment(String blenderNumber, String productionSign, String operatorSign) {
        this.blenderNumber = blenderNumber;
        this.productionSign = productionSign;
        this.operatorSign = operatorSign;
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
}
