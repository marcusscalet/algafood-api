package com.marcusscalet.algafood.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "order_")
public class Order {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private BigDecimal subtotal;

	private BigDecimal shippingFee;

	private BigDecimal totalCost;

	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private OffsetDateTime creationDate;
	
	private OffsetDateTime cancellationDate;

	private OffsetDateTime deliveredDate;

	private OffsetDateTime confirmationDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private PaymentMethod paymentMethod;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Restaurant restaurant;

	@ManyToOne
	@JoinColumn(name = "user_client_id", nullable = false)
	private User client;

	@Embedded
	private Address deliveryAddress;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL) //cascade é necessário para quando salvar um pedido, também salvar os itens do pedido
	private List<OrderItem> itens = new ArrayList<>();

	@Enumerated(EnumType.STRING) //esta anotação resolve o problema referente a conversão da String para Enum
	private OrderStatus status = OrderStatus.CREATED;
	
	public void calcTotalCost() {
		getItens().forEach(OrderItem::calcTotal);
		
	    this.subtotal = getItens().stream()
	        .map(item -> item.getTotalCost())
	        .reduce(BigDecimal.ZERO, BigDecimal::add);
	    
	    this.totalCost = this.subtotal.add(this.shippingFee);
	}

	public void calcShippingFee() {
	    setShippingFee(getRestaurant().getShippingFee());
	}

	public void assignItem() {
	    getItens().forEach(item -> item.setOrder(this));
	}
}
