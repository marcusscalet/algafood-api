package com.marcusscalet.algafood.core.validation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ ElementType.TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = { MultipleValidator.class })
public @interface Multiple {

	String message() default "múltiplo inválido";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
	
	int numero();
	
}