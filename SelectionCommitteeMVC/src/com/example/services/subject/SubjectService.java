package com.example.services.subject;

import com.example.entities.Mark;
import com.example.entities.Subject;

import java.sql.SQLException;
import java.util.List;

public interface SubjectService {
    List<Subject> getAll() throws SQLException;

    Subject getById(int id) throws SQLException;

    Subject getByName(String name) throws SQLException;

    List<Subject> getMissingSubjectsFromMarkList(List<Mark> marks, List<Subject> allSubjects) throws SQLException;
}
