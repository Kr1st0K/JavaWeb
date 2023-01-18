package com.example.services.applicantEnrollResult;

import com.example.entities.ApplicantEnrollResult;

import java.sql.SQLException;
import java.util.List;

public interface ApplicantEnrollResultService {
    void addList(List<ApplicantEnrollResult> results) throws SQLException;

    List<ApplicantEnrollResult> getFinalizedResults(int facultyId) throws SQLException;

    List<ApplicantEnrollResult> getByUserId(int userId) throws SQLException;
}
