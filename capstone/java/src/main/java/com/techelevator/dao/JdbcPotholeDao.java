package com.techelevator.dao;

import com.techelevator.model.Address;
import com.techelevator.model.Report;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import javax.sql.DataSource;

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

        System.out.println("pothole id: " + potholeId);
        System.out.println("report id: " + reportId);
        report.setReportId(reportId);
        report.getPothole().setPotholeId(potholeId);
        return report;
    }

    @Override
    public Report getReport(Long reportId) {
        return null;
    }
}
