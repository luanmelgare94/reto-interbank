package com.reto.interbank.services.service.impl;

import com.reto.interbank.services.entity.CustomerEntity;
import com.reto.interbank.services.repository.CustomerRepository;
import com.reto.interbank.services.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public Mono<CustomerEntity> getById(String id) {
        return customerRepository.findById(id);
    }

}