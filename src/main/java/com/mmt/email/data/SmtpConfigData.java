package com.mmt.email.data;

import lombok.Data;

@Data
public class SmtpConfigData {
    private String host; //example: smtp.gmail.com, smtp.yahoo.com, smtp.abcd.com
    private String port; //example: 587
    private String requireTls; //example: true
    private String requireAuthentication; //example: true
}
