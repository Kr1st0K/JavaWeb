package com.example.dao.facultyCoefficient;

import com.example.mappers.FacultyCoefficientMapper;
import com.example.entities.FacultyCoefficient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FacultyCoefficientDaoImpl implements FacultyCoefficientDao {
    private static FacultyCoefficientDaoImpl instance;
    private final String user;
    private final String password;
    private final String url;

    private static final String ADD_FACULTY_COEFFICIENTS = "insert into faculties_coefficients" +
            "(faculty_id, subject_id, coefficient) values(?,?,?)";

    private static final String DELETE_FACULTY_COEFFICIENTS_BY_FACULTY_ID = "delete from faculties_coefficients where " +
            "faculty_id = ?";

    private static final String SELECT_BY_FACULTY_ID = "select * from faculties_coefficients where faculty_id = ?";

    private static final String SELECT_BY_FACULTY_AND_SUBJECT_ID = "select * from faculties_coefficients where " +
            "faculty_id = ? and subject_id = ?";

    public static FacultyCoefficientDaoImpl getInstance() {
        if (instance == null) {
            instance = new FacultyCoefficientDaoImpl();
        }
        return instance;
    }

    private FacultyCoefficientDaoImpl() {
        this.user = System.getenv("MYSQL_USERNAME");
        this.password = System.getenv("MYSQL_PASSWORD");
        this.url = "jdbc:mysql://" + System.getenv("MYSQL_HOST") + "/selection_committee";
    }

    @Override
    public void addCoefficients(List<FacultyCoefficient> coefficients) throws SQLException {
        try (Connection connection = DriverManager.getConnection(this.url, this.user, this.password);
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_FACULTY_COEFFICIENTS)) {
            for (FacultyCoefficient coefficient : coefficients) {
                preparedStatement.setInt(1, coefficient.getFacultyId());
                preparedStatement.setInt(2, coefficient.getSubjectId());
                preparedStatement.setFloat(3, coefficient.getCoefficient());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }
    }

    @Override
    public void deleteCoefficientsByFacultyId(int facultyId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(this.url, this.user, this.password);
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_FACULTY_COEFFICIENTS_BY_FACULTY_ID)) {
            preparedStatement.setInt(1, facultyId);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public List<FacultyCoefficient> getByFacultyId(int facultyId) throws SQLException {
        List<FacultyCoefficient> facultyCoefficients = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(this.url, this.user, this.password);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_FACULTY_ID)) {
            preparedStatement.setInt(1, facultyId);
            try (ResultSet set = preparedStatement.executeQuery()) {
                while (set.next()) {
                    facultyCoefficients.add(FacultyCoefficientMapper.map(set));
                }
            }
            return facultyCoefficients;
        }
    }

    @Override
    public FacultyCoefficient getByFacultyAndSubjectId(int facultyId, int subjectId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(this.url, this.user, this.password);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_FACULTY_AND_SUBJECT_ID)) {
            preparedStatement.setInt(1, facultyId);
            preparedStatement.setInt(2, subjectId);
            try (ResultSet set = preparedStatement.executeQuery()) {
                if (set.next()) {
                    return FacultyCoefficientMapper.map(set);
                } else return null;
            }
        }
    }
}
