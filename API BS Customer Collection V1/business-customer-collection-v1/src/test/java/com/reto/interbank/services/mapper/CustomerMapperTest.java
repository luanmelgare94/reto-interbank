package com.reto.interbank.services.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.reto.interbank.services.dto.CustomerResponse;
import com.reto.interbank.services.entity.CustomerEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CustomerMapperTest {

    @Autowired
    private CustomerMapper mapper;

    CustomerEntity customerEntity;

    @BeforeEach
    void setUp() {
        customerEntity = new CustomerEntity();
        customerEntity.setId("ID");
        customerEntity.setName("name");
        customerEntity.setFirstLastName("first");
        customerEntity.setSecondLastName("second");
        customerEntity.setDocumentType("type");
        customerEntity.setDocumentNumber("number");
    }

    @Test
    public void shouldMapSourceToDestination() {
        CustomerResponse response = mapper.mapToCustomerResponse(customerEntity);

        assertEquals("ID", response.getId());
        assertEquals("name", response.getName());
        assertEquals("first", response.getFirstLastName());
        assertEquals("second", response.getSecondLastName());
        assertEquals("type", response.getDocument().getType());
        assertEquals("number", response.getDocument().getNumber());
    }

}