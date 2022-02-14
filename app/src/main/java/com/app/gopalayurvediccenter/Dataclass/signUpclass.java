package com.app.gopalayurvediccenter.Dataclass;


public class signUpclass {

    public String signUpemail;

    public String getSignUpName() {
        return signUpName;
    }

    public void setSignUpName(String signUpName) {
        this.signUpName = signUpName;
    }

    public String signUpName;
    public String signUppassword;
    public String username_passwordcheck;
    public String phoneNumber;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUsername_passwordcheck() {
        return username_passwordcheck;
    }

    public signUpclass() {

    }

    public void setUsername_passwordcheck(String username_passwordcheck) {
        this.username_passwordcheck = username_passwordcheck;
    }

    public String getSignUpemail() {
        return signUpemail;
    }

    public void setSignUpemail(String signUpemail) {
        this.signUpemail = signUpemail;
    }


    public String getSignUppassword() {
        return signUppassword;
    }

    public void setSignUppassword(String signUppassword) {
        this.signUppassword = signUppassword;
    }

    public signUpclass(String signUpemail, String signUpName, String signUppassword, String phoneNumber) {
        this.signUpemail = signUpemail;
        this.signUpName = signUpName;
        this.signUppassword = signUppassword;
        this.phoneNumber = phoneNumber;
    }


}
