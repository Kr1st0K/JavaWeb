package com.example.services.mark;

import com.example.entities.Mark;
import com.example.enums.MarkType;

import java.sql.SQLException;
import java.util.List;

public interface MarkService {
    List<Mark> getByUserId(int userId) throws SQLException;

    List<Mark> getByMarkTypeFromList(List<Mark> marks, MarkType type) throws SQLException;

    void add(Mark mark) throws SQLException;
}
