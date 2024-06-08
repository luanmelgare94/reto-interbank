package com.reto.interbank.services.configuration;

import com.reto.interbank.services.util.AesEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AesConfiguration {

    @Value("${aes.secret}")
    private String secret;

    @Bean
    public AesEncryptor aesEncryptor() {
        return new AesEncryptor(secret);
    }

}