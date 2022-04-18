package com.techelevator.model;

import java.sql.Timestamp;
import java.time.Instant;

public class DateAndTime {
    private String fullDate = null;
    //yyyy-mm-dd

    private String fullTime = null;
    //hh:mm

    private Timestamp nestedTimestamp;

    public DateAndTime() {
    }

    public DateAndTime(Timestamp nestedTimestamp) {
        this.nestedTimestamp = nestedTimestamp;
        translateToStrings();
    }

    public String getFullDate() {
        return fullDate;
    }

    public void setFullDate(String fullDate) {
        this.fullDate = fullDate;
    }

    public String getFullTime() {
        return fullTime;
    }

    public void setFullTime(String fullTime) {
        this.fullTime = fullTime;
    }

    public Timestamp getNestedTimestamp() {
        translateToTimestamp();
        return nestedTimestamp;
    }

    public void setNestedTimestamp(Timestamp nestedTimestamp) {
        this.nestedTimestamp = nestedTimestamp;
    }

    public void translateToStrings() {
        if (nestedTimestamp != null) {
            String timestampString = nestedTimestamp.toString();
            fullDate = timestampString.substring(0, 10);
            fullTime = timestampString.substring(11, 19);

        }
    }

    public void translateToTimestamp() {
        if ((fullDate != null) && (fullTime != null)) {
            if(fullTime.length()==8){
                nestedTimestamp = Timestamp.valueOf(fullDate + " " + fullTime);
            }
            else{
                nestedTimestamp = Timestamp.valueOf(fullDate + " " + fullTime + ":00");
            }
        }
    }

    @Override
    public String toString() {
        return "DateAndTime{" +
                "fullDate='" + fullDate + '\'' +
                ", fullTime='" + fullTime + '\'' +
                ", timestamp=" + nestedTimestamp +
                '}';
    }
}
