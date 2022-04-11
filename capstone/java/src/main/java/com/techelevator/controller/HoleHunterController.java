package com.techelevator.controller;

import com.techelevator.model.Address;
import com.techelevator.model.Pothole;
import com.techelevator.model.Report;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class HoleHunterController {

    @RequestMapping(path = "/testObject", method = RequestMethod.GET)
    public Report getPothole() {
        Address address = new Address(1234L, "hole circle", "akron", "oh", 44444L);
        Pothole pothole = new Pothole(1001L, false, "Not repaired", "really bad", address);
        Report report = new Report(pothole);
        return report;
    }

    @RequestMapping(path = "/newReport", method = RequestMethod.POST)
    public Report reportPothole(@RequestBody @Valid Report report) {
        System.out.println(report);
        return report;
    }
}