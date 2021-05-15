package com.mmt.email.builders;

import com.mmt.email.data.EmailRequestData;
import com.mmt.email.dummydata.DummyEmailRequestData;
import com.mmt.email.securitykey.TrippleDes;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MailSenderBuilderTest {
    @Mock
    private TrippleDes trippleDes;
    @Spy
    @InjectMocks
    private MailSenderBuilder mailSenderBuilder;

    @Test
    void getJavaMailSender() throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException, NoSuchPaddingException, UnsupportedEncodingException {
        when(trippleDes.decrypt(anyString(), anyString())).thenReturn("abrakadabra123");
        EmailRequestData emailRequestData = new DummyEmailRequestData().getDummyData();
        JavaMailSender mailSender = mailSenderBuilder.getJavaMailSender(emailRequestData);
        verify(trippleDes).decrypt(anyString(), anyString());
        Assert.assertNotNull(mailSender);
    }
}