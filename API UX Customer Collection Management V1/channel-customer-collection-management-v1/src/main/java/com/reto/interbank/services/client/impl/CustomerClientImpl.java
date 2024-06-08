package com.reto.interbank.services.client.impl;

import com.reto.interbank.services.client.CustomerClient;
import com.reto.interbank.services.model.Customer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class CustomerClientImpl implements CustomerClient {

    @Value("${method.customer.uri}")
    private String methodCustomerUri;

    @Value("${method.customer.param.id}")
    private String paramId;

    @Value("${method.header.authorization}")
    private String headerAuthorization;

    @Value("${method.header.traceId}")
    private String headerTraceId;

    private final WebClient webClient;

    public CustomerClientImpl(@Value("${path.customer}") String pathCustomer) {
        this.webClient = WebClient.create(pathCustomer);
    }

    @Override
    public Mono<Customer> getDetailCustomer(String traceId, String id) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(methodCustomerUri)
                        .queryParam(paramId, id)
                        .build())
                .header(headerAuthorization, "123")
                .header(headerTraceId, "123")
                .retrieve()
                .onStatus(HttpStatus.NO_CONTENT::equals, clientResponse -> Mono.empty())
                .bodyToMono(Customer.class);
    }

}