package com.example.dao.applicant;

import com.example.mappers.ApplicantMapper;
import com.example.entities.Applicant;

import java.sql.*;

public class ApplicantDaoImpl implements ApplicantDao {
    private static ApplicantDaoImpl instance;
    private final String user;
    private final String password;
    private final String url;

    private static final String ADD_APPLICANT = "insert into applicants(user_id, name, age, email, " +
            "phone_number, town, region, school_name)  values (?,?,?,?,?,?,?,?)";

    private static final String SELECT_BY_USER_ID = "select * from applicants where user_id = ?";

    private ApplicantDaoImpl() {
        this.user = System.getenv("MYSQL_USERNAME");
        this.password = System.getenv("MYSQL_PASSWORD");
        this.url = "jdbc:mysql://" + System.getenv("MYSQL_HOST") + "/selection_committee";
    }

    public static ApplicantDaoImpl getInstance() {
        if (instance == null) {
            instance = new ApplicantDaoImpl();
        }
        return instance;
    }

    @Override
    public Applicant getByUserId(int userId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(this.url, this.user, this.password);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_USER_ID)) {
            preparedStatement.setInt(1, userId);
            try (ResultSet set = preparedStatement.executeQuery()) {
                if (set.next()) {
                    return ApplicantMapper.map(set);
                } else return null;
            }
        }
    }

    @Override
    public void add(Applicant applicant) throws SQLException {
        try (Connection connection = DriverManager.getConnection(this.url, this.user, this.password);
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_APPLICANT)) {
            preparedStatement.setInt(1, applicant.getUserId());
            preparedStatement.setString(2, applicant.getName());
            preparedStatement.setInt(3, applicant.getAge());
            preparedStatement.setString(4, applicant.getEmail());
            preparedStatement.setString(5, applicant.getPhoneNumber());
            preparedStatement.setString(6, applicant.getTown());
            preparedStatement.setString(7, applicant.getRegion());
            preparedStatement.setString(8, applicant.getSchoolName());
            preparedStatement.executeUpdate();
        }
    }
}
