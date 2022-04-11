package com.techelevator.dao;


import com.techelevator.model.Report;

public interface PotholeDao {

    Report create(Report report);

    Report getReport(Long reportId);
}
