package com.example.entities;

import com.example.enums.UserRole;

public class User {
    private int id;
    private UserRole role;
    private String login;
    private boolean blocked;

    public User(int id, UserRole role, String login, boolean blocked) {
        this.id = id;
        this.role = role;
        this.login = login;
        this.blocked = blocked;
    }

    public User(int id, UserRole role, String login) {
        this.id = id;
        this.role = role;
        this.login = login;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }
}
