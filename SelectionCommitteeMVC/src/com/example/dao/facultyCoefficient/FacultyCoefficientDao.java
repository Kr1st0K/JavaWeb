package com.example.dao.facultyCoefficient;

import com.example.entities.FacultyCoefficient;

import java.sql.SQLException;
import java.util.List;

public interface FacultyCoefficientDao {
    void addCoefficients(List<FacultyCoefficient> coefficients) throws SQLException;

    void deleteCoefficientsByFacultyId(int facultyId) throws SQLException;

    List<FacultyCoefficient> getByFacultyId(int facultyId) throws SQLException;

    FacultyCoefficient getByFacultyAndSubjectId(int facultyId, int subjectId) throws SQLException;
}
