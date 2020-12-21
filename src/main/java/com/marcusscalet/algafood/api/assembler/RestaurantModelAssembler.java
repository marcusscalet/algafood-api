package com.marcusscalet.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.marcusscalet.algafood.api.model.CuisineDTO;
import com.marcusscalet.algafood.api.model.RestaurantDTO;
import com.marcusscalet.algafood.domain.model.Restaurant;

@Component
public class RestaurantModelAssembler {
	
	public RestaurantDTO toDTO(Restaurant restaurant) {
		CuisineDTO cuisineDTO = new CuisineDTO();
		cuisineDTO.setId(restaurant.getCuisine().getId());
		cuisineDTO.setName(restaurant.getCuisine().getName());

		RestaurantDTO restaurantDTO = new RestaurantDTO();
		restaurantDTO.setId(restaurant.getId());
		restaurantDTO.setName(restaurant.getName());
		restaurantDTO.setShippingFee(restaurant.getShippingFee());
		restaurantDTO.setCuisine(cuisineDTO);
		System.out.println(restaurantDTO);
		return restaurantDTO;
	}

	public List<RestaurantDTO> toCollectionDTO(List<Restaurant> restaurants) {
		return restaurants.stream().map(restaurant -> toDTO(restaurant)).collect(Collectors.toList());
	}

}
