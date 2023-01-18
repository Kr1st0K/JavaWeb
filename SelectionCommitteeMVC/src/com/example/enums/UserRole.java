package com.example.enums;

public enum UserRole {
    ADMIN("ADMIN"),
    APPLICANT("APPLICANT");

    private final String type;
    UserRole(String type){
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type;
    }
}
