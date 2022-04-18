package com.techelevator.dao;

import com.techelevator.model.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
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
        Timestamp now = Timestamp.from(Instant.now());
        Report report = new Report(reportingUser,pothole, now);

        //sql statement if report contains a pothole if(report.getPothole()!=null) {
        String sql = "SELECT hazard_id FROM pothole WHERE house_number = ? AND street_name = ? AND city = ? AND state = ? AND zip = ?;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, reportAddress.getHouseNumber(),
                reportAddress.getStreetName(),reportAddress.getCity(), reportAddress.getState(), reportAddress.getZip());
        if(results.next()){
            hazardId = results.getLong("hazard_id");
        }

        else{
            sql = "insert into pothole (house_number, street_name, city, state, zip, latitude, longitude, verified, " +
                    "repair_status, severity, first_reported_timestamp) values (?,?,?,?,?,?,?,?,?,?,?) RETURNING hazard_id;";
            hazardId = jdbcTemplate.queryForObject(sql, Long.class, reportAddress.getHouseNumber(),
                    reportAddress.getStreetName(), reportAddress.getCity(), reportAddress.getState(),
                    reportAddress.getZip(),reportAddress.getCoordinates().getLat(),reportAddress.getCoordinates().getLng(),
                    pothole.isVerified(), pothole.getRepairStatus(), pothole.getSeverity(), report.getReportedTimestamp());
        }

        sql = "insert into report (pothole_id, user_id, reported_timestamp) values (?,?,?) returning report_id;";
        Long reportId = jdbcTemplate.queryForObject(sql,Long.class, hazardId, reportingUser, report.getReportedTimestamp());

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
        Timestamp now = Timestamp.from(Instant.now());
        Report report = new Report(reportingUser,drain,now);


        //sql statement if report contains a pothole
        String sql = "SELECT hazard_id FROM drain WHERE house_number = ? AND street_name = ? AND city = ? AND state = ? AND zip = ?;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, reportAddress.getHouseNumber(),
                reportAddress.getStreetName(),reportAddress.getCity(), reportAddress.getState(), reportAddress.getZip());
        if(results.next()){
            hazardId = results.getLong("hazard_id");
        }

        //Pothole/drain does not exist
        if (hazardId == -100L) {
            sql = "insert into drain (house_number, street_name, city, state, zip, latitude, longitude, verified, " +
                    "repair_status, is_clogged, first_reported_timestamp) values (?,?,?,?,?,?,?,?,?,?,?) RETURNING hazard_id;";
            hazardId = jdbcTemplate.queryForObject(sql, Long.class, reportAddress.getHouseNumber(),
                    reportAddress.getStreetName(), reportAddress.getCity(), reportAddress.getState(),
                    reportAddress.getZip(),reportAddress.getCoordinates().getLat(),reportAddress.getCoordinates().getLng(),
                    drain.isVerified(),drain.getRepairStatus(),drain.isClogged(), report.getReportedTimestamp());

        }

        sql = "insert into report (drain_id, user_id ,reported_timestamp) values (?,?,?) returning report_id;";
        Long reportId = jdbcTemplate.queryForObject(sql,Long.class, hazardId, reportingUser, report.getReportedTimestamp());

        report.setReportId(reportId);
        report.getDrain().setHazardId(hazardId);
        return report;
    }


    @Override
    public Report getReport(Long reportId) {
        //get report and pothole in report

        String sql = "SELECT report_id, pothole_id, drain_id, user_id, reported_timestamp FROM report WHERE report_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, reportId);
        Report report = null;
        if (results.getObject("pothole_id") != null){
            report = mapRowToReportPothole(results);
            sql = "SELECT hazard_id, house_number, street_name, city, state, zip, verified, repair_status, severity, " +
                    "first_reported_timestamp, inspected_timestamp, scheduled_repair_timestamp, repaired_timestamp " +
                    "FROM pothole where hazard_id  = ?;";
            SqlRowSet potholeResults = jdbcTemplate.queryForRowSet(sql, report.getPothole().getHazardId());
            if(potholeResults.next()) {
                report.setPothole(mapRowToPothole(potholeResults));
            }

        } else {
            report = mapRowToReportDrain(results);
            sql = "SELECT hazard_id, house_number, street_name, city, state, zip, verified, repair_status, severity, " +
                    "first_reported_timestamp, inspected_timestamp, scheduled_repair_timestamp, repaired_timestamp " +
                    "FROM drain where hazard_id  = ?;";
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

        String sql = "SELECT report_id, pothole_id, drain_id, user_id, reported_timestamp FROM report ORDER BY report;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        Report report = null;

        while(results.next()) {
            if (results.getObject("pothole_id") != null){
            report = mapRowToReportPothole(results);
            sql = "SELECT hazard_id, house_number, street_name, city, state, zip, latitude, longitude, verified, " +
                    "repair_status, severity, first_reported_timestamp, inspected_timestamp, scheduled_repair_timestamp, " +
                    "repaired_timestamp FROM pothole where hazard_id  = ?;";
            SqlRowSet potholeResults = jdbcTemplate.queryForRowSet(sql, report.getPothole().getHazardId());
            if(potholeResults.next()) {
                report.setPothole(mapRowToPothole(potholeResults));
            }
            reports.add(report);
        } else {
                report = mapRowToReportDrain(results);
                sql = "SELECT hazard_id, house_number, street_name, city, state, zip, latitude, longitude, verified, " +
                        "repair_status, is_clogged, first_reported_timestamp, inspected_timestamp, " +
                        "scheduled_repair_timestamp, repaired_timestamp  FROM drain where hazard_id  = ?;";
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
    public List<Report> getReportsByHazardId(Long hazardID) {
        List<Report> reports = new ArrayList<>();

        String sql = "SELECT report_id, pothole_id, drain_id, user_id, reported_timestamp FROM report WHERE pothole_id = ? OR drain_id = ? ORDER BY report;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, hazardID, hazardID);
        Report report = null;

        while(results.next()) {
            if (results.getObject("pothole_id") != null){
                report = mapRowToReportPothole(results);
                sql = "SELECT hazard_id, house_number, street_name, city, state, zip, latitude, longitude, verified, " +
                        "repair_status, severity, first_reported_timestamp, inspected_timestamp, scheduled_repair_timestamp, " +
                        "repaired_timestamp FROM pothole where hazard_id  = ?;";
                SqlRowSet potholeResults = jdbcTemplate.queryForRowSet(sql, report.getPothole().getHazardId());
                if(potholeResults.next()) {
                    report.setPothole(mapRowToPothole(potholeResults));
                }
                reports.add(report);
            } else {
                report = mapRowToReportDrain(results);
                sql = "SELECT hazard_id, house_number, street_name, city, state, zip, latitude, longitude, verified, " +
                        "repair_status, is_clogged, first_reported_timestamp, inspected_timestamp, " +
                        "scheduled_repair_timestamp, repaired_timestamp  FROM drain where hazard_id  = ?;";
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
    public List<Hazard> getAllHazards() {
        List<Hazard> allHazards = new ArrayList<>();
        allHazards.addAll(getAllPotholes());
        allHazards.addAll(getAllDrains());
        Collections.sort(allHazards);
        return allHazards;
    }

    @Override
    public List<Pothole> getAllPotholes() {
        List<Pothole> potholes = new ArrayList<>();
        String sql = "SELECT * FROM pothole ORDER BY state, city, street_name, house_number;";
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

        String sql = "SELECT * FROM drain  ORDER BY state, city, street_name, house_number;";
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

    @Override
    public Drain updateDrain(Drain drain) {
        drain.convertDTtoTimestamp();
        String sql = "UPDATE drain SET house_number = ?, street_name = ?, city = ?, state = ?, zip = ?, latitude = ?, longitude = ?, verified = ?, " +
                "repair_status = ?, is_clogged =?, first_reported_timestamp = ?, inspected_timestamp = ?, scheduled_repair_timestamp = ?, repaired_timestamp = ? WHERE hazard_id = ?;";
        int rowsUpdated = jdbcTemplate.update(sql, drain.getAddress().getHouseNumber(),
                drain.getAddress().getStreetName(), drain.getAddress().getCity(), drain.getAddress().getState(),
                drain.getAddress().getZip(),drain.getAddress().getCoordinates().getLat(),drain.getAddress().getCoordinates().getLng(),
                drain.isVerified(),drain.getRepairStatus(),drain.isClogged(), drain.getFirstReportedTimestamp(),
                drain.getInspectedTimestamp(), drain.getScheduledRepairTimestamp(), drain.getRepairedTimestamp(),
                drain.getHazardId());
        if(rowsUpdated == 1){
            return drain;
        }
        else{
            return null;
        }
    }

    @Override
    public Pothole updatePothole(Pothole pothole) {
        pothole.convertDTtoTimestamp();
        String sql = "UPDATE pothole SET house_number = ?, street_name = ?, city = ?, state = ?, zip = ?, latitude = ?, longitude = ?, verified = ?, " +
                "repair_status = ?, severity = ?, first_reported_timestamp = ?, inspected_timestamp = ?, scheduled_repair_timestamp = ?, repaired_timestamp = ? WHERE hazard_id = ?;";
        int rowsUpdated = jdbcTemplate.update(sql, pothole.getAddress().getHouseNumber(),
                pothole.getAddress().getStreetName(), pothole.getAddress().getCity(), pothole.getAddress().getState(),
                pothole.getAddress().getZip(),pothole.getAddress().getCoordinates().getLat(),pothole.getAddress().getCoordinates().getLng(),
                pothole.isVerified(),pothole.getRepairStatus(),pothole.getSeverity(), pothole.getFirstReportedTimestamp(),
                pothole.getInspectedTimestamp(), pothole.getScheduledRepairTimestamp(), pothole.getRepairedTimestamp(),
                pothole.getHazardId());
        if(rowsUpdated == 1){
            return pothole;
        }
        else{
            return null;
        }
    }

    @Override
    public Hazard getHazard(Long hazardId) {
        Hazard drain = getDrain(hazardId);
        Hazard pothole = getPothole(hazardId);

        if(drain == null){
            return pothole;
        }
        if(pothole == null){
            return drain;
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
        report.setReportedTimestamp(rowSet.getTimestamp("reported_timestamp"));
        report.setPothole(pothole);

        return report;
    }
    private Report mapRowToReportDrain(SqlRowSet rowSet){

        Report report = new Report();
        Drain drain = new Drain();

        drain.setHazardId(rowSet.getLong("drain_id"));
        report.setReportId(rowSet.getLong("report_id"));
        report.setReportingUser(rowSet.getLong("user_id"));
        report.setReportedTimestamp(rowSet.getTimestamp("reported_timestamp"));
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
        pothole.setFirstReportedTimestamp(rowSet.getTimestamp("first_reported_timestamp"));
        pothole.setInspectedTimestamp(rowSet.getTimestamp("inspected_timestamp"));
        pothole.setScheduledRepairTimestamp(rowSet.getTimestamp("scheduled_repair_timestamp"));
        pothole.setRepairedTimestamp(rowSet.getTimestamp("repaired_timestamp"));
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
        drain.setFirstReportedTimestamp(rowSet.getTimestamp("first_reported_timestamp"));
        drain.setInspectedTimestamp(rowSet.getTimestamp("inspected_timestamp"));
        drain.setScheduledRepairTimestamp(rowSet.getTimestamp("scheduled_repair_timestamp"));
        drain.setRepairedTimestamp(rowSet.getTimestamp("repaired_timestamp"));

        return drain;
    }

    private Address mapRowToAddress(SqlRowSet rowSet){

        Address address = new Address();
        Coordinates coordinates = new Coordinates();

        address.setHouseNumber(rowSet.getLong("house_number"));
        address.setStreetName(rowSet.getString("street_name"));
        address.setCity(rowSet.getString("city"));
        address.setState(rowSet.getString("state"));
        address.setZip(rowSet.getLong("zip"));
        coordinates.setLat(rowSet.getDouble("latitude"));
        coordinates.setLng(rowSet.getDouble("longitude"));
        address.setCoordinates(coordinates);
        return address;
    }

//    Java: LocalDateTime
//    JDBC: java.sql.Timestamp
//    PostgreSQL: TIMESTAMP WITHOUT TIMEZONE (TIMESTAMP)

}
