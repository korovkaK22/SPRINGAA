package com.example.springaa.services;

import com.example.springaa.entity.User;
import com.example.springaa.web.dto.UserResponse;
import com.example.springaa.repositories.UserRepository;
import com.example.springaa.security.PasswordHasher;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class AuthorizationService {
    private PasswordHasher passwordHasher;
    private UserRepository userDAO;

    /**
     * Повертає UserResponse, якщо пароль правильний. В інших випадках Optional.empty();
     * @param username ім'я
     * @param password пароль
     * @return при наявності такого юзера, та правильному від нього паролю
     */
    public Optional<UserResponse> checkUser(String username, String password){
        Optional<User> user = userDAO.getUserByUsernameIgnoreCase(username);
       if (user.isPresent() && passwordHasher.checkPasswords(password,user.get().getPassword())){
            return user.map(UserResponse::new);
        }
        return Optional.empty();
    }

    public boolean isUserExist(String username){
        Optional<User> user = userDAO.getUserByUsernameIgnoreCase(username);
        return user.isPresent();
    }


}
