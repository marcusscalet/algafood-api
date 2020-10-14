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
import com.marcusscalet.algafood.domain.repository.StateRepository;

@Service
public class CityRegistrationService {

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private StateRepository stateRepository;

	public City saveCity(City city) {
		Long stateId = city.getState().getId();

		State state = stateRepository.findById(stateId)
				.orElseThrow(() -> new EntityNotFoundException(
				String.format("Não existe cadastro de estado com código %d", stateId)));

		city.setState(state);

		return cityRepository.save(city);
	}

	public void remove(Long cityId) {
		try {
			cityRepository.deleteById(cityId);

		} catch (EmptyResultDataAccessException e) {
			throw new EntityNotFoundException(String.format("Não existe um cadastro de cidade com código %d", cityId));

		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(
					String.format("Cidade de código %d não pode ser removida, pois está em uso", cityId));
		}
	}

}
