package com.tucfinancymanager.backend.services;

import com.tucfinancymanager.backend.entities.PasswordResetToken;
import com.tucfinancymanager.backend.entities.User;
import com.tucfinancymanager.backend.exceptions.NotFoundException;
import com.tucfinancymanager.backend.repositories.PasswordResetTokenRepository;
import com.tucfinancymanager.backend.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
public class PasswordResetService {

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final SecureRandom random = new SecureRandom();

    public void sendResetCode(String email) {
        // Verificar se o usuário existe
        User user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        // Gerar código de 6 dígitos
        String code = generateSixDigitCode();

        // Criar token de reset
        PasswordResetToken token = new PasswordResetToken();
        token.setEmail(email);
        token.setCode(code);
        token.setExpiresAt(LocalDateTime.now().plusMinutes(15)); // Expira em 15 minutos
        token.setUsed(false);

        passwordResetTokenRepository.save(token);

        // Enviar email
        String subject = "Código de Recuperação de Senha - TUC Finance Manager";
        String body = String.format(
                "Olá %s,<br><br>" +
                "Você solicitou a recuperação de sua senha.<br>" +
                "Seu código de verificação é: <strong>%s</strong><br><br>" +
                "Este código expira em 15 minutos.<br><br>" +
                "Se você não solicitou esta recuperação, ignore este email.",
                user.getName(), code
        );

        emailService.sendMail(email, user.getName(), subject, body);
    }

    public boolean verifyCode(String email, String code) {
        return passwordResetTokenRepository
                .findValidToken(email, code, LocalDateTime.now())
                .isPresent();
    }

    public void resetPassword(String email, String code, String newPassword) {
        // Verificar se o token é válido
        PasswordResetToken token = passwordResetTokenRepository
                .findValidToken(email, code, LocalDateTime.now())
                .orElseThrow(() -> new NotFoundException("Código inválido ou expirado"));

        // Buscar o usuário
        User user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        // Atualizar a senha
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        usersRepository.save(user);

        // Marcar o token como usado
        token.setUsed(true);
        passwordResetTokenRepository.save(token);
    }

    private String generateSixDigitCode() {
        return String.format("%06d", random.nextInt(1000000));
    }
}
