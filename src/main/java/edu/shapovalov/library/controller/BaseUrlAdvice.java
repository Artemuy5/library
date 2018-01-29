package edu.shapovalov.library.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * @author Artem Shapovalov
 * @version 0.1
 * @since 06.01.2018
 */

@Component
@ControllerAdvice
public class BaseUrlAdvice {
    @ModelAttribute("baseUrl")
    public String baseUrl() {
        return "http://localhost:8080";
    }

    @ExceptionHandler(RuntimeException.class)
    public void handle() {

    }
}
