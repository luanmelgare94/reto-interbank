package com.reto.interbank.services.service.impl;

import com.reto.interbank.services.entity.FinancialEntity;
import com.reto.interbank.services.repository.FinancialRepository;
import com.reto.interbank.services.repository.ProductTypeRepository;
import com.reto.interbank.services.service.FinancialService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class FinancialServiceImpl implements FinancialService {

    private final FinancialRepository financialRepository;

    private final ProductTypeRepository productTypeRepository;

    @Override
    public Flux<FinancialEntity> getByCustomerId(String customerId) {
        return financialRepository.findByCustomerId(customerId)
                .flatMap(financialEntity -> productTypeRepository
                        .findById(financialEntity.getProductType().getId())
                        .map(productTypeEntity -> {
                            financialEntity
                                    .getProductType()
                                    .setName((productTypeEntity.getName()));
                            return financialEntity;
                        }));
    }

}