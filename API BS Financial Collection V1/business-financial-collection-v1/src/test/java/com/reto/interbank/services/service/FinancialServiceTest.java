package com.reto.interbank.services.service;

import static com.reto.interbank.services.utils.TestUtil.fromFileFactory;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.reto.interbank.services.entity.FinancialEntity;
import com.reto.interbank.services.entity.ProductTypeEntity;
import com.reto.interbank.services.repository.FinancialRepository;
import com.reto.interbank.services.repository.ProductTypeRepository;
import com.reto.interbank.services.service.impl.FinancialServiceImpl;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class FinancialServiceTest {

    @Mock
    private FinancialRepository financialRepository;

    @Mock
    private ProductTypeRepository productTypeRepository;

    private FinancialService financialService;

    @BeforeEach
    void setUp() {
        financialService = new FinancialServiceImpl(financialRepository, productTypeRepository);
    }

    @Test
    void returnFinancialServiceWhenCustomerIdExits() throws IOException {
        FinancialEntity financialEntityPartiallyFull = fromFileFactory(
                "mocks/financial_products_entity_partially_full_data.json",
                FinancialEntity.class);

        ProductTypeEntity productType = fromFileFactory(
                "mocks/product_type_entity_data.json",
                ProductTypeEntity.class);

        String customerIdDecrypt = "6660f6e4707a071685b6da52";

        when(financialRepository.findByCustomerId(
                any(String.class)))
                .thenReturn(Flux.just(financialEntityPartiallyFull));

        when(productTypeRepository.findById(
                any(String.class)))
                .thenReturn(Mono.just(productType));


        Flux<FinancialEntity> financialEntityFlux = financialService
                .getByCustomerId(customerIdDecrypt);

        assertNotNull(financialEntityFlux);

        StepVerifier.create(financialEntityFlux)
                .assertNext(v -> {
                    assertNotNull(v.getId());
                    assertNotNull(v.getCustomerId());
                    assertNotNull(v.getFinancialAccount());
                    assertNotNull(v.getProductType().getId());
                    assertNotNull(v.getProductType().getName());
                    assertNotNull(v.getProductName());
                    assertNotNull(v.getFinancialAccount());
                    assertNotNull(v.getBalanceAmount());
                })
                .expectComplete()
                .verify();
    }

}