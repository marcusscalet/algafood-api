package com.marcusscalet.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marcusscalet.algafood.api.model.input.RestaurantInput;
import com.marcusscalet.algafood.domain.model.City;
import com.marcusscalet.algafood.domain.model.Cuisine;
import com.marcusscalet.algafood.domain.model.Restaurant;

@Component
public class RestaurantInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;

	public Restaurant toDomainObject(RestaurantInput restaurantInput) {
		return modelMapper.map(restaurantInput, Restaurant.class);
	}

	// passamos o restaurantInput ao objeto de destino "restaurant" ao qual queremos
	// atribuir os dados
	public void copyToDomainObject(RestaurantInput restaurantInput, Restaurant restaurant) {
		
		//criada uma nova instância limpa, sem rastros do JPA
		//resolvendo problema na atualização de cuisineId em Restaurant
		//evitando org.hibernate.HibernateException: identifier of an instance of
		//com.marcusscalet.algafood.domain.model.Cuisine was altered from 1 to 2
		restaurant.setCuisine(new Cuisine());
		if(restaurant.getAddress() != null) {
			restaurant.getAddress().setCity(new City());
		}
		modelMapper.map(restaurantInput, restaurant);
	}

}