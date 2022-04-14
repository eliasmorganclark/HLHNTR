package com.techelevator.dao;

import com.techelevator.model.Address;
import com.techelevator.model.Pothole;
import com.techelevator.model.Report;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcPotholeDao implements PotholeDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcPotholeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Report create(Report report) {
        //first we need to create a pothole in the DB **if it doesn't already exist**
            //after pothole is created, create new report with this new pothole_id
        //if pothole exists already, create a new report with existing pothole_id
        Long potholeId=-100L;
        //first check DB for matching address in an existing pothole
        Address reportAddress = report.getPothole().getAddress();

        String sql = "SELECT pothole_id FROM pothole WHERE house_number = ? AND street_name = ? AND city = ? AND state = ? AND zip = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, reportAddress.getHouseNumber(),
                reportAddress.getStreetName(),reportAddress.getCity(), reportAddress.getState(), reportAddress.getZip());
        if(results.next()){
            potholeId = results.getLong("pothole_id");
        }

        //Pothole does not exist
        if(potholeId==-100L){
            sql = "insert into pothole (house_number, street_name, city, state, zip, verified) values (?,?,?,?,?,?) RETURNING pothole_id;";
            potholeId = jdbcTemplate.queryForObject(sql,Long.class, reportAddress.getHouseNumber(),
                    reportAddress.getStreetName(),reportAddress.getCity(), reportAddress.getState(),
                    reportAddress.getZip(), report.getPothole().isVerified());
        }

        sql = "insert into report (pothole_id, user_id) values (?,?) returning report_id;";
        Long reportId = jdbcTemplate.queryForObject(sql,Long.class, potholeId, report.getReportingUser());

        report.setReportId(reportId);
        report.getPothole().setHazardId(potholeId);
        return report;
    }

    @Override
    public Report getReport(Long reportId) {
        //get report and pothole in report

        String sql = "SELECT report_id, pothole_id, user_id FROM report WHERE report_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, reportId);
        Report report = null;
        if(results.next()) {
            report = mapRowToReport(results);
            sql = "SELECT pothole_id, house_number, street_name, city, state, zip, verified, repair_status, severity FROM pothole where pothole_id  = ?;";
            results = jdbcTemplate.queryForRowSet(sql, report.getPothole().getHazardId());
            if(results.next()) {
                report.setPothole(mapRowToPothole(results));
            }
        }
        return report;

    }

    @Override
    public List<Report> getAllReports() {
        //get report and pothole in report
        List<Report> reports = new ArrayList<>();

        String sql = "SELECT report_id, pothole_id, user_id FROM report;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        Report report = null;

        while(results.next()) {
            report = mapRowToReport(results);
            sql = "SELECT pothole_id, house_number, street_name, city, state, zip, verified, repair_status, severity FROM pothole where pothole_id  = ?;";
            SqlRowSet potholeResults = jdbcTemplate.queryForRowSet(sql, report.getPothole().getHazardId());
            if(potholeResults.next()) {
                report.setPothole(mapRowToPothole(potholeResults));
            }
            reports.add(report);
        }
        return reports;

    }
//
//    @Override
//    public long getUserIdByUsername(String username) {
//        String sql = "SELECT user_id FROM users WHERE username = ?;";
//        Long results = jdbcTemplate.queryForObject(sql, Long.class, username);
//        return results;
//    }

    private Report mapRowToReport(SqlRowSet rowSet){

        Report report = new Report();
        Pothole pothole = new Pothole();

        pothole.setHazardId(rowSet.getLong("pothole_id"));
        report.setReportId(rowSet.getLong("report_id"));
        report.setReportingUser(rowSet.getLong("user_id"));
        report.setPothole(pothole);

        return report;
    }

    private Pothole mapRowToPothole(SqlRowSet rowSet){

        Pothole pothole = new Pothole();

        pothole.setHazardId(rowSet.getLong("pothole_id"));
        pothole.setAddress(mapRowToAddress(rowSet));
        pothole.setVerified(rowSet.getBoolean("verified"));
        // pothole.setRepairStatus(rowSet.getString("repair_status"));
        pothole.setSeverity(rowSet.getString("severity"));

        return pothole;
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
