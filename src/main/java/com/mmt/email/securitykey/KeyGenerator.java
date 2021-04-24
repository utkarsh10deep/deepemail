package com.mmt.email.securitykey;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KeyGenerator {
    public String generateKey(String username, String password) {
        TrippleDes trippleDes = null;
        try
        {
            trippleDes = new TrippleDes(username);

        }
        catch (Exception e)
        {
            log.error("Error while generating key");
            log.error(e.toString());
        }
        if(trippleDes!=null)
        {
            return trippleDes.encrypt(password);
        }
        else
        {
            return null;
        }


    }
}
