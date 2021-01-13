package com.marcusscalet.algafood.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.marcusscalet.algafood.api.model.AddressDTO;
import com.marcusscalet.algafood.api.model.input.OrderedItemInput;
import com.marcusscalet.algafood.domain.model.Address;
import com.marcusscalet.algafood.domain.model.OrderedItem;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();

		modelMapper.createTypeMap(OrderedItemInput.class, OrderedItem.class)
				.addMappings(mapper -> mapper.skip(OrderedItem::setId));

		// mapeia de address to addressDTO
		var addressToAddressDTOTypeMap = modelMapper.createTypeMap(Address.class, AddressDTO.class);

		// atribui nome do estado da cidade ao destino, que seria atributo state dentro
		// de CityResumeDTO
		addressToAddressDTOTypeMap.<String>addMapping(addressOrigin -> addressOrigin.getCity().getState().getName(),
				(addressDTODestiny, value) -> addressDTODestiny.getCity().setState(value));

		return modelMapper;
	}
}
