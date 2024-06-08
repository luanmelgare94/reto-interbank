package com.reto.interbank.services.service.generic;

import reactor.core.publisher.Flux;

public interface GenericService<T, ID> {

    public Flux<T> getByCustomerId(ID customerId);

}