package com.example.mappers;

import com.example.enums.UserRole;
import com.example.entities.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper {
    public static User map(ResultSet set) throws SQLException {
        int userId = set.getInt(1);
        String userRoleString = set.getString(2);
        UserRole userRole = userRoleString.equals(UserRole.ADMIN.toString())
                ? UserRole.ADMIN : UserRole.APPLICANT;
        String userLogin = set.getString(3);
        boolean userBlocked = set.getBoolean(5);
        return new User(userId, userRole, userLogin, userBlocked);
    }
}
