package com.example.springaa.web.restcontrollers;

import com.example.springaa.web.dto.UserResponse;
import com.example.springaa.services.AuthorizationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/rest")
@Tag(name = "Login and logout account", description = "In this class we have 2 methods for log in and log out our account.")
public class LoginRestController {
    private AuthorizationService authorizationService;

    @Operation(
            summary = "Login account",
            description = "In this method we try to login account, user supposed to be registered. In return we receive session id",
            parameters = {
                    @Parameter(name = "username", description = "username for login", example = "test"),
                    @Parameter(name = "password", description = "password for login", example = "test")
            }
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success - User logged in successfully."),
            @ApiResponse(responseCode = "401", description = "UnAuthorized - Login failed due to invalid credentials.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found - No user found with the provided username.", content = @Content)
    })
    @PostMapping("/login")
    private ResponseEntity<String> loginPage(@RequestParam String username,
                                             @RequestParam String password, HttpSession session) {
        //Перевірка, чи такий юзер існує
        if (!authorizationService.isUserExist(username)) {
            ResponseEntity.status(HttpStatus.NOT_FOUND); //404
        }

        //Спроба логінізації
        Optional<UserResponse> user = authorizationService.checkUser(username, password);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); //401
        }
        session.setAttribute("user", user.get());
        return ResponseEntity.ok(session.getId()); //200
    }

    @Operation(
            summary = "Logout account",
            description = "In this method we try to log out from our account, user supposed to be previously authorized ",
            parameters = {
            }
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success - User logged out successfully."),
            @ApiResponse(responseCode = "401", description = "UnAuthorized - Logout failed: user was not logged in.")
    })
    @PostMapping("/logout")
    private ResponseEntity<Void> logoutRequest(HttpSession session) {
        //Перевірка, чи авторизований користувач
        UserResponse user = (UserResponse) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); //401
        }
        session.removeAttribute("user");
        return ResponseEntity.ok().build(); //200
    }


    @Autowired
    public void setAuthorizationService(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }
}