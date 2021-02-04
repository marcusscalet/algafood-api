package com.marcusscalet.algafood.client;

import java.math.BigDecimal;

import org.springframework.web.client.RestTemplate;

import com.marcusscalet.algafood.client.api.ClientApiException;
import com.marcusscalet.algafood.client.api.RestaurantClient;
import com.marcusscalet.algafood.client.model.input.AddressInput;
import com.marcusscalet.algafood.client.model.input.CityInput;
import com.marcusscalet.algafood.client.model.input.CuisineInput;
import com.marcusscalet.algafood.client.model.input.RestaurantInput;

public class AlgafoodJavaClientApplication {

	public static void main(String[] args) {
		try {
			RestTemplate restTemplate = new RestTemplate();

			RestaurantClient restaurantClient = new RestaurantClient(restTemplate, "http://api.algafood.local:8080");
			
			var cityInput = new CityInput();
			cityInput.setId(1L);
			
			var cuisineInput = new CuisineInput();
			cuisineInput.setId(1L);
			
			var addressInput = new AddressInput();
			addressInput.setCity(cityInput);
			addressInput.setNeighborhood("Centro");
			addressInput.setNumber("123");
			addressInput.setStreet("Rua das Mangas");
			addressInput.setZipCode("13301100");
			
			var restaurantInput = new RestaurantInput();
			restaurantInput.setAddress(addressInput);
			restaurantInput.setCuisine(cuisineInput);
			restaurantInput.setName("Tio Java");
			restaurantInput.setShippingFee(new BigDecimal(12));
			
			restaurantClient.saveRestaurant(restaurantInput);
			
			restaurantClient.listAll().stream().forEach(restaurant -> System.out.println(restaurant));
		} catch (ClientApiException e) {
			if (e.getProblem() != null) {
				System.out.println(e.getProblem().getUserMessage());
				

		        e.getProblem().getObjects().stream()
		          .forEach(p -> System.out.println("- " + p.getUserMessage()));
			} else {
				System.out.println("erro desconhecido");
				e.printStackTrace();
			}
		}
	}

}
