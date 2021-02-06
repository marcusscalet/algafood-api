package com.marcusscalet.algafood.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@JsonInclude(Include.NON_NULL)
@Getter
@Builder // através do builder temos um "construtor" mais fluente ex em CityController
public class Problem {

	@ApiModelProperty(example = "400", position = 1)
	private Integer status;
	
	@ApiModelProperty(example = "2020-12-01T18:09:02.70844Z", position = 5)
	private OffsetDateTime timestamp;
	
	@ApiModelProperty(example = "https://algafood.com.br/invalid-data", position = 10)
	private String type;
	
	@ApiModelProperty(example = "Invalid Data", position = 15)
	private String title;
	
	@ApiModelProperty(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.",
			position = 25)
	private String detail;
	
	@ApiModelProperty(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.",
			position = 25)
	private String userMessage;
	
	
	@ApiModelProperty(value = "Objetos ou campos que geraram o erro.", position = 30)
	private List<Object> objects;
	
	@Getter
	@Builder
	public static class Object{
		
		private String name;
		private String userMessage;
	}
}
