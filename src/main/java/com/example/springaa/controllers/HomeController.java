package com.example.springaa.controllers;

import com.example.springaa.entity.Queue;
import com.example.springaa.entity.User;
import com.example.springaa.repositories.QueueRepository;
import com.example.springaa.repositories.UserRepository;
import com.example.springaa.services.HomeServices;
import com.example.springaa.services.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.LinkedList;
import java.util.List;

@Controller
@AllArgsConstructor
public class HomeController {
    HomeServices homeServices;

    @GetMapping("/")
    private String homepage(Model model){
        model.addAttribute("lastQueues", homeServices.findLastQueues(5));
        return "/WEB-INF/jsp/homePage.jsp";
        }

}
