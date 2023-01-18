package com.example.mappers;

import com.example.enums.EducationForm;
import com.example.entities.ApplicantEnrollResult;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ApplicantEnrollResultMapper {
    public static ApplicantEnrollResult map(ResultSet set) throws SQLException {
        int userId = set.getInt(1);
        int facultyId = set.getInt(2);
        EducationForm form = set.getString(3).equals("BUDGET")
                ? EducationForm.BUDGET : EducationForm.CONTRACT;
        boolean enrolled = set.getBoolean(4);
        return new ApplicantEnrollResult(userId, facultyId, form, enrolled);
    }
}
