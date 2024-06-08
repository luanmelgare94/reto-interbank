package com.reto.interbank.services.helper;

import com.reto.interbank.services.util.AesEncryptor;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.Optional;

import static com.reto.interbank.services.util.Constants.PATTERN_FORMAT_THOUSAND_SEPARATOR;

@Component
public class ControllerHelper {

    public String decryptedCustomerId(AesEncryptor aesEncryptor, String customerId) {
        try {
            return decrypt(aesEncryptor, customerId);
        } catch (Exception e) {
            throw new RuntimeException("Error desencriptando el campo", e);
        }
    }

    private String decrypt(AesEncryptor aesEncryptor, String id) {
        try {
            return aesEncryptor.decrypt(id);
        } catch (Exception e) {
            throw new RuntimeException("Error desencriptando el campo", e);
        }
    }

}