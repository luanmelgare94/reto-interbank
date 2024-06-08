package com.reto.interbank.services.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class TestUtil {

    private static final ObjectMapper OBJECT_MAPPER =
            new ObjectMapper().registerModule(new JavaTimeModule());

    public static <T> T fromFileFactory(String path, Class<T> classObject) throws IOException {
        return OBJECT_MAPPER.readValue(new ClassPathResource(path).getInputStream(), classObject);
    }

    public static <T> List<T> getListFromFileFactory(String path, Class<T[]> classObjectArray) throws IOException {
        return Arrays.asList(OBJECT_MAPPER.readValue(new ClassPathResource(path).getInputStream(), classObjectArray));
    }

}