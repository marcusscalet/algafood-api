package com.marcusscalet.algafood.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.marcusscalet.algafood.api.model.AddressDTO;
import com.marcusscalet.algafood.api.model.input.OrderItemInput;
import com.marcusscalet.algafood.domain.model.Address;
import com.marcusscalet.algafood.domain.model.OrderItem;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();

		modelMapper.createTypeMap(OrderItemInput.class, OrderItem.class)
				.addMappings(mapper -> mapper.skip(OrderItem::setId));

		// mapeia de address to addressDTO
		var addressToAddressDTOTypeMap = modelMapper.createTypeMap(Address.class, AddressDTO.class);

		// atribui nome do estado da cidade ao destino, que seria atributo state dentro
		// de CityResumeDTO
		addressToAddressDTOTypeMap.<String>addMapping(addressOrigin -> addressOrigin.getCity().getState().getName(),
				(addressDTODestiny, value) -> addressDTODestiny.getCity().setState(value));

		return modelMapper;
	}
}
