package com.marcusscalet.algafood.api.v1;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariable.VariableType;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;
import org.springframework.stereotype.Component;

import com.marcusscalet.algafood.api.v1.controller.CityController;
import com.marcusscalet.algafood.api.v1.controller.CuisineController;
import com.marcusscalet.algafood.api.v1.controller.GroupController;
import com.marcusscalet.algafood.api.v1.controller.GroupPermissionController;
import com.marcusscalet.algafood.api.v1.controller.OrderController;
import com.marcusscalet.algafood.api.v1.controller.OrderStatusController;
import com.marcusscalet.algafood.api.v1.controller.PaymentMethodController;
import com.marcusscalet.algafood.api.v1.controller.PermissionController;
import com.marcusscalet.algafood.api.v1.controller.RestaurantController;
import com.marcusscalet.algafood.api.v1.controller.RestaurantPaymentMethodController;
import com.marcusscalet.algafood.api.v1.controller.RestaurantProductController;
import com.marcusscalet.algafood.api.v1.controller.RestaurantProductImageController;
import com.marcusscalet.algafood.api.v1.controller.RestaurantUserController;
import com.marcusscalet.algafood.api.v1.controller.StateController;
import com.marcusscalet.algafood.api.v1.controller.StatisticsController;
import com.marcusscalet.algafood.api.v1.controller.UserController;
import com.marcusscalet.algafood.api.v1.controller.UserGroupController;

@Component
public class AlgaLinks {

	public static final TemplateVariables PROJECTION_VARIABLES = new TemplateVariables(
			new TemplateVariable("projection", VariableType.REQUEST_PARAM)); 
	
	public static final TemplateVariables PAGINATION_VARIABLES = new TemplateVariables(
			new TemplateVariable("page", VariableType.REQUEST_PARAM),
			new TemplateVariable("size", VariableType.REQUEST_PARAM),
			new TemplateVariable("sort", VariableType.REQUEST_PARAM));
	 
	public Link linkToOrders(String rel) {

		TemplateVariables filtroVariables = new TemplateVariables(
				new TemplateVariable("clientId", VariableType.REQUEST_PARAM),
				new TemplateVariable("restaurantId", VariableType.REQUEST_PARAM),
				new TemplateVariable("creationStartDate", VariableType.REQUEST_PARAM),
				new TemplateVariable("creationEndDate", VariableType.REQUEST_PARAM));
		
		String ordersUrl = linkTo(OrderController.class).toUri().toString();
		
		return new Link(UriTemplate.of(ordersUrl, 
				PAGINATION_VARIABLES.concat(filtroVariables)), rel);
	}
	
	public Link linkToStatistics(String rel) {
	    return linkTo(StatisticsController.class).withRel(rel);
	}

	public Link linkToStatisticsDailySales(String rel) {
	    TemplateVariables filterVariables = new TemplateVariables(
	            new TemplateVariable("restaurantId", VariableType.REQUEST_PARAM),
	            new TemplateVariable("creationStartDate", VariableType.REQUEST_PARAM),
	            new TemplateVariable("creationEndDate", VariableType.REQUEST_PARAM),
	            new TemplateVariable("timeOffset", VariableType.REQUEST_PARAM));
	    
	    String ordersUrl = linkTo(methodOn(StatisticsController.class)
	            .queryDailySales(null, null)).toUri().toString();
	    
	    return new Link(UriTemplate.of(ordersUrl, filterVariables), rel);
	} 
	
	public Link linkToConfirmationOrder(String orderCode, String rel) {
		return linkTo(methodOn(OrderStatusController.class)
				.confirmedStatus(orderCode)).withRel(rel);
	}
	
	public Link linkToCancellationOrder(String orderCode, String rel) {
		return linkTo(methodOn(OrderStatusController.class)
				.cancelledStatus(orderCode)).withRel(rel);
	}
	
	public Link linkToDeliveredOrder(String orderCode, String rel) {
		return linkTo(methodOn(OrderStatusController.class)
				.deliveredStatus(orderCode)).withRel(rel);
	}
	
	public Link linkToRestaurant(Long restaurantId, String rel) {
	    return linkTo(methodOn(RestaurantController.class)
	            .find(restaurantId)).withRel(rel);
	}

	public Link linkToRestaurant(Long restaurantId) {
	    return linkToRestaurant(restaurantId, IanaLinkRelations.SELF.value());
	}

