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

	private static final String MSG_STATE_BEING_USED = "Estado de código %d não pode ser removido, pois está em uso";
	private static final String MSG_STATE_NOT_FOUND = "Não existe um cadastro de estado com código %d";
	
	@Autowired
	private StateRepository stateRepository;

	public State saveState(State state) {
		return stateRepository.save(state);
	}

	public void removeState(Long stateId) {
		try {
			stateRepository.deleteById(stateId);

		} catch (EmptyResultDataAccessException e) {
			throw new EntityNotFoundException(
					String.format(MSG_STATE_NOT_FOUND, stateId));

		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(
					String.format(MSG_STATE_BEING_USED, stateId));
		}
	}

	public State searchOrFail(Long stateId) {
		return stateRepository.findById(stateId)
				.orElseThrow(() -> new EntityNotFoundException(
						String.format(MSG_STATE_NOT_FOUND, stateId)));
	}
}