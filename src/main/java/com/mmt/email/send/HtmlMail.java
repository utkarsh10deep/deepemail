package com.mmt.email.send;

import com.mmt.email.builders.MailSenderBuilder;
import com.mmt.email.data.EmailRequestData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class HtmlMail {
    @Autowired
    private MailSenderBuilder mailSenderBuilder;

    public void sendMail(EmailRequestData emailRequestData) throws MessagingException {
        JavaMailSender mailSender = mailSenderBuilder.getJavaMailSender(emailRequestData);
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        String htmlMsg = emailRequestData.getBody();
        helper.setText(htmlMsg, true);
        helper.setTo(emailRequestData.getTargetEmailId());
        helper.setSubject(emailRequestData.getSubject());
        helper.setFrom(emailRequestData.getSourceEmailId());
        mailSender.send(mimeMessage);
    }
}
