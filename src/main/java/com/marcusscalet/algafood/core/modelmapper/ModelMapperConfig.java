package com.marcusscalet.algafood.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.marcusscalet.algafood.api.v1.model.AddressModel;
import com.marcusscalet.algafood.api.v1.model.input.OrderItemInput;
import com.marcusscalet.algafood.api.v2.model.input.CityInputV2;
import com.marcusscalet.algafood.domain.model.Address;
import com.marcusscalet.algafood.domain.model.City;
import com.marcusscalet.algafood.domain.model.OrderItem;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();

		modelMapper.createTypeMap(CityInputV2.class, City.class)
		.addMappings(mapper -> mapper.skip(City::setId));

		modelMapper.createTypeMap(OrderItemInput.class, OrderItem.class)
				.addMappings(mapper -> mapper.skip(OrderItem::setId));

		// mapeia de address to addressModel
		var addressToAddressModelTypeMap = modelMapper.createTypeMap(Address.class, AddressModel.class);

		// atribui nome do estado da cidade ao destino, que seria atributo state dentro
		// de CityResumeModel
		addressToAddressModelTypeMap.<String>addMapping(addressOrigin -> addressOrigin.getCity().getState().getName(),
				(addressModelDestiny, value) -> addressModelDestiny.getCity().setState(value));

		return modelMapper;
	}
}
