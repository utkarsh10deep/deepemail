package com.mmt.email.controller;

import com.mmt.email.data.EmailRequestData;
import com.mmt.email.data.KeyData;
import com.mmt.email.securitykey.KeyGenerator;
import com.mmt.email.securitykey.TrippleDes;
import com.mmt.email.send.SendEmail;
import com.mmt.email.send.SendHtmlMail;
import com.mmt.email.send.TriggerEmail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

//{
//        "sourceEmailId": "emaildeep10@gmail.com", "password" : "emaildeep1010", "targetEmailId":"10utkars@gmail.com",
//        "subject":"TEST EMAIL 5", "body" : "<div><h3 style=\"color:red\">Hello World!</h3><br/><p>How are you?</p><br/><h5>Regards,</h5><h4>Utkarsh Deep</h4></div>", "isHtml":"true"
//
//}


//krUcXXjApi8MUhUjznCsQw==


@Slf4j
@RestController
@RequestMapping("/requestemail")
@Component
public class HandleRequest {
    @Autowired
    private TriggerEmail triggerEmail;
    @Autowired
    private KeyGenerator keyGenerator;
    @Autowired
    private SendEmail sendEmail;
    @Autowired
    private SendHtmlMail sendHtmlMail;

    @RequestMapping(value="/trigger", method= RequestMethod.POST, consumes= MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String trigger(@RequestBody EmailRequestData emailRequestData) throws MessagingException {
        triggerEmail.trigger(emailRequestData);
        return "E-MAIL TRIGERRED";
    }
    @RequestMapping(value="/send", method= RequestMethod.POST, consumes= MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String send(@RequestBody EmailRequestData emailRequestData) throws MessagingException {
        if(emailRequestData.getIsHtml().equalsIgnoreCase("true"))
        {
            sendHtmlMail.sendMail(emailRequestData);
        }
        else
        {
            sendEmail.sendMail(emailRequestData);
        }
        return "E-MAIL SENT";
    }
    @RequestMapping(value="/getKey", method= RequestMethod.POST, consumes= MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String getKey(@RequestBody KeyData keyData) {
        return keyGenerator.generateKey(keyData.getUsername(),keyData.getPassword());

    }
}
