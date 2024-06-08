package com.reto.interbank.services.controller;

import static com.reto.interbank.services.utils.TestUtil.fromFileFactory;
import static org.mockito.Mockito.when;

import com.reto.interbank.services.client.CustomerClient;
import com.reto.interbank.services.client.FinancialClient;
import com.reto.interbank.services.model.Customer;
import com.reto.interbank.services.model.CustomerResponse;
import com.reto.interbank.services.model.FinancialList;
import com.reto.interbank.services.service.CustomerService;
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
public class CustomerControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    CustomerService customerService;

    @MockBean
    CustomerClient customerClient;

    @MockBean
    FinancialClient financialClient;

    MultiValueMap<String, String> queryParams;

    String id;

    @BeforeEach
    void setUp() {
        id = "UFYEIUUO97jLMmeAAnChXbMlpg/Zo2maTQb0KRPjfac=";
        queryParams = new LinkedMultiValueMap<>();
        queryParams.add("id", id);
    }

    @Test
    @DisplayName("Return Customer When Id Exists")
    void returnCustomerWhenIdExists() throws IOException {
        FinancialList financial = fromFileFactory(
                "mocks/financial_products_response_data.json",
                FinancialList.class);

        Customer customer = fromFileFactory(
                "mocks/customer_response_data.json",
                Customer.class);

        CustomerResponse customerResponse = fromFileFactory(
                "mocks/customer_response.json",
                CustomerResponse.class);

        when(customerClient.getDetailCustomer("123", id)).thenReturn(Mono.just(customer));
        when(financialClient.getFinancialProducts("123", id)).thenReturn(Mono.just(financial));
        when(customerService.getCustomer("123", id)).thenReturn(Mono.just(customerResponse));

        WebTestClient.ResponseSpec responseSpec = webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/customer")
                        .queryParams(queryParams)
                        .build())
                .headers(ApiTestUtil::addHeadersCustomer)
                .exchange();

        responseSpec.expectStatus()
                .isOk();
    }

}