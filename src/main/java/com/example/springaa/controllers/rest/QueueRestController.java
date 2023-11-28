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
@RequestMapping("/rest/queues")
public class QueueRestController {
    @Autowired
    QueueService queueService;
    @Autowired
    AuthorizationService authorizationService;
    @Autowired
    QueueRepository queueRepository;

    @PostMapping()
    private ResponseEntity<QueueResponse> createQueue(@RequestParam @NotBlank String name, HttpSession session) {
        //Перевірка, чи авторизований користувач
        UserResponse user = (UserResponse) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); //401
        }
        //Створення черги та віддача
        QueueResponse result = queueService.createQueue(name, user.getId());
        return ResponseEntity.status(201)
                .header("Location", "/rest/queues/" + result.getId())
                .body(result);
    }


    @GetMapping("/{id}")
    private ResponseEntity<QueueResponse> viewQueue(@PathVariable Integer id) {
        Optional<Queue> queueOpt = queueService.getQueueById(id);
        if (queueOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); //404
        }
        return ResponseEntity.ok(new QueueResponse(queueOpt.get()));
    }


    @PutMapping("/change_name")
    private ResponseEntity<QueueResponse> changeQueue(@RequestParam @NotBlank String newName,
                                                      @RequestParam int id,
                                                      HttpSession session) {
        Optional<ResponseEntity.BodyBuilder> validate = new  Validator().checksOfPermissionsOfQueue(id, session);
        if (validate.isPresent()){
            return  validate.get().build();
        }
        //Оновлення черги
        Optional<Queue> resultOpt = queueService.updateName(id, newName);
        return resultOpt.map(queue -> ResponseEntity.ok(new QueueResponse(queue)))
                .orElseGet(() -> ResponseEntity.internalServerError().build());
    }


    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deleteQueue(@PathVariable Integer id, HttpSession session) {
        Optional<ResponseEntity.BodyBuilder> validate = new  Validator().checksOfPermissionsOfQueue(id, session);
        return validate.<ResponseEntity<Void>>map(ResponseEntity.HeadersBuilder::build).orElseGet(() -> queueService.deleteQueue(id) ?
                ResponseEntity.ok().build() :
                ResponseEntity.internalServerError().build());

    }

   //======== Міні-валідація =========
   private class Validator{
       private Optional<ResponseEntity.BodyBuilder> checksOfPermissionsOfQueue(Integer queueId, HttpSession session) {
           //Перевірка, чи авторизований користувач
           UserResponse user = (UserResponse) session.getAttribute("user");
           if (user == null) {
               return Optional.of(ResponseEntity.status(HttpStatus.UNAUTHORIZED)); //401
           }
           //Перевірка, чи є така черга
           Optional<Queue> queueOpt = queueService.getQueueById(queueId);
           if (queueOpt.isEmpty()) {
               return Optional.of(ResponseEntity.status(HttpStatus.NOT_FOUND)); //404
           }
           //Перевірка, чи є користувач овнером черги
           if (queueOpt.get().getOwner().getId() != user.getId()) {
               return Optional.of(ResponseEntity.status(HttpStatus.FORBIDDEN)); //403
           }
           return Optional.empty();
       }

   }
}


