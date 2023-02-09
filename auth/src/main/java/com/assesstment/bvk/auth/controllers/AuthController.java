package com.assesstment.bvk.auth.controllers;

import com.assesstment.bvk.auth.dto.RequestLoginDTO;
import com.assesstment.bvk.auth.dto.RequestRegisterDTO;
import com.assesstment.bvk.auth.services.AuthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RefreshScope
@ControllerAdvice
@RequestMapping(value = "/account", produces= MediaType.APPLICATION_JSON_VALUE)
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping(value = "/login")
    public ResponseEntity<String> login(@RequestBody RequestLoginDTO request) throws JsonProcessingException {
        return authService.login(request);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<String> register(@RequestBody RequestRegisterDTO request) throws JsonProcessingException {
        return authService.register(request);
    }
}
