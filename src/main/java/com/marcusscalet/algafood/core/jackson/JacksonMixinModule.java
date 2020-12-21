package com.marcusscalet.algafood.core.jackson;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.marcusscalet.algafood.api.model.mixin.CityMixin;
import com.marcusscalet.algafood.api.model.mixin.CuisineMixin;
import com.marcusscalet.algafood.domain.model.City;
import com.marcusscalet.algafood.domain.model.Cuisine;

@Component
public class JacksonMixinModule extends SimpleModule {

	private static final long serialVersionUID = 1L;

	public JacksonMixinModule() {
		setMixInAnnotation(City.class, CityMixin.class);
		setMixInAnnotation(Cuisine.class, CuisineMixin.class);
	}
}
