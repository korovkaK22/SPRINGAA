package com.example.springaa.web.controllers;

import com.example.springaa.web.dto.UserResponse;
import com.example.springaa.services.AuthorizationService;
import com.example.springaa.services.UserService;
import com.example.springaa.util.UserValidation;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@AllArgsConstructor
public class AuthorizationController {

    private final AuthorizationService authorizationService;
    private final UserService userService;


    //=======Реєстрація====

    @GetMapping("/register")
    private String registerPage() {
        return "/WEB-INF/jsp/authorization/register.jsp";
    }

    @PostMapping("/register")
    private String registerRequest(Model model,
                                   HttpSession session,
                                   @RequestParam @NotBlank String username,
                                   @RequestParam @NotBlank String password,
                                   @RequestParam @NotBlank String reppassword) {
        //Початкова валідація
        try {
            if (!password.equals(reppassword)){
                model.addAttribute("failMessage","Паролі повинні співпадати");
                model.addAttribute("username", username);
                return "/WEB-INF/jsp/authorization/register.jsp";
            }

            UserValidation.isValid(username, password);
        } catch (Exception e) {
            model.addAttribute("failMessage", e.getMessage());
            model.addAttribute("username", username);
            return "/WEB-INF/jsp/authorization/register.jsp";
        }
        //Перевірка, чи вільне ім'я
        if(authorizationService.isUserExist(username)){
            model.addAttribute("failMessage", "Користувач з таки іменем вже існує");
            model.addAttribute("username", username);
            return "/WEB-INF/jsp/authorization/register.jsp";
        }
        //Все пройшло правильно
        int id = userService.createUser(username, password);

        session.setAttribute("user", new UserResponse(id, username));

        return "redirect:/";
    }


    //========Логін=======

    @GetMapping("/login")
    private String loginPage() {
        return "/WEB-INF/jsp/authorization/login.jsp";
    }

    @PostMapping("/login")
    public String loginRequest(
            Model model,
            HttpSession session,
            @RequestParam @NotBlank String username,
            @RequestParam @NotBlank String password) {

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


    @PostMapping("/logout")
    private String logoutRequest(HttpSession session) {
        session.removeAttribute("user");

        return "redirect:/";
    }
}


