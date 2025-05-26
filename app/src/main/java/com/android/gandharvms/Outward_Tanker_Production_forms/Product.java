package com.android.gandharvms.Outward_Tanker_Production_forms;

public class Product {
    private String productName;
    private String blenderNumber;
    private String signOfProduction;
    private String operatorSign;
    private String remark;
    private String InTime;
    private boolean isExpanded; // To track visibility of details
    private boolean isSelected; // ✅ New field to track selection
    private String OANumber;


    public Product(String productName, String OANumber) {
        this.productName = productName;
        this.OANumber = OANumber;
    }

    public Product(String productName) {
        this.productName = productName;
        this.blenderNumber = "";
        this.operatorSign = "";
        this.signOfProduction = "";
        this.remark = "";
        this.InTime = "";
        this.isSelected = false; // Default: Not selected
    }


    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getBlenderNumber() { return blenderNumber; }
    public void setBlenderNumber(String blenderNumber) { this.blenderNumber = blenderNumber; }

    public String getOperatorSign() { return operatorSign; }
    public void setOperatorSign(String operatorSign) { this.operatorSign = operatorSign; }

    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }

    public String getInTime() { return InTime; }
    public void setInTime(String inTime) { this.InTime = inTime; }
    // ✅ Getter and Setter for `isSelected`
    public boolean isSelected() { return isSelected; }
    public void setSelected(boolean selected) { this.isSelected = selected; }
    public String getSignOfProduction() { return signOfProduction; }
    public void setSignOfProduction(String signOfProduction) { this.signOfProduction = signOfProduction; }

    public String getOANumber() {
        return OANumber;
    }

    public void setOANumber(String OANumber) {
        this.OANumber = OANumber;
    }
}
