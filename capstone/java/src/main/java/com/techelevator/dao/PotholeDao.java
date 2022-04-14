package com.techelevator.dao;


import com.techelevator.model.Report;

import java.util.List;

public interface PotholeDao {

    Report create(Report report);

    Report getReport(Long reportId);

    List<Report> getAllReports();

//    long getUserIdByUsername(String username);
}
