package com.example.dao.applicantAssign;

import com.example.entities.ApplicantAssign;

import java.sql.SQLException;
import java.util.List;

public interface ApplicantAssignDao {
    void add(ApplicantAssign applicantAssign) throws SQLException;

    void addList(List<ApplicantAssign> applicantAssigns) throws SQLException;

    List<ApplicantAssign> getByUserId(int userId) throws SQLException;

    List<ApplicantAssign> getByFacultyId(int facultyId) throws SQLException;
}
