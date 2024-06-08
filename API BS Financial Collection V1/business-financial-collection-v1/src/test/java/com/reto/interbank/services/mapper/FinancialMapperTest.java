package com.reto.interbank.services.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.reto.interbank.services.dto.FinancialResponse;
import com.reto.interbank.services.entity.FinancialEntity;
import com.reto.interbank.services.entity.ProductType;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FinancialMapperTest {

    @Autowired
    private FinancialMapper mapper;

    private ProductType productType;
    private FinancialEntity financialEntity;

    @BeforeEach
    void setUp() {
        productType = new ProductType();
        productType.setId("ID");
        productType.setName("name");

        financialEntity = new FinancialEntity();
        financialEntity.setProductType(productType);
        financialEntity.setBalanceAmount(1000.0);
        financialEntity.setFinancialAccount("123456");
        financialEntity.setCustomerId("456789");
        financialEntity.setProductName("name");
    }

    @Test
    public void shouldMapSourceToDestination() {

        FinancialResponse response = mapper.mapToFinancialResponse(financialEntity);

        assertEquals("name", response.getProduct().getType());
        assertEquals("name", response.getProduct().getName());
        assertEquals("S/ 1,000.00", response.getBalanceAmount());
        assertEquals("123456", response.getFinancialAccount());

    }

    @Test
    public void shouldMapListSourceToDestination() {

        List<FinancialEntity> financialEntities = List.of(financialEntity);
        List<FinancialResponse> responses = mapper.mapToListFinancialResponse(financialEntities);

        assertEquals("name", responses.get(0).getProduct().getType());
        assertEquals("name", responses.get(0).getProduct().getName());
        assertEquals("S/ 1,000.00", responses.get(0).getBalanceAmount());
        assertEquals("123456", responses.get(0).getFinancialAccount());
    }

}