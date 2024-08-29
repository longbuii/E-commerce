package com.LB.Ecommerce.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.LB.Ecommerce.dto.LoginRequest;
import com.LB.Ecommerce.dto.Response;
import com.LB.Ecommerce.dto.UserDto;
import com.LB.Ecommerce.service.interf.UserService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;


    @PostMapping("/register")
    public ResponseEntity<Response> registerUser(@RequestBody UserDto registrationRequest) {
        
        return ResponseEntity.ok(userService.registerUser(registrationRequest));
    }

    
    @PostMapping("/login")
    public ResponseEntity<Response> loginUser(@RequestBody LoginRequest loginRequest) {
        
        return ResponseEntity.ok(userService.loginUser(loginRequest));
    }
    
  
}
