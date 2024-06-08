package com.reto.interbank.services.mapper;

import com.reto.interbank.services.model.Customer;
import com.reto.interbank.services.model.CustomerResponse;
import com.reto.interbank.services.model.FinancialList;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerResponse mapToCustomerResponse(Customer customer, FinancialList financialList);

}