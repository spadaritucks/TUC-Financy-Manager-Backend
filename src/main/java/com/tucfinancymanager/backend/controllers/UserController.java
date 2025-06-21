package com.tucfinancymanager.backend.controllers;

import com.tucfinancymanager.backend.DTOs.user.UserRequestDTO;
import com.tucfinancymanager.backend.DTOs.user.UserRequestUpdateDTO;
import com.tucfinancymanager.backend.DTOs.user.UserResponseDTO;
import com.tucfinancymanager.backend.services.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = UserResponseDTO.class)
                            ))
            })
    })
    public ResponseEntity<List<UserResponseDTO>> getAllUsers (@RequestParam int page, @RequestParam int size) {
        var result = this.usersService.getAllUsers(page, size);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Cadastro do Usuario", description = "Essa função é responsável por cadastrar o usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {
                    @Content(schema = @Schema(implementation = UserResponseDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Dados Invalidos"),
            @ApiResponse(responseCode = "409", description = "O Usuario já existe no sistema")
    })
    public ResponseEntity<UserResponseDTO> createUsers (@Valid @RequestBody UserRequestDTO userRequestDTO){
        var result = this.usersService.createUsers(userRequestDTO);
        return new ResponseEntity<>(result,HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = {RequestMethod.PUT, RequestMethod.PATCH})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = UserResponseDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "O usuario não existe")
    })
    @Operation(summary = "Atualização do Usuario", description = "Essa função é responsável por atualizar o usuario")
    public ResponseEntity<UserResponseDTO> updateUsers ( @PathVariable UUID id, @RequestBody UserRequestUpdateDTO userRequestUpdateDTO){
        var result = this.usersService.updateUsers(id, userRequestUpdateDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Exclusão do Usuario", description = "Essa função é responsável por excluir o usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = UserResponseDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "O usuario não existe")
    })
    public ResponseEntity<UserResponseDTO> deleteUsers (@PathVariable UUID id){
        var result = this.usersService.deleteUsers(id);

        return new ResponseEntity<>(result, HttpStatus.OK);

    }

}
