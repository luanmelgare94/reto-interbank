package com.reto.interbank.services.controller;

import com.reto.interbank.services.model.CustomerResponse;
import com.reto.interbank.services.service.CustomerService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public Mono<ResponseEntity<CustomerResponse>> getCustomer(
            @RequestHeader() String traceId, @RequestParam() String id) {
        return customerService.getCustomer(traceId, id)
                .map(customerResponse -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(customerResponse))
                .defaultIfEmpty(ResponseEntity.noContent().build());
    }

}