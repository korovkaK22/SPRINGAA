package com.example.springaa.services;

import com.example.springaa.entity.User;
import com.example.springaa.repositories.UserRepository;
import com.example.springaa.security.PasswordHasher;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class UserService {
   private final UserRepository userRepository;
   private final PasswordHasher passwordHasher;

    public int createUser(User user){
        return userRepository.save(user).getId();
    }

    public int createUser(String username, String password){
        User user = new User();
        user.setPassword(passwordHasher.getHashedPassword(password));
        user.setUsername(username);
        return createUser(user);
    }

    public Optional<User> getUserById(int id){
        return userRepository.findById(id);
    }

    public void deleteUser(int id){
        userRepository.deleteById(id);
    }




}
