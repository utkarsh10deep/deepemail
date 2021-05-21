package com.mmt.email.controller;

import com.mmt.email.data.EmailRequestData;
import com.mmt.email.data.KeyData;
import com.mmt.email.handler.ErrorHandler;
import com.mmt.email.securitykey.TrippleDes;
import com.mmt.email.send.SendEmail;
import com.mmt.email.send.TriggerEmail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/deepemail")
@Component
public class HandleRequest {
    @Autowired
    private TriggerEmail triggerEmail;
    @Autowired
    private TrippleDes trippleDes;
    @Autowired
    private SendEmail sendEmail;
    @Autowired
    private ErrorHandler errorHandler;

    @CrossOrigin
    @RequestMapping(value = "/trigger", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    String trigger(@RequestBody EmailRequestData emailRequestData) {
        triggerEmail.trigger(emailRequestData);
        return "E-MAIL TRIGERRED - ETA 20 SEC";
    }

    @CrossOrigin
    @RequestMapping(value = "/send", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    String send(@RequestBody EmailRequestData emailRequestData) {
        sendEmail.send(emailRequestData);
        if (errorHandler.isError()) {
            return errorHandler.getErrorMessage();
        }
        return "E-MAIL SENT";
    }

    @CrossOrigin
    @RequestMapping(value = "/getkey", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    String getKey(@RequestBody KeyData keyData) {
        errorHandler.resetErrorMessage();
        String key = trippleDes.encrypt(keyData.getUsername(), keyData.getPassword());
        if (errorHandler.isError()) {
            return errorHandler.getErrorMessage();
        } else {
            return key;
        }

    }
}
