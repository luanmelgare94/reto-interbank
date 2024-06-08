package com.reto.interbank.services.repository;

import com.reto.interbank.services.entity.FinancialEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface FinancialRepository extends ReactiveMongoRepository<FinancialEntity, String> {

    Flux<FinancialEntity> findByCustomerId(String customerId);

}