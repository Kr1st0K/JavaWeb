package com.example.mappers;

import com.example.entities.Faculty;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FacultyMapper {
    public static Faculty map(ResultSet set) throws SQLException {
        int id = set.getInt(1);
        String name = set.getString(2);
        int allPlaces = set.getInt(3);
        int budgetPlaces = set.getInt(4);
        int universityId = set.getInt(5);
        return new Faculty(id, name, allPlaces, budgetPlaces, universityId);
    }
}
