package com.android.gandharvms.Inward_Truck_Security;

public class matriallist {
    private String Material;
    private int Qty;
    private String Qtyuom;

    public matriallist(String material, int qty, String qtyuom) {
        Material = material;
        Qty = qty;
        Qtyuom = qtyuom;
    }

    public String getMaterial() {
        return Material;
    }

    public void setMaterial(String material) {
        Material = material;
    }

    public int getQty() {
        return Qty;
    }

    public void setQty(int qty) {
        Qty = qty;
    }

    public String getQtyuom() {
        return Qtyuom;
    }

    public void setQtyuom(String qtyuom) {
        Qtyuom = qtyuom;
    }
}
