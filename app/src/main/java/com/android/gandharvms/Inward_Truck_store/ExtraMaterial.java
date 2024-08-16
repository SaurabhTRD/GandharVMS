package com.android.gandharvms.Inward_Truck_store;

public class ExtraMaterial {
    private String material;
    private String qty;
    private String qtyuom;
    private String recivingqty;

    public ExtraMaterial(String material, String qty, String qtyuom, String recivingqty) {
        this.material = material;
        this.qty = qty;
        this.qtyuom = qtyuom;
        this.recivingqty = recivingqty;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getQtyuom() {
        return qtyuom;
    }

    public void setQtyuom(String qtyuom) {
        this.qtyuom = qtyuom;
    }

    public String getRecivingqty() {
        return recivingqty;
    }

    public void setRecivingqty(String recivingqty) {
        this.recivingqty = recivingqty;
    }

}
