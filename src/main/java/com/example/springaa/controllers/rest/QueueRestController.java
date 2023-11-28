package com.example.springaa.controllers.rest;

import com.example.springaa.entity.Queue;
import com.example.springaa.entity.QueueResponse;
import com.example.springaa.entity.UserResponse;
import com.example.springaa.repositories.QueueRepository;
import com.example.springaa.services.AuthorizationService;
import com.example.springaa.services.QueueService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class QueueRestController {
    @Autowired
    QueueService queueService;
    @Autowired
    private AuthorizationService authorizationService;
    QueueRepository queueRepository;

    @PostMapping("/rest/queues/create")
    private ResponseEntity<QueueResponse> createQueue(@RequestParam @NotBlank String name, HttpSession session) {
        //Перевірка, чи авторизований користувач
        UserResponse user = (UserResponse) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); //401
        }
        //Створення черги та віддача
        QueueResponse result = queueService.createQueue(name, user.getId());
        return ResponseEntity.status(201)
                .header("Location", "/rest/queues/"+result.getId())
                .body(result);
    }


    @GetMapping("/rest/queues/{id}")
    private ResponseEntity<QueueResponse> viewQueue(@PathVariable Integer id, HttpSession session) {

        Optional<Queue> queueOpt = queueService.getQueueById(id);
        if (queueOpt.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); //404
        }
        return ResponseEntity.ok(new QueueResponse(queueOpt.get()));
    }

    @DeleteMapping("/rest/queues/delete")
    private ResponseEntity<Void> deleteQueue(@RequestParam Integer id, HttpSession session) {
        //Перевірка, чи авторизований користувач
        UserResponse user = (UserResponse) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); //401
        }
        //Перевірка, чи є така черга
        Optional<Queue> queueOpt = queueService.getQueueById(id);
        if (queueOpt.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); //404
        }
        //Перевірка, чи є користувач овнером черги
        if (queueOpt.get().getOwner().getId() != user.getId()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); //403
        }
        //Видалення черги
        return queueService.deleteQueue(id) ? ResponseEntity.ok().build() : ResponseEntity.internalServerError().build();
    }

}
