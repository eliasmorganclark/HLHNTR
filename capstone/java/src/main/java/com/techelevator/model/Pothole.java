package com.techelevator.model;

import javax.validation.Valid;

public class Pothole extends Hazard {
    private String severity; //tbd

    public Pothole() {
    }

    public Pothole(Long hazardId, boolean verified, String severity, Address address, String repairStatus) {
        super(hazardId, verified, address, HazardType.POTHOLE, repairStatus);
        this.severity = severity;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }
}
