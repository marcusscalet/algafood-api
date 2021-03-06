package com.marcusscalet.algafood.domain.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Cuisine {

	@EqualsAndHashCode.Include // specify the use of equals and hash using only the ID
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;
	
	@OneToMany(mappedBy = "cuisine") //mapeamento feito por cozinha na tabela Restaurant
	private List<Restaurant> restaurants = new ArrayList<>();
}
