package com.example.springaa.web.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExceptionController {

    @GetMapping("/404")
    private String notFindQueue(HttpSession session, Model model) {
        model.addAttribute("user", session.getAttribute("user"));
        model.addAttribute("exceptionMessage", "Такої сторінки не існує");
        return "/WEB-INF/jsp/errors/notFoundPage.jsp";
    }

    @GetMapping("/error")
    private String errorStatus(HttpSession session, Model model) {
        model.addAttribute("user", session.getAttribute("user"));
        return "/WEB-INF/jsp/errors/notFoundPage.jsp";
    }

}
