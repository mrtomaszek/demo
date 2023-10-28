package com.empik.tj.controller;

import com.empik.tj.model.dto.ResponseDto;
import com.empik.tj.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(value = "/user/{login}")
    public ResponseDto getUserFromGithub(@PathVariable String login) {

        return userService.getGithubUser(login);
    }

    @ExceptionHandler({HttpClientErrorException.class})
    public ResponseEntity<String> handleException(HttpClientErrorException e) {

        return e.getMessage().contains("404") ? new ResponseEntity<>(
                "User not found", HttpStatus.NOT_FOUND) : new ResponseEntity<>(
                "Server error", HttpStatus.SERVICE_UNAVAILABLE
        );
    }
}
