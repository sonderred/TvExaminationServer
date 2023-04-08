package com.Common;

import com.google.common.annotations.VisibleForTesting;

import java.io.Serializable;

public class UserInformation implements Serializable {
    private String userName;
    private String password;
    public void User() {}  //保存User信息
    private static final long serialVersionUID = 1L;

    public UserInformation(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

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
}

