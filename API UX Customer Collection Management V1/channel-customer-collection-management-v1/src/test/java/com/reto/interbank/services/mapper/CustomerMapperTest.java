package com.reto.interbank.services.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.reto.interbank.services.model.Customer;
import com.reto.interbank.services.model.CustomerDocument;
import com.reto.interbank.services.model.CustomerResponse;
import com.reto.interbank.services.model.Financial;
import com.reto.interbank.services.model.FinancialList;
import com.reto.interbank.services.model.FinancialProduct;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CustomerMapperTest {

    @Autowired
    private CustomerMapper mapper;

    Customer customer;

    FinancialList financialList;

    CustomerDocument document;

    Financial financial;

    FinancialProduct financialProduct;

    @BeforeEach
    void setUp() {
        document = new CustomerDocument();
        document.setType("type");
        document.setNumber("number");

        customer = new Customer();
        customer.setId("Id");
        customer.setName("name");
        customer.setFirstLastName("first");
        customer.setSecondLastName("second");
        customer.setDocument(document);

        financialProduct = new FinancialProduct();
        financialProduct.setType("type");
        financialProduct.setName("name");

        financial = new Financial();
        financial.setFinancialAccount("123456");
        financial.setBalanceAmount("S/ 1,000.00");
        financial.setProduct(financialProduct);

        financialList = new FinancialList();
        financialList.setProducts(List.of(financial));
    }

    @Test
    public void shouldMapSourceToDestination() {
        CustomerResponse response = mapper.mapToCustomerResponse(customer, financialList);

        assertEquals("Id", response.getId());
        assertEquals("name", response.getName());
        assertEquals("first", response.getFirstLastName());
        assertEquals("second", response.getSecondLastName());
        assertEquals("type", response.getDocument().getType());
        assertEquals("number", response.getDocument().getNumber());
        assertEquals("type", response.getProducts().get(0).getProduct().getType());
        assertEquals("name", response.getProducts().get(0).getProduct().getName());
        assertEquals("S/ 1,000.00", response.getProducts().get(0).getBalanceAmount());
        assertEquals("123456", response.getProducts().get(0).getFinancialAccount());
    }

}