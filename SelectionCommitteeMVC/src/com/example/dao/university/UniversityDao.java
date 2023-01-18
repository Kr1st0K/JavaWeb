package com.example.dao.university;

import com.example.entities.University;

import java.sql.SQLException;
import java.util.List;

public interface UniversityDao {
    University getById(int id) throws SQLException;

    University getByName(String name) throws SQLException;

    List<University> getAll() throws SQLException;
}
