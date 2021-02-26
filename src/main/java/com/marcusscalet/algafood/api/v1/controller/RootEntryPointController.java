package com.marcusscalet.algafood.api.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marcusscalet.algafood.api.v1.AlgaLinks;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class RootEntryPointController {

	@Autowired
	private AlgaLinks algaLinks;
	
	@GetMapping
	public RootEntryPointModel root() {
		var rootEntryPointModel = new RootEntryPointModel();
		
		rootEntryPointModel.add(algaLinks.linkToCuisines("cuisines"));
		rootEntryPointModel.add(algaLinks.linkToOrders("orders"));
		rootEntryPointModel.add(algaLinks.linkToRestaurants("restaurants"));
		rootEntryPointModel.add(algaLinks.linkToGroups("groups"));
		rootEntryPointModel.add(algaLinks.linkToUsers("users"));
		rootEntryPointModel.add(algaLinks.linkToPaymentMethods("payment-methods"));
		rootEntryPointModel.add(algaLinks.linkToStates("states"));
		rootEntryPointModel.add(algaLinks.linkToCities("cities"));
		rootEntryPointModel.add(algaLinks.linkToStatistics("statistics"));
		
		return rootEntryPointModel;
	}
	
	private static class RootEntryPointModel extends RepresentationModel<RootEntryPointModel>{
		
	}
}
