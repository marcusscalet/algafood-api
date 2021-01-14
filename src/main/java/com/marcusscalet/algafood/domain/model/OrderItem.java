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
@Entity(name = "order_item")
public class OrderItem {

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
	private Order order;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Product product;
	
	public void calcTotal() {
	    BigDecimal precoUnitario = this.getUnitCost();
	    Integer quantidade = this.getAmount();

	    if (precoUnitario == null) {
	        precoUnitario = BigDecimal.ZERO;
	    }

	    if (quantidade == null) {
	        quantidade = 0;
		}

		this.setTotalCost(unitCost.multiply(new BigDecimal(amount)));
	}
}