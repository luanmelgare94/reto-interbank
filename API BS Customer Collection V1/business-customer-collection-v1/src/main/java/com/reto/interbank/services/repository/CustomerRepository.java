package com.reto.interbank.services.repository;

import com.reto.interbank.services.entity.CustomerEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CustomerRepository extends ReactiveMongoRepository<CustomerEntity, String> {

}