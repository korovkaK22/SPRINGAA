package com.example.springaa.controllers;

import com.example.springaa.entity.UserResponse;
import com.example.springaa.services.AuthorizationService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class AuthorizationController {
    @Autowired
    AuthorizationService authorizationService;


    @GetMapping("/login")
    private String loginPage() {
        return "/WEB-INF/jsp/authorization/login.jsp";
    }

    @GetMapping("/logout")
    private String logoutRequest( HttpSession session) {
        session.removeAttribute("user");

        return "redirect:/";
    }


    @PostMapping("/login")
    public String loginRequest(
            Model model,
            HttpSession session,
            @RequestParam @NotBlank(message = "Name cannot be empty") String username,
            @RequestParam @NotBlank(message = "Password cannot be empty") String password) {

        Optional<UserResponse> user = authorizationService.checkUser(username, password);
        //Валідація не пройдена
        if (user.isEmpty()) {
            model.addAttribute("failMessage", "Неправильний логін або пароль");
            model.addAttribute("username", username);
            return "/WEB-INF/jsp/authorization/login.jsp";
        }
        session.setAttribute("user", user.get());

        return "redirect:/";
    }
}


