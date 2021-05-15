package com.mmt.email.send;

import com.mmt.email.builders.RunnableBuilder;
import com.mmt.email.data.EmailRequestData;
import com.mmt.email.data.ExecutorThreadPool;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Component
@Slf4j
public class SendEmail {
    @Autowired
    private RunnableBuilder runnableBuilder;
    @Autowired
    private ExecutorThreadPool executorThreadPool;


    public void send(@NonNull EmailRequestData data) {

        Runnable runnable = runnableBuilder.getRunnable(data);
        Future future = executorThreadPool.getExecutorService().submit(runnable);
        try {
            future.get(50, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            log.error(e.toString());
        }

    }
}
