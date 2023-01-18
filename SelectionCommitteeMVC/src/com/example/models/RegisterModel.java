package com.example.models;

public class RegisterModel {
    private final String login;
    private final String password;
    private final String name;
    private final int age;
    private final String email;
    private final String phoneNumber;
    private final String town;
    private final String region;
    private final String schoolName;

    public RegisterModel(String login, String password, String name, int age,
                         String email, String phoneNumber, String town,
                         String region, String schoolName) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.age = age;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.town = town;
        this.region = region;
        this.schoolName = schoolName;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getTown() {
        return town;
    }

    public String getRegion() {
        return region;
    }

    public String getSchoolName() {
        return schoolName;
    }
}
