package com.example.services.university;

import com.example.entities.University;

import java.sql.SQLException;
import java.util.List;

public interface UniversityService {
    List<University> getAll() throws SQLException;

    University getById(int id) throws SQLException;

    List<University> getByIds(List<Integer> ids) throws SQLException;

    University getByName(String name) throws SQLException;
}
