package com.mmt.email.send;

import com.mmt.email.builders.RunnableBuilder;
import com.mmt.email.data.EmailRequestData;
import com.mmt.email.data.ExecutorThreadPool;
import com.mmt.email.dummydata.DummyEmailRequestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.concurrent.*;

@ExtendWith(MockitoExtension.class)
class SendEmailTest {

    @InjectMocks
    @Spy
    private SendEmail sendEmail;
    @Mock
    private RunnableBuilder runnableBuilder;
    @Mock
    private ExecutorThreadPool executorThreadPool;

    @Test
    void send() throws InterruptedException, ExecutionException, TimeoutException, NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException, NoSuchPaddingException, UnsupportedEncodingException {
        EmailRequestData emailRequestData = new DummyEmailRequestData().getDummyData();
        Runnable runnableMock = Mockito.mock(Runnable.class);
        ExecutorService executorServiceMock = Mockito.mock(ExecutorService.class);
        Future futureMock = Mockito.mock(Future.class);
        Mockito.when(runnableBuilder.getRunnable(ArgumentMatchers.any())).thenReturn(runnableMock);
        Mockito.when(executorThreadPool.getExecutorService()).thenReturn(executorServiceMock);
        Mockito.when(executorServiceMock.submit(runnableMock)).thenReturn(futureMock);

        sendEmail.send(emailRequestData);

        Mockito.verify(runnableBuilder).getRunnable(ArgumentMatchers.any());
        Mockito.verify(executorThreadPool).getExecutorService();
        Mockito.verify(executorServiceMock).submit(runnableMock);
        Mockito.verify(futureMock).get(ArgumentMatchers.anyLong(), ArgumentMatchers.any(TimeUnit.class));

    }
}