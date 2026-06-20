package com.cours.app.service;

public class AuthService {
    private static final String user_name = "rotsynomena9@gmail.com";
    private static final String pass_word = "rotsy@123456";

    public boolean vraiFaux(String username, String password) {
        return user_name.equals(username) && pass_word.equals(password);
    }
}
