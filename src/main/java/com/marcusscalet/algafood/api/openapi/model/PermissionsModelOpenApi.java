package com.marcusscalet.algafood.api.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.marcusscalet.algafood.api.v1.model.PermissionModel;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("PermissionsModel")
@Data
public class PermissionsModelOpenApi {
	private PermissionsEmbeddedModelOpenApi _embedded;
	private Links _links;

	@ApiModel("PermissionsEmbeddedModel")
	@Data
	public class PermissionsEmbeddedModelOpenApi {

		private List<PermissionModel> permissions;

	}
}