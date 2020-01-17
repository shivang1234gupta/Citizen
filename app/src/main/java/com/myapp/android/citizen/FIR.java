package com.myapp.android.citizen;

public class FIR {
    private String ApplicantName, Dob, ApplicantAddress, ApplicantPermanentAddress, ApplicantGender, status, ApplicantMobileNo,
            IncindentDate, FirDetails;


    public FIR() {

    }

    public FIR(String applicantName, String dob, String applicantAddress, String
            applicantPermanentAddress, String ApplicantGender, String Status,
               String applicantMobileNo, String incindentDate, String firDetails) {

        this.ApplicantName = applicantName;
        this.Dob = dob;
        this.ApplicantAddress = applicantAddress;
        this.ApplicantPermanentAddress = applicantPermanentAddress;
        this.ApplicantGender = ApplicantGender;
        this.status = Status;
        this.ApplicantMobileNo = applicantMobileNo;
        this.IncindentDate = incindentDate;
        this.FirDetails = firDetails;
    }

    public String getApplicantName() {
        return ApplicantName;
    }

    public String getDob() {
        return Dob;
    }

    public String getApplicantAddress() {
        return ApplicantAddress;
    }

    public String getApplicantPermanentAddress() {
        return ApplicantPermanentAddress;
    }

    public String getApplicantMobileNo() {
        return ApplicantMobileNo;
    }

    public String getIncindentDate() {
        return IncindentDate;
    }

    public String getFirDetails() {
        return FirDetails;
    }

    public String getApplicantGender() {
        return ApplicantGender;
    }

    public String getStatus() {
        return status;
    }

    public void setApplicantAddress(String applicantAddress) {
        ApplicantAddress = applicantAddress;
    }

    public void setApplicantName(String applicantName) {
        ApplicantName = applicantName;
    }

    public void setApplicantGender(String applicantGender) {
        ApplicantGender = applicantGender;
    }

    public void setDob(String dob) {
        Dob = dob;
    }

    public void setApplicantMobileNo(String applicantMobileNo) {
        ApplicantMobileNo = applicantMobileNo;
    }

    public void setApplicantPermanentAddress(String applicantPermanentAddress) {
        ApplicantPermanentAddress = applicantPermanentAddress;
    }

    public void setFirDetails(String firDetails) {
        FirDetails = firDetails;
    }

    public void setIncindentDate(String incindentDate) {
        IncindentDate = incindentDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}


