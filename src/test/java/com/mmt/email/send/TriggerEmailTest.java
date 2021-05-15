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
import java.util.concurrent.ExecutorService;

@ExtendWith(MockitoExtension.class)
class TriggerEmailTest {

    @Spy
    @InjectMocks
    private TriggerEmail triggerEmail;
    @Mock
    private RunnableBuilder runnableBuilder;
    @Mock
    private ExecutorThreadPool executorThreadPool;

    @Test
    void trigger() throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException, NoSuchPaddingException, UnsupportedEncodingException {
        Runnable runnableMock = Mockito.mock(Runnable.class);
        ExecutorService executorServiceMock = Mockito.mock(ExecutorService.class);
        EmailRequestData emailRequestData = new DummyEmailRequestData().getDummyData();
        Mockito.when(runnableBuilder.getRunnable(ArgumentMatchers.any())).thenReturn(runnableMock);
        Mockito.when(executorThreadPool.getExecutorService()).thenReturn(executorServiceMock);
        triggerEmail.trigger(emailRequestData);
        Mockito.verify(runnableBuilder).getRunnable(ArgumentMatchers.any());
        Mockito.verify(executorThreadPool).getExecutorService();
        Mockito.verify(executorServiceMock).execute(ArgumentMatchers.any());
    }
}