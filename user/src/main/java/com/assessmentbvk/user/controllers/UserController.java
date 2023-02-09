package com.assessmentbvk.user.controllers;

import com.assessmentbvk.user.dto.RequestUpdateUser;
import com.assessmentbvk.user.services.UserService;
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
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/detail")
    public ResponseEntity<String> login(@RequestHeader(name = "userId") String userId) throws JsonProcessingException {
        return userService.detailUser(Integer.parseInt(userId));
    }

    @PutMapping(value = "/update")
    public ResponseEntity<String> update(@RequestHeader(name = "userId") String userId, @RequestBody RequestUpdateUser request) throws JsonProcessingException {
        return userService.update(Integer.parseInt(userId), request);
    }

}
