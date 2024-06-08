package com.reto.interbank.services.utils;

import org.springframework.http.HttpHeaders;

public class ApiTestUtil {

    public static void addHeadersFinancialProducts(HttpHeaders headers) {
        headers.add("traceId", "123");
    }

}