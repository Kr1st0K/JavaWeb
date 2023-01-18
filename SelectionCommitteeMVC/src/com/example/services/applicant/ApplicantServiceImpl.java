package com.example.services.applicant;

import com.example.dao.applicant.ApplicantDao;
import com.example.dao.applicant.ApplicantDaoImpl;
import com.example.dao.user.UserDao;
import com.example.dao.user.UserDaoImpl;
import com.example.entities.Applicant;
import com.example.entities.User;
import com.example.models.RegisterModel;

import java.sql.SQLException;

public class ApplicantServiceImpl implements ApplicantService {
    private static ApplicantServiceImpl instance;
    private final ApplicantDao applicantDao;
    private final UserDao userDao;

    public static ApplicantServiceImpl getInstance() {
        if (instance == null) {
            instance = new ApplicantServiceImpl();
        }
        return instance;
    }

    private ApplicantServiceImpl() {
        this.applicantDao = ApplicantDaoImpl.getInstance();
        this.userDao = UserDaoImpl.getInstance();
    }

    @Override
    public void register(RegisterModel model) throws SQLException {
        String name = model.getName();
        int age = model.getAge();
        String email = model.getEmail();
        String phoneNumber = model.getPhoneNumber();
        String town = model.getTown();
        String region = model.getRegion();
        String schoolName = model.getSchoolName();
        String login = model.getLogin();
        User user = userDao.getByLogin(login);
        applicantDao.add(new Applicant(user.getId(), name, age, email,
                phoneNumber, town, region, schoolName));
    }

    @Override
    public Applicant getByUserId(int userId) throws SQLException {
        return applicantDao.getByUserId(userId);
    }
}
