package com.example.entities;

public class ApplicantAssign {
    private int userId;
    private int facultyId;
    private int priority;

    public ApplicantAssign(int userId, int facultyId, int priority) {
        this.userId = userId;
        this.facultyId = facultyId;
        this.priority = priority;
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

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
