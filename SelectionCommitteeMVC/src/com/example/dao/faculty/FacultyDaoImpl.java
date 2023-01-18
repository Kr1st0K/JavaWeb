package com.example.dao.faculty;

import com.example.mappers.FacultyMapper;
import com.example.entities.Faculty;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FacultyDaoImpl implements FacultyDao {
    private static FacultyDaoImpl instance;
    private final String user;
    private final String password;
    private final String url;

    private static final String ADD_FACULTY = "insert into faculties(name, all_places_count, " +
            "budget_places_count, university_id) values(?,?,?,?)";

    private static final String EDIT_FACULTY = "update faculties set name = ?, all_places_count = ?," +
            "budget_places_count = ? where id = ?";

    private static final String DELETE_FACULTY = "delete from faculties where id = ?";

    private static final String SELECT_BY_UNIVERSITY_ID = "select * from faculties where university_id = ?";
    private static final String SELECT_BY_NAME_AND_UNIVERSITY_ID = "select * from faculties " +
            "where university_id = ? and name = ?";
    private static final String SELECT_ALL = "select * from faculties";

    private static final String SELECT_BY_ID = "select * from faculties where id = ?";

    public static FacultyDaoImpl getInstance() {
        if (instance == null) {
            instance = new FacultyDaoImpl();
        }
        return instance;
    }

    private FacultyDaoImpl() {
        this.user = System.getenv("MYSQL_USERNAME");
        this.password = System.getenv("MYSQL_PASSWORD");
        this.url = "jdbc:mysql://" + System.getenv("MYSQL_HOST") + "/selection_committee";
    }

    @Override
    public void add(Faculty faculty) throws SQLException {
        try (Connection connection = DriverManager.getConnection(this.url, this.user, this.password);
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_FACULTY)) {
            preparedStatement.setString(1, faculty.getName());
            preparedStatement.setInt(2, faculty.getAllPlacesCount());
            preparedStatement.setInt(3, faculty.getBudgetPlacesCount());
            preparedStatement.setInt(4, faculty.getUniversityId());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void edit(Faculty faculty) throws SQLException {
        try (Connection connection = DriverManager.getConnection(this.url, this.user, this.password);
             PreparedStatement preparedStatement = connection.prepareStatement(EDIT_FACULTY)) {
            preparedStatement.setString(1, faculty.getName());
            preparedStatement.setInt(2, faculty.getAllPlacesCount());
            preparedStatement.setInt(3, faculty.getBudgetPlacesCount());
            preparedStatement.setInt(4, faculty.getId());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        try (Connection connection = DriverManager.getConnection(this.url, this.user, this.password);
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_FACULTY)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public List<Faculty> getByUniversityId(int university_id) throws SQLException {
        List<Faculty> faculties = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(this.url, this.user, this.password);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_UNIVERSITY_ID)) {
            preparedStatement.setInt(1, university_id);
            try (ResultSet set = preparedStatement.executeQuery()) {
                while (set.next()) {
                    faculties.add(FacultyMapper.map(set));
                }
            }
            return faculties;
        }
    }

    @Override
    public Faculty getByNameAndUniversityId(int universityId, String name) throws SQLException {
        try (Connection connection = DriverManager.getConnection(this.url, this.user, this.password);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_NAME_AND_UNIVERSITY_ID)) {
            preparedStatement.setInt(1, universityId);
            preparedStatement.setString(2, name);
            try (ResultSet set = preparedStatement.executeQuery()) {
                if (set.next()) {
                    return FacultyMapper.map(set);
                } else return null;
            }
        }
    }

    @Override
    public Faculty getById(int id) throws SQLException {
        try (Connection connection = DriverManager.getConnection(this.url, this.user, this.password);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {
            preparedStatement.setInt(1, id);
            try (ResultSet set = preparedStatement.executeQuery()) {
                if (set.next()) {
                    return FacultyMapper.map(set);
                } else return null;
            }
        }
    }

    @Override
    public List<Faculty> getAll() throws SQLException {
        List<Faculty> faculties = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(this.url, this.user, this.password);
             Statement statement = connection.createStatement();
             ResultSet set = statement.executeQuery(SELECT_ALL)) {
            while (set.next()) {
                faculties.add(FacultyMapper.map(set));
            }
            return faculties;
        }
    }
}
