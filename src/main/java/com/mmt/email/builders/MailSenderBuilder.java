package com.mmt.email.builders;

import com.mmt.email.data.EmailRequestData;
import com.mmt.email.data.SmtpConfigData;
import com.mmt.email.securitykey.TrippleDes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Slf4j
@Component
public class MailSenderBuilder {

    @Autowired
    private TrippleDes trippleDes;

    public JavaMailSender getJavaMailSender(EmailRequestData emailRequestData) {

        SmtpConfigData configData = emailRequestData.getConfigData();
        if (configData == null) {
            log.info("Sender Email: {}", emailRequestData.getSourceEmailId());
            log.info("No smtp config in json request. Default config will be set for gmail.com");
            log.info("host: smtp.gmail.com");
            log.info("port: 587");
            log.info("require authentication: true");
            log.info("require tls: true");
            return this.getJavaMailSenderGmail(emailRequestData);
        } else {
            log.info("Sender email: {}", emailRequestData.getSourceEmailId());
            log.info("Smtp config found in json request.");
            log.info("host: {}", configData.getHost());
            log.info("port: {}", configData.getPort());
            if (configData.getRequireAuthentication().equalsIgnoreCase("false")) {
                log.info("requireAuthentication: false");
            } else {
                log.info("requireAuthentication: true");
            }
            if (configData.getRequireTls().equalsIgnoreCase("false")) {
                log.info("require TLS: false");
            } else {
                log.info("require TLS: true");
            }
            return this.getJavaMailSenderWithConfig(emailRequestData);
        }
    }

    private JavaMailSender getJavaMailSenderGmail(EmailRequestData emailRequestData) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername(emailRequestData.getSourceEmailId());
        mailSender.setPassword(this.decodePassword(emailRequestData.getSourceEmailId(), emailRequestData.getPassword()));
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }

    private JavaMailSender getJavaMailSenderWithConfig(EmailRequestData emailRequestData) {
        SmtpConfigData configData = emailRequestData.getConfigData();
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(configData.getHost());
        mailSender.setPort(Integer.parseInt(configData.getPort()));
        mailSender.setUsername(emailRequestData.getSourceEmailId());
        mailSender.setPassword(this.decodePassword(emailRequestData.getSourceEmailId(), emailRequestData.getPassword()));
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        if (configData.getRequireAuthentication().equalsIgnoreCase("false")) {
            props.put("mail.smtp.auth", "false");
        } else {
            props.put("mail.smtp.auth", "true");
        }
        if (configData.getRequireTls().equalsIgnoreCase("false")) {
            props.put("mail.smtp.starttls.enable", "false");
        } else {
            props.put("mail.smtp.starttls.enable", "true");
        }
        props.put("mail.debug", "true");

        return mailSender;
    }

    private String decodePassword(String username, String encryptedPassword) {
        return trippleDes.decrypt(username, encryptedPassword);
    }
}
