package com.reto.interbank.services.client;

import com.reto.interbank.services.model.Customer;
import reactor.core.publisher.Mono;

public interface CustomerClient {

    Mono<Customer> getDetailCustomer(String traceId, String id);

}