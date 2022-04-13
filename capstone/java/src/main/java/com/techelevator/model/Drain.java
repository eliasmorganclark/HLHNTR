package com.techelevator.model;

public class Drain extends Hazard {
    private boolean isClogged;

    public Drain() {}

    public Drain(Long hazardId, boolean verified, Address address, HazardType hazardType, RepairStatus repairStatus, boolean isClogged) {
        super(hazardId, verified, address, HazardType.DRAIN, repairStatus);
        this.isClogged = isClogged;
    }

    public boolean isClogged() {
        return isClogged;
    }

    public void setClogged(boolean clogged) {
        isClogged = clogged;
    }
}
