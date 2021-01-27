package com.marcusscalet.algafood.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductImageDTO {

	private String fileName;
	private String description;
	private String contentType;
	private Long size;
}
