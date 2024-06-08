package com.reto.interbank.services.util;

public class Constants {

    private Constants() {

    }

    public static final String EXPRESSION_METHOD_MAPPER_FORMAT_THOUSAND_MILES = "java(new "
            + "com.reto.interbank.services.helper.FinancialHelper().formatThousandSeparator"
            + "(financialEntity.getBalanceAmount()))";
    public static final String PATTERN_FORMAT_THOUSAND_SEPARATOR = "S/ #,##0.00";

}