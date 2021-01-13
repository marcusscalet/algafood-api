package com.marcusscalet.algafood.api.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderedDTO {

	private Long id;

	private BigDecimal subtotal;

	private BigDecimal shippingFee;

	private BigDecimal totalCost;

	private String status;
	
	private OffsetDateTime creationDate;

	private OffsetDateTime cancellationDate;

	private OffsetDateTime deliveredDate;

	private OffsetDateTime confirmationDate;

	private RestaurantSummaryDTO restaurant;
	
	private UserDTO client;
	
	private PaymentMethodDTO paymentMethod;
	
	private AddressDTO deliveryAdrress;

	private List<OrderedItemDTO> itens;


}
