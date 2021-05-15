package com.mmt.email.data;

import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class ExecutorThreadPool {
    private final ExecutorService executorService = Executors.newFixedThreadPool(20);

    public ExecutorService getExecutorService() {
        return this.executorService;
    }
}
