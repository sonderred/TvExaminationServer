package com.Common;

import java.io.Serializable;

public class UserInformation implements Serializable {
    private String userName;
    private String password;
    private String realName;//真实姓名
    private String birthPlace;//出生地
    private static final long serialVersionUID = 1L;

    public UserInformation(String userName, String password, String realName, String birthPlace) {
        this.userName = userName;
        this.password = password;
        this.realName = realName;
        this.birthPlace = birthPlace;
    }
public void UserInformation() {}
    public String getUserName() {
        return userName;

    }

    public String getPassword() {
        return password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getRealName() {
        return realName;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }
}

