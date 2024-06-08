package com.reto.interbank.services.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Customer {
    private String id;
    private String name;
    private String firstLastName;
    private String secondLastName;
    private CustomerDocument document;
}