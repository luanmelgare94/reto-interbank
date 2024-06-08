package com.reto.interbank.services.client.impl;

import com.reto.interbank.services.client.FinancialClient;
import com.reto.interbank.services.model.FinancialList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class FinancialClientImpl implements FinancialClient {

    @Value("${method.financial.uri}")
    private String methodFinancialUri;

    @Value("${method.financial.param.customerId}")
    private String paramCustomerId;

    @Value("${method.header.authorization}")
    private String headerAuthorization;

    @Value("${method.header.traceId}")
    private String headerTraceId;

    private final WebClient webClient;

    public FinancialClientImpl(@Value("${path.financial}") String pathFinancial) {
        this.webClient = WebClient.create(pathFinancial);
    }

    @Override
    public Mono<FinancialList> getFinancialProducts(String traceId, String customerId) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(methodFinancialUri)
                        .queryParam(paramCustomerId, customerId)
                        .build())
                .header(headerAuthorization, "123")
                .header(headerTraceId, "123")
                .retrieve()
                .onStatus(HttpStatus.NO_CONTENT::equals, clientResponse -> Mono.empty())
                .bodyToMono(FinancialList.class);
    }

}