package com.techelevator.model;

import javax.validation.Valid;

public class Pothole {

    Long potholeId;  //generated by DB
    boolean verified;   //true/false
    String repairStatus; //not-repaired, inspected, scheduled for repair, repaired
    String severity; //tbd

    @Valid
    Address address;

    public Pothole() {
    }

    public Pothole(Long potholeId, boolean verified, String repairStatus, String severity, Address address) {
        this.potholeId = potholeId;
        this.verified = verified;
        this.repairStatus = repairStatus;
        this.severity = severity;
        this.address = address;
    }

    public Long getPotholeId() {
        return potholeId;
    }

    public void setPotholeId(Long potholeId) {
        this.potholeId = potholeId;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String getRepairStatus() {
        return repairStatus;
    }

    public void setRepairStatus(String repairStatus) {
        this.repairStatus = repairStatus;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Pothole{" +
                "potholeId=" + potholeId +
                ", verified=" + verified +
                ", repairStatus='" + repairStatus + '\'' +
                ", severity='" + severity + '\'' +
                ", address=" + address +
                '}';
    }
}