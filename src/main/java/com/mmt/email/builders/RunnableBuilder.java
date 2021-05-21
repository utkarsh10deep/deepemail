package com.mmt.email.builders;

import com.mmt.email.data.EmailRequestData;
import com.mmt.email.handler.ErrorHandler;
import com.mmt.email.send.HtmlMail;
import com.mmt.email.send.SimpleEmail;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;

@Component
@Slf4j
public class RunnableBuilder {
    @Autowired
    private SimpleEmail simpleEmail;
    @Autowired
    private HtmlMail htmlMail;
    @Autowired
    private ErrorHandler errorHandler;

    public Runnable getRunnable(@NonNull EmailRequestData data) {
        Runnable runnable = null;
        if (data.getIsHtml().equalsIgnoreCase("true")) {
            runnable = () -> {
                try {
                    errorHandler.resetErrorMessage();
                    htmlMail.sendMail(data);
                } catch (MessagingException e) {
                    log.error("MessagingException: Error while processing a html-email request");
                    log.error(e.toString());
                    errorHandler.setErrorMessage(e);
                } catch (Exception e) {
                    log.error("Error while processing a html-email request");
                    log.error(e.toString());
                    errorHandler.setErrorMessage(e);
                }
            };
        } else {
            runnable = () -> {
                try {
                    errorHandler.resetErrorMessage();
                    simpleEmail.sendMail(data);
                } catch (Exception e) {
                    log.error("Error while processing a email request");
                    log.error(e.toString());
                    errorHandler.setErrorMessage(e);
                }
            };
        }
        return runnable;
    }
}
