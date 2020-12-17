package com.marcusscalet.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marcusscalet.algafood.domain.exception.EntityBeingUsedException;
import com.marcusscalet.algafood.domain.exception.StateNotFoundException;
import com.marcusscalet.algafood.domain.model.State;
import com.marcusscalet.algafood.domain.repository.StateRepository;

@Service
public class StateRegistrationService {

	private static final String MSG_STATE_BEING_USED = "Estado de código %d não pode ser removido, pois está em uso";

	@Autowired
	private StateRepository stateRepository;

	@Transactional
	public State saveState(State state) {
		return stateRepository.save(state);
	}

	@Transactional
	public void removeState(Long stateId) {
		try {
			stateRepository.deleteById(stateId);

		} catch (EmptyResultDataAccessException e) {
			throw new StateNotFoundException(stateId);

		} catch (DataIntegrityViolationException e) {
			throw new EntityBeingUsedException(String.format(MSG_STATE_BEING_USED, stateId));
		}
	}

	public State searchOrFail(Long stateId) {
		return stateRepository.findById(stateId).orElseThrow(() -> new StateNotFoundException(stateId));
	}
}