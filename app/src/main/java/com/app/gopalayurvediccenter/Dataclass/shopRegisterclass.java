package com.app.gopalayurvediccenter.Dataclass;

public class shopRegisterclass {

    public String fullname;
    public String phnumber;
    public String buyer;


    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhnumber() {
        return phnumber;
    }

    public void setPhnumber(String phnumber) {
        this.phnumber = phnumber;
    }
    
    public shopRegisterclass(String fullName, String phnumber)
    {
        this.fullname = fullName;
        this.phnumber = phnumber;

    }


}
