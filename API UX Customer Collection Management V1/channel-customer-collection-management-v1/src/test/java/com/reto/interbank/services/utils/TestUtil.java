package com.reto.interbank.services.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

public class TestUtil {

    private static final ObjectMapper OBJECT_MAPPER =
            new ObjectMapper().registerModule(new JavaTimeModule());

    public static <T> T fromFileFactory(String path, Class<T> classObject) throws IOException {
        return OBJECT_MAPPER.readValue(new ClassPathResource(path).getInputStream(), classObject);
    }

}