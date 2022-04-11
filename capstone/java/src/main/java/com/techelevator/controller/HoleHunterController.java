package com.techelevator.controller;

import com.techelevator.model.Address;
import com.techelevator.model.Pothole;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;

@RestController
public class HoleHunterController {

    @RequestMapping(path = "/testObject", method = RequestMethod.GET)
    public Pothole getPothole() {
        Address address = new Address(1234, "hole circle", "akron", "oh", 44444);
        //int reportId, int potholeId, int reportingUser, boolean verified, String repairStatus, String severity, Address address
        Pothole pothole = new Pothole(1001,2001,1,false,"not repaired","bad",address);
        return pothole;
    }

    @RequestMapping(path = "/newReport", method = RequestMethod.POST)
    public Pothole reportPothole(@RequestBody @Valid Pothole pothole) {
        System.out.println(pothole);
        return pothole;
    }
}