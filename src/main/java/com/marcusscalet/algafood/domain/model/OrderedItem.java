package com.marcusscalet.algafood.domain.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class OrderedItem {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Integer amount;

	private BigDecimal unitCost;

	private BigDecimal totalCost;

	private String note;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Ordered ordered;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Product product;
}