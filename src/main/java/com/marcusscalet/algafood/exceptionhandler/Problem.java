package com.marcusscalet.algafood.exceptionhandler;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder // através do builder temos um "construtor" mais fluente ex em CityController
public class Problem {

	private LocalDateTime dateTime;
	private String message;
}
