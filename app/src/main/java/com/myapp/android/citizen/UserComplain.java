package com.myapp.android.citizen;

public class UserComplain {
    private String Compid,pid;

    public UserComplain(String compid, String pid) {
        Compid = compid;
        this.pid = pid;
    }
    public UserComplain(){

    }

    public String getCompid() {
        return Compid;
    }

    public void setCompid(String compid) {
        Compid = compid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}
