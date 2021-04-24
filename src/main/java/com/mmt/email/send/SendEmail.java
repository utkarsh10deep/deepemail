package com.mmt.email.send;

import com.mmt.email.data.EmailRequestData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
//import javax.mail.*;

//import javax.mail.internet.*;
//import javax.activation.*;

@Component
@Slf4j
public class SendEmail {

    @Autowired
    private MailSenderBuilder mailSenderBuilder;

    public void sendMail(EmailRequestData emailRequestData)
    {
        JavaMailSender mailSender = mailSenderBuilder.getJavaMailSender(emailRequestData);
        SimpleMailMessage message = new SimpleMailMessage();
        //message.setFrom("noreply@utkarshdeep.com");
        message.setFrom(emailRequestData.getSourceEmailId());
        message.setTo(emailRequestData.getTargetEmailId());
        message.setSubject(emailRequestData.getSubject());
        message.setText(emailRequestData.getBody());
        mailSender.send(message);
    }
}
