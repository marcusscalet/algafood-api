package com.marcusscalet.algafood.api.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.marcusscalet.algafood.domain.model.Cuisine;
import com.marcusscalet.algafood.domain.model.Restaurant;
import com.marcusscalet.algafood.domain.repository.CuisineRepository;
import com.marcusscalet.algafood.domain.repository.RestaurantRepository;

@RestController
@RequestMapping("/teste")
public class TestController {

	@Autowired
	private CuisineRepository cuisineRepository;

	@Autowired
	private RestaurantRepository restaurantRepository;

//	@GetMapping("/cozinhas/por-nome")
//	public List<Cuisine> consultByName(@RequestParam("name") String name) {
//		return cuisineRepository.findByName(name);
//	}

	@GetMapping("/cozinhas/existe")
	public boolean cuisineExists(@RequestParam("name") String name) {
		return cuisineRepository.existsByName(name);
	}
	
	@GetMapping("/cozinhas/primeira")
	public Optional<Cuisine> cuisineFirst() {
		return cuisineRepository.findFirst();
	}

	@GetMapping("/restaurant/por-taxa-frete")
	public List<Restaurant> restaurantByFreight(BigDecimal initialShippingFee, BigDecimal finalShippingFee) {
		return restaurantRepository.queryByShippingFeeBetween(initialShippingFee, finalShippingFee);
	}

//	@GetMapping("/restaurant/por-nome")
//	public List<Restaurant> restaurantsByName(String name, Long cuisineId) {
//		return restaurantRepository.consultByName(name, cuisineId);
//	}

	@GetMapping("/restaurant/primeiro-por-nome")
	public Optional<Restaurant> restaurantFirstByName(String name) {
		return restaurantRepository.findFirstByNameContaining(name);
	}

	@GetMapping("/restaurantes/top2-por-nome")
	public List<Restaurant> restaurantsTop2ByName(String name) {
		return restaurantRepository.findTop2ByNameContaining(name);
	}

	@GetMapping("/restaurant/por-nome-e-frete")
	public List<Restaurant> restaurantsByFreightName(String name, BigDecimal initialShippingFee,
			BigDecimal finalShippingFee) {
		return restaurantRepository.find(name, initialShippingFee, finalShippingFee);
	}

	@GetMapping("/restaurantes/quantidade-por-cozinha")
	public int countByCuisineId(Long cuisineId) {
		return restaurantRepository.countByCuisineId(cuisineId);
	}

	@GetMapping("/restaurants/com-frete-gratis")
	public List<Restaurant> restaurantsWithFreeShippingRate(String name) {
		return restaurantRepository.findWithFreeShippingRate(name);
	}

	@GetMapping("/restaurants/primeiro")
	public Optional<Restaurant> restaurantFirst() {
		return restaurantRepository.findFirst();
	}
}
