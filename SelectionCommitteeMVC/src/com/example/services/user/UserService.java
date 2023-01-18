package com.example.services.user;

import com.example.entities.User;
import com.example.enums.UserRole;
import com.example.models.LoginModel;
import com.example.models.RegisterModel;

import java.sql.SQLException;
import java.util.List;

public interface UserService {
    void register(RegisterModel model) throws SQLException;

    User login(LoginModel model) throws SQLException;

    User getById(int id) throws SQLException;

    List<User> getByUserRole(UserRole role) throws SQLException;

    List<User> getAll() throws SQLException;

    List<User> getBlockedUsers() throws SQLException;

    List<User> getUsersWhoCanBeBlocked() throws SQLException;

    void block(int userId) throws SQLException;

    void unblock(int userId) throws SQLException;
}
