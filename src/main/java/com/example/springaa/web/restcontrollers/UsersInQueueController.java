package com.example.springaa.web.restcontrollers;

import com.example.springaa.entity.Queue;
import com.example.springaa.exceptions.ForbiddenAccessException;
import com.example.springaa.exceptions.NotAuthorizedException;
import com.example.springaa.exceptions.ResourceNotFoundException;
import com.example.springaa.web.dto.UserResponse;
import com.example.springaa.services.AuthorizationService;
import com.example.springaa.services.QueueService;
import com.example.springaa.util.QueuesAccessValidation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/queues")
@AllArgsConstructor
@Tag(name = "User in Queue", description = "In this class we do CRUD operations with Users on Queues")
public class UsersInQueueController {
    QueueService queueService;
    AuthorizationService authorizationService;


    @Operation(
            summary = "Get all Users in queue",
            description = "Get all Users in specific Queue",
            parameters = {@Parameter(name = "id", description = "Queue Id", example = "2")}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = @Content(schema = @Schema(implementation = ResourceNotFoundException.class)))
    })
    @GetMapping("/{id}/users")
    private ResponseEntity<List<UserResponse>> viewUsersInQueue(@PathVariable Integer id) {
        //Перевірка, чи існує черга
        queueService.getRestQueueById(id);
        return ResponseEntity.ok(queueService.getAllUsersInQueue(id).stream().map(UserResponse::new).toList());
    }


    @Operation(
            summary = "Add user to Queue",
            description = "Add authorized user to specific queue. For this action session need bo be authorized." +
                    "When operation is succeed, return also \"Location\" of queue in headers",
            parameters = {@Parameter(name = "id", description = "Queue Id", example = "2")}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Added"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = NotAuthorizedException.class))),
            @ApiResponse(responseCode = "409", description = "Conflict (User already in queue)", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = @Content(schema = @Schema(implementation = ResourceNotFoundException.class)))
    })
    @PostMapping("/{id}")
    private ResponseEntity<Void> addUserToQueue(@PathVariable @Positive Integer id, HttpSession session) {
        //Перевірка, чи авторизований користувач
        UserResponse user = getUser(session);
        //Перевірка, чи є черга
         queueService.getRestQueueById(id);
        if (queueService.addUserToQueue(id, user.getId())){
            return ResponseEntity.status(201)
                    .header("Location", "/rest/queues/" +id+ "/users")
                    .build();
        } else {
            return ResponseEntity.status(409).build();
        }
    }




    @Operation(
            summary = "Delete user from Queue",
            description = "Delete authorized user to specific queue. For this action session need bo be authorized" +
                    "and user must be a owner of this queue",
            parameters = {@Parameter(name = "id", description = "Queue Id", example = "2"),
                    @Parameter(name = "userId", description = "User id, that must be kicked", example = "2")}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Deleted"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = NotAuthorizedException.class))),
            @ApiResponse(responseCode = "403", description = "No Access (Not an owner)",
                    content = @Content(schema = @Schema(implementation = ForbiddenAccessException.class))),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = @Content(schema = @Schema(implementation = ResourceNotFoundException.class)))
    })
    @DeleteMapping("/{id}/delete_user")
    private ResponseEntity<Void> removeUserFromQueue(@PathVariable @Positive Integer id,
                                                     @RequestParam @Positive int userId,
                                                     HttpSession session) {
        //Перевірка, чи авторизований користувач
        UserResponse user = getUser(session);
        //Перевірка, чи є черга
        Queue queue = queueService.getRestQueueById(id);
        //Перевірка, чи юзер власника черги
        if (queue.getOwner().getId() != user.getId()){
            throw new ForbiddenAccessException("You are not a owner of this queue");
        }

        if (queueService.deleteUserFromQueue(id, userId)){
            return ResponseEntity.status(200).build();
        } else {
            return ResponseEntity.status(404).build();
        }
    }


    @Operation(
            summary = "Move Queue",
            description = "Move Queue (delete first User in query). That can make only User, that is Queue owner",
            parameters = {@Parameter(name = "id", description = "Queue Id", example = "2")}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Moved"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = NotAuthorizedException.class))),
            @ApiResponse(responseCode = "403", description = "No Access (Not an owner)",
                    content = @Content(schema = @Schema(implementation = ForbiddenAccessException.class))),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = @Content(schema = @Schema(implementation = ResourceNotFoundException.class)))
    })
    @DeleteMapping("/{id}/move")
    private ResponseEntity<Void> moveUsersInQueue(@PathVariable @Positive Integer id,
                                                     HttpSession session) {
        //Перевірка, чи авторизований користувач
        UserResponse user = getUser(session);
        //Перевірка, чи є черга
        Queue queue = queueService.getRestQueueById(id);
        //Перевірка, чи юзер власника черги
        if (queue.getOwner().getId() != user.getId()){
            throw new ForbiddenAccessException("You are not a owner of this queue");
        }
        if (queueService.moveQueue(id)){
            return ResponseEntity.status(200).build();
        } else {
            return ResponseEntity.status(404).build();
        }
    }


    /**
     * Перевіряє,чи користувач залогінений
     * @param session сесія, в якій є юзер
     * @return користувач
     * @throws NotAuthorizedException якщо юзер не залогінений
     */
    private UserResponse getUser(HttpSession session){
        //Перевірка, чи авторизований користувач
        UserResponse user = (UserResponse) session.getAttribute("user");
        if (user == null) {
            throw new NotAuthorizedException("For this action you must be login"); //401
        }
        return user;
    }

}
