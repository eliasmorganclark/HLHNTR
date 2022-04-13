package com.techelevator.model;

import javax.validation.Valid;

public abstract class Hazard {
    private Long hazardId;
    private boolean verified;
    @Valid
    private Address address;
    private HazardType hazardType;
    private RepairStatus repairStatus;

    public Hazard () {}

    // Remove enums from constructor, set manually?
    public Hazard(Long hazardId, boolean verified, Address address, HazardType hazardType, RepairStatus repairStatus) {
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
