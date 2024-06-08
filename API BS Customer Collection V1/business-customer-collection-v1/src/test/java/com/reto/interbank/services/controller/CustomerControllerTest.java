package com.reto.interbank.services.controller;

import static com.reto.interbank.services.utils.TestUtil.fromFileFactory;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.reto.interbank.services.entity.CustomerEntity;
import com.reto.interbank.services.helper.CustomerHelper;
import com.reto.interbank.services.mapper.CustomerMapper;
import com.reto.interbank.services.repository.CustomerRepository;
import com.reto.interbank.services.service.CustomerService;
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
import reactor.core.publisher.Mono;

@WebFluxTest(controllers = CustomerController.class)
@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    CustomerService customerService;

    @MockBean
    AesEncryptor aesEncryptor;

    @MockBean
    CustomerHelper customerHelper;

    @MockBean
    CustomerMapper customerMapper;

    @MockBean
    CustomerRepository customerRepository;

    String idEncrypt;

    String idDecrypt;

    MultiValueMap<String, String> queryParams;

    @BeforeEach
    void setUp() {
        idEncrypt = "UFYEIUUO97jLMmeAAnChXbMlpg/Zo2maTQb0KRPjfac=";
        idDecrypt = "6660f6e4707a071685b6da52";
        queryParams = new LinkedMultiValueMap<>();
        queryParams.add("id", idEncrypt);
    }

    @Test
    @DisplayName("Return Detail Customer When id Exists")
    void returnDetailCustomerWhenIdExists() throws IOException {
        CustomerEntity customerEntity = fromFileFactory(
                "mocks/customer_entity_data.json", CustomerEntity.class);
        CustomerEntity customerEntityEncrypt = fromFileFactory(
                "mocks/customer_entity_data_with_id_encrypt.json", CustomerEntity.class);

        when(customerRepository.findById(
                any(String.class)))
                .thenReturn(Mono.just(customerEntity));

        when(customerHelper.decryptedId(
                aesEncryptor, idEncrypt))
                .thenReturn(idDecrypt);

        when(customerService.getById(
                idDecrypt))
                .thenReturn(Mono.just(customerEntity));

        when(customerHelper.encrypt(
                aesEncryptor, customerEntity))
                .thenReturn(customerEntityEncrypt);

        WebTestClient.ResponseSpec responseSpec = webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/customers/detail")
                        .queryParams(queryParams)
                        .build())
                .headers(ApiTestUtil::addHeadersCustomers)
                .exchange();

        responseSpec.expectStatus()
                .isOk();
    }

    @Test
    @DisplayName("Return Detail Customer When id Not Exists")
    void returnDetailCustomerWhenIdNotExists() {

        when(customerRepository.findById(
                any(String.class)))
                .thenReturn(Mono.empty());

        when(customerHelper.decryptedId(
                aesEncryptor, idEncrypt))
                .thenReturn(idDecrypt);

        when(customerService.getById(
                idDecrypt))
                .thenReturn(Mono.empty());

        WebTestClient.ResponseSpec responseSpec = webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/customers/detail")
                        .queryParams(queryParams)
                        .build())
                .headers(ApiTestUtil::addHeadersCustomers)
                .exchange();

        responseSpec.expectStatus()
                .isNoContent();
    }

}