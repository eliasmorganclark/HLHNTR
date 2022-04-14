package com.techelevator.controller;

import com.techelevator.dao.ReportDao;
import com.techelevator.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
public class HoleHunterController {

    ReportDao reportDao;

    public HoleHunterController(ReportDao reportDao) {
        this.reportDao = reportDao;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/newPotholeReport", method = RequestMethod.POST)
    public Report reportPothole(@RequestBody @Valid Pothole pothole)  {
        return reportDao.create(pothole,pothole.getReportingUser());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/newDrainReport", method = RequestMethod.POST)
    public Report reportDrain(@RequestBody @Valid Drain drain)  {
        return reportDao.create(drain,drain.getReportingUser());
    }


    @RequestMapping(path = "/getReport", method = RequestMethod.GET)
    public Report getReportById(@RequestParam Long reportId) {
        return reportDao.getReport(reportId);
    }

    @RequestMapping(path = "/getAllReports", method = RequestMethod.GET)
    public List<Report> getAllReports() {
        return reportDao.getAllReports();
    }

    @RequestMapping(path = "/getPothole", method = RequestMethod.GET)
    public Pothole getPotholeById(@RequestParam Long hazardId) {
        return reportDao.getPothole(hazardId);
    }

    @RequestMapping(path = "/getDrain", method = RequestMethod.GET)
    public Drain getDrainById(@RequestParam Long hazardId) {
        return reportDao.getDrain(hazardId);
    }

    @RequestMapping(path = "/getAllPotholes", method = RequestMethod.GET)
    public List<Pothole> getAllPotholes() {
        return reportDao.getAllPotholes();
    }

    @RequestMapping(path = "/getAllDrains", method = RequestMethod.GET)
    public List<Drain> getAllDrains() {
        return reportDao.getAllDrains();
    }


    //test endpoints
    @RequestMapping(path = "/testReportObject", method = RequestMethod.GET)
    public Report getReportJSON() {
        Address address = new Address(1234L, "hole circle", "akron", "oh", 44444L);
        Pothole pothole = new Pothole(1001L, false, "Not repaired", address, "broken");
        Report report = new Report(pothole);
        return report;
    }

    @RequestMapping(path = "/testPotholeObject", method = RequestMethod.GET)
    public Pothole getPotholeJSON() {
        Address address = new Address(1234L, "hole circle", "akron", "oh", 44444L);
        Pothole pothole = new Pothole(1001L, false, "Not repaired", address, "broken");

        return pothole;
    }

    @RequestMapping(path = "/testDrainObject", method = RequestMethod.GET)
    public Drain getDrainJSON() {
        Address address = new Address(1234L, "hole circle", "akron", "oh", 44444L);
        Drain drain = new Drain(1001L, false, address,"Not repaired", true);

        return drain;
    }




}