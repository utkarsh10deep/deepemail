package com.mmt.email.securitykey;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Decryptor {
    public String decrypt(String username, String password)
    {
        TrippleDes trippleDes = null;
        try
        {
            trippleDes = new TrippleDes(username);
        }
        catch (Exception e)
        {
            log.error("Error while decrypting key");
            log.error(e.toString());
        }
        if(trippleDes!=null)
        {
            return trippleDes.decrypt(password);
        }
        else
        {
            return null;
        }
    }
}
