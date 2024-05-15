package com.yilmaz.todoapp.controller;

import com.yilmaz.todoapp.dto.auth.LoginRequest;
import com.yilmaz.todoapp.dto.auth.RegisterRequest;
import com.yilmaz.todoapp.service.auth.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        return service.register(request);
    }

    @PostMapping("/login")
    public void authenticate(
            @RequestBody LoginRequest request,
            HttpServletResponse response
    ) throws IOException, JSONException {
        service.authenticate(request, response);
    }

}