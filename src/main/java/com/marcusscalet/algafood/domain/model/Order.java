package com.marcusscalet.algafood.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
import javax.persistence.PrePersist;

import org.hibernate.annotations.CreationTimestamp;

import com.marcusscalet.algafood.domain.exception.BusinessException;

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

	private String code;
	
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

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL) // cascade é necessário para quando salvar um pedido,
	private List<OrderItem> itens = new ArrayList<>();// também salvar os itens do pedido

	@Enumerated(EnumType.STRING) // esta anotação resolve o problema referente a conversão da String para Enum
	private StatusOrder status = StatusOrder.CREATED;

	public void calcTotalCost() {
		getItens().forEach(OrderItem::calcTotal);

		this.subtotal = getItens().stream().map(item -> item.getTotalCost()).reduce(BigDecimal.ZERO, BigDecimal::add);

		this.totalCost = this.subtotal.add(this.shippingFee);
	}

	public void confirm() {
		setStatus(StatusOrder.CONFIRMED);
		setConfirmationDate(OffsetDateTime.now());
	}

	public void deliver() {
		setStatus(StatusOrder.DELIVERED);
		setDeliveredDate(OffsetDateTime.now());
	}

	public void cancel() {
		setStatus(StatusOrder.CANCELED);
		setCancellationDate(OffsetDateTime.now());
	}

	private void setStatus(StatusOrder newStatus) {
		if (getStatus().canNotBeChangedTo(newStatus)) {
			throw new BusinessException(
					String.format("Status do pedido %s não pode ser alterado de %s para %s",
					getCode(),
					getStatus().getDescription(),
					newStatus.getDescription()));
		}

		this.status = newStatus;
	}
	
	@PrePersist
	private void generateCode() {
		setCode(UUID.randomUUID().toString());
	}
}