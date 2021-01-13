package com.marcusscalet.algafood.domain.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marcusscalet.algafood.domain.exception.BusinessException;
import com.marcusscalet.algafood.domain.exception.OrderedNotFoundException;
import com.marcusscalet.algafood.domain.model.City;
import com.marcusscalet.algafood.domain.model.Ordered;
import com.marcusscalet.algafood.domain.model.PaymentMethod;
import com.marcusscalet.algafood.domain.model.Product;
import com.marcusscalet.algafood.domain.model.Restaurant;
import com.marcusscalet.algafood.domain.model.User;
import com.marcusscalet.algafood.domain.repository.OrderedRepository;

@Service
public class OrderedRegistrationService {

	@Autowired
	private OrderedRepository orderedRepository;

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

	public List<Ordered> listAll() {
		return orderedRepository.findAll();
	}

	public Ordered searchOrFail(Long orderedId) {
		return orderedRepository.findById(orderedId).orElseThrow(() -> new OrderedNotFoundException(orderedId));
	}

	public Ordered save(Ordered ordered) {
		return orderedRepository.save(ordered);
	}

	@Transactional
	public Ordered generateOrder(Ordered ordered) {
		validateOrdered(ordered);
		validateItens(ordered);

		ordered.setShippingFee(ordered.getRestaurant().getShippingFee());
		ordered.calcTotalCost();

		return orderedRepository.save(ordered);
	}

	private void validateOrdered(Ordered ordered) {
		System.out.println(ordered.getClient());
		City city = cityRegistrationService.searchOrFail(ordered.getDeliveryAddress().getCity().getId());
		User client = userRegistrationService.searchOrFail(ordered.getClient().getId());
		Restaurant restaurant = restaurantRegistrationService.searchOrFail(ordered.getRestaurant().getId());
		PaymentMethod paymentMethod = paymentMethodRegistrationService.searchOrFail(ordered.getPaymentMethod().getId());

		ordered.getDeliveryAddress().setCity(city);
		ordered.setClient(client);
		ordered.setRestaurant(restaurant);
		ordered.setPaymentMethod(paymentMethod);

		if (restaurant.notAcceptedPaymentMethod(paymentMethod)) {
			throw new BusinessException(String.format("Forma de pagamento '%s' não é aceita por esse restaurante.",
					paymentMethod.getDescription()));
		}
	}

	private void validateItens(Ordered ordered) {
		ordered.getItens().forEach(item -> {
			Product product = productRegistrationService.searchOrFail(ordered.getRestaurant().getId(),
					item.getProduct().getId());

			item.setOrdered(ordered);
			item.setProduct(product);
			item.setUnitCost(product.getPrice());
		});
	}
}
