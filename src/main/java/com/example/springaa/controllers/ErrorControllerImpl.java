package com.example.springaa.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorControllerImpl implements ErrorController {

    @RequestMapping("/error")
    public String handleError() {
        // Перенаправляємо на сторінку помилки 404
        return "404"; // Ім'я вашого файлу 404.html або шаблону
    }

}
