package com.android.gandharvms.outward_Tanker_Lab_forms;

public class LabCompartment_Model {
    public String inTime;
    public String iviscosity;
    public String identinity;
    public String ibatchnum;
    public String iqcofficer;
    public String iremarks;

    public LabCompartment_Model(String inTime, String iviscosity, String identinity, String ibatchnum, String iqcofficer, String iremarks) {
        this.inTime = inTime;
        this.iviscosity = iviscosity;
        this.identinity = identinity;
        this.ibatchnum = ibatchnum;
        this.iqcofficer = iqcofficer;
        this.iremarks = iremarks;
    }

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

    private String originalJson;


    public String getOriginalJson() {
        return originalJson;
    }
    public void setOriginalJson(String originalJson) {
        this.originalJson = originalJson;
    }
}
