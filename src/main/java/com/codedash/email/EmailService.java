package com.codedash.email;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendVerificationEmail(
            String to,
            String verificationLink
    ) {

        SimpleMailMessage message =
                new SimpleMailMessage();

        message.setTo(to);

        message.setSubject(
                "Verify your CodeDash account"
        );

        message.setText(
                """
                Welcome to CodeDash.

                Verify your account using the link below:

                %s
                """.formatted(verificationLink)
        );

        mailSender.send(message);
    }
}