package com.marcusscalet.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marcusscalet.algafood.api.model.CityDTO;
import com.marcusscalet.algafood.domain.model.City;

@Component
public class CityDTOAssembler {

	@Autowired
	private ModelMapper modelMapper;

	public CityDTO toDTO(City city) {
		return modelMapper.map(city, CityDTO.class);
	}
	
	public List<CityDTO> toCollectionDTO(List<City> cities){
		return cities.stream().map(city -> toDTO(city)).collect(Collectors.toList());
	}
}
