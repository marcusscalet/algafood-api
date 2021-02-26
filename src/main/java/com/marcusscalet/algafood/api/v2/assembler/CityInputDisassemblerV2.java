package com.marcusscalet.algafood.api.v2.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marcusscalet.algafood.api.v2.model.input.CityInputV2;
import com.marcusscalet.algafood.domain.model.City;
import com.marcusscalet.algafood.domain.model.State;

@Component
public class CityInputDisassemblerV2 {

	@Autowired
	private ModelMapper modelMapper;

	public City toDomainObject(CityInputV2 cityInput) {
		return modelMapper.map(cityInput, City.class);
	}

	public void copyToDomainObject(CityInputV2 cityInput, City city) {
		
		city.setState(new State());
		modelMapper.map(cityInput, city);
	}

}