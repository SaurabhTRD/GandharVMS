package com.android.gandharvms.Inward_Truck_store;

public class ExtraMaterial {
    private String Material;
    private String Qty;
    private String Qtyuom;
    private String recivingqty;
    private String recQtyUOM;

    public ExtraMaterial(String material, String qty, String qtyuom, String recivingqty,String receiQTYUom) {
        Material = material;
        Qty = qty;
        Qtyuom = qtyuom;
        this.recivingqty = recivingqty;
        recQtyUOM=receiQTYUom;
    }

    public String getMaterial() {
        return Material;
    }

    public void setMaterial(String material) {
        Material = material;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }

    public String getQtyuom() {
        return Qtyuom;
    }

    public void setQtyuom(String qtyuom) {
        Qtyuom = qtyuom;
    }

    public String getRecivingqty() {
        return recivingqty;
    }

    public void setRecivingqty(String recivingqty) {
        this.recivingqty = recivingqty;
    }

    public String getRecQtyUOM() {
        return recQtyUOM;
    }

    public void setRecQtyUOM(String recQtyUOM) {
        this.recQtyUOM = recQtyUOM;
    }
}
