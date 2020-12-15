package com.marcusscalet.algafood.algafoodapi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.marcusscalet.algafood.domain.exception.EntityBeingUsedException;
import com.marcusscalet.algafood.domain.exception.EntityNotFoundException;
import com.marcusscalet.algafood.domain.model.Cuisine;
import com.marcusscalet.algafood.domain.service.CuisineRegistrationService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CuisineRegistrationIT {

	@Autowired
	private CuisineRegistrationService cuisineRegistrationService;

	@Test
	void mustRegisterCuisineWithSuccess() {

		Cuisine newCuisine = new Cuisine();
		newCuisine.setName("Chinesa");

		newCuisine = cuisineRegistrationService.saveCuisine(newCuisine);

		assertThat(newCuisine).isNotNull();
		assertThat(newCuisine.getId()).isNotNull();
	}

	@Test
	public void mustFailWhenRegistrationCuisineWithoutName() {
		Cuisine newCuisine = new Cuisine();
		newCuisine.setName(null);
		assertThrows(DataIntegrityViolationException.class, () -> cuisineRegistrationService.saveCuisine(newCuisine));
	}

	@Test
	public void mustFailWhenRemoveCuisineNotExists() {
		assertThrows(EntityNotFoundException.class, () -> cuisineRegistrationService.removeCuisine(54L));
	}

	@Test
	public void mustFailWhenRemoveCuisineBeingUsed() {
		assertThrows(EntityBeingUsedException.class, () -> cuisineRegistrationService.removeCuisine(1L));
	}

}
