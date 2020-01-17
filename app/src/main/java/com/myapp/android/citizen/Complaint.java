package com.myapp.android.citizen;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Complaint {
    String applicant;
    String stationId;
    String applicantMobile;
    String applicationAdress;
    String applicantDOB;
    String applicantGender;
    String complaintType;
    String detaails;
    String dateOfReg;

    public Complaint(String applicant, String stationId, String applicantMobile, String applicationAdress,
                     String applicantDOB, String applicantGender, String complaintType, String detaails) {
        this.applicant = applicant;
        this.stationId = stationId;
        this.applicantMobile = applicantMobile;
        this.applicationAdress = applicationAdress;
        this.applicantDOB = applicantDOB;
        this.applicantGender = applicantGender;
        this.complaintType = complaintType;
        this.detaails = detaails;
        Calendar c=Calendar.getInstance();
        Date date=c.getTime();
        SimpleDateFormat df=new SimpleDateFormat("DD/MM/YYYY");
        dateOfReg=df.format(date);
    }

    public Complaint() {
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getApplicantMobile() {
        return applicantMobile;
    }

    public void setApplicantMobile(String applicantMobile) {
        this.applicantMobile = applicantMobile;
    }

    public String getApplicationAdress() {
        return applicationAdress;
    }

    public void setApplicationAdress(String applicationAdress) {
        this.applicationAdress = applicationAdress;
    }

    public String getApplicantDOB() {
        return applicantDOB;
    }

    public void setApplicantDOB(String applicantDOB) {
        this.applicantDOB = applicantDOB;
    }

    public String getApplicantGender() {
        return applicantGender;
    }

    public void setApplicantGender(String applicantGender) {
        this.applicantGender = applicantGender;
    }

    public String getComplaintType() {
        return complaintType;
    }

    public void setComplaintType(String complaintType) {
        this.complaintType = complaintType;
    }

    public String getDetaails() {
        return detaails;
    }

    public void setDetaails(String detaails) {
        this.detaails = detaails;
    }

    public String getDateOfReg() {
        return dateOfReg;
    }

    public void setDateOfReg(String dateOfReg) {
        this.dateOfReg = dateOfReg;
    }
}
