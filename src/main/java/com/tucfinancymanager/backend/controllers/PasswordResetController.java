package com.tucfinancymanager.backend.controllers;


import com.tucfinancymanager.backend.DTOs.passwordreset.PasswordResetRequestDTO;
import com.tucfinancymanager.backend.DTOs.passwordreset.ResetPasswordRequestDTO;
import com.tucfinancymanager.backend.DTOs.passwordreset.VerifyCodeRequestDTO;
import com.tucfinancymanager.backend.services.PasswordResetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/password-reset")
@Tag(name = "Recuperação de Senha", description = "Serviços para recuperação de senha")
public class PasswordResetController {

    @Autowired
    private PasswordResetService passwordResetService;

    @PostMapping("/send-code")
    @Operation(summary = "Enviar código de recuperação", description = "Envia um código de 6 dígitos para o email do usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Código enviado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<String> sendResetCode(@Valid @RequestBody PasswordResetRequestDTO request) {
        System.out.println(request);
        passwordResetService.sendResetCode(request.getEmail());
        return new ResponseEntity<>("Código de verificação enviado para o email", HttpStatus.OK);
    }

    @PostMapping("/verify-code")
    @Operation(summary = "Verificar código", description = "Verifica se o código de recuperação é válido")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Código válido"),
            @ApiResponse(responseCode = "400", description = "Código inválido ou expirado")
    })
    public ResponseEntity<String> verifyCode(@Valid @RequestBody VerifyCodeRequestDTO request) {
        boolean isValid = passwordResetService.verifyCode(request.getEmail(), request.getCode());
        if (isValid) {
            return new ResponseEntity<>("Código verificado com sucesso", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Código inválido ou expirado", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/reset-password")
    @Operation(summary = "Redefinir senha", description = "Redefine a senha do usuário usando o código de verificação")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Senha alterada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Código inválido ou expirado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<String> resetPassword(@Valid @RequestBody ResetPasswordRequestDTO request) {
        passwordResetService.resetPassword(request.getEmail(), request.getCode(), request.getNewPassword());
        return new ResponseEntity<>("Senha alterada com sucesso", HttpStatus.OK);
    }
}
