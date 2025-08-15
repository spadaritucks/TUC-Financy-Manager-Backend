package com.tucfinancymanager.backend.controllers;

import com.tucfinancymanager.backend.DTOs.auth.AuthRequestDTO;
import com.tucfinancymanager.backend.DTOs.auth.AuthResponseDTO;
import com.tucfinancymanager.backend.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação", description = "Central de Autenticação")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping
    @Operation(summary = "Serviço de Login", description = "Essa função é responsável por realizar a autenticação do usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = AuthResponseDTO.class))
            }),
            @ApiResponse(responseCode = "401", description = "Senha incorreta"),
            @ApiResponse(responseCode = "404", description = "Usuario não existe")
    })
    public ResponseEntity<AuthResponseDTO> auth (@RequestBody AuthRequestDTO authRequestDTO) {
        var result = this.authService.auth(authRequestDTO);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
}
