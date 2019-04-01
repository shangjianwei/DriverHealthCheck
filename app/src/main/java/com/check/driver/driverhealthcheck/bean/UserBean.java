package com.check.driver.driverhealthcheck.bean;

import org.litepal.crud.LitePalSupport;

/**
 * Created by shang on 2019/4/1.
 */

public class UserBean extends LitePalSupport {
    private int id;
    private String userName;
    private String pass;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
