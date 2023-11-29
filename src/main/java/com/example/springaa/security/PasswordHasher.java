package com.example.springaa.security;

public interface PasswordHasher {
    public String getHashedPassword(String password);

    public boolean checkPasswords(String password, String hashedPassword);
}
