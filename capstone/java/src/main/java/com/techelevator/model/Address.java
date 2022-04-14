package com.techelevator.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class Address {

    @Positive
    Long houseNumber;
    @NotBlank
    String streetName;
    @NotBlank
    String city;
    @NotBlank
    String state;
    @Positive
    Long zip;

    Coordinates coordinates;


    public Address() {
    }

    public Address(Long houseNumber, String streetName, String city, String state, Long zip) {
        this.houseNumber = houseNumber;
        this.streetName = streetName.toUpperCase();
        this.city = city.toUpperCase();
        this.state = state.toUpperCase();
        this.zip = zip;
    }

    public Long getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(Long houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName.toUpperCase();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city.toUpperCase();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state.toUpperCase();
    }

    public Long getZip() {
        return zip;
    }

    public void setZip(Long zip) {
        this.zip = zip;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    //hard-coded OH for state
    @Override
    public String toString() {
        String fixedStreetName = streetName.replace(' ','+');

        return houseNumber + "+" + fixedStreetName + "%2C" + city + "%2COH+" + zip;
    }
}
