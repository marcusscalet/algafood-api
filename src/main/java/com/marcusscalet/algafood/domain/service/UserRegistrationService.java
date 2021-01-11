package com.marcusscalet.algafood.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marcusscalet.algafood.domain.exception.BusinessException;
import com.marcusscalet.algafood.domain.exception.EntityBeingUsedException;
import com.marcusscalet.algafood.domain.exception.UserNotFoundException;
import com.marcusscalet.algafood.domain.model.Group;
import com.marcusscalet.algafood.domain.model.User;
import com.marcusscalet.algafood.domain.repository.UserRepository;

@Service
public class UserRegistrationService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private GroupRegistrationService groupRegistrationService;

	public List<User> listAll() {
		return userRepository.findAll();
	}

	public User searchOrFail(Long userId) {
		return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
	}

	@Transactional
	public User save(User user) {

		userRepository.detach(user);
		Optional<User> userExists = userRepository.findByEmail(user.getEmail());

		if (userExists.isPresent() && !userExists.get().equals(user)) {
			throw new BusinessException(
					String.format("Já existe um usuário cadastrado com o e-mail informado %s", user.getEmail()));
		}

		return userRepository.save(user);
	}

	@Transactional
	public void remove(Long userId) {
		try {
			userRepository.deleteById(userId);
			userRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new UserNotFoundException(userId);
		} catch (DataIntegrityViolationException e) {
			throw new EntityBeingUsedException(
					String.format("Usuário com código %d não pode ser removido, pois está em uso", userId));
		}
	}

	@Transactional
	public void updatePassword(Long userId, String currentPassword, String newPassword) {
		User user = searchOrFail(userId);

		if (user.passwordDoesNotMatch(currentPassword)) {
			throw new BusinessException("Senha atual informada pelo usuário não coincide");
		}

		user.setPassword(newPassword);
	}

	@Transactional
	public void associateGroup(Long userId, Long groupId) {
		User currentUser = searchOrFail(userId);
		Group currentGroup = groupRegistrationService.searchOrFail(groupId);

		currentUser.addGroup(currentGroup);
	}

	@Transactional
	public void disassociateGroup(Long userId, Long groupId) {
		User currentUser = searchOrFail(userId);
		Group currentGroup = groupRegistrationService.searchOrFail(groupId);

		currentUser.removeGroup(currentGroup);
	}
}
