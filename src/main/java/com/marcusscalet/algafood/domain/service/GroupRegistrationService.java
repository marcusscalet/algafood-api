package com.marcusscalet.algafood.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.marcusscalet.algafood.domain.exception.EntityBeingUsedException;
import com.marcusscalet.algafood.domain.exception.GroupNotFoundException;
import com.marcusscalet.algafood.domain.model.Group;
import com.marcusscalet.algafood.infrastructure.repository.GroupRepository;

@Service
public class GroupRegistrationService {

	private static final String MSG_GROUP_BEING_USED = "Gropo com código%d não pode ser removido, pois está em uso";
	@Autowired
	private GroupRepository groupRepository;

	@Transactional
	public Group saveGroup(Group group) {
		return groupRepository.save(group);
	}

	@Transactional
	public void remove(Long groupId) {
		try {
			groupRepository.deleteById(groupId);
			groupRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new GroupNotFoundException(groupId);
		} catch (DataIntegrityViolationException e) {
			throw new EntityBeingUsedException(
					String.format(MSG_GROUP_BEING_USED, groupId));
		}
	}

	public Group searchOrFail(Long groupId) {
		return groupRepository.findById(groupId).orElseThrow(() -> new GroupNotFoundException(groupId));
	}
}
