package com.marcusscalet.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.marcusscalet.algafood.domain.exception.EntityInUseException;
import com.marcusscalet.algafood.domain.exception.EntityNotFoundException;
import com.marcusscalet.algafood.domain.model.City;
import com.marcusscalet.algafood.domain.model.State;
import com.marcusscalet.algafood.domain.repository.CityRepository;

@Service
public class CityRegistrationService {

	private static final String MSG_CITY_BEING_USED = "Cidade de código %d não pode ser removida, pois está em uso";
	private static final String MSG_CITY_NOT_FOUND = "Não existe um cadastro de cidade com código %d";

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private StateRegistrationService stateRegistrationService;

	public City saveCity(City city) {
		Long stateId = city.getState().getId();

		State state = stateRegistrationService.searchOrFail(stateId);

		city.setState(state);
		return cityRepository.save(city);
	}

	public void remove(Long cityId) {
		try {
			cityRepository.deleteById(cityId);

		} catch (EmptyResultDataAccessException e) {
			throw new EntityNotFoundException(String.format(MSG_CITY_NOT_FOUND, cityId));

		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(String.format(MSG_CITY_BEING_USED, cityId));
		}
	}

	public City searchOrFail(Long cityId) {
		return cityRepository.findById(cityId)
				.orElseThrow(() -> new EntityNotFoundException(String.format(MSG_CITY_NOT_FOUND, cityId)));
	}
}