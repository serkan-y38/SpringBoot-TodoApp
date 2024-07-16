package com.yilmaz.todoapp.controller;

import com.yilmaz.todoapp.dto.auth.LoginRequest;
import com.yilmaz.todoapp.dto.auth.RegisterRequest;
import com.yilmaz.todoapp.service.auth.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

import static com.yilmaz.todoapp.utils.MyResponseMessages.LOGIN_ERROR_MESSAGE;
import static com.yilmaz.todoapp.utils.MyResponseMessages.REGISTRATION_ERROR_MESSAGE;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            return service.register(request).get();
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            return new ResponseEntity<>(REGISTRATION_ERROR_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpServletResponse response) {
        try {
            return service.login(request, response).get();
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            return new ResponseEntity<>(LOGIN_ERROR_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}