package com.example.springaa.security;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHasherImpl implements PasswordHasher {

    @Override
    public String getHashedPassword(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    @Override
    public boolean checkPasswords(String password, String hashedPassword){
        return BCrypt.checkpw(password, hashedPassword);
    }
}
