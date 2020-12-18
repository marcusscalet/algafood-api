package com.marcusscalet.algafood.api.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.marcusscalet.algafood.domain.model.State;

public class CityMixin {

	@JsonIgnoreProperties(value = "nome", allowGetters = true)
	private State state;
}
