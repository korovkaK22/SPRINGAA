package com.example.springaa.services;

import com.example.springaa.entity.User;
import com.example.springaa.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class UserService {
    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public int createUser(User user){
        return userRepository.save(user).getId();
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
