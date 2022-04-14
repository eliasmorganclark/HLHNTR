package com.techelevator.dao;

import com.techelevator.model.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcReportDao implements ReportDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcReportDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Report create(Pothole pothole, Long reportingUser) {
        //first we need to create a pothole in the DB **if it doesn't already exist**
            //after pothole is created, create new report with this new pothole_id
        //if pothole exists already, create a new report with existing pothole_id
        Long hazardId=-100L;
        //first check DB for matching address in an existing pothole
        Address reportAddress = pothole.getAddress();
        Report report = new Report(reportingUser,pothole);

        //sql statement if report contains a potholeif(report.getPothole()!=null) {
        String sql = "SELECT hazard_id FROM pothole WHERE house_number = ? AND street_name = ? AND city = ? AND state = ? AND zip = ?;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, reportAddress.getHouseNumber(),
                reportAddress.getStreetName(),reportAddress.getCity(), reportAddress.getState(), reportAddress.getZip());
        if(results.next()){
            hazardId = results.getLong("hazard_id");
        }

        else{
            sql = "insert into pothole (house_number, street_name, city, state, zip, verified, repair_status, severity) values (?,?,?,?,?,?,?,?) RETURNING hazard_id;";
            hazardId = jdbcTemplate.queryForObject(sql, Long.class, reportAddress.getHouseNumber(),
                    reportAddress.getStreetName(), reportAddress.getCity(), reportAddress.getState(),
                    reportAddress.getZip(), pothole.isVerified(), pothole.getRepairStatus(), pothole.getSeverity());
        }

        sql = "insert into report (pothole_id, user_id) values (?,?) returning report_id;";
        Long reportId = jdbcTemplate.queryForObject(sql,Long.class, hazardId, reportingUser);

        report.setReportId(reportId);
        report.getPothole().setHazardId(hazardId);
        return report;
    }

    @Override
    public Report create(Drain drain, Long reportingUser) {
        //first we need to create a pothole in the DB **if it doesn't already exist**
        //after pothole is created, create new report with this new pothole_id
        //if pothole exists already, create a new report with existing pothole_id
        Long hazardId=-100L;
        //first check DB for matching address in an existing pothole
        Address reportAddress = drain.getAddress();
        Report report = new Report(reportingUser,drain);


        //sql statement if report contains a pothole
        String sql = "SELECT hazard_id FROM drain WHERE house_number = ? AND street_name = ? AND city = ? AND state = ? AND zip = ?;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, reportAddress.getHouseNumber(),
                reportAddress.getStreetName(),reportAddress.getCity(), reportAddress.getState(), reportAddress.getZip());
        if(results.next()){
            hazardId = results.getLong("hazard_id");
        }

        //Pothole/drain does not exist
        if (hazardId == -100L) {
            sql = "insert into drain (house_number, street_name, city, state, zip, verified, repair_status, is_clogged) values (?,?,?,?,?,?,?,?) RETURNING hazard_id;";
            hazardId = jdbcTemplate.queryForObject(sql, Long.class, reportAddress.getHouseNumber(),
                    reportAddress.getStreetName(), reportAddress.getCity(), reportAddress.getState(),
                    reportAddress.getZip(), drain.isVerified(), drain.getRepairStatus(), drain.isClogged());

        }

        sql = "insert into report (drain_id, user_id) values (?,?) returning report_id;";
        Long reportId = jdbcTemplate.queryForObject(sql,Long.class, hazardId, reportingUser);

        report.setReportId(reportId);
        report.getDrain().setHazardId(hazardId);
        return report;
    }


    @Override
    public Report getReport(Long reportId) {
        //get report and pothole in report

        String sql = "SELECT report_id, pothole_id, drain_id, user_id FROM report WHERE report_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, reportId);
        Report report = null;
        if (results.getObject("pothole_id") != null){
            report = mapRowToReportPothole(results);
            sql = "SELECT hazard_id, house_number, street_name, city, state, zip, verified, repair_status, severity FROM pothole where hazard_id  = ?;";
            SqlRowSet potholeResults = jdbcTemplate.queryForRowSet(sql, report.getPothole().getHazardId());
            if(potholeResults.next()) {
                report.setPothole(mapRowToPothole(potholeResults));
            }

        } else {
            report = mapRowToReportDrain(results);
            sql = "SELECT hazard_id, house_number, street_name, city, state, zip, verified, repair_status, is_clogged FROM drain where hazard_id  = ?;";
            SqlRowSet drainResults = jdbcTemplate.queryForRowSet(sql, report.getDrain().getHazardId());
            if(drainResults.next()) {
                report.setDrain(mapRowToDrain(drainResults));
            }

        }return report;

    }

    @Override
    public List<Report> getAllReports() {
        //get report and pothole in report
        List<Report> reports = new ArrayList<>();

        String sql = "SELECT report_id, pothole_id, drain_id, user_id FROM report;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        Report report = null;

        while(results.next()) {
            if (results.getObject("pothole_id") != null){
            report = mapRowToReportPothole(results);
            sql = "SELECT hazard_id, house_number, street_name, city, state, zip, verified, repair_status, severity FROM pothole where hazard_id  = ?;";
            SqlRowSet potholeResults = jdbcTemplate.queryForRowSet(sql, report.getPothole().getHazardId());
            if(potholeResults.next()) {
                report.setPothole(mapRowToPothole(potholeResults));
            }
            reports.add(report);
        } else {
                report = mapRowToReportDrain(results);
                sql = "SELECT hazard_id, house_number, street_name, city, state, zip, verified, repair_status, is_clogged FROM drain where hazard_id  = ?;";
                SqlRowSet drainResults = jdbcTemplate.queryForRowSet(sql, report.getDrain().getHazardId());
                if(drainResults.next()) {
                    report.setDrain(mapRowToDrain(drainResults));
                }
                reports.add(report);
            }

            }
        return reports;

    }

    @Override
    public List<Pothole> getAllPotholes() {
        List<Pothole> potholes = new ArrayList<>();
        String sql = "SELECT * FROM pothole";
        SqlRowSet results =  jdbcTemplate.queryForRowSet(sql);
        while (results.next()){
            potholes.add(mapRowToPothole(results));
        }
        return potholes;
    }

    @Override
    public Pothole getPothole(Long hazardId) {

        String sql = "SELECT * FROM pothole WHERE hazard_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, hazardId);
        if (results.next()) {
            return mapRowToPothole(results);

        }
        return null;
    }

    @Override
    public List<Drain> getAllDrains() {
        List<Drain> drains = new ArrayList<>();

        String sql = "SELECT * FROM drain";
        SqlRowSet results =  jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            drains.add(mapRowToDrain(results));
        }
        return drains;
    }

    @Override
    public Drain getDrain(Long hazardId) {
        String sql = "SELECT * FROM drain WHERE hazard_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, hazardId);
        if (results.next()) {
            return mapRowToDrain(results);

        }
        return null;
    }
