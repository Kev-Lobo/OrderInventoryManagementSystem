package com.kevin.todo.orderandinventorymanagementsystem.controller;

import com.kevin.todo.orderandinventorymanagementsystem.dto.request.UserRequest;
import com.kevin.todo.orderandinventorymanagementsystem.dto.response.UserResponse;
import com.kevin.todo.orderandinventorymanagementsystem.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signUp(@RequestBody UserRequest userRequest) {
        UserResponse userResponse = userService.registerUser(userRequest);
        return ResponseEntity.ok(userResponse);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login() {
        try{
            return new  ResponseEntity<>("You've Logged in Successfully",HttpStatus.OK);
        }
        catch (Exception ex){
            log.error("Exception occurred while authenticating the user",ex);
            return new  ResponseEntity<>("Invalid UserName or Password",HttpStatus.UNAUTHORIZED);
        }
    }
}