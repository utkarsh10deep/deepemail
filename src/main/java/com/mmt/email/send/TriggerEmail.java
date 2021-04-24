package com.mmt.email.send;

import com.mmt.email.data.EmailRequestData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@Slf4j
public class TriggerEmail {
    @Autowired
    private SendEmail sendEmail;
    @Autowired
    private SendHtmlMail sendHtmlMail;

    private ExecutorService executorService = Executors.newFixedThreadPool(20);

    public void trigger(EmailRequestData data) {

        Runnable runnable = null;
        if(data.getIsHtml().equalsIgnoreCase("true"))
        {
            runnable = () -> {
                try {
                    sendHtmlMail.sendMail(data);
                }
                catch (MessagingException e) {
                    log.error("MessagingException: Error while processing a trigger html-email request");
                    log.error(e.toString());
                }
                catch (Exception e) {
                    log.error("Error while processing a trigger html-email request");
                    log.error(e.toString());
                }
            };
        }


        else
        {
            runnable = () -> {
                try {
                    sendEmail.sendMail(data);
                }
                catch (Exception e) {
                    log.error("Error while processing a trigger email request");
                    log.error(e.toString());
                }
            };
        }
        executorService.execute(runnable);

    }
}
