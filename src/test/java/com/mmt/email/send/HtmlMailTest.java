package com.mmt.email.send;

import com.mmt.email.builders.MailSenderBuilder;
import com.mmt.email.data.EmailRequestData;
import com.mmt.email.dummydata.DummyEmailRequestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

import javax.crypto.NoSuchPaddingException;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class HtmlMailTest {
    @Mock
    private MailSenderBuilder mailSenderBuilder;
    @Spy
    @InjectMocks
    private HtmlMail htmlMail;

    @Test
    void sendMail() throws MessagingException, NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException, NoSuchPaddingException, UnsupportedEncodingException {
        JavaMailSender mailSenderMock = Mockito.mock(JavaMailSender.class);
        Mockito.when(mailSenderBuilder.getJavaMailSender(any())).thenReturn(mailSenderMock);
        MimeMessage mimeMessage = Mockito.mock(MimeMessage.class);
        Mockito.when(mailSenderMock.createMimeMessage()).thenReturn(mimeMessage);
        EmailRequestData emailRequestData = new DummyEmailRequestData().getDummyData();
        htmlMail.sendMail(emailRequestData);
        Mockito.verify(mailSenderBuilder).getJavaMailSender(any());


    }
}