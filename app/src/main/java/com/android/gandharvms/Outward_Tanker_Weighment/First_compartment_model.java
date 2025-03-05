package com.android.gandharvms.Outward_Tanker_Weighment;

import com.google.gson.annotations.SerializedName;

public class First_compartment_model {
    @SerializedName("tareweight")
    private String tareweight;
    @SerializedName("weighremark")
    private String weighremark;

    public First_compartment_model(String tareweight, String weighremark) {
        this.tareweight = tareweight;
        this.weighremark = weighremark;
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
