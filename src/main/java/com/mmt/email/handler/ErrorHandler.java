package com.mmt.email.handler;

import org.springframework.stereotype.Component;

@Component
public class ErrorHandler {
    private String errorMessage = "";

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setErrorMessage(Exception errorMessage) {
        this.errorMessage = errorMessage.toString();
    }

    public void resetErrorMessage() {
        this.errorMessage = "";
    }

    public boolean isError() {
        return this.errorMessage != null && !this.errorMessage.equals("");
    }

}
