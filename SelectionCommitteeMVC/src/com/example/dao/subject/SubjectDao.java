package com.example.dao.subject;

import com.example.entities.Subject;

import java.sql.SQLException;
import java.util.List;

public interface SubjectDao {
    Subject getById(int id) throws SQLException;

    Subject getByName(String name) throws SQLException;

    List<Subject> getAll() throws SQLException;
}
