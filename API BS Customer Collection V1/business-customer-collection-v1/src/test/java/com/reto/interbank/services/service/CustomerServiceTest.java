package com.reto.interbank.services.service;

import static com.reto.interbank.services.utils.TestUtil.fromFileFactory;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import com.reto.interbank.services.entity.CustomerEntity;
import com.reto.interbank.services.repository.CustomerRepository;
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
    private CustomerRepository customerRepository;

    private CustomerService customerService;

    String idDecrypt;

    @BeforeEach
    void setUp() {
        customerService = new CustomerServiceImpl(customerRepository);
        idDecrypt = "6660f6e4707a071685b6da52";
    }

    @Test
    void returnDetailCustomerWhenIdExists() throws IOException {
        CustomerEntity customerEntity = fromFileFactory(
                "mocks/customer_entity_data.json", CustomerEntity.class);

        when(customerRepository.findById(
                idDecrypt))
                .thenReturn(Mono.just(customerEntity));

        Mono<CustomerEntity> customerEntityMono = customerService.getById(idDecrypt);

        assertNotNull(customerEntityMono);

        StepVerifier.create(customerEntityMono)
                .assertNext(v -> {
                    assertNotNull(v.getId());
                    assertNotNull(v.getName());
                    assertNotNull(v.getFirstLastName());
                    assertNotNull(v.getSecondLastName());
                    assertNotNull(v.getDocumentType());
                    assertNotNull(v.getDocumentNumber());
                })
                .expectComplete()
                .verify();

    }

}