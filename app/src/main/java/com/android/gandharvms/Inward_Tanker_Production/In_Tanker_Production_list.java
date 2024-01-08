package com.android.gandharvms.Inward_Tanker_Production;

public class In_Tanker_Production_list {

String  In_Time, Req_to_unload,Tank_Number_Request,Req_to_op_DT,confirm_unload,Tank_Number,con_unload_DT,outTime,Material,Vehicle_Number;

    public In_Tanker_Production_list() {
    }

    public In_Tanker_Production_list( String in_Time, String req_to_unload, String tank_Number_Request, String req_to_op_DT, String confirm_unload, String tank_Number, String con_unload_DT,String outTime,String Material,String Vehicle_Number) {
        this.In_Time = in_Time;
        this.Req_to_unload = req_to_unload;
       this.Tank_Number_Request = tank_Number_Request;
        this.Req_to_op_DT = req_to_op_DT;
        this.confirm_unload = confirm_unload;
        this.Tank_Number = tank_Number;
        this.con_unload_DT = con_unload_DT;
        this.outTime=outTime;
        this.Material= Material;
        this.Vehicle_Number=Vehicle_Number;
    }


    public String getIn_Time(){
        return In_Time;
    }

    public void setIn_Time(String in_Time) {
        In_Time = in_Time;
    }

    public String getReq_to_unload() {
        return Req_to_unload;
    }

    public void setReq_to_unload(String req_to_unload) {
        Req_to_unload = req_to_unload;
    }

    public String getTank_Number_Request() {
        return Tank_Number_Request;
    }

    public void setTank_Number_Request(String tank_Number_Request) {
        Tank_Number_Request = tank_Number_Request;
    }

    public String getReq_to_op_DT() {
        return Req_to_op_DT;
    }

    public void setReq_to_op_DT(String req_to_op_DT) {
        Req_to_op_DT = req_to_op_DT;
    }

    public String getConfirm_unload() {
        return confirm_unload;
    }

    public void setConfirm_unload(String confirm_unload) {
        this.confirm_unload = confirm_unload;
    }

    public String getTank_Number() {
        return Tank_Number;
    }

    public void setTank_Number(String tank_Number) {
        Tank_Number = tank_Number;
    }

    public String getCon_unload_DT() {
        return con_unload_DT;
    }

    public void setCon_unload_DT(String con_unload_DT) {
        this.con_unload_DT = con_unload_DT;
    }

    public String getOutTime() {
        return outTime;
    }

    public void setOutTime(String outTime) {
        this.outTime = outTime;
    }

    public String getMaterial(){ return Material; }

    public void setMaterial(String material){this.Material=material;}

    public String getVehicle_Number(){return Vehicle_Number;}
    public void setVehicle_Number(String vehicle_Number){ this.Vehicle_Number=vehicle_Number; }
}
