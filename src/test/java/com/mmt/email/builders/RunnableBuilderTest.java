package com.mmt.email.builders;

import com.mmt.email.data.EmailRequestData;
import com.mmt.email.dummydata.DummyEmailRequestData;
import com.mmt.email.send.HtmlMail;
import com.mmt.email.send.SimpleEmail;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.crypto.NoSuchPaddingException;
import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;


@ExtendWith(MockitoExtension.class)
public class RunnableBuilderTest {

    @Spy
    @InjectMocks
    private RunnableBuilder runnableBuilder;
    @Mock
    private SimpleEmail simpleEmail;
    @Mock
    private HtmlMail htmlMail;

    @Test
    public void getRunnable() throws MessagingException, NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException, NoSuchPaddingException, UnsupportedEncodingException {
        EmailRequestData emailRequestData = new DummyEmailRequestData().getDummyData();
        Runnable runnable = runnableBuilder.getRunnable(emailRequestData);
        Mockito.verifyNoInteractions(simpleEmail);
        Mockito.verifyNoInteractions(htmlMail); // no interaction because used inside lambda
        Assert.assertNotNull(runnable);
    }
}