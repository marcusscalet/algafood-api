package com.marcusscalet.algafood.api.model.mixin;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.marcusscalet.algafood.domain.model.Address;
import com.marcusscalet.algafood.domain.model.Cuisine;
import com.marcusscalet.algafood.domain.model.PaymentMethod;
import com.marcusscalet.algafood.domain.model.Product;

public abstract class RestaurantMixin {

	// ignorar "name" de Cuisine em Restaurant apenas quando for desserializar um json para objeto
	@JsonIgnoreProperties(value = "name", allowGetters = true)
	private Cuisine cuisine;

	@JsonIgnore
	private Address address;

	@JsonIgnore
//	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX") 
	private OffsetDateTime registrationDate;

	@JsonIgnore
//	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
	private OffsetDateTime updateDate;

	@JsonIgnore
	private List<PaymentMethod> paymentMethod = new ArrayList<>();

	@JsonIgnore
	private List<Product> products;
}
