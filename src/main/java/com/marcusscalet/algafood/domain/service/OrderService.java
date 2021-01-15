package com.marcusscalet.algafood.domain.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marcusscalet.algafood.domain.exception.BusinessException;
import com.marcusscalet.algafood.domain.exception.OrderNotFoundException;
import com.marcusscalet.algafood.domain.model.City;
import com.marcusscalet.algafood.domain.model.Order;
import com.marcusscalet.algafood.domain.model.PaymentMethod;
import com.marcusscalet.algafood.domain.model.Product;
import com.marcusscalet.algafood.domain.model.Restaurant;
import com.marcusscalet.algafood.domain.model.User;
import com.marcusscalet.algafood.domain.repository.OrderRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private CityRegistrationService cityRegistrationService;

	@Autowired
	private UserRegistrationService userRegistrationService;

	@Autowired
	private RestaurantRegistrationService restaurantRegistrationService;

	@Autowired
	private PaymentMethodRegistrationService paymentMethodRegistrationService;

	@Autowired
	private ProductRegistrationService productRegistrationService;

	public List<Order> listAll() {
		return orderRepository.findAll();
	}

	public Order searchOrFail(String orderCode) {
		return orderRepository.findByCode(orderCode).orElseThrow(() -> new OrderNotFoundException(orderCode));
	}

	public Order save(Order order) {
		return orderRepository.save(order);
	}

	@Transactional
	public Order generateOrder(Order order) {
		validateOrder(order);
		validateItens(order);

		order.setShippingFee(order.getRestaurant().getShippingFee());
		order.calcTotalCost();

		return orderRepository.save(order);
	}

	private void validateOrder(Order order) {
		System.out.println(order.getClient());
		City city = cityRegistrationService.searchOrFail(order.getDeliveryAddress().getCity().getId());
		User client = userRegistrationService.searchOrFail(order.getClient().getId());
		Restaurant restaurant = restaurantRegistrationService.searchOrFail(order.getRestaurant().getId());
		PaymentMethod paymentMethod = paymentMethodRegistrationService.searchOrFail(order.getPaymentMethod().getId());

		order.getDeliveryAddress().setCity(city);
		order.setClient(client);
		order.setRestaurant(restaurant);
		order.setPaymentMethod(paymentMethod);

		if (restaurant.notAcceptedPaymentMethod(paymentMethod)) {
			throw new BusinessException(String.format("Forma de pagamento '%s' não é aceita por esse restaurante.",
					paymentMethod.getDescription()));
		}
	}

	private void validateItens(Order order) {
		order.getItens().forEach(item -> {
			Product product = productRegistrationService.searchOrFail(order.getRestaurant().getId(),
					item.getProduct().getId());

			item.setOrder(order);
			item.setProduct(product);
			item.setUnitCost(product.getPrice());
		});
	}
}
