package com.tucfinancymanager.backend.exceptions;

public class JWTGeneratorException extends RuntimeException {
    public JWTGeneratorException() {
        super("Erro ao gerar o token de autenticação");
    }
}
