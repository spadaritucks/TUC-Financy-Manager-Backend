package com.tucfinancymanager.backend.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.tucfinancymanager.backend.entities.User;
import com.tucfinancymanager.backend.exceptions.JWTGeneratorException;
import com.tucfinancymanager.backend.exceptions.JWTValidatorException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

    public String generateToken(User user) throws JWTGeneratorException {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        String token = JWT.create()
                .withIssuer("tfm-auth-login")
                .withSubject(user.getEmail())
                .withExpiresAt(this.generateExpirationDate())
                .sign(algorithm);

        return token;
    }

    public String validadeToken(String token) throws JWTValidatorException {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.require(algorithm)
                .withIssuer("tfm-auth-login")
                .build()
                .verify(token)
                .getSubject();
    }

}
