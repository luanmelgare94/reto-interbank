package com.reto.interbank.services.mapper;

import static com.reto.interbank.services.util.Constants.EXPRESSION_METHOD_MAPPER_FORMAT_THOUSAND_MILES;

import com.reto.interbank.services.dto.FinancialResponse;
import com.reto.interbank.services.entity.FinancialEntity;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FinancialMapper {

    @Mapping(target = "balanceAmount", expression = EXPRESSION_METHOD_MAPPER_FORMAT_THOUSAND_MILES)
    @Mapping(target = "product.type", source = "productType.name")
    @Mapping(target = "product.name", source = "productName")
    FinancialResponse mapToFinancialResponse(
            FinancialEntity financialEntity);

    List<FinancialResponse> mapToListFinancialResponse(List<FinancialEntity> financialEntities);

}