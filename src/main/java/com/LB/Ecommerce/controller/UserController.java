package com.LB.Ecommerce.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.LB.Ecommerce.dto.Response;
import com.LB.Ecommerce.service.interf.UserService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/get-all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getAllUser(@RequestParam String param) {
        return ResponseEntity.ok(userService.getAllUsers());
    }
    
    @GetMapping("/my-info")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getUserInfoAndOrderHistory() {
        return ResponseEntity.ok(userService.getUserInfoAndOrderHistory());
    }
    
    

}
