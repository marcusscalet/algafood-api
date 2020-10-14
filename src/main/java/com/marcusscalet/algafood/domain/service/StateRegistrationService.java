package com.marcusscalet.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.marcusscalet.algafood.domain.exception.EntityInUseException;
import com.marcusscalet.algafood.domain.exception.EntityNotFoundException;
import com.marcusscalet.algafood.domain.model.State;
import com.marcusscalet.algafood.domain.repository.StateRepository;

@Service
public class StateRegistrationService {

	@Autowired
	private StateRepository stateRepository;

	public State saveState(State state) {
		return stateRepository.save(state);
	}

	public void removeState(Long stateId) {
		try {
			stateRepository.deleteById(stateId);

		} catch (EmptyResultDataAccessException e) {
			throw new EntityNotFoundException(String.format("Não existe um cadastro de estado com código %d", stateId));

		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(
					String.format("Estado de código %d não pode ser removido, pois está em uso", stateId));
		}
	}
}