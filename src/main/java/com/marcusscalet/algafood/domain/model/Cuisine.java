package com.marcusscalet.algafood.domain.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.marcusscalet.algafood.core.validation.Groups;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonRootName("cuisine")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Cuisine {

	@NotNull(groups = Groups.CuisineId.class)
	@EqualsAndHashCode.Include // specify the use of equals and hash using only the ID
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;
	
	@JsonIgnore
	@OneToMany(mappedBy = "cuisine") //mapeamento feito por cozinha na tabela Restaurant
	private List<Restaurant> restaurants = new ArrayList<>();
}
