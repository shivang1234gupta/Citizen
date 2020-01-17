package com.myapp.android.citizen;

import java.io.Serializable;

public class PermissionClass  implements Serializable {

    String ApplicantName ,organisation,applicantMobileNo,organizationMobileNo,
            applicantAddress,organizationAddress,startDate,endDate,type ,status,detail;
    boolean containsOrg;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isContainsOrg() {
        return containsOrg;
    }

    public void setContainsOrg(boolean containsOrg) {
        this.containsOrg = containsOrg;
    }

    static String[] permissionTypes={"Permission for Protest","Permission For Procession",
            "Permission For Event","Permission For Film Shooting"};
    static String permissionType="permissionType";

    public PermissionClass(){

    }

    public PermissionClass(String ApplicantName ,String organisation,String applicantMobileNo,String organizationMobileNo,
                           String applicantAddress,String organizationAddress,String startDate,String endDate,String type ,
                           String status,String detail){
        this.ApplicantName=ApplicantName;
        this.organisation=organisation;
        this.applicantMobileNo=applicantMobileNo;
        this.organizationMobileNo=organizationMobileNo;
        this.applicantAddress=applicantAddress;
        this.organizationAddress=organizationAddress;
        this.startDate=startDate;
        this.endDate=endDate;
        this.type=type;
        this.status=status;
        this.detail=detail;
    }

    public void setStatus1(String status) {
        this.status = status;
    }

    public void setApplicantMobileNo(String applicantMobileNo) {
        this.applicantMobileNo = applicantMobileNo;
    }

    public void setApplicantName(String applicantName) {
        ApplicantName = applicantName;
    }

    public void setApplicantAddress(String applicantAddress) {
        this.applicantAddress = applicantAddress;
    }

    public String getApplicantMobileNo() {
        return applicantMobileNo;
    }

    public String getApplicantName() {
        return ApplicantName;
    }

    public String getOrganisation() {
        return organisation;
    }

    public String getApplicantAddress() {
        return applicantAddress;
    }

    public String getOrganizationAddress() {
        return organizationAddress;
    }

    public String getOrganizationMobileNo() {
        return organizationMobileNo;
    }

    public String getDetail() {
        return detail;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    public void setOrganizationMobileNo(String organizationMobileNo) {
        this.organizationMobileNo = organizationMobileNo;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getType() {
        return type;
    }

    public String getStatus1() {
        return status;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setOrganizationAddress(String organizationAddress) {
        this.organizationAddress = organizationAddress;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setType(String type) {
        this.type = type;
    }
}
