package com.example.mappers;

import com.example.enums.MarkType;
import com.example.entities.Mark;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MarkMapper {
    public static Mark map(ResultSet set) throws SQLException {
        int userId = set.getInt(1);
        int subjectId = set.getInt(2);
        MarkType markType = set.getString(3).equals("IEE") ? MarkType.IEE : MarkType.CERTIFICATE;
        int mark = set.getInt(4);
        return new Mark(userId, subjectId, markType, mark);
    }
}
