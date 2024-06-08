package com.reto.interbank.services.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class FinancialListResponse {
    private List<FinancialResponse> products;
}