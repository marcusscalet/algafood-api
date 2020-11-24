package com.marcusscalet.algafood.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Getter;

@JsonInclude(Include.NON_NULL)
@Getter
@Builder // através do builder temos um "construtor" mais fluente ex em CityController
public class Problem {

	private Integer status;
	private String type;
	private String title;
	private String detail;
	
}
