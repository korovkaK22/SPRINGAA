package com.example.springaa.controllers.rest;

import com.example.springaa.entity.UserResponse;
import com.example.springaa.services.AuthorizationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class LoginRestController {
    private AuthorizationService authorizationService;


    @PostMapping("/rest/login")
    private ResponseEntity<String> loginPage(@RequestParam String username,
                                           @RequestParam String password, HttpSession session) {
        //Перевірка, чи такий юзер існує
        if (!authorizationService.isUserExist(username)) {
            ResponseEntity.status(HttpStatus.NOT_FOUND); //404
        }

        //Спроба логінізації
        Optional<UserResponse> user = authorizationService.checkUser(username, password);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); //401
        }
        session.setAttribute("user", user.get());
        return ResponseEntity.ok(session.getId()); //200
    }

    @PostMapping("/rest/logout")
    private ResponseEntity<Void> logoutRequest(HttpSession session) {
        session.removeAttribute("user");
        return ResponseEntity.ok().build(); //200
    }

    @Autowired
    public void setAuthorizationService(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }
}

