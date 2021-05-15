package com.mmt.email.send;

import com.mmt.email.builders.MailSenderBuilder;
import com.mmt.email.data.EmailRequestData;
import com.mmt.email.dummydata.DummyEmailRequestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@ExtendWith(MockitoExtension.class)
class SimpleEmailTest {

    @InjectMocks
    @Spy
    private SimpleEmail simpleEmail;
    @Mock
    private MailSenderBuilder mailSenderBuilder;

    @Test
    void sendMail() throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException, NoSuchPaddingException, UnsupportedEncodingException {
        EmailRequestData emailRequestData = new DummyEmailRequestData().getDummyData();
        JavaMailSender javaMailSenderMock = Mockito.mock(JavaMailSender.class);
        Mockito.when(mailSenderBuilder.getJavaMailSender(ArgumentMatchers.any())).thenReturn(javaMailSenderMock);
        simpleEmail.sendMail(emailRequestData);
        Mockito.verify(mailSenderBuilder).getJavaMailSender(ArgumentMatchers.any());
        Mockito.verify(simpleEmail).sendMail(ArgumentMatchers.any());
    }
}