package com.example.springaa.web.restcontrollers;

import com.example.springaa.web.dto.UserResponse;
import com.example.springaa.services.AuthorizationService;
import com.example.springaa.services.QueueService;
import com.example.springaa.util.QueuesAccessValidation;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest/queues")
@AllArgsConstructor
public class UsersInQueueController {
    QueueService queueService;
    AuthorizationService authorizationService;
    QueuesAccessValidation validation;


    @GetMapping("/{id}/users")
    private ResponseEntity<List<UserResponse>> viewUsersInQueue(@PathVariable Integer id) {
        Optional<HttpStatus> valid = validation.isQueueExist(id);
        if (valid.isPresent()){
           return ResponseEntity.status(valid.get()).build();
        }
        return ResponseEntity.ok(queueService.getAllUsersInQueue(id).stream().map(UserResponse::new).toList());
    }

    @PostMapping("/{id}")
    private ResponseEntity<Void> addUserToQueue(@PathVariable @Positive Integer id, HttpSession session) {
        Optional<HttpStatus> valid = validation.isUserAuthorizedAndQueueExist(session, id);
        if (valid.isPresent()){
            return ResponseEntity.status(valid.get()).build();
        }
        if (queueService.addUserToQueue(id, ((UserResponse)session.getAttribute("user")).getId())){
            return ResponseEntity.status(201)
                    .header("Location", "/rest/queues/" +id+ "/users")
                    .build();
        } else {
            return ResponseEntity.status(409).build();
        }
    }


    @DeleteMapping("/{id}/delete_user")
    private ResponseEntity<Void> removeUserFromQueue(@PathVariable @Positive Integer id,
                                                     @RequestParam @Positive int userId,
                                                     HttpSession session) {
        Optional<HttpStatus> valid = validation.isUserAuthorizedAndQueueExist(session, id);
        if (valid.isPresent()){
            return ResponseEntity.status(valid.get()).build();
        }

        if (queueService.deleteUserFromQueue(id, userId)){
            return ResponseEntity.status(200).build();
        } else {
            return ResponseEntity.status(404).build();
        }
    }

    @DeleteMapping("/{id}/move")
    private ResponseEntity<Void> moveUsersInQueue(@PathVariable @Positive Integer id,
                                                     HttpSession session) {
        Optional<HttpStatus> valid = validation.isUserAuthorizedAndQueueExist(session, id);
        if (valid.isPresent()){
            return ResponseEntity.status(valid.get()).build();
        }
        if (queueService.moveQueue(id)){
            return ResponseEntity.status(200).build();
        } else {
            return ResponseEntity.status(404).build();
        }
    }


}
