package com.techelevator.dao;

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
        return null;
    }

    @Override
    public Report getReport(Long reportId) {
        return null;
    }
}
