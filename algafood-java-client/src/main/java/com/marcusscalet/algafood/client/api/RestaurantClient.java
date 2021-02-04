package com.marcusscalet.algafood.client.api;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import com.marcusscalet.algafood.client.model.RestaurantDTO;
import com.marcusscalet.algafood.client.model.RestaurantSummaryDTO;
import com.marcusscalet.algafood.client.model.input.RestaurantInput;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RestaurantClient {

	private static final String RESOURCE_PATH = "/restaurants";

	private RestTemplate restTemplate;
	private String url;

	public RestaurantDTO saveRestaurant(RestaurantInput restaurant) {

		URI resourceUri = URI.create(url + RESOURCE_PATH);
		try {
			return restTemplate.postForObject(resourceUri, restaurant, RestaurantDTO.class);
		} catch (HttpClientErrorException e) {
			throw new ClientApiException(e.getMessage(), e);
		}
	}

	public List<RestaurantSummaryDTO> listAll() {
		try {
			URI resourceUri = URI.create(url + RESOURCE_PATH);

			RestaurantSummaryDTO[] restaurants = restTemplate.getForObject(resourceUri, RestaurantSummaryDTO[].class);

			return Arrays.asList(restaurants);
		} catch (RestClientResponseException e) {
			throw new ClientApiException(e.getMessage(), e);
		}
	}

}
