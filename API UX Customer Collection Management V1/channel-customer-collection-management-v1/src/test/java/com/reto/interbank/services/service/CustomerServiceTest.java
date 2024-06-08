package com.reto.interbank.services.service;

import static com.reto.interbank.services.utils.TestUtil.fromFileFactory;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import com.reto.interbank.services.client.CustomerClient;
import com.reto.interbank.services.client.FinancialClient;
import com.reto.interbank.services.mapper.CustomerMapper;
import com.reto.interbank.services.model.Customer;
import com.reto.interbank.services.model.CustomerResponse;
import com.reto.interbank.services.model.FinancialList;
import com.reto.interbank.services.service.impl.CustomerServiceImpl;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerClient customerClient;

    @Mock
    private FinancialClient financialClient;

    @Mock
    private CustomerMapper customerMapper;

    private CustomerService customerService;

    String id;

    String traceId;

    @BeforeEach
    void setUp() {
        customerService = new CustomerServiceImpl(customerClient, financialClient, customerMapper);
        id = "UFYEIUUO97jLMmeAAnChXbMlpg/Zo2maTQb0KRPjfac=";
        traceId = "123";
    }

    @Test
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
        when(customerMapper.mapToCustomerResponse(customer, financial)).thenReturn(customerResponse);

        Mono<CustomerResponse> responseMono = customerService.getCustomer(traceId, id);

        assertNotNull(responseMono);

        StepVerifier.create(responseMono)
                .assertNext(v -> {
                    assertNotNull(v.getId());
                    assertNotNull(v.getName());
                    assertNotNull(v.getFirstLastName());
                    assertNotNull(v.getSecondLastName());
                    assertNotNull(v.getDocument().getType());
                    assertNotNull(v.getDocument().getNumber());
                    assertNotNull(v.getProducts());
                })
                .expectComplete()
                .verify();

    }

}