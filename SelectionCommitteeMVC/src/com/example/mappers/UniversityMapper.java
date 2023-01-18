package com.example.mappers;

import com.example.entities.University;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UniversityMapper {
    public static University map(ResultSet set) throws SQLException {
        int id = set.getInt(1);
        String name = set.getString(2);
        String town = set.getString(3);
        return new University(id, name, town);
    }
}
