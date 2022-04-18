package com.techelevator.controller;

import com.techelevator.dao.ReportDao;
import com.techelevator.model.*;
import com.techelevator.service.GeocodingService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
public class HoleHunterController {

    ReportDao reportDao;
    GeocodingService geocodingService = new GeocodingService();

    public HoleHunterController(ReportDao reportDao) {
        this.reportDao = reportDao;
    }

    //POST REQUESTS
        //Create pothole report
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/pothole", method = RequestMethod.POST)
    public Report reportPothole(@RequestBody @Valid Pothole pothole)  {
        pothole.getAddress().setCoordinates(geocodingService.getGeocodingResultAsCoordinates(pothole.getAddress()));
        return reportDao.create(pothole,pothole.getReportingUser());
    }

        //Create drain report
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/drain", method = RequestMethod.POST)
    public Report reportDrain(@RequestBody @Valid Drain drain)  {
        drain.getAddress().setCoordinates(geocodingService.getGeocodingResultAsCoordinates(drain.getAddress()));
        return reportDao.create(drain,drain.getReportingUser());
    }


    //GET REQUESTS
        //All reports
    @RequestMapping(path = "/report/all", method = RequestMethod.GET)
    public List<Report> getAllReports() {
        return reportDao.getAllReports();
    }

        //All reports for hazardId
    @RequestMapping(path = "/report/all/{hazardId}", method = RequestMethod.GET)
    public List<Report> getAllReportsById(@PathVariable Long hazardId) {
        return reportDao.getReportsByHazardId(hazardId);
    }

        //All hazards
    @RequestMapping(path = "/hazard/all", method = RequestMethod.GET)
    public List<Hazard> getAllHazards() {
        return reportDao.getAllHazards();
    }

        //All potholes
    @RequestMapping(path = "/pothole/all", method = RequestMethod.GET)
    public List<Pothole> getAllPotholes() {
        return reportDao.getAllPotholes();
    }

        //All drains
    @RequestMapping(path = "/drain/all", method = RequestMethod.GET)
    public List<Drain> getAllDrains() {
        return reportDao.getAllDrains();
    }

        //Report by reportId
    @RequestMapping(path = "/report/{reportId}", method = RequestMethod.GET)
    public Report getReportById(@PathVariable Long reportId) {
        return reportDao.getReport(reportId);
    }

        //Pothole by hazardId
    @RequestMapping(path = "/pothole/{hazardId}", method = RequestMethod.GET)
    public Pothole getPotholeById(@PathVariable Long hazardId) {
        return reportDao.getPothole(hazardId);
    }

        //Drain by hazardId
    @RequestMapping(path = "/drain/{hazardId}", method = RequestMethod.GET)
    public Drain getDrainById(@PathVariable Long hazardId) {
        return reportDao.getDrain(hazardId);
    }

        //Hazard by hazardId
    @RequestMapping(path = "/hazard/{hazardId}", method = RequestMethod.GET)
    public Hazard getHazard(@PathVariable Long hazardId) {
        return reportDao.getHazard(hazardId);
    }


    //PUT REQUESTS
        //Update drain
    @RequestMapping(path = "/drain", method = RequestMethod.PUT)
    public Drain updateDrain(@RequestBody @Valid Drain drain) {
        return reportDao.updateDrain(drain);
    }

        //Update pothole
    @RequestMapping(path = "/pothole", method = RequestMethod.PUT)
    public Pothole updatePothole(@RequestBody @Valid Pothole pothole) {
        return reportDao.updatePothole(pothole);
    }



    //TEST ENDPOINTS
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