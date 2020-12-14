package com.marcusscalet.algafood.core.validation;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MultipleValidator implements ConstraintValidator<Multiple, Number> {

	private int multipleNumber;
	
	@Override
	public void initialize(Multiple constraintAnnotation) {
		this.multipleNumber = constraintAnnotation.numero();
	}
	
	@Override
	public boolean isValid(Number value, ConstraintValidatorContext context) {
		boolean valido = true;
		
		if (value != null) {
			var valorDecimal = BigDecimal.valueOf(value.doubleValue());
			var multipleDecimal = BigDecimal.valueOf(this.multipleNumber);
			var resto = valorDecimal.remainder(multipleDecimal);
			
			valido = BigDecimal.ZERO.compareTo(resto) == 0;
		}
		
		return valido;
	}
}