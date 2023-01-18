package com.example.dao.university;

import com.example.mappers.UniversityMapper;
import com.example.entities.University;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UniversityDaoImpl implements UniversityDao {
    private static UniversityDaoImpl instance;
    private final String user;
    private final String password;
    private final String url;

    private static final String SELECT_BY_ID = "select * from universities where id = ?";
    private static final String SELECT_BY_NAME = "select * from universities where name = ?";
    private static final String SELECT_ALL = "select * from universities";

    public static UniversityDaoImpl getInstance() {
        if (instance == null) {
            instance = new UniversityDaoImpl();
        }
        return instance;
    }

    private UniversityDaoImpl() {
        this.user = System.getenv("MYSQL_USERNAME");
        this.password = System.getenv("MYSQL_PASSWORD");
        this.url = "jdbc:mysql://" + System.getenv("MYSQL_HOST") + "/selection_committee";
    }

    @Override
    public University getById(int id) throws SQLException {
        try (Connection connection = DriverManager.getConnection(this.url, this.user, this.password);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {
            preparedStatement.setInt(1, id);
            try (ResultSet set = preparedStatement.executeQuery()) {
                if (set.next()) {
                    return UniversityMapper.map(set);
                } else return null;
            }
        }
    }

    @Override
    public University getByName(String name) throws SQLException {
        try (Connection connection = DriverManager.getConnection(this.url, this.user, this.password);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_NAME)) {
            preparedStatement.setString(1, name);
            try (ResultSet set = preparedStatement.executeQuery()) {
                if (set.next()) {
                    return UniversityMapper.map(set);
                } else return null;
            }
        }
    }

    @Override
    public List<University> getAll() throws SQLException {
        List<University> universities = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(this.url, this.user, this.password);
             Statement statement = connection.createStatement();
             ResultSet set = statement.executeQuery(SELECT_ALL)) {
            while (set.next()) {
                universities.add(UniversityMapper.map(set));
            }
            return universities;
        }
    }
}
