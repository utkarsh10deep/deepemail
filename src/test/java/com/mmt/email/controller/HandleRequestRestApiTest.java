package com.mmt.email.controller;

import com.mmt.email.AbstractRestApiTest;
import com.mmt.email.data.EmailRequestData;
import com.mmt.email.data.KeyData;
import com.mmt.email.dummydata.DummyEmailRequestData;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;


class HandleRequestRestApiTest extends AbstractRestApiTest {
    @Test
    void trigger() throws Exception {
        String url = "/deepemail/trigger";
        EmailRequestData emailRequestData = new DummyEmailRequestData().getDummyData();
        String inputJson = super.mapToJson(emailRequestData);
        super.setUp();
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        System.out.println("STATUS: " + status);
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        boolean isTriggered = content.contains("E-MAIL TRIGERRED");
        Assert.assertEquals(true, isTriggered);
    }

    @Test
    void send() throws Exception {
        String url = "/deepemail/send";
        EmailRequestData emailRequestData = new DummyEmailRequestData().getDummyData();
        String inputJson = super.mapToJson(emailRequestData);
        super.setUp();
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        System.out.println("STATUS: " + status);
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        boolean isTriggered = content.contains("E-MAIL SENT");
        Assert.assertEquals(true, isTriggered);
    }

    @Test
    void getKey() throws Exception {
        String url = "/deepemail/getKey";
        KeyData keyData = new KeyData();
        keyData.setPassword("AbraKaDaabra1234");
        keyData.setUsername("kuchbhi@meraman.com");
        String inputJson = super.mapToJson(keyData);
        super.setUp();
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        System.out.println("STATUS: " + status);
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(content);
        Assert.assertNotEquals("", content);
    }
}