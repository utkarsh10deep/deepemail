package com.mmt.email.data;

import lombok.Data;

@Data
public class EmailRequestData {
    private String sourceEmailId;
    private String password;
    private String targetEmailId;
    private String subject;
    private String body;
    private String isHtml;
    private SmtpConfigData configData;
}
