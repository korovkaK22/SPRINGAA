package com.example.springaa.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    private String homepage(Model model){
        return "/WEB-INF/jsp/homePage.jsp";
        }

}
