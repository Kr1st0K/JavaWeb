package com.example.entities;

import com.example.enums.EducationForm;

public class ApplicantEnrollResult {
    private int userId;
    private int facultyId;
    private EducationForm form;
    private boolean enrolled;

    public ApplicantEnrollResult(int userId, int facultyId, EducationForm form, boolean enrolled) {
        this.userId = userId;
        this.facultyId = facultyId;
        this.form = form;
        this.enrolled = enrolled;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }

    public EducationForm getForm() {
        return form;
    }

    public void setForm(EducationForm form) {
        this.form = form;
    }

    public boolean isEnrolled() {
        return enrolled;
    }

    public void setEnrolled(boolean enrolled) {
        this.enrolled = enrolled;
    }
}
