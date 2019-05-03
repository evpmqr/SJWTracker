package com.evpmqr.objects;

import java.io.Serializable;
import java.util.List;

public class Users implements Serializable{
    private List<User> userList;

    public Users(List<User> userList) {
        this.userList = userList;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
