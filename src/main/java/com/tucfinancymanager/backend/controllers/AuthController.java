package com.tucfinancymanager.backend.controllers;

import com.tucfinancymanager.backend.DTOs.auth.AuthRequestDTO;
import com.tucfinancymanager.backend.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping
    public ResponseEntity<String> auth (AuthRequestDTO authRequestDTO) {
        var result = this.authService.auth(authRequestDTO);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
}
