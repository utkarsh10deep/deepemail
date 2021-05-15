package com.mmt.email.send;

import com.mmt.email.builders.RunnableBuilder;
import com.mmt.email.data.EmailRequestData;
import com.mmt.email.data.ExecutorThreadPool;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class TriggerEmail {
    @Autowired
    private RunnableBuilder runnableBuilder;
    @Autowired
    private ExecutorThreadPool executorThreadPool;


    public void trigger(@NonNull EmailRequestData data) {

        Runnable runnable = runnableBuilder.getRunnable(data);

        executorThreadPool.getExecutorService().execute(runnable);

    }
}
