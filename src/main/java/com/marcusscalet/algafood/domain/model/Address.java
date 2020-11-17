package com.marcusscalet.algafood.domain.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Embeddable
public class Address {

	@Column(name = "address_zip_code")
	private String zipCode;

	@Column(name = "address_street")
	private String street;

	@Column(name = "address_number")
	private String number;

	@Column(name = "address_neighborhood")
	private String neighborhood;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "address_city_id")
	private City city;

}
