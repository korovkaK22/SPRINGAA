package com.example.springaa.services;

import com.example.springaa.entity.User;
import com.example.springaa.repositories.UserRepository;
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

    public int createUser(User user){
        return userRepository.save(user).getId();
    }

    public int createUser(String username, String password){
        User user = new User();
        user.setPassword(password);
        user.setUsername(username);
        return createUser(user);
    }

    public Optional<User> getUserById(int id){
        return userRepository.findById(id);
    }

    public User editUser(int id, User user) throws IllegalStateException{
        userRepository.findById(id).ifPresentOrElse(u -> {
            u.setUsername(user.getUsername());
            u.setPassword(user.getPassword());
        }, ()-> { throw new IllegalArgumentException("can't find user with id"+id);});
        return user;
    }

    public void deleteUser(int id){
        userRepository.deleteById(id);
    }



}
