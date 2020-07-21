package com.marcusscalet.algafood.infraestructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.marcusscalet.algafood.domain.model.FormaPagamento;
import com.marcusscalet.algafood.domain.repository.FormaPagamentoRepository;

@Repository
public class FormaPagamentoImpl implements FormaPagamentoRepository {

	@PersistenceContext
	EntityManager manager;

	@Override
	public List<FormaPagamento> listar(FormaPagamento formaPagamento) {
		return manager.createQuery("from FormaPagamento", FormaPagamento.class).getResultList();
	}

	@Override
	public FormaPagamento buscar(Long id) {
		return manager.find(FormaPagamento.class, id);
	}

	@Transactional
	@Override
	public FormaPagamento salvar(FormaPagamento formaPagamento) {
		return manager.merge(formaPagamento);
	}

	@Transactional
	@Override
	public void remover(FormaPagamento formaPagamento) {
		formaPagamento = buscar(formaPagamento.getId());
		manager.remove(formaPagamento);
	}

}
