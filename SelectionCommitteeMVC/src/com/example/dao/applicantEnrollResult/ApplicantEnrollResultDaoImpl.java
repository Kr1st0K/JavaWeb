package com.example.dao.applicantEnrollResult;

import com.example.enums.EducationForm;
import com.example.mappers.ApplicantEnrollResultMapper;
import com.example.entities.ApplicantEnrollResult;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ApplicantEnrollResultDaoImpl implements ApplicantEnrollResultDao {
    private static ApplicantEnrollResultDaoImpl instance;
    private final String user;
    private final String password;
    private final String url;

    private static final String ADD_APPLICANT_ENROLL_RESULT = "insert into applicants_enroll_results(user_id, " +
            "faculty_id, form, enrolled) values (?,?,?,?)";

    private static final String SELECT_BY_USER_ID = "select * from applicants_enroll_results where user_id = ?";
    private static final String SELECT_BY_EDUCATION_FORM = "select * from applicants_enroll_results" +
            " where form = ?";

    public static ApplicantEnrollResultDaoImpl getInstance() {
        if (instance == null) {
            instance = new ApplicantEnrollResultDaoImpl();
        }
        return instance;
    }

    private ApplicantEnrollResultDaoImpl() {
        this.user = System.getenv("MYSQL_USERNAME");
        this.password = System.getenv("MYSQL_PASSWORD");
        this.url = "jdbc:mysql://" + System.getenv("MYSQL_HOST") + "/selection_committee";
    }

    @Override
    public void add(ApplicantEnrollResult result) throws SQLException {
        try (Connection connection = DriverManager.getConnection(this.url, this.user, this.password);
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_APPLICANT_ENROLL_RESULT)) {
            preparedStatement.setInt(1, result.getUserId());
            preparedStatement.setInt(2, result.getFacultyId());
            preparedStatement.setString(3, result.getForm().toString());
            preparedStatement.setBoolean(4, result.isEnrolled());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void addList(List<ApplicantEnrollResult> results) throws SQLException {
        try (Connection connection = DriverManager.getConnection(this.url, this.user, this.password);
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_APPLICANT_ENROLL_RESULT)) {
            for (ApplicantEnrollResult result : results) {
                preparedStatement.setInt(1, result.getUserId());
                preparedStatement.setInt(2, result.getFacultyId());
                preparedStatement.setString(3, result.getForm().toString());
                preparedStatement.setBoolean(4, result.isEnrolled());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }
    }

    @Override
    public List<ApplicantEnrollResult> getByUserId(int userId) throws SQLException {
        List<ApplicantEnrollResult> results = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(this.url, this.user, this.password);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_USER_ID)) {
            preparedStatement.setInt(1, userId);
            try (ResultSet set = preparedStatement.executeQuery()) {
                while (set.next()) {
                    results.add(ApplicantEnrollResultMapper.map(set));
                }
            }
            return results;
        }
    }

    @Override
    public List<Integer> getUserIdsByEducationForm(EducationForm form) throws SQLException {
        List<Integer> ids = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(this.url, this.user, this.password);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_EDUCATION_FORM)) {
            preparedStatement.setString(1, form.toString());
            try (ResultSet set = preparedStatement.executeQuery()) {
                while (set.next()) {
                    ids.add(set.getInt("user_id"));
                }
            }
            return ids;
        }
    }
}
