package com.marcusscalet.algafood.api.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.marcusscalet.algafood.api.v1.model.GroupModel;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("GroupsModel")
@Data
public class GroupsModelOpenApi {
	
	private GroupsEmbeddedModelOpenApi _embedded;
	private Links _links;

	@ApiModel("GroupsEmbeddedModel")
	@Data
	public class GroupsEmbeddedModelOpenApi {

		private List<GroupModel> groups;

	}
}