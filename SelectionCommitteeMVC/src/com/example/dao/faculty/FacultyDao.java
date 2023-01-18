package com.example.dao.faculty;

import com.example.entities.Faculty;

import java.sql.SQLException;
import java.util.List;

public interface FacultyDao {
    void add(Faculty faculty) throws SQLException;

    void edit(Faculty faculty) throws SQLException;

    void delete(int id) throws SQLException;

    List<Faculty> getByUniversityId(int universityId) throws SQLException;

    Faculty getByNameAndUniversityId(int universityId, String name) throws SQLException;

    Faculty getById(int id) throws SQLException;

    List<Faculty> getAll() throws SQLException;
}
