package com.example.springaa.web.restcontrollers;

import com.example.springaa.entity.Queue;
import com.example.springaa.web.dto.QueueResponse;
import com.example.springaa.web.dto.UserResponse;
import com.example.springaa.services.AuthorizationService;
import com.example.springaa.services.QueueService;
import com.example.springaa.util.QueuesAccessValidation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/rest/queues")
@Tag(name = "Queues", description = "In this class we do CRUD operations with Queues")
public class QueueRestController {
    @Autowired
    QueueService queueService;
    @Autowired
    AuthorizationService authorizationService;
    @Autowired
    QueuesAccessValidation validation;


    @Operation(
            summary = "Create Queue",
            description = "Create new Queue. User became owner of this queue. Need to be authorized",
            parameters = {@Parameter(name = "name", description = "Queue name", example = "myQueue1")}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
    })
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


    @Operation(
            summary = "Get specific Queue",
            description = "Get Queue with specific id. There is no need to be authorized",
            parameters = {@Parameter(name = "id", description = "Queue Id", example = "2")}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Succeed, return queue"),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    })
    @GetMapping("/{id}")
    private ResponseEntity<QueueResponse> viewQueue(@PathVariable Integer id) {
        Optional<Queue> queueOpt = queueService.getQueueById(id);
        if (queueOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); //404
        }
        return ResponseEntity.ok(new QueueResponse(queueOpt.get()));
    }


    @Operation(
            summary = "Change Queue name",
            description = "Change Queue name with specific id. Authorized User needs to be queue's owner",
            parameters = {@Parameter(name = "id", description = "Queue Id", example = "2"),
                          @Parameter(name = "newName", description = "new Queue's name", example = "myNewQueuesName")}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Succeed, return queue"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "No Access (Not an owner)", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server's problem", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    })
    @PutMapping()
    private ResponseEntity<QueueResponse> changeQueue(@RequestParam @NotBlank String newName,
                                                      @RequestParam @Positive int id,
                                                      HttpSession session) {
        Optional<HttpStatus> valid = validation.isUserOwnerOfQueue(session, id);
        if (valid.isPresent()){
            return ResponseEntity.status(valid.get()).build();
        }
        //Оновлення черги
        Optional<Queue> resultOpt = queueService.updateName(id, newName);
        return resultOpt.map(queue -> ResponseEntity.ok(new QueueResponse(queue)))
                .orElseGet(() -> ResponseEntity.internalServerError().build());
    }



    @Operation(
            summary = "Delete Queue ",
            description = "Delete Queue with specific id. Authorized User needs to be queue's owner",
            parameters = {@Parameter(name = "id", description = "Queue Id", example = "2")}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Deleted"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "No Access (Not an owner)", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server's problem", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    })
    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deleteQueue(@PathVariable @Positive Integer id, HttpSession session) {
        Optional<HttpStatus> valid = validation.isUserOwnerOfQueue(session, id);
        if (valid.isPresent()){
            return ResponseEntity.status(valid.get()).build();
        }
        return  queueService.deleteQueue(id) ?
                ResponseEntity.ok().build() :
                ResponseEntity.internalServerError().build();

    }

  }


