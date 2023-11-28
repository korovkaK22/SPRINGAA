package com.example.springaa.controllers;



import com.example.springaa.entity.Queue;
import com.example.springaa.entity.QueueResponse;
import com.example.springaa.entity.UserResponse;
import com.example.springaa.services.QueueService;
import com.example.springaa.util.QueueChangingValidation;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Controller
@AllArgsConstructor
public class QueueController {
    private final QueueService queueService;
    private final QueueChangingValidation queueChangingValidation;


    @GetMapping("/queues/{id}")
    private String homepage(Model model, @PathVariable Integer id, HttpSession session){
        Optional<Queue> queueOpt= queueService.getQueueById(id);
        if (queueOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Queue not found");
        }
        Queue queue = queueOpt.get();
        model.addAttribute("queue", queue);
        model.addAttribute("users", queueService.getAllUsersInQueue(id));
        UserResponse user = (UserResponse) session.getAttribute("user");

        model.addAttribute("isInQueue", user != null &&
                queueService.isUserInQueue(id, user.getId()));
        model.addAttribute("isOwner", user != null &&
               user.getId() == queue.getOwner().getId());

        return "/WEB-INF/jsp/queue/queuePage.jsp";
    }

    @PostMapping("/queues/add_user")
    private String addUser(HttpSession session,
                            @RequestParam int queueId) {
        UserResponse user =(UserResponse) session.getAttribute("user");
        if (user == null){
            return "redirect:/";
        }
        queueService.addUserToQueue(queueId, user.getId());
        return "redirect:/queues/"+ queueId;
    }

    @PostMapping("/queues/remove_user_from_position")
    private String removeUserFromPosition(HttpSession session,
                           @RequestParam int queueId,
                           @RequestParam int position) {
        if (!queueChangingValidation.validatePermissions(session, queueId)){
            return "redirect:/";
        }
        queueService.deleteUserFromPositionInQueue(queueId, position);
        return "redirect:/queues/"+ queueId;
    }

    @PostMapping("/queues/remove_user")
    private String removeUser(HttpSession session,
                            @RequestParam int queueId) {
        UserResponse user =(UserResponse) session.getAttribute("user");
        if (user == null){
            return "redirect:/";
        }
        queueService.deleteUserFromQueue(queueId, user.getId());
        return "redirect:/queues/"+ queueId;
    }

    @PostMapping("/queues/change_closeable")
    private String changeCloseable(HttpSession session,
                              @RequestParam int queueId,
                              @RequestParam boolean value) {
        if (!queueChangingValidation.validatePermissions(session, queueId)){
            return "redirect:/";
        }
        queueService.changeCloseable(queueId, value);
        return "redirect:/queues/"+ queueId;
    }

    @PostMapping("/queues/move_user")
    private String moveUser(HttpSession session,
                                   @RequestParam int queueId) {
        if (!queueChangingValidation.validatePermissions(session, queueId)){
            return "redirect:/";
        }
        queueService.moveQueue(queueId);
        return "redirect:/queues/"+ queueId;
    }


    @PostMapping("/queues/create")
    private String createQueue(HttpSession session,
                            @RequestParam @NotBlank String name) {
        UserResponse user =(UserResponse) session.getAttribute("user");
        if (user == null){
            return "redirect:/";
        }

        QueueResponse queue = queueService.createQueue(name, user.getId());
        return "redirect:/queues/"+ queue.getId();
    }

    @GetMapping("/queues/create")
    private String getCreateQueuePage(HttpSession session,
                            Model model) {
        UserResponse user = (UserResponse) session.getAttribute("user");
        model.addAttribute("user", user) ;

        return "/WEB-INF/jsp/queue/createQueuePage.jsp";
    }



}
