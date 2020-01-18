package com.myapp.android.citizen;

public class UserFIR {
    private String Fid,pid;

    public UserFIR(String fid, String pid) {
        Fid = fid;
        this.pid = pid;
    }
    public UserFIR(){

    }

    public String getFid() {
        return Fid;
    }

    public void setFid(String fid) {
        Fid = fid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}
