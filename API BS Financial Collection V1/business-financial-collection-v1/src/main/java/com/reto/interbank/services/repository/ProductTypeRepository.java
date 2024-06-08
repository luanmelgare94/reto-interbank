package com.reto.interbank.services.repository;

import com.reto.interbank.services.entity.ProductTypeEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ProductTypeRepository extends ReactiveMongoRepository<ProductTypeEntity, String> {

}