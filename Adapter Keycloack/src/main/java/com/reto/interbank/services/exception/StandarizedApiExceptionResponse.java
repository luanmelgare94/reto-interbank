package com.reto.interbank.services.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StandarizedApiExceptionResponse {

    private String type;
    private String title;
    private String code;
    private String detail;
    private String instance;

    public StandarizedApiExceptionResponse(String title, String code, String detail) {
        super();
        this.type = "/errors/uncategorized";
        this.title = title;
        this.code = code;
        this.detail = detail;
        this.instance = "/errors/uncategorized/bank";
    }

}