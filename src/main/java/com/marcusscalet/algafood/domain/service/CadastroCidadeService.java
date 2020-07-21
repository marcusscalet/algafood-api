package com.marcusscalet.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.marcusscalet.algafood.domain.exception.EntidadeEmUsoException;
import com.marcusscalet.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.marcusscalet.algafood.domain.model.Cidade;
import com.marcusscalet.algafood.domain.repository.CidadeRepository;

@Service
public class CadastroCidadeService {

	@Autowired
	private CidadeRepository cidadeRepo;

	public List<Cidade> listar(Cidade cidade) {
		return cidadeRepo.listar();
	}

	public Cidade buscar(Long id) {
		return cidadeRepo.buscar(id);
	}

	public void remover(Long id) {

		try {
			cidadeRepo.remover(id);
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe um cadastro de cidade com código %d", id));
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Cidade de código %d não pode ser removido, pois está em uso", id));
		}
	}

	public Cidade salvar(Cidade cidade) {

		return cidadeRepo.salvar(cidade);
	}

}
