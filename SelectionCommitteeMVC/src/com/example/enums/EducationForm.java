package com.example.enums;

public enum EducationForm {
    BUDGET("BUDGET"),
    CONTRACT("CONTRACT");

    private final String type;

    EducationForm(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type;
    }
}
