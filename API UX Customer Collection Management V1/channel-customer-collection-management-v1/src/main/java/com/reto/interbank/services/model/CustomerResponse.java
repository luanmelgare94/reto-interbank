package com.reto.interbank.services.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CustomerResponse {
    private String id;
    private String name;
    private String firstLastName;
    private String secondLastName;
    private CustomerDocument document;
    private List<Financial> products;
}