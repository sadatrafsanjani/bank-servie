package com.sadat.service.general;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private JavaMailSender mailSender;

    @Autowired
    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    @Async
    public void sendPassword(String to, String username, String password) {

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(to);
        mail.setFrom("xerodispatch@gmail.com");
        mail.setSubject("Login Credentials");
        mail.setText("Username: " + username + "\n "
                + "Password: " + password);

        mailSender.send(mail);
    }

    @Override
    @Async
    public void forgetPassword(String to){

        String body = "<a href='http://localhost:4200/reset-password' target='_blank'>Reset Password</a>";

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(to);
        mail.setFrom("xerodispatch@gmail.com");
        mail.setSubject("Login Credentials");
        mail.setText(body);

        mailSender.send(mail);
    }
}
