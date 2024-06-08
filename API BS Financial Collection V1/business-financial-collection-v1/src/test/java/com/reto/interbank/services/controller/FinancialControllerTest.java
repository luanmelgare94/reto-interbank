package com.reto.interbank.services.controller;

import static com.reto.interbank.services.utils.TestUtil.fromFileFactory;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.reto.interbank.services.entity.FinancialEntity;
import com.reto.interbank.services.entity.ProductTypeEntity;
import com.reto.interbank.services.helper.ControllerHelper;
import com.reto.interbank.services.mapper.FinancialMapper;
import com.reto.interbank.services.repository.FinancialRepository;
import com.reto.interbank.services.repository.ProductTypeRepository;
import com.reto.interbank.services.service.FinancialService;
import com.reto.interbank.services.util.AesEncryptor;
import com.reto.interbank.services.utils.ApiTestUtil;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@WebFluxTest(controllers = FinancialController.class)
@ExtendWith(MockitoExtension.class)
class FinancialControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    FinancialService financialService;

    @MockBean
    AesEncryptor aesEncryptor;

    @MockBean
    ControllerHelper controllerHelper;

    @MockBean
    FinancialMapper financialMapper;

    @MockBean
    FinancialRepository financialRepository;

    @MockBean
    ProductTypeRepository productTypeRepository;

    String customerIdEncrypt;

    String customerIdDecrypt;

    MultiValueMap<String, String> queryParams;

    @BeforeEach
    void setUp() {
        customerIdEncrypt = "UFYEIUUO97jLMmeAAnChXbMlpg/Zo2maTQb0KRPjfac=";
        customerIdDecrypt = "6660f6e4707a071685b6da52";
        queryParams = new LinkedMultiValueMap<>();
        queryParams.add("customerId", customerIdEncrypt);
    }

    @Test
    @DisplayName("Return Financial Products When CustomerId Exists")
    void returnFinancialProductsWhenCustomerIdExists() throws IOException {
        FinancialEntity financialEntityPartiallyFull = fromFileFactory(
                "mocks/financial_products_entity_partially_full_data.json",
                FinancialEntity.class);

        ProductTypeEntity productType = fromFileFactory(
                "mocks/product_type_entity_data.json",
                ProductTypeEntity.class);

        FinancialEntity financialEntityFull = fromFileFactory(
                "mocks/financial_products_entity_full_data.json",
                FinancialEntity.class);

        when(financialRepository.findByCustomerId(
                any(String.class)))
                .thenReturn(Flux.just(financialEntityPartiallyFull));

        when(productTypeRepository.findById(
                any(String.class)))
                .thenReturn(Mono.just(productType));

        when(controllerHelper.decryptedCustomerId(
                aesEncryptor, customerIdEncrypt))
                .thenReturn(customerIdDecrypt);

        when(financialService.getByCustomerId(
                customerIdDecrypt))
                .thenReturn(Flux.just(financialEntityFull));

        WebTestClient.ResponseSpec responseSpec = webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/financials/detail")
                        .queryParams(queryParams)
                        .build())
                .headers(ApiTestUtil::addHeadersFinancialProducts)
                .exchange();

        responseSpec.expectStatus()
                .isOk();

    }

    @Test
    @DisplayName("Return Financial Products When CustomerId Not Exists")
    void returnFinancialProductsWhenCustomerIdNotExists() {

        when(financialRepository.findByCustomerId(
                any(String.class)))
                .thenReturn(Flux.empty());

        when(productTypeRepository.findById(
                any(String.class)))
                .thenReturn(Mono.empty());

        when(controllerHelper.decryptedCustomerId(
                aesEncryptor, customerIdEncrypt))
                .thenReturn(customerIdDecrypt);

        when(financialService.getByCustomerId(
                customerIdDecrypt))
                .thenReturn(Flux.empty());

        WebTestClient.ResponseSpec responseSpec = webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/financials/detail")
                        .queryParams(queryParams)
                        .build())
                .headers(ApiTestUtil::addHeadersFinancialProducts)
                .exchange();

        responseSpec.expectStatus()
                .isNoContent();

    }

}