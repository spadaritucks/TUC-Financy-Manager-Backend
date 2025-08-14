package com.tucfinancymanager.backend.services;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendMail(String receiverEmail, String receiverName, String subject, String body) {

        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            String template = loadHTMLTemplate();

            template = template.replace("${name}", receiverName)
                    .replace("${body}", body);

            helper.setSubject(subject);
            helper.setTo(receiverEmail);
            helper.setText(template, true);

            emailSender.send(message);

        } catch (Exception e) {
            throw new Error("Erro ao enviar o email: " + e.getMessage());
        }
    }

    public String loadHTMLTemplate() throws IOException {
        ClassPathResource resource = new ClassPathResource("templates/email.html");
        return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
    }

}
