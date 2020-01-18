package com.myapp.android.citizen;

public class UserPermission {
    private String pid,per_id;

    public UserPermission(){

    }
    public UserPermission(String pid, String per_id) {
        this.pid = pid;
        this.per_id = per_id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPer_id() {
        return per_id;
    }

    public void setPer_id(String per_id) {
        this.per_id = per_id;
    }
}
