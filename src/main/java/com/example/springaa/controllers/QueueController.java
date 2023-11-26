package com.example.springaa.controllers;



import com.example.springaa.entity.Queue;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedList;
import java.util.List;

@Controller
public class QueueController {
//    private final QueueService queueService;
//
//    @Autowired
//    public QueueController(QueueService queueService) {
//        this.queueService = queueService;
//    }


    @GetMapping("/queues/{id}")
    private String homepage(Model model, @PathVariable Long id){
        model.addAttribute("queue",   new Queue(1L, "name", false,null, null));
        return "/WEB-INF/jsp/queuePage.jsp";
    }





}
