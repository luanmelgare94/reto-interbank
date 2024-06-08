package com.reto.interbank.services.helper;

import com.reto.interbank.services.entity.CustomerEntity;
import com.reto.interbank.services.util.AesEncryptor;
import org.springframework.stereotype.Component;

@Component
public class CustomerHelper {

    public String decryptedId(AesEncryptor aesEncryptor, String id) {
        try {
            return decrypt(aesEncryptor, id);
        } catch (Exception e) {
            throw new RuntimeException("Error desencriptando el campo", e);
        }
    }

    public CustomerEntity encrypt(AesEncryptor aesEncryptor, CustomerEntity customerEntity) {
        try {
            customerEntity.setId(aesEncryptor.encrypt(customerEntity.getId()));
        } catch (Exception e) {
            throw new RuntimeException("Error encriptando el campo", e);
        }
        return customerEntity;
    }

    private String decrypt(AesEncryptor aesEncryptor, String id) {
        try {
            return aesEncryptor.decrypt(id);
        } catch (Exception e) {
            throw new RuntimeException("Error desencriptando el campo", e);
        }
    }

}