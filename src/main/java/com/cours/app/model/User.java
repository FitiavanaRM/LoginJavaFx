package com.cours.app.model;

public class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public User(String password) {
        this.password = password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

