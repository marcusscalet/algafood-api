package com.marcusscalet.algafood.api.model.input;

import javax.validation.constraints.NotBlank;

import com.marcusscalet.algafood.domain.model.State;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StateInput {

	@NotBlank
	private State name;
}
