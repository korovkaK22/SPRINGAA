package com.example.springaa.controllers;

import com.example.springaa.entity.Queue;
import com.example.springaa.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.LinkedList;
import java.util.List;

@Controller
public class HomeController {

    @GetMapping("/")
    private String homepage(Model model){
        model.addAttribute("lastQueues", new LinkedList<Queue>(List.of(
                new Queue(1L, "name", false,null, null),
                new Queue(1L, "name", false,null, null),
                new Queue(1L, "name", false,null, null))));

        return "/WEB-INF/jsp/homePage.jsp";
        }

}
