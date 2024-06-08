package com.reto.interbank.services.service.generic;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GenericService<T> {

    public Mono<T> getById(String id);

}