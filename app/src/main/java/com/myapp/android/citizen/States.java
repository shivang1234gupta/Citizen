package com.myapp.android.citizen;
public class States {
    private int Sid;
    private String State;

    public States(int sid, String state) {
        Sid = sid;
        State = state;
    }

    public int getSid() {
        return Sid;
    }

    public void setSid(int sid) {
        Sid = sid;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }
}
