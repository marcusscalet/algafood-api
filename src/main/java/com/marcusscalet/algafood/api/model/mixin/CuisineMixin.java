package com.marcusscalet.algafood.api.model.mixin;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.marcusscalet.algafood.domain.model.Restaurant;

public class CuisineMixin {

	@JsonIgnore
    private List<Restaurant> restaurants;
}
