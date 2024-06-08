package com.reto.interbank.services.mapper;

import com.reto.interbank.services.dto.CustomerResponse;
import com.reto.interbank.services.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @Mapping(target = "document.type", source = "documentType")
    @Mapping(target = "document.number", source = "documentNumber")
    CustomerResponse mapToCustomerResponse(
            CustomerEntity customerEntity);

}