//
//    @Override
//    public long getUserIdByUsername(String username) {
//        String sql = "SELECT user_id FROM users WHERE username = ?;";
//        Long results = jdbcTemplate.queryForObject(sql, Long.class, username);
//        return results;
//    }

    private Report mapRowToReportPothole(SqlRowSet rowSet){

        Report report = new Report();
        Pothole pothole = new Pothole();

        pothole.setHazardId(rowSet.getLong("pothole_id"));
        report.setReportId(rowSet.getLong("report_id"));
        report.setReportingUser(rowSet.getLong("user_id"));
        report.setPothole(pothole);

        return report;
    }
    private Report mapRowToReportDrain(SqlRowSet rowSet){

        Report report = new Report();
        Drain drain = new Drain();

        drain.setHazardId(rowSet.getLong("drain_id"));
        report.setReportId(rowSet.getLong("report_id"));
        report.setReportingUser(rowSet.getLong("user_id"));
        report.setDrain(drain);

        return report;
    }

    private Pothole mapRowToPothole(SqlRowSet rowSet){

        Pothole pothole = new Pothole();

        pothole.setHazardType(HazardType.POTHOLE);
        pothole.setHazardId(rowSet.getLong("hazard_id"));
        pothole.setAddress(mapRowToAddress(rowSet));
        pothole.setVerified(rowSet.getBoolean("verified"));
        pothole.setRepairStatus(rowSet.getString("repair_status"));
        pothole.setSeverity(rowSet.getString("severity"));

        return pothole;
    }
    private Drain mapRowToDrain(SqlRowSet rowSet){

        Drain drain = new Drain();
        drain.setHazardType(HazardType.DRAIN);
        drain.setHazardId(rowSet.getLong("hazard_id"));
        drain.setAddress(mapRowToAddress(rowSet));
        drain.setVerified(rowSet.getBoolean("verified"));
        drain.setRepairStatus(rowSet.getString("repair_status"));
        drain.setClogged(rowSet.getBoolean("is_clogged"));

        return drain;
    }

    private Address mapRowToAddress(SqlRowSet rowSet){

        Address address = new Address();

        address.setHouseNumber(rowSet.getLong("house_number"));
        address.setStreetName(rowSet.getString("street_name"));
        address.setCity(rowSet.getString("city"));
        address.setState(rowSet.getString("state"));
        address.setZip(rowSet.getLong("zip"));

        return address;
    }

}
