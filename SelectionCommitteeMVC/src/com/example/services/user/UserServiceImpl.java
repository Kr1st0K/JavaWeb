package com.example.services.user;

import com.example.dao.user.UserDaoImpl;
import com.example.entities.User;
import com.example.dao.user.UserDao;
import com.example.enums.UserRole;
import com.example.models.LoginModel;
import com.example.models.RegisterModel;
import com.example.utils.CryptWithMD5;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {
    private static UserServiceImpl instance;
    private final UserDao dao;

    public static UserServiceImpl getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }

    private UserServiceImpl() {
        this.dao = UserDaoImpl.getInstance();
    }

    @Override
    public void register(RegisterModel model) throws SQLException {
        String login = model.getLogin();
        String password = model.getPassword();
        String encrypted = CryptWithMD5.cryptWithMD5(password);
        User user = dao.getByLogin(login);
        if (user == null) {
            dao.register(login, encrypted);
        } else {
            throw new IllegalArgumentException("User with such login has already existed." +
                    " Choose another one.");
        }
    }

    @Override
    public User login(LoginModel model) throws SQLException {
        String login = model.getLogin();
        String password = model.getPassword();
        String encrypted = CryptWithMD5.cryptWithMD5(password);
        User user = dao.login(login, encrypted);
        if (user == null) {
            throw new IllegalArgumentException("Try another login or password");
        }
        if (user.isBlocked())
            throw new IllegalArgumentException("Sorry, but you are blocked.");
        return user;
    }

    @Override
    public User getById(int id) throws SQLException {
        return dao.getById(id);
    }

    @Override
    public List<User> getByUserRole(UserRole role) throws SQLException {
        return dao.getUsersByUserRole(role);
    }

    @Override
    public List<User> getAll() throws SQLException {
        return dao.getAll();
    }

    @Override
    public List<User> getBlockedUsers() throws SQLException {
        return dao.getUsersByBlockStatus(true);
    }

    @Override
    public List<User> getUsersWhoCanBeBlocked() throws SQLException {
        List<User> users = dao.getUsersByUserRole(UserRole.APPLICANT);
        return users.stream()
                .filter(x -> !x.isBlocked())
                .collect(Collectors.toList());
    }

    @Override
    public void block(int userId) throws SQLException {
        dao.setIsBlocked(userId, true);
    }

    @Override
    public void unblock(int userId) throws SQLException {
        dao.setIsBlocked(userId, false);
    }
}
