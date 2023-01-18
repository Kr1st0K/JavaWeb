package com.example.mappers;

import com.example.entities.Subject;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SubjectMapper {
    public static Subject map(ResultSet set) throws SQLException {
        int id = set.getInt(1);
        String name = set.getString(2);
        return new Subject(id, name);
    }
}
