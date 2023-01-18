package com.example.dao.applicant;

import com.example.entities.Applicant;

import java.sql.SQLException;

public interface ApplicantDao {
    Applicant getByUserId(int userId) throws SQLException;

    void add(Applicant applicant) throws SQLException;
}
