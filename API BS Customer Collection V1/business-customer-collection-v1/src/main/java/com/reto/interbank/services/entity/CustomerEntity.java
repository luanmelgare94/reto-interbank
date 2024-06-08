package com.reto.interbank.services.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@Document(collection = "customer")
public class CustomerEntity {

	@Id
	private String id;

	@NotNull
	@Size(min = 2)
	@Field(name = "name")
	private String name;

	@NotNull
	@Size(min = 2)
	@Field(name = "firstLastName")
	private String firstLastName;

	@NotNull
	@Size(min = 2)
	@Field(name = "secondLastName")
	private String secondLastName;

	@NotNull
	@Size(min = 2)
	@Field(name = "documentType")
	private String documentType;

	@NotNull
	@Size(min = 8)
	@Field(name = "documentNumber")
	private String documentNumber;

}