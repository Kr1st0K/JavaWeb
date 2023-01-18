package com.example.entities;

public class FacultyCoefficient {
    private int facultyId;
    private int subjectId;
    private float coefficient;

    public FacultyCoefficient(int facultyId, int subjectId, float coefficient) {
        this.facultyId = facultyId;
        this.subjectId = subjectId;
        this.coefficient = coefficient;
    }

    public FacultyCoefficient(int subjectId, float coefficient) {
        this.subjectId = subjectId;
        this.coefficient = coefficient;
    }

    public int getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public float getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(float coefficient) {
        this.coefficient = coefficient;
    }
}
