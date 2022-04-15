package com.techelevator.model;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public abstract class Hazard {
    private Long hazardId;
    private boolean verified;
    @Valid
    private Address address;
    private HazardType hazardType;
    private String repairStatus;
    private Long reportingUser;

    private Timestamp firstReportedTimestamp;

    private Timestamp inspectedTimestamp;
    private Timestamp scheduledRepairTimestamp;
    private Timestamp repairedTimestamp;

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

    public Timestamp getFirstReportedTimestamp() {
        return firstReportedTimestamp;
    }

    public void setFirstReportedTimestamp(Timestamp firstReportedTimestamp) {
        this.firstReportedTimestamp = firstReportedTimestamp;
    }

    public Timestamp getInspectedTimestamp() {
        return inspectedTimestamp;
    }

    public void setInspectedTimestamp(Timestamp inspectedTimestamp) {
        this.inspectedTimestamp = inspectedTimestamp;
    }

    public Timestamp getScheduledRepairTimestamp() {
        return scheduledRepairTimestamp;
    }

    public void setScheduledRepairTimestamp(Timestamp scheduledRepairTimestamp) {
        this.scheduledRepairTimestamp = scheduledRepairTimestamp;
    }

    public Timestamp getRepairedTimestamp() {
        return repairedTimestamp;
    }

    public void setRepairedTimestamp(Timestamp repairedTimestamp) {
        this.repairedTimestamp = repairedTimestamp;
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
