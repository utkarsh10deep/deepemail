package com.mmt.email.send;

import com.mmt.email.builders.MailSenderBuilder;
import com.mmt.email.data.EmailRequestData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SimpleEmail {

    @Autowired
    private MailSenderBuilder mailSenderBuilder;

    public void sendMail(EmailRequestData emailRequestData) {
        JavaMailSender mailSender = mailSenderBuilder.getJavaMailSender(emailRequestData);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailRequestData.getSourceEmailId());
        message.setTo(emailRequestData.getTargetEmailId());
        message.setSubject(emailRequestData.getSubject());
        message.setText(emailRequestData.getBody());
        mailSender.send(message);
    }
}
