package com.example.springaa.controllers;



import com.example.springaa.entity.Queue;
import com.example.springaa.entity.UserResponse;
import com.example.springaa.services.QueueService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class QueueController {
    private final QueueService queueService;


    @GetMapping("/queues/{id}")
    private String homepage(Model model, @PathVariable Integer id, HttpSession session){
        Optional<Queue> queue= queueService.getQueueById(id);
        if (queue.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Queue not found");
        }
        model.addAttribute("queue", queue.get());

        model.addAttribute("isOwner", session.getAttribute("user") != null &&
                ((UserResponse)session.getAttribute("user")).getId() ==  queue.get().getOwner().getId());

        return "/WEB-INF/jsp/queue/queuePage.jsp";
    }





}
