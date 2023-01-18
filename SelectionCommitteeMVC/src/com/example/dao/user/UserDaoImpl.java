package com.example.dao.user;

import com.example.entities.User;
import com.example.enums.UserRole;
import com.example.mappers.UserMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private static UserDaoImpl instance;
    private final String user;
    private final String password;
    private final String url;

    private final static String LOGIN_SQL = "select * from users where login = ? and encrypted_password = ?";
    private final static String REGISTER_SQL = "insert into users(role, login, " +
            "encrypted_password, blocked) values(?,?,?,?)";
    private final static String SELECT_BY_ID = "select * from users where id = ?";
    private final static String SELECT_ALL = "select * from users";
    private final static String SELECT_BY_LOGIN = "select * from users where login = ?";
    private final static String SELECT_BY_BLOCK = "select * from users where blocked = ?";
    private final static String SELECT_BY_ROLE = "select * from users where role = ?";
    private final static String UPDATE_USER_IS_BLOCKED = "update users set blocked = ? where id = ?";

    public static UserDaoImpl getInstance() {
        if (instance == null) {
            instance = new UserDaoImpl();
        }
        return instance;
    }

    private UserDaoImpl() {
        this.user = System.getenv("MYSQL_USERNAME");
        this.password = System.getenv("MYSQL_PASSWORD");
        this.url = "jdbc:mysql://" + System.getenv("MYSQL_HOST") + "/selection_committee";
    }

    @Override
    public User login(String login, String password) throws SQLException {
        try (Connection connection = DriverManager.getConnection(this.url, this.user, this.password);
             PreparedStatement preparedStatement = connection.prepareStatement(LOGIN_SQL)) {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            try (ResultSet set = preparedStatement.executeQuery()) {
                if (set.next())
                    return UserMapper.map(set);
                else return null;
            }
        }
    }

    @Override
    public void register(String login, String password) throws SQLException {
        try (Connection connection = DriverManager.getConnection(this.url, this.user, this.password);
             PreparedStatement preparedStatement = connection.prepareStatement(REGISTER_SQL)) {
            preparedStatement.setString(1, UserRole.APPLICANT.toString());
            preparedStatement.setString(2, login);
            preparedStatement.setString(3, password);
            preparedStatement.setBoolean(4, false);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public User getById(int id) throws SQLException {
        try (Connection connection = DriverManager.getConnection(this.url, this.user, this.password);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {
            preparedStatement.setInt(1, id);
            try (ResultSet set = preparedStatement.executeQuery()) {
                if (set.next()) {
                    return UserMapper.map(set);
                } else
                    return null;
            }
        }
    }

    @Override
    public User getByLogin(String login) throws SQLException {
        try (Connection connection = DriverManager.getConnection(this.url, this.user, this.password);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_LOGIN)) {
            preparedStatement.setString(1, login);
            try (ResultSet set = preparedStatement.executeQuery()) {
                if (set.next()) {
                    return UserMapper.map(set);
                } else
                    return null;
            }
        }
    }

    @Override
    public List<User> getAll() throws SQLException {
        List<User> users = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(this.url, this.user, this.password);
             Statement statement = connection.createStatement();
             ResultSet set = statement.executeQuery(SELECT_ALL)) {
            while (set.next()) {
                users.add(UserMapper.map(set));
            }
            return users;
        }
    }

    @Override
    public void setIsBlocked(int id, boolean isBlocked) throws SQLException {
        try (Connection connection = DriverManager.getConnection(this.url, this.user, this.password);
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_IS_BLOCKED)) {
            preparedStatement.setBoolean(1, isBlocked);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public List<User> getUsersByBlockStatus(boolean isBlocked) throws SQLException {
        List<User> users = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(this.url, this.user, this.password);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_BLOCK)) {
            preparedStatement.setBoolean(1, isBlocked);
            try (ResultSet set = preparedStatement.executeQuery()) {
                while (set.next()) {
                    users.add(UserMapper.map(set));
                }
            }
            return users;
        }
    }

    @Override
    public List<User> getUsersByUserRole(UserRole role) throws SQLException {
        List<User> users = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(this.url, this.user, this.password);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ROLE)) {
            preparedStatement.setString(1, role.toString());
            try (ResultSet set = preparedStatement.executeQuery()) {
                while (set.next()) {
                    users.add(UserMapper.map(set));
                }
            }
            return users;
        }
    }
}
