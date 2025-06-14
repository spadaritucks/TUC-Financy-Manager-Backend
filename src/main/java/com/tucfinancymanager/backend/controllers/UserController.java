package com.tucfinancymanager.backend.controllers;

import com.tucfinancymanager.backend.DTOs.user.UserRequestDTO;
import com.tucfinancymanager.backend.entities.User;
import com.tucfinancymanager.backend.services.UsersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UsersService usersService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers () {
        var result = this.usersService.getAllUsers();
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> createUsers (@Valid @RequestBody UserRequestDTO userRequestDTO){
        var result = this.usersService.createUsers(userRequestDTO);
        return new ResponseEntity<>(result,HttpStatus.CREATED);
    }

//    @PutMapping
//    public void updateUsers (UUID id, UserRequestDTO userRequestDTO){
//        return this.usersService.updateUsers(id, userRequestDTO);
//    }
//
//    @DeleteMapping
//    public void deleteUsers (UUID id){
//        return this.usersService.deleteUsers(id);
//    }

}
