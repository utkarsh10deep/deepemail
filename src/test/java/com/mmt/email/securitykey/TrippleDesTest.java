package com.mmt.email.securitykey;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;

@ExtendWith(MockitoExtension.class)
class TrippleDesTest {

    @Mock
    private Environment environment;
    @InjectMocks
    @Spy
    private TrippleDes trippleDes;

    @Test
    void encrypt() {
        Mockito.when(environment.getProperty(ArgumentMatchers.anyString())).thenReturn("mySecretKeyAddon");
        String encryptedString = trippleDes.encrypt("kuchbhi@meramann.com", "abrakadabra123");
        Assert.assertNotNull(encryptedString);
        Assert.assertNotEquals("", encryptedString);
        Mockito.verify(environment).getProperty(ArgumentMatchers.anyString());
    }

    @Test
    void decrypt() {
        Mockito.when(environment.getProperty(ArgumentMatchers.anyString())).thenReturn("mySecretKeyAddon");
        String encryptedString = trippleDes.encrypt("kuchbhi@meramann.com", "abrakadabra123");
        String decryptedString = trippleDes.decrypt("kuchbhi@meramann.com", encryptedString);
        Assert.assertEquals("abrakadabra123", decryptedString);
        Mockito.verify(environment, Mockito.times(2)).getProperty(ArgumentMatchers.anyString());
    }
}