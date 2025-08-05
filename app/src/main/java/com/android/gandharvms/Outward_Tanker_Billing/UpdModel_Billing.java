package com.android.gandharvms.Outward_Tanker_Billing;

public class UpdModel_Billing {
    public int OutwardId;
    public String ProductQTYUOMOA;
    public String CustomerName;
    public String Location;


    public int getOutwardId() {
        return OutwardId;
    }

    public void setOutwardId(int outwardId) {
        OutwardId = outwardId;
    }

    public String getProductQTYUOMOA() {
        return ProductQTYUOMOA;
    }

    public void setProductQTYUOMOA(String productQTYUOMOA) {
        ProductQTYUOMOA = productQTYUOMOA;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public UpdModel_Billing(int outwardId, String productQTYUOMOA, String customerName, String location) {
        OutwardId = outwardId;
        ProductQTYUOMOA = productQTYUOMOA;
        CustomerName = customerName;
        Location = location;
    }
}
