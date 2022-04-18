package com.techelevator.model;

import java.sql.Timestamp;

public class DateAndTime {
   private String fullDate = null;
   //yyyy-mm-dd

   private String fullTime = null;
   //hh:mm

   private Timestamp timestamp;

    public DateAndTime(Timestamp timestamp) {
        this.timestamp = timestamp;
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

    public Timestamp getTimestamp() {
        translateToTimestamp();
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public void translateToStrings() {
        String timestampString = timestamp.toString();
        fullDate = timestampString.substring(0,10);
        fullTime = timestampString.substring(11,19);

//        2022-04-16T14:29:42.371+00:00
    }

    public void translateToTimestamp(){
        //yyyy-[m]m-[d]d hh:mm:ss
        if((fullDate != null) && (fullTime != null)) {
            timestamp = Timestamp.valueOf(fullDate + " " + fullTime + ":00");
        }
    }
}
