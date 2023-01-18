package com.example.dao.applicantAssign;

import com.example.mappers.ApplicantAssignMapper;
import com.example.entities.ApplicantAssign;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ApplicantAssignDaoImpl implements ApplicantAssignDao {
    private static ApplicantAssignDaoImpl instance;
    private final String user;
    private final String password;
    private final String url;

    private static final String ADD_APPLICANT_ASSIGN = "insert into applicants_assigned(user_id, faculty_id, priority)" +
            " values (?,?,?)";

    private static final String SELECT_BY_USER_ID = "select * from applicants_assigned where user_id = ?";
    private static final String SELECT_BY_FACULTY_ID = "select * from applicants_assigned where faculty_id = ?";

    public static ApplicantAssignDaoImpl getInstance() {
        if (instance == null) {
            instance = new ApplicantAssignDaoImpl();
        }
        return instance;
    }

    private ApplicantAssignDaoImpl() {
        this.user = System.getenv("MYSQL_USERNAME");
        this.password = System.getenv("MYSQL_PASSWORD");
        this.url = "jdbc:mysql://" + System.getenv("MYSQL_HOST") + "/selection_committee";
    }

    @Override
    public void add(ApplicantAssign applicantAssign) throws SQLException {
        try (Connection connection = DriverManager.getConnection(this.url, this.user, this.password);
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_APPLICANT_ASSIGN)) {
            preparedStatement.setInt(1, applicantAssign.getUserId());
            preparedStatement.setInt(2, applicantAssign.getFacultyId());
            preparedStatement.setInt(3, applicantAssign.getPriority());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void addList(List<ApplicantAssign> applicantAssigns) throws SQLException {
        try (Connection connection = DriverManager.getConnection(this.url, this.user, this.password);
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_APPLICANT_ASSIGN)) {
            for (ApplicantAssign applicantAssign : applicantAssigns) {
                preparedStatement.setInt(1, applicantAssign.getUserId());
                preparedStatement.setInt(2, applicantAssign.getFacultyId());
                preparedStatement.setInt(3, applicantAssign.getPriority());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }
    }

    @Override
    public List<ApplicantAssign> getByUserId(int userId) throws SQLException {
        List<ApplicantAssign> applicantAssigns = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(this.url, this.user, this.password);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_USER_ID)) {
            preparedStatement.setInt(1, userId);
            try (ResultSet set = preparedStatement.executeQuery()) {
                while (set.next()) {
                    applicantAssigns.add(ApplicantAssignMapper.map(set));
                }
            }
            return applicantAssigns;
        }
    }

    @Override
    public List<ApplicantAssign> getByFacultyId(int facultyId) throws SQLException {
        List<ApplicantAssign> applicantAssigns = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(this.url, this.user, this.password);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_FACULTY_ID)) {
            preparedStatement.setInt(1, facultyId);
            try (ResultSet set = preparedStatement.executeQuery()) {
                while (set.next()) {
                    applicantAssigns.add(ApplicantAssignMapper.map(set));
                }
            }
            return applicantAssigns;
        }
    }
}
