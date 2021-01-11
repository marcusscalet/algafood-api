package com.marcusscalet.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marcusscalet.algafood.domain.exception.PermissionNotFoundException;
import com.marcusscalet.algafood.domain.model.Permission;
import com.marcusscalet.algafood.domain.repository.PermissionRepository;

@Service
public class PermissionRegistrationService {

	@Autowired
	private PermissionRepository permissionRepository;

	public Permission searchOrFail(Long permissionId) {
		return permissionRepository.findById(permissionId)
				.orElseThrow(() -> new PermissionNotFoundException(permissionId));
	}

}
