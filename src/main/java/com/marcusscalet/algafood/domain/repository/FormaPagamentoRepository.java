package com.marcusscalet.algafood.domain.repository;

import java.util.List;

import com.marcusscalet.algafood.domain.model.FormaPagamento;

public interface FormaPagamentoRepository {

	List<FormaPagamento> listar(FormaPagamento formaPagamento);

	FormaPagamento buscar(Long id);

	FormaPagamento salvar(FormaPagamento formaPagamento);

	void remover(FormaPagamento formaPagamento);
}
