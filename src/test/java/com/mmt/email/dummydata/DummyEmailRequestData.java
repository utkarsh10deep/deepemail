package com.mmt.email.dummydata;

import com.mmt.email.data.EmailRequestData;

import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class DummyEmailRequestData {
    public EmailRequestData getDummyData() throws InvalidKeySpecException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        /**
         * If you use your actual emails and actual password here,
         * actual email will be sent when you run unit tests.
         */
        String username = "kuchbhi@gmail.com";
        String password = "abrakadabra123";
        String targetEmail = "meraman@gmail.com";
        String key = new DummyKeyGenerator(username).encrypt(password);
        EmailRequestData emailRequestData = new EmailRequestData();
        emailRequestData.setSourceEmailId(username);
        emailRequestData.setPassword(key);
        emailRequestData.setTargetEmailId(targetEmail);
        emailRequestData.setSubject("TEST_EMAIL10");
        emailRequestData.setBody("<div><h3 style=\\\"color:red\\\">Hello World!</h3><br/><p>You're getting this email is an indication that someone has triggerred unit-tests to test your DeepEmail REST APIs.</p><br/><h5>Regards,</h5><h4>Utkarsh Deep</h4></div>");
        emailRequestData.setIsHtml("true");
        return emailRequestData;
    }
}
