package com.techelevator.dao;


import com.techelevator.model.Drain;
import com.techelevator.model.Pothole;
import com.techelevator.model.Report;

import java.util.List;

public interface ReportDao {

    Report create(Pothole pothole, Long reportingUser);

    Report create(Drain drain, Long reportingUser);

    Report getReport(Long reportId);

    List<Report> getAllReports();

    List<Pothole> getAllPotholes();

    Pothole getPothole(Long hazardId);

    List<Drain> getAllDrains();

    Drain getDrain(Long hazardId);

//    long getUserIdByUsername(String username);
}
