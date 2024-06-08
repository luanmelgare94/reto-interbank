package com.reto.interbank.services.service.impl;

import com.reto.interbank.services.client.CustomerClient;
import com.reto.interbank.services.client.FinancialClient;
import com.reto.interbank.services.mapper.CustomerMapper;
import com.reto.interbank.services.model.CustomerResponse;
import com.reto.interbank.services.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerClient customerClient;

    private final FinancialClient financialClient;

    private final CustomerMapper customerMapper;

    @Override
    public Mono<CustomerResponse> getCustomer(String traceId, String id) {
        return customerClient.getDetailCustomer(traceId, id)
                .zipWith(financialClient.getFinancialProducts(traceId, id),
                        customerMapper::mapToCustomerResponse);
    }

}