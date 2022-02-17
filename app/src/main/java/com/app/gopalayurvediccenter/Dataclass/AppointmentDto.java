package com.app.gopalayurvediccenter.Dataclass;

public class AppointmentDto {
    private String time;
    private String date;
    private String fullName;
    private String contactNumber;

    public AppointmentDto(String date, String time, String fullName, String contactNumber)
    {
        this.date = date;
        this.time = time;
        this.fullName = fullName;
        this.contactNumber = contactNumber;
    }

    public String getTime() {
        return time;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public AppointmentDto()
    {

    }
}
