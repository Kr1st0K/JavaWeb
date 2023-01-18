package com.example.services.applicantAssign;

import com.example.entities.ApplicantAssign;
import com.example.entities.Faculty;

import java.sql.SQLException;
import java.util.List;

public interface ApplicantAssignService {
    void assign(ApplicantAssign assign) throws SQLException;

    List<ApplicantAssign> getByUserId(int userId) throws SQLException;

    List<Integer> getFacultyIdsFromApplicantAssigns(List<ApplicantAssign> assigns) throws SQLException;

    boolean isFacultyAssigned(Faculty faculty, int userId) throws SQLException;
}
