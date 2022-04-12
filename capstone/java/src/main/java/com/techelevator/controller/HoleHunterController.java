package com.techelevator.controller;

import com.techelevator.dao.PotholeDao;
import com.techelevator.model.Address;
import com.techelevator.model.Pothole;
import com.techelevator.model.Report;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
public class HoleHunterController {

    PotholeDao potholeDao;

    public HoleHunterController(PotholeDao potholeDao) {
        this.potholeDao = potholeDao;
    }

    @RequestMapping(path = "/newReport", method = RequestMethod.POST)
    public Report reportPothole(@RequestBody @Valid Report report) {
        return potholeDao.create(report);
    }

    @RequestMapping(path = "/getReport", method = RequestMethod.GET)
    public Report reportPothole(@RequestParam Long reportId) {
        return potholeDao.getReport(reportId);
    }

    @RequestMapping(path = "/getAllReports", method = RequestMethod.GET)
    public List<Report> reportPothole() {
        return potholeDao.getAllReports();
    }

    //test endpoints
    @RequestMapping(path = "/testObject", method = RequestMethod.GET)
    public Report getPothole() {
        Address address = new Address(1234L, "hole circle", "akron", "oh", 44444L);
        Pothole pothole = new Pothole(1001L, false, "Not repaired", "really bad", address);
        Report report = new Report(pothole);
        return report;
    }
}