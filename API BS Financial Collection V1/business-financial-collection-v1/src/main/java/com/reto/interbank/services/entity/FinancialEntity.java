package com.reto.interbank.services.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "financial")
public class FinancialEntity {

    @Id
    private String id;

    @NotNull
    @Field(name = "customerId")
    private String customerId;

    @NotNull
    @Field(name = "productType")
    private ProductTypeEntity productType;

    @NotNull
    @Size(min = 2)
    @Field(name = "productName")
    private String productName;

    @NotNull
    @Size(min = 2)
    @Field(name = "financialAccount")
    private String financialAccount;

    @NotNull
    @Min(0)
    @Field(name = "balanceAmount")
    private Double balanceAmount;

}