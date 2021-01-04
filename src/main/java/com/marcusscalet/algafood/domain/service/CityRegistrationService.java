package com.marcusscalet.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marcusscalet.algafood.domain.exception.CityNotFoundException;
import com.marcusscalet.algafood.domain.exception.EntityBeingUsedException;
import com.marcusscalet.algafood.domain.model.City;
import com.marcusscalet.algafood.domain.model.State;
import com.marcusscalet.algafood.domain.repository.CityRepository;

@Service
public class CityRegistrationService {

	private static final String MSG_CITY_BEING_USED = "Cidade de código %d não pode ser removida, pois está em uso";

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private StateRegistrationService stateRegistrationService;

	public List<City> listAll(){
		return cityRepository.findAll();
	}
	
	public City searchOrFail(Long cityId) {
		return cityRepository.findById(cityId).orElseThrow(() -> new CityNotFoundException(cityId));
	}
	
	@Transactional
	public City saveCity(City city) {
		Long stateId = city.getState().getId();

		State state = stateRegistrationService.searchOrFail(stateId);

		city.setState(state);
		return cityRepository.save(city);
	}

	@Transactional
	public void remove(Long cityId) {
		try {
			cityRepository.deleteById(cityId);
			cityRepository.flush();
			
		} catch (EmptyResultDataAccessException e) {
			throw new CityNotFoundException(cityId);

		} catch (DataIntegrityViolationException e) {
			throw new EntityBeingUsedException(String.format(MSG_CITY_BEING_USED, cityId));
		}
	}

}