package com.tucfinancymanager.backend.controllers;

import com.tucfinancymanager.backend.DTOs.user.UserRequestDTO;
import com.tucfinancymanager.backend.DTOs.user.UserRequestUpdateDTO;
import com.tucfinancymanager.backend.DTOs.user.UserResponseDTO;
import com.tucfinancymanager.backend.entities.User;
import com.tucfinancymanager.backend.services.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@Tag(name = "Usuarios", description = "Informações do Usuario")
public class UserController {

    @Autowired
    private UsersService usersService;

    @GetMapping
    @Operation(summary = "Listagem de todos os usuarios", description = "Essa função é responsável por listar todos os usuarios")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers () {
        var result = this.usersService.getAllUsers();
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Cadastro do Usuario", description = "Essa função é responsável por cadastrar o usuario")
    public ResponseEntity<UserResponseDTO> createUsers (@Valid @RequestBody UserRequestDTO userRequestDTO){
        var result = this.usersService.createUsers(userRequestDTO);
        return new ResponseEntity<>(result,HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = {RequestMethod.PUT, RequestMethod.PATCH})
    public ResponseEntity<UserResponseDTO> updateUsers ( @PathVariable UUID id, @RequestBody UserRequestUpdateDTO userRequestUpdateDTO){
        System.out.println(id);
        var result = this.usersService.updateUsers(id, userRequestUpdateDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserResponseDTO> deleteUsers (@PathVariable UUID id){
        var result = this.usersService.deleteUsers(id);

        return new ResponseEntity<>(result, HttpStatus.OK);

    }

}