	public Link linkToUser(Long userId, String rel) {
	    return linkTo(methodOn(UserController.class)
	            .find(userId)).withRel(rel);
	}

	public Link linkToUser(Long userId) {
	    return linkToUser(userId, IanaLinkRelations.SELF.value());
	}

	public Link linkToUsers(String rel) {
	    return linkTo(UserController.class).withRel(rel);
	}

	public Link linkToUsers() {
	    return linkToUsers(IanaLinkRelations.SELF.value());
	}

	public Link linkToUserGroups(Long userId, String rel) {
	    return linkTo(methodOn(UserGroupController.class)
	            .listAll(userId)).withRel(rel);
	}

	public Link linkToUserGroups(Long userId) {
	    return linkToUserGroups(userId, IanaLinkRelations.SELF.value());
	}

	public Link linkToPaymentMethod(Long paymentMethodId, String rel) {
	    return linkTo(methodOn(PaymentMethodController.class)
	            .find(paymentMethodId, null)).withRel(rel);
	}

	public Link linkToPaymentMethod(Long paymentMethodId) {
	    return linkToPaymentMethod(paymentMethodId, IanaLinkRelations.SELF.value());
	}

	public Link linkToCity(Long cityId, String rel) {
	    return linkTo(methodOn(CityController.class)
	            .find(cityId)).withRel(rel);
	}

	public Link linkToCity(Long cityId) {
	    return linkToCity(cityId, IanaLinkRelations.SELF.value());
	}

	public Link linkToCities(String rel) {
	    return linkTo(CityController.class).withRel(rel);
	}

	public Link linkToCities() {
	    return linkToCities(IanaLinkRelations.SELF.value());
	}

	public Link linkToState(Long stateId, String rel) {
	    return linkTo(methodOn(StateController.class)
	            .find(stateId)).withRel(rel);
	}

	public Link linkToState(Long stateId) {
	    return linkToState(stateId, IanaLinkRelations.SELF.value());
	}

	public Link linkToStates(String rel) {
	    return linkTo(StateController.class).withRel(rel);
	}

	public Link linkToStates() {
	    return linkToStates(IanaLinkRelations.SELF.value());
	}

	public Link linkToProduct(Long restaurantId, Long productId, String rel) {
	    return linkTo(methodOn(RestaurantProductController.class)
	            .findById(restaurantId, productId))
	            .withRel(rel);
	}

	public Link linkToProduct(Long restaurantId, Long productId) {
	    return linkToProduct(restaurantId, productId, IanaLinkRelations.SELF.value());
	}
	
	public Link linkToProducts(Long restaurantId, String rel) {
	    return linkTo(methodOn(RestaurantProductController.class)
	            .listAll(restaurantId, null)).withRel(rel);
	}

	public Link linkToProducts(Long restaurantId) {
	    return linkToProducts(restaurantId, IanaLinkRelations.SELF.value());
	}

	public Link linkToCuisines(String rel) {
	    return linkTo(CuisineController.class).withRel(rel);
	}

	public Link linkToCuisines() {
	    return linkToCuisines(IanaLinkRelations.SELF.value());
	}
	
	public Link linkToRestaurants(String rel) {
	    String restaurantsUrl = linkTo(RestaurantController.class).toUri().toString();
	    
	    return new Link(UriTemplate.of(restaurantsUrl, PROJECTION_VARIABLES),rel);
	}
	
	public Link linkToRestaurants() {
	    return linkToRestaurants(IanaLinkRelations.SELF.value());
	}

	public Link linkToRestaurantPaymentMethods(Long restaurantId, String rel) {
	    return linkTo(methodOn(RestaurantPaymentMethodController.class)
	            .listAll(restaurantId)).withRel(rel);
	}

	public Link linkToCuisine(Long cuisineId, String rel) {
	    return linkTo(methodOn(CuisineController.class)
	            .find(cuisineId)).withRel(rel);
	}

	public Link linkToCuisine(Long cuisineId) {
	    return linkToCuisine(cuisineId, IanaLinkRelations.SELF.value());
	}  
	
	public Link linkToOpenRestaurant(Long restauranteId, String rel) {
	    return linkTo(methodOn(RestaurantController.class)
	            .openRestaurant(restauranteId)).withRel(rel);
	}

