package com.example.springaa.services;

import com.example.springaa.entity.User;
import com.example.springaa.entity.UserResponse;
import com.example.springaa.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthorizationService {

    private UserRepository userDAO;

    /**
     * Повертає UserResponse, якщо пароль правильний. В інших випадках Optional.empty();
     * @param username ім'я
     * @param password пароль
     * @return при наявності такого юзера, та правильному від нього паролю
     */
    public Optional<UserResponse> checkUser(String username, String password){
        Optional<User> user = userDAO.getUserByName(username);
        if (user.isPresent() && user.get().getPassword().equals(password)){
            return user.map(UserResponse::new);
        }
        return Optional.empty();
    }



}
