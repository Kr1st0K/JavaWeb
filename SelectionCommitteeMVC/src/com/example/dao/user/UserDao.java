package com.example.dao.user;

import com.example.entities.User;
import com.example.enums.UserRole;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {
    User login(String login, String password) throws SQLException;

    void register(String login, String password) throws SQLException;

    User getById(int id) throws SQLException;

    User getByLogin(String login) throws SQLException;

    List<User> getAll() throws SQLException;

    void setIsBlocked(int id, boolean isBlocked) throws SQLException;

    List<User> getUsersByBlockStatus(boolean isBlocked) throws SQLException;

    List<User> getUsersByUserRole(UserRole role) throws SQLException;
}
