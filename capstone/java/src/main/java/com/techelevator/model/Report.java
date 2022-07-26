package com.techelevator.model;

import javax.validation.constraints.Positive;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Report {

    private Long reportId;   //generated by DB
    @Positive
    private Long reportingUser; //ID of user who reported

    private Pothole pothole = null;
    private Drain drain = null;

    private Timestamp reportedTimestamp;


    public Report() {
    }

    public Report(Long reportingUser, Pothole pothole, Timestamp reportedTimestamp) {
        this.reportingUser = reportingUser;
        this.pothole = pothole;
        this.reportedTimestamp = reportedTimestamp;
    }

    public Report(Long reportingUser, Drain drain , Timestamp reportedTimestamp) {
        this.reportingUser = reportingUser;
        this.drain = drain;
        this.reportedTimestamp = reportedTimestamp;
    }

    public Report(Pothole pothole) {
        this.reportId = 0L;
        this.reportingUser = 0L;
        this.pothole = pothole;

    }

    public Report(Drain drain) {
        this.reportId = 0L;
        this.reportingUser = 0L;
        this.drain = drain;
    }

    public Report(Long reportId, Long reportingUser, Pothole pothole) {
        this.reportId = reportId;
        this.reportingUser = reportingUser;
        this.pothole = pothole;
    }

    public Report(Long reportId, Long reportingUser, Drain drain) {
        this.reportId = reportId;
        this.reportingUser = reportingUser;
        this.drain = drain;
    }

    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    public Long getReportingUser() {
        return reportingUser;
    }

    public void setReportingUser(Long reportingUser) {
        this.reportingUser = reportingUser;
    }

    public Pothole getPothole() {
        return pothole;
    }

    public void setPothole(Pothole pothole) {
        this.pothole = pothole;
    }

    public Drain getDrain() {
        return drain;
    }

    public void setDrain(Drain drain) {
        this.drain = drain;
    }

    public Timestamp getReportedTimestamp() {
        return reportedTimestamp;
    }

    public void setReportedTimestamp(Timestamp reportedTimestamp) {
        this.reportedTimestamp = reportedTimestamp;
    }

    @Override
    public String toString() {
        return "Report{" +
                "reportId=" + reportId +
                ", reportingUser=" + reportingUser +
                ", pothole=" + pothole +
                ", drain=" + drain +
                '}';
    }
}
