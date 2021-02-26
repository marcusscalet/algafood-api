package com.marcusscalet.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.marcusscalet.algafood.api.v1.AlgaLinks;
import com.marcusscalet.algafood.api.v1.controller.RestaurantController;
import com.marcusscalet.algafood.api.v1.model.RestaurantBasicModel;
import com.marcusscalet.algafood.domain.model.Restaurant;

@Component
public class RestaurantBasicModelAssembler extends RepresentationModelAssemblerSupport<Restaurant, RestaurantBasicModel> {

	    @Autowired
	    private ModelMapper modelMapper;
	    
	    @Autowired
	    private AlgaLinks algaLinks;
	    
	    public RestaurantBasicModelAssembler() {
	        super(RestaurantController.class, RestaurantBasicModel.class);
	    }
	    
	    @Override
	    public RestaurantBasicModel toModel(Restaurant restaurant) {
	        RestaurantBasicModel restaurantModel = createModelWithId(
	                restaurant.getId(), restaurant);
	        
	        modelMapper.map(restaurant, restaurantModel);
	        
	        restaurantModel.add(algaLinks.linkToRestaurants("restaurants"));
	        
	        restaurantModel.getCuisine().add(
	                algaLinks.linkToCuisine(restaurant.getCuisine().getId()));
	        
	        return restaurantModel;
	    }
	    
	    @Override
	    public CollectionModel<RestaurantBasicModel> toCollectionModel(Iterable<? extends Restaurant> entities) {
	        return super.toCollectionModel(entities)
	                .add(algaLinks.linkToRestaurants());
	    }   
	}   
