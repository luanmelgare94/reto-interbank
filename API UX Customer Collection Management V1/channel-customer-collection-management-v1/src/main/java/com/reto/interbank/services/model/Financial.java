package com.reto.interbank.services.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Financial {
    private FinancialProduct product;
    private String financialAccount;
    private String balanceAmount;
}