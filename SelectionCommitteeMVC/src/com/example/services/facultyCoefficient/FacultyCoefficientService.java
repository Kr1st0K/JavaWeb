package com.example.services.facultyCoefficient;

import com.example.entities.FacultyCoefficient;

import java.sql.SQLException;
import java.util.List;

public interface FacultyCoefficientService {
    void addList(List<FacultyCoefficient> coefficients) throws SQLException;
    void deleteByFacultyId(int facultyId) throws SQLException;

    List<FacultyCoefficient> getByFacultyId(int facultyId) throws SQLException;
}
