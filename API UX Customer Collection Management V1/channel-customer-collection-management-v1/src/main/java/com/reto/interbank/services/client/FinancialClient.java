package com.reto.interbank.services.client;

import com.reto.interbank.services.model.FinancialList;
import reactor.core.publisher.Mono;

public interface FinancialClient {

    Mono<FinancialList> getFinancialProducts(String traceId, String customerId);

}