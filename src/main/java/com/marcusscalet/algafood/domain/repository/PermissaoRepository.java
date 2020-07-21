package com.marcusscalet.algafood.domain.repository;

import java.util.List;

import com.marcusscalet.algafood.domain.model.Permissao;

public interface PermissaoRepository {

	List<Permissao> listar(Permissao permissao);

	Permissao buscar(Long id);

	Permissao salvar(Permissao permissao);

	void remover(Permissao permissao);
}
