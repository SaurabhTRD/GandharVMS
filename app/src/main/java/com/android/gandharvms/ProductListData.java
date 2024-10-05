package com.android.gandharvms;

public class ProductListData {
    private String OANumber;
    private String ProductName;
    private String ProductQty;
    private String ProductQtyuom;

    public String getOANumber() {
        return OANumber;
    }

    public void setOANumber(String OANumber) {
        this.OANumber = OANumber;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductQty() {
        return ProductQty;
    }

    public void setProductQty(String productQty) {
        ProductQty = productQty;
    }

    public String getProductQtyuom() {
        return ProductQtyuom;
    }

    public void setProductQtyuom(String productQtyuom) {
        ProductQtyuom = productQtyuom;
    }

    public ProductListData(String OANumber, String productName, String productQty, String productQtyuom) {
        this.OANumber = OANumber;
        ProductName = productName;
        ProductQty = productQty;
        ProductQtyuom = productQtyuom;
    }
}
