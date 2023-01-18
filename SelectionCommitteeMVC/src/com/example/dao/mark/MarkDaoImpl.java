package com.example.dao.mark;

import com.example.mappers.MarkMapper;
import com.example.entities.Mark;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MarkDaoImpl implements MarkDao {
    private static MarkDaoImpl instance;
    private final String user;
    private final String password;
    private final String url;

    private static final String ADD_MARK = "insert into marks(user_id, subject_id, type, mark) values(?,?,?,?)";
    private static final String SELECT_BY_USER_ID = "select * from marks where user_id = ?";

    private static final String SELECT_BY_USER_AND_SUBJECT_ID = "select * from marks where " +
            "user_id = ? and subject_id = ?";

    public static MarkDaoImpl getInstance() {
        if (instance == null) {
            instance = new MarkDaoImpl();
        }
        return instance;
    }

    private MarkDaoImpl() {
        this.user = System.getenv("MYSQL_USERNAME");
        this.password = System.getenv("MYSQL_PASSWORD");
        this.url = "jdbc:mysql://" + System.getenv("MYSQL_HOST") + "/selection_committee";
    }

    @Override
    public void add(Mark mark) throws SQLException {
        try (Connection connection = DriverManager.getConnection(this.url, this.user, this.password);
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_MARK)) {
            preparedStatement.setInt(1, mark.getUserId());
            preparedStatement.setInt(2, mark.getSubjectId());
            preparedStatement.setString(3, mark.getType().toString());
            preparedStatement.setInt(4, mark.getMark());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void addList(List<Mark> marks) throws SQLException {
        try (Connection connection = DriverManager.getConnection(this.url, this.user, this.password);
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_MARK)) {
            for (Mark mark : marks) {
                preparedStatement.setInt(1, mark.getUserId());
                preparedStatement.setInt(2, mark.getSubjectId());
                preparedStatement.setString(3, mark.getType().toString());
                preparedStatement.setInt(4, mark.getMark());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }
    }

    @Override
    public List<Mark> getByUserId(int userId) throws SQLException {
        List<Mark> marks = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(this.url, this.user, this.password);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_USER_ID)) {
            preparedStatement.setInt(1, userId);
            try (ResultSet set = preparedStatement.executeQuery()) {
                while (set.next()) {
                    marks.add(MarkMapper.map(set));
                }
            }
            return marks;
        }
    }

    @Override
    public Mark getByUserAndSubjectId(int userId, int subjectId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(this.url, this.user, this.password);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_USER_AND_SUBJECT_ID)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, subjectId);
            try (ResultSet set = preparedStatement.executeQuery()) {
                if (set.next()) {
                    return MarkMapper.map(set);
                } else return null;
            }
        }
    }
}
