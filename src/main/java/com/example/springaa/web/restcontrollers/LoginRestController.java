package com.example.springaa.web.restcontrollers;

import com.example.springaa.web.dto.UserResponse;
import com.example.springaa.services.AuthorizationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/rest")
public class LoginRestController {
    private AuthorizationService authorizationService;


    @PostMapping("/login")
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

    @PostMapping("/logout")
    private ResponseEntity<Void> logoutRequest(HttpSession session) {
        //Перевірка, чи авторизований користувач
        UserResponse user = (UserResponse) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); //401
        }
        session.removeAttribute("user");
        return ResponseEntity.ok().build(); //200
    }





    @Autowired
    public void setAuthorizationService(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }
}

