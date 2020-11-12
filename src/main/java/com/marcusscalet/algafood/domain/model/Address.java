package com.marcusscalet.algafood.domain.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Embeddable
public class Address {

	@Column(name = "adress_zip_code")
	private String zipCode;

	@Column(name = "adress_street")
	private String street;

	@Column(name = "adress_number")
	private String number;

	@Column(name = "address_addressSupplement")
	private String addressSupplement;

	@Column(name = "address_neighborhood")
	private String neighborhood;

	@ManyToOne
	@JoinColumn(name = "address_city_id")
	private City city;

}
