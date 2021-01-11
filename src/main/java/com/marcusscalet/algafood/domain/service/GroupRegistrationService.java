package com.marcusscalet.algafood.domain.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.marcusscalet.algafood.domain.exception.EntityBeingUsedException;
import com.marcusscalet.algafood.domain.exception.GroupNotFoundException;
import com.marcusscalet.algafood.domain.model.Group;
import com.marcusscalet.algafood.domain.model.Permission;
import com.marcusscalet.algafood.domain.repository.GroupRepository;

@Service
public class GroupRegistrationService {

	private static final String MSG_GROUP_BEING_USED = "Grupo com código %d não pode ser removido, pois está em uso";
	
	@Autowired
	private GroupRepository groupRepository;

	@Autowired
	private PermissionRegistrationService permissionRegistrationService;
	
	public List<Group> listAll(){
		return groupRepository.findAll();
	}
	
	@Transactional
	public Group saveGroup(Group group) {
		return groupRepository.save(group);
	}
	
	public Group searchOrFail(Long groupId) {
		return groupRepository.findById(groupId).orElseThrow(() -> new GroupNotFoundException(groupId));
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

	@Transactional
	public void associatePermission(Long groupId, Long permissionId) {
		Group currentGroup = searchOrFail(groupId);
		Permission currentPermission = permissionRegistrationService.searchOrFail(permissionId);
		
		currentGroup.addPermission(currentPermission);
	}
	
	@Transactional
	public void disassociatePermission(Long groupId, Long permissionId) {
		Group currentGroup = searchOrFail(groupId);
		Permission currentPermission = permissionRegistrationService.searchOrFail(permissionId);
		
		currentGroup.removePermission(currentPermission);
	}
}