	public Link linkToCloseRestaurant(Long restaurantId, String rel) {
	    return linkTo(methodOn(RestaurantController.class)
	            .closeRestaurant(restaurantId)).withRel(rel);
	}

	public Link linkToInactivateRestaurant(Long restaurantId, String rel) {
	    return linkTo(methodOn(RestaurantController.class)
	            .inactivate(restaurantId)).withRel(rel);
	}

	public Link linkToActivateRestaurant(Long restaurantId, String rel) {
	    return linkTo(methodOn(RestaurantController.class)
	            .activate(restaurantId)).withRel(rel);
	}
	
	public Link linkToRestaurantPaymentMethods(Long restaurantId) {
	    return linkToRestaurantPaymentMethods(restaurantId, IanaLinkRelations.SELF.value());
	}

	public Link linkToPaymentMethods(String rel) {
	    return linkTo(PaymentMethodController.class).withRel(rel);
	}

	public Link linkToPaymentMethods() {
	    return linkToPaymentMethods(IanaLinkRelations.SELF.value());
	} 
	
	public Link linkToRestaurantPaymentMethodDetach(Long restaurantId, Long paymentMethodId, String rel) {
		return linkTo(methodOn(RestaurantPaymentMethodController.class)
				.detach(restaurantId, paymentMethodId)).withRel(rel);
	}
	
	public Link linkToRestaurantPaymentMethodAttach(Long restaurantId, String rel) {
		return linkTo(methodOn(RestaurantPaymentMethodController.class)
				.attach(restaurantId, null)).withRel(rel);
	}
	
	public Link linkToRestaurantAccountables(Long restaurantId, String rel) {
	    return linkTo(methodOn(RestaurantUserController.class)
	            .listAll(restaurantId)).withRel(rel);
	}

	public Link linkToRestaurantAccountables(Long restaurantId) {
	    return linkToRestaurantAccountables(restaurantId, IanaLinkRelations.SELF.value());
	}
	
	public Link linkToRestaurantAccountableDisassociate(Long restaurantId, Long userId, String rel) {
		   return linkTo(methodOn(RestaurantUserController.class)
		            .disassociateUser(restaurantId, userId)).withRel(rel);
	}

	public Link linkToRestaurantAccountableAssociate(Long restaurantId, String rel) {
		  return linkTo(methodOn(RestaurantUserController.class)
		            .associateUser(restaurantId, null)).withRel(rel);
	}
	
	public Link linkToProductImage(Long restaurantId, Long productId, String rel) {
	    return linkTo(methodOn(RestaurantProductImageController.class)
	            .searchImage(restaurantId, productId)).withRel(rel);
	}

	public Link linkToProductImage(Long restaurantId, Long productId) {
	    return linkToProductImage(restaurantId, productId, IanaLinkRelations.SELF.value());
	}
	
	public Link linkToGroups(String rel) {
	    return linkTo(GroupController.class).withRel(rel);
	}

	public Link linkToGroups() {
	    return linkToGroups(IanaLinkRelations.SELF.value());
	}

	public Link linkToGroupPermissions(Long groupId, String rel) {
	    return linkTo(methodOn(GroupPermissionController.class)
	            .listAll(groupId)).withRel(rel);
	}     
	
	public Link linkToPermissions(String rel) {
	    return linkTo(PermissionController.class).withRel(rel);
	}

	public Link linkToPermissions() {
	    return linkToPermissions(IanaLinkRelations.SELF.value());
	}

	public Link linkToGroupPermissions(Long groupId) {
	    return linkToGroupPermissions(groupId, IanaLinkRelations.SELF.value());
	}

	public Link linkToGroupPermissionAttach(Long groupId, String rel) {
	    return linkTo(methodOn(GroupPermissionController.class)
	            .attach(groupId, null)).withRel(rel);
	}

	public Link linkToGroupPermissionDetach(Long groupId, Long permissionId, String rel) {
	    return linkTo(methodOn(GroupPermissionController.class)
	            .detach(groupId, permissionId)).withRel(rel);
	}
	
	public Link linkToUserGroupAssociate(Long userId, String rel) {
	    return linkTo(methodOn(UserGroupController.class)
	            .associate(userId, null)).withRel(rel);
	}

	public Link linkToUserGroupDisassociate(Long userId, Long groupId, String rel) {
	    return linkTo(methodOn(UserGroupController.class)
	            .disassociate(userId, groupId)).withRel(rel);
	}     
}
