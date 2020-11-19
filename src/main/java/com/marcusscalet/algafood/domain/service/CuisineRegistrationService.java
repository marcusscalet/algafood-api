package com.marcusscalet.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.marcusscalet.algafood.domain.exception.EntityInUseException;
import com.marcusscalet.algafood.domain.exception.EntityNotFoundException;
import com.marcusscalet.algafood.domain.model.Cuisine;
import com.marcusscalet.algafood.domain.repository.CuisineRepository;

@Service
public class CuisineRegistrationService {

	private static final String MSG_CUISINE_BEING_USED = "Cozinha de código %d não pode ser removida, pois está em uso";
	private static final String MSG_CUISINE_NOT_FOUND = "Não existe um cadastro de cozinha com código %d";
	@Autowired
	private CuisineRepository cuisineRepository;

	public Cuisine saveCuisine(Cuisine cuisine) {
		return cuisineRepository.save(cuisine);
	}

	public void removeCuisine(Long cuisineId) {
		try {
			cuisineRepository.deleteById(cuisineId);

		} catch (EmptyResultDataAccessException e) {
			throw new EntityNotFoundException(
					String.format(MSG_CUISINE_NOT_FOUND, cuisineId));

		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(
					String.format(MSG_CUISINE_BEING_USED, cuisineId));
		}
	}

	public Cuisine searchOrFail(Long cuisineId) {
		return cuisineRepository.findById(cuisineId)
				.orElseThrow(() -> new EntityNotFoundException(
						String.format(MSG_CUISINE_NOT_FOUND, cuisineId)));
	}
}