package com.example.demo;

import org.springframework.stereotype.Service;

@Service
public class ClientAuthenticationHelper {

    public boolean validateApiKey(String requestApiKey) {

        Restconfig config = DemoApplication.getConfig().getRest();

        System.out.println("Received key is: "+ requestApiKey);

        if (requestApiKey == null || requestApiKey.isEmpty()) {
            return false;
        }

        return requestApiKey.equals(config.getApikey());
    }

}