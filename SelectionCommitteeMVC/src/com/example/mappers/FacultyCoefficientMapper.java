package com.example.mappers;

import com.example.entities.FacultyCoefficient;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FacultyCoefficientMapper {
    public static FacultyCoefficient map(ResultSet set) throws SQLException {
        int facultyId = set.getInt(1);
        int subjectId = set.getInt(2);
        float coefficient = set.getFloat(3);
        return new FacultyCoefficient(facultyId, subjectId, coefficient);
    }
}
