package com.reto.interbank.services.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDocumentResponse {
    private String type;
    private String number;
}