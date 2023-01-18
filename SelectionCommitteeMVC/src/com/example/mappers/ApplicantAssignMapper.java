package com.example.mappers;

import com.example.entities.ApplicantAssign;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ApplicantAssignMapper {
    public static ApplicantAssign map(ResultSet set) throws SQLException {
        int userId = set.getInt(1);
        int facultyId = set.getInt(2);
        int priority = set.getInt(3);
        return new ApplicantAssign(userId, facultyId, priority);
    }
}
