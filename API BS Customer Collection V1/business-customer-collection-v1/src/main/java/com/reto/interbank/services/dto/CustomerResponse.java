package com.reto.interbank.services.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerResponse {
    private String id;
    private String name;
    private String firstLastName;
    private String secondLastName;
    private CustomerDocumentResponse document;
}