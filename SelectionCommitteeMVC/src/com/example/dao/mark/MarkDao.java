package com.example.dao.mark;

import com.example.entities.Mark;

import java.sql.SQLException;
import java.util.List;

public interface MarkDao {
    void add(Mark mark) throws SQLException;

    void addList(List<Mark> marks) throws SQLException;

    List<Mark> getByUserId(int userId) throws SQLException;
    Mark getByUserAndSubjectId(int userId, int subjectId) throws SQLException;
}
