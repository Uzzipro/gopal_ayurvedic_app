package com.app.gopalayurvediccenter.Dataclass;

public class sellerRegisterclass {

    public String fullNameclass;
    public String phNumberclass;
    public String typeProductclass;
    public String liveLocationclass;


    public String getFullNameclass() {
        return fullNameclass;
    }

    public void setFullNameclass(String fullNameclass) {
        this.fullNameclass = fullNameclass;
    }

    public String getPhNumberclass() {
        return phNumberclass;
    }

    public void setPhNumberclass(String phNumberclass) {
        this.phNumberclass = phNumberclass;
    }

    public String getTypeProductclass() {
        return typeProductclass;
    }

    public void setTypeProductclass(String typeProductclass) {
        this.typeProductclass = typeProductclass;
    }

    public String getLiveLocationclass() {
        return liveLocationclass;
    }

    public void setLiveLocationclass(String liveLocationclass) {
        this.liveLocationclass = liveLocationclass;
    }

    public sellerRegisterclass(String fullNameclassmeth, String phNumberclassmeth, String typeProductclassmeth, String liveLocationclassmeth)
    {
        this.fullNameclass = fullNameclassmeth;
        this.phNumberclass = phNumberclassmeth;
        this.typeProductclass = typeProductclassmeth;
        this.liveLocationclass = liveLocationclassmeth;

    }


    public sellerRegisterclass()
    {

    }



}
