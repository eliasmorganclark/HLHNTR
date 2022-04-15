package com.techelevator.model;

import javax.validation.Valid;
import java.time.LocalDateTime;

public abstract class Hazard {
    private Long hazardId;
    private boolean verified;
    @Valid
    private Address address;
    private HazardType hazardType;
    private String repairStatus;
    private Long reportingUser;

    private LocalDateTime reportedDT;

    private LocalDateTime inspectedDT;
    private LocalDateTime scheduledRepairDT;
    private LocalDateTime repairedDT;

    public Hazard () {}

    public Hazard(Long hazardId, boolean verified, Address address, HazardType hazardType, String repairStatus) {
        this.hazardId = hazardId;
        this.verified = verified;
        this.address = address;
        this.hazardType = hazardType;
        this.repairStatus = repairStatus;
    }

    public Long getHazardId() {
        return hazardId;
    }

    public void setHazardId(Long hazardId) {
        this.hazardId = hazardId;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public HazardType getHazardType() {
        return hazardType;
    }

    public void setHazardType(HazardType hazardType) {
        this.hazardType = hazardType;
    }

    public String getRepairStatus() {
        return repairStatus;
    }

    public void setRepairStatus(String repairStatus) {
        this.repairStatus = repairStatus;
    }

    public Long getReportingUser() {
        return reportingUser;
    }

    public void setReportingUser(Long reportingUser) {
        this.reportingUser = reportingUser;
    }

    public LocalDateTime getReportedDT() {
        return reportedDT;
    }

    public void setReportedDT(LocalDateTime reportedDT) {
        this.reportedDT = reportedDT;
    }

    public LocalDateTime getInspectedDT() {
        return inspectedDT;
    }

    public void setInspectedDT(LocalDateTime inspectedDT) {
        this.inspectedDT = inspectedDT;
    }

    public LocalDateTime getScheduledRepairDT() {
        return scheduledRepairDT;
    }

    public void setScheduledRepairDT(LocalDateTime scheduledRepairDT) {
        this.scheduledRepairDT = scheduledRepairDT;
    }

    public LocalDateTime getRepairedDT() {
        return repairedDT;
    }

    public void setRepairedDT(LocalDateTime repairedDT) {
        this.repairedDT = repairedDT;
    }

    @Override
    public String toString() {
        return "Hazard{" +
                "hazardId=" + hazardId +
                ", verified=" + verified +
                ", address=" + address +
                ", hazardType=" + hazardType +
                '}';
    }
}
