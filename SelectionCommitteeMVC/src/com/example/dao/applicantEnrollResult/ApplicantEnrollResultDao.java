package com.example.dao.applicantEnrollResult;

import com.example.entities.ApplicantEnrollResult;
import com.example.enums.EducationForm;

import java.sql.SQLException;
import java.util.List;

public interface ApplicantEnrollResultDao {
    void add(ApplicantEnrollResult result) throws SQLException;

    void addList(List<ApplicantEnrollResult> results) throws SQLException;

    List<ApplicantEnrollResult> getByUserId(int userId) throws SQLException;

    List<Integer> getUserIdsByEducationForm(EducationForm from) throws SQLException;
}
