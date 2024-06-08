package com.reto.interbank.services.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FinancialResponse {
    private FinancialProduct product;
    private String financialAccount;
    private String balanceAmount;
}