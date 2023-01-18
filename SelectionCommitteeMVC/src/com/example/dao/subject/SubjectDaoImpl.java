package com.example.dao.subject;

import com.example.mappers.SubjectMapper;
import com.example.entities.Subject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubjectDaoImpl implements SubjectDao {
    private static SubjectDaoImpl instance;
    private final String user;
    private final String password;
    private final String url;

    private static final String SELECT_BY_ID = "select * from subjects where id = ?";
    private static final String SELECT_BY_NAME = "select * from subjects where name = ?";
    private static final String SELECT_ALL = "select * from subjects";

    public static SubjectDaoImpl getInstance(){
        if (instance == null) {
            instance = new SubjectDaoImpl();
        }
        return instance;
    }

    private SubjectDaoImpl() {
        this.user = System.getenv("MYSQL_USERNAME");
        this.password = System.getenv("MYSQL_PASSWORD");
        this.url = "jdbc:mysql://" + System.getenv("MYSQL_HOST") + "/selection_committee";
    }

    @Override
    public Subject getById(int id) throws SQLException {
        try (Connection connection = DriverManager.getConnection(this.url, this.user, this.password);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {
            preparedStatement.setInt(1, id);
            try (ResultSet set = preparedStatement.executeQuery()) {
                if (set.next()) {
                    return SubjectMapper.map(set);
                } else return null;
            }
        }
    }

    @Override
    public Subject getByName(String name) throws SQLException {
        try (Connection connection = DriverManager.getConnection(this.url, this.user, this.password);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_NAME)) {
            preparedStatement.setString(1, name);
            try (ResultSet set = preparedStatement.executeQuery()) {
                if (set.next()) {
                    return SubjectMapper.map(set);
                } else return null;
            }
        }
    }

    @Override
    public List<Subject> getAll() throws SQLException {
        List<Subject> subjects = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(this.url, this.user, this.password);
             Statement statement = connection.createStatement();
             ResultSet set = statement.executeQuery(SELECT_ALL)) {
            while (set.next()) {
                subjects.add(SubjectMapper.map(set));
            }
            return subjects;
        }
    }
}
