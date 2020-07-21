package com.marcusscalet.algafood.infraestructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.marcusscalet.algafood.domain.model.Restaurante;
import com.marcusscalet.algafood.domain.repository.RestauranteRepository;

@Repository
public class RestauranteImpl implements RestauranteRepository {

	@PersistenceContext
	EntityManager manager;

	@Override
	public List<Restaurante> listar() {
		return manager.createQuery("from Restaurante", Restaurante.class).getResultList();
	}

	@Override
	public Restaurante buscar(Long id) {
		return manager.find(Restaurante.class, id);
	}

	@Transactional
	@Override
	public Restaurante salvar(Restaurante restaurante) {
		return manager.merge(restaurante);
	}

	@Transactional
	@Override
	public void remover(Long restauranteId) {
		Restaurante restaurante = buscar(restauranteId);
		manager.remove(restaurante);
	}

}
