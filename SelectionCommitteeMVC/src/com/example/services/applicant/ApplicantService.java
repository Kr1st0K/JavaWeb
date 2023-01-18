package com.example.services.applicant;

import com.example.entities.Applicant;
import com.example.models.RegisterModel;

import java.sql.SQLException;

public interface ApplicantService {
    void register(RegisterModel model) throws SQLException;

    Applicant getByUserId(int userId) throws SQLException;
}
