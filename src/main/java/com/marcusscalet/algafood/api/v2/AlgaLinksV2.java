package com.marcusscalet.algafood.api.v2;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import com.marcusscalet.algafood.api.v2.controller.CityControllerV2;
import com.marcusscalet.algafood.api.v2.controller.CuisineControllerV2;

@Component
public class AlgaLinksV2 {

	public Link linkToCities(String rel) {
	    return linkTo(CityControllerV2.class).withRel(rel);
	}

	public Link linkToCities() {
	    return linkToCities(IanaLinkRelations.SELF.value());
	}
	
	public Link linkToCuisines(String rel) {
	    return linkTo(CuisineControllerV2.class).withRel(rel);
	}

	public Link linkToCuisines() {
	    return linkToCuisines(IanaLinkRelations.SELF.value());
	}

}
