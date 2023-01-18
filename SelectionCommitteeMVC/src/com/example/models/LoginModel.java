package com.example.models;

public class LoginModel {
    private final String login;
    private final String password;

    public LoginModel(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
