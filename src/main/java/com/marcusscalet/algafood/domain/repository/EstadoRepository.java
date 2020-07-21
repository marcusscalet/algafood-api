package com.marcusscalet.algafood.domain.repository;

import java.util.List;

import com.marcusscalet.algafood.domain.model.Estado;

public interface EstadoRepository {

	List<Estado> listar();

	Estado buscar(Long id);

	Estado salvar(Estado estado);

	void remover(Long id);

}
