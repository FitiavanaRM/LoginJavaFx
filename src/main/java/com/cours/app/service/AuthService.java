package com.cours.app.service;

import com.cours.app.model.User;

import java.util.Optional;

public class AuthService {
    private static final String user_name = "rotsynomena9@gmail.com";
    private static final String pass_word = "rotsy@123456";

    public Optional<User> login(String email, String rawPassword) {
        if (email == null || email.isBlank()) {
            return Optional.empty();
        }
        if (user_name.equals(email.trim()) && pass_word.equals(rawPassword)) {
            return Optional.of(new User(user_name, ""));
        }
        return Optional.empty();
    }
}
