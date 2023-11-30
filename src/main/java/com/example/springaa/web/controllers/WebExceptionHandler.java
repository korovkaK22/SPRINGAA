package com.example.springaa.web.controllers;

import com.example.springaa.exceptions.ResourceNotFoundException;
import com.example.springaa.web.dto.UserResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;

@ControllerAdvice
public class WebExceptionHandler {

    @ExceptionHandler({ResourceNotFoundException.class})
    public String handleResourceNotFoundException(ResourceNotFoundException e) {
        return "redirect:/404";
    }







}
