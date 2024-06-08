package com.reto.interbank.services.controller;

import com.reto.interbank.services.dto.FinancialListResponse;
import com.reto.interbank.services.helper.ControllerHelper;
import com.reto.interbank.services.mapper.FinancialMapper;
import com.reto.interbank.services.service.FinancialService;
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
@RequestMapping("/financials")
public class FinancialController {

    private final FinancialMapper financialMapper;
    private final FinancialService financialService;
    private final AesEncryptor aesEncryptor;
    private final ControllerHelper controllerHelper;

    public FinancialController(
            FinancialMapper financialMapper,
            FinancialService financialService,
            AesEncryptor aesEncryptor,
            ControllerHelper controllerHelper) {
        this.financialMapper = financialMapper;
        this.financialService = financialService;
        this.aesEncryptor = aesEncryptor;
        this.controllerHelper = controllerHelper;
    }

    @GetMapping("/detail")
    public Mono<ResponseEntity<FinancialListResponse>> getFinancialProducts(
            @RequestHeader("traceId") String traceId, @RequestParam() String customerId) {
        return financialService
                .getByCustomerId(
                        controllerHelper
                                .decryptedCustomerId(aesEncryptor, customerId))
                .collectList()
                .flatMap(financials -> {
                    if (financials.isEmpty()) {
                        return Mono.just(ResponseEntity.noContent().build());
                    } else {
                        return Mono.just(ResponseEntity.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(FinancialListResponse
                                        .builder()
                                        .products(financialMapper
                                                .mapToListFinancialResponse(financials))
                                        .build()));
                    }
                });
    }

}