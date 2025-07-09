package com.tucfinancymanager.backend.exceptions;

public class JWTValidatorException extends RuntimeException {
    public JWTValidatorException() {
        super("Erro ao validar o token de autenticação");
    }
}
