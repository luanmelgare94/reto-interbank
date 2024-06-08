package com.reto.interbank.services.controller;

import com.reto.interbank.services.dto.CustomerResponse;
import com.reto.interbank.services.helper.CustomerHelper;
import com.reto.interbank.services.mapper.CustomerMapper;
import com.reto.interbank.services.service.CustomerService;
import com.reto.interbank.services.util.AesEncryptor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/customers")
public class CustomerController{

    private final CustomerMapper customerMapper;
    private final CustomerService customerService;
    private final AesEncryptor aesEncryptor;
    private final CustomerHelper customerHelper;

    public CustomerController(
            CustomerMapper customerMapper,
            CustomerService customerService,
            AesEncryptor aesEncryptor,
            CustomerHelper customerHelper) {
        this.customerMapper = customerMapper;
        this.customerService = customerService;
        this.aesEncryptor = aesEncryptor;
        this.customerHelper = customerHelper;
    }

    @GetMapping("/detail")
    public Mono<ResponseEntity<CustomerResponse>> getDetailCustomer(
            @RequestHeader("traceId") String traceId, @RequestParam() String id) {
        return customerService.getById(customerHelper.decryptedId(aesEncryptor, id))
                .flatMap(customerEntity -> {
                    customerEntity.setId(
                            customerHelper.encrypt(aesEncryptor, customerEntity)
                                    .getId());
                    return Mono.just(customerEntity);
                })
                .map(customer -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(customerMapper.mapToCustomerResponse(customer)))
                .defaultIfEmpty(ResponseEntity.noContent().build());
    }

}