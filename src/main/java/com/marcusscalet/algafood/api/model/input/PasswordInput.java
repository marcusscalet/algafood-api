package com.marcusscalet.algafood.api.model.input;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordInput{
	
	@NotBlank
	private String currentPassword;
	
	@NotBlank
	private String newPassword;

}
