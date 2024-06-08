package com.reto.interbank.services.service;

import com.reto.interbank.services.model.CustomerResponse;
import reactor.core.publisher.Mono;

public interface CustomerService {

    Mono<CustomerResponse> getCustomer(String traceId, String id);

}