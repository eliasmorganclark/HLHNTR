package com.techelevator.model;

import org.springframework.security.core.parameters.P;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

    @Override
    public String toString() {
        return "Address{" +
                "houseNumber=" + houseNumber +
                ", streetName='" + streetName + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zip=" + zip +
                '}';
    }
}
