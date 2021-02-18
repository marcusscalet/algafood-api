package com.marcusscalet.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.marcusscalet.algafood.api.AlgaLinks;
import com.marcusscalet.algafood.api.controller.RestaurantController;
import com.marcusscalet.algafood.api.model.RestaurantModel;
import com.marcusscalet.algafood.domain.model.Restaurant;

@Component
public class RestaurantModelAssembler extends RepresentationModelAssemblerSupport<Restaurant, RestaurantModel>  {

	@Autowired
	private ModelMapper modelMapper;
	 
    @Autowired
    private AlgaLinks algaLinks;
    
    public RestaurantModelAssembler() {
        super(RestaurantController.class, RestaurantModel.class);
    }
    
    @Override
    public RestaurantModel toModel(Restaurant restaurant) {
        RestaurantModel restaurantModel = createModelWithId(restaurant.getId(), restaurant);
        modelMapper.map(restaurant, restaurantModel);
        
        restaurantModel.add(algaLinks.linkToRestaurants("restaurants"));
        
        restaurantModel.getCuisine().add(
                algaLinks.linkToCuisine(restaurant.getCuisine().getId()));
        
        restaurantModel.getAddress().getCity().add(
                algaLinks.linkToCity(restaurant.getAddress().getCity().getId()));
        
        restaurantModel.add(algaLinks.linkToRestaurantPaymentMethods(restaurant.getId(), 
                "payment-methods"));
        
        restaurantModel.add(algaLinks.linkToRestaurantAccountables(restaurant.getId(), 
                "accountables"));
        
        if (restaurant.allowedToActivate()) {
        	restaurantModel.add(
        			algaLinks.linkToActivateRestaurant(restaurant.getId(), "active"));
        }

        if (restaurant.allowedToInactivate()) {
        	restaurantModel.add(
        			algaLinks.linkToInactivateRestaurant(restaurant.getId(), "inactive"));
        }

        if (restaurant.allowedToOpen()) {
        	restaurantModel.add(
        			algaLinks.linkToOpenRestaurant(restaurant.getId(), "open"));
        }

        if (restaurant.allowedToClose()) {
        	restaurantModel.add(
        			algaLinks.linkToCloseRestaurant(restaurant.getId(), "close"));
        }
        
        return restaurantModel;
    }

	@Override
    public CollectionModel<RestaurantModel> toCollectionModel(Iterable<? extends Restaurant> entities) {
        return super.toCollectionModel(entities)
                .add(algaLinks.linkToRestaurants());
    }   

}
