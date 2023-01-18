package com.example.mappers;

import com.example.entities.Applicant;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ApplicantMapper {
    public static Applicant map(ResultSet set) throws SQLException {
        int userId = set.getInt(1);
        String name = set.getString(2);
        int age = set.getInt(3);
        String email = set.getString(4);
        String phoneNumber = set.getString(5);
        String town = set.getString(6);
        String region = set.getString(7);
        String schoolName = set.getString(8);
        return new Applicant(userId, name, age, email, phoneNumber, town, region, schoolName);
    }
}
